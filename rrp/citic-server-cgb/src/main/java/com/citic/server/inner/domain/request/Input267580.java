package com.citic.server.inner.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.inner.domain.RequestMessage;

@Data
@EqualsAndHashCode(callSuper = false)
public class Input267580 extends RequestMessage {
	private static final long serialVersionUID = 1L;
	
	private String deductWay = "2"; //	扣划方式(1-直接扣划(司法)；2-解冻扣划；3-银行内部直接扣划)
	private String accountNumber; // 账号/卡号
	private String accountSerial; // 账户序号
	private String frozenNumber; // 冻结编号(扣划方式为2解冻扣划时必输(0-保持冻结，1-解冻))
	private String currency; // 货币
	private String cashExCode; // 钞汇标识
	private String deductAmount; // 扣划金额
	private String freezeFlag = "0"; // 剩余冻结处理方式(0-保持冻结，1-解冻对于金额冻结记录进行扣划时必输)
	private String freezeBookNumber; // 变动文书号
	private String freezeInsName; // 冻结机构名称
	private String freezeReason; // 变动原因
	private String moneyFlow; // 资金去向 (1-行内转账；2-他行汇款)
	private String billingAccount; // 入账账户
	private String interestAccount; // 收息账户
	private String remark; // 备注
	private String freezeBranch; // 发起行所
	private String lawName1; // 执法人名称1
	private String lawIDNumber1; // 执法人证件号1
	private String lawName2; // 执法人名称2
	private String lawIDNumber2; // 执法人证件号2
	private String narrative; // 附言
	private String rivalAccount; // 对手账号
	private String rivalAccountName; // 对手账号名称
	private String rivalBankNumber; // 对手支付行号
	private String rivalBusiNumber; // 对手业务编号
	private String rivalCurrency; // 对手货币
}
