package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.service.domain.MP02_rep_org_map;

public class Cache_Mp02_repOrgDetail extends BaseCache {
	
	public Cache_Mp02_repOrgDetail(ApplicationContext _ac, String cachename) {
		super(_ac, cachename);
	}
	
	@Override
	public Object getCacheByName() throws Exception {
		MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
		HashMap<String, MP02_rep_org_map> hash = new HashMap<String, MP02_rep_org_map>();
		ArrayList<MP02_rep_org_map> list = common_Mapper.getMp02_reporg();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MP02_rep_org_map dto = (MP02_rep_org_map) iter.next();
			hash.put(dto.getReptype() + dto.getReport_organkey(), dto);
			hash.put(dto.getReptype() + dto.getHorgankey(), dto); // 银行内部ID
		}
		return hash;
		
	}
	
}
