package com.citic.server.dx.outer.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.jibx.runtime.JiBXException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citic.server.dx.domain.ResponseMessage;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.utils.CommonUtils;

/**
 * @author Liu Xuanfei
 * @date 2016年4月8日 上午11:12:24
 */
@Service("outerPollingService2")
public class OuterPollingService2 {
	@Autowired
	protected PollingTaskMapper service;
	
	@Autowired
	@Qualifier("cacheService")
	private CacheService cacheService;
	
	@SuppressWarnings("unchecked")
	public void parseResponseMessage(String taskKey, ResponseMessage message, MC21_task task, String bankID) throws FileNotFoundException, JiBXException {
		// 内部机构编码
		String organKey = "";
		if (StringUtils.isNotBlank(bankID)) {
			HashMap<String, MP02_rep_org_map> repOrgMap = (HashMap<String, MP02_rep_org_map>) cacheService.getCache("Mp02_repOrgDetail", HashMap.class);
			MP02_rep_org_map repOrgObj = repOrgMap.get("2"+bankID);
			if (repOrgObj != null) {
				organKey = repOrgObj.getHorgankey();
			}
		}
		
		// String from = message.getMessageFrom();
		String txCode = message.getTxCode();
		String transSerialNumber = message.getTransSerialNumber();
		
		// 物理存储请求报文
		String rootpath = ServerEnvironment.getFileRootPath();
		String temppath = CommonUtils.createRelativePath(Keys.FILE_PATH_TEMP, Keys.FILE_DIRECTORY_DX);
		String xmlname = "M" + taskKey + ".xml";
		CommonUtils.marshallDocument(message, rootpath + temppath, xmlname, "UTF-8");
		
		// 数据库操作
		String currDateTime = Utility.currDateTime19();
		
		MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
		taskFact.setTaskKey(taskKey);
		taskFact.setSubTaskID(txCode);
		// taskFact.setBdhm(from + "_" + transSerialNumber); //
		taskFact.setBdhm(transSerialNumber);
		taskFact.setTaskID(task.getTaskID());
		taskFact.setTaskType(task.getTaskType());
		taskFact.setTaskName(task.getTaskName());
		taskFact.setTaskCMD(task.getTaskCMD());
		taskFact.setIsDYNA(task.getIsDYNA());
		taskFact.setDatatime(currDateTime);
		taskFact.setTaskObj(organKey == null ? "" : organKey);
		
		MC20_task_msg taskMessage = new MC20_task_msg();
		taskMessage.setTaskkey(taskKey);
		taskMessage.setCode(txCode);
		// taskMessage.setBdhm(from + "_" + transSerialNumber); //
		taskMessage.setBdhm(transSerialNumber);
		taskMessage.setMsgpath(temppath + File.separator + xmlname); // 
		taskMessage.setAttachpath(message.getAttachpaths());
		taskMessage.setAttachname(message.getAttachnames());
		taskMessage.setCreatedt(currDateTime);
		taskMessage.setPacketkey(bankID); // 
		
		insertTask1(taskFact, taskMessage);
	}
	
	@Transactional
	public void insertTask1(MC20_Task_Fact1 taskFact, MC20_task_msg taskMessage) {
		service.insertMC20_Task_Fact1(taskFact);
		service.insertMC20_Task_Msg(taskMessage);
	}
}
