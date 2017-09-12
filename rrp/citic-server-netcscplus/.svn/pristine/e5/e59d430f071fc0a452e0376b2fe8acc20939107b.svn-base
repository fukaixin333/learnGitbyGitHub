package com.citic.server.service.task;
  


import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;













import com.citic.server.domain.MC00_task_fact;
import com.citic.server.dx.task.taskBo.Br13_alertBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.StrUtils;



 
/**
 *生成案例
 * @author  dingke
 * @version 1.0
 */

public class TK_ESDX_SB02 extends BaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_SB02.class);
	//private JdbcTemplate jdbcTemplate = null;

	private CacheService cacheService;
	
	

	public TK_ESDX_SB02(ApplicationContext ac, MC00_task_fact mC21_task_fact) {
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
	    	
		     //1.删除案例的数据 
	    	  ArrayList<String> delsqlList=new   ArrayList<String>();
	    	  delsqlList = alertBo.delBR22_date(data_dt,delsqlList);// 数据清理
	    	  isSucc = this.syncToDatabase(delsqlList);
	  			if (!isSucc)	return isSucc;
	  			HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
	  			String toorg =StrUtils.null2String((String) sysParaMap.get("TOORG"));
	  		 //2.生成案例
	  		  ArrayList<String> sqlList=new   ArrayList<String>();
	  		  sqlList = alertBo.insertBR22_date(data_dt,toorg,sqlList);// 插入案件表	       
		   
		    //3.若配置自动上报，将某个时间以前的案件数据直接上报
		    	String caseReportDiff =StrUtils.null2String((String) sysParaMap.get("CASEREPORTDIFF"));
		    	if(caseReportDiff!=null&&!caseReportDiff.equals("")){
		    		sqlList=alertBo.insertTaskFact3(data_dt, caseReportDiff, sqlList);
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