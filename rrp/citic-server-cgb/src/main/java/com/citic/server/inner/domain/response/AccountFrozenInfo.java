package com.citic.server.inner.domain.response;

import java.io.Serializable;

import com.citic.server.inner.domain.ResponseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 冻结明细查询
 * 
 * @author liuying07
 * @date 2017年6月20日 下午6:11:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountFrozenInfo extends ResponseMessage implements Serializable {
	
	private static final long serialVersionUID = 3524772307929430898L;
	
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
	private String frozenChannelMedium; // 发起渠道种类
	private String frozenChannelSmall; // 发起渠道细类
}
