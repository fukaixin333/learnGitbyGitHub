package com.citic.server.runtime;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

public class StrTools {

    /**
     * 字符串定长处理
     * @param str  源字符串
     * @param length 定义长度
     * @return
     * 
     * @author yinxiong
     * @date 2017年5月20日 下午2:47:23
     */
	public static String rightPad(String str, int length) {
		int len = 0;
		try {
			len = str.getBytes("GBK").length;
			if (len >= length) {
				return str;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str + StringUtils.rightPad("", length - len, ' ');
	}
}
