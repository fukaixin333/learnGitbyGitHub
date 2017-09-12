package com.citic.server.inner.domain.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.inner.domain.ResponseMessage;

@Data
@EqualsAndHashCode(callSuper = false)
public class CargoRecord extends ResponseMessage {
	private static final long serialVersionUID = -8778405588104165640L;
	
	private String systemId; // 来源系统
	private String queryDate; // 查询日期
	private String querySerialNum; // 查询流水号
	private String queryType; // 查询方式
	private String oriEntrustDate; // 原系统委托日期
	private String oriSerialNum; // 原交易流水号
	private String oriBankCode; // 原发起行号
	private String overType; // 完成标志
	private String transType; // 交易状态
	private String sendType; // 发送状态
	private String dealResult; // 处理结果
	private String feeAmount; // 手续费
	private String remark; // 备注
	private String remark2; // 备用2
}
