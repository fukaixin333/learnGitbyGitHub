package com.citic.server.inner.domain.response;

import com.citic.server.inner.domain.ResponseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户冻结、止付结果信息
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午4:49:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FreezeResult extends ResponseMessage {
	private static final long serialVersionUID = 5532875826023679332L;
	
	private String frozenNumber; // 冻结编号
	private String accountNumber; // 账号
	private String accountSeq; // 账户序号
	private String chequeNumber; // 支票号码
	private String freezeType; // 冻结方式
	private String freezeInsType; // 发起机构类型
	private String currency; // 货币
	private String cashExCode; // 钞汇标识
	private String freezeAmount; // 冻结金额
	private String freezeBookNumber; // 冻结文书号
	private String freezeInsName; // 冻结机构名称
	private String freezeReason; // 冻结原因
	private String effectiveDate; //生效日期
	private String expiringDate; // 到期日期
	private String remark; // 备注
	private String freezeBranch; // 发起行所
	
	private String lawName1; // 执法人名称1
	private String lawIDNumber1; // 执法人证件号1
	private String lawName2; // 执法人名称2
	private String lawIDNumber2; // 执法人证件号2
	
	private String frozenAmount; // 已冻结金额（实际冻结金额）
	private String unfrozenAmount; // 未冻结金额（超额冻结金额）
	private String availableAmount; // 可用余额
	private String accountBalance; // 账户余额
	private String accountType; // 账户性质
}
