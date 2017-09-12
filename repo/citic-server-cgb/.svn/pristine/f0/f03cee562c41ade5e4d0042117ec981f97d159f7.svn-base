package com.citic.server.gdjg.task;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjg.domain.Br57_cxqq;
import com.citic.server.gdjg.domain.Br57_kzqq;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseCkjdkz;
import com.citic.server.gdjg.task.taskBo.Kzqq_JSBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;



/**
 * 解析存款解冻申请
 * 
 * @author liuxuanfei
 * @date 2017年6月15日 下午8:45:26
 */
public class TK_ESJG_JS06 extends NBaseTask{
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJG_JS06.class);
	private CacheService cacheService;
	

	
	public TK_ESJG_JS06(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		cacheService = (CacheService) ac.getBean("cacheService");
	}

	@Override
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			
			MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			Kzqq_JSBo jsBo = new Kzqq_JSBo(this.getAc());
			String docno = mc21_task_fact.getBdhm();
			
			//1.取出文件地址
			ArrayList<MC20_task_msg> taskMsgList = common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
			//获取系统参数
			HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
			String root = StrUtils.null2String((String) sysParaMap.get("2"));
			MC20_task_msg taskmag = new MC20_task_msg();
			if (taskMsgList != null && taskMsgList.size() > 0) {
				taskmag = taskMsgList.get(0);
			}
			
			//2.解析请求报文
			String flie_path = root + taskmag.getMsgpath();
			Gdjg_ResponseCkjdkz control_ckjd = (Gdjg_ResponseCkjdkz) CommonUtils.unmarshallDocument(Gdjg_ResponseCkjdkz.class, flie_path,"GBK");
			
			//3.删除请求单下的数据
			Br57_kzqq br57_kzqq = new Br57_kzqq();
			br57_kzqq.setDocno(docno);//协作编号
			jsBo.delQureyResponse(br57_kzqq);
			
			//4.处理请求报文信息及插入task2
			jsBo.handleCxqqBw_Ckjdkz(control_ckjd, mc21_task_fact, taskmag);
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		return isSucc;
	}
}
