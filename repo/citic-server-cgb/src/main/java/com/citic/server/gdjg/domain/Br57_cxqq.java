package com.citic.server.gdjg.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuxuanfei
 * @date 2017年5月25日 下午2:29:37
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Br57_cxqq implements Serializable {
	private static final long serialVersionUID = -564723911489004725L;
	
	/** 协作编号 */
	private String docno = "";
	/** 案件编号 */
	private String caseno = "";
	/** 案件类型 */
	private String casetype = "";
	/** 案件名称 */
	private String casename = "";
	/** 侦办单位名称 */
	private String exeunit = "";
	/** 申请机构代码 */
	private String applyorg = "";
	/** 目标机构代码 */
	private String targetorg = "";
	/** 备注 */
	private String remark = "";
	/** 发送时间   采用格式YYYYMMDDHHMMSS，24小时制格式，例如：20150410093230；*/
	private String sendtime = "";
	
	
	/** 1：账户 2：交易  3:金融产品  4：保险箱 */
	private String datasource = "";
	/**
	 * 此处码值修改了，请以次出为准
	 * 0：待处理 1：待生成报文 3：反馈成功 4：反馈失败
	 */
	private String status = "";
	/** 归属机构 */
	private String orgkey = "";
	/** 查询日期（分区） */
	private String qrydt = "";
	/** 接收人 */
	private String recipient_p = "";
	/** 接收时间 */
	private String recipient_time = "";
	/** 最后修改时间 */
	private String last_up_dt = "";
	/** 唯一标识 */
	private String uniqueid ="";
	
	
	
	/**动态查询*/    //应转移到mx
//	private String tasktype = "";
//	private String startdate = "";
//	private String enddate = "";
//	private String lastpollingtime = "";
//	private String status_cd = "";
//	private String pollinglock = "";
//	private String seq = "";
//	private String interval ="";
//	private String oldseq = "";
//	private String account = "";
	
		
}
