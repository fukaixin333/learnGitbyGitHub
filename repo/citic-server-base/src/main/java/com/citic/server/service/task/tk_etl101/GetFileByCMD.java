package com.citic.server.service.task.tk_etl101;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_ds_tables;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.utils.ExecmdUtils;

/**
 * 数据源及本地数据库同为ORACLE,并且数据量不大的情况，可以通过DBLINK将数据抽取到ODS表中
 * @author hubaiqing
 *
 */
public class GetFileByCMD extends BaseFile{

	private static final Logger logger = LoggerFactory.getLogger(GetFileByCMD.class);
	
	
	public GetFileByCMD(ApplicationContext ac,MC00_datasource ds, MC00_task_fact tf) {
		super(ac,ds, tf);
	}

	@Override
	public  boolean run() throws Exception{
    	boolean isSucc = false;
    	try{
    		
    		ArrayList tableList = this.getMC00_task_fact().getTableList();
            Iterator iter = tableList.iterator();
            while(iter.hasNext()){
            	MC00_ds_tables mC00_ds_tables = (MC00_ds_tables)iter.next();
            	
            	String cmdStr = mC00_ds_tables.getEtlcmd();
            	String datatime = this.getMC00_task_fact().getDatatime();
            	String freq = this.getMC00_task_fact().getFreq();
            	
            	String tablename = mC00_ds_tables.getTablename();
            	
            	logger.info("DBCMD下载的数据表到本地:"+tablename+",CMD="+cmdStr);
            	
            	String dbpName = this.getDatabaseProductName( this.getMC00_datasource().getDbconnection() );
            	
            	if(cmdStr==null)
    				throw new Exception("抽取命令为空！");
    			if(dbpName.toLowerCase().indexOf("oracle") >-1 ){
    				
    			  isSucc = 	ExecmdUtils.exec_oracle(cmdStr);
    			
    			}else{
    			
    				isSucc = ExecmdUtils.exec_normal(cmdStr);
    			
    			}
            	
    			if(!isSucc){
    				throw new Exception("命令执行失败！");
    			}
    			
            }
    	}catch(Exception e){
    		isSucc = false;
    		throw e;
    	}
    	
    	return isSucc;
    	
    }
    
   
 
    
  
        
}
