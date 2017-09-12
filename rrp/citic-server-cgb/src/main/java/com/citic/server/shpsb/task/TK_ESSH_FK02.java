package com.citic.server.shpsb.task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.shpsb.task.taskBo.Cxqq_FKBo;

 
/**
 * 持卡资料查询反馈
 * @author  
 * @version 1.0
 */

public class TK_ESSH_FK02 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESSH_FK02.class);

	
	
	
	public TK_ESSH_FK02(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
	}


	public boolean calTask() throws Exception {
		boolean isSucc = true;
	   try {
	
			
				MC21_task_fact mc21_task_fact= this.getMC21_task_fact();
				Cxqq_FKBo clBo=new Cxqq_FKBo(this.getAc());
			   String msgseq=mc21_task_fact.getBdhm();
				//1.删除请求单下的数据
				clBo.delBr54_cxqq_back_msg(msgseq);
				
			
				//2.处理请求报文信息及插入task2
				  clBo.MakeMsgCxqqSH_ckrxx(msgseq);
				
			
	   } catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;			
		}
		return isSucc;
	}


	 

}