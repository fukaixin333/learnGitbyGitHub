/**========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年8月22日
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**ß
 * @author gaojx
 *
 */
public class JsonUtils {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static Gson gson = new Gson();

	/**
	 * 把给定对象转换为json字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String toString(Object src) {
		String rslt = gson.toJson(src);
		return rslt;
	} 

	/**
	 * 把joson字符串转换为指定对象,对象类型错误时，不会报错，故需检查转换后属性是否为空
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T toObject(String jsonString, Class<T> cls) {
		T t = null;
		try {
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
			logger.error("json 转换失败：［" + jsonString + "]");
		}
		return t;
	}

}
