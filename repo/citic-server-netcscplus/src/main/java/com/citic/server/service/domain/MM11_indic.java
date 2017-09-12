package com.citic.server.service.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MM11_indic implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	

	private String indickey   = "";
	private String indicname  = "";
	private String indicdes   = "";
	private String freq       = "";
	private String indicattr  = "";
	private String indictype  = "";//分任务依据
	private String facttable  = "";
	private String savedays   = "";
	private String callevel   = "";//分任务依据
	private String flag       = "";
	private String creator    = "";
	private String create_dt  = "";
	
	private String isjl  		= ""; //1-是聚类算法
	private String region_num  = "";  //区间个数K
	private String region_sf 	="";  //1~4，默认3
	private String unit			="";  //样本单位
	
	private String modelkey  = "";
	
}
