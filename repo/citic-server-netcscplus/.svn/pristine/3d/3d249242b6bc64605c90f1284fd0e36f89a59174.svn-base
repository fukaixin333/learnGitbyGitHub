package com.citic.server.dx.task;
  


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
















import com.citic.server.dx.domain.Attachment;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.Transaction;
import com.citic.server.dx.domain.request.CaseRequest100401;
import com.citic.server.dx.domain.request.CaseRequest_Transaction;
import com.citic.server.dx.task.taskBo.Dx_reportBo;
import com.citic.server.net.mapper.BR22_caseMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;



 
/**
 *案件举报
 * 
 * @author  dingke
 * @version 1.0
 */

public class TK_ESDX_SB05 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_SB05.class);
	//private JdbcTemplate jdbcTemplate = null;

	
	
	

	public TK_ESDX_SB05(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
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

			      //1.查询人工举报案件表
		 	CaseRequest100401  cs_case_ajjb=br22_caseMapper.getBr22_Case_ajjb_List(caseid);

			     //2.查询人工举报案件下的交易
			    List<CaseRequest_Transaction> transList=br22_caseMapper.getBr22_PCase_transByCaseIdList(caseid);
			     //3.查询附件
			    List<Attachment>  acctchList=br22_caseMapper.getCaseAttach(caseid);
			     //3. 生成xml文件
				
			     reportBo.makeXml_ajjb(cs_case_ajjb, transList,acctchList);
			    
			     //4.调用接口发送
			   Response response=reportBo.send_msg_ajjb(cs_case_ajjb);
			   //插入回执响应表		
			   reportBo.insertBr24_q_k_back_m_f(response);
			     //修改案件表状态7:已生成xml
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