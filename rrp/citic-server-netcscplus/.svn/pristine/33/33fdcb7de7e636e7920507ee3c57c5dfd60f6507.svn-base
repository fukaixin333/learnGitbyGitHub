package com.citic.server.dx.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.task.taskBo.Q_main_backBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 客户全账号查询反馈任务
 * 
 * @author
 * @version 1.0
 */

public class TK_ESDX_CL05 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_CL05.class);

	private CacheService cacheService;

	public TK_ESDX_CL05(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		cacheService = (CacheService) ac.getBean("cacheService");
	}

	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			Q_main_backBo q_main_backBo = new Q_main_backBo(this.getAc());
			
			// 处理客户全账户查询任务
			q_main_backBo.handleQueryResponse100309(mc21_task_fact);
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}

		return isSucc;
	}

}