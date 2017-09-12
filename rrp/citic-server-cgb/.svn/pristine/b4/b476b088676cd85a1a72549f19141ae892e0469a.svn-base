package com.citic.server.jsga.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.jsga.domain.Br40_cxqq;
import com.citic.server.jsga.domain.Br40_cxqq_back;
import com.citic.server.jsga.domain.Br40_cxqq_back_pz;
import com.citic.server.jsga.domain.Br40_cxqq_back_pz_attach;
import com.citic.server.jsga.domain.Br40_cxqq_mx;
import com.citic.server.jsga.domain.Br42_msg;
import com.citic.server.jsga.domain.Br42_packet;
import com.citic.server.jsga.domain.JSGA_ReturnReceipt;
import com.citic.server.jsga.domain.Datasend_log;
import com.citic.server.jsga.domain.request.JSGA_ControlRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_FeedbackRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Account;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Customer;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Measure;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Priority;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_SubAccount;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Transaction;
import com.citic.server.jsga.domain.request.JSGA_QueryScanRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_RequestDhdj_City;
import com.citic.server.jsga.domain.request.JSGA_RequestGzdj_AccountRule;
import com.citic.server.jsga.domain.request.JSGA_RequestWddj_Branch;
import com.citic.server.service.domain.MC21_task_fact;



public interface MM40_cxqq_jsgaMapper {
	
	/** 删除BR40_CXQQ查询请求单表 */
	void delBr40_cxqq(Br40_cxqq br40_cxqq);
	
	/** 插入BR40_CXQQ查询请求主表 */
	void insertBr40_cxqq(Br40_cxqq br40_cxqq);
	
	/** 删除br40_cxqq_mx 查询请求主体信息 */
	void delBr40_cxqq_mx(Br40_cxqq br40_cxqq);
	
	// void delBr40_cxqq_mx(QueryFormResponse_Main querymain_Cg);
	
	/** 请求单主体信息 */
	void insertBr40_cxqq_mx(Br40_cxqq_mx qqmx);
	
	// void insertBr40_cxqq_mx(QueryFormResponse_Main querymain_Cg);
	
	/** 删除查询请求反馈主体表 */
	void delBr40_cxqq_back(Br40_cxqq br40_cxqq);
	
	/** 插入查询请求反馈主体表 */
	void insertBr40_cxqq_back(Br40_cxqq_back br40_cxqq_back);
	
	void updateBr40_cxqq_back(Br40_cxqq_back br40_cxqq_back);
	
	void updateBr40_cxqq_back1(Br40_cxqq_back br40_cxqq_back);
	
	void updateBr40_cxqq(Br40_cxqq br40_cxqq);
	
	/** 删除Br40_wh_attach附件表 */
	void delBr40_wh_attach(Br40_cxqq br40_cxqq);
	
	/** 插入附件表 */
	void insertBr40_wh_attach(Br40_cxqq br40_cxqq);
	
	void insertBr40_cxqq_acct_dynamic_js(Br40_cxqq br40_cxqq);
	
	void delBr40_cxqq_acct_dynamic_js(Br40_cxqq br40_cxqq);
	
	void updateBr40_cxqq_acct_dynamic_js(Br40_cxqq_mx br40_cxqq_mx);
	
	void updateBr40_cxqq_acct_dynamic_jc(Br40_cxqq_mx br40_cxqq_mx);
	
	/** 按条件 查询BR40_CXQQ查询请求内容主体表 */
	Br40_cxqq selectBr40_cxqqByVo(Br40_cxqq br40_cxqq);// 请求单标识
	
	/** 按条件 查询BR40_CXQQ_BACK查询请求反馈主体表 */
	Br40_cxqq_back selectBr40_cxqq_backByVo(Br40_cxqq br40_cxqq);
	
	List<Br40_cxqq_back> selectBr40_cxqq_backByList(Br40_cxqq br40_cxqq);
	
	/** 按条件 查询BR40_CXQQ_MX查询请求主体信息 */
	Br40_cxqq_mx selectBr40_cxqq_mxByVo(Br40_cxqq br40_cxqq);
	
	/** 通过证件号码获取客户信息 */
	JSGA_QueryRequest_Customer getPartyMsg_IdNum(Br40_cxqq_mx br40_cxqq_mx);
	
	/** 通过卡号获取客户信息 */
	JSGA_QueryRequest_Customer getPartyMsg_CardId(Br40_cxqq_mx br40_cxqq_mx);
	
	/** 通过账号获取客户信息 */
	JSGA_QueryRequest_Customer getPartyMsg_AcctId(Br40_cxqq_mx br40_cxqq_mx);
	
	/** 通过客户号获取个人账户信息 */
	List<JSGA_QueryRequest_Account> getLocalPrivateAcctInfo(String partyid);
	
	/** 通过客户号获取对公账户信息 */
	List<JSGA_QueryRequest_Account> getLocalPublicAcctInfo(String partyid);
	
	/** 插入客户信息 */
	void insertPartyInfo(JSGA_QueryRequest_Customer _queryRequest_party);
	
	/** 插入账户信息 */
	void insertAcctInfo(JSGA_QueryRequest_Account _queryRequest_acct);
	
	/** 插入强制措施信息 */
	void insertBr40_cxqq_back_acct_qzcs(Br40_cxqq_mx br40_cxqq_mx);
	
	void insertBr40_cxqq_back_acct_qzcs1(JSGA_QueryRequest_Measure queryRequest_Measure);
	
	/** 插入子账户信息 */
	void insertBr40_cxqq_back_acct_sub(Br40_cxqq_mx br40_cxqq_mx);
	
	void insertBr40_cxqq_back_acct_sub1(JSGA_QueryRequest_SubAccount queryRequest_SubAccount);
	
	/** 插入共享权优先权信息 */
	void insertBr40_cxqq_back_acct_ql(Br40_cxqq_mx br40_cxqq_mx);
	
	void insertBr40_cxqq_back_acct_ql1(JSGA_QueryRequest_Priority queryRequest_Priority);
	
	/** 获取交易流水列表 */
	List<JSGA_QueryRequest_Transaction> getAcct_TransList(Br40_cxqq_mx br40_cxqq_mx);
	
	/** 插入交易流水表 */
	public void insertBr40_cxqq_back_trans(List<JSGA_QueryRequest_Transaction> transactionList);
	
	/** 获取对公客户法人信息 */
	JSGA_QueryRequest_Customer getCorpPartyInfo(String partyid);
	
	JSGA_QueryRequest_Customer selectBr40_cxqq_back_party(Br40_cxqq_mx br40_cxqq_mx);
	
	List<JSGA_QueryRequest_Account> selectBr40_cxqq_back_acct(Br40_cxqq_mx br40_cxqq_mx);
	
	List<JSGA_QueryRequest_Measure> selectBr40_cxqq_back_acct_qzcs(Br40_cxqq_mx br40_cxqq_mx);
	
	List<JSGA_QueryRequest_Priority> selectBr40_cxqq_back_acct_ql(Br40_cxqq_mx br40_cxqq_mx);
	
	List<JSGA_QueryRequest_SubAccount> selectBr40_cxqq_back_acct_sub(Br40_cxqq_mx br40_cxqq_mx);
	
	List<JSGA_QueryRequest_Transaction> selectBr40_cxqq_back_trans(Br40_cxqq_mx br40_cxqq_mx);
	
	List<JSGA_QueryRequest_Transaction> selectBr40_cxqq_back_trans_dt(Br40_cxqq_back cxqqback);
	
	List<JSGA_ControlRequest_Record> selectBr40_cxqq_back_trans_dtList(Br40_cxqq_back cxqqback);
	
	/** 删除BR40_CXQQ查询请求单表 */
	void delBr40_cxqq_back_acct(Br40_cxqq br40_cxqq);
	
	void delBr40_cxqq_back_acct_qzcs(Br40_cxqq br40_cxqq);
	
	void delBr40_cxqq_back_acct_ql(Br40_cxqq br40_cxqq);
	
	void delBr40_cxqq_back_acct_sub(Br40_cxqq br40_cxqq);
	
	void delBr40_cxqq_back_trans(Br40_cxqq br40_cxqq);
	
	void delBr40_cxqq_back_party(Br40_cxqq br40_cxqq);
	
	void updateBr40_cxqq_back_trans(JSGA_QueryRequest_Transaction tran);
	
	String selectBr40_cxqq_backByMaxSeq(Br40_cxqq_mx br40_cxqq_mx);
	
	//List<LiInfoFormRequest_WsInfo> selectBr40_wh_attachByVo(Br40_cxqq br40_cxqq);
	
	void delBr40_receipt(JSGA_ReturnReceipt message);
	
	void insertBr40_receipt(JSGA_ReturnReceipt message);
	
	void updateBr40_cxqqStatus(JSGA_ReturnReceipt message);
	
	// 操作文书
	void delBr40_cxqq_hzws(Br40_cxqq br40_cxqq);
	
	//	void insertBr40_cxqq_hzws(LiInfoFormRequest_WsInfo wsinfo);
	
	List<JSGA_FeedbackRequest_Record> selectBr40_cxqq_hzwsList(Br40_cxqq br40_cxqq);
	
	Integer getBr40_cxqq_backCount(Br40_cxqq_back br40_cxqq_back);
	
	List<Br40_cxqq_mx> getBr40_cxqq_mxList(Br40_cxqq br40_cxqq);
	
	void delBr42_msg(Br42_msg msg);
	
	void insertBr42_msg(Br42_msg msg);
	
	void delBr42_packet(Br42_packet packet);
	
	void insertBr42_packet(Br42_packet packet);
	
	Br42_packet getBr42_packetbyFileNameCount(String zipFileName);
	
	//判断Br40_cxqq_back査询请求表中 状态是否只为6.已生成报文
	Integer isPacketCount(Br40_cxqq br40_cxqq);
	
	Integer isPzPacketCount(Br40_cxqq br40_cxqq);
	
	Integer isTaskCount(Br40_cxqq_back br40_cxqq_back);
	
	List<Br42_msg> getBr42_msg(Br42_packet packet);
	
	List<JSGA_RequestDhdj_City> getBr40_city_noList(MC21_task_fact mc21_task_fact);
	
	List<JSGA_RequestGzdj_AccountRule> getBr40_acct_ruleList(MC21_task_fact mc21_task_fact);
	
	List<JSGA_RequestWddj_Branch> getBr40_branch_regList(MC21_task_fact mc21_task_fact);
	
	String getLastsendts(MC21_task_fact mc21_task_fact);
	
	void updateBr40_basedata_send(MC21_task_fact mc21_task_fact);
	
	void insertBr40_datasend_log(Datasend_log log);
	
	//反馈客户数
	Integer getFkkhs(Br40_cxqq br40_cxqq);
	
	//反馈账户数  
	Integer getFkzhs(Br40_cxqq br40_cxqq);
	
	//反馈交易明细数  
	Integer getFkjymxs(Br40_cxqq br40_cxqq);
	
	public List<String> selBr40_trans_infoList(@Param("qqdbs") String qqdbs, @Param("rwlsh") String rwlsh, @Param("tasktype") String tasktype, @Param("starttime") String starttime, @Param("endtime") String endtime);
	
	/** 通过客户号获取个人账户信息 */
	List<JSGA_QueryRequest_Account> getLocalCardInfo(String partyid);
	
	/** 通过账卡号账户信息 */
	List<JSGA_QueryRequest_Account> getLocalCardInfo_byAcctNo(Br40_cxqq_mx br40_cxqq_mx);
	
	/** 通过账卡号获取对公账户信息 */
	List<JSGA_QueryRequest_Account> getLocalPublicAcctInfo_byAcctNo(String cxzh);
	
	/** 通过客户号获取个人子账户信息 */
	List<JSGA_QueryRequest_SubAccount> getSubAccountList(String partyid);
	
	/** 通过客户号获取个人子账户信息 */
	List<JSGA_QueryRequest_SubAccount> getSubAccountList_byAcctNo(String cxzh);
	
	void insertBr40_cxqq_back_acct_qzcs2(JSGA_QueryRequest_Measure queryRequest_Measure);
	
	List<JSGA_QueryRequest_Measure> getBb11_freeze(Br40_cxqq_mx br40_cxqq_mx);
	
	//判断是否卡号
	Integer isCardNumCount(String zh);
	
	/** 按条件 查询BR40_CXQQ_MX查询请求主体信息 */
	Br40_cxqq_mx selectBr40_cxqq_mxByOriVo(Br40_cxqq_mx br40_cxqq_mx);
	
	void delBr40_cxqq_pz(Br40_cxqq br40_cxqq);
	
	void updateBr40_cxqq_pz(Br40_cxqq br40_cxqq);
	
	void insertBr40_cxqq_pz(Br40_cxqq_back_pz br40_cxqq_back_pz);
	
	List<JSGA_QueryScanRequest_Record> selBr40_cxqq_back_pzList(Br40_cxqq br40_cxqq);
	
	List<JSGA_QueryScanRequest_Record> selBr40_cxqq_back_pz5List(Br40_cxqq br40_cxqq);
	
	Br40_cxqq_back_pz  queryBr40_cxqq_back_pz(Br40_cxqq br40_cxqq);
	
	void delBr40_cxqq_back_pz_attach(Br40_cxqq br40_cxqq);
	
	void updateBr40_cxqq_back_pz_attach(Br40_cxqq br40_cxqq);
	
	List<Br40_cxqq_back_pz_attach> selBr40_cxqq_back_pzAttachList(Br40_cxqq br40_cxqq);
}
