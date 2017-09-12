/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/25 09:11:14$
 */
package com.citic.server.cgb.domain.request;

import com.citic.server.cgb.domain.GatewayHeader;

/**
 * 综合理财系统固定报文头
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 09:11:14$
 */
public abstract class FinancialMessageInput extends GatewayMessageInput {
	
	/** key_FunctionId: 交易代码 */
	public static final String key_FunctionId = "FunctionId";
	/** key_ExSerial: 发起方流水号 */
	public static final String key_ExSerial = "ExSerial";
	/** key_BankNo: 银行编号（00默认为本行） */
	public static final String key_BankNo = "BankNo";
	/** getBranchNumber: 交易机构 (账号所属行所) */
	public static final String key_BranchNumber = "BranchNo";
	/** key_Channel: 交易渠道（固定渠道号：G） */
	public static final String key_Channel = "Channel";
	/** key_TermNo: 终端代码 */
	public static final String key_TermNo = "TermNo";
	/** key_OperNo: 交易柜员（自动 用虚拟号） */
	public static final String key_OperNo = "OperNo";
	/** key_AuthOper: 授权柜员 */
	public static final String key_AuthOper = "AuthOper";
	/** key_AuthPwd: 授权密码 */
	public static final String key_AuthPwd = "AuthPwd";
	/** key_TransDate: 交易日期 */
	public static final String key_TransDate = "TransDate";
	/** key_TransTime: 交易时间 */
	public static final String key_TransTime = "TransTime";
	/** key_PrdType: 产品类别 */
	public static final String key_PrdType = "PrdType";
	/** key_Reserve: 交易附加信息 */
	public static final String key_Reserve = "Reserve";
	/** key_Reserve1: 附加信息1 */
	public static final String key_Reserve1 = "Reserve1";
	
	@Override
	public void initDefaultHeader() {
		putHeader(key_BankNo, "00"); // 银行编号（00默认为本行）
		putHeader(key_Channel, "G"); //  交易渠道（固定渠道号：G）
		putHeader(key_OperNo, "AAAE0177"); // 交易柜员（自动 用虚拟号）
		//		putHeader(key_TermNo, ""); // 终端代码
		//		putHeader(key_AuthOper, ""); // 授权柜员
		//		putHeader(key_AuthPwd, ""); // 授权密码
		//		putHeader(key_TransDate, ""); //交易日期
		//		putHeader(key_TransTime, ""); // 交易时间
		//		putHeader(key_PrdType, ""); // 产品类别
		//		putHeader(key_Reserve, ""); // 交易附加信息
		//		putHeader(key_Reserve1, ""); // 附加信息1
	}
	
	@Override
	public GatewayHeader beforeCreateGatewayRequest(GatewayHeader gatewayHeader) {
		putHeader(key_FunctionId, gatewayHeader.getTradeCode()); // 交易代码
		putHeader(key_ExSerial, gatewayHeader.getSenderSN()); // 发起方流水号
		
		return super.beforeCreateGatewayRequest(gatewayHeader);
	}
	
	public String getBranchNumber() {
		return getHeaderValue(key_BranchNumber);
	}
	
	public void setBranchNumber(String branchNumber) {
		putHeader(key_BranchNumber, branchNumber);
	}
}
