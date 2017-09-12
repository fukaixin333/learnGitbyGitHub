package com.citic.server.cbrc.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.cbrc.domain.Datasend_log;
import com.citic.server.cbrc.domain.request.CBRC_RequestWddj;
import com.citic.server.cbrc.domain.request.CBRC_RequestWddj_Branch;
import com.citic.server.cbrc.mapper.MM40_cxqq_cbrcMapper;
import com.citic.server.cbrc.service.RequestMessageServiceCBRC;
import com.citic.server.cbrc.task.taskBo.Cxqq_FKBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.DtUtils;

/**
 * 反馈银行网点登记接口
 * 
 * @author hxj
 * @version 1.0
 */

public class TK_ESYJ_YHWD extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESYJ_YHWD.class);
	
	public TK_ESYJ_YHWD(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		
	}
	
	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
				MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
				Cxqq_FKBo fkBo = new Cxqq_FKBo(this.getAc());
				MM40_cxqq_cbrcMapper mm40_cxqq_cbrcMapper = (MM40_cxqq_cbrcMapper) this.getAc().getBean("MM40_cxqq_cbrcMapper");
				//1. 获取最后发送时间（银行基础数据发送表）
				String lastsendts = mm40_cxqq_cbrcMapper.getLastsendts(mc21_task_fact);
				mc21_task_fact.setUsetime(lastsendts);
				
				//2. 获取银行网点登记表(需上报)
				CBRC_RequestWddj wddj = new CBRC_RequestWddj();
				List<CBRC_RequestWddj_Branch> branchs = mm40_cxqq_cbrcMapper.getBr40_branch_regList(mc21_task_fact);
				wddj.setBranchs(branchs);
				if(branchs.size()==0){
					return false;
				}
				//调用上报接口
				RequestMessageServiceCBRC   requestSend = new RequestMessageServiceCBRC();
				//CBRC_Response response = requestSend.sendYhwddjMassage(wddj);
			
				//fkBo.insertBr52_receipt(response,"YHWD");
				
				//3.插入基础数据发送日志表
				Datasend_log logdto = new Datasend_log();
				logdto.setTasktype(mc21_task_fact.getTasktype());//监管类别
				logdto.setBasename("1");//1-网点登记表 2-账号规则表 3-城市代号
				logdto.setSendts(DtUtils.getNowTime());//发送时间
				logdto.setSendnum(branchs.size()+"");//发送记录数
				mm40_cxqq_cbrcMapper.insertBr40_datasend_log(logdto);
				
				//4. 更新银行基础数据发送表
				mc21_task_fact.setUsetime(logdto.getSendts());
				mm40_cxqq_cbrcMapper.updateBr40_basedata_send(mc21_task_fact);
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
	
}