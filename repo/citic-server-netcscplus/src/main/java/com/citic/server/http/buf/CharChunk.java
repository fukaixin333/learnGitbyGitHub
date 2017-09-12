package com.citic.server.http.buf;

import java.io.Serializable;

import com.citic.server.http.util.Ascii;

public class CharChunk implements Cloneable, Serializable {
	private static final long serialVersionUID = 5464924717537964310L;
	
	private char[] buf;
	private int pos;
	private int limit;
	
	public void setChars(char[] chars, int off, int len) {
		this.buf = chars;
		this.pos = off;
		this.limit = off + len;
	}
	
	public int length() {
		return limit - pos;
	}
	
	public char[] chars() {
		return buf;
	}
	
	public int position() {
		return pos;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof CharChunk) {
			CharChunk cc = (CharChunk) obj;
			if (buf == null && cc.buf == null) {
				return true;
			}
			
			int len = limit - pos;
			if (buf == null || cc.buf == null || len != cc.length()) {
				return false;
			}
			
			int p1 = pos;
			int p2 = cc.pos;
			while (len-- > 0) {
				if (buf[p1++] != cc.buf[p2++]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean equalsValue(String str) {
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
		return false;
	}
	
	public boolean equalsValue(ByteChunk bc) {
		byte[] buf2 = bc.bytes();
		if (buf == null && buf2 == null) {
			return true;
		}
		
		int len = limit - pos;
		if (buf == null || buf2 == null || len != bc.length()) {
			return false;
		}
		
		int p1 = pos;
		int p2 = bc.position();
		while (len-- > 0) {
			if (buf[p1++] != buf2[p2++]) {
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
		return new String(buf, pos, limit - pos);
	}
	
	public void recycle() {
		this.pos = 0;
		this.limit = 0;
	}
}
