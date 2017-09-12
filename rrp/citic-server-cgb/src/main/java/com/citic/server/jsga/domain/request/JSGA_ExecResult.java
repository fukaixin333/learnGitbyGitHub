package com.citic.server.jsga.domain.request;

import lombok.Data;

/**
 * 请求执行结束回执
 * 
 * @author Liu Xuanfei
 * @date 2016年10月21日 下午3:08:05
 */
@Data
public class JSGA_ExecResult {
	/** 请求单标识 */
	private String qqdbs;
	
	/** 请求措施类型 */
	private String qqcslx;
	
	/** 申请机构代码 */
	private String sqjgdm;
	
	/** 目标机构代码 */
	private String mbjgdm;
	
	/** 回执时间 */
	private String hzsj;
	
	/** 接收任务数 */
	private String jsrws;
	
	/** 反馈任务数 */
	private String fkrws;
	
	/** 反馈客户数 */
	private String fkkhs;
	
	/** 反馈账户数 */
	private String fkzhs;
	
	/** 反馈交易明细数 */
	private String fkjymxs;
}
