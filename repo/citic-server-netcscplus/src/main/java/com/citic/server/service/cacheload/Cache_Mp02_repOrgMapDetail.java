package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.service.domain.MP02_rep_org_map;

public class Cache_Mp02_repOrgMapDetail extends BaseCache {
	
	public Cache_Mp02_repOrgMapDetail(ApplicationContext _ac, String cachename) {
		super(_ac, cachename);
	}
	
	@Override
	public Object getCacheByName() throws Exception {
		MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
		HashMap hash = new HashMap();
		ArrayList<MP02_rep_org_map> list = common_Mapper.getMp02_reporgmap_map();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MP02_rep_org_map dto = (MP02_rep_org_map) iter.next();
			String organkey_r = dto.getReport_organkey();
			String organkey = dto.getOrgankey();
			if (hash.containsKey(organkey_r)) {
				HashMap subMap = (HashMap) hash.get(organkey_r);
				subMap.put(organkey, dto);
			} else {
				HashMap subMap = new HashMap();
				subMap.put(organkey, dto);
				hash.put(organkey_r, subMap);
			}
			
		}
		return hash;
		
	}
	
}
