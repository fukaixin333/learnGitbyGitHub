package com.citic.server.cbrc.domain.request;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.dict.DictBean;

/**
 * 常规查询反馈关联子账户数据项
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午8:44:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CBRC_QueryRequest_SubAccount implements DictBean, Serializable {
	private static final long serialVersionUID = -2102163488656259622L;
	
	/** 账卡号 */
	private String zh;
	
	/** 子账户序号 */
	private String zzhxh;
	
	/** 子账户类别 */
	private String zzhlb;
	
	/** 子账户账号 */
	private String zzhzh;
	
	/** 币种 */
	private String bz;
	
	/** 钞汇标志 */
	private String chbz;
	
	/** 账户余额 */
	private String zhye;
	
	/** 账户状态 */
	private String zhzt;
	
	/** 可用余额 */
	private String kyye;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	
	/** 请求单标识 */
	private String tasktype = "";
	private String qqdbs = "";
	private String rwlsh = "";
	/** 查询日期 */
	private String qrydt = "";
	private String orgkey = "";
	private String zhmc="";
	/** 卡号 */
	private String kh = "";
	
	@Override
	public String getGroupId() {
		return "CBRC_QUERYREQUEST_SUBACCOUNT";
	}
}
