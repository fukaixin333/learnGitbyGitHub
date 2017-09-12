package com.citic.server.dx.domain.request;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 账户止付上行（反馈）报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午2:40:19
 */
public class StoppayRequest100102 extends RequestMessage {
	private static final long serialVersionUID = -525535871131608644L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 止付结果（4位业务应答码） */
	private String result;
	
	/** 止付账号类别（01-个人；02-对公） */
	private String accountType;
	
	/** 止付帐卡号（与原止付报文账卡号相同） */
	private String accountNumber;
	
	/** 卡/折号（对私业务时需填写） */
	private String cardNumber;
	
	/** 账户余额（止付前余额） */
	private String accountBalance;
	
	/** 止付起始时间（yyyyMMddHHmmss） */
	private String startTime;
	
	/** 止付结束时间（yyyyMMddHHmmss） */
	private String expireTime;
	
	/** 未能止付原因（填写技术原因） */
	private String failureCause;
	
	/** 反馈说明（填写业务原因） */
	private String feedbackRemark;
	
	/** 反馈机构名称 */
	private String feedbackOrgName;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_STOPPAYMENT_FEEDBACK;
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
	
	public String getAccountType() {
		return accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
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
	
	public String getFailureCause() {
		return failureCause;
	}
	
	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}
	
	public String getFeedbackRemark() {
		return feedbackRemark;
	}
	
	public void setFeedbackRemark(String feedbackRemark) {
		this.feedbackRemark = feedbackRemark;
	}
	
	public String getFeedbackOrgName() {
		return feedbackOrgName;
	}
	
	public void setFeedbackOrgName(String feedbackOrgName) {
		this.feedbackOrgName = feedbackOrgName;
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
}
