package com.citic.server.dx.task;
  


import java.util.ArrayList;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.task.taskBo.ValidateBo;
import com.citic.server.server.NBaseTask;

import com.citic.server.service.domain.MC21_task_fact;



 
/**
 *同步案例验证标志
 * @author  dingke
 * @version 1.0
 */

public class TK_ESDX_SB09 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_SB09.class);
	//private JdbcTemplate jdbcTemplate = null;

	//private CacheService cacheService;
	
	

	public TK_ESDX_SB09(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		//ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean("applicationCFG");
		//jdbcTemplate = (JdbcTemplate) this.getAc().getBean(applicationCFG.getJdbctemplate_business());
		//cacheService = (CacheService) ac.getBean("cacheService");

    }

	public boolean calTask() throws Exception {
		boolean isSucc = true;
	      try {
	    	  ValidateBo  validateBo=new ValidateBo();
	    //	  String data_dt=this.getMC21_task_fact().getDatatime();
			  ArrayList<String> sqlList=new   ArrayList<String>();
	  		
	  		  //同步案件标志
	  	    	 sqlList = validateBo.updateBR22_case_validate("", sqlList) ;	
	  		 
		       isSucc = this.syncToDatabase(sqlList);		       
		    	sqlList.clear(); 
				
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;			
		}
	      
	 
		return isSucc;
	}

	 

}