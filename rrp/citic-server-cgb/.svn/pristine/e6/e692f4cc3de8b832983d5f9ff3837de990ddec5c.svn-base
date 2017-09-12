package com.citic.server.gdjg.domain.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询类--账户信息
 * @author wangbo
 * @date 2017年5月23日 下午9:05:57
 */
@Data
public class Gdjg_Response_AccCx  implements Serializable{
	private static final long serialVersionUID = 5658903439220683748L;
	
	/**
	 * 常规查询
	 */
	/** 唯一标识 */
	private String uniqueid;
	/** 类型     UNIT表示单位   PERSON表示自然人  */
	private String type;
	/** 名称 */
	private String name;
	/** 证件类型 */
	private String idtype;
	/** 证件号码 */
	private String id;
	
	/**
	 * 动态查询增加
	 */
	/** 原流水号 */
	private String oldseq;
	/** 紧急程度 */
	private String urgency;
	/** 账号 */
	private String account;
	/** 账户类型 */
	private String acctype;
	/** 户名 */
	private String accname;
	/** 时间区间 */
	private String intervals;
	/** 开始日期 */
	private String startdate;
	/** 结束日期 */
	private String enddate;
	/** 文件名称 */
	private String notifydoc;
	/** 文书文号 */
	private String docnum;
	
	//冻结
	/** 冻结流水号 */
	private String froseq;
	/** 原冻结流水号*/
	private String oldfroseq;
	/** 币种 */
	private String currency;
	/** 汇钞类型 */
	private String exchangetype;
	/** 冻结类型 */
	private String frotype;
	/** 冻结方式 */
	private String fromode;
	/** 冻结金额 */
	private String frobanlance;
	/** 冻结申请时间*/
	private String freezeappdate;
	/** 冻结开始日期 */
	private String frostartdate;
	/** 冻结结束日期 */
	private String froenddate;
	/** 是否调取电子证据 */
	private String istransee;
	
	
	//解冻
	/** 解冻流水号 */
	private String thawseq;
	/** 解冻方式 */
	private String thawmode;
	/** 解冻金额 */
	private String thawbanlance;
	/** 解冻申请时间*/
	private String unfreezeappdate;
	/** 解冻日期 */
	private String thawdate;
	
	//紧急止付
	/** 止付类型 */
	private String stoppayment;
	
}
