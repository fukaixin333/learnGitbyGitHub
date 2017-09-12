/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/14 14:25:23$
 */
package com.citic.server.logback;

/**
 * 转义序列、文本属性、前景色、背景色
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/14 14:25:23$
 */
public class ANSIConstants {
	/** ESC_START: 声明转义序列的开始 */
	public static final String ESC_START = "\033[";
	/** ESC_END: 声明转义序列的结尾 */
	public static final String ESC_END = "m";
	
	/** DEFAULT: 默认值 */
	public static final String DEFAULT = "0;";
	/** BOLD: 粗体 */
	public static final String BOLD = "1;";
	/** UNDERLINE: 下划线 */
	public static final String UNDERLINE = "4;";
	/** BLINK: 闪烁 */
	public static final String BLINK = "5;";
	/** INVERT: 反显 */
	public static final String INVERT = "7;";
	
	/** BLACK_FG: 黑色 */
	public static final String BLACK_FG = "30";
	/** RED_FG: 红色 */
	public static final String RED_FG = "31";
	/** GREEN_FG: 绿色 */
	public static final String GREEN_FG = "32";
	/** YELLOW_FG: 黄色 */
	public static final String YELLOW_FG = "33";
	/** BLUE_FG: 蓝色 */
	public static final String BLUE_FG = "34";
	/** MAGENTA_FG: 洋红 */
	public static final String MAGENTA_FG = "35";
	/** CYAN_FG: 青色 */
	public static final String CYAN_FG = "36";
	/** WHITE_FG: 白色 */
	public static final String WHITE_FG = "37";
	/** DEFAULT_FG: 默认值 */
	public static final String DEFAULT_FG = "39";
	
	/** BLACK_BG: 黑色 */
	public static final String BLACK_BG = ";40";
	/** RED_BG: 红色 */
	public static final String RED_BG = ";41";
	/** GREEN_BG: 绿色 */
	public static final String GREEN_BG = ";42";
	/** YELLOW_BG: 黄色 */
	public static final String YELLOW_BG = ";43";
	/** BLUE_BG: 蓝色 */
	public static final String BLUE_BG = ";44";
	/** MAGENTA_BG: 洋红 */
	public static final String MAGENTA_BG = ";45";
	/** CYAN_BG: 青色 */
	public static final String CYAN_BG = ";46";
	/** WHITE_BG: 白色 */
	public static final String WHITE_BG = ";47";
	/** DEFAULT_BG: 默认值 */
	public static final String DEFAULT_BG = "";
}
