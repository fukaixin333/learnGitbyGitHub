package com.citic.server.cbrc.outer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.basic.IPollingTask;
import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.cbrc.domain.Br40_cxqq;
import com.citic.server.cbrc.domain.Br41_kzqq;
import com.citic.server.cbrc.domain.CBRC_BasicInfo;
import com.citic.server.cbrc.domain.CBRC_QueryPerson;
import com.citic.server.cbrc.domain.CBRC_Response;
import com.citic.server.cbrc.domain.CBRC_ReturnReceipt;
import com.citic.server.cbrc.domain.response.CBRC_ControlResponse;
import com.citic.server.cbrc.domain.response.CBRC_FreezeResponse;
import com.citic.server.cbrc.domain.response.CBRC_LiInfosResponse;
import com.citic.server.cbrc.domain.response.CBRC_QueryResponse;
import com.citic.server.cbrc.domain.response.CBRC_QueryScanResponse;
import com.citic.server.cbrc.domain.response.CBRC_StopPaymentResponse;
import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ProcessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.FtpUtils;
import com.citic.server.utils.SftpUtils;
import com.citic.server.utils.Zip4jUtils;

/**
 * 公安部&银监会、最高检察院、国家安全机关等外联轮询任务
 * 
 * @author Liu Xuanfei
 * @date 2016年8月23日 下午2:57:03
 */
public abstract class OuterPollingTaskCBRC extends AbstractPollingTask {
	private final Logger logger = LoggerFactory.getLogger(OuterPollingTaskCBRC.class);
	
	private SftpUtils sftpUtils;
	private FtpUtils ftpUtils;
	
	private String zipFileSavePath; // 请求文件包本地下载目录
	private String zipFileSuccessPath; // 请求文件包本地存储目录（处理成功）
	private String zipFileFailedPath; // 请求文件包本地存储目录（处理失败）
	
	@Autowired
	@Qualifier("outerPollingServiceCBRC")
	private OuterPollingServiceCBRC outerPollingService;
	
	/**
	 * 本地子目录的键（KEY）
	 * 
	 * @return
	 */
	protected abstract String fileDirectoryKey();
	
	/**
	 * 文件存储子目录 - ${FileSaveDirectory}
	 * <p>
	 * 文件存储绝对路径：<em>${FileRootPath}/download/${FileSaveDirectory}</em><br />
	 * 处理成功文件存储绝对路径：<em>${FileRootPath}/download/${FileSaveDirectory}/done</em><br />
	 * 处理失败文件存储绝对路径：<em>${FileRootPath}/download/${FileSaveDirectory}/failed</em>
	 * 
	 * @return
	 */
	protected abstract String getFileSaveDirectory();
	
	/**
	 * 远程下载目录
	 * 
	 * @return
	 */
	protected abstract String getRemoteDownloadPath();
	
	/**
	 * 远程上传目录
	 * 
	 * @return
	 */
	protected abstract String getRemoteUploadPath();
	
	@Override
	public IPollingTask initPollingTask() throws Exception {
		// 初始化固定路径变量
		String fileRootPath = ServerEnvironment.getFileRootPath();
		this.zipFileSavePath = fileRootPath + File.separator + "download" + File.separator + getFileSaveDirectory();
		this.zipFileSuccessPath = zipFileSavePath + File.separator + "done";
		this.zipFileFailedPath = zipFileSavePath + File.separator + "failed";
		
		// 初始化SFTP工具类
		sftpUtils = new SftpUtils();
		sftpUtils.setHost(getRemoteServerHost());
		sftpUtils.setUsername(getUsername());
		sftpUtils.setPassword(getPassword());
		
		// 初始化FTP工具类
		ftpUtils = new FtpUtils();
		ftpUtils.setServer(getRemoteServerHost());
		ftpUtils.setUser(getUsername());
		ftpUtils.setPassword(getPassword());
		
		return super.initPollingTask();
	}
	
	@Override
	public void executeAction() {
		// 远程下载目录
		String remoteDownloadPath = getRemoteDownloadPath();
		String remoteUploadPath = getRemoteUploadPath();
		// 是否多法人模式
		boolean isMultiCorpType = ServerEnvironment.getBooleanValue(Keys.MULTI_CORPORATION_TYPE);
		
		if (isMultiCorpType) {
			List<String> multiCorpList = service.selectOrganByRepType(getTaskType());
			for (int i = 0; i < multiCorpList.size(); i++) {
				String reporg = multiCorpList.get(i);
				if (StringUtils.isNotBlank(reporg)) {
					String organDirectory = StringUtils.substringBefore(reporg, "-");
					String realRemoteDownloadPath = organDirectory + File.separator + remoteDownloadPath + File.separator;
					String realRemoteUploadPath = organDirectory + File.separator + remoteUploadPath + File.separator;
					
					process(realRemoteDownloadPath, realRemoteUploadPath, StringUtils.substringAfter(reporg, "-"));
				} else {
					logger.error("应用程序设置为'多法人模式'，未查询到多法人机构信息。请检查[MP02_REP_ORG]是否梳理初始化数据。");
				}
			}
		} else {
			process(remoteDownloadPath, remoteUploadPath, getOrganKey());
		}
	}
	
	private void process(String remoteDownloadPath, String remoteUploadPath, String organkey) {
		logger.info("正在从远程下载目录同步请求文件包 - [{}]...", remoteDownloadPath);
		
		long tm1 = System.currentTimeMillis();
		int s_total = 0;
		int s_repeat = 0;
		int s_invalid = 0;
		int s_fail = 0;
		
		// 是否使用SFTP协议
		boolean isSftpMode = "SFTP".equals(ServerEnvironment.getStringValue(CBRCKeys.REMOTE_ACCESS_MODE));
		
		List<String> remoteFileList;
		try {
			if (isSftpMode) {
				remoteFileList = sftpUtils.list(remoteDownloadPath);
			} else {
				ftpUtils.setRemotepath(remoteDownloadPath); // 
				ftpUtils.setLocalpath(this.zipFileSavePath); // 
				remoteFileList = ftpUtils.getFileNameList(null);
			}
		} catch (Exception e) {
			logger.error("从远程下载目录同步请求文件清单失败：{}", e.getMessage(), e);
			return;
		}
		
		if (remoteFileList == null || remoteFileList.size() == 0) {
			s_total = 0;
		} else {
			String taskType = getTaskType();
			for (String remoteFileName : remoteFileList) {
				long li1 = System.currentTimeMillis();
				// 解析文件名
				logger.info("正在校验文件名 - [{}]...", remoteFileName);
				if (remoteFileName.endsWith(".zip")) {
					s_total++;
				} else {
					try {
						logger.info("正在从远程下载目录下传日志文件 - [{}]...", remoteFileName);
						String localSavePath = this.zipFileSavePath + File.separator + "temp";
						if (isSftpMode) {
							sftpUtils.get(remoteDownloadPath, remoteFileName, localSavePath, remoteFileName);
						} else {
							ftpUtils.setRemotepath(remoteDownloadPath); // 
							ftpUtils.setLocalpath(localSavePath); // 
							ftpUtils.getfile(remoteFileName);
						}
						if (remoteFileName.endsWith(".log") && logger.isDebugEnabled()) {
							byte[] log = CommonUtils.readBinaryFile(localSavePath + File.separator + remoteFileName);
							logger.debug("读取日志信息：[\r\n{}]", new String(log, StandardCharsets.GBK)); // 居然是GBK！！
						}
					} catch (Exception e) {
						logger.error("从远程下载目录下载日志文件失败 - [{}][{}]：{}", remoteFileName, remoteDownloadPath, e.getMessage(), e);
						continue;
					}
					// 清理日志文件
					logger.info("正在清理远程下载目录日志文件 - [{}]...", remoteFileName);
					processLogFile(isSftpMode, remoteDownloadPath, remoteFileName);
					continue;
				}
				
				String realZipFileName; // 
				String organCode; // 查控机构代码
				String bankCode; // 目标机构代码（银行代码）
				String transSerialNumber; // 查控请求单标识
				int point = remoteFileName.lastIndexOf(".");
				if (point > 0) {
					realZipFileName = remoteFileName.substring(0, point);
				} else {
					realZipFileName = remoteFileName;
				}
				if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) { // 国家安全机关
					if (realZipFileName.length() == 47) {
						organCode = remoteFileName.substring(0, 8);
						bankCode = remoteFileName.substring(8, 25);
						transSerialNumber = remoteFileName.substring(25, 47);
					} else {
						logger.warn("请求文件包名不符合命名规范 - [{}]", remoteFileName);
						s_invalid++;
						continue;
					}
				} else {
					if (realZipFileName.length() == 45) {
						organCode = remoteFileName.substring(0, 6);
						bankCode = remoteFileName.substring(6, 23);
						transSerialNumber = remoteFileName.substring(23, 45);
					} else {
						logger.warn("请求文件包文件名不符合命名规范 - [{}]", remoteFileName);
						s_invalid++;
						continue;
					}
				}
				
				// 同步请求文件包
				String zipFilePath = this.zipFileSavePath + File.separator + remoteFileName;
				File zipFile = new File(zipFilePath);
				if (zipFile.exists() && zipFile.isFile()) {
					logger.warn("忽略请求文件包 - [{}]：本地下载目录存在同名文件", remoteFileName);
					s_repeat++;
					continue;
				}
				
				try {
					logger.info("正在从远程下载目录下载请求文件包 - [{}]...", remoteFileName);
					if (isSftpMode) {
						sftpUtils.get(remoteDownloadPath, remoteFileName, this.zipFileSavePath, remoteFileName);
					} else {
						ftpUtils.setRemotepath(remoteDownloadPath); // 
						ftpUtils.setLocalpath(this.zipFileSavePath); // 
						ftpUtils.getfile(remoteFileName);
					}
				} catch (Exception e) {
					logger.error("从远程下载目录下载请求文件包失败 - [{}][{}]：{}", remoteFileName, remoteDownloadPath, e.getMessage(), e);
					s_fail++;
					continue;
				}
				
				// 接收回执（代号：RR28）
				CBRC_ReturnReceipt returnReceipt = new CBRC_ReturnReceipt();
				returnReceipt.setTasktype(taskType);
				returnReceipt.setQqdbs(transSerialNumber);
				returnReceipt.setSqjgdm(organCode);
				returnReceipt.setMbjgdm(bankCode);
				
				// 处理请求文件包
				boolean done = true; // 是否处理成功
				try {
					processRequestFile(zipFilePath, remoteFileName);
					returnReceipt.setHzdm(CBRCConstants.REC_CODE_OK); // 回执代码
					returnReceipt.setHzsm("成功接收查控文件"); // 回执说明
				} catch (ProcessException e) {
					logger.error("处理请求文件失败[{}]: {}", e.getCode(), e.getMessage(), e);
					s_invalid++;
					done = false;
					returnReceipt.setHzdm(e.getCode()); // 回执代码
					returnReceipt.setHzsm(e.getMessage()); // 回执说明
				} catch (Exception e) {
					logger.error("应用程序错误: {}", e.getMessage(), e);
					s_fail++;
					done = false;
					returnReceipt.setHzdm(CBRCConstants.REC_CODE_30002); // 回执代码
					returnReceipt.setHzsm("查控报文解析错误 - 应用程序错误：" + e.getMessage()); // 回执说明
				}
				
				// 响应接收回执
				logger.info("正在向监管响应接收回执 - [{}]...", remoteFileName);
				processReturnReceipt(done, remoteUploadPath, returnReceipt, isSftpMode);
				
				// 物理文件清理
				long li2 = System.currentTimeMillis();
				logger.info("请求数据包处理完成，共耗时[{}]毫秒，应用程序准备清理物理文件...", (li2 - li1));
				processFile(done, remoteDownloadPath, zipFilePath, remoteFileName, isSftpMode);
			}
		}
		
		long tm2 = System.currentTimeMillis();
		logger.info("本次轮询请求文件包完成，共耗时[" + (tm2 - tm1) + "]毫秒，发现[" + s_total + "]个请求包，去重[" + s_repeat + "]个，无效[" + s_invalid + "]个，失败[" + s_fail + "]个。");
	}
	
	private void processRequestFile(String zipFilePath, String zipFileName) throws ProcessException, Exception {
		String taskType = getTaskType(); // 
		String realZipFileName; // 
		int point = zipFileName.lastIndexOf(".");
		if (point > 0) {
			realZipFileName = zipFileName.substring(0, point);
		} else {
			realZipFileName = zipFileName;
		}
		String relativePath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, fileDirectoryKey());
		String extractRelativePath = relativePath + File.separator + realZipFileName;
		String extractAbsolutePath = ServerEnvironment.getFileRootPath() + extractRelativePath;
		
		// 解压请求文件包
		logger.info("成功校验请求数据包，正在解压压缩文件 - [{}]...", zipFileName);
		File extractDirectory = new File(extractAbsolutePath);
		if (!extractDirectory.exists() || !extractDirectory.isDirectory()) {
			extractDirectory.mkdirs();
		}
		Zip4jUtils.extractAllFiles(zipFilePath, extractAbsolutePath, realZipFileName); // 请求包文件名作为解压密码
		
		// 请求文件分类（XML报文、附件）
		logger.info("正在处理请求XML报文、法律文书及附件 - [{}]...", zipFileName);
		String[] fileList = extractDirectory.list();
		List<String> xmlFileList = new ArrayList<String>();
		List<String> attatchFileList = new ArrayList<String>();
		for (String fileName : fileList) {
			int suffix = fileName.lastIndexOf(".");
			if (suffix > 0) {
				String extension = fileName.substring(suffix, fileName.length()).trim();
				if (extension.endsWith(".xml")) {
					xmlFileList.add(fileName);
					continue;
				}
			}
			attatchFileList.add(fileName);
		}
		
		if (xmlFileList.size() == 0) {
			throw new ProcessException(CBRCConstants.REC_CODE_30005, "缺少请求报文文件");
		}
		
		// 逐一解析XML报文
		boolean validate = true; // 是否需要验证文书信息
		CBRC_BasicInfo basicInfo = null;
		CBRC_QueryPerson queryPerson = null;
		List<CBRC_LiInfosResponse> liInfoList = null;
		List<MC20_task_msg> taskMsgList = new ArrayList<MC20_task_msg>();
		for (String xmlFileName : xmlFileList) {
			// 不支持分类代码过滤
			//			String code = xmlFileName.substring(0, 4); // 分类代码
			//			if ("SS01".equals(code) || "SS02".equals(code) || "SS05".equals(code) || "SS06".equals(code)) {
			//			} else {
			//				throw new ProcessException(CBRCConstants.REC_CODE_30002, "暂不支持此类交易 - 应用系统目前只支持SS01、SS02、SS05、SS06");
			//			}
			
			String realXmlFileName = xmlFileName.substring(0, xmlFileName.lastIndexOf("."));
			String taskKey = taskType + "_" + realXmlFileName;
			// 排除已经接收过尚未反馈的任务
			if (isMessageReceived(taskKey)) {
				logger.warn("忽略重复请求XML文件 - [{}][{}]：请检查远程下载目录是否存在未清理请求文件包", taskKey, xmlFileName);
				throw new ProcessException(CBRCConstants.REC_CODE_30002, "查控报文解析错误 - 重复/重名查控请求文件");
			}
			
			String xmlRelativePath = extractRelativePath + File.separator + xmlFileName;
			String xmlAbsolutePath = extractAbsolutePath + File.separator + xmlFileName;
			String classCode = xmlFileName.substring(0, 4).toUpperCase();
			CBRC_Response response = null;
			if ("SS01".equals(classCode) || "SS02".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_QueryResponse.class, "cbrc_query_resp" + taskType, xmlAbsolutePath);
			} else if ("SS05".equals(classCode) || "SS06".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_QueryResponse.class, "cbrc_queryaccount_resp" + taskType, xmlAbsolutePath);
			} else if ("SS11".equals(classCode) || "SS12".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_ControlResponse.class, "cbrc_control_resp" + taskType, xmlAbsolutePath);
			} else if ("SS17".equals(classCode) || "SS18".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_FreezeResponse.class, "cbrc_freeze_resp" + taskType, xmlAbsolutePath);
			} else if ("SS21".equals(classCode) || "SS22".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_StopPaymentResponse.class, "cbrc_stoppayment_resp" + taskType, xmlAbsolutePath);
			} else if ("SS25".equals(classCode)) { // 调阅凭证图像申请
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_QueryScanResponse.class, "cbrc_queryscan_resp" + taskType, xmlAbsolutePath);
			} else if ("LI25".equals(classCode) || "LI26".equals(classCode) || "LI27".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_Response.class, "cbrc_liinfo_resp" + taskType, xmlAbsolutePath);
				List<CBRC_LiInfosResponse> liInfos = response.getLiInfos();
				if (liInfos == null) {
				} else {
					for (CBRC_LiInfosResponse liInfo : liInfos) {
						String wjmc = liInfo.getWjmc();
						String wsnr = liInfo.getWsnr();
						if (wjmc == null || wjmc.length() == 0 || wsnr == null || wsnr.length() == 0) {
						} else {
							byte[] data = Utility.decodeBase64(wsnr); // Base64解码
							String liInfoPath = CommonUtils.writeBinaryFile(data, extractAbsolutePath, wjmc); // 写物理附件
							if ("LI25".equals(classCode)) { // 是否法律文书
								validate = false;
							}
							logger.info("输出并保存法律文书或警官证物理文件 - [{}]", liInfoPath);
							liInfo.setWsnr(wjmc);
							if (liInfoList == null) {
								liInfoList = new ArrayList<CBRC_LiInfosResponse>();
							}
							liInfoList.add(liInfo);
						}
					}
				}
				continue; // 继续扫描下一个XML文件
			} else {
				throw new ProcessException(CBRCConstants.REC_CODE_30002, "查控报文解析错误，无效分类代码");
			}
			
			// 基本信息
			if (basicInfo == null) {
				basicInfo = response.getBasicInfo();
			}
			// 请求人信息
			if (queryPerson == null) {
				queryPerson = response.getQueryPerson();
			}
			// 附件信息
			if (liInfoList == null) { // 暂无XML格式的附件
				liInfoList = response.getLiInfos(); // 监管不同，请求报文不一定存在<LIINFOS>标签
			}
			
			MC20_task_msg taskMsg = new MC20_task_msg();
			taskMsg.setTaskkey(taskKey);
			taskMsg.setCode(classCode);
			taskMsg.setBdhm(realXmlFileName);
			taskMsg.setPacketkey(realZipFileName);
			taskMsg.setMsgname(xmlFileName);
			taskMsg.setMsgpath(xmlRelativePath);
			taskMsg.setCreatedt(Utility.currDateTime19());
			taskMsgList.add(taskMsg);
		}
		
		// 验证文书信息
		if (validate) {
			// 无XML格式的法律文书，判断是否存在物理附件
			if (attatchFileList == null || attatchFileList.size() == 0) {
				throw new ProcessException(CBRCConstants.REC_CODE_30003, "查控法律文书缺失");
			}
			// 处理物理附件，并判断是否有法律文书物理文件
			if (liInfoList == null || liInfoList.size() == 0) {
				liInfoList = new ArrayList<CBRC_LiInfosResponse>();
				for (String attatchFile : attatchFileList) {
					// 创建LiInfo对象
					CBRC_LiInfosResponse liInfo = new CBRC_LiInfosResponse();
					liInfo.setWsnr(attatchFile);
					liInfo.setWjmc(attatchFile);
					
					// 判断附件是法律文书或警官证
					String prefix = attatchFile.substring(0, 4).toUpperCase();
					if ("LI25".equals(prefix)) {
						validate = false;
						liInfo.setWslx(".pdf");
					} else {
						liInfo.setWjlx(".jpg");
					}
					
					liInfoList.add(liInfo);
				}
			} else {
				for (CBRC_LiInfosResponse liInfo : liInfoList) {
					String wsnr = liInfo.getWsnr();
					String prefix = wsnr.substring(0, 4).toUpperCase();
					if ("LI25".equals(prefix)) { // 只验证文书信息
						if (attatchFileList.contains(wsnr)) {
							validate = false;
						} else {
							throw new ProcessException(CBRCConstants.REC_CODE_30004, "法律文书与请求单信息不符");
						}
					}
				}
			}
			// 是否仍然需要验证法律文书（即，无法律文书物理文件）
			if (validate) {
				throw new ProcessException(CBRCConstants.REC_CODE_30003, "查控法律文书缺失");
			}
		}
		
		// 存入数据库
		if (basicInfo == null || queryPerson == null) {
			throw new ProcessException(CBRCConstants.REC_CODE_30002, "查控报文解析错误 - 缺少基本数据项或查询人数据项");
		} else {
			logger.info("正在将请求数据入库 - [{}]...", realZipFileName);
			processDatabase(basicInfo, queryPerson, liInfoList, taskMsgList, extractRelativePath);
		}
	}
	
	private void processDatabase(CBRC_BasicInfo basicInfo, CBRC_QueryPerson queryPerson, List<CBRC_LiInfosResponse> liInfoList, List<MC20_task_msg> taskMsgList, String extractRelativePath)
		throws ProcessException {
		String taskType = getTaskType();
		
		String qqcslx = basicInfo.getQqcslx(); // 请求措施类型
		MC21_task task = getTaskClassDef(qqcslx);
		
		if (task == null) {
			throw new ProcessException(CBRCConstants.REC_CODE_99999, "暂不支持此类交易[QQCSLX=\"" + qqcslx + "\"]");
		}
		
		String currDateTime = Utility.currDateTime19();
		String currDate = Utility.currDate10();
		String organKey = getOrganKey();
		
		List<MC20_Task_Fact1> taskFactList = new ArrayList<MC20_Task_Fact1>();
		for (MC20_task_msg taskMsg : taskMsgList) {
			MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
			taskFact.setTaskKey(taskMsg.getTaskkey());
			taskFact.setSubTaskID(qqcslx);
			taskFact.setBdhm(taskMsg.getBdhm());
			taskFact.setTaskID(task.getTaskID());
			taskFact.setTaskType(task.getTaskType());
			taskFact.setTaskName(task.getTaskName());
			taskFact.setTaskCMD(task.getTaskCMD());
			taskFact.setIsDYNA(task.getIsDYNA());
			taskFact.setDatatime(currDateTime);
			taskFact.setFreq("1");
			taskFact.setTaskObj(organKey == null ? "" : organKey); //
			taskFact.setTGroupID("1");
			taskFactList.add(taskFact);
		}
		
		List<MC20_WS> wsList = new ArrayList<MC20_WS>();
		for (CBRC_LiInfosResponse liInfo : liInfoList) {
			String liFileName = liInfo.getWsnr();
			int point = liFileName.lastIndexOf(".");
			if (point > 0) {
				liFileName = liFileName.substring(0, point);
			}
			
			MC20_WS mc20_ws = new MC20_WS();
			mc20_ws.setXh(liFileName);
			mc20_ws.setBdhm(basicInfo.getQqdbs());
			mc20_ws.setWjmc(liInfo.getWjmc());
			mc20_ws.setWjlx(liInfo.getWjlx());
			mc20_ws.setWslb(liInfo.getWslx());
			mc20_ws.setWspath(extractRelativePath + File.separator + liInfo.getWsnr());
			// attach表需要的值
			mc20_ws.setDatetime(currDate);
			mc20_ws.setTasktype(taskType);
			
			wsList.add(mc20_ws);
		}
		
		Br40_cxqq br40_cxqq = null;
		Br41_kzqq br41_kzqq = null;
		if (CBRCConstants.ROUTINE.equals(qqcslx) // 常规查询
			|| CBRCConstants.DYNAMIC.equals(qqcslx) // 动态查询
			|| CBRCConstants.DYNAMIC_CONTINUE.equals(qqcslx) // 继续动态查询
			|| CBRCConstants.DYNAMIC_RELIEVE.equals(qqcslx) // 动态查询解除
			|| CBRCConstants.QUERY_SCAN.equals(qqcslx)) { // 凭证图像调阅
			br40_cxqq = new Br40_cxqq();
			copyProperties(br40_cxqq, basicInfo, queryPerson);
			br40_cxqq.setStatus("0");// 状态
			br40_cxqq.setQrydt(Utility.currDate10());
			br40_cxqq.setTasktype(taskType);// 监管类别
			br40_cxqq.setOrgkey(organKey);
		} else {
			br41_kzqq = new Br41_kzqq();
			copyProperties(br41_kzqq, basicInfo, queryPerson);
			br41_kzqq.setStatus("0");// 状态
			br41_kzqq.setQrydt(Utility.currDate10());
			br41_kzqq.setTasktype(taskType);// 监管类别
			br41_kzqq.setOrgkey(organKey);
		}
		
		// 事务管理
		outerPollingService.transaction(basicInfo.getQqdbs(), taskType, taskFactList, taskMsgList, wsList, br40_cxqq, br41_kzqq);
	}
	
	private void processLogFile(boolean isSftpMode, String remoteDownloadPath, String logFileName) {
		// 远程文件处理
		try {
			if (isSftpMode) {
				sftpUtils.removeFile(remoteDownloadPath, logFileName);
			} else {
				ftpUtils.setRemotepath(remoteDownloadPath); // 
				ftpUtils.removeFile(logFileName);
			}
		} catch (Exception e) {
			logger.warn("清理远程日志文件失败，请手动处理，以免造成数据积压 - [{}][{}]", remoteDownloadPath, logFileName);
		}
	}
	
	private void processFile(boolean done, String remoteDownloadPath, String zipFilePath, String zipFileName, boolean isSftpMode) {
		String localMovePath = done ? this.zipFileSuccessPath : this.zipFileFailedPath;
		localMovePath = localMovePath + File.separator + Utility.currDate8();
		// 本地文件处理
		try {
			FileUtils.moveFile(zipFilePath, localMovePath);
		} catch (Exception e) {
			logger.error("清理本地物理文件异常，应用程序默认忽略此异常。", e);
		}
		
		// 远程文件处理
		try {
			if (isSftpMode) {
				sftpUtils.removeFile(remoteDownloadPath, zipFileName);
			} else {
				ftpUtils.setRemotepath(remoteDownloadPath); // 
				ftpUtils.removeFile(zipFileName);
			}
		} catch (Exception e) {
			logger.warn("清理远程物理文件失败，请手动处理，以免造成数据积压 - [{}][{}]", remoteDownloadPath, zipFileName);
		}
	}
	
	private void processReturnReceipt(boolean done, String remoteUploadPath, CBRC_ReturnReceipt returnReceipt, boolean isSftpMode) {
		String taskType = getTaskType();
		String localMovePath = done ? this.zipFileSuccessPath : this.zipFileFailedPath;
		localMovePath = localMovePath + File.separator + Utility.currDate8();
		// 反馈接收回执文件包
		String xmlFileName = "RR28" + returnReceipt.getMbjgdm() + returnReceipt.getQqdbs() + "000000000001.xml";
		String xmlFilePath = localMovePath + File.separator + xmlFileName;
		String realZipFileName = returnReceipt.getSqjgdm() + returnReceipt.getMbjgdm() + returnReceipt.getQqdbs() + "000000000001";
		String zipFileName = realZipFileName + ".zip";
		String zipFilePath = localMovePath + File.separator + zipFileName;
		try {
			returnReceipt.setJssj(Utility.currDateTime19());
			// 生成XML回执文件及ZIP文件包
			CommonUtils.marshallUTF8Document(returnReceipt, "cbrc_returnreceipt_req" + taskType, localMovePath, xmlFileName);
			if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) { // 国家安全机关
				Zip4jUtils.addFilesWithDeflateComp(zipFilePath, new String[] {xmlFilePath});
			} else {
				Zip4jUtils.addFilesWithStandardEncryption(zipFilePath, new String[] {xmlFilePath}, realZipFileName); //
			}
			
			// 上传回执文件
			if (isSftpMode) {
				sftpUtils.put(localMovePath, zipFileName, remoteUploadPath, zipFileName);
			} else {
				ftpUtils.setRemotepath(remoteUploadPath); // 
				ftpUtils.setLocalpath(localMovePath); // 
				ftpUtils.uploadFile(new File(zipFilePath));
			}
			// 删除XML回执文件
			File xmlFile = new File(xmlFilePath);
			if (xmlFile.exists()) {
				xmlFile.delete();
			}
		} catch (Exception e) {
			logger.error("向监管响应接收回执异常：{}", e.getMessage(), e);
		}
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(CBRCKeys.OUTER_POLLING_PERIOD, "10");
	}
	
	/**
	 * 远程服务器地址
	 * 
	 * @return
	 */
	protected String getRemoteServerHost() {
		return ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_HOST);
	}
	
	/**
	 * SFTP/FTP远程连接密码
	 * 
	 * @return
	 */
	protected String getUsername() {
		return ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_USERNAME);
	}
	
	/**
	 * SFTP/FTP远程连接用户名
	 * 
	 * @return
	 */
	protected String getPassword() {
		return ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_PASSWORD);
	}
	
	// ==========================================================================================
	//                     Help Behavior
	// ==========================================================================================
	
	/**
	 * 复制 JavaBean 之间的属性值，不借助第三方的工具
	 */
	private void copyProperties(Br40_cxqq br40_cxqq, CBRC_BasicInfo basicInfo, CBRC_QueryPerson queryPerson) {
		br40_cxqq.setQqdbs(basicInfo.getQqdbs()); // 请求单标识
		br40_cxqq.setQqcslx(basicInfo.getQqcslx()); // 请求措施类型
		br40_cxqq.setSqjgdm(basicInfo.getSqjgdm()); // 申请机构代码
		br40_cxqq.setMbjgdm(basicInfo.getMbjgdm()); // 目标机构代码
		br40_cxqq.setZtlb(basicInfo.getZtlb()); // 查控主体类别
		br40_cxqq.setAjlx(basicInfo.getAjlx()); // 案件类型
		br40_cxqq.setJjcd(basicInfo.getJjcd()); // 紧急程度
		br40_cxqq.setBeiz(basicInfo.getBeiz()); // 备注
		br40_cxqq.setFssj(basicInfo.getFssj()); // 发送时间
		
		br40_cxqq.setQqrxm(queryPerson.getQqrxm()); // 请求人姓名
		br40_cxqq.setQqrzjlx(queryPerson.getQqrzjlx()); // 请求人证件类型
		br40_cxqq.setQqrzjhm(queryPerson.getQqrzjhm()); // 请求人证件号码
		br40_cxqq.setQqrdwmc(queryPerson.getQqrdwmc()); // 请求人单位名称
		br40_cxqq.setQqrsjh(queryPerson.getQqrsjh()); // 请求人手机号
		br40_cxqq.setXcrxm(queryPerson.getXcrxm()); // 协查人姓名
		br40_cxqq.setXcrzjlx(queryPerson.getXcrzjlx()); // 协查人证件类型
		br40_cxqq.setXcrzjhm(queryPerson.getXcrzjhm()); // 协查人证件号码
		br40_cxqq.setQqrbgdh(queryPerson.getQqrbgdh()); // 请求人办公电话
		br40_cxqq.setXcrsjh(queryPerson.getXcrsjh()); // 协查人手机号
		br40_cxqq.setXcrbgdh(queryPerson.getXcrbgdh()); // 协查人办公电话
		br40_cxqq.setLast_up_dt(Utility.currDateTime19());//最后修改时间
		br40_cxqq.setRecipient_time(Utility.currDateTime19());//接收时间
	}
	
	/**
	 * 复制 JavaBean 之间的属性值，不借助第三方的工具
	 */
	private void copyProperties(Br41_kzqq br41_kzqq, CBRC_BasicInfo basicInfo, CBRC_QueryPerson queryPerson) {
		br41_kzqq.setQqdbs(basicInfo.getQqdbs()); // 请求单标识
		br41_kzqq.setQqcslx(basicInfo.getQqcslx()); // 请求措施类型
		br41_kzqq.setSqjgdm(basicInfo.getSqjgdm()); // 申请机构代码
		br41_kzqq.setMbjgdm(basicInfo.getMbjgdm()); // 目标机构代码
		br41_kzqq.setZtlb(basicInfo.getZtlb()); // 查控主体类别
		br41_kzqq.setAjlx(basicInfo.getAjlx()); // 案件类型
		br41_kzqq.setJjcd(basicInfo.getJjcd()); // 紧急程度
		br41_kzqq.setBeiz(basicInfo.getBeiz()); // 备注
		br41_kzqq.setFssj(basicInfo.getFssj()); // 发送时间
		
		br41_kzqq.setQqrxm(queryPerson.getQqrxm()); // 请求人姓名
		br41_kzqq.setQqrzjlx(queryPerson.getQqrzjlx()); // 请求人证件类型
		br41_kzqq.setQqrzjhm(queryPerson.getQqrzjhm()); // 请求人证件号码
		br41_kzqq.setQqrdwmc(queryPerson.getQqrdwmc()); // 请求人单位名称
		br41_kzqq.setQqrsjh(queryPerson.getQqrsjh()); // 请求人手机号
		br41_kzqq.setXcrxm(queryPerson.getXcrxm()); // 协查人姓名
		br41_kzqq.setXcrzjlx(queryPerson.getXcrzjlx()); // 协查人证件类型
		br41_kzqq.setXcrzjhm(queryPerson.getXcrzjhm()); // 协查人证件号码
		br41_kzqq.setLast_up_dt(Utility.currDateTime19());//最后修改时间
		br41_kzqq.setRecipient_time(Utility.currDateTime19());//接收时间
	}
}
