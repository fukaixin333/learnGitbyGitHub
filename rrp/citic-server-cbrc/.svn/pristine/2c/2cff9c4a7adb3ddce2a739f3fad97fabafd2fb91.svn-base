package com.citic.server.cbrc.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.cbrc.domain.Br40_cxqq;
import com.citic.server.cbrc.domain.Br40_cxqq_back_pz;
import com.citic.server.cbrc.mapper.MM40_cxqq_cbrcMapper;
import com.citic.server.cbrc.mapper.MM41_kzqq_cbrcMapper;
import com.citic.server.cbrc.task.taskBo.Cxqq_CLBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

public class TK_ESYJ_CL07 extends NBaseTask{
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESYJ_CL07.class);

	public TK_ESYJ_CL07(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		super(_ac, mC21_task_fact);
	
	}


	/**
	 *rendonghang 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
		Cxqq_CLBo clBo = new Cxqq_CLBo(this.getAc());

		String orgkey ="";
		String rwlsh ="";
		String qqdbs ="";
		try {		
			MM40_cxqq_cbrcMapper	br40_cxqqMapper = (MM40_cxqq_cbrcMapper) this.getAc().getBean("MM40_cxqq_cbrcMapper");
			// 处理凭证调阅查询
			Br40_cxqq  cxqq=clBo.getBr40_cxqq(mc21_task_fact);
			Br40_cxqq_back_pz br40_cxqq_back_pz =br40_cxqqMapper.queryBr40_cxqq_back_pz(cxqq);
			orgkey = br40_cxqq_back_pz.getOrgkey();
						
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}finally{
			if(!mc21_task_fact.getIsemployee().equals("0")){//发送短信
				//7.发送短信
				clBo.sendMsg(orgkey, mc21_task_fact.getTaskname()+"["+rwlsh+"]",mc21_task_fact.getSubtaskid(),"3",mc21_task_fact.getTasktype(),"2",qqdbs);			
			}
		}

		return isSucc;
	}

}
