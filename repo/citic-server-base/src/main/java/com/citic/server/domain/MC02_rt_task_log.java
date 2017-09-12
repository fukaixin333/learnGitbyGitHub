/* =============================================
 *  Copyright (c) 2014-2015 by CITIC All rights reserved.
 *  Created [2015-04-29] 
 * =============================================
 */

package com.citic.server.domain;

/**
 * <p>Mc02_rt_task_log.java</p>
 * <p>Description: </p>
 * @author $Author:  $
 */

import java.io.Serializable;

import lombok.Data;

@Data
public class MC02_rt_task_log implements Serializable {

	/** 错误描述 */
	private String errdes = "";

	/** 错误时间 */
	private String errtime = "";

	/** 错误类型 */
	private String errortype = "";

	/** 任务类别 */
	private String taskkey = "";

}
