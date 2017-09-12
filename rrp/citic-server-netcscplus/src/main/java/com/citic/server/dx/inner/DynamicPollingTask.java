package com.citic.server.dx.inner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.dx.domain.Br24_q_Main;
import com.citic.server.dx.inner.service.DynamicsPollingService;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;

/**
 * 动态查询任务
 * 
 * @author Liu Xuanfei
 * @date 2016年4月27日 上午11:41:21
 */
@Service("dynamicPollingTask")
public class DynamicPollingTask extends AbstractPollingTask {
	private final Logger logger = LoggerFactory.getLogger(DynamicPollingTask.class);
	
	@Autowired
	@Qualifier("dynamicsPollingService")
	private DynamicsPollingService dynamicsService;
	
	@Override
	public void executeAction() {
		long li1 = System.currentTimeMillis();
		String currDataTime = Utility.currDateTime19();
		
		// 关闭截止时间到期的任务
		service.closeBr24_Q_Acct_Dynamic_Main(currDataTime);
		
		// 获取所有待处理任务
		List<Br24_q_Main> dynamicsTasks = service.queryBr24_Q_Acct_Dynamic_Main("1", currDataTime);
		
		int fail = 0;
		for (Br24_q_Main task : dynamicsTasks) {
			try {
				dynamicsService.dealDynamicsPollingTask(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		long li2 = System.currentTimeMillis();
		logger.info("轮询动态查询任务完成，耗时[" + (li2 - li1) + "]ms，共计[" + dynamicsTasks.size() + "]个任务，失败[" + fail + "]个。");
	}
	
	@Override
	protected String getTaskType() {
		return null;
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(Keys.INNER_POLLING_TASK_DYNAMICS_PERIOD, "每天|02:00");
	}
}
