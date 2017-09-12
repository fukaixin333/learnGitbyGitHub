package com.citic.server.dx.domain.request;

import java.util.List;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 可疑名单上报（异常开卡）上行报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午3:58:18
 */
public class SuspiciousRequest100403 extends RequestMessage {
	private static final long serialVersionUID = -592691442398473740L;
	
	/** 托管机构编码（非托管机构报送数据为空） */
	private String fromTGOrganizationId;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 事件特征码（1001-频繁开卡；1002-累计开卡） */
	private String featureCode;
	
	/** 上报银行机构编码 */
	private String bankID;
	
	/** 证件类型 */
	private String IDType;
	
	/** 证件号 */
	private String IDNumber;
	
	/** 姓名 */
	private String IDName;
	
	/** 卡/折信息列表 */
	private List<SuspiciousRequest_Accounts> accountsList;
	
	/** 上报机构名称 */
	private String reportOrgName;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_REPORT_UNUSUAL_OPENCARD;
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
	
	public String getBankID() {
		return bankID;
	}
	
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	
	public String getIDType() {
		return IDType;
	}
	
	public void setIDType(String iDType) {
		IDType = iDType;
	}
	
	public String getIDNumber() {
		return IDNumber;
	}
	
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	
	public String getIDName() {
		return IDName;
	}
	
	public void setIDName(String iDName) {
		IDName = iDName;
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
	
	public List<SuspiciousRequest_Accounts> getAccountsList() {
		return accountsList;
	}
	
	public void setAccountsList(List<SuspiciousRequest_Accounts> accountsList) {
		this.accountsList = accountsList;
	}
}
