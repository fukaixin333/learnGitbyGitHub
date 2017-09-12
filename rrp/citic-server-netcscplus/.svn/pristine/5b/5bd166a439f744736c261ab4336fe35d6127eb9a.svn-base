package com.citic.server.http.buf;

/**
 * @author Liu Xuanfei
 * @date 2016年8月29日 下午2:03:39
 */
public class MimeHeaders {
	public static final int DEFAULT_HEADER_SIZE = 8;
	
	private MimeHeader[] headers = new MimeHeader[DEFAULT_HEADER_SIZE];
	
	private int count = 0;
	
	public MessageBytes addHeader(String name) {
		MimeHeader header = createHeader();
		header.getName().setString(name);
		return header.getValue();
	}
	
	public MessageBytes addHeader(final byte[] b) {
		return addHeader(b, 0, b.length);
	}
	
	public MessageBytes addHeader(byte[] b, int off, int len) {
		MimeHeader header = createHeader();
		header.getName().setBytes(b, off, len);
		return header.getValue();
	}
	
	public MessageBytes addHeader(final char[] c) {
		return addHeader(c, 0, c.length);
	}
	
	public MessageBytes addHeader(char[] c, int off, int len) {
		MimeHeader header = createHeader();
		header.getName().setChars(c, off, len);
		return header.getValue();
	}
	
	public MessageBytes getHeader(String name) {
		for (int i = 0; i < count; i++) {
			if (headers[i].getName().equalsIgnoreCaseVal(name)) {
				return headers[i].getValue();
			}
		}
		return null;
	}
	
	public String getHeaderValue(String name) {
		MessageBytes mb = getHeader(name);
		return mb == null ? null : mb.toString();
	}
	
	private MimeHeader createHeader() {
		MimeHeader header = null;
		if (count >= headers.length) {
			// expand header list array
			int newLength = count * 2;
			MimeHeader[] tmp = new MimeHeader[newLength];
			System.arraycopy(headers, 0, tmp, 0, headers.length);
			headers = tmp;
		}
		if ((header = headers[count]) == null) {
			headers[count] = header = new MimeHeader();
		}
		count++;
		return header;
	}
	
	public void recycle() {
		for (int i = 0; i < count; i++) {
			headers[i].recycle();
		}
		count = 0;
	}
}

class MimeHeader {
	protected MessageBytes name = new MessageBytes();
	protected MessageBytes value = new MessageBytes();
	
	public MessageBytes getName() {
		return name;
	}
	
	public void setName(MessageBytes name) {
		this.name = name;
	}
	
	public MessageBytes getValue() {
		return value;
	}
	
	public void setValue(MessageBytes value) {
		this.value = value;
	}
	
	public void recycle() {
		name.recycle();
		value.recycle();
	}
}