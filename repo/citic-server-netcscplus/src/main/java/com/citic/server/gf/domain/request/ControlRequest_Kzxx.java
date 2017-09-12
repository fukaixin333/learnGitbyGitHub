package com.citic.server.gf.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 控制结果反馈信息
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 下午1:56:46
 */
@Data
public class ControlRequest_Kzxx implements Serializable {
	private static final long serialVersionUID = -7401711386273601597L;
	
	public ControlRequest_Kzxx() {
		// Do nothing
	}
	
	public ControlRequest_Kzxx(String kzzt, String wnkzyy) {
		this.kzzt = kzzt;
		this.wnkzyy = wnkzyy;
		this.jjyy = wnkzyy;
	}
	
	public ControlRequest_Kzxx(String bdhm, String ccxh, String kzzt, String wnkzyy) {
		this.bdhm = bdhm;
		this.ccxh = ccxh;
		this.kzzt = kzzt;
		this.wnkzyy = wnkzyy;
	}
	
	/** 控制请求单号 */
	private String bdhm = "";
	
	/** 序号 */
	private String ccxh = "";
	
	/** 开户账号 */
	private String khzh = "";
	
	/** glzhhm: 申请控制的子账号 */
	private String glzhhm = "";
	
	/** 金融产品编号 */
	private String jrcpbh = "";
	
	/** 理财账号 */
	private String lczh = "";
	
	/** 控制内容 */
	private String kznr = "";
	
	/** 控制结果 */
	private String kzzt = "";
	
	/** 账户余额 */
	private String ye = "";
	
	/** 可用余额 */
	private String kyye = "";
	
	/** 措施开始日期 */
	private String csksrq = "";
	
	/** 措施结束日期 */
	private String csjsrq = "";
	
	/** 冻结限额 */
	private String djxe = "";
	
	/** 实际控制数量/份额/金额 */
	private String skse;
	
	/** 实际控制可用金额（数额） */
	private String skje = "";
	
	/** 超额控制金额（数额） */
	private String ceskje = "";
	
	/** 未能控制原因 */
	private String wnkzyy = "";
	
	/** 备注 */
	private String beiz = "";
	
	/** 在先冻结信息列表 */
	private List<ControlRequest_Djxx> djxxList;
	
	/** 共有权/优先受偿权信息列表 */
	private List<ControlRequest_Qlxx> qlxxList;
	
	private String orgkey = "";
	private String kzcs = "";
	private String kzlx = "";
	private String hxappid = "";
	private String lcappid = ""; //  理财冻结编号
	/** status_cd: 任务状态 */
	private String status_cd = "";
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	/** 被执行人开户行分行号 */
	private String khhfhh;
	/** 被执行人开户行所号 */
	private String khhsh;
	/** zhlx: 账户类型 */
	private String zhlx = ""; // 用于 BR31_KZZH
	/** status: 阶段/结果 */
	private String status = ""; // 用于 BR31_KZZH
	/** jjyy: 拒绝/失败原因 */
	private String jjyy = ""; // 用于 BR31_KZZH
	
	/** wtrq: 委托日期（二代） */
	private String wtrq; // 用于 BR31_KZCL_INFO
	/** jylsh: 交易流水号（二代） */
	private String jylsh; // 用于 BR31_KZCL_INFO
	/** fqhh: 发起行所号（二代） */
	private String fqhh; // 用于 BR31_KZCL_INFO
	/** lxwtrq: 利息，委托日期（二代） */
	private String lxwtrq; // 用于 BR31_KZCL_INFO
	/** lxjylsh: 利息，交易流水号（二代） */
	private String lxjylsh; // 用于 BR31_KZCL_INFO
	/** lxfqhh: 利息，发起行所号（二代） */
	private String lxfqhh; // 用于 BR31_KZCL_INFO
	
	/** 措施开始日期_CN */
	private String csksrq_cn;
	
	/** 措施结束日期_CN */
	private String csjsrq_cn;
}
