package com.citic.server.shpsb.domain.request;

import java.io.Serializable;
import java.util.List;

import com.citic.server.shpsb.domain.ShpsbSjl;

/**
 * 交易关联号查询反馈
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 上午10:44:02
 */
public class ShpsbRequestJyglh implements Serializable {
	private static final long serialVersionUID = 8544111509102922508L;
	
	/** 首记录 */
	private ShpsbSjl sjl;
	
	/** 明细记录 */
	private List<ShpsbRequestJyglhMxjl> mxjlList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	
	public ShpsbSjl getSjl() {
		return sjl;
	}
	
	public void setSjl(ShpsbSjl sjl) {
		this.sjl = sjl;
	}
	
	public List<ShpsbRequestJyglhMxjl> getMxjlList() {
		return mxjlList;
	}
	
	public void setMxjlList(List<ShpsbRequestJyglhMxjl> mxjlList) {
		this.mxjlList = mxjlList;
	}
}
