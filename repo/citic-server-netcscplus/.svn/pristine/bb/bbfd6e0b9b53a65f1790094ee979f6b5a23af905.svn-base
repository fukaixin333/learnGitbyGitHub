package com.citic.server.service.task;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.net.mapper.MM12_eventMapper;
import com.citic.server.net.mapper.MM12_event_logMapper;
import com.citic.server.net.mapper.MM12_event_sqlMapper;
import com.citic.server.service.domain.MM12_entity;
import com.citic.server.service.domain.MM12_entity_cfg;
import com.citic.server.service.domain.MM12_event;
import com.citic.server.service.domain.MM12_event_log;
import com.citic.server.service.domain.MM12_event_sql;

/**
 * 事件计算：
 * 
 * @author hubaiqing
 * @version 1.0
 */

public class TK_AML301 extends BaseTask {
	
	private static final Logger logger = LoggerFactory.getLogger(TK_AML301.class);
	
	private ApplicationProperties applicationProperties = null;
	private JdbcTemplate jdbcTemplate = null;
	private String global_temporary_prestr = "";
	//事实表名：区分实验室与生产数据
	private String BR12_EVENT_FACT = "BR12_EVENT_FACT";
	private String BR12_EVENT_PARTY = "BR12_EVENT_PARTY";
	private String BR12_EVENT_ACCT = "BR12_EVENT_ACCT";
	private String BR12_EVENT_TRANS = "BR12_EVENT_TRANS";
	private String BR12_EVENT_DETAIL = "BR12_EVENT_DETAIL";
	
	public TK_AML301(ApplicationContext ac, MC00_task_fact mC00_task_fact) {
		super(ac, mC00_task_fact);
		applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());
	}
	
	public boolean calTask() throws Exception {
		//计算初始化
		this.cal00_init();

		String prestr = this.getGlobal_temporary_prestr();
		String eventkey = this.getMC00_task_fact().getSubtaskid();
		String data_dt = this.getMC00_task_fact().getDatatime();
		
		if (eventkey == null || eventkey.equals("") || eventkey.equals("0")) {
			//本类别下没有需要计算的事件！
			return true;
		}
		
		String sql_1 = "DELETE FROM " + prestr + "BT12_EVENT_MID" + " WHERE EVENTKEY='" + eventkey + "' AND DATA_DT='" + data_dt + "'";
		
		String sql_2 = "DELETE FROM " + prestr + "BT12_EVENT_FACT_MID" + " WHERE EVENTKEY='" + eventkey + "' AND DATA_DT='" + data_dt + "'";
		
		String sql_4 = "DELETE FROM " + this.BR12_EVENT_FACT + " WHERE EVENTKEY='" + eventkey + "' AND DATA_DT='" + data_dt + "'";
		
		String sql_5 = "DELETE FROM " + this.BR12_EVENT_PARTY + " WHERE EVENTKEY='" + eventkey + "' AND DATA_DT='" + data_dt + "'";
		
		String sql_6 = "DELETE FROM " + this.BR12_EVENT_ACCT + " WHERE EVENTKEY='" + eventkey + "' AND DATA_DT='" + data_dt + "'";
		
		String sql_7 = "DELETE FROM " + this.BR12_EVENT_TRANS + " WHERE EVENTKEY='" + eventkey + "' AND DATA_DT='" + data_dt + "'";
		
		String sql_8 = "DELETE FROM " + this.BR12_EVENT_DETAIL + " WHERE EVENTKEY='" + eventkey + "' AND DATA_DT='" + data_dt + "'";
		
		//起始：清理：考虑重算情况
    /*   this.jdbcTemplate.update(sql_1);
		this.jdbcTemplate.update(sql_2);
		this.jdbcTemplate.update(sql_4);
		this.jdbcTemplate.update(sql_5);
		this.jdbcTemplate.update(sql_6);
		this.jdbcTemplate.update(sql_7);
		this.jdbcTemplate.update(sql_8);*/
		ArrayList  delsqlList = new ArrayList();
		delsqlList.add(sql_1);
		delsqlList.add(sql_2);
		delsqlList.add(sql_4);
		delsqlList.add(sql_5);
		delsqlList.add(sql_6);
		delsqlList.add(sql_7);
		delsqlList.add(sql_8);
		this.syncToDatabase(delsqlList);
		
		MM12_eventMapper mm12_eventMapper = (MM12_eventMapper) this.getAc().getBean("MM12_eventMapper");
		MM12_event mm12_event = mm12_eventMapper.getMM12_eventDisp(eventkey);
		//第一步：
		ArrayList alerttmpList = this.cal01_getAlertmpList();
		
		ArrayList sqlList = new ArrayList();
		//第二步
		ArrayList entitykeyList = this.cal02_splitAlertmpList(alerttmpList, sqlList);
		//		//第三步
		this.cal03_getAlert(sqlList);
		
		//网络特征类不做明细，只是在计算过程中将逻辑计算即可
		//实际是在前述两类事件计算结果上，再次提取。
		if (!mm12_event.getEvent_type().equals("3")) {
			//第四步
			this.cal04_getAlertRelation(sqlList);
			//第五步
			this.cal05_getAlertDetail(entitykeyList, sqlList);
		}
		
		/**
		 * 在一个connection下执行全部的SQL明细
		 */
		this.syncToDatabase(sqlList);
		
		//终点：清理
		//this.jdbcTemplate.update(sql_1);
		//this.jdbcTemplate.update(sql_2);

		return true;
 
	}
	
	/**
	 * 初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean cal00_init() throws Exception {
		

		
		String createMethod = applicationProperties.getGlobalTempTable_created();
		if (createMethod.equalsIgnoreCase("java")) { //db or  java
			// db2,需要提前声明全局临时表；V9.7以后的版本，可以与oracle一样，提前建立；
			// 同时兼容V9.7以前版本的做法
			String sql_1 = "DECLARE GLOBAL TEMPORARY TABLE BT12_EVENT_MID "
							+ "	( "
							+ "	   ALERTKEY             CHAR(60)             not null,"
							+ "	   EVENTKEY             VARCHAR(20)          not null,"
							+ "	   CREATE_DT            CHAR(10)             not null,"
							+ "	   DATA_DT              CHAR(10),"
							+ "	   PARTY_ID             VARCHAR2(64),"
							+ "	   PARTY_CLASS_CD       CHAR(1),"
							+ "	   OBJKEY             VARCHAR2(128)"
							+ "	)"
							+ " ON COMMIT PRESERVE ROWS"
							+ " NOT LOGGED ON ROLLBACK DELETE ROWS";
			
			String sql_2 = "DECLARE GLOBAL TEMPORARY TABLE BT12_EVENT_FACT_MID "
							+ "	("
							+ "	   ALERTKEY             CHAR(18),"
							+ "	   EVENTKEY             VARCHAR(20),"
							+ "	   DATA_DT              CHAR(10),"
							+ "	   ENTITYKEY            VARCHAR(10),"
							+ "	   TRANS_KEY            VARCHAR2(64),"
							+ "	   PARTY_ID             VARCHAR2(32),"
							+ "	   ACCT_NUM             VARCHAR2(32),"
							+ "	   TRANS_AMT            NUMBER(30,2),"
							+ "	   ENTITYPK             VARCHAR2(64),"
							+ "	   OBJKEY             VARCHAR2(128),"
							+ "	   CARDNUMBER             VARCHAR2(128),"
							+ "	)"
							+ " ON COMMIT PRESERVE ROWS"
							+ " NOT LOGGED ON ROLLBACK DELETE ROWS";
			
			this.jdbcTemplate.update(sql_1);
			this.jdbcTemplate.update(sql_2);
			
			this.setGlobal_temporary_prestr(applicationProperties.getGlobalTempTable_prestr());
		}
		
		/** 实验室模型计算时：计算结果表，单独保存 */
		if (this.getMC00_task_fact().getTasksource().equalsIgnoreCase("lab")) {
			
			BR12_EVENT_FACT = "BR12_EVENT_FACT" + "_L";
			BR12_EVENT_PARTY = "BR12_EVENT_PARTY" + "_L";
			BR12_EVENT_ACCT = "BR12_EVENT_ACCT" + "_L";
			BR12_EVENT_TRANS = "BR12_EVENT_TRANS" + "_L";
			BR12_EVENT_DETAIL = "BR12_EVENT_DETAIL" + "_L";
			
		}
		
		return true;
	}
	
	/**
	 * 第一步 : 取得当前事件对应的临时计算结果
	 */
	public ArrayList cal01_getAlertmpList() throws Exception {
		
		/**
		 * 'CPTPKEY-112401-'|| '502-' || T.PARTY_ID AS TEMPKEY,
		 * T.MAIN_PARTY_ID AS MAIN_PARTY_ID, -- 主客户号
		 * T.PARTY_CLASS_CD AS PARTY_CLASS_CD, --当事人类型（对公对私）
		 * '501|' || T.TRANSACTIONKEY || '&' || T.PARTY_ID || '&' || T.ACCT_NUM || '&' ||
		 * T.CNY_AMT||
		 * '502|' || T.PARTY_ID ||
		 * '503|' || T.ACCT_NUM || '&' || T.PARTY_ID
		 * AS HALFRESULT --明细中间结果
		 */
		
		String eventkey = this.getMC00_task_fact().getSubtaskid();// =eventkey
		String datatime = this.getMC00_task_fact().getDatatime();
		
		MM12_event_sqlMapper mm12_event_sqlMapper = (MM12_event_sqlMapper) this.getAc().getBean("MM12_event_sqlMapper");
		ArrayList list = mm12_event_sqlMapper.getMM12_event_sqlList(eventkey);
		
		// 取所有的参数值
		
		// ==============================================
		
		ArrayList eventseqList = new ArrayList();
		HashMap sqlHash = new HashMap();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MM12_event_sql mm12_event_sql = (MM12_event_sql) iter.next();
			String eventseq = mm12_event_sql.getEventseq();
			String sqlseq = mm12_event_sql.getSqlseq();
			String sqlstr = mm12_event_sql.getSqlstr();
			
			StringBuffer sqlBuf = new StringBuffer();
			if (sqlHash.containsKey(eventseq)) {
				sqlBuf = (StringBuffer) sqlHash.get(eventseq);
			} else {
				eventseqList.add(eventseq);
			}
			
			
			sqlBuf.append(sqlstr);
			
			sqlHash.put(eventseq, sqlBuf);
			
		}
		
		//严格顺序执行SQL
		ArrayList alerttmpList = new ArrayList();
		
		Iterator sqlIter = eventseqList.iterator();
		//
		String[] sqlStrs = new String[eventseqList.size()];
		int i = 0;
		while (sqlIter.hasNext()) {
			String eventseq = (String) sqlIter.next();
			
			StringBuffer sqlStr = (StringBuffer) sqlHash.get(eventseq);
			
			// 根据接口，动态替换参数值
			String finallsqlStr = this.getCalSqlService().getExecSql(eventkey, datatime, sqlStr.toString(), null).trim();
			
			// ==============================================
			
			if (finallsqlStr.trim().startsWith("/*")) {//剔除开始的注释
				finallsqlStr = finallsqlStr.substring(finallsqlStr.trim().indexOf("*/") + 3).trim();
			}
			
			//实验室任务将日表替换为实验室是表BH11_TRANS_D_L
			if (this.getMC00_task_fact().getTasksource().equalsIgnoreCase("lab")) {
				finallsqlStr = finallsqlStr.replaceAll("(?i)BH11_TRANS_D", "BH11_TRANS_D_L");
			}
			
			logger.debug("finallsqlStr=" + finallsqlStr);
			
			// ==============================================
			//主SQL，需要取回结果集
			if (finallsqlStr.toLowerCase().startsWith("select")) {
				alerttmpList = (ArrayList) jdbcTemplate.queryForList(finallsqlStr);
			} else {
				jdbcTemplate.update(finallsqlStr);
			}
			//日志
			if (applicationProperties.getSaveCalSqlLog() == 1)
				sqlStrs[i] = finallsqlStr;
			i++;
		}
		
		// 添加事件计算日志==
		// 需要计算
		if (applicationProperties.getSaveCalSqlLog() == 1) {
			
			//this.writeSqllog(eventkey, datatime, sqlStrs);
			
		}
		
		return alerttmpList;
	}
	
	/**
	 * 第二步：拆分临时计算结果
	 */
	public ArrayList cal02_splitAlertmpList(ArrayList alerttmpList, ArrayList sqlList) throws Exception {
		
		ArrayList entitykeyList = new ArrayList();
		HashMap entitykeyHash = new HashMap();
		
		HashMap alertHash = new HashMap();// 判断预警不重复
		HashMap alertDetailHash = new HashMap();// 判断明细不重复
		int iAlert = 0;
		int iDetail = 0;
		
		//== 查询需要重新生成结果主体主键值的 ==
		// 对于计算后需要切片保存的内容，需要动态生成唯一主键：JGZTBM + 8位时间 + 原业务主键编码
		String sql = "SELECT ENTITYKEY FROM MM12_ENTITY WHERE MAKENEW_PK='1'";
		List flagList = jdbcTemplate.queryForList(sql);
		HashMap flagHash = new HashMap();
		if (flagList != null) {
			for (Object obj : flagList) {
				Map map = (Map) obj;
				flagHash.put((String) map.get("ENTITYKEY"), "");
			}
		}
		
		//==拆分==
		try {
			//
			DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
			DateTime dt = new DateTime();
			String currdate = fmt.print(dt);
			String data_dt = this.getMC00_task_fact().getDatatime();
			int iSeq = 1;
			for (Object obj : alerttmpList) {
				try {
					
					Map hash = (Map) obj;
					
					String eventkey = this.getMC00_task_fact().getSubtaskid();
					String datadt = this.getMC00_task_fact().getDatatime();
					String tempkey = (String) hash.get("TEMPKEY");
					String halfresult = (String) hash.get("HALFRESULT");
					String main_party_id = (String) hash.get("MAIN_PARTY_ID");
					String party_class_cd = (String) hash.get("PARTY_CLASS_CD");
					int index = 0;
					;
					if (tempkey.indexOf("-") > 0)
						index = tempkey.indexOf("-");
					if (tempkey.indexOf("_") > 0)
						index = tempkey.indexOf("-");
					String objkey = tempkey.substring(index + 1, tempkey.length());
					String alertkey = "";
					if (!alertHash.containsKey(tempkey)) {
						String sSeq = "" + iSeq;
						int len = sSeq.length();
						for (int i = 0; i < (8 - len); i++) {
							sSeq = "0" + sSeq;
						}
						iSeq++;
						alertkey = eventkey + datadt.replace("-", "") + sSeq;
						
						alertHash.put(tempkey, alertkey);
						iAlert++;
					}
					
					/**
					 * String[] tempkeys = tempkey.split("-");// CPTPKEY-规则编码-触发主体类别-触发主体主键
					 * // 1-处理预警 一组预警对应一个系统预警
					 * if (!alertHash.containsKey(tempkey)) {
					 * //
					 * ==产生预警编号===================================================================
					 * iSeq = this.getAlertSeqNxtval(datadt, iSeq);
					 * // 长度为8位,不足前补0
					 * String sSeq = "" + iSeq;
					 * int len = sSeq.length();
					 * for (int i = 0; i < (8 - len); i++) {
					 * sSeq = "0" + sSeq;
					 * }
					 * // 共18位：YJ + 8位日期 + 8位流水
					 * alertkey = "YJ"+ datadt.replace("-","") + sSeq;
					 * //
					 * if (alertkey.length() != 18) {
					 * throw new Exception("预警流水号有错误＝" + alertkey);
					 * }
					 * // 一个规则，一个触发主体类别，一个触发主体序号（系统ETL过程中产生）只预警一次
					 * alertHash.put(tempkey, alertkey);
					 * iAlert++;
					 * }
					 **/
					
					//2-处理明细=============
					// 取出真实的主键
					alertkey = (String) alertHash.get(tempkey);
					
					// 拆分，形成结果主体SQL
					String[] halfresults = {};
					if (!"".equals(halfresult)) {
						
						if (halfresult.startsWith("|")) {
							halfresult = halfresult.substring(1);
						}
						
						halfresults = halfresult.split("\\|");
					}
					
					for (int i = 0; i < halfresults.length; i = i + 2) {
						// entitykey
						String entitykey = halfresults[i]; // 结果主体类别编码
						
						if (entitykey == null || entitykey.length() != 3) {
							entitykey = "000"; // 默认值
							logger.error("事件：" + eventkey + "中间结果编辑有误,系统按默认值处理！");
						}
						entitykeyHash.put(entitykey, "");
						
						String entitypk = halfresults[i + 1];// 结果主体主键（业务主键）
						
						//重新生成结果主体主键（切片保存）
						if (flagHash.containsKey(entitykey)) {// 本关注主体需要重新生成结果主体主键，规则见前面叙述
							entitypk = entitykey + data_dt.replace("-", "") + entitypk;
						}
						// 去重：判断本次计算中中间明细是否有重复信息，直接过滤掉
						String key = alertkey + "-" + entitykey + "-" + entitypk;
						if (alertDetailHash.containsKey(key))
							continue;
						alertDetailHash.put(key, "");
						
						String[] entitypks = entitypk.split("&");
						String trans_key = "";
						String party_id = "";
						String acct_num = "";
						String trans_amt = "0";
						String cardnumber = "";
						if (entitykey.equals("501")) {//交易
							trans_key = entitypks[0];
							party_id = entitypks[1];
							acct_num = entitypks[2];
							trans_amt = entitypks[3];
							if (entitypks.length > 4)
								cardnumber = entitypks[4];
							entitypk = "";
						} else if (entitykey.equals("502")) {// 客户
							party_id = entitypks[0];
							entitypk = "";
						} else if (entitykey.equals("503")) {// 账户
							party_id = entitypks[1];
							acct_num = entitypks[0];
							if (entitypks.length > 2)
								cardnumber = entitypks[2];
							entitypk = "";
						} else {
							//只有其他明细类型，entitypk才有用。
							entitypk = entitypks[0];
							party_id = entitypks[1];
						}
						
						// ================================================
						String factSql = "INSERT INTO "
											+ this.getGlobal_temporary_prestr()
											+ "BT12_EVENT_FACT_MID "
											+ "(ALERTKEY,EVENTKEY,DATA_DT,ENTITYKEY,TRANS_KEY,PARTY_ID,ACCT_NUM,TRANS_AMT,ENTITYPK,OBJKEY,CARDNUMBER)"
											+ " VALUES("
											+ " '"
											+ alertkey
											+ "'"
											+ ",'"
											+ eventkey
											+ "'"
											+ ",'"
											+ data_dt
											+ "'"
											+ ",'"
											+ entitykey
											+ "'"
											+ ",'"
											+ trans_key
											+ "'"
											+ ",'"
											+ party_id
											+ "'"
											+ ",'"
											+ acct_num
											+ "'"
											+ ","
											+ trans_amt
											+ ""
											+ ",'"
											+ entitypk
											+ "'"
											+ ",'"
											+ objkey
											+ "'"
											+ ",'"
											+ cardnumber
											+ "'"
											+ ")";
						//logger.debug("factSql="+factSql);
						sqlList.add(factSql);
						iDetail++;
					}
					
					//添加预警信息==需要从明细判断主客户，因此预警信息后放入
					String alertSql = "INSERT INTO "
										+ this.getGlobal_temporary_prestr()
										+ "BT12_EVENT_MID("
										+ "ALERTKEY,EVENTKEY,CREATE_DT,DATA_DT,PARTY_ID,PARTY_CLASS_CD,OBJKEY"
										+ ") VALUES ("
										+ "'"
										+ alertkey
										+ "','"
										+ eventkey
										+ "','"
										+ currdate
										+ "','"
										+ data_dt
										+ "','"
										+ main_party_id
										+ "','"
										+ party_class_cd
										+ "','"
										+ objkey
										+ "'"
										+ ")";
					//logger.debug("alertSql="+alertSql);
					// SQL集中执行
					sqlList.add(alertSql);
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("预警处理错误:" + e.getMessage());
					throw e;
					// break;
				}
				
			}
			//			// 预警编号回写数据库
			//			if (iSeq > 0)
			//				this.finishAlertSeq(data_dt, iSeq);
			
			logger.info("在临时表中，产生预警：" + iAlert + "条");
			logger.info("在临时表中，产生明细对照：" + iDetail + "条");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		//取得所有实体类别编码，用于判断，明细是否需要拷贝
		Iterator iter = entitykeyHash.keySet().iterator();
		while (iter.hasNext()) {
			entitykeyList.add(iter.next());
		}
		
		return entitykeyList;
	}
	
	/**
	 * 第三步：取得事件事实表
	 */
	public boolean cal03_getAlert(ArrayList sqlList) throws Exception {
		
		String datatime = this.getMC00_task_fact().getDatatime();
		
		// 数据库实例
		String sql = "INSERT INTO "
						+ this.BR12_EVENT_FACT
						+ "(ALERTKEY,EVENTKEY,INTERFACTKEY,EVENT_TYPE,PARTY_ID,PARTY_CLASS_CD,FLAG,DATA_DT,CURR_CD,CREATE_DT,OBJKEY)"
						+ " SELECT DISTINCT A.ALERTKEY,A.EVENTKEY,B.INTERFACTKEY,B.EVENT_TYPE,A.PARTY_ID,A.PARTY_CLASS_CD,'0',A.DATA_DT,B.CURR_CD,A.CREATE_DT,OBJKEY"
						+ " FROM "
						+ this.getGlobal_temporary_prestr()
						+ "BT12_EVENT_MID A,MM12_EVENT B"
						+ " WHERE A.EVENTKEY=B.EVENTKEY"
						+ " AND A.EVENTKEY='"
						+ this.getMC00_task_fact().getSubtaskid()
						+ "' AND A.DATA_DT='"
						+ this.getMC00_task_fact().getDatatime()
						+ "'";
		logger.debug("cal03_getAlert=" + sql);
		//int iRs = this.jdbcTemplate.update(sql);
		
		sqlList.add(sql);
		
		return true;
	}
	
	/**
	 * 第四步：将中间表中重复记录剔除，得到事件明细对照表
	 */
	public boolean cal04_getAlertRelation(ArrayList sqlList) throws Exception {
		String datatime = this.getMC00_task_fact().getDatatime();
		// 数据库实例
		String sql_501 = "INSERT INTO "
							+ this.BR12_EVENT_TRANS
							+ " ( ALERTKEY,EVENTKEY,TRANS_KEY,PARTY_ID,ACCT_NUM,TRANS_AMT,DATA_DT ,CARDNUMBER) "
							+ " SELECT DISTINCT ALERTKEY,EVENTKEY,TRANS_KEY,PARTY_ID,ACCT_NUM,TRANS_AMT,'"
							+ datatime
							+ "',CARDNUMBER "
							+ " FROM "
							+ this.getGlobal_temporary_prestr()
							+ "BT12_EVENT_FACT_MID "
							+ " WHERE EVENTKEY='"
							+ this.getMC00_task_fact().getSubtaskid()
							+ "' AND DATA_DT='"
							+ this.getMC00_task_fact().getDatatime()
							+ "'"
							+ " AND ENTITYKEY='501'";
		String sql_502 = "INSERT INTO "
							+ this.BR12_EVENT_PARTY
							+ " ( ALERTKEY,EVENTKEY,PARTY_ID,DATA_DT ) "
							+ " SELECT DISTINCT ALERTKEY,EVENTKEY,PARTY_ID,'"
							+ datatime
							+ "'"
							+ " FROM "
							+ this.getGlobal_temporary_prestr()
							+ "BT12_EVENT_FACT_MID "
							+ " WHERE EVENTKEY='"
							+ this.getMC00_task_fact().getSubtaskid()
							+ "' AND DATA_DT='"
							+ this.getMC00_task_fact().getDatatime()
							+ "'"
							+ " AND ENTITYKEY='502'";
		String sql_503 = "INSERT INTO "
							+ this.BR12_EVENT_ACCT
							+ " ( ALERTKEY,EVENTKEY,PARTY_ID,ACCT_NUM,DATA_DT,CARDNUMBER ) "
							+ " SELECT DISTINCT ALERTKEY,EVENTKEY,PARTY_ID,ACCT_NUM,'"
							+ datatime
							+ "',CARDNUMBER"
							+ " FROM "
							+ this.getGlobal_temporary_prestr()
							+ "BT12_EVENT_FACT_MID "
							+ " WHERE EVENTKEY='"
							+ this.getMC00_task_fact().getSubtaskid()
							+ "' AND DATA_DT='"
							+ this.getMC00_task_fact().getDatatime()
							+ "'"
							+ " AND ENTITYKEY='503'";
		
		String sql_50X = "INSERT INTO "
							+ BR12_EVENT_DETAIL
							+ " ( ALERTKEY,EVENTKEY,ENTITYKEY,ENTITYPK,DATA_DT,PARTY_ID ) "
							+ " SELECT DISTINCT ALERTKEY,EVENTKEY,ENTITYKEY,ENTITYPK,'"
							+ datatime
							+ "',PARTY_ID"
							+ " FROM "
							+ this.getGlobal_temporary_prestr()
							+ "BT12_EVENT_FACT_MID "
							+ " WHERE EVENTKEY='"
							+ this.getMC00_task_fact().getSubtaskid()
							+ "' AND DATA_DT='"
							+ this.getMC00_task_fact().getDatatime()
							+ "'"
							+ " AND ENTITYKEY NOT IN('501','502','503')";
		
		logger.debug(sql_501);
		logger.debug(sql_502);
		logger.debug(sql_503);
		logger.debug(sql_50X);
		
		sqlList.add(sql_501);
		sqlList.add(sql_502);
		sqlList.add(sql_503);
		sqlList.add(sql_50X);
		
		return true;
	}
	
	public boolean cal05_getAlertDetail(ArrayList typeList, ArrayList sqlList) throws java.lang.Exception {
		boolean isSucc = true;
		try {
			
			HashMap tableHash = (HashMap) this.getCacheService().getCache("eventDetailTable", HashMap.class);
			HashMap colshash = (HashMap) this.getCacheService().getCache("eventDetailColumn", HashMap.class);
			
			Iterator iter = typeList.iterator();
			
			while (iter.hasNext()) {
				String entitykey = (String) iter.next();
				
				if (!tableHash.containsKey(entitykey)) {//本次计算的事件明细不需要拷贝明细
					continue;
				}
				
				MM12_entity mm12_entity = (MM12_entity) tableHash.get(entitykey);
				String fmTable = mm12_entity.getFact_s_table();
				String fmPK = mm12_entity.getFact_s_pk();
				String toTable = mm12_entity.getFact_table();
				String toPK = mm12_entity.getFact_pk();
				
				//如果是1，表示目前的结果主体与业务主键是不同的
				//重新组装主键、：主体类型编码+8位日期+业务主键 ＝ 当前结果主体主键
				String makenew_pk = mm12_entity.getMakenew_pk();
				
				int sublength = entitykey.length() + 8;//结果主体类别长度＋8位时间长度
				//
				ArrayList columnList = (ArrayList) colshash.get(entitykey);
				//
				if (columnList == null) {
					logger.error("结果主体，缺少列配置！");
					continue;
				}
				//
				String selectCol = "";
				String insertCol = "";
				Iterator cIter = columnList.iterator();
				while (cIter.hasNext()) {
					MM12_entity_cfg cfg = (MM12_entity_cfg) cIter.next();
					//主键必须来源于预警对照表，其他字段来源于业务模型表（原因预警结果主键与业务主键可能不同）
					//结果主体主键名 ＝ 目标字段名，那么将源字段替换为结果主体主键值 
					
					if (makenew_pk.equals("1")) {//业务原表主键与预警结果主键不同
						//注意：如果是切片复制类型的，哪么T1表中没有EventKey信息,应该从T2.RTETKEY 
						if (toPK.trim().equalsIgnoreCase(cfg.getSourcecol())) {
							selectCol += ",T2.ENTITYKEY"; //+ toPK; 
						} else {
							selectCol += ",T1." + cfg.getSourcecol();
						}
					} else {
						if (fmPK.trim().equalsIgnoreCase(cfg.getSourcecol())) {
							selectCol += ",T2.ENTITYKEY"; //+ fmPK; 
						} else {
							selectCol += ",T1." + cfg.getSourcecol();
						}
					}
					
					insertCol += "," + cfg.getTargetcol();
				}
				//查询内容
				String selectSql = "SELECT DISTINCT "
									+ selectCol.substring(1)
									+ ""
									+ " FROM "
									+ fmTable
									+ " t1,BR12_EVENT_DETAIL T2"
									+ " LEFT JOIN "
									+ toTable
									+ " T3 ON T2.ENTITYKEY=T3."
									+ toPK
									+ " "
									+ " WHERE";
				//表关联条件
				if (makenew_pk.equals("1")) {//业务原表主键与预警结果主键不同
					selectSql += " SUBSTR(T2.ENTITYKEY," + (sublength + 1) + ")=T1." + fmPK;
				} else {
					selectSql += " T2.ENTITYKEY=T1." + fmPK; //预警结果和业务原表关联条件,主键相同
				}
				//限制条件
				selectSql += " AND T2.ENTITYKEY='" + entitykey + "'" + //当前结果主体
								" AND T2.DATA_DT='"
								+ this.getMC00_task_fact().getDatatime()
								+ "'"
								+ //当前日期预警结果
								" AND T3."
								+ toPK
								+ " IS NULL";//限制结果主体表中已经有的记录，不再重复插入
				//通过左关联，不用not in
				//插入部分
				String insertSql = "INSERT INTO " + toTable + " (" + insertCol.substring(1) + ")";
				
				String sql = insertSql + " " + selectSql;
				
				sqlList.add(sql);
				
			}
			
			//
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			throw e;
		}
		return isSucc;
		
	}
	
	private void writeSqllog(String eventkey, String datatime, String[] sqlStrs) {
		try {
			ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
			jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());
			
			JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_manager());
			ArrayList finallSqlList = new ArrayList();
			// 日志删除
			MM12_event_logMapper mm12_event_logMapper = (MM12_event_logMapper) this.getAc().getBean("MM12_event_logMapper");
			mm12_event_logMapper.deleteMM12_event_log(eventkey);
			
			int splitlength = new Integer(applicationProperties.getSaveCalBigSqlSplit());
			
			for (int i = 0; i < sqlStrs.length; i++) {
				String sqlstr = sqlStrs[i];
				
				ArrayList sqlList = new ArrayList();
				sqlList = this.sqlSplit(sqlList, sqlstr, splitlength);
				
				Iterator sqlIter = sqlList.iterator();
				int j = 1;
				while (sqlIter.hasNext()) {
					String sqlcontent = (String) sqlIter.next();
					
					MM12_event_log log = new MM12_event_log();
					log.setEventkey(eventkey);
					log.setDatatime(datatime);
					log.setEventseq("" + (i + 1));
					log.setSqlseq("" + j);
					log.setSqlstr(sqlcontent);
					
					finallSqlList.add(log);
					j++;
				}
			}
			
			this.syncToDatabase(datatime, finallSqlList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void syncToDatabase(String datatime, final ArrayList finallSqlList) throws Exception {
		
		String sql = "insert into mm12_event_log(eventkey,datatime,eventseq,sqlseq,sqlstr) values (?,?,?,?,?)";
		
		ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_manager());
		
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				MM12_event_log log = (MM12_event_log) finallSqlList.get(i);
				ps.setString(1, log.getEventkey());
				ps.setString(2, log.getDatatime());
				ps.setString(3, log.getEventseq());
				ps.setString(4, log.getSqlseq());
				ps.setString(5, log.getSqlstr());
			}
			
			public int getBatchSize() {
				return finallSqlList.size();
			}
		};
		
		int[] resut = jdbcTemplate.batchUpdate(sql, setter);
	}
	
	private ArrayList sqlSplit(ArrayList list, String sqlstr, int splitlength) {
		
		if (sqlstr.length() <= splitlength) {
			list.add(sqlstr);
		} else {
			
			list.add(sqlstr.toString().substring(0, splitlength));
			
			sqlstr = sqlstr.toString().substring(splitlength);
			
			list = this.sqlSplit(list, sqlstr, splitlength);
			
		}
		
		return list;
	}
	
	//	
	//	/**
	//	 * 或取预警主键的流水号部分 本流水号按照数据时间产生，每天一条记录
	//	 * 
	//	 * @param conn
	//	 * @param _statisticdate
	//	 * @param preseq
	//	 * @return
	//	 * @throws Exception
	//	 */
	//	private int getAlertSeqNxtval(String _datatime,
	//			int preseq) throws Exception {
	//		int iSeq = -1;
	//		if (preseq == -1) {
	//			String sql = "SELECT CURRVALUE FROM BR12_SEQUENCE"
	//					+ " WHERE SEQNAME='BR12_EVENT_FACT__ALERTKEY' AND DATATIME='"
	//					+ _datatime + "'";
	//			List list = this.jdbcTemplate.queryForList(sql);
	//
	//			if (list == null || list.size() == 0) {// 初始化sequence记录
	//				String inssql = "INSERT INTO BR12_SEQUENCE(SEQNAME,CURRVALUE,DATATIME) VALUES ('BR12_EVENT_FACT__ALERTKEY',1,'"
	//						+ _datatime + "')";
	//				this.jdbcTemplate.update(inssql);
	//				preseq = 0;
	//			} else {
	//				Map map = (Map) list.get(0);
	//				
	//				//BigDecimal v = (BigDecimal)map.get("CURRVALUE");
	//				
	//				preseq = ( (BigDecimal) map.get("CURRVALUE") ) .intValue();
	//				
	//				//preseq = Integer.parseInt((String) map.get("CURRVALUE"));
	//			}
	//		}
	//		iSeq = preseq + 1;
	//		return iSeq;
	//	}
	//
	//	/**
	//	 * 当日流水号产生后，将最后一个值回写道数据库中
	//	 * 
	//	 * @param conn
	//	 * @param _statisticdate
	//	 * @param preseq
	//	 * @return
	//	 * @throws Exception
	//	 */
	//	private boolean finishAlertSeq(String _datatime,
	//			int preseq) throws Exception {
	//		boolean isSucc = true;
	//
	//		String sql = "UPDATE BR12_SEQUENCE SET CURRVALUE=" + preseq
	//				+ " WHERE SEQNAME='BR12_EVENT_FACT__ALERTKEY' AND DATATIME='"
	//				+ _datatime + "'";
	//		this.jdbcTemplate.update(sql);
	//
	//		return isSucc;
	//	}
	
	public String getGlobal_temporary_prestr() {
		return global_temporary_prestr;
	}
	
	public void setGlobal_temporary_prestr(String global_temporary_prestr) {
		this.global_temporary_prestr = global_temporary_prestr;
	}
}