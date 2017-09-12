package com.citic.server.service.time;

import java.util.Calendar;

/**
 * @author Liu Xuanfei
 * @date 2016年5月13日 下午2:42:20
 */
public interface TimeService {
	/**
	 * Field number for get and set indicating the year.
	 */
	public static final int YEAR = Calendar.YEAR;
	
	/**
	 * Field number for get and set indicating the month.
	 */
	public static final int MONTH = Calendar.MONTH;
	
	// public static final int WEEK = Calendar.WEEK_OF_YEAR;
	
	/**
	 * Field number for get and set indicating the day of the month.
	 */
	public static final int DAY = Calendar.DAY_OF_MONTH;
	
	/**
	 * Field number for get and set indicating the hour of the day.
	 */
	public static final int HOUR = Calendar.HOUR_OF_DAY;
	
	/**
	 * Field number for get and set indicating the minute within the hour.
	 */
	public static final int MINUTE = Calendar.MINUTE;
	
	// public static final int FIRST_DAY_OF_WEEK;
}