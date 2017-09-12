package com.citic.server.service.triger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationCFG;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.service.CacheService;
import com.citic.server.service.base.Base;

/**
 * 
 * @author hubq
 * @version 1.0
 */

public abstract class BaseTrigerCondition extends Base {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseTrigerCondition.class);

	private String datatime;
	private MC00_triger_sub mC00_triger_sub;
	private ApplicationContext ac;
	
	private ArrayList currTksList;
	
    public BaseTrigerCondition(ApplicationContext _ac,String _datatime,MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
    	this.datatime = _datatime;
		this.mC00_triger_sub = _mC00_triger_sub;
		this.ac = _ac;
		this.currTksList = _currTksList;
    }
    
    public abstract ArrayList runTrigerCondition() throws Exception;
    
    /**
     * 取得同触发器中，S2-计算频度配置信息
     * 兼容一个触发器被配置多次S2的情况
     * @return
     */
    public ArrayList getS2_result(){
    	
    	ArrayList freqList = new ArrayList();
    	HashMap freqHash = new HashMap();
    	
    	CacheService cacheService= (CacheService)this.getAc().getBean("cacheService");
    	HashMap trigerMap = (HashMap)cacheService.getCache("triger_subbytrigerid",HashMap.class);
		
    	String trigerid = this.getMC00_triger_sub().getTrigerid();
    	
    	ArrayList trigersubList = (ArrayList)trigerMap.get(trigerid);
    	Iterator iter = trigersubList.iterator();
    	
    	while(iter.hasNext()){
    		MC00_triger_sub sub = (MC00_triger_sub)iter.next();
    		String id = sub.getTrigercondid();
    		
    		if(id.equalsIgnoreCase("s2")){//频度触发器条件
    			String freqs = sub.getParam1();
    			if(freqs.indexOf(",")>0){
    				String[] _freqs = freqs.split(",");
    				for(int i=0;i<_freqs.length;i++){
    					String freq = _freqs[i];
    					if(this.validFreq(freq)){
        					freqHash.put(freq, "");
        				}
    				}
    			}else{
    				if(this.validFreq(freqs)){
    					freqHash.put(freqs, "");
    				}
    			}//
    		}
    		
    	}
    	
    	if(freqHash.size()>0){
    		Iterator fIter = freqHash.keySet().iterator();
    		while(fIter.hasNext()){
    			freqList.add((String)fIter.next());
    		}
    	}else{
    		freqList.add("1");//默认日频度
    	}
    	
    	return freqList;
    }
    
    public boolean validFreq(String freq){
    	boolean isSucc = false;
    	
    	if(freq.equals("1") 
    			|| freq.equals("2") 
    			|| freq.equals("3") 
    			|| freq.equals("4") 
    			|| freq.equals("5") 
    			|| freq.equals("6") 
    			|| freq.equals("7") 
    			){
    		isSucc = true;
    	}
    	
    	return isSucc;
    }
    
    /**
     * 取得同触发器中，S3-数据源编码配置信息
     * 判断同触发器中，是否有按照数据源判断触发的条件
     * @return
     */
    public ArrayList getS3_result(){
    	ArrayList dsList = new ArrayList();
    	
    	CacheService cacheService= (CacheService)this.getAc().getBean("cacheService");
    	HashMap trigerMap = (HashMap)cacheService.getCache("triger_subbytrigerid",HashMap.class);
		
    	String trigerid = this.getMC00_triger_sub().getTrigerid();
    	
    	ArrayList trigersubList = (ArrayList)trigerMap.get(trigerid);
    	Iterator iter = trigersubList.iterator();
    	
    	while(iter.hasNext()){
    		MC00_triger_sub sub = (MC00_triger_sub)iter.next();
    		String id = sub.getTrigercondid();
    		
    		if(id.equalsIgnoreCase("s3")){//数据源触发器条件
    	    	HashMap datasourceMap = (HashMap)cacheService.getCache("datasource",HashMap.class);
    			
    			Iterator dsIter = datasourceMap.keySet().iterator();
    			while(dsIter.hasNext()){
    				String dsid = (String)dsIter.next();
    				dsList.add(dsid);
    			}
    			//
    			break;
    		}
    		
    	}
    	
    	if(dsList.size()==0){
    		dsList.add("0");//默认不区分数据源触发
    	}
    	
    	return dsList;
    }
    
    public boolean isWorkDay(){
    	boolean isworkday = true;
		
		/**
		 * 1-节假日，0-工作日
		 */
		String sql = "select isholiday from mp01_holiday where holidaytype='C' and daykey='"+this.getDatatime()+"'";
		
		ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean("applicationCFG");
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate)this.getAc().getBean(applicationCFG.getJdbctemplate_manager());
		
		List list = jdbcTemplate.queryForList(sql);
		
		if(list.size()>0){
			Map map = (Map)list.get(0);
			
			String isholiday = (String)map.get("isholiday");
			
			if(isholiday.equals("0"))
				isworkday = true;
			else
				isworkday = false;
			
		}else{
			//如果节假日表没有被初始化，判断是否为周六周日
			
			DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
			
			DateTime dt = new DateTime(  this.getDatatime() );
			
			int iWeek = dt.getDayOfWeek();
			if (iWeek == DateTimeConstants.SUNDAY || iWeek== DateTimeConstants.SATURDAY) {//默认周末为节假日
				isworkday = false;
			}
		}
		
		return isworkday;
		
    }
    
    public String getDatatime(){
    	return this.datatime;
    }
    
    public MC00_triger_sub getMC00_triger_sub(){
    	return this.mC00_triger_sub;
    }

	public ApplicationContext getAc() {
		return ac;
	}

	public void setAc(ApplicationContext ac) {
		this.ac = ac;
	}

	public void setDatatime(String datatime) {
		this.datatime = datatime;
	}

	public void setMC00_triger_sub(MC00_triger_sub mC00_triger_sub) {
		this.mC00_triger_sub = mC00_triger_sub;
	}

	public ArrayList getCurrTksList() {
		return currTksList;
	}

	public void setCurrTksList(ArrayList currTksList) {
		this.currTksList = currTksList;
	}
    
    
}
