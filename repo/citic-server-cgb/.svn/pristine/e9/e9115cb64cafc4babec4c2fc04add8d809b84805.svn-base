package com.citic.server.cgb.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 广发银行网关通用报文头
 * 
 * @author Liu Xuanfei
 * @date 2016年11月24日 上午10:16:22
 */
@Data
public class GatewayHeader implements Serializable {
	private static final long serialVersionUID = 5205618416750939297L;
	
	/** 通讯代码 - 请求报文 */
	public static final String COMMCODE_REQUEST = "500001";
	/** 通讯代码 - 响应报文 */
	public static final String COMMCODE_RESPONSE = "510001";
	
	/** 版本号（标识当前数据包的格式版本） */
	private String versionNo = "1";
	
	/** 密押标识（表示数据报文是否压缩格式） */
	private String toEncrypt = "0";
	
	/** 通讯代码 */
	private String commCode = "";
	
	/**
	 * 通讯类型
	 * <p>
	 * 0 - 同步请求，等待接收方返回业务处理结果；<br>
	 * 1 - 异步请求，接收方不反悔结果，由网关返回给请求方通讯回执。
	 */
	private String commType = "0";
	
	/** 接收方标识 */
	private String receiverId = "";
	
	/** 发起方标识 */
	private String senderId = "";
	
	/** 发送方流水号（22位） */
	private String senderSN = "";
	
	/** 发送方日期（yyyyMMdd） */
	private String senderDate = "";
	
	/** 发送方时间（HHmmss） */
	private String senderTime = "";
	
	/** 交易代码 */
	private String tradeCode = "";
	
	/**
	 * 网关错误标识（01-成功；00-错误）
	 * <p>
	 * 用于表示是否处理过程发生错误，发起方填空。
	 */
	private String gwErrorCode = "";
	
	/**
	 * 网关错误代码
	 * <p>
	 * 用于表示具体错误内容的七位的半角字符串，发起方填空。
	 */
	private String gwErrorMessage = "";
	
	/**
	 * 重发标志
	 */
	private String resendFlag = "";
	
	/**
	 * 保留位1
	 */
	private String reserved1 = "";
}
