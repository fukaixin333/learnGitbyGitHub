package com.citic.server.service.task;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.citic.server.domain.MC00_task_fact;
import com.citic.server.domain.MC00_task_status;
import com.citic.server.mapper.MC00_task_factMapper;
import com.citic.server.mapper.MC00_task_statusMapper;
import com.citic.server.service.CacheService;
import com.citic.server.service.CalSqlService;
import com.citic.server.service.TaskService;
import com.citic.server.service.base.Base;
import com.citic.server.service.base.NeetReCalException;
import com.citic.server.utils.DbFuncUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;
import com.google.common.collect.Lists;

/**
 * 
 * @author hubq
 * @version 1.0
 */

public abstract class BaseTask extends Base {

	private static final Logger logger = LoggerFactory.getLogger(BaseTask.class);

	protected ThreadLocal<MC00_task_fact> mC00_task_factL = new ThreadLocal<MC00_task_fact>();

	/** 业务数据库链接串 */
	private String businessJdbcTemplate = "jdbcTemplate";

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected CacheService cacheService;

	@Autowired
	private CalSqlService calSqlService;

	private ApplicationContext ac;

	private final int exec_sql_len = 3000;
	
	protected final DbFuncUtils dbFuncUtils = new DbFuncUtils();
	
	/************************/
	public BaseTask() {
	}
	public void setAc(ApplicationContext ac) {
		this.ac = ac;
	}
	public void setMC00_task_fact( MC00_task_fact mC00_task_fact ) throws Exception {
		mC00_task_factL.set(mC00_task_fact);
	}
	/************************/
	
	public BaseTask(ApplicationContext _ac, MC00_task_fact mC00_task_fact) {
		mC00_task_factL.set(mC00_task_fact);
		this.ac = _ac;

		jdbcTemplate = (JdbcTemplate) ac.getBean(this.businessJdbcTemplate);

		cacheService = (CacheService) ac.getBean("cacheService");

		calSqlService = (CalSqlService) ac.getBean("calSqlService");
	}

	public void run() throws Exception {
		boolean isSuccess = false;
		MC00_task_fact mC00_task_fact = this.mC00_task_factL.get();

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		MC00_task_status status = this.mC00_task_factTOStatus();
		DateTime beginDateTime = new DateTime();
		status.setBegintime(fmt.print(beginDateTime));

		boolean rollback = false;
		try {

			// 任务计算主入口
			isSuccess = this.calTask();
			if (isSuccess) {
				status.setCalstatus("1");// 成功
			} else {
				status.setCalstatus("0");// 失败
			}
			
		} catch (NeetReCalException ne) {
			//
			rollback = true;
			status.setCalstatus("0");// 失败
//			this.insertMc00_task_errorlog(status, ne);
		} catch (Exception e) {
			e.printStackTrace();
			status.setCalstatus("0");// 失败
			this.insertMc00_task_errorlog(status, e);
		} finally {

			if (!mC00_task_fact.getTasksource().equals("lab")) {

				if (rollback) {
					// 循环探测，直到成功
					TaskService taskService = (TaskService) ac.getBean("taskService");

					taskService.unLockCurrTask(mC00_task_fact);

				} else {
					// 成功和失败都会写入结果，如果失败，需要人工解决
					this.setTaskStatus(status, beginDateTime);
				}
			} else {

				if (!isSuccess) {
					throw new Exception("计算失败！");
				}

			}
			if(	"1".equals(mC00_task_fact.getReflag())){
				//重算任务成功，删除重算任务表数据
			   this.deleteReTask();
			}
		}

	}

	//删除重算任务表记录
	private void deleteReTask() {
		MC00_task_fact mC00_task_fact = this.mC00_task_factL.get();
		try {
			MC00_task_factMapper mC00_task_factMapper = (MC00_task_factMapper) ac.getBean("MC00_task_factMapper");
			mC00_task_factMapper.deleteMC00_retask_fact(mC00_task_fact);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("任务状态表更新失败，重置任务事实表......");
		}
	}
	private void setTaskStatus(MC00_task_status status, DateTime beginDateTime) {
		MC00_task_fact mC00_task_fact = this.mC00_task_factL.get();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime endDateTime = new DateTime();
		status.setEndtime(fmt.print(endDateTime));

		// 耗时：
		Duration d = new Duration(beginDateTime, endDateTime);
		long usetime = d.getStandardSeconds();// 秒
		status.setUsetime("" + usetime);

		// 设置状态记录

		try {
			MC00_task_statusMapper mC00_task_statusMapper = (MC00_task_statusMapper) ac.getBean("MC00_task_statusMapper");
			mC00_task_statusMapper.insertMC00_task_status(status);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("任务状态表更新失败，重置任务事实表......");
			// 如果任务状态插入失败，重置serverid为空，确保下次可以扫描
			MC00_task_factMapper mC00_task_factMapper = (MC00_task_factMapper) ac.getBean("MC00_task_factMapper");
			int i = mC00_task_factMapper.updateMC00_task_fact(mC00_task_fact);
			if (i == 1) {
				logger.debug("重置任务事实表成功......");
			}else{
				logger.error("重置任务事实表失败｛"+i+"｝......");
			}
		}
	}

	/**
	 * 只管实现业务逻辑，不管计算状态
	 * 
	 * @return 返回true -执行成功，记入状态表，返回false - 执行失败，不计入状态表，后续继续执行
	 * @throws Exception
	 *             - 抛错，执行失败，记入状态表
	 */
	public abstract boolean calTask() throws Exception;

	public MC00_task_status mC00_task_factTOStatus() {
		MC00_task_fact mC00_task_fact = this.mC00_task_factL.get();
		
		MC00_task_status status = new MC00_task_status();

		status.setTaskid(mC00_task_fact.getTaskid());
		status.setSubtaskid(mC00_task_fact.getSubtaskid());
		status.setDatatime(mC00_task_fact.getDatatime());
		status.setServerid(mC00_task_fact.getServerid());
		status.setFreq(mC00_task_fact.getFreq());
		status.setTrigerid(mC00_task_fact.getTrigerid());
		status.setTaskname(mC00_task_fact.getTaskname());
		status.setTgroupid(mC00_task_fact.getTgroupid());
		status.setDsid(mC00_task_fact.getDsid());
		status.setTaskcmd(mC00_task_fact.getTaskcmd());
		status.setCalstatus("0");

		return status;
	}

	public MC00_task_fact getMC00_task_fact() {
		MC00_task_fact mC00_task_fact = this.mC00_task_factL.get();
		return mC00_task_fact;
	}

	public ApplicationContext getAc() {
		return ac;
	}

	public JdbcTemplate getBusinessJdbcTemplate() {

		return jdbcTemplate;
	}

	public CacheService getCacheService() {

		return cacheService;
	}

	public CalSqlService getCalSqlService() {
		return calSqlService;
	}

	/**
	 * 通过DSName取得JdbcTemplate
	 * 
	 * @param jdbcTemplateName
	 * @return
	 * @throws Exception
	 */
	public JdbcTemplate getJdbcTemplate(String jdbcTemplateName) throws Exception {
		JdbcTemplate jdbcTemplate;

		jdbcTemplate = (JdbcTemplate) ac.getBean(jdbcTemplateName);

		return jdbcTemplate;
	}

	/**
	 * 取得数据库类型名称
	 * 
	 * @param jdbcTemplateName
	 * @return
	 * @throws Exception
	 */
	public String getDatabaseProductName(String jdbcTemplateName) throws Exception {

		JdbcTemplate jdbcTemplate = this.getJdbcTemplate(jdbcTemplateName);

		//DatabaseMetaData md = jdbcTemplate.getDataSource().getConnection().getMetaData();

		//String dbpName = md.getDatabaseProductName();

		String dbpName = "";
		
		Connection conn = null;
		
		try{
			
			conn = jdbcTemplate.getDataSource().getConnection();
			
			DatabaseMetaData md = conn.getMetaData();

			dbpName = md.getDatabaseProductName();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
			conn.close();
			}catch(Exception ee){
				
			}
		}
		
		return dbpName;
	}

	/**
	 * 批量执行入库
	 * 
	 * @param sqlList
	 * @return
	 * @throws Exception
	 */
//	public boolean syncToDatabase(ArrayList sqlList) throws Exception {
//		boolean isSucc = true;
//
//		logger.debug("sqlList.size: " + sqlList.size());
//
//		// logger.debug("exec Sql:"+sqlList.toString());
//
//		if (sqlList == null || sqlList.size() == 0) {
//			return isSucc;
//		}
//		// String[] sqls = (String[]) sqlList.toArray(new
//		// String[sqlList.size()]);
//		//
//		//
//		// this.jdbcTemplate.batchUpdate(sqls);
//
//		/**
//		 * Iterator liter = sqlList.iterator(); while(liter.hasNext()){ String
//		 * sql = (String)liter.next(); logger.info(sql); }
//		 */
//
//		// ==
//		logger.info("开始将计算结果写入数据库......");
//		Connection conn = this.jdbcTemplate.getDataSource().getConnection();
//		boolean orgComm = conn.getAutoCommit();
//		conn.setAutoCommit(false);
//		Statement stmt = null;
//		//
//		try {
//			stmt = conn.createStatement();
//			Iterator iter = sqlList.iterator();
//			int iAddSize = 0;
//			int iAddSizeAll = 0;
//			while (iter.hasNext()) {
//				iAddSize++;
//				String sql = (String) iter.next();
//				logger.info(sql);
//				 stmt.execute(sql);
////				stmt.addBatch(sql);
////				if (iAddSize >= 3000) {
////					int r[] = stmt.executeBatch();
////					iAddSizeAll += iAddSize;
////
////					logger.debug("当前执行：" + iAddSize + "条，累计执行：" + iAddSizeAll + "条！");
////					for (int i = 0; i < r.length; i++) {
////						logger.debug("结果 " + (i + 1) + " 数量：" + r[i]);
////					}
////
////					iAddSize = 0;
////				}
//			}
////			if (iAddSize > 0) {
////				iAddSizeAll += iAddSize;
////				logger.debug("当前执行：" + iAddSize + "条，累计执行：" + iAddSizeAll + "条！");
////				stmt.executeBatch();
////			}
//
//			isSucc = true;
//			conn.commit();
//		} catch (java.sql.SQLException e) {
//			conn.rollback();
//			isSucc = false;
//			e.printStackTrace();
//			throw e;
//		} finally {
//			try {
//				conn.commit();
//			    conn.setAutoCommit(orgComm);
//				if (stmt != null) {
//					stmt.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//			}
//
//		}
//
//		return isSucc;
//	}
	
	public boolean syncToDatabase(ArrayList sqlList) throws Exception {
		boolean isSucc = true;
		int len, beginRec = 0 ,endRec = 0,i=0;
		
		logger.debug("sqlList.size: " + sqlList.size());
		if (sqlList == null || (len=sqlList.size()) == 0) {
			return isSucc;
		}
		logger.debug("开始将计算结果写入数据库......");
	
		
		String[] sqls = (String[]) sqlList.toArray(new String[0]);
	

		DefaultTransactionDefinition df = new DefaultTransactionDefinition();
		DataSourceTransactionManager tm = new DataSourceTransactionManager(this.jdbcTemplate.getDataSource());
		TransactionStatus ts = tm.getTransaction(df);

		try {
			
			do {
				beginRec = (i ++)* exec_sql_len;
				endRec = i * exec_sql_len ;
				endRec = endRec > len ? len : endRec;
				this.jdbcTemplate.batchUpdate(ArrayUtils.subarray(sqls, beginRec, endRec));
				logger.debug("数据写入["+beginRec+"]-["+endRec+"]......");
			} while (endRec < len);
			
		} 
//		catch (Exception e) {
//			logger.error("SQL执行异常，请检查：");
//			logger.error(e.getMessage());
//			logger.error(sqlList.toString());
//			tm.rollback(ts);
//			isSucc = false;
//		} 
		finally {
			tm.commit(ts);
			logger.debug("写入数据库完成......");
			sqlList.clear();
		}

		return isSucc;
	}

	public boolean syncToDatabaseNo(ArrayList sqlList) throws Exception {
		boolean isSucc = true;

		logger.debug("sqlList.size: " + sqlList.size());

		if (sqlList == null || sqlList.size() == 0) {
			return isSucc;
		}

		// ==
		logger.info("开始将计算结果写入数据库......");
		Connection conn = this.jdbcTemplate.getDataSource().getConnection();
		Statement stmt = null;
		//
		try {
			stmt = conn.createStatement();
			Iterator iter = sqlList.iterator();
			while (iter.hasNext()) {
				String sql = (String) iter.next();
				logger.info(sql);
				stmt.execute(sql);
			}
			isSucc = true;
		} catch (java.sql.SQLException e) {
			isSucc = false;
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		return isSucc;
	}
	
//	public boolean syncToDatabaseNo(ArrayList sqlList) throws Exception {
//		return syncToDbBatNoTrans(sqlList, exec_sql_len);
//	}

	/**
	 * 使用一个conn执行SQL，考虑到临时表的问题
	 * @param sqlList
	 * @return
	 * @throws SQLException
	 */
	public  boolean execSqlWithOneConn(ArrayList sqlList) throws SQLException{
				boolean isSucc = true;

				if (sqlList == null || sqlList.size() == 0) {
					return isSucc;
				}
				logger.debug("sqlList.size: " + sqlList.size());
		
				logger.info("开始将计算结果写入数据库......");
				Connection conn =DataSourceUtils.getConnection(this.jdbcTemplate.getDataSource());
				try {
					execBatchSql(conn, sqlList);
				} catch (SQLException e) {
					isSucc = false;
					throw e;
				} finally {
					DataSourceUtils.releaseConnection(conn, this.jdbcTemplate.getDataSource());
					sqlList.clear();
				}
				
				return isSucc;
	}
	
	private boolean execBatchSql(Connection conn, ArrayList sqlList) throws SQLException{
		boolean isSucc = true;
		int i=1;
		Statement stmt = null;
		
		if (sqlList == null || sqlList.size() == 0) {
			return isSucc;
		}
		
		try {
			stmt = conn.createStatement();
			Iterator<String> iter = sqlList.iterator();
			while (iter.hasNext()) {
				String sql = (String) iter.next();
				 stmt.addBatch(sql);
				 
				 if((i++)%exec_sql_len == 0){
					 logger.debug("正在批量执行["+(i-1)+"]条SQL......");
					 stmt.executeBatch();
				 }
			}
			 logger.debug("正在批量执行["+(i-1)+"]条SQL......");
			 stmt.executeBatch();

		} catch (java.sql.SQLException e) {
			isSucc = false;
			logger.error("SQL执行异常，请检查：");
			logger.error(e.getMessage());
			if(i>exec_sql_len){
				logger.error(sqlList.subList(i-exec_sql_len, i).toString());
			}else{
				logger.error(sqlList.toString());
			}
			throw e;
		} finally {
			DbUtils.closeQuietly(stmt);
		}
		
		return isSucc;
	}
	
	/**
	 * 批量执行入库
	 * 
	 * @param sqlList
	 * @return
	 * @throws Exception
	 */
	public boolean syncToDatabase(Connection conn, ArrayList sqls) throws java.sql.SQLException {
		boolean blnTemp = false;
		if (sqls == null) {
			return blnTemp;
		}
		Statement stmt = null;
		int i = 0;
		try {
			stmt = conn.createStatement();
			Iterator iter = sqls.iterator();
			while (iter.hasNext()) {
				String sql = (String) iter.next();
				logger.debug("sql:::" + sql);
				// stmt.addBatch(sql);
				i = stmt.executeUpdate(sql);
				logger.debug("count:::::::::::" + i);
			}
			// stmt.executeBatch();
			blnTemp = true;
		} catch (java.sql.SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
		return blnTemp;
	}

	public boolean syncToDbBatNoTrans(ArrayList sqlList, int transLen) {
		boolean isSucc = true;
		int len, beginRec = 0 ,endRec = 0,i=0;
	
		logger.debug("开始将计算结果写入数据库......");
		if (sqlList == null || (len = sqlList.size()) == 0) {
			return isSucc;
		}

		String[] sqls = (String[]) sqlList.toArray(new String[0]);
		sqlList.clear();
		
		try {
			do {
				beginRec = (i ++)* exec_sql_len;
				endRec = i * exec_sql_len ;
				endRec = endRec > len ? len : endRec;
				this.jdbcTemplate.batchUpdate(ArrayUtils.subarray(sqls, beginRec, endRec));
			} while (endRec < len);
			
			
			logger.debug("数据库写入成功.....");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("数据库写入失败....");
			logger.error(e.getMessage());
			logger.error(sqlList.toString());
			isSucc = false;
		}

		return isSucc;
	}
	
	public boolean insertMc00_task_errorlog(MC00_task_status mc00_task_status,Exception exception)
			throws Exception {
		try {
			String currdate = DtUtils.getNowTime();
			MC00_task_factMapper mc00_task_factMapper = (MC00_task_factMapper) this.ac.getBean("MC00_task_factMapper");
			String errDes = exception.toString();
			if(errDes.length()>3900){
				errDes = errDes.substring(0,3900);
			}else{
				errDes = errDes;
			}
			//将errDes中的'用\替换，防止插不进 
			if(null!=errDes&&!"".equals(errDes)){
				errDes=StrUtils.replaceString(errDes,"'","\'\'");//或者用  ''转义
			}
			mc00_task_status.setErrormsg(errDes);
			mc00_task_factMapper.insertMC00_task_log(mc00_task_status);
		} catch (Exception e) {
			logger.error("任务执行失败：" + e.getMessage());
			e.printStackTrace();
		}  

		return false;
	}
	
	protected ArrayList handleSqlList(ArrayList sqlList){
		for (int i = 0; i < sqlList.size(); i++) {
			String  tmpSql = (String) sqlList.get(i);
			sqlList.set(i, this.dbFuncUtils.toTruncateSql(tmpSql));
		}
		return sqlList;
	}
}
