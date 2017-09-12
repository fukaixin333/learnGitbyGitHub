package com.citic.server.cbrc.outer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.runtime.ServerEnvironment;

/**
 * 国家安全机关
 * 
 * @author Liu Xuanfei
 * @date 2016年10月8日 下午11:11:36
 */
@Service("outerPollingTask04")
public class OuterPollingTask04 extends OuterPollingTaskCBRC {
	private final Logger logger = LoggerFactory.getLogger(OuterPollingTask04.class);
	
	@Override
	public void executeAction() {
		logger.info("开始执行[国家安全机关|涉案账户资金网络查控]轮询任务…");
		super.executeAction();
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_GUOAN;
	}
	
	@Override
	protected String fileDirectoryKey() {
		return CBRCKeys.FILE_DIRECTORY_04;
	}
	
	@Override
	protected String getFileSaveDirectory() {
		return ServerEnvironment.getStringValue(CBRCKeys.FILE_DIRECTORY_04, "guoan");
	}
	
	@Override
	protected String getRemoteDownloadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_DOWNLOAD_PATH_04, "国家安全部/Download");
	}
	
	@Override
	protected String getRemoteUploadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_04, "国家安全部/Upload");
	}
}
