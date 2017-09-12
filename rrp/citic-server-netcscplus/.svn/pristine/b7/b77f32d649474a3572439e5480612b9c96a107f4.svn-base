package com.citic.server.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citic.server.SpringContextHolder;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.conf.ConfigConstants;
import com.citic.server.runtime.conf.SocketListenCommand;

/**
 * Basic监听服务
 * 
 * @author Liu Xuanfei
 * @date 2016年3月24日 上午10:28:59
 */
public abstract class AbstractListenServer implements IListenServer, ConfigConstants {
	private static final Logger logger = LoggerFactory.getLogger(AbstractListenServer.class);
	
	private String serverId;
	private List<IListenTask> runTaskList = new ArrayList<IListenTask>(); // 启动的任务清单
	
	public abstract int listenServerType();
	
	@Override
	public void initListenServer(String... args) throws Exception {
		this.serverId = args[0];
		String[] cids = ArrayUtils.subarray(args, 1, args.length);
		
		// TODO 
		// 这里要调整：HTTP也应该支持部分启动（一个或多个），每个任务类型监听单独的端口。并且兼容“http”的一端口全启动。
		// 判断是否在CID在SocketListenCommand 或 HttpListenCommand，选择不同的启动方式。
		
		for (String cid : cids) {
			if (cid.equals(Constants.TASK_PROTOCOL_HTTP)) {
				logger.info("//////// 启动[HTTP通讯协议]监听服务 ////////");
				IListenTask httpProtocolListenTask = SpringContextHolder.getBean("httpProtocolListenTask");
				httpProtocolListenTask.register(null, listenServerType());
				runTaskList.add(httpProtocolListenTask);
			} else {
				SocketListenCommand command = ServerEnvironment.getSocketListenCommand(cid);
				if (command == null) {
					logger.error("未找到[SERVERID = {}, CID = {}]监听服务，请检查[/conf/service-config.xml]配置信息。", serverId, cid);
					continue;
				}
				
				String bean;
				if (listenServerType() == SERVER_TYPE_OUTER) {
					bean = command.getOuterClass();
				} else {
					bean = command.getInnerClass();
				}
				
				if (bean == null || bean.length() == 0) {
					logger.error("未配置[SERVERID = {}, CID = {}]监听服务类ID，请检查[/conf/service-config.xml]配置信息。", serverId, cid);
					continue;
				}
				
				logger.info("//////// 启动[{}]监听服务 ////////", command.getName());
				IListenTask task = SpringContextHolder.getBean(bean);
				task.register(command, listenServerType()); // register
				runTaskList.add(task);
			}
		}
	}
	
	@Override
	public void call() {
		for (IListenTask listen : runTaskList) {
			try {
				listen.startup();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
