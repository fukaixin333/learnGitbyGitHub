package com.citic.server.dx.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cfca.safeguard.Result;
import cfca.safeguard.api.bank.ClientEnvironment;
import cfca.safeguard.api.bank.SGBusiness;
import cfca.safeguard.api.bank.util.ResultUtil;

import com.citic.server.SpringContextHolder;
import com.citic.server.dx.domain.RequestMessage;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.request.CaseRequest100401;
import com.citic.server.dx.domain.request.FreezeRequest100202;
import com.citic.server.dx.domain.request.FreezeRequest100204;
import com.citic.server.dx.domain.request.FreezeRequest100206;
import com.citic.server.dx.domain.request.QueryRequest100302;
import com.citic.server.dx.domain.request.QueryRequest100304;
import com.citic.server.dx.domain.request.QueryRequest100306;
import com.citic.server.dx.domain.request.QueryRequest100308;
import com.citic.server.dx.domain.request.QueryRequest100310;
import com.citic.server.dx.domain.request.StoppayRequest100102;
import com.citic.server.dx.domain.request.StoppayRequest100104;
import com.citic.server.dx.domain.request.StoppayRequest100106;
import com.citic.server.dx.domain.request.SuspiciousRequest100403;
import com.citic.server.dx.domain.request.SuspiciousRequest100404;
import com.citic.server.dx.domain.request.SuspiciousRequest100405;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.service.time.SequenceService;
import com.citic.server.utils.CommonUtils;

/**
 * 报文反馈（上行）服务
 * 
 * @author Liu Xuanfei
 * @date 2016年4月22日 下午1:05:53
 */
@Service("requestMessageService")
public class RequestMessageService {
	private Logger logger = LoggerFactory.getLogger(RequestMessageService.class);
	
	private SequenceService transSerialNumberService;
	
	private SGBusiness sgBusiness = null;
	
	private String netError = "603202";//网络异常代码
	private int NUM = 3;//重发次数
	
	public RequestMessageService() throws Exception {
		ClientEnvironment.initTxClientEnvironment(ServerEnvironment.getStringValue(Keys.SAFEGUARD_RESOURCE_BUNDLE_BASENAME));
		sgBusiness = new SGBusiness();
	}
	
	/**
	 * 止付反馈
	 * 
	 * @param request 止付反馈报文
	 * @return 响应报文
	 */
	public Response sendStoppayRequest100102(StoppayRequest100102 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 止付解除反馈
	 * 
	 * @param request 止付解除反馈报文
	 * @return 响应报文
	 */
	public Response sendStoppayRequest100104(StoppayRequest100104 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 止付延期反馈
	 * 
	 * @param request 止付延期反馈报文
	 * @return 响应报文
	 */
	public Response sendStoppayRequest100106(StoppayRequest100106 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 冻结反馈
	 * 
	 * @param request 冻结反馈报文
	 * @return 响应报文
	 */
	public Response sendFreezeRequest100202(FreezeRequest100202 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 冻结解除反馈
	 * 
	 * @param request 冻结解除反馈报文
	 * @return 响应报文
	 */
	public Response sendFreezeRequest100204(FreezeRequest100204 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 冻结延期反馈
	 * 
	 * @param request 冻结延期反馈报文
	 * @return 响应报文
	 */
	public Response sendFreezeRequest100206(FreezeRequest100206 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 账户交易明细查询反馈
	 * 
	 * @param request 账户交易明细查询反馈报文
	 * @return 响应报文
	 */
	public Response sendQueryRequest100302(QueryRequest100302 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 账户持卡主体查询反馈
	 * 
	 * @param request 账户持卡主体查询反馈报文
	 * @return 响应报文
	 */
	public Response sendQueryRequest100304(QueryRequest100304 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 账户动态查询反馈
	 * 
	 * @param request 账户动态查询反馈报文
	 * @return 响应报文
	 */
	public Response sendQueryRequest100306(QueryRequest100306 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 账户动态查询解除反馈
	 * 
	 * @param request 账户动态查询解除反馈报文
	 * @return 响应报文
	 */
	public Response sendQueryRequest100308(QueryRequest100308 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 客户全账户查询反馈
	 * 
	 * @param request 客户全账户查询反馈报文
	 * @return 响应报文
	 */
	public Response sendQueryRequest100310(QueryRequest100310 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 案件举报
	 * 
	 * @param request 案件举报报文
	 * @return 响应报文
	 */
	public Response sendCaseRequest100401(CaseRequest100401 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 可疑名单上报-异常开卡
	 * 
	 * @param request 异常开卡上报报文
	 * @return 响应报文
	 */
	public Response sendSuspiciousRequest100403(SuspiciousRequest100403 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 可疑名单上报-涉案账户
	 * 
	 * @param request 涉案账户上报报文
	 * @return 响应报文
	 */
	public Response sendSuspiciousRequest100404(SuspiciousRequest100404 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	/**
	 * 可疑名单上报-异常事件
	 * 
	 * @param request 异常事件上报报文
	 * @return 响应报文
	 */
	public Response sendSuspiciousRequest100405(SuspiciousRequest100405 request) throws RemoteAccessException {
		return writeRequestMessage(request);
	}
	
	public Response sendExceptionMessage(String txCode, String messageFrom, String applicationID, String prefix, String result, String feedbackRemark) throws RemoteAccessException {
		transSerialNumberService = SpringContextHolder.getBean("outerTransSerialNumber");
		StringBuilder requestXML = new StringBuilder();
		requestXML.append("<Response>");
		requestXML.append("<Head>");
		requestXML.append("<Mode>01</Mode>");
		requestXML.append("<To>" + messageFrom + "</To>");
		requestXML.append("<TxCode>" + txCode + "</TxCode>");
		requestXML.append("<TransSerialNumber>" + prefix + transSerialNumberService.next() + "</TransSerialNumber>");
		requestXML.append("</Head>");
		requestXML.append("<Body>");
		requestXML.append("<ApplicationID>" + applicationID + "</ApplicationID>");
		requestXML.append("<Result>" + result + "</Result>");
		requestXML.append("<FeedbackRemark>" + feedbackRemark + "</FeedbackRemark>");
		requestXML.append("</Body>");
		requestXML.append("</Response>");
		return writeRequestMessage(requestXML.toString());
	}
	
	// ==========================================================================================
	//                     Helper Behavior
	// ==========================================================================================
	private Response writeRequestMessage(RequestMessage request) throws RemoteAccessException {
		String requestXML;
		try {
			requestXML = CommonUtils.marshallContext(request); // 使用JiBX绑定文件生成XML报文
			logger.info("使用JiBX生成XML报文-[RequestMessage]:" + CommonUtils.localLineSeparator() + requestXML);
		} catch (JiBXException e) {
			throw new RemoteAccessException(e.getMessage(), e);
		}
		return writeRequestMessage(requestXML);
	}
	
	private Response writeRequestMessage(String requestXML) throws RemoteAccessException {
		//		String responseXML;
		//		try {
		//			responseXML = sgBusiness.sendPackagedRequestXML(requestXML); // 使用CFCA提供的接口发送XML报文
		//			logger.info("使用CFCA API发送并返回响应XML报文-[Response]:" + CommonUtils.localLineSeparator() + responseXML);
		//		} catch (Exception e) {
		//			throw new RemoteAccessException(e.getMessage(), e);
		//		}
		//		Response response;
		//		try {
		//			response = (Response) CommonUtils.unmarshallContext(Response.class, responseXML); // 使用JiBX绑定文件解析响应报文
		//		} catch (JiBXException e) {
		//			logger.warn("使用JiBX解析响应XML报文出现异常，尝试使用CFCA API解析响应报文……");
		//			Result result = ResultUtil.changeXMLToResult(responseXML); // 使用CFCA提供的函数解析响应报文
		//			response = new Response();
		//			try {
		//				BeanUtils.copyProperties(response, result); // Copy property values
		//			} catch (IllegalAccessException ex) {
		//				throw new RemoteAccessException(e.getMessage(), ex);
		//			} catch (InvocationTargetException ex) {
		//				throw new RemoteAccessException(e.getMessage(), ex);
		//			}
		//		}
		//		return response;
		//当网络异常时，重发报文
		//重发要求：返回码为网络异常，重发次数不超过3次
		int count = 0;
		Response response = doSendMsg(requestXML);
		while (count < NUM && StringUtils.equals(netError, response.getCode())) {
			count++;
			response = doSendMsg(requestXML);
		}
		return response;
	}
	
	private Response doSendMsg(String requestXML) throws RemoteAccessException {
		String responseXML;
		try {
			responseXML = sgBusiness.sendPackagedRequestXML(requestXML); // 使用CFCA提供的接口发送XML报文
			logger.info("使用CFCA API发送并返回响应XML报文-[Response]:" + CommonUtils.localLineSeparator() + responseXML);
		} catch (Exception e) {
			throw new RemoteAccessException(e.getMessage(), e);
		}
		Response response;
		try {
			response = (Response) CommonUtils.unmarshallContext(Response.class, responseXML); // 使用JiBX绑定文件解析响应报文
		} catch (JiBXException e) {
			logger.warn("使用JiBX解析响应XML报文出现异常，尝试使用CFCA API解析响应报文……");
			Result result = ResultUtil.changeXMLToResult(responseXML); // 使用CFCA提供的函数解析响应报文
			response = new Response();
			try {
				BeanUtils.copyProperties(response, result); // Copy property values
			} catch (IllegalAccessException ex) {
				throw new RemoteAccessException(e.getMessage(), ex);
			} catch (InvocationTargetException ex) {
				throw new RemoteAccessException(e.getMessage(), ex);
			}
		}
		return response;
	}
}
