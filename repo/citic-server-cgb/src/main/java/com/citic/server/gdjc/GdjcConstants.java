package com.citic.server.gdjc;

/**
 * 广东省纪检监察机关-常量
 * 
 * @author Liu Xuanfei
 * @date 2016年8月16日 下午7:59:34
 */
public final class GdjcConstants {
	
	/** 身份认证 */
	public static final String COMMAND_TYPE_LOGIN = "LOGIN";
	
	/** 表示（登录）成功 */
	public static final String COMMAND_STATUS_OK = "YES";
	/** 表示（登录）失败 */
	public static final String COMMAND_STATUS_ERR = "NO";
	
	/** 表示没有异常 */
	public static final String DATA_TYPE_OK = "NORMAL";
	/** 表示存在异常 */
	public static final String DATA_TYPE_ERR = "ERROR";
	
	/** 商业银行网点登记 */
	public static final String DATA_CONTENT_WDDJ = "YHWDDJ";
	/** 商业银行账号规则登记 */
	public static final String DATA_CONTENT_GZDJ = "YHZHGZDJ";
	/** 城市代号对照登记 */
	public static final String DATA_CONTENT_DHDJ = "CSDHDZDJ";
	
	/** 商业银行存款查询 */
	public static final String DATA_CONTENT_CK = "YHCKCX";
	/** 商业银行存款登记 */
	public static final String DATA_CONTENT_CKDJ = "YHCKDJ";
	
	/** 商业银行交易流水查询 */
	public static final String DATA_CONTENT_LS = "YHJYLSCX";
	/** 商业银行交易流水登记 */
	public static final String DATA_CONTENT_LSDJ = "YHJYLSDJ";

	/** 商业银行交易流水登记文件上传 */
	public static final String DATA_CONTENT_LSDJ_WJSC = "YHJYLSDJ_WJSC";
	/** 商业银行交易流水登记文件上传处理结果查询 */
	public static final String DATA_CONTENT_LSDJ_WJSC_WJJG = "YHJYLSDJ_WJJG";

	/** 类型-单位 */
	public static final String TYPE_UNIT = "UNIT";
	/** 类型-自然人 */
	public static final String TYPE_PERSON = "PERSON";
	/** 类型-账号 */
	public static final String TYPE_ACCOUNT = "ACCOUNT";
	
	/**   */
	public static final String BANK_ID = "GDB";
	public static final String BANK_NAME = "广发银行";
}
