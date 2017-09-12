package com.citic.server.gf.service;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.citic.server.gf.domain.Result;
import com.citic.server.gf.webservice.SyyhConvertDataServiceStub;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;

@Component("requestMessageService01")
public class RequestMessageService01 implements Keys, Constants {
	private final static Logger logger = LoggerFactory.getLogger(RequestMessageService01.class);
	
	private static final String WSDL_LOCATION = ServerEnvironment.getStringValue(GF_WSDL_LOCATION);
	private static final String USER_MARKER;
	static {
		String username = Utility.encodeGBKMIMEBase64(ServerEnvironment.getStringValue(GF_USERMARKER_USERNAME));
		String password = Utility.encodeGBKMIMEBase64(ServerEnvironment.getStringValue(GF_USERMARKER_PASSWORD));
		
		StringBuilder message = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		message.append("<usermarker>");
		message.append("<condition username=\"" + username + "\" password=\"" + password + "\"></condition>");
		message.append("</usermarker>");
		
		USER_MARKER = Utility.encodeGBKMIMEBase64(message.toString());
	}
	
	public SyyhConvertDataServiceStub getSyyhHandler() throws AxisFault {
		SyyhConvertDataServiceStub syyhService = new SyyhConvertDataServiceStub(WSDL_LOCATION);
		syyhService._getServiceClient().getOptions().setTimeOutInMilliSeconds(3 * 60 * 1000);
		syyhService._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, 3 * 60 * 1000); // Socket timeout
		syyhService._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, 30 * 1000); // Connection timeout
		return syyhService;
	}
	
	public Result shfeedXzcxInfo(String content) throws Exception {
		logger.info("反馈司法查询请求处理结果...");
		if (logger.isDebugEnabled()) {
			logger.debug("处理结果报文: [{}{}]", lineSeparator, content);
		}
		
		SyyhConvertDataServiceStub.ShfeedXzcxInfo param = new SyyhConvertDataServiceStub.ShfeedXzcxInfo();
		param.setArg0(USER_MARKER);
		param.setArg1(Utility.encodeGBKMIMEBase64(content));
		SyyhConvertDataServiceStub.ShfeedXzcxInfoResponse syyhResponse = getSyyhHandler().shfeedXzcxInfo(param);
		String realResult = Utility.decodeGBKMIMEBase64(syyhResponse.get_return());
		
		logger.info("解析反馈司法查询请求处理结果响应报文: [{}{}]", lineSeparator, realResult);
		return (Result) CommonUtils.unmarshallContext(Result.class, "binding_result1A", realResult);
	}
	
	public Result shfeedXzkzInfo(String content) throws Exception {
		logger.info("反馈司法控制请求处理结果..");
		if (logger.isDebugEnabled()) {
			logger.debug("处理结果报文: [{}{}]", lineSeparator, content);
		}
		SyyhConvertDataServiceStub.ShfeedXzkzInfo param = new SyyhConvertDataServiceStub.ShfeedXzkzInfo();
		param.setArg0(USER_MARKER);
		param.setArg1(Utility.encodeGBKMIMEBase64(content));
		SyyhConvertDataServiceStub.ShfeedXzkzInfoResponse syyhResponse = getSyyhHandler().shfeedXzkzInfo(param);
		String realResult = Utility.decodeGBKMIMEBase64(syyhResponse.get_return());
		
		logger.info("解析反馈司法查询请求处理结果响应报文: [{}{}]", lineSeparator, realResult);
		return (Result) CommonUtils.unmarshallContext(Result.class, "binding_result1A", realResult);
	}
}
