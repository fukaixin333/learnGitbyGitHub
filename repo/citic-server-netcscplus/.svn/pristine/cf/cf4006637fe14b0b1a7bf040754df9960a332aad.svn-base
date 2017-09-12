package com.citic.server.runtime;

/**
 * 应用程序业务处理异常，常用于中断业务处理，捕获异常执行补救措施
 * 
 * @author Liu Xuanfei
 * @date 2016年9月15日 上午11:52:27
 */
public class ProcessException extends Exception {
	private static final long serialVersionUID = -4047545421304003954L;
	
	private String code;
	
	public ProcessException(String message) {
		super(message);
	}
	
	public ProcessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ProcessException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public ProcessException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
}
