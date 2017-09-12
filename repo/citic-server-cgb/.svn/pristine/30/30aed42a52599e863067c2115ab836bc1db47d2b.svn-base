package com.citic.server.jsga.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.citic.server.runtime.JsgaKeys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.IOUtils;

/**
 * CBRC
 * 
 * @author Liu Xuanfei
 * @date 2016年7月20日 下午1:58:12
 */
@Service("requestMessageService12")
public class RequestMessageService12 implements JsgaKeys {
	
	private Logger logger = LoggerFactory.getLogger(RequestMessageService12.class);
	
	private String remoteAccessURI = ServerEnvironment.getStringValue(JsgaKeys.REMOTE_ACCESS_URL_12);
	
	public String sendQueryResult(String pathXml, String cxlx, boolean isAccount) {
		if (isAccount) {
			if ("01".equals(cxlx)) {
				String URL = remoteAccessURI + FEEDBACK_CERTIFICATE_INFO;
				return accessHttpClient(pathXml, URL);
			} else {
				String URL = remoteAccessURI + FEEDBACK_ACCOUNTNUMBER_INFO;
				return accessHttpClient(pathXml, URL);
			}
		} else {
			String URL = remoteAccessURI + FEEDBACK_ACCOUNT_TRANS;
			return accessHttpClient(pathXml, URL);
		}
	}
	
	public String sendControlResult(String path, String qqcslx, boolean isdthz) {
		String URL = "";
		if (!isdthz) {
			if ("05".equals(qqcslx) || "06".equals(qqcslx) || "07".equals(qqcslx)) {
				URL = remoteAccessURI + FEEDBACK_FREEZE_RESULT;
			} else if ("08".equals(qqcslx) || "09".equals(qqcslx)) {
				URL = remoteAccessURI + FEEDBACK_STOP_PAYMENT_RESULT;
			} else if ("02".equals(qqcslx) || "04".equals(qqcslx) || "03".equals(qqcslx)) {
				URL = remoteAccessURI + FEEDBACK_DYNAMIC_RESULT;
			}
		} else {
			URL = remoteAccessURI + FEEDBACK_DYNAMIC_RESULT_INFO;
		}
		return accessHttpClient(path, URL);
	}
	
	public String accessHttpClient(String path, String url) {
		StringEntity entity = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse closeResponse = null;
		try {
			byte[] feedBack = CommonUtils.readBinaryFile(path);
			httpClient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url);
			entity = new StringEntity(new String(feedBack, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
			httppost.setEntity(entity);
			closeResponse = httpClient.execute(httppost);
			if (closeResponse != null) {
				logger.info("closeResponse" + closeResponse);
				StatusLine statusLine = closeResponse.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("Http 访问江苏接口 失败 statusCode--[{}]", statusCode);
					return "0";
				}
			}
			HttpEntity httpEntity = (HttpEntity) closeResponse.getEntity();
			return new String(IOUtils.toByteArray(httpEntity.getContent()), StandardCharsets.UTF_8);
			
		} catch (Exception e) {
			logger.error("反馈 江苏公安接口URL--[{}],异常--[{}]", url, e.getMessage(),e);
		} finally {
			try {
				EntityUtils.consume(entity);
				if (closeResponse != null) {
					closeResponse.close();
				}
				httpClient.close();
			} catch (Exception e) {
			}
		}
		return "0";
	}
	
}
