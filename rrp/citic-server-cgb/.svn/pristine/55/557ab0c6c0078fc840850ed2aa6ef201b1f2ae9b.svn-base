package com.citic.server.jsga.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.inner.domain.AccountFrozenMeasure;
import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ContractAccount;
import com.citic.server.inner.domain.SubAccountInfo;
import com.citic.server.inner.domain.request.Input267500;
import com.citic.server.inner.domain.request.Input267510;
import com.citic.server.inner.domain.request.Input267540;
import com.citic.server.inner.domain.request.Input998070;
import com.citic.server.inner.domain.response.AccountDetail;
import com.citic.server.inner.domain.response.CorporateCustomer;
import com.citic.server.inner.domain.response.DeferFreezeResult;
import com.citic.server.inner.domain.response.FreezeResult;
import com.citic.server.inner.domain.response.IndividualCustomer;
import com.citic.server.inner.domain.response.UnfreezeResult;
import com.citic.server.inner.domain.response.V_AccountTransaction;
import com.citic.server.inner.service.IPrefixMessageService;
import com.citic.server.jsga.domain.Br40_cxqq_mx;
import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.jsga.domain.request.JSGA_ControlRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Account;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Customer;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Measure;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_SubAccount;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Transaction;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Recored;
import com.citic.server.jsga.domain.response.JSGA_FreezeResponse_Account;
import com.citic.server.jsga.domain.response.JSGA_StopPaymentResponse_Account;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.CacheService;
import com.citic.server.utils.DtUtils;

/**
 * CBRC涉及监管发文的数据加工服务
 * 
 * @author Liu Xuanfei
 * @date 2016年7月7日 下午8:13:30
 */
@Service("remoteDataOperate12")
public class RemoteDataOperate12 implements IDataOperate12 {
	
	@Autowired
	@Qualifier("innerMessageService")
	private IPrefixMessageService messageService;
	
	protected CacheService cacheService;
	
	@Override
	public JSGA_QueryRequest_Customer getAccountDetailAndTransaction(Br40_cxqq_mx cxqq) throws DataOperateException, RemoteAccessException {
		JSGA_QueryRequest_Customer customer = null; // 客户信息
		
		// 查询方式 - 用于判断根据账（卡）号或证照查询
		String queryType = null;
		
		String cxlx = cxqq.getCxlx();
		if ("01".equals(cxlx)) { // 01 - 根据证照查询
			queryType = "01"; // 默认指定为根据三证查询
		}
		
		String subjectType = cxqq.getZtlb(); // 主体类别
		String cxnr = cxqq.getCxnr(); // 查询内容
		
		// 检查必需数据项的有效性
		String mxsdlx = cxqq.getMxsdlx(); // 明细时段类型
		String startDate = cxqq.getMxqssj(); // 明细起始时间
		String endDate = cxqq.getMxjzsj(); // 明细截至时间
		if (CBRCConstants.CXNR_ACC_AND_TRANS.equals(cxnr)) {
			try {
				if ("01".equals(mxsdlx)) {
					startDate = DtUtils.add(DtUtils.getNowDate(), DtUtils.YEAR, -1, false);
					endDate = DtUtils.getNowDate();
				} else if ("02".equals(mxsdlx)) {
					startDate = DtUtils.getBeginDt(DtUtils.getNowDate(), DtUtils.YEAR);
					endDate = DtUtils.getEndDt(DtUtils.getNowDate(), DtUtils.YEAR);
				} else if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
					throw new DataOperateException(CBRCConstants.REC_CODE_99999, "缺少自定义时间段");
				}
			} catch (Exception e) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "暂不支持该明细时间段");
			}
		}
		
		List<String> accountNumbers = new ArrayList<String>();
		List<JSGA_QueryRequest_Account> accountList = new ArrayList<JSGA_QueryRequest_Account>();
		// 根据账（卡）号查询
		if (queryType == null || queryType.length() == 0) {
			String primaryNumber = cxqq.getCxzh(); // 查询账卡号
			if (StringUtils.isBlank(primaryNumber)) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "缺少查询账卡号");
			}
			
			// 查询客户信息
			customer = queryCustomerByAccountNumber(subjectType, primaryNumber);
			if (customer == null) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "查无客户信息");
			}
			
			// 查询账（卡）号详细信息
			AccountDetail accountDetail = messageService.queryAccountDetail(primaryNumber, null, null);
			if (accountDetail == null) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "查无账户信息");
			}
			accountList.add(copyAccountProperties(accountDetail));
			accountNumbers.add(primaryNumber);
		}
		// 根据证件类型、证件号码、主体名称信息查询
		else {
			String credentialType = cxqq.getZzlx(); // 证件类型
			String credentialNumber = cxqq.getZzhm(); // 证件号码
			String subjectName = cxqq.getZtmc(); // 主体名称
			
			// 检查证照信息是否齐全（比如查询方式为02、03时，即使查询方式是01，也需要做如此判断）
			if (StringUtils.isBlank(credentialType) || StringUtils.isBlank(credentialNumber) || StringUtils.isBlank(subjectName)) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "暂不支持证照信息不全的查询方式");
			}
			
			// 查询客户信息
			customer = queryCustomerByCredentialInfo(subjectType, credentialType, credentialNumber, subjectName);
			if (customer == null) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "查无客户信息");
			}
			
			// 根据证件信息查询合约账户基本信息
			List<ContractAccount> contractAccounts = messageService.queryContractAccountList(credentialType, credentialNumber, subjectName);
			if (contractAccounts == null || contractAccounts.size() == 0) {
			} else {
				for (ContractAccount account : contractAccounts) {
					String contractType = account.getContractType();
					// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
					if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
						// 查询账（卡）号详细信息
						AccountDetail accountDetail = messageService.queryAccountDetail(account.getAccountNumber(), account.getCurrency(), null);
						if (accountDetail == null) {
							continue;
						}
						accountList.add(copyAccountProperties(accountDetail));
						accountNumbers.add(account.getAccountNumber());
					}
				}
			}
		}
		
		List<JSGA_QueryRequest_Measure> measureList = new ArrayList<JSGA_QueryRequest_Measure>();
		List<JSGA_QueryRequest_SubAccount> subAccountList = new ArrayList<JSGA_QueryRequest_SubAccount>();
		List<JSGA_QueryRequest_Transaction> transactionList = new ArrayList<JSGA_QueryRequest_Transaction>();
		for (String accountNumber : accountNumbers) {
			// 查询强制措施信息
			List<AccountFrozenMeasure> freezeMeasures = messageService.queryAccountFrozenMeasures(accountNumber);
			if (freezeMeasures == null || freezeMeasures.size() == 0) {
			} else {
				measureList.addAll(copyFreezeMeasureProperties(freezeMeasures));
			}
			
			// 查询子账户信息（个人）
			if ("01".equals(subjectType)) {
				List<SubAccountInfo> subAccountInfos = messageService.querySubAccountInfoList(accountNumber);
				if (subAccountInfos == null || subAccountInfos.size() == 0) {
				} else {
					// 根据子账户基本信息（子账户账号、币种）获取子账户详细信息
					for (SubAccountInfo subAccount : subAccountInfos) {
						String subAccountNumber = subAccount.getAccountNumber(); // 账号
						String currency = subAccount.getCurrency(); // 币种
						String cashExCode = subAccount.getCashExCode(); // 钞汇标志
						if ((subAccountNumber == null || subAccountNumber.length() == 0) || (currency == null || currency.length() == 0)) {
							continue;
						}
						AccountDetail subAccountDetail = messageService.queryAccountDetail(subAccountNumber, currency, cashExCode);
						if (subAccountDetail == null) {
						} else {
							subAccountList.add(copySubAccountProperties(subAccountDetail, accountNumber, subAccount.getAccountSerial()));
						}
					}
				}
			}
			
			// 查询交易流水明细
			if (CBRCConstants.CXNR_ACC_AND_TRANS.equals(cxnr)) {
				List<AccountTransaction> accountTransactions = messageService.queryAccountTransaction(accountNumber, startDate, endDate);
				if (accountTransactions == null || accountTransactions.size() == 0) {
				} else {
					transactionList.addAll(copyTransactionProperties(accountNumber, accountTransactions, customer.getKhmc()));
				}
			}
		}
		
		customer.setAccountList(accountList);
		customer.setMeasureList(measureList);
		customer.setSubAccountList(subAccountList);
		customer.setTransactionList(transactionList);
		
		return customer;
	}
	
	@Override
	public JSGA_QueryRequest_Customer getAccountDetail(Br40_cxqq_mx cxqq) throws DataOperateException, RemoteAccessException {
		return getAccountDetailAndTransaction(cxqq);
	}
	
	@Override
	public List<JSGA_QueryRequest_Transaction> getAccountTransaction(Br40_cxqq_mx cxqq) throws DataOperateException, RemoteAccessException {
		String taskType = cxqq.getTasktype(); // 任务类型
		
		// 查询方式 - 用于判断根据账（卡）号或证照查询
		String queryType = null;
		
		String cxlx = cxqq.getCxlx();
		if ("01".equals(cxlx)) { // 01 - 根据证照查询
			queryType = "01"; // 默认指定为根据三证查询
		}
		
		// 检查必需数据项的有效性
		String mxsdlx = cxqq.getMxsdlx();
		String startDate = cxqq.getMxqssj();
		String endDate = cxqq.getMxjzsj();
		try {
			if ("01".equals(mxsdlx)) {
				startDate = DtUtils.add(DtUtils.getNowDate(), DtUtils.YEAR, -1, false);
				endDate = DtUtils.getNowDate();
			} else if ("02".equals(mxsdlx)) {
				startDate = DtUtils.getBeginDt(DtUtils.getNowDate(), DtUtils.YEAR);
				endDate = DtUtils.getEndDt(DtUtils.getNowDate(), DtUtils.YEAR);
			} else if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "缺少自定义时间段");
			}
		} catch (Exception e) {
			throw new DataOperateException(CBRCConstants.REC_CODE_99999, "暂不支持该明细时间段");
		}
		
		List<String> accountNumbers = new ArrayList<String>();
		// 根据账（卡）号查询
		if (queryType == null || queryType.length() == 0) {
			String primaryNumber = cxqq.getCxzh(); // 查询账卡号
			if (StringUtils.isBlank(primaryNumber)) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "缺少查询账卡号");
			}
			accountNumbers.add(primaryNumber);
		}
		// 根据证件类型、证件号码、主体名称信息查询
		else {
			String credentialType = cxqq.getZzlx(); // 证件类型
			String credentialNumber = cxqq.getZzhm(); // 证件号码
			String subjectName = cxqq.getZtmc(); // 主体名称
			
			// 检查证照信息是否齐全（比如查询方式为02、03时，即使查询方式是01，也需要做如此判断）
			if (StringUtils.isBlank(credentialType) || StringUtils.isBlank(credentialNumber) || StringUtils.isBlank(subjectName)) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "暂不支持证照信息不全的查询方式");
			}
			
			// 查询客户信息
			String subjectType = cxqq.getZtlb(); // 主体类别
			JSGA_QueryRequest_Customer customer = queryCustomerByCredentialInfo(subjectType, credentialType, credentialNumber, subjectName);
			if (customer == null) {
				throw new DataOperateException(CBRCConstants.REC_CODE_99999, "查无客户信息");
			}
			
			// 根据证件信息查询合约账户基本信息
			List<ContractAccount> contractAccounts = messageService.queryContractAccountList(credentialType, credentialNumber, subjectName);
			if (contractAccounts == null || contractAccounts.size() == 0) {
			} else {
				for (ContractAccount account : contractAccounts) {
					String contractType = account.getContractType();
					// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
					if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
						accountNumbers.add(account.getAccountNumber());
					}
				}
			}
		}
		
		// 查询各合约账户的交易流水
		List<JSGA_QueryRequest_Transaction> transactionList = new ArrayList<JSGA_QueryRequest_Transaction>();
		Input998070 in = new Input998070(null, startDate, endDate);
		for (String primaryNumber : accountNumbers) {
			in.setAccountNumber(primaryNumber);
			V_AccountTransaction v = messageService.queryAccountTransaction(in);
			if (v == null || v.getAccountTransactionList() == null || v.getAccountTransactionList().size() == 0) {
			} else {
				transactionList.addAll(copyTransactionProperties(primaryNumber, v));
			}
		}
		
		return transactionList;
	}
	
	@Override
	public JSGA_FreezeRequest_Record freezeAccount(Br41_kzqq kzqq, JSGA_FreezeResponse_Account res) throws DataOperateException, RemoteAccessException {
		Input267500 in = new Input267500(); // 账户冻结输入项
		in.setFreezeInsName(kzqq.getSqjgdm()); // 冻结机构名称
		
		// 转换冻结类型
		String freezeType = res.getDjfs();
		freezeType = freezeType == null ? "" : freezeType.trim();
		if ("01".equals(freezeType)) { // 金额冻结
			in.setFreezeType("2");
			in.setFreezeAmount(res.getJe());
		} else if ("02".equals(freezeType)) { // 账户冻结
			in.setFreezeType("1");
		} else {
			in.setFreezeType(freezeType);
		}
		
		// 调整输入项
		in.setAccountNumber(res.getZh());
		in.setCurrency(res.getBz()); // 币种
		in.setEffectiveDate(res.getKssj());
		in.setExpiringDate(res.getJssj());
		in.setLawIDNumber1(kzqq.getQqrzjhm());
		in.setLawName1(kzqq.getQqrxm());
		in.setLawIDNumber2(kzqq.getXcrzjhm());
		in.setLawName2(kzqq.getXcrxm());
		in.setFreezeBookNumber(res.getRwlsh()); // 任务流水号作为文书编号
		
		// 远程调用
		FreezeResult freezeResult = messageService.freezeAccount(in);
		
		// 调整输出项
		return copyFreezeResultProperties(freezeResult);
	}
	
	@Override
	public JSGA_FreezeRequest_Record freezeAccountJC(Br41_kzqq kzqq, JSGA_FreezeResponse_Account res) throws DataOperateException, RemoteAccessException {
		Input267510 in = new Input267510(); // 解除冻结输入项
		in.setUnfreezeInsName(kzqq.getSqjgdm());// 冻结机构代码
		
		// 调整输入项
		in.setFrozenNumber(res.getHxappid()); // 原冻结编号
		in.setLawIDNumber1(kzqq.getQqrzjhm());
		in.setLawName1(kzqq.getQqrxm());
		in.setLawIDNumber2(kzqq.getXcrzjhm());
		in.setLawName2(kzqq.getXcrxm());
		in.setUnfreezeBookNumber(res.getRwlsh());
		
		// 远程调用
		UnfreezeResult unfreeze = messageService.unfreezeAccount(in);
		
		// 调整输出项
		if (unfreeze == null) {
			return null;
		}
		JSGA_FreezeRequest_Record unfreezeRecord = new JSGA_FreezeRequest_Record();
		unfreezeRecord.setZh(unfreeze.getAccountNumber());
		unfreezeRecord.setSqdjxe(unfreeze.getFrozenAmount());
		unfreezeRecord.setSdje(unfreeze.getUnfreezeAmount());
		unfreezeRecord.setYe(unfreeze.getAccountBalance());
		unfreezeRecord.setZhkyye(unfreeze.getAvailableAmount());
		return unfreezeRecord;
	}
	
	@Override
	public JSGA_FreezeRequest_Record freezeAccountYQ(Br41_kzqq kzqq, JSGA_FreezeResponse_Account res) throws DataOperateException, RemoteAccessException {
		Input267540 in = new Input267540(); // 冻结延期输入项
		in.setFreezeInsName(kzqq.getSqjgdm()); //
		
		// 调整输入项
		in.setFrozenNumber(res.getHxappid()); // 冻结编号
		in.setExpiringDate(res.getJssj());
		in.setLawIDNumber1(kzqq.getQqrzjhm());
		in.setLawName1(kzqq.getQqrxm());
		in.setLawIDNumber2(kzqq.getXcrzjhm());
		in.setLawName2(kzqq.getXcrxm());
		in.setFreezeBookNumber(res.getRwlsh()); // 任务流水号作为文书编号
		
		// 远程调用
		DeferFreezeResult freezeResult = messageService.deferFreezeAccount(in);
		
		// 调整输出项
		return copyFreezeResultProperties(freezeResult);
	}
	
	@Override
	public JSGA_StopPaymentRequest_Recored stoppayAccount(Br41_kzqq kzqq, JSGA_StopPaymentResponse_Account res) throws DataOperateException, RemoteAccessException {
		Input267500 in = new Input267500();
		in.setFreezeInsName(kzqq.getSqjgdm());// 需将申请机构代码转化为名称
		
		// 账户冻结
		in.setFreezeType("1");
		
		// 调整输入项
		Calendar cal = Calendar.getInstance(Locale.CHINESE);
		Date startTime = cal.getTime();
		cal.add(Calendar.DATE, 2);
		Date endTime = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
		
		in.setAccountNumber(res.getZh());
		in.setEffectiveDate(format.format(startTime));// 止付开始时间
		in.setExpiringDate(format.format(endTime));// 止付结束时间
		in.setLawIDNumber1(kzqq.getQqrzjhm());
		in.setLawName1(kzqq.getQqrxm());
		in.setLawIDNumber2(kzqq.getXcrzjhm());
		in.setLawName2(kzqq.getXcrxm());
		in.setFreezeBookNumber(res.getRwlsh());
		
		// 远程调用
		FreezeResult freeze = messageService.freezeAccount(in);
		
		// 调整输出项 
		if (freeze == null) {
			return null;
		}
		JSGA_StopPaymentRequest_Recored stopPayment = new JSGA_StopPaymentRequest_Recored();
		stopPayment.setHxappid(freeze.getFrozenNumber());
		stopPayment.setZh(freeze.getAccountNumber());
		stopPayment.setZxqssj(freeze.getEffectiveDate());
		return stopPayment;
	}
	
	@Override
	public JSGA_StopPaymentRequest_Recored stoppayAccountJC(Br41_kzqq kzqq, JSGA_StopPaymentResponse_Account res) throws DataOperateException, RemoteAccessException {
		Input267510 in = new Input267510();
		in.setUnfreezeInsName(kzqq.getSqjgdm());// 需将申请机构代码转化为名称
		
		// 调整输入项
		in.setFrozenNumber(res.getHxappid());
		in.setLawIDNumber1(kzqq.getQqrzjhm());
		in.setLawName1(kzqq.getQqrxm());
		in.setLawIDNumber2(kzqq.getXcrzjhm());
		in.setLawName2(kzqq.getXcrxm());
		in.setUnfreezeBookNumber(res.getRwlsh());
		
		// 远程调用
		UnfreezeResult unfreeze = messageService.unfreezeAccount(in);
		
		// 调整输出项
		if (unfreeze == null) {
			return null;
		}
		JSGA_StopPaymentRequest_Recored restore = new JSGA_StopPaymentRequest_Recored();
		restore.setZh(unfreeze.getAccountNumber());
		return restore;
	}
	
	// ==========================================================================================
	//                     Help Behavior
	// ==========================================================================================
	
	protected JSGA_QueryRequest_Customer queryCustomerByAccountNumber(String subjectType, String primaryNumber) throws DataOperateException, RemoteAccessException {
		if ("01".equals(subjectType)) {
			// 个人客户信息 
			IndividualCustomer personalInfo = messageService.queryIndividualCustomerInfo(primaryNumber);
			return copyPersonalInfoProperties(personalInfo);
		} else {
			// 对公客户信息
			CorporateCustomer corporateInfo = messageService.queryCorporateCustomerInfo(primaryNumber);
			return copyCorporateInfoProperties(corporateInfo);
		}
	}
	
	protected JSGA_QueryRequest_Customer queryCustomerByCredentialInfo(String subjectType, String credentialType, String credentialNumber, String subjectName)
		throws DataOperateException, RemoteAccessException {
		if ("01".equals(subjectType)) {
			// 个人客户信息 
			IndividualCustomer personalInfo = messageService.queryIndividualCustomerInfo(credentialType, credentialNumber, subjectName);
			return copyPersonalInfoProperties(personalInfo);
		} else {
			// 对公客户信息
			CorporateCustomer corporateInfo = messageService.queryCorporateCustomerInfo(credentialType, credentialNumber, subjectName);
			return copyCorporateInfoProperties(corporateInfo);
		}
	}
	
	@Override
	public void sendTransMsg(List<JSGA_ControlRequest_Record> tranList, String telno) throws Exception {
		// Nothing
	}
	
	// ==========================================================================================
	//                     对象转换的接口函数
	// ==========================================================================================
	private JSGA_FreezeRequest_Record copyFreezeResultProperties(FreezeResult freezeResult) {
		if (freezeResult == null) {
			return null;
		}
		
		JSGA_FreezeRequest_Record freezeRecord = new JSGA_FreezeRequest_Record();
		freezeRecord.setHxappid(freezeResult.getFrozenNumber()); // 冻结编号
		freezeRecord.setZh(freezeResult.getAccountNumber());
		freezeRecord.setSqdjxe(freezeResult.getFreezeAmount()); // 申请冻结限额
		freezeRecord.setSdje(freezeResult.getFrozenAmount()); // 执行冻结金额
		freezeRecord.setYe(freezeResult.getAccountBalance()); // 账户余额
		freezeRecord.setZxqssj(freezeResult.getEffectiveDate());
		freezeRecord.setDjjsrq(freezeResult.getExpiringDate());
		freezeRecord.setZhkyye(freezeResult.getAvailableAmount()); // 冻结后账户可用余额
		return freezeRecord;
	}
	
	private JSGA_FreezeRequest_Record copyFreezeResultProperties(DeferFreezeResult freezeResult) {
		if (freezeResult == null) {
			return null;
		}
		
		JSGA_FreezeRequest_Record deferFreezeRecord = new JSGA_FreezeRequest_Record();
		deferFreezeRecord.setZh(freezeResult.getAccountNumber());
		deferFreezeRecord.setSqdjxe(freezeResult.getFrozenAmount()); // 申请冻结限额
		deferFreezeRecord.setSdje(freezeResult.getFrozenAmount()); // 执行冻结金额
		deferFreezeRecord.setYe(freezeResult.getAccountBalance()); // 账户余额
		deferFreezeRecord.setZhkyye(freezeResult.getAvailableAmount()); // 账户可用余额
		deferFreezeRecord.setZxqssj(freezeResult.getEffectiveDate());
		deferFreezeRecord.setDjjsrq(freezeResult.getExpiringDate());
		return deferFreezeRecord;
	}
	
	private JSGA_QueryRequest_Customer copyPersonalInfoProperties(IndividualCustomer personalInfo) {
		if (personalInfo == null) {
			return null;
		}
		
		JSGA_QueryRequest_Customer customer = new JSGA_QueryRequest_Customer();
		if (personalInfo.getOpenIDInfo() != null) {
			customer.setZzlx(personalInfo.getOpenIDInfo().getIdType());
			customer.setZzhm(personalInfo.getOpenIDInfo().getIdNumber());
		}
		customer.setKhmc(personalInfo.getChineseName());
		customer.setDbrxm(""); // 代办人姓名
		customer.setDbrzjlx(""); // 代办人证件类型
		customer.setDbrzjhm(""); // 代办人证件号码
		
		customer.setLxsj(personalInfo.getTelephoneNumber()); // 联系手机
		customer.setZzdh(personalInfo.getMobilePhoneNumber()); // 住宅电话
		customer.setDwdh(personalInfo.getFixedLineNumber()); // 单位电话
		customer.setYxdz(personalInfo.getEmailAddress()); // 邮箱地址
		customer.setZzdz(personalInfo.getPermanentAddress()); // 住宅地址
		customer.setGzdw(""); // 工作单位
		customer.setDwdz(personalInfo.getMailingAddress()); // 单位地址
		return customer;
	}
	
	private JSGA_QueryRequest_Customer copyCorporateInfoProperties(CorporateCustomer corporateInfo) {
		if (corporateInfo == null) {
			return null;
		}
		
		JSGA_QueryRequest_Customer customer = new JSGA_QueryRequest_Customer();
		customer.setKhmc(corporateInfo.getChineseName());
		customer.setZzlx(corporateInfo.getOpenIDType());
		customer.setZzhm(corporateInfo.getOpenIDNumber());
		customer.setLxsj(corporateInfo.getTelephoneNumber());
		customer.setDwdz(corporateInfo.getRegisteredAddress());
		customer.setDwdh(corporateInfo.getFixedLineNumber());
		customer.setYxdz(corporateInfo.getEmailAddress());
		customer.setFrdb(corporateInfo.getLegalInfo().getName());
		customer.setFrdbzjlx(corporateInfo.getLegalInfo().getIdType());
		customer.setFrdbzjhm(corporateInfo.getLegalInfo().getIdNumber());
		customer.setKhgszzhm(corporateInfo.getBusiIDNumber());
		customer.setGsnsh(corporateInfo.getStateTaxIDNumber());
		customer.setDsnsh(corporateInfo.getLocalTaxIDNumber());
		return customer;
	}
	
	private JSGA_QueryRequest_Account copyAccountProperties(AccountDetail accountDetail) {
		JSGA_QueryRequest_Account account = new JSGA_QueryRequest_Account();
		account.setKh(accountDetail.getAccountNumber());// 卡号
		account.setZh(accountDetail.getDefaultSubAccount()); // 默认实体账号
		account.setBz(accountDetail.getCurrency()); // 币种
		account.setChbz(accountDetail.getCashExCode()); // 钞汇标识
		account.setZhlb(accountDetail.getAccountAttr()); // 账户类型
		account.setZhzt(accountDetail.getAccountStatus()); // 账户状态
		account.setKhwd(accountDetail.getAccountOpenBranch());// 开户网点
		account.setKhwddm(accountDetail.getAccountOpenBranch());// 开户网点代码
		account.setKhrq(accountDetail.getCardOpenDate());// 开户日期
		account.setXhwd(accountDetail.getAccountClosingBranch());// 销户网点
		account.setXhrq(accountDetail.getAccountClosingDate());// 销户日期
		account.setKyye(accountDetail.getAvailableBalance()); // 可用余额
		account.setZhye(accountDetail.getAccountBalance()); //账面余额
		account.setZhjysj(accountDetail.getLastTransDate());//最后交易日期
		return account;
	}
	
	private List<JSGA_QueryRequest_Transaction> copyTransactionProperties(String cardNumber, V_AccountTransaction v) {
		return copyTransactionProperties(cardNumber, v.getAccountTransactionList(), v.getAccountName());
	}
	
	private List<JSGA_QueryRequest_Transaction> copyTransactionProperties(String cardNumebr, List<AccountTransaction> list, String accountName) {
		List<JSGA_QueryRequest_Transaction> transactionList = new ArrayList<JSGA_QueryRequest_Transaction>();
		for (AccountTransaction transaction : list) {
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
			
			// 处理交易流水号（广发银行核心接口没有单独的交易流水号，所以用"[会计日] + [日志号] + [日志顺序号]"作为流水号。）
			String transactionSerial = null;
			String accountingDate = transaction.getAccountingDate(); // 会计日
			String logNumber = transaction.getLogNumber();
			String logNumberSeq = transaction.getLogSeq(); // 日志顺序号
			if (StringUtils.isNotBlank(logNumber) && StringUtils.isNotBlank(logNumberSeq)) {
				logNumber = StringUtils.leftPad(logNumber, 8, '0');
				logNumberSeq = StringUtils.leftPad(logNumberSeq, 4, '0');
				transactionSerial = accountingDate + logNumber + logNumberSeq;
			}
			
			JSGA_QueryRequest_Transaction cbrc_query_transaction = new JSGA_QueryRequest_Transaction();
			cbrc_query_transaction.setTransseq(transactionSerial); // 动态查询去重主键
			cbrc_query_transaction.setZh(transaction.getV_AccountNumber()); // 组合子账号
			cbrc_query_transaction.setCxkh(cardNumebr); // 查询卡号
			cbrc_query_transaction.setJylx(transaction.getTradeChannel()); // 交易类型/交易操作（交易渠道）
			cbrc_query_transaction.setJdbz(transaction.getDrcrFlag()); // 借贷标志
			cbrc_query_transaction.setBz(transaction.getTradeCurrency()); // 交易货币
			cbrc_query_transaction.setJe(transaction.getTradeAmount());
			cbrc_query_transaction.setYe(transaction.getAccountBalance());
			cbrc_query_transaction.setJysj(transaction.getTradeTime()); // 交易时间
			cbrc_query_transaction.setJylsh(transactionSerial); // 交易流水号
			cbrc_query_transaction.setJydfxm(transaction.getRelativeName()); // 交易对手姓名
			cbrc_query_transaction.setJydfzh(transaction.getRelativeNumber()); // 交易对手账号
			cbrc_query_transaction.setJydfkh(transaction.getRelativeNumber()); // 交易对手卡号
			cbrc_query_transaction.setJydfzjhm(""); // 交易对方证件号码
			cbrc_query_transaction.setJyzy(transaction.getRemark());
			cbrc_query_transaction.setJywddm(transaction.getTradeBranch()); // 交易行所
			cbrc_query_transaction.setJywdmc(transaction.getTradeBranch()); // 交易行所（用于逆向转码）
			cbrc_query_transaction.setRzh(transaction.getLogNumber());
			cbrc_query_transaction.setCph(transaction.getVoucherNo()); // 传票号
			cbrc_query_transaction.setPzzl(transaction.getVoucherCode()); // 凭证代码
			cbrc_query_transaction.setPzh(transaction.getVoucherNumber()); // 凭证号
			cbrc_query_transaction.setXjbz(transaction.getCashExCode()); // 钞汇标志
			cbrc_query_transaction.setZdh(""); // 终端号
			cbrc_query_transaction.setJysfcg("01"); // 交易是否成功，01表示成功（默认），02表示失败
			cbrc_query_transaction.setJyfsd(""); // 交易地址
			cbrc_query_transaction.setShmc(""); // 
			cbrc_query_transaction.setShh(""); // 
			cbrc_query_transaction.setIp(""); // IP
			cbrc_query_transaction.setMac(""); // MAC地址
			cbrc_query_transaction.setJygyh(transaction.getTradeTeller()); // 交易柜员
			cbrc_query_transaction.setKhmc(accountName); // 客户名称
			cbrc_query_transaction.setZhmc(accountName); // 账户名称
			transactionList.add(cbrc_query_transaction);
		}
		return transactionList;
	}
	
	private List<JSGA_QueryRequest_Measure> copyFreezeMeasureProperties(List<AccountFrozenMeasure> measures) {
		List<JSGA_QueryRequest_Measure> measureList = new ArrayList<JSGA_QueryRequest_Measure>();
		for (AccountFrozenMeasure measure : measures) {
			if ("N".equals(measure.getFrozenStatus())) { // N-冻结类数据
				JSGA_QueryRequest_Measure cbrc_query_measure = new JSGA_QueryRequest_Measure();
				cbrc_query_measure.setZh(measure.getAccountNumber());
				cbrc_query_measure.setCsxh(measure.getWaitingSeq());
				cbrc_query_measure.setDjksrq(measure.getEffectiveDate());
				cbrc_query_measure.setDjjzrq(measure.getExpiringDate());
				cbrc_query_measure.setDjjgmc(measure.getFrozenInsName());
				cbrc_query_measure.setDjje(measure.getFrozenAmount());
				cbrc_query_measure.setBeiz(measure.getRemark());
				cbrc_query_measure.setDjcslx(measure.getFrozenType());
				measureList.add(cbrc_query_measure);
			}
		}
		return measureList;
	}
	
	private JSGA_QueryRequest_SubAccount copySubAccountProperties(AccountDetail detail, String cardNumber, String serial) {
		JSGA_QueryRequest_SubAccount subAccount = new JSGA_QueryRequest_SubAccount();
		subAccount.setZh(cardNumber);
		subAccount.setZzhxh(serial); //
		subAccount.setZzhlb(detail.getAccountAttr());
		subAccount.setZzhzh(detail.getV_AccountNumber());
		subAccount.setBz(detail.getCurrency());
		subAccount.setChbz(detail.getCashExCode());
		subAccount.setZhye(detail.getAccountBalance());
		subAccount.setZhzt(detail.getAccountStatus());
		subAccount.setKyye(detail.getAvailableBalance());
		return subAccount;
	}
}
