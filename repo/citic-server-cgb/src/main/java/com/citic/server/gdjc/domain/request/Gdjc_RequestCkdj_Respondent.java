package com.citic.server.gdjc.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 商业银行存款登记-被调查人
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:33:36
 */
public class Gdjc_RequestCkdj_Respondent implements Serializable {
	private static final long serialVersionUID = -8744678362653310219L;
	
	/** 唯一标识 */
	private String uniqueid;
	
	/** 开户行 */
	private List<Gdjc_RequestCkdj_Bank> banks;
	
	public String getUniqueid() {
		return uniqueid;
	}
	
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}
	
	public List<Gdjc_RequestCkdj_Bank> getBanks() {
		return banks;
	}
	
	public void setBanks(List<Gdjc_RequestCkdj_Bank> banks) {
		this.banks = banks;
	}
}
