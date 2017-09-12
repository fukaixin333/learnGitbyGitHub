package com.citic.server.gf.outer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.transport.http.HTTPConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.basic.IPollingTask;
import com.citic.server.gf.domain.Result;
import com.citic.server.gf.domain.Result_Jg;
import com.citic.server.gf.domain.request.ControlRequest;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.QueryRequest;
import com.citic.server.gf.domain.request.QueryRequest_Zhxx;
import com.citic.server.gf.domain.response.CertificateResponse;
import com.citic.server.gf.domain.response.CertificateResponse_ZjInfo;
import com.citic.server.gf.domain.response.ControlResponse;
import com.citic.server.gf.domain.response.ControlResponse_Kzqq;
import com.citic.server.gf.domain.response.ControlResponse_Kzzh;
import com.citic.server.gf.domain.response.QueryResponse;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;
import com.citic.server.gf.domain.response.WritResponse;
import com.citic.server.gf.domain.response.WritResponse_WsInfo;
import com.citic.server.gf.webservice.SyyhConvertDataServiceStub;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.TaskLog;
import com.citic.server.runtime.Utility;
import com.citic.server.service.time.SequenceService;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StringUtils;

/**
 * 最高人民法院WebService模式
 * 
 * @author liuxuanfei
 * @date 2017年5月16日 下午2:49:12
 */
@Component("outerPollingTask1A")
public class OuterPollingTask1A extends AbstractPollingTask implements Keys, Constants {
	private final static Logger logger = LoggerFactory.getLogger(OuterPollingTask1A.class);
	
	private String BANK_CODE;
	private String WSDL_LOCATION;
	private String USER_MARKER;
	
	@Autowired
	private PollingTaskMapper pollingMapper;
	
	@Autowired
	@Qualifier("outerPollingService1A")
	private OuterPollingService1A pollingService;
	
	@Autowired
	@Qualifier("gfTransSerialId")
	private SequenceService sequenceService;
	
	private final TaskLog taskLog = new TaskLog();
	
	public static final String ROOTPATH = ServerEnvironment.getFileRootPath();
	
	@Override
	public IPollingTask initPollingTask() throws Exception {
		BANK_CODE = ServerEnvironment.getStringValue(GF_BANK_CODE);
		WSDL_LOCATION = ServerEnvironment.getStringValue(GF_WSDL_LOCATION);
		
		String username = Utility.encodeGBKMIMEBase64(ServerEnvironment.getStringValue(GF_USERMARKER_USERNAME));
		String password = Utility.encodeGBKMIMEBase64(ServerEnvironment.getStringValue(GF_USERMARKER_PASSWORD));
		StringBuilder message = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		message.append("<usermarker>");
		message.append("<condition username=\"" + username + "\" password=\"" + password + "\"></condition>");
		message.append("</usermarker>");
		USER_MARKER = Utility.encodeGBKMIMEBase64(message.toString());
		
		return super.initPollingTask();
	}
	
	@Override
	public void executeAction() {
		logger.info("执行[最高人民法院|金融机构网络查控]轮询服务...");
		
		SyyhConvertDataServiceStub syyhService;
		try {
			syyhService = new SyyhConvertDataServiceStub(WSDL_LOCATION);
			syyhService._getServiceClient().getOptions().setTimeOutInMilliSeconds(3 * 60 * 1000);
			syyhService._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, 3 * 60 * 1000); // Socket timeout
			syyhService._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, 30 * 1000); // Connection timeout
		} catch (Exception e) {
			logger.error("Exception: 执行[最高人民法院|金融机构网络查控]轮询服务异常", e);
			return;
		}
		
		try {
			logger.info("开始获取司法查询请求...");
			taskLog.start();
			invokeGetXzcxList(syyhService); // 
			taskLog.flip();
			logger.info("完成获取司法查询请求。详情: " + taskLog.log());
		} catch (Exception e) {
			logger.error("Exception: 获取司法查询请求异常", e);
		}
		
		try {
			logger.info("开始获取司法控制请求...");
			taskLog.start();
			invokeGetXzkzList(syyhService);
			taskLog.flip();
			logger.info("完成获取司法控制请求。详情: " + taskLog.log());
		} catch (Exception e) {
			logger.error("Exception: 获取司法控制请求异常", e);
		}
	}
	
	// 获取司法查询请求内容
	private void invokeGetXzcxList(SyyhConvertDataServiceStub syyhService) {
		List<QueryResponse_Cxqq> cxqqList = null;
		
		try {
			SyyhConvertDataServiceStub.GetXzcxList param = new SyyhConvertDataServiceStub.GetXzcxList();
			param.setArg0(USER_MARKER);
			SyyhConvertDataServiceStub.GetXzcxListResponse syyhResponse = syyhService.getXzcxList(param);
			String request = Utility.decodeGBKMIMEBase64(syyhResponse.get_return());
			
			QueryResponse response = (QueryResponse) CommonUtils.unmarshallContext(QueryResponse.class, "binding_query_res1A", request);
			if (StringUtils.isNotBlank(response.getErrorMsg())) {
				logger.error("获取司法查询请求响应错误信息: {}", response.getErrorMsg());
				return;
			}
			cxqqList = response.getCxqqList();
		} catch (Exception e) {
			logger.error("Exception: 获取司法查询请求内容异常", e);
			return;
		}
		
		if (cxqqList == null || cxqqList.size() == 0) {
			return;
		}
		
		List<QueryRequest_Zhxx> htxxList = new ArrayList<QueryRequest_Zhxx>(); // 回退信息集合
		String relativeSavePath = CommonUtils.createRelativePath(FILE_PATH_CARD, FILE_DIRECTORY_GF); // 文件存储相对路径
		String absoluteSavePath = ServerEnvironment.getFileRootPath() + relativeSavePath; // 法律文书存储绝对路径
		
		List<String> wsbhList = new ArrayList<String>(); // 已获取文书编号集合
		for (QueryResponse_Cxqq cxqq : cxqqList) {
			String bdhm = cxqq.getBdhm();
			String taskKey = TASK_TYPE_GF + "_QA" + BANK_CODE + bdhm;
			
			// 任务去重
			if (isMessageReceived(taskKey)) {
				logger.warn("忽略重复司法查询请求: BDHM=[{}]", bdhm);
				taskLog.logRepeat();
				continue;
			}
			cxqq.setTaskKey(taskKey);
			
			// 文件存储绝对路径
			String realRelativeSavePath = relativeSavePath + File.separator + bdhm;
			String realAbsoluteSavePath = ServerEnvironment.getFileRootPath() + realRelativeSavePath;
			
			// 记录文书编号
			String wsbh = cxqq.getWsbh();
			if (StringUtils.isBlank(wsbh)) {
				htxxList.add(QueryRequest_Zhxx.getHtInstance(bdhm, "业务审核未通过", "无文书编号，协助执行单位业务审核未通过。"));
				taskLog.logInvaild();
				continue;
			}
			
			try {
				// 获取查询申请涉及的相关文书信息
				List<WritResponse_WsInfo> wsInfoList = null;
				if (!wsbhList.contains(wsbh)) {
					long li1 = System.currentTimeMillis();
					logger.info("获取查询申请涉及的相关文书信息 - WSBH=[{}]...", wsbh);
					wsInfoList = invokeCxwsInfo(syyhService, wsbh);
					if (wsInfoList == null || wsInfoList.size() == 0) {
						htxxList.add(QueryRequest_Zhxx.getHtInstance(bdhm, "业务审核未通过", "无文书，协助执行单位业务审核未通过。"));
						taskLog.logInvaild();
						continue;
					}
					
					boolean wsok = true;
					for (WritResponse_WsInfo wsInfo : wsInfoList) {
						if (wsInfo.getWsnr() == null || wsInfo.getWsnr().length == 0) {
							htxxList.add(QueryRequest_Zhxx.getHtInstance(bdhm, "业务审核未通过", "无文书内容，协助执行单位业务审核未通过。"));
							wsok = false;
							break;
						}
					}
					if (wsok) {
						for (WritResponse_WsInfo wsInfo : wsInfoList) {
							String wjlx = wsInfo.getWjlx() == null ? "" : wsInfo.getWjlx(); // 扩展名
							String wsFileName = "QI" + BANK_CODE + wsbh + wsInfo.getXh() + "." + wjlx;
							CommonUtils.writeBinaryFile(wsInfo.getWsnr(), absoluteSavePath, wsFileName);
							wsInfo.setWsbh(wsbh); // 文书编号（通过文书获取接口得到的文书编号为空）
							wsInfo.setWspath(relativeSavePath + File.separator + wsFileName);
						}
					} else {
						taskLog.logInvaild();
						continue;
					}
					
					// 记录已经获取的文书编号
					wsbhList.add(wsbh);
					
					if (logger.isDebugEnabled()) {
						long li2 = System.currentTimeMillis();
						logger.debug("成功获取查询申请涉及的相关文书信息 - WSBH=[{}]，耗时=[{}]ms", wsbh, (li2 - li1));
					}
				}
				
				// 获取申请涉及的相关证件信息
				List<CertificateResponse_ZjInfo> zjInfoList = invokeZjInfo(syyhService, bdhm);
				if (zjInfoList == null || zjInfoList.size() == 0) {
					htxxList.add(QueryRequest_Zhxx.getHtInstance(bdhm, "业务审核未通过", "无证件，协助执行单位业务未通过。"));
					taskLog.logInvaild();
					continue;
				}
				
				boolean zjok = true;
				for (CertificateResponse_ZjInfo zjInfo : zjInfoList) {
					boolean noGwz = (zjInfo.getGwz() == null || zjInfo.getGwz().length == 0);
					boolean noGzz = (zjInfo.getGzz() == null || zjInfo.getGzz().length == 0);
					if (noGwz && noGzz) {
						zjok = false;
						break;
					}
				}
				if (zjok) {
					for (CertificateResponse_ZjInfo zjInfo : zjInfoList) {
						String xh = zjInfo.getXh() == null ? "" : zjInfo.getXh();
						if (zjInfo.getGwz() != null && zjInfo.getGwz().length > 0) {
							String gwzwjgs = zjInfo.getGwzwjgs() == null ? "" : zjInfo.getGwzwjgs();
							String zjFileName = "QC" + BANK_CODE + bdhm + xh + "." + gwzwjgs; // 扩展名
							CommonUtils.writeBinaryFile(zjInfo.getGwz(), realAbsoluteSavePath, zjFileName);
							zjInfo.setGwzpath(realRelativeSavePath + File.separator + zjFileName);
						}
						
						if (zjInfo.getGzz() != null && zjInfo.getGzz().length > 0) {
							String gzzwjgs = zjInfo.getGzzwjgs() == null ? "" : zjInfo.getGzzwjgs();
							String zjFileName = "QC" + BANK_CODE + bdhm + xh + "." + gzzwjgs; // 扩展名
							CommonUtils.writeBinaryFile(zjInfo.getGzz(), realAbsoluteSavePath, zjFileName);
							zjInfo.setGzzpath(realRelativeSavePath + File.separator + zjFileName);
						}
					}
				} else {
					htxxList.add(QueryRequest_Zhxx.getHtInstance(bdhm, "业务审核未通过", "无证件内容，协助执行单位业务未通过。"));
					taskLog.logInvaild();
					continue;
				}
				
				String xmlFileName = "QA" + BANK_CODE + bdhm + ".xml";
				CommonUtils.marshallUTF8Document(cxqq, "binding_query_cxqq", realAbsoluteSavePath, xmlFileName);
				cxqq.setMsgPath(realRelativeSavePath + File.separator + xmlFileName);
				cxqq.setMsgName(xmlFileName);
				cxqq.setOrgkey(getOrganKey());
				cxqq.setPacketkey("#CXQQ" + BANK_CODE + bdhm);
				
				// 数据库操作
				pollingService.insertCxqqDataTransaction(getTaskClassDef("CXQQ"), cxqq, wsInfoList, zjInfoList);
				
				if (logger.isDebugEnabled()) {
					logger.debug("成功接收司法查询请求及其文书和证件：BDHM=[{}]", bdhm);
				}
				taskLog.logSuccess();
				cacheReceivedTaskKey(taskKey); // 去重处理
			} catch (Exception e) {
				taskLog.logFail();
				logger.error("Exception: 解析司法查询请求法律文书或证件异常 - BDHM=[{}]", bdhm, e);
			}
		}
		
		// 需要回退的司法查询请求
		if (htxxList == null || htxxList.size() == 0) {
		} else {
			QueryRequest rollbackRequest = new QueryRequest();
			rollbackRequest.setZhxxList(htxxList);
			invokeShfeedXzcxInfo(syyhService, rollbackRequest);
		}
	}
	
	// 获取司法控制请求内容
	private void invokeGetXzkzList(SyyhConvertDataServiceStub syyhService) {
		List<ControlResponse_Kzqq> kzqqList = null;
		
		try {
			SyyhConvertDataServiceStub.GetXzkzList param = new SyyhConvertDataServiceStub.GetXzkzList();
			param.setArg0(USER_MARKER);
			SyyhConvertDataServiceStub.GetXzkzListResponse syyhResponse = syyhService.getXzkzList(param);
			String request = Utility.decodeGBKMIMEBase64(syyhResponse.get_return());
			ControlResponse controlResponse = (ControlResponse) CommonUtils.unmarshallContext(ControlResponse.class, "binding_control_res1A", request);
			if (StringUtils.isNotBlank(controlResponse.getErrorMsg())) {
				logger.error("获取司法控制请求内容响应错误信息：{}", controlResponse.getErrorMsg());
				return;
			}
			kzqqList = controlResponse.getKzqqList();
		} catch (Exception e) {
			logger.error("Exception: 获取司法控制请求内容异常", e);
			return;
		}
		
		if (kzqqList.size() == 0 || kzqqList == null) {
			logger.debug("获取司法控制请求内容完成，无请求内容。");
			return;
		}
		
		List<ControlRequest_Kzxx> htxxList = new ArrayList<ControlRequest_Kzxx>(); // 回退信息集合
		String relativeSavePath = CommonUtils.createRelativePath(FILE_PATH_CARD, FILE_DIRECTORY_GF); // 文件存储相对路径
		// String absoluteSavePath = ROOT_PATH + relativeSavePath; // 法律文书存储绝对路径	
		
		for (ControlResponse_Kzqq kzqq : kzqqList) {
			String bdhm = kzqq.getBdhm();
			String taskKey = TASK_TYPE_GF + "_QA" + BANK_CODE + bdhm;
			
			// 任务去重
			if (isMessageReceived(taskKey)) {
				logger.warn("忽略重复司法控制请求（BDHM = [{}]）", bdhm);
				taskLog.logRepeat();
				continue;
			}
			
			kzqq.setTaskKey(taskKey);
			String realRelativeSavePath = relativeSavePath + File.separator + bdhm;
			String realAbsoluteSavePath = ServerEnvironment.getFileRootPath() + realRelativeSavePath;
			
			try {
				//获取法律文书
				List<WritResponse_WsInfo> wsInfoList = invokeKzwsInfo(syyhService, bdhm);
				boolean wsok = true;
				if (wsInfoList == null || wsInfoList.size() == 0) {
					wsok = false;
				} else {
					for (WritResponse_WsInfo wsInfo : wsInfoList) {
						if (wsInfo.getWsnr().length == 0 || wsInfo.getWsnr() == null) {
							wsok = false;
							break;
						}
					}
				}
				
				if (wsok) {
					for (WritResponse_WsInfo wsInfo : wsInfoList) {
						String wjlx = wsInfo.getWjlx() == null ? "" : wsInfo.getWjlx();
						String wsFileName = "QI" + BANK_CODE + bdhm + wsInfo.getXh() + "." + wjlx;
						CommonUtils.writeBinaryFile(wsInfo.getWsnr(), realAbsoluteSavePath, wsFileName);
						wsInfo.setBdhm(bdhm);
						wsInfo.setWspath(realRelativeSavePath + File.separator + wsFileName);
					}
				} else {
					if (kzqq.getKzzhlist() == null || kzqq.getKzzhlist().size() == 0) {
						htxxList.add(new ControlRequest_Kzxx(bdhm, "1", "2", "无法律文书，协助执行单位业务审核未通过"));
					} else {
						for (ControlResponse_Kzzh kzzh : kzqq.getKzzhlist()) {
							htxxList.add(new ControlRequest_Kzxx(bdhm, kzzh.getCcxh(), "2", "无法律文书，协助执行单位业务审核未通过"));
						}
					}
					taskLog.logInvaild();
					continue;
				}
				
				// 获取申请涉及的相关证件信息
				boolean zjok = true;
				List<CertificateResponse_ZjInfo> zjInfoList = invokeZjInfo(syyhService, bdhm);
				if (zjInfoList == null || zjInfoList.size() == 0) {
					zjok = false;
				} else {
					for (CertificateResponse_ZjInfo zjInfo : zjInfoList) {
						boolean noGwz = (zjInfo.getGwz() == null || zjInfo.getGwz().length == 0);
						boolean noGzz = (zjInfo.getGzz() == null || zjInfo.getGzz().length == 0);
						if (noGwz && noGzz) {
							zjok = false;
							break;
						}
					}
				}
				
				if (zjok) {
					for (CertificateResponse_ZjInfo zjInfo : zjInfoList) {
						String xh = zjInfo.getXh() == null ? "" : zjInfo.getXh();
						if (zjInfo.getGwz() != null && zjInfo.getGwz().length > 0) {
							String gwzwjgs = zjInfo.getGwzwjgs() == null ? "" : zjInfo.getGwzwjgs();
							String zjFileName = "QC" + BANK_CODE + bdhm + xh + "." + gwzwjgs; // 扩展名
							CommonUtils.writeBinaryFile(zjInfo.getGwz(), realAbsoluteSavePath, zjFileName);
							zjInfo.setGwzpath(realRelativeSavePath + File.separator + zjFileName);
						}
						
						if (zjInfo.getGzz() != null && zjInfo.getGzz().length > 0) {
							String gzzwjgs = zjInfo.getGzzwjgs() == null ? "" : zjInfo.getGzzwjgs();
							String zjFileName = "QC" + BANK_CODE + bdhm + xh + "." + gzzwjgs; // 扩展名
							CommonUtils.writeBinaryFile(zjInfo.getGzz(), realAbsoluteSavePath, zjFileName);
							zjInfo.setGzzpath(realRelativeSavePath + File.separator + zjFileName);
						}
					}
				} else {
					if (kzqq.getKzzhlist() == null || kzqq.getKzzhlist().size() == 0) {
						htxxList.add(new ControlRequest_Kzxx(bdhm, "1", "2", "无证件信息，协助执行单位业务审核未通过"));
					} else {
						for (ControlResponse_Kzzh kzzh : kzqq.getKzzhlist()) {
							htxxList.add(new ControlRequest_Kzxx(bdhm, kzzh.getCcxh(), "2", "无证件信息，协助执行单位业务审核未通过"));
						}
					}
					taskLog.logInvaild();
					continue;
				}
				
				String xmlFileName = "QA" + BANK_CODE + bdhm + ".xml";
				CommonUtils.marshallUTF8Document(kzqq, "binding_control_kzqq", realAbsoluteSavePath, xmlFileName);
				kzqq.setMsgPath(realRelativeSavePath + File.separator + xmlFileName);
				kzqq.setMsgName(xmlFileName);
				kzqq.setOrgkey(getOrganKey());
				kzqq.setPacketkey("#KZQQ" + BANK_CODE + bdhm);
				
				// 数据库操作
				pollingService.insertKzqqDataTransaction(getTaskClassDef("KZQQ"), kzqq, wsInfoList, zjInfoList);
				
				// 去重处理
				taskLog.logSuccess();
				cacheReceivedTaskKey(taskKey);
			} catch (Exception e) {
				taskLog.logFail();
				logger.error("解析司法控制请求异常（BDHM = [{}]）：{}", bdhm, e.getMessage(), e);
			}
		}
		
		// 需要回退的司法控制请求
		if (htxxList == null || htxxList.size() == 0) {
		} else {
			ControlRequest rollbackRequest = new ControlRequest();
			rollbackRequest.setKzxxList(htxxList);
			List<Result_Jg> resultJG = invokeShfeedXzkzInfo(syyhService, rollbackRequest);
			if (resultJG == null || resultJG.size() == 0) {
			} else {
				StringBuilder rs = new StringBuilder();
				for (Result_Jg jg : resultJG) {
					rs.append(jg.toString() + lineSeparator);
				}
				logger.info("向请求单位反馈需要回退的请求内容响应结果明细：{{}{}}", lineSeparator, rs.toString());
			}
		}
	}
	
	// 获取查询申请涉及的相关文书信息
	private List<WritResponse_WsInfo> invokeCxwsInfo(SyyhConvertDataServiceStub syyhService, String wsbh) {
		StringBuilder message = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		message.append("<wsbhinfo>");
		message.append("<condition WSBH=\"" + Utility.encodeGBKMIMEBase64(wsbh) + "\"></condition>");
		message.append("</wsbhinfo>");
		
		try {
			SyyhConvertDataServiceStub.CxwsInfo param = new SyyhConvertDataServiceStub.CxwsInfo();
			param.setArg0(USER_MARKER);
			param.setArg1(Utility.encodeGBKMIMEBase64(message.toString()));
			SyyhConvertDataServiceStub.CxwsInfoResponse syyhReponse = syyhService.cxwsInfo(param);
			String request = Utility.decodeGBKMIMEBase64(syyhReponse.get_return());
			
			WritResponse response = (WritResponse) CommonUtils.unmarshallContext(WritResponse.class, "binding_writ_res1A", request);
			if (StringUtils.isNotBlank(response.getErrorMsg())) {
				logger.error("获取查询申请涉及的相关文书信息失败 - WSBH=[{}]: {}", wsbh, response.getErrorMsg());
			} else {
				return response.getWsInfoList();
			}
		} catch (Exception e) {
			logger.error("Exception: 获取查询申请涉及的相关文书信息异常 - WSBH=[{}]", wsbh, e);
		}
		
		return null;
	}
	
	// 获取控制申请涉及的相关文书信息
	private List<WritResponse_WsInfo> invokeKzwsInfo(SyyhConvertDataServiceStub syyhService, String bdhm) {
		logger.info("获取控制申请涉及的相关文书信息 - BDHM=[{}]", bdhm);
		
		StringBuilder message = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		message.append("<bdhminfo>");
		message.append("<condition BDHM=\"" + Utility.encodeGBKMIMEBase64(bdhm) + "\"></condition>");
		message.append("</bdhminfo>");
		
		try {
			SyyhConvertDataServiceStub.KzwsInfo param = new SyyhConvertDataServiceStub.KzwsInfo();
			param.setArg0(USER_MARKER);
			param.setArg1(Utility.encodeGBKMIMEBase64(message.toString()));
			SyyhConvertDataServiceStub.KzwsInfoResponse syyhResponse = syyhService.kzwsInfo(param);
			String request = Utility.decodeGBKMIMEBase64(syyhResponse.get_return());
			
			WritResponse writresponse = (WritResponse) CommonUtils.unmarshallContext(WritResponse.class, "binding_writ_res1A", request);
			if (StringUtils.isNotBlank(writresponse.getErrorMsg())) {
				logger.error("获取控制申请涉及的相关文书信息失败：[{}][{}]", bdhm, writresponse.getErrorMsg());
			}
			return writresponse.getWsInfoList();
		} catch (Exception e) {
			logger.error("获取控制申请涉及的相关文书信息异常：[{}]", bdhm, e);
		}
		return null;
	}
	
	// 获取各申请涉及的相关证件信息
	private List<CertificateResponse_ZjInfo> invokeZjInfo(SyyhConvertDataServiceStub syyhService, String bdhm) {
		logger.info("获取司法请求涉及的相关证件信息 - BDHM=[{}]", bdhm);
		
		StringBuilder message = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		message.append("<bdhminfo>");
		message.append("<condition BDHM=\"" + Utility.encodeGBKMIMEBase64(bdhm) + "\"></condition>");
		message.append("</bdhminfo>");
		
		try {
			SyyhConvertDataServiceStub.ZjInfo param = new SyyhConvertDataServiceStub.ZjInfo();
			param.setArg0(USER_MARKER);
			param.setArg1(Utility.encodeGBKMIMEBase64(message.toString()));
			SyyhConvertDataServiceStub.ZjInfoResponse syyhResponse = syyhService.zjInfo(param);
			String request = Utility.decodeGBKMIMEBase64(syyhResponse.get_return());
			
			CertificateResponse response = (CertificateResponse) CommonUtils.unmarshallContext(CertificateResponse.class, "binding_certificate_res1A", request);
			if (StringUtils.isNotBlank(response.getErrorMsg())) {
				logger.error("获取申请涉及的相关证件信息失败 - BDHM=[{}]: {}", bdhm, response.getErrorMsg());
			} else {
				return response.getZjInfoList();
			}
		} catch (Exception e) {
			logger.error("Exception: 获取申请涉及的相关证件信息异常 - BDHM=[{}]", bdhm, e);
		}
		
		return null;
	}
	
	private void invokeShfeedXzcxInfo(SyyhConvertDataServiceStub syyhService, QueryRequest rollbackRequest) {
		try {
			logger.info("开始回退司法查询请求...");
			String xmlHtxx = CommonUtils.marshallContext(rollbackRequest, "binding_query_req1A");
			logger.info("回退司法查询请求报文：[{}{}]", lineSeparator, xmlHtxx);
			
			SyyhConvertDataServiceStub.ShfeedXzcxInfo param = new SyyhConvertDataServiceStub.ShfeedXzcxInfo();
			param.setArg0(USER_MARKER);
			param.setArg1(Utility.encodeGBKMIMEBase64(xmlHtxx));
			SyyhConvertDataServiceStub.ShfeedXzcxInfoResponse syyhResponse = syyhService.shfeedXzcxInfo(param);
			String request = Utility.decodeGBKMIMEBase64(syyhResponse.get_return());
			
			logger.info("回退司法查询请求响应结果: [{}{}]", lineSeparator, request);
			Result result = (Result) CommonUtils.unmarshallContext(Result.class, "binding_result1A", request);
			if (StringUtils.isNotBlank(result.getRealError())) {
				logger.error("回退司法查询请求响应错误信息: [{}]", result.getRealError());
			} else {
				List<Result_Jg> resultJG = result.getRealJgList();
				if (resultJG == null || resultJG.size() == 0) {
					logger.warn("回退司法查询请求无响应结果");
				} else {
					if (logger.isDebugEnabled()) {
						for (Result_Jg jg : resultJG) {
							logger.debug("回退司法查询请求响应结果明细：[{}]", jg.toString());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception: 回退司法查询请求异常", e);
		}
	}
	
	private List<Result_Jg> invokeShfeedXzkzInfo(SyyhConvertDataServiceStub syyhService, ControlRequest rollbackRequest) {
		try {
			logger.info("回退司法控制请求内容...");
			String xmlHtxx = CommonUtils.marshallContext(rollbackRequest, "binding_control_req1A");
			if (logger.isDebugEnabled()) {
				logger.debug("回退请求报文：[{}{}]", lineSeparator, xmlHtxx);
			}
			
			SyyhConvertDataServiceStub.ShfeedXzcxInfo param = new SyyhConvertDataServiceStub.ShfeedXzcxInfo();
			param.setArg0(USER_MARKER);
			param.setArg1(Utility.encodeGBKMIMEBase64(xmlHtxx));
			SyyhConvertDataServiceStub.ShfeedXzcxInfoResponse syyhResponse = syyhService.shfeedXzcxInfo(param);
			String request = Utility.decodeGBKMIMEBase64(syyhResponse.get_return());
			if (logger.isDebugEnabled()) {
				logger.debug("回退请求响应结果: [{}{}]", lineSeparator, request);
			}
			Result result = (Result) CommonUtils.unmarshallContext(Result.class, "binding_result1A", request);
			if (StringUtils.isNotBlank(result.getRealError())) {
				logger.error("回退请求失败: [{}]", result.getRealError());
			}
			return result.getRealJgList();
		} catch (Exception e) {
			logger.error("Exception: 回退请求异常", e);
		}
		
		return null;
	}
	
	@Override
	protected List<String> getReceivedTaskKeyForCaching() {
		return pollingMapper.queryReceivedTaskKeyForCaching01();
	}
	
	@Override
	protected String getTaskType() {
		return TASK_TYPE_GF;
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(Keys.OUTER_POLLING_TASK_GF_PERIOD, "30");
	}
}
