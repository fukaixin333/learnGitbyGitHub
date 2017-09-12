package com.citic.server.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.task.tk_ds101.BaseDS;
import com.citic.server.service.task.tk_ds101.DSFactory;

/**
 * 扫描数据源的准备情况
 * 
 * @author hubaiqing
 * @version 1.0
 */

@Component("TK_DTS101")
public class TK_DTS101 extends BaseTask {
	
	private static final Logger logger = LoggerFactory.getLogger(TK_DTS101.class);

	
	
	public TK_DTS101() {
		super();
	}

	public TK_DTS101(ApplicationContext ac,MC00_task_fact mC00_task_fact) {
		super(ac,mC00_task_fact);
	}

	/**
	 * 执行数据源准备情况扫描任务
	 */
	public boolean calTask() throws Exception{
		
		boolean isSuccess = false;
				
		DSFactory dsFactory = new DSFactory();
		
		BaseDS baseDS = dsFactory.getDSInstance(this.getAc(),this.getMC00_task_fact());
		
		isSuccess = baseDS.run();
		
		return isSuccess;
	}
	
	
	
	
}