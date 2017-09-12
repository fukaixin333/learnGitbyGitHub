/* =============================================
 *  Copyright (c) 2014-2015 by CITIC All rights reserved.
 *  Created [2015-04-29] 
 * =============================================
 */

package com.citic.server.domain;

/**
 * <p>Mc02_rt_task_para.java</p>
 * <p>Description: </p>
 * @author $Author:  $
 */

import java.io.Serializable;

import lombok.Data;

@Data
public class MC02_rt_task_para implements Serializable {

	/** 参数值 */
	private String para_val = "";

	/** 子项编码 */
	private String subnum = "";

	/** 任务执行编码 */
	private String taskkey = "";

	/** 参数编码 */
	private String para_code = "";

}
