package com.citic.server.runtime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.citic.server.SpringContextHolder;
import com.citic.server.crypto.DESCoder;
import com.citic.server.runtime.conf.Configuration;
import com.citic.server.runtime.conf.HttpListenCommand;
import com.citic.server.runtime.conf.HttpListenDef;
import com.citic.server.runtime.conf.Option;
import com.citic.server.runtime.conf.Property;
import com.citic.server.runtime.conf.SocketListenCommand;
import com.citic.server.runtime.conf.SocketListenDef;
import com.citic.server.runtime.conf.TaskDef;
import com.citic.server.service.CacheService;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StringUtils;

public final class ServerEnvironment implements Keys, Constants {
	private static final Logger logger = LoggerFactory.getLogger(ServerEnvironment.class);
	
	private static final Map<String, Property> propertiesMap = new HashMap<String, Property>();
	private static final Map<String, TaskDef> taskDefMap = new HashMap<String, TaskDef>();
	private static final Map<String, HttpListenCommand> httpListenCommandMap = new HashMap<String, HttpListenCommand>();
	private static final Map<String, SocketListenCommand> socketListenCommandMap = new HashMap<String, SocketListenCommand>();
	
	private static final List<String> httpListenUris = new ArrayList<String>();
	
	private static final ServerEnvironment instance = new ServerEnvironment();
	private int httpListenPort;
	private Map<String, Object> sysParamMap = null;
	
	private static String systemUsage;
	private static EnvProfiles environmentProfiles;
	
	private ServerEnvironment() {
	}
	
	public static ServerEnvironment getInstance() {
		return instance;
	}
	
	public static void console() {
		System.out.println("----------------------------------------------------------------------");
		for (Map.Entry<String, Property> prop : propertiesMap.entrySet()) {
			System.out.println(prop.getKey() + " = " + prop.getValue().toString());
		}
		System.out.println("----------------------------------------------------------------------");
		for (Map.Entry<String, TaskDef> taskdef : taskDefMap.entrySet()) {
			System.out.println(taskdef.getKey() + " = " + taskdef.getValue().toString());
		}
		System.out.println("----------------------------------------------------------------------");
	}
	
	public static void initConfigProperties() throws JiBXException, IOException {
		logger.info("正在加载系统初始化配置文件[/conf/service-config.xml]...");
		
		// 读取配置文件
		Resource resource = new ClassPathResource("/conf/service-config.xml");
		Configuration config = (Configuration) CommonUtils.unmarshallInputStream(Configuration.class, resource.getInputStream(), "UTF-8");
		
		// 全局属性
		systemUsage = System.getProperty(SYSTEM_USAGE, config.getUsage());
		environmentProfiles = EnvProfiles.enumOf(systemUsage, EnvProfiles.DEV, true);
		
		// 独立属性配置
		initConfigProperties0(config.getProperties(), null, null);
		
		// 轮询任务配置
		if (config.getTaskdefs() == null || config.getTaskdefs().size() == 0) {
		} else {
			for (TaskDef taskdef : config.getTaskdefs()) {
				initConfigProperties0(taskdef.getProperties(), taskdef.getAlias(), taskdef.getName());
				
				taskdef.clear();
				taskDefMap.put(taskdef.getId(), taskdef);
			}
		}
		
		// HTPP协议监听任服务配置
		HttpListenDef httpListenDef = config.getHttpListenDef();
		if (httpListenDef == null) {
		} else {
			ServerEnvironment.getInstance().setHttpListenPort(httpListenDef.getPort());
			if (httpListenDef.getCommands() == null || httpListenDef.getCommands().size() == 0) {
			} else {
				for (HttpListenCommand command : httpListenDef.getCommands()) {
					initConfigProperties0(command.getProperties(), command.getAlias(), command.getName());
					
					command.clear();
					httpListenCommandMap.put(command.getId(), command);
					httpListenUris.add(command.getUri());
				}
			}
		}
		
		// Socket协议监听服务配置
		SocketListenDef socketListenDef = config.getSocketListenDef();
		if (socketListenDef == null) {
		} else {
			for (SocketListenCommand command : socketListenDef.getCommands()) {
				initConfigProperties0(command.getProperties(), command.getAlias(), command.getName());
				
				command.clear();
				socketListenCommandMap.put(command.getId(), command);
			}
		}
	}
	
	private static void initConfigProperties0(List<Property> properties, String alias, String name) {
		if (properties == null || properties.size() == 0) {
			return;
		}
		
		boolean preAlias = StringUtils.isNotBlank(alias);
		boolean preName = StringUtils.isNotBlank(name);
		for (Property prop : properties) {
			boolean isEncrypt = prop.isEncrypt();
			if (prop.hasOptions()) {
				for (Option option : prop.getOptions()) {
					if (systemUsage != null && systemUsage.equalsIgnoreCase(option.getUsage())) {
						isEncrypt = option.isEncrypt();
						prop.setValue(option.getValue());
						break;
					}
				}
			}
			// 补全前缀
			if (preAlias) {
				prop.setName(alias + "." + prop.getName());
			}
			if (preName) {
				prop.setDescr(name + "-" + prop.getDescr());
			}
			// 解密
			if (isEncrypt) {
				try {
					prop.setValue(new String(DESCoder.decryptHex(prop.getValue(), DESCoder.definiteKey()), StandardCharsets.UTF_8));
				} catch (Exception e) {
					logger.warn("配置项[{}, {}]解密失败，请确认加密方式及数据格式。", prop.getName(), prop.getDescr(), e);
				}
			}
			
			prop.clear();
			propertiesMap.put(prop.getName(), prop);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getSysParam(String name) {
		if (this.sysParamMap == null) {
			if (SpringContextHolder.isInjected()) {
				CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
				this.sysParamMap = (HashMap<String, Object>) cacheService.getCache("sysParaDetail", HashMap.class);
				
				if (this.sysParamMap != null) {
					this.sysParamMap.put(FILE_PATH_ROOT, sysParamMap.get("2"));
					this.sysParamMap.put(FILE_PATH_ROOT_Q, sysParamMap.get("1"));
					this.sysParamMap.put(FILE_PATH_ATTACH, sysParamMap.get("attachpath"));
					this.sysParamMap.put(FILE_PATH_TEMP, sysParamMap.get("temppath"));
					this.sysParamMap.put(FILE_PATH_CARD, sysParamMap.get("cardpath"));
				}
			}
		}
		
		if (this.sysParamMap != null && this.sysParamMap.containsKey(name)) {
			return sysParamMap.get(name);
		}
		return null;
	}
	
	public static EnvProfiles getEnvProfiles() {
		return environmentProfiles;
	}
	
	public static String getFileRootPath_Q() {
		return getStringValue(FILE_PATH_ROOT_Q);
	}
	
	public static String getFileRootPath() {
		return getStringValue(FILE_PATH_ROOT);
	}
	
	public static Property getProperty(String name) {
		return propertiesMap.get(name);
	}
	
	public static int getIntValue(String name) {
		return getIntValue(name, -1);
	}
	
	public static int getIntValue(String name, int nval) {
		Object val = getValue(name);
		if (val == null) {
			return nval;
		}
		
		if (val instanceof Integer) {
			return ((Integer) val).intValue();
		}
		
		if (val instanceof Property) {
			return Integer.parseInt(((Property) val).getValue());
		}
		
		return Integer.parseInt(val.toString());
	}
	
	public static String getStringValue(String name) {
		return getStringValue(name, null);
	}
	
	public static String getStringValue(String name, String nval) {
		Object val = getValue(name);
		if (val == null) {
			return nval;
		}
		
		if (val instanceof Property) {
			return ((Property) val).getValue();
		}
		
		return val.toString();
	}
	
	public static boolean getBooleanValue(String name) {
		return getBooleanValue(name, false);
	}
	
	public static boolean getBooleanValue(String name, boolean nval) {
		Object val = getValue(name);
		if (val == null) {
			return nval;
		}
		
		if (val instanceof Boolean) {
			return ((Boolean) val).booleanValue();
		}
		
		String strVal = null;
		if (val instanceof Property) {
			strVal = ((Property) val).getValue();
		} else {
			strVal = val.toString();
		}
		
		strVal = strVal.toUpperCase(Locale.CHINESE);
		if (strVal.equals("Y") || strVal.equals("TRUE") || strVal.equals("1")) {
			return true;
		}
		return false;
	}
	
	private static Object getValue(String name) {
		Object val = ServerEnvironment.getInstance().getSysParam(name);
		if (val == null) {
			val = propertiesMap.get(name);
		}
		return val;
	}
	
	public static HttpListenCommand getHttpListenCommand(String id) {
		HttpListenCommand command = httpListenCommandMap.get(id);
		if (command == null) {
			return null;
		}
		return command.clone();
	}
	
	public static List<HttpListenCommand> getAllHttpListenCommand() {
		List<HttpListenCommand> commands = new ArrayList<HttpListenCommand>();
		for (Map.Entry<String, HttpListenCommand> command : httpListenCommandMap.entrySet()) {
			if (command != null) {
				commands.add(command.getValue().clone());
			}
		}
		return commands;
	}
	
	public static TaskDef getTaskDef(String id) {
		return taskDefMap.get(id);
	}
	
	public static SocketListenCommand getSocketListenCommand(String id) {
		SocketListenCommand command = socketListenCommandMap.get(id);
		if (command == null) {
			return null;
		}
		return command.clone();
	}
	
	public static int getHttpListenServerPort() {
		return ServerEnvironment.getInstance().getHttpListenPort();
	}
	
	private void setHttpListenPort(int port) {
		this.httpListenPort = port;
	}
	
	public int getHttpListenPort() {
		return httpListenPort;
	}
}
