package com.citic.server.gdjc.domain.request;

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data;

/**
 * 商业银行存款登记-账户
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午10:35:10
 */
@Data
public class Gdjc_RequestCkdj_Acc implements Serializable, DictBean {
	private static final long serialVersionUID = -4341343816151007629L;

	/** 户名 */
	private String accname;

	/** 账户类型（中文描述可依据各银行规则定义：如活期账户、定期账户、理财账户等） */
	private String acctype;

	/** 卡号 */
	private String cardno;

	/** 卡状态（1：待激活卡；2：正常；3：销卡；4：其他） */
	private String cardstatus;

	/** 账号 */
	private String account;

	/** 子帐号 */
	private String subaccount;

	/** 数据类型（1-卡信息；2-账户信息；3-子账户信息） */
	private String datatype;

	/** 余额（为空值时，传 0.00 ） */
	private String banlance;

	/** 币种 */
	private String currency;

	/** 汇钞类型（1-现钞；2-现汇） */
	private String exchangetype;

	/** 开户日期（YYYY-MM-DD） */
	private String opendate;

	/** 最后交易日期（YYYY-MM-DD） */
	private String tradedate;

	/** 开户网点编号 */
	private String openbranchno;

	/** 开户网点名称 */
	private String openbranchname;

	/** 开户网点电话 */
	private String openbranchtel;

	/** 开户网点地址 */
	private String openbranchaddr;

	/** 账户人联系地址 */
	private String addr;

	/** 账户人联系电话 */
	private String tel;

	/** 账户状态标识（0-正常；1-已冻结；2-已销户；3-已挂失；9-其它） */
	private String statusflag;

	/** 账户状态（用中文描述当前账户所处的状态） */
	private String status;

	/** 销户日期（YYYY-MM-DD） */
	private String closedate;

	/** 销户网点编号 */
	private String closebranchno;

	/** 销户网点名称 */
	private String closebranchname;

	/** 主机查询时间 */
	private String querytime;

	// ==========================================================================================
	// Help Field
	// ==========================================================================================
	/** 协作编号 */
	private String docno = "";
	/** 案件编号 */
	private String caseno = "";
	/** 唯一标识 */
	private String uniqueid = "";
	/** 查询日期（分区） */
	private String qrydt = "";
	private String ctac = "";

	/**
	 * 转码使用
	 */
	@Override
	public String getGroupId() {
		return "Gdjc_RequestCkdj_Acc";
	}
}
