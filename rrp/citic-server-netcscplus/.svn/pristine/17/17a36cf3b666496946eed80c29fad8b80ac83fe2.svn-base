package com.citic.server.dx.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.Br25_Freeze_back;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.request.FreezeRequest100202;
import com.citic.server.dx.task.taskBo.Dx_KzqqBo;
import com.citic.server.net.mapper.BR25_kzqqMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 冻结任务发公安
 * 
 * @author dingke
 * @version 1.0
 */

public class TK_ESDX_FK07 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_FK07.class);
	
	//private JdbcTemplate jdbcTemplate = null;
	
	//private CacheService cacheService;
	
	public TK_ESDX_FK07(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
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
			//1.查询冻结请求响应表
			Br25_Freeze_back br25_freeze_back = new Br25_Freeze_back();
			br25_freeze_back.setTransSerialNumber(transserialnumber);
			Br25_Freeze_back cs_freeze = br25_kzqqMapper.getBr25_frozenBackByID(br25_freeze_back);
			//2. 生成xml字符串
			//    String sendStr= kzqqBo.makeXml_dj(cs_freeze);
			//3.调用接口发送
			Response response = kzqqBo.sendMsgl_dj(cs_freeze);
			
			if (!TxConstants.CODE_OK.equals(response.getCode())) {
				isSucc = false;
			}
			
			//4.插入响应表
			kzqqBo.insertBr24_q_k_back_m_f(response);
			//5.修改请求主表的状态
			cs_freeze.setTransSerialNumber(transserialnumber);
			kzqqBo.updateDj_status(cs_freeze, response.getCode());
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
	
}