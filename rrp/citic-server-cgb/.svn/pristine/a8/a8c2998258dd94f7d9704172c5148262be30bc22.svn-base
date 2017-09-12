package com.citic.server.gdjc.domain.request;

import java.util.List;

import com.citic.server.gdjc.GdjcConstants;
import com.citic.server.gdjc.domain.Gdjc_Request;

/**
 * 银行账号规则登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:05:01
 */
public class Gdjc_RequestGzdj extends Gdjc_Request {
	private static final long serialVersionUID = 8246175193493737886L;
	
	private List<Gdjc_RequestGzdj_AccountRule> accountrules;
	
	public List<Gdjc_RequestGzdj_AccountRule> getAccountrules() {
		return accountrules;
	}
	
	public void setAccountrules(List<Gdjc_RequestGzdj_AccountRule> accountrules) {
		this.accountrules = accountrules;
	}
	
	@Override
	public String getContent() {
		return GdjcConstants.DATA_CONTENT_GZDJ;
	}
}
