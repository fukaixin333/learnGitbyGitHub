package com.citic.server.dx.domain.request;

import java.io.Serializable;
import java.util.List;

import com.citic.server.dx.domain.Transaction;

/**
 * 可疑名单上报（涉案账户）上行报文 - 账户信息
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午4:04:41
 */
public class SuspiciousRequest_Account implements Serializable {
	private static final long serialVersionUID = 7526854672618341352L;
	
	/**
	 * 定位账户账号<br />
	 * 定位帐号唯一性字段，定义为<strong>[主账户]_[子账户识别号/账户序号/币种号）]</strong>
	 */
	private String accountNumber;
	
	/** 一般（子）账户序号 */
	private String accountSerial;
	
	/** 一般（子）账户类别（根据各银行实际数据反馈） */
	private String accountType;
	
	/** 账户状态（正常，冻结，销户等） */
	private String accountStatus;
	
	/** 币种（CNY-人民币；USD-美元；EUR-欧元；……) */
	private String currency;
	
	/** 钞汇标志 */
	private String cashRemit;
	
	/** 黑名单账户历史（近30天）资金往来（交易）清单（不超过1000笔） */
	private List<SuspiciousRequest_Transaction> transactionList;
	
	// ==========================================================================================
	//                    other
	// ==========================================================================================
   private String accountname="";
   private String cardnumber="";

   public String getCardnumber() {
	return cardnumber;
}

public void setCardnumber(String cardnumber) {
	this.cardnumber = cardnumber;
}

public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

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
	public List<SuspiciousRequest_Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<SuspiciousRequest_Transaction> transactionList) {
		this.transactionList = transactionList;
	}

}
