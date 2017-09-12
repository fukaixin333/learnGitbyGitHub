 
package com.citic.server.utils;

 
import java.text.DecimalFormat;

public class MonUtils {
	private String moneyFormat = ",##0.00";

	public MonUtils() {
	}

	/**
	 * 将钱格式化
	 * @param money 钱数
	 * @return 字符串
	 */
	public String FormatMoney(double money) {
		DecimalFormat df = new DecimalFormat(this.moneyFormat);
		return df.format(money);
	}

	/**
	 * 将钱格式化
	 * @param money 钱数
	 * @param format 格式化参数
	 * @return 字符串
	 */
	public String FormatMoney(double money, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(money);
	}

	/**
	 * 将钱格式化
	 * @param moneyStr  钱数
	 * @return
	 */
	public String FormatMoney(String moneyStr) {
		double money = 0;
		try {
			money = Double.parseDouble(moneyStr);
		} catch (Exception e) {
		}
		DecimalFormat df = new DecimalFormat(this.moneyFormat);
		return df.format(money);
	}

	/**
	 * 说明: 把钱的数字格式转化成中文表达
	 * @param number	数字钱数
	 * @return String 中文钱数
	 */
	private String hashWord(int number) {
		switch (number) {
		case 0:
			return "零";
		case 1:
			return "壹";
		case 2:
			return "贰";
		case 3:
			return "叁";
		case 4:
			return "肆";
		case 5:
			return "伍";
		case 6:
			return "陆";
		case 7:
			return "柒";
		case 8:
			return "捌";
		case 9:
			return "玖";
		default:
			return null;
		}
	}

	/**
	 * 说明: 把位数转化成中文
	 * @param number	数字钱数
	 * @return String 中文位数
	 */
	private String hashCellWord(int number) {
		switch (number) {
		case 0:
			return "";
		case 1:
			return "十";
		case 2:
			return "百";
		case 3:
			return "千";
		case 4:
			return "万";
		case 5:
			return "十";
		case 6:
			return "百";
		case 7:
			return "千";
		case 8:
			return "亿";
		case 9:
			return "十";
		case 10:
			return "百";
		case 11:
			return "千";
		case 12:
			return "万";
		default:
			return "十万";
		}
	}

	/**
	 * 说明: 把钱的表达转化成中文表达
	 * @param source	欲转换的钱字符串
	 * @return void
	 */
	private String convertToChineseMoney(String source) {
		source = StrUtils.replaceString(source, ",", "");
		String product = "";
		String left;
		String right;
		int leftSite;
		int rightSite;

		int location = source.lastIndexOf(".");
		//将元、角分分开
		if (location == -1) {
			left = source;
			right = "";
		} else {
			left = source.substring(0, location);
			right = source.substring(location + 1);
		}
		leftSite = left.length();
		rightSite = right.length();
		String leftString = "";
		int j = leftSite - 1;
		for (int i = 0; i < leftSite; i++) {
			//取出左边第一位数字
			int aTemp = 0;
			aTemp = Integer.parseInt(String.valueOf(left.charAt(i)));
			//转换为中文
			leftString += String.valueOf(this.hashWord(aTemp))
					+ String.valueOf(this.hashCellWord(j));
			j--;
		}
		leftString += "元";
		//-----------------------------------------------------------------
		String rightString = "";
		if (rightSite == 1) {
			rightString = this.hashWord(Integer.parseInt(String.valueOf(right
					.charAt(0))))
					+ "角";
		} else if (rightSite >= 2) {
			rightString = this.hashWord(Integer.parseInt(String.valueOf(right
					.charAt(0))))
					+ "角"
					+ this.hashWord(Integer.parseInt(String.valueOf(right
							.charAt(1)))) + "分";
		} else if (rightSite <= 0) {
			rightString = "";
		}
		//-----------------------------------------------------------------
		product = leftString + rightString;
		return product;
	}

	/**
	 * 测试入口
	 * @param args 测试参数
	 */
	public static void main(String[] args) {
	} 
}