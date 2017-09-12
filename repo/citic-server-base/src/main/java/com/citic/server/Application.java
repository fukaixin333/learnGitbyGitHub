/**
 * 后台计算主控SERVER
 */
package com.citic.server;

import java.io.File;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.citic.server.server.MainServer;
import com.citic.server.server.TaskServer;
import com.citic.server.service.TaskService;

/**
 * @author hubq
 *
 */
public class Application {
	private static final Logger logger = LoggerFactory
			.getLogger(Application.class);

	private final Timer timer = new Timer();

	/** 通过配置文件读取 单位为（毫秒）默认1分钟 */
	private int scan_timespace = 60000;

	@Autowired
	private ApplicationContext ac;

	@Autowired
	private TaskService taskService;

	@Autowired
	private MainServer mainServer;

	@Autowired
	private TaskServer taskServer;

	private String serverid = "";

	public Application() {

	}

	/**
	 * 执行主程序
	 * 
	 * @param args
	 *            运行参数 args[0]=serverkey
	 */
	public static void main(String[] args) {
		try {

			logger.info("加载系统配置文件，进行系统初始化！");

			if (args.length == 0) {
				throw new Exception("缺少运行参数，请输入运行的SERVERID！");
			}
			
			ApplicationContext ac = new ClassPathXmlApplicationContext(
					"spring/mainserver-config.xml");

			SpringContextHolder springContextHolder = (SpringContextHolder) ac
					.getBean("springContextHolder");

			springContextHolder.setApplicationContext(ac);

			Application application = (Application) ac.getBean("application");
			ApplicationCFG applicationCFG = (ApplicationCFG) ac.getBean("applicationCFG");

			// 检查运行参数
			String serverid = args[0];

			if (serverid.equalsIgnoreCase(applicationCFG.getServer_name_mainserver())) {
				// 主服务
				logger.info("系统服务类型：主服务，SERVERID=" + serverid);
			} else if (serverid.startsWith(applicationCFG.getServer_name_labserver())) {
				// 模型实验室计算服务
				logger.info("系统服务类型：模型实验室计算服务，SERVERID=" + serverid);
			} else if (serverid.startsWith(applicationCFG.getServer_name_taskserver())) {
				// 任务计算负载分担辅助服务
				logger.info("系统服务类型：任务辅助计算服务，SERVERID=" + serverid);
			} else {
				// 计算编码不正确；
				throw new Exception("运行的SERVERID不正确，请检查！");
			}

			application.setServerid(serverid);
			application.setScan_timespace( applicationCFG.getServer_scan_timespace() * 1000 );
			/**
			 * 计算服务被重新启动，需要重置任务执行状态
			 */
			application.taskService.resetMC00_task_status(serverid);
			
			logger.info("进行系统初始化完毕！");

			logger.info("==============================================");
			logger.info("==========                          ==========");
			logger.info("==========   系统批处理守护进程启动      ==========");
			logger.info("==========                          ==========");
			logger.info("==============================================");
			
			// 系统执行
			application.startApplicationServer(application);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 启动Server，每隔一定时间进行一次扫描 看系统是否由任务需要执行
	 * 
	 */
	private void startApplicationServer(Application application) {
		logger.info("系统启动成功,开始任务扫描及计算！");
		
		logger.info("==============================================");

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					ApplicationCFG applicationCFG = (ApplicationCFG) ac.getBean("applicationCFG");
					if (serverid.equalsIgnoreCase(applicationCFG.getServer_name_mainserver())) {
						
						mainServer.cal(serverid);

						taskServer.cal(serverid);

					} else if (getServerid().startsWith(applicationCFG.getServer_name_taskserver())) {

						taskServer.cal(serverid);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 每一分钟循环执行一次
		}, 0, scan_timespace);
	}

	public void setServerid(String serverid){
		this.serverid = serverid;
	}
	
	public String getServerid() {
		return this.serverid;
	}

	public int getScan_timespace() {
		return scan_timespace;
	}

	public void setScan_timespace(int scan_timespace) {
		this.scan_timespace = scan_timespace;
	}
	
	
}
