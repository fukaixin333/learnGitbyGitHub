package com.citic.server.cbrc.domain.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 凭证图像反馈结果数据项
 * <ul>
 * <li>仅“国安”和“检察院”使用此数据项。
 * <li><em>[请求单标识]</em>、<em>[查询卡号]</em>等为“国安”报文特有字段。
 * <li>“国安”报文中<em>[查询账号]<em>和<em>[查询卡号]</em>等字段分别名为<strong>ZH</strong>和<strong>KH</strong>。
 * <li>“国安”报文中<em>[交易流水号]<em>字段名为<strong>JYLS</strong>。
 * <li>“国安”报文中<em>[凭证图像名称]<em>字段名为<strong>TXWJM</strong>。
 * <li><em>[查询反馈结果]<em>、<em>[查询反馈结果原因]<em>、<em>[查询种类]<em>及<em>[凭证图像序号]<em>为“检察院”报文特有字段。
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 上午10:57:57
 */
@Data
public class CBRC_QueryScanRequest_Record implements Serializable {
	private static final long serialVersionUID = -2522287084972578383L;
	
	/** 请求单标识 */
	private String qqdbs;
	
	/** 任务流水号 */
	private String rwlsh;
	
	/** 查询反馈结果 */
	private String cxfkjg;
	
	/** 查询反馈结果原因 */
	private String cxfkjgyy;
	
	/** 查询账号 */
	private String cxzh="";
	
	/** 查询卡号 */
	private String cxkh="";
	
	/** 查询种类 */
	private String cxzl;
	
	/** 交易流水号 */
	private String jylsh;
	
	/** 凭证图像类型 */
	private String pztxlx;
	
	/** 凭证图像序号 */
	private String pztxxh;
	
	/** 凭证图像名称 */
	private String pztxmc;
	
	/** 备注 */
	private String beiz;

	
	
	
}
