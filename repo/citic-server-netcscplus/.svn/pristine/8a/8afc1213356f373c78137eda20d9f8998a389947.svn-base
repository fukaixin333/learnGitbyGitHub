package com.citic.server.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citic.server.SpringContextHolder;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.conf.TaskDef;

/**
 * Basic轮询服务
 * 
 * @author Liu Xuanfei
 * @date 2016年4月7日 下午2:40:15
 */
public abstract class AbstractPollingServer implements IPollingServer {
	private static final Logger logger = LoggerFactory.getLogger(AbstractPollingServer.class);
	
	protected static final int SERVER_TYPE_OUTER = 0;
	protected static final int SERVER_TYPE_INNER = 1;
	
	protected String serverId;
	private List<IPollingTask> runTaskList = new ArrayList<IPollingTask>();
	
	public abstract int listenServerType();
	
	@Override
	public void initPollingServer(String... args) throws Exception {
		this.serverId = args[0];
		String[] cids = ArrayUtils.subarray(args, 1, args.length);
		
		for (String cid : cids) {
			TaskDef taskdef = ServerEnvironment.getTaskDef(cid);
			if (taskdef == null) {
				logger.error("未找到[SERVERID = {}, CID = {}]轮询服务，请检查[/conf/service-config.xml]配置信息。", serverId, cid);
				continue;
			}
			
			String bean;
			if (listenServerType() == SERVER_TYPE_OUTER) {
				bean = taskdef.getOuterClass();
			} else {
				bean = taskdef.getInnerClass();
			}
			
			if (bean == null || bean.length() == 0) {
				logger.error("未配置[SERVERID = {}, CID = {}]轮询服务类ID，请检查[/conf/service-config.xml]配置信息。", serverId, cid);
				continue;
			}
			
			logger.info("//////// 启动[{}]轮询服务 ////////", taskdef.getName());
			IPollingTask task = SpringContextHolder.getBean(bean);
			runTaskList.add(task.initPollingTask());
		}
	}
	
	@Override
	public void call() {
		for (IPollingTask task : runTaskList) {
			try {
				task.execute();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	@Override
	public long getDelay() {
		return 3000;
	}
	
	/**
	 * 轮询频率间隔时间（精确到毫秒）。默认1分钟。
	 */
	@Override
	public long getPeriod() {
		return 60 * 1000;
	}
}
