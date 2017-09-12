package com.citic.server.gdjg.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjg.task.taskBo.Cxqq_FKBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

public class TK_ESJG_FK08 extends NBaseTask{
private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJG_FK08.class);
	
	public TK_ESJG_FK08(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		super(_ac, mC21_task_fact);
	}
	
	@Override
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			Cxqq_FKBo fkBo = new Cxqq_FKBo(this.getAc());
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			
			//查询任务单数据并生成反馈报文
			fkBo.FeedBackQuery08(mc21_task_fact);
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
}
