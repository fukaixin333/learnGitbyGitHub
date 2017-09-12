package com.citic.server.dx.domain.request;

import java.io.Serializable;

/**
 * 可以名单上报（异常开卡）上行报文-卡/折信息
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午4:04:41
 */
public class SuspiciousRequest_Accounts implements Serializable {
	private static final long serialVersionUID = 3973932749094565144L;
	
	/** 卡/折号 */
	private String cardNumber;
	
	/** 开卡时间 */
	private String accountOpenTime;
	
	/** 开卡地点 */
	private String accountOpenPlace;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	private String open_organkey;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getAccountOpenTime() {
		return accountOpenTime;
	}
	
	public void setAccountOpenTime(String accountOpenTime) {
		this.accountOpenTime = accountOpenTime;
	}
	
	public String getAccountOpenPlace() {
		return accountOpenPlace;
	}
	
	public void setAccountOpenPlace(String accountOpenPlace) {
		this.accountOpenPlace = accountOpenPlace;
	}
	
	public String getOpen_organkey() {
		return open_organkey;
	}
	
	public void setOpen_organkey(String open_organkey) {
		this.open_organkey = open_organkey;
	}
}
