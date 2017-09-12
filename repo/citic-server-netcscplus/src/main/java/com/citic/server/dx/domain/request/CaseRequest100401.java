package com.citic.server.dx.domain.request;

import java.util.List;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.Attachment;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 案件举报上行报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午3:47:40
 */
public class CaseRequest100401 extends RequestMessage {
	private static final long serialVersionUID = -4988931844405540774L;
	
	/** 是否主动发送初始节点（01-是） */
	private String isInitialNode = "01";
	
	/** 托管机构编码（非托管机构报送数据为空） */
	private String fromTGOrganizationId;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 案件举报类型（01-报案；02-止付后补流程） */
	private String applicationType;
	
	/** 上报时间 */
	private String reportEndTime;
	
	/** 受害人姓名 */
	private String victimName;
	
	/** 受害人联系电话 */
	private String victimPhoneNumber;
	
	/** 证件类型 */
	private String victimIDType;
	
	/** 证件号 */
	private String victimIDNumber;
	
	/** 事件描述 */
	private String accidentDescription;
	
	/** 上报机构名称 */
	private String reportOrgName;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	/** 交易list */
	private List<CaseRequest_Transaction> transactionList;
	
	/** 紧急止付申请表 */
	private List<Attachment> attachments;
	
	// ==========================================================================================
	//                     Helper Field
	// ==========================================================================================
	private String caseid = "";
	private String msg_type_cd = "";
	private String featurecodetype = "";
	private String orgkey = "";
	private String  toorg="";
	
	

	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_REPORT_CASE;
	}
	
	public String getIsInitialNode() {
		return isInitialNode;
	}
	
	public void setIsInitialNode(String isInitialNode) {
		this.isInitialNode = isInitialNode;
	}
	
	public String getFromTGOrganizationId() {
		return fromTGOrganizationId;
	}
	
	public void setFromTGOrganizationId(String fromTGOrganizationId) {
		this.fromTGOrganizationId = fromTGOrganizationId;
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
	
	public String getVictimName() {
		return victimName;
	}
	
	public void setVictimName(String victimName) {
		this.victimName = victimName;
	}
	
	public String getVictimPhoneNumber() {
		return victimPhoneNumber;
	}
	
	public void setVictimPhoneNumber(String victimPhoneNumber) {
		this.victimPhoneNumber = victimPhoneNumber;
	}
	
	public String getVictimIDType() {
		return victimIDType;
	}
	
	public void setVictimIDType(String victimIDType) {
		this.victimIDType = victimIDType;
	}
	
	public String getVictimIDNumber() {
		return victimIDNumber;
	}
	
	public void setVictimIDNumber(String victimIDNumber) {
		this.victimIDNumber = victimIDNumber;
	}
	
	public String getAccidentDescription() {
		return accidentDescription;
	}
	
	public void setAccidentDescription(String accidentDescription) {
		this.accidentDescription = accidentDescription;
	}
	
	public String getReportOrgName() {
		return reportOrgName;
	}
	
	public void setReportOrgName(String reportOrgName) {
		this.reportOrgName = reportOrgName;
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
	
	public List<CaseRequest_Transaction> getTransactionList() {
		return transactionList;
	}
	
	public void setTransactionList(List<CaseRequest_Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public String getCaseid() {
		return caseid;
	}
	
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	
	public String getMsg_type_cd() {
		return msg_type_cd;
	}
	
	public void setMsg_type_cd(String msg_type_cd) {
		this.msg_type_cd = msg_type_cd;
	}
	
	public String getFeaturecodetype() {
		return featurecodetype;
	}
	
	public void setFeaturecodetype(String featurecodetype) {
		this.featurecodetype = featurecodetype;
	}
	
	public String getOrgkey() {
		return orgkey;
	}
	
	public void setOrgkey(String orgkey) {
		this.orgkey = orgkey;
	}
	public String getToorg() {
		return toorg;
	}

	public void setToorg(String toorg) {
		this.toorg = toorg;
	}
}
