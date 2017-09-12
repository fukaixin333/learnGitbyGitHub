package com.citic.server.whpsb.domain.request;

import java.io.Serializable;

import lombok.Setter;

import com.citic.server.dict.DictBean;

/**
 * 公安提供账号信息查询请求-详情
 * 
 * @author Liu Xuanfei
 * @date 2016年8月18日 下午4:59:29
 */
@Setter
public class Whpsb_RequestKhzl_Detail implements Serializable,DictBean {
	private static final long serialVersionUID = -8053819397064647903L;
	
	// ==========================================================================================
	//                     个人/单位 意同名不同 
	// ==========================================================================================
	/** 住宅地址（zzdz）/单位地址 */
	private String dz;
	
	/** 住宅电话（zzdh）/单位电话 */
	private String dh;
	
	// ==========================================================================================
	//                     个人独有
	// ==========================================================================================
	/** 工作单位 */
	private String gzdw;
	
	/** 单位地址 */
	private String dwdz;
	
	/** 单位电话 */
	private String dwdh;
	
	/** 联系地址 */
	private String lxdz;
	
	/** 联系固话 */
	private String lxdh;
	
	/** 联系手机 */
	private String lxsj;
	
	/** 邮政编码 */
	private String yzbm;
	
	/** 通讯地址 */
	private String txdz;
	
	// ==========================================================================================
	//                     单位独有
	// ==========================================================================================
	/** 联系人 */
	private String lxr;
	
	/** 法人代表 */
	private String frdb;
	
	/** 法人证件类型 */
	private String frdbzjlx;
	
	/** 法人证件号码 */
	private String frdbzjhm;
	
	/** 工商营业执照号 */
	private String gsyyzzh;
	
	/** 国税纳税号 */
	private String gsnsh;
	
	/** 地税纳税号 */
	private String dsnsh;
	
	// ==========================================================================================
	//                     个人/单位 同名同意
	// ==========================================================================================
	/** E_MAIL地址 */
	private String email;
	
	/** 开户网点 */
	private String khwd;
	
	/** 开户网点代码 */
	private String khwddm;
	
	/** 代办人姓名 */
	private String dbrxm;
	
	/** 代办人证照类型 */
	private String dbrzzlx;
	
	/** 代办人证照号码 */
	private String dbrzzh;
	
	//----------------------------附加字段---------------------------------------
	private String msgseq="";
	private String bdhm="";
	private String qrydt="";
	//---------------------------转码用------------------------------------------
	@Override
	public String getGroupId() {
		//参数配置见BB13_DICTGROUP和BB13_DICTGROUPITEM
		return "Whpsb_RequestKhzl_Detail";
	}
	

	//---------------------------get将null转换为"",处理null在jibx处理时不生成标签的问题------------------
	
	public String getDz() {
		return dz==null?"":dz;
	}
	public String getDh() {
		return dh==null?"":dh;
	}
	public String getGzdw() {
		return gzdw==null?"":gzdw;
	}
	public String getDwdz() {
		return dwdz==null?"":dwdz;
	}
	public String getDwdh() {
		return dwdh==null?"":dwdh;
	}
	public String getLxdz() {
		return lxdz==null?"":lxdz;
	}
	public String getLxdh() {
		return lxdh==null?"":lxdh;
	}
	public String getLxsj() {
		return lxsj==null?"":lxsj;
	}
	public String getYzbm() {
		return yzbm==null?"":yzbm;
	}
	public String getTxdz() {
		return txdz==null?"":txdz;
	}
	public String getLxr() {
		return lxr==null?"":lxr;
	}
	public String getFrdb() {
		return frdb==null?"":frdb;
	}
	public String getFrdbzjlx() {
		return frdbzjlx==null?"":frdbzjlx;
	}
	public String getFrdbzjhm() {
		return frdbzjhm==null?"":frdbzjhm;
	}
	public String getGsyyzzh() {
		return gsyyzzh==null?"":gsyyzzh;
	}
	public String getGsnsh() {
		return gsnsh==null?"":gsnsh;
	}
	public String getDsnsh() {
		return dsnsh==null?"":dsnsh;
	}
	public String getEmail() {
		return email==null?"":email;
	}
	public String getKhwd() {
		return khwd==null?"":khwd;
	}
	public String getKhwddm() {
		return khwddm==null?"":khwddm;
	}
	public String getDbrxm() {
		return dbrxm==null?"":dbrxm;
	}
	public String getDbrzzlx() {
		return dbrzzlx==null?"":dbrzzlx;
	}
	public String getDbrzzh() {
		return dbrzzh==null?"":dbrzzh;
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
	
}
