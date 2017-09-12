package com.citic.server.inner.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 账户明细（子账户基本信息）
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午2:52:41
 */
@Data
public class SubAccountInfo implements Serializable {
	private static final long serialVersionUID = 3578990891377063323L;
	
	private String accountSerial; // 子账户序号
	private String accountNumber; // 实体账号
	private String currency; // 币种
	private String cashExCode; // 钞汇标志（1-钞户；2-汇户）
	private String accountType; // 账户类型（DD-活期；TD-定期）
	private String periodAccountType; // 定期账户类型
	private String depositTerm; // 存期
	private String openDate; // 开户日期/起息日期
	private String expiringDate; // 到期日期
	private String accountBalance; // 分户余额
	private String availableBalance; //	可用余额
	private String financialSharesXF; // 鑫福理财份额
	private String frozenBalance; // 冻结金额
	
	private String depositMethod; // 续存类型
	private String interestRate; // 定期利率
	private String interestRevenue; // 利息收入
	private String expectedInterest; // 预期本息
	
	private String accountStatus; // 分户状态（N-正常；S-睡眠户；C-结清/销户；R-冲正）
	
	/**
	 * @return 'V' + 钞汇标志（1位） + 币种（3位） + 账号
	 */
	public String getV_AccountNumber() {
		if ((accountNumber == null || accountNumber.length() == 0) // 账号
			|| (currency == null || currency.length() == 0) // 币种（3位）
			|| (cashExCode == null || cashExCode.length() == 0) // 钞汇标志（1位）
			|| (accountSerial == null || accountSerial.length() == 0)) { // 子账户序号
			return accountNumber;
		}
		return "V" + cashExCode + currency + accountNumber; // 'V' + 钞汇标志 + 币种 + 账号
		// 如果某一天需要拼接子账户序号时，则采用以下方式：
		//		if (accountSerial == null || accountSerial.length() == 0) {
		//			return "V" + cashExCode + currency + accountNumber; // 'V' + 钞汇标志 + 币种 + 账号
		//		}else{
		//			return accountNumber + "V" + cashExCode + currency + StringUtils.leftPad(accountSerial, 6, '0');
		//		}
	}
}
