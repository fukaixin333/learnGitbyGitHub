package com.citic.server.cbrc.task;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.cbrc.domain.Br42_packet;
import com.citic.server.cbrc.domain.CBRC_ReturnReceipt;
import com.citic.server.cbrc.mapper.MM40_cxqq_cbrcMapper;
import com.citic.server.cbrc.mapper.MM41_kzqq_cbrcMapper;
import com.citic.server.cbrc.service.RequestMessageServiceCBRC;
import com.citic.server.cbrc.task.taskBo.PacketBo;
import com.citic.server.runtime.Utility;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;

/**
 * 生成数据包
 * 
 * @author hxj
 * @version 1.0
 */

public class TK_ESYJ_FK06 extends NBaseTask {
	private RequestMessageServiceCBRC service;
	
	public TK_ESYJ_FK06(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		service = SpringContextHolder.getBean("requestMessageServiceCBRC");
	}
	
	public boolean calTask() throws Exception {
		MM40_cxqq_cbrcMapper mm40_cxqq_cbrcMapper = (MM40_cxqq_cbrcMapper) this.getAc().getBean("MM40_cxqq_cbrcMapper");
		MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
		String bdhm = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
		
		PacketBo packetBo = new PacketBo(this.getAc());
		Br42_packet packet = packetBo.makePacket(mc21_task_fact, null);
		
		CBRC_ReturnReceipt returnReceipt = service.sendZipPackage(packet);
		if (returnReceipt == null) {
			returnReceipt = new CBRC_ReturnReceipt();
			returnReceipt.setQqdbs(bdhm); // 请求单标识
			returnReceipt.setHzdm(CBRCConstants.REC_CODE_99999); // 回执代码
			returnReceipt.setJssj(Utility.currDateTime19()); // 接收时间
			returnReceipt.setHzsm("未知错误"); // 回执说明
			returnReceipt.setTasktype(mc21_task_fact.getTasktype()); // 任务类型
		} else {
			returnReceipt.setQqdbs(bdhm); // 请求单标识
			returnReceipt.setTasktype(mc21_task_fact.getTasktype()); // 任务类型
		}
		
		mm40_cxqq_cbrcMapper.delBr40_receipt(returnReceipt);
		mm40_cxqq_cbrcMapper.insertBr40_receipt(returnReceipt);
		// 查询类
		mm40_cxqq_cbrcMapper.updateBr40_cxqqStatus(returnReceipt); // 已打包
		// 控制类
		MM41_kzqq_cbrcMapper mm41Mapper = (MM41_kzqq_cbrcMapper) SpringContextHolder.getBean("MM41_kzqq_cbrcMapper");
		mm41Mapper.updateBr41_kzqqStatus(returnReceipt); // 已打包
		
		return true;
	}
	
}