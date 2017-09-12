package com.citic.server.http.api;

import com.citic.server.http.buf.ByteChunk;
import com.citic.server.http.buf.MimeHeaders;

/**
 * @author Liu Xuanfei
 * @date 2016年9月12日 下午4:36:43
 */
public class HttpEntity {
	
	public static final String DEFAULT_CHARSET = "ISO-8859-1";
	
	private MimeHeaders headers = new MimeHeaders();
	
	private ByteChunk content = new ByteChunk();
	
	private String filename = null;
	
	public MimeHeaders getHeaders() {
		return headers;
	}
	
	public ByteChunk getContent() {
		return content;
	}
	
	public String getFilename() {
		if (filename == null) {
			String disposition = headers.getHeaderValue("content-disposition");
			if (disposition == null) {
				return null;
			}
			int start = disposition.indexOf("filename=");
			if (start < 0) {
				return null;
			}
			String fn = disposition.substring(start + 9);
			int end = fn.indexOf(';');
			if (end >= 0) {
				fn = fn.substring(0, end);
			}
			fn = fn.trim();
			if ((fn.length() > 2) && (fn.startsWith("\"")) && (fn.endsWith("\""))) {
				fn = fn.substring(1, fn.length() - 1);
			}
			filename = fn.trim();
		}
		return filename;
	}
}
