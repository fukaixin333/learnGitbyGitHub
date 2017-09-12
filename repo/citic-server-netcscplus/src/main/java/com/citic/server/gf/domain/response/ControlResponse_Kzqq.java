/*
 * =============================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created [2016-02-18]
 * =============================================
 */

package com.citic.server.gf.domain.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 司法控制请求信息
 * <ul>
 * <li>冻结、续冻
 * <li>解除冻结
 * <li>扣划
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年3月1日 下午7:15:46
 */
@Data
public class ControlResponse_Kzqq implements Serializable {
	private static final long serialVersionUID = 2537252636977709348L;
	
	/** 主键， 即任务 流水号 ，唯 一标识 */
	private String bdhm = "";
	
	/** YH: 商业银行 RH: 人民银行 */
	private String lb = "";
	
	/** 措施类型:1查 2控 区分查询 及控制 ,这里 默认为 1 */
	private String xz = "";
	
	/** 姓名 */
	private String xm = "";
	
	/** 证件类型 */
	private String zjlx = "";
	
	/** 证件号码 */
	private String dsrzjhm = "";
	
	/** 法院名称 */
	private String fymc = "";
	
	/** 法院代码 */
	private String fydm = "";
	
	/** 法官 */
	private String cbr = "";
	
	/** 承办 法官联 系电话 */
	private String yhdh = "";
	
	/** 执 行案号 */
	private String ah = "";
	
	/** 工作证编号 */
	private String gzzbh = "";
	
	/** 承办法 官公务证编号 */
	private String gwzbh = "";
	
	/** 控制通知书名称 */
	private String ckh = "";
	
	/** 承办人联系地址 */
	private String lxfs = "";
	
	/** 备注 */
	private String beiz = "";
	
	/** 控制账户信息列表 */
	List<ControlResponse_Kzzh> kzzhlist;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	
	private String taskKey;
	private String msgPath;
	private String msgName;
	
	private String status = "";
	private String orgkey = "";
	private String msg_type_cd = ""; 
	private String packetkey = ""; 
	private String qrydt = "";
	private String recipient_time="";

	private String wjmc1; // 通知书名称
	private String wjmc2; // 裁定书名称
}
