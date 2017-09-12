package com.citic.server.gdjg.domain.request;

import java.util.List;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

/**
 * 商业银行交易流水登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:59:39
 */
public class Gdjg_RequestLsdj extends Gdjg_Request {
	private static final long serialVersionUID = 7126195288849975028L;
	
	/** 协作编号 */
	private String docno;
	/** 业务类型 */
	private String content;
	/** 查询结束时间 */
	private String queryendtime;
	/** 查询反馈结果 */
	private String queryresult;
	/** 查询反馈结果原因 */
	private String reason;
	/** 主机查询时间 */
	private String querytime;
	/** 交易流水信息 */
	private List<Gdjg_Request_TransCx> trans;
	
	
	public String getDocno() {
		return docno;
	}
	
	public void setDocno(String docno) {
		this.docno = docno;
	}
	
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getQueryendtime() {
		return queryendtime;
	}
	
	public void setQueryendtime(String queryendtime) {
		this.queryendtime = queryendtime;
	}
	
	public String getQueryresult() {
		return queryresult;
	}
	
	public void setQueryresult(String queryresult) {
		this.queryresult = queryresult;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getQuerytime() {
		return querytime;
	}
	
	public void setQuerytime(String querytime) {
		this.querytime = querytime;
	}

	public List<Gdjg_Request_TransCx> getTrans() {
		return trans;
	}

	public void setTrans(List<Gdjg_Request_TransCx> trans) {
		this.trans = trans;
	}
	
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_LSDJ;
	}
}
