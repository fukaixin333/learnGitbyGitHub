package com.citic.server.dx;

import java.util.HashMap;

/**
 * 电信诈骗-常量
 * <p>
 * <code>TxCode</code>-交易类型对应表：
 * <table border="1">
 * <tr>
 * <td align="center" width="90px"><b>编码</b></td>
 * <td align="center" width="120px"><b>交易类型</b></td>
 * </tr>
 * <tr>
 * <td>100101</td>
 * <td>止付报文</td>
 * </tr>
 * <tr>
 * <td>100103</td>
 * <td>止付解除报文</td>
 * </tr>
 * <tr>
 * <td>……</td>
 * <td>……</td>
 * </tr>
 * </table>
 * 
 * @author Liu Xuanfei
 * @date 2016年3月28日 下午3:13:28
 */
public final class TxConstants {
	private TxConstants() {
	}
	
	// ==========================================================================================
	//                     交易类型编码
	// ==========================================================================================
	/** 交易类型编码-止付报文 */
	public static final String TXCODE_STOPPAYMENT = "100101";
	/** 交易类型编码-止付报文反馈 */
	public static final String TXCODE_STOPPAYMENT_FEEDBACK = "100102";
	/** 交易类型编码-止付解除报文 */
	public static final String TXCODE_STOPPAYMENT_RELIEVE = "100103";
	/** 交易类型编码-止付解除反馈报文 */
	public static final String TXCODE_STOPPAYMENT_RELIEVE_FEEDBACK = "100104";
	/** 交易类型编码-止付延期报文 */
	public static final String TXCODE_STOPPAYMENT_POSTPONE = "100105";
	/** 交易类型编码-止付延期反馈报文 */
	public static final String TXCODE_STOPPAYMENT_POSTPONE_FEEDBACK = "100106";
	
	/** 交易类型编码-冻结报文 */
	public static final String TXCODE_FREEZE = "100201";
	/** 交易类型编码-冻结反馈报文 */
	public static final String TXCODE_FREEZE_FEEDBACK = "100202";
	/** 交易类型编码-冻结解除报文 */
	public static final String TXCODE_FREEZE_RELIEVE = "100203";
	/** 交易类型编码-冻结解除反馈报文 */
	public static final String TXCODE_FREEZE_RELIEVE_FEEDBACK = "100204";
	/** 交易类型编码-冻结延期报文 */
	public static final String TXCODE_FREEZE_POSTPONE = "100205";
	/** 交易类型编码-冻结延期反馈报文 */
	public static final String TXCODE_FREEZE_POSTPONE_FEEDBACK = "100206";
	
	/** 交易类型编码-账户交易明细查询报文 */
	public static final String TXCODE_ACCOUNT_TRANSACTION_DETAIL = "100301";
	/** 交易类型编码-账户交易明细查询反馈报文 */
	public static final String TXCODE_ACCOUNT_TRANSACTION_DETAIL_FEEDBACK = "100302";
	/** 交易类型编码-账户持卡主体查询报文 */
	public static final String TXCODE_ACCOUNT_CARD_SUBJECT = "100303";
	/** 交易类型编码-账户持卡主体查询反馈报文 */
	public static final String TXCODE_ACCOUNT_CARD_SUBJECT_FEEDBACK = "100304";
	/** 交易类型编码-账户动态查询报文 */
	public static final String TXCODE_ACCOUNT_DYNAMIC = "100305";
	/** 交易类型编码-账户动态查询反馈报文 */
	public static final String TXCODE_ACCOUNT_DYNAMIC_FEEDBACK = "100306";
	/** 交易类型编码-账户动态查询解除报文 */
	public static final String TXCODE_ACCOUNT_DYNAMIC_RELIEVE = "100307";
	/** 交易类型编码-账户动态查询解除反馈报文 */
	public static final String TXCODE_ACCOUNT_DYNAMIC_RELIEVE_FEEDBACK = "100308";
	
	/** 交易类型编码-客户全账户查询报文 */
	public static final String TXCODE_CUSTOMER_WHOLE_ACCOUNT = "100309";
	/** 交易类型编码-客户全账户查询反馈报文 */
	public static final String TXCODE_CUSTOMER_WHOLE_ACCOUNT_FEEDBACK = "100310";
	
	/** 交易类型编码-案件举报报文 */
	public static final String TXCODE_REPORT_CASE = "100401";
	/** 交易类型编码-案件举报反馈报文 */
	public static final String TXCODE_REPORT_CASE_FEEDBACK = "100402";
	/** 交易类型编码-可疑名单上报-异常开卡报文 */
	public static final String TXCODE_REPORT_UNUSUAL_OPENCARD = "100403";
	/** 交易类型编码-可疑名单上报-涉案账户报文 */
	public static final String TXCODE_REPORT_INVOLVED_ACOUNT = "100404";
	/** 交易类型编码-可疑名单上报-异常事件报文 */
	public static final String TXCODE_REPORT_EXCEPTIONAL_EVENT = "100405";
	
	// ==========================================================================================
	//                     返回码
	// ==========================================================================================
	public static final String CODE_OK = "100000";
	
	private static final HashMap<String, String> cache = new HashMap<String, String>();
	static {
		cache.put(CODE_OK, "成功");
		cache.put("200001", "报文格式错");
		cache.put("200002", "超时无应答");
		cache.put("200003", "重复提交");
		cache.put("200004", "未授权的行内系统");
		cache.put("200005", "未授权的前置机");
		cache.put("200006", "行内系统接口无权限");
		cache.put("200007", "前置机接口无权限");
		cache.put("200008", "前置机调用次数超限");
		cache.put("200009", "前置机通道关闭");
		cache.put("200010", "未找到账户");
		cache.put("600004", "Socket通讯发生异常");
		cache.put("600005", "服务器建立不了更多Socket连接");
	};
	
	public static String valueOf(String key) {
		return cache.get(key);
	}
}
