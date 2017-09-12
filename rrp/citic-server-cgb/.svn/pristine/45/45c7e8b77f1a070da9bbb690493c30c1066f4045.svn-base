package com.citic.server.cgb.domain.response;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 16:06:57$
 */
public abstract class GatewayMessageResult {
	
	private final Map<String, String> headerMap = new LinkedHashMap<String, String>();
	private final Map<String, String> fieldMap = new LinkedHashMap<String, String>();
	
	public abstract void putEntry(String key, String value);
	
	public void putField(String key, String value) {
		fieldMap.put(key, value);
	}
	
	public String getFieldValue(String key) {
		return fieldMap.get(key);
	}
	
	public void putHeader(String key, String value) {
		headerMap.put(key, value);
	}
	
	public String getHeaderValue(String key) {
		return headerMap.get(key);
	}
}
