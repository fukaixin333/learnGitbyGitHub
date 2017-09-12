package com.citic.server.shpsb.domain.request;

import java.io.Serializable;
import java.util.List;

import com.citic.server.shpsb.domain.ShpsbSjl;

/**
 * 对端账号查询反馈
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 上午10:55:55
 */
public class ShpsbRequestDdzh implements Serializable {
	private static final long serialVersionUID = -8825409338273875852L;
	
	/** 首记录 */
	private ShpsbSjl sjl;
	
	/** 明细记录 */
	private List<ShpsbRequestDdzhMxjl> mxjlList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	
	public ShpsbSjl getSjl() {
		return sjl;
	}
	
	public void setSjl(ShpsbSjl sjl) {
		this.sjl = sjl;
	}
	
	public List<ShpsbRequestDdzhMxjl> getMxjlList() {
		return mxjlList;
	}
	
	public void setMxjlList(List<ShpsbRequestDdzhMxjl> mxjlList) {
		this.mxjlList = mxjlList;
	}
}
