package com.citic.server.gdjc.domain.request;

import java.util.List;

import com.citic.server.gdjc.GdjcConstants;
import com.citic.server.gdjc.domain.Gdjc_Request;

/**
 * 商业银行存款登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:31:04
 */
public class Gdjc_RequestCkdj extends Gdjc_Request {
	private static final long serialVersionUID = 5143355439737473256L;
	
	/** 协作编号 */
	private String docno;
	
	/** 案件 */
	private List<Gdjc_RequestCkdj_Case> cases;
	
	public String getDocno() {
		return docno;
	}
	
	public void setDocno(String docno) {
		this.docno = docno;
	}
	
	public List<Gdjc_RequestCkdj_Case> getCases() {
		return cases;
	}
	
	public void setCases(List<Gdjc_RequestCkdj_Case> cases) {
		this.cases = cases;
	}
	
	@Override
	public String getContent() {
		return GdjcConstants.DATA_CONTENT_CKDJ;
	}
}
