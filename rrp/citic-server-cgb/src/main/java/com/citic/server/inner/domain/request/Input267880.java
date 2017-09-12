package com.citic.server.inner.domain.request;

import com.citic.server.inner.domain.RequestMessage;

/**
 * 账户明细列表查询（267880）
 * 
 * @author liuxuanfei
 * @date 2016年12月23日 下午6:23:21
 */
public class Input267880 extends RequestMessage {
	private static final long serialVersionUID = -5732193349687221468L;
	
	private String accountNumber; // 卡号/账号
	private String currency; // 币种
	private String cashExCode; // 钞汇标志
	private String accountType; // 账户类型（DD-活期；TD-定期；不输入默认查询全部）
	private String periodAccountType; // 定期账户类型
	private String spAccountStatus = "9"; // 分户状态（9-统计所有分户；A-统计正常分户；H-统计冻结分户）
	private String voucherNumber; // 凭证号
	
	public Input267880() {
	}
	
	public Input267880(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getCashExCode() {
		return cashExCode;
	}
	
	public void setCashExCode(String cashExCode) {
		this.cashExCode = cashExCode;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getPeriodAccountType() {
		return periodAccountType;
	}
	
	public void setPeriodAccountType(String periodAccountType) {
		this.periodAccountType = periodAccountType;
	}
	
	public String getSpAccountStatus() {
		return spAccountStatus;
	}
	
	public void setSpAccountStatus(String spAccountStatus) {
		this.spAccountStatus = spAccountStatus;
	}
	
	public String getVoucherNumber() {
		return voucherNumber;
	}
	
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
}
