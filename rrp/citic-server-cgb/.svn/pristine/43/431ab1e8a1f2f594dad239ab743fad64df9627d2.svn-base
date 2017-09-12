package com.citic.server.jsga.task;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Detail;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Recored;
import com.citic.server.jsga.domain.response.JSGA_StopPaymentResponse_Account;
import com.citic.server.jsga.mapper.MM41_kzqq_jsgaMapper;
import com.citic.server.jsga.task.taskBo.Kzqq_CLBo;
import com.citic.server.runtime.Constants12;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

public class TK_ESJS_CL05 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJS_CL05.class);
	
	// private JdbcTemplate jdbcTemplate = null;
	
	public TK_ESJS_CL05(ApplicationContext _ac, MC21_task_fact mC21_task_fact) {
		super(_ac, mC21_task_fact);
	}
	
	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
		Kzqq_CLBo kzqq_clBo = new Kzqq_CLBo(this.getAc());
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
			
			//1.查询止付请求表
			br41_kzqq = br41_kzqqMapper.selectBr41_kzqqByVo(br41_kzqq);
			br41_kzqq.setRwlsh(rwlsh);
			JSGA_StopPaymentResponse_Account cg_StopPay = br41_kzqqMapper.selectBr41_kzqq_zfByVo(br41_kzqq);
			JSGA_StopPaymentRequest_Recored stoppay = new JSGA_StopPaymentRequest_Recored();
			//2.查询HXAPPID
			String qqcslx = br41_kzqq.getQqcslx();
			if (!qqcslx.equals(Constants12.STOPPAYMENT)) { //非止付查询冻结编号			
				JSGA_StopPaymentRequest_Recored stopPayOld = kzqq_clBo.getHxAppid_zf(cg_StopPay);
				cg_StopPay.setHxappid(stopPayOld.getHxappid());
				stoppay.setTasktype(stopPayOld.getTasktype());
				stoppay.setQqdbs(stopPayOld.getQqdbs());
				stoppay.setRwlsh(cg_StopPay.getYrwlsh());
				stoppay.setZh(stopPayOld.getZh());
				List<JSGA_StopPaymentRequest_Detail> stoppayList = br41_kzqqMapper.selectBr41_kzqq_zf_back_mxByList(stoppay);
				br41_kzqq.setStoppayList(stoppayList);
			}
			orgkey = cg_StopPay.getOrgankey();
			//3.调用接口发核心
			cg_StopPay.setQqcslx(qqcslx);
			kzqq_clBo.sendHx_zf(cg_StopPay, mc21_task_fact, br41_kzqq);
			
		} catch (Exception e) {
			logger.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		} finally {
			if (!mc21_task_fact.getIsemployee().equals("0")) {//发送短信
				//7.发送短信
				kzqq_clBo.sendMsg(orgkey, mc21_task_fact.getTaskname() + "[" + rwlsh + "]", mc21_task_fact.getSubtaskid(), "3", mc21_task_fact.getTasktype(), "2");
			}
		}
		
		return isSucc;
	}
	
}
