package com.citic.server.service.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 执行任务配置表
 * 
 * @author Liu Xuanfei
 * @date 2016年3月30日 下午9:27:21
 */
@Data
public class MC21_task implements Serializable {
	private static final long serialVersionUID = -5178546082202025088L;
	
	/** 报文编码 */
	private String txCode;
	
	/** 任务编码 */
	private String taskID;
	/** 任务名称 */
	private String taskName;
	/** 任务类别（1-高法；2-电信诈骗；3-公安） */
	private String taskType;
	/** 计算服务编码 */
	private String serverID;
	/** 任务拆分 */
	private String taskSplit;
	/** 拆分参数 */
	private String splitParams;
	/** 任务执行入口 */
	private String taskCMD;
	/** 是否人工处理 */
	private String isEmployee;
	/** 状态 */
	private String flag;
	/** 顺序 */
	private String seq;
	/** 备注 */
	private String des;
	/** 是否动态任务 */
	private String isDYNA;
	/** 自动执行时间 */
	private String execTime;
	/**  */
	private String freq = "1";
	/**  */
	private String tGroupID = "1";
	/**  */
	private String trigerID;
	
	/** multiThread: 是否多线程 */
	private String multiThread;
	
	public boolean isMultiThread0() {
		if (multiThread == null) {
			return false;
		}
		if ("1".equals(multiThread) || "Y".equals(multiThread) || "y".equals(multiThread)) {
			return true;
		}
		return false;
	}
}
