package com.citic.server.inner.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 在先冻结信息
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午4:48:26
 */
@Data
public class AccountFrozenMeasure implements Serializable {
	private static final long serialVersionUID = -5301815445944418675L;
	
	private String frozenNumber; // 冻结编号
	private String accountNumber; // 账号
	private String frozenType; // 冻结方式（1-账户冻结；2-金额冻结；3-圈存；4-受托支付）
	private String frozenInsType; // 1-权利机关；2-银行内部
	private String currency; // 币种
	private String cashExCode; // 钞汇标志
	private String frozenAmount; // 冻结金额
	private String frozenBookNumber; // 冻结文书编号
	private String frozenInsName; // 冻结机构名称
	private String waitingSeq; // 轮候次序
	private String effectiveDate; // 生效日期
	private String expiringDate; // 到期日期
	private String frozenReason; // 冻结原因
	private String remark; // 备注
	
	private String frozenBranch; // 发起行所
	private String lawName1; // 执法人名称
	private String lawIDNumber1; // 执法人证件号
	private String lawName2; // 执法人名称
	private String lawIDNumber2; // 执法人证件号
	
	private String accountType; // 账户类型（1-VIA账户；2-实体账户）
	private String frozenChannel; // 发起渠道
	private String frozenStatus; // 冻结状态（N-生效；C-解除）

}