package com.citic.server.inner.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.citic.server.inner.domain.RequestMessage;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/09 17:39:06$
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InputPSB120 extends RequestMessage {
	private static final long serialVersionUID = -8382038298086390129L;
	
	private String tradeType = "2"; // 交易种类 (0-大额；1-小额；2-小额判断)
	private String currentPartition = "G"; // 往来区分(G：外系统提出往帐；C：人行转发来帐)
	private String systemId = "ZGFY"; // 来源系统 (M)
	private String urgent = "0"; // 加急标志
	private String addDataFlag = "0"; // 附加数据标志
	private String addAttachFlag = "0"; // 附加文件标志
	private String serialNum; // 文件流水号
	private String transBranch; // 交易提出行所号(M)
	private String accountBranch; // 交易帐户行所号
	private String userId; // 操作员(M)
	private String checkUserId; // 复核员(M)
	private String feeMode = "0"; // 手续费模式(M)
	private String feeCal = "0"; // 手续费计算
	private String feeAmount = "0"; // 手续费
	private String infoBackFlag = "1"; // 信息返回标志
	private String receiptFlag = "0"; // 人行回执返回标志
	private String externalSystem; // 外系统方必需信息
	private String receiptDate = "0"; // 回执期限（天数）
	private String deductFlag; // 扣款标志
	private String remark; //	备用
	private String businessNum = "00100"; // 业务类型号
	private String senrBankCode; // 发起行行号(M)
	private String recrBankCode; // 接收行行号(M)
	private String entrustDate; // 委托日期(M)
	private String transSerialNum; // 交易流水号(M)
	private String amount; // 金额(M)
	private String debitBankCode; // 付款人开户行行号(M)
	private String debitAccount; // 付款人账号(M)
	private String debitName; // 付款人名称(M)
	private String debitAddress; // 付款人地址
	private String creditBankCode; // 收款人开户行行号(M)
	private String creditAccount; // 收款人账号(M)
	private String creditName; // 收款人名称(M)
	private String creditAddress; // 收款人地址
	private String realTransAccount; // 实际收/付帐号
	private String realTransName; // 实际收/付名称
	private String busiType = "11"; // 业务种类
	private String remark1; // 附言
	private String verifyPwdType = "0"; // 密码校验方式
	private String password; // 帐户密码
	private String noteType; // 票据类型
	private String notePrefix; // 票据前缀
	private String noteNum; // 凭证号码
	private String creditFlag = "2"; // 挂账标识（新核心新增）1-挂账  2-解挂
	private String creditNumber; // 挂账编号（新核心新增）
	private String remark2; //	备用2
	private String feeFlag; //	手续费标志
	private String feeAccount; // 手续费收取账号
	private String freeBrFlag; // 手续费归属标识
	private String freeBranch; // 手续费归属行所号
}
