package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 银行网点登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午9:40:54
 */
public class CBRC_RequestWddj  implements Serializable {
	private static final long serialVersionUID = -6134577189334828990L;
	
	private List<CBRC_RequestWddj_Branch> branchs;
	
	public List<CBRC_RequestWddj_Branch> getBranchs() {
		return branchs;
	}
	
	public void setBranchs(List<CBRC_RequestWddj_Branch> branchs) {
		this.branchs = branchs;
	}

}
