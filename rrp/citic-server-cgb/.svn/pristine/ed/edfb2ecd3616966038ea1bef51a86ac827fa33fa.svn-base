package com.citic.server.shpsb.domain;

import java.io.Serializable;

import com.citic.server.runtime.Utility;

/**
 * 首记录
 * 
 * @author Liu Xuanfei
 * @date 2016年11月8日 下午7:58:08
 */
public class ShpsbSjl implements Serializable {
	private static final long serialVersionUID = -2999793997339412654L;
	
	/** 总记录数 */
	private String count;
	
	/** 银行代码 */
	private String yhdm;
	
	/** 操作时间（生成XML文件时间） */
	private String czsj;
	
	public ShpsbSjl() {
		// JiBX unmarshall
	}
	
	public ShpsbSjl(String count, String yhdm) {
		this.count = count;
		this.yhdm = yhdm;
		this.czsj = Utility.currDateTime19();
	}
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	
	public String getCount() {
		return count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	public String getYhdm() {
		return yhdm;
	}
	
	public void setYhdm(String yhdm) {
		this.yhdm = yhdm;
	}
	
	public String getCzsj() {
		return czsj;
	}
	
	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}
}
