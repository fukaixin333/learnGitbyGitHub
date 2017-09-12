package com.citic.server.service.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 监听报文表
 * 
 * @author Liu Xuanfei
 * @date 2016年3月29日 下午3:37:27
 */
@Data
public class MP02_rep_org_map implements Serializable {

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 844875473810247704L;

	/** 类型 */
	private String reptype = "";
	
	/** 机构编码 */
	private String organkey = "";
	private String organname="";
	
	private String   report_organkey="";
	private String   operatorname="";
	private String  operatorphonenumber="";
	
	private String  horgankey="";
	private String  horganname="";
	private String  banktype="";
	private String hxcode="";
	private String zhorgankey="";
	
	
}
