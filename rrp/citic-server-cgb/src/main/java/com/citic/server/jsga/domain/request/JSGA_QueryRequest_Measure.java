package com.citic.server.jsga.domain.request;

import java.io.Serializable;

import lombok.Data;

import com.citic.server.dict.DictBean;

/**
 * 常规查询反馈账户强制措施信息数据项
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午8:41:03
 */
@Data
public class JSGA_QueryRequest_Measure implements DictBean, Serializable {
	private static final long serialVersionUID = -2102163488656259622L;
	
	/** 账号 */
	private String zh;
	
	/** 措施序号 */
	private String csxh;
	
	/** 冻结开始日 */
	private String djksrq;
	
	/** 冻结截止日 */
	private String djjzrq;
	
	/** 冻结机关名称 */
	private String djjgmc;
	
	/** 冻结金额 */
	private String djje;
	
	/** 备注 */
	private String beiz;
	
	/** 冻结措施类型 */
	private String djcslx;
	
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
		return "JSGA_QUERYREQUEST_MEASURE";
	}
}
