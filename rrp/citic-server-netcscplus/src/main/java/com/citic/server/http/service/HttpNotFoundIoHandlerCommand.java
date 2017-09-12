package com.citic.server.http.service;

import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import com.citic.server.http.api.HttpStatus;
import com.citic.server.http.api.HttpVersion;
import com.citic.server.http.codec.HttpResponseMessage;

/**
 * @author Liu Xuanfei
 * @date 2017年6月15日 上午11:16:29
 */
@Component("httpNotFoundIoHandlerCommand")
public class HttpNotFoundIoHandlerCommand extends HttpProtocolIoHandlerCommand {
	
	public HttpNotFoundIoHandlerCommand() {
		registerNotFoundHandlerCommand(); // 
	}
	
	@Override
	public boolean initialize() {
		return true;
	}
	
	@Override
	public void executeAction(IoSession session, Object message) {
		HttpResponseMessage response = new HttpResponseMessage(HttpVersion.HTTP_1_1, HttpStatus.CLIENT_ERROR_NOT_FOUND);
		session.write(response);
	}
}
