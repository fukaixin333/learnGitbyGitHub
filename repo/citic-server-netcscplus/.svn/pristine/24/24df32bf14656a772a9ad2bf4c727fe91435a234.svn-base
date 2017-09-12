package com.citic.server.gf.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.dict.DictBean;

/**
 * 账户关联子账户信息
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 上午11:30:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryRequest_Glxx implements DictBean {
	private static final long serialVersionUID = 5291481509906104930L;
	
	/** 查询请求单号 */
	private String bdhm = "";
	
	/** 账户序号 */
	private String ccxh = "";
	
	/** 子账户序号 */
	private String glxh = "";
	
	/** 主账户账号 */
	private String zkhzh = "";
	
	/** 子账户类别 */
	private String glzhlb = "";
	
	/** 子账户账号 */
	private String glzhhm = "";
	
	/** 计价币种 */
	private String bz = "";
	
	/** 资产数额 */
	private String ye = "";
	
	/** 可用资产数额 */
	private String kyye = "";
	
	/** 备注 */
	private String beiz = "";
	
	private String qrydt = "";
	
	@Override
	public String getGroupId() {
		return "QUERYREQUEST_GLXX";
	}
}
