package com.citic.server.cgb.domain.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/26 00:02:23$
 */
public class OnlineTPFinancialDetail {
	
	private static final String key_FinancialNumber = "acctNo"; // 理财账号
	private static final String key_accountNumber = "acNo"; // 回款账号
	private static final String key_productType = "type"; // 明细类型
	private static final String key_productCode = "prodCode"; // 产品代码
	private static final String key_productName = "prodName"; // 产品名称
	private static final String key_saleType = "sellType"; // 销售类型
	private static final String key_currency = "ccy"; // 币种
	private static final String key_amount = "aounmt1"; // 份额
	private static final String key_balance = "aounmt2"; // （基金）可用余额
	private static final String key_mark = "mark"; // 预留字段
	
	public static final String START_KEY = key_FinancialNumber;
	public static final String END_KEY = key_mark;
	
	private final Map<String, String> fieldMap = new HashMap<String, String>();
	
	public void putField(String key, String value) {
		fieldMap.put(key, value);
	}
	
	public String getFinancialNumber() {
		return fieldMap.get(key_FinancialNumber);
	}
	
	public String getAccountNumber() {
		return fieldMap.get(key_accountNumber);
	}
	
	public String getProductType() {
		return fieldMap.get(key_productType);
	}
	
	public String getProductCode() {
		return fieldMap.get(key_productCode);
	}
	
	public String getProductName() {
		return fieldMap.get(key_productName);
	}
	
	public String getSaleType() {
		return fieldMap.get(key_saleType);
	}
	
	public String getCurrency() {
		return fieldMap.get(key_currency);
	}
	
	public String getAmount() {
		return fieldMap.get(key_amount);
	}
	
	public String getBalance() {
		return fieldMap.get(key_balance);
	}
	
	public String getMark() {
		return fieldMap.get(key_mark);
	}
}
