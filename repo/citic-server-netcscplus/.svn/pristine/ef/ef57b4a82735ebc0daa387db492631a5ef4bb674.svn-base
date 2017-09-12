package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.service.domain.Mp02_organ;

public class Cache_BB13_etl_code_mapDetail extends BaseCache {
	
	public Cache_BB13_etl_code_mapDetail(ApplicationContext _ac, String cachename) {
		super(_ac, cachename);
	}
	
	@Override
	public Object getCacheByName() throws Exception {
		MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
		HashMap hash = new HashMap();
		ArrayList<Mp02_organ> list = common_Mapper.getBB13_etl_code_map();
		HashMap subhasp = new HashMap();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Mp02_organ dto = (Mp02_organ) iter.next();
			if (hash.containsKey(dto.getUporgankey())) {
				subhasp = (HashMap) hash.get(dto.getUporgankey());
			} else {
				subhasp = new HashMap();
			}
			subhasp.put(dto.getOrganname(), dto.getOrgankey());
			hash.put(dto.getUporgankey(), subhasp);
		}
		return hash;
	}
}
