package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 城市代号对照登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:14:25
 */
public class CBRC_RequestDhdj implements Serializable {
	private static final long serialVersionUID = -819138972221962793L;
	
	private List<CBRC_RequestDhdj_City> citys;
	
	public List<CBRC_RequestDhdj_City> getCitys() {
		return citys;
	}
	
	public void setCitys(List<CBRC_RequestDhdj_City> citys) {
		this.citys = citys;
	}

}
