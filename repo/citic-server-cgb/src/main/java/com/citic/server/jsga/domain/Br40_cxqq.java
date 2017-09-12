/*
 * =============================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created [2016-05-23]
 * =============================================
 */

package com.citic.server.jsga.domain;

/**
 * 查询请求主表
 * <p>
 * Br40_cxqq.java
 * </p>
 * 
 * @author $Author: $
 */

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Br40_cxqq implements Serializable {
	private static final long serialVersionUID = 4375446540650013220L;
	
	/** 请求单标识 */
	private String tasktype = "";
	
	/** 请求单标识 */
	private String qqdbs = "";
	
	/** 请求措施类型 */
	private String qqcslx = "";
	
	/** 申请机构代码 */
	private String sqjgdm = "";
	
	/** 目标机构代码 */
	private String mbjgdm = "";
	
	/** 查控主体类别 */
	private String ckztlb = "";
	
	/** 案件类型 */
	private String ajlx = "";
	
	/** 紧急程度 */
	private String jjcd = "";
	
	/** 备注 */
	private String beiz = "";
	
	/** 发送时间 */
	private String fssj = "";
	
	/** 请求人姓名 */
	private String qqrxm = "";
	
	/** 请求人证件类型 */
	private String qqrzjlx = "";
	
	/** 请求人证件号码 */
	private String qqrzjhm = "";
	
	/** 请求人单位名称 */
	private String qqrdwmc = "";
	
	/** 请求人手机号 */
	private String qqrsjh = "";
	
	/** 协查人姓名 */
	private String xcrxm = "";
	
	/** 协查人证件类型 */
	private String xcrzjlx = "";
	
	/** 协查人证件号码 */
	private String xcrzjhm = "";
	
	/** 请求人办公电话 */
	private String qqrbgdh = "";
	
	/** 协查人手机号 */
	private String xcrsjh = "";
	
	/** 协查人办公电话 */
	private String xcrbgdh = "";
	
	/** 状态 */
	private String status = "";
	
	/** 归属机构 */
	private String orgkey = "";
	
	/** 查询日期（分区） */
	private String qrydt = "";
	
	/** 接收人 */
	private String recipient_p = "";
	
	/** 接收时间 */
	private String recipient_time = "";
	
	/** 最后修改时间 */
	private String last_up_dt = "";
	
	private String rwlsh = "";
	
	/**
	 * 01代表个人主体；
	 * 02代表对公（单位）主体；
	 * 沿用请求单的类别；
	 */
	private String ztlb = "";
	
	private String cxfkjg = "";
	private String cxzh = "";
	/** 证照类型代码 */
	private String zzlx = "";
	private String cxjssj = "";
	private String zzhm = "";
	private String cxnr = "";

	
	private String mxjzsj = "";
	private String mxqssj = "";
	private String lastpollingtime = "";
	private String status_cd = "";
	private String pollinglock = "";
	private String seq = "";
	
}
