package com.citic.server.gdjg.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjg.task.taskBo.Cxqq_CLBo;
import com.citic.server.gdjg.task.taskBo.Kzqq_CLBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;


/**
 * 处理存款冻结申请
 * 
 * @author liuxuanfei
 * @date 2017年5月25日 下午9:24:39
 */

public class TK_ESJG_CL05  extends NBaseTask{
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJG_CL05.class);
	private CacheService cacheService;
	
	public TK_ESJG_CL05(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		super(_ac, mC21_task_fact);
		cacheService = (CacheService) _ac.getBean("cacheService");
	}
	
	@Override
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			Kzqq_CLBo clBo = new Kzqq_CLBo(this.getAc());
			
			//System.out.println(mc21_task_fact.getBdhm());
			// 1.删除请求单下的数据
			clBo.delKzqqInfo05(mc21_task_fact);
			
			// 2.处理存款冻结登记接口
			String docno = clBo.handleQueryResponse05(mc21_task_fact);
			
			// 3.插入任务task3
			clBo.insertMc21TaskFact3(mc21_task_fact, docno);
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
}
