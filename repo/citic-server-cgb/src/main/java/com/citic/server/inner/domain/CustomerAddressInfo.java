package com.citic.server.inner.domain;

import lombok.Data;

/**
 * 客户信息-地址信息
 * 
 * @author Liu Xuanfei
 * @date 2016年12月16日 下午12:08:30
 */
@Data
public class CustomerAddressInfo {
	
	/**
	 * 114-常住地址；120-通讯地址；130-证件地址；<br>
	 * 210-注册地址；199-其它地址
	 */
	private String addressType; // 地址类型
	private String srcSysNo; // 来源系统编号
	private String standardFlag; // 是否标准化（Y-是；N-否）
	private String standardType; // 标准化类型（C-中文；E-英文）
	private String addressCountry; // 国家
	private String addressProvince; // 地址-省/自治区/直辖市
	private String addressCity; // 地址-市/地区
	private String addressTown; // 地址-县（区）
	private String addressDetail; // 详细地址
	private String postalCode; // 邮政编码
}
