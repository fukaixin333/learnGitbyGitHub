package com.citic.server.gdjg.task.taskBo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Br57_kzqq;
import com.citic.server.gdjg.domain.Br57_kzqq_back;
import com.citic.server.gdjg.domain.Br57_kzqq_input;
import com.citic.server.gdjg.domain.Br57_kzqq_mx;
import com.citic.server.gdjg.domain.Br57_receipt;
import com.citic.server.gdjg.domain.Gdjg_Response;
import com.citic.server.gdjg.domain.request.Gdjg_RequestCkdjdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestCkjddj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDjjdhz;
import com.citic.server.gdjg.domain.request.Gdjg_RequestJjzfdj;
import com.citic.server.gdjg.domain.request.Gdjg_Request_AccCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_BankCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_CaseCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_RespondentCx;
import com.citic.server.gdjg.mapper.MM57_kzqqMapper;
import com.citic.server.gdjg.service.RequestMessageServiceGdjg;
import com.citic.server.runtime.GdjgKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.PDFUtils;

/**
 * 广东省检察院
 * 
 * @author liuYing
 * @date 2017年5月25日 下午9:56:46
 */
public class Kzqq_FKBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Kzqq_FKBo.class);
	
	private MM57_kzqqMapper br57_kzqqMapper;
	private RequestMessageServiceGdjg requestSend;
	
	public Kzqq_FKBo(ApplicationContext ac) {
		super(ac);
		this.br57_kzqqMapper = (MM57_kzqqMapper) ac.getBean("MM57_kzqqMapper");
		this.requestSend = (RequestMessageServiceGdjg) ac.getBean("requestMessageServiceGdjg");
	}
	
	/**
	 * 反馈控制任务_存款冻结
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 * @author liuxuanfei
	 * @throws Exception
	 * @throws Exception
	 * @date 2017年5月31日 下午2:46:02
	 */
	public void FeedBackQuery05(MC21_task_fact mc21_task_fact) throws Exception {
		Br57_kzqq br57_kzqq = new Br57_kzqq();
		br57_kzqq.setDocno(mc21_task_fact.getBdhm());
		logger.info("【存款冻结】获取Br57_kzqq信息[{}]", mc21_task_fact.getBdhm());
		List<Br57_kzqq> kzqqList = br57_kzqqMapper.getBr57_kzqqList(mc21_task_fact.getBdhm());
		// 1.获取查询任务数据
		Gdjg_RequestCkdjdj request05 = new Gdjg_RequestCkdjdj();
		request05.setDocno(br57_kzqq.getDocno());
		List<Gdjg_Request_CaseCx> casesList = new ArrayList<Gdjg_Request_CaseCx>();
		
		for (Br57_kzqq kzqq : kzqqList) {
			br57_kzqq.setCaseno(kzqq.getCaseno());
			logger.info("【存款冻结】获取Br57_kzqq_mx信息[{}]", request05.getDocno());
			List<Br57_kzqq_mx> mxList = br57_kzqqMapper.getBr57_kzqq_mxList(br57_kzqq);
			List<Gdjg_Request_RespondentCx> respondents = new ArrayList<Gdjg_Request_RespondentCx>();
			Gdjg_Request_CaseCx casedto = new Gdjg_Request_CaseCx();
			
			for (Br57_kzqq_mx mx : mxList) {
				List<Gdjg_Request_AccCx> accList = new ArrayList<Gdjg_Request_AccCx>();
				logger.info("【存款冻结】获取Br57_kzqq_input信息[{}]", request05.getDocno());
				List<Br57_kzqq_input> kzqq_inputList = br57_kzqqMapper.selectBr57_kzqq_inputByVo(mx);
				for (Br57_kzqq_input kzqq_input : kzqq_inputList) {
					Br57_kzqq_back br57_kzqq_back = br57_kzqqMapper.selectBr57_kzqq_backByVo(kzqq_input);
					Gdjg_Request_AccCx acc = br57_kzqqMapper.getBr57_kzqq_back_FreezeAcc(kzqq_input);
					
					if (acc == null) {
						logger.info("【存款冻结】获取失败的冻结结果（FailFreezeResult）...");
						acc = getAccountByFreezeFail(kzqq_input, br57_kzqq_back.getCzsbyy()); //生成失败的冻结结果返回信息
						List<Gdjg_Request_AccCx> failAccList = new ArrayList<Gdjg_Request_AccCx>();
						failAccList.add(acc);
						br57_kzqqMapper.batchInsertBr57_kzqq_back_FreezeResult(failAccList);
					}
					//进行回执文书处理
					try {
						produceReplyDoc(mx, kzqq_input, acc, "FREEZE"); //进行回执处理,acc有、无值均会进行处理
					} catch (Exception e) {
						logger.error("生成PDF回执文书失败！", e.getMessage());
						throw e;
					}
					accList.add(acc);
				}
				// 开户行
				List<Gdjg_Request_BankCx> banks = new ArrayList<Gdjg_Request_BankCx>();
				Gdjg_Request_BankCx bank = new Gdjg_Request_BankCx();
				bank.setBankcode(GdjgConstants.BANK_ID);
				bank.setBankname(GdjgConstants.BANK_NAME);
				
				if (accList != null && accList.size() != 0) {
					bank.setAccs(accList);// 账户
					banks.add(bank);
				}
				// 被调查人
				Gdjg_Request_RespondentCx respondent = new Gdjg_Request_RespondentCx();
				respondent.setUniqueid(mx.getUniqueid());
				respondent.setBanks(banks);
				respondents.add(respondent);
			}
			// 案件
			if (respondents.size() != 0) {
				casedto.setCaseno(kzqq.getCaseno());
				casedto.setRespondents(respondents);
				casesList.add(casedto);
			}
		}
		if (casesList.size() != 0) {
			request05.setCases(casesList);
		}
		//logger.info(request05.toString());
		// 2.调用反馈接口
		logger.info("【存款冻结】调用存款冻结登记接口进行反馈,协作编号[{}]", request05.getDocno());
		Gdjg_Response response = requestSend.sendFreezeControlMessage(request05);
		insertBr57_receipt(response, br57_kzqq.getDocno());
		// 3.置反馈表状态
		Br57_kzqq_back br57_kzqq_back = new Br57_kzqq_back();
		br57_kzqq_back.setStatus("2"); // 2.已反馈
		br57_kzqq_back.setDocno(br57_kzqq.getDocno());
		logger.info("【存款冻结】更新Br57_kzqq_back信息[{}]", request05.getDocno());
		br57_kzqqMapper.updateBr57_kzqq_back(br57_kzqq_back);
		//4.修改申请表				
		this.updateBr57_kzqqByGdjg_Response(response, br57_kzqq);
		
		logger.info("【存款冻结】调用冻结回执接口进行登记...");
		this.handldeDjhz(mc21_task_fact); //冻结解冻回执登记接口处理
	}
	
	/**
	 * 反馈控制任务_存款解冻
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 * @author liuxuanfei
	 * @throws Exception
	 * @date 2017年5月31日 下午2:46:02
	 */
	public void FeedBackQuery06(MC21_task_fact mc21_task_fact) throws Exception {
		Br57_kzqq br57_kzqq = new Br57_kzqq();
		br57_kzqq.setDocno(mc21_task_fact.getBdhm());
		logger.info("【存款解冻】获取Br57_kzqq信息[{}]", mc21_task_fact.getBdhm());
		List<Br57_kzqq> kzqqList = br57_kzqqMapper.getBr57_kzqqList(mc21_task_fact.getBdhm());
		// 1.获取查询任务数据
		Gdjg_RequestCkjddj request06 = new Gdjg_RequestCkjddj();
		request06.setDocno(br57_kzqq.getDocno());
		List<Gdjg_Request_CaseCx> casesList = new ArrayList<Gdjg_Request_CaseCx>();
		
		for (Br57_kzqq kzqq : kzqqList) {
			br57_kzqq.setCaseno(kzqq.getCaseno());
			logger.info("【存款解冻】获取Br57_kzqq_mx信息[{}]", request06.getDocno());
			List<Br57_kzqq_mx> mxList = br57_kzqqMapper.getBr57_kzqq_mxList(br57_kzqq);
			List<Gdjg_Request_RespondentCx> respondents = new ArrayList<Gdjg_Request_RespondentCx>();
			Gdjg_Request_CaseCx casedto = new Gdjg_Request_CaseCx();
			
			for (Br57_kzqq_mx mx : mxList) {
				List<Gdjg_Request_AccCx> accList = new ArrayList<Gdjg_Request_AccCx>();
				logger.info("【存款解冻】获取Br57_kzqq_input信息[{}]", request06.getDocno());
				List<Br57_kzqq_input> kzqq_inputList = br57_kzqqMapper.selectBr57_kzqq_inputByVo(mx);
				for (Br57_kzqq_input kzqq_input : kzqq_inputList) {
					Br57_kzqq_back br57_kzqq_back = br57_kzqqMapper.selectBr57_kzqq_backByVo(kzqq_input);
					Gdjg_Request_AccCx acc = br57_kzqqMapper.getBr57_kzqq_back_UnfreezeAcc(kzqq_input);
					
					if (acc == null) {
						acc = getAccountByUnfreezeFail(kzqq_input, br57_kzqq_back.getCzsbyy()); //生成失败的冻结结果返回信息
						List<Gdjg_Request_AccCx> failAccList = new ArrayList<Gdjg_Request_AccCx>();
						failAccList.add(acc);
						br57_kzqqMapper.batchInsertBr57_kzqq_back_UnfreezeResult(failAccList);
					}
					//进行回执文书处理
					try {
						produceReplyDoc(mx, kzqq_input, acc, "UNFREEZE");//进行回执处理,acc有、无值均会进行处理
					} catch (Exception e) {
						logger.error("生成PDF回执文书失败！", e.getMessage());
						throw e;
					}
					accList.add(acc);
				}
				
				// 开户行
				List<Gdjg_Request_BankCx> banks = new ArrayList<Gdjg_Request_BankCx>();
				Gdjg_Request_BankCx bank = new Gdjg_Request_BankCx();
				bank.setBankcode(GdjgConstants.BANK_ID);
				bank.setBankname(GdjgConstants.BANK_NAME);
				if (accList != null && accList.size() != 0) {
					bank.setAccs(accList);// 账户
					banks.add(bank);
					// 被调查人
					Gdjg_Request_RespondentCx respondent = new Gdjg_Request_RespondentCx();
					respondent.setUniqueid(mx.getUniqueid());
					respondent.setBanks(banks);
					// 案件
					respondents.add(respondent);
				}
			}
			// 案件
			if (respondents.size() != 0) {
				casedto.setCaseno(kzqq.getCaseno());
				casedto.setRespondents(respondents);
				casesList.add(casedto);
			}
		}
		
		if (casesList.size() != 0) {
			request06.setCases(casesList);
		}
		// 2.调用反馈接口
		logger.info("【存款解冻】调用存款解冻登记接口进行反馈,协作编号[{}]", request06.getDocno());
		Gdjg_Response response = requestSend.sendUnfreezeControlMessage(request06);
		insertBr57_receipt(response, br57_kzqq.getDocno());
		// 3.置反馈表状态
		Br57_kzqq_back br57_kzqq_back = new Br57_kzqq_back();
		br57_kzqq_back.setStatus("2"); // 2.已反馈
		br57_kzqq_back.setDocno(br57_kzqq.getDocno());
		logger.info("【存款解冻】更新Br57_kzqq_back信息[{}]", request06.getDocno());
		br57_kzqqMapper.updateBr57_kzqq_back(br57_kzqq_back);
		//4.修改申请表
		this.updateBr57_kzqqByGdjg_Response(response, br57_kzqq);
		
		logger.info("【存款解冻】调用解冻回执接口进行登记...");
		this.handldeJdhz(mc21_task_fact); //冻结解冻回执登记接口处理
	}
	
	/**
	 * 反馈控制任务_紧急止付
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 * @author liuxuanfei
	 * @throws Exception
	 * @throws Exception
	 * @date 2017年5月31日 下午2:46:02
	 */
	public void FeedBackQuery07(MC21_task_fact mc21_task_fact) throws Exception {
		Br57_kzqq br57_kzqq = new Br57_kzqq();
		br57_kzqq.setDocno(mc21_task_fact.getBdhm());
		logger.info("【紧急止付】获取Br57_kzqq信息[{}]", mc21_task_fact.getBdhm());
		List<Br57_kzqq> kzqqList = br57_kzqqMapper.getBr57_kzqqList(mc21_task_fact.getBdhm());
		// 1.获取查询任务数据
		Gdjg_RequestJjzfdj request07 = new Gdjg_RequestJjzfdj();
		request07.setDocno(br57_kzqq.getDocno());
		List<Gdjg_Request_CaseCx> casesList = new ArrayList<Gdjg_Request_CaseCx>();
		
		for (Br57_kzqq kzqq : kzqqList) {
			br57_kzqq.setCaseno(kzqq.getCaseno());
			logger.info("【紧急止付】获取Br57_kzqq_mx信息[{}]", request07.getDocno());
			List<Br57_kzqq_mx> mxList = br57_kzqqMapper.getBr57_kzqq_mxList(br57_kzqq);
			
			for (Br57_kzqq_mx mx : mxList) {
				List<Gdjg_Request_AccCx> accList = new ArrayList<Gdjg_Request_AccCx>();
				logger.info("【紧急止付】获取Br57_kzqq_input信息[{}]", request07.getDocno());
				List<Br57_kzqq_input> kzqq_inputList = br57_kzqqMapper.selectBr57_kzqq_inputByVo(mx);
				for (Br57_kzqq_input kzqq_input : kzqq_inputList) {
					Br57_kzqq_back br57_kzqq_back = br57_kzqqMapper.selectBr57_kzqq_backByVo(kzqq_input);
					logger.info("【紧急止付】获取Br57_kzqq_back_Stoppayment信息[{}]", request07.getDocno());
					Gdjg_Request_AccCx acc = br57_kzqqMapper.getBr57_kzqq_back_StoppaymentAcc(kzqq_input);
					
					if (acc == null) {
						acc = getAccountByStoppaymentFail(kzqq_input, br57_kzqq_back.getCzsbyy()); //生成失败的冻结结果返回信息
						List<Gdjg_Request_AccCx> failAccList = new ArrayList<Gdjg_Request_AccCx>();
						failAccList.add(acc);
						br57_kzqqMapper.batchInsertBr57_kzqq_back_StopPaymentResult(failAccList); //将失败结果插入表格
					}
					accList.add(acc);
				}
				
				if (accList != null && accList.size() != 0) {
					Gdjg_Request_CaseCx casedto = new Gdjg_Request_CaseCx();
					casedto.setAccs(accList);// 账户
					casedto.setCaseno(mx.getCaseno());
					casesList.add(casedto);
				} else {
				}
			}
		}
		
		if (casesList.size() != 0) {
			request07.setCases(casesList);
		}
		logger.info(request07.toString());
		// 2.调用反馈接口
		logger.info("【紧急止付】调用紧急止付/解除紧急止付登记接口进行反馈,协作编号[{}]", request07.getDocno());
		Gdjg_Response response = requestSend.sendJjzfControlMessage(request07);
		insertBr57_receipt(response, br57_kzqq.getDocno());
		// 3.置反馈表状态
		Br57_kzqq_back br57_kzqq_back = new Br57_kzqq_back();
		br57_kzqq_back.setStatus("2"); // 2.已反馈
		br57_kzqq_back.setDocno(br57_kzqq.getDocno());
		logger.info("【紧急止付】更新Br57_kzqq_back信息[{}]", request07.getDocno());
		br57_kzqqMapper.updateBr57_kzqq_back(br57_kzqq_back);
		//4.修改申请表
		this.updateBr57_kzqqByGdjg_Response(response, br57_kzqq);
		
	}
	
	/**
	 * 冻结/解冻处理：生成PDF回执
	 * 
	 * @param mx
	 * @param kzqq_input
	 * @param acc
	 * @author liuying07
	 * @param string
	 * @throws Exception
	 * @date 2017/08/15 17:33:18
	 */
	private void produceReplyDoc(Br57_kzqq_mx mx, Br57_kzqq_input kzqq_input, Gdjg_Request_AccCx acc, String flag) throws Exception {
		if ("1".equals(acc.getFroflag()) || "1".equals(acc.getThawflag())) {
			//回执处理
			logger.info("处理结果【成功】的冻结回执文书信息，并生成pdf回执文书：");
			String absolutePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_ATTACH, GdjgKeys.FILE_DIRECTORY) + File.separator
									+ mx.getDocno();
			String pdfFileName = mx.getUniqueid() + ".pdf";
			String outputFilePath = absolutePath + File.separator + pdfFileName;
			
			//创建文件夹
			File dir = new File(absolutePath);
			if (!dir.exists() || !dir.isDirectory()) {
				dir.mkdirs();
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if ("1".equals(acc.getFroflag())) {
				map.put("FROBALANCE", acc.getFrobanlance_2()); //已冻结金额 
			} else if ("1".equals(acc.getThawflag())) {
				map.put("FROBALANCE", acc.getThawbanlance()); //解冻金额
			}
			map.put("RESULT", "1"); //标识冻结结果成功或失败
			map.put("EXEUNIT", kzqq_input.getExeunit()); //执行单位
			map.put("QQCSLX", flag); //控制类型
			map.put("DOCNUM", kzqq_input.getDocnum()); //文书文号 
			map.put("NAME", acc.getAccname()); //户名
			map.put("ACCOUNT", kzqq_input.getAccount()); //账号
			map.put("DATETIME", Utility.currDateCN()); //回执时间
			PDFUtils.html2pdf("gdjg/gdjgFreeze.html", outputFilePath, map);
			
			//插入回执信息路径
			acc.setReplydoc(outputFilePath); //acc中放入回执文书路径
			if ("FREEZE".equals(flag)) {
				logger.info("将冻结回执文书路径插入冻结结果[Br57_kzqq_back_freezeResult]：");
				br57_kzqqMapper.updateBr57_kzqq_back_freezeResult(acc);
			} else if ("UNFREEZE".equals(flag)) {
				logger.info("将解冻回执文书路径插入解冻结果[Br57_kzqq_back_unfreezeResult]：");
				br57_kzqqMapper.updateBr57_kzqq_back_unfreezeResult(acc);
			}
			acc.setReplydoc("详见冻结解冻回执"); //反馈的结果不显示这个路径,注意：这个字段非空
		} else {
			logger.info("处理结果【失败】的冻结回执文书信息，并生成pdf回执文书：");
			String absolutePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_ATTACH, GdjgKeys.FILE_DIRECTORY) + File.separator
									+ mx.getDocno();
			String pdfFileName = mx.getUniqueid() + ".pdf";
			String outputFilePath = absolutePath + File.separator + pdfFileName;
			
			//创建文件夹
			File dir = new File(absolutePath);
			if (!dir.exists() || !dir.isDirectory()) {
				dir.mkdirs();
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("RESULT", "2"); //标识执行结果成功或失败
			map.put("EXEUNIT", kzqq_input.getExeunit()); //执行单位
			map.put("QQCSLX", flag); //控制类型
			map.put("DOCNUM", kzqq_input.getDocnum()); //文书文号 
			map.put("NAME", mx.getName()); //户名
			map.put("ACCOUNT", kzqq_input.getAccount()); //账号
			map.put("FROBALANCE", ""); //已冻结金额 
			map.put("DATETIME", Utility.currDateCN()); //回执时间
			PDFUtils.html2pdf("gdjg/gdjgFreeze.html", outputFilePath, map);
			//插入回执信息路径
			acc.setReplydoc(outputFilePath); //acc中放入回执文书路径
			if ("FREEZE".equals(flag)) {
				logger.info("将冻结回执文书路径插入冻结结果[Br57_kzqq_back_freezeResult]：");
				br57_kzqqMapper.updateBr57_kzqq_back_freezeResult(acc);
			} else if ("UNFREEZE".equals(flag)) {
				logger.info("将解冻回执文书路径插入解冻结果[Br57_kzqq_back_unfreezeResult]：");
				br57_kzqqMapper.updateBr57_kzqq_back_unfreezeResult(acc);
			}
			acc.setReplydoc("详见冻结解冻回执"); //反馈的结果不显示这个路径,注意这个字段非空
		}
		
	}
	
	/** 处理：冻结/续冻（失败）--账户信息处理 */
	private Gdjg_Request_AccCx getAccountByFreezeFail(Br57_kzqq_input br57_kzqq_input, String memo) {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		acc.setDocno(br57_kzqq_input.getDocno());
		acc.setCaseno(br57_kzqq_input.getCaseno());
		acc.setUniqueid(br57_kzqq_input.getUniqueid());
		acc.setAccount(br57_kzqq_input.getAccount());//账号
		acc.setFroseq(br57_kzqq_input.getFroseq());//冻结流水号
		acc.setCardno("-");//卡号
		acc.setBanlance("-");//余额
		acc.setCanbanlance("-");//账户可用余额
		acc.setCurrency(br57_kzqq_input.getCurrency());//币种
		acc.setExchangetype(br57_kzqq_input.getExchangetype());//汇钞类型
		acc.setFrotype(br57_kzqq_input.getFrotype());//冻结类型 
		acc.setFromode(br57_kzqq_input.getFromode());//冻结方式
		acc.setFroflag("2");//冻结标志      1冻结成功    2冻结失败
		acc.setFrobanlance_1(br57_kzqq_input.getFrobanlance());//应冻结金额
		acc.setFrobanlance_2("");//已冻结金额
		acc.setFrobanlance_3("");//未冻结金额
		acc.setFrobanlance("");//冻结额度
		acc.setFrostartdate(br57_kzqq_input.getFrostartdate());//冻结开始日期
		acc.setFroenddate(br57_kzqq_input.getFroenddate());//冻结结束日期
		acc.setMemo(memo);//原因 
		acc.setBeforefro("");//在先冻结机关
		acc.setBeforefroban("");//在先冻结金额 
		acc.setBeforefrodate("");//在先冻结到期日
		acc.setServicetime(Utility.currDateTime19());//送达时间
		acc.setServicedoc("");//送达证
		acc.setReplytime(Utility.currDateTime14());//回执时间
		acc.setReplydoc("");//回执
		acc.setFiletype("PDF");//文件格式
		acc.setFronumber("");//冻结编号（artery）
		acc.setQrydt(DtUtils.getNowDate()); //分区时间
		return acc;
	}
	
	/**
	 * 冻结回执登记接口
	 * 
	 * @param mc21_task_fact
	 * @param accList
	 * @author liuying07
	 * @throws RemoteAccessException
	 * @date 2017/08/15 19:08:53
	 */
	private void handldeDjhz(MC21_task_fact mc21_task_fact) throws RemoteAccessException {
		//List<Gdjg_Request_AccCx> accList = Gdjg_accList;
		logger.info("【冻结回执登记】：处理冻结回执登记信息，并准备发起登记：");
		Gdjg_RequestDjjdhz djhzdj = new Gdjg_RequestDjjdhz();
		String docno = mc21_task_fact.getBdhm();
		djhzdj.setDocno(docno);
		logger.info("【冻结回执登记】：获取Br57_kzqq信息：");
		List<Br57_kzqq> kzqqList = br57_kzqqMapper.getBr57_kzqqList(mc21_task_fact.getBdhm());
		// 1.获取查询任务数据
		List<Gdjg_Request_CaseCx> casesList_hz = new ArrayList<Gdjg_Request_CaseCx>();
		
		for (Br57_kzqq br57_kzqq : kzqqList) {
			logger.info("【冻结回执登记】：获取Br57_kzqq_mx信息：");
			List<Br57_kzqq_mx> mxList = br57_kzqqMapper.getBr57_kzqq_mxList(br57_kzqq);
			List<Gdjg_Request_RespondentCx> respondents = new ArrayList<Gdjg_Request_RespondentCx>();
			Gdjg_Request_CaseCx casedto = new Gdjg_Request_CaseCx();
			for (Br57_kzqq_mx mx : mxList) {
				List<Gdjg_Request_BankCx> banks = new ArrayList<Gdjg_Request_BankCx>();
				Gdjg_Request_BankCx bank = new Gdjg_Request_BankCx();
				bank.setBankcode(GdjgConstants.BANK_ID);
				bank.setBankname(GdjgConstants.BANK_NAME);
				List<Gdjg_Request_AccCx> accList = new ArrayList<Gdjg_Request_AccCx>();
				accList = br57_kzqqMapper.getBr57_kzqq_back_FreezeAccByMx(mx);
				if (accList != null && accList.size() != 0) {
					for (Gdjg_Request_AccCx acc_new : accList) {
						String outputFilePath = acc_new.getReplydoc();
						String replyDoc = null;
						try {
							replyDoc = Utility.encodeBase64(CommonUtils.readBinaryFile(outputFilePath));
						} catch (Exception e) {
							logger.error("【冻结回执登记】：回执文书二进制内容传入字段replyDoc失败", e.getMessage());
							replyDoc = "回执文书二进制内容传入字段replyDoc失败";
						}
						acc_new.setReplydoc(replyDoc);
					}
					bank.setAccs(accList);// 账户
					banks.add(bank);
				}
				Gdjg_Request_RespondentCx respondent = new Gdjg_Request_RespondentCx();
				respondent.setUniqueid(mx.getUniqueid());
				respondent.setBanks(banks);
				respondents.add(respondent);
			}
			if (respondents.size() != 0) {
				casedto.setCaseno(br57_kzqq.getCaseno());
				casedto.setRespondents(respondents);
				casesList_hz.add(casedto);
			}
		}
		if (casesList_hz.size() != 0) {
			djhzdj.setCases(casesList_hz);
		}
		logger.info(djhzdj.toString());
		logger.info("【冻结回执登记】：调用存款冻结回执登记接口进行反馈,协作编号[{}]", djhzdj.getDocno());
		Gdjg_Response response_djhz = requestSend.sendDjjdhzMessage(djhzdj);
		insertBr57_receipt(response_djhz, docno);
	}
	
	/**
	 * 解冻回执登记接口
	 * 
	 * @param mc21_task_fact
	 * @param accList
	 * @author liuying07
	 * @throws RemoteAccessException
	 * @date 2017/08/15 19:08:53
	 */
	private void handldeJdhz(MC21_task_fact mc21_task_fact) throws RemoteAccessException {
		logger.info("【解冻回执登记】：处理解冻回执登记信息，并准备发起登记：");
		Gdjg_RequestDjjdhz djhzdj = new Gdjg_RequestDjjdhz();
		String docno = mc21_task_fact.getBdhm();
		djhzdj.setDocno(docno);
		logger.info("【解冻回执登记】：获取Br57_kzqq信息：");
		List<Br57_kzqq> kzqqList = br57_kzqqMapper.getBr57_kzqqList(mc21_task_fact.getBdhm());
		// 1.获取查询任务数据
		List<Gdjg_Request_CaseCx> casesList_hz = new ArrayList<Gdjg_Request_CaseCx>();
		
		for (Br57_kzqq br57_kzqq : kzqqList) {
			logger.info("【解冻回执登记】：获取Br57_kzqq_mx信息：");
			List<Br57_kzqq_mx> mxList = br57_kzqqMapper.getBr57_kzqq_mxList(br57_kzqq);
			List<Gdjg_Request_RespondentCx> respondents = new ArrayList<Gdjg_Request_RespondentCx>();
			Gdjg_Request_CaseCx casedto = new Gdjg_Request_CaseCx();
			for (Br57_kzqq_mx mx : mxList) {
				List<Gdjg_Request_BankCx> banks = new ArrayList<Gdjg_Request_BankCx>();
				Gdjg_Request_BankCx bank = new Gdjg_Request_BankCx();
				bank.setBankcode(GdjgConstants.BANK_ID);
				bank.setBankname(GdjgConstants.BANK_NAME);
				List<Gdjg_Request_AccCx> accList = new ArrayList<Gdjg_Request_AccCx>();
				accList = br57_kzqqMapper.getBr57_kzqq_back_UnFreezeAccByMx(mx);
				if (accList != null && accList.size() != 0) {
					for (Gdjg_Request_AccCx acc_new : accList) {
						acc_new.setFroseq(acc_new.getThawseq()); //此处是为了与冻结回执共用一套binding模板
						String outputFilePath = acc_new.getReplydoc();
						String replyDoc = null;
						try {
							replyDoc = Utility.encodeBase64(CommonUtils.readBinaryFile(outputFilePath));
						} catch (Exception e) {
							logger.error("【解冻回执登记】：回执文书二进制内容传入字段replyDoc失败", e.getMessage());
							replyDoc = "回执文书二进制内容传入字段replyDoc失败";
						}
						acc_new.setReplydoc(replyDoc);
					}
					bank.setAccs(accList);// 账户
					banks.add(bank);
				}
				Gdjg_Request_RespondentCx respondent = new Gdjg_Request_RespondentCx();
				respondent.setUniqueid(mx.getUniqueid());
				respondent.setBanks(banks);
				respondents.add(respondent);
			}
			if (respondents.size() != 0) {
				casedto.setCaseno(br57_kzqq.getCaseno());
				casedto.setRespondents(respondents);
				casesList_hz.add(casedto);
			}
		}
		if (casesList_hz.size() != 0) {
			djhzdj.setCases(casesList_hz);
		}
		logger.info(djhzdj.toString());
		logger.info("【解冻回执登记】：调用存款解冻回执登记接口进行反馈,协作编号[{}]", djhzdj.getDocno());
		Gdjg_Response response_djhz = requestSend.sendDjjdhzMessage(djhzdj);
		insertBr57_receipt(response_djhz, docno);
	}
	
	/** 处理：解冻（失败）--账户信息处理 */
	private Gdjg_Request_AccCx getAccountByUnfreezeFail(Br57_kzqq_input br57_kzqq_input, String memo) {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		acc.setDocno(br57_kzqq_input.getDocno());
		acc.setCaseno(br57_kzqq_input.getCaseno());
		acc.setUniqueid(br57_kzqq_input.getUniqueid());
		acc.setThawseq(br57_kzqq_input.getThawseq());// 解冻流水号
		acc.setAccount(br57_kzqq_input.getAccount());// 账号
		acc.setCardno("");// 卡号
		acc.setCurrency(br57_kzqq_input.getCurrency());// 币种
		acc.setExchangetype(br57_kzqq_input.getExchangetype());// 汇钞类型     1：现钞  2：外汇
		acc.setThawmode(br57_kzqq_input.getThawmode());//解冻方式
		acc.setThawflag("2");// 解冻标志  1 解冻成功 2 解冻失败
		acc.setThawbanlance(br57_kzqq_input.getThawbanlance());//解冻金额
		acc.setThawdate(DtUtils.getNowDate());// 解冻日期 
		acc.setMemo(memo);//原因
		acc.setServicetime(Utility.currDateTime19());//送达时间
		acc.setServicedoc("");//送达证
		acc.setReplytime(Utility.currDateTime14());//回执时间 
		acc.setReplydoc("");//回执
		acc.setFiletype("PDF");//文件格式
		acc.setQrydt(DtUtils.getNowDate()); //分区时间
		
		return acc;
	}
	
	/** 处理：紧急止付（失败）--账户信息处理 */
	private Gdjg_Request_AccCx getAccountByStoppaymentFail(Br57_kzqq_input br57_kzqq_input, String memo) {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		acc.setDocno(br57_kzqq_input.getDocno());
		acc.setCaseno(br57_kzqq_input.getCaseno());
		acc.setUniqueid(br57_kzqq_input.getUniqueid());
		acc.setAccount(br57_kzqq_input.getAccount());//账号
		acc.setCardno("");//卡号
		acc.setExeresult("1");//执行结果  0-执行成功；1-执行失败
		acc.setStartdate(Utility.currDate10());//开始日期
		acc.setMemo(memo);//原因
		acc.setFronumber("");//冻结编号（artery）
		acc.setFroseq(br57_kzqq_input.getOldseq());//冻结编号
		acc.setQrydt(DtUtils.getNowDate()); //分区时间
		return acc;
	}
	
	/** 处理：解除紧急止付（失败）--账户信息处理 */
	//	private Gdjg_Request_AccCx getAccountByUnstoppaymentFail(Br57_kzqq_input br57_kzqq_input) {
	//		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
	//	acc.setDocno(br57_kzqq_input.getDocno());
	//	acc.setCaseno(br57_kzqq_input.getCaseno());
	//	acc.setUniqueid(br57_kzqq_input.getUniqueid());
	//		acc.setAccount(br57_kzqq_input.getAccount());//账号
	//		acc.setCardno("");//卡号
	//		acc.setExeresult("1");//执行结果   0-执行成功；1-执行失败
	//		acc.setStartdate("");//开始日期
	//		acc.setMemo("");//原因
	//		acc.setFroseq(br57_kzqq_input.getOldseq());//解冻编号
	//		return acc;
	//	}
	
	/**
	 * 修改请求表状态
	 * 
	 * @param response
	 * @param br57_kzqq
	 * @author liuying07
	 * @date 2017年7月3日 上午11:38:21
	 */
	private void updateBr57_kzqqByGdjg_Response(Gdjg_Response response, Br57_kzqq br57_kzqq) {
		String status = "3";
		if (response == null || response.getDatatype().equals(GdjgConstants.DATA_TYPE_ERR)) {
			status = "4";
		}
		br57_kzqq.setStatus(status);//0：待处理 1：待生成报文  3：反馈成功 4：反馈失败
		br57_kzqq.setLast_up_dt(Utility.currDateTime19());
		br57_kzqqMapper.updateBr57_kzqq(br57_kzqq);
		
	}
	
	/**
	 * 回执处理
	 * 
	 * @param response
	 * @param docno
	 * @author liuying07
	 * @date 2017年7月3日 上午11:38:42
	 */
	private void insertBr57_receipt(Gdjg_Response response, String docno) {
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
			br57_kzqqMapper.insertBr57_receipt(receipt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
