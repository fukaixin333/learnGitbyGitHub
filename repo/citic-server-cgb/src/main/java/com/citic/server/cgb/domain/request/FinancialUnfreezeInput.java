package com.citic.server.cgb.domain.request;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 22:21:23$
 */
public class FinancialUnfreezeInput extends FinancialMessageInput {
	
	private static final String key_AccountNumber = "BankAcc"; // 银行账号
	private static final String key_TACode = "TACode"; // TA代码
	private static final String key_PrdCode = "PrdCode"; // 产品代码
	private static final String key_AssoSerial = "AssoSerial"; // 原冻结流水号
	private static final String key_LawNo = "LawNo"; // 法律文书号
	private static final String key_OrgName = "OrgName"; // 执行机构名称
	private static final String key_AccType = "AccType"; // 客户标识类型
	private static final String key_Account = "Account"; // 客户标识
	private static final String key_IdType = "IdType"; // 证件类型
	private static final String key_CashFlag = "CashFlag"; // 钞汇标志
	private static final String key_ClientType = "ClientType"; // 客户类型
	private static final String key_Volume = "Vol"; // 解冻份额
	private static final String key_FrozenName1 = "FrozenName1"; // 执行人1姓名
	private static final String key_FrozenIdType1 = "FrozenIdType1"; // 执行人1证件类型
	private static final String key_FrozenIdCode1 = "FrozenIdCode1"; // 执行人1证件号码
	private static final String key_FrozenName2 = "FrozenName2"; // 执行人2姓名
	private static final String key_FrozenIdType2 = "FrozenIdType2"; // 执行人2证件类型
	private static final String key_FrozenIdCode2 = "FrozenIdCode2"; // 执行人2证件号码
	private static final String key_CauseComment = "CauseComment"; // 原因说明
	
	@Override
	public void initDefaultField() {
	}
	
	public String getAccountNumber() {
		return getFieldValue(key_AccountNumber);
	}
	
	public void setAccountNumber(String accountNumber) {
		putField(key_AccountNumber, accountNumber);
	}
	
	public String getTACode() {
		return getFieldValue(key_TACode);
	}
	
	public void setTACode(String taCode) {
		putField(key_TACode, taCode);
	}
	
	public String getPrdCode() {
		return getFieldValue(key_PrdCode);
	}
	
	public void setPrdCode(String prdCode) {
		putField(key_PrdCode, prdCode);
	}
	
	public String getAssoSerial() {
		return getFieldValue(key_AssoSerial);
	}
	
	public void setAssoSerial(String assoSerial) {
		putField(key_AssoSerial, assoSerial);
	}
	
	public String getLawNo() {
		return getFieldValue(key_LawNo);
	}
	
	public void setLawNo(String lawNo) {
		putField(key_LawNo, lawNo);
	}
	
	public String getOrgName() {
		return getFieldValue(key_OrgName);
	}
	
	public void setOrgName(String orgName) {
		putField(key_OrgName, orgName);
	}
	
	public String getAccType() {
		return getFieldValue(key_AccType);
	}
	
	public void setAccType(String accType) {
		putField(key_AccType, accType);
	}
	
	public String getAccount() {
		return getFieldValue(key_Account);
	}
	
	public void setAccount(String account) {
		putField(key_Account, account);
	}
	
	public String getIdType() {
		return getFieldValue(key_IdType);
	}
	
	public void setIdType(String idType) {
		putField(key_IdType, idType);
	}
	
	public String getCashFlag() {
		return getFieldValue(key_CashFlag);
	}
	
	public void setCashFlag(String cashFlag) {
		putField(key_CashFlag, cashFlag);
	}
	
	public String getClientType() {
		return getFieldValue(key_ClientType);
	}
	
	public void setClientType(String clientType) {
		putField(key_ClientType, clientType);
	}
	
	public String getVolume() {
		return getFieldValue(key_Volume);
	}
	
	public void setVolume(String vol) {
		putField(key_Volume, vol);
	}
	
	public String getFrozenName1() {
		return getFieldValue(key_FrozenName1);
	}
	
	public void setFrozenName1(String frozenName1) {
		putField(key_FrozenName1, frozenName1);
	}
	
	public String getFrozenIdType1() {
		return getFieldValue(key_FrozenIdType1);
	}
	
	public void setFrozenIdType1(String frozenIdType1) {
		putField(key_FrozenIdType1, frozenIdType1);
	}
	
	public String getFrozenIdCode1() {
		return getFieldValue(key_FrozenIdCode1);
	}
	
	public void setFrozenIdCode1(String frozenIdCode1) {
		putField(key_FrozenIdCode1, frozenIdCode1);
	}
	
	public String getFrozenName2() {
		return getFieldValue(key_FrozenName2);
	}
	
	public void setFrozenName2(String frozenName2) {
		putField(key_FrozenName2, frozenName2);
	}
	
	public String getFrozenIdType2() {
		return getFieldValue(key_FrozenIdType2);
	}
	
	public void setFrozenIdType2(String frozenIdType2) {
		putField(key_FrozenIdType2, frozenIdType2);
	}
	
	public String getFrozenIdCode2() {
		return getFieldValue(key_FrozenIdCode2);
	}
	
	public void setFrozenIdCode2(String frozenIdCode2) {
		putField(key_FrozenIdCode2, frozenIdCode2);
	}
	
	public String getCauseComment() {
		return getFieldValue(key_CauseComment);
	}
	
	public void setCauseComment(String causeComment) {
		putField(key_CauseComment, causeComment);
	}
}
