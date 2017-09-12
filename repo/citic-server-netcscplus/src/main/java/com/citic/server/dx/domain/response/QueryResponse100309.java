package com.citic.server.dx.domain.response;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.ResponseMessage;

/**
 * 客户全账户查询下行报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月13日 下午10:11:41
 */
public class QueryResponse100309 extends ResponseMessage {
	private static final long serialVersionUID = -5709218283018880478L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 案件编号 */
	private String caseNumber;
	
	/** 案件类型 */
	private String caseType;
	
	/** 紧急程度（01-正常；02-加急） */
	private String emergencyLevel;
	
	/** 查询主体类别（1-自然人主体；2-法人主体） */
	private String subjectType;
	
	/** 查询银行机构编码 */
	private String bankID;
	
	/** 查询银行名称 */
	private String bankName;
	
	/** 查询证照类型代码 */
	private String accountCredentialType;
	
	/** 查询证照号码 */
	private String accountCredentialNumber;
	
	/** 查询主体名称（个人姓名或公司名称） */
	private String accountSubjectName;
	
	/**
	 * 查询内容
	 * <ul>
	 * <li>01 - 账户基本信息
	 * <li>02 - 账户信息（含强制措施、共有权/优先权信息）
	 * </ul>
	 */
	private String inquiryMode;
	
	/** 查询事由 */
	private String reason;
	
	/** 查询说明 */
	private String remark;
	
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
		return TxConstants.TXCODE_CUSTOMER_WHOLE_ACCOUNT;
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
	
	public String getEmergencyLevel() {
		return emergencyLevel;
	}
	
	public void setEmergencyLevel(String emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}
	
	public String getSubjectType() {
		return subjectType;
	}
	
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
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
	
	public String getAccountCredentialType() {
		return accountCredentialType;
	}
	
	public void setAccountCredentialType(String accountCredentialType) {
		this.accountCredentialType = accountCredentialType;
	}
	
	public String getAccountCredentialNumber() {
		return accountCredentialNumber;
	}
	
	public void setAccountCredentialNumber(String accountCredentialNumber) {
		this.accountCredentialNumber = accountCredentialNumber;
	}
	
	public String getAccountSubjectName() {
		return accountSubjectName;
	}
	
	public void setAccountSubjectName(String accountSubjectName) {
		this.accountSubjectName = accountSubjectName;
	}
	
	public String getInquiryMode() {
		return inquiryMode;
	}
	
	public void setInquiryMode(String inquiryMode) {
		this.inquiryMode = inquiryMode;
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
