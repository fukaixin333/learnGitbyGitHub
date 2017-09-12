package com.citic.server.inner.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.citic.server.inner.domain.AccountFrozenMeasure;
import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ContractAccount;
import com.citic.server.inner.domain.CustomerAddressInfo;
import com.citic.server.inner.domain.CustomerContactInfo;
import com.citic.server.inner.domain.CustomerIDInfo;
import com.citic.server.inner.domain.CustomerUpdateInfo;
import com.citic.server.inner.domain.OwnershipInfo;
import com.citic.server.inner.domain.SubAccountInfo;
import com.citic.server.inner.domain.request.Input025317;
import com.citic.server.inner.domain.request.Input025327;
import com.citic.server.inner.domain.request.Input025890;
import com.citic.server.inner.domain.request.Input028100;
import com.citic.server.inner.domain.request.Input265561;
import com.citic.server.inner.domain.request.Input267500;
import com.citic.server.inner.domain.request.Input267510;
import com.citic.server.inner.domain.request.Input267530;
import com.citic.server.inner.domain.request.Input267540;
import com.citic.server.inner.domain.request.Input267570;
import com.citic.server.inner.domain.request.Input267580;
import com.citic.server.inner.domain.request.Input267880;
import com.citic.server.inner.domain.request.Input358040;
import com.citic.server.inner.domain.request.Input358080;
import com.citic.server.inner.domain.request.Input998070;
import com.citic.server.inner.domain.request.InputPSB120;
import com.citic.server.inner.domain.request.InputPSB411;
import com.citic.server.inner.domain.response.AccountDetail;
import com.citic.server.inner.domain.response.AccountFrozenInfo;
import com.citic.server.inner.domain.response.AccountVerifyInfo;
import com.citic.server.inner.domain.response.CargoRecord;
import com.citic.server.inner.domain.response.CorporateCustomer;
import com.citic.server.inner.domain.response.DeductResult;
import com.citic.server.inner.domain.response.DeferFreezeResult;
import com.citic.server.inner.domain.response.FreezeResult;
import com.citic.server.inner.domain.response.IndividualCustomer;
import com.citic.server.inner.domain.response.UnfreezeResult;
import com.citic.server.inner.domain.response.V_AccountFrozenMeasure;
import com.citic.server.inner.domain.response.V_AccountTransaction;
import com.citic.server.inner.domain.response.V_ContractAccount;
import com.citic.server.inner.domain.response.V_CustomerUpdateInfo;
import com.citic.server.inner.domain.response.V_SubAccountInfo;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.DateUtils;

/**
 * @author liuxuanfei
 * @date 2016年5月4日 下午6:13:37
 */
@Service("innerMessageService")
public class InnerMessageService extends AbstractPrefixMessageService {
	
	// ==========================================================================================
	//                     个人客户信息查询（025317）
	// ==========================================================================================
	@Override
	public IndividualCustomer queryIndividualCustomerInfo(String accountNumber) throws DataOperateException, RemoteAccessException {
		return queryIndividualCustomerInfo(new Input025317(accountNumber));
	}
	
	@Override
	public IndividualCustomer queryIndividualCustomerInfo(String idType, String idNumber, String customerName) throws DataOperateException, RemoteAccessException {
		return queryIndividualCustomerInfo(new Input025317(idType, idNumber, customerName));
	}
	
	@Override
	public IndividualCustomer queryIndividualCustomerInfo(Input025317 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_INDIVIDUAL_INFO);
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
		} else if (accountNumber.startsWith("V") && accountNumber.length() > 5) { // 拆分组合子账号
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		IndividualCustomer customer = (IndividualCustomer) writeRequestMessage(in, IndividualCustomer.class);
		return decodeIndividualCustomer(customer);
	}
	
	private IndividualCustomer decodeIndividualCustomer(IndividualCustomer customer) {
		String tType;
		String tValue;
		
		/**
		 * 13-联络手机号码；14-传真；15-安全手机号；16-固定电话；<br>
		 * 22-QQ；25-微信；26-易信；<br>
		 * 31-电子邮箱；99-其它联系方式
		 */
		for (CustomerContactInfo info : customer.getContactInfoList()) {
			tType = info.getContactType();
			tValue = (info.getContactNumber() == null || info.getContactNumber().length() == 0) ? info.getContactInfo() : info.getContactNumber();
			if ("13".equals(tType)) {
				customer.setTelephoneNumber(tValue);
			} else if ("14".equals(tType)) {
				customer.setFaxNumber(tValue);
			} else if ("15".equals(tType)) {
				customer.setMobilePhoneNumber(tValue);
			} else if ("16".equals(tType)) {
				customer.setFixedLineNumber(tValue);
			} else if ("22".equals(tType)) {
				customer.setTencentQQ(tValue);
			} else if ("25".equals(tType)) {
				customer.setTencentWeix(tValue);
			} else if ("26".equals(tType)) {
				customer.setYiXin(tValue);
			} else if ("31".equals(tType)) {
				customer.setEmailAddress(tValue);
			}
		}
		
		/**
		 * 114-常住地址；120-通讯地址；130-证件地址；<br>
		 * 210-注册地址；199-其它地址
		 */
		for (CustomerAddressInfo info : customer.getAddressInfoList()) {
			tType = info.getAddressType();
			if ("114".equals(tType)) {
				customer.setPermanentAddress(info.getAddressDetail());
			} else if ("120".equals(tType)) {
				customer.setMailingAddress(info.getAddressDetail());
			} else if ("130".equals(tType)) {
				customer.setIdAddress(info.getAddressDetail());
			} else if ("210".equals(tType)) {
				customer.setRegisteredAddress(info.getAddressDetail());
			} else if ("199".equals(tType)) {
				customer.setOtherAddress(info.getAddressDetail());
			}
		}
		
		return customer;
	}
	
	// ==========================================================================================
	//                     公司同业客户信息查询（025327）
	// ==========================================================================================
	@Override
	public CorporateCustomer queryCorporateCustomerInfo(String accountNumber) throws DataOperateException, RemoteAccessException {
		return queryCorporateCustomerInfo(new Input025327(accountNumber));
	}
	
	@Override
	public CorporateCustomer queryCorporateCustomerInfo(String idType, String idNumber, String customerName) throws DataOperateException, RemoteAccessException {
		return queryCorporateCustomerInfo(new Input025327(idType, idNumber, customerName));
	}
	
	@Override
	public CorporateCustomer queryCorporateCustomerInfo(Input025327 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_CORPORATE_INFO);
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
		} else if (accountNumber.startsWith("V") && accountNumber.length() > 5) { // 拆分组合子账号
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		try {
			CorporateCustomer corporateInfo = (CorporateCustomer) writeRequestMessage(in, CorporateCustomer.class);
			return decodeCorporateCustomer(corporateInfo);
		} catch (RemoteAccessException e) {
			throw new DataOperateException("DC2070", "网络错误或客户信息不存在");
		}
	}
	
	private CorporateCustomer decodeCorporateCustomer(CorporateCustomer customer) {
		String tType;
		String tValue;
		
		List<CustomerIDInfo> idInfoList = customer.getIdentificationInfos();
		if (idInfoList != null) {
			for (CustomerIDInfo idInfo : idInfoList) {
				tType = idInfo.getIdType();
				if ("22600".equals(tType)) { // 事业法人证书
					customer.setLpriIDNumber(idInfo.getIdNumber());
				} else if ("20300".equals(tType)) { // 工商登记证
					customer.setBusiIDNumber(idInfo.getIdNumber());
				} else if ("20701".equals(tType)) { // 税务登记证（国税）
					customer.setStateTaxIDNumber(idInfo.getIdNumber());
				} else if ("20702".equals(tType)) { // 税务登记证（地税）
					customer.setLocalTaxIDNumber(idInfo.getIdNumber());
				} else if ("24100".equals(tType)) { // 统一社会信用代码
					customer.setUnifiedSocialCreditCode(idInfo.getIdNumber());
				}
				
				// 如果是开户证件，设置为查询证照
				if ("Y".equals(idInfo.getOpenFlag())) {
					customer.setOpenIDType(tType);
					customer.setOpenIDNumber(idInfo.getIdNumber());
				}
			}
		}
		
		if (customer.getContactInfoList() != null) {
			/**
			 * 13-联络手机号码；14-传真；15-安全手机号；16-固定电话；<br>
			 * 22-QQ；25-微信；26-易信；<br>
			 * 31-电子邮箱；99-其它联系方式
			 */
			for (CustomerContactInfo info : customer.getContactInfoList()) {
				tType = info.getContactType();
				tValue = (info.getContactNumber() == null || info.getContactNumber().length() == 0) ? info.getContactInfo() : info.getContactNumber();
				if ("13".equals(tType)) {
					customer.setTelephoneNumber(tValue);
				} else if ("14".equals(tType)) {
					customer.setFaxNumber(tValue);
				} else if ("15".equals(tType)) {
					customer.setMobilePhoneNumber(tValue);
				} else if ("16".equals(tType)) {
					customer.setFixedLineNumber(tValue);
				} else if ("22".equals(tType)) {
					customer.setTencentQQ(tValue);
				} else if ("25".equals(tType)) {
					customer.setTencentWeix(tValue);
				} else if ("26".equals(tType)) {
					customer.setYiXin(tValue);
				} else if ("31".equals(tType)) {
					customer.setEmailAddress(tValue);
				}
			}
		}
		
		if (customer.getAddressInfoList() != null) {
			/**
			 * 114-常住地址；120-通讯地址；130-证件地址；<br>
			 * 210-注册地址；199-其它地址
			 */
			for (CustomerAddressInfo info : customer.getAddressInfoList()) {
				tType = info.getAddressType();
				if ("114".equals(tType)) {
					customer.setPermanentAddress(info.getAddressDetail());
				} else if ("120".equals(tType)) {
					customer.setMailingAddress(info.getAddressDetail());
				} else if ("130".equals(tType)) {
					customer.setIdAddress(info.getAddressDetail());
				} else if ("210".equals(tType)) {
					customer.setRegisteredAddress(info.getAddressDetail());
				} else if ("199".equals(tType)) {
					customer.setOtherAddress(info.getAddressDetail());
				}
			}
		}
		
		return customer;
	}
	
	// ==========================================================================================
	//                     客户信息变更历史查询（025890）：每页10行
	// ==========================================================================================
	@Override
	public List<CustomerUpdateInfo> queryCustomerInfoUpdateList(String accountNumber, String beginDate, String endDate) throws DataOperateException, RemoteAccessException {
		return queryCustomerInfoUpdateList(new Input025890(accountNumber, beginDate, endDate));
	}
	
	@Override
	public List<CustomerUpdateInfo> queryCustomerInfoUpdateList(Input025890 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_CUSTOMER_UPDATE_LIST);
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
		} else if (accountNumber.startsWith("V") && accountNumber.length() > 5) { // 拆分组合子账号
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		V_CustomerUpdateInfo v = (V_CustomerUpdateInfo) writeRequestMessage(in, V_CustomerUpdateInfo.class);
		return v.getCustomerUpdateInfoList();
	}
	
	// ==========================================================================================
	//                     合约账号查询（028100）：每页25行
	// ==========================================================================================
	@Override
	public List<ContractAccount> queryContractAccountList(String idType, String idNumber, String customerName) throws DataOperateException, RemoteAccessException {
		return queryContractAccountList(new Input028100(idType, idNumber, customerName));
	}
	
	@Override
	public List<ContractAccount> queryContractAccountList(Input028100 in) throws DataOperateException, RemoteAccessException {
		int pageNum = 0; // 查询页码
		int pageRow = 25;
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_CONTRACT_ACCOUNT_LIST);
		in.setPageRow(pageRow); // 每页数据条数
		in.setPageNum(pageNum++);
		
		List<ContractAccount> contractAccountList = new ArrayList<ContractAccount>();
		while (true) {
			V_ContractAccount v = (V_ContractAccount) writeRequestMessage(in, V_ContractAccount.class);
			if (!hasResultSet(v)) {
				break;
			}
			contractAccountList.addAll(v.getContractAccountList());
			// 分页处理
			if (isLastPage(v, pageRow)) {
				break;
			}
			in.setPageNum(++pageNum);
			in.setTotalNum(v.getTotalNum());
			in.setTodRecNum(v.getTodRecNum());
		}
		// 返回结果
		return contractAccountList;
	}
	
	// ==========================================================================================
	//                     账户信息查询（358080）
	// ==========================================================================================
	@Override
	public AccountDetail queryAccountDetail(String accountNumber, String currency, String cashExCode) throws DataOperateException, RemoteAccessException {
		return queryAccountDetail(new Input358080(accountNumber, currency, cashExCode));
	}
	
	@Override
	public AccountDetail queryAccountDetail(Input358080 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_ACCOUNT_DETAIL);
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
			throw new DataOperateException("卡号或账号必须输入");
		}
		
		// 拆分组合子账号
		if (accountNumber.startsWith("V") && accountNumber.length() > 5) {
			if (in.getCashExCode() == null || in.getCashExCode().length() == 0) {
				in.setCashExCode(accountNumber.substring(1, 2));
			}
			if (in.getCurrency() == null || in.getCurrency().length() == 0) {
				in.setCurrency(accountNumber.substring(2, 5));
			}
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		// 查询余额但币种为空时，默认为人民币
		if (in.isQueryBalance() && (in.getCurrency() == null || in.getCurrency().length() == 0)) {
			in.setCurrency("156");
		}
		
		AccountDetail accountDetail = (AccountDetail) writeRequestMessage(in, AccountDetail.class);
		
		// 查询合约账户的详细信息，详细信息中的账号可能返回SASB账号，故需将其还原成合约账号。
		// 否则，SASB账号在执行控制类操作时核心会反馈错误信息。
		if (accountDetail != null) {
			accountDetail.setAccountNumber(in.getAccountNumber());
		}
		
		return accountDetail;
	}
	
	// ==========================================================================================
	//                     账户校验及信息查询（358040）
	// ==========================================================================================
	@Override
	public AccountVerifyInfo queryAccountVerifyInfo(String accountNumber, String currency, String cashExCode) throws DataOperateException, RemoteAccessException {
		return queryAccountVerifyInfo(new Input358040(accountNumber, currency, cashExCode));
	}
	
	@Override
	public AccountVerifyInfo queryAccountVerifyInfo(Input358040 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_ACCOUNT_VERIFY_INFO);
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
			throw new DataOperateException("卡号或账号必须输入");
		}
		if (accountNumber.startsWith("V") && accountNumber.length() > 5) {
			if (in.getCashExCode() == null || in.getCashExCode().length() == 0) {
				in.setCashExCode(accountNumber.substring(1, 2));
			}
			if (in.getCurrency() == null || in.getCurrency().length() == 0) {
				in.setCurrency(accountNumber.substring(2, 5));
			}
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		AccountVerifyInfo accountInfo = (AccountVerifyInfo) writeRequestMessage(in, AccountVerifyInfo.class);
		
		// 查询合约账户的详细信息，详细信息中的账号可能返回SASB账号，故需将其还原成合约账号。
		// 否则，SASB账号在执行控制类操作时核心会反馈错误信息。
		if (accountInfo != null) {
			accountInfo.setAccountNumber(in.getAccountNumber());
		}
		return accountInfo;
	}
	
	// ==========================================================================================
	//                     冻结在先查询（267570）：每页10行
	// ==========================================================================================
	@Override
	public List<AccountFrozenMeasure> queryAccountFrozenMeasures(String accountNumber) throws DataOperateException, RemoteAccessException {
		return queryAccountFrozenMeasures(new Input267570(accountNumber));
	}
	
	@Override
	public List<AccountFrozenMeasure> queryAccountFrozenMeasures(Input267570 in) throws DataOperateException, RemoteAccessException {
		int pageNum = 0;
		int pageRow = 10;
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_ACCOUNT_FROZENMEASURE_LIST);
		in.setPageRow(pageRow);
		in.setPageNum(pageNum++);
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
			throw new DataOperateException("卡号或账号必须输入");
		}
		
		// 拆分组合子账号
		if (accountNumber.startsWith("V") && accountNumber.length() > 5) {
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		List<AccountFrozenMeasure> freezeMeasureList = new ArrayList<AccountFrozenMeasure>();
		while (true) {
			V_AccountFrozenMeasure v = (V_AccountFrozenMeasure) writeRequestMessage(in, V_AccountFrozenMeasure.class);
			if (!hasResultSet(v)) {
				break;
			}
			freezeMeasureList.addAll(v.getFrozenMeasureList());
			// 分页处理
			if (isLastPage(v, pageRow)) {
				break;
			}
			in.setPageNum(++pageNum);
			in.setTotalNum(v.getTotalNum());
			in.setTodRecNum(v.getTodRecNum());
		}
		
		return freezeMeasureList;
	}
	
	// ==========================================================================================
	//                     冻结记录明细查询（267530）
	// ==========================================================================================
	
	public AccountFrozenInfo queryFrozenInfoByFrozenNumber(String frozenNumber) throws DataOperateException, RemoteAccessException {
		return queryFrozenInfoByFrozenNumber(new Input267530(frozenNumber));
	}
	
	public AccountFrozenInfo queryFrozenInfoByFrozenNumber(Input267530 in) throws DataOperateException, RemoteAccessException {
		
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_FROZENINFO_FROZENNUMBER);
		String frzonNuber = in.getFrozenNumber();
		if (frzonNuber == null || frzonNuber.length() == 0) {
			throw new DataOperateException("冻结编号必须输入");
		}
		return (AccountFrozenInfo) writeRequestMessage(in, AccountFrozenInfo.class);
		
	}
	
	// ==========================================================================================
	//                     账户明细列表查询（267880）：每页20行
	// ==========================================================================================
	@Override
	public List<SubAccountInfo> querySubAccountInfoList(String accountNumber) throws DataOperateException, RemoteAccessException {
		return querySubAccountInfoList(new Input267880(accountNumber));
	}
	
	@Override
	public List<SubAccountInfo> querySubAccountInfoList(Input267880 in) throws DataOperateException, RemoteAccessException {
		int pageNum = 0; // 查询页码
		int pageRow = 20;
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_SUB_ACCOUNT_LIST);
		in.setPageRow(pageRow);
		in.setPageNum(pageNum++);
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
			throw new DataOperateException("卡号或账号必须输入");
		}
		
		// 拆分组合子账号
		if (accountNumber.startsWith("V") && accountNumber.length() > 5) {
			if (in.getCashExCode() == null || in.getCashExCode().length() == 0) {
				in.setCashExCode(accountNumber.substring(1, 2));
			}
			if (in.getCurrency() == null || in.getCurrency().length() == 0) {
				in.setCurrency(accountNumber.substring(2, 5));
			}
			in.setAccountNumber(accountNumber.substring(5));
		}
		
		List<SubAccountInfo> accountInfoList = new ArrayList<SubAccountInfo>();
		while (true) {
			V_SubAccountInfo v = (V_SubAccountInfo) writeRequestMessage(in, V_SubAccountInfo.class);
			if (!hasResultSet(v)) {
				break;
			}
			accountInfoList.addAll(v.getSubAccountInfoList());
			// 分页处理
			if (isLastPage(v, pageRow)) {
				break;
			}
			in.setPageNum(++pageNum);
			in.setTotalNum(v.getTotalNum());
			in.setTodRecNum(v.getTodRecNum());
		}
		return accountInfoList;
	}
	
	// ==========================================================================================
	//                     共有权（265561）
	// ==========================================================================================
	
	@Override
	public OwnershipInfo queryOwnershipInfo(String accountNumber) throws DataOperateException, RemoteAccessException {
		return queryOwnershipInfo(new Input265561(accountNumber, null));
	}
	
	@Override
	public OwnershipInfo queryOwnershipInfo(Input265561 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_OWNERSHIP_INFO);
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
		} else {
			// 拆分组合子账号
			if (accountNumber.startsWith("V") && accountNumber.length() > 5) {
				in.setAccountNumber(accountNumber.substring(5));
			}
		}
		return (OwnershipInfo) writeRequestMessage(in, OwnershipInfo.class);
	}
	
	// ==========================================================================================
	//                     交易历史查询（998070）：每页10行
	// ==========================================================================================
	@Override
	public List<AccountTransaction> queryAccountTransaction(String accountNumber, String startDate, String endDate) throws DataOperateException, RemoteAccessException {
		V_AccountTransaction v = queryAccountTransaction(new Input998070(accountNumber, startDate, endDate));
		return v == null ? null : v.getAccountTransactionList();
	}
	
	@Override
	public V_AccountTransaction queryAccountTransaction(Input998070 in) throws DataOperateException, RemoteAccessException {
		int pageNum = 0;
		int pageRow = 10;
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_TRANSACTION_LIST);
		in.setPageRow(pageRow);
		in.setPageNum(pageNum++);
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
		} else {
			// 拆分组合子账号
			if (accountNumber.startsWith("V") && accountNumber.length() > 5) {
				if (in.getCurrency() == null || in.getCurrency().length() == 0) {
					in.setCurrency(accountNumber.substring(2, 5));
				}
				in.setAccountNumber(accountNumber.substring(5));
			}
		}
		
		V_AccountTransaction v = null;
		List<AccountTransaction> accountTransactionList = new ArrayList<AccountTransaction>();
		while (accountTransactionList.size() < 3000) {
			v = (V_AccountTransaction) writeRequestMessage(in, V_AccountTransaction.class);
			if (!hasResultSet(v)) {
				break;
			}
			// 交易流水
			accountTransactionList.addAll(v.getAccountTransactionList());
			// 分页处理
			if (isLastPage(v, pageRow)) {
				break;
			}
			in.setPageNum(++pageNum);
			in.setTotalNum(v.getTotalNum());
			in.setTodRecNum(v.getTodRecNum());
		}
		
		if (v != null) {
			v.setAccountTransactionList(accountTransactionList);
		}
		
		return v;
	}
	
	// ==========================================================================================
	//                     账户冻结（267500）
	// ==========================================================================================
	@Override
	public FreezeResult freezeAccount(Input267500 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(FREEZE_ACCOUNT);
		
		// 时间处理
		String startTime = in.getEffectiveDate(); // 生效日期
		if (DateUtils.compareToCurrDateTime(startTime, "yyyy-MM-dd") != 0) {
			in.setEffectiveDate(Utility.currDateTime19());
		}
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
			throw new DataOperateException("账卡号不能为空");
		}
		
		// 拆分组合子账号
		if (accountNumber.startsWith("V") && accountNumber.length() > 5) {
			if (in.getCashExCode() == null || in.getCashExCode().length() == 0) {
				in.setCashExCode(accountNumber.substring(1, 2));
			}
			if (in.getCurrency() == null || in.getCurrency().length() == 0) {
				in.setCurrency(accountNumber.substring(2, 5));
			}
			in.setAccountNumber(accountNumber.substring(5));
		}
		// 考虑到主账户默认序号为“1”时，会导致主子账户交叉的问题，故取消账户序号的默认值
		// else if (in.getAccountSeq() == null || in.getAccountSeq().length() == 0) {
		//     in.setAccountSeq("1");
		// }
		
		// 冻结方式为非账户冻结时，币种默认为156-人民币
		if (!"1".equals(in.getFreezeType()) && (in.getCurrency() == null || in.getCurrency().length() == 0)) {
			in.setCurrency("156");
		}
		
		// 发送报文
		return (FreezeResult) writeRequestMessage(in, FreezeResult.class);
	}
	
	// ==========================================================================================
	//                     账户解冻（267510）
	// ==========================================================================================
	@Override
	public UnfreezeResult unfreezeAccount(Input267510 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(UNFREEZE_ACCOUNT);
		return (UnfreezeResult) writeRequestMessage(in, UnfreezeResult.class);
	}
	
	// ==========================================================================================
	//                     账户续冻（267540）
	// ==========================================================================================
	@Override
	public DeferFreezeResult deferFreezeAccount(Input267540 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(DEFER_FREEZE_ACCOUNT);
		return (DeferFreezeResult) writeRequestMessage(in, DeferFreezeResult.class);
	}
	
	// ==========================================================================================
	//                     核心扣划（267580）
	// ==========================================================================================
	
	@Override
	public DeductResult deductAccount(Input267580 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(DEDUCT_ACCOUNT);
		
		String accountNumber = in.getAccountNumber();
		if (accountNumber == null || accountNumber.length() == 0) {
			throw new DataOperateException("账卡号不能为空");
		}
		
		// 拆分组合子账号
		if (accountNumber.startsWith("V") && accountNumber.length() > 5) {
			if (in.getCashExCode() == null || in.getCashExCode().length() == 0) {
				in.setCashExCode(accountNumber.substring(1, 2));
			}
			if (in.getCurrency() == null || in.getCurrency().length() == 0) {
				in.setCurrency(accountNumber.substring(2, 5));
			}
			in.setAccountNumber(accountNumber.substring(5));
		} else if (in.getAccountSerial() == null || in.getAccountSerial().length() == 0) {
			in.setAccountSerial("1");
		}
		
		// 币种默认为人民币
		if (in.getCurrency() == null || in.getCurrency().length() == 0) {
			in.setCurrency("156");
		}
		// 钞汇标识默认为钞户
		if (in.getCashExCode() == null || in.getCashExCode().length() == 0) {
			in.setCashExCode("1");
		}
		
		return (DeductResult) writeRequestMessage(in, DeductResult.class);
	}
	
	// ==========================================================================================
	//                     二代扣划（PSB120）
	// ==========================================================================================
	@Override
	public CargoRecord externalTransfer(InputPSB120 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(EXTERNAL_TRABSFER);
		in.setTransSerialNum(getNextSenderSN(getNextTransSerialNumber(), 12));
		return (CargoRecord) writeRequestMessage(in, CargoRecord.class);
	}
	
	@Override
	public CargoRecord queryCargoRecord(InputPSB411 in) throws DataOperateException, RemoteAccessException {
		in.setTo(ServerEnvironment.getStringValue(Keys.ARTERY_TO_CORESYSTEM));
		in.setCode(QUERY_EXTERNAL_TRABSFER_INFO);
		return (CargoRecord) writeRequestMessage(in, CargoRecord.class);
	}
}
