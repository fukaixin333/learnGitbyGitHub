package com.citic.server.cbrc;

import java.util.HashMap;

/**
 * 查控参数
 * 
 * @author Liu Xuanfei
 * @date 2016年6月14日 上午10:55:57
 */
public final class CBRCConstants {
	
	private CBRCConstants() {
	}
	
	// ==========================================================================================
	//                     请求措施类型
	// ==========================================================================================
	/** 措施编码-常规查询 */
	public static final String ROUTINE = "01";
	
	/** 措施编码-动态查询 */
	public static final String DYNAMIC = "02";
	
	/** 措施编码-继续动态查询 */
	public static final String DYNAMIC_CONTINUE = "03";
	
	/** 措施编码-解除动态查询 */
	public static final String DYNAMIC_RELIEVE = "04";
	
	/** 措施编码-冻结 */
	public static final String FREEZE = "05";
	
	/** 措施编码-冻结延期 */
	public static final String FREEZE_POSTPONE = "06";
	
	/** 措施编码-冻结解除 */
	public static final String FREEZE_RELIEVE = "07";
	
	/** 措施编码-止付 */
	public static final String STOPPAYMENT = "08";
	
	/** 措施编码-止付解除 */
	public static final String STOPPAYMENT_RELIEVE = "09";
	
	/** 凭证图像调阅 */
	public static final String QUERY_SCAN = "10";
	
	// ==========================================================================================
	//                     查询内容
	// ==========================================================================================
	/** 查询内容-账户信息 */
	public static final String CXNR_ACCOUNT = "01";
	
	/** 查询内容-账户交易明细 */
	public static final String CXNR_TRANSACTION = "02";
	
	/** 查询内容-账户信息和账户交易明细 */
	public static final String CXNR_ACC_AND_TRANS = "03";
	
	// ==========================================================================================
	//                     回执代码
	// ==========================================================================================
	/** 成功接收查控文件 */
	public static final String REC_CODE_OK = "30000";
	
	/** 数字签名错误，验签没通过 */
	public static final String REC_CODE_30001 = "30001";
	
	/** 查控报文解析错误 */
	public static final String REC_CODE_30002 = "30002";
	
	/** 查控法律文书缺失 */
	public static final String REC_CODE_30003 = "30003";
	
	/** 法律文书与请求单信息不符 */
	public static final String REC_CODE_30004 = "30004";
	
	/** 缺少请求报文文件 */
	public static final String REC_CODE_30005 = "30005";
	
	/** 其他错误 */
	public static final String REC_CODE_99999 = "99999";
	
	/** 成功反馈查控结果信息 */
	public static final String REC_CODE_GA001 = "GA001";
	
	/** 成功反馈查控结果信息 */
	public static final String REC_CODE_GA002 = "GA002";
	
	private static final HashMap<String, String> cache = new HashMap<String, String>();
	static {
		cache.put("01", "SS09"); // 主体类别（个人）-交易流水反馈
		cache.put("02", "SS10"); // 主体类别（对公）-交易流水反馈
		cache.put("SS01", "SS03"); // 根据证照查询（个人）
		cache.put("SS02", "SS04"); // 根据证照查询（对公）
		cache.put("SS05", "SS07"); // 根据账卡号查询（个人）
		cache.put("SS06", "SS08"); // 根据账卡号查询（对公）
		cache.put("SS11", "SS13");
		cache.put("SS12", "SS14");
		cache.put("SS17", "SS19");
		cache.put("SS18", "SS20");
		cache.put("SS21", "SS23");
		cache.put("SS22", "SS24");
	};
	
	public static String valueOf(String key) {
		return cache.get(key);
	}
	
	public static String valueOf(String key, String nval) {
		String val = cache.get(key);
		return val == null ? nval : val;
	}
}
