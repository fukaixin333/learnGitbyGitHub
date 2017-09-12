package com.citic.server.cbrc.domain.response;

import java.util.List;

import com.citic.server.cbrc.domain.CBRC_Response;

/**
 * 常规查询请求报文
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午4:43:19
 */
public class CBRC_QueryResponse extends CBRC_Response {
	private static final long serialVersionUID = -6545363988386504224L;
	
	/** 常规查询请求 */
	private List<CBRC_QueryResponse_Main> queryMainList;
	
	public List<CBRC_QueryResponse_Main> getQueryMainList() {
		return queryMainList;
	}
	
	public void setQueryMainList(List<CBRC_QueryResponse_Main> queryMainList) {
		this.queryMainList = queryMainList;
	}

	@Override
	public String toString() {
		return "CBRC_QueryResponse [queryMainList=" + queryMainList + "]";
	}
}
