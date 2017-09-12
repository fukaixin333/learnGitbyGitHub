/*
 * =============================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created [2017-05-16]
 * =============================================
 */

package com.citic.server.cgb.domain;

/**
 * <p>
 * Bb11_aml_log.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 该类为Bb11_aml_warn_list的接口版
 * @author $Author: $
 */

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AmlWarnDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3874696364663922829L;

	/** 交易对手证件号码 */
	private String opp_cust_id_no = "";
	
	/** 客户名称 */
	private String party_name = "";
	
	/** 客户号 */
	private String party_id = "";
	
	/** 制裁参考名单 */
	private String san_name = "";
	
	/** 写入时间 */
	private String create_time = "";
	
	/** 交易金额 */
	private String amt = "";
	
	/** 发送时间 */
	private String send_time = "";
	
	/** 交易币种 */
	private String curr_cd = "";
	
	/** 0:待发送 1：已发送 9：中间状态 */
	private String send_flag = "";
	
	/** 账户归属机构 */
	private String acct_organkey = "";
	
	/** 交易对手类型 */
	private String opp_party_class_cd = "";
	
	/** yyyyMMddHHmmss */
	private String tx_time = "";
	
	/** 交易账户 */
	private String tx_acctnum = "";
	
	/** 交易机构 */
	private String tx_organkey = "";
	
	/** 1-个人，2-公司客户，3-同业客户 */
	private String party_class_cd = "";
	
	/**
	 * 阻断类型
	 * 3004-收方阻断
	 * 3005-付方阻断
	 * 3006-换卡阻断
	 * 3007-换折阻断
	 * 3008-强制销户阻断
	 * 3009-证件开户/变更阻断
	 * 3010-票据出售阻断
	 * 3011-开账号阻断
	 */
	private String rt_typ = "";
	
	/** 核心：CBOE，卡核心：CCS，决策：CCD，工单：BPS */
	private String sys_ind = "";
	
	/** 交易种类 */
	private String biz_type = "";
	
	/** 1:交易拒绝 2：开户拒绝 */
	private String alert_type = "";
	
	/** 默认：客户主体触发的涉恐名单 */
	private String alert_reason = "";
	
	/** 交易对手账号 */
	private String opp_tx_acctnum = "";
	
	/** 涉及渠道标识 */
	private String channel = "";
	
	/** 默认：已阻断 */
	private String tx_result = "";
	
	/** 交易对手姓名 */
	private String opp_party_name = "";
	
	/** 客户证件号码 */
	private String cust_id_no = "";
	
	/** 交易对手行所名称 */
	private String opp_acct_organkey = "";
	
	//==================另一张表==========================
	/** 监控名单唯一标识码 */
	private String source_id = "";
	
	/** 证件值 */
	private String idvalue = "";
	
	/** 预留字段 */
	private String remark = "";
	
	/** 名称 */
	private String pname = "";
	
	/** +:增加 -:减少 */
	private String act_status = "";
	
	/** F001国内射孔名单 F002中国公安部涉恐名单 F003中国人名银行涉恐名单 F004联合国涉恐名单 */
	private String san_name_type = "";
	
	/** 名单编码 */
	private String md_code = "";
	
	/** yyyy-MM-dd */
	private String data_date = "";
	
	/** add：新增 chg：变更 */
	private String black_action = "";
	
	/** I：个体 C：实体 */
	private String black_class_cd = "";
	
	/** 涉及国外证件类型 */
	private String idtype = "";
	
	//=================辅助字段======================
	private String startTime = "";//查询开始时间
	private String endTime = "";//查询截止时间
	private String jrnNo = "";//日志号
	private String lstCd = "";//名单大类
	private String lstDt = "";//名单细类
	private String filler1 = "";//预留1
	private String filler2 = "";//预留2
	private String pageSize = "";//每页输出行数
	private String pageNum = "";//页码
	
	//分页时用到
	private List<AmlWarnDto> warnList = null;

}
