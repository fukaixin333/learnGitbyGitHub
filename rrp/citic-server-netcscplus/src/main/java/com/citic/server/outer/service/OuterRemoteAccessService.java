package com.citic.server.outer.service;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.springframework.stereotype.Service;

import com.citic.server.basic.service.AbstractRemoteAccessService;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.PrefixedStringCodecFactory;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;

/**
 * 四川公安厅涉案账户资金查控
 * 
 * @author Liu Xuanfei
 * @date 2016年6月14日 上午9:48:52
 */
@Service("outerRemoteAccessService6")
public class OuterRemoteAccessService extends AbstractRemoteAccessService {
	
	@Override
	public ProtocolCodecFactory getProtocolCodecFactory() {
		int prefixLength = ServerEnvironment.getIntValue(Keys.OUTER_MESSAGE_PREFIX_LENGTH, DEFAULT_MESSAGE_PREFIX_LENGTH);
		return new PrefixedStringCodecFactory(StandardCharsets.UTF_8, prefixLength);
	}
	
	@Override
	public String getSocketAddressHost() {
		return ServerEnvironment.getStringValue(Keys.OUTER_REMOTE_ACCESS_HOST);
	}
	
	@Override
	public int getSocketAddressPort() {
		return ServerEnvironment.getIntValue(Keys.OUTER_REMOTE_ACCESS_PORT);
	}
}
