package com.citic.server.dx.domain;


/**
 * 上行报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月8日 上午10:12:57
 */
public class RequestMessage extends Message {
	private static final long serialVersionUID = -5731755773064357890L;
	
	/** 默认值为01 */
	private String mode = "01";
	
	/** 接收机构ID（对应下行报文中的MessageFrom值） */
	private String to = "111111000000";
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
}
