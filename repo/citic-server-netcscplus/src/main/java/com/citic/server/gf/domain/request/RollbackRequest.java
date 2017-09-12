package com.citic.server.gf.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 回退信息报文
 * 
 * @author dingke
 * @date 2016年3月8日 下午1:44:08
 */
public class RollbackRequest implements Serializable {
	private static final long serialVersionUID = -8576320818209114870L;
	
	/** 回退信息列表 */
	private List<RollbackRequest_Htxx> htxxList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public List<RollbackRequest_Htxx> getHtxxList() {
		return htxxList;
	}
	
	public void setHtxxList(List<RollbackRequest_Htxx> htxxList) {
		this.htxxList = htxxList;
	}
}
