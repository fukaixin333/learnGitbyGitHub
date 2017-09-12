package com.citic.server.gdjc.domain.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class Gdjc_ResponseLsdj_wjjg_zipfileinfo implements Serializable {
	/**
	 * 商业银行交易流水sftp文件登记结果查询－－ZIP文件信息
	 */
	private static final long serialVersionUID = -3123578591076988331L;

	/** 文件唯一标示 */
	private String zipfileid;
	
	/** 协作编号 业务唯一标识*/
	private String docno;
	
	/** 0 待处理 1 成功 2 错误 */
	private String resultstatus;
	
	/** 描述处理信息 (主要描述错误信息内容) */
	private String resultinfo;
	
}
