package com.citic.server.runtime;

/**
 * 配置文件service.xml中，广发银行特有键值。
 * 
 * @author yinxiong
 * @date 2016年6月14日
 */
public interface CgbKeys extends Keys {
	
	/** 文服－源系统ip */
	public static final String WF_SEND_IP = "wf.send.ip";
	/** 文服－端口号 */
	public static final String WF_SEND_PORT = "wf.send.port";
	/** 文服－超时时间 单位秒 */
	public static final String WF_SEND_TIMEOUT = "wf.send.timeout";
	/** 目标地址目录 */
	public static final String WF_SEND_ADDRESS = "wf.send.address";
	/** 信用卡的卡bin（用于辨识信用卡） */
	public static final String MD_CREDIT_BIN = "md.credit.bin";
	/** http远程链接URL */
	public static final String MD_REMOTE_ACCESS_URL = "md.remote.access.url";
	/** 新核心禁止推数据送时间段 */
	public static final String MD_CORE_FORBIDDEN_SEND_TIMES = "md.core.forbidden.send.times";
	/** 信用卡禁止推数据送时间段 */
	public static final String MD_CREDIT_FORBIDDEN_SEND_TIMES = "md.credit.forbidden.send.times";
	/** 在线交易平台反恐怖名单延迟查询时间 */
	public static final String FK_OTSP_DELAY_TIME = "fk.otsp.delay.time";
	/** 系统后台应用程序所在的服务器备份主目录 */
	public static final String AFP_BACKUP_DIR = "afp.backup.dir";
	/** 文服 －服务器接收文件主目录 */
	public static final String WF_SAVE_DIR = "wf.save.dir";
	/** 核心约定名单文件最大记录数 */
	public static final String TOP_SIZE_BY_CBOE = "top.size.by.cboe";
	
	/** 网关地址 */
	public static final String REMOTE_ACCESS_GATEWAY_URL = "inner.remoteaccess.gateway.url";
}
