package com.citic.server.gf.domain.response;

import lombok.Data;

import com.citic.server.dict.DictBean;

/**
 * 法院提供的司法查询请求内容
 * 
 * @author Liu Xuanfei
 * @date 2016年3月8日 上午10:52:32
 */
@Data
public class QueryResponse_Cxqq implements DictBean {
	private static final long serialVersionUID = 1L;
	
	/** 查询请求单号 */
	private String bdhm = "";
	
	/** 类别 */
	private String lb = "";
	
	/** 性质 */
	private String xz = "";
	
	/** 被查询人姓名 */
	private String xm = "";
	
	/** 证件类型 */
	private String zjlx = "";
	
	/** 被查询人证件/组织机构号码 */
	private String dsrzjhm = "";
	
	/** 执行法院名称 */
	private String fymc = "";
	
	/** 执行法院代码 */
	private String fydm = "";
	
	/** 承办法官 */
	private String cbr = "";
	
	/** 承办法官联系电话 */
	private String yhdh = "";
	
	/** 执行案号 */
	private String ah = "";
	
	/** 承办法官工作证编号 */
	private String gzzbh = "";
	
	/** 承办法官公务证编号 */
	private String gwzbh = "";
	
	/** 查询法律文书名称 */
	private String ckh = "";
	
	/** 账户资金往来交易信息查询开始时间 */
	private String ckkssj = "";
	
	/** 账户资金往来交易信息查询结束时间 */
	private String ckjssj = "";
	
	/** 文书编号 */
	private String wsbh = "";
	
	// ==========================================================================================
	//                     Help Field
	// ==========================================================================================
	private String taskKey;
	private String msgPath;
	private String msgName;
	
	private String status = "";
	private String orgkey = "";
	private String party_id = "";
	private String khzh = "";
	private String msg_type_cd = "";
	private String packetkey = "";
	private String qrydt = "";
	private String msgcheckresult = "";
	private String recipient_time = "";
	
	@Override
	public String getGroupId() {
		return "QUERYRESPONSE_CXQQ";
	}
}
