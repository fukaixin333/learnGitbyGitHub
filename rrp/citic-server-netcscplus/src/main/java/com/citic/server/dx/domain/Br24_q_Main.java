/*
 * =============================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created [2016-02-18]
 * =============================================
 */

package com.citic.server.dx.domain;



/**
 * 下发请求报文-查询类
 * 
 * @author Liu Xuanfei
 * @date 2016年3月28日 下午4:26:35
 */

public class Br24_q_Main extends ResponseMessage {
	private static final long serialVersionUID = -7751773661309587873L;
	
	/** 案件编号 */
	private String casenumber = "";
	/** 案件类型（参照公安机关案件类型标准）特征编号 */
	private String casetype = "";
	/** 原动态查询申请编号 */
	private String originalapplicationid = "";
	/** 紧急程度（01-正常；02-加急） */
	private String emergencylevel = "";
	/** 查询主体类别（1-自然人主体；2-法人主体） */
	private String subjecttype = "";
	/** 查询证照类型代码 01 身份证 02 驾驶证 03 护照 04 军官证 05 士兵证 06 户口本 07 港澳台通行证 99 其他 */
	private String accountcredentialtype = "";
	/** 查询证照号码 */
	private String accountcredentialnumber = "";
	/** 查询主体名称（个人姓名或公司名称） */
	private String accountsubjectname = "";
	/** 查询账户所属银行机构编码 */
	private String bankid = "";
	/** 查询账户所属银行名称 */
	private String bankname = "";
	/** 查询账户名 */
	private String accountname = "";
	/** 查询账卡号 */
	private String accountnumber = "";
	/** 执行/解除命令 (0-解除；1-执行) */
	private String interceptionorderid = "";
	/** 执行截止日期 */
	private String interceptionenddate = "";
	/** 查询内容（01-账户基本信息；02-账户信息（含强制措施、共有权/优先权信息）） */
	private String inquirymode = "";
	/** 交易流水查询起始时间 */
	private String inquiryperiodstart = "";
	/** 交易流水查询截止时间 */
	private String inquiryperiodend = "";
	/** 结果反馈期限（小时） */
	private String feedbacklimit = "";
	/** 查询事由 */
	private String reason = "";
	/** 查询说明 */
	private String remark = "";
	/** 申请时间(yyyyMMddHHmmss) */
	private String applicationtime;
	/** 申请机构编码 */
	private String applicationorgid = "";
	/** 申请机构名称 */
	private String applicationorgname = "";
	/** 经办人证件类型 */
	private String operatoridtype = "";
	/** 经办人证件号 */
	private String operatoridnumber = "";
	/** 经办人姓名 */
	private String operatorname = "";
	/** 经办人电话 */
	private String operatorphonenumber = "";
	/** 协查人证件类型 */
	private String investigatoridtype = "";
	/** 协查人证件号 */
	private String investigatoridnumber = "";
	/** 协查人姓名 */
	private String investigatorname = "";
	private String applicationid = "";

	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	/** 状态 */
	private String status = "";
	/** 归属机构 */
	private String orgkey = "";
	/** 反馈报文路径 */
	private String msgpath = "";
	
	private String qrydt = "";
	
	private String lastpollingtime;
	
	private String cardNumber="";
	
	private String last_up_dt="";
	
	private String recipient_time="";


	public String getRecipient_time() {
		return recipient_time;
	}

	public void setRecipient_time(String recipient_time) {
		this.recipient_time = recipient_time;
	}

	public String getLast_up_dt() {
		return last_up_dt;
	}

	public void setLast_up_dt(String last_up_dt) {
		this.last_up_dt = last_up_dt;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCasenumber() {
		return casenumber;
	}
	
	public void setCasenumber(String casenumber) {
		this.casenumber = casenumber;
	}
	
	public String getCasetype() {
		return casetype;
	}
	
	public void setCasetype(String casetype) {
		this.casetype = casetype;
	}
	
	public String getOriginalapplicationid() {
		return originalapplicationid;
	}
	
	public void setOriginalapplicationid(String originalapplicationid) {
		this.originalapplicationid = originalapplicationid;
	}
	
	public String getEmergencylevel() {
		return emergencylevel;
	}
	
	public void setEmergencylevel(String emergencylevel) {
		this.emergencylevel = emergencylevel;
	}
	
	public String getSubjecttype() {
		return subjecttype;
	}
	
	public void setSubjecttype(String subjecttype) {
		this.subjecttype = subjecttype;
	}
	
	public String getAccountcredentialtype() {
		return accountcredentialtype;
	}
	
	public void setAccountcredentialtype(String accountcredentialtype) {
		this.accountcredentialtype = accountcredentialtype;
	}
	
	public String getAccountcredentialnumber() {
		return accountcredentialnumber;
	}
	
	public void setAccountcredentialnumber(String accountcredentialnumber) {
		this.accountcredentialnumber = accountcredentialnumber;
	}
	
	public String getAccountsubjectname() {
		return accountsubjectname;
	}
	
	public void setAccountsubjectname(String accountsubjectname) {
		this.accountsubjectname = accountsubjectname;
	}
	
	public String getBankid() {
		return bankid;
	}
	
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	
	public String getBankname() {
		return bankname;
	}
	
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	public String getAccountname() {
		return accountname;
	}
	
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	
	public String getAccountnumber() {
		return accountnumber;
	}
	
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
	public String getInterceptionorderid() {
		return interceptionorderid;
	}
	
	public void setInterceptionorderid(String interceptionorderid) {
		this.interceptionorderid = interceptionorderid;
	}
	
	public String getInterceptionenddate() {
		return interceptionenddate;
	}
	
	public void setInterceptionenddate(String interceptionenddate) {
		this.interceptionenddate = interceptionenddate;
	}
	
	public String getInquirymode() {
		return inquirymode;
	}
	
	public void setInquirymode(String inquirymode) {
		this.inquirymode = inquirymode;
	}
	
	public String getInquiryperiodstart() {
		return inquiryperiodstart;
	}
	
	public void setInquiryperiodstart(String inquiryperiodstart) {
		this.inquiryperiodstart = inquiryperiodstart;
	}
	
	public String getInquiryperiodend() {
		return inquiryperiodend;
	}
	
	public void setInquiryperiodend(String inquiryperiodend) {
		this.inquiryperiodend = inquiryperiodend;
	}
	
	public String getFeedbacklimit() {
		return feedbacklimit;
	}
	
	public void setFeedbacklimit(String feedbacklimit) {
		this.feedbacklimit = feedbacklimit;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getApplicationtime() {
		return applicationtime;
	}
	
	public void setApplicationtime(String applicationtime) {
		this.applicationtime = applicationtime;
	}
	
	public String getApplicationorgid() {
		return applicationorgid;
	}
	
	public void setApplicationorgid(String applicationorgid) {
		this.applicationorgid = applicationorgid;
	}
	
	public String getApplicationorgname() {
		return applicationorgname;
	}
	
	public void setApplicationorgname(String applicationorgname) {
		this.applicationorgname = applicationorgname;
	}
	
	public String getOperatoridtype() {
		return operatoridtype;
	}
	
	public void setOperatoridtype(String operatoridtype) {
		this.operatoridtype = operatoridtype;
	}
	
	public String getOperatoridnumber() {
		return operatoridnumber;
	}
	
	public void setOperatoridnumber(String operatoridnumber) {
		this.operatoridnumber = operatoridnumber;
	}
	
	public String getOperatorname() {
		return operatorname;
	}
	
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}
	
	public String getOperatorphonenumber() {
		return operatorphonenumber;
	}
	
	public void setOperatorphonenumber(String operatorphonenumber) {
		this.operatorphonenumber = operatorphonenumber;
	}
	
	public String getInvestigatoridtype() {
		return investigatoridtype;
	}
	
	public void setInvestigatoridtype(String investigatoridtype) {
		this.investigatoridtype = investigatoridtype;
	}
	
	public String getInvestigatoridnumber() {
		return investigatoridnumber;
	}
	
	public void setInvestigatoridnumber(String investigatoridnumber) {
		this.investigatoridnumber = investigatoridnumber;
	}
	
	public String getInvestigatorname() {
		return investigatorname;
	}
	
	public void setInvestigatorname(String investigatorname) {
		this.investigatorname = investigatorname;
	}
	
	public String getApplicationid() {
		return applicationid;
	}
	
	public void setApplicationid(String applicationid) {
		this.applicationid = applicationid;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getOrgkey() {
		return orgkey;
	}
	
	public void setOrgkey(String orgkey) {
		this.orgkey = orgkey;
	}
	
	public String getMsgpath() {
		return msgpath;
	}
	
	public void setMsgpath(String msgpath) {
		this.msgpath = msgpath;
	}
	
	public String getQrydt() {
		return qrydt;
	}
	
	public void setQrydt(String qrydt) {
		this.qrydt = qrydt;
	}
	
	public String getLastpollingtime() {
		return lastpollingtime;
	}
	
	public void setLastpollingtime(String lastpollingtime) {
		this.lastpollingtime = lastpollingtime;
	}
	
}
