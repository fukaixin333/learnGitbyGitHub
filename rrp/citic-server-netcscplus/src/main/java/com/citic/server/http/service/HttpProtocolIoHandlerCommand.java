package com.citic.server.http.service;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.chain.IoHandlerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citic.server.http.api.HttpRequestMessage;
import com.citic.server.http.buf.MessageBytes;
import com.citic.server.runtime.conf.HttpListenCommand;

/**
 * HTTP协议<code>IoHandlerCommand</code>
 * 
 * @author liuxuanfei
 * @date 2017年4月24日 下午4:20:34
 */
public abstract class HttpProtocolIoHandlerCommand implements IoHandlerCommand {
	private final Logger logger = LoggerFactory.getLogger(HttpProtocolIoHandlerCommand.class);
	
	private boolean isHttpNotFoundHandlerCommand = false;
	
	private HttpListenCommand httpListenCommand = null;
	
	public void register(HttpListenCommand command) throws Exception {
		this.httpListenCommand = command;
		initialize();
	}
	
	protected final void registerNotFoundHandlerCommand() {
		isHttpNotFoundHandlerCommand = true;
	}
	
	public abstract boolean initialize() throws Exception;
	
	public abstract void executeAction(IoSession session, Object message);
	
	public void execute(NextCommand next, IoSession session, Object message) {
		try {
			if (checkable(message)) {
				executeAction(session, message);
			} else {
				next.execute(session, message);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	protected boolean checkable(Object message) throws Exception {
		if (isHttpNotFoundHandlerCommand) {
			return true;
		}
		
		MessageBytes uri = ((HttpRequestMessage) message).requestURI();
		if (uri == null || uri.isNull()) {
			return false;
		}
		
		return uri.equalsVal(httpListenCommand.getUri());
	}
}
