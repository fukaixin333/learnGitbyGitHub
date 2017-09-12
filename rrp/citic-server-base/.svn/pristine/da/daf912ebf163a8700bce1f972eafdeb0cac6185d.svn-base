package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.mapper.MC00_triger_subMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_triger_subbytrigerid extends BaseCache {

	public Cache_triger_subbytrigerid(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MC00_triger_subMapper mC00_triger_subMapper = (MC00_triger_subMapper)this.getAc().getBean("MC00_triger_subMapper");
		
		ArrayList subList = mC00_triger_subMapper.getMC00_triger_subList();
		HashMap resultMap = new HashMap();
		
		Iterator iter = subList.iterator();
		while(iter.hasNext()){
			MC00_triger_sub mC00_triger_sub = (MC00_triger_sub)iter.next();
			String trigerid = mC00_triger_sub.getTrigerid();
			ArrayList trigersubList = new ArrayList();
			if(resultMap.containsKey(trigerid)){
				trigersubList = (ArrayList)resultMap.get(trigerid);
			}
			trigersubList.add( mC00_triger_sub );//一个触发器，对应多个任务
			resultMap.put(trigerid, trigersubList);
		}
		
		return resultMap; 
				
	}
	
}