package com.citic.server.cgb.domain.response;

public class UnifiedPayMentMessageResult extends GatewayMessageResult {
	/** 请求系统流水号 */
	public static final String key_BusiSysSerno = "busiSysSerno";
	/** 交易状态 */
	public static final String key_TrxStatus = "trxStatus";
	/** 出错源系统 */
	public static final String key_ErrBusiSys = "errBusiSys";
	/** 错误代码 */
	public static final String key_ErrorCode = "errorCode";
	/** 错误信息 */
	public static final String key_ErrorMsg = "errorMsg";
	/** 支付平台处理日期 */
	public static final String key_PayDate = "payDate";
	/** 支付平台处理时间 */
	public static final String key_PayTime = "payTime";
	/** 支付平台流水号 */
	public static final String key_PaySerno = "paySerno";
	/** 保留 */
	public static final String key_Reserved = "reserved";
	
	@Override
	public void putEntry(String key, String value) {
		putField(key, value);
	}
	
	public boolean putHeaderEntry(String key, String value) {
		if (key_BusiSysSerno.equals(key)
			|| key_TrxStatus.equals(key)
			|| key_ErrBusiSys.equals(key)
			|| key_ErrorCode.equals(key)
			|| key_ErrorMsg.equals(key)
			|| key_PayDate.equals(key)
			|| key_PayTime.equals(key)
			|| key_PaySerno.equals(key)
			|| key_Reserved.equals(key)) {
			putHeader(key, value);
			
		} else {
			return false;
		}
		return true;
		
	}
	
	public String getBusiSysSerno() {
		
		return getFieldValue(key_BusiSysSerno);
	}
	
	public String getTrxStatus() {
		return getFieldValue(key_TrxStatus);
	}
	
	public String getErrBusiSys() {
		return getFieldValue(key_ErrBusiSys);
	}
	
	public String getErrorCode() {
		return getFieldValue(key_ErrorCode);
	}
	
	public String getErrorMsg() {
		return getFieldValue(key_ErrorMsg);
	}
	
	public String getPayDate() {
		return getFieldValue(key_PayDate);
	}
	
	public String getPayTime() {
		return getFieldValue(key_PayTime);
	}
	
	public String getPaySerno() {
		return getFieldValue(key_PaySerno);
	}
	
	public String getReServed() {
		return getFieldValue(key_Reserved);
	}
	
}
