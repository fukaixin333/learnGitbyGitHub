package com.citic.server.gf.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.dict.DictBean;

/**
 * 账户资金交易往来信息
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 上午11:22:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryRequest_Wlxx implements DictBean {
	private static final long serialVersionUID = 5856071451648199495L;

	/** 查询请求单号 */
	private String bdhm = "";
	
	/** 账户序号 */
	private String ccxh = "";
	
	/** 账户 */
	private String khzh = "";
	
	/** 资金往来序号 */
	private String wlxh = "";
	
	/** 交易种类 */
	private String jyzl = "";
	
	/** 借贷方向（资金流向） */
	private String zjlx = "";
	
	/** 交易币种 */
	private String bz = "";
	
	/** 交易金额 */
	private String je = "";
	
	/** 交易日期 */
	private String jysj = "";
	
	/** 交易流水号 */
	private String jylsh = "";
	
	/** 交易对方姓名/名称 */
	private String zckzxm = "";
	
	/** 交易对方账号 */
	private String zckzh = "";
	
	/** 交易对方账号开户行 */
	private String zckzhkhh = "";
	
	/** 交易对方账号开户行行号 */
	private String jydsyhhh = "";
	
	/** 交易对方开户证件类型 */
	private String jydszjlx = "";
	
	/** 交易对方开户证件号码 */
	private String jydszjhm = "";
	
	/** 交易对方通讯地址 */
	private String jydstxdz = "";
	
	/** 交易对方邮政编码 */
	private String jydsyzbm = "";
	
	/** 交易对方联系电话 */
	private String jydslxdh = "";
	
	/** 备注 */
	private String beiz = "";
	
	private String organkey = "";
	private String qrydt = "";
	
	@Override
	public String getGroupId() {
		return "QUERYREQUEST_WLXX";
	}
}
