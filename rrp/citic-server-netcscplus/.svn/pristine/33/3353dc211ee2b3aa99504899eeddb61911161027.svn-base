package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.service.domain.BB13_sys_para;

public class Cache_sysParaDetail extends BaseCache {
	
	public Cache_sysParaDetail(ApplicationContext _ac, String cachename) {
		super(_ac, cachename);
	}
	
	@Override
	public Object getCacheByName() throws Exception {
		MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
		HashMap<String, String> hash = new HashMap<String, String>();
		ArrayList<BB13_sys_para> list = common_Mapper.getsysPara();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			BB13_sys_para dto = (BB13_sys_para) iter.next();
			hash.put(dto.getCode(), dto.getVals());
		}
		return hash;
		
	}
	
}
