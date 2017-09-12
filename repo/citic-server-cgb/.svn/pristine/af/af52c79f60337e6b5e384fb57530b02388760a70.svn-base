package com.citic.server.inner.service;

import org.apache.commons.lang3.StringUtils;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.citic.server.basic.service.IRemoteAccessService;
import com.citic.server.inner.domain.RequestMessage;
import com.citic.server.inner.domain.ResponseMessage;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.service.time.SequenceService;
import com.citic.server.utils.CommonUtils;

/**
 * 与Artery通讯服务
 * 
 * @author Liu Xuanfei
 * @date 2016年4月12日 下午11:38:52
 */
public abstract class AbstractPrefixMessageService implements IPrefixMessageService, Codes {
	private Logger logger = LoggerFactory.getLogger(IPrefixMessageService.class);
	
	@Autowired
	@Qualifier("innerRemoteAccessService")
	private IRemoteAccessService mina;
	
	@Autowired
	@Qualifier("innerTransSerialNumber")
	private SequenceService transSerialNumberService;
	
	// ==========================================================================================
	//                     Helper Behavior
	// ==========================================================================================
	protected boolean isLastPage(ResponseMessage result, int pageRow) {
		if (result.isLastPage() // 
			|| result.getCurrPage().intValue() == result.getTotalPage().intValue() // 
			|| result.getTotalPage() <= 1 //
			|| result.getCurrPageRow() < pageRow) {
			return true;
		}
		
		return false;
	}
	
	protected boolean hasResultSet(ResponseMessage result) {
		if (!result.hasResultSet() || result.getCurrPageRow() <= 0) {
			return false;
		}
		return true;
	}
	
	protected ResponseMessage writeRequestMessage(RequestMessage in, Class<?> clazz) throws DataOperateException, RemoteAccessException {
		in.setTransSerialNumber(getNextTransSerialNumber());
		in.setSenderSN(getNextSenderSN(in.getTransSerialNumber(), 22));
		if (in.getSenderDate() == null || in.getSenderTime() == null) {
			String currDateTime = Utility.currDateTime14();
			if (in.getSenderDate() == null) {
				in.setSenderDate(currDateTime.substring(0, 8));
			}
			in.setSenderTime(currDateTime.substring(8));
		}
		
		String message;
		try {
			message = CommonUtils.marshallContext(in);
		} catch (JiBXException e) {
			throw new RemoteAccessException("Can't marshall object to context.", e);
		}
		
		return writeRequestMessage(message, clazz);
	}
	
	protected ResponseMessage writeRequestMessage(String message, Class<?> clazz) throws DataOperateException, RemoteAccessException {
		// 通过MINA发送报文给Artery平台
		logger.info("使用MINA发送XML请求报文: " + CommonUtils.localLineSeparator() + "[" + message + "]");
		String response = mina.writeRequestMessage(message);
		logger.info("收到Artery/核心响应报文: " + CommonUtils.localLineSeparator() + "[" + response + "]");
		// 解析Artery响应报文
		ResponseMessage resultMessage = null;
		try {
			resultMessage = (ResponseMessage) CommonUtils.unmarshallContext(clazz, response);
		} catch (JiBXException e) {
			logger.error("解析Artery/核心响应报文错误: " + e.getMessage(), e);
			throw new RemoteAccessException("Can't unmarshall context to object 'RemoteResultMessage'.", e);
		}
		// 处理错误信息
		if (resultMessage.hasException()) {
			String description = resultMessage.getDescription();
			// 删除响应描述中的不必要信息
			if (description == null || description.length() == 0) {
				description = "";
			} else {
				int index = description.indexOf("{");
				if (index > 0) {
					description = description.substring(0, index);
				}
				index = description.indexOf("AFPS");
				if (index > 0) {
					description = description.substring(0, index);
				}
			}
			if (resultMessage.isArteryException()) {
				throw new RemoteAccessException(resultMessage.getCode(), description);
			}
			throw new DataOperateException(resultMessage.getCode(), description);
		}
		// 返回响应报文对象
		return resultMessage;
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
