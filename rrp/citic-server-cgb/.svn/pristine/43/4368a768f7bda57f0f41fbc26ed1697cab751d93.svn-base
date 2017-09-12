package com.citic.server.jsga.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.jsga.domain.Br40_cxqq;
import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;


public interface JSGA_OuterPollingTaskMapper {
	
	public void insertMC20_Task_Fact1(List<MC20_Task_Fact1> taskFactList);
	
	public void insertMC20_Task_Msg(List<MC20_task_msg> taskMsgList);
	
	public void insertMC20_WS(List<MC20_WS> wsList);
	
	public void insertBr40_cxqq(Br40_cxqq br40_cxqq);
	
	public void insertBr41_kzqq(Br41_kzqq br41_kzqq);
	
	public void insertBr40_wh_attach(List<MC20_WS> wsList);
	
	public void deleteMC20_WS(@Param("bdhm") String qqdbs, @Param("tasktype") String tasktype);
	
	public void delBr40_wh_attach(@Param("qqdbs") String qqdbs, @Param("tasktype") String tasktype);
	
	public void delBr40_cxqq(@Param("qqdbs") String qqdbs, @Param("tasktype") String tasktype);
	
	public void delBr41_kzqq(@Param("qqdbs") String qqdbs, @Param("tasktype") String tasktype);
	
	//////////////////////////////////////////////////////////////
	public List<Br40_cxqq> queryBr40_cxqq_acct_dynamic_js(@Param("tasktype") String tasktype, @Param("datatime") String datatime);
	
	public void updateBr40_cxqq_acct_dynamic_js(@Param("tasktype") String tasktype, @Param("aid") String aid, @Param("datatime") String datatime, @Param("pollinglock") String pollinglock);
	
	public void closeBr40_cxqq_acct_dynamic_js(String datatime);
	
	///////////////////////////////////////////////////////////////
	
	public void updateBr41_cxqq_back_dynamic(@Param("datatime") String datatime);
}
