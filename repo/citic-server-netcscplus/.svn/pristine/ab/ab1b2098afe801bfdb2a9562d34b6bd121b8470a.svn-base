package com.citic.server.http.buf;

import java.io.Serializable;
import java.nio.charset.Charset;

import com.citic.server.http.util.Ascii;

public class ByteChunk implements Cloneable, Serializable {
	private static final long serialVersionUID = 3807341202595923342L;
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
	
	private byte[] buf;
	private int pos;
	private int limit;
	
	private Charset charset;
	
	public void setBytes(byte[] bytes, int off, int len) {
		this.buf = bytes;
		this.pos = off;
		this.limit = off + len;
	}
	
	public void setCharset(Charset charset) {
		this.charset = charset;
	}
	
	public int length() {
		return limit - pos;
	}
	
	public byte[] bytes() {
		return buf;
	}
	
	public int position() {
		return pos;
	}
	
	public long getLong() {
		return Ascii.parseLong(buf, pos, limit - pos);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof ByteChunk) {
			ByteChunk bc = (ByteChunk) obj;
			if (buf == null && bc.buf == null) {
				return true;
			}
			
			int len = limit - pos;
			if (buf == null || bc.buf == null || len != bc.length()) {
				return false;
			}
			
			int p1 = pos;
			int p2 = bc.pos;
			while (len-- > 0) {
				if (buf[p1++] != bc.buf[p2++]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean equalsVal(String str) {
		int len = limit - pos;
		if (str == null || buf == null || len != str.length()) {
			return false;
		}
		int p = pos;
		for (int i = 0; i < len; i++) {
			if (buf[p++] != str.charAt(i)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean equalsIgnoreCaseVal(String str) {
		if (buf == null && str == null) {
			return true;
		}
		int len = limit - pos;
		if (buf == null || len != str.length()) {
			return false;
		}
		int off = pos;
		for (int i = 0; i < len; i++) {
			if (Ascii.toLower(buf[off++]) != Ascii.toLower(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean equalsVal(CharChunk cc) {
		char[] chrs = cc.chars();
		if (buf == null && chrs == null) {
			return true;
		}
		
		int len = limit - pos;
		if (buf == null || chrs == null || len != cc.length()) {
			return false;
		}
		
		int p1 = pos;
		int p2 = cc.position();
		while (len-- > 0) {
			if (buf[p1++] != chrs[p2++]) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isNull() {
		return buf == null;
	}
	
	@Override
	public String toString() {
		if (buf == null) {
			return null;
		} else if (limit - pos == 0) {
			return "";
		}
		if (charset == null) {
			charset = DEFAULT_CHARSET;
		}
		return new String(buf, pos, limit - pos, charset);
	}
	
	public void recycle() {
		this.charset = null;
		this.pos = 0;
		this.limit = 0;
	}
}
