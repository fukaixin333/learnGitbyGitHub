package com.citic.server.inner.domain.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.inner.domain.ResponseMessage;

@Data
@EqualsAndHashCode(callSuper = false)
public class FinanceResponse extends ResponseMessage {

	private static final long serialVersionUID = -7003616784668327854L;
	
	private String functionId; // 交易代码
	private String exSerial; // 发起方流水号
	private String errorNo; // 错误代码
	private String errorInfo; // 错误信息
	private String sysDate; // 交易发生时的系统日期
	private String sysTime; // 交易发生时的系统时间
	
}
