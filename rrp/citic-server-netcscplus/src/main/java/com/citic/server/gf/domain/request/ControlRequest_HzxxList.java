package com.citic.server.gf.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 法院提供的司法控制反馈内容列表
 * 
 * @author Liu Xuanfei
 * @date 2016年3月9日 下午6:43:34
 */
public class ControlRequest_HzxxList implements Serializable {
	private static final long serialVersionUID = -2267265313690266924L;

	/** 回执文书信息 */
	private List<ControlRequest_Hzxx> hzxxList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	
	public List<ControlRequest_Hzxx> getHzxxList() {
		return hzxxList;
	}
	
	public void setHzxxList(List<ControlRequest_Hzxx> hzxxList) {
		this.hzxxList = hzxxList;
	}
}
