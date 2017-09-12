package com.citic.server.cgb.domain.request;

import com.citic.server.cgb.domain.GatewayHeader;

public abstract class UnifiedPaymentMessageInput extends GatewayMessageInput {
	/** 请求系统标识（渠道大类） */
	public static final String key_BusiChnl = "busiChnl";
	/** 请求子系统标识 */
	public static final String key_BusiChnl2 = "busiChnl2";
	/** 请求系统日期 */
	public static final String key_BusiSysDate = "busiSysDate";
	/** 请求系统流水号 */
	public static final String key_BusiSysSerno = "busiSysSerno";
	/** 分行号 */
	public static final String key_Zoneno = "zoneno";
	/** 交易行所号 */
	public static final String key_Brno = "brno";
	/** 柜员号 */
	public static final String key_Tellerno = "tellerno";
	/** 商户号 */
	public static final String key_MercId = "mercId";
	/** 保留 */
	public static final String key_Reserved = "reserved";
	/** 源发起渠道大类 */
	public static final String key_OrigChnl = "origChnl";
	/** 源发起渠道中类 */
	public static final String key_OrigChnl2 = "origChnl2";
	/** 源发起渠道细分 */
	public static final String key_OrigChnlDtl = "origChnlDtl";
	
	public void initDefaultHeader() {
		putHeader(key_BusiChnl, "AFP");
		putHeader(key_BusiChnl2, "");
		putHeader(key_OrigChnl, "AFP");
		putHeader(key_OrigChnl2, "");
		putHeader(key_OrigChnlDtl, "");
	}
	
	@Override
	public GatewayHeader beforeCreateGatewayRequest(GatewayHeader gatewayHeader) {
		putHeader(key_BusiSysSerno, gatewayHeader.getSenderSN()); // 发起方流水号
		
		return super.beforeCreateGatewayRequest(gatewayHeader);
	}
	
	public String getBusisysdate() {
		return getFieldValue(key_BusiSysDate);
	}
	
	public String getZoneno() {
		return getFieldValue(key_Zoneno);
	}
	
	public String getBrno() {
		return getFieldValue(key_Brno);
	}
	
	public String getTellerno() {
		return getFieldValue(key_Tellerno);
	}
	
	public void setBusiSysDate(String busiSysDate) {
		putField(key_BusiSysDate, busiSysDate);
	}
	
	public void setZoneno(String zoneno) {
		putField(key_Zoneno, zoneno);
	}
	
	public void setBrno(String brno) {
		putField(key_Brno, brno);
	}
	
	public void setTellerno(String tellerno) {
		putField(key_Tellerno, tellerno);
	}
}
