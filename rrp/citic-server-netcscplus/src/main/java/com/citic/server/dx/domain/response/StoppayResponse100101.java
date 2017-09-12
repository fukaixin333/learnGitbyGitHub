package com.citic.server.dx.domain.response;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.ResponseMessage;

/**
 * 账户止付下行（反馈）报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月13日 下午4:21:45
 */
public class StoppayResponse100101 extends ResponseMessage {
	private static final long serialVersionUID = -9175451949217789842L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/**
	 * 是否补流程
	 * <ul>
	 * <li>00 - 初次提交，正常止付<strong>人民币</strong>活期账户
	 * <li>01 - 初次提交，正常止付<strong>外币活期/人民币定期/外币定期</strong>账户
	 * <li>02 - 银行举报案件，止付<strong>人民币</strong>活期账户
	 * <li>03 - 银行举报案件，止付<strong>外币活期/人民币定期/外币定期</strong>账户
	 * <li>04 - 后补<strong>人民币</strong>活期止付流程
	 * <li>05 - 后补<strong>外币活期/人民币定期/外币定期</strong>止付流程
	 * </ul>
	 */
	private String applicationType;
	
	/**
	 * 原举报申请编号 <br />
	 * 银行报案时必填，即 ApplicationType = 02 / 03 / 04 / 05 时。
	 */
	private String originalApplicationID;
	
	/** 案件编号 */
	private String caseNumber;
	
	/** 案件类型 */
	private String caseType;
	
	/** 紧急程度（01-正常；02-加急） */
	private String emergencyLevel;
	
	/** 转出账户所属银行机构编码 */
	private String transferOutBankID;
	
	/** 转出账户所属银行名称 */
	private String transferOutBankName;
	
	/** 转出账户名 */
	private String transferOutAccountName;
	
	/** 转出账卡号 */
	private String transferOutAccountNumber;
	
	/** 币种（CNY-人民币；USD-美元；EUR-欧元；……） */
	private String currency;
	
	/** 转出金额（单位到元） */
	private String transferAmount;
	
	/** 转出时间（yyyyMMddHHmmss） */
	private String transferTime;
	
	/** 止付账户所属银行机构编码 */
	private String bankID;
	
	/** 止付账户所属银行名称 */
	private String bankName;
	
	/** 止付账号类别（01-个人；02-对公） */
	private String accountType;
	
	/** 止付账户名 */
	private String accountName;
	
	/**
	 * 止付账卡号
	 * <ul>
	 * <li>当 ApplicationType = 00 或 ApplicationType = 02 时，卡折号为主账号，默认止付主账号内活期账户；
	 * <li>当 ApplicationType = 01 或 ApplicationType = 03 时，账卡号为<strong>"[主账号]_[账号识别号]"</strong>
	 */
	private String accountNumber;
	
	/** 止付事由 */
	private String reason;
	
	/** 止付说明 */
	private String remark;
	
	/** 止付起始时间（yyyyMMddHHmmss） */
	private String startTime;
	
	/** 止付截止时间（yyyyMMddHHmmss） */
	private String expireTime;
	
	/** 申请时间（yyyyMMddHHmmss） */
	private String applicationTime;
	
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
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_STOPPAYMENT;
	}
	
	public String getApplicationID() {
		return applicationID;
	}
	
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	
	public String getApplicationType() {
		return applicationType;
	}
	
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	
	public String getOriginalApplicationID() {
		return originalApplicationID;
	}
	
	public void setOriginalApplicationID(String originalApplicationID) {
		this.originalApplicationID = originalApplicationID;
	}
	
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
	
	public String getTransferOutBankID() {
		return transferOutBankID;
	}
	
	public void setTransferOutBankID(String transferOutBankID) {
		this.transferOutBankID = transferOutBankID;
	}
	
	public String getTransferOutBankName() {
		return transferOutBankName;
	}
	
	public void setTransferOutBankName(String transferOutBankName) {
		this.transferOutBankName = transferOutBankName;
	}
	
	public String getTransferOutAccountName() {
		return transferOutAccountName;
	}
	
	public void setTransferOutAccountName(String transferOutAccountName) {
		this.transferOutAccountName = transferOutAccountName;
	}
	
	public String getTransferOutAccountNumber() {
		return transferOutAccountNumber;
	}
	
	public void setTransferOutAccountNumber(String transferOutAccountNumber) {
		this.transferOutAccountNumber = transferOutAccountNumber;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getTransferAmount() {
		return transferAmount;
	}
	
	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	public String getTransferTime() {
		return transferTime;
	}
	
	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
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
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
}
