package com.citic.server.cgb.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 广发银行新核心主机专用请求报文头
 * 
 * @author Liu Xuanfei
 * @date 2016年11月25日 下午8:58:13
 */
@Data
public class CoreHeader implements Serializable {
	private static final long serialVersionUID = 5508779499639225681L;
	
	/** 请求系统标识 */
	private String reqSysId = "AFP";
	
	/**
	 * 请求系统流水号（32位）：用于唯一标识一笔交易
	 * <p>
	 * 组成规则：请求系统标识 + 请求系统日期 + 序号
	 */
	private String reqSeqNo;
	
	/**
	 * 请求类型
	 * <p>
	 * T-普通交易，包括多页查询的首页请求；<br>
	 * P-多页输出交易，后续翻页交易。
	 */
	private String reqType;
	
	/** 渠道交易码 */
	private String trId;
	
	/** 渠道系统标识 */
	private String reqChnl;
	
	/** 渠道系统流水号 */
	private String reqChnlJrn;
	
	/** 渠道系统交易日期（yyyyMMdd） */
	private String reqChnlDate;
	
	/** 渠道系统交易时间（HHmmss） */
	private String reqChnlTime;
	
	/** 终端号（如果使用IP地址，需去掉小数点） */
	private String termId;
	
	/**
	 * 会话编号（默认值：00）
	 * <p>
	 * 柜员同时登陆多次的序号，用于多页输出交易。<br>
	 * 由终端号+会话编号+子会话号组成一个多页输出临时文件的键值。
	 */
	private String sessNo;
	
	/**
	 * 子会话号（默认值：000）
	 * <p>
	 * 柜员在同一个登陆会话中打开多个交易界面的序号，用于多页输出交易。<br>
	 * 由终端号+会话编号+子会话号组成一个多页输出临时文件的键值。
	 */
	private String subSess;
	
	/**
	 * 交易银行号：发起交易的所在银行，如国内行、澳门分行等。
	 */
	private String trBank;
	
	/**
	 * 交易机构/交易行所号
	 * <p>
	 * 如果外围没有上送交易机构，则根据柜员所属机构作为交易结构。<br>
	 * 核心通过交易机构获取交易分行号。
	 */
	private String trBranch;
	
	/** 请求入账日期（暂时不用） */
	private String postDate;
	
	/**
	 * 柜员号
	 * <p>
	 * 柜面使用实体柜员；电子渠道使用电子柜员，由核心系统预先分配。<br>
	 * 实体柜员或电子柜员，以实体柜员上送的交易有可能会产生动态授权，电子柜员则不会。
	 */
	private String tlId;
	
	/**
	 * 商户号：用于对账，支持外围系统根据商户号取对账流水。
	 */
	private String mercId;
	
	/**
	 * 清算日期：用于对账，如外围通过清算日期取对账流水时必须上送，如不使用则无须上送。
	 */
	private String clearDate;
	
	/** 渠道小类 */
	private String reqChnl2;
	
	/** 渠道细分 */
	private String chnlDtl;
	
	/**
	 * 同步标识：用于外围在处理异步应答时匹配原请求交易。
	 */
	private String syncId;
	
	/** MAC校验值（可见字符，如0xFF转换为"FF"） */
	private String mac;
}
