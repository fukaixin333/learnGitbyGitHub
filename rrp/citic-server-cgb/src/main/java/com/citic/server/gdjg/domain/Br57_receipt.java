package com.citic.server.gdjg.domain;

import java.io.Serializable;

import lombok.Data;


/**
 * 回执文件表
 * 
 * @author liuxuanfei
 * @date 2017年6月1日 下午5:22:30
 */

@Data
public class Br57_receipt implements Serializable{
	private static final long serialVersionUID = 8360913063190533786L;
	/** 回执标识 */
	private String receiptkey = "";
	/** 协作编号 */
	private String docno = "";
	/** 回执包编号 */
	private String packetkey = "";
	/** 回执状态 */
	private String receipt_status_cd = "";
	/** 结果信息 */
	private String resultinfo = "";
	/** 查询日期（分区）**/
	private String qrydt="";
}
