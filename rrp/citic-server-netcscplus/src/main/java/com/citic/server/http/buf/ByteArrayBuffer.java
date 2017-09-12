package com.citic.server.http.buf;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Liu Xuanfei
 * @date 2016年8月25日 下午3:30:02
 */
public class ByteArrayBuffer implements Serializable {
	private static final long serialVersionUID = -7037703069657973276L;
	
	private byte[] buf;
	private int len;
	
	public ByteArrayBuffer(final int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Negative initial capacity: " + capacity);
		}
		buf = new byte[capacity];
	}
	
	private void expand(final int newLen) {
		buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newLen));
	}
	
	public void put(final int b) {
		int newLen = this.len + 1;
		if (newLen > this.buf.length) {
			expand(newLen);
		}
		this.buf[len] = (byte) b;
		this.len = newLen;
	}
	
	public void put(final byte[] b, final int off, final int len) {
		if (b == null) {
			return;
		}
		if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) < 0) || ((off + len) > b.length)) {
			throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.length);
		}
		if (len == 0) {
			return;
		}
		final int newlen = this.len + len;
		if (newlen > this.buf.length) {
			expand(newlen);
		}
		System.arraycopy(b, off, this.buf, this.len, len);
		this.len = newlen;
	}
	
	public void put(final char[] c, final int off, final int len) {
		if (c == null) {
			return;
		}
		if ((off < 0) || (off > c.length) || (len < 0) || ((off + len) < 0) || ((off + len) > c.length)) {
			throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + c.length);
		}
		if (len == 0) {
			return;
		}
		final int oldlen = this.len;
		final int newlen = oldlen + len;
		if (newlen > this.buf.length) {
			expand(newlen);
		}
		for (int i1 = off, i2 = oldlen; i2 < newlen; i1++, i2++) {
			this.buf[i2] = (byte) c[i1];
		}
		this.len = newlen;
	}
	
	public void clear() {
		this.len = 0;
	}
	
	public byte[] toByteArray() {
		final byte[] copy = new byte[this.len];
		if (this.len > 0) {
			System.arraycopy(buf, 0, copy, 0, this.len);
		}
		return copy;
	}
	
	public int byteAt(int i) {
		return this.buf[i];
	}
	
	public int capacity() {
		return this.buf.length;
	}
	
	public int length() {
		return this.len;
	}
	
	public byte[] buffer() {
		return this.buf;
	}
	
	public void setLength(final int len) {
		if (len < 0 || len > this.buf.length) {
			throw new IndexOutOfBoundsException("len: " + len + " < 0 or > buffer len: " + this.buf.length);
		}
		this.len = len;
	}
	
	public boolean isEmpty() {
		return this.len == 0;
	}
	
	public boolean isFull() {
		return this.len == this.buf.length;
	}
	
	public int indexOf(final byte b, final int from, final int to) {
		int beginIndex = from;
		if (beginIndex < 0) {
			beginIndex = 0;
		}
		int endIndex = to;
		if (endIndex > this.len) {
			endIndex = this.len;
		}
		if (beginIndex > endIndex) {
			return -1;
		}
		for (int i = beginIndex; i < endIndex; i++) {
			if (this.buf[i] == b) {
				return i;
			}
		}
		return -1;
	}
	
	public int indexOf(final byte b) {
		return indexOf(b, 0, this.len);
	}
}
