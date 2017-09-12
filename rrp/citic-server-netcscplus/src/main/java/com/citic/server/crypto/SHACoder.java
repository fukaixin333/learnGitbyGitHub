package com.citic.server.crypto;

import java.security.MessageDigest;

import com.citic.server.runtime.HexCoder;
import com.citic.server.runtime.StandardCharsets;

/**
 * SHA消息摘要组件
 * <p>
 * Java 6支持SHA-1、SHA-256、SHA-384和SHA-512四种算法。<br />
 * 如果需要使用SHA-224算法，可以通过第三方加密组件包Bouncy Castle提供支持。详见{@link SSH224Coder}类。
 * 
 * @author Liu Xuanfei
 * @date 2016年9月5日 下午5:27:15
 */
public final class SHACoder {
	
	/** SHA是SHA-1的简称 */
	private static final String KEY_ALGORITHM = "SHA";
	
	private static final String KEY_ALGORITHM_256 = "SHA-256";
	private static final String KEY_ALGORITHM_384 = "SHA-384";
	private static final String KEY_ALGORITHM_512 = "SHA-512";
	
	/**
	 * SHA-1消息摘要
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance(KEY_ALGORITHM);
		return md.digest(data);
	}
	
	/**
	 * SHA-1消息摘要，默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA(String data) throws Exception {
		return encodeSHA(data.getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * SHA-1消息摘要（十六进制编码）
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeSHAHex(byte[] data, boolean toLowerCase) throws Exception {
		return HexCoder.encodeToString(encodeSHA(data), toLowerCase);
	}
	
	/**
	 * SHA-1消息摘要（十六进制编码），默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeSHAHex(String data, boolean toLowerCase) throws Exception {
		return encodeSHAHex(data.getBytes(StandardCharsets.UTF_8), toLowerCase);
	}
	
	/**
	 * SHA-256消息摘要
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA256(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance(KEY_ALGORITHM_256);
		return md.digest(data);
	}
	
	/**
	 * SHA-256消息摘要，默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA256(String data) throws Exception {
		return encodeSHA256(data.getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * SHA-256消息摘要（十六进制编码）
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA256Hex(byte[] data, boolean toLowerCase) throws Exception {
		return HexCoder.encodeToString(encodeSHA256(data), toLowerCase);
	}
	
	/**
	 * SHA-256消息摘要（十六进制编码），默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA256Hex(String data, boolean toLowerCase) throws Exception {
		return encodeSHA256Hex(data.getBytes(StandardCharsets.UTF_8), toLowerCase);
	}
	
	/**
	 * SHA-384消息摘要
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA384(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance(KEY_ALGORITHM_384);
		return md.digest(data);
	}
	
	/**
	 * SHA-384消息摘要，默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA384(String data) throws Exception {
		return encodeSHA384(data.getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * SHA-384消息摘要（十六进制编码）
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA384Hex(byte[] data, boolean toLowerCase) throws Exception {
		return HexCoder.encodeToString(encodeSHA384(data), toLowerCase);
	}
	
	/**
	 * SHA-384消息摘要（十六进制编码），默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA384Hex(String data, boolean toLowerCase) throws Exception {
		return encodeSHA384Hex(data.getBytes(StandardCharsets.UTF_8), toLowerCase);
	}
	
	/**
	 * SHA-512消息摘要
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA512(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance(KEY_ALGORITHM_512);
		return md.digest(data);
	}
	
	/**
	 * SHA-512消息摘要，默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeSHA512(String data) throws Exception {
		return encodeSHA512(data.getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * SHA-512消息摘要（十六进制编码）
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA512Hex(byte[] data, boolean toLowerCase) throws Exception {
		return HexCoder.encodeToString(encodeSHA512(data), toLowerCase);
	}
	
	/**
	 * SHA-512消息摘要（十六进制编码），默认使用UTF-8字符集
	 * 
	 * @param data 待做摘要处理的数据
	 * @return 消息摘要
	 * @throws Exception
	 */
	public static String encodeSHA512Hex(String data, boolean toLowerCase) throws Exception {
		return encodeSHA512Hex(data.getBytes(StandardCharsets.UTF_8), toLowerCase);
	}
}
