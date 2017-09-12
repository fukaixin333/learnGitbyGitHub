package com.citic.server.runtime;

import java.math.BigDecimal;

/**
 * @author liuxuanfei
 * @date 2016年12月9日 下午5:05:41
 */
public class FormatTools implements UtilityConstants {
	
	/**
	 * 整数金额除以100，并精确到分（如果是小数，则不做处理）
	 * <p>
	 * 广发银行核心接口返回的金额精确到分，但不带小数点，所以需要除以100，并保留两位小数。
	 * 
	 * @param sum
	 * @return
	 */
	public static String precisionOfCent(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}
		
		String tm = str.trim();
		final int len = tm.length();
		char[] digit = new char[len + 1];
		int index = 0;
		digit[index++] = '+';
		for (int i = 0; i < len; i++) {
			char chr = tm.charAt(i);
			if (Character.isDigit(chr)) {
				digit[index++] = chr;
			} else if (i == len - 1) {
				switch (chr) {
				case '}':
					digit[0] = '-';
					digit[index++] = '0';
					break;
				case 'J':
				case 'K':
				case 'L':
				case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
				case 'R':
					digit[0] = '-';
					digit[index++] = (char) (chr - 25); //
					break;
				default:
					return str;
				}
			} else {
				return str;
			}
		}
		
		BigDecimal percent = new BigDecimal(100);
		BigDecimal decimal = new BigDecimal(new String(digit, 0, index)).divide(percent, 2, BigDecimal.ROUND_DOWN);
		return decimal.toString();
	}
}
