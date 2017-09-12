package com.citic.server.dx.domain.request;

import java.util.List;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 可疑名单上报（异常事件）上行报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月13日 下午10:58:59
 */
public class SuspiciousRequest100405 extends RequestMessage {
	private static final long serialVersionUID = -7462266524823969416L;
	
	/** 托管机构编码（非托管机构报送数据为空） */
	private String fromTGOrganizationId;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 事件特征码（3001-3008） */
	private String featureCode;
	
	/** 上报机构编码 */
	private String bankID;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	/** 交易list */
	private List<ExceptionEventRequest_Accounts> list;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_REPORT_EXCEPTIONAL_EVENT;
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
	
	public String getFeatureCode() {
		return featureCode;
	}
	
	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
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
	
	public List<ExceptionEventRequest_Accounts> getList() {
		return list;
	}
	
	public void setList(List<ExceptionEventRequest_Accounts> list) {
		this.list = list;
	}
}
