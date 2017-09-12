package com.citic.server.gdjc.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjc.domain.Datasend_log;
import com.citic.server.gdjc.domain.Gdjc_Response;
import com.citic.server.gdjc.domain.request.Gdjc_RequestGzdj;
import com.citic.server.gdjc.domain.request.Gdjc_RequestGzdj_AccountRule;
import com.citic.server.gdjc.mapper.MM50_cxqqMapper;
import com.citic.server.gdjc.service.RequestMessageServiceGdjc;
import com.citic.server.gdjc.task.taskBo.Cxqq_FKBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.DtUtils;

/**
 * 反馈银行账号规则登记接口
 * 
 * @author hxj
 * @version 1.0
 */

public class TK_ESJJ_ZHGZ extends NBaseTask {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJJ_ZHGZ.class);
	private RequestMessageServiceGdjc requestSend;
	MM50_cxqqMapper mm50_cxqqMapper;
	public TK_ESJJ_ZHGZ(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		this.requestSend = (RequestMessageServiceGdjc) ac.getBean("requestMessageServiceGdjc");
		this.mm50_cxqqMapper = (MM50_cxqqMapper) ac.getBean("MM50_cxqqMapper");
	}
	
	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
				MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
				Cxqq_FKBo fkBo = new Cxqq_FKBo(this.getAc());
				//1. 获取最后发送时间（银行基础数据发送表）
				String lastsendts = mm50_cxqqMapper.getLastsendts(mc21_task_fact);
				mc21_task_fact.setUsetime(lastsendts);
				//2. 获取银行网点登记表(需上报)
				Gdjc_RequestGzdj gzdj = new Gdjc_RequestGzdj();
				List<Gdjc_RequestGzdj_AccountRule> accountrules = mm50_cxqqMapper.getBr40_acct_ruleList(mc21_task_fact);
				gzdj.setAccountrules(accountrules);
				if(accountrules.size()==0){
					return false;
				}
				//调用上报接口
				Gdjc_Response response = requestSend.sendYhzhgzdjMassage(gzdj);
			
				fkBo.insertBr52_receipt(response,"ZHGZ");
				//3.插入基础数据发送日志表
				Datasend_log logdto = new Datasend_log();
				logdto.setTasktype(mc21_task_fact.getTasktype());//监管类别
				logdto.setBasename("2");//1-网点登记表 2-账号规则表 3-城市代号
				logdto.setSendts(DtUtils.getNowTime());//发送时间
				logdto.setSendnum(accountrules.size()+"");//发送记录数
				mm50_cxqqMapper.insertBr40_datasend_log(logdto);
				
				//4. 更新银行基础数据发送表
				mc21_task_fact.setUsetime(logdto.getSendts());
				mm50_cxqqMapper.updateBr40_basedata_send(mc21_task_fact);
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		
		return isSucc;
	}
	
}