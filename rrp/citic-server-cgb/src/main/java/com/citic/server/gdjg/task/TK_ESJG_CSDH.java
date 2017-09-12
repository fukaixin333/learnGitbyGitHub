package com.citic.server.gdjg.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjg.domain.Datasend_log;
import com.citic.server.gdjg.domain.Gdjg_Response;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDhdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDhdj_City;
import com.citic.server.gdjg.mapper.MM57_cxqqMapper;
import com.citic.server.gdjg.service.RequestMessageServiceGdjg;
import com.citic.server.gdjg.task.taskBo.Cxqq_FKBo;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.DtUtils;

/**
 * 反馈城市代号对照登记接口
 * 
 * @author liuxuanfei
 * @date 2017年6月2日 下午3:41:07
 */
public class TK_ESJG_CSDH extends NBaseTask{
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJG_CSDH.class);
	private RequestMessageServiceGdjg requestSend;
	private MM57_cxqqMapper mm57_cxqqMapper;

	public TK_ESJG_CSDH(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		this.requestSend = (RequestMessageServiceGdjg) ac.getBean("requestMessageServiceGdjg");
		this.mm57_cxqqMapper = (MM57_cxqqMapper) ac.getBean("MM57_cxqqMapper");
	}
	


	@Override
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			Cxqq_FKBo fkBo = new Cxqq_FKBo(this.getAc());
			// 1. 获取最后发送时间（银行基础数据发送表）
			String lastsendts = mm57_cxqqMapper.getLastsendts(mc21_task_fact);
			mc21_task_fact.setUsetime(lastsendts);

			// 2. 获取城市代号表(需上报)
			Gdjg_RequestDhdj dhdj = new Gdjg_RequestDhdj();
			List<Gdjg_RequestDhdj_City> cityList = mm57_cxqqMapper.getBr40_city_noList(mc21_task_fact);
			dhdj.setCitys(cityList);
			if (cityList.size() == 0) {
				return false;
			}
			// 调用上报接口
			Gdjg_Response response = requestSend.sendCsdhdzdjMassage(dhdj);

			fkBo.insertBr57_receipt(response, "CSDH");
			// 3.插入基础数据发送日志表
			Datasend_log logdto = new Datasend_log();
			logdto.setTasktype(mc21_task_fact.getTasktype());// 监管类别
			logdto.setBasename("3");// 1-网点登记表 2-账号规则表 3-城市代号
			logdto.setSendts(DtUtils.getNowTime());// 发送时间
			logdto.setSendnum(cityList.size() + "");// 发送记录数
			mm57_cxqqMapper.insertBr40_datasend_log(logdto);

			// 4. 更新银行基础数据发送表
			mc21_task_fact.setUsetime(logdto.getSendts());
			mm57_cxqqMapper.updateBr40_basedata_send(mc21_task_fact);
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}

		return isSucc;
	}
	
}
