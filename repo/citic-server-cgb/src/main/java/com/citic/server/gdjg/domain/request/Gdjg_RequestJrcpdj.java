package com.citic.server.gdjg.domain.request;

import java.util.List;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

/**
 * 金融产品登记
 * 
 * @author liuxuanfei
 * @date 2017年5月19日 上午9:06:15
 */

public class Gdjg_RequestJrcpdj extends Gdjg_Request {
	private static final long serialVersionUID = -3748033012548306726L;
	
	/** 协作编号 */
	private String docno;
	
	/** 案件 */
	private List<Gdjg_Request_CaseCx> cases;
	
	public String getDocno() {
		return docno;
	}
	
	public void setDocno(String docno) {
		this.docno = docno;
	}

	public List<Gdjg_Request_CaseCx> getCases() {
		return cases;
	}

	public void setCases(List<Gdjg_Request_CaseCx> cases) {
		this.cases = cases;
	}
			
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_YHJRCPDJ;
	}
}
