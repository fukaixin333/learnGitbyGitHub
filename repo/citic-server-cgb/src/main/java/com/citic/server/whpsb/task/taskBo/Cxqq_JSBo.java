package com.citic.server.whpsb.task.taskBo;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;
import com.citic.server.whpsb.domain.Br51_cxqq;
import com.citic.server.whpsb.domain.Br51_cxqq_back;
import com.citic.server.whpsb.domain.Br51_cxqq_mx;
import com.citic.server.whpsb.domain.Whpsb_Header;
import com.citic.server.whpsb.domain.Whpsb_SadxBody;
import com.citic.server.whpsb.domain.response.Whpsb_Response;
import com.citic.server.whpsb.mapper.BR51_cxqqMapper;

/**
 * 常规查询
 * 
 * @author dk
 */
public class Cxqq_JSBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_JSBo.class);
	
	private BR51_cxqqMapper br51_cxqqMapper;
	
	public Cxqq_JSBo(ApplicationContext ac) {
		super(ac);
		br51_cxqqMapper = (BR51_cxqqMapper) ac.getBean("BR51_cxqqMapper");
		
	}
	
	/**
	 * 删除请求任务：查询请求内容、查询请求信息及请求反馈信息
	 * 
	 * @param br51_cxqq 查询请求内容
	 * @throws Exception
	 */
	public void delQureyResponse(String msgseq) throws Exception {
		//1.删除査询请求内容表
		br51_cxqqMapper.delBr51_cxqq(msgseq);
		//2.删除请求单任务信息表
		br51_cxqqMapper.delBr51_cxqq_mx(msgseq);
		//3.删除请求反馈信息表
		br51_cxqqMapper.delBr51_cxqq_back(msgseq);
	}
	
	/**
	 * 处理文件
	 * 
	 * @param query_cgcx
	 * @param isemployee
	 * @throws Exception
	 */
	public void handleCxqqBw_Ckcx(Whpsb_Response query_cx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		String msgname = taskmag.getMsgname();
		String code = taskmag.getCode();
		int index = msgname.lastIndexOf(".");
		String filename = msgname;
		if (index > 0) {
			filename = msgname.substring(0, index);
		}
		String packetname = taskmag.getPacketkey();
		String packetkey = packetname;
		index = packetkey.lastIndexOf(".");
		if (index > 0) {
			packetkey = packetkey.substring(0, index);
		}
		List<Whpsb_SadxBody> sadxList = query_cx.getSadxList();
		Whpsb_Header header = query_cx.getHeader();
		String orgkey = mc21_task_fact.getTaskobj();//归属机构
		
		//1.插入请求基本信息
		String curr_date = Utility.currDateTime19();
		Br51_cxqq br51_cxqq = new Br51_cxqq();
		br51_cxqq.setCount(header.getCount());
		br51_cxqq.setYhdm(header.getYhdm());
		String czsj = "";
		try {
			czsj = Utility.toDateTime19(header.getCzsj());
		} catch (Exception e) {
			logger.error("===[武汉公安查询反馈]操作时间转换失败===");
			e.printStackTrace();
		}
		br51_cxqq.setCzsj(czsj);//将时间转换为19位
		br51_cxqq.setMsgseq(filename);
		br51_cxqq.setRecipient_time(curr_date);
		br51_cxqq.setStatus("0");//0：待处理 1：待生成报文 2：待打包反馈 3：反馈成功 4：反馈失败
		br51_cxqq.setOrgkey(orgkey);
		br51_cxqq.setQrydt(DtUtils.getNowDate());
		br51_cxqq.setLast_up_dt(curr_date);
		br51_cxqq.setPacketkey(packetkey);
		
		br51_cxqqMapper.insertBr51_cxqq(br51_cxqq);
		
		//2.插入请求主体信息、反馈主体信息
		int n = 1;
		for (Whpsb_SadxBody sadx : sadxList) {
			//2.1 插入请求主体信息
			Br51_cxqq_mx qqmx = new Br51_cxqq_mx();
			BeanUtils.copyProperties(qqmx, sadx);
			String bdhm = filename + "_" + (n++);
			qqmx.setMsgseq(filename);
			qqmx.setBdhm(bdhm);
			qqmx.setQrydt(DtUtils.getNowDate());
			qqmx.setOrgkey(orgkey);
			qqmx.setQrydt(DtUtils.getNowDate());
			String partyclass_cd = "I";
			if (code.length() > 2) {
				qqmx.setQrymode(code);
				String lx = filename.substring(5, 7);
				if (lx.equals("dw")) {
					partyclass_cd = "C";
				}
			}
			qqmx.setParty_class_cd(partyclass_cd);
			
			br51_cxqqMapper.insertBr51_cxqq_mx(qqmx);
			
			//2.2 插入反馈主体信息
			Br51_cxqq_back br51_cxqq_back = new Br51_cxqq_back();
			br51_cxqq_back.setBdhm(bdhm);
			br51_cxqq_back.setMsgseq(filename);
			br51_cxqq_back.setAh(qqmx.getAh());
			br51_cxqq_back.setQrydt(DtUtils.getNowDate());
			br51_cxqq_back.setStatus("0");
			br51_cxqq_back.setLast_up_dt(DtUtils.getNowTime());
			br51_cxqq_back.setOrgkey(orgkey);
			br51_cxqq_back.setMsgcheckresult("1");
			br51_cxqqMapper.insertBr51_cxqq_back(br51_cxqq_back);
			
			//2.4 插入task2
			mc21_task_fact.setBdhm(bdhm);
			mc21_task_fact.setTaskobj("");
			this.insertMc21TaskFact2(mc21_task_fact, "whpsb");
		}
		//2.5插入附件（文书证件)
		br51_cxqq.setPacketname(packetname);
		br51_cxqqMapper.insertBr51_attach(br51_cxqq);
		
	}
	
}
