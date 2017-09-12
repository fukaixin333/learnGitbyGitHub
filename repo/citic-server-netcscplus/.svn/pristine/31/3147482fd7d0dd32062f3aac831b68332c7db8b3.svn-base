package com.citic.server.dx.domain.request;

import java.util.List;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 可疑名单上报（涉案账户）上行报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月13日 下午10:46:01
 */
public class SuspiciousRequest100404 extends RequestMessage {
	private static final long serialVersionUID = -6516600268068922865L;
	
	/** 托管机构编码（非托管机构报送数据为空） */
	private String fromTGOrganizationId;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 事件特征码（2001-黑名单账户） */
	private String featureCode = "2001";
	
	/** 上报银行机构编码 */
	private String bankID;
	
	/** 卡/折号 */
	private String cardNumber;
	
	/** 账户名 */
	private String accountName;
	
	/** 证件类型 */
	private String IDType;
	
	/** 证件号 */
	private String IDNumber;
	
	/** 联系电话 */
	private String phoneNumber;
	
	/** 通讯地址 */
	private String address;
	
	/** 邮政编码 */
	private String postCode;
	
	/** 开卡地点 */
	private String accountOpenPlace;
	
	/** 上报机构名称 */
	private String reportOrgName;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	/** 卡/折信息列表 */
	private List<SuspiciousRequest_Account> accountList;
	
	// ==========================================================================================
	//                     Helper Field
	// ==========================================================================================
	private String orgkey = "";
	
	
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	
	public String getOrgkey() {
		return orgkey;
	}
	
	public void setOrgkey(String orgkey) {
		this.orgkey = orgkey;
	}
	
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_REPORT_INVOLVED_ACOUNT;
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
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String getAccountOpenPlace() {
		return accountOpenPlace;
	}
	
	public void setAccountOpenPlace(String accountOpenPlace) {
		this.accountOpenPlace = accountOpenPlace;
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
	
	public List<SuspiciousRequest_Account> getAccountList() {
		return accountList;
	}
	
	public void setAccountList(List<SuspiciousRequest_Account> accountList) {
		this.accountList = accountList;
	}
}
