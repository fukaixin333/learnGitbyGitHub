package com.citic.server.dx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 下发请求报文-止付类
 * 
 * @author Ding Ke
 * @date 2016年3月28日 下午4:48:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br25_StopPay extends ResponseMessage {
	private static final long serialVersionUID = 2335121602910875755L;
	
	/** 业务申请编号 */
	private String applicationID="";
	
	/**
	 * 是否补流程
	 * <ul>
	 * <li>00 - 初次提交，正常止付<strong>人民币</strong>活期账户
	 * <li>01 - 初次提交，正常止付<strong>外币活期/人民币定期/外币定期</strong>账户
	 * <li>02 - 银行举报案件，止付<strong>人民币</strong>活期账户
	 * <li>03 - 银行举报案件，止付<strong>外币活期/人民币定期/外币定期</strong>账户
	 * <li>04 - 后补<strong>人民币</strong>活期止付流程
	 * <li>05 - 后补<strong>外币活期/人民币定期/外币定期</strong>止付流程
	 * </ul>
	 */
	private String applicationType="";
	
	/**
	 * 原举报申请编号 <br />
	 * 银行报案时必填，即 ApplicationType = 02 / 03 / 04 / 05 时。
	 */
	private String originalApplicationID="";
	
	/** 案件编号 */
	private String caseNumber="";
	
	/** 案件类型 */
	private String caseType="";
	
	/** 紧急程度（01-正常；02-加急） */
	private String emergencyLevel="";
	
	/** 转出账户所属银行机构编码 */
	private String transferOutBankID="";
	
	/** 转出账户所属银行名称 */
	private String transferOutBankName="";
	
	/** 转出账户名 */
	private String transferOutAccountName="";
	
	/** 转出账卡号 */
	private String transferOutAccountNumber="";
	
	/** 币种（CNY-人民币；USD-美元；EUR-欧元；……） */
	private String currency="";
	
	/** 转出金额（单位到元） */
	private String transferAmount="";
	
	/** 转出时间（yyyyMMddHHmmss） */
	private String transferTime="";
	
	/** 止付账户所属银行机构编码 */
	private String bankID="";
	
	/** 止付账户所属银行名称 */
	private String bankName="";
	
	/** 止付账号类别（01-个人；02-对公） */
	private String accountType="";
	
	/** 止付账户名 */
	private String accountName="";
	
	/**
	 * 止付账卡号
	 * <ul>
	 * <li>当 ApplicationType = 00 或 ApplicationType = 02 时，卡折号为主账号，默认止付主账号内活期账户；
	 * <li>当 ApplicationType = 01 或 ApplicationType = 03 时，账卡号为<strong>"[主账号]_[账号识别号]"</strong>
	 */
	private String accountNumber="";
	
	/** 止付事由 */
	private String reason="";
	
	/** 止付、止付解除、止付延期说明 */
	private String remark="";
	
	/** 止付起始时间（yyyyMMddHHmmss） */
	private String startTime="";
	
	/** 止付截止时间（yyyyMMddHHmmss） */
	private String expireTime="";
	
	/** 申请时间（yyyyMMddHHmmss） */
	private String applicationTime="";
	
	/** 申请机构编码 */
	private String applicationOrgID="";
	
	/** 申请机构名称 */
	private String applicationOrgName="";
	
	/** 经办人证件类型 */
	private String operatorIDType="";
	
	/** 经办人证件号 */
	private String operatorIDNumber="";
	
	/** 经办人姓名 */
	private String operatorName="";
	
	/** 经办人电话 */
	private String operatorPhoneNumber="";
	
	/** 协查人证件类型 */
	private String investigatorIDType="";
	
	/** 协查人证件号 */
	private String investigatorIDNumber="";
	
	/** 协查人姓名 */
	private String investigatorName="";
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================	
	
	/** 止付解除说明 */
	private String withdrawalRemark="";
	
	/** 止付延期起始时间（yyyyMMddHHmmss） */
	private String postponeStartTime="";
	
	/** 止付延期说明 */
	private String extendRemark="";
	
	/** 创建时间 */
	private String create_dt ="";
	
	/** 0未处理 1已发送 3成功 4失败 */
	private String status_cd = "";
	
	private String hxappid="";
	
	private String qrydt="";
	
	private String orgkey="";
	
	private String last_up_dt="";
	
	private String recipient_time="";

}
