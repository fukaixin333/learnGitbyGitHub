package com.citic.server.inner.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 合约账户（首层账户）基本信息
 * 
 * @author liuxuanfei
 * @date 2016年5月6日 下午4:24:29
 */
@Data
public class ContractAccount implements Serializable {
	private static final long serialVersionUID = 7697469419806305400L;
	
	/**
	 * 0-客户；1-账号；2-卡；3-VA账户（主账户）；<br>
	 * 4-合约；5-IA账户（综合管理账号）
	 */
	private String entityType; // 账号合约实体类型
	private String accountNumber; // 账号合约实体编号（产品合约业务号）
	
	private String productCode; // 产品码
	private String productName; // 产品名称
	
	private String contractType; // 合约类型（CLDD、CLGU、CLDC）
	private String fromApp; // 归属应用（DD、TD）
	private String status; // 状态（0-正常；1-销户）
	private String currency; // 币种
	private String openIns; // 开户机构
	private String belongIns; // 归属机构
	private String openDate; // 签发日期/开户日期
	
	private String customerNumber; // 客户号
	private String chineseName; // 账户开户名称（中文）
	private String englishName; // 账户辅助名称（英文）
}
