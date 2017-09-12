/**
 * 
 */
package com.citic.server.service.task.tk_ds101;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_flagtable;
import com.citic.server.domain.MC00_flagtable_cols;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.service.CacheService;
import com.citic.server.service.base.Base;

/**
 * @author Administrator
 * 
 */
public abstract class BaseDS extends Base{

	private static final Logger logger = LoggerFactory.getLogger(BaseDS.class);
	
	private MC00_datasource ds;
	private MC00_task_fact tf;
	
	private JdbcTemplate jdbcTemplate;
	
	private CacheService cacheService;
	
    private ApplicationContext ac;
	
    public BaseDS(ApplicationContext _ac,MC00_datasource ds,MC00_task_fact tf) {
    	this.ac = _ac;
    	this.ds = ds;
    	this.tf = tf;
    	jdbcTemplate = (JdbcTemplate)ac.getBean("jdbcTemplate");
    	cacheService = (CacheService)ac.getBean("cacheService");
    }

    public abstract boolean run() throws Exception;

    
    /**
     * 
     * @param conn
     * @param t18_flagtable
     * @return
     * @throws Exception
     */
    public boolean verifyTableflag(MC00_datasource _mC00_datasource,MC00_task_fact _mC00_task_fact, MC00_flagtable _mC00_flagtable)
            throws Exception {
    	boolean isSuccess = false;
        
        if(_mC00_flagtable.getDsid().equals("") || _mC00_flagtable.getFlagtable_colsList().size()==0){
            return false;
        }
        
        String query = "";
        String select = "SELECT ";
        String where = " WHERE 1>0 ";
        //String order = " ORDER BY ";
       
        //获取标识表的列集合
        ArrayList colList = _mC00_flagtable.getFlagtable_colsList();
        //迭代
        Iterator iterator = colList.iterator();
        while (iterator.hasNext()) {
        	MC00_flagtable_cols mC00_flagtable_cols = (MC00_flagtable_cols) iterator
                    .next();
            // 如果是时间字段
            //select为:select  col,where :where 1>0  and col ='date'  
            if (mC00_flagtable_cols.getIs_date().equals("1")) {
                select += mC00_flagtable_cols.getColname().toUpperCase() +" AS DT";
                
                where += " AND "
                		+ mC00_flagtable_cols.getColname().toUpperCase()
                		+ " = '" + _mC00_task_fact.getDatatime() + "'";
            }
            // 如果是状态条件字段
            if (mC00_flagtable_cols.getIs_status().equals("1")) {
                where += " AND "
                        + mC00_flagtable_cols.getColname().toUpperCase()
                        + " = '" + _mC00_flagtable.getFlag_val() + "'"
                        + "";
            }
            // 如果是排序字段
            /**
            if (mC00_flagtable_cols.getIs_order().equals("1")) {
                order += mC00_flagtable_cols.getColname().toUpperCase() + " "
                        + mC00_flagtable_cols.getOrder_type().toUpperCase()
                        + ",";
            }
            **/

        }
        //order = order.substring(0, order.length() - 1); 
        
        query = select + " FROM "
                + _mC00_flagtable.getTablename() + where;// + order;
        logger.debug(query);
        
        //从指定表中取当前数据日期的数据记录（满足触发条件），若去到，就说明数据已经准备好
    	String dsStr = _mC00_datasource.getDbconnection();
    	JdbcTemplate cusTemplate = (JdbcTemplate)ac.getBean( dsStr );
    	
    	List resultList = jdbcTemplate.queryForList(query);
    	  
    	if(resultList.size()>0){
    		
    		Map map = (Map)resultList.get(0);
    		
    		if(map.size()>0){
    			String dateStr = (String)map.get("DT");//取到了数据日期
    			
    			if(dateStr==null || (dateStr.length()!=8 && dateStr.length()!=10) ){
                    //格式不正确
                 }else{
                 	isSuccess = true;
                 }
    			
    		}
    	}
       
        return isSuccess;

    }
    
    
    public boolean updateFlagTableStatus(MC00_datasource _mC00_datasource,MC00_task_fact _mC00_task_fact, MC00_flagtable _mC00_flagtable)
    
            throws SQLException {

    	boolean isSucc = false;
        String query = "UPDATE "
                + _mC00_flagtable.getTablename() + " SET ";
        String set = "";
        String where = " WHERE 1>0 ";
        ArrayList colList = _mC00_flagtable.getFlagtable_colsList();
        
        int   isstatusNum=1;
        for (int i = 0; i < colList.size(); i++) {
        	MC00_flagtable_cols mC00_flagtable_cols = (MC00_flagtable_cols) colList.get(i);
        	
        	if (mC00_flagtable_cols.getIs_status().equals("1")) {
        		
          	  if(isstatusNum>1){//多个状态条件字段
          		  set+=" , ";
          	  }
                set += mC00_flagtable_cols.getColname().toUpperCase() + " = '"
                       + _mC00_flagtable.getUpd_val() + "' ";
                ++isstatusNum;
          }
          if (mC00_flagtable_cols.getIs_date().equals("1")) {
              where += " AND "
                      + mC00_flagtable_cols.getColname().toUpperCase()
                      + " = '" +_mC00_task_fact.getDatatime()+"'";
          }
          if(null!=_mC00_flagtable.getFlag_val()&&!"".equals(_mC00_flagtable.getFlag_val())&&
             null!=_mC00_flagtable.getUpd_val()&&!"".equals(_mC00_flagtable.getUpd_val())&&
            		 _mC00_flagtable.getFlag_val().equals(_mC00_flagtable.getUpd_val())){
        	  
          	where +="  AND  " +mC00_flagtable_cols.getColname()+" ='"+_mC00_flagtable.getFlag_val()+"'";
          
          }
		}
        
        query += set + where;

        logger.debug(query);
        
        String dsStr = _mC00_datasource.getDbconnection();
    	JdbcTemplate cusTemplate = (JdbcTemplate)ac.getBean( dsStr );
    	
        try {
        	int i = cusTemplate.update(query);
        	if(i>0) isSucc = true;
        } catch (Exception sqle) {
            isSucc = false;
        } 
        return isSucc;
    }
    
    
    public ApplicationContext getAc() {
		return ac;
	}

	public void setAc(ApplicationContext ac) {
		this.ac = ac;
	}

	public MC00_datasource getMC00_datasource(){
    	return ds;
    }
    public MC00_task_fact getMC00_task_fact(){
    	return tf;
    }
    public JdbcTemplate getBussJdbcTemplate(){
    	return jdbcTemplate;
    }
    public CacheService getCacheService(){
    	return cacheService;
    }
    
}