package com.citic.server.http.codec;

import java.util.HashMap;
import java.util.Map;

import com.citic.server.http.api.HttpEntity;
import com.citic.server.http.api.HttpStatus;
import com.citic.server.http.api.HttpVersion;

public class HttpResponseMessage {
	
	private final HttpVersion version;
	private final HttpStatus status;
	private Map<String, String> headers = new HashMap<String, String>();
	
	private HttpEntity entity = null;
	
	public HttpResponseMessage(HttpVersion version, HttpStatus status) {
		this.version = version;
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public HttpVersion getProtocolVersion() {
		return version;
	}
	
	public String getContentType() {
		return headers.get("content-type");
	}
	
	public boolean isKeepAlive() {
		return false;
	}
	
	public String getHeader(String name) {
		return headers.get(name);
	}
	
	public Map<String, String> getHeaders() {
		return headers;
	}
	
	public void setEntity(HttpEntity entity) {
		this.entity = entity;
	}
	
	public HttpEntity getEnity() {
		return this.entity;
	}
}
