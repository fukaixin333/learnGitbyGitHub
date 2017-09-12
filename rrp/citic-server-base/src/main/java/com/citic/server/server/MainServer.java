package com.citic.server.server;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.citic.server.ApplicationCFG;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.domain.MC00_task_status;
import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.mapper.MC00_task_factMapper;
import com.citic.server.mapper.MC00_task_statusMapper;
import com.citic.server.service.CacheService;
import com.citic.server.service.DatatimeService;
import com.citic.server.service.TaskService;
import com.citic.server.service.TrigerService;
import com.citic.server.service.UtilsService;
import com.citic.server.service.task.BaseTask;
import com.citic.server.service.task.TaskFactory;
import com.citic.server.service.triger.BaseTrigerCondition;
import com.citic.server.service.triger.TrigerFactory;

@Service
public class MainServer {

	private static final Logger logger = LoggerFactory.getLogger(MainServer.class);

	@Autowired
	private ApplicationContext ac;

	@Autowired
	DatatimeService datatimeService;

	@Autowired
	TrigerService trigerService;

	@Autowired
	TaskService taskService;

	@Autowired
	UtilsService utilsService;

	@Autowired
	CacheService cacheService;

	public MainServer() {

	}

	public void cal(String serverid) {
		try {

			// 1-维护待扫描数据日期
			ArrayList scanList = new ArrayList();
			try {
				scanList = (ArrayList) datatimeService.maintainDatatimeForScan();
			} catch (Exception e) {
				logger.error("维护待扫描数据日期失败！");
				e.printStackTrace();
			}
			int tasknum = 0;

			for (int i = scanList.size() - 1; i >= 0; i--) {
				// 数据日期，从最小日期开始
				String datatime = (String) scanList.get(i);

				/**
				 * 判断当前的数据日期所在频度，与任务频度的匹配情况 无法匹配的任务，在判断当日任务执行情况的-完整性判断时，会被忽略掉！
				 */
				HashMap trigersubMap = (HashMap) cacheService.getCache("triger_subbytrigerid", HashMap.class);
				HashMap trigerFreqMap = new HashMap();
				Iterator trigerIter = trigersubMap.keySet().iterator();
				while (trigerIter.hasNext()) {
					String trigerid = (String) trigerIter.next();
					String tfreqs = "1";// 默认：日
					ArrayList subList = (ArrayList) trigersubMap.get(trigerid);
					Iterator trigersubIter = subList.iterator();
					while (trigersubIter.hasNext()) {
						MC00_triger_sub mC00_triger_sub = (MC00_triger_sub) trigersubIter.next();
						String cid = mC00_triger_sub.getTrigercondid();
						if (cid.equalsIgnoreCase("S2")) {// 频度
							tfreqs = mC00_triger_sub.getParam1();
						}//
					}//

					boolean isNeedTriger = false;
					String[] _tfreqs = tfreqs.split(",");
					for (int f = 0; f < _tfreqs.length; f++) {
						if (utilsService.isFreqEnd(datatime, _tfreqs[f])) {// 当前日期是否
																			// 适合
																			// 本频度计算
							isNeedTriger = true;
							break;
						}
					}
					trigerFreqMap.put(trigerid, isNeedTriger);

				}

				// 当前数据日期下，全部的任务执行状态都取出来
				MC00_task_status mC00_task_status = new MC00_task_status();
				mC00_task_status.setDatatime(datatime);
				MC00_task_statusMapper mC00_task_statusMapper = (MC00_task_statusMapper) this.ac.getBean("MC00_task_statusMapper");
				ArrayList currTksList = mC00_task_statusMapper.getMC00_task_statusListForAllTask(mC00_task_status);

				tasknum += currTksList.size();

				// 2-触发条件计算 :
				/** 取得某日 没有被设置过的触发器条件列表，用于判断 */
				ArrayList trigersubList = trigerService.getMC00_triger_subList(datatime);
				Iterator trigersubIter = trigersubList.iterator();
				while (trigersubIter.hasNext()) {
					MC00_triger_sub mC00_triger_sub = (MC00_triger_sub) trigersubIter.next();

					/**
					 * 多线程方式下：，如果频度放快，前一轮没有完成，后一轮开始，会导致重复插入，主键冲突错误。
					 * 目前考虑放弃多线程，主要还是进度不一致，会导致混乱 //new Thread(new
					 * TrigerThreadStart(ac,(ThreadPoolTaskExecutor)ac.getBean(
					 * "serverThreadPoolTaskExecutor"),
					 * datatime,mC00_triger_sub)).start();
					 */

					String trigercondid = mC00_triger_sub.getTrigercondid();

					try {

						TrigerFactory trigerFactory = new TrigerFactory();

						BaseTrigerCondition trigerCondition = trigerFactory.getInstance(ac, datatime, mC00_triger_sub, currTksList);

						ArrayList trigerfactList = trigerCondition.runTrigerCondition();

						this.syncToDatabase(trigerfactList);

					} catch (Exception e) {

						e.printStackTrace();

						String result = "FALSE,(datatime=" + datatime + ";triggerid=" + mC00_triger_sub.getTrigerid() + ";triggercondid="
								+ mC00_triger_sub.getTrigercondid() + "),原因：" + e.getMessage();
						logger.error(result);
					}

				}

				// 3-激发触发器，设置任务事实表
				try {
					// 等待1秒，让触发条件计算的现场尽可能多的完成
					// Thread.currentThread().sleep(1*1000);

					ArrayList trigeredList = trigerService.getFinishedTrigerList(datatime);
					if (trigeredList.size() > 0)
						taskService.setTasks(datatime, trigeredList);

				} catch (Exception e) {
					logger.error("激发触发器，设置任务事实表失败！");
					e.printStackTrace();
				}

				// 4-判断当日所有任务已经执行完毕：更新：mC00_data_time表状态，清理相关临时（mC00_triger_fact、mC00_task_fact）表的数据
				try {
					if (taskService.canFinishTask(datatime, trigerFreqMap)) {
						//
						taskService.finishTask(datatime);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

//			if (tasknum == 0) {
				// 5-重新计算任务处理
				try {
					// 1.获取重新计算的任务列表
					MC00_task_factMapper mc00_task_factMapper = (MC00_task_factMapper) this.ac.getBean("MC00_task_factMapper");
					MC00_task_statusMapper mC00_task_statusMapper = (MC00_task_statusMapper) this.ac.getBean("MC00_task_statusMapper");
					ArrayList taskList = mc00_task_factMapper.getMC00_retask_factList();
					logger.debug("需要重新计算的任务数：" + taskList.size());
					// 2.遍历需要执行的任务
					Iterator iter = taskList.iterator();
					while (iter.hasNext()) {
						MC00_task_fact mC00_task_fact = (MC00_task_fact) iter.next();
						mC00_task_fact.setReflag("1");
						
						// 执行任务
						TaskFactory taskFactory = new TaskFactory();
						BaseTask baseTask = taskFactory.getInstance(ac, mC00_task_fact);
						// 清除状态表的数据
						MC00_task_status mC00_task_status = baseTask.mC00_task_factTOStatus();
						mC00_task_statusMapper.deleteMC00_task_status(mC00_task_status);

						baseTask.run();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
//			}

		} catch (Exception e) {
			logger.error("任务执行失败：" + e.getMessage());
			e.printStackTrace();
		}
	}

	private void syncToDatabase(final ArrayList trigerfactList) throws Exception {
		/**
		 * 添加新任务
		 */
		String sql = "insert into mC00_triger_fact(trigerid,trigercondid,datatime,freq,dsid) values(?,?,?,?,?)";

		ApplicationCFG applicationCFG = (ApplicationCFG) ac.getBean("applicationCFG");

		JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean(applicationCFG.getJdbctemplate_business());
		try {

			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					MC00_triger_fact tf = (MC00_triger_fact) trigerfactList.get(i);
					ps.setString(1, tf.getTrigerid());
					ps.setString(2, tf.getTrigercondid());
					ps.setString(3, tf.getDatatime());
					ps.setString(4, tf.getFreq());
					ps.setString(5, tf.getDsid());

				}

				public int getBatchSize() {
					return trigerfactList.size();
				}
			};

			int[] resut = jdbcTemplate.batchUpdate(sql, setter);

		} catch (Exception e) {
			logger.error("trigerfactList=" + trigerfactList.size());
			Iterator iter1 = trigerfactList.iterator();
			while (iter1.hasNext()) {
				MC00_triger_fact tf = (MC00_triger_fact) iter1.next();
				logger.error(tf.getTrigerid() + "" + tf.getTrigercondid());
			}
			throw e;
		}
	}

}
