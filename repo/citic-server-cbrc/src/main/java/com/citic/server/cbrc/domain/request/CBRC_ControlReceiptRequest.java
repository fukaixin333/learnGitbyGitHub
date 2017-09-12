package com.citic.server.cbrc.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 动态查询请求结果回执报文
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 上午9:40:56
 */
@Data
public class CBRC_ControlReceiptRequest implements Serializable {
	private static final long serialVersionUID = -5786824452123813982L;
	
	/** 请求单标识 */
	private String qqdbs;
	
	/** 查控主体类别 */
	private String ztlb;
	
	/** 申请机构代码 */
	private String sqjgdm;
	
	/** 目标机构代码 */
	private String mbjgdm;
	
	/** 回执时间 */
	private String hzsj;
	
	/**操作失败原因 */
	private String czsbyy;
	
	/** 动态查询请求结果回执信息 */
	private List<CBRC_ControlRequest_Receipt> controlReceiptList;
}
