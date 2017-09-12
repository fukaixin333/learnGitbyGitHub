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
 * @author gaojx 卡折基本信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br24_card_info extends Br24_bas_dto {
	private static final long serialVersionUID = 2663357583066266388L;

	/** 主账户名称(基本账户帐户名称) */
	private String accountName="";
	/** 主账户(对私-卡/折号；对公-基本账户帐号) */
	private String cardNumber="";
	/** 开户网点 */
	private String depositBankBranch="";
	/** 开户网点代码 */
	private String depositBankBranchCode="";
	/** 开户日期(yyyyMMddhhmmss) */
	private String accountOpenTime="";
	/** 销户日期(yyyyMMddhhmmss) */
	private String accountCancellationTime="";
	/** 销户网点 */
	private String accountCancellationBranch="";
	/** 备注 */
	private String remark="";

	// ==========================================================================================
	// Help Field
	// ==========================================================================================
	

}
