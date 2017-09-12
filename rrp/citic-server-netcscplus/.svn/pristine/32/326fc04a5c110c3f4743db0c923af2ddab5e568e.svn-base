package com.citic.server.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.citic.server.ApplicationCFG;
import com.citic.server.SpringContextHolder;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.net.mapper.MC21_task_factMapper;
import com.citic.server.service.threadpool.NTaskThreadStart;

@Service
public class TransReportServer {

	private static final Logger logger = LoggerFactory.getLogger(TransReportServer.class);

	@Autowired
	private ApplicationContext ac;

	@Autowired
	SpringContextHolder springContextHolder;

	@Autowired
	MC21_task_factMapper mc21_task_factMapper;
	
	@Autowired
	CacheService cacheService;
 
	
	public TransReportServer() {

	}

	public void cal(String serverid, String tasktype, String execserver) throws Exception {

		// 计算任务
		if(!"".equals(execserver))serverid=execserver;
		this.calTask(serverid, tasktype, execserver);

	}

	/**
	 * 处理电信诈骗需要上报的数据任务表
	 * @param serverid
	 * @return
	 * @throws Exception
	 */
	public boolean calTask(String serverid, String tasktype, String execserver) throws Exception {

		MC21_task_fact task_fact = new MC21_task_fact();
		task_fact.setTasktype(tasktype);
		if(!"".equals(execserver)) task_fact.setServerid(execserver);
	 
			ArrayList taskfactList = this.getCalTaskfactList(task_fact);
			Iterator iter = taskfactList.iterator();
			while (iter.hasNext()) {
				MC21_task_fact mC21_task_fact = (MC21_task_fact) iter.next(); 
				mC21_task_fact.setFacttablename("MC22_TASK_FACT");
				mC21_task_fact.setStatustablename("MC22_TASK_STATUS");
				
				if (mC21_task_fact.getServerid() != null && !mC21_task_fact.getServerid().equals("")) {
					continue;
				}	
				mC21_task_fact.setServerid(execserver);
				if("".equals(execserver))mC21_task_fact.setServerid(serverid);		
				boolean isLock = this.lockTask(mC21_task_fact);
				if (isLock) {// 可以执行
					String taskid = mC21_task_fact.getTaskid();
					new Thread(new NTaskThreadStart(ac, (ThreadPoolTaskExecutor) ac.getBean("netcscServerThreadPoolTaskExecutor"), mC21_task_fact)).start();
				}
			}
 
		return true;
	}

	private ArrayList getCalTaskfactList(MC21_task_fact task_fact) {
		ArrayList calList = new ArrayList();
		boolean canNextFreq = true;
		ArrayList taskfactList = this.getTaskListForCal(task_fact);
		Iterator iter = taskfactList.iterator();
		while (iter.hasNext()) {
			MC21_task_fact mC21_task_fact = (MC21_task_fact) iter.next();
			String serverid = mC21_task_fact.getServerid();
			if (serverid == null || serverid.equals("")) {
				// 未被锁定，未开始计算
				calList.add(mC21_task_fact);
			}
		}
		return calList;
	}

	public ArrayList getTaskListForCal(MC21_task_fact mC21_task_fact) {

		ArrayList taskfactList = mc21_task_factMapper.getMC22_task_factList(mC21_task_fact);

		return taskfactList;
	}

	public boolean lockTask(MC21_task_fact mC21_task_fact) {
		boolean isSucc = false;

		/**
		 * 如果锁定任务成功，返回值为1 如果锁定任务失败，或者报错，或者返回值为0
		 */
		try {
			int i = mc21_task_factMapper.lockTask(mC21_task_fact);
			if (i == 1) {
				isSucc = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isSucc;
	}

}
