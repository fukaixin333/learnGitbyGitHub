package com.citic.server.dx.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.citic.server.dx.domain.AccountsTransaction;
import com.citic.server.dx.domain.Br24_account_freeze;
import com.citic.server.dx.domain.Br24_account_holder;
import com.citic.server.dx.domain.Br24_account_info;
import com.citic.server.dx.domain.Br24_account_right;
import com.citic.server.dx.domain.Br24_card_info;
import com.citic.server.dx.domain.Br24_trans_info;
import com.citic.server.dx.domain.Br25_Freeze;
import com.citic.server.dx.domain.Br25_Freeze_back;
import com.citic.server.dx.domain.Br25_StopPay;
import com.citic.server.dx.domain.Br25_StopPay_back;
import com.citic.server.dx.domain.OrganKeyQuery;
import com.citic.server.dx.domain.PartyQueryResult;
import com.citic.server.inner.domain.AccountFrozenMeasure;
import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ContractAccount;
import com.citic.server.inner.domain.SubAccountInfo;
import com.citic.server.inner.domain.request.Input267500;
import com.citic.server.inner.domain.request.Input267510;
import com.citic.server.inner.domain.request.Input267540;
import com.citic.server.inner.domain.response.AccountDetail;
import com.citic.server.inner.domain.response.CorporateCustomer;
import com.citic.server.inner.domain.response.DeferFreezeResult;
import com.citic.server.inner.domain.response.FreezeResult;
import com.citic.server.inner.domain.response.IndividualCustomer;
import com.citic.server.inner.domain.response.UnfreezeResult;
import com.citic.server.inner.service.AnswerCodeService;
import com.citic.server.inner.service.IPrefixMessageService;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.BB13_organ_telno;
import com.google.common.collect.Lists;

/**
 * @author Liu Xuanfei
 * @date 2016年4月18日 下午7:31:13
 */
@Service("remoteDataOperate2")
public class RemoteDataOperate2 extends AbstractDataOperate2 {
	// private final Logger logger = LoggerFactory.getLogger(RemoteDataOperate2.class);
	
	@Autowired
	@Qualifier("innerMessageService")
	private IPrefixMessageService messageService;
	
	@Autowired
	@Qualifier("answerCodeService")
	private AnswerCodeService exceptionService;
	
	@Override
	public Br24_account_holder getBr24_account_holder(String subjectType, String accountNumber) throws DataOperateException, RemoteAccessException {
		try {
			Br24_account_holder br24AccountHolder = new Br24_account_holder();
			
			if (StringUtils.equals("1", subjectType)) {// 对私
				IndividualCustomer info = messageService.queryIndividualCustomerInfo(accountNumber);
				if (info.getOpenIDInfo() != null) {
					br24AccountHolder.setAccountCredentialType(info.getOpenIDInfo().getIdType()); // 证件类型
					br24AccountHolder.setAccountCredentialNumber(info.getOpenIDInfo().getIdNumber()); // 证件号码
				}
				br24AccountHolder.setAccountSubjectName(info.getChineseName()); // 主体名称
				br24AccountHolder.setTelNumber(info.getTelephoneNumber()); // 联系手机
				br24AccountHolder.setCardOperatorName(""); // 代办人姓名
				br24AccountHolder.setCardOperatorCredentialType(""); // 代办人证件类型
				br24AccountHolder.setCardOperatorCredentialNumber(""); // 代办人证件号码
				br24AccountHolder.setResidentAddress(info.getPermanentAddress()); // 住宅地址
				br24AccountHolder.setResidentTelNumber(info.getMobilePhoneNumber()); // 住宅电话
				br24AccountHolder.setWorkCompanyName(""); // 工作单位
				br24AccountHolder.setWorkAddress(info.getMailingAddress()); // 单位地址
				br24AccountHolder.setEmailAddress(info.getEmailAddress()); // 邮箱地址
			} else {
				CorporateCustomer info = messageService.queryCorporateCustomerInfo(accountNumber);
				br24AccountHolder.setAccountCredentialType(info.getOpenIDType()); // 证件类型
				br24AccountHolder.setAccountCredentialNumber(info.getOpenIDNumber()); // 证件号码
				br24AccountHolder.setAccountSubjectName(info.getChineseName()); // 主体名称
				br24AccountHolder.setTelNumber(info.getTelephoneNumber()); // 联系手机
				br24AccountHolder.setWorkAddress(info.getMailingAddress()); // 单位地址
				br24AccountHolder.setWorkTelNumber(info.getFixedLineNumber()); // 单位电话
				br24AccountHolder.setEmailAddress(info.getEmailAddress()); // 电子邮箱
				if (info.getLegalInfo() != null) {
					br24AccountHolder.setArtificialPersonRep(info.getLegalInfo().getName()); // 法人代表姓名
					br24AccountHolder.setArtificialPersonRepCredentialType(info.getLegalInfo().getIdType()); // 法人代表证件类型
					br24AccountHolder.setArtificialPersonRepCredentialNumber(info.getLegalInfo().getIdNumber());
				}
				br24AccountHolder.setBusinessLicenseNumber(info.getBusiIDNumber()); // 工商登记号
				br24AccountHolder.setStateTaxSerial(info.getStateTaxIDNumber()); // 国税纳税号
				br24AccountHolder.setLocalTaxSerial(info.getLocalTaxIDNumber()); // 地税纳税号
			}
			
			return br24AccountHolder;
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
	}
	
	@Override
	public List<Br24_account_info> getBr24_account_infoList(String cardNumber, String subjectType) throws DataOperateException, RemoteAccessException {
		List<Br24_account_info> accountInfoList = new ArrayList<Br24_account_info>();
		
		if ("1".equals(subjectType)) {
			try {
				// 1、根据主账户查询子账户基本信息
				List<SubAccountInfo> subAccountInfos = messageService.querySubAccountInfoList(cardNumber);
				
				if (subAccountInfos == null || subAccountInfos.size() == 0) {
					return accountInfoList;
				}
				
				// 2、（循环）获取子账户详细信息
				for (SubAccountInfo subAccountInfo : subAccountInfos) {
					String accountNumber = subAccountInfo.getAccountNumber(); // 子账号
					String currency = subAccountInfo.getCurrency(); // 币种
					String cashExCode = subAccountInfo.getCashExCode(); // 钞汇标志
					if (StringUtils.isBlank(accountNumber) || StringUtils.isBlank(currency)) {
						continue; //
					}
					// 远程请求：获取子账户详细信息
					AccountDetail subAccountDetail = messageService.queryAccountDetail(accountNumber, currency, cashExCode);
					// DTO赋值转换
					Br24_account_info br24AccountInfo = new Br24_account_info();
					br24AccountInfo.setAccountNumber(subAccountDetail.getV_AccountNumber()); // 组合子账号
					br24AccountInfo.setAccountSerial(subAccountInfo.getAccountSerial()); // 子账户序号
					br24AccountInfo.setAccountStatus(subAccountDetail.getAccountStatus()); // 账户状态
					br24AccountInfo.setAccountType(subAccountDetail.getAccountAttr()); // 账户类别
					br24AccountInfo.setCurrency(subAccountDetail.getCurrency()); // 币种
					br24AccountInfo.setCashRemit(subAccountDetail.getCashExCode()); // 钞汇标志
					br24AccountInfo.setAccountBalance(subAccountDetail.getAccountBalance()); // 账户余额
					br24AccountInfo.setAvailableBalance(subAccountDetail.getAvailableBalance()); // 可用余额
					br24AccountInfo.setDepositBankBranchCode(subAccountDetail.getCardOpenBranch()); // 开户网点
					br24AccountInfo.setLastTransactionTime(subAccountDetail.getLastTransDate()); // 最后交易时间
					// 设置卡号（核心反馈的子账户详细信息不包含卡号）
					br24AccountInfo.setCardNumber(cardNumber);
					
					accountInfoList.add(br24AccountInfo);
				}
			} catch (DataOperateException e) {
				throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
			}
		}
		
		return accountInfoList;
	}
	
	@Override
	public Br24_card_info getBr24_card_info(String cardNumber, String SubjectType) throws DataOperateException, RemoteAccessException {
		Br24_card_info br24CardInfo = new Br24_card_info();
		try {
			AccountDetail account = messageService.queryAccountDetail(cardNumber, null, null);
			br24CardInfo.setAccountName(account.getAccountCnName()); // 账户名称
			br24CardInfo.setCardNumber(account.getAccountNumber()); // 账号
			br24CardInfo.setDepositBankBranchCode(account.getAccountOpenBranch()); // 开户网点
			br24CardInfo.setDepositBankBranch(account.getAccountOpenBranch()); // 开户网点
			br24CardInfo.setAccountOpenTime(account.getCardOpenDate()); // 开户日期
			br24CardInfo.setAccountCancellationTime(account.getAccountClosingDate()); // 销户日期
			br24CardInfo.setAccountCancellationBranch(account.getAccountClosingBranch()); // 销户网点
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return br24CardInfo;
	}
	
	@Override
	public Br25_Freeze_back frozenAccount(Br25_Freeze br25Freeze) throws DataOperateException, RemoteAccessException {
		Br25_Freeze_back result = new Br25_Freeze_back();
		try {
			Input267500 in = new Input267500();
			// 调整输入项
			in.setTransSerialNumber(null);
			in.setAccountNumber(br25Freeze.getAccountNumber()); // 账号
			in.setFreezeInsType("1"); // 发起机构类型
			in.setCurrency(br25Freeze.getCurrency()); // 货币
			// in.setCashExCode(""); // 钞汇标识
			in.setFreezeAmount(br25Freeze.getBalance()); // 应冻结金额
			in.setFreezeBookNumber(br25Freeze.getCaseNumber()); // 案件编号作为文书编号
			in.setFreezeInsName(br25Freeze.getApplicationOrgName()); // 冻结机构名称
			in.setFreezeReason(br25Freeze.getReason()); // 冻结原因
			in.setEffectiveDate(br25Freeze.getFreezeStartTime()); // 生效日期
			in.setExpiringDate(br25Freeze.getExpireTime()); // 到期日期
			in.setRemark(br25Freeze.getRemark()); // 备注
			in.setLawName1(br25Freeze.getOperatorName()); // 执法人名称1
			in.setLawIDNumber1(br25Freeze.getOperatorIDNumber()); // 执法人证件号1
			in.setLawName2(br25Freeze.getInvestigatorName()); // 执法人名称2
			in.setLawIDNumber2(br25Freeze.getInvestigatorIDNumber()); // 执法人证件号2
			
			// 冻结方式
			String freezeType = br25Freeze.getFreezeType();
			freezeType = freezeType == null ? "" : freezeType.trim();
			if ("01".equals(freezeType)) {
				in.setFreezeType("2");
			} else if ("02".equals(freezeType)) {
				in.setFreezeType("1");
			} else {
				in.setFreezeType(freezeType);
			}
			
			// 远程请求
			FreezeResult out = messageService.freezeAccount(in);
			// 调整输出项
			result.setHxappid(out.getFrozenNumber()); // 冻结编号
			result.setAccountNumber(out.getAccountNumber());
			result.setAppliedBalance(out.getFreezeAmount());
			result.setFrozedBalance(out.getFrozenAmount());
			result.setCurrency(out.getCurrency());
			result.setAccountBalance(out.getAccountBalance());
			result.setStartTime(out.getEffectiveDate());
			result.setAccountAvaiableBalance(out.getAvailableAmount());
			result.setEndTime(out.getExpiringDate());
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return result;
	}
	
	@Override
	public Br25_Freeze_back frozenAccount_JC(Br25_Freeze br25Freeze) throws DataOperateException, RemoteAccessException {
		Br25_Freeze_back result = new Br25_Freeze_back();
		try {
			Input267510 in = new Input267510();
			// 调整输入项
			in.setTransSerialNumber(null);
			in.setFrozenNumber(br25Freeze.getHxappid()); // 冻结编号
			in.setUnfreezeType("1"); // 解冻方式（1-全部解冻；2-部分解冻）
			in.setUnfreezeBookNumber(br25Freeze.getCaseNumber()); // 案件编号作为文书编号
			in.setUnfreezeInsName(br25Freeze.getApplicationOrgName()); // 冻结机构名称
			in.setUnfreezeReason(br25Freeze.getWithdrawalRemark()); // 变动原因
			in.setLawName1(br25Freeze.getOperatorName()); // 执法人名称1
			in.setLawIDNumber1(br25Freeze.getOperatorIDNumber()); // 执法人证件号1
			in.setLawName2(br25Freeze.getInvestigatorName()); // 执法人名称2
			in.setLawIDNumber2(br25Freeze.getInvestigatorIDNumber()); // 执法人证件号2
			if(StringUtils.isBlank(br25Freeze.getHxappid())){
				throw new DataOperateException("无原冻结编号,请检查冻结请求结果是否成功");
			}
			// 远程请求
			UnfreezeResult out = messageService.unfreezeAccount(in);
			result.setAccountNumber(out.getAccountNumber());
			result.setAppliedBalance(out.getFrozenAmount());
			result.setUnfreezeBalance(out.getUnfreezeAmount());
			result.setAccountBalance(out.getAccountBalance());
			result.setStartTime(out.getFrozenEffDate());
			result.setEndTime(out.getFrozenExpDate());
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return result;
	}
	
	@Override
	public Br25_Freeze_back frozenAccount_YQ(Br25_Freeze br25Freeze) throws DataOperateException, RemoteAccessException {
		Br25_Freeze_back result = new Br25_Freeze_back();
		try {
			Input267540 in = new Input267540();
			// 调整输入项
			in.setTransSerialNumber(null);
			in.setFrozenNumber(br25Freeze.getHxappid()); // 冻结编号
			in.setFreezeBookNumber(br25Freeze.getCaseNumber()); // 案件编号作为文书编号
			in.setFreezeInsName(br25Freeze.getApplicationOrgName());// 冻结机构名称
			in.setExpiringDate(br25Freeze.getExpireTime()); // 新到期日期
			in.setFreezeReason(br25Freeze.getReason());// 变动原因
			in.setRemark(br25Freeze.getRemark());// 备注
			in.setLawName1(br25Freeze.getOperatorName());// 执法人名称1
			in.setLawIDNumber1(br25Freeze.getOperatorIDNumber());// 执法人证件号1
			in.setLawName2(br25Freeze.getInvestigatorName());// 执法人名称2
			in.setLawIDNumber2(br25Freeze.getInvestigatorIDNumber()); // 执法人证件号2
			if(StringUtils.isBlank(br25Freeze.getHxappid())){
				throw new DataOperateException("无原冻结编号,请检查冻结请求结果是否成功");
			}
			// 远程请求
			DeferFreezeResult out = messageService.deferFreezeAccount(in);
			result.setAccountNumber(out.getAccountNumber());
			result.setAppliedBalance(out.getFrozenAmount());
			result.setFrozedBalance(out.getFrozenAmount());
			result.setCurrency(out.getCurrency());
			result.setAccountBalance(out.getAccountBalance());
			result.setStartTime(out.getEffectiveDate());
			result.setEndTime(out.getNewExpiringDate());
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return result;
	}
	
	@Override
	public Br25_StopPay_back StopAccount(Br25_StopPay br25StopPay) throws DataOperateException, RemoteAccessException {
		Br25_StopPay_back result = new Br25_StopPay_back();
		try {
			Input267500 in = new Input267500();
			// 调整输入项
			in.setTransSerialNumber(null);
			in.setFreezeType("1"); // 冻结方式（1-账户冻结） 
			in.setAccountNumber(br25StopPay.getAccountNumber()); // 账号
			in.setFreezeInsType("1"); // 发起机构类型
			in.setCurrency(br25StopPay.getCurrency()); // 货币
			in.setFreezeBookNumber(br25StopPay.getCaseNumber()); // 案件编号作为文书编号
			in.setFreezeInsName(br25StopPay.getApplicationOrgName()); // 冻结机构名称
			in.setFreezeReason(br25StopPay.getReason()); // 冻结原因
			in.setEffectiveDate(br25StopPay.getStartTime()); // 生效日期
			in.setExpiringDate(br25StopPay.getExpireTime()); // 到期日期
			in.setRemark(br25StopPay.getRemark()); // 备注
			in.setLawName1(br25StopPay.getOperatorName()); // 执法人名称1
			in.setLawIDNumber1(br25StopPay.getOperatorIDNumber()); // 执法人证件号1
			in.setLawName2(br25StopPay.getInvestigatorName()); // 执法人名称2
			in.setLawIDNumber2(br25StopPay.getInvestigatorIDNumber()); // 执法人证件号2
			if(StringUtils.isBlank(br25StopPay.getHxappid())){
				throw new DataOperateException("无原冻结编号,请检查冻结请求结果是否成功");
			}
			// 远程请求
			FreezeResult out = messageService.freezeAccount(in); // 以冻结方式处理止付请求
			// 调整输出项
			result.setHxappid(out.getFrozenNumber()); // 冻结编号
			result.setAccountNumber(out.getAccountNumber());
			result.setCurrency(out.getCurrency());
			result.setAccountBalance(out.getAccountBalance());
			result.setStartTime(out.getEffectiveDate());
			result.setEndTime(out.getExpiringDate());
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return result;
	}
	
	@Override
	public Br25_StopPay_back StopAccount_JC(Br25_StopPay br25StopPay) throws DataOperateException, RemoteAccessException {
		Br25_StopPay_back result = new Br25_StopPay_back();
		try {
			Input267510 in = new Input267510();
			// 调整输入项
			in.setTransSerialNumber(null);
			in.setFrozenNumber(br25StopPay.getHxappid()); // 冻结编号
			in.setUnfreezeType("1"); // 解冻方式（1-全部解冻；2-部分解冻）
			in.setUnfreezeBookNumber(br25StopPay.getCaseNumber()); // 案件编号作为文书编号
			in.setUnfreezeInsName(br25StopPay.getApplicationOrgName()); // 冻结机构名称
			in.setUnfreezeReason(br25StopPay.getWithdrawalRemark()); // 变动原因
			in.setLawName1(br25StopPay.getOperatorName()); // 执法人名称1
			in.setLawIDNumber1(br25StopPay.getOperatorIDNumber()); // 执法人证件号1
			in.setLawName2(br25StopPay.getInvestigatorName()); // 执法人名称2
			in.setLawIDNumber2(br25StopPay.getInvestigatorIDNumber()); // 执法人证件号2
			if(StringUtils.isBlank(br25StopPay.getHxappid())){
				throw new DataOperateException("无原冻结编号,请检查冻结请求结果是否成功");
			}
			// 远程请求
			UnfreezeResult out = messageService.unfreezeAccount(in);
			result.setAccountNumber(out.getAccountNumber());
			result.setAccountBalance(out.getAccountBalance());
			result.setStartTime(out.getFrozenEffDate());
			result.setEndTime(out.getFrozenExpDate());
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return result;
	}
	
	@Override
	public Br25_StopPay_back StopAccount_YQ(Br25_StopPay br25StopPay) throws DataOperateException, RemoteAccessException {
		Br25_StopPay_back result = new Br25_StopPay_back();
		try {
			Input267540 in = new Input267540();
			// 调整输入项
			in.setTransSerialNumber(null);
			in.setFrozenNumber(br25StopPay.getHxappid()); // 冻结编号
			in.setFreezeBookNumber(br25StopPay.getCaseNumber()); // 案件编号作为文书编号
			in.setFreezeInsName(br25StopPay.getApplicationOrgName());// 冻结机构名称
			in.setExpiringDate(br25StopPay.getExpireTime()); // 新到期日期
			in.setFreezeReason(br25StopPay.getReason());// 变动原因
			in.setRemark(br25StopPay.getRemark());// 备注
			in.setLawName1(br25StopPay.getOperatorName());// 执法人名称1
			in.setLawIDNumber1(br25StopPay.getOperatorIDNumber());// 执法人证件号1
			in.setLawName2(br25StopPay.getInvestigatorName());// 执法人名称2
			in.setLawIDNumber2(br25StopPay.getInvestigatorIDNumber()); // 执法人证件号2
			if(StringUtils.isBlank(br25StopPay.getHxappid())){
				throw new DataOperateException("无原冻结编号,请检查冻结请求结果是否成功");
			}
			// 远程请求
			DeferFreezeResult out = messageService.deferFreezeAccount(in);
			result.setAccountNumber(out.getAccountNumber());
			result.setCurrency(out.getCurrency());
			result.setAccountBalance(out.getAccountBalance());
			result.setStartTime(out.getEffectiveDate());
			result.setEndTime(out.getNewExpiringDate());
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return result;
	}
	
	// ==========================================================================================
	//                     机构相关的接口函数
	// ==========================================================================================
	
	@Override
	public OrganKeyQuery getOrgkeyByCard(String subjectType, String cardNumber, String accountName) throws DataOperateException, RemoteAccessException {
		return null;
	}
	
	@Override
	public OrganKeyQuery getOrgkeyByCredentialNumber(String credentialtype, String credentialNumber, String accountName, String... args) throws DataOperateException,
		RemoteAccessException {
		return null;
	}
	
	@Override
	public OrganKeyQuery getOrgkeyByAcctNumber(String accountNumber, String accountName) throws DataOperateException, RemoteAccessException {
		return null;
	}
	
	// ==========================================================================================
	//                     实现AbstractDataOperate2抽象方法
	// ==========================================================================================
	
	@Override
	public List<Br24_trans_info> getAccountTransaction(String cardNumber, String inquiryPeriodStart, String inquiryPeriodEnd) throws DataOperateException, RemoteAccessException {
		List<Br24_trans_info> br24TransInfoList = Lists.newArrayList();
		
		try {
			List<AccountTransaction> accountTransactionList = messageService.queryAccountTransaction(cardNumber, inquiryPeriodStart, inquiryPeriodEnd);
			if (accountTransactionList == null || accountTransactionList.size() == 0) {
				return br24TransInfoList;
			}
			
			for (AccountTransaction transaction : accountTransactionList) {
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
				// 处理交易流水号（广发银行核心接口没有单独的交易流水号，所以用"[交易会计日] + [日志号] + [日志顺序号]"作为流水号。）
				String transactionSerial = null;
				String accountingDate = transaction.getAccountingDate(); // 会计日
				String logNumber = transaction.getLogNumber();
				String logNumberSeq = transaction.getLogSeq(); // 日志顺序号
				if (StringUtils.isNotBlank(logNumber) && StringUtils.isNotBlank(logNumberSeq)) {
					logNumber = StringUtils.leftPad(logNumber, 8, '0');
					logNumberSeq = StringUtils.leftPad(logNumberSeq, 4, '0');
					transactionSerial = accountingDate + logNumber + logNumberSeq;
				}
				// 处理现金标志（广发银行核心接口没有单独的现金标志，以现钞标志决定现金标志）
				String cashMark = transaction.getCashExCode();
				if ("1".equals(cashMark)) { // 1-钞
					cashMark = "01";
				} else { // 2-汇 或 其它
					cashMark = "00";
				}
				// DTO赋值转换
				Br24_trans_info br24TransInfo = new Br24_trans_info();
				br24TransInfo.setCardNumber(cardNumber);
				br24TransInfo.setAccountNumber(transaction.getV_AccountNumber()); // 组合子账号
				br24TransInfo.setTransactionType(transaction.getTradeType()); // 交易类型
				br24TransInfo.setBorrowingSigns(transaction.getDrcrFlag()); // 借贷标志
				br24TransInfo.setCurrency(transaction.getTradeCurrency()); // 交易币种
				br24TransInfo.setTransactionAmount(transaction.getTradeAmount()); // 交易金额
				br24TransInfo.setAccountBalance(transaction.getAccountBalance()); // 账户余额
				br24TransInfo.setTransactionTime(transaction.getTradeTime()); // 交易时间
				br24TransInfo.setTransactionSerial(transactionSerial); // 交易流水号
				br24TransInfo.setOpponentName(transaction.getRelativeName()); // 交易对方名称
				br24TransInfo.setOpponentAccountNumber(transaction.getRelativeNumber()); // 交易对方账号
				br24TransInfo.setOpponentCredentialNumber(""); // 交易对方证件号码
				br24TransInfo.setOpponentDepositBankID(transaction.getRelativeBank()); // 交易对方行所
				br24TransInfo.setTransactionRemark(transaction.getAppCode()); // 摘要（交易应用标识）
				br24TransInfo.setLogNumber(logNumber + logNumberSeq); // 日志号
				br24TransInfo.setSummonsNumber(transaction.getVoucherNo()); // 传票号
				br24TransInfo.setVoucherType(transaction.getVoucherCode()); // 凭证种类
				br24TransInfo.setVoucherCode(transaction.getVoucherNumber()); // 凭证号
				br24TransInfo.setCashMark(cashMark); // 现金标志（钞汇标志）
				br24TransInfo.setTerminalNumber(transaction.getTradeBranch()); // 终端号（交易网点）
				br24TransInfo.setTransactionAddress(""); // 交易发生地
				br24TransInfo.setTellerCode(transaction.getTradeTeller()); // 交易柜员号
				br24TransInfo.setRemark(transaction.getRemark()); // 备注
				// 广发银行只记录成功交易流水，无失败交易流水。
				br24TransInfo.setTransactionStatus("00"); // 00-成功
				
				br24TransInfoList.add(br24TransInfo);
			}
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return br24TransInfoList;
	}
	
	@Override
	public List<Br24_card_info> getPrimaryAccountDetails(String subjectType, String accountCredentialType, String acountCredentialNumber, String accountSubjectName, String... args)
		throws DataOperateException, RemoteAccessException {
		List<Br24_card_info> cardInfoList = new ArrayList<Br24_card_info>();
		
		try {
			// 1、根据证件信息查询合约账户基本信息
			List<ContractAccount> contractAccounts = messageService.queryContractAccountList(accountCredentialType, acountCredentialNumber, accountSubjectName);
			if (contractAccounts == null || contractAccounts.size() == 0) {
				return cardInfoList;
			}
			// 2、（循环）获取主账户详细信息
			for (ContractAccount account : contractAccounts) {
				String contractType = account.getContractType();
				// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
				if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
					AccountDetail accountDetail = messageService.queryAccountDetail(account.getAccountNumber(), account.getCurrency(), null);
					
					Br24_card_info br24CardInfo = new Br24_card_info();
					br24CardInfo.setAccountName(accountDetail.getAccountCnName()); // 账户名称
					br24CardInfo.setCardNumber(accountDetail.getAccountNumber()); // 账号
					br24CardInfo.setDepositBankBranchCode(accountDetail.getAccountOpenBranch()); // 开户网点
					br24CardInfo.setDepositBankBranch(accountDetail.getAccountOpenBranch()); // 开户网点
					br24CardInfo.setAccountOpenTime(accountDetail.getCardOpenDate()); // 开户日期
					br24CardInfo.setAccountCancellationTime(accountDetail.getAccountClosingDate()); // 销户日期
					br24CardInfo.setAccountCancellationBranch(accountDetail.getAccountClosingBranch()); // 销户网点
					
					cardInfoList.add(br24CardInfo);
				}
			}
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return cardInfoList;
	}
	
	@Override
	public List<Br24_account_freeze> getFreezeMeasures(String cardNumber) throws DataOperateException, RemoteAccessException {
		List<Br24_account_freeze> accountFreezeList = new ArrayList<Br24_account_freeze>();
		
		try {
			List<AccountFrozenMeasure> freezeMeasureList = messageService.queryAccountFrozenMeasures(cardNumber);
			if (freezeMeasureList == null || freezeMeasureList.size() == 0) {
				return accountFreezeList;
			}
			
			for (AccountFrozenMeasure freezeMeasure : freezeMeasureList) {
				if ("N".equals(freezeMeasure.getFrozenStatus())) { // N-冻结类数据
					Br24_account_freeze accountFreeze = new Br24_account_freeze();
					
					accountFreeze.setCardNumber(cardNumber);
					accountFreeze.setFreezeSerial(freezeMeasure.getWaitingSeq());
					accountFreeze.setAccountNumber(freezeMeasure.getAccountNumber());
					accountFreeze.setFreezeStartTime(freezeMeasure.getEffectiveDate());
					accountFreeze.setFreezeEndTime(freezeMeasure.getExpiringDate());
					accountFreeze.setFreezeDeptName(freezeMeasure.getFrozenInsName());
					accountFreeze.setCurrency(freezeMeasure.getCurrency());
					accountFreeze.setFreezeBalance(freezeMeasure.getFrozenAmount());
					accountFreeze.setRemark(freezeMeasure.getRemark());
					accountFreeze.setFreezeType("9999"); // 9999-其他权利机构
					
					accountFreezeList.add(accountFreeze);
				}
			}
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_DX); // !important
		}
		
		return accountFreezeList;
	}
	
	@Override
	public List<Br24_account_right> getRightsPriorities(String cardNumber) throws DataOperateException, RemoteAccessException {
		return null;
	}
	
	// ==========================================================================================
	//                     重写AbstractDataOperate2默认方法
	// ==========================================================================================
	
	@Override
	public AccountsTransaction getAccountsTransactionByCardNumber(String cardNumber, String subjectType, String inquiryPeriodStart, String inquiryPeriodEnd)
		throws DataOperateException, RemoteAccessException {
		List<Br24_account_info> accountInfoList = null;
		List<Br24_trans_info> transInfoList = null;
		if ("1".equals(subjectType)) { // 自然人主体（对私）
			// 1、根据主账户查询子账户详细信息
			accountInfoList = new ArrayList<Br24_account_info>();
			accountInfoList = getBr24_account_infoList(cardNumber, subjectType);
			if (accountInfoList == null) {
				return null; // 对私客户没有查询到子账户，应该也没有交易流水，所以这里直接return，避免多一次远程调用去查询交易流水。
			}
			// 2、广发银行核心系统接口支持通过卡/折号（主账户）查询出定期、活期的交易流水，所以不需要通过循环子账户查询流水。
			transInfoList = getAccountTransaction(cardNumber, inquiryPeriodStart, inquiryPeriodEnd);
		} else if ("2".equals(subjectType)) { // 法人主体（对公）
			transInfoList = getAccountTransaction(cardNumber, inquiryPeriodStart, inquiryPeriodEnd);
		}
		
		return new AccountsTransaction(accountInfoList, transInfoList);
	}
	
	@Override
	public PartyQueryResult getPartyQuertResultList(String subjectType, String inquiryMode, String accountCredentialType, String acountCredentialNumber, String accountSubjectName, String... args)
		throws DataOperateException, RemoteAccessException {
		return super.getPartyQuertResultList(subjectType, inquiryMode, accountCredentialType, acountCredentialNumber, accountSubjectName);
	}
	
	// ==========================================================================================
	//                     其它函数/方法
	// ==========================================================================================
	
	@Override
	public void SendMsg(List<BB13_organ_telno> bb13_organ_telnoList, String... arg) throws Exception {
	}
}
