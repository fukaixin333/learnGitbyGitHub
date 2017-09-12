package com.citic.server.whpsb.outer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.crypto.DESCoder;
import com.citic.server.crypto.MD5Coder;
import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.runtime.HexCoder;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.runtime.WhpsbKeys;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.SftpUtils;
import com.citic.server.utils.ZipUtils;
import com.citic.server.whpsb.WhpsbConstants;
import com.citic.server.whpsb.domain.Br51_cxqq;
import com.citic.server.whpsb.domain.Br51_cxqq_back_msg;
import com.citic.server.whpsb.domain.Whpsb_Header;
import com.citic.server.whpsb.domain.Whpsb_SadxBody;
import com.citic.server.whpsb.domain.request.Whpsb_RequestCkrzl;
import com.citic.server.whpsb.domain.request.Whpsb_RequestCkrzl_Sadx;
import com.citic.server.whpsb.domain.request.Whpsb_RequestJymx;
import com.citic.server.whpsb.domain.request.Whpsb_RequestKhzl;
import com.citic.server.whpsb.domain.request.Whpsb_RequestKhzl_Sadx;
import com.citic.server.whpsb.domain.request.Whpsb_RequestZhxx;
import com.citic.server.whpsb.domain.request.Whpsb_RequestZhxx_Sadx;
import com.citic.server.whpsb.domain.response.Whpsb_Response;
import com.citic.server.whpsb.mapper.Whpsb_outerListenerMapper;
import com.google.common.collect.Lists;

/**
 * @author 殷雄
 * @date 2016年09月02日 上午11:34:46
 */
@Component("outerPollingTaskWhpsb")
public class OuterPollingTaskWhpsb extends AbstractPollingTask {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Whpsb_outerListenerMapper whpsb_mapper;
	
	private SftpUtils sftpUtils;
	/** 个人账户信息查询xml文件名前缀 */
	private final String ZHXX_GR_PRIFX = "bank_grzhxx";
	/** 个人帐（卡）号持有人查询xml文件名前缀 */
	private final String ZHCYR_GR_PRIFX = "bank_grzhcyr";
	/** 个人开户资料查询xml文件名前缀 */
	private final String KHZL_GR_PRIFX = "bank_grkhzl";
	/** 个人交易明细查询xml文件名前缀 */
	private final String JYMX_GR_PRIFX = "bank_grjymx";
	/** 单位账户信息查询xml文件名前缀 */
	private final String ZHXX_DW_PRIFX = "bank_dwzhxx";
	/** 单位帐（卡）号持有人查询xml文件名前缀 */
	private final String ZHCYR_DW_PRIFX = "bank_dwzhcyr";
	/** 单位开户资料查询xml文件名前缀 */
	private final String KHZL_DW_PRIFX = "bank_dwkhzl";
	/** 单位交易明细查询xml文件名前缀 */
	private final String JYMX_DW_PRIFX = "bank_dwjymx";
	
	/** xml响应报文生成目录名 */
	private final String XML_RES_PATH = "res";
	
	/**
	 * 轮询执行入口
	 * A:1. sftp取zip包 2. 去重处理 3. 根据zip文件名，将文件分为4大类，8小类处理 4. 进行相应业务处理；
	 * B:1. 查询待处理的文件 2.压缩加密处理 3.发送监管
	 */
	@Override
	public void executeAction() {
		try {
			logger.info("===========================");
			logger.info("====武汉公安轮询取任务开始====");
			logger.info("===========================");
			//1.sftp获取指定目录下的所有文件名称
			ArrayList<String> fileNameList = getSftpUtils().list(ServerEnvironment.getStringValue(WhpsbKeys.SFTP_RECV_DIR));
			//2.移除非zip的文件名
			fileNameList = this.getZipFileNameList(fileNameList);
			if (fileNameList != null && fileNameList.size() > 0) {
				//3.sftp取文件
				String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
				String zippath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, WhpsbKeys.FILE_DIRECTORY_WHPSB);
				String path = rootpath + zippath;//zip包存放路径
				File dir = new File(path);
				if (!dir.exists() || !dir.isDirectory()) {// 确保目录存在
					dir.mkdirs();
				}
				ArrayList<String> list = getSftpUtils().getFileMore(ServerEnvironment.getStringValue(WhpsbKeys.SFTP_RECV_DIR), fileNameList, path);
				//4.文件下载完成，进行解密
				logger.info("===本次欲取ZIP数：" + fileNameList.size() + "====实际取回ZIP数：" + list.size());
				int count = 0;//计算处理成功的zip包个数
				for (int i = 0; i < list.size(); i++) {//逐个解密并解析zip包
					boolean zt = this.dealZip(path, zippath, list.get(i));
					if (zt) {
						count++;
					}
				}
				logger.info("===本次轮询结果：====实际处理ZIP包个数：" + list.size() + "==成功处理个数：" + count);
			} else {
				logger.info("===本次轮询结果：====没有需要下载处理的zip文件");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			logger.info("=============================");
			logger.info("====武汉公安轮询登记任务开始====");
			logger.info("=============================");
			//查数据库，取任务
			// HashMap<String, List<Br51_cxqq_back_msg>> map = new HashMap<String, List<Br51_cxqq_back_msg>>();
			ArrayList<Br51_cxqq_back_msg> msgList = whpsb_mapper.selectBr51_cxqq_back_msg();
			if (msgList != null && msgList.size() > 0) {
				int count = 0;//记录发送成功的zip个数
				//拆分根据批次号拆分list[拆分后，每个批次一个zip包]
				Set<String> set = new HashSet<String>();
				for (Br51_cxqq_back_msg msg : msgList) {
					if (!set.contains(msg.getMsgseq())) {
						set.add(msg.getMsgseq());
					}
				}
				
				for (String msgseq : set) {
					ArrayList<Br51_cxqq_back_msg> list = new ArrayList<Br51_cxqq_back_msg>();
					for (Br51_cxqq_back_msg msg : msgList) {
						if (msgseq.equals(msg.getMsgseq())) {
							list.add(msg);
						}
					}
					Br51_cxqq br51_cxqq = new Br51_cxqq();
					//压缩加密发送
					if (this.doFile2ZipAndSend(list)) {
						//修改反馈文件的状态
						whpsb_mapper.updateBr51_cxqq_back_msg(msgseq);//更新数据状态
						br51_cxqq.setStatus("3");//0：待处理 1：待生成报文 2：待打包反馈 3：反馈成功 4：反馈失败
						count++;
					} else {//发送失败
						br51_cxqq.setStatus("4");
					}
					//修改br51_cxqq的状态
					br51_cxqq.setMsgseq(msgseq);
					br51_cxqq.setLast_up_dt(Utility.currDate10());
					br51_cxqq.setFeedback_time(Utility.currDateTime19());
					whpsb_mapper.updateBr51_cxqq(br51_cxqq);
				}
				logger.info("===本次轮询结果：====欲发送zip数：" + set.size() + "==实际发送数：" + count);
			} else {
				logger.info("===本次轮询结果：====没有需要发送登记的zip文件");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 压缩加密sftp发送
	 * 
	 * @param list
	 * @return
	 */
	private boolean doFile2ZipAndSend(ArrayList<Br51_cxqq_back_msg> list) {
		boolean flag = true;
		try {
			String zippath = "";
			String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
			String relativepath = list.get(0).getMsg_filepath().replaceAll("\\\\", "/");//文件相对路径［没有根］
			String xmlname = list.get(0).getMsg_filename();
			int x = relativepath.lastIndexOf("/");
			if (x != -1) {
				zippath = rootpath + relativepath.substring(0, x);//压缩包路径
			} else {
				logger.info("====文件路径有问题====路径：" + rootpath + relativepath);
				return false;
			}
			String temZipName = "";//未加密的zip文件名
			String xmlFullName = "";//待压缩的文件绝对路径
			String zipFullName = "";//压缩后的文件绝对路径
			//1.压缩文件
			if (list.size() == 1) {
				temZipName = xmlname.replace(".xml", "_tmp.zip");
				xmlFullName = rootpath + relativepath;
				zipFullName = zippath + File.separator + temZipName;
				ZipUtils.zip(zipFullName, xmlFullName);
			} else {//交易明细才会存在多个文件［1个xml＋n个txt］
				ArrayList<String> fileList = new ArrayList<String>(list.size());//全部文件名
				for (Br51_cxqq_back_msg ms : list) {//找出xml文件
					if (ms.getMsg_filename().endsWith(".xml")) {
						xmlname = ms.getMsg_filename();
					}
					fileList.add(zippath + File.separator + ms.getMsg_filename());
				}
				temZipName = xmlname.replace(".xml", "_tmp.zip");
				xmlFullName = rootpath + relativepath;//relativepath中已经有文件分隔符号
				zipFullName = zippath + File.separator + temZipName;
				ZipUtils.zip(zipFullName, fileList, "");//多文件压缩
			}
			//2.加密文件
			String zipfilename = this.Zip2Encode(zipFullName, xmlname, zippath);
			//3.sftp上传
			String remoteDir = ServerEnvironment.getStringValue(WhpsbKeys.SFTP_SEND_DIR);
			this.getSftpUtils().putWith(zippath, zipfilename, remoteDir, zipfilename);
		} catch (Exception e) {
			flag = false;
			logger.error("=====ZIP发送失败====");
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * zip文件解密并进行业务处理
	 * 
	 * @param path zip包路径
	 * @param subpath path去掉root层的路径
	 * @param zipFileName zip文件名
	 * @throws
	 */
	public boolean dealZip(String path, String subpath, String zipFileName) {
		boolean flag = true;
		try {
			
			//1.解密zip文件
			String zipPath = path + File.separator + zipFileName;
			File zipFile = new File(zipPath);
			byte[] hexData = this.getFile2Bytes(zipFile);
			String keystr = ServerEnvironment.getStringValue(WhpsbKeys.WHPSB_ZIP_SECRET_KEY);
			byte[] key = keystr.getBytes("UTF-8");//密钥
			byte[] data = HexCoder.decode(new String(hexData, StandardCharsets.UTF_8));
			byte[] decryptData = DESCoder.decrypt(data, key);//解密后的zip字节数组
			//2.解压解密后的zip包到当前文件夹下
			int point = zipFileName.lastIndexOf(".");
			String unzipPath = path + File.separator + zipFileName.substring(0, point);
			subpath += File.separator + zipFileName.substring(0, point);//xml相对路径
			File directory = new File(unzipPath);
			if (!directory.exists() || !directory.isDirectory()) {// 确保目录存在
				directory.mkdirs();
			}
			this.unZipByDataByte(decryptData, unzipPath);//将解密后的zip字节数据转化为流进行解压处理
			
			// 3.处理解压文件
			String[] filenames = FileUtils.getFileNameListByDirectoryAndType(directory, "file");//获取解压目录下的全部文件
			String[] xmlnames = FileUtils.getFileNameListByDirectoryAndType(directory, "xml");//获取解压目录下的xml文件
			if (xmlnames == null || xmlnames.length == 0) {
				logger.warn("=====ZIP包中xml文件缺失=====zip包路径：" + path + File.separator + zipFileName);
				return false;
			}
			
			//=====================================================================================
			//       通过联系武汉方面，法律文书等附件暂时不会放到zip包中，因此，此处暂时注销文书校验
			//======================================================================================
			//3.1判断文书信息，如果没有文书，针对xml进行反馈，否则将附件信息入库，并向task1插入任务
			List<String> attach_list = getAttachList(filenames);//附件列表
			//			String[]  attachs = attach_list.toArray(new String[attach_list.size()]);//list转换为String数组
			//			if (isExistFLWS(attachs)) {
			String packetkey = zipFileName.substring(0, zipFileName.length() - 4);//包名
			//文书及证件存储
			for (int i = 0; i < attach_list.size(); i++) {
				this.insertMc20_ws(packetkey, attach_list.get(i), subpath, i + 1);
			}
			//xml存储及任务插入
			for (String xmlname : xmlnames) {
				String code = this.getCodeByFileName(xmlname);
				MC21_task task = getTaskClassDef(code);// 获取缓存中的任务信息
				this.insertTask1(code, task, xmlname, subpath, packetkey);// 库中存相对路径
			}
			//			} else {// 文书缺失，直接反馈
			//				logger.warn("=====ZIP包中法律文书缺失，将直接反馈=====zip包路径：" + path + File.separator + zipFileName);
			//				for (String xmlname : xmlnames) {
			//					//a.在解压后的目录下，生成反馈报文xml物理文件
			//					boolean bzw = this.getErrorResponseMessage(unzipPath, XML_RES_PATH, xmlname);
			//					if (bzw) {
			//						String res_path = unzipPath + File.separator + XML_RES_PATH;//响应文件路径
			//						String xmlFullName = res_path + File.separator + xmlname;//待压缩文件绝对路径
			//						String temZipName = xmlname.replace(".xml", "_tmp.zip");//未加密的zip文件名
			//						String zipFullName = res_path + File.separator + temZipName;//压缩后的文件绝对路径
			//						//b.压缩为zip包［读取xml文件，生成zip文件］
			//						ZipUtils.zip(zipFullName, xmlFullName);
			//						//c.加密［读取zip文件，生成zip文件］
			//						String zipfilename = this.Zip2Encode(zipFullName, xmlname, res_path);
			//						//d.sftp上传
			//						String remoteDir = ServerEnvironment.getStringValue(WhpsbKeys.SFTP_SEND_DIR);
			//						this.getSftpUtils().putWith(res_path, zipfilename, remoteDir, zipfilename);
			//					}
			//				}
			//				logger.info("=====ZIP包已经反馈====");
			//			}
			logger.info("=====ZIP包处理成功！====");
		} catch (Exception e) {
			logger.error("=====ZIP包处理失败=====zip包路径：" + path + File.separator + zipFileName);
			flag = false;
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * zip文件加密
	 * 
	 * @param zipFullName
	 * @param xmlname
	 * @param res_path
	 * @throws Exception
	 * @return
	 */
	public String Zip2Encode(String zipFullName, String xmlname, String res_path) throws Exception {
		File file = new File(zipFullName);
		byte[] key = ServerEnvironment.getStringValue(WhpsbKeys.WHPSB_ZIP_SECRET_KEY).getBytes("UTF-8");//密钥
		byte[] encryptData = DESCoder.encrypt(this.getFile2Bytes(file), key);//zip包加密后的byte数组
		byte[] hexData = HexCoder.encodeToString(encryptData, true).getBytes(StandardCharsets.UTF_8);
		String md5_data = MD5Coder.encodeHex(hexData, true);//获取文件MD5消息摘要
		String x = "_1_" + md5_data.substring(0, 5) + ".zip";
		String zipfilename = xmlname.replace(".xml", x);//加密后的zip文件名
		CommonUtils.writeBinaryFile(hexData, res_path, zipfilename);//输出物理文件
		
		return zipfilename;//加密后的zip文件名
	}
	
	/**
	 * 获取文件的md5消息摘要
	 * 
	 * @param xmlFullName 文件绝对路径
	 * @return
	 * @throws Exception
	 */
	public String getFile2MD5(String xmlFullName) throws Exception {
		
		File file = new File(xmlFullName);
		byte[] data = this.getFile2Bytes(file);
		byte[] s = MD5Coder.encode(data);
		
		return new String(HexCoder.encode(s, true));
	}
	
	/**
	 * 将文件转换为byte数组
	 * 
	 * @param file
	 * @return
	 */
	public byte[] getFile2Bytes(File file) {
		FileInputStream zis = null;
		ByteArrayOutputStream baos = null;
		byte[] data = null;
		try {
			zis = new FileInputStream(file);
			baos = new ByteArrayOutputStream();// 获取zip 输入流的内容，每次按1M字节读取（每次读取字节大小可按需调整）
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = zis.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			data = baos.toByteArray();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.close();
				}
				if (zis != null) {
					zis.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return data;
	}
	
	/**
	 * 生成异常反馈报文
	 * 
	 * @param path 文件路径
	 * @param filename 文件名称
	 * @return
	 */
	public boolean getErrorResponseMessage(String path, String subpath, String filename) {
		boolean flag = true;
		//1.解析xml文件，获取查询对象
		Whpsb_Response xml_req = null;
		//2.xml反馈报文生成路径，不存在就创建
		String xml_path = path + File.separator + subpath;
		File xml_res_dir = new File(xml_path);
		if (!xml_res_dir.exists()) {
			xml_res_dir.mkdir();
		}
		//3.生成xml物理响应文件
		try {
			if (filename.startsWith(ZHXX_GR_PRIFX)) {
				xml_req = (Whpsb_Response) CommonUtils.unmarshallUTF8Document(Whpsb_Response.class, "binding_whpsb_grzhxxcx", path + File.separator + filename);
				//xml首记录
				Whpsb_Header head = xml_req.getHeader();
				head.setCzsj(Utility.currDate8());
				//xml明细记录
				Whpsb_RequestZhxx zhxx = new Whpsb_RequestZhxx();
				List<Whpsb_SadxBody> sadxList = xml_req.getSadxList();
				List<Whpsb_RequestZhxx_Sadx> list = new ArrayList<Whpsb_RequestZhxx_Sadx>();
				for (Whpsb_SadxBody sadxBody : sadxList) {
					sadxBody.setCljg("1");
					sadxBody.setSbyy("法律文书缺失");
					Whpsb_RequestZhxx_Sadx sadx = new Whpsb_RequestZhxx_Sadx();
					sadx.setSadxBody(sadxBody);
					list.add(sadx);
				}
				zhxx.setHeader(head);
				zhxx.setSadxList(list);
				CommonUtils.marshallUTF8Document(zhxx, "binding_whpsb_grzhxxfk", xml_path, filename);//jibx生成反馈xml
			} else if (filename.startsWith(ZHCYR_GR_PRIFX)) {
				xml_req = (Whpsb_Response) CommonUtils.unmarshallUTF8Document(Whpsb_Response.class, "binding_whpsb_grzhcyrcx", path + File.separator + filename);
				//xml首记录
				Whpsb_Header head = xml_req.getHeader();
				head.setCzsj(Utility.currDate8());
				//xml明细记录
				Whpsb_RequestCkrzl zhcyr = new Whpsb_RequestCkrzl();
				List<Whpsb_SadxBody> sadxList = xml_req.getSadxList();
				List<Whpsb_RequestCkrzl_Sadx> list = new ArrayList<Whpsb_RequestCkrzl_Sadx>();
				for (Whpsb_SadxBody sadxBody : sadxList) {
					sadxBody.setCljg("1");
					sadxBody.setSbyy("法律文书缺失");
					Whpsb_RequestCkrzl_Sadx sadx = new Whpsb_RequestCkrzl_Sadx();
					sadx.setSadxBody(sadxBody);
					list.add(sadx);
				}
				zhcyr.setHeader(head);
				zhcyr.setSadxList(list);
				CommonUtils.marshallUTF8Document(zhcyr, "binding_whpsb_grzhcyrfk", xml_path, filename);
			} else if (filename.startsWith(KHZL_GR_PRIFX)) {
				xml_req = (Whpsb_Response) CommonUtils.unmarshallUTF8Document(Whpsb_Response.class, "binding_whpsb_grkhzlcx", path + File.separator + filename);
				//xml首记录
				Whpsb_Header head = xml_req.getHeader();
				head.setCzsj(Utility.currDate8());
				//xml明细记录
				Whpsb_RequestKhzl khzl = new Whpsb_RequestKhzl();
				List<Whpsb_SadxBody> sadxList = xml_req.getSadxList();
				List<Whpsb_RequestKhzl_Sadx> list = new ArrayList<Whpsb_RequestKhzl_Sadx>();
				for (Whpsb_SadxBody sadxBody : sadxList) {
					sadxBody.setCljg("1");
					sadxBody.setSbyy("法律文书缺失");
					Whpsb_RequestKhzl_Sadx sadx = new Whpsb_RequestKhzl_Sadx();
					sadx.setSadxBody(sadxBody);
					list.add(sadx);
				}
				khzl.setHeader(head);
				khzl.setSadxList(list);
				CommonUtils.marshallUTF8Document(khzl, "binding_whpsb_grkhzlfk", xml_path, filename);
			} else if (filename.startsWith(JYMX_GR_PRIFX)) {
				xml_req = (Whpsb_Response) CommonUtils.unmarshallUTF8Document(Whpsb_Response.class, "binding_whpsb_grjymxcx", path + File.separator + filename);
				//xml首记录
				Whpsb_Header head = xml_req.getHeader();
				head.setCzsj(Utility.currDate8());
				//xml明细记录
				Whpsb_RequestJymx jymx = new Whpsb_RequestJymx();
				List<Whpsb_SadxBody> sadxList = xml_req.getSadxList();
				List<Whpsb_SadxBody> list = new ArrayList<Whpsb_SadxBody>();
				for (Whpsb_SadxBody sadxBody : sadxList) {
					sadxBody.setCljg("1");
					sadxBody.setSbyy("法律文书缺失");
					list.add(sadxBody);
				}
				sadxList.clear();//释放资源
				jymx.setHeader(head);
				jymx.setSadxList(list);
				CommonUtils.marshallUTF8Document(jymx, "binding_whpsb_grjymxfk", xml_path, filename);
			} else if (filename.startsWith(ZHXX_DW_PRIFX)) {
				xml_req = (Whpsb_Response) CommonUtils.unmarshallUTF8Document(Whpsb_Response.class, "binding_whpsb_dwzhxxcx", path + File.separator + filename);
				//xml首记录
				Whpsb_Header head = xml_req.getHeader();
				head.setCzsj(Utility.currDate8());
				//xml明细记录
				Whpsb_RequestZhxx zhxx = new Whpsb_RequestZhxx();
				List<Whpsb_SadxBody> sadxList = xml_req.getSadxList();
				List<Whpsb_RequestZhxx_Sadx> list = new ArrayList<Whpsb_RequestZhxx_Sadx>();
				for (Whpsb_SadxBody sadxBody : sadxList) {
					sadxBody.setCljg("1");
					sadxBody.setSbyy("法律文书缺失");
					Whpsb_RequestZhxx_Sadx sadx = new Whpsb_RequestZhxx_Sadx();
					sadx.setSadxBody(sadxBody);
					list.add(sadx);
				}
				zhxx.setHeader(head);
				zhxx.setSadxList(list);
				CommonUtils.marshallUTF8Document(zhxx, "binding_whpsb_dwzhxxfk", xml_path, filename);
			} else if (filename.startsWith(ZHCYR_DW_PRIFX)) {
				xml_req = (Whpsb_Response) CommonUtils.unmarshallUTF8Document(Whpsb_Response.class, "binding_whpsb_dwzhcyrcx", path + File.separator + filename);
				//xml首记录
				Whpsb_Header head = xml_req.getHeader();
				head.setCzsj(Utility.currDate8());
				//xml明细记录
				Whpsb_RequestCkrzl zhcyr = new Whpsb_RequestCkrzl();
				List<Whpsb_SadxBody> sadxList = xml_req.getSadxList();
				List<Whpsb_RequestCkrzl_Sadx> list = new ArrayList<Whpsb_RequestCkrzl_Sadx>();
				for (Whpsb_SadxBody sadxBody : sadxList) {
					sadxBody.setCljg("1");
					sadxBody.setSbyy("法律文书缺失");
					Whpsb_RequestCkrzl_Sadx sadx = new Whpsb_RequestCkrzl_Sadx();
					sadx.setSadxBody(sadxBody);
					list.add(sadx);
				}
				zhcyr.setHeader(head);
				zhcyr.setSadxList(list);
				CommonUtils.marshallUTF8Document(zhcyr, "binding_whpsb_dwzhcyrfk", xml_path, filename);
			} else if (filename.startsWith(KHZL_DW_PRIFX)) {
				xml_req = (Whpsb_Response) CommonUtils.unmarshallUTF8Document(Whpsb_Response.class, "binding_whpsb_dwkhzlcx", path + File.separator + filename);
				//xml首记录
				Whpsb_Header head = xml_req.getHeader();
				head.setCzsj(Utility.currDate8());
				//xml明细记录
				Whpsb_RequestKhzl khzl = new Whpsb_RequestKhzl();
				List<Whpsb_SadxBody> sadxList = xml_req.getSadxList();
				List<Whpsb_RequestKhzl_Sadx> list = new ArrayList<Whpsb_RequestKhzl_Sadx>();
				for (Whpsb_SadxBody sadxBody : sadxList) {
					sadxBody.setCljg("1");
					sadxBody.setSbyy("法律文书缺失");
					Whpsb_RequestKhzl_Sadx sadx = new Whpsb_RequestKhzl_Sadx();
					sadx.setSadxBody(sadxBody);
					list.add(sadx);
				}
				khzl.setHeader(head);
				khzl.setSadxList(list);
				CommonUtils.marshallUTF8Document(khzl, "binding_whpsb_dwkhzlfk", xml_path, filename);
			} else if (filename.startsWith(JYMX_DW_PRIFX)) {
				xml_req = (Whpsb_Response) CommonUtils.unmarshallUTF8Document(Whpsb_Response.class, "binding_whpsb_dwjymxcx", path + File.separator + filename);
				//xml首记录
				Whpsb_Header head = xml_req.getHeader();
				head.setCzsj(Utility.currDate8());
				//xml明细记录
				Whpsb_RequestJymx jymx = new Whpsb_RequestJymx();
				List<Whpsb_SadxBody> sadxList = xml_req.getSadxList();
				List<Whpsb_SadxBody> list = new ArrayList<Whpsb_SadxBody>();
				for (Whpsb_SadxBody sadxBody : sadxList) {
					sadxBody.setCljg("1");
					sadxBody.setSbyy("法律文书缺失");
					list.add(sadxBody);
				}
				sadxList.clear();//释放资源
				jymx.setHeader(head);
				jymx.setSadxList(list);
				CommonUtils.marshallUTF8Document(jymx, "binding_whpsb_dwjymxfk", xml_path, filename);
			}
		} catch (FileNotFoundException e) {
			logger.error("==xml未找到==xml路径：" + path + File.separator + filename);
			e.printStackTrace();
		} catch (JiBXException e) {
			flag = false;
			logger.error("==JIBX解析异常==xml路径：" + path + File.separator + filename);
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 根据文件名获取code
	 * 
	 * @param filename
	 * @return
	 */
	private String getCodeByFileName(String filename) {
		String code = "";
		if (filename.startsWith(ZHXX_GR_PRIFX)) {
			code = WhpsbConstants.CODE_GR_ZHXX;
		} else if (filename.startsWith(ZHCYR_GR_PRIFX)) {
			code = WhpsbConstants.CODE_GR_ZHCYR;
		} else if (filename.startsWith(KHZL_GR_PRIFX)) {
			code = WhpsbConstants.CODE_GR_KHZL;
		} else if (filename.startsWith(JYMX_GR_PRIFX)) {
			code = WhpsbConstants.CODE_GR_JYMX;
		} else if (filename.startsWith(ZHXX_DW_PRIFX)) {
			code = WhpsbConstants.CODE_DW_ZHXX;
		} else if (filename.startsWith(ZHCYR_DW_PRIFX)) {
			code = WhpsbConstants.CODE_DW_ZHCYR;
		} else if (filename.startsWith(KHZL_DW_PRIFX)) {
			code = WhpsbConstants.CODE_DW_KHZL;
		} else if (filename.startsWith(JYMX_DW_PRIFX)) {
			code = WhpsbConstants.CODE_DW_JYMXL;
		}
		return code;
	}
	
	/**
	 * 获取附件列表
	 * 
	 * @param filenames
	 * @return
	 */
	private List<String> getAttachList(String[] filenames) {
		List<String> list = new ArrayList<String>();
		if (filenames != null && filenames.length > 0) {
			for (String filename : filenames) {
				if (filename.endsWith(".jpg") || filename.endsWith(".pdf")) {
					list.add(filename);
				}
			}
		}
		return list;
	}
	
	private boolean isExistFLWS(String[] filenames) {
		boolean flag = false;
		if (filenames != null && filenames.length > 0) {
			for (String filename : filenames) {
				if (filename.startsWith("bank_flws")) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 解压缩
	 * 
	 * @param zipFileData zip包的字节数组
	 * @param outputDirectory 输出目录
	 * @throws Exception
	 */
	private void unZipByDataByte(byte[] zipFileData, String outputDirectory) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(zipFileData);
		ZipInputStream in = new ZipInputStream(is);
		ZipEntry z;
		while ((z = in.getNextEntry()) != null) {
			System.out.println("unziping " + z.getName());
			if (z.isDirectory()) {
				String name = z.getName();
				name = name.substring(0, name.length() - 1);
				File f = new File(outputDirectory + File.separator + name);
				f.mkdir();
				System.out.println("mkdir " + outputDirectory + File.separator + name);
			} else {
				File f = new File(outputDirectory + File.separator + z.getName());
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				int b;
				while ((b = in.read()) != -1)
					out.write(b);
				out.close();
			}
		}
		
		in.close();
	}
	
	/**
	 * task任务插入
	 * 
	 * @param code 代码
	 * @param task 任务信息
	 * @param fileName 文件名
	 * @param subPath 文件路径[不要root]
	 * @param docno 协作编号
	 */
	private void insertTask1(String code, MC21_task task, String fileName, String subPath, String packetkey) {
		
		String docno = fileName.substring(0, fileName.length() - 4);
		String taskKey = ServerEnvironment.TASK_TYPE_WUHAN + "_" + docno;
		if (isMessageReceived(taskKey)) { // 任务去重
			return;
		}
		//执行任务表
		MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
		taskFact.setTaskKey(taskKey);
		taskFact.setSubTaskID(code);
		taskFact.setBdhm(docno);
		taskFact.setTaskID(task.getTaskID());
		taskFact.setTaskType(task.getTaskType());
		taskFact.setTaskName(task.getTaskName());
		taskFact.setTaskCMD(task.getTaskCMD());
		taskFact.setIsDYNA(task.getIsDYNA());
		taskFact.setDatatime(Utility.currDateTime19());
		taskFact.setFreq("1");
		taskFact.setTaskObj("100000");
		taskFact.setTGroupID("1");
		//任务xml存放表
		MC20_task_msg taskmsg = new MC20_task_msg();
		taskmsg.setTaskkey(taskKey);
		taskmsg.setCode(code);
		taskmsg.setBdhm(docno);
		taskmsg.setPacketkey(packetkey);
		taskmsg.setMsgpath(subPath + File.separator + fileName);
		taskmsg.setMsgname(fileName);
		taskmsg.setCreatedt(Utility.currDateTime19());
		
		whpsb_mapper.insertMC20_Task_Fact1(taskFact);
		whpsb_mapper.insertMC20_Task_Msg(taskmsg);
		
		//去重机制
		cacheReceivedTaskKey(taskKey);
	}
	
	/**
	 * 文书信息插入
	 * 
	 * @param name
	 * @param bdhm
	 */
	private void insertMc20_ws(String packetkey, String wsname, String subpath, int xh) {
		//===说明：武汉需求将所有附件都存放到文书表中，bdhm存包名，文书编号存附件去格式名称==
		MC20_WS ws = new MC20_WS();
		ws.setBdhm(packetkey);
		ws.setWsbh(wsname.substring(0, wsname.length() - 4));//文书编号
		ws.setXh(xh + "");//序号
		ws.setWjmc(wsname);//文件名称
		ws.setWjlx(wsname.substring(wsname.length() - 3));//文件类型
		String wslb = "协办人警官证";
		if (wsname.startsWith("bank_flws")) {
			wslb = "法律文书";
		} else if (wsname.startsWith("bank_cctzs")) {
			wslb = "财产通知书";
		} else if (wsname.startsWith("bank_cbr")) {
			wslb = "承办人警官证";
		}
		ws.setWslb(wslb);//文书类别
		ws.setMd5("");//文书MD5值
		ws.setWspath(subpath + File.separator + wsname);//文书地址
		whpsb_mapper.insertMC20_WS(ws);
	}
	
	/**
	 * 移除非zip格式的文件
	 * 
	 * @param fileNameList 文件列表
	 */
	private ArrayList<String> getZipFileNameList(ArrayList<String> fileNameList) {
		ArrayList<String> list = null;
		if (fileNameList != null && fileNameList.size() > 0) {
			list = Lists.newArrayList();
			for (String fileName : fileNameList) {
				if (fileName.endsWith(".zip")) {
					list.add(fileName);
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取sftp连接
	 * 
	 * @return
	 */
	private SftpUtils getSftpUtils() {// 非线程安全
		if (sftpUtils == null) {
			sftpUtils = new SftpUtils();
			sftpUtils.setHost(ServerEnvironment.getStringValue(WhpsbKeys.SFTP_HOST));
			sftpUtils.setUsername(ServerEnvironment.getStringValue(WhpsbKeys.SFTP_LOGIN_USERNAME));
			sftpUtils.setPassword(ServerEnvironment.getStringValue(WhpsbKeys.SFTP_LOGIN_PASSWORD));
		}
		return sftpUtils;
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_WUHAN;
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(WhpsbKeys.WHPSB_OUTER_POLLING_PERIOD, "30");
	}
	
}
