package com.citic.server.runtime;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * JiBX运行时工具类
 * 
 * @author Liu Xuanfei
 * @date 2016年3月4日 下午1:25:31
 */
public class Utility implements UtilityConstants, Base64Constants {
	
	// ==========================================================================================
	//                     Base64 Encoder
	// ==========================================================================================
	
	/**
	 * 文本Base64编码，普通模式，无换行。使用<code>GBK</code>字符集。
	 * 
	 * @param text 需编码文本
	 * @return Base64编码字符串
	 * @see {@link #encodeBase64(String, Charset)}
	 */
	public static String encodeGBKBase64(String text) {
		return encodeBase64(text, StandardCharsets.GBK);
	}
	
	/**
	 * 文本Base64编码，普通模式，无换行。使用<code>UTF-8</code>字符集。
	 * 
	 * @param text 需编码文本
	 * @return Base64编码字符串
	 * @see {@link #encodeBase64(String, Charset)}
	 */
	public static String encodeUTF8Base64(String text) {
		return encodeBase64(text, StandardCharsets.UTF_8);
	}
	
	/**
	 * 文本Base64编码，普通模式，无换行。
	 * 
	 * @param text 需编码文本
	 * @param charset 字符集
	 * @return Base64编码字符串
	 */
	public static String encodeBase64(String text, Charset charset) {
		if (text == null || text.length() == 0) {
			return "";
		}
		return encodeBase64(text.getBytes(charset));
	}
	
	/**
	 * 文本Base64编码，普通模式，无换行。
	 * 
	 * @param src 需编码字节数组
	 * @return Base编码字符串
	 */
	public static String encodeBase64(byte[] src) {
		if (src == null || src.length == 0) {
			return "";
		}
		byte[] encoded = encodeBase64(src, false, false);
		return new String(encoded, 0, encoded.length, StandardCharsets.ISO_8859_1);
	}
	
	/**
	 * 文本Base64编码，URL模式，无换行。使用<code>GBK</code>字符集。
	 * 
	 * @param text 需编码文本
	 * @return Base64编码字符串
	 * @see {@link #encodeURLBase64(String, Charset)}
	 */
	public static String encodeGBKURLBase64(String text) {
		return encodeURLBase64(text, StandardCharsets.GBK);
	}
	
	/**
	 * 文本Base64编码，URL模式，无换行。使用<code>UTF-8</code>字符集。
	 * 
	 * @param text 需编码文本
	 * @return Base64编码字符串
	 * @see {@link #encodeURLBase64(String, Charset)}
	 */
	public static String encodeUTF8URLBase64(String text) {
		return encodeURLBase64(text, StandardCharsets.UTF_8);
	}
	
	/**
	 * 文本Base64编码，URL模式，无换行。
	 * 
	 * @param text 需编码文本
	 * @param charset 字符集
	 * @return Base64编码字符串
	 */
	public static String encodeURLBase64(String text, Charset charset) {
		if (text == null || text.length() == 0) {
			return "";
		}
		byte[] encoded = encodeBase64(text.getBytes(charset), false, true);
		return new String(encoded, 0, encoded.length, StandardCharsets.ISO_8859_1);
	}
	
	/**
	 * 文本Base64编码，MIME模式，每76个字符换行。使用<code>GBK</code>字符集。
	 * 
	 * @param text 需编码文本
	 * @return Base64编码字符串
	 * @see {@link #encodeMIMEBase64(String, Charset)}
	 */
	public static String encodeGBKMIMEBase64(String text) {
		return encodeMIMEBase64(text, StandardCharsets.GBK);
	}
	
	/**
	 * 文本Base64编码，MIME模式，每76个字符换行。使用<code>UTF-8</code>字符集。
	 * 
	 * @param text 需编码文本
	 * @return Base64编码字符串
	 * @see {@link #encodeMIMEBase64(String, Charset)}
	 */
	public static String encodeUTF8MIMEBase64(String text) {
		return encodeMIMEBase64(text, StandardCharsets.UTF_8);
	}
	
	/**
	 * 文本Base64编码，MIME模式，每76个字符换行。
	 * 
	 * @param text 需编码文本
	 * @param charset 字符集
	 * @return Base64编码字符串
	 * @see {@link #encodeMIMEBase64(byte[])}
	 */
	public static String encodeMIMEBase64(String text, Charset charset) {
		if (text == null || text.length() == 0) {
			return "";
		}
		return encodeMIMEBase64(text.getBytes(charset));
	}
	
	public static String encodeMIMEBase64(byte[] src) {
		if (src == null || src.length == 0) {
			return "";
		}
		byte[] encoded = encodeBase64(src, true, false);
		return new String(encoded, 0, encoded.length, StandardCharsets.ISO_8859_1);
	}
	
	protected static byte[] encodeBase64(byte[] src, boolean isMIME, boolean isURL) {
		int len = 4 * ((src.length + 2) / 3);
		if (isMIME) {
			len += (len - 1) / MIMELINEMAX * CRLF.length;
		}
		byte[] dst = new byte[len];
		int ret = encode0(src, dst, 0, src.length, isMIME, isURL);
		if (ret != dst.length) {
			dst = Arrays.copyOf(dst, ret);
		}
		return dst;
	}
	
	private static int encode0(byte[] src, byte[] dst, int off, int end, boolean isMIME, boolean isURL) {
		char[] base64 = isURL ? toBase64URL : toBase64;
		int sp = off;
		int slen = (end - off) / 3 * 3;
		int sl = off + slen;
		if (isMIME && slen > MIMELINEMAX / 4 * 3) {
			slen = MIMELINEMAX / 4 * 3;
		}
		int dp = 0;
		while (sp < sl) {
			int sl0 = Math.min(sp + slen, sl);
			for (int sp0 = sp, dp0 = dp; sp0 < sl0;) {
				int bits = (src[sp0++] & 0xff) << 16 | (src[sp0++] & 0xff) << 8 | (src[sp0++] & 0xff);
				dst[dp0++] = (byte) base64[(bits >>> 18) & 0x3f];
				dst[dp0++] = (byte) base64[(bits >>> 12) & 0x3f];
				dst[dp0++] = (byte) base64[(bits >>> 6) & 0x3f];
				dst[dp0++] = (byte) base64[bits & 0x3f];
			}
			int dlen = (sl0 - sp) / 3 * 4;
			dp += dlen;
			sp = sl0;
			if (isMIME && dlen == MIMELINEMAX && sp < end) {
				for (byte b : CRLF) {
					dst[dp++] = b;
				}
			}
		}
		if (sp < end) { // 1 or 2 leftover bytes
			int b0 = src[sp++] & 0xff;
			dst[dp++] = (byte) base64[b0 >> 2];
			if (sp == end) {
				dst[dp++] = (byte) base64[(b0 << 4) & 0x3f];
				dst[dp++] = '=';
				dst[dp++] = '=';
			} else {
				int b1 = src[sp++] & 0xff;
				dst[dp++] = (byte) base64[(b0 << 4) & 0x3f | (b1 >> 4)];
				dst[dp++] = (byte) base64[(b1 << 2) & 0x3f];
				dst[dp++] = '=';
			}
		}
		return dp;
	}
	
	// ==========================================================================================
	//                     Base64 Decoder
	// ==========================================================================================
	
	/**
	 * 文本Base64解码，普通模式，无换行。使用<code>GBK</code>字符集。
	 * 
	 * @param text 需解码文本
	 * @return 解码字符串
	 * @see {@link #decodeBase64(String, Charset)}
	 */
	public static String decodeGBKBase64(String text) {
		return decodeBase64(text, StandardCharsets.GBK);
	}
	
	/**
	 * 文本Base64解码，普通模式，无换行。使用<code>UTF-8</code>字符集。
	 * 
	 * @param text 需解码文本
	 * @return 解码字符串
	 * @see {@link #decodeBase64(String, Charset)}
	 */
	public static String decodeUTF8Base64(String text) {
		return decodeBase64(text, StandardCharsets.UTF_8);
	}
	
	/**
	 * 文本Base64解码，普通模式，无换行。
	 * 
	 * @param text 需解码文本
	 * @param charset 字符集
	 * @return 解码字符串
	 */
	public static String decodeBase64(String text, Charset charset) {
		byte[] encoded = decodeBase64(text.getBytes(StandardCharsets.ISO_8859_1), false, false);
		return new String(encoded, 0, encoded.length, charset);
	}
	
	/**
	 * 文本Base64解码，普通模式，无换行。
	 * 
	 * @param text 需解码文本
	 * @return 解码字节数组
	 */
	public static byte[] decodeBase64(String text) {
		return decodeBase64(text.getBytes(StandardCharsets.ISO_8859_1), false, false);
	}
	
	/**
	 * 文本Base64解码，URL模式，无换行。使用<code>GBK</code>字符集。
	 * 
	 * @param text 需解码文本
	 * @return 解码字符串
	 * @see {@link #decodeURLBase64(String, Charset)}
	 */
	public static String decodeGBKURLBase64(String text) {
		return decodeURLBase64(text, StandardCharsets.GBK);
	}
	
	/**
	 * 文本Base64解码，URL模式，无换行。使用<code>UTF-8</code>字符集。
	 * 
	 * @param text 需解码文本
	 * @return 解码字符串
	 * @see {@link #decodeURLBase64(String, Charset)}
	 */
	public static String decodeUTF8URLBase64(String text) {
		return decodeURLBase64(text, StandardCharsets.UTF_8);
	}
	
	/**
	 * 文本Base64解码，URL模式，无换行。
	 * 
	 * @param text 需解码文本
	 * @param charset 字符集
	 * @return 解码字符串
	 */
	public static String decodeURLBase64(String text, Charset charset) {
		byte[] encoded = decodeBase64(text.getBytes(StandardCharsets.ISO_8859_1), false, true);
		return new String(encoded, 0, encoded.length, charset);
	}
	
	/**
	 * 文本Base64解码，MIME模式，每76个字符换行。使用<code>GBK</code>字符集。
	 * 
	 * @param text 需解码文本
	 * @return 解码字符串
	 * @see {@link #decodeMIMEBase64(String, Charset)}
	 */
	public static String decodeGBKMIMEBase64(String text) {
		return decodeMIMEBase64(text, StandardCharsets.GBK);
	}
	
	/**
	 * 文本Base64解码，MIME模式，每76个字符换行。使用<code>UTF-8</code>字符集。
	 * 
	 * @param text 需解码文本
	 * @return 解码字符串
	 * @see {@link #decodeMIMEBase64(String, Charset)}
	 */
	public static String decodeUTF8MIMEBase64(String text) {
		return decodeMIMEBase64(text, StandardCharsets.UTF_8);
	}
	
	/**
	 * 文本Base64解码，MIME模式，每76个字符换行。
	 * 
	 * @param text 需解码文本
	 * @param charset 字符集
	 * @return 解码字符串
	 * @see {@link #decodeMIMEBase64(String)}
	 */
	public static String decodeMIMEBase64(String text, Charset charset) {
		byte[] encoded = decodeMIMEBase64(text);
		return new String(encoded, 0, encoded.length, charset);
	}
	
	public static byte[] decodeMIMEBase64(String text) {
		return decodeBase64(text.getBytes(StandardCharsets.ISO_8859_1), true, false);
	}
	
	public static byte[] decodeMIMEBase64(byte[] byts) {
		return decodeBase64(byts, true, false);
	}
	
	protected static byte[] decodeBase64(byte[] src, boolean isMIME, boolean isURL) {
		int len = src.length;
		int[] base64 = isURL ? fromBase64URL : fromBase64;
		if (len < 2) {
			if (isMIME && base64[0] == -1) {
				len = 0;
			} else {
				throw new IllegalArgumentException("Input byte[] should at least have 2 bytes for base64 bytes");
			}
		} else {
			int paddings = 0;
			int sp = 0;
			int sl = len;
			if (isMIME) {
				// scan all bytes to fill out all non-alphabet. a performance trade-off of pre-scan or Arrays.copyOf
				int n = 0;
				while (sp < sl) {
					int b = src[sp++] & 0xff;
					if (b == '=') {
						len -= (sl - sp + 1);
						break;
					}
					if ((b = base64[b]) == -1)
						n++;
				}
				len -= n;
			} else {
				if (src[sl - 1] == '=') {
					paddings++;
					if (src[sl - 2] == '=')
						paddings++;
				}
			}
			if (paddings == 0 && (len & 0x3) != 0)
				paddings = 4 - (len & 0x3);
			len = 3 * ((len + 3) / 4) - paddings;
		}
		
		byte[] dst = new byte[len];
		int ret = decode0(src, dst, 0, src.length, isMIME, isURL);
		if (ret != dst.length) {
			dst = Arrays.copyOf(dst, ret);
		}
		return dst;
	}
	
	private static int decode0(byte[] src, byte[] dst, int off, int end, boolean isMIME, boolean isURL) {
		int[] base64 = isURL ? fromBase64URL : fromBase64;
		int dp = 0;
		int bits = 0;
		int shiftto = 18; // pos of first byte of 4-byte atom
		while (off < end) {
			int b = src[off++] & 0xff;
			if ((b = base64[b]) < 0) {
				if (b == -2) {
					if (shiftto == 6 && (off == end || src[off++] != '=') || shiftto == 18) {
						throw new IllegalArgumentException("Input byte array has wrong 4-byte ending unit");
					}
					break;
				}
				if (isMIME) // skip if for rfc2045
					continue;
				else {
					throw new IllegalArgumentException("Illegal base64 character " + Integer.toString(src[off - 1], 16));
				}
			}
			bits |= (b << shiftto);
			shiftto -= 6;
			if (shiftto < 0) {
				dst[dp++] = (byte) (bits >> 16);
				dst[dp++] = (byte) (bits >> 8);
				dst[dp++] = (byte) (bits);
				shiftto = 18;
				bits = 0;
			}
		}
		// reached end of byte array or hit padding '=' characters.
		if (shiftto == 6) {
			dst[dp++] = (byte) (bits >> 16);
		} else if (shiftto == 0) {
			dst[dp++] = (byte) (bits >> 16);
			dst[dp++] = (byte) (bits >> 8);
		} else if (shiftto == 12) {
			// dangling single "x", incorrectly encoded.
			throw new IllegalArgumentException("Last unit does not have enough valid bits");
		}
		// anything left is invalid, if is not MIME.
		// if MIME, ignore all non-base64 character
		while (off < end) {
			if (isMIME && base64[src[off++]] < 0) {
				continue;
			}
			throw new IllegalArgumentException("Input byte array has incorrect ending byte at " + off);
		}
		return dp;
	}
	
	// ==========================================================================================
	//                     Hex Convert
	// ==========================================================================================
	
	public static String convertToHex(String text, Charset charset) {
		if (text == null) {
			return null;
		}
		byte[] bytes = text.getBytes(charset);
		return convertToHex(bytes);
	}
	
	public static String convertToHex(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		StringBuilder rs = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			rs.append(toHex[(b & 0xF0) >>> 4 & 0x0F]);
			rs.append(toHex[b & 0x0F]);
		}
		return rs.toString();
	}
	
	public static String convertHexToString(String text, Charset charset) {
		if (text == null) {
			return null;
		}
		return new String(convertHexToBytes(text), charset);
	}
	
	public static byte[] convertHexToBytes(String text) {
		if (text == null) {
			return null;
		}
		byte[] bytes = new byte[text.length() / 2];
		for (int i = 0, j = 0; i < text.length(); i += 2, j++) {
			char ch1 = text.charAt(i);
			char ch2 = text.charAt(i + 1);
			int i1 = 0, i2 = 0;
			for (int k = 0; k < toHex.length; k++) {
				if (ch1 == toHex[k]) {
					i1 = k;
					break;
				}
			}
			for (int k = 0; k < toHex.length; k++) {
				if (ch2 == toHex[k]) {
					i2 = k;
					break;
				}
			}
			bytes[j] = (byte) (i1 << 4 | i2);
		}
		return bytes;
	}
	
	// ==========================================================================================
	//                     Date Format
	// ==========================================================================================
	public static String currTime6() {
		SimpleDateFormat formatTime6 = new SimpleDateFormat("HHmmss", Locale.CHINESE);
		return formatTime6.format(new Date());
	}
	
	@Deprecated
	public static String currData8() {
		return currDate8();
	}
	
	/**
	 * 获取当前'yyyyMMdd'格式日期
	 * 
	 * @return 当前日期
	 */
	public static String currDate8() {
		SimpleDateFormat formatDate8 = new SimpleDateFormat("yyyyMMdd", Locale.CHINESE);
		return formatDate8.format(new Date());
	}
	
	public static String currDate10() {
		SimpleDateFormat formatDate10 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
		return formatDate10.format(new Date());
	}
	
	public static String currDateCN() {
		SimpleDateFormat formatDateCN = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINESE);
		return formatDateCN.format(new Date());
	}
	
	public static String currDateTime14() {
		SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
		return formatDateTime14.format(new Date());
	}
	
	public static String currDateTime19() {
		SimpleDateFormat formatDateTime19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
		return formatDateTime19.format(new Date());
	}
	
	public static String currDateTime19V() {
		SimpleDateFormat formatDateTime19V = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINESE); // virgule
		return formatDateTime19V.format(new Date());
	}
	
	public static String toDate8(String str) throws Exception {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
		str = StringUtils.replaceChars(str, "-/: ", "");
		str = StringUtils.rightPad(str, 14, "01");
		Date date = formatDateTime14.parse(str);
		
		SimpleDateFormat formatDate8 = new SimpleDateFormat("yyyyMMdd", Locale.CHINESE);
		return formatDate8.format(date);
	}
	
	public static String toDate8Required(String str) throws Exception {
		String date8 = toDateTime14(str);
		if (str == null || str.length() == 0) {
			return "00000000";
		}
		return date8;
	}
	
	public static String toDate8Base64(String str) throws Exception {
		return encodeUTF8MIMEBase64(toDate8(str));
	}
	
	public static String toDate8Base64Required(String str) throws Exception {
		return encodeUTF8MIMEBase64(toDate8Required(str));
	}
	
	public static String toDate10(String str) throws Exception {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
		str = StringUtils.replaceChars(str, "-/: ", "");
		str = StringUtils.rightPad(str, 14, "01");
		Date date = formatDateTime14.parse(str);
		
		SimpleDateFormat formatDate10 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
		return formatDate10.format(date);
	}
	
	public static String toDate10Base64(String str) throws Exception {
		return encodeUTF8MIMEBase64(toDate10(str));
	}
	
	public static String base64ToDate10(String str) throws Exception {
		return toDate10(decodeUTF8MIMEBase64(str));
	}
	
	@Deprecated
	public static String toDate10_1(String str) throws Exception {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
		str = StringUtils.replaceChars(str, "-/: ", "");
		str = StringUtils.rightPad(str, 14, "01");
		Date date = formatDateTime14.parse(str);
		
		SimpleDateFormat formatDate10V = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINESE);
		return formatDate10V.format(date);
	}
	
	public static String toDate10V(String str) throws Exception {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
		str = StringUtils.replaceChars(str, "-/: ", "");
		str = StringUtils.rightPad(str, 14, "01");
		Date date = formatDateTime14.parse(str);
		
		SimpleDateFormat formatDate10V = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINESE);
		return formatDate10V.format(date);
	}
	
	public static String toDate10VBase64(String str) throws Exception {
		return encodeUTF8MIMEBase64(toDate10V(str));
	}
	
	public static String toDateTime14(String str) throws Exception {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
		str = StringUtils.replaceChars(str, "-/: ", "");
		if (str.length() < 8) {
			str = StringUtils.rightPad(str, 8, "01");
		}
		str = StringUtils.rightPad(str, 14, "0");
		Date date = formatDateTime14.parse(str);
		return formatDateTime14.format(date);
	}
	
	public static String toDateTime14Required(String str) throws Exception {
		String dateTime14 = toDateTime14(str);
		if (str == null || str.length() == 0) {
			return "00000000000000";
		}
		return dateTime14;
	}
	
	public static String toDateTime14Base64(String str) throws Exception {
		return encodeUTF8MIMEBase64(toDateTime14(str));
	}
	
	public static String toDateTime14Base64Required(String str) throws Exception {
		return encodeUTF8MIMEBase64(toDateTime14Required(str));
	}
	
	public static String toDateTime19(String str) throws Exception {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
		str = StringUtils.replaceChars(str, "-/: ", "");
		if (str.length() < 8) {
			str = StringUtils.rightPad(str, 8, "01");
		}
		str = StringUtils.rightPad(str, 14, "0");
		Date date = formatDateTime14.parse(str);
		
		SimpleDateFormat formatDateTime19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
		return formatDateTime19.format(date);
	}
	
	public static String toDateTimeCN(String str) throws Exception {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
		str = StringUtils.replaceChars(str, "-/: ", "");
		if (str.length() < 8) {
			str = StringUtils.rightPad(str, 8, "01");
		}
		str = StringUtils.rightPad(str, 14, "0");
		Date date = formatDateTime14.parse(str);
		
		SimpleDateFormat formatDateCN = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINESE);
		return formatDateCN.format(date);
	}
	
	public static String toDateTime19Required(String str) throws Exception {
		String dateTime19 = toDateTime19(str);
		if (dateTime19 == null || dateTime19.length() == 0) {
			return "0000-00-00 00:00:00";
		}
		return dateTime19;
	}
	
	public static String toDateTime19Base64(String str) throws Exception {
		return encodeUTF8MIMEBase64(toDateTime19(str));
	}
	
	public static String toDateTime19Base64Required(String str) throws Exception {
		return encodeUTF8MIMEBase64(toDateTime19Required(str));
	}
	
	public static String base64ToDateTime19(String str) throws Exception {
		return toDateTime19(decodeUTF8MIMEBase64(str));
	}
	
	public static String toDateTime19_1(String str) throws Exception {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
		str = StringUtils.replaceChars(str, "-/: ", "");
		if (str.length() < 8) {
			str = StringUtils.rightPad(str, 8, "01");
		}
		str = StringUtils.rightPad(str, 14, "0");
		Date date = formatDateTime14.parse(str);
		
		SimpleDateFormat formatDateTime19V = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINESE); // virgule
		return formatDateTime19V.format(date);
	}
	
	public static String toDateTime19VBase64(String str) throws Exception {
		return encodeUTF8MIMEBase64(toDateTime19_1(str));
	}
	
	public static String base64ToDateTime19V(String str) throws Exception {
		return toDateTime19_1(decodeUTF8MIMEBase64(str));
	}
	
	public static String validateDateTimeTo19(String str) throws JiBXFormatException {
		str = StringUtils.replaceChars(str, "-/: ", "");
		if (StringUtils.isBlank(str) || (str.length() != 8 && str.length() != 14)) {
			throw new JiBXFormatException("0800", "日期格式错误");
		}
		try {
			SimpleDateFormat formatDateTime14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
			str = StringUtils.rightPad(str, 14, "0");
			Date date = formatDateTime14.parse(str);
			
			SimpleDateFormat formatDateTime19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
			return formatDateTime19.format(date);
		} catch (Exception e) {
			throw new JiBXFormatException("0899", "其他日期错误", e);
		}
	}
	
	public static String validateCurrency(String currency) throws JiBXFormatException {
		if (StringUtils.isBlank(currency) || currency.startsWith("-") || !NumberUtils.isNumber(currency)) {
			throw new JiBXFormatException("0200", "无效金额");
		}
		return currency;
	}
	
	public static String validateBlank(String str) throws JiBXFormatException {
		if (StringUtils.isBlank(str)) {
			throw new JiBXFormatException("0500", "报文格式错误");
		}
		return str;
	}
	
	// ==========================================================================================
	//                     String Format
	// ==========================================================================================
	
	public static String trim(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}
	
	@Deprecated
	public static String noBlankCurrency(String currency) {
		if (StringUtils.isBlank(currency)) {
			return "0.00";
		}
		return currency;
	}
	
	public static String noBlankBalance(String balance) {
		if (StringUtils.isBlank(balance)) {
			return "0.00";
		}
		return balance;
	}
	
	@Deprecated
	public static String noBlankCurrencyBase64(String currency) {
		return encodeUTF8MIMEBase64(noBlankCurrency(currency));
	}
	
	public static String toBalanceBase64(String balance) {
		return encodeUTF8MIMEBase64(noBlankBalance(balance));
	}
	
	/**
	 * 整数金额除以100，并精确到分（如果是小数，则不做处理）
	 * 
	 * @param sum
	 * @return
	 */
	public static String precisionOfCent(String sum) {
		if (sum == null || sum.length() == 0) {
			return "";
		}
		
		String trimSum = sum.trim();
		if (!isNumeric(trimSum, false)) {
			return sum; // 返回原值
		}
		
		BigDecimal percent = new BigDecimal(100);
		BigDecimal decimal = new BigDecimal(trimSum).divide(percent, 2, BigDecimal.ROUND_DOWN);
		return decimal.toString();
	}
	
	/**
	 * 金额精确到分，并乘以100转换成整数
	 * <p>
	 * 广发银行核心接口金额精确到分，但不支持小数点，所以需要乘以100，并取整数部分。
	 * 
	 * @param sum
	 * @return
	 */
	public static String noCent(String sum) {
		if (sum == null || sum.length() == 0) {
			return "";
		}
		
		String trimSum = sum.trim();
		if (!isNumeric(trimSum, true)) {
			return sum; // 返回原值
		}
		
		BigDecimal decimal = new BigDecimal(trimSum).setScale(2, BigDecimal.ROUND_DOWN);
		return decimal.unscaledValue().toString();
	}
	
	// 保留小数点两位
	public static String decimalPoint2(String sum) {
		if (sum == null || sum.length() == 0) {
			return "";
		}
		
		String trimSum = sum.trim();
		if (!isNumeric(trimSum, true)) {
			return sum; // 返回原值
		}
		
		BigDecimal decimal = new BigDecimal(trimSum).setScale(2, BigDecimal.ROUND_DOWN);
		return decimal.toString();
	}
	
	public static String toString(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}
	
	/**
	 * 必输项为空时，转换成"-"代替。
	 * 
	 * @param str
	 * @return
	 */
	public static String noBlankString(String str) {
		if (StringUtils.isBlank(str)) {
			return "-";
		}
		return str;
	}
	
	public static String noBlankBase64(String str) {
		return encodeUTF8MIMEBase64(noBlankString(str));
	}
	
	/**
	 * 不允许为<code>null</code>，否则转换为空字符串（""）
	 * 
	 * @param str
	 * @return
	 */
	public static String required(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}
	
	public static Integer toInt(String s) {
		if (!isNumeric(s, false)) {
			return 0;
		}
		return Integer.parseInt(s);
	}
	
	// ==========================================================================================
	//                     Helper Code
	// ==========================================================================================
	public static boolean isNumeric(String str, boolean decimal) {
		if (str == null || str.length() == 0) {
			return false;
		}
		final int sz = str.length();
		int point = 0;
		for (int i = 0; i < sz; i++) {
			if (str.charAt(i) == '.') {
				if (decimal && point == 0) {
					point++;
				} else {
					return false;
				}
			} else if ((str.charAt(i) == '-' || str.charAt(i) == '+') && i == 0) {
				continue;
			} else if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
}
