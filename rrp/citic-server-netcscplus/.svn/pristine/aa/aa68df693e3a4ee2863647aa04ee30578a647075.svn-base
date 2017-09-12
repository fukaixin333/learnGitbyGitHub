package com.citic.server.http.buf;

import java.io.Serializable;

public class MessageBytes implements Cloneable, Serializable {
	private static final long serialVersionUID = 4589674257872405270L;
	
	public static final int T_NULL = 0;
	public static final int T_STRING = 1;
	public static final int T_BYTES = 2;
	public static final int T_CHARS = 3;
	
	private int type = T_NULL;
	
	private String strValue;
	private ByteChunk byteC = new ByteChunk();
	private CharChunk charC = new CharChunk();
	
	public void setString(String str) {
		this.type = T_STRING;
		this.strValue = str;
	}
	
	public void setBytes(final byte[] bytes) {
		this.byteC.setBytes(bytes, 0, bytes.length);
		this.type = T_BYTES;
	}
	
	public void setBytes(byte[] bytes, int off, int len) {
		this.byteC.setBytes(bytes, off, len);
		this.type = T_BYTES;
	}
	
	public void setChars(final char[] chars) {
		this.charC.setChars(chars, 0, charC.length());
		this.type = T_CHARS;
	}
	
	public void setChars(char[] chars, int off, int len) {
		this.charC.setChars(chars, off, len);
		this.type = T_CHARS;
	}
	
	public int getType() {
		return type;
	}
	
	public int getLength() {
		switch (type) {
		case T_STRING:
			return strValue == null ? 0 : strValue.length();
		case T_BYTES:
			return byteC.length();
		case T_CHARS:
			return charC.length();
		default:
			return 0;
		}
	}
	
	public boolean isNull() {
		return strValue == null && byteC.isNull() && charC.isNull();
	}
	
	public long getLong() {
		switch (type) {
		case T_BYTES:
			return byteC.getLong();
		default:
			return Long.parseLong(toString());
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof MessageBytes) {
			MessageBytes mb = (MessageBytes) obj;
			if (type == T_NULL) {
				return mb.type == T_NULL;
			}
			
			if (type == T_STRING) {
				return mb.equalsVal(strValue);
			}
			
			if (mb.type == T_STRING) {
				return equalsVal(mb.strValue);
			} else if (mb.type == T_BYTES) {
				if (type == T_BYTES) {
					return byteC.equals(mb.byteC);
				} else if (type == T_CHARS) {
					return charC.equals(mb.byteC);
				}
			} else if (mb.type == T_CHARS) {
				if (type == T_BYTES) {
					return byteC.equals(mb.charC);
				} else if (type == T_CHARS) {
					return charC.equals(mb.charC);
				}
			}
		}
		return false;
	}
	
	public boolean equalsVal(String str) {
		switch (type) {
		case T_STRING:
			if (strValue == null) {
				return str == null;
			}
			return strValue.equals(str);
		case T_BYTES:
			return byteC.equalsVal(str);
		case T_CHARS:
			return charC.equalsValue(str);
		default:
			return false;
		}
	}
	
	public boolean equalsIgnoreCaseVal(String str) {
		switch (type) {
		case T_STRING:
			if (strValue == null) {
				return str == null;
			}
			return strValue.equalsIgnoreCase(str);
		case T_BYTES:
			return byteC.equalsIgnoreCaseVal(str);
		case T_CHARS:
			return charC.equalsIgnoreCaseVal(str);
		default:
			return false;
		}
	}
	
	public void recycle() {
		this.type = T_NULL;
		this.strValue = null;
		this.byteC.recycle();
		this.charC.recycle();
	}
	
	@Override
	public String toString() {
		switch (type) {
		case T_STRING:
			return strValue;
		case T_BYTES:
			return byteC.toString();
		case T_CHARS:
			return charC.toString();
		default:
			return null;
		}
	}
}
