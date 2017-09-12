package com.citic.server.dx.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 异常事件卡信息
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午4:04:41
 */
public class ExceptionEventRequest_Accounts implements Serializable {
	private static final long serialVersionUID = 3973932749094565144L;
	
	private String accountName;
	private String cardNumber;
	private String remark;
	private List<SuspiciousRequest_Account> accountList;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
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
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<SuspiciousRequest_Account> getAccountList() {
		return accountList;
	}
	
	public void setAccountList(List<SuspiciousRequest_Account> accountList) {
		this.accountList = accountList;
	}
	
}
