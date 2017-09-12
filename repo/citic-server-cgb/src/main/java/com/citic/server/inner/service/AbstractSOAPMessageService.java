package com.citic.server.inner.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.citic.server.cgb.domain.GatewayHeader;
import com.citic.server.cgb.domain.request.GatewayMessageInput;
import com.citic.server.cgb.domain.request.GatewayRequest;
import com.citic.server.cgb.domain.response.GatewayMessageResult;
import com.citic.server.cgb.domain.response.GatewayResponse;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.service.time.SequenceService;
import com.citic.server.utils.CommonUtils;

public abstract class AbstractSOAPMessageService implements ISOAPMessageService {
	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractSOAPMessageService.class);
	
	@Autowired
	@Qualifier("innerTransSerialNumber")
	private SequenceService transSerialNumberService;
	
	public GatewayHeader createGatewayHeader(String receiverId, String tradeCode) {
		GatewayHeader gatewayHeader = new GatewayHeader();
		gatewayHeader.setReceiverId(receiverId); // 接收方系统标识
		gatewayHeader.setSenderId(ServerEnvironment.getStringValue(Keys.SYSTEM_CODE)); // 发起放系统标识
		gatewayHeader.setSenderSN(getNextSenderSN(getNextTransSerialNumber(), 22));
		gatewayHeader.setSenderDate(Utility.currDate8());
		gatewayHeader.setSenderTime(Utility.currTime6());
		gatewayHeader.setTradeCode(tradeCode); // 交易代码
		gatewayHeader.setCommCode(GatewayHeader.COMMCODE_REQUEST);
		return gatewayHeader;
	}
	
	public GatewayMessageResult writeRequestMessage(GatewayMessageInput input, String receiver, String code, String bname) throws DataOperateException, RemoteAccessException {
		GatewayHeader gatewayHeader = createGatewayHeader(receiver, code);
		GatewayRequest gatewayRequest = GatewayRequest.createGatewayRequest(gatewayHeader, input);
		
		String requestMessage;
		try {
			requestMessage = CommonUtils.marshallContext(gatewayRequest, true, "GB18030");
		} catch (JiBXException e) {
			throw new RemoteAccessException("Can't marshall object to context.", e);
		}
		
		LOGGER.info("通过HTTP发送SOAP请求报文：[{}{}]", CommonUtils.localLineSeparator(), requestMessage);
		String responseMessage = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		try {
			HttpPost httpPost = new HttpPost(ServerEnvironment.getStringValue(REMOTE_ACCESS_GATEWAY_URL));
			httpPost.setEntity(new StringEntity(requestMessage, StandardCharsets.GBK));
			
			httpResponse = httpClient.execute(httpPost);
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new RemoteAccessException(statusCode + " (" + statusLine.getReasonPhrase() + ")");
			}
			
			httpEntity = httpResponse.getEntity();
			InputStream in = httpEntity.getContent();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] byts = new byte[2048];
			int len = 0;
			while ((len = in.read(byts)) != -1) {
				baos.write(byts, 0, len);
			}
			responseMessage = new String(baos.toByteArray(), StandardCharsets.GBK);
		} catch (IOException e) {
			throw new RemoteAccessException("网络通讯异常", e);
		} finally {
			try {
				EntityUtils.consume(httpEntity);
				if (httpResponse != null) {
					httpResponse.close();
				}
				httpClient.close();
			} catch (Exception e) {
			}
		}
		
		LOGGER.info("成功收到SOAP响应报文: [{}]", responseMessage);
		GatewayResponse gatewayResponse;
		try {
			gatewayResponse = CommonUtils.unmarshallContext(GatewayResponse.class, bname, responseMessage);
		} catch (JiBXException e) {
			LOGGER.error("解析SOAP响应报文异常：{}", e.getMessage(), e);
			throw new RemoteAccessException("Can't unmarshall context to object 'GatewayResponse'.", e);
		}
		
		gatewayHeader = gatewayResponse.getGatewayHeader();
		String gwErrorCode = gatewayHeader.getGwErrorCode();
		if ("00".equals(gwErrorCode)) {
			String gwErrorMessage = gatewayHeader.getGwErrorMessage();
			if (!(gwErrorMessage == null || gwErrorMessage.length() == 0)) {
				throw new RemoteAccessException(gatewayHeader.getGwErrorCode(), "Gateway error：" + gatewayHeader.getGwErrorMessage());
			}
		}
		
		return gatewayResponse.getGatewayResult();
	}
	
	protected String getNextTransSerialNumber() {
		return transSerialNumberService.next();
	}
	
	protected String getNextSenderSN(String transSerialNumber, int length) {
		if (StringUtils.isBlank(transSerialNumber)) {
			return "";
		}
		int tsLength = transSerialNumber.length();
		if (tsLength > length) {
			return transSerialNumber.substring(tsLength - length);
		} else if (tsLength == length) {
			return transSerialNumber;
		}
		return StringUtils.leftPad(transSerialNumber, length, "0");
	}
	
}
