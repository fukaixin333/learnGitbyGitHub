/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/24 16:59:43$
 */
package com.citic.server.cgb.domain.request;

import java.util.LinkedHashMap;
import java.util.Map;

import com.citic.server.cgb.domain.GatewayHeader;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/24 16:59:43$
 */
public abstract class GatewayMessageInput {
	
	private final Map<String, String> headerMap = new LinkedHashMap<String, String>();
	private final Map<String, String> fieldMap = new LinkedHashMap<String, String>();
	
	public GatewayMessageInput() {
		initDefaultHeader();
		initDefaultField();
	}
	
	public abstract void initDefaultHeader();
	
	public abstract void initDefaultField();
	
	public GatewayHeader beforeCreateGatewayRequest(GatewayHeader gatewayHeader) {
		return gatewayHeader;
	}
	
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}
	
	public Map<String, String> getFieldMap() {
		return fieldMap;
	}
	
	public GatewayMessageInput putField(String key, String value) {
		fieldMap.put(key, value);
		return this;
	}
	
	public String getFieldValue(String key) {
		return fieldMap.get(key);
	}
	
	public GatewayMessageInput putHeader(String key, String value) {
		headerMap.put(key, value);
		return this;
	}
	
	public String getHeaderValue(String key) {
		return headerMap.get(key);
	}
}
