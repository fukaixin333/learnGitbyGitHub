package com.citic.server.runtime;

/**
 * JiBX转换（Format）异常
 * 
 * @author Liu Xuanfei
 * @date 2016年7月11日 上午11:42:44
 */
public class JiBXFormatException extends Exception {
	private static final long serialVersionUID = -5848313737577806523L;
	
	private String code;
	
	public JiBXFormatException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public JiBXFormatException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
