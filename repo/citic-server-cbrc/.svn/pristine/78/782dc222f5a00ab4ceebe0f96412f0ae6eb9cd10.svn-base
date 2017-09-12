package com.citic.server.cbrc.outer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.runtime.ServerEnvironment;

/**
 * 最高人民检察院
 * 
 * @author Liu Xuanfei
 * @date 2016年10月8日 下午11:11:44
 */
@Service("outerPollingTask05")
public class OuterPollingTask05 extends OuterPollingTaskCBRC {
	private final Logger logger = LoggerFactory.getLogger(OuterPollingTask05.class);
	
	@Override
	public void executeAction() {
		logger.info("开始执行[最高人民检察院|涉案账户资金网络查控]轮询任务…");
		super.executeAction();
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_GAOJIAN;
	}
	
	@Override
	protected String fileDirectoryKey() {
		return CBRCKeys.FILE_DIRECTORY_05;
	}
	
	@Override
	protected String getFileSaveDirectory() {
		return ServerEnvironment.getStringValue(CBRCKeys.FILE_DIRECTORY_05, "gaojian");
	}
	
	@Override
	protected String getRemoteDownloadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_DOWNLOAD_PATH_05, "最高人民检查院/Download");
	}
	
	@Override
	protected String getRemoteUploadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_05, "最高人民检查院/Upload");
	}
}
