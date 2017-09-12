package com.citic.server.dx.domain;

/**
 * 账户冻结核心（反馈）
 * 
 * @author dingke
 * @date 2016年4月12日 下午2:36:19
 */

public class Br25_Freeze_back extends Br24_bas_dto {
	private static final long serialVersionUID = 3607614063698184624L;
	
	/** 交易类型编码 */
	private String txCode;
	
	/** 传输报文流水号 */
	private String transSerialNumber;
	
	/** 业务申请编号 */
	private String applicationID;
	/** 冻结结果（4位业务应答码） */
	private String result = "0000";
	
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
	private String endTime;
	
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
	
	//-----冻结解除
	
	/** 解除冻结金额（单位到元） */
	private String unfreezeBalance;
	
	/** 冻结解除生效时间（yyyyMMddHHmmss） */
	private String withdrawalTime;
	
	//--------其他字段
	/** 反馈时间 */
	private String feedback_dt = "";
	
	/** 0未处理 1已处理 2 已发送 3成功 4失败 */
	private String status_cd = "0";
	
	/** 核心冻结编号 */
	private String hxappid = "";
	
	private String toorg = "";
	
	private String qrydt = "";
	
	private String orgkey = "";
	
	private String freezeType = "";
	
	private String modeid = "";
	
	private String last_up_dt = "";
	
	private String msgcheckresult = "";
	
	private String mode = "01";
	private String to = "111111000000";
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	//------------------------------------------------------	
	
	public String getMsgcheckresult() {
		return msgcheckresult;
	}
	
	public void setMsgcheckresult(String msgcheckresult) {
		this.msgcheckresult = msgcheckresult;
	}
	
	public String getLast_up_dt() {
		return last_up_dt;
	}
	
	public void setLast_up_dt(String last_up_dt) {
		this.last_up_dt = last_up_dt;
	}
	
	public String getModeid() {
		return modeid;
	}
	
	public void setModeid(String modeid) {
		this.modeid = modeid;
	}
	
	public String getFreezeType() {
		return freezeType;
	}
	
	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}
	
	public String getOrgkey() {
		return orgkey;
	}
	
	public void setOrgkey(String orgkey) {
		this.orgkey = orgkey;
	}
	
	public String getQrydt() {
		return qrydt;
	}
	
	public void setQrydt(String qrydt) {
		this.qrydt = qrydt;
	}
	
	public String getResult() {
		return result;
	}
	
	public String getAccounttype() {
		return accountType;
	}
	
	public String getAccountnumber() {
		return accountNumber;
	}
	
	public String getCardnumber() {
		return cardNumber;
	}
	
	public String getAppliedbalance() {
		return appliedBalance;
	}
	
	public String getFrozedbalance() {
		return frozedBalance;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public String getAccountbalance() {
		return accountBalance;
	}
	
	public String getStarttime() {
		return startTime;
	}
	
	public String getEndtime() {
		return endTime;
	}
	
	public String getFailurecause() {
		return failureCause;
	}
	
	public String getFormerapplicationdepartment() {
		return formerApplicationDepartment;
	}
	
	public String getFormerfrozencurrency() {
		return formerFrozenCurrency;
	}
	
	public String getFormerfrozenbalance() {
		return formerFrozenBalance;
	}
	
	public String getFormerfrozenexpiretime() {
		return formerFrozenExpireTime;
	}
	
	public String getAccountavaiablebalance() {
		return accountAvaiableBalance;
	}
	
	public String getFeedbackremark() {
		return feedbackRemark;
	}
	
	public String getFeedbackorgname() {
		return feedbackOrgName;
	}
	
	public String getOperatorname() {
		return operatorName;
	}
	
	public String getOperatorphonenumber() {
		return operatorPhoneNumber;
	}
	
	public String getUnfreezebalance() {
		return unfreezeBalance;
	}
	
	public String getWithdrawaltime() {
		return withdrawalTime;
	}
	
	public String getFeedback_dt() {
		return feedback_dt;
	}
	
	public String getTxcode() {
		return txCode;
	}
	
	public String getTransserialnumber() {
		return transSerialNumber;
	}
	
	public String getApplicationid() {
		return applicationID;
	}
	
	public String getTxCode() {
		return txCode;
	}
	
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	
	public String getTransSerialNumber() {
		return transSerialNumber;
	}
	
	public void setTransSerialNumber(String transSerialNumber) {
		this.transSerialNumber = transSerialNumber;
	}
	
	public String getApplicationID() {
		return applicationID;
	}
	
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
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
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	
	public String getUnfreezeBalance() {
		return unfreezeBalance;
	}
	
	public void setUnfreezeBalance(String unfreezeBalance) {
		this.unfreezeBalance = unfreezeBalance;
	}
	
	public String getWithdrawalTime() {
		return withdrawalTime;
	}
	
	public void setWithdrawalTime(String withdrawalTime) {
		this.withdrawalTime = withdrawalTime;
	}
	
	public String getStatus_cd() {
		return status_cd;
	}
	
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	
	public String getHxappid() {
		return hxappid;
	}
	
	public void setHxappid(String hxappid) {
		this.hxappid = hxappid;
	}
	
	public String getToorg() {
		return toorg;
	}
	
	public void setToorg(String toorg) {
		this.toorg = toorg;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public void setFeedback_dt(String feedback_dt) {
		this.feedback_dt = feedback_dt;
	}
	
}
