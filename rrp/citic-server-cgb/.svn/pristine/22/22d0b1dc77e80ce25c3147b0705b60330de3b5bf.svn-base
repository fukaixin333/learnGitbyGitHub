package com.citic.server.gdjg.domain;

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author liuxuanfei
 * @date 2017年6月15日 下午9:28:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br57_kzqq_input implements Serializable,DictBean{

	private static final long serialVersionUID = 1709834914997799372L;

	/** 协作编号 */
	private String docno = "";
	/** 案件编号 */
	private String caseno = "";
	/** 唯一标识 */
	private String uniqueid = "";
	
	//冻结
	/** 冻结流水号 */
	private String froseq;
	/** 原冻结流水号*/
	private String oldfroseq;
	/** 账号 */
	private String account;
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
	/** 文件名称 */
	private String notifydoc;
	/** 文书文号 */
	private String docnum;
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
	/** 证件类型 */
	private String idtype;
	/** 证件号码 */
	private String id;
	/** 原流水号 */
	private String oldseq;
	/** 止付类型 */
	private String stoppayment;
	
	/** 查询日期（分区） */
	private String qrydt = "";
	/** 侦办单位名称 */
	private String exeunit = "";
	/** 冻结编号（Artery） */
	private String fronumber ="";
	
	@Override
	public String getGroupId() {
		return "Gdjg_Br57_kzqq_input";
	}
	                               
}
