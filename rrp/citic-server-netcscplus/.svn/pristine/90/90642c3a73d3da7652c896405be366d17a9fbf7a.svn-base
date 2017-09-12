package com.citic.server.server;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.dx.service.RequestMessageService;
import com.citic.server.net.mapper.MC21_task_factMapper;
import com.citic.server.service.CacheService;
import com.citic.server.service.CalSqlService;
import com.citic.server.service.base.Base;
import com.citic.server.service.base.NeetReCalException;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MC21_task_status;

/**
 * @author hubq
 * @version 1.0
 */

public abstract class NBaseTask extends Base {
	
	private static final Logger logger = LoggerFactory.getLogger(NBaseTask.class);
	
	private ThreadLocal<MC21_task_fact> mC21_task_factL = new ThreadLocal<MC21_task_fact>();
	
	/** 业务数据库链接串 */
	private String businessJdbcTemplate = "jdbcTemplate";
	
	private JdbcTemplate jdbcTemplate;
	
	private CacheService cacheService;
	
	private CalSqlService calSqlService;
	
	protected RequestMessageService requestMessageService;
	
	private ApplicationContext ac;
	
	public NBaseTask(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		mC21_task_factL.set(mC21_task_fact);
		this.ac = _ac;
		
		jdbcTemplate = (JdbcTemplate) ac.getBean(this.businessJdbcTemplate);
		cacheService = (CacheService) ac.getBean("cacheService");
		calSqlService = (CalSqlService) ac.getBean("calSqlService");
		requestMessageService = (RequestMessageService) ac.getBean("requestMessageService");
	}
	
	public void run() throws Exception {
		boolean isSuccess = false;
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		MC21_task_status status = this.mC21_task_factTOStatus();
		DateTime beginDateTime = new DateTime();
		status.setBegintime(fmt.print(beginDateTime));
		
		try {
			isSuccess = this.calTask(); // 任务计算主入口
		} catch (NeetReCalException ne) {
			isSuccess = false;
		} catch (Exception e) {
			isSuccess = false;
			logger.error("Exception: {}", e.getMessage(), e);
		} finally {
			// 成功和失败都会写入结果，如果失败，需要人工解决
			if (isSuccess) {
				status.setCalstatus("1");// 成功
			} else {
				status.setCalstatus("0");// 失败
			}
			this.setTaskStatus(status, beginDateTime);
		}
	}
	
	private void setTaskStatus(MC21_task_status taskStatus, DateTime beginDateTime) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime endDateTime = new DateTime();
		taskStatus.setEndtime(fmt.print(endDateTime));
		
		Duration d = new Duration(beginDateTime, endDateTime);
		taskStatus.setUsetime(String.valueOf(d.getStandardSeconds())); // 秒
		
		// 设置状态记录
		MC21_task_factMapper mC21_task_factMapper = (MC21_task_factMapper) ac.getBean("MC21_task_factMapper");
		try {
			mC21_task_factMapper.deleteMC21_task_status(taskStatus);
			mC21_task_factMapper.insertMC21_task_status(taskStatus);
		} catch (Exception e) {
			logger.error("Exception: 任务状态记录失败 - TaskKey=[{}] CalStatus=[{}]", taskStatus.getTaskkey(), taskStatus.getCalstatus(), e);
			// 任务状态插入失败，不能重置任务（将任务表的ServerID置空）
			// 有些任务，是不允许重复计算的，比如网络查控中的冻结和扣划等任务。业务逻辑中无法对任务进行控制，而导致重复执行任务。
			// > liuxuanfei 2017-08-04 14:43
			
			// 如果任务状态插入失败，重置serverid为空，确保下次可以扫描
			// int i = mC21_task_factMapper.updateMC21_task_fact(mC21_task_fact);
		}
	}
	
	/**
	 * 只管实现业务逻辑，不管计算状态
	 * 
	 * @return 返回true -执行成功，记入状态表，返回false - 执行失败，不计入状态表，后续继续执行
	 * @throws Exception
	 *         - 抛错，执行失败，记入状态表
	 */
	public abstract boolean calTask() throws Exception;
	
	public MC21_task_status mC21_task_factTOStatus() {
		MC21_task_fact mC21_task_fact = this.mC21_task_factL.get();
		
		MC21_task_status status = new MC21_task_status();
		status.setTaskkey(mC21_task_fact.getTaskkey());
		status.setTaskid(mC21_task_fact.getTaskid());
		status.setSubtaskid(mC21_task_fact.getSubtaskid());
		status.setDatatime(mC21_task_fact.getDatatime());
		status.setServerid(mC21_task_fact.getServerid());
		status.setFreq(mC21_task_fact.getFreq());
		status.setTrigerid(mC21_task_fact.getTrigerid());
		status.setTaskname(mC21_task_fact.getTaskname());
		status.setTgroupid(mC21_task_fact.getTgroupid());
		status.setDsid(mC21_task_fact.getDsid());
		status.setTaskcmd(mC21_task_fact.getTaskcmd());
		status.setBdhm(mC21_task_fact.getBdhm());
		status.setIsdyna(mC21_task_fact.getIsdyna());
		status.setTasktype(mC21_task_fact.getTasktype());
		status.setTaskobj(mC21_task_fact.getTaskobj());
		status.setFacttablename(mC21_task_fact.getFacttablename());
		status.setStatustablename(mC21_task_fact.getStatustablename());
		status.setCalstatus("0");
		return status;
	}
	
	public MC21_task_fact getMC21_task_fact() {
		MC21_task_fact mC21_task_fact = this.mC21_task_factL.get();
		return mC21_task_fact;
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
		
		String dbpName = "";
		
		Connection conn = null;
		
		try {
			
			conn = jdbcTemplate.getDataSource().getConnection();
			
			DatabaseMetaData md = conn.getMetaData();
			
			dbpName = md.getDatabaseProductName();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception ee) {
				
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
	public boolean syncToDatabase(ArrayList sqlList) throws Exception {
		boolean isSucc = true;
		
		logger.debug("sqlList.size: " + sqlList.size());
		
		// logger.debug("exec Sql:"+sqlList.toString());
		
		if (sqlList == null || sqlList.size() == 0) {
			return isSucc;
		}
		// String[] sqls = (String[]) sqlList.toArray(new
		// String[sqlList.size()]);
		//
		//
		// this.jdbcTemplate.batchUpdate(sqls);
		
		/**
		 * Iterator liter = sqlList.iterator(); while(liter.hasNext()){ String
		 * sql = (String)liter.next(); logger.info(sql); }
		 */
		
		// ==
		logger.info("开始将计算结果写入数据库......");
		Connection conn = this.jdbcTemplate.getDataSource().getConnection();
		boolean orgComm = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement stmt = null;
		//
		try {
			stmt = conn.createStatement();
			Iterator iter = sqlList.iterator();
			int iAddSize = 0;
			int iAddSizeAll = 0;
			while (iter.hasNext()) {
				iAddSize++;
				String sql = (String) iter.next();
				logger.info(sql);
				stmt.execute(sql);
				//				stmt.addBatch(sql);
				//				if (iAddSize >= 3000) {
				//					int r[] = stmt.executeBatch();
				//					iAddSizeAll += iAddSize;
				//
				//					logger.debug("当前执行：" + iAddSize + "条，累计执行：" + iAddSizeAll + "条！");
				//					for (int i = 0; i < r.length; i++) {
				//						logger.debug("结果 " + (i + 1) + " 数量：" + r[i]);
				//					}
				//
				//					iAddSize = 0;
				//				}
			}
			//			if (iAddSize > 0) {
			//				iAddSizeAll += iAddSize;
			//				logger.debug("当前执行：" + iAddSize + "条，累计执行：" + iAddSizeAll + "条！");
			//				stmt.executeBatch();
			//			}
			
			isSucc = true;
			conn.commit();
		} catch (java.sql.SQLException e) {
			conn.rollback();
			isSucc = false;
			e.printStackTrace();
			throw e;
		} finally {
			try {
				conn.commit();
				conn.setAutoCommit(orgComm);
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
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
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
	
	public void inertErrorLog(Exception e) throws Exception {
		
	}
	
}
