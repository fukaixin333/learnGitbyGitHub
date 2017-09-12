package com.citic.server.gf.task;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gf.task.taskBo.CxqqBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 解析查询任务单
 * 
 * @author
 * @version 1.0
 */
public class TK_ESGF_JS01 extends NBaseTask {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESGF_JS01.class);
	
	public TK_ESGF_JS01(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
	}
	
	public boolean calTask() throws Exception {
		try {
			MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			String bdhm = mc21_task_fact.getBdhm();
			CxqqBo cxqqBo = new CxqqBo(this.getAc());
			
			// 2.取出文件地址
			ArrayList<MC20_task_msg> taskMsgList = common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
			if (taskMsgList == null || taskMsgList.size() > 1) {
				logger.warn("MC21_TASK_FACT1 存在重复请求数据 - [{}]", mc21_task_fact.getTaskkey());
				return false;
			}
			
			// 1.删除查询请求数据
			cxqqBo.delCxqqBw(bdhm);
			
			// 3.解析请求报文
			cxqqBo.analyCxqqBw(taskMsgList.get(0), mc21_task_fact);
		} catch (Exception e) {
			logger.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}
		return true;
	}
}