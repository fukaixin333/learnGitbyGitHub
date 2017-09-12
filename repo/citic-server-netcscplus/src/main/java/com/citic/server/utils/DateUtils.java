package com.citic.server.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * 时间工具
 * 
 * @author Liu Xuanfei
 * @date 2016年5月26日 上午9:18:54
 */
public class DateUtils {
	/**
	 * 比较时间与当前时间的大小
	 * 
	 * @param when
	 * @param format 时间格式字符串
	 * @return 0-等于；1-大于；-1-小于
	 */
	public static int compareToCurrDateTime(String when, String format) {
		if (StringUtils.isBlank(when)) {
			return -1;
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			Date date1 = dateFormat.parse(when);
			Date date2 = dateFormat.parse(dateFormat.format(new Date()));
			return compare(date1, date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 比较两个时间的大小
	 * 
	 * @param date1
	 * @param date2
	 * @return 0-等于；1-大于；-1-小于
	 */
	public static int compare(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return 0;
		} else if (date1 == null) {
			return -1;
		} else if (date2 == null) {
			return 1;
		}
		Calendar c1 = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
		Calendar c2 = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
		c1.setTime(date1);
		c2.setTime(date2);
		return c1.compareTo(c2);
	}
}
