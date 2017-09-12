package com.citic.server.gdjg.domain.request;

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data;
/**
 * 查询类--金融产品
 * @author wangbo
 * @date 2017年5月19日 下午2:59:12
 */
@Data
public class Gdjg_Request_FinancialProductsCx implements Serializable, DictBean{
	private static final long serialVersionUID = -4788052162472673353L;
	
	/** 产品类别 */
	private String producttype;
	/** 机构名称 */
	private String orgname;
	/** 产品代码 */
	private String productcode;
	/** 产品名称 */
	private String productname;
	/** 币种 */
	private String currency;
	/** 金额 */
	private String banlance;
	/** 销售类型 */
	private String salestype;
	/** 产品摘要 */
	private String productsum;
	/** 产品备注 */
	private String productremark;
	
	/** 协作编号 */
	private String docno = "";
	/** 案件编号 */
	private String caseno = "";
	/** 唯一标识 */
	private String uniqueid;
	/** 查询日期（分区） */
	private String qrydt = "";
	

	@Override
	/** 转码使用 */
	public String getGroupId() {
		return "Gdjg_Request_FinancialProductsCx";
	}
}
