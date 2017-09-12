package com.citic.server.inner.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 客户变更信息
 * 
 * @author liuxuanfei
 * @date 2016年5月4日 下午4:17:30
 */
@Data
public class CustomerUpdateInfo implements Serializable {
	private static final long serialVersionUID = -5028572034149012557L;
	
	private String CustomerNumber; // 客户编号
	
	private String idType; // 证件类型
	private String newIDNumber; // 新证件号码
	private String newIDOpenFlag; // 新证件是否开户证件标志（Y-是；N-否）
	private String oldIDNumber; // 旧证件号码	
	private String oldIDOpenFlag; // 旧证件是否开户证件标志（Y-是；N-否）
	private String idUpdateType; // 证件变更类型（A-新增；M-修改；D-删除）
	private String idUpdateDate; // 证件变更日期
	private String idUpdateBranch; // 证件变更机构
	private String idUpdateTeller; // 证件变更柜员
	
	private String nameType; // 名称类型（01-中文名称；02-拼音名；03-英文名称）
	private String newCustomerName; // 新客户名称
	private String newNameOpenFlag; // 新名称开户标志（Y-是；N-否）
	private String oldCustomerName; // 旧客户名称
	private String oldNameOpenFlag; // 旧名称开户标志（Y-是；N-否）
	private String nameUpdateType; // 名称变更类型
	private String nameUpdateDate; // 名称变更日期
	private String nameUpdateBranch; // 名称变更机构
	private String nameUpdateTeller; // 名称变更柜员
	
	/**
	 * 13-联络手机号码；14-传真；15-安全手机号码；16-固定电话；<br>
	 * 22-QQ；25-微信；26-易信；<br>
	 * 31-电子邮箱；99-其他联系方式；
	 */
	private String newContactType; // 新联系方式类型
	private String newContactInfo; // 新联系方式
	private String oldContactType; // 旧联系方式类型
	private String oldContactInfo; // 旧联系方式
	private String contactUpdateType; // 联系方式变更类型（A-新增；M-修改；D-删除）
	private String contactUpdateDate; // 联系方式变更日期
	private String contactUpdateBranch; // 联系方式变更机构
	private String contactUpdateTeller; // 联系方式变更柜员
	
	/**
	 * 114-常住地址；120-通讯地址；130-证件地址；<br>
	 * 210-注册地址；199-其它地址
	 */
	private String newAddressType; // 新地址类型
	private String newAddressInfo; // 新地址信息
	private String oldAddressType; // 旧地址类型
	private String oldAddressInfo; // 新地址信息
	private String addressUpdateType; // 地址变更类型（A-新增；M-修改；D-删除）
	private String addressUpdateDate; // 地址变更日期
	private String addressUpdateBranch; // 地址变更机构
	private String addressUpdateTeller; // 地址变更柜员
}