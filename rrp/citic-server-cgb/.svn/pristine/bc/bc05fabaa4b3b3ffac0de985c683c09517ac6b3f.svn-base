package com.citic.server.gdjg.domain.request;

import java.util.List;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

/**
 * 银行账号规则登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:05:01
 */
public class Gdjg_RequestGzdj extends Gdjg_Request {
	private static final long serialVersionUID = 8246175193493737886L;
	
	private List<Gdjg_RequestGzdj_AccountRule> accountrules;
	
	public List<Gdjg_RequestGzdj_AccountRule> getAccountrules() {
		return accountrules;
	}
	
	public void setAccountrules(List<Gdjg_RequestGzdj_AccountRule> accountrules) {
		this.accountrules = accountrules;
	}
	
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_GZDJ;
	}
}
