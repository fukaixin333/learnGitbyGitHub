package com.citic.server.shpsb.domain.request;

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data;

/**
 * 账号信息查询反馈 - 账号信息
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 上午9:34:44
 */
@Data
public class ShpsbRequestZhxxMx  implements Serializable,DictBean {
	private static final long serialVersionUID = 3668407545579403614L;
	
	/** 账（卡）号 */
	private String zh;
	
	/** 帐户状态 */
	private String zhzt;
	
	/** 开户银行名称 */
	private String yhmc;
	
	/** 查询时间，在银行系统中查询账户信息的截止时间 */
	private String cxsj;
	
	/** 币种 */
	private String hbzl;
	
	/** 帐户余额 */
	private String zhye;
	
	/** 可用余额 */
	private String kyye;
	
	/** 开户日期 */
	private String khrq;
	
	/** 销户日期 */
	private String xhrq;
	
	/** 账户类别 */
	private String zhlb;
	
	/** 账户序号，银行实际账户序号 */
	private String zhxh;
	
	private String bdhm;
	
	private String msgseq;
	
	private String qrydt;
	
	private String ctac="";
	
	@Override
	public String getGroupId() {
		return "SHPSB_REQUEST_ZHXXMX";
	}
}
