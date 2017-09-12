package com.citic.server.inner.domain.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.inner.domain.ResponseMessage;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountVerifyInfo extends ResponseMessage {
	
	private static final long serialVersionUID = -2894437434950843013L;
	
	private String accountNumber; // 	卡号/账号
	private String subAccountNumber; // 	账号
	private String accountSeq; // 	序号
	private String currency; // 	币别
	private String customerNumber; // 	客户号
	private String customerStatus; // 	客户状态
	private String type; // 	客户类型
	private String IDType; // 	证件类型
	private String IDNumber; // 	证件号码
	private String openIDEffectiveDate; // 	开户证件有效日期
	private String customerName; // 	客户名称
	private String customerLevel; // 	客户级别
	private String sex; // 	性别
	private String contactNumber; // 	手机号码
	private String addressType; // 	地址类型
	private String addressFlag; // 	默认地址标识
	private String addressDetail; // 	地址详细信息
	private String addressCountry; // 	国家代码
	private String postalCode; // 	邮政编码
	private String accountProductCode; // 	账户产品码
	private String accountProductName; // 	账户产品名称
	private String accountAttr; // 	账户类型
	private String accountType; // 	账户性质
	private String accountStatus; // 	账户状态
	private String accountStatusWord; // 	账户状态字
	private String accountFrozenStatus; // 	账户冻结状态
	private String amountFrozenStatus; // 	金额冻结状态
	private String openBranch; // 	开户网点
	private String openBank; // 	开户分行
	private String openDate; // 	开户日期
	private String lastTransDate; // 	最后交易日期
	private String accountingBank; // 	账务行
	private String managementBank; // 	管理行
	private String depositPeriod; // 	存期
	private String expirePointing; // 	到期指示
	private String cashExCode; // 	钞汇标志
	private String interestRate; // 	利率值
	private String englishName; // 	账户英文名称
	private String chineseName; // 	账户中文名称
	private String accountBalance; // 	账面余额
	private String availableBalance; // 	可用余额
	private String frozenBalance; // 	冻结金额
	private String treatyDepositFlag; // 	协定存款标志
	private String treatyInterestRate; // 	协定利率
	private String treatyAmount; // 	协定金额
	private String drawType; // 	支取方式
	private String drcrFlag; // 	借贷记标志
	private String crosDrawFlag; // 	通兑标志
	private String crosDepositFlag; // 	通存标志
	private String passbookStatus; // 	存折挂失状态
	private String cardMedium; //  	卡介质类型
	private String cardStatus; // 	卡介质状态
	private String cardUseStatus; // 	卡使用状态
	private String cradProduct; // 	卡产品
	private String cradProductName; // 	卡产品名称
	private String cardJointlySign; // 	卡联名标志
	private String cardPcType; // 	卡归属类别
	private String cardPvType; // 	卡物理属性
	private String cardMobileType; // 	支持移动支付标识
	private String cardLinkType; // 	主副卡标志
	private String cardCategory; // 	卡类
	private String cardOpenBranch; // 	开卡网点
	private String cardOpenBranchName; // 	开卡网点名称
	private String cardOpenDate; // 	开卡日期
	private String cardExpiringDate; // 	失效日期
	private String item; // 	所属科目
	private String accountFlag; // 	账号标志
	private String unstandardType; // 	非标准账号类型
	private String mainAccountNumber; // 	主账号
	private String standardAccountNumber; // 	标准账号
	private String specialKind; // 	专用户资金性质
	private String foreignLabel; // 	外汇属性标注
	private String businessLicense; // 	企业营业执照
	private String clientStsw; // 	客户状态字
	private String socialFlag; // 	社保金融卡标识
	private String accountPurpose; // 	账户用途
	private String relationShipType; // 	对公关联人类型
	private String relationShipName; // 	对公关联人名称
	private String depositPeriodFlag; // 	定期账户凭证类型
	private String deposiPledgeFlag; // 	定期存单质押状态
	private String cardDirectionalAccountFlag; // 	卡定向账户交易标志 
	private String reservedFlag4; // 	预留标识位4
	private String reservedFlag5; // 	预留标识位5
	private String reserved; // 	预留栏位
	private String foreignAccountType; // 	对公外汇账户类型
	private String foreignAccountCode; // 	对公外汇账户性质代码
	private String accountClass; // 	账户分类
	private String remark; // 	预留栏位
	private String nonstandardAccountCount; // 	非标账号个数
	private List<NonstandardAccount> nonstandards; // 	非标账号数组

}
