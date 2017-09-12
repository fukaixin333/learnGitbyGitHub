package com.citic.server.cbrc.domain.response;

import java.util.List;

import com.citic.server.cbrc.domain.CBRC_Response;

/**
 * 凭证图像查询请求报文
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午5:49:11
 */
public class CBRC_QueryScanResponse extends CBRC_Response {
	private static final long serialVersionUID = -2950483639877987632L;
	
	/** 凭证图像请求信息 */
	private List<CBRC_QueryScanResponse_Info> scanInfoList;
	
	public List<CBRC_QueryScanResponse_Info> getScanInfoList() {
		return scanInfoList;
	}
	
	public void setScanInfoList(List<CBRC_QueryScanResponse_Info> scanInfoList) {
		this.scanInfoList = scanInfoList;
	}
}
