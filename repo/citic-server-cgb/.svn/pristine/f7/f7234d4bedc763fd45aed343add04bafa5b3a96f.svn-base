package com.citic.server.shpsb.domain.response;

import java.io.Serializable;
import java.util.List;

import com.citic.server.shpsb.domain.ShpsbSadx;
import com.citic.server.shpsb.domain.ShpsbSjl;

/**
 * 公安请求
 * 
 * @author Liu Xuanfei
 * @date 2016年11月8日 下午8:18:33
 */
public class ShpsbResponse implements Serializable {
	private static final long serialVersionUID = 6525659692110212158L;
	
	/** 首记录 */
	private ShpsbSjl sjl;
	
	/** 涉案对象（多个） */
	private List<ShpsbSadx> sadxList;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	
	public ShpsbSjl getSjl() {
		return sjl;
	}
	
	public void setSjl(ShpsbSjl sjl) {
		this.sjl = sjl;
	}
	
	public List<ShpsbSadx> getSadxList() {
		return sadxList;
	}
	
	public void setSadxList(List<ShpsbSadx> sadxList) {
		this.sadxList = sadxList;
	}
}
