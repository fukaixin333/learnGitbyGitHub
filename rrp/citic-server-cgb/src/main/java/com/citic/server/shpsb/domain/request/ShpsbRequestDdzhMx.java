package com.citic.server.shpsb.domain.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 对端账号查询反馈 - 对端账户信息
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 上午10:56:50
 */
@Data
public class ShpsbRequestDdzhMx implements Serializable {
	private static final long serialVersionUID = 1488617172133880890L;
	
	/** 对端账号 */
	private String ddzh;
	
	/** 对端户名 */
	private String ddhm;
	
	/** 对端开户行 */
	private String ddkhh;
	
	
	private String bdhm="";
	
	private String msgseq="";
	
	private String qrydt="";
}
