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
 * 数据源及本地数据库同为ORACLE,并且数据量不大的情况，可以通过DBLINK将数据抽取到ODS表中
 * @author hubaiqing
 *
 */
public class GetFileByDBLINK extends BaseFile{

	private static final Logger logger = LoggerFactory.getLogger(GetFileByDBLINK.class);
	
	
	public GetFileByDBLINK(ApplicationContext ac,MC00_datasource ds, MC00_task_fact tf) {
		super(ac,ds, tf);
	}
	
    @Override
	public  boolean run() throws Exception{
    	
    	boolean isSucc = false;
    	
		try {
			
			String datatime = this.getMC00_task_fact().getDatatime();
			String freq = this.getMC00_task_fact().getFreq();
			
			ArrayList tableList = this.getMC00_task_fact().getTableList();
            Iterator iter = tableList.iterator();
            while(iter.hasNext()){
            	
            	MC00_ds_tables mC00_ds_tables = (MC00_ds_tables)iter.next();
            	
            	String tablename = mC00_ds_tables.getTablename();
            	String procName = mC00_ds_tables.getEtlcmd();
            	
            	logger.info("DBLINK下载的数据表到本地:"+tablename);
            	
            	JdbcTemplate jdbcTemplate = this.getJdbcTemplate( this.getMC00_datasource().getDbconnection() );
            	
    			String proc_cmd = procName+"('"+datatime+"', '"+tablename+"', '"+freq+"')";
    			
    	    	jdbcTemplate.execute( "{ CALL "+ proc_cmd +"}" );
            	
            }
			
			isSucc = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("taskid="+this.getMC00_task_fact().getTaskid()+";subtaskid="+this.getMC00_task_fact().getSubtaskid()+"出现错误！");
			throw e;
		}
    	
		return isSucc;
    }
        
}
