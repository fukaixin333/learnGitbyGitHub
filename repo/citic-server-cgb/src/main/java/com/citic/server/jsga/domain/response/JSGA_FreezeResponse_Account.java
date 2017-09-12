package com.citic.server.jsga.domain.response;

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data;

/**
 * 冻结/续冻/解除主体数据项
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午5:28:47
 */
@Data
public class JSGA_FreezeResponse_Account implements DictBean, Serializable {
	private static final long serialVersionUID = -2775209043964306273L;
	
	/** 任务流水号 */
	private String rwlsh;
	
	/** 原任务流水号 */
	private String yrwlsh;
	
	/** 冻结账户户主 */
	private String djzhhz;
	
	/** 证件类型代码 */
	private String zzlxdm;
	
	/** 证件号码 */
	private String zzhm;
	
	/** 冻结账卡号 */
	private String zh;
	
	/** 账户序号 */
	private String zhxh;
	
	/** 冻结方式 */
	private String djfs;
	
	/** 金额 */
	private String je;
	
	/** 币种 */
	private String bz;
	
	/** 开始时间 */
	private String kssj;
	
	/** 结束时间 */
	private String jssj;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	private String status = "";
	private String organkey = "";
	private String qrydt = "";
	private String qqdbs = "";
	private String tasktype = "";
	private String qqcslx = "";
	private String hxappid = "";
	private String ztlb = "";
	
	@Override
	public String getGroupId() {
		return "JSGA_FREEZERESPONSE_ACCOUNT";
	}
}
