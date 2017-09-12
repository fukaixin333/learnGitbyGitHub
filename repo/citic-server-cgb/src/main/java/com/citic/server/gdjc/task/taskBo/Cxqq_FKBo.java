package com.citic.server.gdjc.task.taskBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjc.GdjcConstants;
import com.citic.server.gdjc.domain.Br50_cxqq;
import com.citic.server.gdjc.domain.Br50_cxqq_back;
import com.citic.server.gdjc.domain.Br50_cxqq_mx;
import com.citic.server.gdjc.domain.Br52_receipt;
import com.citic.server.gdjc.domain.Gdjc_Response;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Acc;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Bank;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Case;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Respondent;
import com.citic.server.gdjc.domain.request.Gdjc_RequestLsdj_Trans;
import com.citic.server.gdjc.mapper.MM50_cxqqMapper;
import com.citic.server.gdjc.service.RequestMessageServiceGdjc;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;

public class Cxqq_FKBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_FKBo.class);

	private MM50_cxqqMapper br50_cxqqMapper;
	private RequestMessageServiceGdjc requestSend;

	public Cxqq_FKBo(ApplicationContext ac) {
		super(ac);
		this.br50_cxqqMapper = (MM50_cxqqMapper) ac.getBean("MM50_cxqqMapper");
		this.requestSend = (RequestMessageServiceGdjc) ac.getBean("requestMessageServiceGdjc");
	}

	/**
	 * 反馈查询任务_存款查询
	 * 
	 * @param mc21_task_fact
	 * @param dataSource
	 * @throws Exception
	 */
	public void FeedBackQuery01(MC21_task_fact mc21_task_fact) throws Exception {
		Br50_cxqq br50_cxqq = new Br50_cxqq();
		br50_cxqq.setDocno(mc21_task_fact.getBdhm());
		List<Br50_cxqq> cxqqList = br50_cxqqMapper.getBr50_cxqqList(mc21_task_fact.getBdhm());
		// 1.获取查询任务数据
		Gdjc_RequestCkdj request01 = new Gdjc_RequestCkdj();
		request01.setDocno(br50_cxqq.getDocno());

		List<Gdjc_RequestCkdj_Case> casesList = new ArrayList<Gdjc_RequestCkdj_Case>();
		for (Br50_cxqq cxqq : cxqqList) {
			br50_cxqq.setCaseno(cxqq.getCaseno());
			List<Br50_cxqq_mx> mxList = br50_cxqqMapper.getBr50_cxqq_mxList(br50_cxqq);
			List<Gdjc_RequestCkdj_Respondent> respondents = new ArrayList<Gdjc_RequestCkdj_Respondent>();
			Gdjc_RequestCkdj_Case casedto = new Gdjc_RequestCkdj_Case();
			LinkedHashMap<String, List<Gdjc_RequestCkdj_Acc>> accMap = getBr50_cxqq_back_acctListMap(cxqq);
			for (Br50_cxqq_mx mx : mxList) {
				List<Gdjc_RequestCkdj_Acc> accList = accMap.get(mx.getUniqueid());
				// 开户行
				List<Gdjc_RequestCkdj_Bank> banks = new ArrayList<Gdjc_RequestCkdj_Bank>();
				Gdjc_RequestCkdj_Bank bank = new Gdjc_RequestCkdj_Bank();
				bank.setBankcode(GdjcConstants.BANK_ID);
				bank.setBankname(GdjcConstants.BANK_NAME);
				if (accList != null && accList.size() != 0) {
					bank.setAccs(accList);// 账户
					banks.add(bank);
				}
				// 被调查人
				Gdjc_RequestCkdj_Respondent respondent = new Gdjc_RequestCkdj_Respondent();
				if (banks.size() != 0) {
					respondent.setUniqueid(mx.getUniqueid());
					respondent.setBanks(banks);
					// 案件
					respondents.add(respondent);
				}
			}
			// 案件
			if (respondents.size() != 0) {
				casedto.setCaseno(cxqq.getCaseno());
				casedto.setRespondents(respondents);
				casesList.add(casedto);
			}
		}

		if (casesList.size() != 0) {
			request01.setCases(casesList);
		}

		// 2.调用反馈接口
		Gdjc_Response response = requestSend.sendCkdjMassage(request01);

		insertBr52_receipt(response, br50_cxqq.getDocno());

		// 3.置反馈表状态
		Br50_cxqq_back br50_cxqq_back = new Br50_cxqq_back();
		br50_cxqq_back.setStatus("2"); // 2.已反馈
		br50_cxqq_back.setDocno(br50_cxqq.getDocno());
		br50_cxqqMapper.updateBr50_cxqq_back(br50_cxqq_back);
        //4.修改申请表
		this.updateBr50_cxqqByGdjc_Response(response,br50_cxqq);
	}

	/**
	 * 反馈查询任务_交易流水查询
	 * 
	 * @param mc21_task_fact
	 * @param dataSource
	 * @throws Exception
	 */
	public void FeedBackQuery02(MC21_task_fact mc21_task_fact) throws Exception {
		Br50_cxqq br50_cxqq = new Br50_cxqq();
		br50_cxqq.setDocno(mc21_task_fact.getBdhm());

		// 1.获取查询任务数据
		List<Gdjc_RequestLsdj_Trans> tranList = br50_cxqqMapper.getBr50_cxqq_back_transListByDocno(br50_cxqq.getDocno());
		int trancount = tranList.size();
		// 2.调用反馈接口
		Gdjc_Response response = requestSend.sendJylsdjMassage(tranList, mc21_task_fact.getBdhm());
		if (tranList.size() > 0) {
			insertBr52_receipt(response, br50_cxqq.getDocno());
		}

		// 3.置反馈表状态
		Br50_cxqq_back br50_cxqq_back = new Br50_cxqq_back();
		if (trancount == 0) {
			br50_cxqq_back.setCzsbyy("查询流水无记录");
		}
		br50_cxqq_back.setStatus("2"); // 2.已反馈
		br50_cxqq_back.setDocno(br50_cxqq.getDocno());
		br50_cxqqMapper.updateBr50_cxqq_back(br50_cxqq_back);
		
		//4.修改申请表
		this.updateBr50_cxqqByGdjc_Response(response,br50_cxqq);
	}

	public void insertBr52_receipt(Gdjc_Response response, String docno) {
		Br52_receipt receipt = new Br52_receipt();
		try {
			receipt.setReceiptkey(this.geSequenceNumber("SEQ_BR52_RECEIPT"));
			receipt.setDocno(docno);
			if (response.getCmdtype().equals(GdjcConstants.DATA_TYPE_ERR)) {
				receipt.setReceipt_status_cd(response.getCmdstatus());
				receipt.setResultinfo("登录异常" + response.getCmdmessage());
			} else {
				receipt.setReceipt_status_cd(response.getDatatype());
				receipt.setResultinfo(response.getErrmessage());
			}
			br50_cxqqMapper.insertBr52_receipt(receipt);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	LinkedHashMap<String, List<Gdjc_RequestCkdj_Acc>> getBr50_cxqq_back_acctListMap(Br50_cxqq cxqq) {

		LinkedHashMap<String, List<Gdjc_RequestCkdj_Acc>> accMap = new LinkedHashMap<String, List<Gdjc_RequestCkdj_Acc>>();
		try {
			List<Gdjc_RequestCkdj_Acc> accList = br50_cxqqMapper.getBr50_cxqq_back_acctList1(cxqq);
			for (Gdjc_RequestCkdj_Acc acc : accList) {
				//将金额空值进行处理
				String banlance = acc.getBanlance();
				if(banlance==null||banlance.equals("")){
					acc.setBanlance("0.00");
				}
				String uniqueid = acc.getUniqueid();
				if (accMap.containsKey(uniqueid)) {
					accMap.get(uniqueid).add(acc);
				} else {
					List<Gdjc_RequestCkdj_Acc> subList = new ArrayList<Gdjc_RequestCkdj_Acc>();
					subList.add(acc);
					accMap.put(uniqueid, subList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accMap;

	}

	public HashMap<String, String> getNewUniqueidHash(Br50_cxqq cxqq) throws Exception {
		HashMap<String, String> uniqueidMap = new HashMap<String, String>();
		List<Gdjc_RequestCkdj_Acc> uniqueidList = br50_cxqqMapper.getBr50_cxqq_back_acctMap(cxqq);
		for (Gdjc_RequestCkdj_Acc acc : uniqueidList) {
			uniqueidMap.put(acc.getUniqueid(), "");
		}
		return uniqueidMap;
	}

	/**
	 * 修改请求表状态
	 * @param response
	 * @param br50_cxqq
	 */
	public void updateBr50_cxqqByGdjc_Response(Gdjc_Response response,Br50_cxqq br50_cxqq) {
		String status = "3";
		if(response==null||response.getCmdtype().equals(GdjcConstants.DATA_TYPE_ERR)){
			status = "4";
		}
		br50_cxqq.setStatus(status);//0：待处理 1：待生成报文  3：反馈成功 4：反馈失败
		br50_cxqq.setLast_up_dt(Utility.currDateTime19());
		br50_cxqqMapper.updateBr50_cxqq(br50_cxqq);
	}
}
