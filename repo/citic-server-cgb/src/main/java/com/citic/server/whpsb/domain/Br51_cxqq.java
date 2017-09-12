/* =============================================
*  Copyright (c) 2014-2015 by CITIC All rights reserved.
*  Created [2016-08-22] 
* =============================================
*/

package  com.citic.server.whpsb.domain;                
                                                  
/**
* <p>Br51_cxqq.java</p>
* <p>Description: </p>
* @author $Author:  $
*/


import java.io.Serializable;

import lombok.Data; 
import lombok.EqualsAndHashCode; 
                                              
@Data                                                  
@EqualsAndHashCode(callSuper = false)                                                  
public class Br51_cxqq implements Serializable {
                                              
/**
	 * 
	 */
	private static final long serialVersionUID = 890136524179899745L;

/** 报文批次号 */
private String msgseq = "";
                                              
/** 最后修改时间 */
private String last_up_dt = "";
                                              
/** 接收时间 */
private String recipient_time = "";
                                              
/** 总记录数 */
private String count = "";
                                              
/** 银行代码 */
private String yhdm = "";
                                              
/** 接收人 */
private String recipient_p = "";
                                              
/** 归属机构 */
private String orgkey = "";
                                              
/** 
 * 此处码值修改了，请以次出为准
 *0：待处理 1：待生成报文 2：待打包反馈 3：反馈成功 4：反馈失败
 */
private String status = "";
                                              
/** 公安操作时间 */
private String czsj = "";
                                              
/** 查询日期（分区） */
private String qrydt = "";
/** 反馈人 */
private String feedback_p = "";
/** 反馈时间 */
private String feedback_time = "";


private String packetkey="";
                                              
private String packetname="";                                           
}
