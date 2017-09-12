package com.citic.server.gdjg.domain.request;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

/**
 * 动态数据登记
 * 
 * @author liuxuanfei
 * @date 2017年5月19日 上午9:15:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Gdjg_RequestDtcxdj extends Gdjg_Request {
	private static final long serialVersionUID = 3350121967149475777L;
	/** 协作编号 */
	private String docno;
	
	/** 辅助存档字段 */
	private String helper;
	
	/** 动态数据 */
	private List<Gdjg_Request_TransCx> dyndatas;
	
	public List<Gdjg_Request_TransCx> getDyndatas() {
		return dyndatas;
	}
	
	public void setDyndatas(List<Gdjg_Request_TransCx> dyndatas) {
		this.dyndatas = dyndatas;
	}
	
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_YHDTXXDJ;
	}
}
