package com.citic.server.cbrc.outer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.runtime.ServerEnvironment;

/**
 * 公安部&银监会
 * 
 * @author Liu Xuanfei
 * @date 2016年10月8日 下午11:11:23
 */
@Service("outerPollingTask15")
public class OuterPollingTask15 extends OuterPollingTaskCBRC {
	private final Logger logger = LoggerFactory.getLogger(OuterPollingTask15.class);
	
	@Override
	public void executeAction() {
		logger.info("开始执行[内蒙古自治区公安厅|涉案账户资金网络查控]轮询任务…");
		super.executeAction();
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_NMGZZQ;
	}
	
	@Override
	protected String fileDirectoryKey() {
		return CBRCKeys.FILE_DIRECTORY_15;
	}
	
	@Override
	protected String getFileSaveDirectory() {
		return ServerEnvironment.getStringValue(CBRCKeys.FILE_DIRECTORY_15, "gongan");
	}
	
	@Override
	protected String getRemoteDownloadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_DOWNLOAD_PATH_15, "Download");
	}
	
	@Override
	protected String getRemoteUploadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_15, "Upload");
	}
	
	@Override
	protected String getRemoteServerHost() {
		return ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_HOST_15);
	}
	
	@Override
	protected String getUsername() {
		return ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_USERNAME_15);
	}
	
	@Override
	protected String getPassword() {
		return ServerEnvironment.getStringValue(CBRCKeys.FTP_SERVER_PASSWORD_15);
	}
}
