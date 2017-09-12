package com.citic.server.gdjg.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Br57_cxqq_back implements Serializable{
	private static final long serialVersionUID = 3878046514566866034L;

	/** 协作编号 */
	private String docno= "";
	/** 案件编号 */
	private String caseno= "";  
	/** 唯一标识 */
	private String uniqueid= "";
	
	/** 查询日期（分区） */
	private String qrydt = "";
	/**
	 * 此处码值修改了，请以次出为准
	 * 0：待处理 1：待生成报文 3：反馈成功 4：反馈失败
	 */
	private String status = "";
	/** 最后修改时间 */
	private String last_up_dt = "";
	
	/** 对该查询任务的查询结果描述，成功或失败；01表示成功，02表示失败； */
	private String cxfkjg = "";
	                                                                                       
	/** 反馈时间 */
	private String feedback_time = "";
	                                              
	/** 反馈人 */
	private String feedback_p = "";
	                                              
	/** 操作失败相关信息，成功则返回为空。
	操作成功但无结果，需注明“未找到符合请求条件的数据” */
	private String czsbyy = "";
	                                              
	/** 处理时间 */
	private String dealing_time = "";
	                                              
	/** 处理人 */
	private String dealing_p = "";
	                                                                                           
	/** 报文校验结果 */
	private String msgcheckresult = "";
	                                                                                            
	                                              
	/** 归属机构 */
	private String orgkey = "";
	
	
	/** 查询结束时间 */
	private String queryendtime= "";
	/** 查询反馈结果  01表示成功，02表示失败；*/
	private String queryresult= "";
	/** 查询反馈结果原因 */
	private String reason= "";
	/** 主机查询时间 */
	private String querytime= "";
}
