package com.citic.server.net.mapper;

import java.util.List;

import com.citic.server.dx.domain.Attachment;
import com.citic.server.dx.domain.Br22_Msg;
import com.citic.server.dx.domain.Br22_StopPay;
import com.citic.server.dx.domain.Cs_case;
import com.citic.server.dx.domain.Transaction;
import com.citic.server.dx.domain.request.CaseRequest100401;
import com.citic.server.dx.domain.request.CaseRequest_Transaction;
import com.citic.server.dx.domain.request.SuspiciousRequest100404;
import com.citic.server.dx.domain.request.SuspiciousRequest_Account;
import com.citic.server.dx.domain.request.SuspiciousRequest_Accounts;
import com.citic.server.dx.domain.request.SuspiciousRequest_Transaction;
import com.citic.server.dx.domain.response.CaseResponse100402;

public interface BR22_caseMapper {
	//查询案件举报的案例
	CaseRequest100401 getBr22_Case_ajjb_List(String caseid);
	
	Cs_case getBr22_CaseList(String caseid);
	
	//查询案件的下的交易
	List<SuspiciousRequest_Transaction> getBr22_Case_transByCaseIdList(String caseid);
	
	//查询案件的下的交易
	List<CaseRequest_Transaction> getBr22_PCase_transByCaseIdList(String caseid);
	
	//查询案件的下的卡信息
	List<SuspiciousRequest_Accounts> getBr22_Case_CardByCaseIdList(String caseid);
	
	//查询涉案账户案件的下的主账户信息
	SuspiciousRequest100404 getBr22_CaseMinAcct(String caseid);
	
	//查询案件的下的账户
	List<SuspiciousRequest_Account> getBr22_Case_acctByCaseIdList(String caseid);
	
	List<Attachment> getCaseAttach(String caseid);
	
	void updateCaseStatus(Cs_case cs_case);
	
	void deleteBr22Msg(Br22_Msg br22_msg);
	
	void insertBr22Msg(Br22_Msg br22_msg);
	
	Br22_Msg getBr22_Msg(Br22_Msg br22_msg);
	
	void updateBr22_Stop(Br22_StopPay br22_stoppay);
	
	//删除BR22_caseback单主表
	void delBr22_CASE_BACK(String bdhm);
	
	//插入BR22_caseback表
	void insertBr22_CASE_BACK(CaseResponse100402 query);
	
}
