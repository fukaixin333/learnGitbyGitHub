package com.citic.server.cbrc.service;

import java.io.ByteArrayInputStream;
import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.cbrc.domain.Br42_packet;
import com.citic.server.cbrc.domain.CBRC_ReturnReceipt;
import com.citic.server.crypto.CertificateCoder;
import com.citic.server.crypto.RC2Coder;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.runtime.IllegalDataException;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.FtpUtils;
import com.citic.server.utils.IOUtils;
import com.citic.server.utils.SftpUtils;

/**
 * CBRC
 * 
 * @author Liu Xuanfei
 * @date 2016年7月20日 下午1:58:12
 */
@Service("requestMessageServiceCBRC")
public class RequestMessageServiceCBRC {
	
	private Logger logger = LoggerFactory.getLogger(RequestMessageServiceCBRC.class);
	
	// 数字证书
	private final String certificatePath = ServerEnvironment.getStringValue(CBRCKeys.CERTIFICATE_PATH_08);
	private final String keyStorePath = ServerEnvironment.getStringValue(CBRCKeys.KEYSTORE_PATH_08);
	private final String keyStoreAlias = ServerEnvironment.getStringValue(CBRCKeys.KEYSTORE_ALIAS_08);
	private final String keyStoreType = ServerEnvironment.getStringValue(CBRCKeys.KEYSTORE_TYPE_08);
	private final String keyStorePassword = ServerEnvironment.getStringValue(CBRCKeys.KEYSTORE_PASSWORD_08);
	
	boolean isSftpMode = "SFTP".equals(ServerEnvironment.getStringValue(CBRCKeys.REMOTE_ACCESS_MODE)); // 是否使用SFTP通讯方式
	
	boolean isMultiCorpType = ServerEnvironment.getBooleanValue(Keys.MULTI_CORPORATION_TYPE); // 是否多法人模式
	
	@Autowired
	protected PollingTaskMapper service;
	
	/**
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public CBRC_ReturnReceipt sendZipPackage(Br42_packet packet) throws Exception {
		String taskType = packet.getTasktype();
		String fileRootPath = ServerEnvironment.getFileRootPath();
		String zipFileName = packet.getFilename();
		String zipPath = packet.getFilepath();
		String zipFilePath = null;
		if (zipPath.startsWith("/") || zipPath.startsWith("\\")) {
			zipFilePath = fileRootPath + zipPath;
		} else {
			zipFilePath = fileRootPath + File.separator + zipPath;
		}
		
		// 根据不同的TaskType，调用不同的通讯地址和通讯方式
		if (ServerEnvironment.TASK_TYPE_GONGAN.equals(taskType)) {
			return sendZipPackage3(zipFilePath, zipFileName);
		} else if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType)) {
			return sendZipPackage4(zipFilePath, zipFileName);
		} else if (ServerEnvironment.TASK_TYPE_GAOJIAN.equals(taskType)) {
			return sendZipPackage5(zipFilePath, zipFileName);
		} else if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(taskType)) {
			return sendZipPackage8(zipFilePath, zipFileName);
		} else if (ServerEnvironment.TASK_TYPE_NMGZZQ.equals(taskType)) {
			return sendZipPackage15(zipFilePath, zipFileName);
		} else if (ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) {
			return sendZipPackage19(zipFilePath, zipFileName);
		} else {
			throw new IllegalDataException("应用程序暂不支持此类任务的数据反馈 - [TASKTYPE = " + taskType + "]");
		}
	}
	
	private CBRC_ReturnReceipt sendZipPackage8(String zipFilePath, String zipFileName) throws Exception {
		CBRC_ReturnReceipt receipt = null;
		
		logger.info("正在向深圳公安局反馈请求结果数据...");
		// 读取待发送压缩数据包
		logger.info("正在读取请求结果数据包文件... - [{}]", zipFilePath);
		byte[] data = CommonUtils.readBinaryFile(zipFilePath);
		
		// RC2算法的编码密钥
		logger.info("正在初始化RC2加密算法编码密钥...");
		byte[] rc2key = RC2Coder.initKey();
		// Base64编码'RC2算法的编码密钥'
		logger.info("正在Base64编码'RC2加密算法的密码密钥'...");
		byte[] encodeKey = Utility.encodeMIMEBase64(rc2key).getBytes();
		// 对方公钥加密'Base64编码的密钥'（反馈数据之一）
		logger.info("正在使用监管数字证书/公钥加密'Base64编码的密码'（反馈数据之一）...");
		byte[] key = CertificateCoder.encryptByPublicKey(encodeKey, certificatePath);
		// 使用RC2编码密钥加密反馈数据（反馈数据之一）
		logger.info("正在使用RC2编码密钥加密反馈数据包（反馈数据之一）...");
		byte[] zipdata = RC2Coder.encrypt(data, rc2key);
		// 使用私钥库数字签名（反馈数据之一）
		logger.info("正在使用私钥库数字签名加密数据包（反馈数据之一）...");
		byte[] signature = CertificateCoder.sign(zipdata, keyStorePath, keyStoreAlias, keyStoreType, keyStorePassword, CertificateCoder.SIGNATURE_ALGORITHM_1);
		
		// 打包加密签名后的需要的反馈数据
		logger.info("正在将需要反馈的文件（zipdata、signature、key）打成压缩包...");
		String secretFilePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_TEMP, CBRCKeys.FILE_DIRECTORY_08);
		FileUtils.mkDirs(secretFilePath); // 创建目录及其父目录
		secretFilePath = secretFilePath + File.separator + zipFileName;
		FileUtils.deleteFile(secretFilePath); // 如果文件存在，先删除之
		
		ZipFile zipFile = new ZipFile(secretFilePath); // 初始化ZipFile对象
		ZipParameters parameters = new ZipParameters(); // 初始化压缩属性
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
		parameters.setSourceExternalStream(true); // 指定直接从流中获取数据
		// 以流的方式直接更新并添加文件到压缩文件
		parameters.setFileNameInZip("zipdata");
		zipFile.addStream(new ByteArrayInputStream(zipdata, 0, zipdata.length), parameters);
		parameters.setFileNameInZip("signature");
		zipFile.addStream(new ByteArrayInputStream(signature, 0, signature.length), parameters);
		parameters.setFileNameInZip("key");
		zipFile.addStream(new ByteArrayInputStream(key, 0, key.length), parameters);
		
		// 使用 HttpClient 发送反馈数据包
		logger.info("正在通过HTTP协议向深圳公安局发送最终数据包...");
		File sendZipFile = new File(secretFilePath);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(ServerEnvironment.getStringValue(CBRCKeys.REMOTE_ACCESS_URL_08));
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addBinaryBody(sendZipFile.getName(), sendZipFile);
		HttpEntity requestEntity = builder.build();
		httppost.setEntity(requestEntity);
		CloseableHttpResponse response = httpclient.execute(httppost);
		
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			logger.info("正在解析深圳公安局回执报文（InputStream方式）...");
			HttpEntity entity = response.getEntity();
			try {
				receipt = (CBRC_ReturnReceipt) CommonUtils.unmarshallInputStream(CBRC_ReturnReceipt.class, "cbrc_returnreceipt_resp8", entity.getContent(), "UTF-8");
				if (CBRCConstants.REC_CODE_OK.equals(receipt.getHzdm())) {
				} else {
					logger.warn("回执说明（代号：{}） - [{}]", receipt.getHzdm(), receipt.getHzsm());
				}
			} catch (Exception e) {
				// 再尝试一次
				String receiptContext = new String(IOUtils.toByteArray(entity.getContent()), StandardCharsets.UTF_8);
				logger.warn("使用InputStream方式解析回执报文失败，尝试使用Context方式解析... - [{}]", receiptContext, e);
				try {
					receipt = (CBRC_ReturnReceipt) CommonUtils.unmarshallContext(CBRC_ReturnReceipt.class, "cbrc_returnreceipt_resp8", receiptContext);
				} catch (Exception ex) {
					logger.error("通过JiBX解析深圳公安局回执报文失败 - [\r\n{}]", receiptContext, ex);
				}
			} finally {
				EntityUtils.consume(entity);
			}
		} else {
			receipt = new CBRC_ReturnReceipt();
			receipt.setHzdm(CBRCConstants.REC_CODE_99999); // 回执代码
			receipt.setJssj(Utility.currDateTime19()); // 接收时间
			receipt.setHzsm("HTTP错误 - " + statusCode); // 回执说明
		}
		
		return receipt;
	}
	
	private CBRC_ReturnReceipt sendZipPackage3(String zipFilePath, String zipFileName) throws Exception {
		String remoteServerHost = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_HOST);
		String username = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_USERNAME);
		String password = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_PASSWORD);
		String remoteUploadPath = ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_03, "公安部经侦/Upload");
		
		String realRemoteUploadPath;
		if (isMultiCorpType) { // 是否多法人
			realRemoteUploadPath = zipFileName.substring(6, 23) + File.separator + remoteUploadPath;
		} else {
			realRemoteUploadPath = remoteUploadPath;
		}
		
		return sendZipPackageCBRC(remoteServerHost, username, password, realRemoteUploadPath, zipFilePath, zipFileName);
	}
	
	private CBRC_ReturnReceipt sendZipPackage4(String zipFilePath, String zipFileName) throws Exception {
		String remoteServerHost = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_HOST);
		String username = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_USERNAME);
		String password = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_PASSWORD);
		String remoteUploadPath = ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_04, "国家安全部/Upload");
		
		String realRemoteUploadPath;
		if (isMultiCorpType) { // 是否多法人
			realRemoteUploadPath = zipFileName.substring(8, 25) + File.separator + remoteUploadPath;
		} else {
			realRemoteUploadPath = remoteUploadPath;
		}
		
		return sendZipPackageCBRC(remoteServerHost, username, password, realRemoteUploadPath, zipFilePath, zipFileName);
	}
	
	private CBRC_ReturnReceipt sendZipPackage5(String zipFilePath, String zipFileName) throws Exception {
		String remoteServerHost = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_HOST);
		String username = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_USERNAME);
		String password = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_PASSWORD);
		String remoteUploadPath = ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_05, "最高人民检查院/Upload");
		
		String realRemoteUploadPath;
		if (isMultiCorpType) { // 是否多法人
			realRemoteUploadPath = zipFileName.substring(8, 25) + File.separator + remoteUploadPath;
		} else {
			realRemoteUploadPath = remoteUploadPath;
		}
		
		return sendZipPackageCBRC(remoteServerHost, username, password, realRemoteUploadPath, zipFilePath, zipFileName);
	}
	
	private CBRC_ReturnReceipt sendZipPackage15(String zipFilePath, String zipFileName) throws Exception {
		String remoteServerHost = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_HOST_15);
		String username = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_USERNAME_15);
		String password = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_PASSWORD_15);
		String remoteUploadPath = ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_15, "Upload");
		
		String realRemoteUploadPath;
		if (isMultiCorpType) { // 是否多法人
			realRemoteUploadPath = zipFileName.substring(6, 23) + File.separator + remoteUploadPath;
		} else {
			realRemoteUploadPath = remoteUploadPath;
		}
		
		return sendZipPackageCBRC(remoteServerHost, username, password, realRemoteUploadPath, zipFilePath, zipFileName);
	}
	
	private CBRC_ReturnReceipt sendZipPackage19(String zipFilePath, String zipFileName) throws Exception {
		String remoteServerHost = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_HOST);
		String username = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_USERNAME);
		String password = ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_PASSWORD);
		String remoteUploadPath = ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_19, "云南省国家安全厅/Upload");
		
		String realRemoteUploadPath;
		if (isMultiCorpType) { // 是否多法人
			realRemoteUploadPath = zipFileName.substring(8, 25) + File.separator + remoteUploadPath;
		} else {
			realRemoteUploadPath = remoteUploadPath;
		}
		
		return sendZipPackageCBRC(remoteServerHost, username, password, realRemoteUploadPath, zipFilePath, zipFileName);
	}
	
	private CBRC_ReturnReceipt sendZipPackageCBRC(String host, String user, String pwd, String remoteUploadPath, String localFilePath, String localFileName) throws Exception {
		logger.info("正在向监管反馈请求结果数据 - [{}]...", localFileName);
		CBRC_ReturnReceipt receipt = null;
		
		try {
			if (isSftpMode) {
				SftpUtils sftpUtils = new SftpUtils();
				sftpUtils.setHost(host);
				sftpUtils.setUsername(user);
				sftpUtils.setPassword(pwd);
				
				String realFilePath = localFilePath;
				int point = localFilePath.replaceAll("\\\\", "/").lastIndexOf("/");
				if (point > 0) {
					realFilePath = realFilePath.substring(0, point);
				}
				sftpUtils.put(realFilePath, localFileName, remoteUploadPath, localFileName);
			} else {
				FtpUtils ftpUtils = new FtpUtils();
				ftpUtils.setServer(host);
				ftpUtils.setUser(user);
				ftpUtils.setPassword(pwd);
				ftpUtils.setRemotepath(remoteUploadPath);
				ftpUtils.uploadFile(new File(localFilePath));
			}
			receipt = new CBRC_ReturnReceipt();
			receipt.setHzdm(CBRCConstants.REC_CODE_OK); // 回执代码
			receipt.setJssj(Utility.currDateTime19()); // 接收时间
			receipt.setHzsm("上传成功"); // 回执说明
		} catch (Exception e) {
			receipt = new CBRC_ReturnReceipt();
			receipt.setHzdm(CBRCConstants.REC_CODE_99999); // 回执代码
			receipt.setJssj(Utility.currDateTime19()); // 接收时间
			receipt.setHzsm("上传失败 - " + e.getMessage()); // 回执说明
		}
		
		return receipt;
	}
}
