package com.citic.server.gdjc.domain.request;

import java.util.List;

import com.citic.server.gdjc.GdjcConstants;
import com.citic.server.gdjc.domain.Gdjc_Request;

/**
 * 城市代号对照登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:14:25
 */
public class Gdjc_RequestDhdj extends Gdjc_Request {
	private static final long serialVersionUID = -819138972221962793L;
	
	private List<Gdjc_RequestDhdj_City> citys;
	
	public List<Gdjc_RequestDhdj_City> getCitys() {
		return citys;
	}
	
	public void setCitys(List<Gdjc_RequestDhdj_City> citys) {
		this.citys = citys;
	}
	
	@Override
	public String getContent() {
		return GdjcConstants.DATA_CONTENT_DHDJ;
	}
}
