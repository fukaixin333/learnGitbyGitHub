package com.citic.server.dx.domain.request;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 账户止付解除上行（反馈）报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月14日 上午9:23:25
 */
public class StoppayRequest100104 extends RequestMessage {
	private static final long serialVersionUID = -1025214570684219887L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 止付解除结果（4位业务应答码） */
	private String result;
	
	/** 止付账号类别（01-个人；02-对公） */
	private String accountType;
	
	/** 止付帐卡号（与原止付报文账卡号相同） */
	private String accountNumber;
	
	/** 卡/折号（对私业务时需填写） */
	private String cardNumber;
	
	/** 止付解除生效时间（yyyyMMddHHmmss） */
	private String withdrawalTime;
	
	/** 未能解除止付原因 */
	private String failureCause;
	
	/** 反馈说明 */
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
		return TxConstants.TXCODE_STOPPAYMENT_RELIEVE_FEEDBACK;
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
	
	public String getWithdrawalTime() {
		return withdrawalTime;
	}
	
	public void setWithdrawalTime(String withdrawalTime) {
		this.withdrawalTime = withdrawalTime;
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
