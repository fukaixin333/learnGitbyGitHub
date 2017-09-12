package com.citic.server.dx.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.Br24_bas_info;
import com.citic.server.dx.domain.Br24_q_Main;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.request.QueryRequest100302;
import com.citic.server.dx.service.RequestMessageService;
import com.citic.server.dx.task.taskBo.Q_main_backBo;
import com.citic.server.net.mapper.MM24_q_mainMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.DtUtils;

/**
 * 账户交易明细查询
 * 
 * @author hxj
 * @version 1.0
 */

public class TK_ESDX_FK01 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_FK01.class);
	
	public TK_ESDX_FK01(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		
	}
	
	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			
			MM24_q_mainMapper mm24_q_mainMapper = (MM24_q_mainMapper) this.getAc().getBean("MM24_q_mainMapper");
			
			Q_main_backBo q_main_backBo = new Q_main_backBo(this.getAc());
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			
			QueryRequest100302 request = new QueryRequest100302();
			
			//1.获取账户交易明细查询反馈DTORequest100302
			request = q_main_backBo.setQueryRequest100302(mc21_task_fact);
			
			//2.调用接口发送
			Response response = requestMessageService.sendQueryRequest100302(request);
			//Response response =new Response();
			
			//3.插入响应表
			response.setTransSerialNumber(mc21_task_fact.getBdhm());
			q_main_backBo.insertBr24_q_k_back_m_f(response);
			
			//4.设置BR24_查询请求单主表状态 3成功 4失败
			Br24_q_Main main = new Br24_q_Main();
			main.setTransSerialNumber(mc21_task_fact.getBdhm());
			String status = "";
			if (TxConstants.CODE_OK.equals(response.getCode())) {
				status = "3";
			} else {
				isSucc = false;
				status = "4";
			}
			main.setStatus(status);
			mm24_q_mainMapper.updateBr24_q_main(main);
			
			Br24_bas_info br24_bas_info = new Br24_bas_info();
			br24_bas_info.setTransSerialNumber(mc21_task_fact.getBdhm());
			br24_bas_info.setStatus(status);
			br24_bas_info.setFeedback_dt(DtUtils.getNowTime());
			br24_bas_info.setResult("");
			mm24_q_mainMapper.updateBr24_bas_info(br24_bas_info);
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
	
}