package com.citic.server.whpsb.domain.request;

import java.io.Serializable;

import lombok.Setter;

import com.citic.server.dict.DictBean;

/**
 * 银行提供账户信息查询反馈-详情
 * 
 * @author Liu Xuanfei
 * @date 2016年8月18日 下午6:04:47
 */
@Setter
public class Whpsb_RequestZhxx_Detail implements Serializable,DictBean {
	private static final long serialVersionUID = -4708905591097207866L;
	
	/** 账户序号 */
	private String zhxh;
	
	/** 开户账号 */
	private String zh;
	
	/** 账户类别 */
	private String zhlb;
	
	/** 帐户状态 */
	private String zhzt;
	
	/** 开户银行名称 */
	private String yhmc;
	
	/** 开户银行代码 */
	private String khwddm;
	
	/** 开户日期 */
	private String khrq;
	
	/** 销户日期 */
	private String xhrq;
	
	/** 查询时间，在银行系统中查询账户信息的截止时间 */
	private String cxsj;
	
	// <zhjeqk>
	// 监管发文关于币种是单独的一个组，这就得需要最后在反馈的时候合并同一个账户的信息（比如活期、定期等）。
	// 如果合并，会有很多问题需要考虑，所以，先按子账户的粒度进行反馈，即，最终每个XML报文中币种组下就一条数据（这一点由JiBX完成）。
	/** 币种 */
	private String hbzl;
	
	/** 帐户余额 */
	private String zhye;
	
	/** 账户可用余额 */
	private String kyye;
	// </zhjeqk>
	
	
	//-------------------------------------------------------------------
	private String msgseq="";
	private String bdhm="";
	private String qrydt="";
	private String ctac="";
	//--------------------------转码使用------------------------------------------------
	@Override
	public String getGroupId() {
		//参数配置见BB13_DICTGROUP和BB13_DICTGROUPITEM
		return "Whpsb_RequestZhxx_Detail";
	}
	//------------------------get将null转换为"",处理null在jibx处理时不生成标签的问题----------------
	public String getZhxh() {
		return zhxh==null?"":zhxh;
	}
	public String getZh() {
		return zh==null?"":zh;
	}
	public String getZhlb() {
		return zhlb==null?"":zhlb;
	}
	public String getZhzt() {
		return zhzt==null?"":zhzt;
	}
	public String getYhmc() {
		return yhmc==null?"":yhmc;
	}
	public String getKhwddm() {
		return khwddm==null?"":khwddm;
	}
	public String getKhrq() {
		return khrq==null?"":khrq;
	}
	public String getXhrq() {
		return xhrq==null?"":xhrq;
	}
	public String getCxsj() {
		return cxsj==null?"":cxsj;
	}
	public String getHbzl() {
		return hbzl==null?"":hbzl;
	}
	public String getZhye() {
		return zhye==null?"":zhye;
	}
	public String getKyye() {
		return kyye==null?"":kyye;
	}
	public String getMsgseq() {
		return msgseq==null?"":msgseq;
	}
	public String getBdhm() {
		return bdhm==null?"":bdhm;
	}
	public String getQrydt() {
		return qrydt==null?"":qrydt;
	}
	public String getCtac() {
		return ctac==null?"":ctac;
	}
	
	
}
