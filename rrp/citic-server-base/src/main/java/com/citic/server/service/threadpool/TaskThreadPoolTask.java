package com.citic.server.service.threadpool;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_task_fact;
import com.citic.server.mapper.MC00_task_factMapper;
import com.citic.server.service.TaskService;
import com.citic.server.service.task.BaseTask;
import com.citic.server.service.task.TaskFactory;

public class TaskThreadPoolTask implements Callable<String>, Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskThreadPoolTask.class);
	
	private static final long serialVersionUID = 0;

	// 保存任务所需要的数据
	private MC00_task_fact mC00_task_fact;

	@Autowired
	TaskService taskService;
	@Autowired
	MC00_task_factMapper mC00_task_factMapper;
	private ApplicationContext ac;
	
	public TaskThreadPoolTask(ApplicationContext _ac,MC00_task_fact _mC00_task_fact) {

		this.ac = _ac;
		this.mC00_task_fact = _mC00_task_fact;

	}

	public synchronized String call() throws Exception {

		String result = "SUCESS";
		
		long useTime = 0;

		try {

			DateTime startDatetime = new DateTime();
			
			TaskFactory taskFactory = new TaskFactory();
			
			BaseTask baseTask = taskFactory.getInstance(ac,mC00_task_fact);
			
			baseTask.run();

			DateTime endDatetime = new DateTime();
			
			Duration d = new Duration(startDatetime, endDatetime);  
			
			useTime =  d.getMillis()/1000; 
			
		} catch (Exception e) {

			e.printStackTrace();

			result = "FALSE";
			
			logger.error(e.getMessage());
			
		}finally{
			
			logger.info( result );
			logger.info("taskid="+this.mC00_task_fact.getTaskid()+"；taskname="+this.mC00_task_fact.getTaskname());
			logger.info("TotalUseTime="+useTime);
			
		}

		return result;

	}

	
}
