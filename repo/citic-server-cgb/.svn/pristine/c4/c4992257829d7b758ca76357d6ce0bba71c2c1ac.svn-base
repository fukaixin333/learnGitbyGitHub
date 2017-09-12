package com.citic.server.inner.domain.response;

import java.util.List;

import com.citic.server.inner.domain.CustomerAddressInfo;
import com.citic.server.inner.domain.CustomerContactInfo;
import com.citic.server.inner.domain.CustomerHoldingInfo;
import com.citic.server.inner.domain.CustomerIDInfo;
import com.citic.server.inner.domain.ResponseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 核心接口 - 公司同业客户信息查询（输出）
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午4:43:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CorporateCustomer extends ResponseMessage {
	private static final long serialVersionUID = -5698724355904794624L;
	
	private String customerNumber; // 客户编号
	private String customerType; // 客户类型（2-对公客户；3-同业客户）
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
	
	private List<CustomerIDInfo> identificationInfos; // 证件信息
	
	private String customerStatus; // 客户状态（00-正常；01-暂禁；02-销户）
	private String customerSubType; // 客户细分（01-中资机构；02-外资机构；98-未知）
	private String residentFlag; // 居民性质（1-居民；2-非居民；9-其他；0-未知）
	
	private String interType; // 同业标志（01-完整信息；02-存放同业信息）
	private String financialType; // 金融机构类型
	private String organizationType; // 组织机构类型
	private String economyType; // 经济类型
	private String holeEconmyType; // 控股经济类型
	private String industry; // 所属行业
	
	private String encType; // 特殊经济区（Exclusive Economic Zone）内企业类型
	private String nedType; // 国民经济部门
	
	private String sidFlag; // 客户境内外标志（1-境内；2-境外）
	private String size; // 企业规模（国标）
	private String inSize; // 企业规模（行标）
	private String bussineScope; // 经营范围
	private String regDate; // 注册日期
	private String regCurrency; // 注册资本币种
	private String regAmount; // 注册资本金额
	private String casFlag; // 银企对账方式（1-按账户对账；2-按客户对账）
	private String hqCountry; // 总部国别
	private String pubFlag; // 公共部门实体（1-是；0-非）
	private String fatcaType; // FATCA客户类别
	
	// 联系方式信息
	private List<CustomerContactInfo> contactInfoList;
	// 地址信息
	private List<CustomerAddressInfo> addressInfoList;
	
	private CustomerHoldingInfo legalInfo; // 法人代表/单位负责人信息
	private CustomerHoldingInfo actualHolderInfo; // 实际控股人信息
	private CustomerHoldingInfo shareHolderInfo; // 控股股东信息
	
	// ==========================================================================================
	//                     Helper Fields
	// ==========================================================================================
	private String openIDType;
	private String openIDNumber;
	
	private String lpriIDNumber; // 事业法人证（Legal Person's Registration of Institutions，事业单位法人登记证书）
	private String busiIDNumber; // 工商登记证
	private String stateTaxIDNumber; // 税务登记证（国税）
	private String localTaxIDNumber; // 税务登记证（地税）
	private String unifiedSocialCreditCode; // 统一社会信用代码（Unified social credit code）
	
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
