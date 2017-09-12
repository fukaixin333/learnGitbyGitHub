/**========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月17日
 * Description: 电信诈骗反馈基本信息表
 * 
 *=========================================================
 */
package com.citic.server.dx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Br24_dq_release extends Br24_bas_dto {

	private static final long serialVersionUID = -2183751102076269550L;

	/** 默认值01 */
	private String mode;
	/** 接收机构ID（对应下行报文中的MessageFrom值） */
	private String to;
	/** 交易类型编码100308 */
	private String txCode;
	/** 业务申请编号（参见附录I） */
	private String applicationID;
	/** 动态查询解除结果(参见勘误修订后4位业务应答码) */
	private String result;
	/** 案件编号 */
	private String caseNumber;
	/** 申请机构编码 */
	private String applicationOrgID;
	/** 申请机构名称 */
	private String applicationOrgName;
	/** 申请时间(yyyyMMddHHmmss) */
	private String applicationTime;
	/** 经办人姓名 */
	private String operatorName;
	/** 经办人电话 */
	private String operatorPhoneNumber;
	/** 动态查询账户所属银行机构编码 */
	private String bankID;
	/** 动态查询账户所属银行名称 */
	private String bankName;
	/** 动态查询账户名 */
	private String accountName;
	/** 动态查询卡/折号(与原账户动态查询请求卡/折号一致) */
	private String cardNumber;
	/** 解除动态查询说明 */
	private String withdrawalRemark;
	/** 反馈说明 */
	private String feedbackRemark;

	public String getTxcode() {
		return txCode;
	}

	public String getApplicationid() {
		return applicationID;
	}

	public String getCasenumber() {
		return caseNumber;
	}

	public String getApplicationorgid() {
		return applicationOrgID;
	}

	public String getApplicationorgname() {
		return applicationOrgName;
	}

	public String getApplicationtime() {
		return applicationTime;
	}

	public String getOperatorname() {
		return operatorName;
	}

	public String getOperatorphonenumber() {
		return operatorPhoneNumber;
	}

	public String getBankid() {
		return bankID;
	}

	public String getBankname() {
		return bankName;
	}

	public String getAccountname() {
		return accountName;
	}

	public String getCardnumber() {
		return cardNumber;
	}

	public String getWithdrawalremark() {
		return withdrawalRemark;
	}

	public String getFeedbackremark() {
		return feedbackRemark;
	}

}
