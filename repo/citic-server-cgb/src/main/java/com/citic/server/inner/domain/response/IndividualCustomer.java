package com.citic.server.inner.domain.response;

import java.util.List;

import com.citic.server.inner.domain.CustomerAddressInfo;
import com.citic.server.inner.domain.CustomerContactInfo;
import com.citic.server.inner.domain.CustomerIDInfo;
import com.citic.server.inner.domain.ResponseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 核心接口 - 个人客户信息查询（输出）
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午4:41:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IndividualCustomer extends ResponseMessage {
	private static final long serialVersionUID = 1786034600070569799L;
	
	private String customerNumber; // 客户编号
	private String customerType; // 客户类型（1-个人客户）
	/**
	 * 1-正常客户；2-一次性金融客户；3-风险测评客户；<br>
	 * 4-潜在客户；5-特殊客户；6-验资客户；<br>
	 * 9-其他；0-未知
	 */
	private String customerAttr; // 客户性质
	private String openBank; // 开户行所
	private String nameTypeCN; // 名称类型（默认01-中文名称）
	private String chineseName; // 中文名称
	private String nameTypeEN; // 名称类型（默认03-英文名称）
	private String englishName; // 英文名称
	
	private CustomerIDInfo openIDInfo; // 开户证件信息
	private CustomerIDInfo auxiliaryIDInfo1; // 辅助证件信息
	private CustomerIDInfo auxiliaryIDInfo2; // 辅助证件信息
	
	private String customerStatus; // 客户状态（00-正常；01-暂禁；02-销户）
	private String customerSubType; // 客户细分（01-法人、02-自然人、03-个体工商户、04-其他组织、00-未知、99－其他）
	private String residentFlag; // 居民性质（1-居民；2-非居民；9-其他；0-未知）
	private String sex; // 性别（1-未知；2-男；3-女；9-未说明）
	private String maritalStatus; // 婚姻状况（10-未婚；20-已婚；99-未说明）
	private String birthday; // 客户出生日期
	private String citizenship; // 国籍
	private String nationality; // 民族
	private String occupation; // 职业
	
	private String staffFlag; // 员工标志（Y-是；N-否）
	private String payFlag; // 代发工资客户标识（Y-是；N-不是）
	private String payCustomerName; // 代发工资客户名称
	private String customerLevel; // 客户等级
	private String fatcaType; // FATCA客户类别（1-美国个人客户；2-非美国个人客户；3-顽固账户持有人；4-联名账户）
	
	// 联系方式信息
	private List<CustomerContactInfo> contactInfoList;
	// 地址信息
	private List<CustomerAddressInfo> addressInfoList;
	
	// ==========================================================================================
	//                     Helper Fields
	// ==========================================================================================
	private String telephoneNumber; // 联络手机号码
	private String faxNumber; // 传真号码
	private String mobilePhoneNumber; // 安全手机号码
	private String fixedLineNumber; // 固定电话号码
	private String tencentQQ; // QQ
	private String tencentWeix; // 微信
	private String yiXin; // 易信
	private String emailAddress; // 电子邮箱
	
	private String permanentAddress; // 常住地址
	private String mailingAddress; // 通讯地址
	private String idAddress; // 证件地址
	private String registeredAddress; // 注册地址
	private String otherAddress; // 其它地址
}
