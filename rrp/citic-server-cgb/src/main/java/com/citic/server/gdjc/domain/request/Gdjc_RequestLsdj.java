package com.citic.server.gdjc.domain.request;

import java.util.List;

import com.citic.server.gdjc.GdjcConstants;
import com.citic.server.gdjc.domain.Gdjc_Request;

/**
 * 商业银行交易流水登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:59:39
 */
public class Gdjc_RequestLsdj extends Gdjc_Request {
	private static final long serialVersionUID = 7126195288849975028L;
	
	/** 协作编号 */
	private String docno;
	
	/** 案件 */
	private List<Gdjc_RequestLsdj_Case> cases;
	
	public String getDocno() {
		return docno;
	}
	
	public void setDocno(String docno) {
		this.docno = docno;
	}
	
	public List<Gdjc_RequestLsdj_Case> getCases() {
		return cases;
	}
	
	public void setCases(List<Gdjc_RequestLsdj_Case> cases) {
		this.cases = cases;
	}
	
	@Override
	public String getContent() {
		return GdjcConstants.DATA_CONTENT_LSDJ;
	}
}
