package com.citic.server.cbrc.domain.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 紧急止付/解除止付主体数据项
 * <ul>
 * <li>仅“四川省公安厅”报文包含<em>[执行时间区间]</em>字段，其它监管不包含。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午5:51:43
 */
@Data
public class CBRC_StopPaymentResponse_Account implements Serializable {
	private static final long serialVersionUID = 3194118206491729181L;
	
	/** 任务流水号 */
	private String rwlsh;
	
	/** 账卡号 */
	private String zh;
	
	/** 原任务流水号 */
	private String yrwlsh;
	
	/** 执行时间区间 */
	private String zxsjqj;
	
	// ==========================================================================================
		//                     Help Field
	// ==========================================================================================
		private String status = "";
		private String organkey = "";
		private String qrydt = "";
		private String qqdbs = "";
		private String tasktype = "";
		private String hxappid = "";
		/** 措施类型：08  紧急止付 09  解除止付*/
		private String qqcslx = "";
		private String ztlb = "";
}
