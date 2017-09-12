package com.citic.server.gf.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Liu Xuanfei
 * @date 2016年4月28日 下午11:17:17
 */
@Data
public class MC20_GZZ implements Serializable {
	private static final long serialVersionUID = 1286320543847283129L;
	
	private String bdhm = "";
	
	private String xh = "";
	
	private String zjmc = "";
	
	private String gzzbm = "";
	private String gzzwjgs = "";
	private String gzzpath = "";
	
	private String gwzbm = "";
	private String gwzwjgs = "";
	private String gwzpath = "";
	
	private String tasktype; //
}
