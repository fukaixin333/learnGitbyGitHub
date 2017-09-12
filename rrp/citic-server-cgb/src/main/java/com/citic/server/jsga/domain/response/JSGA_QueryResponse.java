package com.citic.server.jsga.domain.response;

import java.util.List;

import com.citic.server.jsga.domain.JSGA_Response;

/**
 * 常规查询请求报文
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午4:43:19
 */
public class JSGA_QueryResponse extends JSGA_Response {
	private static final long serialVersionUID = -654536386504224L;
	
	/** 常规查询请求 */
	private List<JSGA_QueryResponse_Main> queryMainList;
	
	public List<JSGA_QueryResponse_Main> getQueryMainList() {
		return queryMainList;
	}
	
	public void setQueryMainList(List<JSGA_QueryResponse_Main> queryMainList) {
		this.queryMainList = queryMainList;
	}

	@Override
	public String toString() {
		return "JSGA_QueryResponse [queryMainList=" + queryMainList + "]";
	}
}
