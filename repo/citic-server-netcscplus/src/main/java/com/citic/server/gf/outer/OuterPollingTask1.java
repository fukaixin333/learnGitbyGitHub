package com.citic.server.gf.outer;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citic.server.SpringContextHolder;
import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.gf.domain.Br32_packet;
import com.citic.server.gf.domain.MC20_GZZ;
import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.gf.domain.response.CertificateResponse;
import com.citic.server.gf.domain.response.CertificateResponse_ZjInfo;
import com.citic.server.gf.domain.response.ControlResponse;
import com.citic.server.gf.domain.response.ControlResponse_Kzqq;
import com.citic.server.gf.domain.response.QueryResponse;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;
import com.citic.server.gf.domain.response.WritResponse;
import com.citic.server.gf.domain.response.WritResponse_WsInfo;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DbFuncUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FtpUtils;
import com.citic.server.utils.SftpUtils;
import com.citic.server.utils.StrUtils;
import com.citic.server.utils.ZipUtils;

/**
 * Outer Polling Task - 最高人民法院|网络查控|读取请求文件
 * 
 * @author Liu Xuanfei
 * @date 2016年3月6日 下午4:13:07
 */
@Service("outerPollingTask1")
public class OuterPollingTask1 extends AbstractPollingTask implements Constants {
	private static final Logger logger = LoggerFactory.getLogger(OuterPollingTask1.class);
	
	private final List<String> CacheFile = new ArrayList<String>(); // 缓存已经读取过的文件
	
	// 查询请求：QA+银行代码【17位】+请求批次号【30位】
	// 法律请求文书：QI+银行代码【17位】+请求批次号【30位】
	// 法官证件：QC+银行代码【17位】+请求批次号【30位】
	
	private final String TXCODE_CX = "CXQQ"; // <searching marker>
	private final String TXCODE_KZ = "KZQQ"; //
	private final String path = File.separator + "gf" + File.separator + "download";
	
	@Autowired
	private PollingTaskMapper service;
	
	private DbFuncUtils dbfunc;
	private SftpUtils sftpUtils;
	private FtpUtils ftpUtils;
	
	private DbFuncUtils getDbFuncUtils() {
		if (dbfunc == null) {
			dbfunc = new DbFuncUtils();
		}
		return dbfunc;
	}
	
	// 非线程安全
	private SftpUtils getSftpUtils() {
		if (sftpUtils == null) {
			sftpUtils = new SftpUtils();
			sftpUtils.setHost(ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_HOST));
			sftpUtils.setUsername(ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_USERNAME));
			sftpUtils.setPassword(ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_PASSWORD));
		}
		return sftpUtils;
	}
	
	// 非线程安全
	private FtpUtils getFtpUtils() {
		if (ftpUtils == null) {
			ftpUtils = new FtpUtils();
			ftpUtils.setServer(ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_HOST));
			ftpUtils.setUser(ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_USERNAME));
			ftpUtils.setPassword(ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_PASSWORD));
		}
		return ftpUtils;
	}
	
	@Override
	public void executeAction() { // 读取请求文件
		long li1 = System.currentTimeMillis();
		logger.info("执行[最高人民法院|网络查控]轮询任务…");
		
		int zipNum = 0;
		int xmlNum = 0;
		String defaultDownloadPath = ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_DOWNPATH);// FTP下载目录 
		boolean isMultiCorpType = ServerEnvironment.getBooleanValue(Keys.MULTI_CORPORATION_TYPE); // 是否多法人 1为多法人
		if (isMultiCorpType) {
			List<String> multi_corpList = service.selectMP02_REP_ORG(); // 查询所有法人行号
			for (int i = 0; i < multi_corpList.size(); i++) {
				String reporg = multi_corpList.get(i); // 人行机构编码-总行机构编码
				if (StringUtils.isNotBlank(reporg)) {
					String realDownloadPath = StringUtils.substringBefore(reporg, "-") + File.separator + defaultDownloadPath + File.separator;
					try {
						int[] _s = downloadZipFile(realDownloadPath, StringUtils.substringAfter(reporg, "-"));
						zipNum += _s[0];
						xmlNum += _s[1];
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			try {
				//取总行机构号
				CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
				HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
				String orgkey = StrUtils.null2String((String) sysParaMap.get("innerOrgKey"));
				int[] _s = downloadZipFile(defaultDownloadPath, orgkey);
				zipNum = _s[0];
				xmlNum = _s[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 上传数据包到服务器
		try {
			uploadZipFile(isMultiCorpType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long li2 = System.currentTimeMillis();
		logger.info("本次轮询任务完成，共耗时[" + (li2 - li1) + "]ms，下载[" + zipNum + "]个批次Zip包，共[" + xmlNum + "]个XML文件。");
	}
	
	private int[] downloadZipFile(String downloadPath, String organkey) throws Exception {
		int zipNum = 0;
		int xmlNum = 0;
		String localDownloadPath = ServerEnvironment.getFileRootPath() + path;
		List<String> zipFileList = null;
		if ("sftp".equals(ServerEnvironment.getStringValue(Keys.FTP_TYPE))) {
			// SFTP
			logger.debug("SFTP Download Path: " + ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_DOWNPATH));
			zipFileList = getSftpUtils().list(downloadPath);
		} else {
			// 从服务器下载目录到本地
			getFtpUtils().setRemotepath(downloadPath);
			getFtpUtils().setLocalpath(localDownloadPath); // 本地服务器地址
			zipFileList = getFtpUtils().getFileNameList(null);
		}
		
		ZipUtils zipUtils = new ZipUtils();
		for (String zipFileName : zipFileList) {
			// 下载Zip包
			String zipFilePath = localDownloadPath + File.separator + zipFileName;
			if (new File(zipFilePath).exists()) {
				continue;
			}
			try {
				if ("sftp".equals(ServerEnvironment.getStringValue(Keys.FTP_TYPE))) {
					getSftpUtils().get(downloadPath, zipFileName, localDownloadPath, zipFileName);
				} else {
					getFtpUtils().getfile(zipFileName);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			zipNum++; //
			
			if (zipFileName.startsWith("CXHZ")) { // 查询反馈失败回执
				dealFeedback(localDownloadPath, zipFileName, "CXHZ", organkey);
			} else if (zipFileName.startsWith("KZHZ")) { // 控制反馈失败回执
				dealFeedback(localDownloadPath, zipFileName, "KZHZ", organkey);
			}
			
			// 解压Zip包
			String unzipPath = localDownloadPath + File.separator;
			int point = zipFileName.lastIndexOf(".");
			if (point > 0) {
				unzipPath = unzipPath + zipFileName.substring(0, point);
			} else {
				unzipPath = unzipPath + zipFileName;
			}
			File directory = new File(unzipPath);
			if (!directory.exists() || !directory.isDirectory()) {
				directory.mkdirs();
			}
			
			try {
				zipUtils.unzip(zipFilePath, unzipPath);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			File[] xmlFiles = directory.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isFile() && file.getName().endsWith(".xml");
				}
			});
			
			for (File xml : xmlFiles) {
				xmlNum++;
				marshallXML(xml, zipFileName, organkey);
			}
		}
		return new int[] {zipNum, xmlNum};
	}
	
	public void marshallXML(File xmlFile, String zipFileName, String organkey) {
		// 文件去重
		if (CacheFile.contains(xmlFile.getAbsolutePath())) {
			return;
		}
		try {
			if (xmlFile.getName().startsWith("QA")) { // 请求报文
				String packetkey = zipFileName.substring(0, 51); //zipFileName.substring(21, 51); 
				if (zipFileName.startsWith("CXQQ")) { // 查询请求
					QueryResponse cxqqList = (QueryResponse) CommonUtils.unmarshallDocument(QueryResponse.class, "binding_query_res", xmlFile.getAbsolutePath(), "UTF-8");
					String errorMsg = cxqqList.getErrorMsg();
					if (StringUtils.isNotBlank(errorMsg)) {
						logger.debug(errorMsg);
						return;
					}
					
					List<QueryResponse_Cxqq> list = cxqqList.getCxqqList();
					for (QueryResponse_Cxqq cxqq : list) {
						try {
							dealCxqq(cxqq, packetkey, organkey);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (zipFileName.startsWith("KZQQ")) { // 控制请求
					ControlResponse kzqqList = (ControlResponse) CommonUtils.unmarshallDocument(ControlResponse.class, "binding_control_res", xmlFile.getAbsolutePath(), "UTF-8");
					String errorMsg = kzqqList.getErrorMsg();
					if (StringUtils.isNotBlank(errorMsg)) {
						logger.debug(errorMsg);
						return;
					}
					
					List<ControlResponse_Kzqq> list = kzqqList.getKzqqList();
					for (ControlResponse_Kzqq kzqq : list) {
						try {
							dealKzqq(kzqq, packetkey, organkey);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (zipFileName.startsWith("CXFK")) { // 查询反馈
				
				} else if (zipFileName.startsWith("KZFK")) { // 控制反馈
				
				}
			} else if (xmlFile.getName().startsWith("QI")) { // 法律文书
				WritResponse wsinfoList = (WritResponse) CommonUtils.unmarshallDocument(WritResponse.class, "binding_writ_res", xmlFile.getAbsolutePath(), "UTF-8");
				String errorMsg = wsinfoList.getErrorMsg();
				if (StringUtils.isNotBlank(errorMsg)) { // 错误
					logger.debug(errorMsg);
					return;
				}
				
				String rootpath = ServerEnvironment.getFileRootPath_Q();
				String cardpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, Keys.FILE_DIRECTORY_GF);
				List<WritResponse_WsInfo> list = wsinfoList.getWsInfoList();
				for (WritResponse_WsInfo wsinfo : list) {
					try {
						if (wsinfo.getWsnr() == null) {
							continue;
						}
						
						String filename = StrUtils.null2String(wsinfo.getWsbh()) + StrUtils.null2String(wsinfo.getBdhm()) + wsinfo.getXh() + "." + wsinfo.getWjlx();
						CommonUtils.writeBinaryFile(wsinfo.getWsnr(), rootpath + cardpath, filename);
						
						MC20_WS mc20_ws = new MC20_WS();
						BeanUtils.copyProperties(mc20_ws, wsinfo); //
						mc20_ws.setWspath(cardpath + File.separator + filename);
						mc20_ws.setWspath(mc20_ws.getWspath().replace("\\", "/"));
						if (mc20_ws.getBdhm() == null) {
							mc20_ws.setBdhm("");
						}
						if (mc20_ws.getWsbh() == null) {
							mc20_ws.setWsbh("");
						}
						mc20_ws.setTasktype(TASK_TYPE_GF); // 文书表增加TaskType字段
						
						// INSERT文书信息表
						service.deleteMC20_WS(mc20_ws);
						service.insertMC20_WS(mc20_ws);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (xmlFile.getName().startsWith("QC")) { // 工作证
				CertificateResponse zjinfoList = (CertificateResponse) CommonUtils.unmarshallDocument(CertificateResponse.class, "binding_certificate_res", xmlFile.getAbsolutePath(), "UTF-8");
				String errorMsg = zjinfoList.getErrorMsg();
				if (StringUtils.isNotBlank(errorMsg)) { // 错误
					logger.debug(errorMsg);
					return;
				}
				
				String rootpath = ServerEnvironment.getFileRootPath_Q();
				String cardpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, Keys.FILE_DIRECTORY_GF);
				List<CertificateResponse_ZjInfo> list = zjinfoList.getZjInfoList();
				for (CertificateResponse_ZjInfo zjinfo : list) {
					try {
						if ((zjinfo.getGzz() == null || zjinfo.getGzz().length == 0) && (zjinfo.getGwz() == null || zjinfo.getGwz().length == 0)) {
							continue;
						}
						
						MC20_GZZ mc20_gzz = new MC20_GZZ();
						BeanUtils.copyProperties(mc20_gzz, zjinfo); //
						if (mc20_gzz.getBdhm() == null) {
							mc20_gzz.setBdhm("");
						}
						
						// 工作证
						if (zjinfo.getGzz() != null && zjinfo.getGzz().length > 0) {
							String filename = zjinfo.getGzzbm() + "." + zjinfo.getGzzwjgs();
							CommonUtils.writeBinaryFile(zjinfo.getGzz(), rootpath + cardpath, filename);
							mc20_gzz.setGzzpath(cardpath + File.separator + filename);
						}
						
						// 公务证
						if (zjinfo.getGwz() != null && zjinfo.getGwz().length > 0) {
							String filename = zjinfo.getGwzbm() + "." + zjinfo.getGwzwjgs();
							CommonUtils.writeBinaryFile(zjinfo.getGwz(), rootpath + cardpath, filename);
							mc20_gzz.setGwzpath(cardpath + File.separator + filename);
						}
						mc20_gzz.setGzzpath(mc20_gzz.getGzzpath().replace("\\", "/"));
						mc20_gzz.setGwzpath(mc20_gzz.getGwzpath().replace("\\", "/"));
						
						mc20_gzz.setTasktype(TASK_TYPE_GF);
						// INSERT工作证信息表
						service.deleteMC20_GZZ(mc20_gzz);
						service.insertMC20_GZZ(mc20_gzz);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CacheFile.add(xmlFile.getAbsolutePath());
	}
	
	private void dealFeedback(String localDownloadPath, String zipFileName, String code, String organkey) {
		try {
			int point = zipFileName.lastIndexOf(".");
			String zipKey;
			if (point > 0) {
				zipKey = zipFileName.substring(0, point);
			} else {
				zipKey = zipFileName;
			}
			String taskKey = ServerEnvironment.TASK_TYPE_GF + "_" + zipKey;
			if (isMessageReceived(taskKey)) {
				return;
			}
			
			MC21_task task = getTaskClassDef(code);
			String currDateTime = Utility.currDateTime19();
			
			MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
			taskFact.setTaskKey(taskKey);
			taskFact.setSubTaskID(code);
			taskFact.setBdhm(zipKey);
			taskFact.setTaskID(task.getTaskID());
			taskFact.setTaskType(task.getTaskType());
			taskFact.setTaskName(task.getTaskName());
			taskFact.setTaskCMD(task.getTaskCMD());
			taskFact.setIsDYNA(task.getIsDYNA());
			taskFact.setDatatime(currDateTime);
			taskFact.setFreq("1");
			taskFact.setTaskObj(organkey == null ? "" : organkey);
			taskFact.setTGroupID("1");
			
			MC20_task_msg taskMessage = new MC20_task_msg();
			taskMessage.setTaskkey(taskKey);
			taskMessage.setCode(code);
			taskMessage.setBdhm(zipKey);
			taskMessage.setMsgpath(localDownloadPath + File.separator + zipFileName);
			taskMessage.setCreatedt(currDateTime);
			String packetseq = zipFileName.substring(0, 51); // 包的批次号
			String packetsequece = getDbFuncUtils().getSeq("SEQ_BR32_PACKET");
			String packetkey = packetseq + Utility.currDate8() + packetsequece;
			taskMessage.setPacketkey(packetkey);
			
			service.insertMC20_Task_Fact1(taskFact);
			service.insertMC20_Task_Msg(taskMessage);
			insertBr32_packet(packetkey, code, zipFileName);
			
			cacheReceivedTaskKey(taskKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertBr32_packet(String packetkey, String code, String zipFileName) {
		Br32_packet packet = new Br32_packet();
		packet.setPacketkey(packetkey);
		packet.setPack_type_cd(code);
		packet.setCreate_dt(DtUtils.getNowTime());
		packet.setSenddate_dt(DtUtils.getNowDate());
		packet.setStatus_cd("0");
		packet.setFilepath(path + "/" + zipFileName);
		packet.setFilename(zipFileName);
		
		service.insertBR32_Packet(packet); // 插入“包”表
	}
	
	@Transactional
	private void dealCxqq(QueryResponse_Cxqq cxqq, String packetkey, String organkey) throws FileNotFoundException, JiBXException {
		String bdhm = cxqq.getBdhm();
		String taskKey = ServerEnvironment.TASK_TYPE_GF + "_" + TXCODE_CX + bdhm;
		if (isMessageReceived(taskKey)) { // 任务去重
			return;
		}
		
		String rootpath = ServerEnvironment.getFileRootPath();
		String subPath = CommonUtils.createRelativePath(Keys.FILE_PATH_TEMP, Keys.FILE_DIRECTORY_GF);
		String filename = bdhm + ".xml";
		CommonUtils.marshallDocument(cxqq, rootpath + subPath, filename, "UTF-8");
		
		// 任务信息入库
		MC21_task task = getTaskClassDef(TXCODE_CX);
		String currDateTime = Utility.currDateTime19();
		
		MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
		taskFact.setTaskKey(taskKey);
		taskFact.setSubTaskID(TXCODE_CX);
		taskFact.setBdhm(bdhm);
		taskFact.setTaskID(task.getTaskID());
		taskFact.setTaskType(task.getTaskType());
		taskFact.setTaskName(task.getTaskName());
		taskFact.setTaskCMD(task.getTaskCMD());
		taskFact.setIsDYNA(task.getIsDYNA());
		taskFact.setDatatime(currDateTime);
		taskFact.setFreq("1");
		taskFact.setTaskObj(organkey == null ? "" : organkey); // 稍后写机构
		taskFact.setTGroupID("1");
		
		MC20_task_msg taskMessage = new MC20_task_msg();
		taskMessage.setTaskkey(taskKey);
		taskMessage.setCode(TXCODE_CX);
		taskMessage.setBdhm(bdhm);
		taskMessage.setMsgpath(subPath + File.separator + filename);
		taskMessage.setCreatedt(currDateTime);
		taskMessage.setPacketkey(packetkey);
		
		service.insertMC20_Task_Fact1(taskFact);
		service.insertMC20_Task_Msg(taskMessage);
		
		cacheReceivedTaskKey(taskKey);
	}
	
	@Transactional
	private void dealKzqq(ControlResponse_Kzqq kzqq, String packetkey, String organkey) throws FileNotFoundException, JiBXException {
		String bdhm = kzqq.getBdhm();
		String taskKey = ServerEnvironment.TASK_TYPE_GF + "_" + TXCODE_KZ + bdhm;
		if (isMessageReceived(taskKey)) { // 任务去重
			return;
		}
		
		String rootpath = ServerEnvironment.getFileRootPath();
		String subPath = CommonUtils.createRelativePath(Keys.FILE_PATH_TEMP, Keys.FILE_DIRECTORY_GF);
		String filename = bdhm + ".xml";
		CommonUtils.marshallDocument(kzqq, rootpath + subPath, filename, "UTF-8");
		
		// 任务信息入库
		MC21_task task = getTaskClassDef(TXCODE_KZ);
		String currDateTime = Utility.currDateTime19();
		
		MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
		taskFact.setTaskKey(taskKey);
		taskFact.setSubTaskID(TXCODE_KZ);
		taskFact.setBdhm(bdhm);
		taskFact.setTaskID(task.getTaskID());
		taskFact.setTaskType(task.getTaskType());
		taskFact.setTaskName(task.getTaskName());
		taskFact.setTaskCMD(task.getTaskCMD());
		taskFact.setIsDYNA(task.getIsDYNA());
		taskFact.setDatatime(currDateTime);
		taskFact.setFreq("1");
		taskFact.setTaskObj(organkey == null ? "" : organkey); // 稍后写机构
		taskFact.setTGroupID("1");
		
		MC20_task_msg taskMessage = new MC20_task_msg();
		taskMessage.setTaskkey(taskKey);
		taskMessage.setCode(TXCODE_KZ);
		taskMessage.setBdhm(bdhm);
		taskMessage.setMsgpath(subPath + File.separator + filename);
		taskMessage.setCreatedt(currDateTime);
		taskMessage.setPacketkey(packetkey);
		
		service.insertMC20_Task_Fact1(taskFact);
		service.insertMC20_Task_Msg(taskMessage);
		
		cacheReceivedTaskKey(taskKey);
	}
	
	private void uploadZipFile(boolean isMultiCorpType) throws Exception {
		
		// 设置上传路径
		String uploadpath = ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_UPLOADPATH);
		// 查询需要上传的数据包
		String root = ServerEnvironment.getFileRootPath_Q();
		List<Br32_packet> packetList = service.selectUploadPacket("0");
		for (Br32_packet br32_packet : packetList) {
			String packetkey = br32_packet.getPacketkey();
			String packetPath = root + br32_packet.getFilepath();
			String rhorgankey = br32_packet.getFilename().substring(4, 21);
			if (isMultiCorpType) {
				uploadpath = File.separator + rhorgankey + File.separator + ServerEnvironment.getStringValue(Keys.FTP_OUTER_POLLING_UPLOADPATH);
			}
			if ("sftp".equals(ServerEnvironment.getStringValue(Keys.FTP_TYPE))) {
				getSftpUtils().put(packetPath.replace(br32_packet.getFilename(), ""), br32_packet.getFilename(), uploadpath, br32_packet.getFilename());
			} else {
				getFtpUtils().setRemotepath(uploadpath);
				getFtpUtils().uploadDirectory(packetPath);
			}
			// 上传到服务器
			// 修改数据表的状态
			service.updatePacketStatus(packetkey);
		}
		
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_GF;
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(Keys.OUTER_POLLING_TASK_GF_PERIOD, "30");
	}
}
