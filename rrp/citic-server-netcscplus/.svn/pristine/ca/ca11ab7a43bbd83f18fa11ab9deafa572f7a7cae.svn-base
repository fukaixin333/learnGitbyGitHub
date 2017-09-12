/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月18日
 * Description:
 * =========================================================
 */
package com.citic.server.dx.service.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.SpringContextHolder;
import com.citic.server.dx.domain.AccountsTransaction;
import com.citic.server.dx.domain.Br24_account_freeze;
import com.citic.server.dx.domain.Br24_account_holder;
import com.citic.server.dx.domain.Br24_account_info;
import com.citic.server.dx.domain.Br24_account_right;
import com.citic.server.dx.domain.Br24_card_info;
import com.citic.server.dx.domain.Br24_q_Main;
import com.citic.server.dx.domain.Br24_trans_info;
import com.citic.server.dx.domain.Br25_Freeze;
import com.citic.server.dx.domain.Br25_Freeze_back;
import com.citic.server.dx.domain.Br25_StopPay;
import com.citic.server.dx.domain.Br25_StopPay_back;
import com.citic.server.dx.domain.OrganKeyQuery;
import com.citic.server.dx.domain.PartyQueryResult;
import com.citic.server.dx.service.DataOperate2;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.net.mapper.MM24_q_mainMapper;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.BB13_organ_telno;
import com.google.common.collect.Lists;

/**
 * @author gaojx
 */
@Service("localDataOperate2")
public class LocalDataOperate2 implements DataOperate2 {
	
	@Autowired
	private MM24_q_mainMapper mm24_q_mainMapper;
	@Autowired
	public MC00_common_Mapper common_Mapper;
	@Autowired
	private CacheService cacheService;
	
	@Override
	public List<Br24_account_info> getBr24_account_infoList(String cardNumber, String subjectType) throws DataOperateException {
		List<Br24_account_info> accountList = new ArrayList<Br24_account_info>();
		
		if ("1".equals(subjectType)) {//（1-自然人主体；2-法人主体）
			accountList = mm24_q_mainMapper.getAccountInfo_D(cardNumber);
		} else {
			accountList = mm24_q_mainMapper.getAccountInfo_C(cardNumber);
		}
		return accountList;
	}
	
	@Override
	public Br24_card_info getBr24_card_info(String cardNumber, String SubjectType) throws DataOperateException {
		Br24_card_info _card_info = new Br24_card_info();
		if ("1".equals(SubjectType)) {//（1-自然人主体；2-法人主体）
			_card_info = mm24_q_mainMapper.getCardInfo_D(cardNumber);
		} else {
			_card_info = mm24_q_mainMapper.getCardInfo_C(cardNumber);
		}
		
		if (_card_info == null)
			_card_info = new Br24_card_info();
		
		return _card_info;
	}
	
	public List<Br24_trans_info> getBr24_trans_infoList(String cardNumber, String inquiryPeriodStart, String inquiryPeriodEnd, String... args) throws DataOperateException {
		List<Br24_trans_info> transList = new ArrayList<Br24_trans_info>();
		Br24_q_Main cg_q_main = new Br24_q_Main();
		cg_q_main.setCardNumber(cardNumber);
		cg_q_main.setInquiryperiodstart(inquiryPeriodStart);
		cg_q_main.setInquiryperiodend(inquiryPeriodEnd);
		transList = mm24_q_mainMapper.getTransInfoList(cg_q_main);
		return transList;
	}
	
	@Override
	public Br24_account_holder getBr24_account_holder(String subjectType, String accountNumber) throws DataOperateException {
		Br24_account_holder _account_holder = new Br24_account_holder();
		Br24_account_holder _account_holder_corp = new Br24_account_holder();
		Br24_account_holder _holder = mm24_q_mainMapper.getParty_idbyCardnumber(accountNumber);
		
		if (_holder != null) {
			_account_holder = mm24_q_mainMapper.getCardInfo(_holder.getParty_id());
			_account_holder.setCardOperatorName(_holder.getCardOperatorName());
			_account_holder.setCardOperatorCredentialType(_holder.getCardOperatorCredentialType());
			_account_holder.setCardOperatorCredentialNumber(_holder.getCardOperatorCredentialNumber());
		} else {
			_holder = new Br24_account_holder();
		}
		if ("2".equals(subjectType)) {
			_account_holder_corp = mm24_q_mainMapper.getCard_CorpInfo(_holder.getParty_id());
			if (_account_holder_corp != null) {
				//法人代表
				_account_holder.setArtificialPersonRep(_account_holder_corp.getArtificialPersonRep());
				//法人代表证件类型
				_account_holder.setArtificialPersonRepCredentialType(_account_holder_corp.getCrit());
				//法人代表证件号码
				_account_holder.setArtificialPersonRepCredentialNumber(_account_holder_corp.getCrid());
				//客户工商执照号码
				_account_holder.setBusinessLicenseNumber(_account_holder_corp.getBusinessLicenseNumber());
				//国税纳税号
				_account_holder.setStateTaxSerial(_account_holder_corp.getStateTaxSerial());
				//地税纳税号
				_account_holder.setLocalTaxSerial(_account_holder_corp.getLocalTaxSerial());
			}
		}
		return _account_holder;
	}
	
	@Override
	public Br25_StopPay_back StopAccount(Br25_StopPay Br25_StopPay) {
		return null;
	}
	
	@Override
	public Br25_StopPay_back StopAccount_JC(Br25_StopPay Br25_StopPay) {
		return null;
	}
	
	@Override
	public Br25_StopPay_back StopAccount_YQ(Br25_StopPay Br25_StopPay) {
		return null;
	}
	
	@Override
	public Br25_Freeze_back frozenAccount(Br25_Freeze cg_Freeze) {
		return null;
	}
	
	@Override
	public Br25_Freeze_back frozenAccount_JC(Br25_Freeze cg_Freeze) {
		return null;
	}
	
	@Override
	public Br25_Freeze_back frozenAccount_YQ(Br25_Freeze cg_Freeze) {
		return null;
	}
	
	@Override
	public AccountsTransaction getAccountsTransactionByCardNumber(String cardNumber, String subjectType, String inquiryPeriodStart, String inquiryPeriodEnd)
		throws DataOperateException, RemoteAccessException {
		AccountsTransaction accountsTransaction = new AccountsTransaction();
		//账户
		accountsTransaction.setAccountInfoList(this.getBr24_account_infoList(cardNumber, subjectType));
		//插入交易
		List<Br24_trans_info> transList = this.getBr24_trans_infoList(cardNumber, inquiryPeriodStart, inquiryPeriodEnd);
		accountsTransaction.setTransInfoList(transList);
		return accountsTransaction;
	}
	
	@Override
	public OrganKeyQuery getOrgkeyByCard(String subjectType, String cardNumber, String acctname) throws DataOperateException, RemoteAccessException {
		OrganKeyQuery  organkeyquery=new OrganKeyQuery();
		try {
			List<OrganKeyQuery> organList = common_Mapper.getBb11_card_organ(cardNumber);	
			if(organList!=null&&organList.size()>0){
				organkeyquery=organList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteAccessException(e.getMessage());
		}
		return organkeyquery;
	}
	
	@Override
	public OrganKeyQuery getOrgkeyByCredentialNumber(String credentialtype, String credentialNumber, String acctname,String... args) throws DataOperateException, RemoteAccessException {
		OrganKeyQuery  organkeyquery=new OrganKeyQuery();
		try {
			List<OrganKeyQuery> organList = common_Mapper.getBb11_party_organ(credentialNumber);
			if(organList!=null&&organList.size()>0){
				organkeyquery=organList.get(0);
			}	
			HashMap orgMap=new  HashMap();
			String organkey_r="";
			if(args.length>0&&!organkeyquery.getOrgKey().equals("")){
				organkey_r = args[0];
				CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
				HashMap repOrgHash = (HashMap<String, Object>) cacheService.getCache("Mp02_repOrgMapDetail", HashMap.class);
				 orgMap=(HashMap)repOrgHash.get(organkey_r);
				 if(!orgMap.containsKey(organkeyquery.getOrgKey())){//客户归属机构不属于法人机构
					 String party_id=organkeyquery.getParty_id();
					 //查询客户下的账户
					 List<OrganKeyQuery> acctList = common_Mapper.getBb11_deposit_organ_bypartyid(party_id);
					 for(OrganKeyQuery accountDetail: acctList){
						 if(orgMap.containsKey(accountDetail.getOrgKey())){//该条账户信息属于该法人机构
							 organkeyquery=accountDetail;
							 break;
						 }
					 }
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteAccessException(e.getMessage());
		}
		return organkeyquery;
	}
	
	@Override
	public OrganKeyQuery getOrgkeyByAcctNumber(String acctNumber, String acctname) throws DataOperateException, RemoteAccessException {
		OrganKeyQuery  organkeyquery=new OrganKeyQuery();
		try {
			List<OrganKeyQuery> organList = common_Mapper.getBb11_card_organ(acctNumber);
			if(organList!=null&&organList.size()>0){
				organkeyquery=organList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteAccessException(e.getMessage());
		}
		return organkeyquery;
	}
	
	@Override
	public PartyQueryResult getPartyQuertResultList(String subjectType, String inquiryMode, String accountCredentialType, String acountCredentialNumber, String accountSubjectName, String... args)
		throws DataOperateException, RemoteAccessException {
		
		PartyQueryResult  partyqueryresult=new PartyQueryResult();
		try {
			List<Br24_card_info> br24_card_infoList = Lists.newArrayList();
			List<Br24_account_info> acct_infoList = Lists.newArrayList();
			List<Br24_account_freeze> acct_freezeList = Lists.newArrayList();
			List<Br24_account_right> acct_rightList = Lists.newArrayList();
			String party_id = mm24_q_mainMapper.getParty_id(acountCredentialNumber);
			if (party_id == null)
				party_id = "";
			if ("1".equals(subjectType)) {//（1-自然人主体；2-法人主体）
				br24_card_infoList = mm24_q_mainMapper.getCardInfobyPartidList_D(party_id);
				acct_infoList = mm24_q_mainMapper.getAccountInfoBypartyID_D(party_id);
				if (!"01".equals(inquiryMode)) {//查询内容(01-账户基本信息；02-账户信息(含强制措施、共有权/优先权信息)
					acct_rightList = mm24_q_mainMapper.getAccountRightList_D(party_id);
					acct_freezeList = mm24_q_mainMapper.getAccountFreezeList_D(party_id);
				}
			} else {
				br24_card_infoList = mm24_q_mainMapper.getCardInfobyPartidList_C(party_id);
				acct_infoList = mm24_q_mainMapper.getAccountInfoBypartyID_C(party_id);
				if (!"01".equals(inquiryMode)) {
					acct_rightList = mm24_q_mainMapper.getAccountRightList_C(party_id);
					acct_freezeList = mm24_q_mainMapper.getAccountFreezeList_C(party_id);
				}
			}
			partyqueryresult.setCard_infoList(br24_card_infoList);
			partyqueryresult.setAccountInfoList(acct_infoList);
			partyqueryresult.setFreezeList(acct_freezeList);
			partyqueryresult.setRightList(acct_rightList);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteAccessException(e.getMessage());
		}
		
		return partyqueryresult;
	}
	
	@Override
	public void SendMsg(List<BB13_organ_telno> bb13_organ_telnoList, String... arg) throws Exception {
		
	}
}
