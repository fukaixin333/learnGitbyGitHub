package com.citic.server.inner.domain.response;

import java.util.List;

import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ResponseMessage;

public class V_AccountTransaction extends ResponseMessage {
	private static final long serialVersionUID = -8285788818134692386L;
	
	private String accountOpenBranch; // 账号开户行所号
	private String accountOpenBranchName; // 账号开户行所名
	private String accountName; // 账户名
	private List<AccountTransaction> accountTransactionList;
	
	public String getAccountOpenBranch() {
		return accountOpenBranch;
	}
	
	public void setAccountOpenBranch(String accountOpenBranch) {
		this.accountOpenBranch = accountOpenBranch;
	}
	
	public String getAccountOpenBranchName() {
		return accountOpenBranchName;
	}
	
	public void setAccountOpenBranchName(String accountOpenBranchName) {
		this.accountOpenBranchName = accountOpenBranchName;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public List<AccountTransaction> getAccountTransactionList() {
		return accountTransactionList;
	}
	
	public void setAccountTransactionList(List<AccountTransaction> accountTransactionList) {
		this.accountTransactionList = accountTransactionList;
	}
}
