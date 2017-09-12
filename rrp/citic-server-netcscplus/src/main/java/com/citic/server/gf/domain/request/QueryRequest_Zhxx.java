package com.citic.server.gf.domain.request;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.dict.DictBean;

/**
 * 账户基本信息
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 上午11:03:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryRequest_Zhxx implements DictBean {
	private static final long serialVersionUID = 8590577954941460754L;
	
	/** 查询请求单号 */
	private String bdhm = "";
	
	/** 账户序号 */
	private String ccxh = "";
	
	/** 开户账号，默认值为“查无开户信息” */
	private String khzh = "查无开户信息";
	
	/**
	 * 账户类别（如理财卡、准贷记卡、信用卡等）
	 * 理财产品回款资金账户请填写账户类别：理财产品回款资金账户。
	 */
	private String cclb = "";
	
	/** 账户状态 */
	private String zhzt = "";
	
	/** 开户网点 */
	private String khwd = "";
	
	/** 开户网点代码（人行统一的网点代码） */
	private String khwddm = "";
	
	/** 开户日期 */
	private String khrq = "";
	
	/** 销户日期 */
	private String xhrq = "";
	
	/** 计价币种 */
	private String bz = "";
	
	/** 资产数额 */
	private String ye = "";
	
	/** 可用资产数额 */
	private String kyye = "";
	
	/** 关联资金账户 */
	private String glzjzh = "";
	
	/** 反馈结果时间 */
	private String fksj = "";
	
	/** 通讯地址 */
	private String txdz = "";
	
	/** 邮政编码 */
	private String yzbm = "";
	
	/** 联系电话 */
	private String lxdh = "";
	
	/** 备注 */
	private String beiz = "";
	
	/** 账户金融资产信息列表 */
	private List<QueryRequest_Jrxx> jrxxList;
	
	/** 账户司法强制措施信息列表 */
	private List<QueryRequest_Djxx> djxxList;
	
	/** 账户资金交易往来信息 */
	private List<QueryRequest_Wlxx> wlxxList;
	
	/** 账户共有权/优先权信息列表 */
	private List<QueryRequest_Qlxx> qlxxList;
	
	/** 账户关联子账户信息列表 */
	private List<QueryRequest_Glxx> glxxList;
	
	private String qrydt = "";
	
	//传给核心的账号查交易的账号
	private String cardnumber = "";
	private String orgkey = "";
	
	public static QueryRequest_Zhxx getHtInstance(String bdhm, String error, String desc) {
		QueryRequest_Zhxx htIns = new QueryRequest_Zhxx();
		htIns.setBdhm(bdhm);
		htIns.setCcxh("1");
		htIns.setKhzh(error);
		htIns.setBeiz(desc);
		return htIns;
	}
	
	@Override
	public String getGroupId() {
		return "QUERYREQUEST_ZHXX";
	}
}
