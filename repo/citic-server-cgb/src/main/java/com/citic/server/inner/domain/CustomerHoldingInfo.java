package com.citic.server.inner.domain;

import lombok.Data;

/**
 * 客户信息-企业法人、控股人、股东信息
 * 
 * @author Liu Xuanfei
 * @date 2016年12月16日 下午4:08:26
 */
@Data
public class CustomerHoldingInfo {
	
	private String name; // 姓名
	private String idType; // 证件类型
	private String idNumber; // 证件号码
	private String idExpiringDate; // 证件到期日
	private String mobilePhone; // 手机号码
	private String telephone; // 固定电话
	private String sex; // 性别
	private String birthday; // 出生日期
	private String country; // 国籍
	
	private String flag; // 法人代表/单位负责人标志（01-法人代表；02-单位负责人）
}
