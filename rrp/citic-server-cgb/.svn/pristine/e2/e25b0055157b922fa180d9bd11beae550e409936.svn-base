package com.citic.server.gdjg.domain.request;

import java.util.List;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Gdjg_Request;

/**
 * 城市代号对照登记
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:14:25
 */
public class Gdjg_RequestDhdj extends Gdjg_Request {
	private static final long serialVersionUID = -819138972221962793L;
	
	private List<Gdjg_RequestDhdj_City> citys;
	
	public List<Gdjg_RequestDhdj_City> getCitys() {
		return citys;
	}
	
	public void setCitys(List<Gdjg_RequestDhdj_City> citys) {
		this.citys = citys;
	}
	
	@Override
	public String getContent() {
		return GdjgConstants.DATA_CONTENT_DHDJ;
	}
}
