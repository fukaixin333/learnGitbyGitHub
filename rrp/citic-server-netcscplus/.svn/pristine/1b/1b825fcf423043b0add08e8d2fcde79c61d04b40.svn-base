package com.citic.server.crypto;

import java.security.MessageDigest;

import com.citic.server.runtime.HexCoder;
import com.citic.server.runtime.StandardCharsets;

/**
 * MD5消息摘要组件
 * 
 * @author Liu Xuanfei
 * @date 2016年9月5日 下午12:02:29
 */
public final class MD5Coder {
	
	private static final String KEY_ALGORITHM = "MD5";
	
	/**
	 * MD5消息摘要
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encode(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance(KEY_ALGORITHM);
		// 执行消息摘要
		return md.digest(data);
	}
	
	/**
	 * MD5消息摘要，默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encode(String data) throws Exception {
		return encode(data.getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * MD5消息摘要（十六进制编码）
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeHex(byte[] data, boolean toLowerCase) throws Exception {
		return HexCoder.encodeToString(encode(data), toLowerCase);
	}
	
	/**
	 * MD5消息摘要（十六进制编码），默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeHex(String data, boolean toLowerCase) throws Exception {
		return encodeHex(data.getBytes(StandardCharsets.UTF_8), toLowerCase);
	}
}
