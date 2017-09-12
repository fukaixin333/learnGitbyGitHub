package com.citic.server.gdjg.domain;

import java.io.Serializable;

import com.citic.server.dict.DictBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuxuanfei
 * @date 2017年5月25日 下午3:42:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Br57_cxqq_mx implements Serializable, DictBean {
	private static final long serialVersionUID = 2107325153943134210L;
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
	
	/** 原流水号 */
	private String oldseq = "";
	/** 紧急程度 */
	private String urgency = "";
	/** 账号 涉案账号或卡号 */
	private String account = "";
	/** 账户类型 */
	private String acctype = "";
	/** 户名 */
	private String accname = "";
	/** 时间区间 */
	private String intervals = "";   //interval为关键字
	/** 开始日期 YYYY-MM-DD */
	private String startdate = "";
	/**
	 * 结束日期 YYYY-MM-DD
	 * 注：开始日期和结束日期都为空时，表示查询本账户的全部交易流水
	 */
	private String enddate = "";
	/** 文件名称 */
	private String notifydoc = "";
	/** 文书文号 */
	private String docnum = "";
	/** 任务类别 */
	private String tasktype = "";
	/** 最近轮询时间 */
	private String lastpollingtime = "";
	/** 任务状态 */
	private String status_cd = "";
	/** 轮询锁 */
	private String pollinglock = "";
	/** 任务流水号 */
	private String rwlsh = "";
	
	
	/**
	 * 查询方式
	 * 1：按名称、证件号查询
	 * 2：按账号或卡号查询
	 * 3：按单位名称查询
	 * 4：按组织机构代码查询
	 * 5：按工商营业执照编码查询
	 * 6：按证件号查询（如有些新疆的姓名存在录入困难或包含特殊符号，导致真实姓名与银行账户的户名匹配不上）
	 * 注：查询自然人时，只能按第1、2、6种方式查询；查询法人时，只能按第2、3、4、5种方式查询
	 */
	private String querymode = "";
	/** 组织机构代码 */
	private String orgcode = "";
	/** 工商营业执照 */
	private String buslicense = "";
	/** 注册地名称 TYPE="UNIT"时，表示"注册地名称（具体到区县级）"；TYPE="PERSON"时，表示"发证机关所在地名称（具体到区县级） */
	private String location = "";
	/**
	 * 查询内容
	 * 01 账户信息；
	 * 03 账户和账户的交易明细信息；
	 * 此处值默认为01
	 */
	private String querycontent = "";
	
	/** 查询日期（分区） */
	private String qrydt = "";
	
	/** 归属机构 */
	private String orgkey = "";
	
	/**
	 * 转码使用
	 */
	@Override
	public String getGroupId() {
		return "Gdjg_Br57_cxqq_mx";
	}
	
}
