package com.citic.server.gdjg.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.citic.server.cgb.domain.response.OnlineTPFinancialDetail;
import com.citic.server.dict.DictCoder;
import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Br57_cxqq_mx;
import com.citic.server.gdjg.domain.Br57_kzqq_input;
import com.citic.server.gdjg.domain.Br57_kzqq_mx;
import com.citic.server.gdjg.domain.Br57_kzqq_mx_policeman;
import com.citic.server.gdjg.domain.request.Gdjg_RequestBxxdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestLsdj;
import com.citic.server.gdjg.domain.request.Gdjg_Request_AccCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_BankCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_FinancialProductsCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_FroInfoCx;
import com.citic.server.gdjg.domain.request.Gdjg_Request_TransCx;
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
import com.citic.server.inner.service.ISOAPMessageService;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.service.CacheService;
import com.citic.server.utils.DtUtils;

/**
 * 广东省检察院
 * 
 * @author liuxuanfei
 * @date 2017年5月25日 下午9:56:46
 */

@Service("remoteDataOperateGdjg")
public class RemoteDataOperateGdjg implements IDataOperateGdjg {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(RemoteDataOperateGdjg.class);
	@Autowired
	private IPrefixMessageService messageService;
	
	@Autowired
	private ISOAPMessageService isoapmessageService;
	
	/** 码表转换接口 */
	private DictCoder dictCoder;
	
	@Autowired
	@Qualifier("answerCodeService")
	private AnswerCodeService exceptionService;
	
	protected CacheService cacheService;
	
	/** 存款查询 */
	@Override
	public Gdjg_Request_BankCx getAccountDetail(Br57_cxqq_mx br57_cxqq_mx) throws DataOperateException, RemoteAccessException {
		Gdjg_Request_BankCx requestckdj_bank = new Gdjg_Request_BankCx();
		List<Gdjg_Request_AccCx> accs = new ArrayList<Gdjg_Request_AccCx>();
		List<Gdjg_Request_AccCx> subAccs = new ArrayList<Gdjg_Request_AccCx>();
		AccountDetail accountDetail = null;
		
		//账号查询依据
		String type = br57_cxqq_mx.getType();
		String primaryAccount = br57_cxqq_mx.getAccount();
		//对私查询依据
		String name = br57_cxqq_mx.getName();
		String idType = br57_cxqq_mx.getIdtype();
		String id = br57_cxqq_mx.getId();
		//对公查询依据
		String queryMode = br57_cxqq_mx.getQuerymode();
		String orgCode = br57_cxqq_mx.getOrgcode();
		String buslicense = br57_cxqq_mx.getBuslicense();
		
		//1.类型为ACCOUNT，账户查询
		if (StringUtils.equals(GdjgConstants.TYPE_ACCOUNT, type)) {
			logger.info("【存款查询】账号详细信息查询：");
			String startdate = DtUtils.getNowDate();
			accountDetail = messageService.queryAccountDetail(primaryAccount, null, null);
			if (accountDetail == null || StringUtils.isEmpty(accountDetail.getCustomerType())) {
				return null;
			}
			
			//1.1  对私客户账户
			if (StringUtils.equals("1", accountDetail.getCustomerType())) {
				//1.1.1 处理基本信息
				Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
				acc = getDivAccCxDetail(accountDetail, primaryAccount, type, startdate);
				//1.1.2 处理冻结信息，权利信息暂时无法处理
				List<Gdjg_Request_FroInfoCx> froInfos = new ArrayList<Gdjg_Request_FroInfoCx>();
				froInfos = getFroInfos(primaryAccount);
				acc.setFroinfos(froInfos);
				//1.1.3 处理子账户信息
				subAccs = getSubAccDetail(primaryAccount, type);
				
				//1.1.4 主账户和子账户合并后存入
				accs.add(acc);
				accs.addAll(subAccs);
				requestckdj_bank.setAccs(accs);
				
				// 1.2 对公客户账户，同业客户视同对公客户（无子账户）
			} else {
				//1.2.1 处理基本信息
				Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
				acc = getCorAccCxDetail(accountDetail, primaryAccount, type);
				//1.2.2 处理冻结信息，权利信息暂时无法处理
				List<Gdjg_Request_FroInfoCx> froInfos = new ArrayList<Gdjg_Request_FroInfoCx>();
				froInfos = getFroInfos(primaryAccount);
				acc.setFroinfos(froInfos);
				//1.2.3 存入数据
				accs.add(acc);
				requestckdj_bank.setAccs(accs);
			}
			
			//2.类型为PERSON,个人查询
		} else if (StringUtils.equals(GdjgConstants.TYPE_PERSON, type)) {
			//2.1 账户查询
			if (!StringUtils.isEmpty(primaryAccount)) {
				logger.info("【存款查询】账号详细信息查询：");
				String startdate = DtUtils.getNowDate();
				accountDetail = messageService.queryAccountDetail(primaryAccount, null, null);
				if (accountDetail == null || StringUtils.isEmpty(accountDetail.getCustomerType())) {
					return null;
				}
				//2.1.1 处理基本信息
				Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
				acc = getDivAccCxDetail(accountDetail, primaryAccount, type, startdate);
				
				//2.1.2 处理冻结信息，权利信息暂时无法处理
				List<Gdjg_Request_FroInfoCx> froInfos = new ArrayList<Gdjg_Request_FroInfoCx>();
				froInfos = getFroInfos(primaryAccount);
				acc.setFroinfos(froInfos);
				//2.1.3 处理子账户信息
				subAccs = getSubAccDetail(primaryAccount, type);
				
				//2.1.4 主账户和子账户合并后存入
				accs.add(acc);
				accs.addAll(subAccs);
				requestckdj_bank.setAccs(accs);
				
			}
			//2.2 三证查询
			if (StringUtils.isEmpty(name) || StringUtils.isEmpty(idType) || StringUtils.isEmpty(id)) { // 如果名称、证件号码、证件名称有一个为空，则返回失败	
				throw new DataOperateException("9999", "暂不支持该类查询");
			} else {
				//2.2.1  查询首层合约账户
				List<ContractAccount> contractAccs = new ArrayList<ContractAccount>();
				logger.info("【存款查询】首层合约账号信息查询：");
				contractAccs = messageService.queryContractAccountList(idType, id, name);
				if (contractAccs == null || contractAccs.size() == 0) {
					return null;
				}
				// 2.2.2 处理基本信息
				for (ContractAccount account : contractAccs) {
					String contractType = account.getContractType();
					
					// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
					if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
						primaryAccount = account.getAccountNumber();
						logger.info("【存款查询】主账号详细信息查询：");
						String startdate = DtUtils.getNowDate();
						accountDetail = messageService.queryAccountDetail(primaryAccount, account.getCurrency(), null);
						Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
						acc = getDivAccCxDetail(accountDetail, primaryAccount, type, startdate);
						// 2.2.3 处理冻结信息，权利信息无法提供
						List<Gdjg_Request_FroInfoCx> froInfos = new ArrayList<Gdjg_Request_FroInfoCx>();
						froInfos = getFroInfos(primaryAccount);
						acc.setFroinfos(froInfos);
						
						//2.2.4 处理子账户信息
						subAccs = getSubAccDetail(primaryAccount, type);
						//2.2.5 主账户和子账户合并后存入
						accs.add(acc);
						accs.addAll(subAccs);
					}
				}
				requestckdj_bank.setAccs(accs);
			}
		}
		//3.类型为UNIT,单位查询
		else if (StringUtils.equals(GdjgConstants.TYPE_UNIT, type)) {
			idType = StringUtils.trimToEmpty(getIDType(br57_cxqq_mx).getIdtype());
			id = getIDType(br57_cxqq_mx).getId();
			//3.1 账户查询
			if (!StringUtils.isEmpty(primaryAccount)) {
				if (!StringUtils.isEmpty(primaryAccount)) {
					logger.info("【存款查询】账号详细信息查询：");
					accountDetail = messageService.queryAccountDetail(primaryAccount, null, null);
					if (accountDetail == null || StringUtils.isEmpty(accountDetail.getCustomerType())) {
						return null;
					}
					//3.1.1 处理基本信息
					Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
					acc = getCorAccCxDetail(accountDetail, primaryAccount, type);
					//3.1.2 处理冻结信息，权利信息暂时无法处理
					List<Gdjg_Request_FroInfoCx> froInfos = new ArrayList<Gdjg_Request_FroInfoCx>();
					froInfos = getFroInfos(primaryAccount);
					acc.setFroinfos(froInfos);
					//3.1.3 存入数据
					requestckdj_bank.setAccs(accs);
				}
				
				// 3.2 三证查询--组织机构代码
			} else if ((!StringUtils.isEmpty(name)) && (!StringUtils.isEmpty(orgCode))) {
				//3.2.1  查询首层合约账户
				List<ContractAccount> contractAccs = new ArrayList<ContractAccount>();
				logger.info("【存款查询】首层合约账户信息查询：");
				contractAccs = messageService.queryContractAccountList(idType, orgCode, name);
				if (contractAccs == null || contractAccs.size() == 0) {
					return null;
				}
				// 3.2.2 处理基本信息
				for (ContractAccount account : contractAccs) {
					String contractType = account.getContractType();
					// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
					if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
						primaryAccount = account.getAccountNumber();
						logger.info("【存款查询】账号详细信息查询：");
						accountDetail = messageService.queryAccountDetail(primaryAccount, account.getCurrency(), null);
						Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
						acc = getCorAccCxDetail(accountDetail, primaryAccount, type);
						// 3.2.3 处理冻结信息，权利信息暂时无法处理
						List<Gdjg_Request_FroInfoCx> froInfos = new ArrayList<Gdjg_Request_FroInfoCx>();
						froInfos = getFroInfos(primaryAccount);
						acc.setFroinfos(froInfos);
						//3.2.4  存入数据
						accs.add(acc);
					}
				}
				requestckdj_bank.setAccs(accs);
				
				//3.3三证查询--工商营业执照
			} else if ((!StringUtils.isEmpty(name)) && (!StringUtils.isEmpty(buslicense))) {
				//3.3.1  查询首层合约账户
				List<ContractAccount> contractAccs = new ArrayList<ContractAccount>();
				logger.info("【存款查询】首层合约账户信息查询：");
				contractAccs = messageService.queryContractAccountList(idType, buslicense, name);
				if (contractAccs == null || contractAccs.size() == 0) {
					return null;
				}
				// 3.3.2 处理基本信息
				for (ContractAccount account : contractAccs) {
					String contractType = account.getContractType();
					// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
					if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
						primaryAccount = account.getAccountNumber();
						logger.info("【存款查询】账号详细信息查询：");
						accountDetail = messageService.queryAccountDetail(primaryAccount, account.getCurrency(), null);
						Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
						acc = getCorAccCxDetail(accountDetail, primaryAccount, type);
						// 3.3.3 处理冻结信息，权利信息暂时无法处理
						List<Gdjg_Request_FroInfoCx> froInfos = new ArrayList<Gdjg_Request_FroInfoCx>();
						froInfos = getFroInfos(primaryAccount);
						acc.setFroinfos(froInfos);
						//3.3.4  存入数据
						accs.add(acc);
					}
				}
				requestckdj_bank.setAccs(accs);
			} else {
				throw new DataOperateException("9999", "暂不支持该类查询");
			}
		} else {
			logger.error("【存款查询】查询类别出现错误，字段不属于UNIT、PERSON、ACCOUNT其中任一种！");
		}
		
		return requestckdj_bank;
	}
	
	/** 交易流水查询 */
	@Override
	public Gdjg_RequestLsdj getAccountTransaction(Br57_cxqq_mx br57_cxqq_mx) throws DataOperateException, RemoteAccessException {
		Gdjg_RequestLsdj tran = new Gdjg_RequestLsdj();
		
		String account_req = br57_cxqq_mx.getAccount();
		String name_req = br57_cxqq_mx.getName();
		String id_req = br57_cxqq_mx.getId();
		String idType_req = br57_cxqq_mx.getIdtype();
		try {
			//获取查询开始和结束时间区间
			String startdate = StringUtils.trimToEmpty(br57_cxqq_mx.getStartdate());
			String enddate = StringUtils.trimToEmpty(br57_cxqq_mx.getEnddate());
			if (enddate.isEmpty()) { //如果结束日期为空，取当前日期
				enddate = DtUtils.getNowDate();
			}
			if (startdate.isEmpty()) { //如果查询起始日期为空，取结束日期前一年的日期
				startdate = DtUtils.add(enddate, DtUtils.YEAR, -1, false);
			}
			//三证查询
			if ((!StringUtils.isEmpty(name_req)) && (!StringUtils.isEmpty(idType_req)) && (!StringUtils.isEmpty(id_req))) { // 如果名称、证件号码、证件名称有一个为空，则返回失败
				Br57_cxqq_mx br57_cxqq_mx_copy = br57_cxqq_mx;
				//1  查询首层合约账户
				List<ContractAccount> contractAccs = new ArrayList<ContractAccount>();
				logger.info("【交易流水查询】首层合约账号查询：");
				logger.info(idType_req + id_req + name_req);
				contractAccs = messageService.queryContractAccountList(idType_req, id_req, name_req);
				if ((contractAccs != null) && (contractAccs.size() != 0)) {
					// 2  获取账户号
					for (ContractAccount account : contractAccs) {
						String contractType = account.getContractType();
						// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
						if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
							String accNum = account.getAccountNumber();
							logger.info("【交易流水查询】账号详细信息查询：");
							AccountDetail primaryAccountDetail = messageService.queryAccountDetail(accNum, null, null);
							if (StringUtils.equals(primaryAccountDetail.getCustomerType(), "1")) {//1:个人客户、2:对公客户、3:同业客户(对公)	
								br57_cxqq_mx_copy.setType(GdjgConstants.TYPE_PERSON);
							} else {
								br57_cxqq_mx_copy.setType(GdjgConstants.TYPE_UNIT);
							}
							br57_cxqq_mx_copy.setAccount(accNum);
							logger.info("【交易流水查询】交易流水信息查询：");
							List<AccountTransaction> accountTransactions = messageService.queryAccountTransaction(br57_cxqq_mx_copy.getAccount(), startdate, enddate);
							if (accountTransactions != null) {
								tran.setTrans(getTransactionList(accountTransactions, br57_cxqq_mx_copy));
								tran.setQuerytime(Utility.currDateTime19());//主机查询时间
							}
						}
					}
				}
				
				//账号查询
			} else if (!StringUtils.isEmpty(account_req)) {
				logger.info("【交易流水查询】账号详细信息查询：");
				AccountDetail primaryAccountDetail = messageService.queryAccountDetail(br57_cxqq_mx.getAccount(), null, null);
				br57_cxqq_mx.setName(primaryAccountDetail.getCustomerName());
				if (StringUtils.equals(primaryAccountDetail.getCustomerType(), "1")) {//1:个人客户、2:对公客户、3:同业客户(对公)	
					br57_cxqq_mx.setType(GdjgConstants.TYPE_PERSON);
				} else {
					br57_cxqq_mx.setType(GdjgConstants.TYPE_UNIT);
				}
				logger.info("【交易流水查询】交易流水信息查询：");
				List<AccountTransaction> accountTransactions = messageService.queryAccountTransaction(br57_cxqq_mx.getAccount(), startdate, enddate);
				if (accountTransactions != null) {
					tran.setTrans(getTransactionList(accountTransactions, br57_cxqq_mx));
					tran.setQuerytime(Utility.currDateTime19());//主机查询时间
				}
			}
		} catch (Exception e) {
			logger.error("【交易流水查询】查询交易流水信息失败，" + e.getMessage());
			throw new DataOperateException(e.getMessage());
		}
		return tran;
	}
	
	/** 金融产品查询 */
	@Override
	public Gdjg_Request_BankCx getJrcpInfo(Br57_cxqq_mx br57_cxqq_mx) throws DataOperateException, RemoteAccessException {
		Gdjg_Request_BankCx requestckdj_bank = new Gdjg_Request_BankCx();
		List<Gdjg_Request_FinancialProductsCx> products = new ArrayList<Gdjg_Request_FinancialProductsCx>();
		
		//查询依据
		String type = br57_cxqq_mx.getType();
		String name = br57_cxqq_mx.getName();
		String idType = br57_cxqq_mx.getIdtype();
		String id = br57_cxqq_mx.getId();
		try {
			//对私
			if (StringUtils.equals(GdjgConstants.TYPE_PERSON, type)) {
				if (StringUtils.isEmpty(name) || StringUtils.isEmpty(idType) || StringUtils.isEmpty(id)) { // 如果名称、证件号码、证件名称有一个为空，则返回失败	
					throw new DataOperateException("9999", "证件类型不全，暂不支持该类查询");
				} else {
					logger.info("【商业银行金融产品】信息查询：");
					logger.info("证件类型[{}],证件号码[{}]，姓名[{}]", idType, id, name);
					List<OnlineTPFinancialDetail> jrcpMsgs = isoapmessageService.queryFinancialFromOnlineTP(idType, id, name);
					if (jrcpMsgs == null || jrcpMsgs.size() == 0) {
						return null;
						
					}
					for (OnlineTPFinancialDetail jrcpMsg : jrcpMsgs) {
						Gdjg_Request_FinancialProductsCx ProductsCx = new Gdjg_Request_FinancialProductsCx();
						ProductsCx = getProductsCxMsg(jrcpMsg);
						products.add(ProductsCx);
					}
				}
				//对公
			} else if (StringUtils.equals(GdjgConstants.TYPE_UNIT, type)) {
				if (StringUtils.isEmpty(name) || StringUtils.isEmpty(idType) || StringUtils.isEmpty(id)) { // 如果名称、证件号码、证件名称有一个为空，则返回失败	
					throw new DataOperateException("9999", "证件类型不全，暂不支持该类查询");
				} else {
					logger.info("【商业银行金融产品】信息查询：");
					List<OnlineTPFinancialDetail> jrcpMsgs = isoapmessageService.queryFinancialFromOnlineTP(idType, id, name);
					if (jrcpMsgs == null || jrcpMsgs.size() == 0) {
						return null;
					}
					for (OnlineTPFinancialDetail jrcpMsg : jrcpMsgs) {
						Gdjg_Request_FinancialProductsCx ProductsCx = new Gdjg_Request_FinancialProductsCx();
						ProductsCx = getProductsCxMsg(jrcpMsg);
						products.add(ProductsCx);
					}
				}
			} else {
				logger.error("【商业银行金融产品】查询类别出现错误，字段不属于UNIT、PERSON其中任一种！");
			}
			requestckdj_bank.setFinancialPros(products);
		} catch (Exception e) {
			logger.error("查询失败：" + e.getMessage());
			throw new DataOperateException(e.getMessage());
		}
		return requestckdj_bank;
	}
	
	/** 处理：保险箱查询 */
	@Override
	public Gdjg_RequestBxxdj getSafeBoxInfo(Br57_cxqq_mx br57_cxqq_mx) throws DataOperateException, RemoteAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 处理：账户查询--对私账户--基本信息
	 * 
	 * @param accountDetail
	 * @param primaryAccount
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 * @author liuxuanfei
	 * @date 2017年5月27日 下午3:42:59
	 */
	public Gdjg_Request_AccCx getDivAccCxDetail(AccountDetail accountDetail, String account, String type, String startdate) throws DataOperateException, RemoteAccessException {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		if (accountDetail != null) {
			//账户信息
			acc.setAccname(accountDetail.getCustomerName());//户名
			acc.setType(type);
			acc.setAccount(account);
			acc.setCardno(account);//卡号
			acc.setAcctype(accountDetail.getAccountAttr());//账户类型
			acc.setBanlance(accountDetail.getAccountBalance());//余额（为空值时，传 0.00 ）
			acc.setAvabanlance(accountDetail.getAvailableBalance());//可用余额
			acc.setCurrency(getCurrencyTrans(accountDetail.getCurrency()));//币种
			acc.setExchangetype(accountDetail.getCashExCode());//汇钞类型（1-现钞；2-现汇）
			acc.setOpendate(accountDetail.getCardOpenDate());//开户日期（YYYY-MM-DD）
			acc.setTradedate(accountDetail.getLastTransDate());//最后交易日期
			acc.setOpenbranchno(accountDetail.getAccountOpenBranch());//开户网点编号
			acc.setOpenbranchname(accountDetail.getAccountOpenBranch());//开户网点名称 需转码 wb
			acc.setIdtype(accountDetail.getIdType());//证件类型
			acc.setId(accountDetail.getIdNumber());//证件号码
			acc.setStatusflag(accountDetail.getAccountStatus());// 账户状态标识（0-正常；1-已冻结；2-已销户；3-已挂失；9-其它）
			acc.setStatus(accountDetail.getAccountStatus());//账户状态（用中文描述当前账户所处的状态）
			acc.setClosedate(accountDetail.getAccountClosingDate());// 销户日期（YYYY-MM-DD）
			acc.setClosebranchno(accountDetail.getAccountClosingBranch());//销户网点编号
			acc.setClosebranchname(accountDetail.getAccountClosingBranch());//销户网点名称 需转码 wb
			acc.setQrydt(DtUtils.getNowDate());
			acc.setStartdate(startdate);
			acc.setEnddate(DtUtils.getNowDate());
			acc.setIsfro("1");//是否支持网上冻结   0：不支持   1：支持（为空时默认为支持）
		}
		logger.info("个人客户信息查询：");
		IndividualCustomer inCustomer = messageService.queryIndividualCustomerInfo(account);
		if (inCustomer == null || ("").equals(inCustomer)) {
		} else {
			acc.setOpenbranchtel("");//开户网点电话
			acc.setOpenbranchaddr("");//开户网点地址
			acc.setAddr(inCustomer.getPermanentAddress());//账户人联系地址
			acc.setTel(inCustomer.getTelephoneNumber());//账户人联系电话
			acc.setHomeaddr(inCustomer.getPermanentAddress());//住宅地址
			acc.setHometel(inCustomer.getFixedLineNumber());//住宅电话
			acc.setIphone(inCustomer.getMobilePhoneNumber());//账户人联系手机
			acc.setAgentname("");//代办人姓名
			acc.setAgentidtype("");//代办人证件类型
			acc.setAgentid("");//代办人证件号码
			acc.setWork("");//工作单位
			acc.setWorkaddr("");//单位地址
			acc.setWorktel("");//单位电话
			acc.setEmail(inCustomer.getEmailAddress());//邮箱地址
			acc.setLegal("");//法人代表
			acc.setLegalidtype("");//法人代表证件类型
			acc.setLegalid("");//法人代表证件号码
			acc.setBuslicense("");//工商营业执照
			acc.setNationaltax("");//国税纳税号
			acc.setLandtax("");//地税纳税号
		}
		
		return acc;
	}
	
	/**
	 * 处理：账户查询--对公账户--基本信息
	 * 
	 * @param accountDetail
	 * @param account
	 * @param type
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 * @author liuxuanfei
	 * @date 2017年5月31日 上午8:48:02
	 */
	public Gdjg_Request_AccCx getCorAccCxDetail(AccountDetail accountDetail, String account, String type) throws DataOperateException, RemoteAccessException {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		if (accountDetail != null) {
			//账户信息
			acc.setAccname(accountDetail.getCustomerName());//户名
			acc.setType(type);
			acc.setAccount(account);
			acc.setCardno(account);//卡号
			acc.setAcctype(accountDetail.getAccountAttr());//账户类型
			acc.setBanlance(accountDetail.getAccountBalance());//余额（为空值时，传 0.00 ）
			acc.setAvabanlance(accountDetail.getAvailableBalance());//可用余额（为空值时，传 0.00 ）
			acc.setCurrency(getCurrencyTrans(accountDetail.getCurrency()));//币种
			acc.setExchangetype(accountDetail.getCashExCode());//汇钞类型（1-现钞；2-现汇）
			acc.setOpendate(accountDetail.getCardOpenDate());//开户日期（YYYY-MM-DD）
			acc.setTradedate(accountDetail.getLastTransDate());//最后交易日期
			acc.setOpenbranchno(accountDetail.getAccountOpenBranch());//开户网点编号
			acc.setOpenbranchname(accountDetail.getAccountOpenBranch());//开户网点名称
			acc.setIdtype(accountDetail.getIdType());//证件类型
			acc.setId(accountDetail.getIdNumber());//证件号码
			acc.setStatusflag(accountDetail.getAccountStatus());// 账户状态标识（0-正常；1-已冻结；2-已销户；3-已挂失；9-其它）
			acc.setStatus(accountDetail.getAccountStatus());//账户状态（用中文描述当前账户所处的状态）
			acc.setClosedate(accountDetail.getAccountClosingDate());// 销户日期（YYYY-MM-DD）
			acc.setClosebranchno(accountDetail.getAccountClosingBranch());//销户网点编号
			acc.setClosebranchname(accountDetail.getAccountClosingBranch());//销户网点名称
			acc.setIsfro("0");//是否支持网上冻结   0：不支持   1：支持（为空时默认为支持）
		}
		logger.info("对公客户信息查询：");
		CorporateCustomer coCustomer = messageService.queryCorporateCustomerInfo(account);
		if (coCustomer == null || ("").equals(coCustomer)) {
		} else {
			acc.setOpenbranchtel("");//开户网点电话
			acc.setOpenbranchaddr("");//开户网点地址
			acc.setAddr("");//账户人联系地址
			acc.setTel("");//账户人联系电话
			acc.setHomeaddr("");//住宅地址
			acc.setHometel("");//住宅电话
			acc.setIphone("");//账户人联系手机
			acc.setAgentname("");//代办人姓名
			acc.setAgentidtype("");//代办人证件类型
			acc.setAgentid("");//代办人证件号码
			acc.setWork(coCustomer.getChineseName());//工作单位
			acc.setWorkaddr(coCustomer.getRegisteredAddress());//单位地址
			acc.setWorktel(coCustomer.getFixedLineNumber());//单位电话
			acc.setEmail(coCustomer.getEmailAddress());//邮箱地址
			acc.setLegal(coCustomer.getLegalInfo().getName());//法人代表
			acc.setLegalidtype(coCustomer.getLegalInfo().getIdType());//法人代表证件类型
			acc.setLegalid(coCustomer.getLegalInfo().getIdNumber());//法人代表证件号码
			acc.setBuslicense(coCustomer.getBusiIDNumber());//工商营业执照
			acc.setNationaltax(coCustomer.getStateTaxIDNumber());//国税纳税号
			acc.setLandtax(coCustomer.getLocalTaxIDNumber());//地税纳税号
		}
		
		return acc;
	}
	
	/**
	 * 处理：冻结信息
	 * 
	 * @param account
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 * @author liuxuanfei
	 * @date 2017年5月27日 下午4:05:51
	 */
	public List<Gdjg_Request_FroInfoCx> getFroInfos(String account) throws DataOperateException, RemoteAccessException {
		List<Gdjg_Request_FroInfoCx> froInfos = new ArrayList<Gdjg_Request_FroInfoCx>();
		List<AccountFrozenMeasure> accFroMsgs = new ArrayList<AccountFrozenMeasure>();
		logger.info("账户冻结详细信息查询：");
		accFroMsgs = messageService.queryAccountFrozenMeasures(account);
		if (accFroMsgs == null || accFroMsgs.size() == 0) {
			return null;
		} else {
			for (AccountFrozenMeasure froMsg : accFroMsgs) {
				Gdjg_Request_FroInfoCx fro = new Gdjg_Request_FroInfoCx();
				if (!("").equals(froMsg.getFrozenNumber())) {
					fro.setFrono(froMsg.getFrozenNumber());
					fro.setFrotype(froMsg.getFrozenType());
					fro.setFrounit("");//冻结申请单位 不反
					fro.setFrobanlance(froMsg.getFrozenAmount());
					fro.setFrostartdate(froMsg.getEffectiveDate());
					fro.setFroenddate(froMsg.getExpiringDate());
					
					fro.setFrozenaccount(froMsg.getAccountNumber());
					fro.setFrozeninstype(froMsg.getFrozenInsType());
					fro.setFrozenstatus(froMsg.getFrozenStatus());
					fro.setWaitingseq(froMsg.getWaitingSeq());
				} else {
					continue;
				}
				froInfos.add(fro);
			}
		}
		return froInfos;
	}
	
	/**
	 * 处理：子账户信息（冻结信息暂不处理）
	 * 
	 * @param primaryAccount
	 * @param type
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 * @author liuxuanfei
	 * @date 2017年5月27日 下午4:52:06
	 */
	public List<Gdjg_Request_AccCx> getSubAccDetail(String primaryAccount, String type) throws DataOperateException, RemoteAccessException {
		List<Gdjg_Request_AccCx> subAccs = new ArrayList<Gdjg_Request_AccCx>();
		Gdjg_Request_AccCx subacc = new Gdjg_Request_AccCx();
		logger.info("子账号信息查询：");
		String startdate = DtUtils.getNowDate();
		List<SubAccountInfo> subAccountInfos = messageService.querySubAccountInfoList(primaryAccount);
		if (subAccountInfos == null || subAccountInfos.size() == 0) {
		} else {
			for (SubAccountInfo subAccount : subAccountInfos) {
				String subAccountNo = subAccount.getAccountNumber();
				String currency = subAccount.getCurrency();
				String cashExcode = subAccount.getCashExCode();
				String v_account = subAccount.getV_AccountNumber();
				
				if ((subAccountNo == null || cashExcode == null) || (currency == null || currency.length() == 0)) {
					continue;
				}
				logger.info("账号详细信息查询：");
				AccountDetail subAccountDetail = messageService.queryAccountDetail(subAccountNo, currency, cashExcode);
				if (subAccountDetail == null) {
				} else {
					subacc = getDivAccCxDetail(subAccountDetail, primaryAccount, type, startdate);
					String subaccount = v_account; //+ subAccountNo?
					subacc.setAccount(subaccount);
					
					logger.info("账号冻结信息查询：");
					List<Gdjg_Request_FroInfoCx> froInfos = new ArrayList<Gdjg_Request_FroInfoCx>();
					froInfos = getFroInfos(subAccountNo);
					subacc.setFroinfos(froInfos);
				}
				subAccs.add(subacc);
			}
		}
		
		return subAccs;
	}
	
	/**
	 * 处理对公查询参数：组织机构代码/工商营业执照编码（仅单位名称查尚不支持）
	 * 
	 * @param br57_cxqq_mx
	 * @return
	 * @author liuxuanfei
	 * @date 2017年5月31日 上午9:42:48
	 */
	public Br57_cxqq_mx getIDType(Br57_cxqq_mx br57_cxqq_mx) {
		
		String qureymode = br57_cxqq_mx.getQuerymode();
		String idtype = br57_cxqq_mx.getIdtype();
		String id = br57_cxqq_mx.getId();
		if ("4".equals(qureymode)) {// 3：按组织机构代码查询
			id = br57_cxqq_mx.getOrgcode();
			idtype = "20600";
		} else if ("5".equals(qureymode)) {// 4：按工商营业执照编码查询
			id = br57_cxqq_mx.getBuslicense();
			idtype = "20500";
		}
		br57_cxqq_mx.setId(id);
		br57_cxqq_mx.setIdtype(idtype);
		return br57_cxqq_mx;
	}
	
	/**
	 * 交易流水信息处理
	 * 
	 * @param accountTransactions
	 * @param br57_cxqq_mx
	 * @return
	 * @author liuxuanfei
	 * @date 2017年5月31日 下午2:04:43
	 */
	public List<Gdjg_Request_TransCx> getTransactionList(List<AccountTransaction> accountTransactions, Br57_cxqq_mx br57_cxqq_mx) {
		List<Gdjg_Request_TransCx> transactionList = new ArrayList<Gdjg_Request_TransCx>();
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
				transaction.setTradeTime("1970-01-01 00:00:00");//此处存疑
			}
			
			Gdjg_Request_TransCx request_transaction = new Gdjg_Request_TransCx();
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
			request_transaction.setAccname(br57_cxqq_mx.getName());// 查询卡号客户的名称br57_cxqq_mx.getAccname()
			request_transaction.setAcctype(br57_cxqq_mx.getType());// 账户类型
			request_transaction.setAccount(br57_cxqq_mx.getAccount());// 账号
			//request_transaction.setAccname(br57_cxqq_mx.getAccname());
			if (br57_cxqq_mx.getType().equals(GdjgConstants.TYPE_PERSON)) {
				request_transaction.setCardid(br57_cxqq_mx.getAccount());// 卡号
			}
			
			request_transaction.setIdtype(br57_cxqq_mx.getIdtype());//证件类型
			request_transaction.setId(br57_cxqq_mx.getId());
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
			request_transaction.setCurrency(getCurrencyTrans(transaction.getTradeCurrency()));// 币种
			request_transaction.setTranstype(transaction.getTradeChannel());// 交易类型 给交易渠道
			request_transaction.setTransaddr("");// 交易地址
			request_transaction.setTransaddrno(transaction.getTradeBranch());// 交易网点编号或ATM
																				// 机编号
			request_transaction.setTranscountry("");// 交易国家或地区
			request_transaction.setTransremark(transaction.getRemark());// 交易备注
			request_transaction.setTranstel("");// 交易联系电话
			request_transaction.setTranschannel(transaction.getTradeChannelReq());// 交易渠道
			request_transaction.setTransteller(transaction.getTradeTeller());// 交易柜员号
			request_transaction.setTranstermip("");// 交易终端IP
			request_transaction.setMatchaccount(transaction.getRelativeNumber());// 对方账号
			request_transaction.setMatchaccname(transaction.getRelativeName());// 对方户名
			request_transaction.setMatchbankno(transaction.getRelativeBank());// 对方机构号
			request_transaction.setMatchbankname("");// 对方机构名
			request_transaction.setQrydt(DtUtils.getNowDate());
			request_transaction.setMatchidtype("");//对方证件类型
			request_transaction.setMatchid("");//对方证件号码
			request_transaction.setLoanflag(transaction.getDrcrFlag());//借贷标志
			request_transaction.setTransoutlets("");//交易网点代码
			request_transaction.setMatchcard(transaction.getRelativeNumber());//对方卡号
			request_transaction.setMatchbanlance("");//对方余额
			request_transaction.setMatchaddr("");//对方通讯地址
			request_transaction.setMatchzipcode("");//对方邮政编码
			request_transaction.setMatchtel("");//对方联系电话
			request_transaction.setLogno(transaction.getLogNumber());//日志号
			request_transaction.setCitationno(transaction.getVoucherNo());//传票号
			request_transaction.setVouchertype("");//凭证种类
			request_transaction.setVoucher(transaction.getVoucherNumber());//凭证号
			request_transaction.setCashflag("");//现金标志
			request_transaction.setTerm("");//终端号
			request_transaction.setTandf("");//交易是否成功
			request_transaction.setBusinessesname("");//商户名称
			request_transaction.setBusinessesno("");//商户号
			request_transaction.setMacaddr("");//MAC地址
			
			//动态查询另加字段
			request_transaction.setCredit(""); //账户信用额度
			request_transaction.setAuthorizeteller(transaction.getAuthorizer1());//授权柜员号
			request_transaction.setValidityperiod("");//卡有效期 
			request_transaction.setTransno(transaction.getTradeCode());//交易代码
			request_transaction.setAntitransflag("");//反交易标志
			request_transaction.setInvolvingflag("");//冲正交易标识
			
			transactionList.add(request_transaction);
		}
		return transactionList;
		
	}
	
	/**
	 * 处理：金融产品查询--金融产品信息
	 * 
	 * @param accountDetail
	 * @param primaryAccount
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 * @author liuxuanfei
	 * @date 2017年5月27日 下午3:42:59
	 */
	public Gdjg_Request_FinancialProductsCx getProductsCxMsg(OnlineTPFinancialDetail jrcpMsg) {
		Gdjg_Request_FinancialProductsCx ProductsCx = new Gdjg_Request_FinancialProductsCx();
		ProductsCx.setProducttype(jrcpMsg.getProductType()); //产品类别
		ProductsCx.setOrgname("广发银行");//机构名称
		ProductsCx.setProductcode(jrcpMsg.getProductCode());//产品代码
		ProductsCx.setProductname(jrcpMsg.getProductName());//产品名称
		ProductsCx.setCurrency(getCurrencyTrans(jrcpMsg.getCurrency()));//币种
		ProductsCx.setBanlance(jrcpMsg.getAmount());//金额
		ProductsCx.setSalestype(jrcpMsg.getSaleType());//销售类型
		ProductsCx.setProductsum("");//产品摘要
		ProductsCx.setProductremark(jrcpMsg.getMark());//产品备注
		return ProductsCx;
	}
	
	/*********************************************************************************************************/
	//===================================== 控     制     类  ======================================================
	/*********************************************************************************************************/
	
	/** 冻结执行 */
	@Override
	public Gdjg_Request_BankCx getFreezeResult(Br57_kzqq_input br57_kzqq_input, Br57_kzqq_mx br57_kzqq_mx, List<Br57_kzqq_mx_policeman> mx_policeman) throws DataOperateException,
		RemoteAccessException {
		Gdjg_Request_BankCx requestckdj_bank = new Gdjg_Request_BankCx();
		List<Gdjg_Request_AccCx> accs = new ArrayList<Gdjg_Request_AccCx>();
		
		//获取冻结账户的名称,验证是否为本行账号
		logger.info("验证存款冻结账户[{}]是否为本行账号：", br57_kzqq_input.getAccount());
		String account_in = br57_kzqq_input.getAccount();
		AccountDetail accountDetail = null;
		try {
			accountDetail = messageService.queryAccountDetail(account_in, null, null); //查询该账号信息
		} catch (Exception e) {
			logger.error("查询不到该账号[{}]信息，为非本行账号！", account_in, e);
			throw new DataOperateException("冻结账号为非本行账号");
		}
		
		String reqName = accountDetail.getCustomerName(); //获取账户名
		//判断为冻结或续冻
		String djfs = br57_kzqq_input.getFrotype(); //获取冻结类型
		//1.普通冻结
		if (("1".equals(djfs)) || ("2".equals(djfs))) { //冻结方式为冻结或轮候冻结，核心支持轮候冻结
			logger.info("对账号[{}]进行冻结操作...", account_in);
			Input267500 input267500 = getFreezeInput(br57_kzqq_input, mx_policeman, account_in); //处理冻结输入项
			FreezeResult freezeResult = messageService.freezeAccount(input267500); //冻结操作
			logger.info("冻结成功，结果（freezeResult）:" + freezeResult.toString());
			Gdjg_Request_AccCx account = new Gdjg_Request_AccCx();
			try {
				account = getAccountByFreeze(freezeResult, br57_kzqq_input);
			} catch (Exception e) {
				logger.error("冻结结果返回开始/结束日期存在问题", e.getMessage());
				throw new DataOperateException("冻结结果返回开始/结束日期存在问题");
			} //对冻结结果进行处理
			account.setAccname(reqName); //插入户名，回执需要返回
			accs.add(account);
			requestckdj_bank.setAccs(accs);
			
			//2.续冻
		} else if ("3".equals(djfs)) { //冻结方式为续冻
			logger.info("对账号[{}]进行续冻操作...", account_in);
			Input267540 input267540 = getDeferFreezeInput(br57_kzqq_input, mx_policeman); //处理续冻输入项
			DeferFreezeResult deferFreezeResult = messageService.deferFreezeAccount(input267540); //续冻操作
			logger.info("续冻成功，结果（deferFreezeResult）:" + deferFreezeResult.toString());
			Gdjg_Request_AccCx account = new Gdjg_Request_AccCx();
			try {
				account = getAccountByDeferFreeze(deferFreezeResult, br57_kzqq_input);
			} catch (Exception e) {
				logger.error("续冻结果返回开始/结束日期存在问题", e.getMessage());
				throw new DataOperateException("续冻结果返回开始/结束日期存在问题");
			} //对续冻结果进行处理
			account.setAccname(reqName); //插入户名，回执需要返回
			accs.add(account);
			requestckdj_bank.setAccs(accs);
			
		} else {
			logger.error("冻结类型[{}]有误，必须为：1 普通冻结，2 轮候冻结，3 续冻！", djfs);
			throw new DataOperateException("冻结类型FROTYPE有误");
		}
		
		return requestckdj_bank;
	}
	
	/** 解冻执行 */
	@Override
	public Gdjg_Request_BankCx getUnfreezeResult(Br57_kzqq_input br57_kzqq_input, Br57_kzqq_mx br57_kzqq_mx, List<Br57_kzqq_mx_policeman> mx_policeman)
		throws DataOperateException, RemoteAccessException {
		Gdjg_Request_BankCx requestckdj_bank = new Gdjg_Request_BankCx();
		List<Gdjg_Request_AccCx> accs = new ArrayList<Gdjg_Request_AccCx>();
		
		//获取冻结账户的名称
		logger.info("验证存款解冻账户[{}]是否为本行账号：", br57_kzqq_input.getAccount());
		String account_in = br57_kzqq_input.getAccount();
		AccountDetail accountDetail = null;
		try {
			accountDetail = messageService.queryAccountDetail(account_in, null, null); //查询该账号信息
		} catch (Exception e) {
			logger.error("查询不到该账号[{}]信息，为非本行账号！", account_in, e);
			throw new DataOperateException("冻结账号为非本行账号");
		}
		
		String reqName = accountDetail.getCustomerName();
		logger.info("对冻结流水号[{}]进行解除冻结操作...", br57_kzqq_input.getFroseq());
		Input267510 input267510 = getUnfreezeInput(br57_kzqq_input, mx_policeman); //处理解冻输入项
		UnfreezeResult unfreezeResult = messageService.unfreezeAccount(input267510);//执行解冻操作
		logger.info("解冻成功，解冻结果（unfreezeResult）:" + unfreezeResult.toString());
		Gdjg_Request_AccCx account = new Gdjg_Request_AccCx();
		account = getAccountByUnfreeze(unfreezeResult); //对解冻结果进行处理
		account.setAccname(reqName); //插入户名，回执需要返回
		accs.add(account);
		requestckdj_bank.setAccs(accs);
		
		return requestckdj_bank;
	}
	
	/** 紧急止付执行 */
	@Override
	public Gdjg_Request_BankCx getStopPaymentResult(Br57_kzqq_input br57_kzqq_input, Br57_kzqq_mx br57_kzqq_mx, List<Br57_kzqq_mx_policeman> mx_policeman)
		throws DataOperateException, RemoteAccessException {
		Gdjg_Request_BankCx requestckdj_bank = new Gdjg_Request_BankCx();
		List<Gdjg_Request_AccCx> accs = new ArrayList<Gdjg_Request_AccCx>();
		
		String jjzflx = br57_kzqq_input.getStoppayment();
		//紧急止付
		if ("0".equals(jjzflx)) {
			//调整输入项
			Calendar cal = Calendar.getInstance(Locale.CHINESE);
			Date start = cal.getTime();
			cal.add(Calendar.DATE, 2);
			Date end = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
			String startTime = sdf.format(start);
			String endTime = sdf.format(end);
			
			logger.info("对账号[{}]进行紧急止付操作...", br57_kzqq_input.getAccount());
			String account_in = br57_kzqq_input.getAccount();//提取账号或子账号信息，当带子账号时，紧急止付子账号
			Input267500 input267500 = getStopPaymentInput(br57_kzqq_input, mx_policeman, account_in, startTime, endTime); //处理输入项
			FreezeResult freezeResult = messageService.freezeAccount(input267500); //进行紧急止付操作
			logger.info("紧急止付处理成功，结果（StoppaymentResult）:" + freezeResult.toString());
			Gdjg_Request_AccCx account = new Gdjg_Request_AccCx();
			try {
				account = getAccountByStoppayment(freezeResult);
			} catch (Exception e) {
				logger.error("紧急止付结果返回开始/结束日期存在问题", e.getMessage());
				throw new DataOperateException("紧急止付结果返回开始/结束日期存在问题");
			} //处理紧急止付结果
			accs.add(account);
			requestckdj_bank.setAccs(accs);
			
			//解除止付
		} else if ("1".equals(jjzflx)) {
			logger.info("对账号[{}]进行解除紧急止付操作...", br57_kzqq_input.getAccount());
			Input267510 input267510 = getUnstopPaymentInput(br57_kzqq_input, mx_policeman); //处理输入项
			UnfreezeResult unfreezeResult = messageService.unfreezeAccount(input267510); //进行解除紧急止付操作
			logger.info("紧急止付处理成功，结果（UnstoppaymentResult）:" + unfreezeResult.toString());
			Gdjg_Request_AccCx account = new Gdjg_Request_AccCx();
			account = getAccountByUnstoppayment(unfreezeResult); //处理解除紧急止付结果
			accs.add(account);
			requestckdj_bank.setAccs(accs);
			
		} else {
			logger.error("紧急止付方式[{}]有误！必须为：0：紧急止付；1：解除止付", jjzflx);
			throw new DataOperateException("紧急止付类型STOPPAYMENT有误");
		}
		return requestckdj_bank;
	}
	
	/**
	 * 处理：冻结--账户信息处理
	 * 
	 * @throws Exception
	 */
	private Gdjg_Request_AccCx getAccountByFreeze(FreezeResult freezeResult, Br57_kzqq_input br57_kzqq_input) throws Exception {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		acc.setAccount(freezeResult.getAccountNumber());//账号
		acc.setFroseq(freezeResult.getFreezeBookNumber());//冻结流水号
		acc.setCardno("");//卡号
		acc.setBanlance(trimToZero(freezeResult.getAccountBalance()));//余额
		acc.setCanbanlance(trimToZero(freezeResult.getAvailableAmount()));//账户可用余额
		acc.setCurrency(freezeResult.getCurrency());//币种
		acc.setExchangetype(freezeResult.getCashExCode());//汇钞类型
		acc.setFrotype(br57_kzqq_input.getFrotype());//冻结类型 
		acc.setFromode(br57_kzqq_input.getFromode());//冻结方式
		acc.setFroflag("1");//冻结标志      1冻结成功    2冻结失败
		acc.setFrobanlance_1(trimToZero(freezeResult.getFreezeAmount()));//应冻结金额
		acc.setFrobanlance_2(trimToZero(freezeResult.getFrozenAmount()));//已冻结金额
		acc.setFrobanlance_3(trimToZero(freezeResult.getUnfrozenAmount()));//未冻结金额
		acc.setFrobanlance(trimToZero(freezeResult.getFreezeAmount()));//冻结额度
		acc.setFrostartdate(Utility.toDate10(freezeResult.getEffectiveDate()));//冻结开始日期
		acc.setFroenddate(Utility.toDate10(freezeResult.getExpiringDate()));//冻结结束日期
		acc.setMemo("成功");//原因 
		acc.setBeforefro("");//在先冻结机关
		acc.setBeforefroban("");//在先冻结金额 
		acc.setBeforefrodate("");//在先冻结到期日
		acc.setServicetime(Utility.currDateTime19());//送达时间
		acc.setServicedoc("");//送达证
		acc.setReplytime(Utility.currDateTime14());//回执时间
		acc.setReplydoc("");//回执
		acc.setFiletype("PDF");//文件格式
		acc.setFronumber(freezeResult.getFrozenNumber());//冻结编号（artery）
		return acc;
	}
	
	/**
	 * 处理：续冻--账户信息处理
	 * 
	 * @throws Exception
	 */
	private Gdjg_Request_AccCx getAccountByDeferFreeze(DeferFreezeResult deferFreezeResult, Br57_kzqq_input br57_kzqq_input) throws Exception {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		acc.setAccount(deferFreezeResult.getAccountNumber());//账号
		acc.setFroseq(deferFreezeResult.getDeferBookNumber());//冻结流水号
		acc.setCardno("");//卡号
		acc.setBanlance(trimToZero(deferFreezeResult.getAccountBalance()));//余额
		acc.setCanbanlance(trimToZero(deferFreezeResult.getAvailableAmount()));//账户可用余额
		acc.setCurrency(deferFreezeResult.getCurrency());//币种
		acc.setExchangetype(deferFreezeResult.getCashExCode());//汇钞类型
		acc.setFrotype(br57_kzqq_input.getFrotype());//冻结类型 
		acc.setFromode(br57_kzqq_input.getFromode());//冻结方式
		acc.setFroflag("1");//冻结标志  1冻结成功    2冻结失败
		acc.setFrobanlance_1(br57_kzqq_input.getFrobanlance());//应冻结金额
		acc.setFrobanlance_2(trimToZero(deferFreezeResult.getFrozenAmount()));//已冻结金额
		acc.setFrobanlance_3("-");//未冻结金额
		acc.setFrobanlance(trimToZero(deferFreezeResult.getFrozenAmount()));//冻结额度
		acc.setFrostartdate(Utility.toDate10(deferFreezeResult.getEffectiveDate()));//冻结开始日期
		acc.setFroenddate(Utility.toDate10(deferFreezeResult.getNewExpiringDate()));//冻结结束日期
		acc.setMemo("成功");//原因 
		acc.setBeforefro("");//在先冻结机关
		acc.setBeforefroban("");//在先冻结金额 
		acc.setBeforefrodate(deferFreezeResult.getExpiringDate());//在先冻结到期日
		acc.setServicetime(Utility.currDateTime19());//送达时间
		acc.setServicedoc("");//送达证
		acc.setReplytime(Utility.currDateTime14());//回执时间
		acc.setReplydoc("");//回执
		acc.setFiletype("PDF");//文件格式
		acc.setFronumber(deferFreezeResult.getFrozenNumber());//冻结编号（artery）
		return acc;
	}
	
	/** 处理：解冻--账户信息处理 */
	private Gdjg_Request_AccCx getAccountByUnfreeze(UnfreezeResult unfreezeResult) {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		acc.setThawseq(unfreezeResult.getUnfreezeBookNumber());// 解冻流水号
		acc.setAccount(unfreezeResult.getAccountNumber());// 账号
		acc.setCardno("");// 卡号
		acc.setCurrency(unfreezeResult.getCurrency());// 币种
		acc.setExchangetype(unfreezeResult.getCashExCode());// 汇钞类型     1：现钞  2：外汇
		acc.setThawmode(unfreezeResult.getUnfreezeType());//解冻方式
		acc.setThawflag("1");// 解冻标志  1 解冻成功 2 解冻失败
		acc.setThawbanlance(trimToZero(unfreezeResult.getUnfreezeAmount()));//解冻金额
		acc.setThawdate(DtUtils.getNowDate());// 解冻日期 
		acc.setMemo("成功");//原因
		acc.setServicetime(Utility.currDateTime19());//送达时间
		acc.setServicedoc("");//送达证
		acc.setReplytime(Utility.currDateTime14());//回执时间 
		acc.setReplydoc("");//回执
		acc.setFiletype("PDF");//文件格式
		
		return acc;
	}
	
	/**
	 * 处理：紧急止付--账户信息处理
	 * 
	 * @throws Exception
	 */
	private Gdjg_Request_AccCx getAccountByStoppayment(FreezeResult freezeResult) throws Exception {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		acc.setAccount(freezeResult.getAccountNumber());//账号
		acc.setCardno("");//卡号
		acc.setExeresult("0");//执行结果  0-执行成功；1-执行失败
		acc.setStartdate(Utility.toDate10(freezeResult.getEffectiveDate()));//开始日期
		acc.setMemo("成功");//原因
		acc.setFronumber(freezeResult.getFrozenNumber());//冻结编号（artery）
		acc.setFroseq(freezeResult.getFreezeBookNumber());//冻结编号
		return acc;
		
	}
	
	/** 处理：解除紧急止付--账户信息处理 */
	private Gdjg_Request_AccCx getAccountByUnstoppayment(UnfreezeResult unfreezeResult) {
		Gdjg_Request_AccCx acc = new Gdjg_Request_AccCx();
		acc.setAccount(unfreezeResult.getAccountNumber());//账号
		acc.setCardno("");//卡号
		acc.setExeresult("0");//执行结果   0-执行成功；1-执行失败
		acc.setStartdate(Utility.currDate10());//开始日期
		acc.setMemo("成功");//原因
		acc.setFroseq(unfreezeResult.getUnfreezeBookNumber());//解冻编号
		return acc;
	}
	
	/** 处理：冻结输入项处理 */
	private Input267500 getFreezeInput(Br57_kzqq_input br57_kzqq_input, List<Br57_kzqq_mx_policeman> mx_policeman, String account_in) {
		Input267500 input = new Input267500();
		input.setAccountNumber(account_in); // 账号
		input.setAccountSeq(null); // 账户序号
		input.setChequeNumber(null);// 支票号码
		input.setFreezeType(transFreezeType(br57_kzqq_input.getFromode())); //冻结方式（1-账户冻结，2-金额冻结，3-圈存）			
		input.setFreezeInsType("1"); //发起机构类型（1-权利机关，2-银行内部）
		input.setCurrency(br57_kzqq_input.getCurrency()); // 货币
		input.setCashExCode(br57_kzqq_input.getExchangetype()); // 钞汇标识
		input.setFreezeAmount(br57_kzqq_input.getFrobanlance()); // 应冻结金额
		input.setFreezeBookNumber(br57_kzqq_input.getFroseq()); // 冻结文书号
		input.setFreezeInsName(br57_kzqq_input.getExeunit()); // 冻结机构名称
		input.setFreezeReason(""); // 冻结原因
		input.setEffectiveDate(br57_kzqq_input.getFrostartdate()); // 生效日期
		input.setExpiringDate(br57_kzqq_input.getFroenddate()); // 到期日期
		input.setRemark(""); // 备注
		input.setFreezeBranch("199999"); // 发起行所
		input.setLawName1(mx_policeman.get(0).getExename()); // 执法人名称1
		input.setLawIDNumber1(mx_policeman.get(0).getPolicecert()); // 执法人证件号1
		input.setLawName2(mx_policeman.get(1).getExename()); // 执法人名称2
		input.setLawIDNumber2(mx_policeman.get(1).getPolicecert()); // 执法人证件号2
		return input;
	}
	
	/** 处理：续冻输入项处理 */
	private Input267540 getDeferFreezeInput(Br57_kzqq_input br57_kzqq_input, List<Br57_kzqq_mx_policeman> mx_policeman) {
		Input267540 input = new Input267540();
		input.setFrozenNumber(br57_kzqq_input.getOldfroseq()); // 冻结编号
		input.setFreezeBookNumber(br57_kzqq_input.getFroseq()); //变动文书号
		input.setFreezeInsName(br57_kzqq_input.getExeunit()); //冻结机构名称
		input.setExpiringDate(br57_kzqq_input.getFroenddate()); //新到期日期
		input.setFreezeReason(""); //变动原因
		input.setRemark(""); //备注
		input.setFreezeBranch("199999"); //发起行所
		input.setLawName1(mx_policeman.get(0).getExename()); //执法人名称1
		input.setLawIDNumber1(mx_policeman.get(0).getPolicecert()); //执法人证件号1
		input.setLawName2(mx_policeman.get(1).getExename()); //执法人名称2
		input.setLawIDNumber2(mx_policeman.get(1).getPolicecert());//执法人证件号2
		return input;
	}
	
	/** 处理：解冻输入项处理 */
	private Input267510 getUnfreezeInput(Br57_kzqq_input br57_kzqq_input, List<Br57_kzqq_mx_policeman> mx_policeman) {
		Input267510 input = new Input267510();
		input.setFrozenNumber(br57_kzqq_input.getFroseq()); // 冻结编号
		input.setUnfreezeAmount(br57_kzqq_input.getThawbanlance()); //  解冻金额  
		input.setUnfreezeType("1"); // 解冻方式，写死
		input.setUnfreezeBookNumber(br57_kzqq_input.getThawseq()); // 变动文书号
		input.setUnfreezeBranch("199999"); // 发起行所
		input.setUnfreezeInsName(br57_kzqq_input.getExeunit()); // 冻结机构名称
		input.setRemark("");//备注
		input.setLawName1(mx_policeman.get(0).getExename());// 执法人名称1
		input.setLawIDNumber1(mx_policeman.get(0).getPolicecert());// 执法人证件号1
		input.setLawName2(mx_policeman.get(1).getExename());// 执法人名称2
		input.setLawIDNumber2(mx_policeman.get(1).getPolicecert());// 执法人证件号2
		return input;
	}
	
	/**
	 * 处理：紧急止付输入项处理
	 * 
	 * @param endTime
	 * @param startTime
	 */
	private Input267500 getStopPaymentInput(Br57_kzqq_input br57_kzqq_input, List<Br57_kzqq_mx_policeman> mx_policeman, String account_in, String startTime, String endTime) {
		Input267500 input = new Input267500();
		input.setAccountNumber(account_in); // 账号
		input.setAccountSeq(""); // 账户序号
		input.setChequeNumber("");// 支票号码
		input.setFreezeType("1"); //冻结方式 1.账户冻结
		input.setFreezeInsType("1"); //发起机构类型（1-权利机关，2-银行内部）
		input.setCurrency(""); // 货币
		input.setCashExCode(""); // 钞汇标识
		input.setFreezeAmount(""); // 应冻结金额
		input.setFreezeBookNumber(br57_kzqq_input.getFroseq()); // 冻结文书号
		input.setFreezeInsName(br57_kzqq_input.getExeunit()); // 冻结机构名称
		input.setFreezeReason(""); // 冻结原因
		input.setEffectiveDate(startTime); // 生效日期
		input.setExpiringDate(endTime); // 到期日期
		input.setRemark(""); // 备注
		input.setFreezeBranch("199999"); // 发起行所
		input.setLawName1(mx_policeman.get(0).getExename()); // 执法人名称1
		input.setLawIDNumber1(mx_policeman.get(0).getPolicecert()); // 执法人证件号1
		input.setLawName2(mx_policeman.get(1).getExename()); // 执法人名称2
		input.setLawIDNumber2(mx_policeman.get(1).getPolicecert()); // 执法人证件号2
		return input;
	}
	
	/** 处理：解除紧急止付输入项处理 */
	private Input267510 getUnstopPaymentInput(Br57_kzqq_input br57_kzqq_input, List<Br57_kzqq_mx_policeman> mx_policeman) {
		Input267510 input = new Input267510();
		input.setFrozenNumber(br57_kzqq_input.getOldseq()); // 冻结编号
		input.setUnfreezeAmount(""); //  解冻金额  
		input.setUnfreezeType("1"); // 解冻方式（1-全部解冻；2-部分解冻）
		input.setUnfreezeBookNumber(br57_kzqq_input.getFroseq()); // 变动文书号
		input.setUnfreezeBranch("199999"); // 发起行所
		input.setUnfreezeInsName(br57_kzqq_input.getExeunit()); // 冻结机构名称
		input.setRemark("");//备注
		input.setLawName1(mx_policeman.get(0).getExename());// 执法人名称1
		input.setLawIDNumber1(mx_policeman.get(0).getPolicecert());// 执法人证件号1
		input.setLawName2(mx_policeman.get(1).getExename());// 执法人名称2
		input.setLawIDNumber2(mx_policeman.get(1).getPolicecert());// 执法人证件号2
		return input;
	}
	
	//==============================  Helper    Tools  ==============================================
	
	/** 金额处理 */
	private String trimToZero(String inStr) {
		return StringUtils.isBlank(inStr) ? "0.00" : inStr;
	}
	
	/** 空格处理 */
	private String trimToNull(String inStr) {
		return StringUtils.isBlank(inStr) ? "无信息" : inStr;
	}
	
	/** 币种处理 */
	private String getCurrencyTrans(String currency) {
		if (currency == null || currency == "") {
			currency = "OTHER";
		}
		return currency;
	}
	
	/** 冻结方式转换 */
	private String transFreezeType(String transtype) {
		String type = null;
		if ("1".equals(transtype.trim())) {
			type = "2";
		} else if ("2".equals(transtype.trim())) {
			type = "1";
		} else {
			type = transtype;
		}
		return type;
	}
	
	/** 非空项处理 */
	private String vancancyDeal(String value) {
		if (value != null && value != "") {
		} else {
			value = "核心未提供";
		}
		return value;
	}
	
}
