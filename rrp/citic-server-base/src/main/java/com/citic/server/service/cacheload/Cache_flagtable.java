package com.citic.server.service.cacheload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_flagtable;
import com.citic.server.domain.MC00_flagtable_cols;
import com.citic.server.mapper.MC00_flagtableMapper;
import com.citic.server.mapper.MC00_flagtable_colsMapper;

/**
 * @author hubq
 * @version 1.0
 */


public class Cache_flagtable extends BaseCache {

	public Cache_flagtable(ApplicationContext ac,String cachename) {
		super(ac,cachename);
	}

	/**
	 * 实现数据源准备情况扫描
	 * @return 数据源列表
	 */
	public Object getCacheByName() throws Exception{
		
		MC00_flagtableMapper mC00_flagtableMapper = (MC00_flagtableMapper)this.getAc().getBean("MC00_flagtableMapper");
		MC00_flagtable_colsMapper mC00_flagtable_colsMapper = (MC00_flagtable_colsMapper)this.getAc().getBean("MC00_flagtable_colsMapper");
		
		ArrayList list = mC00_flagtableMapper.getMC00_flagtableList();
		ArrayList clist = mC00_flagtable_colsMapper.getMC00_flagtable_colsList();
		
		HashMap resultMap = new HashMap();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			MC00_flagtable ft = (MC00_flagtable)iter.next();
			
			String dsid = ft.getDsid();
			String tablename = ft.getTablename();
			
			//== 列配置信息
			ArrayList flagtable_colsList = new ArrayList();
			Iterator cIter = clist.iterator();
			while(cIter.hasNext()){
				MC00_flagtable_cols ftc = (MC00_flagtable_cols)cIter.next();
				
				if(ftc.getDsid().equals(dsid) && ftc.getTablename().equalsIgnoreCase( tablename )){
					flagtable_colsList.add( ftc );
				}
				
			}
			ft.setFlagtable_colsList( flagtable_colsList );
			
			resultMap.put(dsid, ft);
		}
		
		return resultMap; 
				
	}
	
}