/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/26 10:55:03$
 */
package com.citic.server.cgb.domain.request;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/26 10:55:03$
 */
public class OnlineTPQueryFKWarnInput extends OnlineTPMessageInput {
	
	private static final String key_pageSize = "pageSize"; // 每页输出行数
	private static final String key_pageNum = "pageNum"; // 页码
	private static final String key_startTime = "startTime"; // 起始交易时间
	private static final String key_endTime = "endTime"; // 结束交易时间
	private static final String key_jrnNo = "jrnNo"; // 日志号
	private static final String key_lstCd = "lstCd"; // 名单大类
	private static final String key_lstDt = "lstDt"; // 名单细类
	private static final String key_filler1 = "filler1"; // 预留1
	private static final String key_filler2 = "filler2"; // 预留2
	
	@Override
	public void initDefaultField() {
		putField(key_jrnNo, ""); // 日志号
		putField(key_lstDt, ""); //名单细类
		putField(key_filler1, ""); //预留1
		putField(key_filler2, ""); //预留2
	}
	
	public void setStartTime(String startTime) {
		putField(key_startTime, startTime);
	}
	
	public void setEndTime(String endTime) {
		putField(key_endTime, endTime);
	}
	
	public void setJrnNo(String jrnNo) {
		putField(key_jrnNo, jrnNo);
	}
	
	public void setLstCd(String lstCd) {
		putField(key_lstCd, lstCd);
	}
	
	public void setLstDt(String lstDt) {
		putField(key_lstDt, lstDt);
	}
	
	public void setFiller1(String filler) {
		putField(key_filler1, filler);
	}
	
	public void setFiller2(String filler) {
		putField(key_filler2, filler);
	}
	
	public void setPageSize(String pageSize) {
		putField(key_pageSize, pageSize);
	}
	
	public void setPageNum(String pageNum) {
		putField(key_pageNum, pageNum);
	}
}
