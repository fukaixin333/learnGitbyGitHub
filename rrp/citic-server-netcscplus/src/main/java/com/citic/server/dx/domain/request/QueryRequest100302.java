package com.citic.server.dx.domain.request;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 查询类上行（反馈）报文 - 账户交易明细查询
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午2:30:34
 */
public class QueryRequest100302 extends RequestMessage {
	private static final long serialVersionUID = -6261685720622507221L;
	
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
	
	/** 账户明细 */
	private QueryRequest_Accounts accounts;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_ACCOUNT_TRANSACTION_DETAIL_FEEDBACK;
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
	
	public QueryRequest_Accounts getAccounts() {
		return accounts;
	}
	
	public void setAccounts(QueryRequest_Accounts accounts) {
		this.accounts = accounts;
	}
}
