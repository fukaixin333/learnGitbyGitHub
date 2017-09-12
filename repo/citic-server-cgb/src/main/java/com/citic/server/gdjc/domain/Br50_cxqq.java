/* =============================================
*  Copyright (c) 2014-2015 by CITIC All rights reserved.
*  Created [2016-08-18] 
* =============================================
*/

package com.citic.server.gdjc.domain;                
                                                  
/**
* <p>Br50_cxqq.java</p>
* <p>Description: </p>
* @author $Author:  $
*/

import java.io.Serializable;

import lombok.Data; 
import lombok.EqualsAndHashCode; 
                                              
@Data                                                  
@EqualsAndHashCode(callSuper = false)                                                  
public class Br50_cxqq implements Serializable {
                                              
/**
	 * 
	 */
	private static final long serialVersionUID = 3077280547354715839L;

/** 接收人 */
private String recipient_p = "";
                                              
/** 最后修改时间 */
private String last_up_dt = "";
                                              
/**
 * 此处码值修改了，请以次出为准
 *  0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 */
private String status = "";
                                              
/** 案件编号 */
private String caseno = "";
                                              
/** 协作编号 */
private String docno = "";
                                              
/** 接收时间 */
private String recipient_time = "";
                                              
/** 侦办单位名称 */
private String exeunit = "";
                                              
/** 1：账户 2：交易 */
private String datasource = "";
                                              
/** 案件名称 */
private String casename = "";
                                              
/** 查询日期（分区） */
private String qrydt = "";
                                              
/** 归属机构 */
private String orgkey = "";
/** 唯一标识 */
private String uniqueid = "";
                                              
                                              
}
