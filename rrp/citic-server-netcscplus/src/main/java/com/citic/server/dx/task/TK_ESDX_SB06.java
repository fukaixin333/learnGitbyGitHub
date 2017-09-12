package com.citic.server.dx.task;
  



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.domain.Cs_case;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.request.SuspiciousRequest100403;
import com.citic.server.dx.domain.request.SuspiciousRequest_Accounts;
import com.citic.server.dx.task.taskBo.Dx_reportBo;
import com.citic.server.net.mapper.BR22_caseMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;


 
/**
 *异常开卡
 * 
 * @author  dingke
 * @version 1.0
 */

public class TK_ESDX_SB06 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_SB06.class);
	//private JdbcTemplate jdbcTemplate = null;


	
	

	public TK_ESDX_SB06(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		//ApplicationCFG applicationCFG = (ApplicationCFG) this.getAc().getBean("applicationCFG");
		//jdbcTemplate = (JdbcTemplate) this.getAc().getBean(applicationCFG.getJdbctemplate_business());
	
 
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

			      //1.查询异常开卡案件表
		     	Cs_case  cs_case_ajjb=br22_caseMapper.getBr22_CaseList(caseid);
			
			     //2.查询异常开卡的卡信息
			    List<SuspiciousRequest_Accounts> cardList=br22_caseMapper.getBr22_Case_CardByCaseIdList(caseid);
			     //3. 生成xml文件
			    SuspiciousRequest100403 case_yckk= reportBo.makeXml_yckk(cs_case_ajjb, cardList);
	
			      //4.调用接口发送
				   Response response=reportBo.send_msg_yckk(case_yckk,cs_case_ajjb.getCaseid(),cs_case_ajjb.getMsg_type_cd());
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