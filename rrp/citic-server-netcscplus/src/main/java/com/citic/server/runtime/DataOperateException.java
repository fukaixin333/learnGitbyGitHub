/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月17日
 * Description:
 * =========================================================
 */
package com.citic.server.runtime;

/**
 * @author gaojx 查询异常定义接口
 */
public class DataOperateException extends Exception {
	private static final long serialVersionUID = -7896720383042695064L;
	
	/** 查询错误定义码 */
	private String code;
	
	/** 错误描述 */
	private String descr;
	
	public DataOperateException(String message) {
		super(message);
		this.code = "999999";
	}
	
	public DataOperateException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public DataOperateException(String message, Throwable e) {
		super(message, e);
		this.code = "999999";
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescr() {
		if (descr == null) {
			return getMessage();
		}
		return descr;
	}
	
	public void setDescr(String descr) {
		this.descr = descr;
	}
}
