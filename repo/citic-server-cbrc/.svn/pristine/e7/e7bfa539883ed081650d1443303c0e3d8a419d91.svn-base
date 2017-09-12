package com.citic.server.cbrc.task.taskBo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.cbrc.domain.Br40_cxqq;
import com.citic.server.cbrc.domain.Br40_cxqq_back;
import com.citic.server.cbrc.domain.Br40_cxqq_mx;
import com.citic.server.cbrc.domain.Br42_msg;
import com.citic.server.cbrc.domain.Br42_packet;
import com.citic.server.cbrc.domain.CBRC_ReturnReceipt;
import com.citic.server.cbrc.domain.request.CBRC_ControlReceiptRequest;
import com.citic.server.cbrc.domain.request.CBRC_ControlRecordRequest;
import com.citic.server.cbrc.domain.request.CBRC_ControlRequest_Receipt;
import com.citic.server.cbrc.domain.request.CBRC_ControlRequest_Record;
import com.citic.server.cbrc.domain.request.CBRC_ExecResult;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Account;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Customer;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Measure;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Priority;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_SubAccount;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Transaction;
import com.citic.server.cbrc.domain.request.CBRC_QueryScanRequest;
import com.citic.server.cbrc.domain.request.CBRC_QueryScanRequest_Record;
import com.citic.server.cbrc.mapper.MM40_cxqq_cbrcMapper;
import com.citic.server.cbrc.service.IDataOperateCBRC;
import com.citic.server.cbrc.service.RequestMessageServiceCBRC;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.Zip4jUtils;

public class Cxqq_FKBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_FKBo.class);
	private MM40_cxqq_cbrcMapper br40_cxqqMapper;
	private IDataOperateCBRC dataOperate;
	
	public Cxqq_FKBo(ApplicationContext ac) {
		super(ac);
		this.br40_cxqqMapper = (MM40_cxqq_cbrcMapper) ac.getBean("MM40_cxqq_cbrcMapper");
		dataOperate = (IDataOperateCBRC) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_CBRC);
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
	 * 在根据账卡号查询时，深圳公安局（代号：8）的报文与银监会的报文存在一些差异：
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
		boolean isTaskType8 = ServerEnvironment.TASK_TYPE_SHENZHEN.equals(taskType); // 是否深圳发文
		boolean isTaskType4 = ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType); // 是否国安发文
		boolean isTaskType5 = ServerEnvironment.TASK_TYPE_GAOJIAN.equals(taskType);
		
		// 1.获取查询任务数据
		for (Br40_cxqq_mx br40_cxqq_mx : mxList) {
			String cxnr = br40_cxqq_mx.getCxnr(); // 查询内容分三种（01-账户信息；02-账户交易明细；03-账户信息和交易明细）
			
			// 查询方式 - 用于判断根据账（卡）号或证照查询
			String queryType = null;
			if (isTaskType8) {
				// 深圳公安局直接获取查询方式
				queryType = br40_cxqq_mx.getCxfs(); // 根据证照查询时分三种查询方式（01/02/03）
			} else {
				// 公安部经侦、国安根据“查询类型”进行判断，改值并非监管给，而是Task根据查询账号是否为空赋予
				String cxlx = br40_cxqq_mx.getCxlx();
				if ("01".equals(cxlx)) { // 01 - 根据证照查询
					queryType = "01"; // 默认指定为根据三证查询
				}
			}
			
			// 特殊字段：基本数据项 - 操作失败原因
			String czsbyy_customer = ""; // 默认为空
			String czsbyy_trans = ""; // 默认为空
			logger.info("queryType" + queryType);
			// 两个特殊标记值
			boolean isQueryAccount = queryType == null || queryType.length() == 0; // 是否账卡号查询
			boolean isSpecialDeal = isTaskType8 && isQueryAccount; // 是否特殊处理深圳公安局账卡号查询请求
			
			List<CBRC_QueryRequest_Transaction> transactionList = null;
			CBRC_QueryRequest_Customer customer;
			if (CBRCConstants.CXNR_TRANSACTION.equals(cxnr)) {
				// 交易流水信息
				transactionList = br40_cxqqMapper.selectBr40_cxqq_back_trans(br40_cxqq_mx);
				if (transactionList == null || transactionList.size() == 0) {
					transactionList = new ArrayList<CBRC_QueryRequest_Transaction>();
					Br40_cxqq cxqq = new Br40_cxqq();
					BeanUtils.copyProperties(cxqq, br40_cxqq_mx);
					Br40_cxqq_back back = br40_cxqqMapper.selectBr40_cxqq_backByVo(cxqq);
					
					CBRC_QueryRequest_Transaction transaction = new CBRC_QueryRequest_Transaction();
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
					
					transaction.setCxfkjg(cxfkjg); // 
					transaction.setCxfkjgyy(cxfkjgyy); // 
					transactionList.add(transaction);
					czsbyy_trans = "失败"; //
				}
				customer = new CBRC_QueryRequest_Customer();
				customer.setTransactionList(transactionList);
			} else {
				// 客户信息
				customer = br40_cxqqMapper.selectBr40_cxqq_back_party(br40_cxqq_mx);
				if (customer == null) { // 这里只会出现 Task2 处理失败的情况吗？
					Br40_cxqq cxqq = new Br40_cxqq();
					BeanUtils.copyProperties(cxqq, br40_cxqq_mx);
					Br40_cxqq_back back = br40_cxqqMapper.selectBr40_cxqq_backByVo(cxqq);
					
					customer = new CBRC_QueryRequest_Customer();
					customer.setQqdbs(br40_cxqq_mx.getQqdbs());
					customer.setRwlsh(br40_cxqq_mx.getRwlsh());
					if ("01".equals(queryType)) {
						customer.setZzlx(br40_cxqq_mx.getZzlx());
						customer.setZzhm(br40_cxqq_mx.getZzhm());
						customer.setKhmc(br40_cxqq_mx.getZtmc());
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
					
					// 查询内容 = 账户信息和交易明细
					// 国家安全机关即使没有客户信息也需要返回空的交易流水报文
					if (isTaskType4 && CBRCConstants.CXNR_ACC_AND_TRANS.equals(cxnr)) {
						transactionList = new ArrayList<CBRC_QueryRequest_Transaction>();
						CBRC_QueryRequest_Transaction transaction = new CBRC_QueryRequest_Transaction();
						transaction.setRwlsh(br40_cxqq_mx.getRwlsh());
						transaction.setCxfkjg("02"); // 02-失败
						transaction.setCxfkjgyy("查无交易流水：" + customer.getCxfkjgyy());
						transactionList.add(transaction);
						customer.setTransactionList(transactionList);
						czsbyy_trans = "失败"; // 
					}
				} else {
					// 账户信息
					List<CBRC_QueryRequest_Account> accountList = br40_cxqqMapper.selectBr40_cxqq_back_acct(br40_cxqq_mx);
					// 深圳公安要求<ACCOUNTS>下必须有子标签，判断List为空时，传一个空对象，保证有<ACCOUNT>标签。
					if (isTaskType8 && (accountList.size() < 1)) {
						CBRC_QueryRequest_Account szAccount = new CBRC_QueryRequest_Account();
						accountList.add(szAccount);
					}
					customer.setAccountList(accountList);
					
					// 根据查询方式是否有值，判断是根据证照查询或是根据账卡号查询
					if (isSpecialDeal) { // 深圳公安局根据账卡号查询
					} else {
						// 强制措施信息
						List<CBRC_QueryRequest_Measure> measureList = br40_cxqqMapper.selectBr40_cxqq_back_acct_qzcs(br40_cxqq_mx);
						customer.setMeasureList(measureList);
						
						// 共有权/优先权信息
						List<CBRC_QueryRequest_Priority> prioritiesList = br40_cxqqMapper.selectBr40_cxqq_back_acct_ql(br40_cxqq_mx);
						customer.setPrioritiesList(prioritiesList);
						
						// 子账户信息
						List<CBRC_QueryRequest_SubAccount> subAccountList = br40_cxqqMapper.selectBr40_cxqq_back_acct_sub(br40_cxqq_mx);
						customer.setSubAccountList(subAccountList);
					}
					
					// 交易流水信息
					if (CBRCConstants.CXNR_ACC_AND_TRANS.equals(cxnr)) { // 查询内容 = 账户信息和交易明细
						transactionList = br40_cxqqMapper.selectBr40_cxqq_back_trans(br40_cxqq_mx);
						if (transactionList == null || transactionList.size() == 0) {
							transactionList = new ArrayList<CBRC_QueryRequest_Transaction>();
							CBRC_QueryRequest_Transaction transaction = new CBRC_QueryRequest_Transaction();
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
					List<CBRC_QueryRequest_Account> accountList = customer.getAccountList();
					if (accountList == null || accountList.size() == 0) {
						// B引用A
						CBRC_QueryRequest_Account account = new CBRC_QueryRequest_Account();
						account.setCustomer(customer);
						// A引用B
						accountList = new ArrayList<CBRC_QueryRequest_Account>();
						accountList.add(account);
						customer.setAccountList(accountList);
					} else {
						for (CBRC_QueryRequest_Account account : accountList) {
							account.setCustomer(customer);
						}
					}
				}
			}
			
			List<CBRC_QueryRequest_Customer> customerList = new ArrayList<CBRC_QueryRequest_Customer>();
			customerList.add(customer);
			
			// 生成XML报文
			CBRC_QueryRequest queryRequest = new CBRC_QueryRequest();
			queryRequest.setQqdbs(br40_cxqq.getQqdbs());
			queryRequest.setZtlb(br40_cxqq_mx.getZtlb());
			queryRequest.setSqjgdm(br40_cxqq.getSqjgdm());
			queryRequest.setMbjgdm(br40_cxqq.getMbjgdm());
			queryRequest.setCxjssj(Utility.currDateTime19());
			queryRequest.setHzsj(Utility.currDateTime19());
			queryRequest.setYhzjsjsj(yhzjsjsj);
			queryRequest.setSfxd("01"); // 此处如果支持重发修订模式，则应设置为'02'
			queryRequest.setCustomerList(customerList);
			
			if (CBRCConstants.CXNR_ACCOUNT.equals(cxnr) || CBRCConstants.CXNR_ACC_AND_TRANS.equals(cxnr)) {
				queryRequest.setCzsbyy(czsbyy_customer); //
				String xmlType = CBRCConstants.valueOf(br40_cxqq_mx.getXmltype(), "SS01"); // 根据请求代码确定分类代码
				String bindingName = isQueryAccount ? "cbrc_queryaccount_req" : "cbrc_querycustomer_req";
				String xmlPath = generateXMLDocument(queryRequest, br40_cxqq, xmlType, bindingName, taskType);
				// 文件地址入库
				insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
			}
			
			if (CBRCConstants.CXNR_TRANSACTION.equals(cxnr) || CBRCConstants.CXNR_ACC_AND_TRANS.equals(cxnr)) {
				if (transactionList == null || transactionList.size() == 0) {
					// CBRCConstants.CXNR_TRANSACTION: 交易流水不可能为空，即使为空，也会有一条反馈错误信息的数据；
					// CBRCConstants.CXNR_ACC_AND_TRANS: 可能没有查询交易流水，或者甚至账户都没有，不管哪种情况，都不需要生成XML文件。
				} else {
					queryRequest.setCzsbyy(czsbyy_trans); // 
					String xmlType = CBRCConstants.valueOf(br40_cxqq_mx.getZtlb(), "SS09"); // 根据主体类别确定分类代码
					String xmlPath = generateXMLDocument(queryRequest, br40_cxqq, xmlType, "cbrc_querytransaction_req", taskType);
					// 文件地址入库
					insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
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
		//5.写task3生成数据包，需判断查询请求表(Br40_cxqq_back)中同请求单编码(Qqdbs)下的状态只为（2.已生成报文 ）才能写打包任务
		int i = br40_cxqqMapper.isPacketCount(br40_cxqq);
		if (i == 0) {
			// 置主表状态
			br40_cxqq.setStatus("2"); // 已处理
			br40_cxqqMapper.updateBr40_cxqq(br40_cxqq);
			
			if (isTaskType8) {
				//无需
			} else {
				//请求执行结束回执信息内容
				dealReturnReceipt29(br40_cxqq);
			}
			String prefix = "CBRC_" + taskType;
			mc21_task_fact.setTaskkey("'T3_" + prefix + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
			mc21_task_fact.setDatatime(DtUtils.getNowTime());
			mc21_task_fact.setTaskid("TK_ESYJ_FK06");
			if (!ServerEnvironment.TASK_TYPE_GONGAN.equals(taskType)) {
				mc21_task_fact.setTaskid("TK_ESYJ_FK06_" + taskType);
			}
			if (isTaskType8) {
				mc21_task_fact.setBdhm(br40_cxqq.getQqdbs() + "$" + br40_cxqq.getMbjgdm().substring(0, 8));
			} else {
				mc21_task_fact.setBdhm(br40_cxqq.getQqdbs() + "$" + br40_cxqq.getMbjgdm());
			}
			mc21_task_fact.setTaskobj(br40_cxqq.getSqjgdm());
			if (isTaskType5) {
				packageXmlFileList(mc21_task_fact);//将所有xml打包发送
			} else {
				common_Mapper.insertMc21_task_fact3_packet(mc21_task_fact);
				
			}
		}
	}
	
	// 最高检打包发送
	public void packageXmlFileList(MC21_task_fact mc21_task_fact) throws Exception {
		String taskType = mc21_task_fact.getTasktype(); // 任务类型
		Br40_cxqq br40_cxqq = this.getBr40_cxqqFortask3(mc21_task_fact);
		String qqdbs = br40_cxqq.getQqdbs();
		String organkey = br40_cxqq.getMbjgdm();
		String sqjgdm = br40_cxqq.getSqjgdm();
		
		String rootPath = ServerEnvironment.getFileRootPath();
		String relativePath = CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, CBRCKeys.getFileDirectoryKey(taskType)) + File.separator + qqdbs;
		
		Br42_packet packet = new Br42_packet();
		packet.setTasktype(taskType);
		packet.setPacketkey(qqdbs);
		ArrayList<File> xmlFileList = new ArrayList<File>();
		List<Br42_msg> msgList = br40_cxqqMapper.getBr42_msg(packet);
		for (Br42_msg _msg : msgList) {
			xmlFileList.add(new File(_msg.getMsg_filepath()));
		}
		int n = 2;
		for (File xmlfile : xmlFileList) {
			String xmlZipFileSEQ = "00000000000" + n;
			xmlZipFileSEQ = xmlZipFileSEQ.substring(xmlZipFileSEQ.length() - 12, xmlZipFileSEQ.length());
			ArrayList<File> xmlFiles = new ArrayList<File>();
			xmlFiles.add(xmlfile);
			// CBRC命名规范：查控机构代码【6/8位】 +银行代码【17位】+请求单标识【22位】+序号【12位，000000000001-999999999999】.ZIP
			String realZipFileName = sqjgdm + organkey + qqdbs + xmlZipFileSEQ;
			String zipFileName = realZipFileName + ".zip";
			String absoluteZipFilePath = rootPath + relativePath + File.separator + zipFileName;
			// 先删除可能已经存在的同名压缩包文件
			FileUtils.deleteFile(absoluteZipFilePath);
			Zip4jUtils.addFilesWithDeflateComp(absoluteZipFilePath, xmlFiles); // 无密码压缩方式
			//发送
			packet.setFilename(zipFileName);
			packet.setFilepath(relativePath + File.separator + zipFileName);
			RequestMessageServiceCBRC service = new RequestMessageServiceCBRC();
			CBRC_ReturnReceipt returnReceipt = service.sendZipPackage(packet);
			String bdhm = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
			if (returnReceipt == null) {
				returnReceipt = new CBRC_ReturnReceipt();
				returnReceipt.setQqdbs(bdhm); // 请求单标识
				returnReceipt.setHzdm(CBRCConstants.REC_CODE_99999); // 回执代码n 
				returnReceipt.setJssj(Utility.currDateTime19()); // 接收时间
				returnReceipt.setHzsm("未知错误"); // 回执说明
				returnReceipt.setTasktype(mc21_task_fact.getTasktype()); // 任务类型
			} else {
				returnReceipt.setQqdbs(bdhm); // 请求单标识
				returnReceipt.setTasktype(mc21_task_fact.getTasktype()); // 任务类型
			}
			br40_cxqqMapper.delBr40_receipt(returnReceipt);
			br40_cxqqMapper.insertBr40_receipt(returnReceipt);
			// 查询类
			br40_cxqqMapper.updateBr40_cxqqStatus(returnReceipt); // 已打包
			n++;
		}
		for (File xmlFiles : xmlFileList) {
			if (xmlFiles.exists()) {
				xmlFiles.delete();
			}
		}
	}
	
	public void dealReturnReceipt29(Br40_cxqq br40_cxqq) throws Exception {
		Br40_cxqq_mx br40_cxqq_mx = new Br40_cxqq_mx();
		CBRC_ExecResult execResult = new CBRC_ExecResult();
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
		
		String xmlPath = generateXMLDocument(execResult, br40_cxqq, "RR29", "cbrc_execresult_req", br40_cxqq.getTasktype());
		
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
		
		CBRC_ControlReceiptRequest request01 = new CBRC_ControlReceiptRequest();
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
			Br40_cxqq_mx dynamicTaskInfo = br40_cxqqMapper.selectBr40_cxqq_mxByVo(dynamicTask);
			
			CBRC_ControlRequest_Receipt controlrequest = new CBRC_ControlRequest_Receipt();
			controlrequest.setRwlsh(cxqqback.getRwlsh());
			controlrequest.setZh(cxqqback.getZh());
			controlrequest.setKh(cxqqback.getKh());
			controlrequest.setZxjg(cxqqback.getCxfkjg());
			controlrequest.setSbyy(cxqqback.getCzsbyy());
			controlrequest.setFksjhm(cxqqback.getFksjhm());
			controlrequest.setZxqssj(dynamicTaskInfo.getMxqssj()); // 被解除的动态查询任务的起始时间
			controlrequest.setZxsjqj("0"); // 0-解除动态查询（措施类型）
			controlrequest.setJssj(currDateTime); // 结束时间为当前时间
			
			List<CBRC_ControlRequest_Receipt> list = new ArrayList<CBRC_ControlRequest_Receipt>();
			list.add(controlrequest);
			
			request01.setControlReceiptList(list);
			
			String xmlType = CBRCConstants.valueOf(br40_cxqq_mx.getXmltype()); // 根据主体类别确定分类代码
			String taskType = mc21_task_fact.getTasktype();
			if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) {
				xmlType = "RR30";
			}
			String xmlPath = generateXMLDocument(request01, br40_cxqq, xmlType, "cbrc_controlreceipt_req", br40_cxqq.getTasktype());
			// 文件地址入库
			insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
			
			//4.设置BR40_CXQQ_BACK 査询请求反馈主表 3成功 4失败
			Br40_cxqq_back back = new Br40_cxqq_back();
			back.setTasktype(br40_cxqq_mx.getTasktype());
			back.setQqdbs(br40_cxqq_mx.getQqdbs());
			back.setRwlsh(br40_cxqq_mx.getRwlsh());
			back.setStatus("6");
			br40_cxqqMapper.updateBr40_cxqq_back(back);
		}
		
		//5.写task3生成数据包，需判断查询请求表(Br40_cxqq_back)中同请求单编码(Qqdbs)下的状态只为（2.已生成报文 ）才能写打包任务
		int i = br40_cxqqMapper.isPacketCount(br40_cxqq);
		String taskType = mc21_task_fact.getTasktype();
		if (i == 0) {
			if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType)
				|| ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) {
				//无需
			} else {
				//请求执行结束回执信息内容
				dealReturnReceipt29(br40_cxqq);
			}
			// 置主表状态
			br40_cxqq.setStatus("2"); // 已处理
			br40_cxqqMapper.updateBr40_cxqq(br40_cxqq);
			
			mc21_task_fact.setTaskkey("'T3_" + "CBRC" + mc21_task_fact.getTasktype() + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
			mc21_task_fact.setDatatime(DtUtils.getNowTime());
			mc21_task_fact.setTaskid("TK_ESYJ_FK06");
			if (!ServerEnvironment.TASK_TYPE_GONGAN.equals(taskType)) {
				mc21_task_fact.setTaskid("TK_ESYJ_FK06_" + mc21_task_fact.getTasktype());
			}
			if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(mc21_task_fact.getTasktype())) {
				mc21_task_fact.setBdhm(br40_cxqq.getQqdbs() + "$" + br40_cxqq.getMbjgdm().substring(0, 8));
			} else {
				mc21_task_fact.setBdhm(br40_cxqq.getQqdbs() + "$" + br40_cxqq.getMbjgdm());
			}
			mc21_task_fact.setTaskobj(br40_cxqq.getSqjgdm());
			common_Mapper.insertMc21_task_fact3_packet(mc21_task_fact);
			
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
		String rwlsh = StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$");
		if (mc21_task_fact.getTaskobj().equals("HZ")) {
			br40_cxqq.setSeq("0");
			br40_cxqq.setQqdbs(qqdbs);
			List<Br40_cxqq_back> backlist = br40_cxqqMapper.selectBr40_cxqq_backByList(br40_cxqq);
			//1.feedbackinfo银行反馈的动态查询执行结果信息
			CBRC_ControlReceiptRequest request01 = new CBRC_ControlReceiptRequest();
			request01.setQqdbs(qqdbs);
			
			request01.setSqjgdm(br40_cxqq.getSqjgdm());
			request01.setMbjgdm(br40_cxqq.getMbjgdm());
			request01.setHzsj(DtUtils.getNowTime());
			request01.setCzsbyy("");
			for (Br40_cxqq_back _cxqqback : backlist) {
				List<CBRC_ControlRequest_Receipt> list = new ArrayList<CBRC_ControlRequest_Receipt>();
				br40_cxqq.setRwlsh(_cxqqback.getRwlsh());
				br40_cxqq_mx = this.getQueryRequest_mx(br40_cxqq);
				CBRC_ControlRequest_Receipt controlrequest = new CBRC_ControlRequest_Receipt();
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
				
				String xmlType = CBRCConstants.valueOf(br40_cxqq_mx.getXmltype()); // 根据主体类别确定分类代码
				String taskType = mc21_task_fact.getTasktype();
				if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) {
					xmlType = "RR30";
				}
				String xmlPath = generateXMLDocument(request01, br40_cxqq, xmlType, "cbrc_controlreceipt_req", br40_cxqq.getTasktype());
				// 文件地址入库
				insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
			}
			
			String taskType = mc21_task_fact.getTasktype();
			if (ServerEnvironment.TASK_TYPE_GONGAN.equals(taskType)) {
				dealReturnReceipt29(br40_cxqq);//请求执行结束回执信息内容
			}
			
			mc21_task_fact.setTaskkey("'T3_" + "CBRC_" + mc21_task_fact.getTasktype() + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
			mc21_task_fact.setDatatime(DtUtils.getNowTime());
			mc21_task_fact.setTaskid("TK_ESYJ_FK06");
			if (!ServerEnvironment.TASK_TYPE_GONGAN.equals(taskType)) {
				mc21_task_fact.setTaskid("TK_ESYJ_FK06_" + taskType);
			}
			
			mc21_task_fact.setTaskobj(br40_cxqq.getSqjgdm());
			if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(mc21_task_fact.getTasktype())) {
				mc21_task_fact.setBdhm(qqdbs + "$" + br40_cxqq.getMbjgdm().substring(0, 8));
			} else {
				mc21_task_fact.setBdhm(qqdbs + "$" + br40_cxqq.getMbjgdm());
			}
			// mc21_task_fact.setTgroupid(cxqqback.getSeq());
			common_Mapper.insertMc21_task_fact3_packet(mc21_task_fact);
		} else {
			br40_cxqq.setSeq(mc21_task_fact.getTgroupid());
			cxqqback = br40_cxqqMapper.selectBr40_cxqq_backByVo(br40_cxqq);
			CBRC_ControlRecordRequest request02 = new CBRC_ControlRecordRequest();
			request02.setQqdbs(qqdbs);
			request02.setZtlb(br40_cxqq_mx.getZtlb());
			request02.setSqjgdm(br40_cxqq.getSqjgdm());
			request02.setMbjgdm(br40_cxqq.getMbjgdm());
			request02.setCxjssj(DtUtils.getNowTime());
			cxqqback.setRwlsh(cxqqback.getRwlsh() + "_" + cxqqback.getSeq());
			List<CBRC_ControlRequest_Record> tranList = br40_cxqqMapper.selectBr40_cxqq_back_trans_dtList(cxqqback);
			String taskType = mc21_task_fact.getTasktype();
			if (tranList != null) {
				request02.setControlRecordList(tranList);
				String xmlType = br40_cxqq_mx.getXmltype().equals("SS11") ? "SS15" : "SS16";// 根据主体类别确定分类代码
				if (ServerEnvironment.TASK_TYPE_GUOAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GUOAN_YUNNAN.equals(taskType)) {
					xmlType = CBRCConstants.valueOf(br40_cxqq_mx.getXmltype()); // 国安没有SS15，SS16
				}
				
				String xmlPath = generateXMLDocument(request02, br40_cxqq, xmlType, "cbrc_controlrecord_req", br40_cxqq.getTasktype());
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
				
				mc21_task_fact.setTaskkey("'T3_" + "CBRC_" + mc21_task_fact.getTasktype() + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
				mc21_task_fact.setDatatime(DtUtils.getNowTime());
				mc21_task_fact.setTaskid("TK_ESYJ_FK06");
				if (!ServerEnvironment.TASK_TYPE_GONGAN.equals(taskType)) {
					mc21_task_fact.setTaskid("TK_ESYJ_FK06_" + taskType);
				}
				
				mc21_task_fact.setTaskobj(br40_cxqq.getSqjgdm());
				if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(mc21_task_fact.getTasktype())) {
					mc21_task_fact.setBdhm(qqdbs + "$" + br40_cxqq.getMbjgdm().substring(0, 8));
				} else {
					mc21_task_fact.setBdhm(qqdbs + "$" + br40_cxqq.getMbjgdm());
				}
				
				String tgroupid;
				if (rwlsh != null && rwlsh.length() >= 5) {
					tgroupid = rwlsh.substring(rwlsh.length() - 5) + StringUtils.leftPad(cxqqback.getSeq(), 7, '0');
				} else {
					tgroupid = cxqqback.getSeq();
				}
				mc21_task_fact.setTgroupid(tgroupid + "$" + cxqqback.getRwlsh());
				
				common_Mapper.insertMc21_task_fact3_packet(mc21_task_fact);
			}
		}
	}
	
	public void insertBr40_receipt(Br40_cxqq br40_cxqq, CBRC_ReturnReceipt message) throws Exception {
		message.setTasktype(br40_cxqq.getTasktype());
		message.setQqdbs(br40_cxqq.getQqdbs());
		
		br40_cxqqMapper.delBr40_receipt(message);
		br40_cxqqMapper.insertBr40_receipt(message);
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
		String absolutePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_ATTACH, CBRCKeys.getFileDirectoryKey(taskType));
		absolutePath = absolutePath + File.separator + br40_cxqq.getQqdbs();
		String mbjgdm = br40_cxqq.getMbjgdm(); // 目标机构代码（银行代码）
		if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(taskType)) { // 深圳公安局
			mbjgdm = mbjgdm.substring(0, 8); // 截取前8位
		}
		String filename = this.getMsgNameCBRC(br40_cxqq.getTasktype(), xmltype, br40_cxqq.getQqdbs(), mbjgdm) + ".xml";
		CommonUtils.marshallUTF8Document(request, bindingname + taskType, absolutePath, filename);
		
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
	
	public void FeedBack_Pz(MC21_task_fact mc21_task_fact) throws Exception {
		Br40_cxqq br40_cxqq = this.getBr40_cxqq(mc21_task_fact);
		String qqdbs = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
		String rwlsh = StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$");
		String tasktype = mc21_task_fact.getTasktype();
		br40_cxqq.setRwlsh(rwlsh);
		List<CBRC_QueryScanRequest_Record> pzList = new ArrayList<CBRC_QueryScanRequest_Record>();
		//修改附件表序列
		br40_cxqqMapper.updateBr40_cxqq_back_pz_attach(br40_cxqq);
		if (tasktype.equals("5")) { //高检
			pzList = br40_cxqqMapper.selBr40_cxqq_back_pz5List(br40_cxqq);
		} else {
			pzList = br40_cxqqMapper.selBr40_cxqq_back_pzList(br40_cxqq);
		}
		CBRC_QueryScanRequest request02 = new CBRC_QueryScanRequest();
		request02.setQqdbs(qqdbs);
		request02.setZtlb(br40_cxqq.getZtlb());
		request02.setSqjgdm(br40_cxqq.getSqjgdm());
		request02.setMbjgdm(br40_cxqq.getMbjgdm());
		request02.setQqcslx(br40_cxqq.getQqcslx());
		request02.setCxjssj(DtUtils.getNowTime());
		request02.setCzsbyy("");
		for (CBRC_QueryScanRequest_Record scanRecord : pzList) {
			String zxjg = scanRecord.getCxfkjg();
			if (zxjg.equals("1")) {
				request02.setCzsbyy("失败");
			}
			if (tasktype.equals("5")) { //高检
				String filename = scanRecord.getPztxmc();
				if (filename.indexOf(".") > 0) {
					scanRecord.setPztxlx(filename.substring(filename.indexOf(".") + 1));
				}
			}
		}
		
		request02.setScanRecoredList(pzList);
		String xmlType = "SS26";
		String xmlPath = generateXMLDocument(request02, br40_cxqq, xmlType, "cbrc_queryscan_req", br40_cxqq.getTasktype());
		// 文件地址入库
		Br40_cxqq_mx br40_cxqq_mx = new Br40_cxqq_mx();
		br40_cxqq_mx.setRwlsh(rwlsh);
		br40_cxqq_mx.setQqdbs(qqdbs);
		br40_cxqq_mx.setTasktype(tasktype);
		insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
		
		//修改反馈表的状态
		br40_cxqq.setStatus("6");
		br40_cxqqMapper.updateBr40_cxqq_pz(br40_cxqq);
		
		//5.写task3生成数据包，需判断查询请求表(Br40_cxqq_back)中同请求单编码(Qqdbs)下的状态只为（6.已生成报文 ）才能写打包任务
		int i = br40_cxqqMapper.isPzPacketCount(br40_cxqq);
		if (i == 0) {
			// 置主表状态
			br40_cxqq.setStatus("2"); // 已处理
			br40_cxqqMapper.updateBr40_cxqq(br40_cxqq);
			
			// 请求执行结束回执信息内容
			// dealReturnReceiptPz29(br40_cxqq);
			
			String prefix = "CBRC_" + tasktype;
			mc21_task_fact.setTaskkey("'T3_" + prefix + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
			mc21_task_fact.setDatatime(DtUtils.getNowTime());
			mc21_task_fact.setTaskid("TK_ESYJ_FK08_" + tasktype);
			if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(mc21_task_fact.getTasktype())) {
				mc21_task_fact.setBdhm(qqdbs + "$" + br40_cxqq.getMbjgdm().substring(0, 8));
			} else {
				mc21_task_fact.setBdhm(qqdbs + "$" + br40_cxqq.getMbjgdm());
			}
			mc21_task_fact.setTaskobj(br40_cxqq.getSqjgdm());
			common_Mapper.insertMc21_task_fact3_packet(mc21_task_fact);
		}
	}
	
	public void dealReturnReceiptPz29(Br40_cxqq br40_cxqq) throws Exception {
		
		CBRC_ExecResult execResult = new CBRC_ExecResult();
		
		execResult.setQqdbs(br40_cxqq.getQqdbs());
		execResult.setQqcslx(br40_cxqq.getQqcslx());
		execResult.setMbjgdm(br40_cxqq.getMbjgdm());
		execResult.setSqjgdm(br40_cxqq.getSqjgdm());
		execResult.setHzsj(Utility.currDateTime14());
		br40_cxqq.setRwlsh("");
		List<CBRC_QueryScanRequest_Record> mxlist = br40_cxqqMapper.selBr40_cxqq_back_pzList(br40_cxqq);
		
		execResult.setJsrws(mxlist.size() + "");
		execResult.setFkrws(mxlist.size() + "");
		
		execResult.setFkkhs("0");
		execResult.setFkzhs("0");
		execResult.setFkjymxs("0");
		
		String xmlPath = generateXMLDocument(execResult, br40_cxqq, "RR29", "cbrc_execresult_req", br40_cxqq.getTasktype());
		
		// 文件地址入库
		Br40_cxqq_mx br40_cxqq_mx = new Br40_cxqq_mx();
		br40_cxqq_mx.setTasktype(br40_cxqq.getTasktype());
		br40_cxqq_mx.setQqdbs(br40_cxqq.getQqdbs());
		br40_cxqq_mx.setRwlsh("RR29");
		insertBr42_msg(br40_cxqq_mx, xmlPath, br40_cxqq.getMbjgdm());
	}
}
