package com.citic.server.http.util;

/**
 * @author Liu Xuanfei
 * @date 2016年9月12日 下午3:48:11
 */
public class ContentType {
	
	public static String getCharsetFromContentType(String contentType) {
		if (contentType == null) {
			return (null);
		}
		int start = contentType.indexOf("charset=");
		if (start < 0) {
			return (null);
		}
		String encoding = contentType.substring(start + 8);
		int end = encoding.indexOf(';');
		if (end >= 0) {
			encoding = encoding.substring(0, end);
		}
		encoding = encoding.trim();
		if ((encoding.length() > 2) && (encoding.startsWith("\"")) && (encoding.endsWith("\""))) {
			encoding = encoding.substring(1, encoding.length() - 1);
		}
		return (encoding.trim());
	}
	
	public static String getBoundaryFromContentType(String contentType) {
		if (contentType == null) {
			return null;
		}
		int start = contentType.indexOf("boundary=");
		if (start < 0) {
			return null;
		}
		String boundary = contentType.substring(start + 9);
		int end = boundary.indexOf(';');
		if (end >= 0) {
			boundary = boundary.substring(0, end);
		}
		boundary = boundary.trim();
		if ((boundary.length() > 2) && (boundary.startsWith("\"")) && (boundary.endsWith("\""))) {
			boundary = boundary.substring(1, boundary.length() - 1);
		}
		return boundary.trim();
	}
}
