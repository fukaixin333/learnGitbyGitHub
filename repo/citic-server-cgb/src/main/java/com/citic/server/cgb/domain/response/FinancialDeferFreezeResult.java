package com.citic.server.cgb.domain.response;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 19:39:54$
 */
public class FinancialDeferFreezeResult extends FinancialMessageResult {
	
	private static final String key_SerialNo = "SerialNo"; // 系统流水号
	private static final String key_ClientNo = "ClientNo"; // 客户编号
	private static final String key_ClientName = "ClientName"; // 客户名称
	private static final String key_TACode = "TACode"; // TA代码
	private static final String key_TAName = "TAName"; // TA名称
	private static final String key_PrdCode = "PrdCode"; // 产品代码
	private static final String key_PrdName = "PrdName"; // 产品名称
	private static final String key_CurrType = "CurrType"; // 币种
	private static final String key_OldEndDate = "OldEndDate"; // 原冻结截止日期
	private static final String key_NewEndDate = "NewEndDate"; // 新冻结截止日期
	private static final String key_Vol = "Vol"; // 冻结份额
	private static final String key_LawNo = "LawNo"; // 法律文书号
	private static final String key_OrgName = "OrgName"; // 执行机构名称
	private static final String key_Status = "Status"; // 交易状态
	private static final String key_StatusName = "StatusName"; // 交易状态名称
	
	public String getSerialNo() {
		return getFieldValue(key_SerialNo);
	}
	
	public String getClientNo() {
		return getFieldValue(key_ClientNo);
	}
	
	public String getClientName() {
		return getFieldValue(key_ClientName);
	}
	
	public String getTACode() {
		return getFieldValue(key_TACode);
	}
	
	public String getTAName() {
		return getFieldValue(key_TAName);
	}
	
	public String getPrdCode() {
		return getFieldValue(key_PrdCode);
	}
	
	public String getPrdName() {
		return getFieldValue(key_PrdName);
	}
	
	public String getCurrType() {
		return getFieldValue(key_CurrType);
	}
	
	public String getOldEndDate() {
		return getFieldValue(key_OldEndDate);
	}
	
	public String getNewEndDate() {
		return getFieldValue(key_NewEndDate);
	}
	
	public String getVol() {
		return getFieldValue(key_Vol);
	}
	
	public String getLawNo() {
		return getFieldValue(key_LawNo);
	}
	
	public String getOrgName() {
		return getFieldValue(key_OrgName);
	}
	
	public String getStatus() {
		return getFieldValue(key_Status);
	}
	
	public String getStatusName() {
		return getFieldValue(key_StatusName);
	}
}
