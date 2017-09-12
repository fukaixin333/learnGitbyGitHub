package com.citic.server.gdjg.domain.response;

import java.util.List;

import com.citic.server.gdjg.domain.Gdjg_Response;

/**
 * 金融产品查询
 * 
 * @author liuxuanfei
 * @date 2017年5月18日 下午7:52:56
 */

public class Gdjg_ResponseJrcpcx extends Gdjg_Response {
	private static final long serialVersionUID = 8714768900659585177L;

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
