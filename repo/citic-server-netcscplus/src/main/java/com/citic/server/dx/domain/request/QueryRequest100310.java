package com.citic.server.dx.domain.request;

import java.util.List;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 客户全账户查询上行（反馈）报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午3:34:12
 */
public class QueryRequest100310 extends RequestMessage {
	private static final long serialVersionUID = 5475131935339047731L;
	
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
	private List<QueryRequest_Accounts> accounts;
	
	/** 冻结强制措施 */
	private List<QueryRequest_Measure> measures;
	
	/** 共有权/优先权 */
	private List<QueryRequest_Priority> priorities;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_CUSTOMER_WHOLE_ACCOUNT_FEEDBACK;
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
	
	public List<QueryRequest_Accounts> getAccounts() {
		return accounts;
	}
	
	public void setAccounts(List<QueryRequest_Accounts> accounts) {
		this.accounts = accounts;
	}
	
	public List<QueryRequest_Measure> getMeasures() {
		return measures;
	}
	
	public void setMeasures(List<QueryRequest_Measure> measures) {
		this.measures = measures;
	}
	
	public List<QueryRequest_Priority> getPriorities() {
		return priorities;
	}
	
	public void setPriorities(List<QueryRequest_Priority> priorities) {
		this.priorities = priorities;
	}
}
