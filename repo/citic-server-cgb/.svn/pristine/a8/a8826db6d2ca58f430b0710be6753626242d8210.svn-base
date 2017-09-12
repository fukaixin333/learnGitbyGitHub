package com.citic.server.inner.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.inner.domain.RequestMessage;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/09 21:02:18$
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InputPSB411 extends RequestMessage {
	private static final long serialVersionUID = -5716782185445289848L;
	
	private String systemId = "ZGFY"; // 来源系统
	private String queryDate; // 查询日期
	private String querySerialNumber; // 查询流水号
	private String queryMode; // 查询方式
	
	private String oriEntrustDate; // 原系统委托日期
	private String oriSerialNum; // 原交易流水号
	private String oriBankCode; // 原发起行号
	private String remark1; // 备注1
	private String remark2; // 备注2
}
