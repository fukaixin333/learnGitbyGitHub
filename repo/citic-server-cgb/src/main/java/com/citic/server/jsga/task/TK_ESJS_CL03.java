package com.citic.server.jsga.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.jsga.task.taskBo.Cxqq_CLBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;

public class TK_ESJS_CL03 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJS_CL03.class);
	// private JdbcTemplate jdbcTemplate = null;
	
	private CacheService cacheService;
	
	public TK_ESJS_CL03(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		super(_ac, mC21_task_fact);
		cacheService = (CacheService) _ac.getBean("cacheService");
	}
	
	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			
			Cxqq_CLBo cgcx_clBo = new Cxqq_CLBo(this.getAc());
			// 处理动态查询解除任务
			cgcx_clBo.handleQueryResponse_dtjc(mc21_task_fact);
			
		} catch (Exception e) {
			logger.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}
		
		return isSucc;
	}
	
}
