package com.citic.server.cbrc;

import com.citic.server.runtime.ServerEnvironment;

/**
 * @author Liu Xuanfei
 * @date 2016年8月23日 下午4:18:09
 */
public class CBRCKeys {
	
	/** 联轮询服务间隔时间 */
	public static final String OUTER_POLLING_PERIOD = "cbrc.outer.polling.period";
	/** 内联动态查询间隔时间 */
	public static final String INNER_POLLING_PERIOD_DYNAMICS = "cbrc.inner.polling.period.dynamics";
	
	/** 服务器访问方式（FTP/SFTP） */
	public static final String REMOTE_ACCESS_MODE = "cbrc.server.access.mode";
	/** FTP服务地址 */
	public static final String FTP_SERVER_HOST = "cbrc.ftp.server.host";
	/** FTP服务用户名 */
	public static final String FTP_SERVER_USERNAME = "cbrc.ftp.server.username";
	/** FTP服务密码 */
	public static final String FTP_SERVER_PASSWORD = "cbrc.ftp.server.password";
	
	public static final String FILE_DIRECTORY_03 = "directory.cbrc.03";
	public static final String FILE_DIRECTORY_04 = "directory.cbrc.04";
	public static final String FILE_DIRECTORY_05 = "directory.cbrc.05";
	
	public static final String REMOTE_DOWNLOAD_PATH_03 = "cbrc.03.download.path";
	public static final String REMOTE_UPLOAD_PATH_03 = "cbrc.03.upload.path";
	
	public static final String REMOTE_DOWNLOAD_PATH_04 = "cbrc.04.download.path";
	public static final String REMOTE_UPLOAD_PATH_04 = "cbrc.04.upload.path";
	
	public static final String REMOTE_DOWNLOAD_PATH_05 = "cbrc.05.download.path";
	public static final String REMOTE_UPLOAD_PATH_05 = "cbrc.05.upload.path";
	
	// ==========================================================================================
	//                     深圳公安局（涉案账户资金网络查控系统）
	// ==========================================================================================
	public static final String FILE_DIRECTORY_08 = "directory.cbrc.08";
	public static final String BANK_CODE_08 = "cbrc.08.bank.code";
	
	public static final String REMOTE_ACCESS_URL_08 = "cbrc.08.remote.access.url";
	public static final String CERTIFICATE_PATH_08 = "cbrc.08.certificate.path";
	public static final String KEYSTORE_PATH_08 = "cbrc.08.keystore.path";
	public static final String KEYSTORE_ALIAS_08 = "cbrc.08.keystore.alias";
	public static final String KEYSTORE_TYPE_08 = "cbrc.08.keystore.type";
	public static final String KEYSTORE_PASSWORD_08 = "cbrc.08.keysotre.password";
	
	// ==========================================================================================
	//                     内蒙古自治区公安厅（涉案账户资金网络查控系统）
	// ==========================================================================================
	
	public static final String FILE_DIRECTORY_15 = "directory.cbrc.15";
	public static final String REMOTE_DOWNLOAD_PATH_15 = "cbrc.15.download.path";
	public static final String REMOTE_UPLOAD_PATH_15 = "cbrc.15.upload.path";
	
	public static final String FTP_SERVER_HOST_15 = "cbrc.15.ftp.server.host";
	public static final String FTP_SERVER_USERNAME_15 = "cbrc.15.ftp.server.username";
	public static final String FTP_SERVER_PASSWORD_15 = "cbrc.15.ftp.server.password";
	
	// ==========================================================================================
	//                     云南省国家安全厅
	// ==========================================================================================
	
	public static final String FILE_DIRECTORY_19 = "directory.cbrc.19"; // 云南省国家安全厅
	public static final String REMOTE_DOWNLOAD_PATH_19 = "cbrc.19.download.path";
	public static final String REMOTE_UPLOAD_PATH_19 = "cbrc.19.upload.path";
	
	public static String getFileDirectoryKey(String taskType) {
		if (taskType == null || taskType.length() == 0) {
			return null;
		}
		
		if (ServerEnvironment.TASK_TYPE_GONGAN.equals(taskType)) {
			return CBRCKeys.FILE_DIRECTORY_03;
		} else if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType)) {
			return CBRCKeys.FILE_DIRECTORY_04;
		} else if (ServerEnvironment.TASK_TYPE_GAOJIAN.equals(taskType)) {
			return CBRCKeys.FILE_DIRECTORY_05;
		} else if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(taskType)) {
			return CBRCKeys.FILE_DIRECTORY_08;
		} else if (ServerEnvironment.TASK_TYPE_NMGZZQ.equals(taskType)) {
			return CBRCKeys.FILE_DIRECTORY_15;
		} else if (ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) {
			return CBRCKeys.FILE_DIRECTORY_19;
		}
		
		return "";
	}
}
