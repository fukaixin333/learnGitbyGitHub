/*
 * =============================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created [2016-05-23]
 * =============================================
 */

package com.citic.server.cbrc.domain;

/**
 * 反馈信息主表
 * <p>
 * Br40_cxqq_back.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author $Author: $
 */

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Br40_cxqq_back implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7387575804392234415L;
	
	/** 对该查询任务的查询结果描述，成功或失败；01表示成功，02表示失败； */
	private String cxfkjg = "";
	private String cxfkjg_disp = "";
	
	/**
	 * 操作失败相关信息，成功则返回为空。
	 * 操作成功但无结果，需注明“未找到符合请求条件的数据”
	 */
	private String czsbyy = "";
	/** 监管类别 */
	private String tasktype = "";
	/** 卡号（动态查询用） */
	private String kh = "";
	
	/** 动态查询用，其他默认1 */
	private String seq = "";
	
	/** 主键， 请求单流水号，用于唯一标识查控请求单 */
	private String qqdbs = "";
	
	/** 账号（动态查询用） */
	private String zh = "";
	
	/** 0未处理 1已发送 3成功 4失败 */
	private String status = "";
	
	/** 查询机构代码,统一编码 */
	private String mbjgdm = "";
	
	/** 查控主体类别 */
	private String ztlb = "";
	private String ztlb_disp = "";
	
	/** 查询机构代码,统一编码 */
	private String sqjgdm = "";
	
	/** 执行完查询结果日期，采用格式YYYYMMDDhhmmss，24小时制格式，例如：20150410093230； */
	private String cxjssj = "";
	
	/** 归属机构 */
	private String orgkey = "";
	
	/** 任务流水号 */
	private String rwlsh = "";
	
	/** 查询日期（分区） */
	private String qrydt = "";
	/** 反馈手机号码 */
	private String fksjhm = "";
	/** 动态查询起始时间 */
	private String zxqssj = "";
	private String zxsjqj = ""; // 执行时间区间
	/** 动态查询结束时间 */
	private String jssj = "";
	/** 执行时间区间 */
	private String mxsdlx = "";
	private String mxsdlx_disp = "";
	
	
	private String czsj="";//操作时间  20160702-liangtao
	
	private String msgcheckresult="";
}
