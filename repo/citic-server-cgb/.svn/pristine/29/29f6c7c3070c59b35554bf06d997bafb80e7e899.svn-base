package com.citic.server.jsga.domain.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 凭证图像查询主体数据项
 * <ul>
 * <li>仅“国安”和“检察院”有此请求，其它监管无此请求。
 * <li>“国安”报文中无<em>[任务流水号]</em>字段。
 * <li>“国安”报文中分<em>[查询账号]</em>和<em>[查询卡号]</em>，字段名分别为<strong>ZH</strong>和<strong>KH</strong>。
 * <li>“国安”报文中<em>[交易流水号]</em>字段名为<strong>JYLSH</strong>，<em>[凭证图像类型]<em>字段名为<strong>PZTXLX</strong>。
 * <li>“检察院”报文只包含 <strong>CXZH</strong>，无<strong>CXKH</strong>。
 * <li>
 * </ul>
 * 
 * @author Liu Xuanfei
 * @date 2016年7月5日 下午5:40:22
 */
@Data
public class JSGA_QueryScanResponse_Info implements Serializable {
	private static final long serialVersionUID = -5859492321744319049L;
	
	/** 任务流水号 */
	private String rwlsh;
	
	/** 交易流水号 */
	private String jyls;
	
	/** 查询种类/凭证图像类型 */
	private String cxzl;
	
	/** 查询账号/卡号 */
	private String cxzh;
	
	/** 查询卡号 */
	private String cxkh;
	
	/** 查询内容 */
	private String cxnr;
}
