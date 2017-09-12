package com.citic.server.runtime;


/**
 * 广东省检察院
 * 
 * @author liuxuanfei
 * @date 2017年5月24日 上午10:17:11
 */
public class GdjgKeys {
	/** 子目录名 */
	public static final String FILE_DIRECTORY = "gdjg21.directory";
	/** 外联轮询服务间隔时间 */
	public static final String OUTER_POLLING_PERIOD = "gdjg21.outer.polling.period";
	
	/** 远程服务HTTP URL */
	public static final String HTTP_SERVER_URL = "gdjg21.http.server.url";
	/** XML报文加密解密的密钥 */
	public static final String HTTP_SECRET_KEY = "gdjg21.http.secret.key";
	
	/** 查询请求单位的用户名 */
	public static final String LOGIN_USERNAME = "gdjg21.http.login.username";
	/** 查询请求单位的密码 */
	public static final String LOGIN_PASSWORD = "gdjg21.http.login.password";
	
	/** ftp登录地址[IP] */
	public static final String FTP_HOST = "gdjg21.ftp.host";
	/** ftp登录的用户名 */
	public static final String FTP_LOGIN_USERNAME = "gdjg21.ftp.login.username";
	/** ftp登录的密码 */
	public static final String FTP_LOGIN_PASSWORD = "gdjg21.ftp.login.password";
	/** ftp文件发送目录 */
	public static final String FTP_SEND_DIR = "gdjg21.ftp.send.dir";
	/** ftp文件接收目录 */
	public static final String FTP_RECV_DIR = "gdjg21.ftp.recv.dir";
	/** 内联轮询服务间隔时间 */
	public static final String INNER_POLLING_TASK_DYNAMICS_PERIOD_GDJG = "gdjg21.inner.polling.task.dynamics.period";
}
