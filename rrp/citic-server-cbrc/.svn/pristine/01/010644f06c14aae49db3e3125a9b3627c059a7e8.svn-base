package com.citic.server.cbrc.domain.request;

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data;

/**
 * 常规查询反馈账户共有权/优先权信息数据项
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午8:41:26
 */
@Data
public class CBRC_QueryRequest_Priority implements DictBean, Serializable {
	private static final long serialVersionUID = -2102163488656259622L;
	
	/** 账号 */
	private String zh;
	
	/** 权利序号 */
	private String xh;
	
	/** 权利类型 */
	private String qllx;
	
	/** 证件类型代码 */
	private String zzlxdm;
	
	/** 证件号码 */
	private String zzhm;
	
	/** 权利人姓名 */
	private String qlrxm;
	
	/** 权利金额 */
	private String qlje;
	
	/** 权利人通讯地址 */
	private String qlrdz;
	
	/** 权利人联系方式 */
	private String qlrlxfs;
	
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
	
	@Override
	public String getGroupId() {
		return "CBRC_QUERYREQUEST_PRIORITY";
	}
}
