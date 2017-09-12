package com.citic.server.service.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.mapper.MC00_triger_factMapper;

public class TrigerThreadStart implements Runnable {
	
	private ApplicationContext ac;
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	private String datatime;
	private MC00_triger_sub mC00_triger_sub;
	//private MC00_triger_factMapper mC00_triger_factMapper;
	
	public TrigerThreadStart(ApplicationContext _ac,ThreadPoolTaskExecutor _threadPoolTaskExecutor,String _datatime,MC00_triger_sub _mC00_triger_sub) {
		this.ac = _ac;
		this.threadPoolTaskExecutor = _threadPoolTaskExecutor;
		this.datatime = _datatime;
		this.mC00_triger_sub = _mC00_triger_sub;
		//this.mC00_triger_factMapper = _mC00_triger_factMapper;

	}

	//@Override
	public synchronized void run() {

		FutureTask<String> futureTask = new FutureTask<String>( new TrigerThreadPoolTask(ac,datatime,mC00_triger_sub) );

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
