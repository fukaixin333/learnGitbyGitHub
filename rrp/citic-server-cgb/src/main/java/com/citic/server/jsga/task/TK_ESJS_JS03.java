package com.citic.server.jsga.task;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.jsga.domain.Br40_cxqq;
import com.citic.server.jsga.domain.response.JSGA_ControlResponse;
import com.citic.server.jsga.task.taskBo.Cxqq_JSBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;

/**
 * 解析账户动态查询任务
 * 
 * @author
 * @version 1.0
 */

public class TK_ESJS_JS03 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJS_JS03.class);
	//private JdbcTemplate jdbcTemplate = null;
	
	private CacheService cacheService;
	
	private String root = "";
	
	public TK_ESJS_JS03(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
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
			
			String tasktype = mc21_task_fact.getTasktype();
			
			String qqdbs = mc21_task_fact.getBdhm().substring(21);
			Cxqq_JSBo jsBo = new Cxqq_JSBo(this.getAc());
			
			// 不同监管不同代码使用不同的绑定文件
			String bindingName = "binding_jsga_control_resp";
			
			//1.取出文件地址
			ArrayList<MC20_task_msg> taskMsgList = common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
			MC20_task_msg taskmag = new MC20_task_msg();
			if (taskMsgList != null && taskMsgList.size() > 0) {
				taskmag = taskMsgList.get(0);
			}
			
			//2.解析请求报文
			String flie_path = root + taskmag.getMsgpath();
			JSGA_ControlResponse query = (JSGA_ControlResponse) CommonUtils.unmarshallUTF8Document(JSGA_ControlResponse.class, bindingName, flie_path);
			
			//3.删除请求单号下的数据
			Br40_cxqq br40_cxqq = new Br40_cxqq();
			br40_cxqq.setQqdbs(qqdbs);//请求单标识
			br40_cxqq.setTasktype(tasktype);//监管类别
			jsBo.delQureyResponse(br40_cxqq);
			
			//4.插入查询请求单主表
			jsBo.insertBr40_cxqq_dt(query, mc21_task_fact, taskmag);
			
			//5.插入文书/警官证件信息
			jsBo.insertBr40_wh_attach(br40_cxqq);
			
		} catch (Exception e) {
			logger.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}
		
		return isSucc;
	}
	
}