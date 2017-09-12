package com.citic.server.http.api;

import com.citic.server.http.buf.MessageBytes;

public enum HttpVersion {
	
	HTTP_1_1("HTTP/1.1"), HTTP_1_0("HTTP/1.0");
	
	private final String value;
	
	private HttpVersion(String value) {
		this.value = value;
	}
	
	public static boolean valueOf(MessageBytes mb) {
		return mb.equalsIgnoreCaseVal(HTTP_1_1.toString()) || mb.equalsIgnoreCaseVal(HTTP_1_0.toString());
	}
	
	public static HttpVersion valueOfIgnoreCase(String str) {
		if (HTTP_1_1.toString().equalsIgnoreCase(str)) {
			return HTTP_1_1;
		}
		
		if (HTTP_1_0.toString().equalsIgnoreCase(str)) {
			return HTTP_1_0;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
