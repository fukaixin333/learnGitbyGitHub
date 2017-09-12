package com.citic.server.http;

/**
 * HTTP 常量
 * 
 * @author Liu Xuanfei
 * @date 2016年7月21日 上午9:40:22
 */
public final class HttpConstants {
	
	public static final String HTTP_KEY = "http";
	
	/** CR. */
	public static final byte CR = (byte) '\r';
	
	/** LF. */
	public static final byte LF = (byte) '\n';
	
	/** SP. */
	public static final byte SP = (byte) ' ';
	
	/** HT. */
	public static final byte HT = (byte) '\t';
	
	/** '?'. */
	public static final byte QUESTION = (byte) '?';
	
	/** ':'. */
	public static final byte COLON = (byte) ':';
	
	/** 'A'. */
	public static final byte A = (byte) 'A';
	
	/** 'a'. */
	public static final byte a = (byte) 'a';
	
	/** 'Z'. */
	public static final byte Z = (byte) 'Z';
	
	/** Lower case offset. */
	public static final byte LC_OFFSET = A - a;
	
	public static final boolean[] HTTP_TOKEN_CHAR = new boolean[128];
	static {
		for (int i = 0; i < 128; i++) {
			if (i < 32) {
				HTTP_TOKEN_CHAR[i] = false;
			} else {
				HTTP_TOKEN_CHAR[i] = true;
			}
		}
		HTTP_TOKEN_CHAR['('] = false;
		HTTP_TOKEN_CHAR[')'] = false;
		HTTP_TOKEN_CHAR['<'] = false;
		HTTP_TOKEN_CHAR['>'] = false;
		HTTP_TOKEN_CHAR['@'] = false;
		HTTP_TOKEN_CHAR[','] = false;
		HTTP_TOKEN_CHAR[';'] = false;
		HTTP_TOKEN_CHAR[':'] = false;
		HTTP_TOKEN_CHAR['\\'] = false;
		HTTP_TOKEN_CHAR['\"'] = false;
		HTTP_TOKEN_CHAR['/'] = false;
		HTTP_TOKEN_CHAR['['] = false;
		HTTP_TOKEN_CHAR[']'] = false;
		HTTP_TOKEN_CHAR['?'] = false;
		HTTP_TOKEN_CHAR['='] = false;
		HTTP_TOKEN_CHAR['{'] = false;
		HTTP_TOKEN_CHAR['}'] = false;
		HTTP_TOKEN_CHAR[' '] = false;
		HTTP_TOKEN_CHAR['\t'] = false;
		HTTP_TOKEN_CHAR[127] = false;
	}
	
	public static final byte DASH = (byte) '-';
	
	public static final byte[] END_OF_BOUNDARY = {DASH, DASH};
	
	public static final byte[] CRLF = {CR, LF};
}
