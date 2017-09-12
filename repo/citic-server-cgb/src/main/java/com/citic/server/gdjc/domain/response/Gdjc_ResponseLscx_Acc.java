package com.citic.server.gdjc.domain.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 商业银行交易流水查询-账户
 * <p>
 * <strong>注：开始日期和结束 日期都为空时，表示查询本账户的全部交易流水</strong>
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午11:44:32
 */
@Data
public class Gdjc_ResponseLscx_Acc implements Serializable {
	private static final long serialVersionUID = -1984455707373429477L;
	
	/** 唯一标识 */
	private String uniqueid;
	
	/** 账号 */
	private String account;
	
	/** 账户类型 */
	private String acctype;
	
	/** 公司名称/姓名 */
	private String accname;
	
	/** 类型（UNIT-单位；PERSON-自然人） */
	private String type;
	
	/** 证件类型 */
	private String idtype;
	
	/** 证件号码 */
	private String id;
	
	/** 开始日期（YYYY-MM-DD） */
	private String startdate;
	
	/** 结束日期（YYYY-MM-DD） */
	private String enddate;
}
