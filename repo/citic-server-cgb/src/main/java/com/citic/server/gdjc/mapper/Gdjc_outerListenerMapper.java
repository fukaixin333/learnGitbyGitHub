package com.citic.server.gdjc.mapper;

import java.util.ArrayList;

import com.citic.server.gdjc.domain.Br52_packet;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;

/**
 * 
 * @author yinxiong
 *
 */
public interface Gdjc_outerListenerMapper {
	
	/** 插入任务信息表 */
	public void insertMC20_Task_Msg(MC20_task_msg taskMessage);
	
	/** 插入任务清单表 */
	public void insertMC20_Task_Fact1(MC20_Task_Fact1 taskFact);
	
	/** 删除数据包信息 */
	void delBr52_packet(Br52_packet packet);
	
	/** 插入数据包信息 */
	void insertBr52_packet(Br52_packet packet);
	
	/** 查询待处理的zip包 */
	public ArrayList<Br52_packet> selectBr52_packet(Br52_packet br52_packet);
	
	/** 更新数据状态 */
	public void updateBr52_packet(Br52_packet br52_packet);
	
	/** 检测数据是否重传过 */
	public Integer selectReTransFlagByDocno(String docno);
	
	//================重启task1任务开始======================
	public void updateMC21_Task_Fact3(MC20_Task_Fact1 taskFact);
	public void deleteMc21_Task3_Status(MC20_Task_Fact1 taskFact);
	//================重启task1任务结束======================
}
