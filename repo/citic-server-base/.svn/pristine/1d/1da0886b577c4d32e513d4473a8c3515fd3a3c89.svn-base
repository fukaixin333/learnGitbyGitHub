package com.citic.server.service.task;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.citic.server.ApplicationCFG;
import com.citic.server.domain.MC00_di_tables;
import com.citic.server.domain.MC00_task_fact;

/**
 * 从ODS到数据集市的基础转换逻辑
 * @author hubiqing
 * @version 1.0
 */
@Component("TK_ETL301")
public class TK_ETL301 extends BaseTask {
	
	private static final Logger logger = LoggerFactory.getLogger(TK_ETL301.class);
//	private JdbcTemplate jdbcTemplate = null;
	public TK_ETL301(ApplicationContext ac,MC00_task_fact mC00_task_fact) {
		super(ac,mC00_task_fact);
		ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean("applicationCFG");
		jdbcTemplate = (JdbcTemplate) this.getAc().getBean(applicationCFG.getJdbctemplate_business());
	}
	
	

	public TK_ETL301() {
		super();
	}



	public boolean calTask() throws Exception{
		boolean isSuccess = true;
		
		String taskid = this.getMC00_task_fact().getTaskid();
		String subtaskid = this.getMC00_task_fact().getSubtaskid();
		String datatime = this.getMC00_task_fact().getDatatime();
		String freq = this.getMC00_task_fact().getFreq();
		String dbpName = getdbpName();
        
		//MC00_di_tables mC00_di_tables = new MC00_di_tables();
		
		/** 取得当前任务对应的数据表列表 */
		HashMap di_tableMap = (HashMap)this.getCacheService().getCache("di_tables",HashMap.class);
		
		ArrayList tableList = new ArrayList();
		if(di_tableMap.containsKey( taskid )){
			
			HashMap subtaskMap = (HashMap)di_tableMap.get(taskid);
			
			if(subtaskMap.containsKey( subtaskid )){
				
				tableList = (ArrayList)subtaskMap.get( subtaskid );
				
			}

		}
		if(tableList.size()==0){
			logger.info("没有找到ODS2DM的ETL执行命令，请检查MC00_DI_TABLES配置正确性！TASKID="+taskid+";SUBTASKID="+subtaskid+"");
		}
		
		/**
		 * 对一个子任务下的多个数据表，进行ETL
		 */
		Iterator iter = tableList.iterator();
		while(iter.hasNext()){
			MC00_di_tables dit = (MC00_di_tables)iter.next();
			
			String procName = dit.getEtlproc();
			String tableName = dit.getTablename();
			
			logger.info("开始装载数据表："+tableName+" TASKID="+taskid +";SUBTASKID="+subtaskid);
			
			JdbcTemplate jdbcTemplate = this.getBusinessJdbcTemplate();
	    	
			String proc_cmd = procName+"('"+datatime+"', '"+tableName+"', '"+freq+"')";
			 if (dbpName.toLowerCase().indexOf("postgresql") > -1) {
				 jdbcTemplate.execute( " SELECT  "+ proc_cmd );
			}else{
	         	jdbcTemplate.execute( "{ CALL "+ proc_cmd +"}" );
				}
	    	
	    	logger.info("数据表："+tableName+" 装载 OK!");
			
		}
		
		return isSuccess ; 
		
	}
	
	 private String getdbpName(){
			Connection conn =  null ;
			String dbpName = "";
			
			try {
				conn= jdbcTemplate.getDataSource().getConnection();
				DatabaseMetaData md = conn.getMetaData();
			    dbpName = md.getDatabaseProductName();
			} catch (SQLException e) {
				logger.error("获取数据库错误。。。");
				logger.error(e.getMessage());
			}finally {
				DbUtils.closeQuietly(conn);

			}
			
			return dbpName;

	 }
	
	
}