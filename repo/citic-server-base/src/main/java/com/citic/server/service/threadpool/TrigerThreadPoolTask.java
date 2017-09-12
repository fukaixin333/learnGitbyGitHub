package com.citic.server.service.threadpool;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationCFG;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.mapper.MC00_triger_factMapper;
import com.citic.server.service.triger.BaseTrigerCondition;
import com.citic.server.service.triger.TrigerFactory;

public class TrigerThreadPoolTask implements Callable<String>, Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(TrigerThreadPoolTask.class);
	
	private static final long serialVersionUID = 0;

	// 保存任务所需要的数据
	private String datatime;
	private MC00_triger_sub mC00_triger_sub;
	private ApplicationContext ac;
	//private MC00_triger_factMapper mC00_triger_factMapper;
	
	public TrigerThreadPoolTask(ApplicationContext _ac,String _datatime,MC00_triger_sub _mC00_triger_sub) {

		this.ac = _ac;
		this.datatime = _datatime;
		this.mC00_triger_sub = _mC00_triger_sub;
		//this.mC00_triger_factMapper = _mC00_triger_factMapper;

	}

	public synchronized String call() throws Exception {

		String result = "SUCESS";

		String trigercondid = mC00_triger_sub.getTrigercondid();

		try {

			TrigerFactory trigerFactory = new TrigerFactory();
			
			BaseTrigerCondition trigerCondition = trigerFactory.getInstance(ac,datatime, mC00_triger_sub,null);
			
			ArrayList trigerfactList = trigerCondition.runTrigerCondition();
			
			this.syncToDatabase(trigerfactList);
			
		} catch (Exception e) {

			e.printStackTrace();

			result = "FALSE,(datatime="+datatime+";triggerid="+mC00_triger_sub.getTrigerid()+";triggercondid="+mC00_triger_sub.getTrigercondid()+"),原因："+e.getMessage();
			logger.error(result);
		}

		return result;

	}

	private void syncToDatabase(final ArrayList trigerfactList) throws Exception{
		/**
		 * 添加新任务
		 */
		String sql = "insert into mC00_triger_fact(trigerid,trigercondid,datatime,freq,dsid) values(?,?,?,?,?)";
		
		ApplicationCFG applicationCFG = (ApplicationCFG) ac.getBean("applicationCFG");
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate)ac.getBean(applicationCFG.getJdbctemplate_business());
		
		try{
			
		
		BatchPreparedStatementSetter setter=new BatchPreparedStatementSetter (){
	          public void setValues(PreparedStatement ps,int i) throws SQLException{
	        	  MC00_triger_fact tf=(MC00_triger_fact)trigerfactList.get(i);
	               ps.setString(1,tf.getTrigerid() );
	               ps.setString(2,tf.getTrigercondid() );
	               ps.setString(3,tf.getDatatime() );
	               ps.setString(4,tf.getFreq() );
	               ps.setString(5,tf.getDsid() );
	               
	          }
	          public int getBatchSize(){
	             return trigerfactList.size();
	          }
		};
		
		
		
		int[] resut =  jdbcTemplate.batchUpdate(sql,setter);
		
		}catch(Exception e){
			logger.error("trigerfactList="+trigerfactList.size());
			Iterator iter1 = trigerfactList.iterator();
			while(iter1.hasNext()){
				MC00_triger_fact tf=(MC00_triger_fact)iter1.next();
				logger.error(tf.getTrigerid()+""+tf.getTrigercondid());
			}
			throw e;
		}
	}
	
}
