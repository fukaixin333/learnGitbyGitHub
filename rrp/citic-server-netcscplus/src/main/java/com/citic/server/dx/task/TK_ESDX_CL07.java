package com.citic.server.dx.task;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.Br25_Freeze;
import com.citic.server.dx.domain.Br25_Freeze_back;
import com.citic.server.dx.task.taskBo.Dx_KzqqBo;
import com.citic.server.net.mapper.BR25_kzqqMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 冻结任务发核心
 * 
 * @author dingke
 * @version 1.0
 */

public class TK_ESDX_CL07 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_CL07.class);
	//private JdbcTemplate jdbcTemplate = null;
	
	private CacheService cacheService;
	
	public TK_ESDX_CL07(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
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
		MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
		Dx_KzqqBo kzqqBo = new Dx_KzqqBo(this.getAc());
		String orgkey = "";
		try {
			BR25_kzqqMapper br25_kzqqMapper = (BR25_kzqqMapper) this.getAc().getBean("BR25_kzqqMapper");
			String transserialnumber = mc21_task_fact.getBdhm();
			
			//1.查询请求表
			Br25_Freeze cg_Freeze = br25_kzqqMapper.getBr25_frozenByID(transserialnumber);
			Br25_Freeze_back cs_Freeze = new Br25_Freeze_back();
			cs_Freeze.setTransSerialNumber(cg_Freeze.getTransSerialNumber());
			cs_Freeze = br25_kzqqMapper.getBr25_frozenBackByID(cs_Freeze);
			if (cs_Freeze == null) {
				cs_Freeze = new Br25_Freeze_back();
			}
			orgkey = cs_Freeze.getOrgkey();
			String msgCheckResult = cs_Freeze.getMsgcheckresult();
			if (msgCheckResult != null && (msgCheckResult.equals("") || msgCheckResult.equals("1"))) { //判断是否是本行数据
				//2.调用接口发核心
				cs_Freeze = kzqqBo.sendHx_dj(cg_Freeze);
			} else {
				mc21_task_fact.setIsemployee("0");
			}
			if (mc21_task_fact.getIsemployee() != null && mc21_task_fact.getIsemployee().equals("2")) { //Isemployee=2成功的不走流程 不成功时走流程
				if (!cs_Freeze.getResult().equals("0000")) {
					mc21_task_fact.setIsemployee("0");
				} else {
					mc21_task_fact.setIsemployee("1");
				}
			}
			
			//删除请求单号下的响应数据
			br25_kzqqMapper.delKzResBw_dj(transserialnumber);
			// 3.插入响应表
			cs_Freeze.setMsgcheckresult(msgCheckResult);
			kzqqBo.insertKzXy_dj(cg_Freeze, cs_Freeze);
			
			//3.插入task3
			kzqqBo.insertMc21TaskFact3(mc21_task_fact, "DX");
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		} finally {
			if (mc21_task_fact.getIsemployee().equals("1")) {//发送短信
				//7.发送短信
				kzqqBo.sendMsg(orgkey, mc21_task_fact.getTaskname(), mc21_task_fact.getSubtaskid(), "3", mc21_task_fact.getTasktype(), "2");
				
			}
		}
		
		return isSucc;
	}
	
}