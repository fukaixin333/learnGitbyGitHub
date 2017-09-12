package com.citic.server.dx.domain.request;

import com.citic.server.dx.domain.Transaction;

/**
 * @author Liu Xuanfei
 * @date 2016年4月25日 下午3:34:00
 */
public class CaseRequest_Transaction extends Transaction {
	private static final long serialVersionUID = 6612995538957206453L;
	
	/** 是否已止付(0-已止付；1-未止付) */
	private String isCeased;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getIsCeased() {
		return isCeased;
	}
	
	public void setIsCeased(String isCeased) {
		this.isCeased = isCeased;
	}
}
