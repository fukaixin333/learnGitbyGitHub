/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年5月9日
 * Description:
 * =========================================================
 */
package com.citic.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

/**
 * 
 *
 */
public class Base64Utils {
	private final Logger logger = LoggerFactory.getLogger(Base64Utils.class);
	
	public Base64Utils(){
		
	}
	
	// 将 s 进行 BASE64 编码
	public static String encodeBase64(String s) {
		if (s == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static String decodeBase64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 加密
	 * @param s
	 * @return
	 */
	public static String makeEncryption(String s){
		if(s==null) s="";
		
		s = "@icitic@"+s;
		
		return Base64Utils.encodeBase64(s);
		
	}
	
	/**
	 * 解密
	 * @param s
	 * @return
	 */
	public static String unmakeEncryption(String s){
		if(s==null) s="";
		
		s = Base64Utils.decodeBase64(s);
		
		if(s.indexOf("@icitic@")>-1){
			s = s.replace("@icitic@", "");
		}
		
		return s;
		
	}
	
	/**
	 * 是否被加密过
	 * @param s
	 * @return
	 */
	public static boolean isEncryption(String s){
		if(s==null) s="";
		
		s = Base64Utils.decodeBase64(s);
		
		if(s.indexOf("@icitic@")>-1){
			return true;
		}else{
			return false;
		}
	}
	
	public  static void main(String[] args){
		
		System.out.println("密码生产=["+Base64Utils.makeEncryption( "123456" ) +"]");
		
		//System.out.println("2=["+Base64Utils.isEncryption( Base64Utils.makeEncryption( "123456" )) +"]");
		
		//System.out.println("2=["+Base64Utils.isEncryption(  "123456" ) +"]");
		
		//System.out.println("3=["+Base64Utils.unmakeEncryption( Base64Utils.makeEncryption( "123456" )) +"]");
		
	}

}
