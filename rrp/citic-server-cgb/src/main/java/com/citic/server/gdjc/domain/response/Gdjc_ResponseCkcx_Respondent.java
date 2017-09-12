package com.citic.server.gdjc.domain.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 商业银行存款查询-被调查人
 * 
 * @author Liu Xuanfei
 * @date 2016年8月17日 上午11:31:44
 */
@Data
public class Gdjc_ResponseCkcx_Respondent implements Serializable {
	private static final long serialVersionUID = -7232784931378951701L;
	
	/** 唯一标识 */
	private String uniqueid;
	
	/**
	 * 类型
	 * <ul>
	 * <li>UNIT: 表示单位
	 * <li>PERSON: 表示自然人
	 * <li>ACCOUNT: 表示账号（当查询方式为 2 时）
	 * </ul>
	 */
	private String type;
	
	/**
	 * 查询方式
	 * <ul>
	 * <li>1：按名称、证件号查询
	 * <li>2：按账号或卡号查询
	 * <li>3：按单位名称查询
	 * <li>4：按组织机构代码查询
	 * <li>5：按工商营业执照编 码查询
	 * <li>6：按三证合一编号查询
	 * </ul>
	 * <strong>注：查询自然人时，只能按第 1、2 种方式查询；查询法人时，只能按第 2、3、4、5 、6种方式查询</strong>
	 */
	private String querymode;
	
	/** 公司名称/姓名 */
	private String name;
	
	/** 证件类型 */
	private String idtype;
	
	/** 证件号码 */
	private String id;
	
	/** 账号 */
	private String account;
	
	/** 组织机构代码 */
	private String orgcode;
	
	/** 工商营业执照 */
	private String buslicense;
	
	/** 三证合一号码 */
	private String threeinone;
	
	/** 注册地名称 */
	private String location;
}
