package com.citic.server.http.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字节工具包
 * 
 * @author Liu Xuanfei
 * @date 2016年7月22日 下午7:29:38
 */
public class ByteUtils {
	
	public static boolean arrayEquals(byte[] rst, byte[] dst, int count) {
		for (int i = 0; i < count; i++) {
			if (rst[i] != dst[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 此方法采用的是普通的BF算法，另外有一种称为KMP的算法。
	 * <p>
	 * 但往往HTTP请求报文中的<tt>boundary<tt>很少出现ABCDAB类似的重复串。<br />
	 * 解析HTTP请求报文时，干脆直接逐个字节去读取及判断。（Apache Tomcat底层就是这么干的）
	 * 
	 * @param rst
	 * @param dst
	 * @return
	 */
	public static byte[][] arraySplit(byte[] rst, byte[] dst) {
		if (rst.length < dst.length) {
			return new byte[][] {rst};
		}
		
		List<byte[]> list = new ArrayList<byte[]>();
		
		int cursor = 0;
		int j = 0;
		for (int i = 0; i < rst.length; i++) {
			if (rst[i] == dst[j]) {
				if (j == dst.length - 1) {
					byte[] e = Arrays.copyOfRange(rst, cursor, i - j);
					list.add(e);
					cursor = i + 1;
					j = 0;
				} else {
					j++;
				}
			} else {
				j = 0;
			}
		}
		
		if (cursor == 0) {
			return new byte[][] {rst};
		}
		
		if (cursor != rst.length) {
			byte[] e = Arrays.copyOfRange(rst, cursor, rst.length);
			list.add(e);
		}
		
		return list.toArray(new byte[0][]);
	}
}
