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
 * @author gaojx 交易信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br24_trans_info extends Br24_bas_dto {
	private static final long serialVersionUID = -737688669831168354L;
	
	// ==========================================================================================
	// XML Generation
	// ==========================================================================================
	/** 交易类型 */
	private String transactionType="";
	/** 借贷标志(0-:借"="";1-"贷") */
	private String borrowingSigns="";
	/** 币种(CNY人民币、USD美元、EUR欧元…) */
	private String currency="";
	/** 交易金额 */
	private String transactionAmount="";
	/** 交易余额 */
	private String accountBalance="";
	/** 交易时间 */
	private String transactionTime="";
	/** 交易流水号 */
	private String transactionSerial="";
	/** 交易对方名称 */
	private String opponentName="";
	/** 交易对方账卡号 */
	private String opponentAccountNumber="";
	/** 交易对方证件号码 */
	private String opponentCredentialNumber="";
	/** 交易对方账号开户行 */
	private String opponentDepositBankID="";
	/** 交易摘要 */
	private String transactionRemark="";
	/** 交易网点名称 */
	private String transactionBranchName="";
	/** 交易网点代码 */
	private String transactionBranchCode="";
	/** 日志号 */
	private String logNumber="";
	/** 传票号 */
	private String summonsNumber="";
	/** 凭证种类 */
	private String voucherType="";
	/** 凭证号 */
	private String voucherCode="";
	/** 现金标志(00-其它="";01-现金交易) */
	private String cashMark="";
	/** 终端号 */
	private String terminalNumber="";
	/** 交易是否成功(00-成功="";01-失败) */
	private String transactionStatus="";
	/** 交易发生地 */
	private String transactionAddress="";
	/** 商户名称 */
	private String merchantName="";
	/** 商户号 */
	private String merchantCode="";
	/** IP地址 */
	private String iPAddress="";
	/** MAC地址 */
	private String mAC="";
	/** 交易柜员号 */
	private String tellerCode="";
	/** 备注 */
	private String remark="";

	
	// ==========================================================================================
	// Help Field
	// ==========================================================================================
	/** 定位账户账号(定位帐号唯一性字段，定义为主账户+"_"+"子账户识别号[账户序号或币种号]") */
	private String accountNumber="";
	/** 主账户(对私-卡/折号；对公-基本账户帐号) */
	private String cardNumber="";
	
	private String transseq="";
	
}
