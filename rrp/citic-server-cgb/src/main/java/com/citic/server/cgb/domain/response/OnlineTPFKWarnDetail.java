package com.citic.server.cgb.domain.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinxiong
 * @version $Revision: 1.0.0, $Date: 2017/07/26 00:02:23$
 */
public class OnlineTPFKWarnDetail {
	
	private static final String key_txTime = "txTime";//交易时间
	private static final String key_jrnNo = "jrnNo";//日志号
	private static final String key_subSeq = "subSeq";//序号
	private static final String key_lstCd = "lstCd";//名单大类
	private static final String key_lstDt = "lstDt";//名单细类
	private static final String key_txBr = "txBr";//交易机构
	private static final String key_acctBr = "acctBr";//账户归属机构
	private static final String key_ciNo = "ciNo";//客户号
	private static final String key_ciTyp = "ciTyp";//客户类型
	private static final String key_ciName = "ciName";//客户名称
	private static final String key_idTyp = "idTyp";//证件类型
	private static final String key_idNo = "idNo";//证件号码
	private static final String key_chnl1 = "chnl1";//渠道大类
	private static final String key_txAc = "txAc";//交易账户
	private static final String key_txCcy = "txCcy";//币种
	private static final String key_txAmt = "txAmt";//交易金额
	private static final String key_mmo = "mmo";//摘要码
	private static final String key_alertType = "alertType";//预警类型
	private static final String key_rtType = "rtType";//阻断类型
	private static final String key_oppBr = "oppBr";//交易对手行所号
	private static final String key_oppCiTyp = "oppCiTyp";//交易对手客户类型
	private static final String key_oppTxAc = "oppTxAc";//交易对手账号
	private static final String key_oppCiName = "oppCiName";//交易对手名称
	private static final String key_oppIdTyp = "oppIdTyp";//交易对手证件类型
	private static final String key_oppIdNo = "oppIdNo";//交易对手证件号码
	private static final String key_trTlr = "trTlr";//交易柜员
	
	public static final String START_KEY = key_txTime;//起始字段
	public static final String END_KEY = key_trTlr;//终止字段
	
	private final Map<String, String> fieldMap = new HashMap<String, String>();
	
	public void putField(String key, String value) {
		fieldMap.put(key, value);
	}
	
	public String getTxTime() {
		return fieldMap.get(key_txTime);
	}
	
	public String getJrnno() {
		return fieldMap.get(key_jrnNo);
	}
	
	public String getSubSeq() {
		return fieldMap.get(key_subSeq);
	}
	
	public String getLstCd() {
		return fieldMap.get(key_lstCd);
	}
	
	public String getLstDt() {
		return fieldMap.get(key_lstDt);
	}
	
	public String getTxBr() {
		return fieldMap.get(key_txBr);
	}
	
	public String getAcctBr() {
		return fieldMap.get(key_acctBr);
	}
	
	public String getCiNo() {
		return fieldMap.get(key_ciNo);
	}
	
	public String getCiTyp() {
		return fieldMap.get(key_ciTyp);
	}
	
	public String getCiName() {
		return fieldMap.get(key_ciName);
	}
	
	public String getIdTyp() {
		return fieldMap.get(key_idTyp);
	}
	
	public String getIdNo() {
		return fieldMap.get(key_idNo);
	}
	
	public String getChnl1() {
		return fieldMap.get(key_chnl1);
	}
	
	public String getTxAc() {
		return fieldMap.get(key_txAc);
	}
	
	public String getTxCcy() {
		return fieldMap.get(key_txCcy);
	}
	
	public String getTxAmt() {
		return fieldMap.get(key_txAmt);
	}
	
	public String getMmo() {
		return fieldMap.get(key_mmo);
	}
	
	public String getAlertType() {
		return fieldMap.get(key_alertType);
	}
	
	public String getRtType() {
		return fieldMap.get(key_rtType);
	}
	
	public String getOppBr() {
		return fieldMap.get(key_oppBr);
	}
	
	public String getOppCiTyp() {
		return fieldMap.get(key_oppCiTyp);
	}
	
	public String getOppTxAc() {
		return fieldMap.get(key_oppTxAc);
	}
	
	public String getOppCiName() {
		return fieldMap.get(key_oppCiName);
	}
	
	public String getOppIdTyp() {
		return fieldMap.get(key_oppIdTyp);
	}
	
	public String getOppIdNo() {
		return fieldMap.get(key_oppIdNo);
	}
	
	public String getTrTlr() {
		return fieldMap.get(key_trTlr);
	}
}
