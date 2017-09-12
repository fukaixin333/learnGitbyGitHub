/*
 * =============================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created [2016-02-18]
 * =============================================
 */

package com.citic.server.gf.domain.response;

import lombok.Data;

import com.citic.server.dict.DictBean;

/**
 * 司法控制账户信息
 * 
 * @author Liu Xuanfei
 * @date 2016年3月1日 下午7:19:11
 */
@Data
public class ControlResponse_Kzzh implements DictBean {
	private static final long serialVersionUID = 8364013023248414643L;
	
	/** 序号 */
	private String ccxh = "";;
	
	/** 控制类型，1-存款，2-非存款类金融资产 */
	private String kzlx = "";
	
	/** 控制措施 */
	private String kzcs = "";
	
	/** 开户账号 */
	private String khzh = "";
	
	/** 开户账号子账号 */
	private String glzhhm = "";
	
	/** 账户类别 */
	private String cclb = "";
	
	/** 开户网点 */
	private String khwd = "";
	
	/** 人行统一的网点代码 */
	private String khwddm = "";
	
	/** 金融资产名称 */
	private String zcmc = "";
	
	/** 金融资产类型KZLX=2时提供该项 */
	private String zclx = "";
	
	/** 产品销售种类 */
	private String cpxszl;
	
	/** 地区号 */
	private String dqh;
	
	/** 金融产品编号 */
	private String jrcpbh;
	
	/** 理财账号 */
	private String lczh;
	
	/** 资金回款账户 */
	private String zjhkzh;
	
	/** 计量单位KZLX=2时提供该项 */
	private String jldw = "";
	
	/** 1-账户下的资金,2-账户 */
	private String kznr = "";
	
	/** 申请控制金额币种 */
	private String bz = "";
	
	/** 申请控制金额 */
	private String je = "";
	
	/** 申请控制开始时间 */
	private String ksrq = "";
	
	/** 申请控制结束时间 */
	private String jsrq = "";
	
	/** 申请控制数量/份额/金额 */
	private String se = "";
	
	/** 是否结汇 */
	private String sfjh = "";
	
	/** 裁定书文号 */
	private String ckwh = "";
	
	/** 执行款专户户名 */
	private String zxkzhhm = "";
	
	/** 执行款专户开户行 */
	private String zxkzhkhh = "";
	
	/** 执行款专户开户行号 */
	private String zxkzhkhhhh = "";
	
	/** 执行款专户账号 */
	private String zxkzhzh = "";
	
	/** 执行款专户类型 */
	private String zxkzhlx = "";
	
	/** 原冻结案号（解除冻结时需要） */
	private String ydjah = "";
	
	/** 执行款专户内部账号 */
	private String inacct = "";
	
	/** 执行款专户内部账号名称 */
	private String inacctname = "";
	
	//----------------------------------------------------------------''
	
	/** 原冻结请求单号（任务流水号）（解除冻结时需要） */
	private String ydjdh = "";
	/* 请求单号 */
	private String bdhm = "";
	/* 核心冻结编号 */
	private String hxappid = "";
	/* 理财冻结编号*/
	private String lcappid="";
	/* 查询日期 */
	private String qrydt = "";
	/* 机构号 */
	private String orgkey = "";
	
	/** status_cd: 任务状态 */
	private String status_cd = "";
	
	private String msgcheckresult = "";
	
	/** xm: 被控制人姓名 */
	private String xm;
	/** zjlx: 被控制人证件类型 */
	private String zjlx;
	/** dsrzjhm: 被控制人证件号码/组织机构代码 */
	private String dsrzjhm;
	
	private String fymc = "";
	private String cbr = "";
	private String gzzbh = "";
	private String gwzbh = "";
	private String skje = "";
	private String skse = "";
	private String ah = "";
	private String ckh = "";
	
	/** zhlx: 账户类型 */
	private String zhlx;
	/** status: 阶段/结果 */
	private String status = "";
	/** jjyy: 拒绝/失败原因 */
	private String jjyy;
	/** beiz: 备注说明 */
	private String beiz;
	/** sxzh: 收息账户 */
	private String sxzh;
	/** sxzhmc: 收息账户姓名 */
	private String sxzhmc;
	/** sxzhkhhh: 收息账户开户行号 */
	private String sxzhkhhh;
	/** sxzhkhhmc: 收息账户开户行名称 */
	private String sxzhkhhmc;
	/** userid: 操作员 */
	private String userid;
	/** checkuserid: 复核员 */
	private String checkuserid;
	
	@Override
	public String getGroupId() {
		return "CONTROLRESPONSE_KZZH";
	}
}
