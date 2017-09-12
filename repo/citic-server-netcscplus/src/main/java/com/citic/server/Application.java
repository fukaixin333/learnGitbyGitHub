/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/08/07 23:20:50$
 */
package com.citic.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.citic.server.runtime.ServerEnvironment;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/08/07 23:20:50$
 */
public class Application {
	
	/** 同步监控器 */
	private static final Object shutdownMonitor = new Object();
	private static Thread shutdownHook = null;
	
	private static ClassPathXmlApplicationContext applicationContext;
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0) { // 检查运行参数
			throw new IllegalArgumentException("请输入启动运行参数。格式：服务别名 [任务类型 [任务模块1[,任务模块2[,...]] [特殊条件]]]");
		}
		
		// Loading application configuration properties...
		ServerEnvironment.initConfigProperties(); // 加载/conf/service.xml（必须在Spring注入之前加载）
		
		// Loading Spring application context...
		applicationContext = new ClassPathXmlApplicationContext();
		applicationContext.getEnvironment().setDefaultProfiles(ServerEnvironment.getEnvProfiles().name());
		applicationContext.setConfigLocation("classpath:spring/root-application-context.xml");
		applicationContext.refresh();
		
		Application.registerShutdownHook();
		Application.run(args);
	}
	
	private static void run(String[] args) {
		ApplicationCaster applicationCaster = applicationContext.getBean(ApplicationCaster.class);
		applicationCaster.run(args);
	}
	
	private static void registerShutdownHook() {
		// Add a shutdown hook for the above context...
		applicationContext.registerShutdownHook();
		
		// Add a shutdown hook for the current application...
		if (Application.shutdownHook == null) {
			Application.shutdownHook = new Thread("VM-shutdown-hook") {
				@Override
				public void run() {
					close();
				}
			};
		}
		Runtime.getRuntime().addShutdownHook(Application.shutdownHook);
	}
	
	private static void close() {
		synchronized (Application.shutdownMonitor) {
			// Close Spring application context, destroying all beans in its bean factory.
			applicationContext.close();
			
			// Call the hook prior to the app shutting down...
			if (Application.shutdownHook != null) {
				try {
					Runtime.getRuntime().removeShutdownHook(Application.shutdownHook);
				} catch (Exception e) {
					// Ignore -VM is already shutting down
				}
			}
		}
	}
}
