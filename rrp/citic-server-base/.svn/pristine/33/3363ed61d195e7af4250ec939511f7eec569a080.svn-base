package com.citic.server.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.task.tk_etl101.BaseFile;
import com.citic.server.service.task.tk_etl101.FileFactory;

/**
 * 从数据源获取数据（数据文件)到本地文件
 * @author hubaiqing
 * @version 1.0
 */

@Component("TK_ETL101")
public class TK_ETL101 extends BaseTask {
	
	private static final Logger logger = LoggerFactory.getLogger(TK_ETL101.class);

	public TK_ETL101(ApplicationContext ac,MC00_task_fact mC00_task_fact) {
		super(ac,mC00_task_fact);
	}
	

	public TK_ETL101() {
		super();
	}


	/**
	 * 执行数据源准备情况扫描任务
	 */
	public boolean calTask() throws Exception{
		
		boolean isSuccess = false;
				
		FileFactory fileFactory = new FileFactory();
		
		BaseFile baseFile = fileFactory.getTaskInstance(this.getAc(),this.getMC00_task_fact());
		
		isSuccess = baseFile.run();
		
		return isSuccess;
	}
	
	
	
	
}