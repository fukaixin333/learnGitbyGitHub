package com.citic.server.gdjc.domain.request;

import java.util.List;

import com.citic.server.gdjc.GdjcConstants;
import com.citic.server.gdjc.domain.Gdjc_Request;

/**
 * 银行网点登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午9:40:54
 */
public class Gdjc_RequestWddj extends Gdjc_Request {
	private static final long serialVersionUID = -6134577189334828990L;
	
	private List<Gdjc_RequestWddj_Branch> branchs;
	
	public List<Gdjc_RequestWddj_Branch> getBranchs() {
		return branchs;
	}
	
	public void setBranchs(List<Gdjc_RequestWddj_Branch> branchs) {
		this.branchs = branchs;
	}
	
	@Override
	public String getContent() {
		return GdjcConstants.DATA_CONTENT_WDDJ;
	}
}
