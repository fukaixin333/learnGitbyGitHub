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
@Service("outerPollingTask03")
public class OuterPollingTask03 extends OuterPollingTaskCBRC {
	private final Logger logger = LoggerFactory.getLogger(OuterPollingTask03.class);
	
	@Override
	public void executeAction() {
		logger.info("开始执行[公安部&银监会|涉案账户资金网络查控]轮询任务…");
		super.executeAction();
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_GONGAN;
	}
	
	@Override
	protected String fileDirectoryKey() {
		return CBRCKeys.FILE_DIRECTORY_03;
	}
	
	@Override
	protected String getFileSaveDirectory() {
		return ServerEnvironment.getStringValue(CBRCKeys.FILE_DIRECTORY_03, "gongan");
	}
	
	@Override
	protected String getRemoteDownloadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_DOWNLOAD_PATH_03, "公安部经侦/Download");
	}
	
	@Override
	protected String getRemoteUploadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_03, "公安部经侦/Upload");
	}
}
