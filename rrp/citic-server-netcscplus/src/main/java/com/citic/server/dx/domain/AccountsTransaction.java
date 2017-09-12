package com.citic.server.dx.domain;

import java.util.List;

/**
 * 账户及交易流水综合信息
 * 
 * @author Liu Xuanfei
 * @date 2016年5月21日 下午2:09:41
 */
public class AccountsTransaction {
	
	private List<Br24_account_info> accountInfoList;
	private List<Br24_trans_info> transInfoList;
	
	public AccountsTransaction() {
	}
	
	public AccountsTransaction(List<Br24_account_info> accountInfoList, List<Br24_trans_info> transInfoList) {
		this.accountInfoList = accountInfoList;
		this.transInfoList = transInfoList;
	}
	
	public List<Br24_account_info> getAccountInfoList() {
		return accountInfoList;
	}
	
	public void setAccountInfoList(List<Br24_account_info> accountInfoList) {
		this.accountInfoList = accountInfoList;
	}
	
	public List<Br24_trans_info> getTransInfoList() {
		return transInfoList;
	}
	
	public void setTransInfoList(List<Br24_trans_info> transInfoList) {
		this.transInfoList = transInfoList;
	}
}
