/* =============================================
*  Copyright (c) 2014-2015 by CITIC All rights reserved.
*  Created [2015-04-29] 
* =============================================
*/

package com.citic.server.domain;          
                                                  
/**
* <p>Mc02_rt_tasklist.java</p>
* <p>Description: </p>
* @author $Author:  $
*/

import java.io.Serializable;

import lombok.Data;
                                              
@Data                                                                                                  
public class MC02_rt_tasklist implements Serializable {
                                              
/** 执行任务类,用于运算服务 */
private String exec_class = "";
                                              
/** 描述 */
private String des = "";
                                              
/** 任务名称 */
private String busi_name = "";
                                              
/** 任务类别   1、前台发起即时计算  2、定时计算
 */
private String task_type = "";
                                              
/** 按小时设置任务，逗号分隔，例如，11,12 */
private String settasktime = "";
                                              
/** 结果页面 */
private String resulturl = "";
                                              
/** 对应业务编码 */
private String busikey = "";
                                              
                                              
}
