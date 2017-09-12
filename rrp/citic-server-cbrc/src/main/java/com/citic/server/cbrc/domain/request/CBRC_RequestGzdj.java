package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 银行账号规则登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:05:01
 */
public class CBRC_RequestGzdj  implements Serializable {
	private static final long serialVersionUID = 8246175193493737886L;
	
	private List<CBRC_RequestGzdj_AccountRule> accountrules;
	
	public List<CBRC_RequestGzdj_AccountRule> getAccountrules() {
		return accountrules;
	}
	
	public void setAccountrules(List<CBRC_RequestGzdj_AccountRule> accountrules) {
		this.accountrules = accountrules;
	}
	

}
