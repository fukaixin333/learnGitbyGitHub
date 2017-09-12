package com.citic.server.service.task;
  


import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.dx.task.taskBo.Br13_alertBo;
import com.citic.server.service.CacheService; 


 
/**
 *匹配成功的预警合并
 * 
 * @author  dingke
 * @version 1.0
 */

public class TK_ESDX_SB01 extends BaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_SB01.class);
	//private JdbcTemplate jdbcTemplate = null;

	private CacheService cacheService;
	
	

	public TK_ESDX_SB01(ApplicationContext ac, MC00_task_fact mC21_task_fact) {
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
	    	  Br13_alertBo  alertBo=new Br13_alertBo();
	    	  String data_dt=this.getMC00_task_fact().getDatatime();
	    	
		     //1.删除生成案例的数据 
	    	  ArrayList<String> delsqlList=new   ArrayList<String>();
	    	  delsqlList = alertBo.delBR13_date(data_dt,delsqlList);// 数据清理
	    	  isSucc = this.syncToDatabase(delsqlList);
	  			if (!isSucc)	return isSucc;
	  	
	  		 //2.将匹配成功的数据放入到合并表
	  		  ArrayList<String> sqlList=new   ArrayList<String>();
	  		sqlList = alertBo.insertBR13_date(data_dt,sqlList);// 插入合并预警表
	         //3.修改状态
	  		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
	  	// 获取合并天数和交易笔数
			String bsmStr = (String) sysParaMap.get("BSM");
	  		sqlList = alertBo.updateBR13_date(bsmStr,data_dt,sqlList);
	  		
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