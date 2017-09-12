package com.citic.server.gf.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gf.task.taskBo.KzqqBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 反馈控制任务单
 * 
 * @author
 * @version 1.0
 */

public class TK_ESGF_FK02 extends NBaseTask {
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(TK_ESGF_FK02.class);
	
	public TK_ESGF_FK02(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
	}
	
	@Override
	public boolean calTask() throws Exception {
		try {
			KzqqBo kzqqBo = new KzqqBo(this.getAc());
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			
			//查询控制任务单数据并生成反馈报文
			kzqqBo.FeedBackControl(mc21_task_fact);
		} catch (Exception e) {
			LOGGER.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}
		
		return true;
	}
}