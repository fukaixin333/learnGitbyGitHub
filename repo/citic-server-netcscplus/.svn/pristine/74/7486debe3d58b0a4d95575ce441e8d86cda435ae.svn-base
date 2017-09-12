package com.citic.server.runtime;

/**
 * 十六进制转换
 * 
 * @author Liu Xuanfei
 * @date 2016年9月5日 下午4:49:42
 */
public final class HexCoder {
	
	private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	public static final int MIN_POINT = 0x00;
	public static final int MAX_POINT = 0xFF;
	
	public static final int RADIX = 0x10;
	
	public static String encode(int chr) {
		return Integer.toHexString(chr);
	}
	
	public static char[] encode(byte[] data) {
		return encode(data, false);
	}
	
	public final static char[] encode(byte[] data, boolean toLowerCase) {
		final char[] toHex = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
		
		int len = data.length;
		char[] value = new char[len << 1];
		// 将每个字节转成两位十六进制
		for (int i = 0, j = 0; i < len; i++) {
			value[j++] = toHex[(data[i] & 0xF0) >>> 4 & 0x0F];
			value[j++] = toHex[data[i] & 0x0F];
		}
		return value;
	}
	
	public static String encodeToString(byte[] data, boolean toLowerCase) {
		return new String(encode(data, toLowerCase));
	}
	
	public static byte[] decode(String data) {
		return decode(data.toCharArray());
	}
	
	public final static byte[] decode(char[] data) {
		int len = data.length;
		byte[] bytes = new byte[len >> 1];
		for (int i = 0, j = 0; i < len; j++) {
			// 将每两位十六进制合转成一个字节
			int l = toDigit(data[i++], i) << 4;
			bytes[j] = (byte) (l | toDigit(data[i++], i));
		}
		return bytes;
	}
	
	public final static byte[] decode(byte[] data) {
		int len = data.length;
		byte[] bytes = new byte[len >> 1];
		for (int i = 0, j = 0; i < len; j++) {
			// 将每两位十六进制合转成一个字节
			int l = toDigit(data[i++], i) << 4;
			bytes[j] = (byte) (l | toDigit(data[i++], i));
		}
		return bytes;
	}
	
	private static int toDigit(int c, int index) {
		if (c >= MIN_POINT && c <= MAX_POINT) {
			for (int i = 0; i < RADIX; i++) {
				if (c == DIGITS_UPPER[i] || c == DIGITS_LOWER[i]) {
					return i;
				}
			}
		}
		throw new IllegalArgumentException("Illegal hexadecimal character " + c + " at index " + index);
	}
}
