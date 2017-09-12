package com.citic.server.service.task;
  


import java.util.ArrayList;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;











import com.citic.server.domain.MC00_task_fact;
import com.citic.server.dx.task.taskBo.Br13_alertBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;



 
/**
 *生成案例下的补录数据
 * @author  dingke
 * @version 1.0
 */

public class TK_ESDX_SB03 extends BaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_SB03.class);
	//private JdbcTemplate jdbcTemplate = null;

	//private CacheService cacheService;
	
	

	public TK_ESDX_SB03(ApplicationContext ac, MC00_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		//ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean("applicationCFG");
		//jdbcTemplate = (JdbcTemplate) this.getAc().getBean(applicationCFG.getJdbctemplate_business());
		//cacheService = (CacheService) ac.getBean("cacheService");

    }

	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
	      try {
	    	  Br13_alertBo  alertBo=new Br13_alertBo();
	    	  String data_dt=this.getMC00_task_fact().getDatatime();
	    	
		     //1.插入补录交易
	  		  ArrayList<String> sqlList=new   ArrayList<String>();
	  		  sqlList = alertBo.insertBR21_trans_data(data_dt,sqlList);
	  		  //2.插入补录客户表
	  		 sqlList = alertBo.insertBR21_party_data(data_dt,sqlList);
	  		  //3.插入补录卡表
	  		 sqlList = alertBo.insertBR21_CARD_data(data_dt,sqlList);
	  		  //4.插入补录账户表
	  		 sqlList = alertBo.insertBR21_acct_data(data_dt, sqlList);	  		  
	  		  //5.修改案件表中的客户证件类型和号码名称
	  		 sqlList = alertBo.UPDATEBR22_case(data_dt, sqlList) ;	
	  		 
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