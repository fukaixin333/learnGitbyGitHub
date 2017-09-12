package com.citic.server.gdjc.domain;

import java.io.Serializable;

import com.citic.server.gdjc.GdjcConstants;

/**
 * @author Liu Xuanfei
 * @date 2016年8月16日 下午8:35:58
 */
public class Gdjc_Request implements Serializable {
	private static final long serialVersionUID = -2196807444505375124L;
	
	// ==========================================================================================
	//                     COMMAND 标准格式
	// ==========================================================================================
	private String cmdtype = GdjcConstants.COMMAND_TYPE_LOGIN;
	
	/** 查询申请单位用户 */
	private String username;
	
	/** 查询申请单位密码 */
	private String password;
	
	// ==========================================================================================
	//                     DATA 标准格式
	// ==========================================================================================
	private String content;
	
	// ==========================================================================================
	//                     Getters and Setters
	// ==========================================================================================
	
	public String getCmdtype() {
		return cmdtype;
	}
	
	public void setCmdtype(String cmdtype) {
		this.cmdtype = cmdtype;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
