package com.citic.server.cbrc.task;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.cbrc.domain.response.CBRC_QueryResponse;
import com.citic.server.cbrc.task.taskBo.Cxqq_JSBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;
import com.citic.server.cbrc.domain.Br40_cxqq;

/**
 * 解析常规查询请求报文
 * 
 * @author
 * @version 1.0
 */

public class TK_ESYJ_JS01 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESYJ_JS01.class);
	private CacheService cacheService;
	
	public TK_ESYJ_JS01(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		cacheService = (CacheService) ac.getBean("cacheService");
	}
	
	/**
	 * 解析常规查询：账户信息、交易明细、账户信息及交易明细查询请求报文
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			Cxqq_JSBo jsBo = new Cxqq_JSBo(this.getAc());
			String dbhm = mc21_task_fact.getBdhm();
			String type = dbhm.substring(0, 4);
			String qqdbs = "";
			String taskType = mc21_task_fact.getTasktype();
			if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(taskType)) {
				qqdbs = mc21_task_fact.getBdhm().substring(12);
			} else {
				qqdbs = mc21_task_fact.getBdhm().substring(21);
			}
			
			String tasktype = mc21_task_fact.getTasktype();
			
			// 不同监管不同代码使用不同的绑定文件
			String bindingName = null;
			if (type.equals("SS05") || type.equals("SS06")) {
				bindingName = "cbrc_queryaccount_resp";
			} else {
				bindingName = "cbrc_query_resp";
			}
			bindingName = bindingName + tasktype;
			
			//1.取出文件地址
			ArrayList<MC20_task_msg> taskMsgList = common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
			//获取系统参数
			HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
			String root = StrUtils.null2String((String) sysParaMap.get("2"));
			MC20_task_msg taskmag = new MC20_task_msg();
			if (taskMsgList != null && taskMsgList.size() > 0) {
				taskmag = taskMsgList.get(0);
			}
			
			//2.解析请求报文
			String flie_path = root + taskmag.getMsgpath();
			
			CBRC_QueryResponse query_cgcx = (CBRC_QueryResponse) CommonUtils.unmarshallUTF8Document(CBRC_QueryResponse.class, bindingName, flie_path);
			//3.删除请求单下的数据
			Br40_cxqq br40_cxqq = new Br40_cxqq();
			br40_cxqq.setQqdbs(qqdbs);//请求单标识
			br40_cxqq.setTasktype(tasktype);//监管类别
			jsBo.delQureyResponse(br40_cxqq);
			
			//4.处理请求报文信息及插入task2
			jsBo.handleCxqqBw(query_cgcx, mc21_task_fact, taskmag);
			
			//5.插入文书/警官证件信息
			//jsBo.insertBr40_wh_attach(br40_cxqq) ;
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		return isSucc;
	}
	
}