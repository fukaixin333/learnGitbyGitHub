package com.citic.server.cbrc.outer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.runtime.ServerEnvironment;

/**
 * 云南省国家安全厅
 * 
 * @author liuxuanfei
 * @date 2017年4月18日 下午3:16:25
 */
@Service("outerPollingTask19")
public class OuterPollingTask19 extends OuterPollingTaskCBRC {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void executeAction() {
		logger.info("开始执行[云南省国家安全机关|涉案账户资金网络查控]轮询任务…");
		super.executeAction();
	}
	
	@Override
	protected String fileDirectoryKey() {
		return CBRCKeys.FILE_DIRECTORY_19;
	}
	
	@Override
	protected String getFileSaveDirectory() {
		return ServerEnvironment.getStringValue(CBRCKeys.FILE_DIRECTORY_19, "guoan2");
	}
	
	@Override
	protected String getRemoteDownloadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_DOWNLOAD_PATH_19, "云南省国家安全厅/Download");
	}
	
	@Override
	protected String getRemoteUploadPath() {
		return ServerEnvironment.getStringValue(CBRCKeys.REMOTE_UPLOAD_PATH_19, "云南省国家安全厅/Upload");
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN; // 
	}
	
}
