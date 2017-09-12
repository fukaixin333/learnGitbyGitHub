package com.citic.server.gdjg.domain.request;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

/**
 * 商业银行存款登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:31:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Gdjg_RequestCkdj extends Gdjg_Request {
	private static final long serialVersionUID = 5143355439737473256L;
	
	/** 协作编号 */
	private String docno;
	
	/** 案件 */
	private List<Gdjg_Request_CaseCx> cases;
	
	/** 交易明细 */
	private List<Gdjg_RequestLsdj> lsdjs;
	
	
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_CKDJ;
	}
}
