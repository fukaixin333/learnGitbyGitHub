package com.citic.server.whpsb.domain.request;

import java.io.Serializable;

import lombok.Setter;

import com.citic.server.dict.DictBean;

/**
 * 银行提供账卡号持有人资料查询反馈-详情
 * 
 * @author Liu Xuanfei
 * @date 2016年8月19日 上午10:14:46
 */
@Setter
public class Whpsb_RequestCkrzl_Detail implements Serializable,DictBean {
	private static final long serialVersionUID = 4862707586439151351L;
	
	/** 单位名称/个人姓名 */
	private String ckrxm;
	
	/** 证照类型 */
	private String zzlx;
	
	/** 证照/证件号码 */
	private String zzhm;
	
	//-------------------------附加字段--------------------------------------------------
	private String msgseq="";
	private String bdhm="";
	private String qrydt="";
	//--------------------------转码使用------------------------------------------------
	@Override
	public String getGroupId() {
		//参数配置见BB13_DICTGROUP和BB13_DICTGROUPITEM
		return "Whpsb_RequestCkrzl_Detail";
	}
	
	//------------------------get将null转换为"",处理null在jibx处理时不生成标签的问题----------------
	public String getCkrxm() {
		return ckrxm==null?"":ckrxm;
	}
	public String getZzlx() {
		return zzlx ==null?"":zzlx;
	}
	public String getZzhm() {
		return zzhm ==null?"":zzhm;
	}
	public String getMsgseq() {
		return msgseq ==null?"":msgseq;
	}
	public String getBdhm() {
		return bdhm ==null?"":bdhm;
	}
	public String getQrydt() {
		return qrydt ==null?"":qrydt;
	}
	
}
