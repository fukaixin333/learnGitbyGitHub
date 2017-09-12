package com.citic.server.gf.task;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gf.task.taskBo.KzqqBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.StrUtils;

/**
 * 解析控制任务单
 * 
 * @author
 * @version 1.0
 */

public class TK_ESGF_JS02 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESGF_JS02.class);
	//private JdbcTemplate jdbcTemplate = null;
	
	private CacheService cacheService;
	
	private String root = "";
	
	public TK_ESGF_JS02(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		//ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean("applicationCFG");
		//jdbcTemplate = (JdbcTemplate) this.getAc().getBean(applicationCFG.getJdbctemplate_business());
		cacheService = (CacheService) ac.getBean("cacheService");
		//获取系统参数
		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		root = StrUtils.null2String((String) sysParaMap.get("2"));
	}
	
	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			
			MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			String bdhm = mc21_task_fact.getBdhm();
			KzqqBo kzqqBo = new KzqqBo(this.getAc());
			//删除请求单号下的数据
			kzqqBo.delKzqqBw(bdhm);
			
			//1.取出文件地址
			ArrayList<MC20_task_msg> taskMsgList = common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
			MC20_task_msg taskmag = new MC20_task_msg();
			if (taskMsgList != null && taskMsgList.size() > 0) {
				taskmag = taskMsgList.get(0);
			}
			
			//2.解析请求报文
			kzqqBo.analyKzqqBw(root, taskmag, mc21_task_fact);
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
	
}