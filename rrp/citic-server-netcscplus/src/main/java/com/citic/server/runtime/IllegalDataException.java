package com.citic.server.runtime;

/**
 * 无效数据异常
 * 
 * @author Liu Xuanfei
 * @date 2016年9月17日 下午4:24:23
 */
public class IllegalDataException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 7092245551779104253L;
	
	public IllegalDataException(String data, String message) {
		super(new StringBuilder().append("Illegal data '").append(data).append("': ").append(message).toString());
	}
	
	public IllegalDataException(String message) {
		super(message);
	}
}
