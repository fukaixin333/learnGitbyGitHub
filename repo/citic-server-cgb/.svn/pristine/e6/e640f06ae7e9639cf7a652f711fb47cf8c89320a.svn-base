package com.citic.server.gdjg.domain.request;


import java.util.List;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 商业银行存款解冻登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午11:27:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Gdjg_RequestCkjddj extends Gdjg_Request{
	private static final long serialVersionUID = -1103258143315691428L;

	/** 协作编号 */
	private String docno;
	
	/** 案件 */
	private List<Gdjg_Request_CaseCx> cases;
	
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_YHCKJDDJ;
	}
}
