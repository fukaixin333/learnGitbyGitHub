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
 * @author gaojx 账户基本信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br24_account_holder extends Br24_bas_dto {
	private static final long serialVersionUID = -4561585966861340456L;

	/** 查询反馈说明 */
	private String feedbackRemark="";
	/** 查询证照类型代码 */
	private String accountCredentialType="";
	/** 查询证照号码 */
	private String accountCredentialNumber="";
	/** 查询主体名称 */
	private String accountSubjectName="";
	/** 联系手机 */
	private String telNumber="";
	/** 帐/卡代办人姓名 */
	private String cardOperatorName="";
	/** 帐/卡代办人证件类型 */
	private String cardOperatorCredentialType="";
	/** 帐/卡代办人证件号码 */
	private String cardOperatorCredentialNumber="";
	/** 住宅地址 */
	private String residentAddress="";
	/** 住宅电话 */
	private String residentTelNumber="";
	/** 工作单位 */
	private String workCompanyName="";
	/** 单位地址 */
	private String workAddress="";
	/** 单位电话 */
	private String workTelNumber="";
	/** 邮箱地址 */
	private String emailAddress="";
	/** 法人代表 */
	private String artificialPersonRep="";
	/** 法人代表证件类型 */
	private String artificialPersonRepCredentialType="";
	private String crit="";
	/** 法人代表证件号码 */
	private String artificialPersonRepCredentialNumber="";
	private String crid="";
	/** 客户原工商注册号码 */
	private String businessLicenseNumber="";
	/** 国税纳税号 */
	private String stateTaxSerial="";
	/** 地税纳税号 */
	private String localTaxSerial="";
	/** 卡号 */
	private String cardnumber="";
	/** 卡号 */
	private String party_id="";
	
	private String organkey="";
		
	/**证照失效日期（格式：YYYYMMDD）*/
	private String credentialExpiryDate="";
	private String remark="";

}
