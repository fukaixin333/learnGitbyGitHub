package com.citic.server.gdjc.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 商业银行交易流水登记-账户
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午11:14:27
 */
public class Gdjc_RequestLsdj_Acc implements Serializable {
	private static final long serialVersionUID = 2482202488274624349L;
	
	/** 唯一标识 */
	private String uniqueid;
	
	/** 交易流水 */
	private List<Gdjc_RequestLsdj_Trans> translist;

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public List<Gdjc_RequestLsdj_Trans> getTranslist() {
		return translist;
	}

	public void setTranslist(List<Gdjc_RequestLsdj_Trans> translist) {
		this.translist = translist;
	}
	
	
}
