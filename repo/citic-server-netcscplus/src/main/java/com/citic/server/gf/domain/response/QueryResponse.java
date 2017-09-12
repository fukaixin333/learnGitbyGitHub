package com.citic.server.gf.domain.response;

import java.io.Serializable;
import java.util.List;

/**
 * 法院提供的司法查询请求内容列表
 * 
 * @author Liu Xuanfei
 * @date 2016年3月9日 下午6:43:34
 */
public class QueryResponse implements Serializable {
	private static final long serialVersionUID = -7907742480797366657L;
	
	/** 错误信息描述 */
	private String errorMsg;
	
	/** 法院提供的司法查询请求内容列表 */
	private List<QueryResponse_Cxqq> cxqqList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public List<QueryResponse_Cxqq> getCxqqList() {
		return cxqqList;
	}
	
	public void setCxqqList(List<QueryResponse_Cxqq> cxqqList) {
		this.cxqqList = cxqqList;
	}
}