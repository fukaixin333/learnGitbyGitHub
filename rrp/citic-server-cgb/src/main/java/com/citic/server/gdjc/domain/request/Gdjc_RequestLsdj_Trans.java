package com.citic.server.gdjc.domain.request;

import java.io.Serializable;

import lombok.Data;

import com.citic.server.dict.DictBean;
import com.google.gson.annotations.SerializedName;

/**
 * 商业银行交易流水登记-交易流水
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午11:15:58
 */
@Data
public class Gdjc_RequestLsdj_Trans implements Serializable,DictBean{
	private static final long serialVersionUID = 2453551500900630793L;
	
	//=========================说明=================================
	//        该类在RequestMessageServiceGdjc类中涉及json处理
	//        修改字段初始化为"",避免json处理时，null字段自动跳过，不处理的问题
	//        增加别名，解决json字串要求属性大写的问题
	//==============================================================
	
	/** 协作编号 */
	@SerializedName("DOCNO")
	private String docno = "";
	
	/** 案件编号 */
	@SerializedName("CASENO")
	private String caseno = "";
	
	/** 唯一标识 */
	@SerializedName("UNIQUEID")
	private String uniqueid = "";
	
	/** 交易流水号 */
	@SerializedName("TRANSNUM")
	private String transnum = "";
	
	/** 户名 */
	@SerializedName("ACCNAME")
	private String accname = "";
	
	/** 账户类型 */
	@SerializedName("ACCTYPE")
	private String acctype = "";
	
	/** 账号 */
	@SerializedName("ACCOUNT")
	private String account = "";

	/** 子账户 */
	@SerializedName("SUBACCOUNT")
	private String subaccount = "";
	
	/** 卡号 */
	@SerializedName("CARDNO")
	private String cardno = "";
	
	/** 汇钞类型（1-现钞；2-外汇） */
	@SerializedName("EXCHANGETYPE")
	private String exchangetype = "";
	
	/** 交易时间（YYYY-MM-DD HH24:MI:SS） */
	@SerializedName("TRANSTIME")
	private String transtime = "";
	
	/** 支出 */
	@SerializedName("EXPENSE")
	private String expense = "";
	
	/** 存入 */
	@SerializedName("INCOME")
	private String income = "";
	
	/** 余额（余额为空值时，传0.00） */
	@SerializedName("BANLANCE")
	private String banlance = "";
	
	/** 币种 */
	@SerializedName("CURRENCY")
	private String currency = "";
	
	/** 交易类型（使用中文描述。如：定期存款、活期存款等） */
	@SerializedName("TRANSTYPE")
	private String transtype = "";
	
	/** 交易地址 */
	@SerializedName("TRANSADDR")
	private String transaddr = "";
	
	/** 交易地址编号（交易网点编号或ATM机编号） */
	@SerializedName("TRANSADDRNO")
	private String transaddrno = "";
	
	/** 交易国家或地区 */
	@SerializedName("TRANSCOUNTRY")
	private String transcountry = "";
	
	/** 交易备注 */
	@SerializedName("TRANSREMARK")
	private String transremark = "";
	
	/** 交易联系电话 */
	@SerializedName("TRANSTEL")
	private String transtel = "";
	
	/** 交易渠道（ 使用中文描述。如：柜员机、自助终端、小额支付系统、网上银行、POS机、柜台、转账电话等） */
	@SerializedName("TRANSCHANNEL")
	private String transchannel = "";
	
	/** 交易柜员号 */
	@SerializedName("TRANSTELLE")
	private String transtelle = "";
	
	/** 交易终端IP */
	@SerializedName("TRANSTERMIP")
	private String transtermip = "";
	
	/** 对方账号 */
	@SerializedName("MATCHACCOUNT")
	private String matchaccount = "";
	
	/** 对方户名 */
	@SerializedName("MATCHACCNAME")
	private String matchaccname = "";
	
	/** 对方机构号 */
	@SerializedName("MATCHBANKNO")
	private String matchbankno = "";
	
	/** 对方机构名 */
	@SerializedName("MATCHBANKNAME")
	private String matchbankname = "";
	
	
	// ==========================================================================================
		//                     Help Field
	    //该类请不要随意增加冗余字段，若新增冗余字段，请修改RequestMessageServiceGdjc类的getGsonUtils方法的shouldSkipField部分
	    //将冗余字段在生成json对象时排除
	// ==========================================================================================
	
	/** 查询日期（分区） */
	private String qrydt = "";


	@Override
	public String getGroupId() {
		return "Gdjc_RequestLsdj_Trans";
	}
}
