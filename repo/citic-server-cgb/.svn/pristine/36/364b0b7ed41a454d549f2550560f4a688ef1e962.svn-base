package com.citic.server.jsga.domain.request;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.dict.DictBean;

/**
 * 常规查询反馈账户基本信息数据项
 * <ul>
 * <li>“检察院”报文中包含<em>[网银账户名称]</em>、<em>[最后登录IP]</em>和<em>[最后登录时间]</em>等字段，其它监管不包含。
 * <li>“国安”报文中包含<em>[开户网点所在地]</em>、<em>[销户网点代码]</em>、……等16个字段，其它监管不包含。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午8:17:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JSGA_QueryRequest_Account implements DictBean, Serializable {
	private static final long serialVersionUID = -2102163488656259622L;
	
	/** 卡号 */
	private String kh;
	
	/** 账号 */
	private String zh;
	
	/** 网银账户名称 */
	private String wyzhmc;
	
	/** 最后登录IP */
	private String zhdlip;
	
	/** 最后登录时间 */
	private String zhdlsj;
	
	/** 账户类别 */
	private String zhlb;
	
	/** 账户状态 */
	private String zhzt;
	
	/** 开户网点 */
	private String khwd;
	
	/** 开户网点代码 */
	private String khwddm;
	
	/** 开户日期 */
	private String khrq;
	
	/** 销户日期 */
	private String xhrq;
	
	/** 销户网点 */
	private String xhwd;
	
	/** 币种 */
	private String bz;
	
	/** 钞汇标志 */
	private String chbz;
	
	/** 账户余额 */
	private String zhye;
	
	/** 可用余额 */
	private String kyye;
	
	/** 最后交易时间 */
	private String zhjysj;
	
	/** 备注 */
	private String beiz;
	
	/** 开户网点所在地 */
	private String khwdszd;
	
	/** 销户网点代码 */
	private String xhwddm;
	
	/** 销户网点所在地 */
	private String xhwdszd;
	
	/** 有效期 */
	private String yxq;
	
	/** 银行卡签约电话 */
	private String yhkqydh;
	
	/** 银行卡签约时间 */
	private String yhkqysj;
	
	/** 银行卡签约网点 */
	private String yhkqywd;
	
	/** 银行卡终止签约时间 */
	private String syblrq;
	
	/** 帐号等级 */
	private String zhdj;
	
	/** 帐号类型 */
	private String zhlx;
	
	/** 支持外币类型 */
	private String zcwblx;
	
	/** 补卡时间 */
	private String bksj;
	
	/** 补卡网点 */
	private String bkwd;
	
	/** 补卡网点代码 */
	private String bkwddm;
	
	/** 补卡网点所在地 */
	private String bkwdszd;
	
	/** 银行卡终止签约时间（补卡） */
	private String byblrq;
	
	/** 为了满足深圳公安局（代号：8）的根据账卡号查询时的反馈报文，进行对象的相互引用，即：A引用B，B引用A */
	private JSGA_QueryRequest_Customer customer;
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	
	/** 请求单标识 */
	private String qqdbs = "";
	/** 查询日期 */
	private String qrydt = "";
	private String orgkey = "";
	private String tasktype = "";
	private String rwlsh = "";
	private String ctac = "";
	private String dwdh = "";
	private String qywd = "";
	private String syzzrq = "";
	private String zhmc="";
	
	@Override
	public String getGroupId() {
		return "JSGA_QUERYREQUEST_ACCOUNT";
	}
}
