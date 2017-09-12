package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MP01_system_param;
import com.citic.server.mapper.MP01_system_paramMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_mp01_system_param extends BaseCache {

	public Cache_mp01_system_param(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MP01_system_paramMapper mP01_system_paramMapper = (MP01_system_paramMapper)this.getAc().getBean("MP01_system_paramMapper");
		
		ArrayList subList = mP01_system_paramMapper.getMP01_system_paramList();
		HashMap resultMap = new HashMap();
		
		Iterator iter = subList.iterator();
		while(iter.hasNext()){
			MP01_system_param mP01_system_param = (MP01_system_param)iter.next();
			String paramtype = mP01_system_param.getParamtype();
			String paramkey = mP01_system_param.getParamkey();
			
			String key = paramtype+"-"+paramkey;
			
			resultMap.put(key, mP01_system_param);
		}
		
		return resultMap; 
				
	}
	
}