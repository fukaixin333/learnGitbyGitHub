package com.citic.server.inner.domain.request;

import com.citic.server.inner.domain.RequestMessage;

/**
 * 合约账号查询（028100）
 * 
 * @author liuxuanfei
 * @date 2016年12月23日 下午5:09:43
 */
public class Input028100 extends RequestMessage {
	private static final long serialVersionUID = 9145438296020282341L;
	
	private String customerNumber; // 客户号
	private String entityType; // 合约实体类型
	private String entityNumber; // 合约实体编号
	private String idType; // 开户证件类型
	private String idNumber; // 开户证件号码
	private String customerName; // 开户名称
	private String fromApp; // 归属应用
	private String queryFlag = "A"; // 查询标识（A-查首层合约账号）
	
	public Input028100() {
	}
	
	public Input028100(String idType, String idNumber, String customerName) {
		this.idType = idType;
		this.idNumber = idNumber;
		this.customerName = customerName;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}
	
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getEntityType() {
		return entityType;
	}
	
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	public String getEntityNumber() {
		return entityNumber;
	}
	
	public void setEntityNumber(String entityNumber) {
		this.entityNumber = entityNumber;
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
	
	public String getFromApp() {
		return fromApp;
	}
	
	public void setFromApp(String fromApp) {
		this.fromApp = fromApp;
	}
	
	public String getQueryFlag() {
		return queryFlag;
	}
	
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
}
