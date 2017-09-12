package com.citic.server.gdjg.domain.request;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

/**
 * 动态回执登记
 * 
 * @author liuxuanfei
 * @date 2017年5月19日 上午10:21:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Gdjg_RequestDtcxhz extends Gdjg_Request {
	private static final long serialVersionUID = -3048534783401448746L;
	
	/** 协助编号 */
	private String docno;
	
	/** 案件信息 */
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
		return GdjgConstants.DATA_CONTENT_YHDTCXHZ;
	}
	
}
