package com.citic.server.dx.domain.request;

import java.io.Serializable;

/**
 * 查询类上行（反馈）报文 - 共有权/优先权信息
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午3:36:43
 */
public class QueryRequest_Priority implements Serializable {
	private static final long serialVersionUID = -6555400544626728739L;
	
	/** 序号 */
	private String priortySerial;
	
	/** 账号 */
	private String accountNumber;
	
	/** 卡号（对私业务时需填写） */
	private String cardNumber;
	
	/** 证件类型代码 */
	private String credentialType;
	
	/** 证件号码 */
	private String credentialNumber;
	
	/** 权利类型（01-共有权；02-优先权；03-质押权等） */
	private String rightsType;
	
	/** 权利人姓名 */
	private String obligeeName;
	
	/** 权利金额（精确至整数） */
	private String rightsBalance;
	
	/** 权利人通讯地址 */
	private String obligeeAddress;
	
	/** 权利人联系方式 */
	private String obligeeContactInfo;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getPriortySerial() {
		return priortySerial;
	}
	
	public void setPriortySerial(String priortySerial) {
		this.priortySerial = priortySerial;
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
	
	public String getCredentialType() {
		return credentialType;
	}
	
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
	
	public String getCredentialNumber() {
		return credentialNumber;
	}
	
	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}
	
	public String getRightsType() {
		return rightsType;
	}
	
	public void setRightsType(String rightsType) {
		this.rightsType = rightsType;
	}
	
	public String getObligeeName() {
		return obligeeName;
	}
	
	public void setObligeeName(String obligeeName) {
		this.obligeeName = obligeeName;
	}
	
	public String getRightsBalance() {
		return rightsBalance;
	}
	
	public void setRightsBalance(String rightsBalance) {
		this.rightsBalance = rightsBalance;
	}
	
	public String getObligeeAddress() {
		return obligeeAddress;
	}
	
	public void setObligeeAddress(String obligeeAddress) {
		this.obligeeAddress = obligeeAddress;
	}
	
	public String getObligeeContactInfo() {
		return obligeeContactInfo;
	}
	
	public void setObligeeContactInfo(String obligeeContactInfo) {
		this.obligeeContactInfo = obligeeContactInfo;
	}
}