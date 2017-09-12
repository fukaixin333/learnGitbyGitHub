/* =============================================
 *  Copyright (c) 2014-2015 by CITIC All rights reserved.
 *  Created [2015-04-29] 
 * =============================================
 */

package com.citic.server.domain;

/**
 * <p>Mc02_rt_task_fact.java</p>
 * <p>Description: </p>
 * @author $Author:  $
 */

import java.io.Serializable;
import java.util.HashMap;

import lombok.Data;

@Data
public class MC02_rt_task_fact implements Serializable {
	 
	/** 扫描时间 */
	private String scantime = "";

	/** 创建人 */
	private String creat_user = "";

	/** 创建时间 */
	private String creat_dt = "";

	/** 业务描述 */
	private String objdes = "";

	/** 对应业务编码 */
	private String busikey = "";

	/** 计算状态 0失败；2未计算；3正在计算；4已计算; 5重新计算; 6等待计算 */
	private String calstatus = "";

	/** 线程编码 */
	private String theadkey = "";

	/** 计算时间 */
	private String usetime = "";

	/** 任务编码 */
	private String taskkey = "";

	/** 开始时间 */
	private String begintime = "";

	/** 服务编码 */
	private String serverkey = "";

	/** 数据时间 */
	private String statisticdate = "";

	/** 结束时间 */
	private String endtime = "";

	/** 业务主键 */
	private String objkey = "";
	/** 执行类 */
	private String exec_class = "";
	/** 参数 */
	private HashMap Paramap = new HashMap();
	private String settasktime = "";
	private String businame = "";
	private String task_type = "";
}
