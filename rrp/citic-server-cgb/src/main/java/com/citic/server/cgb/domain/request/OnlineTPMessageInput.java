/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/25 23:40:09$
 */
package com.citic.server.cgb.domain.request;

/**
 * 在线交易平台
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/25 23:40:09$
 */
public abstract class OnlineTPMessageInput extends GatewayMessageInput {
	
	private static final String key_PageSize = "pageSize"; // 每页输出行数
	private static final String key_PageNum = "pageNum"; // 页码
	
	@Override
	public void initDefaultHeader() {
		putHeader(key_PageSize, "20");
		putHeader(key_PageNum, "1");
	}
	
	public void setPageSize(int pageSize) {
		putHeader(key_PageSize, String.valueOf(pageSize));
	}
	
	public void setPageSize(String pageSize) {
		putHeader(key_PageSize, pageSize);
	}
	
	public void setPageNum(int pageNum) {
		putHeader(key_PageNum, String.valueOf(pageNum));
	}
	
	public void setPageNum(String pageNum) {
		putHeader(key_PageNum, pageNum);
	}
}
