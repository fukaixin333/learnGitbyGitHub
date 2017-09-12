package com.citic.server.gf.domain.request;

import lombok.Data;

import com.citic.server.dict.DictBean;

/**
 * 账户金融资产信息
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 上午11:19:47
 */
@Data
public class QueryRequest_Jrxx implements DictBean {
	
	/** 查询请求单号 */
	private String bdhm = "";
	
	/** 账户序号 */
	private String ccxh = "";
	
	/** 金融资产序号 */
	private String zcxh = "";
	
	/** 金融资产名称 */
	private String zcmc = "";
	
	/** 金融资产类型 */
	private String zclx = "";
	
	/** 产品销售种类（识别直销、代销） */
	private String cpxszl = "";
	/** 地区号 */
	private String dqh = "";
	/** 金融产品编号 */
	private String jrcpbh = "";
	/** 理财账号 */
	private String lczh = "";
	/** 资金回款账户 */
	private String zjhkzh = "";
	
	/** 资产管理人 */
	private String zcglr = "";
	
	/** 资产可否通过银行交易 */
	private String zckfjy = "";
	
	/** 资产交易限制类型 */
	private String zcjyxz = "";
	
	/** 资产交易限制消除时间 */
	private String xzxcrq = "";
	
	/** 质押权人（直销产品时反馈） */
	private String zyqr = "";
	/** 托管人 （代销产品时需反馈） */
	private String tgr = "";
	/** 受益人 （代销产品时需反馈） */
	private String syr = "";
	/** 成立日 （代销产品时需反馈） */
	private String clr = "";
	/** 赎回日 （代销产品时需反馈） */
	private String shr = "";
	/** 托管账号 （代销产品时需反馈） */
	private String tgzh = "";
	
	/** 计量单位 */
	private String jldw = "";
	
	/** 计价币种 */
	private String bz = "";
	
	/** 资产单位价格 */
	private String zcdwjg = "";
	
	/** 数量/份额/金额 */
	private String se = "";
	/** 可控数量/份额/金额 */
	private String kyse = "";
	/** 资产总数额（金融产品的资产总金额） */
	private String zczje = "";
	
	/** 备注 */
	private String beiz = "";
	
	/** 开户账号 */
	private String khzh = "";
	
	/** 查询日期 */
	private String qrydt = "";
	
	/** 开户网点代码 */
	private String khwddm = "";

	@Override
	public String getGroupId() {
		return "QUERYREQUEST_JRXX";
	}
	
}
