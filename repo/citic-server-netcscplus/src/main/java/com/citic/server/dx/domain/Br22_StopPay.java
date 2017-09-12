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
 * 人工举报止付信息
 * 
 * @author dingke
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br22_StopPay extends Message {
	private static final long serialVersionUID = 4891965054400290736L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 止付结果（4位业务应答码） */
	private String result = "";
	
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
	
	/** 反馈说明（填写业务原因） */
	private String feedbackRemark = "";
	
	/** 止付结束时间（yyyyMMddHHmmss） */
	private String expireTime = "";
	
	/** 反馈时间 */
	private String feedback_dt = "";
	
	private String hxappid = "";
	
	private String caseid = "";
	
}
