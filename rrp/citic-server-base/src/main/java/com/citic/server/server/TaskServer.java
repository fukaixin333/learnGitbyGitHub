package com.citic.server.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.DatatimeService;
import com.citic.server.service.TaskService;
import com.citic.server.service.TrigerService;
import com.citic.server.service.task.BaseTask;
import com.citic.server.service.task.TaskFactory;
import com.citic.server.service.threadpool.TaskThreadPoolTask;
import com.citic.server.service.threadpool.TaskThreadStart;


@Service
public class TaskServer {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskServer.class);
	
	@Autowired
    private ApplicationContext ac;
	
	@Autowired
	DatatimeService datatimeService;
	
	@Autowired
	TrigerService trigerService;
	
	@Autowired
	TaskService taskService;
	
	public TaskServer(){
		
	}

	/**
	 *  任务执行
	 */
	public void cal(String serverid) {
		
		//ArrayList taskfactList = taskService.getTaskListForCal();
		ArrayList taskfactList = this.getCalTaskfactList();
		
		Iterator iter = taskfactList.iterator();
		while(iter.hasNext()){
			MC00_task_fact mC00_task_fact = (MC00_task_fact)iter.next();
			
			if(mC00_task_fact.getServerid()!=null && !mC00_task_fact.getServerid().equals("")){
				continue;
			}
			
			mC00_task_fact.setServerid(serverid);
			
			boolean isLock = taskService.lockTask(serverid, mC00_task_fact);
			
			if(isLock){//可以执行
				
				String taskid = mC00_task_fact.getTaskid();
				
				/**
				 * 从数据源FTP抽取数据任务，需要特殊控制线程数量:因为对方服务器可能控制我方连接数
				 * 配置文件：tools-cofnig.xml ftpserverThreadPoolTaskExecutor
				 * 
				 * 其余任务本地服务器自行控制
				 * 配置文件：tools-cofnig.xml serverThreadPoolTaskExecutor
				 */
				boolean isFtpTask = false;
				if(taskid.equalsIgnoreCase("TK_ETL101")){
					String subtaskid=mC00_task_fact.getSubtaskid();
					
					isFtpTask = true;
				}
				
				ThreadPoolTaskExecutor threadPoolTaskExecutor = null;
				if(isFtpTask){
//					new Thread(new TaskThreadStart(ac,(ThreadPoolTaskExecutor)ac.getBean("ftpserverThreadPoolTaskExecutor"),mC00_task_fact)).start();
					threadPoolTaskExecutor = (ThreadPoolTaskExecutor)ac.getBean("ftpserverThreadPoolTaskExecutor");
				}else{
//					new Thread(new TaskThreadStart(ac,(ThreadPoolTaskExecutor)ac.getBean("serverThreadPoolTaskExecutor"),mC00_task_fact)).start();
					threadPoolTaskExecutor = (ThreadPoolTaskExecutor)ac.getBean("serverThreadPoolTaskExecutor");
					
				}

				FutureTask<String> futureTask = new FutureTask<String>( new TaskThreadPoolTask(ac,mC00_task_fact) );
				threadPoolTaskExecutor.execute(futureTask);
				
			}
			
		}
		
	}
	
	private ArrayList getCalTaskfactList(){
		
		ArrayList calList = new ArrayList();
		
		for(int ifreq=1;ifreq<=7 ; ifreq++){
			/**
			 * 如果前一个频度有任务，后一个频度就不用判断了。
			 */
			boolean canNextFreq = true;
			ArrayList taskfactList = taskService.getTaskListForCal();
			
			Iterator iter = taskfactList.iterator();
			while(iter.hasNext()){
				MC00_task_fact mC00_task_fact = (MC00_task_fact)iter.next();
				
				String freq = mC00_task_fact.getFreq();
				String serverid = mC00_task_fact.getServerid();
				if(freq.equals(""+ifreq)){
					
					canNextFreq = false;
					
					if(serverid==null || serverid.equals("")){
						//未被锁定，未开始计算
						calList.add( mC00_task_fact );
					}
					else{
						//如果有正在执行的任务
						//说明，前述频度任务还没有执行完毕，需要等待。
//						calList = new ArrayList();
//						break;
					}
				}
			}
			
			if(!canNextFreq){
				break;
			}
			
		}
		
		return calList;
	}
	
}
