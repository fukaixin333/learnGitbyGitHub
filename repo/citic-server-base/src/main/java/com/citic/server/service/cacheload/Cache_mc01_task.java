package com.citic.server.service.cacheload;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

import com.citic.server.mapper.MC01_taskMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_mc01_task extends BaseCache {

	public Cache_mc01_task(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MC01_taskMapper mc01_taskMapper = (MC01_taskMapper)this.getAc().getBean("MC01_taskMapper");
		
		
		ArrayList list = mc01_taskMapper.getMC01_taskList();
		
		
		return list; 
				
	}
	
}