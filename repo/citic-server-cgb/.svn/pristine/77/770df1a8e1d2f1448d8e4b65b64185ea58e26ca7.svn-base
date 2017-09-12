package com.citic.server.jsga.task.taskBo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.dict.DictCoder;
import com.citic.server.jsga.domain.Br40_cxqq;
import com.citic.server.jsga.domain.Br40_cxqq_back;
import com.citic.server.jsga.domain.Br40_cxqq_mx;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Account;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Customer;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Measure;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Priority;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_SubAccount;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Transaction;
import com.citic.server.jsga.mapper.MM40_cxqq_jsgaMapper;
import com.citic.server.jsga.service.IDataOperate12;
import com.citic.server.runtime.Constants12;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

public class Cxqq_CLBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_CLBo.class);
	
	/** 数据获取接口 */
	private IDataOperate12 dataOperate;
	
	/** 数据字典码值转换组件 */
	private DictCoder dictCoder;
	
	private MM40_cxqq_jsgaMapper br40_cxqqMapper;
	
	public Cxqq_CLBo(ApplicationContext ac) {
		super(ac);
		this.br40_cxqqMapper = (MM40_cxqq_jsgaMapper) ac.getBean("MM40_cxqq_jsgaMapper");
		this.dictCoder = (DictCoder) SpringContextHolder.getBean(DictCoder.class);
	}
	
	/**
	 * 删除查询信息
	 * 
	 * @param bdhm
	 * @throws Exception
	 */
	public void delCxqqInfo(MC21_task_fact mc21_task_fact) throws Exception {
		Br40_cxqq br40_cxqq = new Br40_cxqq();
		br40_cxqq.setTasktype(mc21_task_fact.getTasktype());
		br40_cxqq.setQqdbs(StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$"));
		br40_cxqq.setRwlsh(StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$"));
		br40_cxqqMapper.delBr40_cxqq_back_acct(br40_cxqq);
		br40_cxqqMapper.delBr40_cxqq_back_acct_qzcs(br40_cxqq);
		br40_cxqqMapper.delBr40_cxqq_back_acct_ql(br40_cxqq);
		br40_cxqqMapper.delBr40_cxqq_back_acct_sub(br40_cxqq);
		br40_cxqqMapper.delBr40_cxqq_back_trans(br40_cxqq);
		br40_cxqqMapper.delBr40_cxqq_back_party(br40_cxqq);
	}
	
	/**
	 * 处理账户交易明细查询任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public void handleQueryResponse(MC21_task_fact mc21_task_fact) throws Exception {
		String cxfkjg = "01";
		String czsbyy = "成功";
		
		String taskType = mc21_task_fact.getTasktype(); // 
		
		//1.查询请求内容
		// 1.1、根据任务信息，获取查询请求信息
		Br40_cxqq br40_cxqq = this.getBr40_cxqq(mc21_task_fact);
		// 1.2.根据请求单标识、监管类别获取查询主体信息内容
		Br40_cxqq_mx br40_cxqq_mx = this.getQueryRequest_mx(br40_cxqq);
		// 1.3、获取反馈基本信息表
		Br40_cxqq_back br40_cxqq_back = this.getBr40_cxqq_back(br40_cxqq);
		
		String msgCheckResult = br40_cxqq_back.getMsgcheckresult();
		if (msgCheckResult != null && msgCheckResult.equals("1")) { // 判断是否是本行数据	
			String qqdbs = br40_cxqq_mx.getQqdbs();
			String rwlsh = br40_cxqq_mx.getRwlsh();
			
			try {
				//2.初始化接口
				
				dataOperate = (IDataOperate12) this.ac.getBean(Constants12.REMOTE_DATA_OPERATE_NAME_JSGA);
				
				// 码值转换（将监管数据转换为核心系统可识别的数据）
				dictCoder.transcode(br40_cxqq_mx, taskType);
				br40_cxqq_mx.setMbjgdm(br40_cxqq.getMbjgdm());
				
				//4. 查询结果插入落地表
				//4.1查询内容为01和03（ 账户信息、账户和账户交易明细）时执行以下方法：
				JSGA_QueryRequest_Customer customer = null;
				List<JSGA_QueryRequest_Transaction> transactionList = null;
				String cxnr = br40_cxqq_mx.getCxnr();
				String qrydt = Utility.currDate10(); // 当前日期，格式：yyyy-MM-dd
				
				if (Constants12.CXNR_TRANSACTION.equals(cxnr)) {
					transactionList = dataOperate.getAccountTransaction(br40_cxqq_mx);
				} else {
					if (Constants12.CXNR_ACC_AND_TRANS.equals(cxnr)) {
						customer = dataOperate.getAccountDetailAndTransaction(br40_cxqq_mx);
					} else {
						customer = dataOperate.getAccountDetail(br40_cxqq_mx);
					}
					
					if (customer == null) {
						cxfkjg = "02";
						czsbyy = "查无客户信息";
					} else {
						customer.setQqdbs(qqdbs); // 请求单标识
						customer.setTasktype(taskType); // 
						customer.setRwlsh(rwlsh);
						customer.setQrydt(qrydt);
						customer.setCxfkjg("01"); // 01-成功
						customer.setCxfkjgyy(getCxfkjgyy("01", "成功", taskType)); // "查询反馈结果原因"需特殊处理
						
						// 转码并插入客户信息
						dictCoder.reverse(customer, taskType);
						br40_cxqqMapper.insertPartyInfo(customer);
						
						List<JSGA_QueryRequest_Account> accountList = customer.getAccountList(); // 账户
						if (accountList != null) {
							for (JSGA_QueryRequest_Account account : accountList) {
								account.setQqdbs(qqdbs);
								account.setTasktype(taskType);
								account.setRwlsh(rwlsh);
								account.setQrydt(qrydt);
								
								//add by jiangdaming 账户余额和可用金额以.开始的情况
								if (null != account.getKyye() && account.getKyye().startsWith(".")) {
									account.setKyye("0" + account.getKyye());
								}
								if (null != account.getZhye() && account.getZhye().startsWith(".")) {
									account.setZhye("0" + account.getZhye());
								}
								
								// 转码并插入账户信息
								account.setKhwd(account.getKhwddm()); // 开户网点代码 - 开户网点名称
								account.setXhwd(account.getXhwddm()); // 销户网点代码 - 销户网点名称
								account.setBkwd(account.getBkwddm()); // 补卡网点代码 - 补卡网点名称
								account.setZhjysj(account.getZhjysj());
								dictCoder.reverse(account, taskType);
								br40_cxqqMapper.insertAcctInfo(account);
							}
						}
						
						//4.1.3 插入账户强制措施信息、子账户、共享权/优先权
						insertAcct_qzcs_sub_ql(br40_cxqq_mx, customer);
						
						if (Constants12.CXNR_ACC_AND_TRANS.equals(cxnr)) {
							transactionList = customer.getTransactionList();
						}
					}
				}
				
				if (transactionList != null && transactionList.size() > 0) {
					int seq = 1;
					int i = 0;
					List<JSGA_QueryRequest_Transaction> insertList = new ArrayList<JSGA_QueryRequest_Transaction>();
					for (JSGA_QueryRequest_Transaction transaction : transactionList) {
						if (transaction.getTransseq() == null || transaction.getTransseq().length() == 0) {
							transaction.setTransseq(String.valueOf(seq++));//需要从核心反馈
						}
						transaction.setQqdbs(qqdbs);
						transaction.setTasktype(taskType);
						transaction.setRwlsh(rwlsh);
						transaction.setQrydt(qrydt);
						transaction.setCxfkjg("01"); // 01-成功
						transaction.setCxfkjgyy(getCxfkjgyy("01", "成功", taskType)); // "查询反馈结果原因"需特殊处理
						// 处理“交易对方卡号”、“交易对方账号”及“交易对方账卡号”
						if (transaction.getJydfzh() == null || transaction.getJydfzh().length() == 0) { // 交易对方账号
							transaction.setJydfzh(transaction.getJydfkh()); // 交易对方卡号
						} else if (transaction.getJydfkh() == null || transaction.getJydfkh().length() == 0) { // 交易对方卡号
							transaction.setJydfkh(transaction.getJydfzh()); // 交易对方账号
						}
						// 交易余额和可用金额以.开始的情况
						if (null != transaction.getJe() && transaction.getJe().startsWith(".")) {
							transaction.setJe("0" + transaction.getJe());
						}
						if (null != transaction.getYe() && transaction.getYe().startsWith(".")) {
							transaction.setYe("0" + transaction.getYe());
						}
						
						dictCoder.reverse(transaction, taskType); // 逆向转码
						insertList.add(transaction);
						
						// 分批插入数据库（流水表多达54个字段，一次不能插入太多数据）
						i++;
						if (i % 500 == 0 || i == transactionList.size()) {
							br40_cxqqMapper.insertBr40_cxqq_back_trans(insertList); // 批量插入
							insertList.clear();
						}
					}
				}
			} catch (DataOperateException e) {
				cxfkjg = "02";
				czsbyy = e.getMessage();
				logger.warn("数据处理异常：{}", e.getMessage());
			} catch (RemoteAccessException e) {
				cxfkjg = "02";
				czsbyy = "应用程序异常";
				logger.error("应用程序异常：{}", e.getMessage(), e);
				
			}
		} else {
			cxfkjg = "02";
			czsbyy = "查无客户信息";
		}
		
		br40_cxqq_back.setCxfkjg(cxfkjg);
		br40_cxqq_back.setCzsbyy(getCxfkjgyy(cxfkjg, czsbyy, taskType)); // "操作失败原因"需特殊处理
		br40_cxqq_back.setStatus("1"); //核心已反馈
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee.equals("0")) { //不走人工
			br40_cxqq_back.setStatus("2");//已处理
		}
		updateBr40_cxqq_back(br40_cxqq_back);
		
		if (isemployee.equals("0")) { //不走人工	
			// 5.插入任务task3
			/*
			 * int istaskok = br40_cxqqMapper.isTaskCount(br40_cxqq_back);
			 * if (istaskok == 0) {
			 * int isok = br40_cxqqMapper.getBr40_cxqq_backCount(br40_cxqq_back);
			 * if (isok == 0) {
			 * br40_cxqq.setStatus("2");//修改已提交
			 * br40_cxqqMapper.updateBr40_cxqq(br40_cxqq);
			 * mc21_task_fact.setBdhm(br40_cxqq_back.getQqdbs());
			 * insertMc21TaskFact3(mc21_task_fact, "CBRC");
			 * }
			 * }
			 */
			insertMc21TaskFact3(mc21_task_fact, "JSGA" + taskType); //直接插入task3一条task2对应一个task3
		}
		
	}
	
	private String getCxfkjgyy(String cxfkjg, String cxfkjgyy, String taskType) {
		// 特殊处理
		if ("01".equals(cxfkjg)) { // 成功
			if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) {
				return ""; // 查询成功时CXFKJGYY赋空值
			}
		}
		return cxfkjgyy;
	}
	
	private String getCzsbyy(String cxfkjg, String cxfkjgyy, String taskType) {
		// 特殊处理
		if ("0".equals(cxfkjg)) { // 成功
			if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) {
				return ""; // 查询成功时CXFKJGYY赋空值
			}
		}
		return cxfkjgyy;
	}
	
	public void insertAcct_qzcs_sub_ql(Br40_cxqq_mx br40_cxqq_mx, JSGA_QueryRequest_Customer customer) throws Exception {
		if (StringUtils.equals(this.query_datasource, LOCAL_QUERY)) {
			//4.1.4 插入子账户信息
			//br40_cxqqMapper.insertBr40_cxqq_back_acct_sub(br40_cxqq_mx);
			List<JSGA_QueryRequest_SubAccount> subAccountList = customer.getSubAccountList();
			if (subAccountList != null) {
				for (JSGA_QueryRequest_SubAccount subAccount : subAccountList) {
					String tasktype1 = br40_cxqq_mx.getTasktype();
					// 转码并插入子账户信息
					dictCoder.reverse(subAccount, tasktype1);
					br40_cxqqMapper.insertBr40_cxqq_back_acct_sub1(subAccount);
				}
			}
			//4.1.5 插入共享权优先权信息
			br40_cxqqMapper.insertBr40_cxqq_back_acct_ql(br40_cxqq_mx);
			
			//插入强制措施信息
			//br40_cxqqMapper.insertBr40_cxqq_back_acct_qzcs(br40_cxqq_mx);
			String qqdbs = br40_cxqq_mx.getQqdbs();
			String tasktype = br40_cxqq_mx.getTasktype();
			String rwlsh = br40_cxqq_mx.getRwlsh();
			String qrydt = Utility.currDate10(); // 当前日期，格式：yyyy-MM-dd
			
			List<JSGA_QueryRequest_Measure> measureList = br40_cxqqMapper.getBb11_freeze(br40_cxqq_mx); // 强制措施
			if (measureList != null) {
				int i = 1;
				for (JSGA_QueryRequest_Measure measure : measureList) {
					measure.setQqdbs(qqdbs);
					measure.setTasktype(tasktype);
					measure.setRwlsh(rwlsh);
					measure.setQrydt(qrydt);
					measure.setCsxh(String.valueOf(i));
					i = i + 1;
					// 转码并插入强制措施信息
					dictCoder.reverse(measure, tasktype);
					br40_cxqqMapper.insertBr40_cxqq_back_acct_qzcs2(measure);
				}
			}
		} else {
			String qqdbs = br40_cxqq_mx.getQqdbs();
			String tasktype = br40_cxqq_mx.getTasktype();
			String rwlsh = br40_cxqq_mx.getRwlsh();
			String qrydt = Utility.currDate10(); // 当前日期，格式：yyyy-MM-dd
			
			List<JSGA_QueryRequest_Measure> measureList = customer.getMeasureList(); // 强制措施
			if (measureList != null) {
				int i = 1;
				for (JSGA_QueryRequest_Measure measure : measureList) {
					measure.setQqdbs(qqdbs);
					measure.setTasktype(tasktype);
					measure.setRwlsh(rwlsh);
					measure.setQrydt(qrydt);
					if (measure.getDjcslx().equals("")) {
						measure.setDjcslx("03");
					}
					measure.setCsxh(String.valueOf(i));
					i = i + 1;
					// 转码并插入强制措施信息
					dictCoder.reverse(measure, tasktype);
					br40_cxqqMapper.insertBr40_cxqq_back_acct_qzcs1(measure);
				}
			}
			
			List<JSGA_QueryRequest_SubAccount> subAccountList = customer.getSubAccountList(); // 子账户
			if (subAccountList != null) {
				for (JSGA_QueryRequest_SubAccount subAccount : subAccountList) {
					subAccount.setQqdbs(qqdbs);
					subAccount.setTasktype(tasktype);
					subAccount.setRwlsh(rwlsh);
					subAccount.setQrydt(qrydt);
					
					//add by jiangdaming 账户余额和可用金额以.开始的情况
					if (null != subAccount.getKyye() && subAccount.getKyye().startsWith(".")) {
						subAccount.setKyye("0" + subAccount.getKyye());
					}
					if (null != subAccount.getZhye() && subAccount.getZhye().startsWith(".")) {
						subAccount.setZhye("0" + subAccount.getZhye());
					}
					
					// 转码并插入子账户信息
					dictCoder.reverse(subAccount, tasktype);
					br40_cxqqMapper.insertBr40_cxqq_back_acct_sub1(subAccount);
				}
			}
			
			List<JSGA_QueryRequest_Priority> priorityList = customer.getPrioritiesList(); // 共有权/优先权
			if (priorityList != null) {
				int n = 1;
				for (JSGA_QueryRequest_Priority priority : priorityList) {
					priority.setQqdbs(qqdbs);
					priority.setTasktype(tasktype);
					priority.setRwlsh(rwlsh);
					priority.setQrydt(qrydt);
					priority.setXh(n + "");
					n = n + 1;
					// 转码并插入共有权/优先权信息
					dictCoder.reverse(priority, tasktype);
					br40_cxqqMapper.insertBr40_cxqq_back_acct_ql1(priority);
				}
			}
		}
		
	}
	
	/**
	 * 处理账户动态查询任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public void handleQueryResponse_dtcx(MC21_task_fact mc21_task_fact) throws Exception {
		
		// 1、根据任务信息，获取查询请求信息
		Br40_cxqq br40_cxqq = this.getBr40_cxqq(mc21_task_fact);
		Br40_cxqq_mx br40_cxqq_mx = this.getQueryRequest_mx(br40_cxqq);
		try {
			br40_cxqq.setSeq("0");
			Br40_cxqq_back br40_cxqq_back = this.getBr40_cxqq_back(br40_cxqq);
			br40_cxqq_back.setStatus("1"); //核心已反馈
			br40_cxqq_back.setCxfkjg("0");
			br40_cxqq_back.setCzsbyy("成功");
			String isemployee = mc21_task_fact.getIsemployee();
			if (isemployee.equals("0")) { //不走人工
				br40_cxqq_back.setStatus("2");//已处理
			}
			String msgCheckResult = br40_cxqq_back.getMsgcheckresult();
			if (msgCheckResult != null && msgCheckResult.equals("1")) { //判断是否是本行数据		
				//写账户动态查询或解除任务表
				boolean isOk = true;
				String qqcslx = br40_cxqq.getQqcslx();
				br40_cxqq_mx.setQqcslx(qqcslx);
				String mxjzsj = Utility.toDate10(br40_cxqq_mx.getMxjzsj());
				String mxqssj = Utility.toDate10(br40_cxqq_mx.getMxqssj());
				String enddate = DtUtils.add(mxqssj, 1, 100);
				if ((DtUtils.comp(mxjzsj, enddate, 1)) > 0) { //若结束日期比开始日期大于100天则请求无效返回失败
					isOk = false;
					br40_cxqq_back.setCzsbyy("起止时间不能超过100天");
				}
				
				if (Constants12.DYNAMIC_CONTINUE.equals(qqcslx)) {
					//查询原动态查询的结束日期
					Br40_cxqq_mx Br40_cxqq_mxori = br40_cxqqMapper.selectBr40_cxqq_mxByOriVo(br40_cxqq_mx);
					String yjzsj = Utility.toDate10(Br40_cxqq_mxori.getMxjzsj()); // 原截止时间
					if ((DtUtils.comp(yjzsj, mxjzsj, 1)) > 0) { // 原截止时间大于新截止时间
						isOk = false;
						br40_cxqq_back.setCzsbyy("继续动态查询的截止时间不能小于原截止时间");
					} else {
						br40_cxqq_mx.setMxjzsjold(yjzsj); // 原截止时间
					}
				}
				
				if (isOk) {
					insertBr40_cxqq_acct_dynamic_js(br40_cxqq_mx);
				} else {
					//修改反馈表状态是失败
					br40_cxqq_back.setCxfkjg("1");
				}
			} else {
				br40_cxqq_back.setCzsbyy("查无此客户");
				br40_cxqq_back.setCxfkjg("1");
			}
			br40_cxqq_back.setCzsbyy(getCzsbyy(br40_cxqq_back.getCxfkjg(), br40_cxqq_back.getCzsbyy(), mc21_task_fact.getTasktype())); // "操作失败原因"需特殊处理
			
			this.updateBr40_cxqq_back(br40_cxqq_back); //修改
			
			if (mc21_task_fact.getIsemployee().equals("0")) { //不走人工	
				// 5.插入任务task3
				int istaskok = br40_cxqqMapper.isTaskCount(br40_cxqq_back);
				if (istaskok == 0) {
					int isok = br40_cxqqMapper.getBr40_cxqq_backCount(br40_cxqq_back);
					if (isok == 0) {
						br40_cxqq.setStatus("2");//修改已提交
						br40_cxqqMapper.updateBr40_cxqq(br40_cxqq);
						mc21_task_fact.setBdhm(br40_cxqq_back.getQqdbs());
						mc21_task_fact.setTaskobj("HZ");
						insertMc21TaskFact3(mc21_task_fact, "JSGA_" + mc21_task_fact.getTasktype());
					}
				}
			}
		} catch (Exception e) {
			// 错误统一采用异常处理
			logger.error("查询异常" + e.getMessage());
			throw e;
		}
		
	}
	
	/**
	 * 处理账户动态查询解除任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public void handleQueryResponse_dtjc(MC21_task_fact mc21_task_fact) throws Exception {
		
		// 1、根据任务信息，获取查询请求信息
		Br40_cxqq br40_cxqq = this.getBr40_cxqq(mc21_task_fact);
		Br40_cxqq_mx br40_cxqq_mx = this.getQueryRequest_mx(br40_cxqq);
		String yrwlsh = br40_cxqq_mx.getYrwlsh();
		String rwlsh = br40_cxqq_mx.getRwlsh();
		br40_cxqq_mx.setRwlsh(yrwlsh);
		// 2、账户动态查询或解除任务表
		br40_cxqqMapper.updateBr40_cxqq_acct_dynamic_jc(br40_cxqq_mx);
		
		// 3、获取反馈基本信息表
		Br40_cxqq_back br40_cxqq_back = new Br40_cxqq_back();
		BeanUtils.copyProperties(br40_cxqq_back, br40_cxqq_mx);
		
		//将原来的动态查询的状态改成3
		br40_cxqq_back.setSeq("0");
		br40_cxqq_back.setStatus("3");
		br40_cxqq_back.setRwlsh(yrwlsh);
		br40_cxqqMapper.updateBr40_cxqq_back1(br40_cxqq_back);
		
		// 4、写反馈基本信息表 
		br40_cxqq_back.setStatus("2");//已处理
		br40_cxqq_back.setRwlsh(rwlsh);
		br40_cxqq_back.setCxfkjg("0");
		br40_cxqq_back.setCzsbyy("成功");
		br40_cxqq_back.setCzsbyy(getCzsbyy(br40_cxqq_back.getCxfkjg(), br40_cxqq_back.getCzsbyy(), mc21_task_fact.getTasktype())); // "操作失败原因"需特殊处理
		
		updateBr40_cxqq_back(br40_cxqq_back);
		
		// 5.插入任务task3
		//		int istaskok = br40_cxqqMapper.isTaskCount(br40_cxqq_back);
		//		if (istaskok == 0) {
		//			int isok = br40_cxqqMapper.getBr40_cxqq_backCount(br40_cxqq_back);
		//			if (isok == 0) {
		//				mc21_task_fact.setBdhm(br40_cxqq_back.getQqdbs());
		//				insertMc21TaskFact3(mc21_task_fact, "CBRC_" + mc21_task_fact.getTasktype());
		//			}
		//		}
		insertMc21TaskFact3(mc21_task_fact, "JSGA_" + mc21_task_fact.getTasktype());
	}
	
	/**
	 * 处理凭证图像任务 - task2
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	
	public void handleQueryResponse_pz(MC21_task_fact mc21_task_fact) throws Exception {
		
	}
	
	public void insertBr40_cxqq_acct_dynamic_js(Br40_cxqq_mx br40_cxqq_mx) throws Exception {
		// 删除请求单号下的数据
		Br40_cxqq br40_cxqq = new Br40_cxqq();
		String tasktype = br40_cxqq_mx.getTasktype();
		String qqdbs = br40_cxqq_mx.getQqdbs();
		br40_cxqq.setTasktype(tasktype);
		br40_cxqq.setQqdbs(qqdbs);
		br40_cxqq.setRwlsh(br40_cxqq_mx.getRwlsh());
		br40_cxqqMapper.delBr40_cxqq_acct_dynamic_js(br40_cxqq);
		
		br40_cxqq.setStatus("1");//状态  0：已解除  1：执行中
		br40_cxqq.setCxzh(br40_cxqq_mx.getCxzh());
		
		String mxqssj = Utility.toDate10(br40_cxqq_mx.getMxqssj()) + " 00:00:00";
		String mxjzsj = Utility.toDate10(br40_cxqq_mx.getMxjzsj()) + " 23:59:59";
		br40_cxqq.setMxqssj(mxqssj);
		br40_cxqq.setMxjzsj(mxjzsj);
		
		String qqcslx = br40_cxqq_mx.getQqcslx();
		if (Constants12.DYNAMIC.equals(qqcslx)) { // 动态查询
			br40_cxqq.setLastpollingtime(mxqssj);
		} else if (Constants12.DYNAMIC_CONTINUE.equals(qqcslx)) { // 继续动态查询
			//			String yjzsj = br40_cxqq_mx.getMxjzsjold() + " 23:59:59"; // 原截止时间
			//			br40_cxqq.setMxqssj(yjzsj); // 起始时间为原截止时间
			
			String currDateTime = Utility.currDateTime19();
			//			if (DtUtils.comp(yjzsj, currDateTime, 1) > 0) {
			//				br40_cxqq.setLastpollingtime(yjzsj); // 最后执行时间为原截止时间
			//			} else {
			//				br40_cxqq.setLastpollingtime(currDateTime); // 最后执行时间为当前时间
			//			}
			br40_cxqq.setMxqssj(currDateTime); // 起始时间调整为当前时间
			br40_cxqq.setLastpollingtime(currDateTime); // 最后执行时间为当前时间
			
			// 解除原动态查询
			String yrwlsh = br40_cxqq_mx.getYrwlsh();
			br40_cxqq_mx.setRwlsh(yrwlsh);
			br40_cxqq_mx.setQqdbs("");
			br40_cxqqMapper.updateBr40_cxqq_acct_dynamic_js(br40_cxqq_mx); // set status_cd = '0'
			
			Br40_cxqq_back br40_cxqq_back = new Br40_cxqq_back();
			br40_cxqq_back.setSeq("0");
			br40_cxqq_back.setStatus("3");
			br40_cxqq_back.setRwlsh(yrwlsh);
			br40_cxqq_back.setTasktype(tasktype);
			br40_cxqq_back.setQqdbs(qqdbs);
			br40_cxqqMapper.updateBr40_cxqq_back(br40_cxqq_back);
		}
		
		br40_cxqq.setQrydt(DtUtils.getNowDate());
		br40_cxqq.setPollinglock("0"); // 用于反馈时的序列计数
		br40_cxqqMapper.insertBr40_cxqq_acct_dynamic_js(br40_cxqq);
	}
	
	public Br40_cxqq getBr40_cxqq(MC21_task_fact mc21_task_fact) {
		Br40_cxqq br40_cxqq = new Br40_cxqq();
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
		br40_cxqq_back.setCxjssj(DtUtils.getNowDate());
		br40_cxqqMapper.updateBr40_cxqq_back(br40_cxqq_back);
		
	}
	
}
