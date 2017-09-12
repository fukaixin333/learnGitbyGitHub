package com.citic.server.dx.task.taskBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.dx.domain.AccountsTransaction;
import com.citic.server.dx.domain.Br24_account_freeze;
import com.citic.server.dx.domain.Br24_account_holder;
import com.citic.server.dx.domain.Br24_account_info;
import com.citic.server.dx.domain.Br24_account_right;
import com.citic.server.dx.domain.Br24_bas_info;
import com.citic.server.dx.domain.Br24_card_info;
import com.citic.server.dx.domain.Br24_q_Main;
import com.citic.server.dx.domain.Br24_trans_info;
import com.citic.server.dx.domain.PartyQueryResult;
import com.citic.server.dx.domain.request.QueryRequest100302;
import com.citic.server.dx.domain.request.QueryRequest100304;
import com.citic.server.dx.domain.request.QueryRequest100306;
import com.citic.server.dx.domain.request.QueryRequest100308;
import com.citic.server.dx.domain.request.QueryRequest100310;
import com.citic.server.dx.domain.request.QueryRequest_Account;
import com.citic.server.dx.domain.request.QueryRequest_Accounts;
import com.citic.server.dx.domain.request.QueryRequest_Measure;
import com.citic.server.dx.domain.request.QueryRequest_Priority;
import com.citic.server.dx.domain.request.QueryRequest_Transaction;
import com.citic.server.dx.service.CodeChange;
import com.citic.server.dx.service.DataOperate2;
import com.citic.server.net.mapper.MM24_q_mainMapper;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.BB13_sys_para;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

public class Q_main_backBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Q_main_backBo.class);
	
	/** 数据获取接口 */
	private DataOperate2 dataOperate;
	private DataOperate2 dataOperate_t;
	
	/** 码表转换接口 */
	private CodeChange codeChange;
	
	private MM24_q_mainMapper mm24_q_mainMapper;
	
	public Q_main_backBo(ApplicationContext ac) {
		super(ac);
		this.mm24_q_mainMapper = (MM24_q_mainMapper) ac.getBean("MM24_q_mainMapper");
		this.codeChange = (CodeChange) ac.getBean("codeChangeImpl");
		
	}
	
	/**
	 * 处理账户交易明细查询任务 - task2
	 * 查询数据落地100301
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public void handleQueryResponse100301(MC21_task_fact mc21_task_fact) throws Exception {
		Br24_card_info br24_card_info = new Br24_card_info();
		List<Br24_account_info> account_infoList = new ArrayList<Br24_account_info>();
		List<Br24_trans_info> trans_infoList = new ArrayList<Br24_trans_info>();
		
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		// 2、获取反馈基本信息表
		Br24_bas_info br24_bas_info = this.getBr24_bas_info(cg_q_main);
		
		try {
			// 3、生成卡折信息、账户信息、账户交易流水
			// 3.1 根据请求信息及标志，判断查询数据接口(1.全核心 2.全批后除快速查询 3.全核心除历史流水)
			if (StringUtils.equals(this.query_datasource, REMOTE_QUERY)) {
				dataOperate = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
			} else if (StringUtils.equals(this.query_datasource, LOCAL_QUERY)) {
				dataOperate = (DataOperate2) this.ac.getBean(LOCAL_DATA_OPERATE_NAME_DX);
				dataOperate_t = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
			} else {
				dataOperate = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
				dataOperate_t = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
			}
			String cardnum = cg_q_main.getAccountnumber();
			String transserialnumber = br24_bas_info.getTransserialnumber();
			AccountsTransaction accountstransaction = new AccountsTransaction();
			//判断卡号是否该法人的
			String msgCheckResult = br24_bas_info.getMsgcheckresult();
			if (msgCheckResult != null && (msgCheckResult.equals("") || msgCheckResult.equals("1"))) { //判断是否是本行数据
				// 3.2 执行查询，获取数据
				br24_card_info = dataOperate.getBr24_card_info(cardnum, cg_q_main.getSubjecttype());
				
				//查询内容（01-账户基本信息；02-账户交易明细； 03-账户基本信息+交易明细；04-账户明细快速查询）
				String subjecttype = cg_q_main.getSubjecttype();
				if (!"01".equals(cg_q_main.getInquirymode())) {
					if (StringUtils.equals(this.query_datasource, LOCAL_QUERY)) {
						if ("04".equals(cg_q_main.getInquirymode())) {
							accountstransaction = dataOperate_t.getAccountsTransactionByCardNumber(cardnum, subjecttype, cg_q_main.getInquiryperiodstart(), cg_q_main.getInquiryperiodend());
						} else {
							accountstransaction = dataOperate.getAccountsTransactionByCardNumber(cardnum, cg_q_main.getSubjecttype(), cg_q_main.getInquiryperiodstart(), cg_q_main.getInquiryperiodend());
							
						}
					} else if (StringUtils.equals(this.query_datasource, REMOTE_QUERY)) {
						accountstransaction = dataOperate.getAccountsTransactionByCardNumber(cardnum, subjecttype, cg_q_main.getInquiryperiodstart(), cg_q_main.getInquiryperiodend());
						
					} else {
						if ("04".equals(cg_q_main.getInquirymode())) {
							accountstransaction = dataOperate_t.getAccountsTransactionByCardNumber(cardnum, subjecttype, cg_q_main.getInquiryperiodstart(), cg_q_main.getInquiryperiodend());
						} else {
							accountstransaction = dataOperate.getAccountsTransactionByCardNumber(cardnum, cg_q_main.getSubjecttype(), cg_q_main.getInquiryperiodstart(), cg_q_main.getInquiryperiodend());
							
						}
					}
					if (accountstransaction != null) {
						account_infoList = accountstransaction.getAccountInfoList();
						trans_infoList = accountstransaction.getTransInfoList();
					}
					
				} else {
					account_infoList = dataOperate.getBr24_account_infoList(cardnum, cg_q_main.getSubjecttype());
				}
				
				// 3.3 码表转换（即使是批后提取业务数据，也需要进行码表转换）
				codeChange.chgCode(br24_card_info, "BR24_CARD_INFO", this.query_datasource);
				codeChange.chgCode(account_infoList, "BR24_ACCOUNT_INFO", this.query_datasource);
				if (!"01".equals(cg_q_main.getInquirymode())) {
					codeChange.chgCode(trans_infoList, "BR24_TRANS_INFO", this.query_datasource);
				}
				
				// 3.4写卡折信息表、账户信息表、账户交易流水表
				insertBr24_card_info(br24_card_info, transserialnumber);
				insertBr24_account_info(account_infoList, br24_bas_info);
				
				// 将反馈结果置为成功状态
				br24_bas_info.setResult("0000");
				br24_bas_info.setFeedbackRemark("");
				
				if (!"01".equals(cg_q_main.getInquirymode())) {
					String flag = insertBr24_trans_info(trans_infoList, transserialnumber, cardnum);
					if (flag.equals("1")) {
						br24_bas_info.setResult("2400");
					}
				}
				
			} else {
				mc21_task_fact.setIsemployee("0");
			}
		} catch (DataOperateException e) {
			// 3.3 错误统一采用异常处理
			logger.error("数据处理异常：{}", e.getMessage());
			br24_bas_info.setResult(e.getCode());
			br24_bas_info.setFeedbackRemark(e.getDescr());
		} catch (RemoteAccessException e) {
			logger.error("应用程序异常：{}", e.getMessage(), e);
			br24_bas_info.setResult("1199");
			br24_bas_info.setFeedbackRemark("应用程序异常");
		}
		// 4、写反馈基本信息表 
		updateBr24_bas_info(br24_bas_info);
		
		// 5.插入任务task3
		insertMc21TaskFact3(mc21_task_fact, "DX");
		
	}
	
	/**
	 * 处理账户持卡主体查询任务 - task2
	 * 查询数据落地100303
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public void handleQueryResponse100303(MC21_task_fact mc21_task_fact) throws Exception {
		Br24_account_holder br24_account_holder;
		
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		String transnum = mc21_task_fact.getBdhm();//流水号
		// 2、获取反馈基本信息表
		Br24_bas_info br24_bas_info = this.getBr24_bas_info(cg_q_main);
		
		try {
			// 3、生成持卡主体信息
			// 3.1 根据请求信息及标志，判断查询数据接口
			if (StringUtils.equals(this.query_datasource, LOCAL_QUERY)) {
				dataOperate = (DataOperate2) this.ac.getBean(LOCAL_DATA_OPERATE_NAME_DX);
			} else {
				dataOperate = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
			}
			String msgCheckResult = br24_bas_info.getMsgcheckresult();
			if (msgCheckResult != null && (msgCheckResult.equals("") || msgCheckResult.equals("1"))) { //判断是否是本行数据
			
				// 3.2 执行查询，获取数据cg_q_main.getAccountnumber()
				br24_account_holder = dataOperate.getBr24_account_holder(cg_q_main.getSubjecttype(), cg_q_main.getAccountnumber());
				
				if (br24_account_holder == null)
					br24_account_holder = new Br24_account_holder();
				
				// 3.3 码表转换（即使是批后提取业务数据，也需要进行码表转换）
				codeChange.chgCode(br24_account_holder, "BR24_ACCOUNT_HOLDER", this.query_datasource);
				// 3.4 写持卡主体主表
				br24_account_holder.setTransSerialNumber(br24_bas_info.getTransserialnumber());
				insertBr24_account_holder(br24_account_holder, cg_q_main);
				
				// 将反馈结果置为成功状态
				br24_bas_info.setResult("0000");
				br24_bas_info.setFeedbackRemark("");
				
			} else {
				mc21_task_fact.setIsemployee("0");
			}
		} catch (DataOperateException e) {
			e.printStackTrace();
			// 3.3 错误统一采用异常处理
			logger.error("查询异常" + e.getMessage());
			br24_bas_info.setResult(e.getCode());
			br24_bas_info.setFeedbackRemark(e.getDescr());
		} catch (RemoteAccessException e) {
			e.printStackTrace();
			logger.error("Artery异常" + e.getMessage());
			//throw e;
			br24_bas_info.setResult("1199");
			br24_bas_info.setFeedbackRemark("处理异常");
		}
		// 4、写反馈基本信息表 
		updateBr24_bas_info(br24_bas_info);
		
		// 5、插入任务task3
		insertMc21TaskFact3(mc21_task_fact, "DX");
		
	}
	
	/**
	 * 处理账户动态查询任务 - task2
	 * 查询数据落地100305
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public void handleQueryResponse100305(MC21_task_fact mc21_task_fact) throws Exception {
		//List<Br24_trans_info> trans_infoList;
		
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		try {
			Br24_bas_info br24_bas_info = new Br24_bas_info();
			br24_bas_info.setApplicationID(cg_q_main.getApplicationid());
			br24_bas_info.setToorg(cg_q_main.getMessageFrom());
			br24_bas_info.setTxCode(cg_q_main.getTxCode());
			br24_bas_info.setTransSerialNumber(mc21_task_fact.getBdhm());
			br24_bas_info.setOrgkey(cg_q_main.getOrgkey());
			Q_mainBo q_mainBo = new Q_mainBo(this.ac);
			q_mainBo.insertBr24_bas_info(br24_bas_info);
			mc21_task_fact.setTgroupid(mc21_task_fact.getBdhm());
			this.insertMc21TaskFact3(mc21_task_fact, "DX"); //动态查询先返回一条回执
			
			String status = cg_q_main.getStatus();
			if (status != null && status.equals("2")) { //非本行客户直接插入task3
			
			} else {
				//写账户动态查询或解除任务表
				insertBr24_q_acct_dynamic_main(cg_q_main);
			}
			/*
			 * // 2、获取反馈基本信息表
			 * Br24_bas_info br24_bas_info = new Br24_bas_info();
			 * br24_bas_info.setApplicationID(cg_q_main.getApplicationid());
			 * br24_bas_info.setTo(cg_q_main.getMessageFrom());
			 * // 删除请求单号下的数据
			 * mm24_q_mainMapper.delBr24_bas_info(br24_bas_info);
			 * try {
			 * // 3、生成账户交易流水表
			 * // 3.1 根据请求信息及标志，判断查询数据接口
			 * if (StringUtils.equals(this.query_datasource, LOCAL_QUERY)) {
			 * dataOperate = (DataOperate) this.ac.getBean(LOCAL_QUERY_BEAN);
			 * } else {
			 * dataOperate = (DataOperate) this.ac.getBean(REMOTE_QUERY_BEAN);
			 * }
			 * // 3.2 执行查询，获取数据
			 * trans_infoList = dataOperate.getBr24_trans_infoList(cg_q_main.getAccountnumber(), "",
			 * "");
			 * // 3.3 码表转换（即使是批后提取业务数据，也需要进行码表转换）
			 * codeChange.chgCode(trans_infoList, "BR24_TRANS_INFO", this.query_datasource);
			 * //4. 遍历流水，归集账户下的流水
			 * //4.1 取账户数
			 * HashMap<String, String> accountnumberMap = new HashMap();
			 * for(int i=0;i<trans_infoList.size();i++){
			 * Br24_trans_info trans = (Br24_trans_info)trans_infoList.get(i);
			 * accountnumberMap.put(trans.getAcountNumber(), "");
			 * }
			 * //4.2 归集账户下的流水
			 * HashMap<String, List<Br24_trans_info>> transMap = new HashMap();
			 * Iterator iter = accountnumberMap.entrySet().iterator();
			 * while (iter.hasNext()) {
			 * java.util.Map.Entry<String, String> entry = (Entry<String, String>) iter.next();
			 * String accountnumber=(String)entry.getKey();
			 * List<Br24_trans_info> n_transList = new ArrayList<Br24_trans_info>();
			 * for(int n= 0;n<trans_infoList.size();n++){
			 * Br24_trans_info trans = (Br24_trans_info)trans_infoList.get(n);
			 * if(accountnumber.equals(trans.getAcountNumber())){
			 * n_transList.add(trans);
			 * }
			 * }
			 * transMap.put(accountnumber, n_transList);
			 * }
			 * // 4.3 写账户交易流水表及相应表
			 * Iterator itertrans = transMap.entrySet().iterator();
			 * while (itertrans.hasNext()) {
			 * java.util.Map.Entry<String, List<Br24_trans_info>> entry = (Entry<String,
			 * List<Br24_trans_info>>) itertrans.next();
			 * String accountnumber=entry.getKey();
			 * List<Br24_trans_info> tranList = entry.getValue();
			 * // 写响应表
			 * String transserialnumberFK = this.geTransSerialNumber("2");
			 * br24_bas_info.setTransSerialNumber(transserialnumberFK);
			 * br24_bas_info.setTxCode(cg_q_main.getTxCode());
			 * br24_bas_info.setResult(TxConstants.CODE_OK);
			 * handleBr24_bas_info(br24_bas_info);
			 * // 写账户交易流水表
			 * insertBr24_trans_info(tranList,br24_bas_info);
			 * }
			 */
			
		} catch (Exception e) {
			// 错误统一采用异常处理
			logger.error("查询异常" + e.getMessage());
			throw e;
		}
		
	}
	
	/**
	 * 处理账户动态查询解除任务 - task2
	 * 查询数据落地100307
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public void handleQueryResponse100307(MC21_task_fact mc21_task_fact) throws Exception {
		
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		
		String transserialnumber = cg_q_main.getTransSerialNumber();
		String applicationid = cg_q_main.getOriginalapplicationid();
		String msgfrom = cg_q_main.getMessageFrom();
		Br24_bas_info br24_bas_info = new Br24_bas_info();
		//查询原动态查询的请求
		Br24_q_Main cg_q_main_old = mm24_q_mainMapper.selectBr24_q_main_OldByVo(cg_q_main);
		if (cg_q_main_old == null) {
			br24_bas_info.setResult("1600");
		}
		//查询是否被解除
		Br24_q_Main br24_dy_main = mm24_q_mainMapper.selBr24_q_acct_dynamic_main(applicationid, msgfrom);
		if (br24_dy_main != null) { //若已解除
			br24_bas_info.setResult("1601");
			mc21_task_fact.setIsemployee("0");
		}
		if (br24_bas_info.getResult().equals("0000")) {
			// 2、账户动态查询或解除任务表
			mm24_q_mainMapper.updateBr24_q_acct_dynamic_main(applicationid, msgfrom, cg_q_main.getInquiryperiodend());
		}
		// 3、获取反馈基本信息表
		
		br24_bas_info.setTransSerialNumber(transserialnumber);
		
		// 4、写反馈基本信息表 
		updateBr24_bas_info(br24_bas_info);
		
		// 5、插入任务task3
		insertMc21TaskFact3(mc21_task_fact, "DX");
		
	}
	
	/**
	 * 处理客户全账户查询任务 - task2
	 * 查询数据落地100309
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public void handleQueryResponse100309(MC21_task_fact mc21_task_fact) throws Exception {
		List<Br24_card_info> card_infoList = new ArrayList<Br24_card_info>();
		List<Br24_account_info> account_infoList = new ArrayList<Br24_account_info>();
		List<Br24_account_right> rightList = new ArrayList<Br24_account_right>();
		List<Br24_account_freeze> freezeList = new ArrayList<Br24_account_freeze>();
		
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		// 2、获取反馈基本信息表
		Br24_bas_info br24_bas_info = this.getBr24_bas_info(cg_q_main);
		
		try {
			String msgCheckResult = br24_bas_info.getMsgcheckresult();
			if (msgCheckResult != null && (msgCheckResult.equals("") || msgCheckResult.equals("1"))) { //判断是否是本行数据
				String orgkey = br24_bas_info.getOrgkey();
				
				MP02_rep_org_map reporg = this.getMp02_organPerson("2", orgkey);
				String organkey_r = reporg.getReport_organkey();
				// 3、生成卡折信息、账户信息、冻结信息、账户权利信息
				// 3.1 根据请求信息及标志，判断查询数据接口
				if (StringUtils.equals(this.query_datasource, LOCAL_QUERY)) {
					dataOperate = (DataOperate2) this.ac.getBean(LOCAL_DATA_OPERATE_NAME_DX);
					String inquirymode = cg_q_main.getInquirymode();
					String subjectype = cg_q_main.getSubjecttype();
					String cardtype = cg_q_main.getAccountcredentialtype();
					try {
						PartyQueryResult partyqueryresult = dataOperate.getPartyQuertResultList(subjectype, inquirymode, cardtype, cg_q_main.getAccountcredentialnumber(), cg_q_main.getAccountsubjectname(), organkey_r);
						if (partyqueryresult.getCard_infoList() != null) {
							card_infoList = partyqueryresult.getCard_infoList();
						}
						if (partyqueryresult.getAccountInfoList() != null) {
							account_infoList = partyqueryresult.getAccountInfoList();
						}
						if (partyqueryresult.getRightList() != null) {
							rightList = partyqueryresult.getRightList();
						}
						if (partyqueryresult.getFreezeList() != null) {
							freezeList = partyqueryresult.getFreezeList();
						}
					} catch (RemoteAccessException e) {
						e.printStackTrace();
						br24_bas_info.setResult("1199");
						br24_bas_info.setFeedbackRemark("处理异常");
					}
					
				} else {
					
					dataOperate = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
					
					HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
					HashMap zjlxMap = (HashMap) etlcodeMap.get("DXZJ");
					String cardtype = cg_q_main.getAccountcredentialtype();
					if (zjlxMap != null && zjlxMap.get(cardtype) != null) { //证件类型转成核心需要的
						cardtype = (String) zjlxMap.get(cardtype);
					}
					String inquirymode = cg_q_main.getInquirymode();
					String subjectype = cg_q_main.getSubjecttype();
					PartyQueryResult partyqueryresult = dataOperate.getPartyQuertResultList(subjectype, inquirymode, cardtype, cg_q_main.getAccountcredentialnumber(), cg_q_main.getAccountsubjectname(), organkey_r);
					if (partyqueryresult.getCard_infoList() != null) {
						card_infoList = partyqueryresult.getCard_infoList();
					}
					if (partyqueryresult.getAccountInfoList() != null) {
						account_infoList = partyqueryresult.getAccountInfoList();
					}
					if (partyqueryresult.getRightList() != null) {
						rightList = partyqueryresult.getRightList();
					}
					if (partyqueryresult.getFreezeList() != null) {
						freezeList = partyqueryresult.getFreezeList();
					}
				}
				// 3.2 执行查询，获取数据
				
				// 3.3 码表转换（即使是批后提取业务数据，也需要进行码表转换）
				codeChange.chgCode(card_infoList, "BR24_CARD_INFO", this.query_datasource);
				codeChange.chgCode(account_infoList, "BR24_ACCOUNT_INFO", this.query_datasource);
				codeChange.chgCode(rightList, "BR24_ACCOUNT_RIGHT", this.query_datasource);
				codeChange.chgCode(freezeList, "BR24_ACCOUNT_FREEZE", this.query_datasource);
				// 3.4写卡折信息表、账户信息表、账户交易流水表
				
				HashMap<String, String> cardMap = insertBr24_card_infoList(card_infoList, br24_bas_info.getTransserialnumber(), organkey_r);
				
				HashMap<String, String> acctMap = insertBr24_account_info(account_infoList, br24_bas_info, cardMap);
				if (!"01".equals(cg_q_main.getInquirymode())) {
					insertBr24_account_right(rightList, br24_bas_info, acctMap);
					insertBr24_account_freeze(freezeList, br24_bas_info, acctMap);
				}
				// 将反馈结果置为成功状态
				br24_bas_info.setResult("0000");
				br24_bas_info.setFeedbackRemark("");
			} else {
				mc21_task_fact.setIsemployee("0");
			}
		} catch (DataOperateException e) {
			// 3.3 错误统一采用异常处理
			logger.warn("数据处理异常：{}", e.getMessage());
			br24_bas_info.setResult(e.getCode());
			br24_bas_info.setFeedbackRemark(e.getDescr());
		} catch (RemoteAccessException e) {
			logger.error("应用程序异常：{}", e.getMessage(), e);
			br24_bas_info.setResult("1199");
			br24_bas_info.setFeedbackRemark("应用程序异常");
		}
		// 4、写反馈基本信息表 
		updateBr24_bas_info(br24_bas_info);
		
		// 5.插入任务task3	
		insertMc21TaskFact3(mc21_task_fact, "DX");
		
	}
	
	/**
	 * 反馈账户交易明细查询任务 - task3
	 * 100302
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public QueryRequest100302 setQueryRequest100302(MC21_task_fact mc21_task_fact) throws Exception {
		
		QueryRequest100302 request = new QueryRequest100302();
		QueryRequest_Accounts card_info = new QueryRequest_Accounts();
		List<Br24_account_info> accountList = new ArrayList<Br24_account_info>();
		List<QueryRequest_Transaction> transList = new ArrayList<QueryRequest_Transaction>();
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		// 2、获取反馈基本信息表
		Br24_bas_info br24_bas_info = this.getBr24_bas_info(cg_q_main);
		
		String transserialnumber = br24_bas_info.getTransserialnumber();
		// 3.1、获取卡折信息表、账户信息表、账户交易流水表
		List<QueryRequest_Accounts> cardList = mm24_q_mainMapper.getBr24_card_infoList(transserialnumber);
		if (cardList != null && cardList.size() > 0)
			card_info = cardList.get(0);
		
		accountList = mm24_q_mainMapper.getBr24_account_infoList(transserialnumber);
		//查询内容：01-账户基本信息；02-账户交易明细； 03-账户基本信息+交易明细；04-账户明细快速查询
		
		transList = mm24_q_mainMapper.getBr24_trans_infoList(transserialnumber);
		
		//3.2遍历账户、流水，归集账户下的流水，形成transMap
		/*
		 * HashMap<String, List<QueryRequest_Transaction>> transMap = new HashMap();
		 * for (int j = 0; j < accountList.size(); j++) {
		 * Br24_account_info account = (Br24_account_info) accountList.get(j);
		 * List<QueryRequest_Transaction> n_transList = new ArrayList<QueryRequest_Transaction>();
		 * for (int i = 0; i < transList.size(); i++) {
		 * QueryRequest_Transaction trans = (QueryRequest_Transaction) transList.get(i);
		 * //归集账户下的流水
		 * if (account.getAccountNumber().equals(trans.getAccountNumber())) {
		 * n_transList.add(trans);
		 * }
		 * }
		 * transMap.put(account.getAccountNumber(), n_transList);
		 * }
		 */
		HashMap<String, List<QueryRequest_Transaction>> transMap = this.getTransHash(transList); //替换上面的注释
		
		//3.3遍历子账户信息,组织反馈报文<Account> <Transactions>	
		List<QueryRequest_Account> n_accountList = new ArrayList<QueryRequest_Account>();
		for (int n = 0; n < accountList.size(); n++) {
			Br24_account_info accountinfo = (Br24_account_info) accountList.get(n);
			QueryRequest_Account requestAccount = new QueryRequest_Account();
			if (transMap.containsKey(accountinfo.getAccountNumber())) {
				requestAccount.setTransactions(transMap.get(accountinfo.getAccountNumber()));
			}
			requestAccount.setAccountNumber(accountinfo.getAccountNumber());
			requestAccount.setAccountSerial(accountinfo.getAccountSerial());
			requestAccount.setAccountType(accountinfo.getAccountType());
			requestAccount.setAccountStatus(accountinfo.getAccountStatus());
			requestAccount.setCurrency(accountinfo.getCurrency());
			requestAccount.setCashRemit(accountinfo.getCashRemit());
			requestAccount.setAccountBalance(accountinfo.getAccountBalance());
			requestAccount.setAvailableBalance(accountinfo.getAvailableBalance());
			requestAccount.setLastTransactionTime(accountinfo.getLastTransactionTime());
			
			n_accountList.add(requestAccount);
		}
		
		//组织反馈报文：<Head>
		request.setMode(br24_bas_info.getModeid());
		request.setTo(br24_bas_info.getToorg());
		request.setTxCode(br24_bas_info.getTxcode());
		transserialnumber = this.getTransSerialNumber("2", br24_bas_info.getOrgkey());
		request.setTransSerialNumber(transserialnumber);
		
		//组织反馈报文：<Body>
		request.setApplicationID(cg_q_main.getApplicationid());
		request.setResult(br24_bas_info.getResult());
		request.setOperatorName(br24_bas_info.getOperatorname());
		request.setOperatorPhoneNumber(br24_bas_info.getOperatorphonenumber());
		request.setFeedbackOrgName(br24_bas_info.getFeedbackorgname());
		request.setFeedbackRemark(br24_bas_info.getFeedbackremark());
		
		//组织反馈报文：<Accounts>
		QueryRequest_Accounts accounts = new QueryRequest_Accounts();
		BeanUtils.copyProperties(accounts, card_info);
		accounts.setList(n_accountList);
		request.setAccounts(accounts);
		
		return request;
	}
	
	/*
	 * transInfos :交易
	 */
	public HashMap<String, List<QueryRequest_Transaction>> getTransHash(List<QueryRequest_Transaction> transInfos) throws Exception {
		
		//4.2 归集账户下的流水
		HashMap<String, List<QueryRequest_Transaction>> transMap = new HashMap<String, List<QueryRequest_Transaction>>();
		for (int i = 0; i < transInfos.size(); i++) {
			QueryRequest_Transaction trans = (QueryRequest_Transaction) transInfos.get(i);
			String accountnumber = trans.getAccountNumber();
			if (transMap.containsKey(accountnumber)) {
				List<QueryRequest_Transaction> n_transList = transMap.get(accountnumber);
				n_transList.add(trans);
			} else {
				List<QueryRequest_Transaction> n_transList = new ArrayList<QueryRequest_Transaction>();
				n_transList.add(trans);
				transMap.put(accountnumber, n_transList);
			}
		}
		
		return transMap;
	}
	
	/**
	 * 反馈账户持卡主体查询任务 - task3
	 * 100304
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public QueryRequest100304 setQueryRequest100304(MC21_task_fact mc21_task_fact) throws Exception {
		
		QueryRequest100304 request = new QueryRequest100304();
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		String transserialnumber = cg_q_main.getTransSerialNumber();
		// 2、获取反馈基本信息表
		Br24_bas_info br24_bas_info = mm24_q_mainMapper.selectBr24_bas_infoById(transserialnumber);
		// 3、获取持卡主体信息表
		Br24_account_holder _holder = mm24_q_mainMapper.selectBr24_account_holderByVo(br24_bas_info.getTransserialnumber());
		if (_holder != null) {
			BeanUtils.copyProperties(request, _holder);
		}
		
		//组织反馈报文：Head
		request.setMode(br24_bas_info.getModeid());
		request.setTo(br24_bas_info.getToorg());
		request.setTxCode(br24_bas_info.getTxcode());
		String transnum = this.getTransSerialNumber("2", br24_bas_info.getOrgkey());
		request.setTransSerialNumber(transnum);
		
		//组织反馈报文：Body
		request.setApplicationID(br24_bas_info.getApplicationid());
		request.setResult(br24_bas_info.getResult());
		request.setOperatorName(br24_bas_info.getOperatorname());
		request.setOperatorPhoneNumber(br24_bas_info.getOperatorphonenumber());
		request.setFeedbackOrgName(br24_bas_info.getFeedbackorgname());
		request.setFeedbackRemark(br24_bas_info.getFeedbackremark());
		
		return request;
		
	}
	
	/**
	 * 反馈账户动态查询任务 - task3
	 * 100306
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public QueryRequest100306 setQueryRequest100306(MC21_task_fact mc21_task_fact) throws Exception {
		QueryRequest100306 request = new QueryRequest100306();
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		// 2、获取反馈基本信息表
		Br24_bas_info br24_bas_info = mm24_q_mainMapper.selectBr24_bas_infoById(mc21_task_fact.getTgroupid());
		
		// 3、获取账户交易流水表
		List<QueryRequest_Transaction> transList = mm24_q_mainMapper.getBr24_trans_infoList(br24_bas_info.getTransserialnumber());
		
		//组织反馈报文：Head
		request.setMode(br24_bas_info.getModeid());
		request.setTo(br24_bas_info.getToorg());
		request.setTxCode(br24_bas_info.getTxcode());
		request.setTransSerialNumber(this.getTransSerialNumber("2", br24_bas_info.getOrgkey()));
		
		//组织反馈报文：Body
		
		if (transList != null && transList.size() > 0) {
			String accountNumber = transList.get(0).getAccountNumber();
			request.setAccountNumber(accountNumber);
		}
		request.setApplicationID(cg_q_main.getApplicationid());
		request.setResult(br24_bas_info.getResult());
		request.setOperatorName(br24_bas_info.getOperatorname());
		request.setOperatorPhoneNumber(br24_bas_info.getOperatorphonenumber());
		request.setFeedbackOrgName(br24_bas_info.getFeedbackorgname());
		request.setFeedbackRemark(br24_bas_info.getFeedbackremark());
		request.setAccountName(cg_q_main.getAccountname());
		request.setCardNumber(cg_q_main.getAccountnumber());
		
		request.setTransactions(transList);
		
		return request;
	}
	
	/**
	 * 反馈账户动态查询解除任务 - task3
	 * 100308
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public QueryRequest100308 setQueryRequest100308(MC21_task_fact mc21_task_fact) throws Exception {
		
		QueryRequest100308 request = new QueryRequest100308();
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		// 2、获取反馈基本信息表
		Br24_bas_info br24_bas_info = this.getBr24_bas_info(cg_q_main);
		
		//组织反馈报文：Head
		request.setMode(br24_bas_info.getModeid());
		request.setTo(br24_bas_info.getToorg());
		request.setTxCode(br24_bas_info.getTxcode());
		request.setTransSerialNumber(this.getTransSerialNumber("2", br24_bas_info.getOrgkey()));
		
		//组织反馈报文：Body
		request.setApplicationID(cg_q_main.getApplicationid());
		request.setResult(br24_bas_info.getResult());
		request.setCaseNumber(cg_q_main.getCasenumber());
		request.setApplicationOrgID(cg_q_main.getApplicationorgid());
		request.setApplicationOrgName(cg_q_main.getApplicationorgname());
		request.setApplicationTime(cg_q_main.getApplicationtime());
		
		request.setOperatorName(br24_bas_info.getOperatorname());
		request.setOperatorPhoneNumber(br24_bas_info.getOperatorphonenumber());
		
		request.setBankID(cg_q_main.getBankid());
		request.setBankName(cg_q_main.getBankname());
		request.setAccountName(cg_q_main.getAccountname());
		request.setCardNumber(cg_q_main.getAccountnumber());
		request.setWithdrawalRemark("");
		request.setFeedbackRemark(br24_bas_info.getFeedbackremark());
		
		return request;
		
	}
	
	/**
	 * 反馈客户全账户查询任务 - task3
	 * 100310
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public QueryRequest100310 setQueryRequest100310(MC21_task_fact mc21_task_fact) throws Exception {
		
		QueryRequest100310 request = new QueryRequest100310();
		// 1、根据任务信息，获取查询请求信息
		Br24_q_Main cg_q_main = this.getCg_Q_Main(mc21_task_fact);
		// 2、获取反馈基本信息表
		Br24_bas_info br24_bas_info = this.getBr24_bas_info(cg_q_main);
		// 3、获取卡折信息表、账户信息表、账户权利信息表、冻结信息表
		List<QueryRequest_Accounts> card_infoList = mm24_q_mainMapper.getBr24_card_infoList(br24_bas_info.getTransserialnumber());
		List<Br24_account_info> account_infoList = mm24_q_mainMapper.getBr24_account_infoList(br24_bas_info.getTransserialnumber());
		
		List<QueryRequest_Accounts> accountsList = new ArrayList<QueryRequest_Accounts>();
		for (int i = 0; i < card_infoList.size(); i++) {
			QueryRequest_Accounts cardinfo = (QueryRequest_Accounts) card_infoList.get(i);
			List<QueryRequest_Account> n_account_infoList = new ArrayList<QueryRequest_Account>();
			for (int j = 0; j < account_infoList.size(); j++) {
				Br24_account_info accountinfo = (Br24_account_info) account_infoList.get(j);
				QueryRequest_Account requestAccount = new QueryRequest_Account();
				if (cardinfo.getCardNumber().equals(accountinfo.getCardNumber())) {
					requestAccount.setAccountNumber(accountinfo.getAccountNumber());
					requestAccount.setAccountSerial(accountinfo.getAccountSerial());
					requestAccount.setAccountType(accountinfo.getAccountType());
					requestAccount.setAccountStatus(accountinfo.getAccountStatus());
					requestAccount.setCurrency(accountinfo.getCurrency());
					requestAccount.setCashRemit(accountinfo.getCashRemit());
					requestAccount.setAccountBalance(accountinfo.getAccountBalance());
					requestAccount.setAvailableBalance(accountinfo.getAvailableBalance());
					requestAccount.setLastTransactionTime(accountinfo.getLastTransactionTime());
					n_account_infoList.add(requestAccount);
				}
			}
			cardinfo.setList(n_account_infoList);
			accountsList.add(cardinfo);
		}
		
		//组织反馈报文：Head
		request.setMode(br24_bas_info.getModeid());
		request.setTo(br24_bas_info.getToorg());
		request.setTxCode(br24_bas_info.getTxcode());
		request.setTransSerialNumber(this.getTransSerialNumber("2", br24_bas_info.getOrgkey()));
		
		//组织反馈报文：Body
		request.setApplicationID(cg_q_main.getApplicationid());
		request.setResult(br24_bas_info.getResult());
		request.setOperatorName(br24_bas_info.getOperatorname());
		request.setOperatorPhoneNumber(br24_bas_info.getOperatorphonenumber());
		request.setFeedbackOrgName(br24_bas_info.getFeedbackorgname());
		request.setFeedbackRemark(br24_bas_info.getFeedbackremark());
		
		request.setAccounts(accountsList);
		
		//查询内容：01-账户基本信息；02-账户信息(含强制措施、共有权/优先权信息)
		if ("02".equals(cg_q_main.getInquirymode())) {
			List<QueryRequest_Priority> rightList = mm24_q_mainMapper.getBr24_account_rightList(br24_bas_info.getTransserialnumber());
			List<QueryRequest_Measure> freezeList = mm24_q_mainMapper.getBr24_account_freezeList(br24_bas_info.getTransserialnumber());
			request.setMeasures(freezeList);
			request.setPriorities(rightList);
		} else {
			request.setMeasures(null);
			request.setPriorities(null);
		}
		
		return request;
		
	}
	
	/**
	 * 写持卡主体主表
	 * 
	 * @throws Exception
	 */
	public void insertBr24_account_holder(Br24_account_holder br24_account_holder, Br24_q_Main cg_q_main) throws Exception {
		//先删除
		mm24_q_mainMapper.delBr24_account_holder(cg_q_main.getTransSerialNumber());
		//再插入
		br24_account_holder.setQrydt(DtUtils.getNowDate());
		br24_account_holder.setCardnumber(cg_q_main.getAccountnumber());
		mm24_q_mainMapper.insertBr24_account_holder(br24_account_holder);
	}
	
	/**
	 * 写卡折信息表
	 * 
	 * @throws Exception
	 */
	public void insertBr24_card_info(Br24_card_info br24_card_info, String transserialnumber) throws Exception {
		//先删除
		mm24_q_mainMapper.delBr24_card_info(transserialnumber);
		//再插入
		br24_card_info.setQrydt(DtUtils.getNowDate());
		br24_card_info.setTransSerialNumber(transserialnumber);
		//人行开户网点显示现代化止付系统行号
		CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
		HashMap transtypeHash = (HashMap<String, Object>) cacheService.getCache("ReHangOrganMap", HashMap.class);
		String depositBankBranchCode = br24_card_info.getDepositBankBranchCode();
		String depositBankBranch = br24_card_info.getDepositBankBranch();
		if (transtypeHash != null && transtypeHash.get(depositBankBranchCode) != null) {
			BB13_sys_para syspara = (BB13_sys_para) transtypeHash.get(depositBankBranchCode);
			depositBankBranchCode = syspara.getVals();
			depositBankBranch = syspara.getCodename();
		}
		br24_card_info.setDepositBankBranch(depositBankBranch);
		br24_card_info.setDepositBankBranchCode(depositBankBranchCode);
		mm24_q_mainMapper.insertBr24_card_info(br24_card_info);
	}
	
	/**
	 * 写账户信息表
	 * 
	 * @throws Exception
	 */
	public void insertBr24_account_info(List<Br24_account_info> account_infoList, Br24_bas_info _bas_info) throws Exception {
		//先删除
		mm24_q_mainMapper.delBr24_account_info(_bas_info.getTransserialnumber());
		//再插入
		if (account_infoList != null) {
			for (int i = 0; i < account_infoList.size(); i++) {
				Br24_account_info info = (Br24_account_info) account_infoList.get(i);
				info.setQrydt(DtUtils.getNowDate());
				info.setTransSerialNumber(_bas_info.getTransserialnumber());
				mm24_q_mainMapper.insertBr24_account_info(info);
			}
		}
	}
	
	public HashMap<String, String> insertBr24_account_info(List<Br24_account_info> account_infoList, Br24_bas_info _bas_info, HashMap<String, String> cardMap) throws Exception {
		//先删除
		mm24_q_mainMapper.delBr24_account_info(_bas_info.getTransserialnumber());
		HashMap<String, String> map = new HashMap<String, String>();
		//再插入
		if (account_infoList != null) {
			boolean isMultiType = ServerEnvironment.getBooleanValue(Keys.MULTI_CORPORATION_TYPE);
			for (int i = 0; i < account_infoList.size(); i++) {
				Br24_account_info info = (Br24_account_info) account_infoList.get(i);
				String cardNumber = info.getCardNumber();
				if (!isMultiType || cardMap.containsKey(cardNumber)) {
					info.setQrydt(DtUtils.getNowDate());
					info.setTransSerialNumber(_bas_info.getTransserialnumber());
					mm24_q_mainMapper.insertBr24_account_info(info);
					map.put(info.getAccountNumber(), "");
				}
			}
		}
		return map;
	}
	
	/**
	 * 写账户交易流水表
	 * 
	 * @throws Exception
	 */
	public String insertBr24_trans_info(List<Br24_trans_info> trans_infoList, String transserialnumber, String cardnum) throws Exception {
		String flag = "0";
		//先删除
		mm24_q_mainMapper.delBr24_trans_info(transserialnumber);
		//再插入
		if (trans_infoList != null) {
			for (int i = 0; i < trans_infoList.size(); i++) {
				Br24_trans_info info = (Br24_trans_info) trans_infoList.get(i);
				if (info.getTransseq() != null && !info.getTransseq().equals("")) {
					
				} else {
					info.setTransseq(i + "");
				}
				info.setQrydt(DtUtils.getNowDate());
				info.setTransSerialNumber(transserialnumber);
				info.setCardNumber(cardnum);
				mm24_q_mainMapper.insertBr24_trans_info(info);
			}
			if (trans_infoList.size() > 1000) { //若超过1000的按时间排除删除
				mm24_q_mainMapper.deleteBr24_trans_info(transserialnumber);
				flag = "1";
			}
		}
		return flag;
	}
	
	/**
	 * 写卡折信息表
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> insertBr24_card_infoList(List<Br24_card_info> card_infoList, String transserialnumber, String organkey_r) throws Exception {
		//先删除
		mm24_q_mainMapper.delBr24_card_info(transserialnumber);
		HashMap<String, String> map = new HashMap<String, String>();
		//人行开户网点显示现代化止付系统行号
		CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
		HashMap transtypeHash = (HashMap<String, Object>) cacheService.getCache("ReHangOrganMap", HashMap.class);
		//再插入
		if (card_infoList != null) {
			boolean isMultiType = ServerEnvironment.getBooleanValue(Keys.MULTI_CORPORATION_TYPE);
			for (int i = 0; i < card_infoList.size(); i++) {
				Br24_card_info info = (Br24_card_info) card_infoList.get(i);
				String comorgankey = info.getDepositBankBranchCode();
				if (!isMultiType || this.isOrgan(organkey_r, comorgankey)) {
					info.setQrydt(DtUtils.getNowDate());
					info.setTransSerialNumber(transserialnumber);
					String depositBankBranchCode = info.getDepositBankBranchCode();
					String depositBankBranch = info.getDepositBankBranch();
					if (transtypeHash != null && transtypeHash.get(depositBankBranchCode) != null) {
						BB13_sys_para syspara = (BB13_sys_para) transtypeHash.get(depositBankBranchCode);
						depositBankBranchCode = syspara.getVals();
						depositBankBranch = syspara.getCodename();
					}
					info.setDepositBankBranch(depositBankBranch);
					info.setDepositBankBranchCode(depositBankBranchCode);
					mm24_q_mainMapper.insertBr24_card_info(info);
					map.put(info.getCardNumber(), "");
				}
				
			}
		}
		return map;
	}
	
	/**
	 * 写账户权利信息表
	 * 
	 * @throws Exception
	 */
	public void insertBr24_account_right(List<Br24_account_right> rightList, Br24_bas_info _bas_info, HashMap<String, String> acctMap) throws Exception {
		//先删除
		mm24_q_mainMapper.delBr24_account_right(_bas_info.getTransserialnumber());
		//再插入
		if (rightList != null) {
			boolean isMultiType = ServerEnvironment.getBooleanValue(Keys.MULTI_CORPORATION_TYPE);
			for (int i = 0; i < rightList.size(); i++) {
				Br24_account_right info = (Br24_account_right) rightList.get(i);
				String acctnum = info.getAccountNumber();
				if (!isMultiType || acctMap.containsKey(acctnum)) {
					info.setQrydt(DtUtils.getNowDate());
					info.setTransSerialNumber(_bas_info.getTransserialnumber());
					mm24_q_mainMapper.insertBr24_account_right(info);
				}
			}
		}
	}
	
	/**
	 * 写冻结信息表
	 * 
	 * @throws Exception
	 */
	public void insertBr24_account_freeze(List<Br24_account_freeze> freezeList, Br24_bas_info _bas_info, HashMap<String, String> acctMap) throws Exception {
		//先删除
		mm24_q_mainMapper.delBr24_account_freeze(_bas_info.getTransserialnumber());
		//再插入
		if (freezeList != null) {
			boolean isMultiType = ServerEnvironment.getBooleanValue(Keys.MULTI_CORPORATION_TYPE);
			for (int i = 0; i < freezeList.size(); i++) {
				Br24_account_freeze info = (Br24_account_freeze) freezeList.get(i);
				String acctnum = info.getAccountNumber();
				if (!isMultiType || acctMap.containsKey(acctnum)) {
					info.setQrydt(DtUtils.getNowDate());
					info.setTransSerialNumber(_bas_info.getTransserialnumber());
					mm24_q_mainMapper.insertBr24_account_freeze(info);
				}
			}
		}
	}
	
	/**
	 * 根据任务信息（传输报文流水号、业务申请编号），获取查询请求信息
	 * 
	 * @param mc21_task_fact
	 * @return
	 */
	private Br24_q_Main getCg_Q_Main(MC21_task_fact mc21_task_fact) {
		String transSerialNumber = mc21_task_fact.getBdhm();
		Br24_q_Main cg_q_main = mm24_q_mainMapper.selectBr24_q_mainByVo(transSerialNumber);
		return cg_q_main;
	}
	
	/**
	 * 获取反馈基本信息表
	 * 
	 * @param cg_q_main
	 * @return
	 * @throws Exception
	 */
	private Br24_bas_info getBr24_bas_info(Br24_q_Main cg_q_main) throws Exception {
		Br24_bas_info br24_bas_info = mm24_q_mainMapper.selectBr24_bas_infoByVo(cg_q_main);
		if (br24_bas_info == null) {
			br24_bas_info = new Br24_bas_info();
		}
		
		br24_bas_info.setMode(br24_bas_info.getModeid());
		return br24_bas_info;
	}
	
	/**
	 * 处理反馈信息表
	 * 
	 * @param br24_bas_info
	 * @throws Exception
	 */
	public void updateBr24_bas_info(Br24_bas_info br24_bas_info) throws Exception {
		/** 反馈日期,默认取当前期 */
		br24_bas_info.setFeedback_dt(DtUtils.getNowDate());
		br24_bas_info.setStatus("1");
		mm24_q_mainMapper.updateBr24_bas_info(br24_bas_info);
	}
	
	public void handleBr24_bas_info(Br24_bas_info br24_bas_info) throws Exception {
		
		MP02_rep_org_map rep_org = this.getMp02_organPerson("2", br24_bas_info.getOrgkey());
		String operatorName = rep_org.getOperatorname();
		String operatorPhoneNumber = rep_org.getOperatorphonenumber();
		String feedbackOrgName = rep_org.getOrganname();
		
		br24_bas_info.setMode("01");
		br24_bas_info.setTxCode(String.valueOf(Integer.parseInt(br24_bas_info.getTxcode()) + 1));
		br24_bas_info.setOperatorName(operatorName);
		br24_bas_info.setOperatorPhoneNumber(operatorPhoneNumber);
		br24_bas_info.setFeedbackOrgName(feedbackOrgName);
		br24_bas_info.setQrydt(DtUtils.getNowDate());
		br24_bas_info.setStatus("0");
		
		mm24_q_mainMapper.insertBr24_bas_info(br24_bas_info);
	}
	
	public void insertBr24_q_acct_dynamic_main(Br24_q_Main cg_q_main) throws Exception {
		
		// 删除请求单号下的数据
		mm24_q_mainMapper.delBr24_q_acct_dynamic_main(cg_q_main.getTransSerialNumber());
		
		cg_q_main.setStatus("1");//状态  0：已解除  1：执行中
		cg_q_main.setInterceptionenddate(cg_q_main.getInquiryperiodend());
		cg_q_main.setLastpollingtime(cg_q_main.getInquiryperiodstart());
		cg_q_main.setQrydt(DtUtils.getNowDate());
		mm24_q_mainMapper.insertBr24_q_acct_dynamic_main(cg_q_main);
	}
	
	public boolean isOrgan(String organkey_r, String comorgankey) throws Exception {
		boolean flag = true;
		if (organkey_r != null && !organkey_r.equals("")) {
			HashMap repOrgHash = (HashMap<String, Object>) cacheService.getCache("Mp02_repOrgMapDetail", HashMap.class);
			HashMap orgMap = (HashMap) repOrgHash.get(organkey_r);
			if (!orgMap.containsKey(comorgankey)) {
				flag = false;
			}
		}
		
		return flag;
	}
}