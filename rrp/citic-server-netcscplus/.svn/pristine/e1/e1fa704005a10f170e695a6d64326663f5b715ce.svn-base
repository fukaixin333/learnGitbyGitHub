package com.citic.server.service.task;
  


import java.util.ArrayList;





import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_task_fact;
import com.citic.server.dx.task.taskBo.ValidateBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.StrUtils;



 
/**
 *案例下的补录数据验证
 * @author  dingke
 * @version 1.0
 */

public class TK_ESDX_SB04 extends BaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_SB04.class);
	//private JdbcTemplate jdbcTemplate = null;

	private CacheService cacheService;
	
	

	public TK_ESDX_SB04(ApplicationContext ac, MC00_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		//ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean("applicationCFG");
		//jdbcTemplate = (JdbcTemplate) this.getAc().getBean(applicationCFG.getJdbctemplate_business());
		cacheService = (CacheService) ac.getBean("cacheService");

    }

	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
	      try {
	    	  ValidateBo  validateBo=new ValidateBo();
	    	  String data_dt=this.getMC00_task_fact().getDatatime();
			  ArrayList<String> sqlList=new   ArrayList<String>();		  
				HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);	
				String validateFlag = (String) sysParaMap.get("validateFlag");
			if(validateFlag!=null&&validateFlag.equals("0")){
				 sqlList = validateBo.updateBR22_case_validateOK(data_dt, sqlList) ;	
			}else{
	  		  sqlList = validateBo.insertBR21_validate_trans_mid(data_dt, sqlList);
	  		   isSucc = this.syncToDatabase(sqlList);		       
	  		   if(!isSucc) return false;
	  		  sqlList=new   ArrayList<String>();
		     //1.验证交易  
	  		 sqlList =validateBo.insertBT21_validate_mid(data_dt, sqlList);
	  		  //2.验证客户  卡  账号
	  		 sqlList =validateBo.dealBT21_PARTY_A_C_mid(sqlList);	 
	  		  //同步案件标志
  	    	 sqlList = validateBo.updateBR22_case_validate(data_dt, sqlList) ;	
			}
		       isSucc = this.syncToDatabase(sqlList);		       
		    	sqlList.clear(); 
				
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			throw e;			
		}
	      
	 
		return isSucc;
	}

	 

}