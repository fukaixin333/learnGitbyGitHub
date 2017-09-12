package com.citic.server.gdjg.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuxuanfei
 * @date 2017年6月15日 下午9:32:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br57_kzqq_mx implements Serializable{
	private static final long serialVersionUID = 7923141445740490253L;
	/** 协作编号 */
	private String docno = "";
	/** 案件编号 */
	private String caseno = "";
	/** 唯一标识 */
	private String uniqueid = "";
	/** 类型 UNIT表示单位 PERSON表示自然人 ACCOUNT表示账号（当查询方式为2时） */
	private String type = "";
	/** 公司名称/姓名 TYPE="UNIT"时，表示"公司名称"；TYPE="PERSON"时，表示"姓名" */
	private String name = "";
	/** 证件类型 TYPE="UNIT"时，值为空；TYPE="PERSON"时，表示"证件类型" */
	private String idtype = "";
	/** 证件号码 TYPE="UNIT"时，值为空；TYPE="PERSON"时，表示"证件号码" */
	private String id = "";
	
	/** 查询日期（分区） */
	private String qrydt = "";
	
	/** 归属机构 */
	private String orgkey = "";
	
	
	
}
