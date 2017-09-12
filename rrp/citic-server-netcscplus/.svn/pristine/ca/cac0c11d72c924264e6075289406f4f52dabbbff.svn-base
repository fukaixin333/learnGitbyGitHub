package com.citic.server.inner;

import org.springframework.stereotype.Service;

import com.citic.server.basic.AbstractPollingServer;

/**
 * 内联轮询服务（用于动态查询、可疑名单推送等）
 * 
 * @author liuxuanfei
 * @date 2016年5月9日 下午5:48:32
 */
@Service("innerPollingServer")
public class InnerPollingServer extends AbstractPollingServer {
	
	@Override
	public int listenServerType() {
		return SERVER_TYPE_INNER;
	}
}
