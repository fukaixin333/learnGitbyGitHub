package com.citic.server.cbrc.task;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.cbrc.domain.Br41_kzqq;
import com.citic.server.cbrc.domain.response.CBRC_StopPaymentResponse;
import com.citic.server.cbrc.task.taskBo.Kzqq_JSBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;

/**
 * 解析止付任务
 * 
 * @author
 * @version 1.0
 */

public class TK_ESYJ_JS05 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESYJ_JS05.class);
	//private JdbcTemplate jdbcTemplate = null;
	
	private CacheService cacheService;
	
	private String root = "";
	
	public TK_ESYJ_JS05(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
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
			Kzqq_JSBo jsBo = new Kzqq_JSBo(this.getAc());
			String qqdbs = "";
			if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(mc21_task_fact.getTasktype())) {
				qqdbs = mc21_task_fact.getBdhm().substring(12);
			}else{
				qqdbs = mc21_task_fact.getBdhm().substring(21);
			}
			// 不同监管不同代码使用不同的绑定文件
			String bindingName = "cbrc_stoppayment_resp" + tasktype;
			
			//1.取出文件地址
			ArrayList<MC20_task_msg> taskMsgList = common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
			MC20_task_msg taskmag = new MC20_task_msg();
			if (taskMsgList != null && taskMsgList.size() > 0) {
				taskmag = taskMsgList.get(0);
			}
			
			//2.解析请求报文
			String flie_path = root + taskmag.getMsgpath();
			CBRC_StopPaymentResponse query = (CBRC_StopPaymentResponse) CommonUtils.unmarshallUTF8Document(CBRC_StopPaymentResponse.class, bindingName, flie_path);
			
			//3.删除请求单号下的数据
			Br41_kzqq br41_kzqq = new Br41_kzqq();
			br41_kzqq.setQqdbs(qqdbs);//请求单标识
			br41_kzqq.setTasktype(tasktype);//监管类别
			jsBo.delStopPayResponse(br41_kzqq);
			
			//4.插入查询请求单主表及task2
			jsBo.insertBr41_kzqq_zf(query, mc21_task_fact);
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
	
}