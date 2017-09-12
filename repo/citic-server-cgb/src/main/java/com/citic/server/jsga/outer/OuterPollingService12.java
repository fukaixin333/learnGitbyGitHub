package com.citic.server.jsga.outer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.jsga.domain.Br40_cxqq;
import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.jsga.mapper.JSGA_OuterPollingTaskMapper;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;

/**
 * 事务处理CBRC轮训任务的数据库操作
 * 
 * @author liuxuanfei
 * @date 2016年9月18日 下午8:43:19
 */
@Service("outerPollingService12")
public class OuterPollingService12 {
	
	@Autowired
	private JSGA_OuterPollingTaskMapper mapper;
	
	@Transactional
	public void transaction(String qqdbs, String taskType, List<MC20_Task_Fact1> taskFactList, List<MC20_task_msg> taskMsgList, List<MC20_WS> wsList, Br40_cxqq br40_cxqq, Br41_kzqq br41_kzqq) {
		// 删除相关数据
		mapper.deleteMC20_WS(qqdbs, taskType);
		mapper.delBr40_wh_attach(qqdbs, taskType);
		if (br40_cxqq != null) {
			mapper.delBr40_cxqq(qqdbs, taskType);
		}
		if (br41_kzqq != null) {
			mapper.delBr41_kzqq(qqdbs, taskType);
		}
		
		// 添加相关数据
		mapper.insertMC20_Task_Fact1(taskFactList);
		mapper.insertMC20_Task_Msg(taskMsgList);
		if (wsList != null) {
			mapper.insertMC20_WS(wsList);
			mapper.insertBr40_wh_attach(wsList);
		}
		
		if (br40_cxqq != null) {
			mapper.insertBr40_cxqq(br40_cxqq);
		}
		if (br41_kzqq != null) {
			mapper.insertBr41_kzqq(br41_kzqq);
		}
	}
}
