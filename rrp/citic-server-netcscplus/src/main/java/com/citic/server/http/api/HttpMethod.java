package com.citic.server.http.api;

import com.citic.server.http.buf.MessageBytes;

/**
 * @author Liu Xuanfei
 * @date 2016年7月18日 上午11:08:09
 */
public enum HttpMethod {
	
	GET, HEAD, POST, PUT, DELETE, OPTIONS, TRACE, CONNECT;
	
	public static boolean valueOf(MessageBytes mb) {
		return mb.equalsVal(GET.toString())
				|| mb.equalsVal(HEAD.toString())
				|| mb.equalsVal(POST.toString())
				|| mb.equalsVal(PUT.toString())
				|| mb.equalsVal(DELETE.toString())
				|| mb.equalsVal(OPTIONS.toString())
				|| mb.equalsVal(TRACE.toString());
	}
}
