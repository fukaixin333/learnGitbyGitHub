package com.citic.server.service.tasksplit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_ds_tables;
import com.citic.server.mapper.MC00_ds_tablesMapper;
import com.citic.server.service.CacheService;

/**
 * 数据抽取任务拆分（按照数据源，数据源表）
 * @author hubaiqing
 * @version 1.0
 */

public class Split_dstables extends BaseSplit {

	public Split_dstables(ApplicationContext ac,String taskid,String tasksplit,String splitparams) {
		super(ac,taskid,tasksplit,splitparams);
	}

	/**
	 * 
	 */
	public ArrayList getSubtaskidList() throws Exception{
		ArrayList subtaskidList = new ArrayList();
		String splitparams = this.getSplitparams();
		String[] params = splitparams.split(",");
		String freq = params[0];
		String dsid = params[1];
		
		CacheService cacheService = (CacheService)this.getAc().getBean( "cacheService" );
		
		HashMap ds_tablesMap = (HashMap)cacheService.getCache("ds_tables",HashMap.class);
		
		if(ds_tablesMap.containsKey( this.getTaskid() )){
			
			HashMap subtaskMap = (HashMap) ds_tablesMap.get( this.getTaskid() );
			
			Iterator iter = subtaskMap.keySet().iterator();
			
			while(iter.hasNext()){
				
				String subtaskid = (String)iter.next();
				
				//判断对应的表格属性
				ArrayList dstList = (ArrayList)subtaskMap.get(subtaskid);
				Iterator dstIter = dstList.iterator();
				boolean isSet = true;
				while(dstIter.hasNext()){
					MC00_ds_tables dst = (MC00_ds_tables)dstIter.next();
					String _dsid = dst.getDsid();
					//String _freq = dst.getFreq();
					//if(!_dsid.equals(dsid) || !_freq.equals(freq)){
					if(!_dsid.equals(dsid)){
						isSet = false;
						break;
					}
				}
				if(isSet){
					subtaskidList.add( subtaskid );
				}
				
				//
			}
			
		}

		return subtaskidList;
	}
	
}