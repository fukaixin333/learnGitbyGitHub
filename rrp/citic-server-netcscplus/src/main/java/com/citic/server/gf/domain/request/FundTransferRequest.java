package com.citic.server.gf.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 资金往来（交易）信息单独反馈
 * 
 * @author Liu Xuanfei
 * @date 2016年4月27日 上午10:07:56
 */
public class FundTransferRequest implements Serializable {
	private static final long serialVersionUID = 7583581192377825304L;
	
	private List<QueryRequest_Wlxx> wlxxList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public List<QueryRequest_Wlxx> getWlxxList() {
		return wlxxList;
	}
	
	public void setWlxxList(List<QueryRequest_Wlxx> wlxxList) {
		this.wlxxList = wlxxList;
	}
}
