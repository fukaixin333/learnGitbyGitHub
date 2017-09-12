/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/08/07 23:21:36$
 */
package com.citic.server;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.citic.server.basic.IListenServer;
import com.citic.server.basic.IPollingServer;
import com.citic.server.server.LabServer;
import com.citic.server.server.MainServer;
import com.citic.server.server.NetcscServer;
import com.citic.server.server.RtServer;
import com.citic.server.server.TaskServer;
import com.citic.server.server.TransReportServer;
import com.citic.server.service.TaskService;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/08/07 23:21:36$
 */
@Component
public class ApplicationCaster {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationCaster.class);
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private MainServer mainServer;
	@Autowired
	private TaskServer taskServer;
	@Autowired
	private LabServer labServer;
	@Autowired
	private RtServer rtServer;
	@Autowired
	private NetcscServer netcscserver;
	@Autowired
	private TransReportServer transReportServer;
	
	@Autowired
	@Qualifier("outerPollingServer")
	private IPollingServer outerPollingServer;
	
	@Autowired
	@Qualifier("innerPollingServer")
	private IPollingServer innerPollingServer;
	
	@Autowired
	@Qualifier("outerListenServer")
	private IListenServer outerListenServer;
	
	@Autowired
	@Qualifier("innerListenServer")
	private IListenServer innerListenServer;
	
	private final Timer timer = new Timer("Timer-Application");
	
	public void run(String[] args) {
		if (ApplicationProperties.SERVER_NAME_POLLING_OUTER.equalsIgnoreCase(args[0])) {
			startPollingServer(outerPollingServer, args); // 外联轮训服务
		} else if (ApplicationProperties.SERVER_NAME_POLLING_INNER.equalsIgnoreCase(args[0])) {
			startPollingServer(innerPollingServer, args); // 内联轮训服务
		} else if (ApplicationProperties.SERVER_NAME_LISTENER_OUTER.equalsIgnoreCase(args[0])) {
			startListenServer(outerListenServer, args); // 外联监听服务
		} else if (ApplicationProperties.SERVER_NAME_LISTENER_INNER.equalsIgnoreCase(args[0])) {
			startListenServer(innerListenServer, args); // 内联监听服务
		} else {
			startTaskServer(args); // 任务处理
		}
	}
	
	private void startListenServer(final IListenServer server, String[] args) {
		LOGGER.info("启动系统（监听）服务：Alias=[{}] Args=[{}]", args[0], StringUtils.join(args, ", "));
		try {
			server.initListenServer(args);
			server.call();
		} catch (Exception e) {
			LOGGER.error("Exception: 启动系统（监听）服务异常 - Alias=[{}] Args=[{}]", args[0], StringUtils.join(args, ", "), e);
		}
	}
	
	private void startPollingServer(final IPollingServer server, String[] args) {
		LOGGER.info("启动系统（轮询）服务：Alias=[" + args[0] + "] Args=[" + StringUtils.join(args, ", ") + "]");
		try {
			server.initPollingServer(args);
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					server.call();
				}
			}, server.getDelay(), server.getPeriod());
		} catch (Exception e) {
			LOGGER.error("Exception: 启动系统（轮询）服务异常 - Alias=[{}] Args=[{}]", args[0], StringUtils.join(args, ", "), e);
		}
	}
	
	private void startTaskServer(String[] args) {
		final String serverAlias = args[0]; // 
		final String taskType = args.length > 1 ? args[1] : "";
		final String taskModule = args.length > 2 ? args[2] : "";
		
		LOGGER.info("启动系统（任务）服务：Alias=[{}] TaskType=[{}] TaskModule=[{}]", serverAlias, taskType, taskModule);
		timer.schedule(new TimerTask() { // TODO 这里应该将各服务的TimerTask分开，不要在每次执行时都一堆的判断
			@Override
			public void run() {
				try {
					if (serverAlias.equalsIgnoreCase(applicationProperties.getMainServerName())) {
						mainServer.cal(serverAlias);
						taskServer.cal(serverAlias);
					} else if (serverAlias.equalsIgnoreCase(applicationProperties.getNetServerName())) { // 网络查控服务
						netcscserver.call(serverAlias, taskType, taskModule);
					} else if (serverAlias.equalsIgnoreCase(applicationProperties.getLabServerName())) {
						labServer.cal(serverAlias);
					} else if (serverAlias.equalsIgnoreCase(applicationProperties.getTaskServerName())) {
						taskServer.cal(serverAlias);
					} else if (serverAlias.equalsIgnoreCase(applicationProperties.getRtServerName())) {
						rtServer.cal(serverAlias);
					} else if (serverAlias.equalsIgnoreCase(applicationProperties.getTrepServerName())) { // 电信诈骗上报数据服务
						transReportServer.cal(serverAlias, taskType, taskModule);
					} else {
						throw new IllegalArgumentException("启动运行参数不支持 - 服务别名=[{" + serverAlias + "}]");
					}
				} catch (Exception e) {
					LOGGER.error("Exception: 执行系统（任务）服务异常：Alias=[{}] TaskType=[{}] TaskModule=[{}]", serverAlias, taskType, taskModule, e);
				}
			}
		}, 0, applicationProperties.getServerScanTimespace() * 1000); // 循环时间间隔
	}
}
