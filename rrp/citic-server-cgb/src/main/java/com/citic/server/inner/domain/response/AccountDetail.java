package com.citic.server.inner.domain.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.inner.domain.ResponseMessage;

/**
 * 账户信息（358080）
 * 
 * @author liuxuanfei
 * @date 2016年5月4日 上午11:53:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountDetail extends ResponseMessage {
	private static final long serialVersionUID = 7791427398695936037L;
	
	private String accountNumber; // 卡号/账号
	private String currency; // 币种
	private String cashExCode; // 钞汇标志
	
	private String customerNumber; // 客户号
	private String customerStatus; // 客户状态（0-正常；1-暂禁；2-销户）
	private String customerType; // 客户类型
	private String idType; // 证件类型
	private String idNumber; // 证件号码
	private String customerName; // 客户名称
	private String customerLevel; // 客户级别
	private String sex; // 性别
	
	private String accountProductCode; // 账户产品码
	private String accountAttr; // 账户类型（11-活期；12-定期；26-借记卡；01-内部户）
	private String accountType; // 账户性质
	private String accountStatus; // 账户状态
	private String accountOpenBranch; // 开户网点
	private String accountOpenBank; // 开户分行
	private String accountEnName; // 账户英文名称
	private String accountCnName; // 账户中文名称
	private String accountBalance; // 账面余额
	private String availableBalance; // 可用余额
	private String frozenBalance; // 冻结金额
	private String drcrFlag; // 借贷记标志
	
	private String passbookStatus; // 存着挂失状态（N-正常；U-口头挂失；W-正式挂失）
	private String cardMedium; // 卡介质类型（1-磁条卡；2-IC卡；3-IC/磁条卡）
	private String cardStatus; // 卡介质状态（0-未制卡；1-制卡中；2-已制卡；N-正常；C-销卡状态）
	private String cardUseStatus; // 卡使用状态
	private String cardProduct; // 卡产品
	private String cardJointlySign; // 卡联名标志
	private String cardPcType; // 卡归属类别（P-个人卡；C-公司卡）
	private String cardPvType; // 卡物理属性（P-物理卡；V-虚拟卡）
	private String cardMobileType; // 卡支持移动支付标识（Y-支持；N-不支持）
	private String cardLinkType; // 主副卡标志（0-无主附卡关联；1-主卡关联；2-附卡关联）
	private String cardCategory; // 卡类
	private String cardOpenBranch; // 开卡网点
	private String cardOpenDate; // 开卡日期
	private String cardExpiringDate; // 失效日期
	
	private String accountFlag; // 账号标志（0-非标准账号；1-标准账号且有非标账号；2-标准账号且无非标账号）
	private String unstandardType; // 非标准账户类型
	private String standardAccount; // 标准账号
	private String foreignLabel; // 外汇属性标注
	private String specialKind; // 专用户资金性质
	
	private String accountClass; // 账户分类（1-Ⅰ类账户；2-Ⅱ类账户；3-Ⅲ类账户）
	private String defaultSubAccount; // 默认实体账号
	private String lastTransDate; // 最后交易日期
	private String accountClosingDate; // 销户日期
	private String accountClosingBranch; // 销户行所
	
	/**
	 * @return 'V' + 钞汇标志（1位） + 币种（3位） + 账号
	 */
	public String getV_AccountNumber() {
		if ((accountNumber == null || accountNumber.length() == 0) // 账号
			|| (currency == null || currency.length() == 0) // 币种（3位）
			|| (cashExCode == null || cashExCode.length() == 0)) { // 钞汇标志（1位）
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
