package com.citic.server.cbrc.task;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.cbrc.domain.Br40_cxqq;
import com.citic.server.cbrc.domain.Br40_cxqq_back_pz_attach;
import com.citic.server.cbrc.domain.Br42_packet;
import com.citic.server.cbrc.domain.CBRC_ReturnReceipt;
import com.citic.server.cbrc.mapper.MM40_cxqq_cbrcMapper;
import com.citic.server.cbrc.mapper.MM41_kzqq_cbrcMapper;
import com.citic.server.cbrc.service.RequestMessageServiceCBRC;
import com.citic.server.cbrc.task.taskBo.PacketBo;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.Utility;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;

/**
 * 凭证生成数据包
 * 
 * @author hxj
 * @version 1.0
 */

public class TK_ESYJ_FK08 extends NBaseTask {
	private RequestMessageServiceCBRC service;
	
	private CacheService cacheService;
	public TK_ESYJ_FK08(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		service = SpringContextHolder.getBean("requestMessageServiceCBRC");
		cacheService = (CacheService) ac.getBean("cacheService");
	}
	
	public boolean calTask() throws Exception {
		MM40_cxqq_cbrcMapper mm40_cxqq_cbrcMapper = (MM40_cxqq_cbrcMapper) this.getAc().getBean("MM40_cxqq_cbrcMapper");
		MC21_task_fact mc21_task_fact = this.getMC21_task_fact();
		String bdhm = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
		
		PacketBo packetBo = new PacketBo(this.getAc());
		//查询凭证文件
	      HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);		
		String root = StrUtils.null2String((String) sysParaMap.get("2"));
		Br40_cxqq br40_cxqq=new Br40_cxqq();
		br40_cxqq.setQqdbs(bdhm);
		br40_cxqq.setTasktype(mc21_task_fact.getTasktype());
		 List<Br40_cxqq_back_pz_attach> pzacctahList=	mm40_cxqq_cbrcMapper.selBr40_cxqq_back_pzAttachList(br40_cxqq);
		ArrayList<File> xmlFileList = new ArrayList<File>(); 
		if(pzacctahList!=null){
			
		   for(Br40_cxqq_back_pz_attach  pzacctah:pzacctahList){
			 String filepath=pzacctah.getFilepath();
			 xmlFileList.add(new File(root+filepath));
		   }
		}
		Br42_packet packet = packetBo.makePacket(mc21_task_fact,xmlFileList);
		
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

		
		return true;
	}
	
}