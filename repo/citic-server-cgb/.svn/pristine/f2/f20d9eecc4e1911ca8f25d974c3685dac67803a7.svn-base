package com.citic.server.gdjc.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjc.task.taskBo.Cxqq_CLBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;

public class TK_ESJJ_CL01 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJJ_CL01.class);
	// private JdbcTemplate jdbcTemplate = null;

	private CacheService cacheService;

	public TK_ESJJ_CL01(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		super(_ac, mC21_task_fact);
		cacheService = (CacheService) _ac.getBean("cacheService");
	}

	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			Cxqq_CLBo clBo = new Cxqq_CLBo(this.getAc());

			// 1.删除请求单下的数据
			clBo.delCxqqInfo01(mc21_task_fact);

			// 2.处理存款登记接口
			String docno = clBo.handleQueryResponse01(mc21_task_fact);

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
