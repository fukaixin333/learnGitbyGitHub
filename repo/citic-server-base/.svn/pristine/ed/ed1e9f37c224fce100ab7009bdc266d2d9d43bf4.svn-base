package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.BB13_code_filter;
import com.citic.server.domain.MC00_ds_tables;
import com.citic.server.mapper.BB13_code_filterMapper;
import com.citic.server.mapper.MC00_ds_tablesMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_code_filter extends BaseCache {

	public Cache_code_filter(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		BB13_code_filterMapper bb13_code_filterMapper = (BB13_code_filterMapper)this.getAc().getBean("BB13_code_filterMapper");
		
		ArrayList list = bb13_code_filterMapper.getBB13_code_filterList();
		
		/** key = subtaskid value - MC00_ds_tables */
		HashMap resultMap = new HashMap();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			BB13_code_filter filter = (BB13_code_filter)iter.next();
			
			String code = filter.getCode();
			
			if(!resultMap.containsKey(code)){
				
				resultMap.put(code, filter.getValue());

			}
			
		}
		
		return resultMap; 
				
	}
	
}