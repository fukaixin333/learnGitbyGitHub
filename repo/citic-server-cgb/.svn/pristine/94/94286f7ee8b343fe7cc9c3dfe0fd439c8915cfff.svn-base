package com.citic.server.shpsb.domain;

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data;

/**
 * 涉案对象
 * 
 * @author Liu Xuanfei
 * @date 2016年11月8日 下午8:16:34
 */
@Data
public class ShpsbSadx implements DictBean, Serializable {
	private static final long serialVersionUID = 8020662575870432157L;
	
	/** 案号 */
	private String ah;
	
	/** 承办单位（办案单位名称） */
	private String cbdw;
	
	/** 承办人1 */
	private String cbr1;
	
	/** 承办人2 */
	private String cbr2;
	
	//////// 账户信息查询|开户资料查询 ////////
	/** 证照类型代码 */
	private String zzlx;
	
	/** 证照号码 */
	private String zzhm;
	
	/** 涉案对象名称（姓名或企业名称） */
	private String mc;
	
	//////// 操作日志查询 ////////
	/** 条件类别 */
	private String tjlb;
	
	//////// 操作日志查询|持卡人资料查询|交易明细查询|关联号查询 ////////
	/** 账卡号 */
	private String zh;
	
	//////// 操作日志查询 ////////
	/** 交易流水号 */
	private String jylsh;
	
	//////// 操作日志查询|交易明细查询|关联号查询 ////////
	/** 查询时间（起） */
	private String cxkssj;
	
	/** 查询时间（止） */
	private String cxjssj;
	
	//////// 对端账号查询 ////////
	/** 交易关联号 */
	private String jyglh;
	
	// ==========================================================================================
	//                     银行向公安反馈时所需字段
	// ==========================================================================================
	
	/** 处理结果 */
	private String cljg;
	
	/** 失败原因 */
	private String sbyy;
	
	/** I:对私 C:对公 */
	private String party_class_cd = "";
	
	/** 归属机构 */
	private String orgkey = "";
	
	/**
	 * grzhxxcx:个人账户信息查询 dwzhxxcx:单位账户信息查询
	 * grzhcyrcx:个人账（卡）号持有人资料查询
	 * dwzhcyrcx:单位账（卡）号持有人资料查询
	 * grkhzlcx:个人开户资料查询
	 * dwkhzlcx:单位开户资料查询
	 * grjymxcx:个人交易明细查询
	 * dwjymxcx:单位交易明细查询
	 */
	private String qrymode = "";
	/** 报文批次号 */
	private String msgseq = "";
	
	/** 请求单号(案号_序号） */
	private String bdhm = "";
	
	/** 查询日期（分区） */
	private String qrydt = "";
	
	@Override
	public String getGroupId() {
		return "SHPSB_SADX";
	}
}
