/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/11 11:46:07$
 */
package com.citic.server.runtime;

/**
 * 任务过程日志
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/11 11:46:07$
 */
public class TaskLog {
	private int success = 0;
	private int fail = 0;
	private int repeat = 0;
	private int invaild = 0;
	private int total = 0;
	
	private long li1 = 0L;
	private long li2 = 0L;
	
	public void logSuccess() {
		success++;
		total++;
	}
	
	public void logFail() {
		fail++;
		total++;
	}
	
	public void logRepeat() {
		repeat++;
		total++;
	}
	
	public void logInvaild() {
		invaild++;
		total++;
	}
	
	public int success() {
		return this.success;
	}
	
	public int fail() {
		return this.fail;
	}
	
	public int repeat() {
		return this.repeat;
	}
	
	public int invaild() {
		return this.invaild;
	}
	
	public int total() {
		return this.total;
	}
	
	public void reset() {
		this.success = 0;
		this.fail = 0;
		this.repeat = 0;
		this.invaild = 0;
		this.total = 0;
		this.li1 = 0L;
		this.li2 = 0L;
	}
	
	public void start() {
		this.reset();
		this.li1 = System.currentTimeMillis();
	}
	
	public void flip() {
		this.li2 = System.currentTimeMillis();
	}
	
	public String log() {
		StringBuilder log = new StringBuilder();
		log.append("Time=[" + (li2 - li1) + "]ms ");
		log.append(": Total=[" + total + "]");
		log.append(", Success=[" + success + "]");
		log.append(", Fail=[" + fail + "]");
		log.append(", Repeat=[" + repeat + "]");
		log.append(", Invaild=[" + invaild + "]");
		return log.toString();
	}
}
