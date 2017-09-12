/**
 * 常用字符工具类
 */
package com.citic.server.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.collect.Lists;

/**
 * 一般情况下，请尽量使用apache或Guava提供的字符处理工具，本类的方法，主要是用于处理单字节字符
 * 
 * @author gaosen
 *
 */
public class StrUtils {

	/**
	 * 判断给定字符串的字节长度
	 * 
	 * @param s
	 * @return
	 */
	public static int length(String s) {
		int len = 0;
		if (StringUtils.isNotEmpty(s)) {
			try {
				len = s.getBytes("GBK").length;
			} catch (UnsupportedEncodingException e) {
			}
		}
		return len;
	}


	/**
	 * 功能:字符串替换函数
	 * @param s
	 * @return
	 */
	public static String strReplace(String s, String sourceStr,
			String replaceStr) {
		if (s == null || s.length() == 0 || sourceStr == null
				|| sourceStr.length() == 0 || replaceStr == null)
			return s;
		int index = 0, startIndex = 0;
		int length = sourceStr.length();
		int length1 = replaceStr.length();
		StringBuffer bf = new StringBuffer(s);

		while (!((index = bf.indexOf(sourceStr, startIndex)) < 0)) {
			startIndex = index - length + length1;
			bf.replace(index, index + length, replaceStr);
		}
		return bf.toString();
	}

	/**
	 * 按照字节长度对字符串进行分割
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String[] Split(String str, int length) {
		int count = 0;
		int offset = 0;
		int last_count = 0;
		List<String> list = Lists.newArrayList();
		char[] c = str.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (isChinese(c[i])) {
				offset = 2;
				count += 2;
			} else {
				offset = 1;
				count++;
			}

			if (count == length) {
				list.add(str.substring(last_count, i + 1));
				last_count = i+1;
				count = 0;
			}

			if ((count == length + 1 && offset == 2)) {
				list.add(str.substring(last_count, i));
				last_count = i;
				count = 2;
			}
		}
		if (count > 0) {
			list.add(str.substring(last_count, c.length));
		}

		if (list.size() > 0) {
			return (String[]) list.toArray(new String[list.size()]);
		} else {
			return new String[] {};
		}
	}

    /**
	 * 判断字符是否中文
	 * 
	 * @param a
	 * @return
	 */
	public static boolean isChinese(char a) {
		int v = (int) a;
		return (v >= 19968 && v <= 171941);
	}

	
	public static String[] split(String s, String delim) {
		if (s == null || s.length() == 0 || delim == null
				|| delim.length() == 0) {
			return null;
		}
		ArrayList list = new ArrayList();
		int index = 0, startIndex = 0, endIndex = 0;
		int length1 = delim.length();

		while (!((index = s.indexOf(delim, startIndex)) < 0)) {
			endIndex = index;
			list.add(s.substring(startIndex, endIndex));
			startIndex = index + length1;
		}

		//加上最后一个数据
		list.add(s.substring(startIndex));

		String[] a = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			a[i] = (String) list.get(i);
		}

		return a;
	}

	/**
	 * 按照字节截取指定长度的字符串，不考虑字符中间存在汉字乱码的情况
	 * 
	 * @param s
	 * @param begin
	 *            从1开始
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String subStr(String s, int begin, int length) {
		if (StringUtils.isEmpty(s)) {
			return "";
		}
		String tmpStr = "";
		int tmpLength = 0;

		try {
			byte[] bytes = s.getBytes("GBK");
			if (begin >= bytes.length) {
				return "";
			}

			if (begin + length - 1 > bytes.length) {
				tmpLength = bytes.length - begin + 1;
			} else {
				tmpLength = length;
			}
			tmpStr = new String(bytes, begin - 1, tmpLength, "GBK");
		} catch (UnsupportedEncodingException e) {

		}

		return tmpStr;
	}

	/**
	 * 按万元格式化数据项，保留四位小数
	 * 
	 * @param inStr
	 * @return
	 */
	public static String getTenThousand(String inStr) {
		return getFmtNum(inStr, 4, 0.0001, true);
	}

	/**
	 * 按万元格式化数据项
	 * 
	 * @param inStr
	 * @param decDig
	 *            小数位数
	 * @return
	 */
	public static String getTenThousand(String inStr, int decDig) {
		return getFmtNum(inStr, decDig, 0.0001, true);
	}

	/**
	 * 返回货币形式数值，保留2位小数
	 * 
	 * @param inStr
	 *            输入字符串
	 * @param decDig
	 *            小数位数
	 * @return 输入字符串为空 返回"",输入字符串非法，按0处理
	 */
	public static String getMoney(String inStr) {
		return getFmtNum(inStr, 2, 1, true);
	}

	/**
	 * 按百分比格式化输入值
	 * 
	 * @param inStr
	 * @return
	 */
	public static String getPercentum(String inStr) {
		String result = getFmtNum(inStr, 2, 100.00, false);

		if (result.equals("")) {
			return result;
		} else {
			return result + "%";
		}
	}

	/**
	 * 取格式化后的字符串，进行四舍五入处理。 <h5>Example</h5>
	 * 
	 * <pre>
	 *      StrUtils.getFmtNum(&quot;&quot;, 2, 1/100, true)=                    &quot;&quot;, 
	 *      StrUtils.getFmtNum(&quot;12344&quot;, 2, 0.001, true)=               &quot;12.34&quot;,                             
	 *      StrUtils.getFmtNum(&quot;12346&quot;, 2, 0.001, true)=               &quot;12.35&quot;,                             
	 *      StrUtils.getFmtNum(&quot;-12344&quot;, 2, 0.001, true)=              &quot;-12.34&quot;,                            
	 *      StrUtils.getFmtNum(&quot;-12345&quot;, 2, 0.001, true)=              &quot;-12.35&quot;,                            
	 *      StrUtils.getFmtNum(&quot;-1111234567890123&quot;, 2, 0.001, true)=  &quot;-1,111,234,567,890.12&quot;
	 *      StrUtils.getFmtNum(&quot;-.345&quot;, 2, 1, true) = &quot;-0.35&quot;
	 * </pre>
	 * 
	 * @param inStr
	 *            输入字符串
	 * @param decDig
	 *            小数位数
	 * @param mulNum
	 *            调整系数 如：元转换为万元 为 1/10000
	 * @param groupFlag
	 *            分组标志即是否加","分位符
	 * @return 输入字符串为空 返回"",输入字符串非法，按0处理
	 */
	public static String getFmtNum(String inStr, int decDig, double mulNum, boolean groupFlag) {
		if (StringUtils.isBlank(inStr)) {
			return "";
		}
		StringBuffer fmt = new StringBuffer(32);
		long adj = 1;
		int chgFlag = 1;

		if (groupFlag) {
			fmt.append(",");
		}
		fmt.append("##0");
		for (int i = 0; i < decDig; i++) {
			if (i == 0) {
				fmt.append(".");
			}
			fmt.append('0');
			adj *= 10;
		}
		java.text.DecimalFormat df = new DecimalFormat(fmt.toString());

		// 由于默认转换采用HALF_EVEN舍入，故调整为四舍五入方式
		double tmpResult = NumberUtils.toDouble(inStr);
		if (tmpResult < 0.00) {
			tmpResult *= -1;
			chgFlag = -1;
		}
		long tmpLong = (long) (tmpResult * (mulNum * adj) + 0.5);
		tmpResult = ((double) tmpLong) / adj * chgFlag;

		return df.format(tmpResult);
	}
	
	public static String nullToString(String str) {
		if (str == null)
			str = "";
		return str.trim();
	}
	
	/**
	 * 替换字符串中全部的特殊子串
	 * @param mainString 被字符串
	 * @param oldString 原有子串
	 * @param newString 新的子串
	 * @return 替换后字符串
	 */
	public static String replaceString(String mainString, String oldString,
			String newString) {
		if (mainString == null) {
			return null;
		}
		if (oldString == null || oldString.length() == 0) {
			return mainString;
		}
		if (newString == null) {
			newString = "";
		}
		int i = mainString.lastIndexOf(oldString);
		if (i < 0)
			return mainString;
		StringBuffer mainSb = new StringBuffer(mainString);
		while (i >= 0) {
			mainSb.replace(i, i + oldString.length(), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}
	public static String nullObject2String(Object s) {
		String str = "";
		try {
			str = s.toString();
		} catch (Exception e) {
			str = "";
		}
		return str;
	}
	public static String null2String(String str) {
		if (str == null)
			str = "";
		return str.trim();
	}
	public static int null2int(String s) {
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	
	public static String IsoToGBKTool(String strIn, String dbcode) {
		String strOut = null;
		if (strIn == null)
			return "";
		if (dbcode == null || dbcode.equals(""))
			return strIn;
		try {
			byte[] b = strIn.getBytes(dbcode);
			strOut = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
		}
		return strOut;
	}
	
	public static List splitStr(String str, String delim) {
		List splitList = null;
		StringTokenizer st = null;

		if (str == null)
			return splitList;

		if (delim != null)
			st = new StringTokenizer(str, delim);
		else
			st = new StringTokenizer(str);

		if (st != null && st.hasMoreTokens()) {
			splitList = new ArrayList();

			while (st.hasMoreTokens())
				splitList.add(st.nextToken());
		}
		return splitList;
	}
}
