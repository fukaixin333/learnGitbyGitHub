package com.citic.server.inner.domain.request;

import com.citic.server.inner.domain.RequestMessage;

/**
 * 冻结在线查询（267570）
 * 
 * @author liuxuanfei
 * @date 2016年12月23日 下午6:23:11
 */
public class Input267570 extends RequestMessage {
	private static final long serialVersionUID = 911988725916420403L;
	
	private String accountNumber;
	private String subAccountSeq;
	private String frozenStatus = "N"; // 冻结状态（N-冻结；C-解除）
	
	public Input267570() {
	}
	
	public Input267570(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getSubAccountSeq() {
		return subAccountSeq;
	}
	
	public void setSubAccountSeq(String subAccountSeq) {
		this.subAccountSeq = subAccountSeq;
	}
	
	public String getFrozenStatus() {
		return frozenStatus;
	}
	
	public void setFrozenStatus(String frozenStatus) {
		this.frozenStatus = frozenStatus;
	}
}
