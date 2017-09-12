package com.citic.server.shpsb.domain.request;

import java.io.Serializable;
import java.util.List;

import com.citic.server.shpsb.domain.ShpsbSjl;

/**
 * 账号持有人资料查询反馈
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 上午9:48:30
 */
public class ShpsbRequestZhcyr implements Serializable {
	private static final long serialVersionUID = 6837545419005100331L;
	
	/** 首记录 */
	private ShpsbSjl sjl;
	
	/** 明细记录 */
	private List<ShpsbRequestZhcyrMxjl> mxjlList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	
	public ShpsbSjl getSjl() {
		return sjl;
	}
	
	public void setSjl(ShpsbSjl sjl) {
		this.sjl = sjl;
	}
	
	public List<ShpsbRequestZhcyrMxjl> getMxjlList() {
		return mxjlList;
	}
	
	public void setMxjlList(List<ShpsbRequestZhcyrMxjl> mxjlList) {
		this.mxjlList = mxjlList;
	}
}
