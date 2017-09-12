package com.citic.server.gdjg.domain.request;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

/**
 * 冻结/解冻回执登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:14:25
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Gdjg_RequestDjjdhz extends Gdjg_Request {
	private static final long serialVersionUID = -2364720255444888005L;
	
	/** 协作编号 */
	private String docno;
	
	/** 案件 */
	private List<Gdjg_Request_CaseCx> cases;
	
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_YHDJJDHZ;
	}
}
