package com.citic.server.shpsb.outer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.basic.IPollingTask;
import com.citic.server.crypto.DESCoder;
import com.citic.server.runtime.HexCoder;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ProcessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.ShpsbKeys;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.shpsb.ShpsbCode;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.SftpUtils;

/**
 * 上海市公安局（公安银行间破案追赃协查系统）外联轮训任务
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 下午5:14:25
 */
@Component("outerPollingTaskShpsb")
public class OuterPollingTaskShpsb extends AbstractPollingTask {
	private final Logger logger = LoggerFactory.getLogger(OuterPollingTaskShpsb.class);
	
	private SftpUtils sftpUtils;
	
	private String xmlFileSavePath; // 请求文件包本地下载目录
	private String xmlFileSuccessPath; // 请求文件包本地存储目录（处理成功）
	private String xmlFileFailedPath; // 请求文件包本地存储目录（处理失败）
	
	@Autowired
	@Qualifier("outerPollingServiceShpsb")
	private OuterPollingServiceShpsb service;
	
	@Override
	public IPollingTask initPollingTask() throws Exception {
		// 初始化固定路径变量
		String fileRootPath = ServerEnvironment.getFileRootPath();
		this.xmlFileSavePath = fileRootPath + File.separator + "download" + File.separator + ServerEnvironment.getStringValue(ShpsbKeys.FILE_DIRECTORY_SHPSB);
		this.xmlFileSuccessPath = xmlFileSavePath + File.separator + "done";
		this.xmlFileFailedPath = xmlFileSavePath + File.separator + "failed";
		
		sftpUtils = new SftpUtils();
		sftpUtils.setHost(ServerEnvironment.getStringValue(ShpsbKeys.SFTP_SERVER_HOST));
		sftpUtils.setUsername(ServerEnvironment.getStringValue(ShpsbKeys.SFTP_SERVER_USERNAME));
		sftpUtils.setPassword(ServerEnvironment.getStringValue(ShpsbKeys.SFTP_SERVER_PASSWORD));
		
		return super.initPollingTask();
	}
	
	// “ grzhxxcx” +8 位日期+2 位银行编码+3 位批次号+“ .xml
	// 1、 先下载 dwzhxxcx2013042511008.xml
	// 2、 下载完成改名为 dwzhxxcx2013042511008.xml.done
	
	@Override
	public void executeAction() {
		final String remoteDownloadPath = ServerEnvironment.getStringValue(ShpsbKeys.REMOTE_DOWNLOAD_PATH); // 
		
		logger.info("正在从远程下载目录同步请求文件清单 - [{}]...", remoteDownloadPath);
		long tm1 = System.currentTimeMillis();
		int s_total = 0;
		int s_repeat = 0;
		int s_invalid = 0;
		int s_fail = 0;
		
		List<String> remoteFileList;
		try {
			remoteFileList = sftpUtils.list(remoteDownloadPath);
		} catch (Exception e) {
			logger.error("从远程下载目录同步请求文件清单失败：{}", e.getMessage(), e);
			return;
		}
		
		if (remoteFileList == null || remoteFileList.size() == 0) {
			long tm2 = System.currentTimeMillis();
			logger.info("本次轮询请求文件完成，共耗时[" + (tm2 - tm1) + "]毫秒，发现[" + s_total + "]个文件。");
			return;
		}
		
		List<MC20_task_msg> taskMsgList = new ArrayList<MC20_task_msg>();
		List<MC20_Task_Fact1> taskFactList = new ArrayList<MC20_Task_Fact1>();
		
		for (String remoteFileName : remoteFileList) {
			s_total++;
			if (remoteFileName == null || remoteFileName.endsWith(".done")) {
				s_invalid++;
				continue;
			}
			
			logger.info("正在从远程下载目录下载请求文件 - [{}]...", remoteFileName);
			try {
				sftpUtils.get(remoteDownloadPath, remoteFileName, xmlFileSavePath, remoteFileName, ".done");
			} catch (Exception e) {
				s_fail++;
				logger.error("从远程下载目录下载请求文件失败 - [{}]： {}", remoteFileName, e.getMessage(), e);
				return;
			}
			
			logger.info("正在校验文件名 - [{}]...", remoteFileName);
			int point = remoteFileName.lastIndexOf(".");
			if (point < 19 || !remoteFileName.endsWith(".xml")) {
				s_invalid++;
				logger.error("请求文件名不符合命名规范 - [{}]", remoteFileName);
				processFile(false, remoteFileName); // 清理文件
				continue;
			}
			
			String realRemoteFileName = remoteFileName.substring(0, point);
			String prefix = realRemoteFileName.substring(0, realRemoteFileName.length() - 13);
			
			if (!ShpsbCode.contains(prefix)) {
				s_fail++;
				logger.error("暂不支持此类交易（代码：{}） - [{}]", prefix, remoteFileName);
				processFile(false, remoteFileName); // 清理文件
				continue;
			}
			
			logger.info("正在解析请求文件 - [{}]", remoteFileName);
			String xmlFilePath;
			try {
				xmlFilePath = processRequestFile(remoteFileName);
			} catch (Exception e) {
				s_fail++;
				logger.error("解析请求文件失败：{}", e.getMessage(), e);
				processFile(false, remoteFileName); // 清理文件
				continue;
			}
			
			String taskKey = ServerEnvironment.TASK_TYPE_SHANGHAI + "_" + realRemoteFileName;
			if (this.isMessageReceived(taskKey)) {
				s_repeat++;
				logger.warn("重复/重名请求文件 - [{}]", remoteFileName);
				continue;
			}
			
			logger.info("正在处理数据库数据 - [{}]", remoteFileName);
			try {
				processDatabase(taskKey, prefix, realRemoteFileName, xmlFilePath, remoteFileName, taskMsgList, taskFactList);
			} catch (ProcessException e) {
				s_fail++;
				logger.error(e.getMessage(), e);
				processFile(false, remoteFileName); // 清理文件
				continue;
			}
			
			// 处理文件
			logger.info("正在清理本地物理文件...");
			processFile(true, remoteFileName);
		}
		
		// 事务处理本次轮询接收到的所有请求数据
		if (taskMsgList.size() == 0 || taskFactList.size() == 0) {
		} else {
			service.transaction(taskMsgList, taskFactList);
		}
		
		long tm2 = System.currentTimeMillis();
		logger.info("本次轮询请求文件完成，共耗时[" + (tm2 - tm1) + "]毫秒，发现[" + s_total + "]个文件，去重[" + s_repeat + "]个，无效[" + s_invalid + "]个，失败[" + s_fail + "]个。");
	}
	
	private String processRequestFile(String xmlFileName) throws Exception {
		// 读取本地请求文件并解密
		final String cryptoKey = ServerEnvironment.getStringValue(ShpsbKeys.CRYPTO_KEY); //
		
		String xmlFilePath = xmlFileSavePath + File.separator + xmlFileName;
		byte[] xmlData = CommonUtils.readBinaryFile(xmlFilePath);
		byte[] mData = DESCoder.decrypt(HexCoder.decode(xmlData), cryptoKey.getBytes(StandardCharsets.UTF_8));
		
		// 写入解密后的请求文件
		String localRelativePath = CommonUtils.createRelativePath(Keys.FILE_PATH_TEMP, ShpsbKeys.FILE_DIRECTORY_SHPSB);
		String localAbsolutePath = ServerEnvironment.getFileRootPath() + localRelativePath;
		CommonUtils.writeBinaryFile(mData, localAbsolutePath, xmlFileName);
		
		return localRelativePath;
	}
	
	private void processDatabase(String taskKey, String code, String bdhm, String xmlFilePath, String xmlFileName, List<MC20_task_msg> taskMsgList, List<MC20_Task_Fact1> taskFactList)
		throws ProcessException {
		MC21_task task = getTaskClassDef(code);
		if (task == null) {
			throw new ProcessException("暂不支持此类交易（代码：" + code + "） - [" + xmlFileName + "]");
		}
		
		String currDateTime = Utility.currDateTime19();
		MC20_task_msg taskMsg = new MC20_task_msg();
		taskMsg.setTaskkey(taskKey);
		taskMsg.setCode(code);
		taskMsg.setBdhm(bdhm);
		taskMsg.setPacketkey("");
		taskMsg.setMsgname(xmlFileName);
		taskMsg.setMsgpath(xmlFilePath + File.separator + xmlFileName);
		taskMsg.setCreatedt(currDateTime);
		taskMsgList.add(taskMsg);
		
		MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
		taskFact.setTaskKey(taskKey);
		taskFact.setSubTaskID(code);
		taskFact.setBdhm(taskMsg.getBdhm());
		taskFact.setTaskID(task.getTaskID());
		taskFact.setTaskType(task.getTaskType());
		taskFact.setTaskName(task.getTaskName());
		taskFact.setTaskCMD(task.getTaskCMD());
		taskFact.setIsDYNA(task.getIsDYNA());
		taskFact.setDatatime(currDateTime);
		taskFact.setFreq("1");
		taskFact.setTaskObj(getOrganKey()); //
		taskFact.setTGroupID("1");
		taskFactList.add(taskFact);
		
		this.cacheReceivedTaskKey(taskKey); // 加入去重缓存
	}
	
	private void processFile(boolean done, String xmlFileName) {
		String localMovePath = done ? xmlFileSuccessPath : xmlFileFailedPath;
		localMovePath = localMovePath + File.separator + Utility.currDate8();
		// 本地文件处理
		try {
			FileUtils.moveFile(xmlFileSavePath + File.separator + xmlFileName, localMovePath);
		} catch (Exception e) {
			logger.error("清理本地物理文件异常，应用程序默认忽略此异常。", e);
		}
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_SHANGHAI;
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(ShpsbKeys.OUTER_POLLING_PERIOD, "30");
	}
}
