package com.citic.server.gdjg.domain.request;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;


/**
 * 保险箱登记
 * 
 * @author liuxuanfei
 * @date 2017年5月19日 上午9:17:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Gdjg_RequestBxxdj extends Gdjg_Request{
	private static final long serialVersionUID = -1906067743258815752L;

	/** 协作编号 */
	private String docno;
	
	/** 案件信息*/
	private List<Gdjg_Request_CaseCx> cases;
 
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_YHBXXDJ;
	}
}
