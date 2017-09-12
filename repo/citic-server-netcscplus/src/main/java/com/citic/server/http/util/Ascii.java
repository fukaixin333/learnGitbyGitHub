package com.citic.server.http.util;

/**
 * @author Liu Xuanfei
 * @date 2016年8月29日 下午3:25:37
 */
public final class Ascii {
	
	private static final byte[] toUpper = new byte[256];
	private static final byte[] toLower = new byte[256];
	
	private static final boolean[] isAlpha = new boolean[256];
	private static final boolean[] isUpper = new boolean[256];
	private static final boolean[] isLower = new boolean[256];
	private static final boolean[] isWhite = new boolean[256];
	private static final boolean[] isDigit = new boolean[256];
	
	static {
		for (int i = 0; i < 256; i++) {
			toUpper[i] = (byte) i;
			toLower[i] = (byte) i;
		}
		
		for (int lc = 'a'; lc <= 'z'; lc++) {
			int uc = lc + 'A' - 'a';
			
			toUpper[lc] = (byte) uc;
			toLower[uc] = (byte) lc;
			isAlpha[lc] = true;
			isAlpha[uc] = true;
			isLower[lc] = true;
			isUpper[uc] = true;
		}
		
		isWhite[' '] = true;
		isWhite['\t'] = true;
		isWhite['\r'] = true;
		isWhite['\n'] = true;
		isWhite['\f'] = true;
		isWhite['\b'] = true;
		
		for (int i = '0'; i <= '9'; i++) {
			isDigit[i] = true;
		}
	}
	
	public static int toUpper(int c) {
		return toUpper[c & 0xff] & 0xff;
	}
	
	public static int toLower(int c) {
		return toLower[c & 0xff] & 0xff;
	}
	
	public static boolean isAlpha(int c) {
		return isAlpha[c & 0xff];
	}
	
	public static boolean isUpper(int c) {
		return isUpper[c & 0xff];
	}
	
	public static boolean isLower(int c) {
		return isLower[c & 0xff];
	}
	
	public static boolean isWhite(int c) {
		return isWhite[c & 0xff];
	}
	
	public static boolean isDigit(int c) {
		return isDigit[c & 0xff];
	}
	
	/**
	 * 将指定范围的子字节数组转换为无符号长整型数
	 * 
	 * @param b
	 * @param off
	 * @param len
	 * @return
	 * @throws NumberFormatException
	 */
	public static long parseLong(byte[] b, int off, int len) throws NumberFormatException {
		int c;
		
		if (b == null || len <= 0 || !isDigit(c = b[off++])) {
			throw new NumberFormatException();
		}
		
		long n = c - '0';
		long m;
		while (--len > 0) {
			if (!isDigit(c = b[off++])) {
				throw new NumberFormatException();
			}
			m = n * 10 + c - '0';
			
			if (m < n) {
				// Overflow
				throw new NumberFormatException();
			} else {
				n = m;
			}
		}
		
		return n;
	}
}
