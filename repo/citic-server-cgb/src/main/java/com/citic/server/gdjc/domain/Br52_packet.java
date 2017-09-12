package com.citic.server.gdjc.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Br52_packet  implements Serializable  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 318816384482960966L;

	/** id**/
	private String  packetkey="";
	
	/** 协作编号**/
	private String  docno="";
	
	/** 发送日期**/
	private String  senddate_dt="";
	
	/** 数据包文件名**/
	private String  filename="";
	
	/** 文件名［不含root级］+完整路径**/
	private String  filepath="";
	
	/** 状态0:未发送1:已发送**/
	private String  status_cd="";
	
	/** 生成时间**/
	private String  create_dt="";
	
	/** 结果状态 0: 待处理 1: 成功  2: 错误**/
	private String  resultstatus="";
	
	/** 处理信息**/
	private String  resultinfo="";
	
	/** N：正常 R：重发**/
	private String  msg_type_cd="";
	
}
