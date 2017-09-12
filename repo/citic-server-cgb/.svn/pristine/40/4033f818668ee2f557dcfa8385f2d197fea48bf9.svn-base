package com.citic.server.inner.domain.response;

import com.citic.server.inner.domain.ResponseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 核心接口 - 冻结续冻、止付延期（输出）
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午4:52:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeferFreezeResult extends ResponseMessage {
	private static final long serialVersionUID = 3663955910086028447L;
	
	private String frozenNumber; // 冻结编号
	private String accountNumber; // 账号
	private String frozenType; // 冻结方式
	private String frozenInsType; // 发起机构类型
	private String currency; // 货币
	private String cashExCode; // 钞汇标识
	private String frozenAmount; // 冻结金额
	private String effectiveDate; // 生效日期
	private String expiringDate; // 原到期日期
	private String newExpiringDate; // 新到期日期
	private String deferBookNumber; // 变动文书号
	private String frozenInsName; // 冻结机构名称
	private String frozenReason; // 变动原因
	private String remark; // 备注
	private String frozenBranch; // 发起行所
	
	private String lawName1; // 执法人名称1
	private String lawIDNumber1; // 执法人证件号1
	private String lawName2; // 执法人名称2
	private String lawIDNumber2; // 执法人证件号2
	
	private String accountType; // 账户性质
	private String accountBalance; // 账户余额
	private String availableAmount; // 可用余额
}
