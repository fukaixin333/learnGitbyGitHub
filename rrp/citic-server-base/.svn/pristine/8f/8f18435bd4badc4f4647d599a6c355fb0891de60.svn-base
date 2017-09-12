package com.citic.server.service.tasksplit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_di_tables;
import com.citic.server.service.CacheService;

/**
 * 数据抽取任务拆分（按照数据源，数据源表）
 * @author hubaiqing
 * @version 1.0
 */

public class Split_ditables extends BaseSplit {

	public Split_ditables(ApplicationContext ac,String taskid,String tasksplit,String splitparams) {
		super(ac,taskid,tasksplit,splitparams);
	}

	/**
	 * 对数据接口表：
	 * 如果一个任务（taskid）下对应多个子任务，那么按照子任务设置
	 */
	public ArrayList getSubtaskidList() throws Exception{
		ArrayList subtaskidList = new ArrayList();
		
		String splitparams = this.getSplitparams();
		String[] params = splitparams.split(",");
		String freq = params[0];
		String dsid = params[1];
		
		CacheService cacheService = (CacheService)this.getAc().getBean( "cacheService" );
		
		HashMap di_tablesMap = (HashMap)cacheService.getCache("di_tables",HashMap.class);
		
		if(di_tablesMap.containsKey( this.getTaskid() )){
			
			HashMap subtaskMap = (HashMap) di_tablesMap.get( this.getTaskid() );
			
			Iterator iter = subtaskMap.keySet().iterator();
			
			while(iter.hasNext()){
				
				String subtaskid = (String)iter.next();
				
				boolean canset = false;
				ArrayList ditList = (ArrayList)subtaskMap.get(subtaskid);
				
				Iterator ditIter = ditList.iterator();
				while(ditIter.hasNext()){
					MC00_di_tables dit = (MC00_di_tables)ditIter.next();
					String _dsid = dit.getDsid();
					String _freq = dit.getFreq();
					if(!_dsid.equals(dsid) || !_freq.equals(freq)){//子任务与数据源相同，认为可以设置任务
						canset = true;
						break;
					}
				}
				if(canset){
					subtaskidList.add( subtaskid );
				}
				
			}
			
		}

		return subtaskidList;
	}
	
}