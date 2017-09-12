package com.citic.server.gf.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gf.task.taskBo.CxqqBo;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 反馈查询任务单
 * 
 * @author
 * @version 1.0
 */

public class TK_ESGF_FK01 extends NBaseTask {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESGF_FK01.class);
	
	public TK_ESGF_FK01(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
	}
	
	public boolean calTask() throws Exception {
		try {
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			CxqqBo cxqqBo = new CxqqBo(this.getAc());
			cxqqBo.feedBackQuery(mc21_task_fact); // 查询任务单数据并生成反馈报文
		} catch (DataOperateException e) {
			logger.error("反馈司法查询请求失败 - BDHM=[{}]: {}", e.getCode(), e.getMessage());
			return false;
		} catch (Exception e) {
			logger.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}
		
		return true;
	}
}