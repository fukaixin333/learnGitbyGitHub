package com.citic.server.gf.domain.response;

import java.io.Serializable;
import java.util.List;

/**
 * 司法控制请求信息列表
 * 
 * @author Liu Xuanfei
 * @date 2016年3月9日 下午6:45:20
 */
public class ControlResponse implements Serializable {
	private static final long serialVersionUID = 2499186846800678088L;
	
	/** 错误信息描述 */
	private String errorMsg;
	
	/** 司法控制请求信息列表 */
	private List<ControlResponse_Kzqq> kzqqList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public List<ControlResponse_Kzqq> getKzqqList() {
		return kzqqList;
	}
	
	public void setKzqqList(List<ControlResponse_Kzqq> kzqqList) {
		this.kzqqList = kzqqList;
	}
}
