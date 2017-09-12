package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 紧急止付/解除止付反馈结果数据项
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 上午10:35:26
 */
@Data
public class CBRC_StopPaymentRequest_Recored implements Serializable {
	private static final long serialVersionUID = 8103655500902072349L;
	
	/** 任务流水号 */
	private String rwlsh;
	
	/** 账号 */
	private String zh;
	
	/** 卡号 */
	private String kh;
	
	/** 执行结果 */
	private String zxjg;
	
	/** 失败原因 */
	private String sbyy;
	
	/** 执行起始时间 */
	private String zxqssj;
	
	/** 紧急止付/解除止付明细 */
	private List<CBRC_StopPaymentRequest_Detail> stopPaymentDetailList;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	private String status = "";
	private String organkey = "";
	private String qrydt = "";
	private String hxappid = "";
	private String qqdbs = "";
	private String tasktype = "";
	private String msgcheckresult="";
	private String 	ztlb ="";
}
