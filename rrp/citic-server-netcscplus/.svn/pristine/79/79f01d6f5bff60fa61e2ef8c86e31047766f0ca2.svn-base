package com.citic.server.runtime;

/**
 * 远程通讯异常（远程通讯使用MINA）
 * 
 * @author Liu Xuanfei
 * @date 2016年3月11日 下午3:27:12
 */
public class RemoteAccessException extends Exception {
	private static final long serialVersionUID = 8690616176525435953L;
	
	/** 错误编码 */
	private String code;
	
	public RemoteAccessException(String message) {
		super(message);
	}
	
	public RemoteAccessException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public RemoteAccessException(String message, Throwable root) {
		super(message, root);
	}
	
	public String getCode() {
		if (code == null) {
			return "";
		}
		return code;
	}
}
