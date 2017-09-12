package com.citic.server.dx.domain.response;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.ResponseMessage;

/**
 * 案件举报反馈下行报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月13日 下午10:25:06
 */
public class CaseResponse100402 extends ResponseMessage {
	private static final long serialVersionUID = -3870470027529411910L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 案件举报类型（01-报案；02-止付后补流程） */
	private String applicationType;
	
	/** 反馈时间 */
	private String reportEndTime;
	
	/** 查询结果 */
	private String result;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	/** 反馈机构名称 */
	private String feedbackOrgName;
	
	/** 查询反馈说明 */
	private String feedbackRemark;
	
	private String caseid;
	
	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_REPORT_CASE_FEEDBACK;
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
	
	public String getReportEndTime() {
		return reportEndTime;
	}
	
	public void setReportEndTime(String reportEndTime) {
		this.reportEndTime = reportEndTime;
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
}
