package com.citic.server.jsga.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.jsga.task.taskBo.Cxqq_CLBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

public class TK_ESJS_CL01 extends NBaseTask{
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJS_CL01.class);

	public TK_ESJS_CL01(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		super(_ac, mC21_task_fact);
	
	}


	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			Cxqq_CLBo clBo = new Cxqq_CLBo(this.getAc());
			
			//1.删除请求单下的数据
			clBo.delCxqqInfo(mc21_task_fact);
			
			// 处理常规查询
			clBo.handleQueryResponse(mc21_task_fact);
			
			
		} catch (Exception e) {
			logger.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}

		return isSucc;
	}

}
