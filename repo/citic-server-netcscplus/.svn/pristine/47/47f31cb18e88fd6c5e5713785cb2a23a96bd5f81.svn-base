package com.citic.server.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.citic.server.SpringContextHolder;
import com.citic.server.net.mapper.MC21_task_factMapper;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.threadpool.NTaskThreadPoolTask;

@Service
public class NetcscServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetcscServer.class);
	
	@Autowired
	private MC21_task_factMapper mc21_task_factMapper;
	
	@Autowired
	private CacheService cacheService;
	
	public static final String TASK_MODULE_1 = "01"; // Task 1
	public static final String TASK_MODULE_2 = "02"; // Task 2
	public static final String TASK_MODULE_3 = "03"; // Task 3
	
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	public NetcscServer() {
		// Do nothing
	}
	
	public void call(String serverAlias, String taskType, String taskModule) throws Exception {
		LOGGER.info("开始执行任务服务: Alias=[{}] TaskType=[{}] TaskModule=[{}]", serverAlias, taskType, taskModule);
		long li1 = System.currentTimeMillis();
		
		List<String[]> taskTableList; // 暂未发现关联 STATUS 表的实际用意，暂不使用
		if (taskModule == null || taskModule.length() == 0) {
			taskTableList = new ArrayList<String[]>(3);
			taskTableList.add(new String[] {"MC20_TASK_FACT1", "MC20_TASK1_STATUS", TASK_MODULE_1});
			taskTableList.add(new String[] {"MC21_TASK_FACT2", "MC21_TASK2_STATUS", TASK_MODULE_2});
			taskTableList.add(new String[] {"MC21_TASK_FACT3", "MC21_TASK3_STATUS", TASK_MODULE_3});
		} else {
			taskTableList = new ArrayList<String[]>(1); // 保留将来支持多任务模块
			if (taskModule.equals(TASK_MODULE_1)) {
				taskTableList.add(new String[] {"MC20_TASK_FACT1", "MC20_TASK1_STATUS", TASK_MODULE_1});
			} else if (taskModule.equals(TASK_MODULE_2)) {
				taskTableList.add(new String[] {"MC21_TASK_FACT2", "MC21_TASK2_STATUS", TASK_MODULE_2});
			} else if (taskModule.equals(TASK_MODULE_3)) {
				taskTableList.add(new String[] {"MC21_TASK_FACT3", "MC21_TASK3_STATUS", TASK_MODULE_3});
			} else {
				throw new IllegalArgumentException("启动运行参数[任务模块]必须是 01、02 或者 03（暂不支持多任务模块）");
			}
		}
		
		@SuppressWarnings("unchecked")
		HashMap<String, MC21_task> taskConfMap = (HashMap<String, MC21_task>) cacheService.getCache("MC21TaskDetail", HashMap.class);
		
		int taskCount = 0;
		for (String[] taskTable : taskTableList) {
			// 暂未发现关联 STATUS 表的实际用意，暂不使用
			List<MC21_task_fact> taskList = mc21_task_factMapper.queryNextExecutoryTaskList(taskTable[0], taskType, taskTable[2]);
			
			if (taskList == null || taskList.size() == 0) {
				continue;
			}
			
			for (MC21_task_fact taskFact : taskList) {
				taskFact.setFacttablename(taskTable[0]);
				taskFact.setStatustablename(taskTable[1]);
				taskFact.setServerid(taskTable[2]);
				
				boolean isLocked = this.lockTask(taskFact);
				if (!isLocked) {
					continue;
				}
				
				MC21_task taskConf = taskConfMap.get(taskFact.getTaskid());
				taskFact.setIsemployee(taskConf.getIsEmployee());
				
				if (taskConf.isMultiThread0()) { // 是否使用多线程方式
					Callable<String> callableTask = new NTaskThreadPoolTask(SpringContextHolder.getApplicationContext(), taskFact);
					// FutureTask 会内部吃掉异常，所以Callable必须自己捕获异常
					FutureTask<String> futureTask = new FutureTask<String>(callableTask);
					createThreadPoolTaskExecutor().execute(futureTask);
				} else {
					synchronousExecute(taskFact);
				}
				taskCount++;
			}
		}
		
		long li2 = System.currentTimeMillis();
		LOGGER.info("本次任务服务执行完成，约耗时=[{}]秒，任务数=[{}]", (li2 - li1) / 1000, taskCount);
	}
	
	public ThreadPoolTaskExecutor createThreadPoolTaskExecutor() {
		if (threadPoolTaskExecutor == null) {
			threadPoolTaskExecutor = SpringContextHolder.getBean("netcscServerThreadPoolTaskExecutor");
		}
		return threadPoolTaskExecutor;
	}
	
	/**
	 * 直接执行任务
	 * 
	 * @param taskFact
	 */
	private void synchronousExecute(MC21_task_fact taskFact) {
		DateTime startDatetime = new DateTime();
		long useTime = 0;
		String result = "SUCCESS";
		try {
			NTaskFactory taskFactory = new NTaskFactory();
			NBaseTask baseTask = taskFactory.getInstance(SpringContextHolder.getApplicationContext(), taskFact);
			baseTask.run();
		} catch (Exception e) {
			result = "FAILED";
			LOGGER.error("应用程序异常：{}", e.getMessage(), e);
		} finally {
			DateTime endDatetime = new DateTime();
			Duration d = new Duration(startDatetime, endDatetime);
			useTime = d.getMillis() / 1000;
			LOGGER.info("{} TaskId=[{}]，TaskName=[{}]，TotalUseTime=[{}]", result, taskFact.getTaskid(), taskFact.getTaskname(), useTime);
		}
	}
	
	private boolean lockTask(MC21_task_fact taskFact) {
		try {
			int n = mc21_task_factMapper.lockTask(taskFact);
			if (n >= 1) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception: 锁定待处理任务异常 - TaskKey=[{}]", taskFact.getTaskkey(), e);
		}
		return false;
	}
}
