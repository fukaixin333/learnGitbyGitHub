package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 冻结/续冻/解除冻结反馈结果数据项
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 上午10:04:25
 */
@Data
public class CBRC_FreezeRequest_Record implements Serializable {
	private static final long serialVersionUID = 8019289361986433795L;
	
	/** 任务流水号 */
	private String rwlsh;
	
	/** 账号 */
	private String zh;
	
	/** 卡号 */
	private String kh;
	
	/** 执行结果 */
	private String zxjg;
	
	/** 申请冻结限额 */
	private String sqdjxe;
	
	/** 执行冻结金额 */
	private String sdje;
	
	/** 账户余额 */
	private String ye;
	
	/** 执行起始时间 */
	private String zxqssj;
	
	/** 执行结束日期 */
	private String djjsrq;
	
	/** 未能冻结原因 */
	private String wndjyy;
	
	/** 在先冻结机关 */
	private String djjg;
	
	/** 在先冻结金额 */
	private String djje;
	
	/** 在先冻结到期日 */
	private String djjzrq;
	
	/** 未冻结金额 */
	private String wdjje;
	
	/** 账户可用余额 */
	private String zhkyye;
	
	/** 备注 */
	private String beiz;
	
	/** 冻结/续冻/解冻明细 */
	private List<CBRC_FreezeRequest_Detail> freezeDetailList;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	private String status = "";
	private String organkey = "";
	private String hxappid = "";
	private String qqcslx = "";
	private String qqdbs = "";
	private String tasktype = "";
	private String qrydt = "";
	private String djzhhz = "";
	private String djfs = "";
	private String msgcheckresult = "";
	private String zhxh="";
	private String ztlb="";
}
