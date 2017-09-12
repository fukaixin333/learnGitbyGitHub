package com.citic.server.gf.domain.response;

import java.io.Serializable;
import java.util.List;

/**
 * 查询/控制请求涉及的文书信息列表
 * 
 * @author Liu Xuanfei
 * @date 2016年3月9日 下午6:51:40
 */
public class WritResponse implements Serializable {
	private static final long serialVersionUID = 6865807967004972531L;
	
	/** 错误信息描述 */
	private String errorMsg;
	
	/** 查询/控制请求涉及的文书信息列表 */
	private List<WritResponse_WsInfo> wsInfoList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public List<WritResponse_WsInfo> getWsInfoList() {
		return wsInfoList;
	}
	
	public void setWsInfoList(List<WritResponse_WsInfo> wsInfoList) {
		this.wsInfoList = wsInfoList;
	}
}
