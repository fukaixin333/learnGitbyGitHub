package com.citic.server.inner.domain.request;

import com.citic.server.inner.domain.RequestMessage;

/**
 * 账户信息查询（358080）
 * 
 * @author liuxuanfei
 * @date 2016年12月23日 下午5:43:02
 */
public class Input358080 extends RequestMessage {
	private static final long serialVersionUID = 5213096223441386072L;
	
	private String accountNumber; // 卡号或账号
	private String currency; // 币种
	private String cashExCode; // 钞汇标志
	private String checkPwd = "N"; // 校验密码标识
	private String password; // 密码
	private String checkID = "N"; // 校验证件标识
	private String idType; // 证件类型
	private String idNumber; // 证件号码
	private String checkMag = "N"; // 校验磁条标识
	private String magneticStripe2; // 第二磁道信息
	private String magneticStripe3; // 第三磁道信息
	private String checkName = "N"; // 校验户名标识
	private String name; // 户名
	private String queryBalance = "Y"; // 查询余额标识
	
	public Input358080() {
	}
	
	public Input358080(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public Input358080(String accountNumber, String currency, String cashExCode) {
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.cashExCode = cashExCode;
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
	
	public String getCheckPwd() {
		return checkPwd;
	}
	
	public void setCheckPwd(String checkPwd) {
		this.checkPwd = checkPwd;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCheckID() {
		return checkID;
	}
	
	public void setCheckID(String checkID) {
		this.checkID = checkID;
	}
	
	public void setCheckID(boolean bool) {
		if (bool) {
			this.checkID = "Y";
		} else {
			this.checkID = "N";
		}
	}
	
	public String getIdType() {
		return idType;
	}
	
	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public String getIdNumber() {
		return idNumber;
	}
	
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getCheckMag() {
		return checkMag;
	}
	
	public void setCheckMag(String checkMag) {
		this.checkMag = checkMag;
	}
	
	public String getMagneticStripe2() {
		return magneticStripe2;
	}
	
	public void setMagneticStripe2(String magneticStripe2) {
		this.magneticStripe2 = magneticStripe2;
	}
	
	public String getMagneticStripe3() {
		return magneticStripe3;
	}
	
	public void setMagneticStripe3(String magneticStripe3) {
		this.magneticStripe3 = magneticStripe3;
	}
	
	public String getCheckName() {
		return checkName;
	}
	
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
	public void setCheckName(boolean bool) {
		if (bool) {
			this.checkName = "Y";
		} else {
			this.checkName = "N";
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getQueryBalance() {
		return queryBalance;
	}
	
	public void setQueryBalance(String queryBalance) {
		this.queryBalance = queryBalance;
	}
	
	public boolean isQueryBalance() {
		if (queryBalance == null || queryBalance.length() == 0) {
			return false;
		}
		return "Y".equals(queryBalance);
	}
	
	public void setQueryBalance(boolean bool) {
		if (bool) {
			this.queryBalance = "Y";
		} else {
			this.queryBalance = "N";
		}
	}
}
