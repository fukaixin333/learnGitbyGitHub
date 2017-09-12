package com.citic.server.jsga.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 查控请求基础数据项
 * <ul>
 * <li>“常规查询请求”和“凭证图像查询请求”报文的<em>[查控主体类别]</em>字段名为<strong>ztlb</strong>。
 * <li>“国安”的“凭证图像查询请求”报文中无<em>[请求措施类型]</em>字段。
 * <li>“法律文书”报文中无<em>[查控主体类别]</em>、<em>[备注]</em>等字段。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午2:25:47
 */
@Data
public class JSGA_BasicInfo implements Serializable {
	private static final long serialVersionUID = 4059205994609118L;
	
	/** 请求单标识 */
	private String qqdbs;
	
	/** 请求措施类型 */
	private String qqcslx;
	
	/** 申请机构代码 */
	private String sqjgdm;
	
	/** 目标机构代码 */
	private String mbjgdm;
	
	/** 查控主体类别 */
	private String ztlb;
	
	/** 案件类型 */
	private String ajlx;
	
	/** 紧急程度 */
	private String jjcd;
	
	/** 备注 */
	private String beiz;
	
	/** 发送时间 */
	private String fssj;
}
