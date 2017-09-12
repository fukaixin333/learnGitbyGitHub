package com.citic.server.jsga.task.taskBo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.jsga.domain.Br40_cxqq;
import com.citic.server.jsga.domain.Br40_cxqq_back;
import com.citic.server.jsga.domain.Br40_cxqq_mx;
import com.citic.server.jsga.domain.Br42_msg;
import com.citic.server.jsga.domain.JSGA_ReturnReceipt;
import com.citic.server.jsga.domain.request.JSGA_ControlReceiptRequest;
import com.citic.server.jsga.domain.request.JSGA_ControlRecordRequest;
import com.citic.server.jsga.domain.request.JSGA_ControlRequest_Receipt;
import com.citic.server.jsga.domain.request.JSGA_ControlRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_ExecResult;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Account;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Customer;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Measure;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Priority;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_SubAccount;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Transaction;
import com.citic.server.jsga.mapper.MM40_cxqq_jsgaMapper;
import com.citic.server.jsga.service.IDataOperate12;
import com.citic.server.jsga.service.RequestMessageService12;
import com.citic.server.runtime.Constants12;
import com.citic.server.runtime.JsgaKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FileUtils;

public class Cxqq_FKBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_FKBo.class);
	private MM40_cxqq_jsgaMapper br40_cxqqMapper;
	private IDataOperate12 dataOperate;
	
	public Cxqq_FKBo(ApplicationContext ac) {
		super(ac);
		this.br40_cxqqMapper = (MM40_cxqq_jsgaMapper) ac.getBean("MM40_cxqq_jsgaMapper");
		dataOperate = (IDataOperate12) this.ac.getBean(Constants12.REMOTE_DATA_OPERATE_NAME_JSGA);
	}
	
	public void delMSG(MC21_task_fact mc21_task_fact) throws Exception {
		Br42_msg msg = new Br42_msg();
		msg.setPacketkey(mc21_task_fact.getBdhm());
		msg.setTasktype(mc21_task_fact.getTasktype());
		br40_cxqqMapper.delBr42_msg(msg);
	}
	
	/**
	 * 根据请求单标识，获取查询请求信息
	 * 
	 * @param mc21_task_fact
	 * @return
	 */
	private Br40_cxqq getBr40_cxqqFortask3(MC21_task_fact mc21_task_fact) {
		Br40_cxqq br40_cxqq = new Br40_cxqq();
		//br40_cxqq.setQqdbs(mc21_task_fact.getBdhm());//请求单标识
		br40_cxqq.setQqdbs(StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$"));//请求单标识
		br40_cxqq.setTasktype(mc21_task_fact.getTasktype());//监管类别
		br40_cxqq = br40_cxqqMapper.selectBr40_cxqqByVo(br40_cxqq);
		br40_cxqq.setRwlsh(StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$"));
		return br40_cxqq;
	}
	
	/**
	 * 根据请求单标识，监管类别获取查询请求主体信息
	 * 
	 * @param mc21_task_fact
	 * @return
	 */
	private Br40_cxqq_mx getQueryRequest_mx(Br40_cxqq br40_cxqq) {
		Br40_cxqq_mx queryrequest_mx = br40_cxqqMapper.selectBr40_cxqq_mxByVo(br40_cxqq);
		if (queryrequest_mx == null) {
			queryrequest_mx = new Br40_cxqq_mx();
		}
		return queryrequest_mx;
	}
	
	/**
	 * 获取反馈基本信息表
	 * 
	 * @param cg_q_main
	 * @return
	 * @throws Exception
	 */
	private Br40_cxqq_back getBr40_cxqq_back(Br40_cxqq br40_cxqq) throws Exception {
		Br40_cxqq_back br40_cxqq_back = br40_cxqqMapper.selectBr40_cxqq_backByVo(br40_cxqq);
		if (br40_cxqq_back == null) {
			br40_cxqq_back = new Br40_cxqq_back();
		}
		return br40_cxqq_back;
	}
	
	/**
	 * 处理反馈信息表
	 * 
	 * @param br24_bas_info
	 * @throws Exception
	 */
	public void updateBr40_cxqq_back(Br40_cxqq_back br40_cxqq_back) throws Exception {
		/** 查询结束时间,默认取当前期 */
		br40_cxqq_back.setCxfkjg("01");
		br40_cxqq_back.setCxjssj(DtUtils.getNowDate());
		br40_cxqq_back.setStatus("1");
		br40_cxqqMapper.updateBr40_cxqq_back(br40_cxqq_back);
		
	}
	
	/**
	 * 常规查询反馈
	 * <p>
	 * 在根据账卡号查询时
	 * <ol>
	 * <li>不反馈强制措施、共有权、子账户等附加信息；
	 * <li>账户信息与客户信息“拉平”，所以必须保证账户信息只能有一条，然后通过Jibx的binding文件生成“拉平”的报文。
	 * </ol>
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public void feedBackQuery_Cg(MC21_task_fact mc21_task_fact) throws Exception {
		Br40_cxqq br40_cxqq = this.getBr40_cxqqFortask3(mc21_task_fact);
		List<Br40_cxqq_mx> mxList = br40_cxqqMapper.getBr40_cxqq_mxList(br40_cxqq);
		String taskType = mc21_task_fact.getTasktype(); // 任务类型
		String yhzjsjsj = Utility.currDateTime19();
		// 1.获取查询任务数据
		for (Br40_cxqq_mx br40_cxqq_mx : mxList) {
			String cxnr = br40_cxqq_mx.getCxnr(); // 查询内容分三种（01-账户信息；02-账户交易明细；03-账户信息和交易明细）
			
			// 查询方式 - 用于判断根据账（卡）号或证照查询
			String queryType = null;
			
			String cxlx = br40_cxqq_mx.getCxlx();
			if ("01".equals(cxlx)) { // 01 - 根据证照查询
				queryType = "01"; // 默认指定为根据三证查询
			}
			
			// 特殊字段：基本数据项 - 操作失败原因
			String czsbyy_customer = ""; // 默认为空
			String czsbyy_trans = ""; // 默认为空
			// 两个特殊标记值
			boolean isQueryAccount = queryType == null || queryType.length() == 0; // 是否账卡号查询
			
			List<JSGA_QueryRequest_Transaction> transactionList = null;
			JSGA_QueryRequest_Customer customer;
			if (Constants12.CXNR_TRANSACTION.equals(cxnr)) {
				// 交易流水信息
				transactionList = br40_cxqqMapper.selectBr40_cxqq_back_trans(br40_cxqq_mx);
				if (transactionList == null || transactionList.size() == 0) {
					transactionList = new ArrayList<JSGA_QueryRequest_Transaction>();
					Br40_cxqq cxqq = new Br40_cxqq();
					BeanUtils.copyProperties(cxqq, br40_cxqq_mx);
					Br40_cxqq_back back = br40_cxqqMapper.selectBr40_cxqq_backByVo(cxqq);
					
					JSGA_QueryRequest_Transaction transaction = new JSGA_QueryRequest_Transaction();
					transaction.setRwlsh(br40_cxqq_mx.getRwlsh());
					
					// 这里存在两种情况：
					//   1、与核心查询交易失败，这种情况直接使用转码后的核心错误信息；
					//   2、与核心查询交易成功，但（在时间段内）没有查询到交易流水，此种情况强制将反馈信息置为"失败 - 查无交易流水"，
					//      否则监管无法正确解析反馈报文。
					String cxfkjg = back.getCxfkjg();
					String cxfkjgyy = back.getCzsbyy();
					if ("01".equals(cxfkjg)) { // 01-成功
						cxfkjg = "02"; // 02-失败
						cxfkjgyy = "查无交易流水";
					}
					//查无流水时要求返回查询基本信息
					if ("02".equals(cxfkjg)) {
						transaction.setCxkh(br40_cxqq_mx.getCxzh());
						transaction.setKhmc(br40_cxqq_mx.getZtmc());
						transaction.setZzlx(br40_cxqq_mx.getZzlx());
						transaction.setZzhm(br40_cxqq_mx.getZzhm());
						transaction.setZh(br40_cxqq_mx.getZh());
					}
					transaction.setCxfkjg(cxfkjg); // 
					transaction.setCxfkjgyy(cxfkjgyy); // 
					transactionList.add(transaction);
					czsbyy_trans = "失败"; //
				}
				customer = new JSGA_QueryRequest_Customer();
				customer.setTransactionList(transactionList);
			} else {
				// 客户信息
				customer = br40_cxqqMapper.selectBr40_cxqq_back_party(br40_cxqq_mx);
				if (customer == null) { // 这里只会出现 Task2 处理失败的情况吗？
					Br40_cxqq cxqq = new Br40_cxqq();
					BeanUtils.copyProperties(cxqq, br40_cxqq_mx);
					Br40_cxqq_back back = br40_cxqqMapper.selectBr40_cxqq_backByVo(cxqq);
					
					customer = new JSGA_QueryRequest_Customer();
					customer.setQqdbs(br40_cxqq_mx.getQqdbs());
					customer.setRwlsh(br40_cxqq_mx.getRwlsh());
					if ("01".equals(queryType)) {
						customer.setZzlx(br40_cxqq_mx.getZzlx());
						customer.setZzhm(br40_cxqq_mx.getZzhm());
						customer.setKhmc(br40_cxqq_mx.getZtmc());
					} else {
						// <Start> chenjie
						List<JSGA_QueryRequest_Account> accountList = new ArrayList<JSGA_QueryRequest_Account>();
						JSGA_QueryRequest_Account account = new JSGA_QueryRequest_Account();
						account.setKh(back.getKh());
						accountList.add(account);
						customer.setAccountList(accountList);
						// <End> chenjie
					}
					String cxfkjg = back.getCxfkjg();
					String cxfkjgyy = back.getCzsbyy();
					if ("01".equals(cxfkjg)) { // 01-成功
						cxfkjg = "02"; // 02-失败
						cxfkjgyy = "查无客户信息";
					}
					customer.setCxfkjg(cxfkjg);
					customer.setCxfkjgyy(cxfkjgyy);
					czsbyy_customer = "失败"; //
				} else {
					// 账户信息
					List<JSGA_QueryRequest_Account> accountList = br40_cxqqMapper.selectBr40_cxqq_back_acct(br40_cxqq_mx);
					customer.setAccountList(accountList);
					
					// 根据查询方式是否有值，判断是根据证照查询或是根据账卡号查询
					
					// 强制措施信息
					List<JSGA_QueryRequest_Measure> measureList = br40_cxqqMapper.selectBr40_cxqq_back_acct_qzcs(br40_cxqq_mx);
					customer.setMeasureList(measureList);
					
					// 共有权/优先权信息
					List<JSGA_QueryRequest_Priority> prioritiesList = br40_cxqqMapper.selectBr40_cxqq_back_acct_ql(br40_cxqq_mx);
					customer.setPrioritiesList(prioritiesList);
					
					// 子账户信息
					List<JSGA_QueryRequest_SubAccount> subAccountList = br40_cxqqMapper.selectBr40_cxqq_back_acct_sub(br40_cxqq_mx);
					customer.setSubAccountList(subAccountList);
					
					// 交易流水信息
					if (Constants12.CXNR_ACC_AND_TRANS.equals(cxnr)) { // 查询内容 = 账户信息和交易明细
						transactionList = br40_cxqqMapper.selectBr40_cxqq_back_trans(br40_cxqq_mx);
						if (transactionList == null || transactionList.size() == 0) {
							transactionList = new ArrayList<JSGA_QueryRequest_Transaction>();
							JSGA_QueryRequest_Transaction transaction = new JSGA_QueryRequest_Transaction();
							transaction.setRwlsh(br40_cxqq_mx.getRwlsh());
							transaction.setCxfkjg("02"); // 02-失败
							transaction.setCxfkjgyy("查无交易流水");
							transactionList.add(transaction);
							czsbyy_trans = "失败"; // 
						}
						customer.setTransactionList(transactionList);
					}
				}
				
				if (isQueryAccount) {
					// 这里有两种处理方式：
					//   1、强制限制List<CBRC_QueryRequest_Account>的实体只有一个；
					//   2、Jibx的binding文件在生成报文时，将账户信息的Collection作为实际第一层，若这样处理，即使真出现多个账户信息时，
					//      最终报文格式是多个<Account>标签，只不过这些标签中的客户信息是一样的，而深圳公安局端恰好支持这种格式的报文。
					// 因此，优先采用第2种方式，进行对象的相互引用，灵活性、兼容性更高，即：A引用B，B引用A。
					List<JSGA_QueryRequest_Account> accountList = customer.getAccountList();
					if (accountList == null || accountList.size() == 0) {
						// B引用A
						JSGA_QueryRequest_Account account = new JSGA_QueryRequest_Account();
						account.setCustomer(customer);
						// A引用B
						accountList = new ArrayList<JSGA_QueryRequest_Account>();
						accountList.add(account);
						customer.setAccountList(accountList);
					} else {
						for (JSGA_QueryRequest_Account account : accountList) {
							account.setCustomer(customer);
						}
					}
				}
			}
			//查无流水时要求返回查询基本信息
			if ("02".equalsIgnoreCase(customer.getCxfkjg())) {
				customer.setKhmc(br40_cxqq_mx.getZtmc());
				customer.setZzlx(br40_cxqq_mx.getZzlx());
				customer.setZzhm(br40_cxqq_mx.getZzhm());
			}
			List<JSGA_QueryRequest_Customer> customerList = new ArrayList<JSGA_QueryRequest_Customer>();
			customerList.add(customer);
			
			// 生成XML报文
			JSGA_QueryRequest queryRequest = new JSGA_QueryRequest();
			queryRequest.setQqdbs(br40_cxqq.getQqdbs());
			queryRequest.setZtlb(br40_cxqq_mx.getZtlb());
			queryRequest.setSqjgdm(br40_cxqq.getSqjgdm());
			queryRequest.setMbjgdm(br40_cxqq.getMbjgdm());
			queryRequest.setCxjssj(Utility.currDateTime19());
			queryRequest.setHzsj(Utility.currDateTime19());
			queryRequest.setYhzjsjsj(yhzjsjsj);
			queryRequest.setSfxd("01"); // 此处如果支持重发修订模式，则应设置为'02'
			queryRequest.setCustomerList(customerList);
			
			if (Constants12.CXNR_ACCOUNT.equals(cxnr) || Constants12.CXNR_ACC_AND_TRANS.equals(cxnr)) {
				queryRequest.setCzsbyy(czsbyy_customer); //
				String xmlType = Constants12.valueOf(br40_cxqq_mx.getXmltype(), "SS01"); // 根据请求代码确定分类代码
				String bindingName = isQueryAccount ? "binding_jsga_queryaccount_req" : "binding_jsga_querycustomer_req";
				String xmlPath = generateXMLDocument(queryRequest, br40_cxqq, xmlType, bindingName, taskType);
				// 文件地址入库
				insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
				// 江苏 不需要打包通过 http发送 sentmessage
				//判断是发送用户信息还是发送交易流水
				boolean isAccount = true;
				
				deal(xmlPath, br40_cxqq_mx.getCxlx(), isAccount, br40_cxqq.getQqdbs(), br40_cxqq.getRwlsh(), mc21_task_fact.getTasktype());
				
			}
			
			if (Constants12.CXNR_TRANSACTION.equals(cxnr) || Constants12.CXNR_ACC_AND_TRANS.equals(cxnr)) {
				if (transactionList == null || transactionList.size() == 0) {
					// CBRCConstants.CXNR_TRANSACTION: 交易流水不可能为空，即使为空，也会有一条反馈错误信息的数据；
					// CBRCConstants.CXNR_ACC_AND_TRANS: 可能没有查询交易流水，或者甚至账户都没有，不管哪种情况，都不需要生成XML文件。
				} else {
					queryRequest.setCzsbyy(czsbyy_trans); // 
					String xmlType = Constants12.valueOf(br40_cxqq_mx.getZtlb(), "SS09"); // 根据主体类别确定分类代码
					String xmlPath = generateXMLDocument(queryRequest, br40_cxqq, xmlType, "binding_jsga_querytransaction_req", taskType);
					// 文件地址入库
					insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
					// 江苏 不需要打包通过 http发送 sentmessage
					//判断是发送用户信息还是发送交易流水
					boolean isAccount = false;
					
					deal(xmlPath, br40_cxqq_mx.getCxlx(), isAccount, br40_cxqq.getQqdbs(), br40_cxqq.getRwlsh(), mc21_task_fact.getTasktype());
				}
			}
			
			// 4.置反馈表状态
			Br40_cxqq_back br40_cxqq_back = new Br40_cxqq_back();
			br40_cxqq_back.setStatus("6"); // 2.已生成报文
			br40_cxqq_back.setTasktype(taskType);
			br40_cxqq_back.setQqdbs(br40_cxqq_mx.getQqdbs());
			br40_cxqq_back.setRwlsh(br40_cxqq_mx.getRwlsh());
			br40_cxqqMapper.updateBr40_cxqq_back(br40_cxqq_back);
		}
	}
	
	public void dealReturnReceipt29(Br40_cxqq br40_cxqq) throws Exception {
		Br40_cxqq_mx br40_cxqq_mx = new Br40_cxqq_mx();
		JSGA_ExecResult execResult = new JSGA_ExecResult();
		String qqcslx = br40_cxqq.getQqcslx();
		execResult.setQqdbs(br40_cxqq.getQqdbs());
		execResult.setQqcslx(qqcslx);
		execResult.setMbjgdm(br40_cxqq.getMbjgdm());
		execResult.setSqjgdm(br40_cxqq.getSqjgdm());
		execResult.setHzsj(Utility.currDateTime14());
		br40_cxqq.setRwlsh("");
		List<Br40_cxqq_mx> mxlist = br40_cxqqMapper.getBr40_cxqq_mxList(br40_cxqq);
		
		execResult.setJsrws(mxlist.size() + "");
		execResult.setFkrws(mxlist.size() + "");
		Integer fkkhs = new Integer(0);
		Integer fkzhs = new Integer(0);
		Integer fkjymxs = new Integer(0);
		if (qqcslx.equals("01")) {
			/** 反馈客户数 */
			fkkhs = br40_cxqqMapper.getFkkhs(br40_cxqq);
			/** 反馈账户数 */
			fkzhs = br40_cxqqMapper.getFkzhs(br40_cxqq);
			/** 反馈交易明细数 */
			fkjymxs = br40_cxqqMapper.getFkjymxs(br40_cxqq);
			if (fkjymxs == null) {
				fkjymxs = new Integer(0);
			}
		}
		execResult.setFkkhs(fkkhs + "");
		execResult.setFkzhs(fkzhs + "");
		execResult.setFkjymxs(fkjymxs + "");
		
		String xmlPath = generateXMLDocument(execResult, br40_cxqq, "RR29", "binding_jsga_execresult_req", br40_cxqq.getTasktype());
		
		// 文件地址入库
		br40_cxqq_mx.setTasktype(br40_cxqq.getTasktype());
		br40_cxqq_mx.setQqdbs(br40_cxqq.getQqdbs());
		br40_cxqq_mx.setRwlsh("RR29");
		insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
	}
	
	/**
	 * 动态查询解除
	 */
	public void FeedBackQuery_dtjc(MC21_task_fact mc21_task_fact) throws Exception {
		Br40_cxqq br40_cxqq = this.getBr40_cxqqFortask3(mc21_task_fact);
		List<Br40_cxqq_mx> mxList = br40_cxqqMapper.getBr40_cxqq_mxList(br40_cxqq);
		//Br40_cxqq_mx br40_cxqq_mx = this.getQueryRequest_mx(br40_cxqq);
		String qqdbs = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");//请求单标识
		String currDateTime = Utility.currDateTime19();
		
		JSGA_ControlReceiptRequest request01 = new JSGA_ControlReceiptRequest();
		request01.setQqdbs(qqdbs);
		request01.setSqjgdm(br40_cxqq.getSqjgdm());
		request01.setMbjgdm(br40_cxqq.getMbjgdm());
		
		request01.setHzsj(currDateTime);
		
		for (Br40_cxqq_mx br40_cxqq_mx : mxList) {
			request01.setZtlb(br40_cxqq_mx.getZtlb());
			br40_cxqq.setRwlsh(br40_cxqq_mx.getRwlsh());
			Br40_cxqq_back cxqqback = br40_cxqqMapper.selectBr40_cxqq_backByVo(br40_cxqq);
			
			// 查询被解除的动态查询任务
			Br40_cxqq dynamicTask = new Br40_cxqq();
			dynamicTask.setRwlsh(br40_cxqq_mx.getYrwlsh());
			dynamicTask.setTasktype(mc21_task_fact.getTasktype());
			
			JSGA_ControlRequest_Receipt controlrequest = new JSGA_ControlRequest_Receipt();
			controlrequest.setRwlsh(cxqqback.getRwlsh());
			controlrequest.setZh(cxqqback.getZh());
			controlrequest.setKh(cxqqback.getKh());
			controlrequest.setZxjg(cxqqback.getCxfkjg());
			controlrequest.setSbyy(cxqqback.getCzsbyy());
			controlrequest.setFksjhm(cxqqback.getFksjhm());
			controlrequest.setZxqssj(cxqqback.getZxqssj()); // 被解除的动态查询任务的起始时间
			controlrequest.setZxsjqj("0"); // 0-解除动态查询（措施类型）
			controlrequest.setJssj(currDateTime); // 结束时间为当前时间
			
			List<JSGA_ControlRequest_Receipt> list = new ArrayList<JSGA_ControlRequest_Receipt>();
			list.add(controlrequest);
			
			request01.setControlReceiptList(list);
			
			String xmlType = Constants12.valueOf(br40_cxqq_mx.getXmltype()); // 根据主体类别确定分类代码
			String xmlPath = generateXMLDocument(request01, br40_cxqq, xmlType, "binding_jsga_controlreceipt_req", br40_cxqq.getTasktype());
			// 文件地址入库
			insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
			
			deal(xmlPath, qqdbs, br40_cxqq.getRwlsh(), br40_cxqq.getTasktype(),br40_cxqq.getQqcslx(),false);
			//4.设置BR40_CXQQ_BACK 査询请求反馈主表 3成功 4失败
			Br40_cxqq_back back = new Br40_cxqq_back();
			back.setTasktype(br40_cxqq_mx.getTasktype());
			back.setQqdbs(br40_cxqq_mx.getQqdbs());
			back.setRwlsh(br40_cxqq_mx.getRwlsh());
			back.setStatus("6");
			br40_cxqqMapper.updateBr40_cxqq_back(back);
		}
	
		
	}
	
	/**
	 * 动态查询反馈
	 */
	public void FeedBackQuery_dt(MC21_task_fact mc21_task_fact) throws Exception {
		Br40_cxqq br40_cxqq = this.getBr40_cxqq(mc21_task_fact);
		Br40_cxqq_mx br40_cxqq_mx = new Br40_cxqq_mx();
		br40_cxqq_mx = this.getQueryRequest_mx(br40_cxqq);
		Br40_cxqq_back cxqqback = new Br40_cxqq_back();
		
		String qqdbs = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
		if (mc21_task_fact.getTaskobj().equals("HZ")) {
			br40_cxqq.setSeq("0");
			br40_cxqq.setQqdbs(qqdbs);
			List<Br40_cxqq_back> backlist = br40_cxqqMapper.selectBr40_cxqq_backByList(br40_cxqq);
			//1.feedbackinfo银行反馈的动态查询执行结果信息
			JSGA_ControlReceiptRequest request01 = new JSGA_ControlReceiptRequest();
			request01.setQqdbs(qqdbs);
			
			request01.setSqjgdm(br40_cxqq.getSqjgdm());
			request01.setMbjgdm(br40_cxqq.getMbjgdm());
			request01.setHzsj(DtUtils.getNowTime());
			request01.setCzsbyy("");
			for (Br40_cxqq_back _cxqqback : backlist) {
				List<JSGA_ControlRequest_Receipt> list = new ArrayList<JSGA_ControlRequest_Receipt>();
				br40_cxqq.setRwlsh(_cxqqback.getRwlsh());
				br40_cxqq_mx = this.getQueryRequest_mx(br40_cxqq);
				JSGA_ControlRequest_Receipt controlrequest = new JSGA_ControlRequest_Receipt();
				controlrequest.setRwlsh(_cxqqback.getRwlsh());
				controlrequest.setZh(_cxqqback.getZh());
				controlrequest.setKh(_cxqqback.getKh());
				controlrequest.setZxjg(_cxqqback.getCxfkjg());
				controlrequest.setSbyy(_cxqqback.getCzsbyy());
				controlrequest.setFksjhm(_cxqqback.getFksjhm());
				controlrequest.setZxqssj(_cxqqback.getZxqssj()); // 执行起始时间
				controlrequest.setZxsjqj(_cxqqback.getZxsjqj()); // 执行时间区间
				controlrequest.setJssj(_cxqqback.getJssj()); // 结束时间
				request01.setZtlb(br40_cxqq_mx.getZtlb());
				String zxjg = _cxqqback.getCxfkjg();
				if (zxjg != null && zxjg.equals("1")) {
					request01.setCzsbyy("失败");
				}
				list.add(controlrequest);
				
				request01.setControlReceiptList(list);
				
				String xmlType = Constants12.valueOf(br40_cxqq_mx.getXmltype()); // 根据主体类别确定分类代码
				
				String xmlPath = generateXMLDocument(request01, br40_cxqq, xmlType, "binding_jsga_controlreceipt_req", br40_cxqq.getTasktype());
				// 文件地址入库
				insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
				String taskType = mc21_task_fact.getTasktype();
				String rwlsh=_cxqqback.getRwlsh();
				deal(xmlPath, qqdbs, rwlsh, taskType,br40_cxqq.getQqcslx(),false);
			}
		} else {
			br40_cxqq.setSeq(mc21_task_fact.getTgroupid());
			cxqqback = br40_cxqqMapper.selectBr40_cxqq_backByVo(br40_cxqq);
			JSGA_ControlRecordRequest request02 = new JSGA_ControlRecordRequest();
			request02.setQqdbs(qqdbs);
			request02.setZtlb(br40_cxqq_mx.getZtlb());
			request02.setSqjgdm(br40_cxqq.getSqjgdm());
			request02.setMbjgdm(br40_cxqq.getMbjgdm());
			request02.setCxjssj(DtUtils.getNowTime());
			cxqqback.setRwlsh(cxqqback.getRwlsh() + "_" + cxqqback.getSeq());
			List<JSGA_ControlRequest_Record> tranList = br40_cxqqMapper.selectBr40_cxqq_back_trans_dtList(cxqqback);
			if (tranList != null) {
				request02.setControlRecordList(tranList);
				String xmlType = br40_cxqq_mx.getXmltype().equals("SS11") ? "SS15" : "SS16";// 根据主体类别确定分类代码
				
				String xmlPath = generateXMLDocument(request02, br40_cxqq, xmlType, "binding_jsga_controlrecord_req", br40_cxqq.getTasktype());
				// 文件地址入库
				br40_cxqq_mx.setRwlsh(cxqqback.getRwlsh());
				br40_cxqq_mx.setQqdbs(cxqqback.getQqdbs());
				br40_cxqq_mx.setTasktype(cxqqback.getTasktype());
				insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
				//发送交易短信
				String telno = br40_cxqq.getQqrsjh();
				if (telno != null && !telno.equals("")) {
					dataOperate.sendTransMsg(tranList, telno);
				}
				deal(xmlPath, qqdbs, br40_cxqq.getRwlsh(), br40_cxqq.getTasktype(),br40_cxqq.getQqcslx(),true);
			}
		}
	}
	
	private void deal(String xmlPath, String qqdbs, String rwlsh, String taskType, String qqcslx,boolean isdthz) {
		logger.info("deal---xmlpath[{}],cxlx[{}],isAccount[{}],bdhm[{}],tasktype[{}]", xmlPath, qqdbs, rwlsh, taskType, qqcslx);
		RequestMessageService12 service = SpringContextHolder.getBean("requestMessageService12");
		String receipt = service.sendControlResult(xmlPath, qqcslx,isdthz);
		logger.info("反馈code {}", receipt);
		JSGA_ReturnReceipt returnReceipt = new JSGA_ReturnReceipt();
		if (!"1000".equals(receipt.trim())) {
			returnReceipt.setQqdbs(qqdbs); // 请求单标识
			returnReceipt.setHzdm(receipt); // 回执代码
			returnReceipt.setJssj(Utility.currDateTime19()); // 接收时间
			returnReceipt.setHzsm("未知错误"); // 回执说明
			returnReceipt.setTasktype(taskType); // 任务类型
			returnReceipt.setRwlsh(rwlsh);
		} else {
			returnReceipt.setQqdbs(qqdbs); // 请求单标识
			returnReceipt.setTasktype(taskType); // 任务类型
			returnReceipt.setHzdm(receipt);
			returnReceipt.setJssj(Utility.currDateTime19());
			returnReceipt.setHzsm("成功反馈"); // 回执说明
			returnReceipt.setTasktype(taskType);
			returnReceipt.setRwlsh(rwlsh);
			
		}
		br40_cxqqMapper.delBr40_receipt(returnReceipt);
		br40_cxqqMapper.insertBr40_receipt(returnReceipt);
	}

	private Br40_cxqq getBr40_cxqq(MC21_task_fact mc21_task_fact) {
		Br40_cxqq br40_cxqq = new Br40_cxqq();
		br40_cxqq.setQqdbs(StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$"));//请求单标识
		br40_cxqq.setTasktype(mc21_task_fact.getTasktype());//监管类别
		br40_cxqq = br40_cxqqMapper.selectBr40_cxqqByVo(br40_cxqq);
		br40_cxqq.setRwlsh(StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$"));
		return br40_cxqq;
	}
	
	/**
	 * 生成查询反馈报文
	 * 
	 * @param mc21_task_fact
	 * @param qs_zhxxlist
	 * @throws Exception
	 */
	public String generateXMLDocument(Object request, Br40_cxqq br40_cxqq, String xmltype, String bindingname, String taskType) throws Exception {
		String absolutePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_ATTACH, JsgaKeys.FILE_DIRECTORY_12);
		absolutePath = absolutePath + File.separator + br40_cxqq.getQqdbs();
		String mbjgdm = br40_cxqq.getMbjgdm(); // 目标机构代码（银行代码）
		
		String filename = this.getMsgNameCBRC(br40_cxqq.getTasktype(), xmltype, br40_cxqq.getQqdbs(), mbjgdm) + ".xml";
		CommonUtils.marshallUTF8Document(request, bindingname, absolutePath, filename);
		
		return absolutePath + File.separator + filename;
	}
	
	public void insertBr42_msg(Br40_cxqq_mx br40_cxqq_mx, String path, String organkey_r) throws Exception {
		Br42_msg msg = new Br42_msg();
		
		String filename = FileUtils.getFilenameFromPath(path);
		msg.setMsgkey(this.geSequenceNumber("SEQ_BR42_MSG"));
		msg.setBdhm(br40_cxqq_mx.getRwlsh());
		msg.setMsg_type_cd(filename.substring(0, 4));
		msg.setPacketkey(br40_cxqq_mx.getQqdbs());
		if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(br40_cxqq_mx.getTasktype())) {
			msg.setOrgankey_r(organkey_r.substring(0, 8));
		} else {
			msg.setOrgankey_r(organkey_r);
		}
		msg.setSenddate(DtUtils.getNowDate());
		msg.setMsg_filename(filename);
		msg.setMsg_filepath(path);
		msg.setStatus_cd("0"); //报文状态 0:待打包 1:已打包
		msg.setCreate_dt(DtUtils.getNowTime());
		msg.setQrydt(DtUtils.getNowDate());
		msg.setTasktype(br40_cxqq_mx.getTasktype());
		br40_cxqqMapper.delBr42_msg(msg);
		br40_cxqqMapper.insertBr42_msg(msg);
	}
	
	public void deal(String xmlPath, String cxlx, boolean isAccount, String bdhm, String rwlsh, String taskType) {
		logger.info("deal---xmlpath[{}],cxlx[{}],isAccount[{}],bdhm[{}],tasktype[{}]", xmlPath, cxlx, isAccount, bdhm, taskType);
		RequestMessageService12 service = SpringContextHolder.getBean("requestMessageService12");
		String receipt = service.sendQueryResult(xmlPath, cxlx, isAccount);
		logger.info("反馈code {}", receipt);
		JSGA_ReturnReceipt returnReceipt = new JSGA_ReturnReceipt();
		if (!"1000".equals(receipt.trim())) {
			returnReceipt.setQqdbs(bdhm); // 请求单标识
			returnReceipt.setHzdm(receipt); // 回执代码
			returnReceipt.setJssj(Utility.currDateTime19()); // 接收时间
			returnReceipt.setHzsm("未知错误"); // 回执说明
			returnReceipt.setTasktype(taskType); // 任务类型
			returnReceipt.setRwlsh(rwlsh);
		} else {
			returnReceipt.setQqdbs(bdhm); // 请求单标识
			returnReceipt.setTasktype(taskType); // 任务类型
			returnReceipt.setHzdm(receipt);
			returnReceipt.setJssj(Utility.currDateTime19());
			returnReceipt.setHzsm("成功反馈"); // 回执说明
			returnReceipt.setTasktype(taskType);
			returnReceipt.setRwlsh(rwlsh);
			
		}
		br40_cxqqMapper.delBr40_receipt(returnReceipt);
		br40_cxqqMapper.insertBr40_receipt(returnReceipt);
	}
}
