package com.citic.server.net.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.dx.domain.Br24_bas_info;
import com.citic.server.dx.domain.Br24_q_Main;
import com.citic.server.gf.domain.Br32_packet;
import com.citic.server.gf.domain.MC20_GZZ;
import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.gf.domain.request.RollbackRequest_Htxx;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * @author Liu Xuanfei
 * @date 2016年3月29日 下午3:35:14
 */
public interface PollingTaskMapper {
	
	public void insertMC20_Task_Msg(MC20_task_msg taskMessage);
	
	public void insertMC20_Task_Fact1(MC20_Task_Fact1 taskFact);
	
	public void insertBR32_Packet(Br32_packet packet);
	
	public List<Br32_packet> selectUploadPacket(String status_cd);
	
	public List<String> selectMP02_REP_ORG();
	
	public List<String> selectOrganByRepType(@Param("reptype") String repType);
	
	public void updatePacketStatus(String packetkey);
	
	/**
	 * 查询非“已完成”状态的任务数据
	 * 
	 * @param taskType
	 * @return
	 */
	public List<MC20_Task_Fact1> queryMC20_Task_Fact1(@Param("tasktype") String taskType);
	
	/**
	 * 查询Task处理映射表
	 * 
	 * @param taskType
	 * @return
	 */
	public List<MC21_task> queryMC21_Task(@Param("tasktype") String taskType);
	
	public List<Br24_q_Main> queryBr24_Q_Acct_Dynamic_Main(@Param("status") String status, @Param("datatime") String datatime);
	
	public void updateBr24_Q_Acct_Dynamic_Main(@Param("aid") String aid, @Param("datatime") String datatime);
	
	public void insertBr24_Q_Main_Back(Br24_bas_info back);
	
	public void insertMC21_Task_Fact3(MC21_task_fact taskFact);
	
	public void closeBr24_Q_Acct_Dynamic_Main(String datatime);
	
	public void insertMC20_GZZ(MC20_GZZ gzz);
	
	public void insertMC20_WS(MC20_WS ws);
	
	public void deleteMC20_WS(MC20_WS ws);
	
	public void deleteMC20_GZZ(MC20_GZZ gzz);
	
	public void insertMC20_WSList(List<MC20_WS> mc20ws);
	
	public void insertMC20_GZZList(List<MC20_GZZ> mc20gzz);
	
	public void insertMC20_Task_Fact1List(List<MC20_Task_Fact1> mc20TaskFact1List);
	
	public void insertMC20_Task_MsgList(List<MC20_task_msg> mc20TaskMsg);
	
	public void deleteBr30_htxx(List<RollbackRequest_Htxx> listHtxx);
	
	public void insertBr30_htxx(List<RollbackRequest_Htxx> listHtxx);
	
	public List<String> queryReceivedTaskKeyForCaching01();
}
