package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.net.mapper.MM14_modelMapper;
import com.citic.server.service.domain.MM14_link_event;
import com.citic.server.service.domain.MM14_model;

public class Cache_moduleEventDetail extends BaseCache {
	
	public Cache_moduleEventDetail(ApplicationContext _ac, String cachename) {
		super(_ac, cachename);
	}
	
	@Override
	public Object getCacheByName() throws Exception {
		
		MM14_modelMapper mm14_modelMapper = (MM14_modelMapper) this.getAc().getBean("MM14_modelMapper");
		HashMap hash = new HashMap();
		HashMap linkhash = new HashMap();
		HashMap eventhash = new HashMap();
		ArrayList linkval = new ArrayList();
		// 获取所有模型信息
		ArrayList list = mm14_modelMapper.getMM14_modelList();
		// // 模型下的环节信息
		// ArrayList linklist = mm14_modelMapper.getMM14_linkList("");
		// Iterator linkiter = linklist.iterator();
		// while (linkiter.hasNext()) {
		// MM14_link dto = (MM14_link) linkiter.next();
		// if (linkhash.containsKey(dto.getModelkey())) {
		// linkval = (ArrayList) linkhash.get(dto.getModelkey());
		// } else {
		// linkval = new ArrayList();
		// }
		// linkval.add(dto);
		// linkhash.put(dto.getModelkey(), linkval);
		// }
		// 模型下的事件信息
		ArrayList link_eventlist = mm14_modelMapper.getMM14_link_eventList("");
		Iterator eventiter = link_eventlist.iterator();
		while (eventiter.hasNext()) {
			MM14_link_event dto = (MM14_link_event) eventiter.next();
			if (eventhash.containsKey(dto.getModelkey())) {
				linkval = (ArrayList) eventhash.get(dto.getModelkey());
			} else {
				linkval = new ArrayList();
			}
			linkval.add(dto);
			eventhash.put(dto.getModelkey(), linkval);
		}
		
		// 模型信息
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MM14_model dto = (MM14_model) iter.next();
			String key = (String) dto.getModelkey();
			System.out.print("key:::::::::::::::::" + key);
			// dto.setLinkList((ArrayList) linkhash.get(key));
			if (eventhash.get(key) != null) {
				dto.setEventList((ArrayList) eventhash.get(key));
			}
			// 拼装事件字符串
			if (dto.getEventList() != null) {
				String eventStr = "";
				Iterator event_iter = dto.getEventList().iterator();
				while (event_iter.hasNext()) {
					MM14_link_event event_dto = (MM14_link_event) event_iter.next();
					eventStr += "'" + event_dto.getEventkey() + "',";
				}
				dto.setEventStr(eventStr.substring(0, eventStr.length() - 1));
			}
			hash.put(key, dto);
		}
		
		return hash;
		
	}
	
}
