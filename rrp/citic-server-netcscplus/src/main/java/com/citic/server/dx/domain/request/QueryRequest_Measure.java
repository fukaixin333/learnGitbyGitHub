package com.citic.server.dx.domain.request;

import java.io.Serializable;

/**
 * 查询类上行（反馈）报文 - 强制措施信息
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午3:35:45
 */
public class QueryRequest_Measure implements Serializable {
	private static final long serialVersionUID = -2391345060923204023L;
	
	/** 措施序号 */
	private String freezeSerial;
	
	/** 账号 */
	private String accountNumber;
	
	/** 卡号（对私业务时需填写） */
	private String cardNumber;
	
	/** 冻结开始日 */
	private String freezeStartTime;
	
	/** 冻结截止日 */
	private String freezeEndTime;
	
	/** 冻结机关名称 */
	private String freezeDeptName;
	
	/** 币种（CNY-人民币；USD-美元；EUR-欧元；……） */
	private String currency;
	
	/** 冻结金额（精确至整数） */
	private String freezeBalance;
	
	/** 备注 */
	private String remark;
	
	/** 冻结措施类型（0001-公安止付；0002-公安冻结；1001-高法冻结） */
	private String freezeType;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getFreezeSerial() {
		return freezeSerial;
	}
	
	public void setFreezeSerial(String freezeSerial) {
		this.freezeSerial = freezeSerial;
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
	
	public String getFreezeStartTime() {
		return freezeStartTime;
	}
	
	public void setFreezeStartTime(String freezeStartTime) {
		this.freezeStartTime = freezeStartTime;
	}
	
	public String getFreezeEndTime() {
		return freezeEndTime;
	}
	
	public void setFreezeEndTime(String freezeEndTime) {
		this.freezeEndTime = freezeEndTime;
	}
	
	public String getFreezeDeptName() {
		return freezeDeptName;
	}
	
	public void setFreezeDeptName(String freezeDeptName) {
		this.freezeDeptName = freezeDeptName;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getFreezeBalance() {
		return freezeBalance;
	}
	
	public void setFreezeBalance(String freezeBalance) {
		this.freezeBalance = freezeBalance;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getFreezeType() {
		return freezeType;
	}
	
	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}
}
