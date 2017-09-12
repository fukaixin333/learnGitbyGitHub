package com.citic.server.dx.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 查询类上行（反馈）报文 - 账户信息
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午2:49:37
 */
public class QueryRequest_Account implements Serializable {
	private static final long serialVersionUID = -647487785837616953L;
	
	/**
	 * 定位账户账号<br />
	 * 定位账号唯一性字段，定义为<strong>[主账户]_[子账户识别号/账户序号/币种号]</strong>
	 */
	private String accountNumber;
	
	/** 一般（子）账户序号 */
	private String accountSerial;
	
	/** 一般（子）账户类别（根据各银行实际数据反馈） */
	private String accountType;
	
	/** 账户状态（正常，冻结，销户等） */
	private String accountStatus;
	
	/** 币种(CNY-人民币；USD-美元；EUR欧元；……) */
	private String currency;
	
	/** 钞汇标志 */
	private String cashRemit;
	
	/** 账户余额 */
	private String accountBalance;
	
	/** 可用余额 */
	private String availableBalance;
	
	/** 最后交易时间 */
	private String lastTransactionTime;
	
	/** 交易明细 */
	private List<QueryRequest_Transaction> transactions;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getAccountSerial() {
		return accountSerial;
	}
	
	public void setAccountSerial(String accountSerial) {
		this.accountSerial = accountSerial;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getAccountStatus() {
		return accountStatus;
	}
	
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getCashRemit() {
		return cashRemit;
	}
	
	public void setCashRemit(String cashRemit) {
		this.cashRemit = cashRemit;
	}
	
	public String getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public String getAvailableBalance() {
		return availableBalance;
	}
	
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	
	public String getLastTransactionTime() {
		return lastTransactionTime;
	}
	
	public void setLastTransactionTime(String lastTransactionTime) {
		this.lastTransactionTime = lastTransactionTime;
	}
	
	public List<QueryRequest_Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<QueryRequest_Transaction> transactions) {
		this.transactions = transactions;
	}
}
