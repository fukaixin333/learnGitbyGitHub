package com.citic.server.gf.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.cgb.domain.request.ExternalTransferInput;
import com.citic.server.cgb.domain.request.FinancialDeferFreezeInput;
import com.citic.server.cgb.domain.request.FinancialFreezeInput;
import com.citic.server.cgb.domain.request.FinancialFrozenMeasuresInput;
import com.citic.server.cgb.domain.request.FinancialUnfreezeInput;
import com.citic.server.cgb.domain.response.CargoRecordResult;
import com.citic.server.cgb.domain.response.FinancialDeferFreezeResult;
import com.citic.server.cgb.domain.response.FinancialFreezeResult;
import com.citic.server.cgb.domain.response.FinancialFrozenMeasure;
import com.citic.server.cgb.domain.response.FinancialUnfreezeResult;
import com.citic.server.cgb.domain.response.OnlineTPFinancialDetail;
import com.citic.server.gf.domain.QueryRequestObj;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Glxx;
import com.citic.server.gf.domain.request.QueryRequest_Jrxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.request.QueryRequest_Wlxx;
import com.citic.server.gf.domain.request.QueryRequest_Zhxx;
import com.citic.server.gf.domain.response.ControlResponse_Kzzh;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;
import com.citic.server.inner.domain.AccountFrozenMeasure;
import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ContractAccount;
import com.citic.server.inner.domain.OwnershipInfo;
import com.citic.server.inner.domain.SubAccountInfo;
import com.citic.server.inner.domain.request.Input267500;
import com.citic.server.inner.domain.request.Input267510;
import com.citic.server.inner.domain.request.Input267540;
import com.citic.server.inner.domain.request.Input267570;
import com.citic.server.inner.domain.request.Input267580;
import com.citic.server.inner.domain.request.Input358040;
import com.citic.server.inner.domain.request.Input358080;
import com.citic.server.inner.domain.response.AccountDetail;
import com.citic.server.inner.domain.response.AccountVerifyInfo;
import com.citic.server.inner.domain.response.CorporateCustomer;
import com.citic.server.inner.domain.response.DeductResult;
import com.citic.server.inner.domain.response.DeferFreezeResult;
import com.citic.server.inner.domain.response.FreezeResult;
import com.citic.server.inner.domain.response.IndividualCustomer;
import com.citic.server.inner.domain.response.UnfreezeResult;
import com.citic.server.inner.service.IPrefixMessageService;
import com.citic.server.inner.service.ISOAPMessageService;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.BB13_organ_telno;
import com.citic.server.service.time.SequenceService;

@Service("remoteDataOperate1")
public class RemoteDataOperate01 extends AbstractDataOperate01 {
	
	private final Logger logger = LoggerFactory.getLogger(RemoteDataOperate01.class);
	
	@Autowired
	@Qualifier("innerMessageService")
	private IPrefixMessageService prefixMessageService;
	
	@Autowired
	@Qualifier("innerTransSerialNumber")
	private SequenceService transSerialNumberService;
	
	@Autowired
	@Qualifier("soapMessageService")
	private ISOAPMessageService soapMessageService;
	
	@Override
	public QueryRequestObj getQueryRequestObj(QueryResponse_Cxqq cxqq) throws DataOperateException, RemoteAccessException {
		QueryRequestObj requestObj = new QueryRequestObj();
		
		String customerName = cxqq.getXm(); // 被查询人姓名
		String idType = cxqq.getZjlx(); // 证件类型
		String idNumber = cxqq.getDsrzjhm(); // 证件号码
		
		if (StringUtils.isBlank(customerName) || StringUtils.isBlank(idType) || StringUtils.isBlank(idType)) {
			throw new DataOperateException(CBRCConstants.REC_CODE_99999, "暂时不支持三证不全信息查询");
		}
		
		boolean personal; // 是否个人客户
		String mailingAddress = null; // 通讯地址
		String telephoneNumber = null; // 联系电话
		
		// 1. 查询客户信息
		// 需要区分个人和对公两个接口，而高法请求中并未直接标注被请求人的类型，只能根据证件类型是否为“组织机构代码”来判断。
		if ("20600".equals(idType) || "20500".equals(idType) || "24100".equals(idType)) { // 组织机构代码/企业营业执照/统一社会信用代码
			// 1.1. 对公客户信息
			personal = false;
			CorporateCustomer corporateInfo = prefixMessageService.queryCorporateCustomerInfo(idType, idNumber, customerName);
			mailingAddress = corporateInfo.getMailingAddress();
			telephoneNumber = corporateInfo.getTelephoneNumber();
		} else {
			// 1.2. 个人客户信息
			personal = true;
			IndividualCustomer personalInfo = prefixMessageService.queryIndividualCustomerInfo(idType, idNumber, customerName);
			mailingAddress = personalInfo.getMailingAddress();
			telephoneNumber = personalInfo.getTelephoneNumber();
		}
		
		// 2. 查询首层合约账户
		List<ContractAccount> contractAccounts = prefixMessageService.queryContractAccountList(idType, idNumber, customerName);
		if (contractAccounts.size() == 0 || contractAccounts == null) {
			throw new DataOperateException(CBRCConstants.REC_CODE_99999, "查无合约账户信息");
		}
		
		// 3. 查询各关联详细信息
		List<QueryRequest_Zhxx> zhxxList = new ArrayList<QueryRequest_Zhxx>();
		List<QueryRequest_Glxx> glxxList = new ArrayList<QueryRequest_Glxx>();
		List<QueryRequest_Wlxx> wlxxList = new ArrayList<QueryRequest_Wlxx>();
		List<QueryRequest_Djxx> djxxList = new ArrayList<QueryRequest_Djxx>();
		List<QueryRequest_Jrxx> jrxxList = new ArrayList<QueryRequest_Jrxx>();
		List<QueryRequest_Qlxx> qlxxList = new ArrayList<QueryRequest_Qlxx>();
		
		HashMap<String, String[]> amap = new HashMap<String, String[]>();
		int acxh = 0;
		for (ContractAccount account : contractAccounts) {
			String accountNumber = account.getAccountNumber();
			String contractType = account.getContractType();
			String fromApp = account.getFromApp();
			AccountDetail accountDetail = null;
			String[] acc = null;
			
			boolean bool = false;
			if (StringUtils.isBlank(contractType)) {
				if ("DD".equals(fromApp) || "TD".equals(fromApp)) {
					bool = true;
				}
			} else if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
				bool = true;
			}
			
			if (!bool) {
				continue;
			}
			
			// 3.1. 查询合约账户详细信息（CARD-借记卡、CAAC-活期存款、MMDP-定期存款）（CLDD-）
			try {
				accountDetail = prefixMessageService.queryAccountDetail(account.getAccountNumber(), account.getCurrency(), null);
			} catch (Exception e) {
				logger.error("Exception: 查询合约账户详细信息异常 - 账号=[{}]", account.getAccountNumber(), e);
				continue;
			}
			
			if (accountDetail == null) {
				continue;
			}
			
			zhxxList.add(copyQueryRequestZhxx(accountDetail, ++acxh, mailingAddress, telephoneNumber));
			acc = new String[] {accountNumber, String.valueOf(acxh)};
			amap.put(accountNumber, acc);
			
			// 3.2. 查询合约账户的共有权/优先权信息
			int osxh = 1;
			OwnershipInfo joInfo = null;
			try {
				joInfo = prefixMessageService.queryOwnershipInfo(accountNumber);
				if (joInfo != null && (joInfo.isMargin() || joInfo.isPledge())) {
					qlxxList.add(copyQueryRequestQlxx(accountNumber, acxh, osxh++, accountDetail));
				}
			} catch (DataOperateException e) {
				logger.error("Exception: 共有权/优先权信息查询异常 - BDHM=[{}] ACCOUNT=[{}]", cxqq.getBdhm(), accountNumber, e);
			}
			
			// 3.3. 对私客户查询账户详细列表（子账户）
			if (personal) {
				List<SubAccountInfo> subAccountInfos = prefixMessageService.querySubAccountInfoList(accountNumber);
				
				int glxh = 1;
				boolean queryOwnership = (joInfo != null) && (joInfo.isMargin() || joInfo.isPledge() || joInfo.hasSubOwnership());
				for (SubAccountInfo subAccount : subAccountInfos) {
					String subAccountNumber = subAccount.getAccountNumber(); // 账号
					String currency = subAccount.getCurrency(); // 币种
					String cashExCode = subAccount.getCashExCode(); // 钞汇标志
					if ((subAccountNumber == null || subAccountNumber.length() == 0) || (currency == null || currency.length() == 0)) {
						continue;
					}
					AccountDetail subAccountDetail = prefixMessageService.queryAccountDetail(subAccountNumber, currency, cashExCode);
					if (subAccountDetail == null) {
						continue;
					}
					glxxList.add(copyQueryRequestGlxx(subAccountDetail, accountNumber, acxh, glxh++, subAccount.getAccountSerial()));
					amap.put(subAccountNumber, acc);
					
					// 3.3.1. 查询共有权/优先权信息
					if (queryOwnership) {
						String accountType = subAccount.getAccountType(); // 账户类型（DD-活期；TD-定期）
						if ("DD".equals(accountType) || "TD".equals(accountType)) {
							try {
								OwnershipInfo subJoInfo = prefixMessageService.queryOwnershipInfo(subAccountNumber);
								if (subJoInfo != null && (subJoInfo.isMargin() || subJoInfo.isPledge())) {
									qlxxList.add(copyQueryRequestQlxx(accountNumber, acxh, osxh++, subAccountDetail));
								}
							} catch (Exception e) {
								logger.error("Exception: 共有权/优先权信息查询异常 - BDHM=[{}] ACCOUNT=[{}]", cxqq.getBdhm(), subAccountNumber, e);
							}
						}
					}
				}
			}
			
			// 3.4. 查询司法强制措施信息（在先冻结信息）
			List<AccountFrozenMeasure> frozenMeasureList = prefixMessageService.queryAccountFrozenMeasures(accountNumber);
			if (frozenMeasureList != null && frozenMeasureList.size() > 0) {
				int fmxh = 1;
				for (AccountFrozenMeasure accountFrozenMeasure : frozenMeasureList) {
					if ("2".equals(accountFrozenMeasure.getFrozenInsType())) {
						continue; // 根据业务要求，银行内部冻结信息不反馈给最高法
					}
					djxxList.add(copyQueryRequestDjxx(accountFrozenMeasure, accountNumber, acxh, fmxh++));
				}
			}
			
			// 3.5. 查询资金往来（交易）信息
			String startDate = cxqq.getCkkssj();
			String endDate = cxqq.getCkjssj();
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				try {
					startDate = Utility.toDate10(startDate);
					endDate = Utility.toDate10(endDate);
				} catch (Exception e) {
					logger.error("交易流水查询时间格式转换失败 - [{}][{}]", startDate, endDate, e);
				}
				List<AccountTransaction> transactionList = prefixMessageService.queryAccountTransaction(accountNumber, startDate, endDate);
				if (transactionList != null && transactionList.size() > 0) {
					int trxh = 1;
					for (AccountTransaction accountTransaction : transactionList) {
						wlxxList.add(copyQueryRequestWlxx(accountTransaction, accountNumber, acxh, trxh++));
					}
				}
			}
		}
		
		// 4. 查询金融资产信息（通过在线交易平台）
		try {
			List<OnlineTPFinancialDetail> financialDetailList = soapMessageService.queryFinancialFromOnlineTP(idType, idNumber, customerName);
			if (financialDetailList != null && financialDetailList.size() > 0) {
				int fdxh = 1;
				for (OnlineTPFinancialDetail financialDetail : financialDetailList) {
					String[] acc = amap.get(financialDetail.getAccountNumber());
					if (acc == null || acc.length != 2) {
						continue;
					}
					jrxxList.add(copyQueryRequestJrxx(financialDetail, acc[0], acc[1], fdxh++));
				}
			}
		} catch (DataOperateException e) {
			if ("001002".equals(e.getCode()) || "001004".equals(e.getCode())) { // 客户不存在
				if (logger.isDebugEnabled()) {
					logger.debug("未查询到金融资产信息 - 证件类型=[{}], 证件号码=[{}], 客户名称=[{}]", idType, idNumber, customerName);
				}
			} else {
				logger.error("金融资产信息查询异常 - [{}]{}", e.getCode(), e.getMessage(), e);
			}
		}
		
		requestObj.setZhxxList(zhxxList);
		requestObj.setGlxxList(glxxList);
		requestObj.setDjxxList(djxxList);
		requestObj.setWlxxList(wlxxList);
		requestObj.setJrxxList(jrxxList);
		requestObj.setQlxxList(qlxxList);
		
		return requestObj;
	}
	
	@Override
	public List<QueryRequest_Djxx> getDjxxList(ControlResponse_Kzzh kzqq) throws RemoteAccessException, DataOperateException {
		List<QueryRequest_Djxx> queryRequestDjxxList = new ArrayList<QueryRequest_Djxx>();
		String accountNumber = kzqq.getKhzh();
		String subAccountNumber = kzqq.getGlzhhm();
		
		String realAccountNumber = accountNumber;
		String subAccountSeq = null;
		if (StringUtils.startsWith(subAccountNumber, "V")) {
			realAccountNumber = subAccountNumber;
		} else if (StringUtils.isNotBlank(subAccountNumber)) { // 兼容原中间业务平台反馈的账户序号
			subAccountSeq = subAccountNumber;
		}
		
		if ("1".equals(kzqq.getKzlx())) { // 存款类冻信息查询
			try {
				Input267570 in = new Input267570();
				in.setAccountNumber(realAccountNumber);
				in.setSubAccountSeq(subAccountSeq);
				List<AccountFrozenMeasure> accountFrozenMeasureList = prefixMessageService.queryAccountFrozenMeasures(in);
				int csxh = 1;
				for (AccountFrozenMeasure accountFrozenMeasure : accountFrozenMeasureList) {
					if ("2".equals(accountFrozenMeasure.getFrozenInsType())) {
						continue; // 根据业务要求，银行内部冻结信息不反馈给最高法
					}
					queryRequestDjxxList.add(copyQueryRequestDjxx(accountFrozenMeasure, kzqq.getKhzh(), kzqq.getCcxh(), csxh++));
				}
			} catch (Exception e) {
				logger.error("Exception: 查询存款类在先冻结信息异常 - BDHM=[{}] ACCOUNT=[{}]", kzqq.getBdhm(), realAccountNumber, e);
			}
		} else { // 非存款类查询 金融理财查询
			try {
				FinancialFrozenMeasuresInput in = new FinancialFrozenMeasuresInput();
				in.setBranchNumber(ServerEnvironment.getStringValue(Keys.GF_FREEZE_BRANCH));
				in.setFlagNumber(realAccountNumber);
				List<FinancialFrozenMeasure> frozenMeasureList = soapMessageService.queryFinancialFrozenMeasures(in);
				int fsxh = 1;
				for (FinancialFrozenMeasure frozenMeasure : frozenMeasureList) {
					queryRequestDjxxList.add(copyQueryRequestDjxx(frozenMeasure, kzqq.getKhzh(), 1, fsxh));
					fsxh++;
				}
			} catch (Exception e) {
				logger.error("Exception: 查询金融理财产品在先冻结信息异常 - ACCOUNT=[{}]", kzqq.getKhzh(), e);
			}
		}
		return queryRequestDjxxList;
	}
	
	@Override
	public List<QueryRequest_Qlxx> getQlxxList(ControlResponse_Kzzh kzqq) throws RemoteAccessException, DataOperateException {
		String accountNumber = kzqq.getKhzh();
		String subAccountNumber = kzqq.getGlzhhm();
		
		String realAccountNumber = accountNumber;
		String subAccountSeq = null;
		if (StringUtils.startsWith(subAccountNumber, "V")) {
			realAccountNumber = subAccountNumber;
		} else if (StringUtils.isNotBlank(subAccountNumber)) { // 兼容原中间业务平台反馈的账户序号
			subAccountSeq = subAccountNumber;
		}
		
		List<QueryRequest_Qlxx> qlxxList = new ArrayList<QueryRequest_Qlxx>();
		AccountVerifyInfo account = prefixMessageService.queryAccountVerifyInfo(realAccountNumber, kzqq.getBz(), null);
		if ("M".equals(account.getAccountType()) || "9".equals(account.getDepositPeriodFlag()) || "Y".equals(account.getDeposiPledgeFlag())) {
			qlxxList.add(copyQueryRequestQlxx(accountNumber, 1, 1, account));
		}
		return qlxxList;
	}
	
	@Override
	public ControlRequest_Kzxx invokeFreezeAccount(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException {
		ControlRequest_Kzxx kzxx = null;
		String kzlx = kzqq.getKzlx(); // 控制类型（1-存款；2-非存款类金融资产）
		String kzcs = kzqq.getKzcs(); // 控制措施（01-冻结；02-继续冻结；04-解冻；06-划拨）
		
		String accountNumber = kzqq.getKhzh();
		String subAccountNumber = kzqq.getGlzhhm();
		
		String realAccountNumber = accountNumber;
		if (StringUtils.startsWith(subAccountNumber, "V")) {
			realAccountNumber = subAccountNumber;
		}
		
		if (realAccountNumber == null || realAccountNumber.length() == 0) {
			throw new DataOperateException("无申请控制账号，协助执行单位业务审核未通过");
		}
		
		// 对公主账户 定期  358080无法获取币种与钞汇 现在改为358040 在对公的时候 卡与实体记账卡是
		//一一对应，故在对公账户时，输入默认序号 1
		Input358040 in = new Input358040();
		in.setAccountNumber(realAccountNumber); // 申请控制的账号/子账户
		in.setCurrency(kzqq.getBz());
		if ("20600".equals(kzqq.getZjlx())) {
			in.setAccountSeq("1");
		}
		
		AccountVerifyInfo accountInfo = prefixMessageService.queryAccountVerifyInfo(in);
		if (accountInfo == null) {
			throw new DataOperateException("查无账户信息，协助执行单位业务审核未通过");
		}
		
		String idNumber = StringUtils.replace(accountInfo.getIDNumber(), "-", "");
		if (!StringUtils.equals(StringUtils.replace(kzqq.getDsrzjhm(), "-", ""), idNumber)) {
			throw new DataOperateException("账号与被控制人证件信息不匹配，协助执行单位业务审核未通过");
		} else if (!StringUtils.equals(kzqq.getXm(), accountInfo.getCustomerName())) {
			throw new DataOperateException("账号与被控制人姓名不匹配，协助执行单位业务审核未通过");
		}
		
		// 控制措施：01-冻结；02-继续冻结；04-解除冻结
		boolean isKzlx2 = "2".equals(kzlx); // 是否金融资产类控制
		if ("01".equals(kzcs)) {
			kzxx = processFreezeAccount(kzqq, accountInfo);
			if (isKzlx2) {
				try {
					if (kzxx == null) {
						kzxx = new ControlRequest_Kzxx();
					}
					ControlRequest_Kzxx kzxxFinance = processFreezeFinancial(kzqq);
					kzxx.setSkse(kzxxFinance.getSkse());
					kzxx.setLcappid(kzxxFinance.getLcappid()); // 冻结编号
				} catch (Exception e) {
					logger.warn("Exception: 成功冻结资金回款账户，但理财产品冻结失败 - BDHM=[{}]", kzqq.getBdhm(), e);
					kzxx.setWnkzyy("成功冻结资金回款账户，但理财产品冻结失败");
				}
			}
		} else if ("02".equals(kzcs)) {
			kzxx = processDeferFreezeAccount(kzqq);
			if (isKzlx2) {
				try {
					if (kzxx == null) {
						kzxx = new ControlRequest_Kzxx();
					}
					ControlRequest_Kzxx kzxxFinance = processDeferFreezeFinancial(kzqq);
					kzxx.setLcappid(kzxxFinance.getLcappid());
					kzxx.setSkse(kzxxFinance.getSkse()); // 续冻金额
				} catch (Exception e) {
					logger.warn("Exception: 成功续冻资金回款账户，但理财产品续冻失败 - BDHM=[{}]", kzqq.getBdhm(), e);
					kzxx.setWnkzyy("成功续冻资金回款账户，但理财产品续冻失败");
				}
			}
		} else if ("04".equals(kzcs)) {
			kzxx = processUnfreezeAccount(kzqq);
			if (isKzlx2) {
				try {
					if (kzxx == null) {
						kzxx = new ControlRequest_Kzxx();
					}
					ControlRequest_Kzxx kzxxFinance = processUnfreezeFinancial(kzqq);
					kzxx.setHxappid(kzxxFinance.getLcappid()); // 冻结编号
					kzxx.setSkse(kzxxFinance.getSkse()); // 解冻金额
				} catch (Exception e) {
					logger.warn("Exception: 成功解冻资金回款账户，但理财产品解冻失败 - BDHM=[{}]", kzqq.getBdhm(), e);
					kzxx.setWnkzyy("成功解冻资金回款账户，但理财产品解冻失败");
				}
			}
		} else {
			throw new DataOperateException("控制措施必须是[01]冻结、[02]续冻、[04]解冻，不支持[" + kzcs + "]");
		}
		
		// 开户行所信息
		kzxx.setKhhsh(accountInfo.getOpenBranch());
		kzxx.setKhhfhh(accountInfo.getOpenBank());
		return kzxx;
	}
	
	private ControlRequest_Kzxx processFreezeFinancial(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException {
		String gzzbh = kzqq.getGzzbh();
		String gwzbh = kzqq.getGwzbh();
		if (gzzbh == null || gzzbh.length() == 0) {
			gzzbh = gwzbh;
		} else if (gwzbh == null || gwzbh.length() == 0) {
			gwzbh = gzzbh;
		}
		
		String accountNumber = kzqq.getKhzh();
		String subAccountNumber = kzqq.getGlzhhm();
		
		String realAccountNumber = accountNumber;
		if (StringUtils.startsWith(subAccountNumber, "V")) {
			realAccountNumber = subAccountNumber;
		}
		
		FinancialFreezeInput in = new FinancialFreezeInput();
		in.setBranchNumber(ServerEnvironment.getStringValue(Keys.GF_FREEZE_BRANCH));
		in.setAccountNumber(realAccountNumber);
		in.setProductCode(kzqq.getJrcpbh());
		in.setVolume(kzqq.getSe());
		in.setLawNumber(kzqq.getBdhm());
		in.setOrganName(kzqq.getFymc());
		try {
			in.setFreezeEndDate(Utility.toDate8(kzqq.getJsrq()));
		} catch (Exception e) {
		}
		
		in.setFlagType("2"); // 客户标识
		in.setIdType(kzqq.getZjlx()); // 证件类型
		in.setFlagNumber(kzqq.getDsrzjhm()); // 证件号
		in.setLawName1(kzqq.getCbr());
		in.setLawIdCode1(gzzbh);
		in.setLawName2(kzqq.getCbr());
		in.setLawIdCode2(gwzbh);
		
		FinancialFreezeResult result = soapMessageService.invokeFreezeFinancial(in);
		return copyFinancialFreezeResult(result);
	}
	
	private ControlRequest_Kzxx processDeferFreezeFinancial(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException {
		String gzzbh = kzqq.getGzzbh();
		String gwzbh = kzqq.getGwzbh();
		if (gzzbh == null || gzzbh.length() == 0) {
			gzzbh = gwzbh;
		} else if (gwzbh == null || gwzbh.length() == 0) {
			gwzbh = gzzbh;
		}
		
		String accountNumber = kzqq.getKhzh();
		String subAccountNumber = kzqq.getGlzhhm();
		
		String realAccountNumber = accountNumber;
		if (StringUtils.startsWith(subAccountNumber, "V")) {
			realAccountNumber = subAccountNumber;
		}
		
		FinancialDeferFreezeInput in = new FinancialDeferFreezeInput();
		in.setBranchNumber(ServerEnvironment.getStringValue(Keys.GF_FREEZE_BRANCH));
		in.setAccountNumber(realAccountNumber);
		in.setIdType(kzqq.getZjlx()); // 证件类型
		in.setAccount(kzqq.getDsrzjhm()); // 证件号
		in.setAccType("2");
		in.setAssoSerial(kzqq.getLcappid());
		in.setLawNo(kzqq.getBdhm());
		in.setOrgName(kzqq.getFymc());
		try {
			in.setEndDate(Utility.toDate8(kzqq.getJsrq()));
		} catch (Exception e) {
		}
		in.setFrozenName1(kzqq.getCbr());
		in.setFrozenIdCode1(gzzbh);
		in.setFrozenName2(kzqq.getCbr());
		in.setFrozenIdCode2(gwzbh);
		
		FinancialDeferFreezeResult result = soapMessageService.invokeDeferFreezeFinancial(in);
		return copyFinancialDeferFreezeResult(result);
	}
	
	private ControlRequest_Kzxx processUnfreezeFinancial(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException {
		String gzzbh = kzqq.getGzzbh();
		String gwzbh = kzqq.getGwzbh();
		if (gzzbh == null || gzzbh.length() == 0) {
			gzzbh = gwzbh;
		} else if (gwzbh == null || gwzbh.length() == 0) {
			gwzbh = gzzbh;
		}
		
		String accountNumber = kzqq.getKhzh();
		String subAccountNumber = kzqq.getGlzhhm();
		
		String realAccountNumber = accountNumber;
		if (StringUtils.startsWith(subAccountNumber, "V")) {
			realAccountNumber = subAccountNumber;
		}
		
		FinancialUnfreezeInput in = new FinancialUnfreezeInput();
		in.setBranchNumber(ServerEnvironment.getStringValue(Keys.GF_FREEZE_BRANCH)); // 开户行所
		in.setAccountNumber(realAccountNumber); // 账号
		in.setPrdCode(kzqq.getJrcpbh()); // 理财产品
		in.setAssoSerial(kzqq.getLcappid()); // 原冻结流水号
		in.setLawNo(kzqq.getYdjdh());
		in.setOrgName(kzqq.getFymc());
		in.setIdType(kzqq.getZjlx()); // 证件类型
		in.setAccount(kzqq.getDsrzjhm()); // 证件号
		in.setAccType("2"); // AccType=1，填核心客户号 ,AccType=2，填证件号码
		in.setVolume(kzqq.getSe()); // 份额
		in.setFrozenName1(kzqq.getCbr());
		in.setFrozenIdCode1(gzzbh);
		in.setFrozenName2(kzqq.getCbr());
		in.setFrozenIdCode2(gwzbh);
		
		FinancialUnfreezeResult result = soapMessageService.invokeUnfreezeFinancial(in);
		return copyFinancialUnfreezeResult(result);
	}
	
	protected ControlRequest_Kzxx processFreezeAccount(ControlResponse_Kzzh kzqq, AccountVerifyInfo accountInfo) throws DataOperateException, RemoteAccessException {
		Input267500 in = new Input267500();
		String gzzbh = kzqq.getGzzbh();
		String gwzbh = kzqq.getGwzbh();
		if (gzzbh == null || gzzbh.length() == 0) {
			gzzbh = gwzbh;
		} else if (gwzbh == null || gwzbh.length() == 0) {
			gwzbh = gzzbh;
		}
		
		String freezeType = kzqq.getKznr();
		// 转换冻结类型
		if ("1".equals(freezeType)) { // 账户资金冻结
			in.setFreezeType("2");
		} else if ("2".equals(freezeType)) { // 账户冻结
			in.setFreezeType("1");
		}
		
		String accountNumber = kzqq.getKhzh();
		String subAccountNumber = kzqq.getGlzhhm();
		
		String realAccountNumber = accountNumber;
		if (StringUtils.startsWith(subAccountNumber, "V")) {
			realAccountNumber = subAccountNumber;
		} else if (StringUtils.isNotBlank(subAccountNumber)) {
			in.setAccountSeq(subAccountNumber); // 兼容原中间业务平台返回的账户序号
		}
		
		in.setAccountNumber(realAccountNumber);
		in.setFreezeAmount(kzqq.getJe());
		in.setEffectiveDate(kzqq.getKsrq()); // 申请控制开始时间
		in.setExpiringDate(kzqq.getJsrq()); // 申请控制结束时间
		in.setLawName1(kzqq.getCbr());
		in.setLawIDNumber1(gzzbh);
		in.setLawName2(kzqq.getCbr());
		in.setLawIDNumber2(gwzbh);
		in.setFreezeBookNumber(kzqq.getBdhm()); //
		in.setFreezeBranch(ServerEnvironment.getStringValue(Keys.GF_FREEZE_BRANCH));
		in.setFreezeInsName(kzqq.getFymc()); // 冻结机构名称
		in.setCurrency(accountInfo.getCurrency());
		in.setCashExCode(accountInfo.getCashExCode());
		
		FreezeResult freezeResult = prefixMessageService.freezeAccount(in);
		
		return copyFreezeResultProperties(freezeResult);
	}
	
	private ControlRequest_Kzxx processUnfreezeAccount(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException {
		String gzzbh = kzqq.getGzzbh();
		String gwzbh = kzqq.getGwzbh();
		if (gzzbh == null || gzzbh.length() == 0) {
			gzzbh = gwzbh;
		} else if (gwzbh == null || gwzbh.length() == 0) {
			gwzbh = gzzbh;
		}
		
		Input267510 in = new Input267510();
		in.setFrozenNumber(kzqq.getHxappid()); // 原冻结编号
		in.setUnfreezeInsName(kzqq.getFymc()); // 机构代码
		in.setLawIDNumber1(gzzbh);
		in.setLawName1(kzqq.getCbr()); // 承办法官
		in.setLawIDNumber2(gwzbh);
		in.setLawName2(kzqq.getCbr()); // 承办法官
		in.setUnfreezeBookNumber(kzqq.getBdhm()); // 裁定书文号
		in.setUnfreezeBranch(ServerEnvironment.getStringValue(Keys.GF_FREEZE_BRANCH));
		UnfreezeResult result = prefixMessageService.unfreezeAccount(in);
		return copyControlRequestUnFreezeResult(result);
	}
	
	private ControlRequest_Kzxx processDeferFreezeAccount(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException {
		String gzzbh = kzqq.getGzzbh();
		String gwzbh = kzqq.getGwzbh();
		if (gzzbh == null || gzzbh.length() == 0) {
			gzzbh = gwzbh;
		} else if (gwzbh == null || gwzbh.length() == 0) {
			gwzbh = gzzbh;
		}
		
		Input267540 in = new Input267540(); // 冻结延期输入项
		in.setFreezeInsName(kzqq.getFymc()); // 冻结机构
		in.setFrozenNumber(kzqq.getHxappid()); // 原冻结编号
		in.setExpiringDate(kzqq.getJsrq()); // 申请控制结束时间
		in.setLawName1(kzqq.getCbr()); // 承办法官
		in.setLawIDNumber1(gzzbh); // 承办法官工作证编号 或公务证
		in.setLawName2(kzqq.getCbr()); // 承办法官
		in.setLawIDNumber2(gwzbh); // 承办法官工作证编号 或公务证
		in.setFreezeBookNumber(kzqq.getBdhm()); // 裁定书文号
		in.setFreezeBranch(ServerEnvironment.getStringValue(Keys.GF_FREEZE_BRANCH));
		DeferFreezeResult deferFreezeResult = prefixMessageService.deferFreezeAccount(in);
		
		return copyControlRequestDeferFreezeResult(deferFreezeResult);
	}
	
	@Override
	public ControlRequest_Kzxx invokeDeductFunds(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException {
		ControlRequest_Kzxx kzxx = new ControlRequest_Kzxx();
		
		String accountNumber = kzqq.getKhzh(); // 申请控制的账户
		String subAccountNumber = kzqq.getGlzhhm(); // 申请控制的子账户
		
		String realAccountNumber = accountNumber;
		String accountSerial = null;
		if (StringUtils.startsWith(subAccountNumber, "V")) {
			realAccountNumber = subAccountNumber;
		} else if (StringUtils.isNotBlank(subAccountNumber)) {
			accountSerial = subAccountNumber; // 兼容原中间业务平台反馈的账户序号
		}
		
		if (realAccountNumber == null || realAccountNumber.length() == 0) {
			throw new DataOperateException("无申请控制账号，协助执行单位业务审核未通过");
		}
		
		String status = kzqq.getStatus(); // 任务处理阶段标识（H-待执行（线上处理）；S-补录（线下处理）；R-拒绝请求；）
		if ("H".equals(status)) {
			String freezeBranch = ServerEnvironment.getStringValue(Keys.GF_FREEZE_BRANCH); // 发起行所号
			String insAccount = ServerEnvironment.getStringValue(Keys.GF_DEDUCT_INSACCOUNT); // 内部户账号
			String insAccountName = ServerEnvironment.getStringValue(Keys.GF_DEDUCT_INSACCOUNT_NAME); // 内部户户名
			
			// 根据358080接口查询内部户户名。生产上出现过内部户户名更改的情况，采用固定默认值存在一定的风险。
			insAccountName = getFactInsAccountName(insAccount, insAccountName);
			
			// 判断执行款专户账号（收款方）是否本行账户
			boolean isInnerCredit = isInsAccount(kzqq.getBdhm(), kzqq.getZxkzhzh(), kzqq.getZxkzhhm());
			
			// 调用核心接口，将资金从被控制账户划转到内部户或执行款账户
			DeductResult deductResult = dealDeductFunds(realAccountNumber, accountSerial, freezeBranch, isInnerCredit, insAccount, kzqq);
			
			// 扣划金额
			kzxx.setSkje(Utility.precisionOfCent(deductResult.getDeductAmount()));
			
			if (isInnerCredit) { // 本行（收款方）
				kzxx.setStatus("O"); // O-成功
			} else {
				// 当执行款账户是行外是，执行行外扣划 
				CargoRecordResult record = dealExternalTransferInput(freezeBranch, deductResult.getAccountNumber(), deductResult.getCreditNumber(), insAccount, insAccountName, kzqq);
				kzxx.setWtrq(record.getPayPathWkDate()); // 委托日期
				kzxx.setJylsh(record.getBusiSysSerno()); // 交易流水号
				kzxx.setStatus("C"); // C-成功（未核实）
			}
			
			try {
				// 查询账户可用余额和账户余额
				AccountDetail deductAccDetail = prefixMessageService.queryAccountDetail(realAccountNumber, kzqq.getBz(), null);
				kzxx.setYe(deductAccDetail.getAccountBalance()); // 账户余额
				kzxx.setKyye(deductAccDetail.getAvailableBalance()); // 可用余额
			} catch (Exception e) {
				logger.error("Exception: 账户余额查询失败 - BDHM=[{}]", kzqq.getBdhm(), e);
			}
		} else if ("S".equals(status)) {
			try {
				// 查询账户可用余额和账户余额
				AccountDetail deductAccDetail = prefixMessageService.queryAccountDetail(realAccountNumber, kzqq.getBz(), null);
				kzxx.setYe(deductAccDetail.getAccountBalance()); // 账户余额
				kzxx.setKyye(deductAccDetail.getAvailableBalance()); // 可用余额
			} catch (Exception e) {
				logger.error("Exception: 账户余额查询失败 - BDHM=[{}]", kzqq.getBdhm(), e);
			}
			
			kzxx.setStatus(status);
		} else if ("R".equals(status)) {
			kzxx.setWnkzyy(kzxx.getJjyy());
			kzxx.setStatus(status);
		} else {
			// 账户校验
			AccountDetail accountDetail = validateAccount(realAccountNumber, kzqq.getDsrzjhm(), kzqq.getXm());
			
			kzxx.setStatus("N"); // N-未处理
			kzxx.setZhlx(accountDetail.getAccountAttr()); // 账户类型（11-活期；12-定期；26-借记卡；01-内部户）\
			kzxx.setKhhsh(accountDetail.getAccountOpenBranch());
			kzxx.setKhhfhh(accountDetail.getAccountOpenBank());
		}
		
		return kzxx;
	}
	
	@Override
	public void invokeSendMessage(List<BB13_organ_telno> telOrganList, String organKey, String kzcs) throws Exception {
		// Do nothing
	}
	
	private QueryRequest_Zhxx copyQueryRequestZhxx(AccountDetail accountDetail, int xh, String mailingAddress, String telephoneNumber) {
		if (accountDetail == null) {
			return null;
		}
		
		QueryRequest_Zhxx zhxx = new QueryRequest_Zhxx();
		zhxx.setCcxh(String.valueOf(xh)); // 账户序号
		zhxx.setKhzh(accountDetail.getAccountNumber()); // 开户账号
		zhxx.setCclb(accountDetail.getAccountAttr()); // 账户类别
		zhxx.setZhzt(accountDetail.getAccountStatus()); // 账户状态
		zhxx.setKhwd(accountDetail.getAccountOpenBranch());// 开户网点
		zhxx.setKhwddm(accountDetail.getAccountOpenBranch());// 开户网点代码
		zhxx.setKhrq(accountDetail.getCardOpenDate());// 开户日期
		zhxx.setXhrq(accountDetail.getAccountClosingDate());// 销户日期
		if (accountDetail.getCurrency() == null || accountDetail.getCurrency().length() == 0) {
			if (!(accountDetail.getAccountBalance() == null || accountDetail.getAccountBalance().length() == 0)) {
				zhxx.setBz("156"); // 账户余额不为空时，币种默认为人民币
			}
		} else {
			zhxx.setBz(accountDetail.getCurrency()); // 计价币种
		}
		zhxx.setYe(accountDetail.getAccountBalance()); // 资产数额 - 账户余额
		zhxx.setKyye(accountDetail.getAvailableBalance()); // 可用资产数额 - 可用余额
		zhxx.setGlzjzh(accountDetail.getDefaultSubAccount()); // 关联资金账户 - 默认实体账号
		zhxx.setBeiz(accountDetail.getAccountClass());// 备注 - 账户分类（1-Ⅰ类账户；2-Ⅱ类账户；3-Ⅲ类账户）
		zhxx.setTxdz(mailingAddress); // 通讯地址
		zhxx.setLxdh(telephoneNumber); // 联系电话
		return zhxx;
	}
	
	private QueryRequest_Glxx copyQueryRequestGlxx(AccountDetail accountDetail, String cardNumber, int xh, int glxh, String accountSerial) {
		if (accountDetail == null) {
			return null;
		}
		
		QueryRequest_Glxx glxx = new QueryRequest_Glxx();
		glxx.setCcxh(String.valueOf(xh)); // 账户序号
		glxx.setGlxh(String.valueOf(glxh)); // 子账户序号
		glxx.setZkhzh(cardNumber); // 主账户账号
		glxx.setGlzhlb(accountDetail.getAccountAttr()); // 子账户类别
		glxx.setGlzhhm(accountDetail.getV_AccountNumber()); // 子账户账号
		glxx.setBz(accountDetail.getCurrency()); // 计价币种
		glxx.setYe(accountDetail.getAccountBalance()); // 资产数额
		glxx.setKyye(accountDetail.getAvailableBalance()); // 可用资产数额
		glxx.setBeiz(accountDetail.getCashExCode()); // 备注 - 钞汇标识
		return glxx;
	}
	
	private QueryRequest_Djxx copyQueryRequestDjxx(AccountFrozenMeasure frozenMeasure, String accountNumber, String xh, int csxh) {
		if (frozenMeasure == null) {
			return null;
		}
		
		QueryRequest_Djxx djxx = new QueryRequest_Djxx();
		djxx.setCcxh(xh);
		djxx.setCsxh(String.valueOf(csxh));// 措施序号
		djxx.setDjjzrq(frozenMeasure.getExpiringDate());// 冻结截止日
		djxx.setDjjg(frozenMeasure.getFrozenInsName());// 冻结机关
		djxx.setDjwh(frozenMeasure.getFrozenBookNumber());// 冻结文号
		djxx.setDjje(frozenMeasure.getFrozenAmount());// 冻结金额/数额
		djxx.setBeiz(frozenMeasure.getFrozenType()); // 备注 - 冻结方式（1-账户冻结；2-金额冻结；3-圈存；4-受托支付）
		djxx.setKhzh(accountNumber); // 开户账号
		djxx.setHxappid(frozenMeasure.getFrozenNumber()); // 冻结编号
		return djxx;
	}
	
	private QueryRequest_Djxx copyQueryRequestDjxx(AccountFrozenMeasure frozenMeasure, String accountNumber, int xh, int csxh) {
		return copyQueryRequestDjxx(frozenMeasure, accountNumber, String.valueOf(xh), csxh);
	}
	
	private QueryRequest_Wlxx copyQueryRequestWlxx(AccountTransaction transaction, String khzh, int xh, int wlxh) {
		if (transaction == null) {
			return null;
		}
		
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
		
		QueryRequest_Wlxx wlxx = new QueryRequest_Wlxx();
		wlxx.setCcxh(String.valueOf(xh)); // 账户序号
		wlxx.setWlxh(String.valueOf(wlxh)); // 资金往来序号
		wlxx.setKhzh(khzh);
		// wlxx.setKhzh(transaction.getV_AccountNumber()); // 账号 - 组合子账号
		wlxx.setJyzl(transaction.getTradeChannel());// 交易种类 - 交易类型/交易操作（交易渠道）
		wlxx.setZjlx(transaction.getDrcrFlag()); // 借贷方向（资金流向）
		wlxx.setBz(transaction.getTradeCurrency()); // 交易币种
		wlxx.setJe(transaction.getTradeAmount()); // 交易金额
		wlxx.setJysj(transaction.getTradeTime()); // 交易日期
		wlxx.setJylsh(transactionSerial); // 交易流水号
		wlxx.setZckzxm(transaction.getRelativeName()); // 交易对方姓名/名称
		wlxx.setZckzh(transaction.getRelativeNumber()); // 交易对方账号
		wlxx.setZckzhkhh(""); // 交易对方账号开户行
		wlxx.setJydsyhhh(""); // 交易对方账号开户行行号
		wlxx.setJydszjlx(""); // 交易对方开户证件类型
		wlxx.setJydszjhm(""); // 交易对方开户证件号码
		wlxx.setJydstxdz(""); // 交易对方通讯地址
		wlxx.setJydsyzbm(""); // 交易对方邮政编码
		wlxx.setJydslxdh(""); // 交易对方联系电话
		wlxx.setBeiz(transaction.getRelativeNumber()); // 备注 - 交易对手卡号
		return wlxx;
	}
	
	private QueryRequest_Jrxx copyQueryRequestJrxx(OnlineTPFinancialDetail financialDetail, String khzh, String ccxh, int zcxh) {
		if (financialDetail == null) {
			return null;
		}
		
		QueryRequest_Jrxx jrxx = new QueryRequest_Jrxx();
		jrxx.setKhzh(khzh); // 开户账号
		jrxx.setCcxh(ccxh); // 账户序号
		jrxx.setZcxh(String.valueOf(zcxh)); // 金融资产序号
		jrxx.setZcmc(financialDetail.getProductName()); // 金融资产名称
		jrxx.setZclx(financialDetail.getProductType()); // 金融资产类型（M101、M102、... 、M601）
		jrxx.setCpxszl(financialDetail.getSaleType()); // 产品销售种类（00-直销、10-代销）
		jrxx.setDqh(""); // 地区号
		jrxx.setJrcpbh(financialDetail.getProductCode()); // 金融产品编号
		jrxx.setLczh(financialDetail.getFinancialNumber()); // 理财账号
		jrxx.setZjhkzh(financialDetail.getAccountNumber()); // 资金回款账户
		jrxx.setZcglr(""); // 资产管理人
		jrxx.setZckfjy("00".equals(financialDetail.getSaleType()) ? "是" : ""); // 资产可否通过银行交易
		jrxx.setZcjyxz(""); // 资产交易限制类型
		jrxx.setXzxcrq(""); // 资产交易限制消除时间
		jrxx.setZyqr(""); // 质押权人（直销产品时反馈）
		jrxx.setTgr(""); // 托管人 （代销产品时需反馈）
		jrxx.setSyr(""); // 受益人 （代销产品时需反馈）
		jrxx.setClr(""); // 成立日 （代销产品时需反馈）
		jrxx.setShr(""); // 赎回日 （代销产品时需反馈）
		jrxx.setTgzh(""); // 托管账号 （代销产品时需反馈）
		jrxx.setJldw(financialDetail.getProductType()); // 计量单位（根据资产类型决定）（转码）
		jrxx.setBz(financialDetail.getCurrency()); // 计价币种
		jrxx.setZcdwjg(""); // 资产单位价格
		jrxx.setSe(financialDetail.getAmount()); // 数量/份额/金额
		jrxx.setKyse(financialDetail.getBalance()); // 可控数量/份额/金额 （仅限基金类）
		jrxx.setZczje(financialDetail.getAmount()); // 资产总数额
		jrxx.setBeiz(financialDetail.getMark()); // 备注
		
		return jrxx;
	}
	
	private QueryRequest_Qlxx copyQueryRequestQlxx(String accountNumber, int ccxh, int xh, AccountDetail accountDetail) {
		if (accountDetail == null) {
			return null;
		}
		
		QueryRequest_Qlxx qlxx = new QueryRequest_Qlxx();
		qlxx.setCcxh(String.valueOf(ccxh));// 账户序号
		qlxx.setXh(String.valueOf(xh)); // 序号
		qlxx.setZcxh(""); // 金融资产序号
		qlxx.setJrcpbh(""); // 金融产品编号
		qlxx.setQllx("优先受偿权类型"); // 权利类型
		qlxx.setQlr("广发银行股份有限公司"); // 权利人
		qlxx.setQlje(accountDetail.getAccountBalance()); // 权利金额/数额
		qlxx.setQlrdz(""); // 权利人通讯地址
		qlxx.setQlrlxfs(""); // 权利人联系方式
		qlxx.setBeiz(""); // 备注
		qlxx.setKhzh(accountNumber); // 开户账户
		
		return qlxx;
	}
	
	private QueryRequest_Qlxx copyQueryRequestQlxx(String accountNumber, int ccxh, int xh, AccountVerifyInfo account) {
		if (account == null) {
			return null;
		}
		
		QueryRequest_Qlxx qlxx = new QueryRequest_Qlxx();
		qlxx.setCcxh(String.valueOf(ccxh));// 账户序号
		qlxx.setXh(String.valueOf(xh)); // 序号
		qlxx.setZcxh(""); // 金融资产序号
		qlxx.setJrcpbh(""); // 金融产品编号
		qlxx.setQllx("优先受偿权类型"); // 权利类型
		qlxx.setQlr("广发银行股份有限公司"); // 权利人
		qlxx.setQlje(account.getAccountBalance()); // 权利金额/数额
		qlxx.setQlrdz(""); // 权利人通讯地址
		qlxx.setQlrlxfs(""); // 权利人联系方式
		qlxx.setBeiz(""); // 备注
		qlxx.setKhzh(accountNumber); // 开户账户
		
		return qlxx;
	}
	
	private QueryRequest_Djxx copyQueryRequestDjxx(FinancialFrozenMeasure financeTrans, String acctnumber, int xh, int csxh) {
		if (financeTrans == null) {
			return null;
		}
		
		QueryRequest_Djxx djxx = new QueryRequest_Djxx();
		djxx.setCcxh(String.valueOf(xh)); // 账户序号
		djxx.setCsxh(String.valueOf(csxh)); // 措施序号
		djxx.setDjjzrq(financeTrans.getEndDate()); // 冻结截止日
		djxx.setDjjg(financeTrans.getOrgName()); // 冻结机关
		djxx.setDjwh(financeTrans.getLawNo()); // 冻结文号
		djxx.setDjje(financeTrans.getVolume()); // 冻结金额/数额
		djxx.setBeiz(financeTrans.getPrdName()); // 备注 - 产品名称
		djxx.setKhzh(financeTrans.getBankAcc()); // 开户账号
		return djxx;
	}
	
	private ControlRequest_Kzxx copyFreezeResultProperties(FreezeResult freezeResult) {
		if (freezeResult == null) {
			return null;
		}
		
		ControlRequest_Kzxx kzxx = new ControlRequest_Kzxx();
		kzxx.setHxappid(freezeResult.getFrozenNumber()); // 冻结编号
		kzxx.setKhzh(freezeResult.getAccountNumber()); // 账号
		kzxx.setGlzhhm(freezeResult.getAccountSeq()); // 序号
		kzxx.setKznr(freezeResult.getFreezeType()); // 冻结方式
		kzxx.setSkje(freezeResult.getFrozenAmount()); // 冻结金额
		kzxx.setCsksrq(freezeResult.getEffectiveDate()); // 生效日期
		kzxx.setCsjsrq(freezeResult.getExpiringDate()); // 到期日期
		kzxx.setKyye(freezeResult.getAvailableAmount()); // 可用余额
		kzxx.setYe(freezeResult.getAccountBalance()); // 账户余额
		kzxx.setCeskje(freezeResult.getUnfrozenAmount());
		return kzxx;
	}
	
	private ControlRequest_Kzxx copyFinancialUnfreezeResult(FinancialUnfreezeResult result) {
		if (result == null) {
			return null;
		}
		
		ControlRequest_Kzxx kzxx = new ControlRequest_Kzxx();
		kzxx.setHxappid(result.getSerialNo()); // 冻结编号
		kzxx.setSkse(result.getFrozenVol()); // 解冻金额
		return kzxx;
	}
	
	private ControlRequest_Kzxx copyFinancialDeferFreezeResult(FinancialDeferFreezeResult result) {
		ControlRequest_Kzxx kzxx = new ControlRequest_Kzxx();
		kzxx.setLcappid(result.getSerialNo());
		kzxx.setCsjsrq(result.getNewEndDate()); // 到期日期
		kzxx.setSkse(result.getVol()); // 解冻金额
		return kzxx;
	}
	
	private ControlRequest_Kzxx copyControlRequestDeferFreezeResult(DeferFreezeResult deferFreezeResult) {
		if (deferFreezeResult == null) {
			return null;
		}
		ControlRequest_Kzxx kzxx = new ControlRequest_Kzxx();
		kzxx.setHxappid(deferFreezeResult.getFrozenNumber()); // 冻结编号
		kzxx.setKhzh(deferFreezeResult.getAccountNumber()); // 账号
		kzxx.setKznr(deferFreezeResult.getFrozenType()); // 冻结方式
		kzxx.setSkje(deferFreezeResult.getFrozenAmount()); // 冻结金额
		kzxx.setCsksrq(deferFreezeResult.getEffectiveDate()); // 生效日期
		kzxx.setCsjsrq(deferFreezeResult.getNewExpiringDate()); // 到期日期
		kzxx.setKyye(deferFreezeResult.getAvailableAmount()); // 可用余额
		kzxx.setYe(deferFreezeResult.getAccountBalance()); // 账户余额
		return kzxx;
	}
	
	private ControlRequest_Kzxx copyControlRequestUnFreezeResult(UnfreezeResult result) {
		if (result == null) {
			return null;
		}
		
		ControlRequest_Kzxx kzxx = new ControlRequest_Kzxx();
		kzxx.setHxappid(result.getFrozenNumber()); // 冻结编号
		kzxx.setKhzh(result.getAccountNumber()); // 解冻账户
		kzxx.setDjxe(result.getFrozenAmount()); // 原冻结金额
		kzxx.setSkje(result.getUnfreezeAmount()); // 解冻金额
		kzxx.setCsksrq(result.getFrozenEffDate()); // 生效日期
		kzxx.setCsjsrq(result.getFrozenExpDate()); // 到期日期
		kzxx.setKyye(result.getAvailableAmount()); // 可用余额
		kzxx.setYe(result.getAccountBalance()); // 账户余额
		return kzxx;
	}
	
	private ControlRequest_Kzxx copyFinancialFreezeResult(FinancialFreezeResult result) {
		if (result == null) {
			return null;
		}
		
		ControlRequest_Kzxx kzxx = new ControlRequest_Kzxx();
		kzxx.setLcappid(result.getSerialNo()); // 冻结编号
		kzxx.setKhzh(result.getAccount()); // 账户
		kzxx.setKznr("份额"); // 冻结方式
		kzxx.setSkse(result.getRealFrozenVol()); // 实际控制金额
		kzxx.setCsksrq(Utility.currDateTime19()); // 措施始期
		kzxx.setCsjsrq(result.getEndDate()); // 措施终期
		
		return kzxx;
	}
	
	private AccountDetail validateAccount(String realAccountNumber, String dsrzjhm, String xm) throws DataOperateException, RemoteAccessException {
		Input358080 in = new Input358080();
		in.setAccountNumber(realAccountNumber); // 申请控制的账号/子账户
		in.setQueryBalance(false);
		
		AccountDetail accountDetail = prefixMessageService.queryAccountDetail(in);
		if (accountDetail == null) {
			throw new DataOperateException("查无账户信息，协助执行单位业务审核未通过");
		}
		
		String idNumber = StringUtils.replace(accountDetail.getIdNumber(), "-", "");
		if (!StringUtils.equals(StringUtils.replace(dsrzjhm, "-", ""), idNumber)) {
			throw new DataOperateException("账号与被控制人证件信息不匹配，协助执行单位业务审核未通过");
		} else if (!StringUtils.equals(xm, accountDetail.getCustomerName())) {
			throw new DataOperateException("账号与被控制人姓名不匹配，协助执行单位业务审核未通过");
		}
		return accountDetail;
	}
	
	private String getFactInsAccountName(String insAccount, String insAccountName) {
		try {
			Input358080 in = new Input358080();
			in.setQueryBalance(false);
			in.setAccountNumber(insAccount);
			AccountDetail insAccountDetail = prefixMessageService.queryAccountDetail(in);
			if (insAccountDetail == null) {
				logger.warn("查询内部户户名失败，将使用默认值：[{}]", insAccountName);
			} else {
				String accountCNName = insAccountDetail.getAccountCnName();
				if (accountCNName == null || accountCNName.length() == 0) {
					logger.warn("查询内部户户名为空，将使用默认值：[{}]", insAccountName);
				} else {
					insAccountName = accountCNName;
				}
			}
		} catch (Exception e) {
			logger.error("查询内部户户名失败，将使用默认值：[{}]", insAccountName, e);
		}
		return insAccountName;
	}
	
	private CargoRecordResult dealExternalTransferInput(String freezeBranch, String accountNumber, String creditNumber, String insAccount, String insAccountName, ControlResponse_Kzzh kzqq)
		throws DataOperateException {
		ExternalTransferInput up = new ExternalTransferInput();
		up.setBusiSysDate(Utility.currDate8());
		up.setBrno(freezeBranch);
		up.setTellerno(kzqq.getUserid());
		up.setAuthTellerno(kzqq.getCheckuserid());
		up.setZoneno(freezeBranch);
		up.setAmount(Utility.noCent(kzqq.getJe()));
		up.setPayerAcct(accountNumber);
		up.setPayerName(kzqq.getXm());
		up.setSuspSerno(creditNumber);
		up.setRealPayerAcct(insAccount);
		up.setRealPayerName(insAccountName);
		up.setPayeeBank(kzqq.getZxkzhkhhhh());
		up.setPayeeAcct(kzqq.getZxkzhzh());
		up.setPayeeName(kzqq.getZxkzhhm());
		
		try {
			CargoRecordResult record = soapMessageService.invokeExternalTransfer(up);
			if (record == null) {
				throw new DataOperateException("存款账户行外划拨发生未知错误");
			}
			return record;
		} catch (DataOperateException e) {
			throw new DataOperateException("存款账户行外划拨失败：" + e.getMessage());
		} catch (Exception e) {
			throw new DataOperateException("存款账户行外划拨失败");
		}
	}
	
	private DeductResult dealDeductFunds(String realAccountNumber, String accountSerial, String freezeBranch, boolean isInnerCredit, String insAccount, ControlResponse_Kzzh kzqq)
		throws DataOperateException {
		Input267580 in = new Input267580();
		in.setAccountNumber(realAccountNumber); //
		// 这里暂不考虑单层账户的情况：根据接口判断是否单层账号，如果是单层账号须不传序号。
		in.setAccountSerial(accountSerial);
		in.setFrozenNumber(kzqq.getHxappid()); // 冻结编号
		in.setCurrency(kzqq.getBz()); // 币种
		in.setDeductAmount(Utility.noCent(kzqq.getJe())); // 扣划金额
		in.setFreezeBookNumber(kzqq.getBdhm()); // 变动文书号
		in.setFreezeInsName(kzqq.getFymc()); // 冻结机构名称
		in.setFreezeBranch(freezeBranch); // 发起行所
		
		String lawIDNumber = kzqq.getGzzbh();
		if (lawIDNumber == null || lawIDNumber.length() == 0) {
			lawIDNumber = kzqq.getGwzbh();
		}
		try {
			if (lawIDNumber.getBytes("GBK").length > 15) {
				throw new DataOperateException("承办法官证件号码超长，请业务人员线下处理");
			}
		} catch (Exception e) {
			throw new DataOperateException("承办法官证件号码校验长度异常：{}", e.getMessage());
		}
		
		in.setLawIDNumber1(lawIDNumber);
		in.setLawIDNumber2(lawIDNumber);
		
		in.setLawName1(kzqq.getCbr());
		in.setLawName2(kzqq.getCbr());
		in.setInterestAccount(kzqq.getSxzh()); // 收息账户
		in.setRivalAccount(kzqq.getZxkzhzh()); // 对手账号
		
		if (isInnerCredit) {
			in.setBillingAccount(kzqq.getZxkzhzh()); // 入账账户
			in.setMoneyFlow("1"); // 资金流向（1-行内转账；2-他行汇款）
		} else {
			in.setBillingAccount(insAccount); // 入账账户
			in.setMoneyFlow("2"); // 资金流向（1-行内转账；2-他行汇款）
			in.setRivalAccountName(kzqq.getZxkzhhm()); // 对手账号户名
			in.setRivalBankNumber(kzqq.getZxkzhkhhhh()); // 对手账号行号
		}
		
		DeductResult deductResult = null;
		try {
			deductResult = prefixMessageService.deductAccount(in);
			if (deductResult == null) {
				throw new DataOperateException("存款账户扣划发生未知错误");
			}
		} catch (DataOperateException e) {
			throw new DataOperateException("存款账户扣划失败：{}", e.getMessage());
		} catch (Exception e) {
			throw new DataOperateException("存款账户扣划失败");
		}
		
		return deductResult;
	}
	
	private boolean isInsAccount(String bdhm, String accountNumber, String name) throws RemoteAccessException, DataOperateException {
		boolean isInsAccount = true;
		try {
			Input358080 input = new Input358080();
			input.setAccountNumber(accountNumber); // 执行款专户账号
			input.setCheckName(true); // 校验户名
			input.setName(name); // 执行款专户户名
			input.setQueryBalance(false);
			AccountDetail accountDetail = prefixMessageService.queryAccountDetail(input);
			if (accountDetail == null) {
				throw new DataOperateException("查询执行款专户账户信息失败");
			}
		} catch (DataOperateException e) {
			if ("DC2070".equals(e.getCode())) { // 查无账户信息
				isInsAccount = false;
			} else {
				logger.error("Exception: 查询执行款专户账户信息失败 - BDHM=[{}] ACCOUNT=[{}]", bdhm, accountNumber, e);
				throw new DataOperateException("存款账户扣划失败：{}", e.getMessage());
			}
		}
		
		return isInsAccount;
	}
}
