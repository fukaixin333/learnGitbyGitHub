package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.mapper.MC00_datasourceMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_datasource extends BaseCache {

	public Cache_datasource(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MC00_datasourceMapper mC00_datasourceMapper = (MC00_datasourceMapper)this.getAc().getBean("MC00_datasourceMapper");
		
		ArrayList dsList = mC00_datasourceMapper.getMC00_datasourceList();
		HashMap resultMap = new HashMap();
		Iterator iter = dsList.iterator();
		while(iter.hasNext()){
			MC00_datasource ds = (MC00_datasource)iter.next();
			
			String dsid = ds.getDsid();
			
			//resultMap.put(dsid, dsList);
			resultMap.put(dsid, ds);
		}
		
		return resultMap; 
				
	}
	
}