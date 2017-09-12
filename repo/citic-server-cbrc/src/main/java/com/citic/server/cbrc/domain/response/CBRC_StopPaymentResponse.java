package com.citic.server.cbrc.domain.response;

import java.util.List;

import com.citic.server.cbrc.domain.CBRC_Response;

/**
 * 紧急止付/解除止付请求报文
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午5:51:29
 */
public class CBRC_StopPaymentResponse extends CBRC_Response {
	private static final long serialVersionUID = -6389903838875739984L;
	
	/** 紧急止付/解除止付请求信息 */
	private List<CBRC_StopPaymentResponse_Account> stopPaymentAccountList;
	
	public List<CBRC_StopPaymentResponse_Account> getStopPaymentAccountList() {
		return stopPaymentAccountList;
	}
	
	public void setStopPaymentAccountList(List<CBRC_StopPaymentResponse_Account> stopPaymentAccountList) {
		this.stopPaymentAccountList = stopPaymentAccountList;
	}
}
