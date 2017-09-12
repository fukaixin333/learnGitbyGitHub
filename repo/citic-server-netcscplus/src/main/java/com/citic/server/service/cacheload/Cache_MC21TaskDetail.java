package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MC21_task_factMapper;
import com.citic.server.service.domain.MC21_task;

public class Cache_MC21TaskDetail extends BaseCache {
	
	public Cache_MC21TaskDetail(ApplicationContext _ac, String cachename) {
		super(_ac, cachename);
	}
	
	@Override
	public Object getCacheByName() throws Exception {
		MC21_task_factMapper mc21_task_factMapper = (MC21_task_factMapper) this.getAc().getBean("MC21_task_factMapper");
		HashMap hash = new HashMap();
		ArrayList<MC21_task> list = mc21_task_factMapper.getMC21_taskList();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MC21_task dto = (MC21_task) iter.next();
			hash.put(dto.getTaskID(), dto);
		}
		return hash;
	}
}
