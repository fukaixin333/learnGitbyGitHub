package com.citic.server.dx.outer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cfca.safeguard.api.bank.ClientEnvironment;
import cfca.safeguard.api.bank.Constants;
import cfca.safeguard.api.bank.SGBusiness;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100101;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100103;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100105;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100201;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100203;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100205;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100301;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100303;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100305;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100307;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100309;
import cfca.safeguard.api.bank.bean.tx.downstream.Tx100402;
import cfca.safeguard.api.bank.util.Base64;
import cfca.safeguard.api.bank.util.ResultUtil;
import cfca.safeguard.api.bank.util.StringUtil;
import cfca.safeguard.tx.Attachment;
import cfca.safeguard.tx.TxBase;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.basic.IPollingTask;
import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.ResponseMessage;
import com.citic.server.dx.domain.response.CaseResponse100402;
import com.citic.server.dx.domain.response.FreezeResponse100201;
import com.citic.server.dx.domain.response.FreezeResponse100203;
import com.citic.server.dx.domain.response.FreezeResponse100205;
import com.citic.server.dx.domain.response.QueryResponse100301;
import com.citic.server.dx.domain.response.QueryResponse100303;
import com.citic.server.dx.domain.response.QueryResponse100305;
import com.citic.server.dx.domain.response.QueryResponse100307;
import com.citic.server.dx.domain.response.QueryResponse100309;
import com.citic.server.dx.domain.response.StoppayResponse100101;
import com.citic.server.dx.domain.response.StoppayResponse100103;
import com.citic.server.dx.domain.response.StoppayResponse100105;
import com.citic.server.dx.outer.service.OuterPollingService2;
import com.citic.server.dx.service.RequestMessageService;
import com.citic.server.runtime.JiBXFormatException;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;

/**
 * 轮询任务 - 公安部|人民银行|电信诈骗
 * 
 * @author Liu Xuanfei
 * @date 2016年3月24日 上午10:33:48
 */
@Service("outerPollingTask2")
public class OuterPollingTask2 extends AbstractPollingTask {
	private static final Logger logger = LoggerFactory.getLogger(OuterPollingTask2.class);
	
	@Autowired
	@Qualifier("outerPollingService2")
	private OuterPollingService2 service;
	
	@Autowired
	@Qualifier("requestMessageService")
	private RequestMessageService requestMessageService;
	
	private final String tgOrganization = "000000000001";
	
	private OuterPollingTask2() {
	}
	
	@Override
	public IPollingTask initPollingTask() throws Exception {
		super.initPollingTask();
		String resourceBundle = ServerEnvironment.getStringValue(Keys.SAFEGUARD_RESOURCE_BUNDLE_BASENAME);
		
		logger.info("正在加载 CFCA 连接配置文件[{}.properties]...", resourceBundle);
		ClientEnvironment.initTxClientEnvironment(resourceBundle);
		return this;
	}
	
	@Override
	public void executeAction() {
		logger.info("执行[公安部|中国人民银行|电信诈骗]（主机构）轮询任务…");
		String responseXML = null;
		SGBusiness sgBusiness = new SGBusiness();
		try {
			responseXML = sgBusiness.getMessage();
		} catch (Exception e) {
			logger.info("本次轮询任务失败（主机构），请检查CFCA API配置文件[config.properties]");
			e.printStackTrace();
			return;
		}
		realExecute(responseXML, false); // 处理主机构的
		
		boolean hasTgOrgan = ServerEnvironment.getBooleanValue(Keys.OUTER_POLLING_TASK_DX_HASTGORGAN);
		if (hasTgOrgan) {
			logger.info("执行[公安部|中国人民银行|电信诈骗]轮询任务（托管机构）…");
			try {
				responseXML = sgBusiness.getMessage(tgOrganization);
			} catch (Exception e) {
				logger.info("本次轮询任务失败（托管机构），请检查CFCA API配置文件[config.properties]");
				e.printStackTrace();
				return;
			}
			realExecute(responseXML, true);
		}
	}
	
	private void realExecute(String responseXML, boolean isTgOrgan) {
		long li1 = System.currentTimeMillis();
		List<String> messageList;
		String code = StringUtil.getNodeText(responseXML, Constants.CODE);
		if (Constants.SUCCESS_CODE_VALUE.equals(code)) {
			messageList = StringUtil.getNodeTextList(responseXML, Constants.MESSAGE); //
		} else {
			logger.error("Get message eror! Error code '" + code + "'. Error description: " + StringUtil.getNodeText(responseXML, Constants.DESCRIPTION));
			return;
		}
		
		if (messageList == null || messageList.size() == 0) {
			logger.info("No message has been received need to deal with");
			return;
		}
		
		int s_repeat = 0;
		int s_invalid = 0;
		int s_fail = 0;
		for (String message : messageList) {
			TxBase tx;
			try {
				String xml = Base64.decode(message, Constants.DEFAULT_CHARSET); // Base64解码
				tx = ResultUtil.convertTxFromMessageXML(xml);
			} catch (UnsupportedEncodingException e1) {
				s_fail++;
				logger.error("Decode fialed. " + message);
				continue;
			}
			
			String messageFrom = tx.getMessageFrom();
			String txCode = tx.getTxCode();
			
			// 获取任务配置信息
			if (!isHasTaskClass(txCode)) {
				logger.warn("No task defined for TxCode [" + txCode + "] in MC21_TASK.");
				s_invalid++;
				continue;
			}
			
			List<Attachment> attachments = null;
			String applicationID = "";
			String bankID = "";
			String feedbackCode = null;
			ResponseMessage responseMessage = null;
			
			if (TxConstants.TXCODE_STOPPAYMENT.equals(txCode)) { // 止付
				attachments = ((Tx100101) tx).getAttachments();
				applicationID = ((Tx100101) tx).getApplicationID();
				bankID = ((Tx100101) tx).getBankID();
				responseMessage = new StoppayResponse100101();
				feedbackCode = TxConstants.TXCODE_STOPPAYMENT_FEEDBACK;
			} else if (TxConstants.TXCODE_STOPPAYMENT_RELIEVE.equals(txCode)) { // 止付解除
				attachments = ((Tx100103) tx).getAttachments();
				applicationID = ((Tx100103) tx).getApplicationID();
				bankID = ((Tx100103) tx).getBankID();
				responseMessage = new StoppayResponse100103();
				feedbackCode = TxConstants.TXCODE_STOPPAYMENT_RELIEVE_FEEDBACK;
			} else if (TxConstants.TXCODE_STOPPAYMENT_POSTPONE.equals(txCode)) { // 止付延期
				attachments = ((Tx100105) tx).getAttachments();
				applicationID = ((Tx100105) tx).getApplicationID();
				bankID = ((Tx100105) tx).getBankID();
				responseMessage = new StoppayResponse100105();
				feedbackCode = TxConstants.TXCODE_STOPPAYMENT_POSTPONE_FEEDBACK;
			} else if (TxConstants.TXCODE_FREEZE.equals(txCode)) { // 冻结
				attachments = ((Tx100201) tx).getAttachments();
				applicationID = ((Tx100201) tx).getApplicationID();
				bankID = ((Tx100201) tx).getBankID();
				responseMessage = new FreezeResponse100201();
				feedbackCode = TxConstants.TXCODE_FREEZE_FEEDBACK;
			} else if (TxConstants.TXCODE_FREEZE_RELIEVE.equals(txCode)) { // 冻结解除 
				attachments = ((Tx100203) tx).getAttachments();
				applicationID = ((Tx100203) tx).getApplicationID();
				bankID = ((Tx100203) tx).getBankID();
				responseMessage = new FreezeResponse100203();
				feedbackCode = TxConstants.TXCODE_FREEZE_RELIEVE_FEEDBACK;
			} else if (TxConstants.TXCODE_FREEZE_POSTPONE.equals(txCode)) { // 冻结延期
				attachments = ((Tx100205) tx).getAttachments();
				applicationID = ((Tx100205) tx).getApplicationID();
				bankID = ((Tx100205) tx).getBankID();
				responseMessage = new FreezeResponse100205();
				feedbackCode = TxConstants.TXCODE_FREEZE_POSTPONE_FEEDBACK;
			} else if (TxConstants.TXCODE_ACCOUNT_TRANSACTION_DETAIL.equals(txCode)) { // 账户交易明细查询
				attachments = ((Tx100301) tx).getAttachments();
				applicationID = ((Tx100301) tx).getApplicationID();
				bankID = ((Tx100301) tx).getBankID();
				responseMessage = new QueryResponse100301();
				feedbackCode = TxConstants.TXCODE_ACCOUNT_TRANSACTION_DETAIL_FEEDBACK;
			} else if (TxConstants.TXCODE_ACCOUNT_CARD_SUBJECT.equals(txCode)) { // 账户持卡主体查询
				attachments = ((Tx100303) tx).getAttachments();
				applicationID = ((Tx100303) tx).getApplicationID();
				bankID = ((Tx100303) tx).getBankID();
				responseMessage = new QueryResponse100303();
				feedbackCode = TxConstants.TXCODE_ACCOUNT_CARD_SUBJECT_FEEDBACK;
			} else if (TxConstants.TXCODE_ACCOUNT_DYNAMIC.equals(txCode)) { // 账户动态查询
				attachments = ((Tx100305) tx).getAttachments();
				applicationID = ((Tx100305) tx).getApplicationID();
				bankID = ((Tx100305) tx).getBankID();
				responseMessage = new QueryResponse100305();
				feedbackCode = TxConstants.TXCODE_ACCOUNT_DYNAMIC_FEEDBACK;
			} else if (TxConstants.TXCODE_ACCOUNT_DYNAMIC_RELIEVE.equals(txCode)) { // 账户动态查询解除
				attachments = ((Tx100307) tx).getAttachments();
				applicationID = ((Tx100307) tx).getApplicationID();
				bankID = ((Tx100307) tx).getBankID();
				responseMessage = new QueryResponse100307();
				feedbackCode = TxConstants.TXCODE_ACCOUNT_DYNAMIC_RELIEVE_FEEDBACK;
			} else if (TxConstants.TXCODE_CUSTOMER_WHOLE_ACCOUNT.equals(txCode)) { // 客户全账户查询
				attachments = ((Tx100309) tx).getAttachments();
				applicationID = ((Tx100309) tx).getApplicationID();
				bankID = ((Tx100309) tx).getBankID();
				responseMessage = new QueryResponse100309();
				feedbackCode = TxConstants.TXCODE_CUSTOMER_WHOLE_ACCOUNT_FEEDBACK;
			} else if (TxConstants.TXCODE_REPORT_CASE_FEEDBACK.equals(txCode)) { // 案件举报反馈
				applicationID = ((Tx100402) tx).getApplicationID();
				responseMessage = new CaseResponse100402();
			} else {
				logger.info("Illegal TxCode: '" + txCode + "'");
				continue;
			}
			
			if ((messageFrom == null || messageFrom.equals("")) || (applicationID == null || applicationID.equals(""))) {
				logger.error("The \"MessageFrom\" or \"ApplicationID\" must not be null or blank.");
				continue;
			}
			
			String taskKey = ServerEnvironment.TASK_TYPE_DX + "_" + messageFrom + applicationID;
			// 排除已经接收过尚未反馈的任务
			if (isMessageReceived(taskKey)) {
				logger.info("The task [" + messageFrom + "|" + applicationID + "] has been received.");
				s_repeat++;
				continue;
			}
			
			try {
				// 保存附件
				String[] array = saveAttachments(taskKey, attachments);
				
				// 处理请求信息
				logger.info("Parse response message, From = [" + messageFrom + "], TxCode = [" + txCode + "], ApplicationID = [" + applicationID + "], BankID = [" + bankID + "]");
				BeanUtils.copyProperties(responseMessage, tx);
				if (array != null && array.length == 2) {
					responseMessage.setAttachpaths(array[0]);
					responseMessage.setAttachnames(array[1]);
				}
				service.parseResponseMessage(taskKey, responseMessage, getTaskClassDef(txCode), bankID);
			} catch (JiBXException e) {
				if (e.getRootCause() instanceof JiBXFormatException) {
					s_invalid++;
					JiBXFormatException ex = (JiBXFormatException) e.getRootCause();
					if (feedbackCode == null) {
						ex.printStackTrace();
					} else {
						sendExceptionMessage(bankID, messageFrom, feedbackCode, applicationID, ex.getCode(), ex.getMessage());
					}
				} else {
					logger.error(e.getMessage());
					s_fail++;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				s_fail++;
				sendExceptionMessage(bankID, messageFrom, feedbackCode, applicationID, "0599", "报文格式错误");
			}
			
			// 更新缓存
			cacheReceivedTaskKey(taskKey);
		}
		
		long li2 = System.currentTimeMillis();
		StringBuilder info = new StringBuilder();
		info.append("本次轮询任务完成（" + (isTgOrgan ? "托管机构" : "主机构") + "），共耗时[" + (li2 - li1) + "]ms；");
		info.append("收到[" + messageList.size() + "]个请求，去重[" + s_repeat + "]个，无效[" + s_invalid + "]个，失败[" + s_fail + "]个。");
		logger.info(info.toString());
	}
	
	private String[] saveAttachments(String taskKey, List<Attachment> attachments) {
		if (attachments == null) {
			return null;
		}
		
		StringBuilder attachpaths = null;
		StringBuilder attachnames = null;
		String rootpath = ServerEnvironment.getFileRootPath();
		String cardpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, Keys.FILE_DIRECTORY_DX);
		attachpaths = new StringBuilder();
		attachnames = new StringBuilder();
		int seq = 1;
		for (Attachment attach : attachments) {
			String content = attach.getContent();
			if (StringUtils.isBlank(content)) {
				continue;
			}
			String filename = attach.getFilename();
			if (StringUtils.isNotBlank(filename)) {
				// 文件类型
				String type = filename.substring(0, 2);
				if ("01".equals(type)) { // 警官身份证件
				} else if ("02".equals(type)) { // 冻结法律文书
				} else if ("03".equals(type)) { // 紧急止付申请表
				} else if ("04".equals(type)) { // 身份证
				} else {
				}
				// 文件格式
				String suffix = filename.substring(2, 3);
				if ("0".equals(suffix)) { // JPG
					suffix = ".jpg";
				} else if ("1".equals(suffix)) { // PGF
					suffix = ".pdf";
				} else {
					int p = filename.lastIndexOf(".");
					if (p > 0) {
						suffix = filename.substring(p);
					} else {
						suffix = ".attach";
					}
				}
				if (!StringUtils.endsWithIgnoreCase(filename, suffix)) {
					filename += suffix;
				}
			} else {
				filename = taskKey + ".attach";
			}
			// 创建物理文件
			try {
				String attachname = "N" + seq + "_" + filename;
				CommonUtils.writeBinaryFile(Utility.decodeMIMEBase64(content), rootpath + cardpath, attachname);
				if (seq == 1) {
					attachpaths.append(cardpath + File.separator + attachname);
					attachnames.append(filename);
				} else {
					attachpaths.append(";" + cardpath + File.separator + attachname);
					attachnames.append(";" + filename);
				}
				seq++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new String[] {attachpaths.toString(), attachnames.toString()};
	}
	
	private void sendExceptionMessage(String bankID, String messageFrom, String feedbackCode, String applicationID, String result, String feedbackRemark) {
		try {
			String prefix; // 引用郭嘉的一句台词：“哦，就这样吧……”。
			if (ServerEnvironment.getBooleanValue(Keys.OUTER_POLLING_TASK_DX_HASTGORGAN)) {
				prefix = bankID + "_" + tgOrganization;
			} else {
				prefix = bankID + "_000000000000";
			}
			logger.info("无效报文，直接反馈 - [" + messageFrom + "][" + applicationID + "]");
			Response response = requestMessageService.sendExceptionMessage(feedbackCode, messageFrom, applicationID, prefix, result, feedbackRemark);
			logger.info("反馈响应 - [" + response.getCode() + "][" + response.getDescription() + "]");
		} catch (RemoteAccessException rex) {
			rex.printStackTrace();
		}
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_DX;
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(Keys.OUTER_POLLING_TASK_DX_PERIOD, "10");
	}
}
