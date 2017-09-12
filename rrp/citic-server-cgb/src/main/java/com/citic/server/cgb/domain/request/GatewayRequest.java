package com.citic.server.cgb.domain.request;

import java.util.LinkedHashMap;
import java.util.Map;

import com.citic.server.cgb.domain.GatewayHeader;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 11:39:25$
 */
public class GatewayRequest {
	
	private GatewayHeader gatewayHeader;
	
	private final Map<String, String> fieldMap = new LinkedHashMap<String, String>();
	
	private GatewayRequest() {
		// Do nothing
	}
	
	//	public void setTransSerialNumber(String sn) {
	//		gatewayHeader.setSenderSN(sn);
	//	}
	//	
	//	public void setRequestTime(String time) {
	//		gatewayHeader.setSenderTime(time);
	//	}
	
	public final static GatewayRequest createGatewayRequest(GatewayHeader gatewayHeader, GatewayMessageInput in) {
		GatewayRequest instance = new GatewayRequest();
		instance.setGatewayHeader(in.beforeCreateGatewayRequest(gatewayHeader));
		instance.putFieldAll(in.getHeaderMap());
		instance.putFieldAll(in.getFieldMap());
		return instance;
	}
	
	public void putFieldAll(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return;
		}
		fieldMap.putAll(map);
	}
	
	public void putField(String key, String value) {
		fieldMap.put(key, value);
	}
	
	public String getFieldValue(String key) {
		return fieldMap.get(key);
	}
	
	public GatewayHeader getGatewayHeader() {
		return gatewayHeader;
	}
	
	public void setGatewayHeader(GatewayHeader gatewayHeader) {
		this.gatewayHeader = gatewayHeader;
	}
	
	public Map<String, String> getFieldMap() {
		return fieldMap;
	}
	
	public void setFieldMap(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return;
		}
		fieldMap.putAll(map);
	}
}
