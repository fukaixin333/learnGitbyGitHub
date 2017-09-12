package com.citic.server.gdjg.domain.response;

import java.util.List;

import com.citic.server.gdjg.domain.Gdjg_Response;

/**
 * 商业银行存款解冻申请
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午11:27:16
 */
public class Gdjg_ResponseCkjdkz extends Gdjg_Response{
	private static final long serialVersionUID = 2866874678064326150L;

	/** 协作编号 */
	private String docno;
	
	/** 案件 */
	private List<Gdjg_Response_CaseCx> cases;
	
	public String getDocno() {
		return docno;
	}
	
	public void setDocno(String docno) {
		this.docno = docno;
	}
	
	public List<Gdjg_Response_CaseCx> getCases() {
		return cases;
	}
	
	public void setCases(List<Gdjg_Response_CaseCx> cases) {
		this.cases = cases;
	}
}
