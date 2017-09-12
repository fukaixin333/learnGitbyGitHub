package com.citic.server.gdjg;

/**
 * 广东省检察院-常量
 * 
 * @author Liu Xuanfei
 * @date 2016年8月16日 下午7:59:34
 */
public interface GdjgConstants {
	public static final String REMOTE_DATA_OPERATE_NAME_GDJG = "remoteDataOperateGdjg";
	
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
	
	/** 商业银行交易流水登记文件上传 ftp */
	public static final String DATA_CONTENT_LSDJ_WJSC = "YHJYLSDJ_WJSC_TXT";
	/** 商业银行交易流水登记文件上传处理结果查询 ftp */
	public static final String DATA_CONTENT_LSDJ_WJSC_WJJG = "YHJYLSDJ_WJJG";
	
	/** 类型-单位 */
	public static final String TYPE_UNIT = "UNIT";
	/** 类型-自然人 */
	public static final String TYPE_PERSON = "PERSON";
	/** 类型-账号 */
	public static final String TYPE_ACCOUNT = "ACCOUNT";
	
	public static final String BANK_ID = "CGB";
	public static final String BANK_NAME = "广发银行";
	
	/** 存款冻结申请 */
	public static final String DATA_CONTENT_YHCKDJCX = "YHCKDJCX";
	/** 存款冻结登记 */
	public static final String DATA_CONTENT_YHCKDJDJ = "YHCKDJDJ";
	/** 存款解冻申请 */
	public static final String DATA_CONTENT_YHCKJDCX = "YHCKJDCX";
	/** 存款解冻登记 */
	public static final String DATA_CONTENT_YHCKJDDJ = "YHCKJDDJ";
	/** 存款冻结解冻回执 */
	public static final String DATA_CONTENT_YHDJJDHZ = "YHDJJDHZ";
	/** 交易流水ftp文件登记（txt） */
	public static final String DATA_CONTENT_YHJYLSDJ_WJSC_TXT = "YHJYLSDJ_WJSC_TXT";
	/** 交易流水ftp文件登记结果查询 */
	public static final String DATA_CONTENT_YHJYLSDJ_WJJG = "YHJYLSDJ_WJJG";
	/** 动态查询 */
	public static final String DATA_CONTENT_YHDTCX = "YHDTCX";
	/** 动态查询回执 */
	public static final String DATA_CONTENT_YHDTCXHZ = "YHDTCXHZ";
	/** 动态查询信息登记 */
	public static final String DATA_CONTENT_YHDTXXDJ = "YHDTXXDJ";
	/** 紧急止付申请 */
	public static final String DATA_CONTENT_YHJJZFQQ = "YHJJZFQQ";
	/** 紧急止付登记 */
	public static final String DATA_CONTENT_YHJJZFDJ = "YHJJZFDJ";
	/** 金融产品查询 */
	public static final String DATA_CONTENT_YHJRCPCX = "YHJRCPCX";
	/** 金融产品登记 */
	public static final String DATA_CONTENT_YHJRCPDJ = "YHJRCPDJ";
	/** 保险箱查询 */
	public static final String DATA_CONTENT_YHBXXCX = "YHBXXCX";
	/** 保险箱登记 */
	public static final String DATA_CONTENT_YHBXXDJ = "YHBXXDJ";
	/** 电子证据调取请求 */
	public static final String DATA_CONTENT_DZZJDQQQ = "DZZJDQQQ";
	/** 电子签章查询 */
	public static final String DATA_CONTENT_DZQZCX = "DZQZCX";
	/** 电子签章接收 */
	public static final String DATA_CONTENT_DZZJS = "DZZJS";
	/** 签章文档核查 */
	public static final String DATA_CONTENT_QZWDHC = "QZWDHC";
}
