package com.citic.server.shpsb.domain.request;

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data;

/**
 * 账号持有人资料查询反馈 - 账号持卡人资料
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 上午9:51:43
 */
@Data
public class ShpsbRequestZhcyrMx implements Serializable,DictBean {
	private static final long serialVersionUID = -2346410439483894367L;
	
	/** 个人姓名/单位名称 */
	private String ckrxm;
	
	/** 证照类型 */
	private String zzlx;
	
	/** 证件号码 */
	private String zzhm;
	
	private String bdhm;
	private String msgseq;
	private String qrydt;
	
	@Override
	public String getGroupId() {
		return "SHPSB_REQUEST_ZHCYRMX";
	}
}
