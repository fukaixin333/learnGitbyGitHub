package com.citic.server.gdjc.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.citic.server.gdjc.GdjcConstants;
import com.citic.server.gdjc.domain.Br50_cxqq_mx;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Acc;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Bank;
import com.citic.server.gdjc.domain.request.Gdjc_RequestLsdj_Acc;
import com.citic.server.gdjc.domain.request.Gdjc_RequestLsdj_Trans;
import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ContractAccount;
import com.citic.server.inner.domain.SubAccountInfo;
import com.citic.server.inner.domain.response.AccountDetail;
import com.citic.server.inner.service.AnswerCodeService;
import com.citic.server.inner.service.IPrefixMessageService;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.DtUtils;
import com.google.common.collect.Lists;

/**
 * @author Liu Xuanfei
 * @date 2016年8月17日 下午2:39:35
 */
@Service("remoteDataOperateGdjc")
public class RemoteDataOperateGdjc implements IDataOperateGdjc {
	@Autowired
	@Qualifier("innerMessageService")
	private IPrefixMessageService messageService;
	
	@Autowired
	@Qualifier("answerCodeService")
	private AnswerCodeService exceptionService;
	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.citic.server.gdjc.service.IDataOperateGdjc#getAccountDetail(com.citic
	 * .server.gdjc.domain.Br50_cxqq_mx)
	 */
	@Override
	public Gdjc_RequestCkdj_Bank getAccountDetail(Br50_cxqq_mx br50_cxqq_mx) throws DataOperateException, RemoteAccessException {
		
		Gdjc_RequestCkdj_Bank requestckdj_bank = new Gdjc_RequestCkdj_Bank();
		List<Gdjc_RequestCkdj_Acc> accs = new ArrayList<Gdjc_RequestCkdj_Acc>();
		AccountDetail accountDetail = null;
		Gdjc_RequestCkdj_Acc mainAcc = null;
		
		// 根据类型分为对公、对私、账户等三类查询
		String type = br50_cxqq_mx.getType();
		String primaryAccount = br50_cxqq_mx.getAccount();
		
		if (StringUtils.equals(GdjcConstants.TYPE_ACCOUNT, type)) { // 按账号查询
			accountDetail = messageService.queryAccountDetail(br50_cxqq_mx.getAccount(), null, null);
			if (accountDetail == null || StringUtils.isEmpty(accountDetail.getCustomerType())) {
				return null;
			}
			mainAcc = chgMainAcc(accountDetail);
			mainAcc.setCardno(primaryAccount);
			
			if (StringUtils.equals("1", accountDetail.getCustomerType())) { // 对私客户账户
				// 参见示例3，一张银行卡对应多个账户，没有子账户（需要返回一条卡号信息，卡号关联的一条账户信息以及该账户下的子账户信息
				mainAcc.setCurrency("");// 币种,卡不需要返回币种
				accs.add(mainAcc);
				accs.addAll(getRequestCkdj_AccinfoList(br50_cxqq_mx.getAccount(), mainAcc));// 子账户明细
				requestckdj_bank.setAccs(accs);
			} else {// 对公客户账户，同业客户视同对公客户
				accs.add(getSubAcc(mainAcc));
				// 参见示例5.仅存在一个独立账户的情况（需要返回一条账户信息）
				requestckdj_bank.setAccs(accs);
			}
		} else {
			String name = br50_cxqq_mx.getName();
			String idtype = StringUtils.trimToEmpty(getIDType(br50_cxqq_mx).getIdtype());
			String id = getIDType(br50_cxqq_mx).getId();
			//			String queryMode = StringUtils.trimToEmpty(br50_cxqq_mx.getQuerymode());
			if (StringUtils.isEmpty(name) || StringUtils.isEmpty(idtype) || StringUtils.isEmpty(id)) { // 如果名称、证件号码、证件名称有一个为空，则返回失败
				throw new DataOperateException("9999", "暂不支持该类查询");
			}
			// 转换证件类型
			// idtype = "4".equals(queryMode) ? "20600" : "5".equals(queryMode) ? "20500": "6".equals(queryMode) ? "24100" : idtype;
			// 主账户详细信息列表
			List<ContractAccount> contractAccounts = messageService.queryContractAccountList(idtype, id, name);
			if (contractAccounts == null || contractAccounts.size() == 0) {
				return null;
			}
			
			for (ContractAccount account : contractAccounts) {
				String contractType = account.getContractType();
				// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
				if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
					accountDetail = messageService.queryAccountDetail(account.getAccountNumber(), account.getCurrency(), null);
					mainAcc = chgMainAcc(accountDetail);
					mainAcc.setCardno(account.getAccountNumber());
					// 参见示例3，一张银行卡对应多个账户，没有子账户（需要返回一条卡号信息，卡号关联的一条账户信息以及该账户下的子账户信息
					if (GdjcConstants.TYPE_PERSON.equals(type)) {// 只有对私客户返回关联子账户
						mainAcc.setCurrency("");// 币种,卡不需要返回币种
						accs.add(mainAcc); // 对于对私客户，首先加主账户，在加子账户，分两层结构
						List<Gdjc_RequestCkdj_Acc> subacclist = getRequestCkdj_AccinfoList(account.getAccountNumber(), mainAcc);
						accs.addAll(subacclist);
					} else { // 对于对公客户，只加一层账户
						// 参见示例5.仅存在一个独立账户的情况（需要返回一条账户信息）
						accs.add(getSubAcc(mainAcc));
					}
				}
			}
			
			requestckdj_bank.setAccs(accs);
			
		}
		
		return requestckdj_bank;
	}
	
	/**
	 * 主账户转换
	 * 
	 * @param accountDetail
	 * @return
	 */
	private Gdjc_RequestCkdj_Acc chgMainAcc(AccountDetail accountDetail) {
		Gdjc_RequestCkdj_Acc acc = new Gdjc_RequestCkdj_Acc();
		
		// 默认全部为卡信息，无卡账户也视同卡信息
		acc.setDatatype("1");
		if (accountDetail != null) {
			acc.setCardstatus(accountDetail.getAccountStatus());
			acc.setAccname(accountDetail.getCustomerName());
			acc.setExchangetype("");// 汇钞类型
			acc.setOpendate(accountDetail.getCardOpenDate());// 开户日期
			acc.setTradedate("");// 最后交易日期
			acc.setOpenbranchno(accountDetail.getAccountOpenBranch());// 开户网点编号
			acc.setOpenbranchname(accountDetail.getAccountOpenBranch());// 开户网点名称
			acc.setOpenbranchtel("");// 开户网点电话
			acc.setOpenbranchaddr("");// 开户网点地址
			acc.setAddr("");// 账户人联系地址
			acc.setTel("");// 账户人联系电话
			acc.setStatusflag(accountDetail.getAccountStatus());// 账户状态标识
			acc.setStatus(accountDetail.getAccountStatus());// 账户状态
			acc.setClosedate(accountDetail.getAccountClosingDate());// 销户日期
			acc.setClosebranchno(accountDetail.getAccountClosingBranch());// 销户网点编号
			acc.setClosebranchname("");// 销户网点名称
			acc.setQuerytime(DtUtils.getNowTime());// 主机查询时间
			acc.setCurrency(accountDetail.getCurrency());// 币种
			acc.setBanlance("");// 余额,卡不需要返回余额
		}
		
		return acc;
	}
	
	/**
	 * 根据主账户，返回该主账户详细信息，适用于对公账户信息
	 * 
	 * @param mainAcc
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	private Gdjc_RequestCkdj_Acc getSubAcc(Gdjc_RequestCkdj_Acc mainAcc) throws DataOperateException, RemoteAccessException {
		Gdjc_RequestCkdj_Acc accountInfo = getAccinfo(mainAcc.getCardno(), mainAcc.getCurrency(), null);
		accountInfo.setAccname(mainAcc.getAccname());// 户名
		accountInfo.setCardno("");// 卡号,对公时，卡号得清空   
		
		return accountInfo;
	}
	
	/**
	 * 根据账号及币种，查询账户详细信息，并将核心返回数据转换为省纪委需要的DTO <br>
	 * 注意：由于子账户中不存在，故卡号及户名需单独赋值，
	 * 
	 * @param accountNumber
	 * @param currency
	 * @param cashExCode
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	private Gdjc_RequestCkdj_Acc getAccinfo(String accountNumber, String currency, String cashExCode) throws DataOperateException, RemoteAccessException {
		Gdjc_RequestCkdj_Acc accountInfo = new Gdjc_RequestCkdj_Acc();
		// 远程请求
		AccountDetail subAccountDetail = messageService.queryAccountDetail(accountNumber, currency, cashExCode);
		accountInfo.setAcctype(subAccountDetail.getAccountAttr());// 账户类型
		accountInfo.setAccount(accountNumber);// 账号
		accountInfo.setSubaccount(subAccountDetail.getV_AccountNumber());// 子帐号
		accountInfo.setDatatype("2");// 数据类型
		accountInfo.setBanlance(trimToZero(subAccountDetail.getAccountBalance()));// 余额
		accountInfo.setCurrency(currency);// 币种
		accountInfo.setExchangetype(subAccountDetail.getCashExCode());// 汇钞类型
		accountInfo.setOpendate(subAccountDetail.getCardOpenDate());// 开户日期
		accountInfo.setTradedate(subAccountDetail.getLastTransDate());// 最后交易日期
		accountInfo.setOpenbranchno(subAccountDetail.getAccountOpenBranch());// 开户网点编号
		accountInfo.setOpenbranchname(subAccountDetail.getAccountOpenBranch());// 开户网点名称
		accountInfo.setOpenbranchtel("");// 开户网点电话
		accountInfo.setOpenbranchaddr("");// 开户网点地址
		accountInfo.setAddr("");// 账户人联系地址
		accountInfo.setTel("");// 账户人联系电话
		accountInfo.setStatusflag(subAccountDetail.getAccountStatus());// 账户状态标识
		accountInfo.setStatus(subAccountDetail.getAccountStatus());// 账户状态
		accountInfo.setClosedate(subAccountDetail.getAccountClosingDate());// 销户日期
		accountInfo.setClosebranchno(subAccountDetail.getAccountClosingBranch());// 销户网点编号
		accountInfo.setClosebranchname(subAccountDetail.getAccountClosingBranch());// 销户网点名称
		accountInfo.setQuerytime("");// 主机查询时间
		
		return accountInfo;
	}
	
	private List<Gdjc_RequestCkdj_Acc> getRequestCkdj_AccinfoList(String cardNumber, Gdjc_RequestCkdj_Acc acc) throws DataOperateException, RemoteAccessException {
		List<Gdjc_RequestCkdj_Acc> accountInfoList = Lists.newArrayList();
		
		// 1、根据主账户查询子账户基本信息
		List<SubAccountInfo> subAccountInfos = messageService.querySubAccountInfoList(cardNumber);
		
		if (subAccountInfos == null || subAccountInfos.size() == 0) {
			return accountInfoList;
		}
		
		// 2、（循环）获取子账户详细信息
		for (SubAccountInfo subAccountInfo : subAccountInfos) {
			String accountNumber = subAccountInfo.getAccountNumber();//帐号
			String currency = subAccountInfo.getCurrency(); //币种
			String cashExCode = subAccountInfo.getCashExCode(); // 钞汇标志
			if (StringUtils.isBlank(accountNumber) || StringUtils.isBlank(currency)) {
				continue;
			}
			// 远程请求
			Gdjc_RequestCkdj_Acc accountInfo = getAccinfo(accountNumber, currency, cashExCode);
			
			accountInfo.setAccname(acc.getAccname());// 户名
			accountInfo.setCardno(cardNumber);// 卡号
			
			accountInfoList.add(accountInfo);
		}
		
		return accountInfoList;
	}
	
	@Override
	public Gdjc_RequestLsdj_Acc getAccountTransaction(Br50_cxqq_mx br50_cxqq_mx) throws DataOperateException, RemoteAccessException {
		Gdjc_RequestLsdj_Acc acc = new Gdjc_RequestLsdj_Acc();
		try {
			String startdate = StringUtils.trimToEmpty(br50_cxqq_mx.getStartdate());
			String enddate = StringUtils.trimToEmpty(br50_cxqq_mx.getEnddate());
			if (enddate.isEmpty()) { //如果结束日期为空，取当前日期
				enddate = DtUtils.getNowDate();
			}
			if (startdate.isEmpty()) { //如果查询起始日期为空，取结束日期前一年的日期
				startdate = DtUtils.add(enddate, DtUtils.YEAR, -1, false);
			}
			if (StringUtils.isEmpty(br50_cxqq_mx.getName()) || StringUtils.isEmpty(br50_cxqq_mx.getType())) {
				AccountDetail primaryAccountDetail = messageService.queryAccountDetail(br50_cxqq_mx.getAccount(), null, null);
				br50_cxqq_mx.setName(primaryAccountDetail.getCustomerName());
				if (StringUtils.equals(primaryAccountDetail.getCustomerType(), "1")) {//1:个人客户、2:对公客户、3:同业客户(对公)	
					br50_cxqq_mx.setType(GdjcConstants.TYPE_PERSON);
				} else {
					br50_cxqq_mx.setType(GdjcConstants.TYPE_UNIT);
				}
			}
			List<AccountTransaction> accountTransactions = messageService.queryAccountTransaction(br50_cxqq_mx.getAccount(), startdate, enddate);
			if (accountTransactions != null) {
				acc.setTranslist(getTransactionList(accountTransactions, br50_cxqq_mx));
			} else {
				acc = null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return acc;
	}
	
	/**
	 * 交易明细记录
	 * 
	 * @param accountTransactions
	 * @return
	 */
	public List<Gdjc_RequestLsdj_Trans> getTransactionList(List<AccountTransaction> accountTransactions, Br50_cxqq_mx br50_cxqq_mx) {
		List<Gdjc_RequestLsdj_Trans> transactionList = new ArrayList<Gdjc_RequestLsdj_Trans>();
		for (AccountTransaction transaction : accountTransactions) {
			// 时间处理
			String transactionDate = transaction.getTradeDate(); // 交易日
			String time = transaction.getTradeTime(); // 交易时间
			if (StringUtils.isBlank(transactionDate)) {
				transactionDate = "19700101";
			}
			if (StringUtils.isBlank(time)) {
				time = "000000";
			}
			try {
				String transactionTime = Utility.toDateTime19(transactionDate + time);
				transaction.setTradeTime(transactionTime);
			} catch (Exception e) {
				transaction.setTradeTime("1970-01-01 00:00:00");
			}
			Gdjc_RequestLsdj_Trans request_transaction = new Gdjc_RequestLsdj_Trans();
			// 处理交易流水号（广发银行核心接口没有单独的交易流水号，所以用"[交易会计日] + [日志号] + [日志顺序号]"作为流水号。）
			String transDate = transaction.getTradeDate();
			String logNumber = transaction.getLogNumber();
			String logNumberSeq = transaction.getLogSeq();
			if (StringUtils.isNotBlank(logNumber) && StringUtils.isNotBlank(logNumberSeq)) {
				if (transDate == null) {
					transDate = "";
				}
				logNumberSeq = StringUtils.leftPad(logNumberSeq, 4, '0');
				request_transaction.setTransnum(transDate + logNumber + logNumberSeq);// 交易流水号
			}
			request_transaction.setAccname(br50_cxqq_mx.getName());// 查询卡号客户的名称
			if (br50_cxqq_mx.getType().equals(GdjcConstants.TYPE_PERSON)) {
				request_transaction.setCardno(br50_cxqq_mx.getAccount());// 卡号
			}
			request_transaction.setAcctype("");// 账户类型
			request_transaction.setAccount(transaction.getAccountNumber());// 账号
			request_transaction.setSubaccount(transaction.getV_AccountNumber());// 子账户 V+钞汇标志+账号
			request_transaction.setExchangetype(transaction.getCashExCode());// 汇钞类型
			request_transaction.setTranstime(transaction.getTradeTime());// 交易时间
			if (transaction.getDrcrFlag().equals("D")) {// D - 借 、C - 贷
				request_transaction.setExpense(trimToZero(transaction.getTradeAmount()));// 支出
				request_transaction.setIncome("0.00");
				
			} else {
				request_transaction.setIncome(trimToZero(transaction.getTradeAmount()));// 存入
				request_transaction.setExpense("0.00");
			}
			request_transaction.setBanlance(trimToZero(transaction.getAccountBalance()));// 余额为空值时，传0.00
			request_transaction.setCurrency(transaction.getTradeCurrency());// 币种
			request_transaction.setTranstype(transaction.getTradeType());// 交易类型
			request_transaction.setTransaddr(transaction.getTradeBranch());// 交易地址 TODO
			request_transaction.setTransaddrno(transaction.getTradeBranch());// 交易网点编号或ATM
																				// 机编号
			request_transaction.setTranscountry("");// 交易国家或地区
			request_transaction.setTransremark(transaction.getRemark());// 交易备注
			request_transaction.setTranstel("");// 交易联系电话
			request_transaction.setTranschannel("");// 交易渠道
			request_transaction.setTranstelle(transaction.getTradeTeller());// 交易柜员号
			request_transaction.setTranstermip("");// 交易终端IP
			request_transaction.setMatchaccount(transaction.getRelativeNumber());// 对方账号
			request_transaction.setMatchaccname(transaction.getRelativeName());// 对方户名
			request_transaction.setMatchbankno(transaction.getRelativeBank());// 对方机构号
			request_transaction.setMatchbankname("");// 对方机构名
			transactionList.add(request_transaction);
		}
		return transactionList;
	}
	
	public Br50_cxqq_mx getIDType(Br50_cxqq_mx br50_cxqq_mx) {
		
		String qureymode = br50_cxqq_mx.getQuerymode();
		String idtype = br50_cxqq_mx.getIdtype();
		String id = br50_cxqq_mx.getId();
		if ("4".equals(qureymode)) {// 4：按组织机构代码查询
			id = br50_cxqq_mx.getOrgcode();
			idtype = "20600";
		} else if ("5".equals(qureymode)) {// 5：按工商营业执照编码查询
			id = br50_cxqq_mx.getBuslicense();
			idtype = "20500";
		} else if ("6".equals(qureymode)) {// 6：按三证合一编号查询
			id = br50_cxqq_mx.getThreeinone();
			idtype = "24100";
		}
		br50_cxqq_mx.setId(id);
		br50_cxqq_mx.setIdtype(idtype);
		return br50_cxqq_mx;
	}
	
	/**
	 * 金额处理
	 * 
	 * @param inStr
	 * @return
	 * @author yinxiong
	 * @date 2016年9月18日 下午9:32:30
	 */
	private String trimToZero(String inStr) {
		return StringUtils.isBlank(inStr) ? "0.00" : inStr;
	}
}
