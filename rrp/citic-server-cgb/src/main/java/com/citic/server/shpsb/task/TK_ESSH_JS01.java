package com.citic.server.shpsb.task;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.shpsb.domain.response.ShpsbResponse;
import com.citic.server.shpsb.task.taskBo.Cxqq_JSBo;
import com.citic.server.utils.CommonUtils;

/**
 * 解析存款账户查询
 * 
 * @author
 * @version 1.0
 */

public class TK_ESSH_JS01 extends NBaseTask {
	// private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESSH_JS01.class);
	
	public TK_ESSH_JS01(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
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
			
			//1.取出文件地址
			ArrayList<MC20_task_msg> taskMsgList = common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
			
			MC20_task_msg taskMsg = new MC20_task_msg();
			if (taskMsgList != null && taskMsgList.size() > 0) {
				taskMsg = taskMsgList.get(0);
			}
			//根据任务编号使用相对应的binding文件
			String bindingName = "binding_shpsb_" + taskMsg.getCode();
			//2.解析请求报文
			String flie_path = ServerEnvironment.getFileRootPath() + taskMsg.getMsgpath();
			ShpsbResponse query_ckcx = (ShpsbResponse) CommonUtils.unmarshallUTF8Document(ShpsbResponse.class, bindingName, flie_path);
			
			//3.删除请求单下的数据
			jsBo.delQureyResponse(mc21_task_fact.getBdhm());
			
			//4.处理请求报文信息及插入task2
			jsBo.handleCxqqBw_Ckcx(query_ckcx, mc21_task_fact, taskMsg);
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		return isSucc;
	}
	
}