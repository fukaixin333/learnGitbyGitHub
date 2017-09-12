/* =============================================
*  Copyright (c) 2014-2015 by CITIC All rights reserved.
*  Created [2016-08-22] 
* =============================================
*/

        
                                                  
/**
* <p>Br51_cxqq_back.java</p>
* <p>Description: </p>
* @author $Author:  $
*/

package  com.citic.server.shpsb.domain;       

import java.io.Serializable;


import lombok.Data; 
import lombok.EqualsAndHashCode; 
                                              
@Data                                                  
@EqualsAndHashCode(callSuper = false)                                                  
public class Br54_cxqq_back implements Serializable {
                                              
/**
	 * 
	 */
	private static final long serialVersionUID = -839759798727442214L;

//** 0-成功；1-失败； */
private String cljg = "0";
                                              
/** 最后修改时间 */
private String last_up_dt = "";                                           
                                              
/** 归属机构 */
private String orgkey = "";
                                              
/** 0未处理 1已发核心 2 已处理 */
private String status = "";
                                              
/** 报文批次号 */
private String msgseq = "";
                                              
/** 案号 */
private String ah = "";
                                              

                                              
/** 请求单号(案号_序号） */
private String bdhm = "";
                                              
/** 处理时间 */
private String dealing_time = "";
                                              
/** 报文校验结果 */
private String msgcheckresult = "";
                                              
/** 查询日期（分区） */
private String qrydt = "";
                                              
/** 处理人 */
private String dealing_p = "";
                                              
/** 操作失败相关信息，成功则返回为空。
操作成功但无结果，需注明“未找到符合请求条件的数据” */
private String czsbyy = "";
                                              
                                              
}
