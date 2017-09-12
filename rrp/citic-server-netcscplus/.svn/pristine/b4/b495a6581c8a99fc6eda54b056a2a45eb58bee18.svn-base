package com.citic.server.dx.domain.request;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 账户动态查询解除上行（反馈）报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午3:31:14
 */
public class QueryRequest100308 extends RequestMessage {
	private static final long serialVersionUID = -8212457556823209617L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 动态查询解除结果（4位业务应答码） */
	private String result;
	
	/** 案件编号 */
	private String caseNumber;
	
	/** 申请机构编码 */
	private String applicationOrgID;
	
	/** 申请机构名称 */
	private String applicationOrgName;
	
	/** 申请时间（yyyyMMddHHmmss） */
	private String applicationTime;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	/** 动态查询账户所属银行机构编码 */
	private String bankID;
	
	/** 动态查询账户所属银行名称 */
	private String bankName;
	
	/** 动态查询账户名 */
	private String accountName;
	
	/** 动态查询卡/折号（与原账户动态查询请求卡/折号一致） */
	private String cardNumber;
	
	/** 解除动态查询说明 */
	private String withdrawalRemark;
	
	/** 反馈说明 */
	private String feedbackRemark;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_ACCOUNT_DYNAMIC_RELIEVE_FEEDBACK;
	}
	
	public String getApplicationID() {
		return applicationID;
	}
	
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getCaseNumber() {
		return caseNumber;
	}
	
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
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
	
	public String getApplicationTime() {
		return applicationTime;
	}
	
	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
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
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getWithdrawalRemark() {
		return withdrawalRemark;
	}
	
	public void setWithdrawalRemark(String withdrawalRemark) {
		this.withdrawalRemark = withdrawalRemark;
	}
	
	public String getFeedbackRemark() {
		return feedbackRemark;
	}
	
	public void setFeedbackRemark(String feedbackRemark) {
		this.feedbackRemark = feedbackRemark;
	}
}
