package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_task;
import com.citic.server.mapper.MC00_taskMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_taskbytrigerid extends BaseCache {

	public Cache_taskbytrigerid(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MC00_taskMapper mC00_taskMapper = (MC00_taskMapper)this.getAc().getBean("MC00_taskMapper");
		
		ArrayList taskList = mC00_taskMapper.getMC00_taskList();
		HashMap resultMap = new HashMap();
		Iterator iter = taskList.iterator();
		while(iter.hasNext()){
			MC00_task mC00_task = (MC00_task)iter.next();
			String trigerid = mC00_task.getTrigerid();
			resultMap.put(trigerid, mC00_task);
		}
		
		return resultMap; 
				
	}
	
}