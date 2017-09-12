package com.citic.server.jsga.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.jsga.task.taskBo.Cxqq_FKBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 反馈账户动态解除
 * 
 * @author hxj
 * @version 1.0
 */

public class TK_ESJS_FK03 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJS_FK03.class);
	
	public TK_ESJS_FK03(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		
	}
	
	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
				Cxqq_FKBo fkBo = new Cxqq_FKBo(this.getAc());
				MC21_task_fact mc21_task_fact = this.getMC21_task_fact();

				//查询任务单数据并生成反馈报文
				fkBo.FeedBackQuery_dtjc(mc21_task_fact);
		} catch (Exception e) {
			logger.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}
		
		return isSucc;
	}
	
}