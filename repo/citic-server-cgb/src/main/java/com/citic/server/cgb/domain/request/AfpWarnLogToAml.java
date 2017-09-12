package com.citic.server.cgb.domain.request;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.citic.server.cgb.domain.GatewayHeader;

/**
 * 反恐怖名单的预警信息推送
 * 
 * @author yinxiong
 * @date 2017年5月22日 下午5:51:44
 */
public class AfpWarnLogToAml implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8518282262670209609L;
	/** 网关通用报文头 */
	private GatewayHeader gatewayHeader;
	/**
	 * 报文body部分的字段
	 */
	private LinkedHashMap<String, String> fieldMap;
	
	public AfpWarnLogToAml() {
		fieldMap = new LinkedHashMap<String, String>();
		//========请求信息============
		fieldMap.put("Tx_Organkey", ""); // 交易机构
		fieldMap.put("Acct_Organkey", ""); // 账户归属机构
		fieldMap.put("Tx_Time", ""); // 交易时间
		fieldMap.put("Party_Id", ""); // 客户号
		fieldMap.put("Party_Class_Cd", ""); // 客户类型
		fieldMap.put("Party_Name", ""); // 客户名称
		fieldMap.put("Cust_Id_No", ""); // 客户证件号码
		fieldMap.put("Sys_Ind", ""); // 系统标识
		fieldMap.put("Channel", ""); // 渠道标识
		fieldMap.put("Tx_Acctnum", ""); //交易账户
		fieldMap.put("Curr_Cd", ""); // 交易币种
		fieldMap.put("Amt", ""); // 交易金额
		fieldMap.put("Alert_Reason", ""); // 预警原因
		fieldMap.put("San_Name", ""); // 名单细类
		fieldMap.put("Biz_Type", ""); // 交易种类
		fieldMap.put("Tx_Result", ""); // 交易结果
		fieldMap.put("alert_Type", ""); // 预警类型
		fieldMap.put("opp_acct_organkey", ""); // 交易对手行所名称
		fieldMap.put("opp_party_class_cd", ""); // 交易对手类型
		fieldMap.put("opp_tx_acctnum", ""); // 交易对手账号
		fieldMap.put("opp_party_name", ""); // 交易对手姓名
		fieldMap.put("opp_cust_id_no", ""); // 交易对手证件号码
		fieldMap.put("rt_typ", ""); // 阻断类型
		//======响应信息==========
//		fieldMap.put("Return_Code", ""); //响应代码
//		fieldMap.put("Return_Msg", ""); //响应描述
	}
	
	public GatewayHeader getGatewayHeader() {
		return gatewayHeader;
	}
	
	public void setGatewayHeader(GatewayHeader gatewayHeader) {
		this.gatewayHeader = gatewayHeader;
	}
	
	//================设置字段的get/set方法================================
	public String getTx_Organkey() {
		return fieldMap.get("Tx_Organkey");
	}
	
	public String getAcct_Organkey() {
		return fieldMap.get("Acct_Organkey");
	}
	
	public String getTx_Time() {
		return fieldMap.get("Tx_Time");
	}
	
	public String getParty_Id() {
		return fieldMap.get("Party_Id");
	}
	
	public String getParty_Class_Cd() {
		return fieldMap.get("Party_Class_Cd");
	}
	
	public String getParty_Name() {
		return fieldMap.get("Party_Name");
	}
	
	public String getCust_Id_No() {
		return fieldMap.get("Cust_Id_No");
	}
	
	public String getSys_Ind() {
		return fieldMap.get("Sys_Ind");
	}
	
	public String getChannel() {
		return fieldMap.get("Channel");
	}
	
	public String getTx_Acctnum() {
		return fieldMap.get("Tx_Acctnum");
	}
	
	public String getCurr_Cd() {
		return fieldMap.get("Curr_Cd");
	}
	
	public String getAmt() {
		return fieldMap.get("Amt");
	}
	
	public String getAlert_Reason() {
		return fieldMap.get("Alert_Reason");
	}
	
	public String getSan_Name() {
		return fieldMap.get("San_Name");
	}
	
	public String getBiz_Type() {
		return fieldMap.get("Biz_Type");
	}
	
	public String getTx_Result() {
		return fieldMap.get("Tx_Result");
	}
	
	public String getAlert_Type() {
		return fieldMap.get("alert_Type");
	}
	
	public String getOpp_acct_organkey() {
		return fieldMap.get("opp_acct_organkey");
	}
	public String getOpp_party_class_cd() {
		return fieldMap.get("opp_party_class_cd");
	}
	public String getOpp_tx_acctnum() {
		return fieldMap.get("opp_tx_acctnum");
	}
	
	public String getOpp_party_name() {
		return fieldMap.get("opp_party_name");
	}
	
	public String getOpp_cust_id_no() {
		return fieldMap.get("opp_cust_id_no");
	}
	
	public String getRt_typ() {
		return fieldMap.get("rt_typ");
	}
	
	
	public void setTx_Organkey(String Tx_Organkey) {
		fieldMap.put("Tx_Organkey", Tx_Organkey);
	}
	
	public void setAcct_Organkey(String Acct_Organkey) {
		fieldMap.put("Acct_Organkey", Acct_Organkey);
	}
	
	public void setTx_Time(String Tx_Time) {
		fieldMap.put("Tx_Time", Tx_Time);
	}
	
	public void setParty_Id(String Party_Id) {
		fieldMap.put("Party_Id", Party_Id);
	}
	
	public void setParty_Class_Cd(String Party_Class_Cd) {
		fieldMap.put("Party_Class_Cd", Party_Class_Cd);
	}
	
	public void setParty_Name(String Party_Name) {
		fieldMap.put("Party_Name", Party_Name);
	}
	
	public void setCust_Id_No(String Cust_Id_No) {
		fieldMap.put("Cust_Id_No", Cust_Id_No);
	}
	
	public void setSys_Ind(String Sys_Ind) {
		fieldMap.put("Sys_Ind", Sys_Ind);
	}
	
	public void setChannel(String Channel) {
		fieldMap.put("Channel", Channel);
	}
	
	public void setTx_Acctnum(String Tx_Acctnum) {
		fieldMap.put("Tx_Acctnum", Tx_Acctnum);
	}
	
	public void setCurr_Cd(String Curr_Cd) {
		fieldMap.put("Curr_Cd", Curr_Cd);
	}
	
	public void setAmt(String Amt) {
		fieldMap.put("Amt", Amt);
	}
	
	public void setAlert_Reason(String Alert_Reason) {
		fieldMap.put("Alert_Reason", Alert_Reason);
	}
	
	public void setSan_Name(String San_Name) {
		fieldMap.put("San_Name", San_Name);
	}
	
	public void setBiz_Type(String Biz_Type) {
		fieldMap.put("Biz_Type", Biz_Type);
	}
	
	public void setTx_Result(String Tx_Result) {
		fieldMap.put("Tx_Result", Tx_Result);
	}
	
	public void setAlert_Type(String alert_Type) {
		fieldMap.put("alert_Type", alert_Type);
	}
	
	public void setOpp_acct_organkey(String opp_acct_organkey) {
		fieldMap.put("opp_acct_organkey", opp_acct_organkey);
	}
	
	public void setOpp_party_class_cd(String opp_party_class_cd) {
		fieldMap.put("opp_party_class_cd", opp_party_class_cd);
	}
	
	public void setOpp_tx_acctnum(String opp_tx_acctnum) {
		fieldMap.put("opp_tx_acctnum", opp_tx_acctnum);
	}
	
	public void setOpp_party_name(String opp_party_name) {
		fieldMap.put("opp_party_name", opp_party_name);
	}
	
	public void setOpp_cust_id_no(String opp_cust_id_no) {
		fieldMap.put("opp_cust_id_no", opp_cust_id_no);
	}
	
	public void setRt_typ(String rt_typ) {
		fieldMap.put("rt_typ", rt_typ);
	}
	
	//==================响应字段=================
	public String getReturn_Code() {
		return fieldMap.get("Return_Code");
	}
	public String getReturn_Msg() {
		return fieldMap.get("Return_Msg");
	}
	public void setReturn_Code(String Return_Code) {
		fieldMap.put("Return_Code", Return_Code);
	}
	public void setReturn_Msg(String Return_Msg) {
		fieldMap.put("Return_Msg", Return_Msg);
	}
}
