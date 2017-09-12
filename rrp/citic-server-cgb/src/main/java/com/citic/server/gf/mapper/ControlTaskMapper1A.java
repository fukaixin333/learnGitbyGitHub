package com.citic.server.gf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.gf.domain.Br32_msg;
import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.gf.domain.Result_Jg;
import com.citic.server.gf.domain.request.ControlRequest_Djxx;
import com.citic.server.gf.domain.request.ControlRequest_Hzxx;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.ControlRequest_Qlxx;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.response.ControlResponse_Kzqq;
import com.citic.server.gf.domain.response.ControlResponse_Kzzh;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/05 21:48:14$
 */
public interface ControlTaskMapper1A {
	public ControlResponse_Kzqq queryControlTaskInfo(@Param("bdhm") String bdhm);
	
	public ControlResponse_Kzzh queryControlTaskDetail(@Param("bdhm") String bdhm, @Param("ccxh") String ccxh);
	
	public List<ControlRequest_Kzxx> queryTaskResultListByBdhm(@Param("bdhm") String bdhm);
	
	public ControlRequest_Kzxx queryOriginalTaskResult(@Param("bdhm") String bdhm, @Param("khzh") String khzh, @Param("glzhhm") String glzhhm);
	
	public Integer queryTodoTaskCount(@Param("bdhm") String bdhm);
	
	public List<ControlRequest_Djxx> queryTaskResultDjxxList(@Param("bdhm") String bdhm, @Param("ccxh") String ccxh);
	
	public List<ControlRequest_Qlxx> queryTaskResultQlxxList(@Param("bdhm") String bdhm, @Param("ccxh") String ccxh);
	
	public List<MC20_WS> queryLegalDocumentInfo(@Param("bdhm") String bdhm, @Param("tasktype") String taskType);
	
	public int updateControlTaskResult(ControlRequest_Kzxx kzxx);
	
	public int updateControlTaskInfo(ControlRequest_Kzxx kzxx);
	
	public int updateControlRequestStatus(@Param("bdhm") String bdhm, @Param("status") String status);
	
	public int insertControlTaskQlxx(QueryRequest_Qlxx qlxx);
	
	public int insertControlTaskDjxx(QueryRequest_Djxx djxx);
	
	public void insertShfeedMessage(Br32_msg message);
	
	public void insertShfeedResult(Result_Jg jg);
	
	public void insertFallbackDocument(ControlRequest_Hzxx hzxx);
	
	public void clearAllQlxxInfo(@Param("bdhm") String bdhm, @Param("ccxh") String ccxh);
	
	public void clearAllDjxxInfo(@Param("bdhm") String bdhm, @Param("ccxh") String ccxh);
	
	public void clearShfeedMessage(@Param("bdhm") String bdhm);
	
	public void clearShfeedResult(String bdhm);
	
	public void clearFallbackDocument(@Param("bdhm") String bdhm);
	
	public String getNextSequence(String sql);
	
	public List<String> queryOriginalCount(@Param("ydjdh") String ydjdh, @Param("kzcs") String kzcs);
	
	public void updateMC21TaskFact2(List<String> bdhmList);
	
	public void deleteMc21Task2Status(List<String> bdhmList);

	public void updateUnfreezeStatus(String bdhm);
}
