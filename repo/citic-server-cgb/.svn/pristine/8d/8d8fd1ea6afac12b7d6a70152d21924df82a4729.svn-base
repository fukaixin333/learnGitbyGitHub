/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/25 11:15:13$
 */
package com.citic.server.cgb.domain.response;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 11:15:13$
 */
public class FinancialMessageResult extends GatewayMessageResult {
	/** key_FunctionId: 交易代码 */
	public static final String key_FunctionId = "FunctionId";
	/** key_ExSerial: 发起方流水号 */
	public static final String key_ExSerial = "ExSerial";
	/** key_ErrorNo: 错误代码 */
	public static final String key_ErrorNo = "ErrorNo";
	/** key_ErrorInfo: 错误信息 */
	public static final String key_ErrorInfo = "ErrorInfo";
	/** key_SysDate: 交易发生时的系统日期 */
	public static final String key_SysDate = "SysDate";
	/** key_SysTime: 交易发生时的系统时间 */
	public static final String key_SysTime = "SysTime";
	
	@Override
	public void putEntry(String key, String value) {
		if (putHeaderEntry(key, value)) {
			return;
		}
		putField(key, value);
	}
	
	public boolean hasException() {
		return !"0000".equals(getErrorNo());
	}
	
	public boolean putHeaderEntry(String key, String value) {
		if (key_FunctionId.equals(key) // Wrap
			|| key_ExSerial.equals(key)
			|| key_ErrorNo.equals(key)
			|| key_ErrorInfo.equals(key)
			|| key_SysDate.equals(key)
			|| key_SysTime.equals(key)) {
			putHeader(key, value);
		} else {
			return false;
		}
		return true;
	}
	
	public String getFunctionId() {
		return getHeaderValue(key_FunctionId);
	}
	
	public String getExSerial() {
		return getHeaderValue(key_ExSerial);
	}
	
	public String getErrorNo() {
		return getHeaderValue(key_ErrorNo);
	}
	
	public String getErrorInfo() {
		return getHeaderValue(key_ErrorInfo);
	}
	
	public String getSysDate() {
		return getHeaderValue(key_SysDate);
	}
	
	public String getSysTime() {
		return getHeaderValue(key_SysTime);
	}
}
