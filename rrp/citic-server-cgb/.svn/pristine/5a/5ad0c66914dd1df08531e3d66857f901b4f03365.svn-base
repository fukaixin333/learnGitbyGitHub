package com.citic.server.cgb.domain.request;


/**
 * 理财产品份额冻结（100222）
 * 
 * @author liuying07
 * @date 2017年6月14日 上午10:40:12
 */
public class FinancialFreezeInput extends FinancialMessageInput {
	
	private final String key_accountNumber = "BankAcc"; // 银行账号(m)
	private final String key_productCode = "PrdCode"; // 产品代码(m)
	private final String key_volume = "Vol"; // 理财产品份额(m)
	private final String key_frozenCause = "FrozenCause"; // 冻结原因(m)（0-司法冻结；1-柜台冻结；2-质押；3-质押/司法；4-柜台/司法）
	private final String key_lawNumber = "LawNo"; // 法律文书号(m)  [FrozenCause =0-司法冻结是必输]
	private final String key_organName = "OrgName"; // 执行机构名称(m) [FrozenCause =0-司法冻结是必输]
	private final String key_freezeEndDate = "EndDate"; // 冻结截止日期(m)
	private final String key_flagType = "AccType"; // 客户标识类型(m)  [1-核心客户号 ,2-证件号]
	private final String key_flagNumber = "Account"; // 客户标识(m) [AccType=1，填核心客户号 ,AccType=2，填证件号码]
	private final String key_idType = "IdType"; // 证件类型(m) [客户标识类型=证件号时必填]
	private final String key_cashExCode = "CashFlag"; // 钞汇标志
	private final String key_customerType = "ClientType"; // 客户类型
	private final String key_lawName1 = "FrozenName1"; // 执行人1姓名
	private final String key_lawIdType1 = "FrozenIdType1"; // 执行人1证件类型
	private final String key_lawIdCode1 = "FrozenIdCode1"; // 执行人1证件号码
	private final String key_lawName2 = "FrozenName2"; // 执行人2姓名
	private final String key_lawIdType2 = "FrozenIdType2"; // 执行人2证件类型
	private final String key_lawIdCode2 = "FrozenIdCode2"; // 执行人2证件号码
	private final String key_frozenComment = "CauseComment"; // 原因说明
	
	@Override
	public void initDefaultField() {
		putField(key_frozenCause, "0");
	}
	
	public String getAccountNumber() {
		return getFieldValue(key_accountNumber);
	}
	
	public void setAccountNumber(String accountNumber) {
		putField(key_accountNumber, accountNumber);
	}
	
	public String getProductCode() {
		return getFieldValue(key_productCode);
	}
	
	public void setProductCode(String productCode) {
		putField(key_productCode, productCode);
	}
	
	public String getVolume() {
		return getFieldValue(key_volume);
	}
	
	public void setVolume(String volume) {
		putField(key_volume, volume);
	}
	
	public String getFrozenCause() {
		return getFieldValue(key_frozenCause);
	}
	
	public void setFrozenCause(String frozenCause) {
		putField(key_frozenCause, frozenCause);
	}
	
	public String getLawNumber() {
		return getFieldValue(key_lawNumber);
	}
	
	public void setLawNumber(String lawNumber) {
		putField(key_lawNumber, lawNumber);
	}
	
	public String getOrganName() {
		return getFieldValue(key_organName);
	}
	
	public void setOrganName(String organName) {
		putField(key_organName, organName);
	}
	
	public String getFreezeEndDate() {
		return getFieldValue(key_freezeEndDate);
	}
	
	public void setFreezeEndDate(String freezeEndDate) {
		putField(key_freezeEndDate, freezeEndDate);
	}
	
	public String getFlagType() {
		return getFieldValue(key_flagType);
	}
	
	public void setFlagType(String flagType) {
		putField(key_flagType, flagType);
	}
	
	public String getFlagNumber() {
		return getFieldValue(key_flagNumber);
	}
	
	public void setFlagNumber(String flagNumber) {
		putField(key_flagNumber, flagNumber);
	}
	
	public String getIdType() {
		return getFieldValue(key_idType);
	}
	
	public void setIdType(String idType) {
		putField(key_idType, idType);
	}
	
	public String getCashExCode() {
		return getFieldValue(key_cashExCode);
	}
	
	public void setCashExCode(String cashExCode) {
		putField(key_cashExCode, cashExCode);
	}
	
	public String getCustomerType() {
		return getFieldValue(key_customerType);
	}
	
	public void setCustomerType(String customerType) {
		putField(key_customerType, customerType);
	}
	
	public String getLawName1() {
		return getFieldValue(key_lawName1);
	}
	
	public void setLawName1(String lawName1) {
		putField(key_lawName1, lawName1);
	}
	
	public String getLawIdType1() {
		return getFieldValue(key_lawIdType1);
	}
	
	public void setLawIdType1(String lawIdType1) {
		putField(key_lawIdType1, lawIdType1);
	}
	
	public String getLawIdCode1() {
		return getFieldValue(key_lawIdCode1);
	}
	
	public void setLawIdCode1(String lawIdCode1) {
		putField(key_lawIdCode1, lawIdCode1);
	}
	
	public String getLawName2() {
		return getFieldValue(key_lawName2);
	}
	
	public void setLawName2(String lawName2) {
		putField(key_lawName2, lawName2);
	}
	
	public String getLawIdType2() {
		return getFieldValue(key_lawIdType2);
	}
	
	public void setLawIdType2(String lawIdType2) {
		putField(key_lawIdType2, lawIdType2);
	}
	
	public String getLawIdCode2() {
		return getFieldValue(key_lawIdCode2);
	}
	
	public void setLawIdCode2(String lawIdCode2) {
		putField(key_lawIdCode2, lawIdCode2);
	}
	
	public String getFrozenComment() {
		return getFieldValue(key_frozenComment);
	}
	
	public void setFrozenComment(String frozenComment) {
		putField(key_frozenComment, frozenComment);
	}
}
