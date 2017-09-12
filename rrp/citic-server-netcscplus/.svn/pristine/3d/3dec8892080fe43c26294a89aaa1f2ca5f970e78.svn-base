/**
 * 日期处理公共类
 */
package com.citic.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

/**
 * @author gaosen
 *
 */
public class AmlDtUtils {
	// ----------------粒度定义------------------------------------------------------
	/** 日粒度 */
	public static final int DAY = 1;

	/** 周粒度 */
	public static final int WEEK = 2;

	/** 旬粒度 */
	public static final int TENDAYS = 4;

	/** 月粒度 */
	public static final int MONTH = 8;

	/** 季粒度 */

	public static final int SEASON = 16;

	/** 半年粒度 */
	public static final int HALFYEAR = 32;

	/** 年粒度 */

	public static final int YEAR = 64;

	// -------------------时间字符串格式定义-----------------------------------------------------
	private static final String ISO_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

	private static final String ISO_DATE = "yyyy-MM-dd";

	private static final String[] parsePatterns = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd" };

	/**
	 * 取得日期的小时
	 * 
	 * @param date
	 *            日期
	 * @return hh 小时字符串
	 */
	public static String getHour(Date date) {
		return FormatDate(date, "HH");
	}

	/**
	 * 取得日期的年份
	 * 
	 * @param date
	 *            日期
	 * @return yyyy 年份字符串
	 */
	public static String getYear(Date date) {
		return FormatDate(date, "yyyy");
	}

	/**
	 * 取得日期的月份
	 * 
	 * @param date
	 *            日期
	 * @return mm 月份字符串
	 */
	public static String getMonth(Date date) {
		return FormatDate(date, "MM");
	}

	public static String getMonthBegin(Date date) {
		String newDateStr = FormatDate(date, "yyyy-MM") + "-01";
		// FormatDate(date, "yyyy-MM-dd");
		return newDateStr;
	}

	/**
	 * 取得日期的天份
	 * 
	 * @param date
	 *            日期
	 * @return dd 天字符串
	 */
	public static String getDay(Date date) {
		return FormatDate(date, "dd");
	}

	/**
	 * 取得日期的分钟
	 * 
	 * @param date
	 *            时间
	 * @return mm 分钟字符串
	 */
	public static String getMinute(Date date) {
		return FormatDate(date, "mm");
	}

	/**
	 * 取得时间的秒
	 * 
	 * @param date
	 *            时间
	 * @return ss 秒字符串
	 */
	public static String getSecond(Date date) {
		return FormatDate(date, "ss");
	}

	/**
	 * 对日期进行格式化
	 * 
	 * @param date
	 *            日期
	 * @param sf
	 *            日期格式
	 * @return 字符串
	 */
	public static String FormatDate(Date date, String sf) {
		if (date == null)
			return "";
		SimpleDateFormat dateformat = new SimpleDateFormat(sf);
		return dateformat.format(date);
	}

	/**
	 * 将字符串日期转换为java日期
	 * 
	 * @param dtStr
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String dtStr) throws ParseException {
		Assert.notNull(dtStr, "字符串日期不能为空");
		return DateUtils.parseDate(dtStr, parsePatterns);
	}

	/**
	 * 以国际标准格式将指定日期转换为字符串格式
	 * 
	 * @param dt
	 * @return
	 */
	public static String toStrDate(Date dt) {
		return toStrDate(dt, ISO_DATE);
	}

	/**
	 * 以国际标准格式将指定日期转换为字符串格式
	 * 
	 * @param dt
	 * @return
	 * @throws ParseException
	 */
	public static String toStrDate(String dtStr) throws ParseException {
		return toStrDate(toDate(dtStr), ISO_DATE);
	}

	/**
	 * 按照用户指定格式将日期转换为字符串格式
	 * 
	 * @param dt
	 * @param formatStr
	 * @return
	 */
	public static String toStrDate(Date dt, String formatStr) {
		if (dt == null) {
			return "";
		}
		FastDateFormat fdt = FastDateFormat.getInstance(formatStr);
		return fdt.format(dt);
	}

	/**
	 * 将java日期转换为数据库形式日期
	 * 
	 * @param dt
	 * @return
	 */
	public static java.sql.Date toDbDate(Date dt) {
		Assert.notNull(dt, "日期不能为空");
		return new java.sql.Date(dt.getTime());
	}

	/**
	 * 将字符型日期转换为数据库型日期
	 * 
	 * @param dtStr
	 * @return
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static java.sql.Date toDbDate(String dtStr) throws ParseException {
		Assert.notNull(dtStr, "字符串日期不能为空");

		return new java.sql.Date(toDate(dtStr).getTime());
	}

	/**
	 * 按照指定格式取当前时间
	 * 
	 * @param formatStr
	 * @return
	 */
	public static String getNowDate(String formatStr) {
		Assert.notNull(formatStr, "formatStr不能为空");

		DateTime nowTime = new DateTime();
		return nowTime.toString(formatStr);
	}

	/**
	 * 按照国际标准格式取系统当前日期
	 */
	public static String getNowDate() {
		return getNowDate(ISO_DATE);
	}

	/**
	 * 按照国际标准格式取系统当前时间
	 * 
	 * @return
	 */
	public static String getNowTime() {
		return getNowDate(ISO_TIMESTAMP);
	}

	/**
	 * 以国标格式得到字符串日期，指定粒度的期初日期
	 * 
	 * @param strDt
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	public static String getBeginDt(String strDt, int gran) throws ParseException {
		return toStrDate(getBeginDt(toDate(strDt), gran));
	}

	/**
	 * 获取下一个粒度的起始日期
	 * 
	 * @param strDt
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	public static String getNextBeginDt(String strDt, int gran) throws ParseException {
		return getBeginDt(add(strDt, gran, 1), gran);
	}

	/**
	 * 获取上一个粒度的起始日期
	 * 
	 * @param strDt
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	static String getPreBeginDt(String strDt, int gran) throws ParseException {
		return getBeginDt(add(strDt, gran, 1), gran);
	}

	/**
	 * 获取下一个粒度日期
	 * 
	 * @param strDt
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	public static String getNextDt(String strDt, int gran) throws ParseException {
		return add(strDt, 1, gran);
	}

	/**
	 * 获取上一个粒度日期
	 * 
	 * @param strDt
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	public static String getPreDt(String strDt, int gran) throws ParseException {
		return add(strDt, -1, gran);
	}

	/**
	 * 取指定日期指定粒度的期初日期
	 * 
	 * @param dt
	 * @param gran
	 *            粒度
	 * @return
	 */
	public static Date getBeginDt(Date dt, int gran) {
		Assert.notNull(dt, "参数不能为空");

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dt);

		switch (gran) {
		case WEEK:
			gc.add(Calendar.DATE, -1);
			gc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			break;
		case TENDAYS:
			int dtDay = gc.get(Calendar.DAY_OF_MONTH);
			int firstTenDays = dtDay > 20 ? 21 : dtDay > 10 ? 11 : 1;
			gc.set(Calendar.DAY_OF_MONTH, firstTenDays);
			break;
		case MONTH:
			gc.set(Calendar.DAY_OF_MONTH, 1);
			break;
		case SEASON:
			int dtMonth = gc.get(Calendar.MONTH) + 1;
			int firstMonth = dtMonth < 4 ? 1 : dtMonth < 7 ? 4 : dtMonth < 10 ? 7 : 10;
			gc.set(gc.get(Calendar.YEAR), firstMonth - 1, 1);
			break;
		case HALFYEAR:
			int firstHalfYear = gc.get(Calendar.MONTH) < 6 ? 1 : 7;
			gc.set(gc.get(Calendar.YEAR), firstHalfYear - 1, 1);
			break;
		case YEAR:
			gc.set(gc.get(Calendar.YEAR), 0, 1);
			break;
		default:
			break;
		}
		return gc.getTime();
	}

	/**
	 * 取指定粒度指定粒度的期末日期
	 * 
	 * @param dt
	 * @param gran
	 * @return
	 */
	public static Date getEndDt(Date dt, int gran) {
		int endDay;
		int endMonth;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dt);
		int dtYear = gc.get(Calendar.YEAR);
		int dtMonth = gc.get(Calendar.MONTH) + 1;
		int dtDay = gc.get(Calendar.DAY_OF_MONTH);

		switch (gran) {
		case WEEK:
			gc.add(Calendar.DATE, 6);
			gc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			break;
		case TENDAYS:
			endDay = dtDay < 11 ? 10 : dtDay < 21 ? 20 : gc.getActualMaximum(Calendar.DAY_OF_MONTH);
			gc.set(Calendar.DAY_OF_MONTH, endDay);
			break;
		case MONTH:
			gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
			break;
		case SEASON:
			endMonth = dtMonth < 4 ? 3 : dtMonth < 7 ? 6 : dtMonth < 10 ? 9 : 12;
			gc.set(Calendar.MONTH, endMonth - 1);
			gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
			break;
		case HALFYEAR:
			endMonth = dtMonth < 7 ? 6 : 12;
			endDay = dtMonth < 7 ? 30 : 31;
			gc.set(dtYear, endMonth - 1, endDay);
			break;
		case YEAR:
			gc.set(dtYear, 11, 31);
			break;
		default:
			break;
		}
		return gc.getTime();
	}

	/**
	 * 以国标格式得到指定字符串日期，指定粒度的期末日期
	 * 
	 * @param strDt
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	public static String getEndDt(String strDt, int gran) throws ParseException {
		return toStrDate(getEndDt(toDate(strDt), gran));
	}

	/**
	 * 顺序返回指定粒度，指定时间段内的所有日期列表。 <h5>Example</h5>
	 * 
	 * <pre>
	 * AmlDtUtils.getDtList(&quot;20060227&quot;, &quot;2006-03-11&quot;, AmlDtUtils.TENDAYS)= {&quot;2006-02-28&quot;,&quot;2006-03-10&quot;}
	 * </pre>
	 * 
	 * @param strDt1
	 * @param strDt2
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	public static ArrayList<String> getDtList(String strDt1, String strDt2, int gran) throws ParseException {
		return getDtList(strDt1, strDt2, gran, true);
	}

	/**
	 * 返回指定粒度，指定时间段内的所有日期列表。 <h5>Example</h5>
	 * 
	 * <pre>
	 *         AmlDtUtils.getDtList(&quot;20060227&quot;, &quot;2006-03-11&quot;, AmlDtUtils.TENDAYS,true) = {&quot;2006-02-28&quot;,&quot;2006-03-10&quot;}
	 *         AmlDtUtils.getDtList(&quot;2006-03-11&quot;,&quot;20060227&quot;,  AmlDtUtils.TENDAYS,false) = {&quot;2006-02-28&quot;,&quot;2006-03-10&quot;,&quot;2006-03-20&quot;}
	 * </pre>
	 * 
	 * @param strDt1
	 *            日期1
	 * @param strDt2
	 *            日期2
	 * @param gran
	 *            粒度
	 * @param isBetween
	 *            返回日期是否必须在时间段内 true 是 false 否
	 * @return
	 * @throws ParseException
	 */
	public static ArrayList<String> getDtList(String strDt1, String strDt2, int gran, boolean isBetween) throws ParseException {
		Date beginDt = toDate(strDt1);
		Date endDt = toDate(strDt2);
		Date tmpDate;
		int granSize = sub(endDt, beginDt, gran);

		if (granSize < 0) {
			granSize *= -1;
			tmpDate = beginDt;
			beginDt = endDt;
			endDt = tmpDate;
		}
		ArrayList<String> result = Lists.newArrayList();
		for (int i = 0; i < granSize; i++) {
			result.add(toStrDate(add(beginDt, gran, i)));

		}
		// 判断是否加最后一个日期
		tmpDate = add(beginDt, gran, granSize);
		if (comp(tmpDate, endDt, DAY) == 0 || !isBetween) {
			result.add(toStrDate(tmpDate));
		}

		return result;
	}

	/**
	 * 以字符串形式返回指定粒度，指定时间段内的所有日期.
	 * 
	 * @param strDt1
	 * @param strDt2
	 * @param granStr
	 * @return
	 * @throws ParseException
	 */
	public static String getDtStr(String strDt1, String strDt2, String granStr) throws ParseException {
		return getDtStr(strDt1, strDt2, granStr, true);
	}

	/**
	 * 以字符串形式返回指定粒度，指定时间段内的所有日期.
	 * 
	 * @param strDt1
	 * @param strDt2
	 * @param gran
	 * @param isBetween
	 *            返回日期是否必须在时间段内 true 是 false 否
	 * @return
	 * @throws ParseException
	 */
	public static String getDtStr(String strDt1, String strDt2, String granStr, boolean isBetween) throws ParseException {
		Date beginDt = toDate(strDt1);
		Date endDt = toDate(strDt2);
		Date tmpDate;
		int gran = NumberUtils.toInt(granStr, DAY);
		int granSize = sub(endDt, beginDt, gran);

		if (granSize < 0) {
			granSize *= -1;
			tmpDate = beginDt;
			beginDt = endDt;
			endDt = tmpDate;
		}

		StringBuffer result = new StringBuffer(granSize * 11);

		for (int i = 0; i < granSize; i++) {
			result.append(",").append(toStrDate(add(beginDt, gran, i)));

		}
		// 判断是否加最后一个日期
		tmpDate = add(beginDt, gran, granSize);
		if (comp(tmpDate, endDt, DAY) == 0 || !isBetween) {
			result.append(",").append(toStrDate(tmpDate));
		}

		return (result.substring(1)).toString();
	}

	/**
	 * 判断指定日期是否指定粒度末日期
	 * 
	 * @param dt
	 * @param gran
	 * @return ture
	 */
	public static boolean isEnd(Date dt, int gran) {
		boolean result = false;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dt);
		switch (gran) {
		case DAY:
			result = true;
			break;
		case WEEK:
			result = (Calendar.SUNDAY == gc.get(Calendar.DAY_OF_WEEK));
			break;
		case TENDAYS:
			int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
			if (10 == dayOfMonth || 20 == dayOfMonth || gc.getActualMaximum(Calendar.DAY_OF_MONTH) == dayOfMonth) {
				result = true;
			}
			break;
		case MONTH:
			result = gc.getActualMaximum(Calendar.DAY_OF_MONTH) == gc.get(Calendar.DAY_OF_MONTH);
			break;
		case SEASON:
			if ((gc.get(Calendar.MONTH) + 1) % 3 == 0 && gc.getActualMaximum(Calendar.DAY_OF_MONTH) == gc.get(Calendar.DAY_OF_MONTH)) {
				result = true;
			}
			break;
		case HALFYEAR:
			if ((gc.get(Calendar.MONTH) + 1) % 6 == 0 && gc.getActualMaximum(Calendar.DAY_OF_MONTH) == gc.get(Calendar.DAY_OF_MONTH)) {
				result = true;
			}
			break;
		case YEAR:
			if (gc.get(Calendar.MONTH) == 11 && gc.getActualMaximum(Calendar.DAY_OF_MONTH) == gc.get(Calendar.DAY_OF_MONTH)) {
				result = true;
			}

			break;
		default:
			break;
		}
		return result;

	}

	/**
	 * 判断给定字符串日期是否指定粒度末日期
	 * 
	 * @param dtStr
	 * @param gran
	 * @return
	 * @throws ParseException
	 */
	public static boolean isEnd(String dtStr, int gran) throws ParseException {
		return isEnd(toDate(dtStr), gran);
	}

	/**
	 * 判断在指定粒度里指定日期1是否晚于指定日期2
	 * 
	 * @param dt1
	 * @param dt2
	 * @param gran
	 * @return
	 */
	public static int comp(Date dt1, Date dt2, int gran) {
		Date tmpDt1 = getEndDt(dt1, gran);
		Date tmpDt2 = getEndDt(dt2, gran);
		long result = tmpDt1.getTime() - tmpDt2.getTime();

		return result > 0 ? 1 : result < 0 ? -1 : 0;
	}

	/**
	 * 按照指定的粒度比较字符串日期。
	 * 
	 * <h5>Example</h5>
	 * 
	 * <pre>
	 *          AmlDtUtils.comp(&quot;20060101&quot;, &quot;2007-01-01&quot;, AmlDtUtils.DAY)   = -1
	 *          AmlDtUtils.comp(&quot;20060101&quot;, &quot;2007-01-01&quot;, AmlDtUtils.MONTH) = -1
	 *          AmlDtUtils.comp(&quot;20060101&quot;, &quot;2006-11-01&quot;, AmlDtUtils.YEAR)  = 0 
	 *          AmlDtUtils.comp(&quot;20060101&quot;, &quot;2005-11-01&quot;, AmlDtUtils.YEAR)  = 1
	 * </pre>
	 * 
	 * @param dtStr1
	 * @param dtStr2
	 * @param gran
	 * @return dtStr1 晚于dtStr2：1，相等：0 ，否则：-1
	 * @throws ParseException
	 */
	public static int comp(String dtStr1, String dtStr2, int gran) throws ParseException {
		return comp(toDate(dtStr1), toDate(dtStr2), gran);
	}

	/**
	 * 计算指定粒度及期数后的日期
	 * 
	 * @param dt
	 *            计算起始日期
	 * @param gran
	 *            粒度
	 * @param amount
	 *            期数
	 * @param isChgEnd
	 *            是否转换为粒度末日期 true 转换 false 不转换
	 * @return 结果日期
	 */
	public static Date add(Date dt, int gran, int amount, boolean isChgEnd) {
		Date result;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dt);
		switch (gran) {
		case DAY:
			gc.add(Calendar.DATE, amount);
			break;
		case WEEK:
			gc.add(Calendar.DATE, amount * 7);
			break;
		case TENDAYS:
			int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
			int dayOfTenDays = dayOfMonth < 11 ? dayOfMonth : dayOfMonth < 21 ? dayOfMonth - 10 : dayOfMonth - 20;
			int tenDaysOfMonth = dayOfMonth < 11 ? 0 : dayOfMonth < 21 ? 1 : 2;

			// 计算调整月数及旬数
			int modiMonths = amount / 3;
			int modiTenDays = amount % 3;
			if (amount < 0) {
				modiMonths -= 1;
				modiTenDays += 3;
			}
			modiTenDays += tenDaysOfMonth;
			gc.add(Calendar.MONDAY, modiMonths + modiTenDays / 3);

			// 计算调整后天数
			int factDay = dayOfTenDays + modiTenDays % 3 * 10;
			switch (modiTenDays % 3) {
			case 0:
				dayOfMonth = factDay < 10 ? factDay : 10;
				break;
			case 1:
				dayOfMonth = factDay < 20 ? factDay : 20;
				break;
			default:
				int maxDay = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
				dayOfMonth = factDay < maxDay ? factDay : maxDay;
				break;
			}
			gc.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			break;
		case MONTH:
			gc.add(Calendar.MONTH, amount);
			break;
		case SEASON:
			gc.add(Calendar.MONTH, amount * 3);
			break;
		case HALFYEAR:
			gc.add(Calendar.MONTH, amount * 6);
			break;
		case YEAR:
			gc.add(Calendar.YEAR, amount);
			break;
		default:
			break;
		}
		if (isChgEnd) {
			result = getEndDt(gc.getTime(), gran);
		} else {
			result = gc.getTime();
		}

		return result;
	}

	/**
	 * 计算指定日期指定粒度后的日期，日期将转换为粒度末日期
	 * 
	 * @param dt
	 * @param gran
	 * @param amount
	 * @return 结果日期
	 */
	public static Date add(Date dt, int gran, int amount) {
		return add(dt, gran, amount, true);
	}

	/**
	 * 计算指定字符串日期n期后日期
	 * 
	 * @param dtStr
	 * @param gran
	 * @param amount
	 * @param isChgEnds
	 *            是否转换为期末日期 true 转换 false 不转换
	 * @return
	 * @throws ParseException
	 */
	public static String add(String dtStr, int gran, int amount, boolean isChgEnds) throws ParseException {
		return toStrDate(add(toDate(dtStr), gran, amount, isChgEnds));
	}

	/**
	 * 计算指定字符串日期n期后期末日期
	 * 
	 * @param dtStr
	 * @param gran
	 * @param amount
	 * @return
	 * @throws ParseException
	 */
	public static String add(String dtStr, int gran, int amount) throws ParseException {
		return add(dtStr, gran, amount, true);
	}

	/**
	 * 计算指定日期指定粒度的相差期数
	 * 
	 * @param dt1
	 * @param dt2
	 * @param gran
	 * @return
	 */
	public static int sub(Date dt1, Date dt2, int gran) {
		GregorianCalendar gc1 = new GregorianCalendar();
		GregorianCalendar gc2 = new GregorianCalendar();
		Date tmpDt1 = getEndDt(dt1, gran);
		Date tmpDt2 = getEndDt(dt2, gran);
		gc1.setTime(tmpDt1);
		gc2.setTime(tmpDt2);
		int result = 0;

		switch (gran) {
		case DAY:
			result = (int)( (tmpDt1.getTime() - tmpDt2.getTime()) / (24 * 60 * 60 * 1000));
			break;
		case WEEK:
			result = (gc1.get(Calendar.YEAR) - gc2.get(Calendar.YEAR)) * 52 + gc1.get(Calendar.WEEK_OF_YEAR) - gc2.get(Calendar.WEEK_OF_YEAR);
			break;
		case TENDAYS:
			result = (gc1.get(Calendar.YEAR) - gc2.get(Calendar.YEAR)) * 36;
			result += (gc1.get(Calendar.MONTH) - gc2.get(Calendar.MONTH)) * 3;
			int dayOfMon1 = gc1.get(Calendar.DAY_OF_MONTH);
			int dayOfMon2 = gc2.get(Calendar.DAY_OF_MONTH);
			int tenDaysOfMon1 = dayOfMon1 < 11 ? 1 : dayOfMon1 < 21 ? 2 : 3;
			int tenDaysOfMon2 = dayOfMon2 < 11 ? 1 : dayOfMon2 < 21 ? 2 : 3;
			result += (tenDaysOfMon1 - tenDaysOfMon2);
			break;
		case MONTH:
			result = (gc1.get(Calendar.YEAR) - gc2.get(Calendar.YEAR)) * 12 + gc1.get(Calendar.MONTH) - gc2.get(Calendar.MONTH);
			break;
		case SEASON:
			result = (gc1.get(Calendar.YEAR) - gc2.get(Calendar.YEAR)) * 4 + (gc1.get(Calendar.MONTH) - gc2.get(Calendar.MONTH)) / 3;
			break;
		case HALFYEAR:
			result = (gc1.get(Calendar.YEAR) - gc2.get(Calendar.YEAR)) * 2 + (gc1.get(Calendar.MONTH) - gc2.get(Calendar.MONTH)) / 6;
			break;
		case YEAR:
			result = (gc1.get(Calendar.YEAR) - gc2.get(Calendar.YEAR));
			break;

		default:
			break;
		}

		return result;
	}

	/**
	 * 计算指定字符串日期指定粒度相差的期数,与参数顺序有关。
	 * 
	 * <h5>Example</h5>
	 * 
	 * <pre>
	 *         AmlDtUtils.sub(&quot;20060227&quot;, &quot;20060301&quot;, AmlDtUtils.DAY)       = -2
	 *         AmlDtUtils.sub(&quot;20060227&quot;, &quot;20060301&quot;, AmlDtUtils.WEEK)      =  0
	 *         AmlDtUtils.sub(&quot;20060226&quot;, &quot;20060301&quot;, AmlDtUtils.WEEK)      = -1
	 *         AmlDtUtils.sub(&quot;20060226&quot;, &quot;20060301&quot;, AmlDtUtils.TENDAYS)   = -1
	 *         AmlDtUtils.sub(&quot;20060126&quot;, &quot;20060301&quot;, AmlDtUtils.TENDAYS)   = -4
	 *         AmlDtUtils.sub(&quot;20061226&quot;, &quot;20070201&quot;, AmlDtUtils.MONTH)     = -2
	 *         AmlDtUtils.sub(&quot;20061226&quot;, &quot;20070201&quot;, AmlDtUtils.SEASON)    = -1
	 *         AmlDtUtils.sub(&quot;20061226&quot;, &quot;20070201&quot;, AmlDtUtils.HALFYEAR)  = -1
	 *         AmlDtUtils.sub(&quot;20061226&quot;, &quot;20070201&quot;, AmlDtUtils.YEAR)      = -1
	 *         AmlDtUtils.sub(AmlDtUtils.getCurrDt(),&quot;&quot;,AmlDtUtils.DAY)        = 0
	 * </pre>
	 * 
	 * @param dtStr1
	 * @param dtStr2
	 * @param gran
	 * @return 相差期数
	 * @throws ParseException
	 */
	public static int sub(String dtStr1, String dtStr2, int gran) throws ParseException {
		return sub(toDate(dtStr1), toDate(dtStr2), gran);
	}

	/*
	 * @param currentTime 计算的日期
	 * 
	 * @param type 偏移的类别
	 * 
	 * @param iQuantity 偏移数量
	 * 
	 * @return 偏移后的时间
	 */
	public static Date getDateChangeTime(Date currentTime, String type, int iQuantity) {
		int year = Integer.parseInt(FormatDate(currentTime, "yyyy"));
		int month = Integer.parseInt(FormatDate(currentTime, "MM"));
		// 月份修正,必须在日期变更前修改正月份，否则小月份都会产成问题
		month = month - 1;
		int day = Integer.parseInt(FormatDate(currentTime, "dd"));
		int hour = Integer.parseInt(FormatDate(currentTime, "HH"));
		int mi = Integer.parseInt(FormatDate(currentTime, "mm"));
		int ss = Integer.parseInt(FormatDate(currentTime, "ss"));
		GregorianCalendar gc = new GregorianCalendar(year, month, day, hour, mi, ss);

		// 月份修正,必须在日期变更前修改正月份，否则小月份都会产成问题

		if (type.equalsIgnoreCase("y")) {
			gc.add(GregorianCalendar.YEAR, iQuantity);
		} else if (type.equalsIgnoreCase("m")) {
			gc.add(GregorianCalendar.MONTH, iQuantity);
		} else if (type.equalsIgnoreCase("d")) {
			gc.add(GregorianCalendar.DATE, iQuantity);
		} else if (type.equalsIgnoreCase("h")) {
			gc.add(GregorianCalendar.HOUR, iQuantity);
		} else if (type.equalsIgnoreCase("mi")) {
			gc.add(GregorianCalendar.MINUTE, iQuantity);
		} else if (type.equalsIgnoreCase("s")) {
			gc.add(GregorianCalendar.SECOND, iQuantity);
		}

		return gc.getTime();
	}

	/**
	 *
	 * @param currentTime
	 *            计算的日期
	 * @param type
	 *            偏移的类别
	 * @param iQuantity
	 *            偏移数量
	 * @return 偏移后的时间串
	 */
	public Date getDateChangeTime(String currentTime, String type, int iQuantity) throws Exception {
		Date curr = this.toDate(currentTime);
		curr = this.getDateChangeTime(curr, type, iQuantity);
		return curr;
	}

	/**
	 * 取月末时间
	 * 
	 * @param date
	 *            日期
	 * @return date
	 */
	public String getMonthEnd(Date date) {
		int year = Integer.parseInt(this.FormatDate(date, "yyyy"));
		int month = Integer.parseInt(this.FormatDate(date, "MM"));
		int day = Integer.parseInt(this.FormatDate(date, "dd"));

		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day, 0, 0, 0);
		int monthLength = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
		String newDateStr = this.FormatDate(date, "yyyy") + "-" + this.FormatDate(date, "MM") + "-";
		if (monthLength < 10)
			newDateStr += "0" + monthLength;
		else
			newDateStr += "" + monthLength;
		return newDateStr;
	}

	public String getDateChangeALL(String currentTime, String type, int iQuantity) throws ParseException {
		Date curr = null;
		String newtype = "";
		if (currentTime.length() == 10) {
			curr = this.toDate(currentTime);
		}
		if (currentTime.length() > 10) {
			curr = this.toDate(currentTime);
		}

		// 日
		if (type.equals("1")) {
			iQuantity = iQuantity;
			newtype = "d";
		}
		// 周，按照7天计算
		else if (type.equals("2")) {
			iQuantity = iQuantity * 7;
			newtype = "d";
		}
		// 旬，按照10天计算
		else if (type.equals("3")) {
			iQuantity = iQuantity * 10;
			newtype = "d";
			String month = this.getMonth(curr);
			if (month.equals("02") || month.equals("2")) {
				String day = this.getDay(curr);
				if (day.equals("20") && iQuantity > 0) {
					iQuantity = 8;
				}
			}
		}
		// 月
		else if (type.equals("4")) {
			iQuantity = iQuantity;
			newtype = "m";
		}
		// 季，按照3个月计算
		else if (type.equals("5")) {
			iQuantity = iQuantity * 3;
			newtype = "m";
		}
		// 半年，按照六个月计算
		else if (type.equals("6")) {
			iQuantity = iQuantity * 6;
			newtype = "m";
		}
		// 年
		else if (type.equals("7")) {
			newtype = "y";
		}
		// 两年
		else if (type.equals("8")) {
			iQuantity = iQuantity * 2;
			newtype = "y";
		}
		// 三年
		else if (type.equals("9")) {
			iQuantity = iQuantity * 3;
			newtype = "y";
		} else {
			iQuantity = iQuantity;
			newtype = "d";
		}

		Date change = this.getDateChangeTime(curr, newtype, iQuantity);

		// if(!type.equals("d")){
		// change = this.getMonthEnd(change);
		// }

		return this.toStrDate(change) ;
	}
	public String getPreEndDate(String granularity, String statisticDate) throws ParseException
	  {
	    Date date = this.toDate(statisticDate);
	    String year = ""; String month = "01"; String day = "01";
	    year = getYear(date);
	    month = getMonth(date);
	    day = getDay(date);

	    int preM = Integer.parseInt(month) - 1;
	    if (preM == 0) {
	      preM = 12;
	    }
	    String newdate=this.getDateChangeALL(statisticDate, granularity, -1);
	   // return getEndDate(granularity, dateToStringShort(stringToDateShort(year + "-" + preM + "-" + day)));
	    return this.getEndDt(newdate,Integer.valueOf(granularity));
	  }
	public String getNextEndDate(String granularity, String statisticDate,int index) throws ParseException
	  {
	    String newdate=this.getDateChangeALL(statisticDate, granularity, index);
	   // return getEndDate(granularity, dateToStringShort(stringToDateShort(year + "-" + preM + "-" + day)));
	    return  this.getEndDt(newdate,Integer.valueOf(granularity));
	  }
 
	public    String getDateChangeTime2(String currentTime, String type,
			int iQuantity) throws ParseException {
		Date curr = this.toDate(currentTime);
		Date curr2 = getDateChangeTime(curr, type, iQuantity);
		return this.toStrDate(curr2);
	}
}
