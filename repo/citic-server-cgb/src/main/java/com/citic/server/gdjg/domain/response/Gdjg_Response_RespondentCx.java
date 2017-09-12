package com.citic.server.gdjg.domain.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 查询类--被调查人信息
 * 
 * @author wangbo
 * @date 2017年5月23日 下午9:08:53
 */
@Data
public class Gdjg_Response_RespondentCx implements Serializable {
	private static final long serialVersionUID = -1464413597969561935L;
	/** 唯一标识 */
	private String uniqueid;
	
	/** 类型 */
	private String type;
	
	/** 查询方式 */
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
	
	/** 注册地名称 */
	private String location;
	
	/** 查询内容 */
	private String querycontent;
	
	/** 明细开始日期 */
	private String startdate;
	
	/** 明细结束日期 */
	private String enddate;
	
	/** 文件名称 */
	private String notifydoc;
	
	/** 文件文号 */
	private String docnum;
	
	/** 开户行 */
	private List<Gdjg_Response_BankCx> banks;
	
	/** 账户 */
	private List<Gdjg_Response_AccCx> accs;
	
}
