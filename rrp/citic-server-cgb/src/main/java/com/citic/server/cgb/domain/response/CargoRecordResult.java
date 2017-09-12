package com.citic.server.cgb.domain.response;

public class CargoRecordResult extends UnifiedPayMentMessageResult {
	private static final String key_feeAmount = "feeAmount"; // 	手续费金额
	private static final String key_payPath = "payPath"; // 	通道代码
	private static final String key_payPathWkDate = "payPathWkDate"; // 	通道工作日期
	private static final String key_payPathSerno = "payPathSerno"; // 	通道流水号
	private static final String key_pathProcStatus = "pathProcStatus"; // 	通道处理状态
	private static final String key_coreAcctDate = "coreAcctDate"; // 	核心记账日期
	private static final String key_coreSerno = "coreSerno"; // 	核心日志号
	private static final String key_coreProcStatus = "coreProcStatus"; // 	核心处理状态
	private static final String key_reserve1 = "reserve1"; // 	预留字段1
	private static final String key_reserve2 = "reserve2"; // 	预留字段2
	private static final String key_reserve3 = "reserve3"; // 	预留字段3
	private static final String key_reserve4 = "reserve4"; // 	预留字段4
	private static final String key_reserve5 = "reserve5"; // 	预留字段5
	
	public String getFeeAmount() {
		return getFieldValue(key_feeAmount);
		
	}
	
	public String getPayPath() {
		return getFieldValue(key_payPath);
		
	}
	
	public String getPayPathWkDate() {
		return getFieldValue(key_payPathWkDate);
		
	}
	
	public String getPayPathSerno() {
		return getFieldValue(key_payPathSerno);
		
	}
	
	public String getPathProcStatus() {
		return getFieldValue(key_pathProcStatus);
		
	}
	
	public String getCoreAcctDate() {
		return getFieldValue(key_coreAcctDate);
		
	}
	
	public String getCoreSerno() {
		return getFieldValue(key_coreSerno);
		
	}
	
	public String getCoreProcStatus() {
		return getFieldValue(key_coreProcStatus);
		
	}
	
	public String getReserve1() {
		return getFieldValue(key_reserve1);
		
	}
	
	public String getReserve2() {
		return getFieldValue(key_reserve2);
		
	}
	
	public String getReserve3() {
		return getFieldValue(key_reserve3);
		
	}
	
	public String getReserve4() {
		return getFieldValue(key_reserve4);
		
	}
	
	public String getReserve5() {
		return getFieldValue(key_reserve5);
		
	}
	
}
