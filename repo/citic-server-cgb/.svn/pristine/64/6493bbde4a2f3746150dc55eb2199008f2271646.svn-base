package com.citic.server.jsga.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 查询人数据项
 * <ul>
 * <li>“江西省公安”和“深圳分行公安厅”查控请求报文中包含<em>[请求人办公电话]</em>、<em>[协查人手机号]</em>、<em>[协查人办公电话]</em>
 * 等三个字段，其它来源不包含。
 * <li>“四川省公安厅”-“法律文书”报文中包含<em>[请求人姓名]</em>字段，其它来源的“法律文书”报文不包含。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午2:43:01
 */
@Data
public class JSGA_QueryPerson implements Serializable {
	private static final long serialVersionUID = 2271803943886484L;
	
	/** 请求人姓名 */
	private String qqrxm;
	
	/** 请求人证件类型 */
	private String qqrzjlx;
	
	/** 请求人证件号码 */
	private String qqrzjhm;
	
	/** 请求人单位名称 */
	private String qqrdwmc;
	
	/** 请求人手机号 */
	private String qqrsjh;
	
	/** 协查人姓名 */
	private String xcrxm;
	
	/** 协查人证件类型 */
	private String xcrzjlx;
	
	/** 协查人证件号码 */
	private String xcrzjhm;
	
	/** 请求人办公电话 */
	private String qqrbgdh;
	
	/** 协查人手机号 */
	private String xcrsjh;
	
	/** 协查人办公电话 */
	private String xcrbgdh;
}
