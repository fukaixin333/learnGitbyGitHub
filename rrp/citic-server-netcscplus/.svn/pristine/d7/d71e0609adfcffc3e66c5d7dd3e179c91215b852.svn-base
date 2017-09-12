package com.citic.server.dx.domain;

import com.citic.server.dx.domain.ResponseMessage;

import lombok.EqualsAndHashCode;

/**
 * 账户冻结发核心
 * 
 * @author 丁珂
 * @date 2016年4月13日 下午4:28:39
 */

public class Br25_Freeze extends ResponseMessage {
	
	private static final long serialVersionUID = -1570885335709632003L;
	
	protected String messageFrom;
	
	/** 交易类型编码 */
	private String txCode;
	
	/** 传输报文流水号 */
	private String transSerialNumber;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 冻结业务类型 */
	private String applicationType;
	


	/** 案件编号 */
	private String caseNumber;
	
	/** 案件类型 */
	private String caseType;
	
	/** 冻结事由 */
	private String reason = "";
	
	/** 冻结说明 */
	private String remark = "";
	
	/** 紧急程度（01-正常；02-加急） */
	private String emergencyLevel;
	
	/** 冻结账户所属银行机构编码 */
	private String bankID = "";
	
	/** 冻结账户所属银行名称 */
	private String bankName = "";
	
	/** 冻结账号类别（01-个人；02-对公） */
	private String accountType = "";
	
	/** 冻结账户名 */
	private String accountName = "";
	
	/** 冻结账卡号（账卡号为<strong>[主账号]_[账号识别号]</strong>） */
	private String accountNumber = "";
	
	/** 冻结账户证件类型 */
	private String IDType = "";
	
	/** 冻结账号证件号码 */
	private String IDNumber = "";
	
	/** 冻结方式（01-限额冻结；02-全额冻结） */
	private String freezeType = "";
	
	/** 金额（单位到元） */
	private String balance = "";
	
	/** 币种（CNY-人民币；USB美元；EUR欧元；……） */
	private String currency = "";
	
	/** 冻结起始时间（yyyyMMddHHmmss） */
	private String freezeStartTime = "";
	
	/** 冻结到期时间（yyyyMMddHHmmss） */
	private String expireTime = "";
	
	/** 申请时间（yyyyMMddHHmmss） */
	private String applicationTime = "";
	
	/** 申请机构编码 */
	private String applicationOrgID;
	
	/** 申请机构名称 */
	private String applicationOrgName;
	
	/** 经办人证件类型 */
	private String operatorIDType;
	
	/** 经办人证件号 */
	private String operatorIDNumber;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	/** 协查人证件类型 */
	private String investigatorIDType;
	
	/** 协查人证件号 */
	private String investigatorIDNumber;
	
	/** 协查人姓名 */
	private String investigatorName;
	
	//-----冻结解除
	/** 原申请编号 */
	private String originalApplicationID;
	
	/** 冻结解除说明 */
	private String withdrawalRemark = "";
	
	//----冻结延期
	/** 冻结延期说明 */
	private String extendRemark = "";
	/** 冻结延期起始时间（yyyyMMddHHmmss） */
	private String postponeStartTime = "";
	
	/** 核心冻结编号 */
	private String hxappid = "";
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	/** 创建时间 */
	private String create_dt = "";
	
	/** 0未处理 1已发送 3成功 4失败 */
	private String status_cd = "";
	
	private String qrydt="";
	private String orgkey="";
	
	private String last_up_dt="";
	
	private String recipient_time="";
	
	//-------------------------------------------------------------------
	
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

	public String getOrgkey() {
		return orgkey;
	}

	public void setOrgkey(String orgkey) {
		this.orgkey = orgkey;
	}

	public String getQrydt() {
		return qrydt;
	}

	public void setQrydt(String qrydt) {
		this.qrydt = qrydt;
	}

	public String getCasenumber() {
		return caseNumber;
	}
	
	public String getCasetype() {
		return caseType;
	}
	
	public String getReason() {
		return reason;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public String getEmergencylevel() {
		return emergencyLevel;
	}
	
	public String getBankid() {
		return bankID;
	}
	
	public String getBankname() {
		return bankName;
	}
	
	public String getAccounttype() {
		return accountType;
	}
	
	public String getAccountname() {
		return accountName;
	}
	
	public String getAccountnumber() {
		return accountNumber;
	}
	
	public String getIdtype() {
		return IDType;
	}
	
	public String getIdnumber() {
		return IDNumber;
	}
	
	public String getFreezetype() {
		return freezeType;
	}
	
	public String getBalance() {
		return balance;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public String getFreezestarttime() {
		return freezeStartTime;
	}
	
	public String getExpiretime() {
		return expireTime;
	}
	
	public String getApplicationtime() {
		return applicationTime;
	}
	
	public String getApplicationorgid() {
		return applicationOrgID;
	}
	
	public String getApplicationorgname() {
		return applicationOrgName;
	}
	
	public String getOperatoridtype() {
		return operatorIDType;
	}
	
	public String getOperatoridnumber() {
		return operatorIDNumber;
	}
	
	public String getOperatorname() {
		return operatorName;
	}
	
	public String getOperatorphonenumber() {
		return operatorPhoneNumber;
	}
	
	public String getInvestigatoridtype() {
		return investigatorIDType;
	}
	
	public String getInvestigatoridnumber() {
		return investigatorIDNumber;
	}
	
	public String getInvestigatorname() {
		return investigatorName;
	}
	
	public String getOriginalapplicationid() {
		return originalApplicationID;
	}
	
	public String getWithdrawalremark() {
		return withdrawalRemark;
	}
	
	public String getExtendremark() {
		return extendRemark;
	}
	
	public String getPostponestarttime() {
		return postponeStartTime;
	}
	
	public String getTxcode() {
		return txCode;
	}
	
	public String getTransserialnumber() {
		return transSerialNumber;
	}
	
	public String getApplicationid() {
		return applicationID;
	}
	
	public String getMessagefrom() {
		return messageFrom;
	}
	
	public String getMessageFrom() {
		return messageFrom;
	}
	
	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}
	
	public String getTxCode() {
		return txCode;
	}
	
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	
	public String getTransSerialNumber() {
		return transSerialNumber;
	}
	
	public void setTransSerialNumber(String transSerialNumber) {
		this.transSerialNumber = transSerialNumber;
	}
	
	public String getApplicationID() {
		return applicationID;
	}
	
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	
	//	--------------------------------------------------------------------------------------------
	
	public String getCaseNumber() {
		return caseNumber;
	}
	
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	
	public String getCaseType() {
		return caseType;
	}
	
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	
	public String getEmergencyLevel() {
		return emergencyLevel;
	}
	
	public void setEmergencyLevel(String emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}
	
	public String getBankID() {
		return bankID;
	}
	
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getIDType() {
		return IDType;
	}
	
	public void setIDType(String iDType) {
		IDType = iDType;
	}
	
	public String getIDNumber() {
		return IDNumber;
	}
	
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	
	public String getFreezeType() {
		return freezeType;
	}
	
	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}
	
	public String getFreezeStartTime() {
		return freezeStartTime;
	}
	
	public void setFreezeStartTime(String freezeStartTime) {
		this.freezeStartTime = freezeStartTime;
	}
	
	public String getExpireTime() {
		return expireTime;
	}
	
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	
	public String getApplicationTime() {
		return applicationTime;
	}
	
	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	public String getApplicationOrgID() {
		return applicationOrgID;
	}
	
	public void setApplicationOrgID(String applicationOrgID) {
		this.applicationOrgID = applicationOrgID;
	}
	
	public String getApplicationOrgName() {
		return applicationOrgName;
	}
	
	public void setApplicationOrgName(String applicationOrgName) {
		this.applicationOrgName = applicationOrgName;
	}
	
	public String getOperatorIDType() {
		return operatorIDType;
	}
	
	public void setOperatorIDType(String operatorIDType) {
		this.operatorIDType = operatorIDType;
	}
	
	public String getOperatorIDNumber() {
		return operatorIDNumber;
	}
	
	public void setOperatorIDNumber(String operatorIDNumber) {
		this.operatorIDNumber = operatorIDNumber;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getOperatorPhoneNumber() {
		return operatorPhoneNumber;
	}
	
	public void setOperatorPhoneNumber(String operatorPhoneNumber) {
		this.operatorPhoneNumber = operatorPhoneNumber;
	}
	
	public String getInvestigatorIDType() {
		return investigatorIDType;
	}
	
	public void setInvestigatorIDType(String investigatorIDType) {
		this.investigatorIDType = investigatorIDType;
	}
	
	public String getInvestigatorIDNumber() {
		return investigatorIDNumber;
	}
	
	public void setInvestigatorIDNumber(String investigatorIDNumber) {
		this.investigatorIDNumber = investigatorIDNumber;
	}
	
	public String getInvestigatorName() {
		return investigatorName;
	}
	
	public void setInvestigatorName(String investigatorName) {
		this.investigatorName = investigatorName;
	}
	
	public String getOriginalApplicationID() {
		return originalApplicationID;
	}
	
	public void setOriginalApplicationID(String originalApplicationID) {
		this.originalApplicationID = originalApplicationID;
	}
	
	public String getWithdrawalRemark() {
		return withdrawalRemark;
	}
	
	public void setWithdrawalRemark(String withdrawalRemark) {
		this.withdrawalRemark = withdrawalRemark;
	}
	
	public String getExtendRemark() {
		return extendRemark;
	}
	
	public void setExtendRemark(String extendRemark) {
		this.extendRemark = extendRemark;
	}
	
	public String getPostponeStartTime() {
		return postponeStartTime;
	}
	
	public void setPostponeStartTime(String postponeStartTime) {
		this.postponeStartTime = postponeStartTime;
	}
	
	public String getCreate_dt() {
		return create_dt;
	}
	
	public void setCreate_dt(String create_dt) {
		this.create_dt = create_dt;
	}
	
	public String getStatus_cd() {
		return status_cd;
	}
	
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getHxappid() {
		return hxappid;
	}
	
	public void setHxappid(String hxappid) {
		this.hxappid = hxappid;
	}
	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	
}
