package com.citic.server.shpsb.domain.request;

import java.io.Serializable;
import java.util.List;

import com.citic.server.shpsb.domain.ShpsbSadx;

/**
 * 账号持有人资料查询反馈 - 明细记录
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 上午9:50:16
 */
public class ShpsbRequestZhcyrMxjl implements Serializable {
	private static final long serialVersionUID = -8110425069395654127L;
	
	/** 涉案对象 */
	private ShpsbSadx sadx;
	
	/** 账号持卡人资料 */
	private List<ShpsbRequestZhcyrMx> cyrzlList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	
	public ShpsbSadx getSadx() {
		return sadx;
	}
	
	public void setSadx(ShpsbSadx sadx) {
		this.sadx = sadx;
	}
	
	public List<ShpsbRequestZhcyrMx> getCyrzlList() {
		return cyrzlList;
	}
	
	public void setCyrzlList(List<ShpsbRequestZhcyrMx> cyrzlList) {
		this.cyrzlList = cyrzlList;
	}
}
