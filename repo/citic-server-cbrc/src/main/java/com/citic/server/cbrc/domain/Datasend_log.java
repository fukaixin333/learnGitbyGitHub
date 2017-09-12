/* =============================================
*  Copyright (c) 2014-2015 by CITIC All rights reserved.
*  Created [2016-08-18] 
* =============================================
*/

package com.citic.server.cbrc.domain;                
                                                  
/**
* <p>基础数据发送日志表</p>
* <p>Description: </p>
* @author $Author:  $
*/

import java.io.Serializable;

import lombok.Data; 
import lombok.EqualsAndHashCode; 
                                              
@Data                                                  
@EqualsAndHashCode(callSuper = false)                                                  
public class Datasend_log implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 4124979873717996031L;

/**监管类别 */
private String tasktype = "";
                                              
/** 基础信息类别 */
private String basename = "";
                                             
/** 发送时间 */
private String sendts = "";
                                              
/** 发送记录数 */
private String sendnum = "";
                                              
/**发送数据 */
private String senddata = "";
                                         
}
