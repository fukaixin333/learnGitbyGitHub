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
public class BR42_sequences implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -445649594538683740L;

	/** 包批次 */
	private String packet_seq = "";
	
	/** 机构编码 */
	private String organkey_r = "";
	
	/** 文件序号 */ 
	private Integer msg_num =new Integer(1);
	
	private Integer  packet_num=new Integer(1);
	
	
}
