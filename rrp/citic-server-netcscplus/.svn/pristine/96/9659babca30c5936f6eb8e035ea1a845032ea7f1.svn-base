package com.citic.server.http.util;

import java.util.Random;

/**
 * MultipartEntityBuilder
 * 
 * @author Liu Xuanfei
 * @date 2016年9月19日 上午10:45:49
 */
public class EntityUtils {
	/**
	 * The pool of ASCII chars to be used for generating a multipart boundary.
	 */
	private final static char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	public static String generateBoundary() {
		final StringBuilder buffer = new StringBuilder();
		final Random rand = new Random();
		final int count = rand.nextInt(11) + 30; // a random size from 30 to 40
		for (int i = 0; i < count; i++) {
			buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
		}
		return buffer.toString();
	}
}
