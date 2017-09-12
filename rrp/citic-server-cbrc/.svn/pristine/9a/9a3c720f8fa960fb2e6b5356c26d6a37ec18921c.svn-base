/*
 * =============================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created [2016-05-23]
 * =============================================
 */

package com.citic.server.cbrc.domain;

/**
 * 查询请求明细表
 * <p>
 * Br40_cxqq.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author $Author: $
 */

import java.io.Serializable;

import com.citic.server.dict.DictBean;
import com.citic.server.runtime.ServerEnvironment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Br40_cxqq_mx implements DictBean, Serializable {
	private static final long serialVersionUID = 389969952122230594L;
	
	/** 任务流水号 */
	private String rwlsh = "";
	/** 查询类型 查询类型：01-按证照查询 02-按账卡号查询 */
	private String cxlx = "";
	/** 证照类型代码 */
	private String zzlx = "";
	/** 证照号码 */
	private String zzhm = "";
	/** 主体名称 */
	private String ztmc = "";
	/** 查询账卡号 */
	private String cxzh = "";
	/** 查询内容 */
	private String cxnr = "";
	/** 明细时段类型：01表示开户至今；02表示当年数据；03自定义时间段； */
	private String mxsdlx = "";
	/** 明细起始时间 */
	private String mxqssj = "";
	/** 明细截至时间 */
	private String mxjzsj = "";
	/** 查询方式 */
	private String cxfs = "";
	/** 分类代码 */
	private String xmltype = "";
	
	private String status = "";
	private String orgkey = "";
	private String qrydt = "";
	private String qqdbs = "";
	private String tasktype = "";
	private String ztlb = ""; // 查询主体类别 ：01个人/02对公
	private String acct_num = "";
	private String zh;
	private String yrwlsh = "";
	private String qqcslx = "";
	private String sqjgdm = "";
	private String mbjgdm = ""; //目标机构代码
	private String partyid = "";
	
	private String mxjzsjold = "";
	
	@Override
	public String getGroupId() {
		return "CBRC_BR40_CXQQ_MX";
	}
}
