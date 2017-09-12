package com.citic.server.service.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.citic.server.service.domain.MC21_task_fact;
 

public class NTaskThreadStart implements Runnable {

	private ApplicationContext ac;
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	private MC21_task_fact mC21_task_fact;
	
	public NTaskThreadStart(ApplicationContext _ac,ThreadPoolTaskExecutor _threadPoolTaskExecutor,MC21_task_fact _mC21_task_fact) {
		this.ac = _ac;
		this.threadPoolTaskExecutor = _threadPoolTaskExecutor;
		this.mC21_task_fact = _mC21_task_fact;

	}

	//@Override
	public synchronized void run() {

		FutureTask<String> futureTask = new FutureTask<String>( new NTaskThreadPoolTask(ac,mC21_task_fact) );

		threadPoolTaskExecutor.execute(futureTask);

		// 在这里可以做别的任何事情
		
		//计算结果进行处理
		String result = null;

		try {

			// 取得结果，同时设置超时执行时间为1秒。同样可以用future.get()，不设置执行超时时间取得结果
			//result = futureTask.get(1000, TimeUnit.MILLISECONDS);
			result = futureTask.get();

		} catch (InterruptedException e) {

			futureTask.cancel(true);

		} catch (ExecutionException e) {

			futureTask.cancel(true);

		} catch (Exception e) {

			futureTask.cancel(true);

			// 超时后，进行相应处理

		} finally {

			//

		}

	}

}
