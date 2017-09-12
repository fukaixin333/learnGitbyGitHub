package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.service.domain.Mp02_organ;

public class Cache_DareaDetail extends BaseCache {
	
	public Cache_DareaDetail(ApplicationContext _ac, String cachename) {
		super(_ac, cachename);
	}
	
	@Override
	public Object getCacheByName() throws Exception {
		MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
		HashMap<String, String> hash = new HashMap<String, String>();
		ArrayList<Mp02_organ> list = common_Mapper.getMp02_area();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Mp02_organ dto = (Mp02_organ) iter.next();
			hash.put(dto.getOrgankey(), dto.getOrganname());
		}
		return hash;
		
	}
	
}
