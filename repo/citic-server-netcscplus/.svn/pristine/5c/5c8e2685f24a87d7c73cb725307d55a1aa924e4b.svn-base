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
 * @author gaojx 账户冻结信息
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br24_account_freeze extends Br24_bas_dto {

	private static final long serialVersionUID = -1677630666655204705L;

	/** 措施序号 */
	private String freezeSerial;
	/** 账号 */
	private String accountNumber;
	/** 卡号(对私业务时需填写) */
	private String cardNumber;
	/** 冻结开始日 */
	private String freezeStartTime;
	/** 冻结截止日 */
	private String freezeEndTime;
	/** 冻结机关名称 */
	private String freezeDeptName;
	/** 币种(CNY人民币、USD美元、EUR欧元…) */
	private String currency;
	/** 冻结金额(精确至整数) */
	private String freezeBalance;
	/** 备注 */
	private String remark;
	/** 冻结措施类型(0001-公安止付;0002-公安冻结;1001-高法冻结... ) */
	private String freezeType;

	
}
