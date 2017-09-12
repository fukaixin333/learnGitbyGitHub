package com.citic.server.gf.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gf.task.taskBo.KzqqBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 处理控制单处理
 */
public class TK_ESGF_CL02 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESGF_CL02.class);
	
	public TK_ESGF_CL02(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
	}
	
	public boolean calTask() throws Exception {
		try {
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			KzqqBo kzqqBo = new KzqqBo(this.getAc());
			//删除插入的表
			String ccxh = mc21_task_fact.getTaskobj();
			kzqqBo.delKzqqInfo(mc21_task_fact.getBdhm(), ccxh);
			// 处理高法控制任务单
			kzqqBo.handleControlData(mc21_task_fact);
		} catch (Exception e) {
			logger.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}
		
		return true;
	}
}