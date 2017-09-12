/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年3月17日
 * Description:
 * =========================================================
 */
package com.citic.server.dx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 止付信息
 * 
 * @author dingke
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br25_StopPay_back extends RequestMessage {
	private static final long serialVersionUID = 7797576838344662791L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 止付结果（4位业务应答码） */
	private String result = "0000";
	
	/** 止付账号类别（01-个人；02-对公） */
	private String accountType = "";
	
	/** 止付帐卡号（与原止付报文账卡号相同） */
	private String accountNumber = "";
	
	/** 卡/折号（对私业务时需填写） */
	private String cardNumber = "";
	
	/** 账户余额（止付前余额） */
	private String accountBalance = "";
	
	/** 止付、止付解除、止付延期起始时间（yyyyMMddHHmmss） */
	private String startTime = "";
	
	/** 止付结束时间（yyyyMMddHHmmss） */
	private String endTime = "";
	
	/** 未能止付、解除止付、止付延期原因（填写技术原因） */
	private String failureCause = "";
	
	/** 反馈说明（填写业务原因） */
	private String feedbackRemark = "";
	
	/** 反馈机构名称 */
	private String feedbackOrgName = "";
	
	/** 经办人姓名 */
	private String operatorName = "";
	
	/** 经办人电话 */
	private String operatorPhoneNumber = "";
	
	private String hxappid = "";
	
	private String currency="";
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	
	/** 止付解除生效时间（yyyyMMddHHmmss） */
	private String withdrawalTime = "";
	/** 止付结束时间（yyyyMMddHHmmss） */
	private String expireTime = "";
	
	private String toorg = "";
	/** 反馈时间 */
	private String feedback_dt = "";
	/** 0未处理 1已发送 3成功 4失败 */
	private String status_cd = "0";
	
	private String qrydt="";
	
	private String orgkey="";
	
	private String modeid="";
	
	private String last_up_dt="";
	
	private String transSerialNumber="";

	private String msgcheckresult="";
}
