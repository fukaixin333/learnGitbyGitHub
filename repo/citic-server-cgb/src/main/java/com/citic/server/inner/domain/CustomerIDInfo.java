package com.citic.server.inner.domain;

import lombok.Data;

/**
 * 客户信息-开户证件信息
 * 
 * @author Liu Xuanfei
 * @date 2016年12月16日 上午11:40:43
 */
@Data
public class CustomerIDInfo {
	
	private String idType; // 证件类型
	private String idNumber; // 证件号码
	private String lssueCount; // 签发/换证次数
	private String idExpiringDate; // 证件到期日
	private String idRemark; // 证件备注
	
	private String idZone; // 证件地区码
	private String openFlag; // 开户标识（Y-开户证件；N-非开户证件）
}
