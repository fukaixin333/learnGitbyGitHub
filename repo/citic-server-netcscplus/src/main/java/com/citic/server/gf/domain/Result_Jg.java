package com.citic.server.gf.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询/控制结果反馈以及回退请求的处理结果信息
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 下午4:20:36
 */
@Data
public class Result_Jg implements Serializable {
	private static final long serialVersionUID = -6970965619920420799L;
	
	/** 查询/控制请求单号 */
	private String bdhm = "";
	/** 处理结果 */
	private String result = "";
	/** 处理失败原因描述 */
	private String msg = "";
	
	private String qrydt;
	
	public Result_Jg() {
	}
	
	public Result_Jg(String _bdhm, String _result, String _msg) {
		this.bdhm = _bdhm;
		this.result = _result;
		this.msg = _msg;
	}
	
	@Override
	public String toString() {
		return "请求单号 = [" + bdhm + "], 回退结果 = [" + result + "], 失败原因 = [" + msg + "]";
	}
}