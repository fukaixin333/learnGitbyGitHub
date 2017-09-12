package com.citic.server.dx.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.Br25_StopPay_back;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.task.taskBo.Dx_KzqqBo;
import com.citic.server.net.mapper.BR25_kzqqMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 止付任务发公安
 * 
 * @author dingke
 * @version 1.0
 */

public class TK_ESDX_FK06 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_FK06.class);
	
	//private JdbcTemplate jdbcTemplate = null;
	
	//private CacheService cacheService;
	
	public TK_ESDX_FK06(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
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
			BR25_kzqqMapper br25_kzqqMapper = (BR25_kzqqMapper) this.getAc().getBean("BR25_kzqqMapper");
			
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			String transserialnumber = mc21_task_fact.getBdhm();
			Dx_KzqqBo kzqqBo = new Dx_KzqqBo(this.getAc());
			//1.查询止付请求响应表
			Br25_StopPay_back cs_StopPay = new Br25_StopPay_back();
			cs_StopPay.setTransSerialNumber(transserialnumber);
			cs_StopPay = br25_kzqqMapper.getBr25_stopBackByID(cs_StopPay);
			//2. 生成xml字符串
			//  String sendStr= kzqqBo.makeXml_zf(cs_StopPay);
			//3.调用接口发送
			Response response = kzqqBo.sendMsg_zf(cs_StopPay);
			
			if (!TxConstants.CODE_OK.equals(response.getCode())) {
				isSucc = false;
			}
			
			//4.插入响应表
			kzqqBo.insertBr24_q_k_back_m_f(response);
			cs_StopPay.setTransSerialNumber(transserialnumber);
			//5.修改请求主表的状态
			kzqqBo.updateZf_status(cs_StopPay, response.getCode());
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
	
}