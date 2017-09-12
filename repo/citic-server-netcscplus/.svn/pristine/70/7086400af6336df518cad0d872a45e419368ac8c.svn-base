package com.citic.server.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author
 * @version
 */
public class StrUtils {
	
	// Constants used by escapeHTMLTags
	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();
	
	//取得随机数用
	private static Random randGen = new Random();
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	
	/**
	 * 说明: 将字符串变量由null转换为""串
	 * 
	 * @param str 需要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String null2String(String str) {
		if (str == null)
			str = "";
		return str.trim();
	}
	
	public static String empty2null(String str) {
		if (str != null) {
			if (str.trim().equals("")) {
				str = null;
			}
		}
		return str;
	}
	
	public static double null2double(String s) {
		double d = 0.0;
		try {
			d = Double.parseDouble(s);
		} catch (Exception e) {
			d = 0.0;
		}
		return d;
		
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
	
	public static Integer null2Integer(String s) {
		Integer i = new Integer(0);
		try {
			i = new Integer(Integer.parseInt(s));
			
		} catch (Exception e) {
			i = new Integer(0);
		}
		return i;
	}
	
	public static long null2Long(String s) {
		long i = 0;
		try {
			i = Long.parseLong(s);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	
	//转换成整型
	public static String changeIntoInt(String s) {
		if (s == null || s.length() == 0)
			return "0";
		else {
			String i = getFormatStr(s, 0);
			//System.out.println("i="+i.substring(0,i.length()-2));
			// return i.substring(0,i.length()-2);
			/** modify for bigdecimal */
			return i;
		}
	}
	
	//转换成字符型
	public static String changeIntoZifu(String s) {
		if (s == null || s.equals("") || s.equals("null"))
			s = "";
		return s;
	}
	
	//转换成浮点型
	public static String changeIntoFudian(String str, String s) {
		if (str == null || str.length() == 0)
			str = "0";
		
		int num = Integer.parseInt(s);
		return getFormatStr(str, num);
		
	}
	
	//字符转换函数
	//input: 对象
	//output:如果字符串为null,返回为空,否则返回该字符串
	public static String nullObject2String(Object s) {
		String str = "";
		try {
			str = s.toString();
		} catch (Exception e) {
			str = "";
		}
		return str;
	}
	
	public static String nullObject2String(Object s, String chr) {
		String str = chr;
		try {
			str = s.toString();
		} catch (Exception e) {
			str = chr;
		}
		return str;
	}
	
	/**
	 * 如果传入对象为空或无法转为数字就返回0
	 * 
	 * @param s
	 * @return
	 */
	public static int nullObject2int(Object s) {
		String str = "";
		int i = 0;
		try {
			str = s.toString();
			i = Integer.parseInt(str);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	
	/**
	 * 如果传入对象为空或无法转为数字就返回默认值
	 * 
	 * @param s
	 * @return
	 */
	public static int nullObject2int(Object s, int in) {
		String str = "";
		int i = in;
		try {
			str = s.toString();
			i = Integer.parseInt(str);
		} catch (Exception e) {
			i = in;
		}
		return i;
	}
	
	/**
	 * 字符串的首字母大写
	 * 
	 * @param string
	 * @return
	 */
	public static String firstToUpperCase(String string) {
		String post = string.substring(1, string.length());
		String first = ("" + string.charAt(0)).toUpperCase();
		return first + post;
	}
	
	public static ArrayList TokenizerString(String str, String dim) {
		return TokenizerString(str, dim, false);
	}
	
	/******
	 * 将输入的字符串str按照分割符dim分割成字符串数组并返回ArrayList字符串数组********
	 * If the returndim flag is true, then the dim characters are also returned as tokens.
	 * Each delimiter is returned as a string of length one. If the flag is false,
	 * the delimiter characters are skipped and only serve as separators between tokens.
	 **************************************************************************/
	public static ArrayList TokenizerString(String str, String dim, boolean returndim) {
		str = null2String(str);
		dim = null2String(dim);
		ArrayList strlist = new ArrayList();
		StringTokenizer strtoken = new StringTokenizer(str, dim, returndim);
		while (strtoken.hasMoreTokens()) {
			strlist.add(strtoken.nextToken());
		}
		return strlist;
	}
	
	/******
	 * 类似上面的方法,将输入的字符串str按照分割符dim分割成字符串数组,******************
	 * 并返回定长字符串数组
	 **************************************************************/
	
	public static String[] TokenizerString2(String str, String dim) {
		return TokenizerString2(str, dim, false);
	}
	
	public static String[] TokenizerString2(String str, String dim, boolean returndim) {
		ArrayList strlist = TokenizerString(str, dim, returndim);
		int strcount = strlist.size();
		String[] strarray = new String[strcount];
		for (int i = 0; i < strcount; i++) {
			strarray[i] = (String) strlist.get(i);
		}
		return strarray;
	}
	
	/**
	 * 取得一个随机字符串,包含数字和字符
	 * 
	 * @param length 字符串长度
	 * @return 随机字符串
	 */
	public static final String getRandomString(int length) {
		if (length < 1) {
			return null;
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}
	
	/**
	 * ISO_8559_1(或别的字符集)字符转换为GBK字符工具
	 * 
	 * @param strIn 需要转换的字符
	 * @return 转换后的GBK字符
	 */
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
	
	/**
	 * GBK字符转换为ISO_8559_1(或别的字符集)字符工具
	 * 
	 * @param strIn 需要转换的GBK字符
	 * @return 转换后的ISO_8559_1字符
	 */
	public static String GBKToIsoTool(String strIn, String dbcode) {
		byte[] b;
		String strOut = null;
		if (strIn == null)
			return "";
		if (dbcode == null || dbcode.equals(""))
			return strIn;
		try {
			b = strIn.getBytes("GBK");
			strOut = new String(b, dbcode);
		} catch (UnsupportedEncodingException e) {
		}
		return strOut;
	}
	
	/**
	 * 说明: GBK字符转换为UTF-8字符
	 * 
	 * @param strIn 需要转换的GBK字符
	 * @return 转换后的字符
	 */
	public static String strToUTF(String str) {
		if (str == null || "".equals(str)) {
			return str;
		}
		String retVal = str;
		try {
			//retVal = new String(str.getBytes(""), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * 替换字符串中全部的特殊子串
	 * 
	 * @param mainString 被字符串
	 * @param oldString 原有子串
	 * @param newString 新的子串
	 * @return 替换后字符串
	 */
	public static String replaceString(String mainString, String oldString, String newString) {
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
	
	/**
	 * 替换字符串中全部的特殊子串（忽略大小写）
	 * 
	 * @param line 被字符串
	 * @param oldString 原有子串
	 * @param newString 新的子串
	 * @return 替换后字符串
	 */
	public static final String replaceIgnoreCase(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}
	
	/**
	 * 替换字符串中全部的特殊子串，同时，记录替换得个数
	 * 
	 * @param line 被字符串
	 * @param oldString 原有子串
	 * @param newString 新的子串
	 * @param count 替换个数
	 * @return line 替换后字符串
	 */
	public static final String replace(String line, String oldString, String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}
	
	/**
	 * 替换字符串中全部的特殊子串（忽略大小写），同时，记录替换得个数
	 * 
	 * @param line 被字符串
	 * @param oldString 原有子串
	 * @param newString 新的子串
	 * @param count 替换个数
	 * @return 替换后字符串
	 */
	public static final String replaceIgnoreCase(String line, String oldString, String newString, int[] count) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 0;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}
	
	/**
	 * 对字符串进行分割
	 * 
	 * @param line 源字符串
	 * @param newString 分割字符串
	 * @return 字符串数组
	 */
	public static String[] split(String line, String newString) {
		int begin = 0;
		int end = 0;
		ArrayList strList = new ArrayList();
		if (line == null) {
			return null;
		}
		if (newString == "") {
			int i;
			for (i = 0; i < line.length(); i++) {
				strList.add(line.substring(i, i + 1));
			}
			return (String[]) strList.toArray(new String[strList.size()]);
		}
		
		end = line.indexOf(newString);
		if (end == -1) {
			strList.add(line);
			return (String[]) strList.toArray(new String[strList.size()]);
		} else {
			while (end >= 0) {
				strList.add(line.substring(begin, end));
				begin = end + newString.length();
				end = line.indexOf(newString, begin);
			}
			strList.add(line.substring(begin, line.length()));
			return (String[]) strList.toArray(new String[strList.size()]);
		}
	}
	
	/**
	 * 合并被切分的字符串
	 * 
	 * @param list 子串集合
	 * @param delim 合并分割符
	 * @return 合并字符串
	 */
	public static String joinStr(List list, String delim) {
		if (list == null || list.size() < 1)
			return null;
		StringBuffer buf = new StringBuffer();
		Iterator i = list.iterator();
		while (i.hasNext()) {
			buf.append((String) i.next());
			if (i.hasNext())
				buf.append(delim);
		}
		return buf.toString();
	}
	
	/**
	 * 切分字符串
	 * 
	 * @param str 源字符串
	 * @param delim 切分标志
	 * @return 子串集合
	 */
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
	
	/**
	 * 二进制数转化成十进制数,目前只支持正数
	 * 
	 * @param str 一个由0，1组成的字符串
	 * @return 转化后的十进制数值,若返回-1则表明转化失败，有可能是输入字符串
	 *         不合法
	 */
	public static int binsToDecs(String str) {
		int ret = 0;
		int v = 1;
		for (int i = 0; i < str.length(); i++) {
			if (i != 0)
				v = v * 2;
			if (str.charAt(i) == '0')
				continue;
			else if (str.charAt(i) == '1')
				ret = ret + v;
			else
				return -1;
		}
		return ret;
	}
	
	/**
	 * 过滤HTML标签
	 * 
	 * @param in 源字符串
	 * @return 替换后字符串
	 */
	public static final String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}
	
	/**
	 * Escapes all necessary characters in the String so that it can be used
	 * in an XML doc.
	 *
	 * @param string the string to escape.
	 * @return the string with appropriate characters escaped.
	 */
	public static final String escapeForXML(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}
	
	/**
	 * Unescapes the String by converting XML escape sequences back into normal
	 * characters.
	 *
	 * @param string the string to unescape.
	 * @return the string with appropriate characters unescaped.
	 */
	public static final String unescapeFromXML(String string) {
		string = replaceString(string, "&lt;", "<");
		string = replaceString(string, "&gt;", ">");
		string = replaceString(string, "&quot;", "\"");
		return replaceString(string, "&amp;", "&");
	}
	
	/**
	 * 转还文本的回车为HTML回车
	 * 
	 * @param str 源串
	 * @return 替换后字符串
	 */
	public static String encodeCR(String str) {
		str = replaceString(str, "\r\n", "\n");
		str = replaceString(str, "\n", "<BR />");
		return str;
	}
	
	public static String encodeCRWithPram(String str) {
		str = replaceString(str, "　", "");
		str = replaceString(str, "&", "&amp;");
		str = replaceString(str, "<", "&lt;");
		str = replaceString(str, ">", "&gt;");
		str = replaceString(str, "\r\n", "\n");
		str = replaceString(str, "\n", "<BR/>　　");
		str = "　　" + str;
		return str;
	}
	
	/**
	 * 转化HTML回车文本回车
	 * 
	 * @param str 源串
	 * @return 替换后字符串
	 */
	public static String decodeCR(String str) {
		str = replaceString(str, "<BR />", "\r\n");
		return str;
	}
	
	/**
	 * 对字符串进行截取
	 * 
	 * @param StrCmd 要截取的字符串
	 * @param subnum 要保留的字符数
	 * @return 返回值为截取后的字符串
	 */
	public static String subStr(String StrCmd, int subnum) {
		String tempSub = "";
		if (subnum <= 0) {
			subnum = 5;
		}
		for (int i = 0; i < StrCmd.length(); i++) {
			String tmpstr = StrCmd.substring(i, i + 1);
			int codenum = tmpstr.hashCode();
			if (codenum >= 128) {
				subnum = subnum - 2;
			} else {
				subnum = subnum - 1;
			}
			tempSub += tmpstr;
			if (subnum <= 0) {
				tempSub += "...";
				break;
			}
		}
		return tempSub;
	}
	
	/**
	 * 得到两个数之间的一个随机数
	 * 
	 * @param iLower 最小值
	 * @param iUpper 最大值
	 * @return 随机数
	 */
	public String getRandom(int iLower, int iUpper) {
		int iRandom = 0;
		Random random = new Random();
		float fRandom = random.nextFloat();
		iRandom = iLower + (int) ((iUpper - iLower) * fRandom);
		String strRandom = String.valueOf(iRandom);
		return strRandom;
	}
	
	//根据参数
	//得到中文的空格数量
	public static String getChineseSpace(int num) {
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < num; i++) {
			bf.append("　");
		}
		return bf.toString();
	}
	
	public String strDecimalFormat(String strDecimal) {
		String sFormatDecimal = strDecimal;
		if (strDecimal.length() > 0) {
			if (strDecimal.substring(0, 1).equals(".")) {
				sFormatDecimal = "0" + strDecimal;
			}
		}
		if (strDecimal.length() > 1) {
			if (strDecimal.substring(0, 2).equals("-.")) {
				sFormatDecimal = "-0" + strDecimal.substring(1);
			}
		}
		return sFormatDecimal;
	}
	
	/**
	 * 格式化数字（字符串性质），保留小数点后num位。四舍五入
	 * 
	 * @param inStr String 小数（字符串性质）
	 * @param num int 小数点后位数
	 * @return String
	 */
	public static String getFormatStr(String inStr, int num) {
		
		long temp = 1;
		if (inStr == null || "".equals(inStr))
			inStr = "0";
		double inVal = Double.parseDouble(inStr);
		
		return roundString(inVal, num);
		
		/*
		 * String retVal = "";
		 * for (int i = num; i > 0; i--) {
		 * temp *= 10;
		 * }
		 * inVal *= temp;
		 * long dl = Math.round(inVal);
		 * retVal = numFormat((double) (dl) / (double)temp);
		 * if(retVal.indexOf('.')==-1 && num>0)
		 * retVal += ".0";
		 * int tempNum = retVal.substring(retVal.indexOf('.')+1).length();
		 * for(int i=0;i<num-tempNum;i++)
		 * retVal += "0";
		 * return retVal;
		 */
		
	}
	
	/**
	 * 主函数
	 * 
	 * @param args 测试参数
	 */
	public static void main(String[] args) {
		StrUtils sUtils = new StrUtils();
		
		String i = getFormatStr(".623641", 0);
		System.out.println("i=" + i.substring(0, i.length() - 2));
		
	}
	
	/**
	 * 字符串前导符( Escape: '\' ) 自动添加函数.
	 * <p>
	 * 对于通过 HTML &lt;TEXTAREA&gt; 输入的数据, 因为其中包含回车、换行等特殊字符, 在将这些变量传给 HTML 中的 javascript 变量时,这些字符串将导致
	 * javascript 程序出错, 因此需要对于包含这些特殊字符的字符串进行转换处理, 因为 javascript, 特殊字符转义前导符与 java/C 语言一致,
	 * 所以实际是在这些特殊字符中加入前导符 '\' .
	 *
	 * @param s 要输出或处理的字符串.
	 * @return 自动添加了前导符的字符串.
	 */
	public static String escape(String s) {
		try {
			int i = 0;
			char c;
			StringBuffer bf = new StringBuffer("");
			while (i < s.length()) {
				c = s.charAt(i);
				if (c == '\\')
					bf.append("\\\\");
				else if (c == '\r')
					bf.append("\\r");
				else if (c == '\n')
					bf.append("\\n");
				else if (c == '\t')
					bf.append("\\t");
				else if (c == '"')
					bf.append("\\\"");
				else
					bf.append(c);
				i++;
			}
			return (bf.toString());
		} catch (Exception e) {
			return (null);
		}
	}
	
	/**
	 * \u00B9\u00A6\u00C4\u00DC:×\u00D6·\u00FB\u00B2\u00B9×\u00E3".00"\u00BA\u00AF\u00CA\u00FD
	 * 
	 * @param s
	 *        ,\u00B4\u00AB\u00C8\u00EB\u00B5\u00C4\u00D0è\u00D2\u00AA\u00B2\u00B9×\u00E3×\u00D6·\u00FB
	 *        \u00B4\u00AE
	 * @param n\u00A3
	 *        \u00AC\u00D0è\u00D2\u00AA\u00B2\u00B9×\u00E3\u00B5\u00C4\u00CE\u00BB\u00CA\u00FD
	 * @return
	 */
	public static String numFull(String s2, int n) {
		String s = s2;
		if (s == null) {
			s = "0";
		}
		int index2 = s.indexOf(".");
		String s1 = "";
		if (index2 > 0) {
			s1 = s.substring(index2 + 1);
		} else if (n > 0) {
			s = s + ".";
		}
		for (int i = n - s1.length(); i > 0; i--) {
			s = s + "0";
		}
		return s;
	}
	
	//\u00B6\u00D4\u00CA\u00FD×\u00D6\u00B8\u00F1\u00CA\u00BD\u00BB\u00AF\u00CA\u00E4\u00B3\u00F6
	public static String numFormat(double n) {
		String s = "";
		
		//        System.out.println("old=="+n);
		
		s = java.text.DecimalFormat.getInstance().format(n);
		
		//         System.out.println("new=="+s);
		
		s = replaceString(s, ",", "");
		
		//         System.out.println("return string =="+s);
		
		return s;
	}
	
	public static String numFormat(long n) {
		String s = "";
		s = java.text.DecimalFormat.getInstance().format(n);
		s = replaceString(s, ",", "");
		return s;
	}
	
	public static String numFormat(int n) {
		String s = "";
		s = java.text.DecimalFormat.getInstance().format(n);
		s = replaceString(s, ",", "");
		return s;
	}
	
	public static String numFormat(float n) {
		String s = "";
		s = java.text.DecimalFormat.getInstance().format(n);
		s = replaceString(s, ",", "");
		return s;
	}
	
	public static String numFormat(String n) {
		String s = "";
		if (n == null || n.length() == 0)
			return "";
		else {
			try {
				s = java.text.DecimalFormat.getInstance().format(Double.parseDouble(n));
				s = replaceString(s, ",", "");
			} catch (Exception e) {
			}
		}
		return s;
	}
	
	/**
	 * 判断字符串是否为数据
	 * 
	 * @param str String
	 * @return boolean
	 */
	public static boolean isNumType(String str) {
		
		boolean retValue = true;
		if (str == null || str.length() <= 0)
			return false;
		for (int i = 0; i < str.length(); i++) {
			char t = str.charAt(i);
			if (t < '0' || t > '9') {
				if (t != '.' && t != '-') {
					retValue = false;
					break;
				}
			}
		}
		return retValue;
		
	}
	
	/**
	 * 说明: 将字符串变量由null转换为""串
	 * 
	 * @param str 需要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String nullToStr(String str) {
		if (str == null)
			str = "";
		return str.trim();
	}
	
	/**
	 * 双精度浮点数取小数点后若干位.
	 * <p>
	 * 取浮点数后若干位, 其余位数按四舍五入舍去.
	 *
	 * @param f 要处理的浮点数.
	 * @param n 小数点后要保留的小数位数.
	 * @return 进行小数点位数处理后的浮点数转换后的字符串.
	 */
	public static String roundString(double f, int n) {
		int r = 1;
		double f2;
		boolean fushuFlag = false;
		//如果是负数的话
		if (f < 0) {
			fushuFlag = true;
			f = -1 * f;
		}
		for (int i = 1; i <= n; i++)
			r = r * 10;
		f2 = ((double) Math.round(f * r));
		String s = java.text.DecimalFormat.getInstance().format(f2);
		s = replaceString(s, ",", "");
		
		if (n > 0) {
			if (s.length() <= n) {
				for (int i = s.length(); i <= n; i++) {
					s = "0" + s;
				}
			}
			s = s.substring(0, s.length() - n) + "." + s.substring(s.length() - n);
		}
		if (fushuFlag)
			s = "-" + s;
		return s;
	}
	
	/**
	 * 公式格式化
	 * 
	 * @param formulaStr
	 * @return
	 */
	public static String formulaFormat(String valueStr) {
		if (valueStr == null)
			valueStr = "NULL";
		valueStr = valueStr.replaceAll("null", "0");
		valueStr = valueStr.replaceAll("NULL", "0");
		
		if (valueStr.equals(""))
			valueStr = "0";
		if (valueStr.startsWith("-") || valueStr.startsWith("+"))
			valueStr = "(0" + valueStr + ")";
		
		return valueStr;
	}
	
	/**
	 * 将数字转换为字符，例如：1＝》A，2＝》B，27＝》AA，28＝》AB
	 * 目前：最大值到ZZ
	 * 
	 * @param numValue
	 * @return
	 */
	public static String num2Char(int numValue) {
		
		int fatherNum = 0;
		while (numValue > 26) {
			fatherNum++;
			numValue -= 26;
		}
		char numChar = (char) ('A' + numValue - 1);
		String retValue = String.valueOf(numChar);
		if (fatherNum > 0)
			retValue += String.valueOf((char) ('A' + fatherNum - 1));
		
		return retValue;
	}
	
	/**
	 * 取格式化后的字符串，进行四舍五入处理。
	 * 
	 * @param inStr
	 *        输入字符串
	 * @param decDig
	 *        小数位数
	 * @return 输入字符串为空 返回"",输入字符串非法，按0处理
	 */
	public static String getFmtNum(String inStr, int decDig) {
		return getFmtNum(inStr, decDig, 1, true);
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
	 *        输入字符串
	 * @param decDig
	 *        小数位数
	 * @param mulNum
	 *        调整系数 如：元转换为万元 为 1/10000
	 * @param groupFlag
	 *        分组标志即是否加","分位符
	 * @return 输入字符串为空 返回"",输入字符串非法，按0处理
	 */
	public static String getFmtNum(String inStr, int decDig, double mulNum, boolean groupFlag) {
		if (isEmpty(inStr)) {
			return "";
		}
		StringBuffer fmt = new StringBuffer(15);
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
		DecimalFormat df = new DecimalFormat(fmt.toString());
		
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
	
	/**
	 * 判断输入的字符是否为空. <h5>Example</h5>
	 * 
	 * <pre>
	 *  null = true;
	 *  &quot; \t \n&quot; = true;
	 *  &quot; a&quot;= false
	 * </pre>
	 * 
	 * @param inStr
	 * @return
	 */
	public final static boolean isEmpty(String inStr) {
		boolean result = true;
		int len;
		if (inStr == null || (len = inStr.length()) == 0) {
			return result;
		}
		
		for (int i = 0; i < len; i++) {
			if (inStr.charAt(i) != ' ' && inStr.charAt(i) != '\t' && inStr.charAt(i) != '\n') {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public final static String MoneyFormat(double dMoney, int n) {
		String fm = "###,##0";
		if (n > 0) {
			fm += ".";
		}
		for (int i = 0; i < n; i++) {
			fm += "0";
		}
		DecimalFormat myformat = new DecimalFormat(fm);
		String sMoney = myformat.format(dMoney);
		return sMoney;
	}
	
	/**
	 * 把文件路径中冒号，斜杆等去掉
	 * 
	 * @param filePath 文件路径
	 * @return String
	 */
	public static String handleFilePath(String filePath) {
		if (filePath == null)
			return filePath;
		
		filePath = replaceString(filePath, ":", "");
		filePath = replaceString(filePath, "\\", "");
		filePath = replaceString(filePath, "/", "");
		filePath = replaceString(filePath, ".", "");
		
		return filePath;
	}
	
	/**
	 * 清除掉所有特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String StringFilter(String str) {
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	public static String doubleToString(double d) {
		String i = DecimalFormat.getInstance().format(d);
		String result = i.replaceAll(",", "");
		return result;
	}
	
	public static String fillStr(int  d,String str) {
		
	String newreturnStr = "";
	for (int m = 0; m < (d- str.length()); m++) {
		newreturnStr += "0";
	}
	return  newreturnStr + str;
	}
	
	public static String StringDate19To14(String str) {
		str=str.replaceAll("-", "");
		str=str.replaceAll(" ", "");
		str=str.replaceAll(":", "");
		return str;
	}
}
