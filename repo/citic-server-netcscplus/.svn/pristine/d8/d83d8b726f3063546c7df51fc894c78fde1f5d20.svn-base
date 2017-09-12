package com.citic.server.service.threadpool;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MC21_task_factMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.server.NTaskFactory;
import com.citic.server.service.domain.MC21_task_fact;

public class NTaskThreadPoolTask implements Callable<String>, Serializable {
	private static final long serialVersionUID = 8493155309265631755L;
	
	private static final Logger logger = LoggerFactory.getLogger(NTaskThreadPoolTask.class);
	
	private ApplicationContext ac;
	// 保存任务所需要的数据
	private MC21_task_fact mC21_task_fact;
	
	@Autowired
	MC21_task_factMapper mC00_task_factMapper;
	
	public NTaskThreadPoolTask(ApplicationContext _ac, MC21_task_fact _mC21_task_fact) {
		this.ac = _ac;
		this.mC21_task_fact = _mC21_task_fact;
	}
	
	@Override
	public String call() throws Exception { // synchronized
		DateTime startDatetime = new DateTime();
		long useTime = 0;
		String result = "SUCCESS";
		try {
			NTaskFactory taskFactory = new NTaskFactory();
			NBaseTask baseTask = taskFactory.getInstance(ac, mC21_task_fact);
			baseTask.run();
		} catch (Exception e) {
			result = "FAILED";
			logger.error("应用程序异常：{}", e.getMessage(), e);
		} finally {
			DateTime endDatetime = new DateTime();
			Duration d = new Duration(startDatetime, endDatetime);
			useTime = d.getMillis() / 1000;
			logger.info("{} TaskId=[{}]，TaskName=[{}]，TotalUseTime=[{}]", result, mC21_task_fact.getTaskid(), mC21_task_fact.getTaskname(), useTime);
		}
		
		return result;
	}
}
