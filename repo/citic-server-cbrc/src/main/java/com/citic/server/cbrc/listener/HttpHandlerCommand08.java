package com.citic.server.cbrc.listener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.citic.server.SpringContextHolder;
import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.cbrc.domain.Br40_cxqq;
import com.citic.server.cbrc.domain.Br41_kzqq;
import com.citic.server.cbrc.domain.CBRC_BasicInfo;
import com.citic.server.cbrc.domain.CBRC_QueryPerson;
import com.citic.server.cbrc.domain.CBRC_Response;
import com.citic.server.cbrc.domain.response.CBRC_ControlResponse;
import com.citic.server.cbrc.domain.response.CBRC_FreezeResponse;
import com.citic.server.cbrc.domain.response.CBRC_LiInfosResponse;
import com.citic.server.cbrc.domain.response.CBRC_QueryResponse;
import com.citic.server.cbrc.domain.response.CBRC_StopPaymentResponse;
import com.citic.server.cbrc.outer.OuterPollingServiceCBRC;
import com.citic.server.crypto.CertificateCoder;
import com.citic.server.crypto.RC2Coder;
import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.http.HttpConstants;
import com.citic.server.http.api.HttpEntity;
import com.citic.server.http.api.HttpRequestMessage;
import com.citic.server.http.api.HttpStatus;
import com.citic.server.http.api.HttpVersion;
import com.citic.server.http.buf.ByteChunk;
import com.citic.server.http.codec.HttpResponseMessage;
import com.citic.server.http.service.HttpProtocolIoHandlerCommand;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ProcessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.IOUtils;
import com.citic.server.utils.StrUtils;
import com.citic.server.utils.Zip4jUtils;

/**
 * 深圳公安局
 * 
 * @author chen jie
 * @date 2017年5月9日 下午8:14:45
 */
@Component("httpHandlerCommand08")
public class HttpHandlerCommand08 extends HttpProtocolIoHandlerCommand {
	private static final Logger logger = LoggerFactory.getLogger(HttpHandlerCommand08.class);
	
	@Autowired
	private PollingTaskMapper taskMapper;
	
	private static final String SECRET_FILE_SUFFIX = ".secret";
	private static final String TASK_TYPE = ServerEnvironment.TASK_TYPE_SHENZHEN;
	
	// 数字证书
	private final String certificatePath = ServerEnvironment.getStringValue(CBRCKeys.CERTIFICATE_PATH_08);
	private final String keyStorePath = ServerEnvironment.getStringValue(CBRCKeys.KEYSTORE_PATH_08);
	private final String keyStoreAlias = ServerEnvironment.getStringValue(CBRCKeys.KEYSTORE_ALIAS_08);
	private final String keyStoreType = ServerEnvironment.getStringValue(CBRCKeys.KEYSTORE_TYPE_08);
	private final String password = ServerEnvironment.getStringValue(CBRCKeys.KEYSTORE_PASSWORD_08);
	
	// 文件路径
	private String zipFileSavePath;
	private String zipFileSuccessPath;
	private String zipFileFailedPath;
	
	// 任务配置（缓存）
	private final HashMap<String, MC21_task> taskClassDefCache = new HashMap<String, MC21_task>();
	
	// 机构编码
	private String organkey = null;
	
	@Autowired
	@Qualifier("outerPollingServiceCBRC")
	private OuterPollingServiceCBRC service;
	
	@Override
	public boolean initialize() {
		String fileRootPath = ServerEnvironment.getFileRootPath();
		String fileSaveDirectory = ServerEnvironment.getStringValue(CBRCKeys.FILE_DIRECTORY_08);
		
		zipFileSavePath = fileRootPath + File.separator + "download" + File.separator + fileSaveDirectory;
		zipFileSuccessPath = zipFileSavePath + File.separator + "done";
		zipFileFailedPath = zipFileSavePath + File.separator + "failed";
		
		return true;
	}
	
	@Override
	public void executeAction(IoSession session, Object message) {
		HttpResponseMessage response = null;
		HttpRequestMessage request = (HttpRequestMessage) message;
		List<HttpEntity> parts = request.getEntityParts();
		if (parts == null || parts.size() == 0) {
			response = new HttpResponseMessage(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_NO_CONTENT);
		} else {
			for (HttpEntity part : parts) {
				long li1 = System.currentTimeMillis();
				String zipFileName = null; // ZIP文件名称，由processEntityPart返回，HTTP报文头中获取
				String secretFileName = null; // 未解密ZIP文件名称
				String transSerialNumber = "-"; // 请求单标识
				boolean done = true; // 是否处理成功
				try {
					// 存储接收到的二进制请求文件
					logger.info("接收到深圳公安局请求信息，正在处理...");
					zipFileName = processEntityPart(part);
					secretFileName = zipFileName + SECRET_FILE_SUFFIX;
					
					logger.info("成功接收并保存深圳公安局请求文件 - [" + secretFileName + "]");
					// 处理二进制请求文件（验证、解密、解析、入库、清理）
					String bankCode = secretFileName.substring(0, 8).toUpperCase(); // 银行代码
					transSerialNumber = secretFileName.substring(8, 30); // 请求单标识
					if (bankCode.equals(ServerEnvironment.getStringValue(CBRCKeys.BANK_CODE_08))) {
						processRequestFile(transSerialNumber, secretFileName);
					} else {
						throw new ProcessException(CBRCConstants.REC_CODE_99999, "无效银行代码（非本行数据）");
					}
					
					HttpEntity entity = new HttpEntity();
					byte[] content = createReturnReceipt(transSerialNumber, CBRCConstants.REC_CODE_OK, "成功接收查控文件");
					entity.getContent().setBytes(content, 0, content.length);
					
					response = new HttpResponseMessage(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_OK);
					response.setEntity(entity);
				} catch (ProcessException e) {
					logger.error("处理请求文件失败[" + e.getCode() + "]: " + e.getMessage(), e);
					
					done = false;
					HttpEntity entity = new HttpEntity();
					byte[] content = createReturnReceipt(transSerialNumber, e.getCode(), e.getMessage());
					entity.getContent().setBytes(content, 0, content.length);
					
					response = new HttpResponseMessage(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_OK);
					response.setEntity(entity);
				} catch (Exception e) {
					logger.error("应用程序错误: " + e.getMessage(), e);
					
					done = false;
					HttpEntity entity = new HttpEntity();
					byte[] content = createReturnReceipt(transSerialNumber, CBRCConstants.REC_CODE_99999, e.getMessage());
					entity.getContent().setBytes(content, 0, content.length);
					
					response = new HttpResponseMessage(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_OK);
					response.setEntity(entity);
				}
				
				// 物理文件清理
				long li2 = System.currentTimeMillis();
				logger.info("请求数据包处理完成，共耗时[" + (li2 - li1) + "]毫秒，应用程序准备清理物理文件...");
				processFile(done, secretFileName, zipFileName);
				
				break; // Only handle the first entity
			}
		}
		
		// return the response message
		logger.info("正在向深圳公安局响应回执信息...");
		session.write(response);
		
	}
	
	/**
	 * 保存接收到的二进制文件，并加上后缀<em>.secret</em>
	 * 
	 * @param part
	 * @return 源文件名
	 * @throws ProcessException
	 * @throws IOException
	 */
	private String processEntityPart(HttpEntity part) throws ProcessException, IOException {
		String zipFileName = part.getFilename();
		if (zipFileName == null || zipFileName.length() < 30) {
			throw new ProcessException(CBRCConstants.REC_CODE_99999, "HTTP报文头缺少文件名或无效文件名");
		}
		
		String secretFileName = zipFileName + SECRET_FILE_SUFFIX;
		String secretFilePath = zipFileSavePath + File.separator + secretFileName;
		BufferedOutputStream bout = null;
		try {
			File file = new File(zipFileSavePath);
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			
			ByteChunk chunk = part.getContent();
			file = new File(secretFilePath);
			bout = new BufferedOutputStream(new FileOutputStream(file, false));
			bout.write(chunk.bytes(), chunk.position(), chunk.length());
		} finally {
			if (bout != null) {
				try {
					bout.flush();
					bout.close();
				} catch (Exception e) {
				}
			}
		}
		
		return zipFileName;
	}
	
	private void processRequestFile(String transSerialNumber, String secretFile) throws ProcessException, Exception {
		byte[] data = processSecretFile(secretFile);
		
		// 文件名处理
		String zipFileName = secretFile.substring(0, secretFile.lastIndexOf("."));
		String realZipFileName = zipFileName.substring(0, zipFileName.lastIndexOf("."));
		String relativePath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, CBRCKeys.FILE_DIRECTORY_08);
		String extractRelativePath = relativePath + File.separator + realZipFileName;
		String extractAbsolutePath = ServerEnvironment.getFileRootPath() + extractRelativePath;
		
		// 解密后的压缩文件
		String zipFilePath = CommonUtils.writeBinaryFile(data, zipFileSavePath, zipFileName);
		
		// 解压压缩文件
		logger.info("成功校验并解密请求数据包，正在解压压缩文件 - [" + realZipFileName + "]...");
		File extractDirectory = new File(extractAbsolutePath);
		if (!extractDirectory.exists() || !extractDirectory.isDirectory()) {
			extractDirectory.mkdirs();
		}
		Zip4jUtils.extractAllFiles(zipFilePath, extractAbsolutePath, null);
		
		// 请求文件分类（XML报文、附件）
		logger.info("正在处理请求XML报文、法律文书及附件 - [" + realZipFileName + "]...");
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
		
		if (attatchFileList.size() == 0) {
			throw new ProcessException(CBRCConstants.REC_CODE_30003, "查控法律文件缺失");
		}
		
		// 逐一解析XML报文
		boolean validate = true; // 只做一次文书验证
		CBRC_BasicInfo basicInfo = null;
		CBRC_QueryPerson queryPerson = null;
		List<CBRC_LiInfosResponse> liInfoList = null;
		List<MC20_task_msg> taskMsgList = new ArrayList<MC20_task_msg>();
		for (String xmlFileName : xmlFileList) {
			String xmlRelativePath = extractRelativePath + File.separator + xmlFileName;
			String xmlAbsolutePath = extractAbsolutePath + File.separator + xmlFileName;
			String classCode = xmlFileName.substring(0, 4).toUpperCase();
			CBRC_Response response = null;
			if ("SS01".equals(classCode) || "SS02".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_QueryResponse.class, "cbrc_query_resp8", xmlAbsolutePath);
			} else if ("SS05".equals(classCode) || "SS06".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_QueryResponse.class, "cbrc_queryaccount_resp8", xmlAbsolutePath);
			} else if ("SS11".equals(classCode) || "SS12".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_ControlResponse.class, "cbrc_control_resp8", xmlAbsolutePath);
			} else if ("SS17".equals(classCode) || "SS18".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_FreezeResponse.class, "cbrc_freeze_resp8", xmlAbsolutePath);
			} else if ("SS21".equals(classCode) || "SS22".equals(classCode)) {
				response = (CBRC_Response) CommonUtils.unmarshallUTF8Document(CBRC_StopPaymentResponse.class, "cbrc_stoppayment_resp8", xmlAbsolutePath);
			} else {
				throw new ProcessException(CBRCConstants.REC_CODE_30002, "查控报文解析错误，无效分类代码");
			}
			
			// 验证文书信息
			if (validate) {
				basicInfo = response.getBasicInfo();
				queryPerson = response.getQueryPerson();
				liInfoList = response.getLiInfos();
				
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
			
			String realXmlFileName = xmlFileName.substring(0, xmlFileName.lastIndexOf("."));
			String taskKey = TASK_TYPE + "_" + realXmlFileName;
			
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
		
		// 存入数据库
		logger.info("正在将请求数据入库 - [" + realZipFileName + "]...");
		processDatabase(basicInfo, queryPerson, liInfoList, taskMsgList, extractRelativePath);
	}
	
	private byte[] processSecretFile(String secretFile) throws ProcessException, Exception {
		logger.info("正在校验加密请求数据包文件头部信息 - [" + secretFile + "]...");
		ZipFile zipFile = new ZipFile(zipFileSavePath + File.separator + secretFile);
		FileHeader zipDataHeader = zipFile.getFileHeader("zipdata");
		if (zipDataHeader == null) {
			throw new ProcessException(CBRCConstants.REC_CODE_99999, "加密数据包中'zipdata'文件缺失");
		}
		FileHeader signatureHeader = zipFile.getFileHeader("signature");
		if (signatureHeader == null) {
			throw new ProcessException(CBRCConstants.REC_CODE_99999, "加密数据包中'signature'文件缺失");
		}
		FileHeader keyHeader = zipFile.getFileHeader("key");
		if (keyHeader == null) {
			throw new ProcessException(CBRCConstants.REC_CODE_99999, "加密数据包中'key'文件缺失");
		}
		
		byte[] zipdata = toByteArray(zipFile.getInputStream(zipDataHeader));
		byte[] signature = toByteArray(zipFile.getInputStream(signatureHeader));
		byte[] key = toByteArray(zipFile.getInputStream(keyHeader));
		
		// 数字签名验证
		logger.info("正在验证加密请求数据包的数字签名 - [" + secretFile + "]...");
		boolean verify;
		try {
			verify = CertificateCoder.verify(zipdata, signature, certificatePath, CertificateCoder.SIGNATURE_ALGORITHM_1);
		} catch (Exception e) {
			throw new ProcessException(CBRCConstants.REC_CODE_30001, "数字签名错误，程序出现异常", e);
		}
		if (!verify) {
			throw new ProcessException(CBRCConstants.REC_CODE_30001, "数字签名错误，验签没通过");
		}
		
		// 私钥解密获取'Base64编码格式的密钥'
		logger.info("正在使用私钥解密加密请求数据包的编码密钥 - [" + secretFile + "]...");
		byte[] encodedKey;
		try {
			encodedKey = CertificateCoder.decryptByPrivateKey(key, keyStorePath, keyStoreAlias, keyStoreType, password);
		} catch (Exception e) {
			throw new ProcessException(CBRCConstants.REC_CODE_99999, "私钥解密错误，RC2密钥获取失败", e);
		}
		
		// Base64解码获得'RC2算法的编码密钥'
		byte[] rc2key = Utility.decodeMIMEBase64(new String(encodedKey));
		
		// RC2 算法解密压缩文件
		logger.info("正在使用编码密钥解密请求数据包 - [" + secretFile + "]...");
		try {
			return RC2Coder.decrypt(zipdata, rc2key);
		} catch (Exception e) {
			throw new ProcessException(CBRCConstants.REC_CODE_99999, "RC2算法解密错误，压缩包获取失败", e);
		}
	}
	
	private void processDatabase(CBRC_BasicInfo basicInfo, CBRC_QueryPerson queryPerson, List<CBRC_LiInfosResponse> liInfoList, List<MC20_task_msg> taskMsgList, String extractRelativePath)
		throws ProcessException {
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
			mc20_ws.setTasktype(TASK_TYPE);
			
			wsList.add(mc20_ws);
		}
		
		Br40_cxqq br40_cxqq = null;
		Br41_kzqq br41_kzqq = null;
		if (CBRCConstants.ROUTINE.equals(qqcslx)
			|| CBRCConstants.DYNAMIC.equals(qqcslx)
			|| CBRCConstants.DYNAMIC_CONTINUE.equals(qqcslx)
			|| CBRCConstants.DYNAMIC_RELIEVE.equals(qqcslx)) {
			br40_cxqq = new Br40_cxqq();
			copyProperties(br40_cxqq, basicInfo, queryPerson);
			br40_cxqq.setStatus("0");// 状态
			br40_cxqq.setQrydt(Utility.currDate10());
			br40_cxqq.setTasktype(TASK_TYPE);// 监管类别
		} else {
			br41_kzqq = new Br41_kzqq();
			copyProperties(br41_kzqq, basicInfo, queryPerson);
			br41_kzqq.setStatus("0");// 状态
			br41_kzqq.setQrydt(Utility.currDate10());
			br41_kzqq.setTasktype(TASK_TYPE);// 监管类别
		}
		
		// 事务管理
		service.transaction(basicInfo.getQqdbs(), TASK_TYPE, taskFactList, taskMsgList, wsList, br40_cxqq, br41_kzqq);
	}
	
	private void processFile(boolean done, String secretFileName, String zipFileName) {
		String localMovePath = done ? this.zipFileSuccessPath : this.zipFileFailedPath;
		localMovePath = localMovePath + File.separator + Utility.currDate8();
		try {
			String secretFilePath = zipFileSavePath + File.separator + secretFileName;
			String zipFilePath = zipFileSavePath + File.separator + zipFileName;
			FileUtils.moveFile(secretFilePath, localMovePath);
			FileUtils.deleteFile(zipFilePath);
		} catch (Exception e) {
			logger.error("清理物理文件异常，应用程序默认忽略此异常。", e);
		}
	}
	
	public byte[] createReturnReceipt(String qqdbs, String code, String description) {
		StringBuilder receipt = new StringBuilder();
		receipt.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		receipt.append("\r\n");
		receipt.append("<RETURNRECEIPTS>");
		receipt.append("<RETURNRECEIPT");
		receipt.append(" QQDBS=\"" + qqdbs + "\"");
		receipt.append(" SQJGDM=\"440300\"");
		receipt.append(" MBJGDM=\"E007H101440101001\"");
		receipt.append(" JSSJ=\"" + Utility.currDateTime14() + "\"");
		receipt.append(" HZDM=\"" + code + "\"");
		receipt.append(" HZSM=\"" + description + "\"");
		receipt.append(" />");
		receipt.append("</RETURNRECEIPTS>");
		
		return receipt.toString().getBytes(StandardCharsets.UTF_8);
	}
	
	/**
	 * 获取任务配置
	 * 
	 * @param taskid 任务ID
	 * @return 配置信息
	 */
	private MC21_task getTaskClassDef(String taskid) {
		if (taskClassDefCache.isEmpty()) {
			synchronized (taskClassDefCache) {
				if (taskClassDefCache.isEmpty()) {
					List<MC21_task> tasks = taskMapper.queryMC21_Task(TASK_TYPE);
					String code;
					for (MC21_task task : tasks) {
						code = task.getTxCode();
						if (code == null || code.equals("")) {
							continue;
						}
						if (taskClassDefCache.containsKey(code)) {
							throw new RuntimeException("Multiple task defined for code '" + code + "'.");
						}
						taskClassDefCache.put(code, task);
					}
				}
			}
		}
		return taskClassDefCache.get(taskid);
	}
	
	@SuppressWarnings("rawtypes")
	private String getOrganKey() {
		if (this.organkey == null) {
			CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
			HashMap sysParaMap = ((HashMap) cacheService.getCache("sysParaDetail", HashMap.class));
			this.organkey = StrUtils.null2String((String) sysParaMap.get("innerOrgKey"));
		}
		return this.organkey;
	}
	
	public void messageSent(IoSession session, Object message) throws Exception {
		Integer key = (Integer) session.getAttribute(HttpConstants.HTTP_KEY);
		if (key != null && key.intValue() == 0) {
			logger.info("回执信息已发送，断开本次连接。");
		}
	}
	
	// ==========================================================================================
	//                     Help Behavior
	// ==========================================================================================
	
	/**
	 * IO流转换为字节数组
	 * <p>
	 * 相对于{@link IOUtils#toByteArray(java.io.InputStream)}，此方法会关闭流。
	 * 
	 * @param input 待读取的IO输入流
	 * @return 字节数组
	 * @throws IOException
	 */
	private byte[] toByteArray(ZipInputStream input) throws IOException {
		try {
			return IOUtils.toByteArray(input);
		} finally {
			if (input != null) {
				try {
					input.close(true);
				} catch (Exception e) {
				}
			}
		}
	}
	
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
	}
}
