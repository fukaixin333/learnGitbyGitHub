package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_ds_tables;
import com.citic.server.mapper.MC00_ds_tablesMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_ds_tables extends BaseCache {

	public Cache_ds_tables(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MC00_ds_tablesMapper mC00_ds_tablesMapper = (MC00_ds_tablesMapper)this.getAc().getBean("MC00_ds_tablesMapper");
		
		ArrayList list = mC00_ds_tablesMapper.getMC00_ds_tablesList();
		
		/** key = subtaskid value - MC00_ds_tables */
		HashMap resultMap = new HashMap();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			MC00_ds_tables dst = (MC00_ds_tables)iter.next();
			
			String taskid = dst.getTaskid();
			String subtaskid = dst.getSubtaskid();
			String dsid = dst.getDsid();
			
			HashMap subtaskMap = new HashMap();
			
			if(resultMap.containsKey(taskid)){
				
				subtaskMap = (HashMap)resultMap.get(taskid);

			}
			
			ArrayList subList = new ArrayList();
			if(subtaskMap.containsKey(subtaskid)){
				subList = (ArrayList)subtaskMap.get(subtaskid);
			}
			subList.add(dst);
			
			subtaskMap.put(subtaskid, subList);
			
			resultMap.put(taskid, subtaskMap);
			
		}
		
		return resultMap; 
				
	}
	
}