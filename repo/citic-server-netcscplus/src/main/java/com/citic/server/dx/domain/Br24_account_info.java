/**========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年3月17日
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.dx.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gaojx 子账户信息
 * 
 */
 @Data
 @EqualsAndHashCode(callSuper = false)
public class Br24_account_info extends Br24_bas_dto {

	private static final long serialVersionUID = 7569275574586747380L;

	/** 定位账户账号(定位帐号唯一性字段，定义为主账户+"_"+"子账户识别号[账户序号或币种号]") */
	private String accountNumber="";
	/** 一般(子)账户序号 */
	private String accountSerial="";
	/** 一般(子)账户类别(根据各银行实际数据反馈) */
	private String accountType="";
	/** 账户状态：正常，冻结，销户等 */
	private String accountStatus="";
	/** 币种(CNY人民币、USD美元、EUR欧元…) */
	private String currency="";
	/** 钞汇标志 */
	private String cashRemit="";
	/** 账户余额 */
	private String accountBalance="";
	/** 可用余额 */
	private String availableBalance="";
	/** 最后交易时间 */
	private String lastTransactionTime="";
	
	/** 开户网点代码 */
	private String depositBankBranchCode;

	// ==========================================================================================
	// Help Field
	// ==========================================================================================
	/** 主账户(对私-卡/折号；对公-基本账户帐号) */
	private String cardNumber="";
	/** 账户名称 */
	private String accountName="";

	private List<Br24_trans_info> transInfoList;



}
