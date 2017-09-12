package com.citic.server.dx.domain.request;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.RequestMessage;

/**
 * 账户冻结上行（反馈）报文
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午2:36:19
 */
public class FreezeRequest100202 extends RequestMessage {
	private static final long serialVersionUID = 3607614063698184624L;
	
	/** 业务申请编号 */
	private String applicationID;
	
	/** 冻结结果（4位业务应答码） */
	private String result;
	
	/** 冻结账号类别（01-个人；02-对公） */
	private String accountType;
	
	/** 冻结帐卡号（与原冻结报文帐卡号相同） */
	private String accountNumber;
	
	/** 卡/折号（对私业务时需填写） */
	private String cardNumber;
	
	/** 申请冻结限额（单位到元） */
	private String appliedBalance;
	
	/** 执行冻结金额（单位到元） */
	private String frozedBalance;
	
	/** 币种（CNY-人民币；USD-美元；EUR-欧元；……） */
	private String currency;
	
	/** 账户余额 */
	private String accountBalance;
	
	/** 冻结起始时间（yyyyMMddHHmmss） */
	private String startTime;
	
	/** 冻结结束时间（yyyyMMddHHmmss） */
	private String expireTime;
	
	/** 未能冻结原因 */
	private String failureCause;
	
	/** 在先冻结机关（上一个轮候机关） */
	private String formerApplicationDepartment;
	
	/** 币种（CNY-人民币；USD-美元；EUR-欧元；……） */
	private String formerFrozenCurrency;
	
	/** 在先冻结金额 */
	private String formerFrozenBalance;
	
	/** 在先冻结到期时间（yyyyMMddHHmmss） */
	private String formerFrozenExpireTime;
	
	/** 冻结后账户可用余额 */
	private String accountAvaiableBalance;
	
	/** 反馈说明 */
	private String feedbackRemark;
	
	/** 反馈机构名称 */
	private String feedbackOrgName;
	
	/** 经办人姓名 */
	private String operatorName;
	
	/** 经办人电话 */
	private String operatorPhoneNumber;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	@Override
	public String getTxCode() {
		return TxConstants.TXCODE_FREEZE_FEEDBACK;
	}
	
	public String getApplicationID() {
		return applicationID;
	}
	
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getAppliedBalance() {
		return appliedBalance;
	}
	
	public void setAppliedBalance(String appliedBalance) {
		this.appliedBalance = appliedBalance;
	}
	
	public String getFrozedBalance() {
		return frozedBalance;
	}
	
	public void setFrozedBalance(String frozedBalance) {
		this.frozedBalance = frozedBalance;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getExpireTime() {
		return expireTime;
	}
	
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	
	public String getFailureCause() {
		return failureCause;
	}
	
	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}
	
	public String getFormerApplicationDepartment() {
		return formerApplicationDepartment;
	}
	
	public void setFormerApplicationDepartment(String formerApplicationDepartment) {
		this.formerApplicationDepartment = formerApplicationDepartment;
	}
	
	public String getFormerFrozenCurrency() {
		return formerFrozenCurrency;
	}
	
	public void setFormerFrozenCurrency(String formerFrozenCurrency) {
		this.formerFrozenCurrency = formerFrozenCurrency;
	}
	
	public String getFormerFrozenBalance() {
		return formerFrozenBalance;
	}
	
	public void setFormerFrozenBalance(String formerFrozenBalance) {
		this.formerFrozenBalance = formerFrozenBalance;
	}
	
	public String getFormerFrozenExpireTime() {
		return formerFrozenExpireTime;
	}
	
	public void setFormerFrozenExpireTime(String formerFrozenExpireTime) {
		this.formerFrozenExpireTime = formerFrozenExpireTime;
	}
	
	public String getAccountAvaiableBalance() {
		return accountAvaiableBalance;
	}
	
	public void setAccountAvaiableBalance(String accountAvaiableBalance) {
		this.accountAvaiableBalance = accountAvaiableBalance;
	}
	
	public String getFeedbackRemark() {
		return feedbackRemark;
	}
	
	public void setFeedbackRemark(String feedbackRemark) {
		this.feedbackRemark = feedbackRemark;
	}
	
	public String getFeedbackOrgName() {
		return feedbackOrgName;
	}
	
	public void setFeedbackOrgName(String feedbackOrgName) {
		this.feedbackOrgName = feedbackOrgName;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getOperatorPhoneNumber() {
		return operatorPhoneNumber;
	}
	
	public void setOperatorPhoneNumber(String operatorPhoneNumber) {
		this.operatorPhoneNumber = operatorPhoneNumber;
	}
}
