package com.citic.server.whpsb.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ContractAccount;
import com.citic.server.inner.domain.CustomerHoldingInfo;
import com.citic.server.inner.domain.CustomerIDInfo;
import com.citic.server.inner.domain.SubAccountInfo;
import com.citic.server.inner.domain.response.AccountDetail;
import com.citic.server.inner.domain.response.CorporateCustomer;
import com.citic.server.inner.domain.response.IndividualCustomer;
import com.citic.server.inner.service.AnswerCodeService;
import com.citic.server.inner.service.IPrefixMessageService;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.DtUtils;
import com.citic.server.whpsb.WhpsbConstants;
import com.citic.server.whpsb.domain.Br51_cxqq_mx;
import com.citic.server.whpsb.domain.Whpsb_RequestJymx_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestCkrzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestKhzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestZhxx_Detail;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author 殷雄
 * @date 2016年8月19日 上午11:35:32
 */
@Service("remoteDataOperateWhpsb")
public class RemoteDataOperateWhpsb implements IDataOperateWhpsb {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("innerMessageService")
	private IPrefixMessageService msgs;
	
	@Autowired
	@Qualifier("answerCodeService")
	private AnswerCodeService exceptionService;
	
	@Override
	public List<Whpsb_RequestZhxx_Detail> getWhpsb_RequestZhxxList(Br51_cxqq_mx br51_cxqq_mx) throws DataOperateException, RemoteAccessException {
		
		List<Whpsb_RequestZhxx_Detail> requestzhxx = new ArrayList<Whpsb_RequestZhxx_Detail>();
		String credentialType = br51_cxqq_mx.getZzlx();// 证件类型
		String credentialNumber = br51_cxqq_mx.getZzhm();// 证件号码
		String subjectName = br51_cxqq_mx.getMc();// 对象名称
		// 主账户基本信息列表
		List<ContractAccount> contractAccounts = msgs.queryContractAccountList(credentialType, credentialNumber, subjectName);
		if (contractAccounts == null || contractAccounts.size() == 0) {
			return null;
		}
		int zhxh = 1;
		for (ContractAccount account : contractAccounts) {
			String contractType = account.getContractType();
			// CARD-借记卡、CAAC-活期存款、MMDP-定期存款
			if ("CARD".equals(contractType) || "CAAC".equals(contractType) || "MMDP".equals(contractType)) {
				//主账户明细信息
				AccountDetail accountDetail = msgs.queryAccountDetail(account.getAccountNumber(), account.getCurrency(), null);
				Whpsb_RequestZhxx_Detail acc = new Whpsb_RequestZhxx_Detail();
				if (accountDetail != null) {//数据项待调整细节
					zhxh++;
					acc.setZhxh(zhxh + "");//帐户序号 
					acc.setZh(accountDetail.getAccountNumber());//开户账号
					acc.setZhlb(this.getZhlb(accountDetail.getAccountAttr().trim()));//账户类别 
					acc.setZhzt(accountDetail.getAccountStatus());//账户状态
					acc.setYhmc(accountDetail.getAccountOpenBranch());//开户银行名称
					acc.setKhwddm(accountDetail.getAccountOpenBranch());//开户银行代码
					acc.setKhrq(accountDetail.getCardOpenDate());//开户日期
					acc.setXhrq(accountDetail.getAccountClosingDate());//销户日期
					acc.setCxsj(Utility.currDateTime19());//查询时间
					acc.setHbzl(accountDetail.getCurrency());//币种 
					acc.setZhye(accountDetail.getAccountBalance());//帐户余额
					acc.setKyye(accountDetail.getAvailableBalance());//账户可用余额
				}
				requestzhxx.add(acc);
				//对私客户返回关联子账户
				if ("1".equals(accountDetail.getCustomerType())) {// 1:个人客户、2:对公客户、3:同业客户
					List<Whpsb_RequestZhxx_Detail> subacclist = getRequestZhxx_AccinfoList(account.getAccountNumber(), subjectName);
					requestzhxx.addAll(subacclist);
				}
			}
		}
		
		return requestzhxx;
	}
	
	@Override
	public Whpsb_RequestCkrzl_Detail getWhpsb_RequestCkzlList(Br51_cxqq_mx br51_cxqq_mx) throws DataOperateException, RemoteAccessException {
		
		Whpsb_RequestCkrzl_Detail zhcyr = new Whpsb_RequestCkrzl_Detail();
		String qrymode = br51_cxqq_mx.getQrymode();//grzhcyrcx:个人账（卡）号持有人资料查询 dwzhcyrcx:单位账（卡）号持有人资料查询
		String PrimaryNum = br51_cxqq_mx.getZh();//主账户（账卡号）
		if (qrymode.equals(WhpsbConstants.CODE_DW_ZHCYR)) {//对公
			CorporateCustomer info = msgs.queryCorporateCustomerInfo(PrimaryNum);
			if (info != null) {
				zhcyr.setCkrxm(info.getChineseName());//对象名称
				zhcyr.setZzhm(info.getOpenIDNumber());//证件号码
				zhcyr.setZzlx(info.getOpenIDType());//证照类型
			} else {
				zhcyr = null;
				logger.info("=====［单位持有人资料］未查询到结果====账号：" + PrimaryNum);
			}
		} else {
			IndividualCustomer info = msgs.queryIndividualCustomerInfo(PrimaryNum);
			if (info != null&& info.getOpenIDInfo() != null) {
				CustomerIDInfo openIDInfo = info.getOpenIDInfo();//开户信息
				
				zhcyr.setCkrxm(info.getChineseName());//对象名称
				zhcyr.setZzhm(openIDInfo.getIdNumber());//证件号码
				zhcyr.setZzlx(openIDInfo.getIdType());//证照类型
			} else {
				zhcyr = null;
				logger.info("=====［个人持有人资料］未查询到结果====卡号：" + PrimaryNum);
			}
		}
		return zhcyr;
	}
	
	@Override
	public Whpsb_RequestKhzl_Detail getWhpsb_RequestKhzlList(Br51_cxqq_mx br51_cxqq_mx) throws DataOperateException, RemoteAccessException {
		
		Whpsb_RequestKhzl_Detail khzl = new Whpsb_RequestKhzl_Detail();
		String qrymode = br51_cxqq_mx.getQrymode();//grkhzlcx:个人开户资料查询 dwkhzlcx:单位开户资料查询
		String credentialType = br51_cxqq_mx.getZzlx();// 证件类型
		String credentialNumber = br51_cxqq_mx.getZzhm();// 证件号码
		String subjectName = br51_cxqq_mx.getMc();// 对象名称
		if (qrymode.equals(WhpsbConstants.CODE_DW_KHZL)) {//对公
			CorporateCustomer info = msgs.queryCorporateCustomerInfo(credentialType, credentialNumber, subjectName);
			if (info != null) {
				khzl.setDz(info.getRegisteredAddress());//单位地址 (取注册地址)
				khzl.setDh(info.getFixedLineNumber());//单位电话
				//-----单位独有------
				khzl.setLxr("");// 联系人
				if(info.getLegalInfo()!= null){
					CustomerHoldingInfo legalInfo = info.getLegalInfo();
					khzl.setFrdb(legalInfo.getName());// 法人代表
					khzl.setFrdbzjlx(legalInfo.getIdType());//法人证件类型
					khzl.setFrdbzjhm(legalInfo.getIdNumber());//法人证件号码
				}
				khzl.setGsyyzzh(info.getBusiIDNumber());//工商营业执照号
				khzl.setGsnsh(info.getStateTaxIDNumber());//国税纳税号
				khzl.setDsnsh(info.getLocalTaxIDNumber());//地税纳税号
				//--------共有---------
				khzl.setEmail(info.getEmailAddress());//email
				khzl.setKhwd(info.getOpenBank());//开户网点
				khzl.setKhwddm(info.getOpenBank());//开户网点代码
				khzl.setDbrxm("");// 代办人姓名
				khzl.setDbrzzlx("");//代办人证照类型
				khzl.setDbrzzh("");//代办人证照号码
			} else {
				khzl = null;
				logger.info("=====［单位开户资料］未查询到结果====证件类型：" + credentialType + "==证件号码：" + credentialNumber + "==名称：" + subjectName);
			}
		} else {
			IndividualCustomer info = msgs.queryIndividualCustomerInfo(credentialType, credentialNumber, subjectName);
			if (info != null) {
				khzl.setDz(info.getPermanentAddress());//住宅地址
				khzl.setDh(info.getFixedLineNumber());//住宅电话
				//-----个人独有------
				khzl.setGzdw("");//工作单位
				khzl.setDwdz("");//单位地址 
				khzl.setDwdh("");// 单位电话
				khzl.setLxdz(info.getPermanentAddress());//联系地址
				khzl.setLxdh(info.getFixedLineNumber());// 联系固话
				khzl.setLxsj(info.getTelephoneNumber());//联系手机
				khzl.setYzbm("");//邮政编码
				khzl.setTxdz(info.getMailingAddress());//通讯地址
				//--------共有---------
				khzl.setEmail(info.getEmailAddress());//email
				khzl.setKhwd(info.getOpenBank());//开户网点
				khzl.setKhwddm(info.getOpenBank());//开户网点代码
				khzl.setDbrxm("");// 代办人姓名
				khzl.setDbrzzlx("");//代办人证照类型
				khzl.setDbrzzh("");//代办人证照号码
			} else {
				khzl = null;
				logger.info("=====［个人开户资料］未查询到结果====证件类型：" + credentialType + "==证件号码：" + credentialNumber + "==名称：" + subjectName);
			}
		}
		return khzl;
	}
	
	@Override
	public List<Whpsb_RequestJymx_Detail> getWhpsb_RequestJymxList(Br51_cxqq_mx br51_cxqq_mx) throws DataOperateException, RemoteAccessException {
		List<Whpsb_RequestJymx_Detail> list = new ArrayList<Whpsb_RequestJymx_Detail>();
		try {
			String PrimaryNum = br51_cxqq_mx.getZh();//主账户（账卡号）
			String cxkssj = br51_cxqq_mx.getCxkssj();//查询起始时间
			String cxjssj = br51_cxqq_mx.getCxjssj();//查询截止时间
			if (cxjssj.isEmpty()) { //如果结束日期为空，取当前日期
				cxjssj = DtUtils.getNowDate();
			}
			if (cxkssj.isEmpty()) { //如果查询起始日期为空，取结束日期前一年的日期
				cxkssj = DtUtils.add(cxjssj, DtUtils.YEAR, -1, false);
			}
			//调用核心层接口，查询交易
			List<AccountTransaction> tansList = msgs.queryAccountTransaction(PrimaryNum, cxkssj, cxjssj);
			if (tansList != null && tansList.size() > 0) {
				for (AccountTransaction accountTransaction : tansList) {
					Whpsb_RequestJymx_Detail jymx = new Whpsb_RequestJymx_Detail();
					jymx.setAh(br51_cxqq_mx.getAh());//案号
					jymx.setZh(br51_cxqq_mx.getZh());//账卡号
					jymx.setCtac(br51_cxqq_mx.getZh());
					jymx.setTransseq(accountTransaction.getLogNumber());//资金往来序号,用日志号填充
					
					//交易时间处理
					String transactionDate = accountTransaction.getTradeDate(); // 交易日
					String transactionTime = accountTransaction.getTradeTime(); // 交易时间
					if (StringUtils.isBlank(transactionDate)) {
						transactionDate = "19700101";
					}
					if (StringUtils.isBlank(transactionTime)) {
						transactionTime = "000000";
					}
					jymx.setTransdata(transactionDate);//交易日期
					jymx.setTranstime(transactionTime);//交易时间
					jymx.setMatchaccou(accountTransaction.getRelativeNumber());//对方账号
					jymx.setMatchbankname(accountTransaction.getRelativeBank());//对方行名
					jymx.setMatchaccna(accountTransaction.getRelativeName());//对方户名
					jymx.setOppkm("");//对方科目名称
					jymx.setCurrency(accountTransaction.getTradeCurrency());//币种
					jymx.setAmt(accountTransaction.getTradeAmount());//交易金额
					jymx.setDebit_credit(accountTransaction.getDrcrFlag());//借贷标记
					jymx.setTranstype(accountTransaction.getTradeType());//交易种类
					jymx.setOrganname("");//交易网点
					jymx.setBanlance(accountTransaction.getAccountBalance());//账户余额
					jymx.setVoucher_no("");//传票号
					
					// 处理交易流水号（广发银行核心接口没有单独的交易流水号，所以用"[交易会计日] + [日志号] + [日志顺序号]"作为流水号。）
					String logNumber = accountTransaction.getLogNumber();
					String logNumberSeq = accountTransaction.getLogSeq();
					if (StringUtils.isNotBlank(logNumber) && StringUtils.isNotBlank(logNumberSeq)) {
						logNumberSeq = StringUtils.leftPad(logNumberSeq, 4, '0');
						jymx.setTransnum(transactionDate + logNumber + logNumberSeq); // 交易流水号
					}else{
						jymx.setTransnum("-");
					}
					
					jymx.setIp("");//ip地址
					jymx.setMac("");//MAC地址
					jymx.setTransremark(accountTransaction.getRemark());//备注
					
					list.add(jymx);
				}
			} else {
				list = null;
				logger.info("=====［交易明细查询］未查询到结果====账卡号：" + PrimaryNum + "==查询开始时间：" + cxkssj + "==查询结束时间：" + cxjssj);
			}
			
		} catch (DataOperateException e) {
			throw exceptionService.convertDataOperateException(e, ServerEnvironment.TASK_TYPE_WUHAN); // !important
		} catch (ParseException e) {
			logger.info("=====［交易明细查询］查询时间转换失败==");
			list = null;
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 根据主账户［账卡号］获取子帐户信息［对私客户用］
	 * 
	 * @param cardNumber
	 * @param acctName
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<Whpsb_RequestZhxx_Detail> getRequestZhxx_AccinfoList(String cardNumber, String acctName) throws DataOperateException, RemoteAccessException {
		List<Whpsb_RequestZhxx_Detail> accountInfoList = Lists.newArrayList();
		
		// 1、根据主账户查询子账户基本信息
		List<SubAccountInfo> subAccountInfos = msgs.querySubAccountInfoList(cardNumber);
		
		if (subAccountInfos == null || subAccountInfos.size() == 0) {
			return accountInfoList;
		}
		
		// 2、（循环）获取子账户详细信息
		for (SubAccountInfo subAccountInfo : subAccountInfos) {
			String accountNumber = subAccountInfo.getAccountNumber();
			String currency = subAccountInfo.getCurrency();
			String cashExCode = subAccountInfo.getCashExCode(); // 钞汇标志
			if (StringUtils.isBlank(accountNumber) || StringUtils.isBlank(currency)) {
				continue;
			}
			// 远程请求
			AccountDetail subAccountDetail = msgs.queryAccountDetail(accountNumber, currency,cashExCode);
			// DTO赋值转换
			Whpsb_RequestZhxx_Detail accountInfo = new Whpsb_RequestZhxx_Detail();
			accountInfo.setZhxh(subAccountInfo.getAccountSerial());//帐户序号 ----原来填币种，接口升级后有序号了
			accountInfo.setZh(subAccountDetail.getAccountNumber());//开户账号
			accountInfo.setZhlb(this.getZhlb(subAccountDetail.getAccountAttr().trim()));//账户类别  
			accountInfo.setZhzt(subAccountDetail.getAccountStatus());//帐户状态 0-正常；1-冻结；2-注销
			accountInfo.setYhmc(subAccountDetail.getAccountOpenBranch());//开户银行名称
			accountInfo.setKhwddm(subAccountDetail.getAccountOpenBranch());//开户银行代码
			accountInfo.setKhrq(subAccountDetail.getCardOpenDate());//开户日期
			accountInfo.setXhrq(subAccountDetail.getAccountClosingDate());//销户日期
			accountInfo.setCxsj(Utility.currDateTime19());//查询时间
			accountInfo.setHbzl(currency);//币种 
			accountInfo.setZhye(subAccountDetail.getAccountBalance());//帐户余额
			accountInfo.setKyye(subAccountDetail.getAvailableBalance());//账户可用余额
			
			accountInfoList.add(accountInfo);
		}
		
		return accountInfoList;
	}
	
	/**
	 * 账户类别转码
	 * 
	 * @param code
	 * @return
	 */
	private String getZhlb(String code) {
		HashMap<String, String> zhlbMap = Maps.newHashMap();
		zhlbMap.put("11", "活期");
		zhlbMap.put("12", "定期");
		zhlbMap.put("26", "借记卡");
		zhlbMap.put("01", "内部户");
		zhlbMap.put("13", "货款");
		zhlbMap.put("60", "同业");
		zhlbMap.put("99", "-");
		if ("".equals(code)) {//code不存在，返回“－”
			code = "99";
		}
		return zhlbMap.get(code);
	}
}
