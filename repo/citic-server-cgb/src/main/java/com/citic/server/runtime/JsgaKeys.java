package com.citic.server.runtime;

public interface JsgaKeys {
	
	// ==========================================================================================
	//                     江苏省公安厅（涉案账户资金网络查控系统）
	// ==========================================================================================
	
	public static final String FILE_DIRECTORY_12 = "directory.jsga.12";
	public static final String BANK_CODE_12 = "jsga.12.bank.code";
	public static final String REMOTE_ACCESS_URL_12 = "jsga.12.remote.access.url";
	
	/** 内联动态查询间隔时间 */
	public static final String INNER_POLLING_PERIOD_DYNAMICS = "jsga.inner.polling.period.dynamics";
	
	
	/** 根据证件查询请求数据接口 */
	String QUERY_BY_CERTIFICATE = "YjCgcxZjQqsjServices";
	/** 根据证件查询反馈账户信息数据接口 */
	String FEEDBACK_CERTIFICATE_INFO = "YjCgcxZjJbxxFkServices";
	/** 证件类查询 绑定文件 */
	String CERTIFICATE_BINDING_NAME = "binding_jsga_query_resp";
	
	/** 根据账（卡）号查询请求数据接口 */
	String QUERY_BY_ACCOUNTNUMBER = "YjCgcxZhQqsjServices";
	/** 根据账（卡）号查询反馈账户信息数据接口 */
	String FEEDBACK_ACCOUNTNUMBER_INFO = "YjCgcxZhJbxxFkServices";
	/** 证件类查询 绑定文件 */
	String ACCOUNTNUMBER_BINDING_NAME = "binding_jsga_queryaccount_resp";
	
	/** 常规查询反馈账户交易明细接口 */
	String FEEDBACK_ACCOUNT_TRANS = "YjCgcxJymxfkServices";
	
	/** 账户冻结/解冻/续冻请求 */
	String FREEZE_ACCOUNT = "YjZhdjQqsjServices";
	/** 账户冻结/解冻/续冻请求结果 */
	String FEEDBACK_FREEZE_RESULT = "YjZhdjQqhzServices";
	/** 账户冻结/解冻/续冻 绑定文件 */
	String FREEZE_BINDING_NAME = "binding_jsga_freeze_resp";
	
	/** 动态查询请求 */
	String DYNAMIC_QUERY = "YjDtbkQqsjServices";
	/** 动态查询请求结果 */
	String FEEDBACK_DYNAMIC_RESULT = "YjDtbkQqhzServices";
	/** 动态查询请求 绑定文件 */
	String DYNAMIC_BINDING_NAME = "binding_jsga_control_resp";
	/** 动态查询请求结果信息 */
	String FEEDBACK_DYNAMIC_RESULT_INFO="YjDtbkFkCxJgServices";
	
	/** 紧急止付请求 */
	String STOP_PAYMENT = "YjJjzfQqsjServices";
	/** 紧急止付请求反馈 */
	String FEEDBACK_STOP_PAYMENT_RESULT = "YjJjzfQqhzServices";
	/** 紧急止付 绑定文件 */
	String STOP_PAYMENT_BINDING_NAME = "binding_jsga_stoppayment_resp";
	
	/** 法律文书信息接口 */
	String SERVICE_FLWS = "YjFlwsServices";
	String LAWDOCUMENT_BINDING_NAME="binding_jsga_gainducoment_resp";
	
	/** 请求数据下发回执接口 */
	String SERVICE_XFHZ = "YjSjxfhzServices";
}
