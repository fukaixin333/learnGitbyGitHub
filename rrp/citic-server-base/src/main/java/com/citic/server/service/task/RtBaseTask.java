package com.citic.server.service.task;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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

import com.citic.server.domain.MC02_rt_task_fact;
import com.citic.server.mapper.MC02_rt_task_factMapper;
import com.citic.server.service.CacheService;
import com.citic.server.service.base.Base;
import com.citic.server.service.base.NeetReCalException;

/**
 * 
 * @author hubq
 * @version 1.0
 */

public abstract class RtBaseTask extends Base {

	private static final Logger logger = LoggerFactory.getLogger(RtBaseTask.class);

	private MC02_rt_task_fact mC02_rt_task_fact = new MC02_rt_task_fact();

	/** 业务数据库链接串 */
	private String businessJdbcTemplate = "jdbcTemplate";

	private JdbcTemplate jdbcTemplate;

	private CacheService cacheService;

	private ApplicationContext ac;

	public RtBaseTask(ApplicationContext _ac, MC02_rt_task_fact _mC02_rt_task_fact) {
		this.mC02_rt_task_fact = _mC02_rt_task_fact;
		this.ac = _ac;

		jdbcTemplate = (JdbcTemplate) ac.getBean(this.businessJdbcTemplate);

		cacheService = (CacheService) ac.getBean("cacheService");
	}

	public void run() throws Exception {
		boolean isSuccess = false;

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		MC02_rt_task_fact status = mC02_rt_task_fact;
		DateTime beginDateTime = new DateTime();
		status.setBegintime(fmt.print(beginDateTime));
		try {
			// 任务计算主入口
			isSuccess = this.calTask();
			if (isSuccess) {
				status.setCalstatus("4");// 成功
			} else {
				status.setCalstatus("0");// 失败
			}
		} catch (NeetReCalException ne) {
			status.setCalstatus("0");// 失败
		} catch (Exception e) {
			e.printStackTrace();
			status.setCalstatus("0");// 失败
		} finally {
			// 成功和失败都会写入结果，如果失败，需要人工解决
			this.setTaskStatus(status, beginDateTime);
		}

	}

	private void setTaskStatus(MC02_rt_task_fact status, DateTime beginDateTime) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime endDateTime = new DateTime();
		status.setEndtime(fmt.print(endDateTime));
		// 耗时：
		Duration d = new Duration(beginDateTime, endDateTime);
		long usetime = d.getStandardSeconds();// 秒
		status.setUsetime("" + usetime);
		// 设置状态记录
		try {
			MC02_rt_task_factMapper mC02_rt_task_factMapper = (MC02_rt_task_factMapper) ac.getBean("MC02_rt_task_factMapper");
			mC02_rt_task_factMapper.updateMc02_rt_task_fact(status);
		} catch (Exception e) {
			logger.error("任务状态表更新失败！");
			e.printStackTrace();
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

	public ApplicationContext getAc() {
		return ac;
	}

	public JdbcTemplate getBusinessJdbcTemplate() {

		return jdbcTemplate;
	}

	public CacheService getCacheService() {

		return cacheService;
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

	public MC02_rt_task_fact getMC02_rt_task_fact() {
		return mC02_rt_task_fact;
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
		try {
			String[] sqls = new String[sqlList.size()];
			Iterator iter = sqlList.iterator();
			int i = 0;
			while (iter.hasNext()) {
				String sql = (String) iter.next();
				logger.info(sql);
				sqls[i] = sql;
				i++;
			}
			this.jdbcTemplate.batchUpdate(sqls);
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			throw e;
		}
		return isSucc;
	}
}
