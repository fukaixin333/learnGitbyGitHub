package com.citic.server.inner.domain.request;

import com.citic.server.inner.domain.RequestMessage;

/**
 * 核心接口 - 账户冻结、止付（输入）
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午4:49:10
 */
public class Input267500 extends RequestMessage {
	private static final long serialVersionUID = -4435049002073650319L;
	
	private String accountNumber; // 账号
	private String accountSeq; // 账户序号
	private String chequeNumber; // 支票号码
	private String freezeType; // 冻结方式（1-账户冻结，2-金额冻结，3-圈存）
	private String freezeInsType = "1"; // 发起机构类型（1-权利机关，2-银行内部）
	private String currency; // 货币
	private String cashExCode; // 钞汇标识
	private String freezeAmount; // 应冻结金额
	private String freezeBookNumber; // 冻结文书号
	private String freezeInsName; // 冻结机构名称
	private String freezeReason; // 冻结原因
	private String effectiveDate; // 生效日期
	private String expiringDate; // 到期日期
	private String remark; // 备注
	private String freezeBranch; // 发起行所
	
	private String lawName1; // 执法人名称1
	private String lawIDNumber1; // 执法人证件号1
	private String lawName2; // 执法人名称2
	private String lawIDNumber2; // 执法人证件号2
	
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
	
	public String getChequeNumber() {
		return chequeNumber;
	}
	
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	
	public String getFreezeType() {
		return freezeType;
	}
	
	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}
	
	public String getFreezeInsType() {
		return freezeInsType;
	}
	
	public void setFreezeInsType(String freezeInsType) {
		this.freezeInsType = freezeInsType;
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
	
	public String getFreezeAmount() {
		return freezeAmount;
	}
	
	public void setFreezeAmount(String freezeAmount) {
		this.freezeAmount = freezeAmount;
	}
	
	public String getFreezeBookNumber() {
		return freezeBookNumber;
	}
	
	public void setFreezeBookNumber(String freezeBookNumber) {
		this.freezeBookNumber = freezeBookNumber;
	}
	
	public String getFreezeInsName() {
		return freezeInsName;
	}
	
	public void setFreezeInsName(String freezeInsName) {
		this.freezeInsName = freezeInsName;
	}
	
	public String getFreezeReason() {
		return freezeReason;
	}
	
	public void setFreezeReason(String freezeReason) {
		this.freezeReason = freezeReason;
	}
	
	public String getEffectiveDate() {
		return effectiveDate;
	}
	
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public String getExpiringDate() {
		return expiringDate;
	}
	
	public void setExpiringDate(String expiringDate) {
		this.expiringDate = expiringDate;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getFreezeBranch() {
		return freezeBranch;
	}
	
	public void setFreezeBranch(String freezeBranch) {
		this.freezeBranch = freezeBranch;
	}
	
	public String getLawName1() {
		return lawName1;
	}
	
	public void setLawName1(String lawName1) {
		this.lawName1 = lawName1;
	}
	
	public String getLawIDNumber1() {
		return lawIDNumber1;
	}
	
	public void setLawIDNumber1(String lawIDNumber1) {
		this.lawIDNumber1 = lawIDNumber1;
	}
	
	public String getLawName2() {
		return lawName2;
	}
	
	public void setLawName2(String lawName2) {
		this.lawName2 = lawName2;
	}
	
	public String getLawIDNumber2() {
		return lawIDNumber2;
	}
	
	public void setLawIDNumber2(String lawIDNumber2) {
		this.lawIDNumber2 = lawIDNumber2;
	}
}
