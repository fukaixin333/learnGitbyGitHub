package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MM12_entityMapper;
import com.citic.server.service.domain.MM12_entity;

/**
 * @author hubq
 * @version 1.0
 */

public class Cache_eventDetailTable extends BaseCache {
	
	public Cache_eventDetailTable(ApplicationContext ac, String cachename) {
		super(ac, cachename);
	}
	
	/**
	 * 实现数据源准备情况扫描
	 * 
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception {
		
		MM12_entityMapper mm12_entityMapper = (MM12_entityMapper) this.getAc().getBean("MM12_entityMapper");
		
		ArrayList list = mm12_entityMapper.getMM12_entityList();
		
		HashMap hash = new HashMap();
		
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MM12_entity dto = (MM12_entity) iter.next();
			String key = (String) dto.getEntitykey();
			
			hash.put(key, dto);
		}
		
		return hash;
		
	}
	
}