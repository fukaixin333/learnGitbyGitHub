package com.citic.server.dx.domain.response;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.ResponseMessage;

/**
 * 冻结延期下行报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月13日 下午9:29:10
 */
public class FreezeResponse100205 extends ResponseMessage {
	private static final long serialVersionUID = 4223160388909495245L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 案件编号 */
	private String caseNumber;
	
	/** 案件类型 */
	private String caseType;
	
	/** 原冻结申请编号 */
	private String originalApplicationID;
	
	/** 紧急程度（01-正常；02-加急） */
	private String emergencyLevel;
	
	/** 冻结账户所属银行机构编码 */
	private String bankID;
	
	/** 冻结账户所属银行名称 */
	private String bankName;
	
	/** 冻结账号类别（01-个人；02-对公） */
	private String accountType;
	
	/** 冻结账户名 */
	private String accountName;
	
	/** 冻结账卡号（与原冻结报文账卡号相同） */
	private String accountNumber;
	
	/** 冻结账户证件类型 */
	private String IDType;
	
	/** 冻结账号证件号码 */
	private String IDNumber;
	
	/** 冻结方式（01-限额冻结；02-全额冻结） */
	private String freezeType;
	
	/** 金额 */
	private String balance;
	
	/** 币种（CNY-人民币；USB美元；EUR欧元；……） */
	private String currency;
	
	/** 冻结延期起始时间（yyyyMMddHHmmss） */
	private String postponeStartTime;
	
	/** 冻结到期时间（yyyyMMddHHmmss） */
	private String expireTime;
	
	/** 冻结延期说明 */
	private String extendRemark;
	
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
		return TxConstants.TXCODE_FREEZE_POSTPONE;
	}
	
	public String getApplicationID() {
		return applicationID;
	}
	
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
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
	
	public String getOriginalApplicationID() {
		return originalApplicationID;
	}
	
	public void setOriginalApplicationID(String originalApplicationID) {
		this.originalApplicationID = originalApplicationID;
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
	
	public String getBalance() {
		return balance;
	}
	
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getPostponeStartTime() {
		return postponeStartTime;
	}
	
	public void setPostponeStartTime(String postponeStartTime) {
		this.postponeStartTime = postponeStartTime;
	}
	
	public String getExpireTime() {
		return expireTime;
	}
	
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	
	public String getExtendRemark() {
		return extendRemark;
	}
	
	public void setExtendRemark(String extendRemark) {
		this.extendRemark = extendRemark;
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
