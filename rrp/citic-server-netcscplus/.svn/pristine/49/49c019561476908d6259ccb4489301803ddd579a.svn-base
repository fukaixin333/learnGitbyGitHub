package com.citic.server.runtime;

/**
 * Utility的扩展
 * 
 * @author Liu Xuanfei
 * @date 2017年5月18日 下午4:54:11
 */
public class UtilityExt extends Utility {
	
	/**
	 * 字节数组两次Base64编码，基于GBK字符编码。
	 */
	public static String doubleEncodeGBKMIMEBase64(byte[] src) {
		String base64Text = encodeMIMEBase64(src);
		return encodeBase64(base64Text, StandardCharsets.GBK);
		
	}
	
	/**
	 * 将Base64编码的内容，基于GBK字符编码，连续解码两次。
	 */
	public static byte[] doubleDecodeGBKMIMEBase64(String text) {
		String base64Text = decodeMIMEBase64(text, StandardCharsets.GBK);
		return decodeBase64(base64Text.getBytes(StandardCharsets.ISO_8859_1), true, false);
	}
}
