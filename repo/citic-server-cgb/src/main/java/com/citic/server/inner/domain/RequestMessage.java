package com.citic.server.inner.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 平台与Artery前置交互报文对象
 * 
 * @author Liu Xuanfei
 * @date 2016年3月11日 下午2:42:47
 */
@Data
public abstract class RequestMessage implements Serializable {
	private static final long serialVersionUID = -7180321883331973561L;
	
	/** 请求目标简称 */
	private String to;
	
	/** 交易编码（输入） */
	private String code;
	
	/** 错误描述信息 */
	private String description;
	
	/** 请求流水号（22位） */
	private String senderSN;
	
	/** 请求日期（yyyyMMdd） */
	private String senderDate;
	
	/** 请求时间（HHmmss） */
	private String senderTime;
	
	/** 系统流水号（32位） */
	private String transSerialNumber;
	
	// ==========================================================================================
	//                     Paging Information
	// ==========================================================================================
	/** 查询行数（每页） */
	private int pageRow = 10;
	
	/** 查询页码 */
	private int pageNum = 0;
	
	/** 总记录条数 */
	private int totalNum = 0;
	
	/** 当前会计日符合条件记录数 */
	private int todRecNum = 0;
}
