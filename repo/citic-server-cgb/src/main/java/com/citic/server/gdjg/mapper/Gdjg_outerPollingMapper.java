package com.citic.server.gdjg.mapper;

import java.util.ArrayList;

import com.citic.server.gdjg.domain.Br57_packet;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;

/**
 * 广东检察院轮询数据处理dao
 * 
 * @author liuxuanfei
 * @date 2017年5月24日 下午4:31:07
 */

public interface Gdjg_outerPollingMapper {
	/** 插入任务信息表 */
	public void insertMC20_Task_Msg(MC20_task_msg taskMsg);
	
	/** 插入任务清单表 */
	public void insertMC20_Task_Fact1(MC20_Task_Fact1 taskFact);
	
	/** 删除数据包信息 */
	public void delBr57_packet(Br57_packet br57_packet);
	
	/** 插入数据包信息 */
	public void insertBr57_packet(Br57_packet br57_packet);
	
	/** 查询待处理的zip包 */
	public ArrayList<Br57_packet> selectBr57_packet(Br57_packet br57_packet);
	
	/** 更新数据包状态 */
	public void updateBr57_packet(Br57_packet br57_packet);
	
	/** 检测数据是否重传过 */
	public Integer selectReTransFlagByDocno(String docno);
	
	//===================重启task1任务开始======================
	public void updateMC21_Task_Fact3(MC20_Task_Fact1 taskfact);
	
	public void deleteMC21_Task3_Status(MC20_Task_Fact1 taskfact);
	//===================重启task1任务结束======================
	
}
