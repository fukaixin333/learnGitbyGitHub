package com.citic.server.cgb.domain.request;

public class ExternalTransferInput extends UnifiedPaymentMessageInput {
	
	private final String key_RouteInd = "routeInd";// 路由标识
	private final String key_PayPath = "payPath"; // 通道代码
	private final String key_ProdPriCode = "prodPriCode"; // 支付产品大类代码
	private final String key_ProdSecCode = "prodSecCode"; // 支付产品小类代码
	private final String key_BusiType = "busiType"; // 标准业务类型
	private final String key_BusiKind = "busiKind"; // 标准业务种类
	private final String key_AcctMeth = "acctMeth"; // 核算方式
	private final String key_AcctBrno = "acctBrno"; // 核算机构
	private final String key_TaskId = "taskId"; // 任务号
	private final String key_Currency = "currency"; // 币种
	private final String key_CashExCode = "cashExInd"; // 钞汇标识
	private final String key_Amount = "amount"; // 交易金额 
	private final String key_Priority = "priority"; // 业务优先级
	private final String key_PayerBank = "payerBank"; // 付款行号
	private final String key_PayerAcct = "payerAcct"; // 付款人账号
	private final String key_PayerName = "payerName"; // 付款人名称
	private final String key_PayerAddr = "payerAddr"; // 付款人地址
	private final String key_CusVouchChkInd = "cusVouchChkInd"; // 是否验证凭证
	private final String key_CusVoucherType = "cusVoucherType"; // 付款人凭证类型
	private final String key_CusVouchno = "cusVouchno"; // 付款人凭证号码
	private final String key_CusVouchDate = "cusVouchDate"; // 付款人凭证日期
	private final String key_CusVouchPwdInd = "cusVouchPwdInd"; // 支付密码校验标识
	private final String key_CusVouchPwd = "cusVouchPwd"; // 支付密码
	private final String key_PayerMediaType = "payerMediaType"; // 付款方介质类型
	private final String key_CertMeth = "certMeth"; // 付款认证方式
	private final String key_Password = "password"; // 密码
	private final String key_Track2 = "track2"; // 磁道信息（二磁）
	private final String key_Track3 = "track3"; // 磁道信息（三磁）
	private final String key_IcCardInfo23 = "icCardInfo23"; // 付款方IC卡23域信息
	private final String key_IcCardInfo55 = "icCardInfo55"; // 付款方IC卡55域信息
	private final String key_RealPayerAcct = "realPayerAcct"; // 实际付款人账号
	private final String key_RealPayerName = "realPayerName"; // 实际付款人名称 
	private final String key_CustNo = "custNo"; // 客户号
	private final String key_PayeeBank = "payeeBank"; // 接收行行号
	private final String key_PayeeAcct = "payeeAcct"; // 收款人账号
	private final String key_PayeeName = "payeeName"; // 收款人名称
	private final String key_PayeeAddr = "payeeAddr"; // 收款人地址
	private final String key_PayeeAcctBrno = "payeeAcctBrno"; // 收款人开户行行号
	private final String key_PayeeAcctBrName = "payeeAcctBrName"; // 收款人开户行行名
	private final String key_CflDate = "cflDate"; // 原圈存日期
	private final String key_CflSerno = "cflSerno"; // 原圈存编号
	private final String key_SuspSerno = "suspSerno"; // 挂账编号
	private final String key_BillType = "billType"; // 票据种类
	private final String key_Billno = "billno"; // 票据号码
	private final String key_BillDate = "billDate"; // 票据日期
	private final String key_AuthTellerno = "authTellerno"; // 票据日期
	private final String key_AuthFlag = "authFlag"; // 授权标识
	private final String key_FeePayMode = "feePayMode"; // 手续费收取方式
	private final String key_FeeCalcType = "feeCalcType"; // 手续费计算方式
	private final String key_FeeRecvable = "feeRecvable"; // 应收手续费金额
	private final String key_FeeAmount = "feeAmount"; // 实收手续费金额
	private final String key_FeeCode = "feeCode"; // 费用代码
	private final String key_FeeAcct = "feeAcct"; // 扣手续费账号
	private final String key_FeeNo = "feeNo"; // 费用编码
	private final String key_EndToEndId = "endToEndId"; // 端到端标识号
	private final String key_Summary = "summary"; // 摘要码
	private final String key_Narrative = "narrative"; // 附言
	private final String key_Remarks = "remarks"; // 备注
	private final String key_AgentInd = "agentInd"; // 代理分行标识
	private final String key_AgentZoneno = "agentZoneno"; // 代理分行号
	private final String key_BookDate = "bookDate"; // 预约日期
	private final String key_BookTime = "bookTime"; // 预约时间
	private final String key_BusiFlag = "busiFlag"; // 业务标识
	private final String key_AddFlag = "addFlag"; // 附加数据标志
	private final String key_AddData = "addData"; // 附加域数据
	private final String key_PayeeProvince = "payeeProvince"; // 收方开户行省
	private final String key_PayeeCity = "payeeCity"; // 收方开户行市
	private final String key_StmtNo = "stmtNo"; // 账单号码
	private final String key_CardAcctName = "cardAcctName"; // 单位结算卡完整账户名称
	private final String key_RegCode = "regCode"; // 营业执照注册号
	private final String key_MerchType = "merchType"; // 商户类别
	private final String key_MerchName = "merchName"; // 商户名称
	private final String key_MerchShortName = "merchShortName"; // 商户简称
	private final String key_OrderNo = "orderNo"; // 商品订单号
	private final String key_OrderTime = "orderTime"; // 订单时间
	private final String key_AgentAddInfo = "agentAddInfo"; // 代付业务附加信息
	private final String key_Reserve1 = "reserve1"; // 预留字段1
	private final String key_Reserve2 = "reserve2"; // 预留字段2
	private final String key_Reserve3 = "reserve3"; // 预留字段3
	private final String key_Reserve4 = "reserve4"; // 预留字段4
	private final String key_Reserve5 = "reserve5"; // 预留字段5
	
	@Override
	public void initDefaultField() {
		putHeader(key_RouteInd, "0");
		putHeader(key_PayPath, "CNPS");
		putHeader(key_ProdPriCode, "TFRT");
		putHeader(key_ProdSecCode, "TFRT001");
		putHeader(key_BusiType, "A100");
		putHeader(key_BusiKind, "02102");
		putHeader(key_AcctMeth, "DJ010001");
		putHeader(key_Currency, "156");
		putHeader(key_CashExCode, "1");
		putHeader(key_Priority, "0");
		putHeader(key_CusVouchChkInd, "N");
		putHeader(key_CusVouchPwdInd, "N");
		putHeader(key_AuthFlag, "0");
		putHeader(key_FeePayMode, "0");
		putHeader(key_FeeCalcType, "0");
		putHeader(key_Summary, "A33");
		putHeader(key_BusiFlag, "ZGFY");
	}
	
	public String getRouteInd() {
		return getFieldValue(key_RouteInd);
	}
	
	public String getPayPath() {
		return getFieldValue(key_PayPath);
	}
	
	public String getProdPriCode() {
		return getFieldValue(key_ProdPriCode);
	}
	
	public String getProdSecCode() {
		return getFieldValue(key_ProdSecCode);
	}
	
	public String getBusiType() {
		return getFieldValue(key_BusiType);
	}
	
	public String getBusiKind() {
		return getFieldValue(key_BusiKind);
	}
	
	public String getAcctMeth() {
		return getFieldValue(key_AcctMeth);
	}
	
	public String getAcctBrno() {
		return getFieldValue(key_AcctBrno);
	}
	
	public String getTaskId() {
		return getFieldValue(key_TaskId);
	}
	
	public String getCurrency() {
		return getFieldValue(key_Currency);
	}
	
	public String getCashExInd() {
		return getFieldValue(key_CashExCode);
	}
	
	public String getAmount() {
		return getFieldValue(key_Amount);
	}
	
	public String getPriority() {
		return getFieldValue(key_Priority);
	}
	
	public String getPayerBank() {
		return getFieldValue(key_PayerBank);
	}
	
	public String getPayerAcct() {
		return getFieldValue(key_PayerAcct);
	}
	
	public String getPayerName() {
		return getFieldValue(key_PayerName);
	}
	
	public String getPayerAddr() {
		return getFieldValue(key_PayerAddr);
	}
	
	public String getCusVouchChkInd() {
		return getFieldValue(key_CusVouchChkInd);
	}
	
	public String getCusVoucherType() {
		return getFieldValue(key_CusVoucherType);
	}
	
	public String getCusVouchno() {
		return getFieldValue(key_CusVouchno);
	}
	
	public String getCusVouchDate() {
		return getFieldValue(key_CusVouchDate);
	}
	
	public String getCusVouchPwdInd() {
		return getFieldValue(key_CusVouchPwdInd);
	}
	
	public String getCusVouchPwd() {
		return getFieldValue(key_CusVouchPwd);
	}
	
	public String getPayerMediaType() {
		return getFieldValue(key_PayerMediaType);
	}
	
	public String getCertMeth() {
		return getFieldValue(key_CertMeth);
	}
	
	public String getPassword() {
		return getFieldValue(key_Password);
	}
	
	public String getTrack2() {
		return getFieldValue(key_Track2);
	}
	
	public String getTrack3() {
		return getFieldValue(key_Track3);
	}
	
	public String getIcCardInfo23() {
		return getFieldValue(key_IcCardInfo23);
	}
	
	public String getIcCardInfo55() {
		return getFieldValue(key_IcCardInfo55);
	}
	
	public String getRealPayerAcct() {
		return getFieldValue(key_RealPayerAcct);
	}
	
	public String getRealPayerName() {
		return getFieldValue(key_RealPayerName);
	}
	
	public String getCustNo() {
		return getFieldValue(key_CustNo);
	}
	
	public String getPayeeBank() {
		return getFieldValue(key_PayeeBank);
	}
	
	public String getPayeeAcct() {
		return getFieldValue(key_PayeeAcct);
	}
	
	public String getPayeeName() {
		return getFieldValue(key_PayeeName);
	}
	
	public String getPayeeAddr() {
		return getFieldValue(key_PayeeAddr);
	}
	
	public String getPayeeAcctBrno() {
		return getFieldValue(key_PayeeAcctBrno);
	}
	
	public String getPayeeAcctBrName() {
		return getFieldValue(key_PayeeAcctBrName);
	}
	
	public String getCflDate() {
		return getFieldValue(key_CflDate);
	}
	
	public String getCflSerno() {
		return getFieldValue(key_CflSerno);
	}
	
	public String getSuspSerno() {
		return getFieldValue(key_SuspSerno);
	}
	
	public String getBillType() {
		return getFieldValue(key_BillType);
	}
	
	public String getBillno() {
		return getFieldValue(key_Billno);
	}
	
	public String getBillDate() {
		return getFieldValue(key_BillDate);
	}
	
	public String getAuthTellerno() {
		return getFieldValue(key_AuthTellerno);
	}
	
	public String getAuthFlag() {
		return getFieldValue(key_AuthFlag);
	}
	
	public String getFeePayMode() {
		return getFieldValue(key_FeePayMode);
	}
	
	public String getFeeCalcType() {
		return getFieldValue(key_FeeCalcType);
	}
	
	public String getFeeRecvable() {
		return getFieldValue(key_FeeRecvable);
	}
	
	public String getFeeAmount() {
		return getFieldValue(key_FeeAmount);
	}
	
	public String getFeeCode() {
		return getFieldValue(key_FeeCode);
	}
	
	public String getFeeAcct() {
		return getFieldValue(key_FeeAcct);
	}
	
	public String getFeeNo() {
		return getFieldValue(key_FeeNo);
	}
	
	public String getEndToEndId() {
		return getFieldValue(key_EndToEndId);
	}
	
	public String getSummary() {
		return getFieldValue(key_Summary);
	}
	
	public String getNarrative() {
		return getFieldValue(key_Narrative);
	}
	
	public String getRemarks() {
		return getFieldValue(key_Remarks);
	}
	
	public String getAgentInd() {
		return getFieldValue(key_AgentInd);
	}
	
	public String getAgentZoneno() {
		return getFieldValue(key_AgentZoneno);
	}
	
	public String getBookDate() {
		return getFieldValue(key_BookDate);
	}
	
	public String getBookTime() {
		return getFieldValue(key_BookTime);
	}
	
	public String getBusiFlag() {
		return getFieldValue(key_BusiFlag);
	}
	
	public String getAddFlag() {
		return getFieldValue(key_AddFlag);
	}
	
	public String getAddData() {
		return getFieldValue(key_AddData);
	}
	
	public String getPayeeProvince() {
		return getFieldValue(key_PayeeProvince);
	}
	
	public String getPayeeCity() {
		return getFieldValue(key_PayeeCity);
	}
	
	public String getStmtNo() {
		return getFieldValue(key_StmtNo);
	}
	
	public String getCardAcctName() {
		return getFieldValue(key_CardAcctName);
	}
	
	public String getRegCode() {
		return getFieldValue(key_RegCode);
	}
	
	public String getMerchType() {
		return getFieldValue(key_MerchType);
	}
	
	public String getMerchName() {
		return getFieldValue(key_MerchName);
	}
	
	public String getMerchShortName() {
		return getFieldValue(key_MerchShortName);
	}
	
	public String getOrderNo() {
		return getFieldValue(key_OrderNo);
	}
	
	public String getOrderTime() {
		return getFieldValue(key_OrderTime);
	}
	
	public String getAgentAddInfo() {
		return getFieldValue(key_AgentAddInfo);
	}
	
	public String getReserve1() {
		return getFieldValue(key_Reserve1);
	}
	
	public String getReserve2() {
		return getFieldValue(key_Reserve2);
	}
	
	public String getReserve3() {
		return getFieldValue(key_Reserve3);
	}
	
	public String getReserve4() {
		return getFieldValue(key_Reserve4);
	}
	
	public String getReserve5() {
		return getFieldValue(key_Reserve5);
	}
	
	public void setRouteInd(String routeInd) {
		putField(key_RouteInd, routeInd);
	}
	public void setPayPath(String payPath) {
		putField(key_PayPath, payPath);
	}
	public void setProdPriCode(String prodPriCode) {
		putField(key_ProdPriCode, prodPriCode);
	}
	public void setProdSecCode(String prodSecCode) {
		putField(key_ProdSecCode, prodSecCode);
	}
	public void setBusiType(String busiType) {
		putField(key_BusiType, busiType);
	}
	public void setBusiKind(String busiKind) {
		putField(key_BusiKind, busiKind);
	}
	public void setAcctMeth(String acctMeth) {
		putField(key_AcctMeth, acctMeth);
	}
	public void setAcctBrno(String acctBrno) {
		putField(key_AcctBrno, acctBrno);
	}
	public void setTaskId(String taskId) {
		putField(key_TaskId, taskId);
	}
	public void setCurrency(String currency) {
		putField(key_Currency, currency);
	}
	public void setCashExCode(String cashExInd) {
		putField(key_CashExCode, cashExInd);
	}
	public void setAmount(String amount) {
		putField(key_Amount, amount);
	}
	public void setPriority(String priority) {
		putField(key_Priority, priority);
	}
	public void setPayerBank(String payerBank) {
		putField(key_PayerBank, payerBank);
	}
	public void setPayerAcct(String payerAcct) {
		putField(key_PayerAcct, payerAcct);
	}
	public void setPayerName(String payerName) {
		putField(key_PayerName, payerName);
	}
	public void setPayerAddr(String payerAddr) {
		putField(key_PayerAddr, payerAddr);
	}
	public void setCusVouchChkInd(String cusVouchChkInd) {
		putField(key_CusVouchChkInd, cusVouchChkInd);
	}
	public void setCusVoucherType(String cusVoucherType) {
		putField(key_CusVoucherType, cusVoucherType);
	}
	public void setCusVouchno(String cusVouchno) {
		putField(key_CusVouchno, cusVouchno);
	}
	public void setCusVouchDate(String cusVouchDate) {
		putField(key_CusVouchDate, cusVouchDate);
	}
	public void setCusVouchPwdInd(String cusVouchPwdInd) {
		putField(key_CusVouchPwdInd, cusVouchPwdInd);
	}
	public void setCusVouchPwd(String cusVouchPwd) {
		putField(key_CusVouchPwd, cusVouchPwd);
	}
	public void setPayerMediaType(String payerMediaType) {
		putField(key_PayerMediaType, payerMediaType);
	}
	public void setCertMeth(String certMeth) {
		putField(key_CertMeth, certMeth);
	}
	public void setPassword(String password) {
		putField(key_Password, password);
	}
	public void setTrack2(String track2) {
		putField(key_Track2, track2);
	}
	public void setTrack3(String track3) {
		putField(key_Track3, track3);
	}
	public void setIcCardInfo23(String icCardInfo23) {
		putField(key_IcCardInfo23, icCardInfo23);
	}
	public void setIcCardInfo55(String icCardInfo55) {
		putField(key_IcCardInfo55, icCardInfo55);
	}
	public void setRealPayerAcct(String realPayerAcct) {
		putField(key_RealPayerAcct, realPayerAcct);
	}
	public void setRealPayerName(String realPayerName) {
		putField(key_RealPayerName, realPayerName);
	}
	public void setCustNo(String custNo) {
		putField(key_CustNo, custNo);
	}
	public void setPayeeBank(String payeeBank) {
		putField(key_PayeeBank, payeeBank);
	}
	public void setPayeeAcct(String payeeAcct) {
		putField(key_PayeeAcct, payeeAcct);
	}
	public void setPayeeName(String payeeName) {
		putField(key_PayeeName, payeeName);
	}
	public void setPayeeAddr(String payeeAddr) {
		putField(key_PayeeAddr, payeeAddr);
	}
	public void setPayeeAcctBrno(String payeeAcctBrno) {
		putField(key_PayeeAcctBrno, payeeAcctBrno);
	}
	public void setPayeeAcctBrName(String payeeAcctBrName) {
		putField(key_PayeeAcctBrName, payeeAcctBrName);
	}
	public void setCflDate(String cflDate) {
		putField(key_CflDate, cflDate);
	}
	public void setCflSerno(String cflSerno) {
		putField(key_CflSerno, cflSerno);
	}
	public void setSuspSerno(String suspSerno) {
		putField(key_SuspSerno, suspSerno);
	}
	public void setBillType(String billType) {
		putField(key_BillType, billType);
	}
	public void setBillno(String billno) {
		putField(key_Billno, billno);
	}
	public void setBillDate(String billDate) {
		putField(key_BillDate, billDate);
	}
	public void setAuthTellerno(String authTellerno) {
		putField(key_AuthTellerno, authTellerno);
	}
	public void setAuthFlag(String authFlag) {
		putField(key_AuthFlag, authFlag);
	}
	public void setFeePayMode(String feePayMode) {
		putField(key_FeePayMode, feePayMode);
	}
	public void setFeeCalcType(String feeCalcType) {
		putField(key_FeeCalcType, feeCalcType);
	}
	public void setFeeRecvable(String feeRecvable) {
		putField(key_FeeRecvable, feeRecvable);
	}
	public void setFeeAmount(String feeAmount) {
		putField(key_FeeAmount, feeAmount);
	}
	public void setFeeCode(String feeCode) {
		putField(key_FeeCode, feeCode);
	}
	public void setFeeAcct(String feeNo) {
		putField(key_FeeAcct, feeNo);
	}
	public void setEndToEndId(String endToEndId) {
		putField(key_EndToEndId, endToEndId);
	}
	public void setSummary(String summary) {
		putField(key_Summary, summary);
	}
	public void setNarrative(String narrative) {
		putField(key_Narrative, narrative);
	}
	public void setRemarks(String remarks) {
		putField(key_Remarks, remarks);
	}
	public void setAgentInd(String agentInd) {
		putField(key_AgentInd, agentInd);
	}
	public void setAgentZoneno(String agentZoneno) {
		putField(key_AgentZoneno, agentZoneno);
	}
	public void setBookDate(String bookDate) {
		putField(key_BookDate, bookDate);
	}
	public void setBookTime(String bookTime) {
		putField(key_BookTime, bookTime);
	}
	public void setBusiFlag(String busiFlag) {
		putField(key_BusiFlag, busiFlag);
	}
	public void setAddFlag(String addFlag) {
		putField(key_AddFlag, addFlag);
	}
	public void setAddData(String addData) {
		putField(key_AddData, addData);
	}
	public void setPayeeProvince(String payeeProvince) {
		putField(key_PayeeProvince, payeeProvince);
	}
	public void setPayeeCity(String payeeCity) {
		putField(key_PayeeCity, payeeCity);
	}
	public void setStmtNo(String stmtNo) {
		putField(key_StmtNo, stmtNo);
	}
	public void setCardAcctName(String cardAcctName) {
		putField(key_CardAcctName, cardAcctName);
	}
	public void setRegCode(String regCode) {
		putField(key_RegCode, regCode);
	}
	public void setMerchType(String merchType) {
		putField(key_MerchType, merchType);
	}
	public void setMerchName(String merchName) {
		putField(key_MerchName, merchName);
	}
	public void setMerchShortName(String merchShortName) {
		putField(key_MerchShortName, merchShortName);
	}
	public void setOrderNo(String orderNo) {
		putField(key_OrderNo, orderNo);
	}
	public void setOrderTime(String orderTime) {
		putField(key_OrderTime, orderTime);
	}
	public void setAgentAddInfo(String agentAddInfo) {
		putField(key_AgentAddInfo, agentAddInfo);
	}
	public void setReserve1(String reserve1) {
		putField(key_Reserve1, reserve1);
	}
	public void setReserve2(String reserve2) {
		putField(key_Reserve2, reserve2);
	}
	public void setReserve3(String reserve3) {
		putField(key_Reserve3, reserve3);
	}
	public void set_Reserve4(String reserve4) {
		putField(key_Reserve4, reserve4);
	}
	public void setReserve5(String reserve5) {
		putField(key_Reserve5, reserve5);
	}
	
	
	
	
}
