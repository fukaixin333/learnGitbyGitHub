package com.citic.server.cgb.domain.response;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 23:54:43$
 */
public class OnlineTPMessageResult extends GatewayMessageResult {
	
	public static final String key_resCode = "resCode";
	public static final String key_resMessage = "resMsg";
	
	@Override
	public void putEntry(String key, String value) {
		if (putHeaderEntry(key, value)) {
			return;
		}
		putField(key, value);
	}
	
	public boolean hasException() {
		return !"000000".equals(getResCode());
	}
	
	public boolean putHeaderEntry(String key, String value) {
		if (key_resCode.equals(key) || key_resMessage.equals(key)) {
			putHeader(key, value);
		} else {
			return false;
		}
		return true;
	}
	
	public String getResCode() {
		return getHeaderValue(key_resCode);
	}
	
	public String getResMessage() {
		return getHeaderValue(key_resMessage);
	}
}
