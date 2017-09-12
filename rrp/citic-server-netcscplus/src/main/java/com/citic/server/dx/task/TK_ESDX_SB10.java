package com.citic.server.dx.task;
  


import java.util.ArrayList;









import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.request.SuspiciousRequest100405;
import com.citic.server.dx.task.taskBo.Dx_reportBo;
import com.citic.server.dx.task.taskBo.ValidateBo;
import com.citic.server.net.mapper.BR22_caseMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;



 
/**
 *取消报文
 * @author  dingke
 * @version 1.0
 */

public class TK_ESDX_SB10 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_SB10.class);
	//private JdbcTemplate jdbcTemplate = null;

	//private CacheService cacheService;
	
	

	public TK_ESDX_SB10(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		//ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean("applicationCFG");
		//jdbcTemplate = (JdbcTemplate) this.getAc().getBean(applicationCFG.getJdbctemplate_business());
		//cacheService = (CacheService) ac.getBean("cacheService");

    }

	public boolean calTask() throws Exception {
		boolean isSucc = true;
	      try {
			 	MC21_task_fact mc21_task_fact= this.getMC21_task_fact();
			 	Dx_reportBo  reportBo=new Dx_reportBo(this.getAc());
			 	String caseid=mc21_task_fact.getSubtaskid();		 	
			    //3. 生成取消报文4.调用接口发送
			   Response response=reportBo.makeXml_qx(caseid);
			   //插入回执响应表		
			   reportBo.insertBr24_q_k_back_m_f(response);
			     //修改案件表状态
			   reportBo.updateCase_status(caseid, response.getCode());
				
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;			
		}
	      
	 
		return isSucc;
	}

	 

}