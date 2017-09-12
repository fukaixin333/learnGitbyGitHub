package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_flagfile;
import com.citic.server.mapper.MC00_flagfileMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_flagfile extends BaseCache {

	public Cache_flagfile(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MC00_flagfileMapper mC00_flagfileMapper = (MC00_flagfileMapper)this.getAc().getBean("MC00_flagfileMapper");
		
		ArrayList list = mC00_flagfileMapper.getMC00_flagfileList();
		HashMap resultMap = new HashMap();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			MC00_flagfile ff = (MC00_flagfile)iter.next();
			
			String dsid = ff.getDsid();
			
			resultMap.put(dsid, ff);
		}
		
		return resultMap; 
				
	}
	
}