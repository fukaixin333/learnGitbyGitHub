package com.citic.server.service.task.tk_ds101;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.CacheService;

public class DSFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(DSFactory.class);

	public BaseDS getDSInstance(ApplicationContext _ac,MC00_task_fact _mC00_task_fact)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SecurityException, NoSuchMethodException,
			IllegalArgumentException {

		// 通过文件名的方式获取对应的实例
		String pkg = BaseDS.class.getPackage().getName();

		String dsid = _mC00_task_fact.getSubtaskid();
		
		CacheService cacheService = (CacheService)_ac.getBean("cacheService");
		HashMap dsMap = (HashMap) cacheService.getCache("datasource",
				HashMap.class);

		MC00_datasource ds = (MC00_datasource) dsMap.get(dsid);

		// 标识类型，table，file
		String ftype = ds.getFtype().toLowerCase();
		// 标识文件位置，local,remote
		String flocation = ds.getFlocation().toLowerCase();

		// Eg:***RemoteFile.java
		String classNameStr = flocation.substring(0, 1).toUpperCase()
				+ flocation.substring(1) + ftype.substring(0, 1).toUpperCase()
				+ ftype.substring(1);

		String className = pkg + "." + classNameStr;

		BaseDS baseDS = null;

		Class dsClass;
		try {
			dsClass = Class.forName(className);
			if (dsClass != null) {
				// 初始化构造函数
				Constructor cc = dsClass
						.getConstructor(new Class[] { ApplicationContext.class,MC00_datasource.class,MC00_task_fact.class });

				baseDS = (BaseDS) cc
						.newInstance(new Object[] { _ac,ds,_mC00_task_fact });

			}
		} catch (java.lang.NoSuchMethodException e) {
			e.printStackTrace();
			logger.error("未能找到相应的构造方法！className=" + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("未能找到任务工作实现类！className=" + className);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return baseDS;
	}

	public static void main(String[] args) {

	}

}
