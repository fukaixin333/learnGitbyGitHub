package com.citic.server.dx.task;
  



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;






import com.citic.server.dx.domain.Cs_case;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.request.SuspiciousRequest100404;
import com.citic.server.dx.domain.request.SuspiciousRequest_Account;
import com.citic.server.dx.task.taskBo.Dx_reportBo;
import com.citic.server.net.mapper.BR22_caseMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;


 
/**
 *涉案账户
 * 
 * @author  dingke
 * @version 1.0
 */

public class TK_ESDX_SB07 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_SB07.class);
	//private JdbcTemplate jdbcTemplate = null;

	private CacheService cacheService;
	
	

	public TK_ESDX_SB07(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
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
	    	  BR22_caseMapper br22_caseMapper = (BR22_caseMapper) this.getAc().getBean("BR22_caseMapper");		
		 	MC21_task_fact mc21_task_fact= this.getMC21_task_fact();
		 	Dx_reportBo  reportBo=new Dx_reportBo(this.getAc());
		 	String caseid=mc21_task_fact.getSubtaskid();
			
			     //1.查询涉案账户的主账户信息
		 	SuspiciousRequest100404 cs_case_sazh=br22_caseMapper.getBr22_CaseMinAcct(caseid);
			   
			   //2.查询其他账户信息
			   List<SuspiciousRequest_Account>  acctList=br22_caseMapper.getBr22_Case_acctByCaseIdList(caseid);
			     //3. 生成xml文件
			   Cs_case cs_case= reportBo.makeXml_sazh(cs_case_sazh,acctList,caseid);
			   //4.调用接口发送
			   Response response=reportBo.send_msg_sazh(cs_case_sazh,cs_case.getCaseid(),cs_case.getMsg_type_cd());
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