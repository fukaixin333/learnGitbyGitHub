package com.citic.server.service.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MM14_model;
import com.citic.server.service.task.taskBo.AlertBo;
import com.citic.server.utils.AmlDtUtils;

/**
 * 合并预警
 * 
 * @author liangchunyu
 * @version 1.0 1、按模型合并预警处理 2、单主体模型，按客户合并一定周期的预警； 3、多主体模型，将模型下事件的交易进行组网，形成网络；
 */
public class TK_AML501 extends BaseTask {

	private static final Logger logger = LoggerFactory.getLogger(TK_AML501.class);

	private CacheService cacheService;
	private ThreadLocal<AlertBo> alertBoL = new ThreadLocal<AlertBo>();
//	private AlertBo alertBo;
//	private String taskid = "";
//	private String modelkey = "";
//	private String datatime = "";
	private JdbcTemplate jdbcTemplate;

	public TK_AML501(ApplicationContext _ac, MC00_task_fact mC00_task_fact) throws Exception {
		super(_ac, mC00_task_fact);
		cacheService = (CacheService) _ac.getBean("cacheService");
		// 获取任务信息
//		taskid = this.getMC00_task_fact().getTaskid();
//		modelkey = this.getMC00_task_fact().getSubtaskid();// =eventkey
//		datatime = this.getMC00_task_fact().getDatatime();
//		alertBo = new AlertBo();
		this.alertBoL.set(new AlertBo());
		ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());
	}

	@Override
	public boolean calTask() throws Exception {
		MC00_task_fact mC00_task_fact = this.getMC00_task_fact();
		String modelkey = mC00_task_fact.getSubtaskid();
		String datatime = mC00_task_fact.getDatatime();
		
		AlertBo alertBo = alertBoL.get();
		
		boolean result = false;
		if (modelkey.equals("0")) {
			logger.info("没有找到需要计算的模型！");
			return true;
		}
		alertBo.init(this.getMC00_task_fact().getTasksource());

		// 获取模型的事件信息
		HashMap moduleMap = (HashMap) cacheService.getCache("moduleEventDetail", HashMap.class);
		MM14_model mm14_model = (MM14_model) moduleMap.get(modelkey); // 模型明细
		logger.debug("modelkey:::::::::::::" + modelkey);
		/*
		 * MM14_modelMapper mm14_modelMapper =
		 * (MM14_modelMapper)this.getAc().getBean("MM14_modelMapper");
		 * MM14_model mm14_model =mm14_modelMapper.getMM14_model(modelkey);
		 */
		ArrayList sqlList = new ArrayList();
//		if ("1".equals(mm14_model.getModel_type())) {
			sqlList = alertBo.deleteTask(sqlList,datatime, modelkey);// 数据清理
			result = this.syncToDatabase(sqlList);
			if (!result) return result;
			// 处理单主体模型,按客户合并一定周期的预警
			sqlList = this.alert_party(mm14_model);
			result = this.syncToDatabase(sqlList);
//		} 
//		else {
//			sqlList = alertBo.deleteTaskNet(sqlList, datatime, modelkey);// 数据清理
//			result = this.syncToDatabase(sqlList);
//			if (!result)
//				return result;
//			// 多主体模型，组织资金网络
//			result = this.alert_net(mm14_model);
//		}
		return result;
	}

	/***
	 * 处理单主体模型，合并客户主体一定周期内的预警
	 * 
	 * @param mm14_model
	 * @return
	 * @throws Exception
	 */
	private ArrayList alert_party(MM14_model mm14_model) throws Exception {
		ArrayList sqlList = new ArrayList();
		MC00_task_fact mC00_task_fact = this.getMC00_task_fact();
		String modelkey = mC00_task_fact.getSubtaskid();
		String datatime = mC00_task_fact.getDatatime();
		
		AlertBo alertBo = alertBoL.get();

		String rundate = mm14_model.getRunday();
		String befordate = datatime;

		// 获取模型跑批数据周期
		if (rundate != null && !"".equals(rundate) && Integer.valueOf(rundate) > 0) {
			befordate = AmlDtUtils.toStrDate(AmlDtUtils.getDateChangeTime(AmlDtUtils.toDate(datatime), "d",Integer.valueOf("-"+rundate)));
		}


		// 1、生成合并后的预警信息主表
		sqlList = alertBo.insertBR13_CASE_FACT(sqlList, modelkey, datatime, befordate);
		// 2、写案件客户信息表
		sqlList = alertBo.insertBR13_CASE_PARTY(sqlList, modelkey, datatime);
		// 3、写案件账户信息表
		sqlList = alertBo.insertBR13_CASE_ACCT(sqlList, modelkey, datatime);
		// 4、写案件交易明细表
		sqlList = alertBo.insertBR13_CASE_TRANS(sqlList, modelkey, datatime,befordate);
		// 5、更新涉罪类型
//		sqlList = alertBo.updateCRIMINAL_TYPE(sqlList, modelkey, datatime);

		return sqlList;
	}

	/**
	 * 处理多主体模型，组织资金网络
	 * 
	 * @param mm14_model
	 * @return
	 * @throws Exception
	 */
	private boolean alert_net(MM14_model mm14_model) throws Exception {
		MC00_task_fact mC00_task_fact = this.getMC00_task_fact();
		String datatime = mC00_task_fact.getDatatime();
		AlertBo alertBo = alertBoL.get();

		boolean result = true;
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		ArrayList sqlList = new ArrayList();
		try {
			conn.setAutoCommit(false);
			// 1、建立组网交易明细
			sqlList = alertBo.insertBT13_TRANS_MID(sqlList, datatime, mm14_model);

			sqlList = alertBo.insertBT13_trans_d_acct(sqlList);

			// 2、生成存在多笔交易的账号的网络，包括对手
			// 2.1 找到发生多笔交易的账号
			sqlList = alertBo.insertBT13_acct_count(sqlList);
			// 2.2 多笔交易的账号直接关联的账号
			sqlList = alertBo.insertBT13_net_acct_muti(sqlList);
			// 2.3 多笔交易的账号直接关联的账号插入合并表BT13_net_acct(批次一)
			sqlList = alertBo.insertBT13_net_acct1(sqlList);
			result = this.syncToDatabase(conn, sqlList);
			if (!result) {
				return result;
			}
			 
			// 2.4合并网络(批次一)
			result = alertBo.mergeNet(conn, datatime, "1", false);
			if (!result) {
				return result;
			}
			sqlList = new ArrayList();
			// 3.1 生成剩余未进入网络的关系数据
			sqlList = alertBo.insertBT13_trans_d_acct_2(sqlList);

			// 3.2找单笔交易中存在关联的
			sqlList = alertBo.insertBT13_net_acct_single(sqlList);

			// 3.3单笔交易中存在关联的账号插入合并表BT13_net_acct（批次二）
			sqlList = alertBo.insertBT13_net_acct2(sqlList);
			result = this.syncToDatabase(conn, sqlList);
			if (!result) {
				return result;
			}
			// 3.4合并网络(批次二)
			result = alertBo.mergeNet(conn, datatime, "2", false);
			if (!result) {
				return result;
			}
			sqlList = new ArrayList();
			/** 4.将单笔交易插入网络 **/
			// 4.1生成剩余未进入网络的关系数据
			sqlList = alertBo.insertBT13_trans_d_acct_3(sqlList);

			// 4.2插入剩余未进入网络的关系数据到中间表
			sqlList = alertBo.insertBT13_net_acct_non(sqlList);

			// 4.3插入合并表BT13_net_acct（批次三）
			sqlList = alertBo.insertBT13_net_acct3(sqlList);
			result = this.syncToDatabase(conn, sqlList);
			if (!result) {
				return result;
			}
			// 4.4合并网络(批次三)
			result = alertBo.mergeNet(conn, datatime, "3", false);
			if (!result) {
				return result;
			}
			// 准备批次四的临时数据
			// 批次四:A-B一笔交易，A-C(n)，B-D(n)，A和B分别进入C、D网络，而没有合并
			sqlList = new ArrayList();
			sqlList = alertBo.insertBT13_net_acct4(sqlList);
			result = this.syncToDatabase(conn, sqlList);
			if (!result) {
				return result;
			}
			// 4.5合并网络(批次四)
			result = alertBo.mergeNet(conn, datatime, "4", false);
			if (!result) {
				return result;
			}
			sqlList = new ArrayList();
			// 6.2插入当天合并的网络并更新创建日期
			sqlList = alertBo.insertBT13_net_acct_day(sqlList, datatime);

			// 网络交易明细表
			sqlList = alertBo.insertBR13_net_event_trans(sqlList, mm14_model.getModelkey());
			 
			// 插入网络事件明细
			sqlList = alertBo.insertBR13_net_event(sqlList, mm14_model.getModelkey(),datatime);
			 
			// 插入网络主体
			sqlList = alertBo.insertBR13_net_trans(sqlList, mm14_model.getCriminal_type(), datatime, mm14_model.getModelkey());
			result = this.syncToDatabase(conn, sqlList);
			if (!result) {
				conn.rollback();
				return result;
			}
			conn.commit();
			
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
			ex.printStackTrace();
			conn.rollback();
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		return result;
	}

	/**
	 * 初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean cal_init_net() throws Exception {

		return true;
	}
}
