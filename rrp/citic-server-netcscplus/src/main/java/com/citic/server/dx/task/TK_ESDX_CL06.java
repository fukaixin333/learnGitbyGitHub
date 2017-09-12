package com.citic.server.dx.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.Br25_StopPay;
import com.citic.server.dx.domain.Br25_StopPay_back;
import com.citic.server.dx.task.taskBo.Dx_KzqqBo;
import com.citic.server.net.mapper.BR25_kzqqMapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 止付任务发核心
 * 
 * @author dingke
 * @version 1.0
 */

public class TK_ESDX_CL06 extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_CL06.class);
	
	//private JdbcTemplate jdbcTemplate = null;
	
	//private CacheService cacheService;
	
	public TK_ESDX_CL06(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
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
		Dx_KzqqBo kzqqBo = new Dx_KzqqBo(this.getAc());
		
		MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
		String orgkey="";
		try {
			BR25_kzqqMapper br25_kzqqMapper = (BR25_kzqqMapper) this.getAc().getBean("BR25_kzqqMapper");

			String transserialnumber = mc21_task_fact.getBdhm();

			Br25_StopPay_back cs_StopPay = new Br25_StopPay_back();
			//1.查询止付请求表
			Br25_StopPay cg_StopPay = br25_kzqqMapper.getBr25_stopByID(transserialnumber);
			cs_StopPay.setTransSerialNumber(cg_StopPay.getTransSerialNumber());
			 cs_StopPay=br25_kzqqMapper.getBr25_stopBackByID(cs_StopPay);
			if(cs_StopPay==null){
				cs_StopPay= new Br25_StopPay_back();			 
			}
			  orgkey=cs_StopPay.getOrgkey();
			//2.查询HXAPPID
			String txcode = cg_StopPay.getTxCode();
			if (!txcode.equals(TxConstants.TXCODE_STOPPAYMENT)) { //非止付查询冻结编号		
				Br25_StopPay_back br25_stoppay_back= kzqqBo.getHxAppid_zf(cg_StopPay);
				String hxappid =br25_stoppay_back.getHxappid();
				cg_StopPay.setHxappid(hxappid);
				Br25_StopPay _br25_br25_stoppay = br25_kzqqMapper.getBr25_stopByID(br25_stoppay_back.getTransSerialNumber());
				cg_StopPay.setTransferAmount(_br25_br25_stoppay.getTransferAmount());
				cg_StopPay.setCurrency(_br25_br25_stoppay.getCurrency());
				cg_StopPay.setApplicationType(_br25_br25_stoppay.getApplicationType());
				
			}
			String msgCheckResult=cs_StopPay.getMsgcheckresult();
			   if(msgCheckResult!=null&&(msgCheckResult.equals("")||msgCheckResult.equals("1"))){  //判断是否是本行数据
			//4.调用接口发核心
			 cs_StopPay = kzqqBo.sendHx_zf(cg_StopPay);
			   }else{
				   mc21_task_fact.setIsemployee("0");  
			   }
				if (mc21_task_fact.getIsemployee() != null && mc21_task_fact.getIsemployee().equals("2")) { //Isemployee=2成功的不走流程 不成功时走流程
					if(!cs_StopPay.getResult().equals("0000")){
						mc21_task_fact.setIsemployee("0");
					}else{
						mc21_task_fact.setIsemployee("1");
					}
				}
			
			//删除请求单号下的响应数据
			br25_kzqqMapper.delKzResBw_zf(transserialnumber);
			// 5.插入止付响应表
			cs_StopPay.setMsgcheckresult(msgCheckResult);
			kzqqBo.insertKzXy_zf(cg_StopPay, cs_StopPay);
			//6.插入task3
			kzqqBo.insertMc21TaskFact3(mc21_task_fact, "DX");
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}finally{
			if(mc21_task_fact.getIsemployee().equals("1")){//发送短信
				//7.发送短信
				 kzqqBo.sendMsg(orgkey, mc21_task_fact.getTaskname(),mc21_task_fact.getSubtaskid(),"3",mc21_task_fact.getTasktype(),"2");
				
			}
		}
		
		return isSucc;
	}
	
}