package com.citic.server.whpsb.mapper;

import java.util.ArrayList;

import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.whpsb.domain.Br51_cxqq;
import com.citic.server.whpsb.domain.Br51_cxqq_back_msg;

/**
 * 
 * @author yinxiong
 *
 */
public interface Whpsb_outerListenerMapper {
	

	/** 插入任务信息表 */
	public void insertMC20_Task_Msg(MC20_task_msg taskMessage);
	
	/** 插入任务清单表 */
	public void insertMC20_Task_Fact1(MC20_Task_Fact1 taskFact);
	
	/** 插入任务清单表 */
	public void insertMC20_WS(MC20_WS ws);
	
	//查询待处理的文件信息
	public ArrayList<Br51_cxqq_back_msg>  selectBr51_cxqq_back_msg();
	//根据msgseq更新Br51_cxqq_back_msg状态
	public void updateBr51_cxqq_back_msg(String msgseq);
	//根据msgseq更新Br51_cxqq状态
	public void updateBr51_cxqq(Br51_cxqq br51_cxqq);
}
