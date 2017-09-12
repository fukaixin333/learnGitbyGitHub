package com.citic.server.cgb.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 广发银行非金融交换报文头（128报文头）
 * 
 * @author Liu Xuanfei
 * @date 2016年11月28日 下午2:25:23
 */
public class Header128 implements Serializable {
	private static final long serialVersionUID = 5732405547530888730L;
	
	protected LinkedHashMap<String, String> fieldMap;
	
	public Header128() {
		fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("HEAD_LEN", "128"); // 报文头长度
		fieldMap.put("PACK_LEN", "01352"); // 报文长度
		fieldMap.put("MSG_TYPE", ""); // 消息类型
		fieldMap.put("VERSION", "1"); // 版本号
		fieldMap.put("COMM_TYPE", "0"); // 通讯类型 0-同步请求:短连接时均填0 1-异步请求长连接时填为1
		fieldMap.put("TRAN_TYPE", "0"); // 传输方式 0-ASC字符非压缩 1-EBCDIC字符非压缩 目前针对GUI使用1，其他接接系统全部填0
		fieldMap.put("PACK_FMT", "FIXD"); // 报文格式 三种报文类型：8583、XML、FIXD（定长）网关转换用
		fieldMap.put("GW_NUM", ""); // 网关编号
		fieldMap.put("SRC_SYSID", "ABSM"); // 源系统标识
		fieldMap.put("OPR_CODE", "T00089"); // 操作代码
		fieldMap.put("TXN_CHL", "NTS"); // 交易渠道
		fieldMap.put("TXN_SRC", "#QZ"); // 交易来源
		fieldMap.put("BUSI_SEC", "00"); // 业务场景
		fieldMap.put("ACQ_DATE", ""); // 源发起方日期
		fieldMap.put("ACQ_TIME", ""); // 源发起方时间
		fieldMap.put("ACQ_SEI_NUM", ""); // 源发起方流水
		fieldMap.put("OPER_ID", ""); // 操作员代码
		fieldMap.put("RSP_CODE", ""); // 交易响应码
		fieldMap.put("TT_PACK", "001"); // 总包数
		fieldMap.put("PACK_SEI", "001"); // 包序号
		fieldMap.put("REFU_CODE", ""); // 拒绝码
		fieldMap.put("OPR_CODE_TYPE", "1"); // 操作代码分类标识
		fieldMap.put("RESERVE", ""); // 预留域
	}
	
	public String getHeadLength() {
		return fieldMap.get("HEAD_LEN");
	}
	
	public void setHeadLength(String headLength) {
		fieldMap.put("HEAD_LEN", headLength); // 报文头长度
	}
	
	public String getPackLength() {
		return fieldMap.get("PACK_LEN");
	}
	
	public void setPackLength(String packLength) {
		fieldMap.put("PACK_LEN", packLength); // 报文长度
	}
	
	public String getMsgType() {
		return fieldMap.get("MSG_TYPE");
	}
	
	public void setMsgType(String msgType) {
		fieldMap.put("MSG_TYPE", msgType); // 消息类型
	}
	
	public String getVersion() {
		return fieldMap.get("VERSION");
	}
	
	public void setVersion(String version) {
		fieldMap.put("VERSION", version); // 版本号
	}
	
	public String getCommType() {
		return fieldMap.get("COMM_TYPE");
	}
	
	public void setCommType(String commType) {
		fieldMap.put("COMM_TYPE", commType); // 通讯类型
	}
	
	public String getTranType() {
		return fieldMap.get("TRAN_TYPE");
	}
	
	public void setTranType(String tranType) {
		fieldMap.put("TRAN_TYPE", tranType); // 传输方式
	}
	
	public String getPackFmt() {
		return fieldMap.get("PACK_FMT");
	}
	
	public void setPackFmt(String packFmt) {
		fieldMap.put("PACK_FMT", packFmt); // 报文格式
	}
	
	public String getGwNumber() {
		return fieldMap.get("GW_NUM");
	}
	
	public void setGwNumber(String gwNumber) {
		fieldMap.put("GW_NUM", gwNumber); // 网关编号
	}
	
	public String getSrcSysId() {
		return fieldMap.get("SRC_SYSID");
	}
	
	public void setSrcSysId(String srcSysId) {
		fieldMap.put("SRC_SYSID", "srcSysId"); // 源系统标识
	}
	
	public String getOprCode() {
		return fieldMap.get("OPR_CODE");
	}
	
	public void setOprCode(String oprCode) {
		fieldMap.put("OPR_CODE", oprCode); // 操作代码
	}
	
	public String getTxnChannel() {
		return fieldMap.get("TXN_CHL");
	}
	
	public void setTxnChannel(String txnChannel) {
		fieldMap.put("TXN_CHL", txnChannel); // 交易渠道
	}
	
	public String getTxnSource() {
		return fieldMap.get("TXN_SRC");
	}
	
	public void setTxnSource(String txnSource) {
		fieldMap.put("TXN_SRC", txnSource); // 交易来源
	}
	
	public String getBusiSec() {
		return fieldMap.get("BUSI_SEC");
	}
	
	public void setBusiSec(String busiSec) {
		fieldMap.put("BUSI_SEC", busiSec); // 业务场景
	}
	
	public String getAcqDate() {
		return fieldMap.get("ACQ_DATE");
	}
	
	public void setAcqDate(String acqDate) {
		fieldMap.put("ACQ_DATE", acqDate); // 源发起方日期
	}
	
	public String getAcqTime() {
		return fieldMap.get("ACQ_TIME");
	}
	
	public void setAcqTime(String acqTime) {
		fieldMap.put("ACQ_TIME", acqTime); // 源发起方时间
	}
	
	public String getAcqSeiNumber() {
		return fieldMap.get("ACQ_SEI_NUM");
	}
	
	public void setAcqSeiNumber(String acqSeiNumber) {
		fieldMap.put("ACQ_SEI_NUM", acqSeiNumber); // 源发起方流水
	}
	
	public String getOperId() {
		return fieldMap.get("OPER_ID");
	}
	
	public void setOperId(String operId) {
		fieldMap.put("OPER_ID", operId); // 操作员代码
	}
	
	public String getRspCode() {
		return fieldMap.get("RSP_CODE");
	}
	
	public void setRspCode(String rspCode) {
		fieldMap.put("RSP_CODE", rspCode); // 交易响应码
	}
	
	public String getTTPack() {
		return fieldMap.get("TT_PACK");
	}
	
	public void setTTPack(String ttPack) {
		fieldMap.put("TT_PACK", ttPack); // 总包数
	}
	
	public String getPackSei() {
		return fieldMap.get("PACK_SEI");
	}
	
	public void setPackSei(String packSei) {
		fieldMap.put("PACK_SEI", packSei); // 包序号
	}
	
	public String getRefuCode() {
		return fieldMap.get("REFU_CODE");
	}
	
	public void setRefuCode(String refuCode) {
		fieldMap.put("REFU_CODE", refuCode); // 拒绝码
	}
	
	public String getOprCodeType() {
		return fieldMap.get("OPR_CODE_TYPE");
	}
	
	public void setOprCodeType(String oprCodeType) {
		fieldMap.put("OPR_CODE_TYPE", oprCodeType); // 操作代码分类标识
	}
}
