package com.citic.server.inner.domain.request;

import com.citic.server.inner.domain.RequestMessage;

public class Input025890 extends RequestMessage {
	private static final long serialVersionUID = 4965396563872348943L;
	
	private String queryType = "C"; // 查询类型（I-查询证件；N-查询名称；C-查询联系方式；A-查询地址）
	private String queryCase = "00000114"; // 查询情形
	private String customerNumber; // 客户编号
	private String accountNumber; // 账号或卡号
	private String idType; // 证件类型
	private String idNumber; // 证件号码
	private String customerName; // 客户名称
	private String beginDate; // 起始时间
	private String endDate; // 结束日期
	
	public Input025890() {
	}
	
	public Input025890(String accountNumber, String beginDate, String endDate) {
		this.accountNumber = accountNumber;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	
	public String getQueryType() {
		return queryType;
	}
	
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	public String getQueryCase() {
		return queryCase;
	}
	
	public void setQueryCase(String queryCase) {
		this.queryCase = queryCase;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}
	
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBeginDate() {
		return beginDate;
	}
	
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
