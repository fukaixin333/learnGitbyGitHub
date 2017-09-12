package com.citic.server.cbrc.domain.response;

import java.util.List;

import com.citic.server.cbrc.domain.CBRC_Response;

/**
 * 动态查询请求报文
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午5:22:21
 */
public class CBRC_ControlResponse extends CBRC_Response {
	private static final long serialVersionUID = -2087416706549404747L;
	
	/** 动态查询请求信息 */
	private List<CBRC_ControlResponse_Account> controlAccountList;
	
	public List<CBRC_ControlResponse_Account> getControlAccountList() {
		return controlAccountList;
	}
	
	public void setControlAccountList(List<CBRC_ControlResponse_Account> controlAccountList) {
		this.controlAccountList = controlAccountList;
	}
}
