package com.citic.server.jsga.task;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.jsga.domain.response.JSGA_FreezeResponse_Account;
import com.citic.server.jsga.mapper.MM41_kzqq_jsgaMapper;
import com.citic.server.jsga.task.taskBo.Kzqq_CLBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

public class TK_ESJS_CL04 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJS_CL04.class);
	
	public TK_ESJS_CL04(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		super(_ac, mC21_task_fact);
		
	}
	
	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
		Kzqq_CLBo clBo = new Kzqq_CLBo(this.getAc());
		String orgkey = "";
		String rwlsh = "";
		try {
			
			MM41_kzqq_jsgaMapper br41_kzqqMapper = (MM41_kzqq_jsgaMapper) this.getAc().getBean("MM41_kzqq_jsgaMapper");
			String qqdbs = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
			rwlsh = StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$");
			/** 主键 */
			Br41_kzqq br41_kzqq = new Br41_kzqq();
			br41_kzqq.setTasktype(mc21_task_fact.getTasktype());
			br41_kzqq.setQqdbs(qqdbs);
			
			//1.查询请求表
			br41_kzqq = br41_kzqqMapper.selectBr41_kzqqByVo(br41_kzqq);
			br41_kzqq.setRwlsh(rwlsh);
			JSGA_FreezeResponse_Account cg_Freeze = br41_kzqqMapper.selectBr41_kzqq_djByVo(br41_kzqq);
			orgkey = cg_Freeze.getOrgankey();
			//2.调用接口发核心
			
			cg_Freeze.setQqcslx(br41_kzqq.getQqcslx());
			clBo.sendHx_dj(cg_Freeze, mc21_task_fact);
			
		} catch (Exception e) {
			logger.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		} finally {
			if (!mc21_task_fact.getIsemployee().equals("0")) {//发送短信
				//7.发送短信
				clBo.sendMsg(orgkey, mc21_task_fact.getTaskname() + "[" + rwlsh + "]", mc21_task_fact.getSubtaskid(), "3", mc21_task_fact.getTasktype(), "2");
			}
		}
		
		return isSucc;
	}
	
}
