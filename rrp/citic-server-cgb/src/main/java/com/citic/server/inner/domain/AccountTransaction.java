package com.citic.server.inner.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 核心接口 - 交易历史查询（输出）
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午4:39:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountTransaction implements Serializable {
	private static final long serialVersionUID = -3635605979646205182L;
	
	private String tradeDate; // 交易日
	private String accountingDate; // 会计日
	private String logNumber; // 日志号
	private String logSeq; // 日志顺序号
	
	private String accountNumber; // 账号
	
	private String serviceNumber; // 业务编号
	private String tradeTool; // 交易工具/交易介质
	private String appCode; // 应用符
	
	private String tradeCode; // 交易码
	private String tradeBranch; // 交易行所
	private String tradeDepartment; // 交易部门
	private String tradeTeller; // 交易柜员
	private String authorizer1; // 授权人1
	private String authorizer2; // 授权人2
	private String tradeChannel; // 交易渠道
	private String tradeChannelReq; // 交易渠道种类
	private String tradeChannelDetail; // 交易渠道细类
	private String financialFlag; // 金融标志
	private String voucherNo; // 传票号
	private String customerNumber; // 客户号
	private String drcrFlag; // 借贷方向（D-借；C-贷）
	
	private String tradeCurrency; // 交易货币
	private String tradeAmount; // 交易金额
	private String accountBalance; // 账面余额
	private String tradeEffectDate; // 交易生效日期
	private String productCode; // 产品代码
	private String tradeStatus; // 交易状态（N-正常；C-已冲销；R-正常的冲销/抹账交易）
	private String tradeTime; // 交易时间
	private String remark; // 备注
	
	private String tradeType; // 交易类型（未用）
	private String tradeTypeCode; // 业务代码
	private String makerTeller; // 操作柜员
	
	private String relativeNumber; // 对手账号
	private String staffFlag; // 员工标志
	private String relativeName; // 对手户名
	private String voucherCode; // 凭证代码
	private String headNumber; // 冠字号
	private String voucherNumber; // 凭证号
	private String cashExCode; // 钞汇标志（1-钞；2-汇）
	private String relativeBank; // 对手支付行号
	
	/**
	 * @return 'V' + 钞汇标志（1位） + 币种（3位） + 账号
	 */
	public String getV_AccountNumber() {
		if ((accountNumber == null || accountNumber.length() == 0) // 账号
			|| (tradeCurrency == null || tradeCurrency.length() == 0) // 币种（3位）
			|| (cashExCode == null || cashExCode.length() == 0)) { // 钞汇标志（1位）
			return accountNumber;
		}
		return "V" + cashExCode + tradeCurrency + accountNumber; // 'V' + 钞汇标志 + 币种 + 账号
	}
}
