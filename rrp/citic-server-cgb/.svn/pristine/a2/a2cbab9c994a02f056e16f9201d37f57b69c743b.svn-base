package com.citic.server.runtime;

import java.util.HashMap;

/**
 * 江苏公安厅
 * 
 * @author chen jie
 * @date 2017年5月9日 下午9:07:05
 */
public final class Constants12 {
	
	public static final String REMOTE_DATA_OPERATE_NAME_JSGA = "remoteDataOperate12";
	
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
	
	private static final HashMap<String, String> cache12 = new HashMap<String, String>();
	static {
		cache12.put("01", "SS09"); // 主体类别（个人）-交易流水反馈
		cache12.put("02", "SS10"); // 主体类别（对公）-交易流水反馈
		cache12.put("SS01", "SS03"); // 根据证照查询（个人）
		cache12.put("SS02", "SS04"); // 根据证照查询（对公）
		cache12.put("SS05", "SS07"); // 根据账卡号查询（个人）
		cache12.put("SS06", "SS08"); // 根据账卡号查询（对公）
		cache12.put("SS11", "SS13");
		cache12.put("SS12", "SS14");
		cache12.put("SS17", "SS19");
		cache12.put("SS18", "SS20");
		cache12.put("SS21", "SS23");
		cache12.put("SS22", "SS24");
	}
	
	public static String valueOf(String key) {
		return cache12.get(key);
	}
	
	public static String valueOf(String key, String nval) {
		String val = cache12.get(key);
		return val == null ? nval : val;
	}
	
}
