package com.citic.server.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class FiledCollector {
	
	// 如果使用 lombok.jar 则不考虑第二个字母是否是大写
	// 如果使用 IDE 则考虑第二个字母是否是大写
	
	public static Map<String, FieldEntity> getFields(Object obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
		InvocationTargetException {
		Map<String, FieldEntity> map = new HashMap<String, FieldEntity>();
		
		Class<?> clazz = obj.getClass();
		Field[] fileds = clazz.getDeclaredFields();
		for (Field filed : fileds) {
			String fieldName = filed.getName();
			
			// get getter method name
			String methodName = toGetter(fieldName);
			System.out.println(fieldName + "对应的getter方法：" + methodName);
			// invoke Method
			Method getter = clazz.getMethod(methodName);
			Object value = getter.invoke(obj);
			
			map.put(fieldName, new FieldEntity(fieldName, value, filed.getType()));
		}
		
		return map;
	}
	
	private static String toGetter(String filedName) {
		if (filedName.length() > 2) {
			char chr = filedName.charAt(1);
			// 当field的第二个字母是大写时，则Getter方法不会改变field的第一个字母的大小写。
			// 例如：aBcBean的Getter方法是getaBcBean()
			if (chr >= 'A' && chr <= 'Z') {
				return "get" + filedName;
			}
		}
		
		char chr = filedName.charAt(0);
		// field的第一个字母是小写时（由上，第二个字母必是小写），则Getter方法会将第一个字母变成大写。
		// 例如：abcBean的Getter方法是getAbcBean()
		if (chr >= 'a' && chr <= 'z') {
			filedName = StringUtils.replace(filedName, Character.toString(chr), Character.toString((char) (chr + 'A' - 'a')), 1);
		}
		return "get" + filedName;
	}
	
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		TestBean bean = new TestBean();
		Map<String, FieldEntity> map = FiledCollector.getFields(bean);
		for (Iterator<Entry<String, FieldEntity>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, FieldEntity> entry = iterator.next();
			String key = entry.getKey();
			FieldEntity entity = entry.getValue();
			System.out.println(key + ": " + entity.toString());
		}
	}
	
	public static boolean isBlank(final CharSequence cs) {
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

class TestBean {
	private String abcBean;
	private String aBcBean;
	private String ABcBean;
	private String AbcBean;
	
	public String getABcBean() {
		return ABcBean;
	}
	
	public void setABcBean(String aBcBean) {
		ABcBean = aBcBean;
	}
	
	public String getAbcBean() {
		return abcBean;
	}
	
	public void setAbcBean(String abcBean) {
		this.abcBean = abcBean;
	}
	
	public String getaBcBean() {
		return aBcBean;
	}
	
	public void setaBcBean(String aBcBean) {
		this.aBcBean = aBcBean;
	}
}
