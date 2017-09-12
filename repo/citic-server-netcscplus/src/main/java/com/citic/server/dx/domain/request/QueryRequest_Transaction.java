package com.citic.server.dx.domain.request;

import java.io.Serializable;

/**
 * 止付上行（反馈）报文 - 交易明细
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午2:51:26
 */
public class QueryRequest_Transaction implements Serializable {
	private static final long serialVersionUID = 1565875230478633080L;
	
	/** 交易类型 */
	private String transactionType;
	
	/** 借贷标志（0-借；1-贷） */
	private String borrowingSigns;
	
	/** 币种（CNY-人民币；USD-美元；EUR-欧元；……） */
	private String currency;
	
	/** 交易金额 */
	private String transactionAmount;
	
	/** 交易余额 */
	private String accountBalance;
	
	/** 交易时间 */
	private String transactionTime;
	
	/** 交易流水号 */
	private String transactionSerial;
	
	/** 交易对方名称 */
	private String opponentName;
	
	/** 交易对方账卡号 */
	private String opponentAccountNumber;
	
	/** 交易对方证件号码 */
	private String opponentCredentialNumber;
	
	/** 交易对方账号开户行 */
	private String opponentDepositBankID;
	
	/** 交易摘要 */
	private String transactionRemark;
	
	/** 交易网点名称 */
	private String transactionBranchName;
	
	/** 交易网点代码 */
	private String transactionBranchCode;
	
	/** 日志号 */
	private String logNumber;
	
	/** 传票号 */
	private String summonsNumber;
	
	/** 凭证种类 */
	private String voucherType;
	
	/** 凭证号 */
	private String voucherCode;
	
	/** 现金标志（00-其它；01-现金交易） */
	private String cashMark;
	
	/** 终端号 */
	private String terminalNumber;
	
	/** 交易是否成功（00-成功；01-失败） */
	private String transactionStatus;
	
	/** 交易发生地 */
	private String transactionAddress;
	
	/** 商户名称 */
	private String merchantName;
	
	/** 商户号 */
	private String merchantCode;
	
	/** IP地址 */
	private String IPAddress;
	
	/** MAC地址 */
	private String MAC;
	
	/** 交易柜员号 */
	private String tellerCode;
	
	/** 备注 */
	private String remark;
	
	// ==========================================================================================
	//                     Helper Field
	// ==========================================================================================
	/** 账户账号 */
	private String accountNumber;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getBorrowingSigns() {
		return borrowingSigns;
	}
	
	public void setBorrowingSigns(String borrowingSigns) {
		this.borrowingSigns = borrowingSigns;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getTransactionAmount() {
		return transactionAmount;
	}
	
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public String getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public String getTransactionTime() {
		return transactionTime;
	}
	
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	
	public String getTransactionSerial() {
		return transactionSerial;
	}
	
	public void setTransactionSerial(String transactionSerial) {
		this.transactionSerial = transactionSerial;
	}
	
	public String getOpponentName() {
		return opponentName;
	}
	
	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}
	
	public String getOpponentAccountNumber() {
		return opponentAccountNumber;
	}
	
	public void setOpponentAccountNumber(String opponentAccountNumber) {
		this.opponentAccountNumber = opponentAccountNumber;
	}
	
	public String getOpponentCredentialNumber() {
		return opponentCredentialNumber;
	}
	
	public void setOpponentCredentialNumber(String opponentCredentialNumber) {
		this.opponentCredentialNumber = opponentCredentialNumber;
	}
	
	public String getOpponentDepositBankID() {
		return opponentDepositBankID;
	}
	
	public void setOpponentDepositBankID(String opponentDepositBankID) {
		this.opponentDepositBankID = opponentDepositBankID;
	}
	
	public String getTransactionRemark() {
		return transactionRemark;
	}
	
	public void setTransactionRemark(String transactionRemark) {
		this.transactionRemark = transactionRemark;
	}
	
	public String getTransactionBranchName() {
		return transactionBranchName;
	}
	
	public void setTransactionBranchName(String transactionBranchName) {
		this.transactionBranchName = transactionBranchName;
	}
	
	public String getTransactionBranchCode() {
		return transactionBranchCode;
	}
	
	public void setTransactionBranchCode(String transactionBranchCode) {
		this.transactionBranchCode = transactionBranchCode;
	}
	
	public String getLogNumber() {
		return logNumber;
	}
	
	public void setLogNumber(String logNumber) {
		this.logNumber = logNumber;
	}
	
	public String getSummonsNumber() {
		return summonsNumber;
	}
	
	public void setSummonsNumber(String summonsNumber) {
		this.summonsNumber = summonsNumber;
	}
	
	public String getVoucherType() {
		return voucherType;
	}
	
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	
	public String getVoucherCode() {
		return voucherCode;
	}
	
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	
	public String getCashMark() {
		return cashMark;
	}
	
	public void setCashMark(String cashMark) {
		this.cashMark = cashMark;
	}
	
	public String getTerminalNumber() {
		return terminalNumber;
	}
	
	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}
	
	public String getTransactionStatus() {
		return transactionStatus;
	}
	
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	public String getTransactionAddress() {
		return transactionAddress;
	}
	
	public void setTransactionAddress(String transactionAddress) {
		this.transactionAddress = transactionAddress;
	}
	
	public String getMerchantName() {
		return merchantName;
	}
	
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getMerchantCode() {
		return merchantCode;
	}
	
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	public String getIPAddress() {
		return IPAddress;
	}
	
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	
	public String getMAC() {
		return MAC;
	}
	
	public void setMAC(String mAC) {
		MAC = mAC;
	}
	
	public String getTellerCode() {
		return tellerCode;
	}
	
	public void setTellerCode(String tellerCode) {
		this.tellerCode = tellerCode;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}