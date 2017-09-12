package com.citic.server.inner.domain.response;

import com.citic.server.inner.domain.ResponseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 核心接口 - 账户解冻、解除止付（输出）
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午4:50:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UnfreezeResult extends ResponseMessage {
	private static final long serialVersionUID = -7072422532791232093L;
	
	private String frozenNumber; // 冻结编号
	private String accountNumber; // 账号
	private String frozenType; // 冻结方式
	private String frozenInsType; // 发起机构类型
	private String currency; // 货币
	private String cashExCode; // 钞汇标识
	private String frozenAmount; // 原冻结金额
	private String frozenEffDate; // 原生效日期
	private String frozenExpDate; // 到期日期
	private String unfreezeType; // 解冻方式
	private String unfreezeAmount; // 解冻金额
	private String frozenBalance; // 剩余冻结金额
	private String unfreezeBookNumber; // 变动文书号
	private String unfreezeInsName; // 冻结机构名称
	private String unfreezeReason; // 变动原因
	private String remark; // 备注
	private String unfreezeBranch; // 发起行所
	
	private String lawName1; // 执法人名称1
	private String lawIDNumber1; // 执法人证件号1
	private String lawName2; // 执法人名称2
	private String lawIDNumber2; // 执法人证件号2
	
	private String accountType; // 账户性质
	private String accountBalance; // 账户余额
	private String availableAmount; // 可用余额
}
