package com.citic.server.inner.domain.request;

import com.citic.server.inner.domain.RequestMessage;

/**
 * 共有权/优先权信息
 */
public class Input265561 extends RequestMessage {
	private static final long serialVersionUID = 1L;
	
	private String accountNumber; // 账号
	private String accountSeq; // 账户序号
	private String bankVersionNo; //定期凭证号
	
	public Input265561() {
	}
	
	public Input265561(String accountNumber, String accountSeq) {
		this.accountNumber = accountNumber;
		this.accountSeq = accountSeq;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getAccountSeq() {
		return accountSeq;
	}
	
	public void setAccountSeq(String accountSeq) {
		this.accountSeq = accountSeq;
	}
	
	public String getBankVersionNo() {
		return bankVersionNo;
	}
	
	public void setBankVersionNo(String bankVersionNo) {
		this.bankVersionNo = bankVersionNo;
	}
}
