package com.citic.server.gdjg.domain.request;

import java.util.List;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

/**
 * 银行网点登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午9:40:54
 */
public class Gdjg_RequestWddj extends Gdjg_Request {
	private static final long serialVersionUID = -6134577189334828990L;
	
	private List<Gdjg_RequestWddj_Branch> branchs;
	
	public List<Gdjg_RequestWddj_Branch> getBranchs() {
		return branchs;
	}
	
	public void setBranchs(List<Gdjg_RequestWddj_Branch> branchs) {
		this.branchs = branchs;
	}
	
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_WDDJ;
	}
}
