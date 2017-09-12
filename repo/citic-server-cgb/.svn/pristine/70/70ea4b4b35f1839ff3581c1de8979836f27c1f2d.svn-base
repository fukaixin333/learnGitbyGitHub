package com.citic.server.whpsb.domain;

import java.io.Serializable;

import lombok.Setter;

/**
 * 涉案对象
 * 
 * @author Liu Xuanfei
 * @date 2016年8月18日 下午5:19:15
 */
@Setter
public class Whpsb_SadxBody implements Serializable {
	private static final long serialVersionUID = 3955911308939513321L;
	
	// =====================================================================
	//     1-账户信息查询、2-账户持有人查询、3-开户资料查询、4-交易明细查询
	// =====================================================================
	/** 案号（12位批次号 + 案事件编号） */
	private String ah;
	
	/** 承办单位名称 */
	private String cbdw;
	
	/** 承办人1 */
	private String cbr1;
	
	/** 承办人2 */
	private String cbr2;
	
	// =====================================================================
	//                   1-账户信息查询、3-开户资料查询
	// =====================================================================
	/** 证照类型（见证照类型代码表，个人一般为身份证，企业一般为组织机构代码） */
	private String zzlx;
	
	/** 证照号码 */
	private String zzhm;
	
	/** 涉案对象名称（个人一般为姓名，企业一般为企业名称） */
	private String mc;
	
	// =====================================================================
	//                   2-账户持有人查询、4-交易明细查询
	// =====================================================================
	/** 账(卡)号 */
	private String zh;
	
	// =====================================================================
	//                   4-交易明细查询
	// =====================================================================
	/** 查询时间（起） */
	private String cxkssj;
	
	/** 查询时间（止） */
	private String cxjssj;
	
	// =====================================================================
	//                   1、2、3、4-反馈
	// =====================================================================
	/** 处理结果 */
	private String cljg;
	
	/** 失败原因 */
	private String sbyy;

	
	//------------------------get将null转换为"",处理null在jibx处理时不生成标签的问题----------------
	public String getAh() {
		return ah==null?"":ah;
	}

	public String getCbdw() {
		return cbdw==null?"":cbdw;
	}

	public String getCbr1() {
		return cbr1==null?"":cbr1;
	}

	public String getCbr2() {
		return cbr2==null?"":cbr2;
	}

	public String getZzlx() {
		return zzlx==null?"":zzlx;
	}

	public String getZzhm() {
		return zzhm==null?"":zzhm;
	}

	public String getMc() {
		return mc==null?"":mc;
	}

	public String getZh() {
		return zh==null?"":zh;
	}

	public String getCxkssj() {
		return cxkssj==null?"":cxkssj;
	}

	public String getCxjssj() {
		return cxjssj==null?"":cxjssj;
	}

	public String getCljg() {
		return cljg==null?"":cljg;
	}

	public String getSbyy() {
		return sbyy==null?"":sbyy;
	}
	
	
}
