package com.citic.server.http.service;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.chain.ChainedIoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citic.server.http.HttpConstants;
import com.citic.server.http.api.HttpStatus;
import com.citic.server.http.api.HttpVersion;
import com.citic.server.http.codec.HttpResponseMessage;

/**
 * HTTP协议<code>IoHandler</code>链
 * 
 * @author Liu Xuanfei
 * @date 2016年9月13日 下午4:08:55
 */
public class HttpProtocolChainedIoHandler extends ChainedIoHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpProtocolChainedIoHandler.class);
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("OPENED");
		}
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60); // set idle time to 60 seconds
		session.setAttribute(HttpConstants.HTTP_KEY, Integer.valueOf(0)); // initial http is zero
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("CLOSED");
		}
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		session.closeNow();
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("IDLE");
		}
		session.closeNow(); // disconnect an idle client
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		LOGGER.warn("Exception: {}", cause.getMessage(), cause);
		Integer in = (Integer) session.getAttribute(HttpConstants.HTTP_KEY);
		if (in != null && in.intValue() == 0) {
			session.setAttribute(HttpConstants.HTTP_KEY, Integer.valueOf(1));
			HttpResponseMessage response = new HttpResponseMessage(HttpVersion.HTTP_1_1, HttpStatus.SERVER_ERROR_NOT_IMPLEMENTED);
			session.write(response);
		} else {
			session.closeNow(); // close the connection on exceptional situation
		}
	}
}
