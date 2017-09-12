package com.citic.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.mapper.MC00_triger_factMapper;
import com.citic.server.mapper.MC00_triger_subMapper;

/**
 * @author hubq
 *
 */

@Service
public class TrigerService {
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(TrigerService.class);

	@Autowired
	MC00_triger_factMapper mC00_triger_factMapper;
	
	@Autowired
	MC00_triger_subMapper mC00_triger_subMapper;
	
	@Autowired
	CacheService cacheService;
	
	public TrigerService() {

	}
	
	/**
	 * 取得待计算的触发条件列表(去掉已经设置的)
	 * 用于计算触发器条件
	 * @return
	 */
	
	public ArrayList getMC00_triger_subList(String datatime) throws Exception{
		
		ArrayList triger_subList = new ArrayList();
		
		HashMap trigerMap = (HashMap)cacheService.getCache("triger_subbytrigerid",HashMap.class);
		HashMap factMap = (HashMap)this.getTriger_factMap(datatime);
		
		Iterator keyIter = trigerMap.keySet().iterator();
		while(keyIter.hasNext()){ // 按照触发器循环判断
			String trigerid = (String)keyIter.next();
			
			ArrayList subList = (ArrayList)trigerMap.get(trigerid);
			
			Iterator iter = subList.iterator();
			
			while(iter.hasNext()){ //按照触发器下的条件，循环判断
				MC00_triger_sub mC00_triger_sub = (MC00_triger_sub)iter.next();
				
				if(!factMap.containsKey(trigerid)){//没设置过,所有条件都要判断
					triger_subList.add( mC00_triger_sub );
				}else{//判断不满足的条件才设置
					String trigercondid = mC00_triger_sub.getTrigercondid();
					HashMap trigercondidMap = (HashMap)factMap.get(trigerid);

					if(!trigercondidMap.containsKey(trigercondid)){
						triger_subList.add( mC00_triger_sub );
					}
					
				}
				
			}//end while
			
			
		}//end while
		
		return triger_subList;
	}

	/**
	 * 
	 * @param datatime
	 * @return
	 */
	public HashMap getTriger_factMap(String datatime){
		HashMap resultMap = new HashMap();
		
		ArrayList factList = mC00_triger_factMapper.getMC00_triger_factList(datatime);
		
		Iterator fIter = factList.iterator(); 
		while(fIter.hasNext()){
			MC00_triger_fact mC00_triger_fact = (MC00_triger_fact)fIter.next();
			String key = mC00_triger_fact.getTrigerid();
			String val = mC00_triger_fact.getTrigercondid();
			String vvalue = "";
			
			if(val.equalsIgnoreCase("S2")){
				vvalue = mC00_triger_fact.getFreq();
			}
			
			if(val.equalsIgnoreCase("S3")){
				vvalue = mC00_triger_fact.getDsid();
			}
			
			HashMap subMap = new HashMap();
			
			if(resultMap.containsKey(key)){
				subMap = (HashMap)resultMap.get(key);
			}
			subMap.put(val, vvalue);
			
			resultMap.put(key, subMap);
			
		}// end while
		
		return resultMap;
	}

	/**
	 * 根据当日触发条件的计算情况，判断触发器满足情况 
	 * @param datatime
	 * @return 任务编码列表
	 */
	public ArrayList getFinishedTrigerList(String datatime){
		ArrayList list = new ArrayList();
		/** 触发器与任务映射列表 */
		HashMap taskMap = (HashMap) cacheService.getCache("taskbytrigerid",HashMap.class);
		/** 系统所有触发条件 */
		HashMap trigersubMap = (HashMap) cacheService.getCache("triger_subbytrigerid", HashMap.class);
		/** 触发条件事实表 */
		HashMap trigerfactMap =(HashMap)this.getTriger_factMap(datatime);
		
		Iterator iter = taskMap.keySet().iterator();
		while (iter.hasNext()) {//触发器循环
			String trigerid = (String) iter.next();
			
			String freqs = "";
			String dsids = "";
			//
			ArrayList trigersubList=null;
			if(trigersubMap.containsKey(trigerid))
				trigersubList=(ArrayList)trigersubMap.get(trigerid);
			
			HashMap trigerfactHash = null;//key=trigerid value=HashMap< key = trigercondid>
			if(trigerfactMap.containsKey(trigerid))
				trigerfactHash = (HashMap)trigerfactMap.get(trigerid); 
			
			if(trigersubList!=null && trigerfactHash!=null && (trigersubList.size() == trigerfactHash.size()) ){//触发条件满足
				
				Iterator trigerfactIter = trigerfactHash.keySet().iterator();
				while(trigerfactIter.hasNext()){//触发条件循环
					//MC00_triger_fact mC00_triger_fact = (MC00_triger_fact)trigerfactIter.next();
					
					String trigercondid = (String)trigerfactIter.next();
					
					if(trigercondid.equalsIgnoreCase("S2")){
						freqs = (String)trigerfactHash.get( trigercondid );
					}
					
					if(trigercondid.equalsIgnoreCase("S3")){
						dsids = (String)trigerfactHash.get( trigercondid );
					}
					
				}
				
				HashMap trigerMap = new HashMap();
				trigerMap.put("trigerid", trigerid);
				trigerMap.put("freqs", freqs);
				trigerMap.put("dsids", dsids);
				list.add(trigerMap);
				
			}
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param datatime
	 * @return
	 */
	public HashMap getTrigerFactMap(String datatime){
		HashMap map = new HashMap();
		/** 触发条件事实表 */
		ArrayList trigerfactList = mC00_triger_factMapper.getMC00_triger_factList(datatime);
		Iterator tfIter = trigerfactList.iterator();
		while(tfIter.hasNext()){//判断触发条件是否满足
			MC00_triger_fact mC00_triger_fact = (MC00_triger_fact)tfIter.next();
			String trigerid = mC00_triger_fact.getTrigerid();
			
			ArrayList factList = new ArrayList();
			if(map.containsKey(trigerid)){
				factList = (ArrayList)map.get(trigerid);
			}
			factList.add(mC00_triger_fact);
			
			map.put(trigerid, factList);
			
		}
		
		return map;
	}
	
}
