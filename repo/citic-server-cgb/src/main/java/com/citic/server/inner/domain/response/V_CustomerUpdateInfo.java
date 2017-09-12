package com.citic.server.inner.domain.response;

import java.util.List;

import com.citic.server.inner.domain.CustomerUpdateInfo;
import com.citic.server.inner.domain.ResponseMessage;

public class V_CustomerUpdateInfo extends ResponseMessage {
	private static final long serialVersionUID = -8285788818134692386L;
	
	private String queryResult; // 查询结果标志（Y-有记录；N-无记录）
	private List<CustomerUpdateInfo> customerUpdateInfoList;
	
	public String getQueryResult() {
		return queryResult;
	}
	
	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}
	
	public List<CustomerUpdateInfo> getCustomerUpdateInfoList() {
		return customerUpdateInfoList;
	}
	
	public void setCustomerUpdateInfoList(List<CustomerUpdateInfo> customerUpdateInfoList) {
		this.customerUpdateInfoList = customerUpdateInfoList;
	}
}
