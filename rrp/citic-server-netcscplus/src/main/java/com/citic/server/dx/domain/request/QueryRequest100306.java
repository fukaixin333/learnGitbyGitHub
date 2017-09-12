package com.citic.server.dx.domain.request;

import java.util.List;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 查询类上行（反馈）报文 - 账户动态查询
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午3:29:30
 */
public class QueryRequest100306 extends RequestMessage {
	private static final long serialVersionUID = 2780014634809888922L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 查询结果（4位业务应答码） */
	private String result;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	/** 反馈机构名称 */
	private String feedbackOrgName;
	
	/** 查询反馈说明 */
	private String feedbackRemark;
	
	/** 账户名 */
	private String accountName;
	
	/**
	 * 帐卡号定位账户账号<br />
	 * 定位帐号唯一性字段，定义为<strong>[主账户]_[子账户识别号/账户序号/币种号]</strong>
	 */
	private String accountNumber;
	
	/** 卡号（与原动态查询帐卡号相同） */
	private String cardNumber;
	
	/** 交易明细 */
	private List<QueryRequest_Transaction> transactions;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_ACCOUNT_DYNAMIC_FEEDBACK;
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
	
	public String getFeedbackOrgName() {
		return feedbackOrgName;
	}
	
	public void setFeedbackOrgName(String feedbackOrgName) {
		this.feedbackOrgName = feedbackOrgName;
	}
	
	public String getFeedbackRemark() {
		return feedbackRemark;
	}
	
	public void setFeedbackRemark(String feedbackRemark) {
		this.feedbackRemark = feedbackRemark;
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
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public List<QueryRequest_Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<QueryRequest_Transaction> transactions) {
		this.transactions = transactions;
	}
}
