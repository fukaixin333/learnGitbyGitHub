package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.service.domain.BB13_sys_para;

public class Cache_ReHangOrganMap extends BaseCache {
	
	public Cache_ReHangOrganMap(ApplicationContext _ac, String cachename) {
		super(_ac, cachename);
	}
	
	@Override
	public Object getCacheByName() throws Exception {
		MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
		HashMap<String, BB13_sys_para> hash = new HashMap<String, BB13_sys_para>();
		ArrayList<BB13_sys_para> list = common_Mapper.getBB13_union();
		Iterator<BB13_sys_para> iter = list.iterator();
		while (iter.hasNext()) {
			BB13_sys_para dto = iter.next();
			String code = dto.getCode();
			hash.put(code, dto);
		}
		return hash;
	}
	
}
