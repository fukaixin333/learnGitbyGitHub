package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_di_tables;
import com.citic.server.mapper.MC00_di_tablesMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_di_tables extends BaseCache {

	public Cache_di_tables(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MC00_di_tablesMapper mC00_di_tablesMapper = (MC00_di_tablesMapper)this.getAc().getBean("MC00_di_tablesMapper");
		
		ArrayList list = mC00_di_tablesMapper.getMC00_di_tablesList();
		
		/** 
		 * taskid - subtaskid(subtaskMap) - MC00_di_tables(ArrayList) 
		 */
		HashMap resultMap = new HashMap();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			MC00_di_tables dit = (MC00_di_tables)iter.next();
			
			String taskid = dit.getTaskid();
			String subtaskid = dit.getSubtaskid();
			
			HashMap subtaskMap = new HashMap();
			
			if(resultMap.containsKey(taskid)){
				
				subtaskMap = (HashMap)resultMap.get(taskid);
				
			}

			ArrayList subList = new ArrayList();
			if(subtaskMap.containsKey(subtaskid)){
				subList = (ArrayList)subtaskMap.get(subtaskid);
			}
			subList.add(dit);
			
			subtaskMap.put(subtaskid, subList);
			
			resultMap.put(taskid, subtaskMap);
		}
		
		return resultMap; 
				
	}
	
}