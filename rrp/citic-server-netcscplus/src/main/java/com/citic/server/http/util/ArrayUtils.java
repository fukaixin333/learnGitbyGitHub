package com.citic.server.http.util;

/**
 * 数组工具包
 * 
 * @author Liu Xuanfei
 * @date 2016年7月22日 下午7:30:22
 */
public class ArrayUtils {
	
	public static String[] trimRightBlank(String[] array) {
		for (int i = array.length - 1; i >= 0; i--) {
			if (!isBlank(array[i])) {
				String[] trimmedArray = new String[i + 1];
				System.arraycopy(array, 0, trimmedArray, 0, i + 1);
				return trimmedArray;
			}
		}
		return null;
	}
	
	public static String[] trimRightWhitespace(String[] array) {
		for (int i = array.length - 1; i >= 0; i--) {
			if (!isWhitespace(array[i])) {
				String[] trimmedArray = new String[i + 1];
				System.arraycopy(array, 0, trimmedArray, 0, i + 1);
				return trimmedArray;
			}
		}
		return null;
	}
	
	public static String[] trimRightEmpty(String[] array) {
		for (int i = array.length - 1; i >= 0; i--) {
			if (!isEmpty(array[i])) {
				String[] trimmedArray = new String[i + 1];
				System.arraycopy(array, 0, trimmedArray, 0, i + 1);
				return trimmedArray;
			}
		}
		return null;
	}
	
	protected static boolean isWhitespace(final CharSequence cs) {
		if (cs == null) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	protected static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}
	
	protected static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
}
