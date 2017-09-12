package com.citic.server.gdjg.task;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjg.domain.Br57_cxqq;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseBxxcx;
import com.citic.server.gdjg.task.taskBo.Cxqq_JSBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;



/**
 * 解析保险箱查询
 * 
 * @author liuxuanfei
 * @date 2017年5月24日 下午8:21:59
 */
public class TK_ESJG_JS04 extends NBaseTask{
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESJG_JS04.class);
	private CacheService cacheService;
	
	public TK_ESJG_JS04(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		cacheService = (CacheService) ac.getBean("cacheService");
	}

	@Override
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {
			
			MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			Cxqq_JSBo jsBo = new Cxqq_JSBo(this.getAc());
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
			Gdjg_ResponseBxxcx query_bxxcx = (Gdjg_ResponseBxxcx) CommonUtils.unmarshallDocument(Gdjg_ResponseBxxcx.class, flie_path,"GBK");
			
			//3.删除请求单下的数据
			Br57_cxqq br57_cxqq = new Br57_cxqq();
			br57_cxqq.setDocno(docno);//协作编号
			jsBo.delQureyResponse(br57_cxqq);
			
			//4.处理请求报文信息及插入task2
			jsBo.handleCxqqBw_Bxxcx(query_bxxcx, mc21_task_fact, taskmag);
			
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;
		}
		return isSucc;
	}
	
}
