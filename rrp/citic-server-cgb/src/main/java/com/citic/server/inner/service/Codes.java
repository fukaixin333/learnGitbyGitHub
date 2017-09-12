package com.citic.server.inner.service;

/**
 * 交易码（常量）
 * 
 * @author Liu Xuanfei
 * @date 2016年5月24日 上午10:58:18
 */
public interface Codes {
	/** 个人客户信息查询 */
	String QUERY_INDIVIDUAL_INFO = "025317"; // 个人客户信息查询
	
	/** 公司同业客户信息查询 */
	String QUERY_CORPORATE_INFO = "025327"; // 公司同业客户信息查询
	
	/** 客户信息变更历史查询 */
	String QUERY_CUSTOMER_UPDATE_LIST = "025890"; // 客户信息变更历史查询
	
	/** 合约账号查询 */
	String QUERY_CONTRACT_ACCOUNT_LIST = "028100"; // 合约账号查询
	
	/** 账户信息查询 */
	String QUERY_ACCOUNT_DETAIL = "358080"; // 账户信息查询
	
	/** 账户校验及信息查询*/
	String QUERY_ACCOUNT_VERIFY_INFO = "358040"; // 账户校验及信息查询
	
	/** 账户冻结在先查询 */
	String QUERY_ACCOUNT_FROZENMEASURE_LIST = "267570"; // 账户冻结在先查询
	
	/** 冻结记录明细查询 */
	String QUERY_FROZENINFO_FROZENNUMBER = "267530"; // 冻结记录明细查询
	
	/** 账户明细列表查询 */
	String QUERY_SUB_ACCOUNT_LIST = "267880"; // 账户明细列表查询
	
	/** 质押和授信类保证金查询 */
	String QUERY_OWNERSHIP_INFO = "265561"; // 共有权信息
	
	/** 交易流水查询 */
	String QUERY_TRANSACTION_LIST = "998070"; // 交易流水查询
	
	/** 账户冻结 */
	String FREEZE_ACCOUNT = "267500"; // 账户冻结
	
	/** 账户解冻 */
	String UNFREEZE_ACCOUNT = "267510"; // 账户解冻
	
	/** 账户续冻 */
	String DEFER_FREEZE_ACCOUNT = "267540"; // 账户续冻
	
	/** 账户扣划 */
	String DEDUCT_ACCOUNT = "267580"; // 账户扣划
	
	/** 二代扣划 */
	String EXTERNAL_TRABSFER = "PSB120"; // 二代扣划
	
	String QUERY_FK_WARN_DETAIL = "ZXFK01";
	String FK_WARN_DETAIL_BNAME_RT = "binding_GatewayResponse_ZXFK01";
	
	String QUERY_FINANCIAL_DEATAIL = "ZXZC02"; // 金融资产信息查询
	String FINANCIAL_DEATAIL_BNAME_RT = "binding_GatewayResponse_ZXZC02"; // 
	
	/** 查询二代扣划信息 */
	String QUERY_EXTERNAL_TRABSFER_INFO = "PSB411";
	//String GATEWAYREQUEST_BINDING_NAME = "binding_GatewayRequest"; // binding name
	
	/** 统一支付行外转账 */
	String UNIFIED_PAYMENT_TRANSFER="UP1101";
	String UNIFIED_PAYMENT_TRANSFER_BNAME_RT="binding_GatewayResponse_UP1101";
	
	/** 理财产品份额冻结 */
	String FREEZE_FINANCIAL = "100222";
	String FREEZE_FINANCIAL_BNAME_RT = "binding_GatewayResponse_100222";
	/** 理财产品份额解冻 */
	String UNFREEZE_FINANCIAL = "100223";
	String UNFREEZE_FINANCIAL_BNAME_RT = "binding_GatewayResponse_100223";
	/** 司法续冻交易 */
	String DEFERFREEZE_FINANCIAL = "109224";
	String DEFERFREEZE_FINANCIAL_BNAME_RT = "binding_GatewayResponse_109224";
	/** 未解冻流水查询 */
	String QUERY_FINANCICAL_FREEZEMEASURE = "100323";
	String FINANCICAL_FREEZEMEASURE_BANME_RT = "binding_GatewayResponse_100323";
}
