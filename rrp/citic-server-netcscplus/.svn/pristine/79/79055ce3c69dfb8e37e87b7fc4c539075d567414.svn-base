package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MM12_entity_cfgMapper;
import com.citic.server.service.domain.MM12_entity_cfg;

/**
 * @author hubq
 * @version 1.0
 */

public class Cache_eventDetailColumn extends BaseCache {
	
	public Cache_eventDetailColumn(ApplicationContext ac, String cachename) {
		super(ac, cachename);
	}
	
	/**
	 * 实现数据源准备情况扫描
	 * 
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception {
		
		MM12_entity_cfgMapper mm12_entity_cfgMapper = (MM12_entity_cfgMapper) this.getAc().getBean("MM12_entity_cfgMapper");
		
		ArrayList list = mm12_entity_cfgMapper.getMM12_entity_cfgList();
		
		HashMap hash = new HashMap();
		
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MM12_entity_cfg dto = (MM12_entity_cfg) iter.next();
			String key = (String) dto.getEntitykey();
			
			ArrayList valueList = new ArrayList();
			if (hash.containsKey(key)) {
				valueList = (ArrayList) hash.get(key);
			}
			valueList.add(dto);
			
			hash.put(key, valueList);
		}
		
		return hash;
		
	}
	
}