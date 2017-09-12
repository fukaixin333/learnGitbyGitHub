package com.citic.server.service.tasksplit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.service.CacheService;

/**
 * @author hubq
 * @version 1.0
 */


public class Split_datasource extends BaseSplit {

	public Split_datasource(ApplicationContext ac,String taskid,String tasksplit,String splitparams) {
		super(ac,taskid,tasksplit,splitparams);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public ArrayList getSubtaskidList() throws Exception{
		ArrayList subtaskidList = new ArrayList();
		String splitparams = this.getSplitparams();
		String[] params = splitparams.split(",");
		String freq = params[0];
		String dsid = params[1];
		
		if(dsid.equals("")){
			CacheService cacheService = (CacheService)this.getAc().getBean( "cacheService" );
			
			HashMap datasourceMap = (HashMap)cacheService.getCache("datasource",HashMap.class);
			
			Iterator iter = datasourceMap.keySet().iterator();
			
			while(iter.hasNext()){
				subtaskidList.add( iter.next() );
			}
		}else{
			subtaskidList.add( dsid );
		}
		
		return subtaskidList;
	}
	
}