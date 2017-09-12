package com.citic.server.gf.outer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citic.server.gf.domain.MC20_GZZ;
import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.gf.domain.response.CertificateResponse_ZjInfo;
import com.citic.server.gf.domain.response.ControlResponse_Kzqq;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;
import com.citic.server.gf.domain.response.WritResponse_WsInfo;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;

@Service("outerPollingService1A")
public class OuterPollingService1A implements Constants {
	
	@Autowired
	private PollingTaskMapper pollingTaskMapper;
	
	@Transactional
	public void insertCxqqDataTransaction(MC21_task taskInfo, QueryResponse_Cxqq cxqq, List<WritResponse_WsInfo> wsInfoList, List<CertificateResponse_ZjInfo> zjInfoList) {
		String currDateTime = Utility.currDateTime19();
		
		MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
		taskFact.setTaskKey(cxqq.getTaskKey()); // 主键
		taskFact.setTaskID(taskInfo.getTaskID()); // 任务编码 
		taskFact.setSubTaskID("CXQQ"); // 子任务编码（交易编码）
		taskFact.setTaskType(TASK_TYPE_GF);
		taskFact.setDatatime(currDateTime); // 数据时间
		taskFact.setServerID(taskInfo.getServerID()); // 计算服务编码
		taskFact.setBdhm(cxqq.getBdhm()); // 任务单编号
		taskFact.setTaskObj(cxqq.getOrgkey()); // 任务对象 
		taskFact.setTaskName(taskInfo.getTaskName()); // 任务名称
		taskFact.setTaskCMD(taskInfo.getTaskCMD()); // 任务执行入口
		taskFact.setIsDYNA(taskInfo.getIsDYNA()); // 是否动态任务
		
		MC20_task_msg taskMessage = new MC20_task_msg();
		taskMessage.setTaskkey(cxqq.getTaskKey());
		taskMessage.setCode("CXQQ");
		taskMessage.setBdhm(cxqq.getBdhm());
		taskMessage.setMsgpath(cxqq.getMsgPath());
		taskMessage.setMsgname(cxqq.getMsgName());
		taskMessage.setCreatedt(currDateTime);
		taskMessage.setPacketkey(cxqq.getPacketkey());
		
		insertDocumentInfo(wsInfoList, zjInfoList, cxqq.getBdhm(), false); // 查询请求的法律文书并不对应唯一的请求单标识
		pollingTaskMapper.insertMC20_Task_Msg(taskMessage);
		pollingTaskMapper.insertMC20_Task_Fact1(taskFact);
	}
	
	@Transactional
	public void insertKzqqDataTransaction(MC21_task taskInfo, ControlResponse_Kzqq kzqq, List<WritResponse_WsInfo> wsInfoList, List<CertificateResponse_ZjInfo> zjInfoList) {
		String currDateTime = Utility.currDateTime19();
		String bdhm = kzqq.getBdhm();
		MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
		taskFact.setTaskKey(kzqq.getTaskKey()); // 主键
		taskFact.setTaskID(taskInfo.getTaskID()); // 任务编码 
		taskFact.setSubTaskID("KZQQ"); // 子任务编码（交易编码）
		taskFact.setTaskType(TASK_TYPE_GF);
		taskFact.setDatatime(currDateTime); // 数据时间
		taskFact.setServerID(taskInfo.getServerID()); // 计算服务编码
		taskFact.setBdhm(bdhm); // 任务单编号
		taskFact.setTaskObj(kzqq.getOrgkey()); // 任务对象 
		taskFact.setTaskName(taskInfo.getTaskName()); // 任务名称
		taskFact.setTaskCMD(taskInfo.getTaskCMD()); // 任务执行入口
		taskFact.setIsDYNA(taskInfo.getIsDYNA()); // 是否动态任务
		
		MC20_task_msg taskMessage = new MC20_task_msg();
		taskMessage.setTaskkey(kzqq.getTaskKey());
		taskMessage.setCode("KZQQ");
		taskMessage.setBdhm(bdhm);
		taskMessage.setMsgpath(kzqq.getMsgPath());
		taskMessage.setMsgname(kzqq.getMsgName());
		taskMessage.setCreatedt(currDateTime);
		taskMessage.setPacketkey(kzqq.getPacketkey());
		
		insertDocumentInfo(wsInfoList, zjInfoList, bdhm, true);
		pollingTaskMapper.insertMC20_Task_Msg(taskMessage);
		pollingTaskMapper.insertMC20_Task_Fact1(taskFact);
	}
	
	private void insertDocumentInfo(List<WritResponse_WsInfo> wsInfoList, List<CertificateResponse_ZjInfo> zjInfoList, String bdhm, boolean bool) {
		if (wsInfoList != null && wsInfoList.size() > 0) {
			List<MC20_WS> wsList = new ArrayList<MC20_WS>(wsInfoList.size());
			for (WritResponse_WsInfo wsInfo : wsInfoList) {
				MC20_WS ws = new MC20_WS();
				if (bool) {
					ws.setBdhm(bdhm);
				}
				ws.setWsbh(wsInfo.getWsbh());
				ws.setXh(wsInfo.getXh());
				ws.setWjmc(wsInfo.getWjmc());
				ws.setWjlx(wsInfo.getWjlx());
				ws.setWslb(wsInfo.getWslb());
				ws.setMd5(wsInfo.getMd5());
				ws.setWspath(wsInfo.getWspath());
				ws.setTasktype(TASK_TYPE_GF);
				wsList.add(ws);
			}
			pollingTaskMapper.insertMC20_WSList(wsList);
		}
		
		if (zjInfoList != null && zjInfoList.size() > 0) {
			List<MC20_GZZ> gzzList = new ArrayList<MC20_GZZ>(zjInfoList.size());
			for (CertificateResponse_ZjInfo zjInfo : zjInfoList) {
				MC20_GZZ gzz = new MC20_GZZ();
				gzz.setBdhm(bdhm);
				gzz.setXh(zjInfo.getXh());
				gzz.setZjmc(zjInfo.getZjmc());
				if (zjInfo.getGwzpath() == null || zjInfo.getGwzpath().length() == 0) {
					gzz.setGzzbm(zjInfo.getGzzbm());
					gzz.setGzzwjgs(zjInfo.getGzzwjgs());
					gzz.setGzzpath(zjInfo.getGzzpath());
				} else {
					gzz.setGwzbm(zjInfo.getGwzbm());
					gzz.setGwzwjgs(zjInfo.getGwzwjgs());
					gzz.setGwzpath(zjInfo.getGwzpath());
				}
				gzz.setTasktype(TASK_TYPE_GF);
				gzzList.add(gzz);
			}
			pollingTaskMapper.insertMC20_GZZList(gzzList);
		}
	}
}
