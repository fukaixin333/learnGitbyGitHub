package com.citic.server.service.task.tk_etl101;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_ds_tables;
import com.citic.server.domain.MC00_task_fact;


/**
 * 将数据源表数据，通过JDBC读取到本地，写成数据文件
 * 不适合大量数据抽取
 * @author hubaiqing
 */
public class GetFileByJDBC extends BaseFile{

	private static final Logger logger = LoggerFactory.getLogger(GetFileByJDBC.class);
	
	public GetFileByJDBC(ApplicationContext ac,MC00_datasource ds, MC00_task_fact tf) {
		super(ac,ds, tf);
	}

    @Override
	public  boolean run() throws Exception{
    	boolean isSucc = false;

    	try{
    		JdbcTemplate jdbcTemplate = this.getJdbcTemplate(this.getMC00_datasource().getDbconnection());
    		
    		ArrayList tableList = this.getMC00_task_fact().getTableList();
            Iterator iter = tableList.iterator();
            while(iter.hasNext()){
            	MC00_ds_tables mC00_ds_tables = (MC00_ds_tables)iter.next();
            	
            	String etlsql = mC00_ds_tables.getEtlcmd();
            	String datatime = this.getMC00_task_fact().getDatatime();
            	String freq = this.getMC00_task_fact().getFreq();
            	String tablename = mC00_ds_tables.getTablename();
            	etlsql = etlsql.replace("YYYY-MM-DD", datatime).replace("FREQ", freq);
            	
            	logger.info("JDBC下载的数据表到本地:"+tablename);
            	
            	isSucc = startEtl(jdbcTemplate,etlsql);
            	
            }
    		    		
    	}catch(Exception e){
    		throw e;
    	}
    	
    	return isSucc;
    	
    }
    
    /**
     * 分页获取数据，并写入文件
     * @param conn
     * @param sql
     * @return
     * @throws Exception
     */
    private boolean startEtl(JdbcTemplate jdbcTemplate,String sql) throws Exception{
    	 
    	//
    	logger.error("暂未实现！");
    	 
    	 return false;
    	
    }
    
}
