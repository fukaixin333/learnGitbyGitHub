package com.citic.server.runtime;

/**
 * 上海市公安局（公安银行间破案追赃协查系统）配置项
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 下午5:20:12
 */
public final class ShpsbKeys {
	
	/** 子目录名 */
	public static final String FILE_DIRECTORY_SHPSB = "directory.shpsb";
	/** 外联轮询服务间隔时间 */
	public static final String OUTER_POLLING_PERIOD = "shpsb.outer.polling.period";
	
	/** DES加解密秘钥 */
	public static final String CRYPTO_KEY = "shpsb.crypto.key";
	
	/** SFTP服务地址 */
	public static final String SFTP_SERVER_HOST = "shpsb.sftp.server.host";
	/** SFTP服务用户名 */
	public static final String SFTP_SERVER_USERNAME = "shpsb.sftp.server.username";
	/** SFTP服务密码 */
	public static final String SFTP_SERVER_PASSWORD = "shpsb.sftp.server.password";
	
	/** 远程下载目录 */
	public static final String REMOTE_DOWNLOAD_PATH = "shpsb.download.path";
	/** 远程上传目录 */
	public static final String REMOTE_UPLOAD_PATH = "shpsb.upload.path";
	/** 远程备份目录 */
	public static final String REMOTE_BACKUP_PATH = "shpsb.backup.path";
}
