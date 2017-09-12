package com.citic.server.cbrc.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.cbrc.task.taskBo.Cxqq_CLBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;

public class TK_ESYJ_CL02 extends NBaseTask{
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESYJ_CL02.class);

	public TK_ESYJ_CL02(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		super(_ac, mC21_task_fact);
	}


	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			
			Cxqq_CLBo cgcx_main_backBo = new Cxqq_CLBo(this.getAc());
			// 处理动态查询及继续动态查询
			cgcx_main_backBo.handleQueryResponse_dtcx(mc21_task_fact);
			
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}

		return isSucc;
	}

}
