package com.citic.server.service.triger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.service.CacheService;

/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_S3 extends BaseTrigerCondition {

	public TrigerCondition_S3(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}
	
	/**
	 * S3：按照数据源触发，默认按照全部数据源触发,参数不用设置
	 *    
	 *    核心含义：如果设置S3，那么触发器要分数据源进行触发 ， 在生成任务列表时，会按照数据源分别生成
	 *          备注：与S2核心含义不同
	 *    
	 * 参数说明：
	 * @param datatime
	 * @param mC00_triger_sub
	 * @return
	 */
	public ArrayList runTrigerCondition() throws Exception{
		ArrayList tfList = new ArrayList();
		
		String dsids = "";
		CacheService cacheService= (CacheService)this.getAc().getBean("cacheService");
		HashMap datasourceMap = (HashMap)cacheService.getCache("datasource",HashMap.class);
		
		Iterator iter = datasourceMap.keySet().iterator();
		while(iter.hasNext()){
			String dsid = (String)iter.next();
			dsids = dsids + "," +dsid;//所有数据源都需要
		}
		
		if(dsids.startsWith(",")){
			dsids = dsids.substring(1);
		}
		
		MC00_triger_fact mC00_triger_fact= new MC00_triger_fact();
		mC00_triger_fact.setTrigerid( this.getMC00_triger_sub().getTrigerid());
		mC00_triger_fact.setTrigercondid(this.getMC00_triger_sub().getTrigercondid());
		mC00_triger_fact.setDatatime(this.getDatatime());		
		mC00_triger_fact.setFreq("0");
		mC00_triger_fact.setDsid(dsids);
		
		if(dsids.length()>0)
			tfList.add(mC00_triger_fact);
		return tfList;
	}
	
}