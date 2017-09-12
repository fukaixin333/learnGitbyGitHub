package com.citic.server.dx.task;
  
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.domain.Br24_bas_info;
import com.citic.server.dx.domain.response.QueryResponse100301;
import com.citic.server.dx.task.taskBo.Q_mainBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;
 
/**
 * 解析账户交易明细查询请求报文
 * @author  
 * @version 1.0
 */

public class TK_ESDX_JS01 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_JS01.class);
	private CacheService cacheService;
	
	
	
	public TK_ESDX_JS01(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		cacheService = (CacheService) ac.getBean("cacheService");
	}

	/**
	 * 解析账户交易明细查询请求报文
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
	   try {
	
		MC00_common_Mapper common_Mapper=(MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper"); 

		MC21_task_fact mc21_task_fact= this.getMC21_task_fact();
		String orgkey=mc21_task_fact.getTaskobj();
		Br24_bas_info br24_bas_info = new Br24_bas_info();
		Q_mainBo q_mainBo=new Q_mainBo(this.getAc());

	    //1.取出文件地址
	    ArrayList<MC20_task_msg> taskMsgList=common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
	    //获取系统参数
		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		String root=StrUtils.null2String((String)sysParaMap.get("2"));
		MC20_task_msg  taskmag=new MC20_task_msg();
		if(taskMsgList!=null&&taskMsgList.size()>0){
			  taskmag=taskMsgList.get(0);
		 }
		 
		//2.解析请求报文
		String flie_path = root + taskmag.getMsgpath();
		QueryResponse100301 query = (QueryResponse100301) CommonUtils.unmarshallUTF8Document(QueryResponse100301.class, flie_path);
		
		//删除请求单号下的数据
		q_mainBo.delQureyResponse(mc21_task_fact.getBdhm());
		
		//插入查询请求单主表
		q_mainBo.insertBr24_q_main100301(query,orgkey,mc21_task_fact.getIsemployee());
		
		//插入反馈基本信息表
		br24_bas_info.setApplicationID(query.getApplicationID());
		br24_bas_info.setToorg(query.getMessageFrom());
		br24_bas_info.setTxCode(query.getTxCode());
		br24_bas_info.setTransSerialNumber(mc21_task_fact.getBdhm());
		br24_bas_info.setOrgkey(orgkey);
		q_mainBo.insertBr24_bas_info(br24_bas_info);
		
		//插入附件表
		q_mainBo.insertBr24_q_attach(mc21_task_fact.getBdhm(),taskmag);

		//插入归属机构的task2任务
		mc21_task_fact.setFacttablename(query.getAccountName()+"#"+taskmag.getPacketkey());
	    q_mainBo.insertOrgMc21TaskFact2(mc21_task_fact,"1",query.getSubjectType(),query.getCardNumber());
	
        //3.插入task2
		q_mainBo.insertMc21TaskFact2(mc21_task_fact, "DX");
		
		
	      
	   } catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
			this.inertErrorLog(e);
			throw e;			
		}
		return isSucc;
	}


	 

}