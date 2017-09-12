package com.citic.server.shpsb.outer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.shpsb.mapper.Shpsb_outerListenerMapper;

/**
 * 事务处理轮训任务的数据库操作
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 下午5:50:25
 */
@Service("outerPollingServiceShpsb")
public class OuterPollingServiceShpsb {
	
	@Autowired
	private Shpsb_outerListenerMapper mapper;
	
	@Transactional
	public void transaction(List<MC20_task_msg> taskMsgList, List<MC20_Task_Fact1> taskFactList) {
		mapper.insertMC20_Task_Msg(taskMsgList);
		mapper.insertMC20_Task_Fact1(taskFactList);
	}
}
