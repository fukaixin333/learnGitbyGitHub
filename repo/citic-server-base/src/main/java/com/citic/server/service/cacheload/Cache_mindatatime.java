package com.citic.server.service.cacheload;

import org.springframework.context.ApplicationContext;

import com.citic.server.mapper.MC00_data_timeMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_mindatatime extends BaseCache {

	public Cache_mindatatime(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MC00_data_timeMapper mc00_data_timeMapper = (MC00_data_timeMapper)this.getAc().getBean("MC00_data_timeMapper");
		
		String datatime = mc00_data_timeMapper.getDatatimeForMinDt();
		
		return datatime; 
				
	}
	
}