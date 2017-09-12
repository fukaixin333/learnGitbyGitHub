package com.citic.server.dx.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * 查询类上行（反馈）报文 - 卡/折信息
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午2:48:42
 */
public class QueryRequest_Accounts implements Serializable {
	private static final long serialVersionUID = -7760973237023563747L;
	
	/** 主账户名称（基本账户帐户名称） */
	private String accountName;
	
	/** 主账户（对私-卡/折号；对公-基本账户帐号） */
	private String cardNumber;
	
	/** 开户网点 */
	private String depositBankBranch;
	
	/** 开户网点代码 */
	private String depositBankBranchCode;
	
	/** 开户日期（yyyyMMddhhmmss） */
	private String accountOpenTime;
	
	/** 销户日期（yyyyMMddhhmmss） */
	private String accountCancellationTime;
	
	/** 销户网点 */
	private String accountCancellationBranch;
	
	/** 备注 */
	private String remark;
	
	/** 账户及交易明细 */
	private List<QueryRequest_Account> list;
	
	// ==========================================================================================
	//                     Getter and Setter
	// ==========================================================================================
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getDepositBankBranch() {
		return depositBankBranch;
	}
	
	public void setDepositBankBranch(String depositBankBranch) {
		this.depositBankBranch = depositBankBranch;
	}
	
	public String getDepositBankBranchCode() {
		return depositBankBranchCode;
	}
	
	public void setDepositBankBranchCode(String depositBankBranchCode) {
		this.depositBankBranchCode = depositBankBranchCode;
	}
	
	public String getAccountOpenTime() {
		return accountOpenTime;
	}
	
	public void setAccountOpenTime(String accountOpenTime) {
		this.accountOpenTime = accountOpenTime;
	}
	
	public String getAccountCancellationTime() {
		return accountCancellationTime;
	}
	
	public void setAccountCancellationTime(String accountCancellationTime) {
		this.accountCancellationTime = accountCancellationTime;
	}
	
	public String getAccountCancellationBranch() {
		return accountCancellationBranch;
	}
	
	public void setAccountCancellationBranch(String accountCancellationBranch) {
		this.accountCancellationBranch = accountCancellationBranch;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<QueryRequest_Account> getList() {
		return list;
	}
	
	public void setList(List<QueryRequest_Account> list) {
		this.list = list;
	}
}
