package com.citic.server.gdjg.task.taskBo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Br57_cxqq;
import com.citic.server.gdjg.domain.Br57_cxqq_back;
import com.citic.server.gdjg.domain.Br57_cxqq_mx;
import com.citic.server.gdjg.domain.Br57_cxqq_mx_policeman;
import com.citic.server.gdjg.domain.Br57_receipt;
import com.citic.server.gdjg.domain.Gdjg_Response;
import com.citic.server.gdjg.domain.request.Gdjg_RequestCkdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDtcxdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDtcxhz;
import com.citic.server.gdjg.domain.request.Gdjg_RequestJrcpdj;
import com.citic.server.gdjg.domain.request.Gdjg_Request_AccCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_BankCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_CaseCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_FinancialProductsCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_FroInfoCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_RespondentCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_TransCx;
import com.citic.server.gdjg.mapper.MM57_cxqqMapper;
import com.citic.server.gdjg.service.RequestMessageServiceGdjg;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

/**
 * 广东省检察院
 * 
 * @author liuYing
 * @date 2017年5月25日 下午9:56:46
 */
public class Cxqq_FKBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_FKBo.class);
	
	private MM57_cxqqMapper br57_cxqqMapper;
	private RequestMessageServiceGdjg requestSend;
	
	public Cxqq_FKBo(ApplicationContext ac) {
		super(ac);
		this.br57_cxqqMapper = (MM57_cxqqMapper) ac.getBean("MM57_cxqqMapper");
		this.requestSend = (RequestMessageServiceGdjg) ac.getBean("requestMessageServiceGdjg");
	}
	
	/**
	 * 反馈查询任务_存款查询
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 * @author liuxuanfei
	 * @throws Exception
	 * @date 2017年5月31日 下午2:46:02
	 */
	public void FeedBackQuery01(MC21_task_fact mc21_task_fact) throws Exception {
		Br57_cxqq br57_cxqq = new Br57_cxqq();
		br57_cxqq.setDocno(mc21_task_fact.getBdhm());
		logger.info("【存款查询】获取Br57_cxqq信息[{}]", mc21_task_fact.getBdhm());
		List<Br57_cxqq> cxqqList = br57_cxqqMapper.getBr57_cxqqList(mc21_task_fact.getBdhm());
		// 1.获取查询任务数据
		Gdjg_RequestCkdj request01 = new Gdjg_RequestCkdj();
		request01.setDocno(br57_cxqq.getDocno());
		List<Gdjg_Request_CaseCx> casesList = new ArrayList<Gdjg_Request_CaseCx>();
		
		//2.判断是否查询过交易流水信息（若是则调用交易流水登记）
		Br57_cxqq br57_cxqq_copy = br57_cxqq;
		List<Br57_cxqq> cxqqList_copy = cxqqList;
		MC21_task_fact mc21_task_fact_copy = mc21_task_fact;
		checkTransDo(br57_cxqq_copy, cxqqList_copy, mc21_task_fact_copy);
		
		//3.进行数据拼接，生成反馈报文
		for (Br57_cxqq cxqq : cxqqList) {
			br57_cxqq.setCaseno(cxqq.getCaseno());
			logger.info("【存款查询】获取Br57_cxqq_mx信息[{}]", request01.getDocno());
			List<Br57_cxqq_mx> mxList = br57_cxqqMapper.getBr57_cxqq_mxList(br57_cxqq);
			List<Gdjg_Request_RespondentCx> respondents = new ArrayList<Gdjg_Request_RespondentCx>();
			Gdjg_Request_CaseCx casedto = new Gdjg_Request_CaseCx();
			
			LinkedHashMap<String, List<Gdjg_Request_AccCx>> accMap = getBr57_cxqq_back_acctListMap(cxqq);
			for (Br57_cxqq_mx mx : mxList) {
				// 冻结产品信息
				List<Gdjg_Request_AccCx> accList = accMap.get(mx.getUniqueid());
				if (accList != null && accList.size() != 0) {
					for (Gdjg_Request_AccCx account : accList) {
						Br57_cxqq_mx ck_mx = new Br57_cxqq_mx();
						ck_mx.setUniqueid(account.getUniqueid());
						ck_mx.setCaseno(account.getCaseno());
						ck_mx.setDocno(account.getDocno());
						logger.info("【存款查询】获取Gdjg_Request_FroInfoCx信息[{}]", request01.getDocno());
						List<Gdjg_Request_FroInfoCx> froList = br57_cxqqMapper.getBr57_cxqq_froList(ck_mx);
						account.setFroinfos(froList);
					}
				}
				
				// 开户行
				List<Gdjg_Request_BankCx> banks = new ArrayList<Gdjg_Request_BankCx>();
				Gdjg_Request_BankCx bank = new Gdjg_Request_BankCx();
				bank.setBankcode(GdjgConstants.BANK_ID);
				bank.setBankname(GdjgConstants.BANK_NAME);
				if (accList != null && accList.size() != 0) {
					bank.setAccs(accList);// 账户
					banks.add(bank);
				} else {
					/*
					 * List<Gdjg_Request_AccCx> accList_byNull = new
					 * ArrayList<Gdjg_Request_AccCx>();
					 * Gdjg_Request_AccCx account = new Gdjg_Request_AccCx();
					 * account.setAccname(mx.getName());
					 * account.setAccount("-"); //mx.getAccount()
					 * account.setAcctype(mx.getAcctype());
					 * account.setId(mx.getId());
					 * account.setIdtype(mx.getIdtype());
					 * account.setType(mx.getType());
					 * account.setCurrency("-");
					 * account.setStatusflag("-");
					 * accList_byNull.add(account);
					 * bank.setAccs(accList_byNull);// 账户
					 * banks.add(bank);
					 */
				}
				// 被调查人
				logger.info("【存款查询】获取Br57_cxqq_back信息[{}]", request01.getDocno());
				Br57_cxqq_back cxqq_back = br57_cxqqMapper.getBr57_cxqq_back(mx);
				Gdjg_Request_RespondentCx respondent = new Gdjg_Request_RespondentCx();
				respondent.setUniqueid(mx.getUniqueid());
				respondent.setReason(cxqq_back.getCzsbyy());
				respondent.setQueryresult(cxqq_back.getCxfkjg());
				respondent.setQueryendtime(Utility.currDateTime14());
				respondent.setQuerytime("");
				respondent.setBanks(banks);
				respondents.add(respondent);
				
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
		logger.info(request01.toString());
		// 2.调用反馈接口
		logger.info("【存款查询】进行银行存款查询结果登记[{}]", request01.getDocno());
		Gdjg_Response response = requestSend.sendCkdjMassage(request01);
		insertBr57_receipt(response, br57_cxqq.getDocno());
		// 3.置反馈表状态
		Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
		br57_cxqq_back.setStatus("2"); // 2.已反馈
		br57_cxqq_back.setDocno(br57_cxqq.getDocno());
		logger.info("【存款查询】更新Br57_cxqq_back状态");
		br57_cxqqMapper.updateBr57_cxqq_back(br57_cxqq_back);
		//4.修改申请表
		this.updateBr57_cxqqByGdjg_Response(response, br57_cxqq);
	}
	
	/**
	 * 反馈查询任务_交易流水查询
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 * @author liuxuanfei
	 * @throws Exception
	 * @date 2017年5月31日 下午2:46:02
	 */
	public void FeedBackQuery02(MC21_task_fact mc21_task_fact) throws Exception {
		Br57_cxqq br57_cxqq = new Br57_cxqq();
		String docno = mc21_task_fact.getBdhm();
		br57_cxqq.setDocno(docno);
		List<Br57_cxqq_back> backList = new ArrayList<Br57_cxqq_back>();
		logger.info("【流水查询】获取Br57_cxqq_mx信息[{}]", docno);
		List<Br57_cxqq_mx> mxList = br57_cxqqMapper.getBr57_cxqq_mxList(br57_cxqq);
		for (Br57_cxqq_mx mx : mxList) {
			logger.info("【流水查询】获取Br57_cxqq_back信息[{}]", docno);
			Br57_cxqq_back cxqq_back = br57_cxqqMapper.getBr57_cxqq_back(mx);
			backList.add(cxqq_back);
		}
		
		// 1.获取查询任务数据
		logger.info("【流水查询】获取Gdjg_Request_TransCx信息[{}]", docno);
		List<Gdjg_Request_TransCx> tranList = br57_cxqqMapper.getBr57_cxqq_back_transListByDocno(br57_cxqq.getDocno());
		int trancount = tranList.size();
		// 2.调用反馈接口
		logger.info("【流水查询】进行交易流水查询结果登记[{}]", docno);
		Gdjg_Response response = requestSend.sendJylsdjMassage(tranList, backList);
		insertBr57_receipt(response, br57_cxqq.getDocno());
		
		// 3.置反馈表状态
		Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
		if (trancount == 0) {
			br57_cxqq_back.setCzsbyy("查询流水无记录");
		}
		br57_cxqq_back.setStatus("2"); // 2.已反馈
		br57_cxqq_back.setDocno(br57_cxqq.getDocno());
		logger.info("【流水查询】更新Br57_cxqq_back状态[{}]", docno);
		br57_cxqqMapper.updateBr57_cxqq_back(br57_cxqq_back);
		
		//4.修改申请表
		if (!(response == null)) {
			this.updateBr57_cxqqByGdjg_Response(response, br57_cxqq);
		}
	}
	
	/**
	 * 回执处理
	 * 
	 * @param response
	 * @param docno
	 * @author liuxuanfei
	 * @date 2017年6月1日 下午5:27:54
	 */
	public void insertBr57_receipt(Gdjg_Response response, String docno) {
		logger.info("将回执信息插入Br57_receipt表：");
		Br57_receipt receipt = new Br57_receipt();
		try {
			receipt.setReceiptkey(this.geSequenceNumber("SEQ_BR57_RECEIPT"));
			receipt.setDocno(docno);
			receipt.setQrydt(DtUtils.getNowDate());
			logger.info(receipt.toString());
			if ((GdjgConstants.DATA_TYPE_ERR).equals(response.getCmdtype())) {
				receipt.setReceipt_status_cd(response.getCmdstatus());
				receipt.setResultinfo("登录异常" + response.getCmdmessage());
			} else {
				receipt.setReceipt_status_cd(response.getDatatype());
				receipt.setResultinfo(response.getErrmessage());
			}
			br57_cxqqMapper.insertBr57_receipt(receipt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 将金额空值进行处理
	 * 
	 * @param cxqq
	 * @return
	 * @author liuxuanfei
	 * @date 2017年6月1日 下午3:45:32
	 */
	LinkedHashMap<String, List<Gdjg_Request_AccCx>> getBr57_cxqq_back_acctListMap(Br57_cxqq cxqq) {
		
		LinkedHashMap<String, List<Gdjg_Request_AccCx>> accMap = new LinkedHashMap<String, List<Gdjg_Request_AccCx>>();
		try {
			List<Gdjg_Request_AccCx> accList = br57_cxqqMapper.getBr57_cxqq_back_acctList1(cxqq);
			for (Gdjg_Request_AccCx acc : accList) {
				//将金额空值进行处理
				String banlance = acc.getBanlance();
				if (banlance == null || banlance.equals("")) {
					acc.setBanlance("0.00");
				}
				String uniqueid = acc.getUniqueid();
				if (accMap.containsKey(uniqueid)) {
					accMap.get(uniqueid).add(acc);
				} else {
					List<Gdjg_Request_AccCx> subList = new ArrayList<Gdjg_Request_AccCx>();
					subList.add(acc);
					accMap.put(uniqueid, subList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return accMap;
		
	}
	
	/**
	 * 修改请求表状态
	 * 
	 * @param response
	 * @param br57_cxqq
	 */
	public void updateBr57_cxqqByGdjg_Response(Gdjg_Response response, Br57_cxqq br57_cxqq) {
		String status = "3";
		if (response == null || response.getDatatype().equals(GdjgConstants.DATA_TYPE_ERR)) {
			status = "4";
		}
		br57_cxqq.setStatus(status);//0：待处理 1：待生成报文  3：反馈成功 4：反馈失败
		br57_cxqq.setLast_up_dt(Utility.currDateTime19());
		br57_cxqqMapper.updateBr57_cxqq(br57_cxqq);
	}
	
	/**
	 * 反馈查询任务_金融产品查询
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 * @author liuxuanfei
	 * @throws Exception
	 * @date 2017年5月31日 下午2:46:02
	 */
	public void FeedBackQuery03(MC21_task_fact mc21_task_fact) throws Exception {
		Br57_cxqq br57_cxqq = new Br57_cxqq();
		br57_cxqq.setDocno(mc21_task_fact.getBdhm());
		logger.info("【金融产品查询】获取Br57_cxqq信息[{}]", mc21_task_fact.getBdhm());
		List<Br57_cxqq> cxqqList = br57_cxqqMapper.getBr57_cxqqList(mc21_task_fact.getBdhm());
		// 1.获取查询任务数据
		Gdjg_RequestJrcpdj request03 = new Gdjg_RequestJrcpdj();
		request03.setDocno(br57_cxqq.getDocno());
		List<Gdjg_Request_CaseCx> casesList = new ArrayList<Gdjg_Request_CaseCx>();
		
		for (Br57_cxqq cxqq : cxqqList) {
			br57_cxqq.setCaseno(cxqq.getCaseno());
			logger.info("【金融产品查询】获取Br57_cxqq_mx信息[{}]", request03.getDocno());
			List<Br57_cxqq_mx> mxList = br57_cxqqMapper.getBr57_cxqq_mxList(br57_cxqq);
			List<Gdjg_Request_RespondentCx> respondents = new ArrayList<Gdjg_Request_RespondentCx>();
			Gdjg_Request_CaseCx casedto = new Gdjg_Request_CaseCx();
			
			for (Br57_cxqq_mx mx : mxList) {
				logger.info("【金融产品查询】获取Gdjg_Request_FinancialProductsCx信息[{}]", request03.getDocno());
				List<Gdjg_Request_FinancialProductsCx> jrcpList = br57_cxqqMapper.getBr57_cxqq_back_financePro(mx);
				// 开户行
				List<Gdjg_Request_BankCx> banks = new ArrayList<Gdjg_Request_BankCx>();
				Gdjg_Request_BankCx bank = new Gdjg_Request_BankCx();
				bank.setBankcode(GdjgConstants.BANK_ID);
				bank.setBankname(GdjgConstants.BANK_NAME);
				if (jrcpList != null && jrcpList.size() != 0) {
					bank.setFinancialPros(jrcpList);// 账户
					banks.add(bank);
				} else {
					//					
					//					List<Gdjg_Request_FinancialProductsCx> products_byNull = new ArrayList<Gdjg_Request_FinancialProductsCx>();
					//					Gdjg_Request_FinancialProductsCx product = new Gdjg_Request_FinancialProductsCx();
					//					product.setProducttype("--");
					//					product.setOrgname("--");
					//					product.setProductcode("--");
					//					product.setProductname("--");
					//					product.setCurrency("--");
					//					product.setBanlance("--");
					//					product.setSalestype("--");
					//					product.setProductsum("--");
					//					product.setProductremark("--");
					
					//					products_byNull.add(product);
					//					bank.setFinancialPros(products_byNull);// 账户
					//					banks.add(bank);
				}
				
				// 被调查人
				logger.info("【金融产品查询】获取Br57_cxqq_back信息[{}]", request03.getDocno());
				Br57_cxqq_back cxqq_back = br57_cxqqMapper.getBr57_cxqq_back(mx);
				Gdjg_Request_RespondentCx respondent = new Gdjg_Request_RespondentCx();
				respondent.setUniqueid(mx.getUniqueid());
				respondent.setReason(cxqq_back.getCzsbyy());
				respondent.setQueryresult(cxqq_back.getCxfkjg());
				respondent.setQueryendtime(Utility.currDateTime14());
				respondent.setQuerytime("");
				respondent.setBanks(banks);
				// 案件
				respondents.add(respondent);
				
			}
			// 案件
			if (respondents.size() != 0) {
				casedto.setCaseno(cxqq.getCaseno());
				casedto.setRespondents(respondents);
				casesList.add(casedto);
			}
		}
		if (casesList.size() != 0) {
			request03.setCases(casesList);
		}
		logger.info(request03.toString());
		// 2.调用反馈接口
		logger.info("【金融产品查询】进行金融产品查询结果登记[{}]", request03.getDocno());
		Gdjg_Response response = requestSend.sendJrcpMassage(request03);
		insertBr57_receipt(response, br57_cxqq.getDocno());
		// 3.置反馈表状态
		Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
		br57_cxqq_back.setStatus("2"); // 2.已反馈
		br57_cxqq_back.setDocno(br57_cxqq.getDocno());
		logger.info("【金融产品查询】更新Br57_cxqq_back状态[{}]", request03.getDocno());
		br57_cxqqMapper.updateBr57_cxqq_back(br57_cxqq_back);
		//4.修改申请表
		this.updateBr57_cxqqByGdjg_Response(response, br57_cxqq);
	}
	
	/**
	 * 反馈查询任务_保险箱查询
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 * @author liuxuanfei
	 * @date 2017年5月31日 下午2:46:02
	 */
	public void FeedBackQuery04(MC21_task_fact mc21_task_fact) {
		
	}
	
	/**
	 * 反馈查询任务_动态查询回执
	 * 
	 * @param mc21_task_fact
	 * @author Liu Ying
	 * @throws Exception
	 * @date 2017/08/08 14:46:34
	 */
	public void FeedBackQuery08(MC21_task_fact mc21_task_fact) throws Exception {
		String taskObj = mc21_task_fact.getTaskobj();
		if (taskObj.contains("$")) {
			logger.info("【动态查询】准备返回动态数据:协作编号-[{}]", mc21_task_fact.getBdhm());
			MC21_task_fact mc21_task_fact_c = mc21_task_fact;
			FeedBackDynamicData(mc21_task_fact_c);
		} else {
			logger.info("【动态查询】准备返回动态查询回执:协作编号-[{}]", mc21_task_fact.getBdhm());
			Br57_cxqq br57_cxqq = new Br57_cxqq();
			br57_cxqq.setDocno(mc21_task_fact.getBdhm());
			
			logger.info("【动态查询】获取Br57_cxqq信息:协作编号-[{}]", mc21_task_fact.getBdhm());
			List<Br57_cxqq> cxqqList = br57_cxqqMapper.getBr57_cxqqList(mc21_task_fact.getBdhm());
			// 1.获取查询任务数据
			Gdjg_RequestDtcxhz request08 = new Gdjg_RequestDtcxhz();
			request08.setDocno(br57_cxqq.getDocno());
			List<Gdjg_Request_CaseCx> casesList = new ArrayList<Gdjg_Request_CaseCx>();
			for (Br57_cxqq cxqq : cxqqList) {
				br57_cxqq.setCaseno(cxqq.getCaseno());
				logger.info("【动态查询】获取Br57_cxqq_mx信息:协作编号-[{}]", request08.getDocno());
				List<Br57_cxqq_mx> mxList = br57_cxqqMapper.getBr57_cxqq_mxList(br57_cxqq);
				List<Gdjg_Request_AccCx> accountList = new ArrayList<Gdjg_Request_AccCx>();
				Gdjg_Request_CaseCx casedto = new Gdjg_Request_CaseCx();
				
				for (Br57_cxqq_mx mx : mxList) {
					logger.info("【动态查询】获取Br57_cxqq_back信息:协作编号-[{}]", request08.getDocno());
					Br57_cxqq_back cxqq_back = br57_cxqqMapper.getBr57_cxqq_back(mx);
					List<Br57_cxqq_mx_policeman> mx_policeman = br57_cxqqMapper.getBr57_cxqq_mxpolicemanList(br57_cxqq);
					Gdjg_Request_AccCx account = new Gdjg_Request_AccCx();
					account.setUniqueid(mx.getUniqueid());
					account.setAccount(mx.getAccount());
					account.setCardno("");
					String result = null;
					if ("01".equals(cxqq_back.getCxfkjg())) {
						result = "0";
					} else if ("02".equals(cxqq_back.getCxfkjg())) {
						result = "1";
					}
					account.setExeresult(result);
					account.setMemo(cxqq_back.getCzsbyy());
					account.setPhone(mx_policeman.get(0).getPolicetel());
					account.setIntervals(mx.getIntervals());
					account.setStartdate(mx.getStartdate());
					account.setEnddate(mx.getEnddate());
					accountList.add(account);
				}
				// 案件
				if (accountList.size() != 0) {
					casedto.setCaseno(cxqq.getCaseno());
					casedto.setAccs(accountList);
					casesList.add(casedto);
				}
			}
			if (casesList.size() != 0) {
				request08.setCases(casesList);
			}
			logger.info(request08.toString());
			
			// 2.调用反馈接口
			logger.info("【动态查询】发送动态查询回执：[{}]", request08.getDocno());
			Gdjg_Response response = requestSend.sendDtcxhzMassage(request08);
			insertBr57_receipt(response, br57_cxqq.getDocno());
			// 3.置反馈表状态
			Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
			br57_cxqq_back.setStatus("2"); // 2.已反馈
			br57_cxqq_back.setDocno(br57_cxqq.getDocno());
			logger.info("【动态查询】更新Br57_cxqq_back状态：[{}]", request08.getDocno());
			br57_cxqqMapper.updateBr57_cxqq_back(br57_cxqq_back);
			//4.修改申请表
			this.updateBr57_cxqqByGdjg_Response(response, br57_cxqq);
		}
	}
	
	/**
	 * 反馈查询任务_动态数据登记
	 * 
	 * @param mc21_task_fact
	 * @author Liu Ying
	 * @throws Exception
	 * @date 2017/08/09 17:07:46
	 */
	private void FeedBackDynamicData(MC21_task_fact mc21_task_fact) throws Exception {
		String docno =mc21_task_fact.getBdhm();
		String seq = mc21_task_fact.getTgroupid();
		String uniqueId = StringUtils.substringAfter(mc21_task_fact.getTaskobj(), "$");
		
		
		// 1.获取查询任务数据
		Gdjg_RequestDtcxdj request08_dj = new Gdjg_RequestDtcxdj();
		request08_dj.setDocno(docno);
		request08_dj.setHelper(uniqueId);  //仅用于写入本地存档的字段
	
		Br57_cxqq_mx  br57_cxqq_mx= new Br57_cxqq_mx();
		br57_cxqq_mx.setDocno(docno);
		br57_cxqq_mx.setUniqueid(uniqueId);
		
		logger.info("【动态查询】获取Br57_cxqq_back_dynamic_trans信息[{}]", request08_dj.getDocno());
		List<Gdjg_Request_TransCx> dynDataList = br57_cxqqMapper.getBr57_cxqq_back_dynamic_transList(br57_cxqq_mx);
		if (dynDataList != null && dynDataList.size() != 0) {
			for (Gdjg_Request_TransCx dynData : dynDataList) {
				String banlance = dynData.getBanlance();
				if (banlance == null || banlance.equals("")) {
					dynData.setBanlance("0.00"); //处理金额格式
				}
				dynData.setUniqueid(StringUtils.substringBefore(dynData.getUniqueid(), "_"));
			}
			request08_dj.setDyndatas(dynDataList);
		} else {
			logger.error("【动态查询】dynDataList信息为空！");
		}
		
		logger.info(request08_dj.toString());
		// 2.调用反馈接口
		logger.info("【动态查询】发送动态数据反馈[{}]", request08_dj.getDocno());
		Gdjg_Response response = requestSend.sendDtcxdjMassage(request08_dj);
		insertBr57_receipt(response, docno);
		// 3.置反馈表状态
		Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
		br57_cxqq_back.setStatus("2"); // 2.已反馈
		br57_cxqq_back.setDocno(docno);
		logger.info("【动态查询】更新Br57_cxqq_back状态[{}]", request08_dj.getDocno());
		br57_cxqqMapper.updateBr57_cxqq_back(br57_cxqq_back);
		//4.修改申请表
		Br57_cxqq br57_cxqq = new Br57_cxqq();
		br57_cxqq.setDocno(docno);
		this.updateBr57_cxqqByGdjg_Response(response, br57_cxqq);
		
	}
	
	/**
	 * 判断是否需要反馈交易流水信息
	 * 
	 * @param cxqqList_copy
	 * @author liuxuanfei
	 * @throws Exception
	 * @date 2017年6月12日 下午3:31:37
	 */
	private void checkTransDo(Br57_cxqq br57_cxqq_copy, List<Br57_cxqq> cxqqList_copy, MC21_task_fact mc21_task_fact_copy) throws Exception {
		boolean flag = false;
		C: for (Br57_cxqq cxqq : cxqqList_copy) {
			br57_cxqq_copy.setCaseno(cxqq.getCaseno());
			List<Br57_cxqq_mx> mxList = br57_cxqqMapper.getBr57_cxqq_mxList(br57_cxqq_copy);
			for (Br57_cxqq_mx mx : mxList) {
				if ("03".equals(mx.getQuerycontent())) {
					flag = true;
					break C;
				}
			}
		}
		if (flag) {
			FeedBackQuery02(mc21_task_fact_copy);
		}
	}
	
}
