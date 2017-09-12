package com.citic.server.dx.task;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.domain.Br24_bas_info;
import com.citic.server.dx.domain.response.QueryResponse100305;
import com.citic.server.dx.task.taskBo.Q_mainBo;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;

/**
 * 解析动态查询请求报文
 * 
 * @author
 * @version 1.0
 */

public class TK_ESDX_JS03 extends NBaseTask {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TK_ESDX_JS03.class);
	private CacheService cacheService;

	public TK_ESDX_JS03(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		cacheService = (CacheService) ac.getBean("cacheService");
	}

	/**
	 * 解析账户交易明细查询请求报文
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		try {

			MC00_common_Mapper common_Mapper = (MC00_common_Mapper) this.getAc().getBean("MC00_common_Mapper");
			MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
			String orgkey=mc21_task_fact.getTaskobj();
			Q_mainBo q_mainBo = new Q_mainBo(this.getAc());

			// 取出文件地址
			ArrayList<MC20_task_msg> taskMsgList = common_Mapper.getMC20_task_msgList(mc21_task_fact.getTaskkey());
			// 获取系统参数
			HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
			String root = StrUtils.null2String((String) sysParaMap.get("2"));
			MC20_task_msg taskmag = new MC20_task_msg();
			if (taskMsgList != null && taskMsgList.size() > 0) {
				taskmag = taskMsgList.get(0);
			}

			// 解析请求报文
			String flie_path = root + taskmag.getMsgpath();
			QueryResponse100305 query = (QueryResponse100305) CommonUtils.unmarshallUTF8Document(QueryResponse100305.class, flie_path);

			// 删除请求单号下的数据
			q_mainBo.delQureyResponse(mc21_task_fact.getBdhm());

			// 插入查询请求单主表
			q_mainBo.insertBr24_q_main100305(query,orgkey,mc21_task_fact.getIsemployee());

			// 插入反馈基本信息表(动态查询不插入,在task2插入动态轮询任务)
		/*	Br24_bas_info br24_bas_info = new Br24_bas_info();
			br24_bas_info.setApplicationID(query.getApplicationID());
			br24_bas_info.setTo(query.getMessageFrom());
			br24_bas_info.setTxCode(query.getTxCode());
			br24_bas_info.setTransSerialNumber(mc21_task_fact.getBdhm());
			q_mainBo.insertBr24_bas_info(br24_bas_info);
*/
			// 插入附件表
			q_mainBo.insertBr24_q_attach(mc21_task_fact.getBdhm(),taskmag);
			
			//插入归属机构的task2任务
			mc21_task_fact.setFacttablename(query.getAccountName()+"#"+taskmag.getPacketkey());
		    q_mainBo.insertOrgMc21TaskFact2(mc21_task_fact,"1",query.getSubjectType(),query.getCardNumber());

			// 插入task2
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