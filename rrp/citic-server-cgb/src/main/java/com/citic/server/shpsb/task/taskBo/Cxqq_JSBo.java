package com.citic.server.shpsb.task.taskBo;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;

import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.shpsb.ShpsbCode;
import com.citic.server.shpsb.domain.Br54_cxqq;
import com.citic.server.shpsb.domain.Br54_cxqq_back;
import com.citic.server.shpsb.domain.ShpsbSadx;
import com.citic.server.shpsb.domain.ShpsbSjl;
import com.citic.server.shpsb.domain.response.ShpsbResponse;
import com.citic.server.shpsb.mapper.BR54_cxqqMapper;
import com.citic.server.utils.DtUtils;

/**
 * 常规查询
 * 
 * @author dk
 */
public class Cxqq_JSBo extends BaseBo {
	// private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_JSBo.class);
	
	private BR54_cxqqMapper br54_cxqqMapper;
	
	public Cxqq_JSBo(ApplicationContext ac) {
		super(ac);
		br54_cxqqMapper = (BR54_cxqqMapper) ac.getBean("BR54_cxqqMapper");
	}
	
	/**
	 * 删除请求任务：查询请求内容、查询请求信息及请求反馈信息
	 * 
	 * @param br51_cxqq 查询请求内容
	 * @throws Exception
	 */
	public void delQureyResponse(String msgseq) throws Exception {
		//1.删除査询请求内容表
		br54_cxqqMapper.delBr54_cxqq(msgseq);
		//2.删除请求单任务信息表
		br54_cxqqMapper.delBr54_cxqq_mx(msgseq);
		//3.删除请求反馈信息表
		br54_cxqqMapper.delBr54_cxqq_back(msgseq);
	}
	
	/**
	 * 处理文件
	 * 
	 * @param query_cgcx
	 * @param isemployee
	 * @throws Exception
	 */
	public void handleCxqqBw_Ckcx(ShpsbResponse query_cx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
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
		List<ShpsbSadx> sadxList = query_cx.getSadxList();
		ShpsbSjl header = query_cx.getSjl();
		String orgkey = mc21_task_fact.getTaskobj();//归属机构
		
		//1.插入请求基本信息
		String curr_date = Utility.currDateTime19();
		Br54_cxqq br54_cxqq = new Br54_cxqq();
		br54_cxqq.setCount(header.getCount());
		br54_cxqq.setYhdm(header.getYhdm());
		br54_cxqq.setCzsj(header.getCzsj()); // 
		br54_cxqq.setMsgseq(filename);
		br54_cxqq.setRecipient_time(curr_date);
		br54_cxqq.setStatus("0");//0：待处理 1：待生成报文 2：待打包反馈 3：反馈成功 4：反馈失败
		br54_cxqq.setOrgkey(orgkey);
		br54_cxqq.setQrydt(DtUtils.getNowDate());
		br54_cxqq.setLast_up_dt(curr_date);
		br54_cxqq.setPacketkey(packetkey);
		
		br54_cxqqMapper.insertBr54_cxqq(br54_cxqq);
		
		//2.插入请求主体信息、反馈主体信息
		int n = 1;
		for (ShpsbSadx sadx : sadxList) {
			//2.1 插入请求主体信息
			ShpsbSadx qqmx = new ShpsbSadx();
			BeanUtils.copyProperties(qqmx, sadx);
			String bdhm = filename + "_" + (n++);
			qqmx.setMsgseq(filename);
			qqmx.setBdhm(bdhm);
			qqmx.setQrydt(DtUtils.getNowDate());
			qqmx.setOrgkey(orgkey);
			qqmx.setQrydt(DtUtils.getNowDate());
			qqmx.setQrymode(code);
			// 对公 or 个人
			String partyclass_cd = "C";
			if (ShpsbCode.enumOf(code).isPersonal()) {
				partyclass_cd = "I";
			}
			qqmx.setParty_class_cd(partyclass_cd);
			
			br54_cxqqMapper.insertBr54_cxqq_mx(qqmx);
			
			//2.2 插入反馈主体信息
			Br54_cxqq_back br54_cxqq_back = new Br54_cxqq_back();
			br54_cxqq_back.setBdhm(bdhm);
			br54_cxqq_back.setMsgseq(filename);
			br54_cxqq_back.setAh(qqmx.getAh());
			br54_cxqq_back.setQrydt(DtUtils.getNowDate());
			br54_cxqq_back.setStatus("0");
			br54_cxqq_back.setLast_up_dt(DtUtils.getNowTime());
			br54_cxqq_back.setOrgkey(orgkey);
			br54_cxqq_back.setMsgcheckresult("1");
			br54_cxqqMapper.insertBr54_cxqq_back(br54_cxqq_back);
			
			//2.4 插入task2
			mc21_task_fact.setBdhm(bdhm);
			mc21_task_fact.setTaskobj("");
			this.insertMc21TaskFact2(mc21_task_fact, "shpsb");
		}
		//2.5插入附件（文书证件)
		br54_cxqq.setPacketname(packetname);
		// br54_cxqqMapper.insertBr54_attach(br54_cxqq);
	}
}
