/**========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年3月17日
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.dx.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gaojx 账户权利信息
 *
 */
 @Data
 @EqualsAndHashCode(callSuper = false)
public class Br24_account_right extends Br24_bas_dto {
	private static final long serialVersionUID = -6705418472289045830L;

	/** 序号 */
	private String priortySerial;
	/** 账号 */
	private String accountNumber;
	/** 卡号(对私业务时需填写) */
	private String cardNumber;
	/** 证件类型代码 */
	private String credentialType;
	/** 证件号码 */
	private String credentialNumber;
	/** 权利类型（01-共有权；02-优先权；03-质押权等） */
	private String rightsType;
	/** 权利人姓名 */
	private String obligeeName;
	/** 权利金额(精确至整数) */
	private String rightsBalance;
	/** 权利人通讯地址 */
	private String obligeeAddress;
	/** 权利人联系方式 */
	private String obligeeContactInfo;
	// ==========================================================================================
	// Help Field
	// ==========================================================================================

	

}
