package com.citic.server.http.api;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * @author Liu Xuanfei
 * @date 2016年7月19日 上午9:28:57
 */
public class HttpMessageContext {
	
	private final IoBuffer buf;
	private boolean capacity = false;
	private long limit = 0;
	private long position = 0;
	
	private final HttpRequestMessage request = new HttpRequestMessage();
	
	private int markValue; // 标记某个特殊值
	
	public HttpMessageContext() {
		buf = IoBuffer.allocate(1024).setAutoExpand(true);
	}
	
	public boolean capacity() {
		return capacity;
	}
	
	public IoBuffer put(IoBuffer _buf) {
		position += _buf.remaining(); //
		return buf.put(_buf);
	}
	
	public void mark(int pos) {
		this.markValue = pos;
	}
	
	public void setLimit(long limit) {
		this.limit = limit;
		this.capacity = true; // 
	}
	
	public HttpRequestMessage getRequestMessage() {
		return this.request;
	}
	
	public int getMarkValue() {
		return this.markValue;
	}
	
	public long limit() {
		return limit;
	}
	
	public long position() {
		return position;
	}
	
	public void recycle() {
		this.buf.clear();
		this.capacity = false;
		this.limit = 0;
		this.position = 0;
		this.request.recycle();
	}
}
