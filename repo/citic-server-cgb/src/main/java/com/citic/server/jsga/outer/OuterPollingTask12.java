package com.citic.server.jsga.outer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.jsga.domain.Br40_cxqq;
import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.jsga.domain.JSGA_BasicInfo;
import com.citic.server.jsga.domain.JSGA_QueryPerson;
import com.citic.server.jsga.domain.JSGA_Response;
import com.citic.server.jsga.domain.response.JSGA_ControlResponse;
import com.citic.server.jsga.domain.response.JSGA_FreezeResponse;
import com.citic.server.jsga.domain.response.JSGA_LiInfosResponse;
import com.citic.server.jsga.domain.response.JSGA_QueryResponse;
import com.citic.server.jsga.domain.response.JSGA_StopPaymentResponse;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.runtime.JsgaKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ProcessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.IOUtils;

@Service("outerPollingTask12")
public class OuterPollingTask12 extends AbstractPollingTask implements JsgaKeys {
	private final Logger logger = LoggerFactory.getLogger(OuterPollingTask12.class);
	
	private String remoteAccessURI = ServerEnvironment.getStringValue(JsgaKeys.REMOTE_ACCESS_URL_12);
	
	@Autowired
	@Qualifier("outerPollingService12")
	private OuterPollingService12 service;
	
	@Autowired
	private PollingTaskMapper taskMapper;
	
	@Override
	public void executeAction() {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("yhdm", ServerEnvironment.getStringValue(JsgaKeys.BANK_CODE_12, "E007H101440101001")));
		try {
			logger.info("开始执行--------[江苏公安|获取司法]-----------");
			UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(param, "UTF-8");
			
			//获取 根据账（卡）号查询请求
			getAccountNumberRequest(encodedFormEntity);
			
			//获取 根据三证查询请求
			getCertificateRequest(encodedFormEntity);
			
			//获取 冻结/续冻/解冻请求
			getFreezeRequest(encodedFormEntity);
			
			//获取  紧急止付请求
			getStopPaymentRequest(encodedFormEntity);
			
			//获取 动态查询
			getDynamicQueryRequest(encodedFormEntity);
			
		} catch (Exception e) {
			logger.error("UrlEncodedFormEntity转换异常--[{}]", e.getMessage(),e);
		}
	}
	
	private void getAccountNumberRequest(UrlEncodedFormEntity encodedFormEntity) {
		boolean isContinue = true;
		int accountNumber_total = 0;
		try {
			while (isContinue && accountNumber_total < 20) {
				isContinue = gainRequest(encodedFormEntity, ACCOUNTNUMBER_BINDING_NAME, QUERY_BY_ACCOUNTNUMBER);
				accountNumber_total++;
			}
		} catch (Exception e) {
			logger.error("Exception：获取查询账（卡）号请求异常", e);
		}
		logger.info("获取查询 账（卡）号请求==== 完成，共{}条", accountNumber_total);
	}
	
	private void getCertificateRequest(UrlEncodedFormEntity encodedFormEntity) {
		boolean isContinue = true;
		int certificate_total = 0;
		try {
			while (isContinue && certificate_total < 20) {
				isContinue = gainRequest(encodedFormEntity, CERTIFICATE_BINDING_NAME, QUERY_BY_CERTIFICATE);
				certificate_total++;
			}
		} catch (Exception e) {
			logger.error("Exception：获取查询 三证请求异常", e);
		}
		logger.info("获取查询 三证请求 ==== 完成，共{}条", certificate_total);
	}
	
	private void getFreezeRequest(UrlEncodedFormEntity encodedFormEntity) {
		boolean isContinue = true;
		int freeze_total = 0;
		try {
			while (isContinue && freeze_total < 20) {
				isContinue = gainRequest(encodedFormEntity, FREEZE_BINDING_NAME, FREEZE_ACCOUNT);
				freeze_total++;
			}
		} catch (Exception e) {
			logger.error("Exception：获取冻结类请求异常", e);
		}
		logger.info("获取冻结类请求请求 ==== 完成，共{}条", freeze_total);
	}
	
	private void getStopPaymentRequest(UrlEncodedFormEntity encodedFormEntity) {
		boolean isContinue = true;
		int stop_payment_total = 0;
		try {
			while (isContinue && stop_payment_total < 20) {
				isContinue = gainRequest(encodedFormEntity, STOP_PAYMENT_BINDING_NAME, STOP_PAYMENT);
				stop_payment_total++;
			}
		} catch (Exception e) {
			logger.error("Exception：获取紧急止付请求异常", e);
		}
		logger.info("获取紧急止付请求 ==== 完成，共{}条", stop_payment_total);
	}
	
	private void getDynamicQueryRequest(UrlEncodedFormEntity encodedFormEntity) {
		boolean isContinue = true;
		int stop_payment_total = 0;
		try {
			while (isContinue && stop_payment_total < 20) {
				isContinue = gainRequest(encodedFormEntity, DYNAMIC_BINDING_NAME, DYNAMIC_QUERY);
				stop_payment_total++;
			}
		} catch (Exception e) {
			logger.error("Exception：获取动态查询请求异常", e);
		}
		logger.info("获取动态请求 ==== 完成，共{}条", stop_payment_total);
	}
	
	public boolean gainRequest(HttpEntity entity, String bingding, String lx) {
		logger.info("请求--------- " + lx + "-------接口");
		String qqdbs = null;
		boolean isContinue = true;
		try {
			// 获取江苏公安反馈的数据
			byte[] content = process(lx, entity);
			isContinue = content != null && content.length > 0;
			if (isContinue) {
				JSGA_Response JSGAResponse = null;
				if (FREEZE_ACCOUNT.equals(lx)) {// 账户冻结/解冻/续冻请求
					JSGAResponse = (JSGA_Response) CommonUtils.unmarshallContext(JSGA_FreezeResponse.class, bingding, new String(content, StandardCharsets.UTF_8));
				} else if (DYNAMIC_QUERY.equals(lx)) {// 动态查询请求
					JSGAResponse = (JSGA_Response) CommonUtils.unmarshallContext(JSGA_ControlResponse.class, bingding, new String(content, StandardCharsets.UTF_8));
				} else if (STOP_PAYMENT.equals(lx)) { // 紧急止付
					JSGAResponse = (JSGA_Response) CommonUtils.unmarshallContext(JSGA_StopPaymentResponse.class, bingding, new String(content, StandardCharsets.UTF_8));
				} else { // 常规查询
					JSGAResponse = (JSGA_Response) CommonUtils.unmarshallContext(JSGA_QueryResponse.class, bingding, new String(content, StandardCharsets.UTF_8));
				}
				
				if (JSGAResponse != null) {
					if (JSGAResponse.getBasicInfo() != null) {
						//获取请求单表识，用qqdbs获取法律文书
						qqdbs = JSGAResponse.getBasicInfo().getQqdbs();
						logger.info("请求qqdbs---[{}]", qqdbs);
						String relativeFilePath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, JsgaKeys.FILE_DIRECTORY_12) + File.separator + qqdbs;
						String absoluteFilePath = ServerEnvironment.getFileRootPath() + File.separator + relativeFilePath;
						List<JSGA_LiInfosResponse> liInfoList = gainServiceFlws(qqdbs, absoluteFilePath);
						if (liInfoList == null || liInfoList.size() == 0) {
							logger.info(" 未成功获取文书信息");
							String contents = createReturnReceipt(qqdbs, CBRCConstants.REC_CODE_30003, "缺少法律文书");
							StringEntity stringEntity = new StringEntity(contents, StandardCharsets.UTF_8);
							YjSjxfhzServices(stringEntity);
							return isContinue;
						}
						
						// 用任务中的信息拼接 文件名称
						String ztlb = JSGAResponse.getBasicInfo().getZtlb(); // 获取主体类别
						
						String xmlFileName = acquireXmlFileName(lx, ztlb); // 
						
						xmlFileName = xmlFileName + ServerEnvironment.getStringValue(JsgaKeys.BANK_CODE_12) + qqdbs;
						String fileName = xmlFileName + ".xml";
						CommonUtils.writeBinaryFile(content, absoluteFilePath, fileName);
						// 设置 Mc20TaskMsg 信息
						List<MC20_task_msg> taskMsgList = setMc20TaskMsg(xmlFileName, qqdbs, relativeFilePath, fileName);
						JSGA_BasicInfo basicInfo = JSGAResponse.getBasicInfo();
						JSGA_QueryPerson queryPerson = JSGAResponse.getQueryPerson();
						
						// 存入数据库
						logger.info("正在将请求数据入库 - [" + fileName + "]...");
						processDatabase(basicInfo, queryPerson, liInfoList, taskMsgList, relativeFilePath + File.separator + fileName);
						
						//调用反馈接口通知江苏公安接收信息
						String contents = createReturnReceipt(qqdbs, CBRCConstants.REC_CODE_OK, "成功接收查控文件");
						StringEntity stringEntity = new StringEntity(contents, StandardCharsets.UTF_8);
						YjSjxfhzServices(stringEntity);
					}
				}
			}
		} catch (Exception e) {
			logger.error("江苏应用程序异常" + e.getMessage(),e);
			String contents = createReturnReceipt(qqdbs, CBRCConstants.REC_CODE_99999, e.getMessage());
			StringEntity stringEntity = new StringEntity(contents, StandardCharsets.UTF_8);
			YjSjxfhzServices(stringEntity);
			return isContinue;
		}
		return isContinue;
	}
	
	public String YjSjxfhzServices(HttpEntity entity) {
		try {
			CloseableHttpResponse response = sentMessage(SERVICE_XFHZ, entity);
			String content = new String(IOUtils.toByteArray(response.getEntity().getContent()));
			logger.info("调用数据下发回执接口 反馈码content----[{}]", content);
			return content;
		} catch (Exception e) {
			logger.error("请求数据下发回执接口调用 异常--[{}]", e.getMessage(),e);
		}
		return null;
		
	}
	
	public byte[] process(String URL, HttpEntity entity) throws Exception {
		CloseableHttpResponse closeResponse = null;
		closeResponse = sentMessage(URL, entity);
		if (closeResponse != null) {
			StatusLine statusLine = closeResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Http 访问江苏接口 失败 statusCode--[{}]", statusCode);
				return null;
			}
		}
		HttpEntity httpEntity = (HttpEntity) closeResponse.getEntity();
		logger.info("接收到江苏公安局请求信息，正在处理...");
		byte[] content = IOUtils.toByteArray(httpEntity.getContent());
		String infomation = new String(content, StandardCharsets.UTF_8);
		logger.info("接收到请求数据---[{}]", infomation);
		if (StringUtils.isBlank(infomation) || "1006".equals(infomation.trim())) {
			logger.info("暂时没有请求数据infomation--[{}]", infomation);
			return null;
		}
		return content;
	}
	
	public CloseableHttpResponse sentMessage(String service, HttpEntity entity) throws Exception {
		CloseableHttpResponse closeResponse = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			String URL = remoteAccessURI + service;
			HttpPost httppost = new HttpPost(URL);
			httppost.setEntity(entity);
			closeResponse = httpclient.execute(httppost);
		} catch (Exception e) {
			logger.error("网络连接异常[{}]", e.getMessage(),e);
		} finally {
			EntityUtils.consume(entity);
		}
		return closeResponse;
	}
	
	public List<MC20_task_msg> setMc20TaskMsg(String xmlFileName, String qqdbs, String filePath, String fileName) {
		List<MC20_task_msg> taskMsgList = new ArrayList<MC20_task_msg>();
		String taskKey = getTaskType() + "_" + xmlFileName;
		MC20_task_msg taskMsg = new MC20_task_msg();
		taskMsg.setTaskkey(taskKey);
		taskMsg.setCode(xmlFileName.substring(0, 4).toUpperCase());
		taskMsg.setBdhm(xmlFileName);
		taskMsg.setPacketkey(filePath + File.separator + fileName);
		taskMsg.setMsgname(xmlFileName);
		taskMsg.setMsgpath(filePath + File.separator + fileName);
		taskMsg.setCreatedt(Utility.currDateTime19());
		logger.info("taskMsg " + taskMsg);
		taskMsgList.add(taskMsg);
		return taskMsgList;
	}
	
	public List<JSGA_LiInfosResponse> gainServiceFlws(String qqdbs, String path) throws Exception {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("qqdbs", qqdbs));
		UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(param, "UTF-8");
		CloseableHttpResponse closeResponse = sentMessage(SERVICE_FLWS, encodedFormEntity);
		if (closeResponse != null) {
			StatusLine statusLine = closeResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Http 访问江苏接口 失败 statusCode--[{}]", statusCode);
				return null;
			}
		}
		HttpEntity httpEntity = (HttpEntity) closeResponse.getEntity();
		logger.info("接收到江苏公安局请求信息，正在获取文书...");
		byte[] content = IOUtils.toByteArray(httpEntity.getContent());
		JSGA_Response CBRCResponse = (JSGA_Response) CommonUtils.unmarshallContext(JSGA_QueryResponse.class, LAWDOCUMENT_BINDING_NAME, new String(content, StandardCharsets.UTF_8));
		List<JSGA_LiInfosResponse> wsInfoList = CBRCResponse.getLiInfos();
		if (wsInfoList != null && wsInfoList.size() > 0) {
			boolean wsok = true;
			for (JSGA_LiInfosResponse wsInfo : wsInfoList) {
				String wjlx = wsInfo.getWjlx() == null ? "" : wsInfo.getWjlx();
				String wjmc = wsInfo.getWjmc();
				String fileName = wjmc + "." + wjlx;
				if (wsInfo.getRealwsnr() == null || wsInfo.getRealwsnr().length == 0) {
					logger.error("文书请求单表识[{}]无内容。", qqdbs);
					wsok = false;
					break;
				}
				logger.info("文件名称  文件路径 [{}],[{}] ", fileName, path);
				CommonUtils.writeBinaryFile(wsInfo.getRealwsnr(), path, fileName);
			}
			if (wsok) {
				return CBRCResponse.getLiInfos();
			} else {
				return null;
			}
		}
		return wsInfoList;
	}
	
	private String acquireXmlFileName(String lx, String ztlb) {
		String xmlFileName = "";
		if (QUERY_BY_ACCOUNTNUMBER.equals(lx)) {
			if ("01".equals(ztlb)) {
				xmlFileName = "SS05";
			} else if ("02".equals(ztlb)) {
				xmlFileName = "SS06";
			}
		} else if (QUERY_BY_CERTIFICATE.equals(lx)) {
			if ("01".equals(ztlb)) {
				xmlFileName = "SS01";
			} else if ("02".equals(ztlb)) {
				xmlFileName = "SS02";
			}
		} else if (FREEZE_ACCOUNT.equals(lx)) {
			if ("01".equals(ztlb)) {
				xmlFileName = "SS17";
			} else if ("02".equals(ztlb)) {
				xmlFileName = "SS18";
			}
		} else if (DYNAMIC_QUERY.equals(lx)) {
			if ("01".equals(ztlb)) {
				xmlFileName = "SS11";
			} else if ("02".equals(ztlb)) {
				xmlFileName = "SS12";
			}
		} else if (STOP_PAYMENT.equals(lx)) {
			if ("01".equals(ztlb)) {
				xmlFileName = "SS21";
			} else if ("02".equals(ztlb)) {
				xmlFileName = "SS22";
			}
		}
		return xmlFileName;
	}
	
	private void processDatabase(JSGA_BasicInfo basicInfo, JSGA_QueryPerson queryPerson, List<JSGA_LiInfosResponse> liInfoList, List<MC20_task_msg> taskMsgList, String extractRelativePath)
		throws ProcessException {
		String qqcslx = basicInfo.getQqcslx(); // 请求措施类型
		MC21_task task = getTaskClassDef(qqcslx);
		
		if (task == null) {
			throw new ProcessException(CBRCConstants.REC_CODE_99999, "暂不支持此类交易[QQCSLX=\"" + qqcslx + "\"]");
		}
		String currDateTime = Utility.currDateTime19();
		String currDate = Utility.currDate10();
		String organKey = getOrganKey();
		
		List<MC20_Task_Fact1> taskFactList = new ArrayList<MC20_Task_Fact1>();
		for (MC20_task_msg taskMsg : taskMsgList) {
			MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
			taskFact.setTaskKey(taskMsg.getTaskkey());
			taskFact.setSubTaskID(qqcslx);
			taskFact.setBdhm(taskMsg.getBdhm());
			taskFact.setTaskID(task.getTaskID());
			taskFact.setTaskType(task.getTaskType());
			taskFact.setTaskName(task.getTaskName());
			taskFact.setTaskCMD(task.getTaskCMD());
			taskFact.setIsDYNA(task.getIsDYNA());
			taskFact.setDatatime(currDateTime);
			taskFact.setFreq("1");
			taskFact.setTaskObj(organKey == null ? "" : organKey); //
			taskFact.setTGroupID("1");
			taskFactList.add(taskFact);
		}
		List<MC20_WS> wsList = null;
		if (liInfoList != null && liInfoList.size() > 0) {
			if (wsList == null) {
				wsList = new ArrayList<MC20_WS>();
			}
			for (JSGA_LiInfosResponse liInfo : liInfoList) {
				String liFileName = liInfo.getWjmc();
				MC20_WS mc20_ws = new MC20_WS();
				mc20_ws.setXh(liFileName);
				mc20_ws.setBdhm(basicInfo.getQqdbs());
				mc20_ws.setWjmc(liFileName);
				mc20_ws.setWjlx(liInfo.getWjlx());
				mc20_ws.setWslb(liInfo.getWslx());
				mc20_ws.setWspath(extractRelativePath);
				// attach表需要的值
				mc20_ws.setDatetime(currDate);
				mc20_ws.setTasktype(getTaskType());
				wsList.add(mc20_ws);
			}
		}
		Br40_cxqq br40_cxqq = null;
		Br41_kzqq br41_kzqq = null;
		if (CBRCConstants.ROUTINE.equals(qqcslx)
			|| CBRCConstants.DYNAMIC.equals(qqcslx)
			|| CBRCConstants.DYNAMIC_CONTINUE.equals(qqcslx)
			|| CBRCConstants.DYNAMIC_RELIEVE.equals(qqcslx)) {
			br40_cxqq = new Br40_cxqq();
			copyProperties(br40_cxqq, basicInfo, queryPerson);
			br40_cxqq.setStatus("0");// 状态
			br40_cxqq.setQrydt(Utility.currDate10());
			br40_cxqq.setTasktype(getTaskType());// 监管类别
		} else {
			br41_kzqq = new Br41_kzqq();
			copyProperties(br41_kzqq, basicInfo, queryPerson);
			br41_kzqq.setStatus("0");// 状态
			br41_kzqq.setQrydt(Utility.currDate10());
			br41_kzqq.setTasktype(getTaskType());// 监管类别
		}
		// 事务管理
		service.transaction(basicInfo.getQqdbs(), getTaskType(), taskFactList, taskMsgList, wsList, br40_cxqq, br41_kzqq);
	}
	
	public String createReturnReceipt(String qqdbs, String code, String description) {
		StringBuilder receipt = new StringBuilder();
		receipt.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		receipt.append("\r\n");
		receipt.append("<RETURNRECEIPTS>");
		receipt.append("<RETURNRECEIPT");
		receipt.append(" QQDBS=\"" + qqdbs + "\"");
		receipt.append(" SQJGDM=\"320000\"");
		receipt.append(" MBJGDM=\"E007H101440101001\"");
		receipt.append(" JSSJ=\"" + Utility.currDateTime14() + "\"");
		receipt.append(" HZDM=\"" + code + "\"");
		receipt.append(" HZSM=\"" + description + "\"");
		receipt.append(" />");
		receipt.append("</RETURNRECEIPTS>");
		return receipt.toString();
	}
	
	private void copyProperties(Br40_cxqq br40_cxqq, JSGA_BasicInfo basicInfo, JSGA_QueryPerson queryPerson) {
		br40_cxqq.setQqdbs(basicInfo.getQqdbs()); // 请求单标识
		br40_cxqq.setQqcslx(basicInfo.getQqcslx()); // 请求措施类型
		br40_cxqq.setSqjgdm(basicInfo.getSqjgdm()); // 申请机构代码
		br40_cxqq.setMbjgdm(basicInfo.getMbjgdm()); // 目标机构代码
		br40_cxqq.setZtlb(basicInfo.getZtlb()); // 查控主体类别
		br40_cxqq.setAjlx(basicInfo.getAjlx()); // 案件类型
		br40_cxqq.setJjcd(basicInfo.getJjcd()); // 紧急程度
		br40_cxqq.setBeiz(basicInfo.getBeiz()); // 备注
		br40_cxqq.setFssj(basicInfo.getFssj()); // 发送时间
		
		br40_cxqq.setQqrxm(queryPerson.getQqrxm()); // 请求人姓名
		br40_cxqq.setQqrzjlx(queryPerson.getQqrzjlx()); // 请求人证件类型
		br40_cxqq.setQqrzjhm(queryPerson.getQqrzjhm()); // 请求人证件号码
		br40_cxqq.setQqrdwmc(queryPerson.getQqrdwmc()); // 请求人单位名称
		br40_cxqq.setQqrsjh(queryPerson.getQqrsjh()); // 请求人手机号
		br40_cxqq.setXcrxm(queryPerson.getXcrxm()); // 协查人姓名
		br40_cxqq.setXcrzjlx(queryPerson.getXcrzjlx()); // 协查人证件类型
		br40_cxqq.setXcrzjhm(queryPerson.getXcrzjhm()); // 协查人证件号码
		br40_cxqq.setQqrbgdh(queryPerson.getQqrbgdh()); // 请求人办公电话
		br40_cxqq.setXcrsjh(queryPerson.getXcrsjh()); // 协查人手机号
		br40_cxqq.setXcrbgdh(queryPerson.getXcrbgdh()); // 协查人办公电话
	}
	
	private void copyProperties(Br41_kzqq br41_kzqq, JSGA_BasicInfo basicInfo, JSGA_QueryPerson queryPerson) {
		br41_kzqq.setQqdbs(basicInfo.getQqdbs()); // 请求单标识
		br41_kzqq.setQqcslx(basicInfo.getQqcslx()); // 请求措施类型
		br41_kzqq.setSqjgdm(basicInfo.getSqjgdm()); // 申请机构代码
		br41_kzqq.setMbjgdm(basicInfo.getMbjgdm()); // 目标机构代码
		br41_kzqq.setZtlb(basicInfo.getZtlb()); // 查控主体类别
		br41_kzqq.setAjlx(basicInfo.getAjlx()); // 案件类型
		br41_kzqq.setJjcd(basicInfo.getJjcd()); // 紧急程度
		br41_kzqq.setBeiz(basicInfo.getBeiz()); // 备注
		br41_kzqq.setFssj(basicInfo.getFssj()); // 发送时间
		
		br41_kzqq.setQqrxm(queryPerson.getQqrxm()); // 请求人姓名
		br41_kzqq.setQqrzjlx(queryPerson.getQqrzjlx()); // 请求人证件类型
		br41_kzqq.setQqrzjhm(queryPerson.getQqrzjhm()); // 请求人证件号码
		br41_kzqq.setQqrdwmc(queryPerson.getQqrdwmc()); // 请求人单位名称
		br41_kzqq.setQqrsjh(queryPerson.getQqrsjh()); // 请求人手机号
		br41_kzqq.setXcrxm(queryPerson.getXcrxm()); // 协查人姓名
		br41_kzqq.setXcrzjlx(queryPerson.getXcrzjlx()); // 协查人证件类型
		br41_kzqq.setXcrzjhm(queryPerson.getXcrzjhm()); // 协查人证件号码
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_JIANGSU;
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(CBRCKeys.OUTER_POLLING_PERIOD, "10");
	}
}