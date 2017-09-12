package com.citic.server;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/08/07 20:11:41$
 */
@Component
@Getter
public class ApplicationProperties {
	
	/** 外联轮询服务 */
	public static final String SERVER_NAME_POLLING_OUTER = "OuterPollingServer";
	/** 内联轮询服务 */
	public static final String SERVER_NAME_POLLING_INNER = "InnerPollingServer";
	/** 外联监听服务 */
	public static final String SERVER_NAME_LISTENER_OUTER = "OuterListenServer";
	/** 内联监听服务 */
	public static final String SERVER_NAME_LISTENER_INNER = "InnerListenServer";
	
	/** 任务扫描时间间隔（单位：秒） */
	@Value("${citic.server.scan.timespace:60}")
	private int serverScanTimespace;
	
	/** 缓存名 */
	@Value("${citic.server.cache.name:serverCache}")
	private String serverCacheName;
	
	@Value("${citic.server.main.name:MainServer}")
	private String mainServerName;
	
	@Value("${citic.server.task.name:TaskServer}")
	private String taskServerName;
	
	@Value("${citic.server.lab.name:LabServer}")
	private String labServerName;
	
	@Value("${citic.server.rt.name:RtServer}")
	private String rtServerName;
	
	/** 人民银行电信诈骗上报服务名称 */
	@Value("${citic.server.trep.name:TrepServer}")
	private String trepServerName;
	
	/** 有权机关网络查控平台服务名称 */
	@Value("${citic.server.netcsc.name:NetcscServer}")
	private String netServerName;
	
	@Value("${citic.server.scan.ds.datatime.length:1}")
	private int serverScanDsDatatimeLength;
	
	@Value("${citic.datafile.backup.days:90}")
	private int datafileBackupDays;
	
	@Value("${citic.taskid.ds.propery:TK_DTS101}")
	private String taskIdDsPropery;
	
	@Value("${citic.taskid.getdata.tolocal:TK_ETL101}")
	private String taskIdGetDataTolocal;
	
	@Value("${citic.jdbctemplate.ods:jdbcTemplate}")
	private String jdbcTemplate_ods;
	
	@Value("${citic.jdbctemplate.manager:jdbcTemplate}")
	private String jdbcTemplate_manager;
	
	@Value("${citic.jdbctemplate.business:jdbcTemplate}")
	private String jdbcTemplate_business;
	
	@Value("${citic.jdbctemplate.history:jdbcTemplate}")
	private String jdbcTemplate_history;
	
	@Value("${citic.save.cal.sqllog:1}")
	private int saveCalSqlLog;
	
	@Value("${citic.save.cal.bigsql.split:2000}")
	private int saveCalBigSqlSplit;
	
	@Value("${citic.global.temp.table.created:db}")
	private String globalTempTable_created;
	
	@Value("${citic.global.temp.table.prestr:session}")
	private String globalTempTable_prestr;
	
	@Value("${citic.lab.calmodel.maxdate.length:30}")
	private int labCalModelMaxDateLength;
	
	@Value("${citic.back.dataload.flag.username:}")
	private String backDataLoadFlag_username;
	
	@Value("${citic.back.dataload.flag.password:}")
	private String backDataLoadFlag_password;
	
	@Value("${citic.back.dataload.flag.dbname:}")
	private String backDataLoadFlag_dbname;
	
	@Value("${citic.back.dataload.database.username:}")
	private String backDataLoadDatabase_username;
	
	@Value("${citic.back.dataload.database.password:}")
	private String backDataLoadDatabase_password;
	
	@Value("${citic.back.dataload.database.dbname:}")
	private String backDataLoadDatabase_dbname;
}
