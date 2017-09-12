package com.citic.server.inner.service;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.springframework.stereotype.Service;

import com.citic.server.basic.service.AbstractRemoteAccessService;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.PrefixedStringCodecFactory;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;

@Service("innerRemoteAccessService")
public class InnerRemoteAccessService extends AbstractRemoteAccessService {
	
	@Override
	public ProtocolCodecFactory getProtocolCodecFactory() {
		int prefixLength = ServerEnvironment.getIntValue(Keys.INNER_MESSAGE_PREFIX_LENGTH, DEFAULT_MESSAGE_PREFIX_LENGTH);
		return new PrefixedStringCodecFactory(StandardCharsets.UTF_8, prefixLength);
	}
	
	@Override
	public String getSocketAddressHost() {
		return ServerEnvironment.getStringValue(Keys.INNER_REMOTE_ACCESS_HOST);
	}
	
	@Override
	public int getSocketAddressPort() {
		return ServerEnvironment.getIntValue(Keys.INNER_REMOTE_ACCESS_PORT);
	}
}