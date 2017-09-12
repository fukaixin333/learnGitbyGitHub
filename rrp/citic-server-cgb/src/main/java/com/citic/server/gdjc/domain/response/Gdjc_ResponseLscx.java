package com.citic.server.gdjc.domain.response;

import java.util.List;

import com.citic.server.gdjc.domain.Gdjc_Response;

/**
 * 商业银行交易流水查询
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午11:41:31
 */
public class Gdjc_ResponseLscx extends Gdjc_Response {
	private static final long serialVersionUID = 962159862783202976L;
	
	/** 协作编号 */
	private String docno;
	
	/** 案件 */
	private List<Gdjc_ResponseLscx_Case> cases;
	
	public String getDocno() {
		return docno;
	}
	
	public void setDocno(String docno) {
		this.docno = docno;
	}
	
	public List<Gdjc_ResponseLscx_Case> getCases() {
		return cases;
	}
	
	public void setCases(List<Gdjc_ResponseLscx_Case> cases) {
		this.cases = cases;
	}
}
