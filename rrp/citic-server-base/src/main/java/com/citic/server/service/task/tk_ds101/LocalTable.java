package com.citic.server.service.task.tk_ds101;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_flagtable;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.base.NeetReCalException;


public class LocalTable extends BaseDS {

	private static final Logger logger = LoggerFactory
			.getLogger(LocalTable.class);
   
	public LocalTable(ApplicationContext ac,MC00_datasource ds, MC00_task_fact tf) {
		super(ac,ds, tf);
	} 

    /**
     *
     */
    @Override
    public boolean run() throws Exception {
    	
    	boolean isSuccess = false;
    	
    	HashMap flagTableHash = (HashMap) this.getCacheService().getCache(
				"flagtable", HashMap.class);

		MC00_flagtable ft = (MC00_flagtable) flagTableHash.get(this
				.getMC00_datasource().getDsid());
    	
        try {
        	
        	isSuccess = this.verifyTableflag(this.getMC00_datasource(), this.getMC00_task_fact(), ft);
        	
        	//是否更新标识表
        	if(ft.getIs_upd().equals("1")){
        		
        		isSuccess = this.updateFlagTableStatus(this.getMC00_datasource(), this.getMC00_task_fact(), ft);
        		
        	}
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //throw ex;
            isSuccess = false;
        }finally{
        	if(!isSuccess){//没有找到符合的数据标识文件，本任务需要循环探测
    			throw new NeetReCalException();
    		}
        }
        
        
        
        return isSuccess;
    }

}
