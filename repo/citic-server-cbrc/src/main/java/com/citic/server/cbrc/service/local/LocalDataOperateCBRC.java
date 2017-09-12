/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月18日
 * Description:
 * =========================================================
 */
package com.citic.server.cbrc.service.local;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.cbrc.domain.Br40_cxqq_mx;
import com.citic.server.cbrc.domain.Br41_kzqq;
import com.citic.server.cbrc.domain.request.CBRC_ControlRequest_Record;
import com.citic.server.cbrc.domain.request.CBRC_FreezeRequest_Record;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Account;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Customer;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_SubAccount;
import com.citic.server.cbrc.domain.request.CBRC_QueryRequest_Transaction;
import com.citic.server.cbrc.domain.request.CBRC_StopPaymentRequest_Recored;
import com.citic.server.cbrc.domain.response.CBRC_FreezeResponse_Account;
import com.citic.server.cbrc.domain.response.CBRC_StopPaymentResponse_Account;
import com.citic.server.cbrc.mapper.MM40_cxqq_cbrcMapper;
import com.citic.server.cbrc.service.IDataOperateCBRC;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.DtUtils;

/**
 * @author gaojx
 */
@Service("localDataOperateCBRC")
public class LocalDataOperateCBRC implements IDataOperateCBRC {
	
	/*
	 * (non-Javadoc)
	 * @see com.citic.server.dx.service.DataOperate#getBr24_card_infoList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Autowired
	private MM40_cxqq_cbrcMapper br40_cxqqMapper;
	
	/**
	 * 本地实现类：账户基本信息查询
	 */
	@Override
	public CBRC_QueryRequest_Customer getAccountDetail(Br40_cxqq_mx br40_cxqq_mx) throws DataOperateException, RemoteAccessException {
		/** 客户基本信息（包含客户信息、强回执措施信息列表、共有权/优先权列表、子账户列表、账户信息列表等） */
		CBRC_QueryRequest_Customer partyinfo = new CBRC_QueryRequest_Customer();
		
		List<CBRC_QueryRequest_Account> queryRequest_acctList = new ArrayList<CBRC_QueryRequest_Account>();
		List<CBRC_QueryRequest_SubAccount> subAcctList = new ArrayList<CBRC_QueryRequest_SubAccount>();
		//对公客户信息查询
		CBRC_QueryRequest_Customer _corpPartyInfo = new CBRC_QueryRequest_Customer();
		String qqdbs = br40_cxqq_mx.getQqdbs();//请求单标识
		String rwlsh = br40_cxqq_mx.getRwlsh();//任务流水号
		String tasktype = br40_cxqq_mx.getTasktype();//监管类别
		
		//1. 查询个人相关信息
		//查控主体类别：01个人/02对公
		if ("01".equals(br40_cxqq_mx.getZtlb())) {
			//1.1 个人信息按证件号码查询
			// 查询类型：01-按证照查询 02-按账卡号查询
			if ("01".equals(br40_cxqq_mx.getCxlx())) {
				//1.1.1 通过证件号码查询客户信息
				//通过证件号获取客户信息
				partyinfo = br40_cxqqMapper.getPartyMsg_IdNum(br40_cxqq_mx);
				String partyid = partyinfo.getParty_id();
				//1.1.2 通过客户号查询个人账户信息
				//通过客户号获取本地个人账户信息
				//queryRequest_acctList = br40_cxqqMapper.getLocalPrivateAcctInfo(partyid);
				queryRequest_acctList = br40_cxqqMapper.getLocalCardInfo(partyid);
				subAcctList = br40_cxqqMapper.getSubAccountList(partyid);
			}
			//1.2 个人信息按账卡号查询
			//查询类型：01-按证照查询 02-按账卡号查询
			else if ("02".equals(br40_cxqq_mx.getCxlx())) {
				//1.2.1 通过账卡号查询客户信息
				//通过卡号获取客户信息
				partyinfo = br40_cxqqMapper.getPartyMsg_CardId(br40_cxqq_mx);
				//String partyid = partyinfo.getParty_id();
				//1.2.2 通过客户信息查询对私账户信息
				//通过客户号获取本地个人账户信息
				//queryRequest_acctList = br40_cxqqMapper.getLocalPrivateAcctInfo(partyid);
				queryRequest_acctList = br40_cxqqMapper.getLocalCardInfo_byAcctNo(br40_cxqq_mx);
				subAcctList = br40_cxqqMapper.getSubAccountList_byAcctNo(br40_cxqq_mx.getCxzh());
			}
		}
		//2. 查询对公相关信息
		//对公
		else {
			//2.1 对公信息按证件号码查询
			// 查询类型：01-按证照查询 02-按账卡号查询
			if ("01".equals(br40_cxqq_mx.getCxlx())) {
				//2.1.1 通过对公证件号码查询客户信息
				//通过证件号获取客户号
				partyinfo = br40_cxqqMapper.getPartyMsg_IdNum(br40_cxqq_mx);
				String partyid = partyinfo.getParty_id();
				//2.1.2 通过客户号查询对公客户法人相关信息
				//获取对公客户的法人等信息
				_corpPartyInfo = br40_cxqqMapper.getCorpPartyInfo(partyid);
				if (_corpPartyInfo != null) {
					partyinfo.setFrdb(_corpPartyInfo.getFrdb());//法人代表
					partyinfo.setFrdbzjlx(_corpPartyInfo.getFrdbzjlx());//法人代表证件类型
					partyinfo.setFrdbzjhm(_corpPartyInfo.getFrdbzjhm());//法人代表证件号码
					partyinfo.setKhgszzhm(_corpPartyInfo.getKhgszzhm());//客户工商执照号码
					partyinfo.setGsnsh(_corpPartyInfo.getGsnsh());//国税纳税号
					partyinfo.setDsnsh(_corpPartyInfo.getDsnsh());//地税纳税号
				}
				//2.1.3通过客户号查询对公账户信息
				//通过客户号获取本地对公账户信息
				queryRequest_acctList = br40_cxqqMapper.getLocalPublicAcctInfo(partyid);
				//subAcctList = br40_cxqqMapper.getSubAccountList(partyid);
			}
			//2.2 对公信息按账卡号查询
			//查询类型：01-按证照查询 02-按账卡号查询
			else if ("02".equals(br40_cxqq_mx.getCxlx())) {
				//2.2.1 通过对公账卡号查询客户信息
				//通过卡号获取客户信息
				partyinfo = br40_cxqqMapper.getPartyMsg_AcctId(br40_cxqq_mx);
				String partyid = partyinfo.getParty_id();
				//2.2.2 通过客户号查询对公客户信息，取法人相关信息
				//获取对公客户的法人等信息
				_corpPartyInfo = br40_cxqqMapper.getCorpPartyInfo(partyid);
				if (_corpPartyInfo != null) {
					partyinfo.setFrdb(_corpPartyInfo.getFrdb());//法人代表
					partyinfo.setFrdbzjlx(_corpPartyInfo.getFrdbzjlx());//法人代表证件类型
					partyinfo.setFrdbzjhm(_corpPartyInfo.getFrdbzjhm());//法人代表证件号码
					partyinfo.setKhgszzhm(_corpPartyInfo.getKhgszzhm());//客户工商执照号码
					partyinfo.setGsnsh(_corpPartyInfo.getGsnsh());//国税纳税号
					partyinfo.setDsnsh(_corpPartyInfo.getDsnsh());//地税纳税号
				}
				//2.2.3 通过客户号查询对公账户信息
				//通过客户号获取本地对公账户信息
				queryRequest_acctList = br40_cxqqMapper.getLocalPublicAcctInfo_byAcctNo(br40_cxqq_mx.getCxzh());
				//subAcctList = br40_cxqqMapper.getSubAccountList_byAcctNo(br40_cxqq_mx.getCxzh());
			}
		}
		
		//3. 给账户信息赋值	
		//账户信息、共有权/优先权、子账户、强制措施
		for (CBRC_QueryRequest_Account _queryRequest_acct : queryRequest_acctList) {
			//账号
			_queryRequest_acct.setQqdbs(qqdbs);//请求单标识
			_queryRequest_acct.setTasktype(tasktype);//监管类别
			_queryRequest_acct.setRwlsh(rwlsh);//任务流水号
		}
		
		for (CBRC_QueryRequest_SubAccount _subAcct : subAcctList) {
			//账号
			_subAcct.setQqdbs(qqdbs);//请求单标识
			_subAcct.setTasktype(tasktype);//监管类别
			_subAcct.setRwlsh(rwlsh);//任务流水号
			_subAcct.setTasktype(tasktype);//任务类别
		}
		
		//4. 账户信息结果集存入对象中
		partyinfo.setAccountList(queryRequest_acctList);
		partyinfo.setSubAccountList(subAcctList);
		return partyinfo;
	}
	
	/**
	 * 账户交易明细
	 */
	@Override
	public List<CBRC_QueryRequest_Transaction> getAccountTransaction(Br40_cxqq_mx cxqq) throws DataOperateException, RemoteAccessException {
		/** 账户信息及交易明细 */
		CBRC_QueryRequest_Account queryRequest_acct = new CBRC_QueryRequest_Account();
		//获取交易明细并插入
		List<CBRC_QueryRequest_Transaction> queryRequest_transList = new ArrayList<CBRC_QueryRequest_Transaction>();
		
		String qqdbs = cxqq.getQqdbs();//请求单标识
		String rwlsh = cxqq.getRwlsh();//任务流水号
		String tasktype = cxqq.getTasktype();//监管类别
		/**
		 * 赘述：
		 * 无需判断主体类别（01对私，02对公）的原因是，在SQL中已做处理；
		 * 查询交易明细时，目前只用按账卡号查询，故查询类型未02按账卡号查询；
		 */
		//1. 账卡号查询交易明细
		//查询类型：01-按证照查询 02-按账卡号查询
		if ("02".equals(cxqq.getCxlx())) {
			//1.1 通过条件查询账户交易明细
			queryRequest_transList = br40_cxqqMapper.getAcct_TransList(cxqq);
		}
		//查询交易明细，只用账卡号查询，没有证件查询，
		else {
			CBRC_QueryRequest_Customer partyinfo = br40_cxqqMapper.getPartyMsg_IdNum(cxqq);
			String partyid = partyinfo.getParty_id();
			cxqq.setPartyid(partyid);
			queryRequest_transList = br40_cxqqMapper.getAcct_TransList(cxqq);
		}
		
		//2. 结果集中赋值
		int i = 0;
		String currTime = Utility.currDateTime14();
		for (CBRC_QueryRequest_Transaction queryRequest_trans : queryRequest_transList) {
			queryRequest_trans.setQqdbs(qqdbs);//请求单标识
			queryRequest_trans.setTasktype(tasktype);//监管类别
			queryRequest_trans.setRwlsh(rwlsh);//任务流水号
			queryRequest_trans.setQrydt(DtUtils.getNowDate());
			queryRequest_trans.setJywdmc(queryRequest_trans.getJywddm());
			String jysfcg = queryRequest_trans.getJysfcg();
			try {
				queryRequest_trans.setJysj(Utility.toDateTime14(queryRequest_trans.getJysj()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(null != jysfcg && jysfcg.equals("00")) {
				queryRequest_trans.setJysfcg("01");
			} else {
				queryRequest_trans.setJysfcg("02");
			}
			queryRequest_trans.setTransseq(currTime + "_" + (i + 1));
			i++;
		}
		return queryRequest_transList;
	}
	
	@Override
	public CBRC_QueryRequest_Customer getAccountDetailAndTransaction(Br40_cxqq_mx br40_cxqq_mx) throws DataOperateException, RemoteAccessException {
		/** 客户基本信息（包含客户信息、强回执措施信息列表、共有权/优先权列表、子账户列表、账户信息列表等） */
		CBRC_QueryRequest_Customer partyinfo = new CBRC_QueryRequest_Customer();
		
		List<CBRC_QueryRequest_Account> queryRequest_acctList = new ArrayList<CBRC_QueryRequest_Account>();
		List<CBRC_QueryRequest_SubAccount> subAcctList = new ArrayList<CBRC_QueryRequest_SubAccount>();
		
		//对公客户信息查询
		CBRC_QueryRequest_Customer _corpPartyInfo = new CBRC_QueryRequest_Customer();
		String qqdbs = br40_cxqq_mx.getQqdbs();//请求单标识
		String rwlsh = br40_cxqq_mx.getRwlsh();//任务流水号
		String tasktype = br40_cxqq_mx.getTasktype();//监管类别
		
		//1. 查询个人相关信息
		//查控主体类别：01个人/02对公
		if ("01".equals(br40_cxqq_mx.getZtlb())) {
			//1.1 个人信息按证件号码查询
			// 查询类型：01-按证照查询 02-按账卡号查询
			/*partyinfo = br40_cxqqMapper.getPartyMsg_IdNum(br40_cxqq_mx);
			if(null == partyinfo || partyinfo.getParty_id().equals("")) {
				partyinfo = br40_cxqqMapper.getPartyMsg_CardId(br40_cxqq_mx);
			}
			String partyid = partyinfo.getParty_id();
			//1.1.2 通过客户号查询个人账户信息
			//通过客户号获取本地个人账户信息
			queryRequest_acctList = br40_cxqqMapper.getLocalPrivateAcctInfo(partyid);
			subAcctList = br40_cxqqMapper.getSubAccountList_byAcctNo(br40_cxqq_mx.getCxzh());*/
			
			if ("01".equals(br40_cxqq_mx.getCxlx())) {
				//1.1.1 通过证件号码查询客户信息
				//通过证件号获取客户信息
				partyinfo = br40_cxqqMapper.getPartyMsg_IdNum(br40_cxqq_mx);
				String partyid = partyinfo.getParty_id();
				//1.1.2 通过客户号查询个人账户信息
				//通过客户号获取本地个人账户信息
				//queryRequest_acctList = br40_cxqqMapper.getLocalPrivateAcctInfo(partyid);
				queryRequest_acctList = br40_cxqqMapper.getLocalCardInfo(partyid);
				subAcctList = br40_cxqqMapper.getSubAccountList(partyid);
			}
			//1.2 个人信息按账卡号查询
			//查询类型：01-按证照查询 02-按账卡号查询
			else if ("02".equals(br40_cxqq_mx.getCxlx())) {
				//1.2.1 通过账卡号查询客户信息
				//通过卡号获取客户信息
				partyinfo = br40_cxqqMapper.getPartyMsg_CardId(br40_cxqq_mx);
				//String partyid = partyinfo.getParty_id();
				//1.2.2 通过客户信息查询对私账户信息
				//通过客户号获取本地个人账户信息
				//queryRequest_acctList = br40_cxqqMapper.getLocalPrivateAcctInfo(partyid);
				queryRequest_acctList = br40_cxqqMapper.getLocalCardInfo_byAcctNo(br40_cxqq_mx);
				subAcctList = br40_cxqqMapper.getSubAccountList_byAcctNo(br40_cxqq_mx.getCxzh());
			}
		}
		//2. 查询对公相关信息
		//对公
		else {
			//2.1 对公信息按证件号码查询
			// 查询类型：01-按证照查询 02-按账卡号查询
			/*partyinfo = br40_cxqqMapper.getPartyMsg_IdNum(br40_cxqq_mx);
			if(null == partyinfo || partyinfo.getParty_id().equals("")) {
				partyinfo = br40_cxqqMapper.getPartyMsg_AcctId(br40_cxqq_mx);
			}
			String partyid = partyinfo.getParty_id();
			//2.1.2 通过客户号查询对公客户法人相关信息
			//获取对公客户的法人等信息
			_corpPartyInfo = br40_cxqqMapper.getCorpPartyInfo(partyid);
			if (_corpPartyInfo != null) {
				partyinfo.setFrdb(_corpPartyInfo.getFrdb());//法人代表
				partyinfo.setFrdbzjlx(_corpPartyInfo.getFrdbzjlx());//法人代表证件类型
				partyinfo.setFrdbzjhm(_corpPartyInfo.getFrdbzjhm());//法人代表证件号码
				partyinfo.setKhgszzhm(_corpPartyInfo.getKhgszzhm());//客户工商执照号码
				partyinfo.setGsnsh(_corpPartyInfo.getGsnsh());//国税纳税号
				partyinfo.setDsnsh(_corpPartyInfo.getDsnsh());//地税纳税号
			}
			//2.1.3通过客户号查询对公账户信息
			//通过客户号获取本地对公账户信息
			queryRequest_acctList = br40_cxqqMapper.getLocalPublicAcctInfo(partyid);*/
			
			//2.1 对公信息按证件号码查询
			// 查询类型：01-按证照查询 02-按账卡号查询
			if ("01".equals(br40_cxqq_mx.getCxlx())) {
				//2.1.1 通过对公证件号码查询客户信息
				//通过证件号获取客户号
				partyinfo = br40_cxqqMapper.getPartyMsg_IdNum(br40_cxqq_mx);
				String partyid = partyinfo.getParty_id();
				//2.1.2 通过客户号查询对公客户法人相关信息
				//获取对公客户的法人等信息
				_corpPartyInfo = br40_cxqqMapper.getCorpPartyInfo(partyid);
				if (_corpPartyInfo != null) {
					partyinfo.setFrdb(_corpPartyInfo.getFrdb());//法人代表
					partyinfo.setFrdbzjlx(_corpPartyInfo.getFrdbzjlx());//法人代表证件类型
					partyinfo.setFrdbzjhm(_corpPartyInfo.getFrdbzjhm());//法人代表证件号码
					partyinfo.setKhgszzhm(_corpPartyInfo.getKhgszzhm());//客户工商执照号码
					partyinfo.setGsnsh(_corpPartyInfo.getGsnsh());//国税纳税号
					partyinfo.setDsnsh(_corpPartyInfo.getDsnsh());//地税纳税号
				}
				//2.1.3通过客户号查询对公账户信息
				//通过客户号获取本地对公账户信息
				queryRequest_acctList = br40_cxqqMapper.getLocalPublicAcctInfo(partyid);
			}
			//2.2 对公信息按账卡号查询
			//查询类型：01-按证照查询 02-按账卡号查询
			else if ("02".equals(br40_cxqq_mx.getCxlx())) {
				//2.2.1 通过对公账卡号查询客户信息
				//通过卡号获取客户信息
				partyinfo = br40_cxqqMapper.getPartyMsg_AcctId(br40_cxqq_mx);
				String partyid = partyinfo.getParty_id();
				//2.2.2 通过客户号查询对公客户信息，取法人相关信息
				//获取对公客户的法人等信息
				_corpPartyInfo = br40_cxqqMapper.getCorpPartyInfo(partyid);
				if (_corpPartyInfo != null) {
					partyinfo.setFrdb(_corpPartyInfo.getFrdb());//法人代表
					partyinfo.setFrdbzjlx(_corpPartyInfo.getFrdbzjlx());//法人代表证件类型
					partyinfo.setFrdbzjhm(_corpPartyInfo.getFrdbzjhm());//法人代表证件号码
					partyinfo.setKhgszzhm(_corpPartyInfo.getKhgszzhm());//客户工商执照号码
					partyinfo.setGsnsh(_corpPartyInfo.getGsnsh());//国税纳税号
					partyinfo.setDsnsh(_corpPartyInfo.getDsnsh());//地税纳税号
				}
				//2.2.3 通过客户号查询对公账户信息
				//通过客户号获取本地对公账户信息
				queryRequest_acctList = br40_cxqqMapper.getLocalPublicAcctInfo_byAcctNo(br40_cxqq_mx.getCxzh());
			}
		}
		
		//3. 给账户信息赋值	
		//账户信息、共有权/优先权、子账户、强制措施
		for (CBRC_QueryRequest_Account _queryRequest_acct : queryRequest_acctList) {
			//账号
			_queryRequest_acct.setQqdbs(qqdbs);//请求单标识
			_queryRequest_acct.setTasktype(tasktype);//监管类别
			_queryRequest_acct.setRwlsh(rwlsh);//任务流水号
		}
		
		//获取交易明细并插入
		List<CBRC_QueryRequest_Transaction> queryRequest_transList = new ArrayList<CBRC_QueryRequest_Transaction>();
		
		//String qqdbs = cxqq.getQqdbs();//请求单标识
		//String rwlsh = cxqq.getRwlsh();//任务流水号
		//String tasktype = cxqq.getTasktype();//监管类别
		/**
		 * 赘述：
		 * 无需判断主体类别（01对私，02对公）的原因是，在SQL中已做处理；
		 * 查询交易明细时，目前只用按账卡号查询，故查询类型未02按账卡号查询；
		 */
		//1. 账卡号查询交易明细
		//查询类型：01-按证照查询 02-按账卡号查询
		if ("02".equals(br40_cxqq_mx.getCxlx())) {
			//1.1 通过条件查询账户交易明细
			queryRequest_transList = br40_cxqqMapper.getAcct_TransList(br40_cxqq_mx);
		}
		//查询交易明细，只用账卡号查询，没有证件查询，则排除证件查询交易明细的情况
		else {
			//queryRequest_transList = new ArrayList<CBRC_QueryRequest_Transaction>();
			CBRC_QueryRequest_Customer partyinfo1 = br40_cxqqMapper.getPartyMsg_IdNum(br40_cxqq_mx);
			String partyid = partyinfo1.getParty_id();
			br40_cxqq_mx.setPartyid(partyid);
			queryRequest_transList = br40_cxqqMapper.getAcct_TransList(br40_cxqq_mx);
		}
		
		//2. 结果集中赋值
		int i = 0;
		String currTime = Utility.currDateTime14();
		for (CBRC_QueryRequest_Transaction queryRequest_trans : queryRequest_transList) {
			queryRequest_trans.setQqdbs(qqdbs);//请求单标识
			queryRequest_trans.setTasktype(tasktype);//监管类别
			queryRequest_trans.setRwlsh(rwlsh);//任务流水号
			queryRequest_trans.setQrydt(DtUtils.getNowDate());
			queryRequest_trans.setJywdmc(queryRequest_trans.getJywddm());
			String jysfcg = queryRequest_trans.getJysfcg();
			try {
				queryRequest_trans.setJysj(Utility.toDateTime14(queryRequest_trans.getJysj()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(null != jysfcg && jysfcg.equals("00")) {
				queryRequest_trans.setJysfcg("01");
			} else {
				queryRequest_trans.setJysfcg("02");
			}
			queryRequest_trans.setTransseq(currTime + "_" + (i + 1));
			i++;
		}
		
		//关联子账户赋值
		for (CBRC_QueryRequest_SubAccount _subAcct : subAcctList) {
			//账号
			_subAcct.setQqdbs(qqdbs);//请求单标识
			_subAcct.setTasktype(tasktype);//监管类别
			_subAcct.setRwlsh(rwlsh);//任务流水号
			_subAcct.setTasktype(tasktype);//任务类别
		}
		
		partyinfo.setAccountList(queryRequest_acctList);
		partyinfo.setSubAccountList(subAcctList);
		partyinfo.setTransactionList(queryRequest_transList);
		return partyinfo;
	}
	
	@Override
	public CBRC_FreezeRequest_Record freezeAccount(Br41_kzqq kzqq, CBRC_FreezeResponse_Account res) throws DataOperateException, RemoteAccessException {
		return null;
	}
	
	@Override
	public CBRC_FreezeRequest_Record freezeAccountYQ(Br41_kzqq kzqq, CBRC_FreezeResponse_Account res) throws DataOperateException, RemoteAccessException {
		return null;
	}
	
	@Override
	public CBRC_FreezeRequest_Record freezeAccountJC(Br41_kzqq kzqq, CBRC_FreezeResponse_Account res) throws DataOperateException, RemoteAccessException {
		return null;
	}
	
	@Override
	public CBRC_StopPaymentRequest_Recored stoppayAccount(Br41_kzqq kzqq, CBRC_StopPaymentResponse_Account res) throws DataOperateException, RemoteAccessException {
		return null;
	}
	
	@Override
	public CBRC_StopPaymentRequest_Recored stoppayAccountJC(Br41_kzqq kzqq, CBRC_StopPaymentResponse_Account res) throws DataOperateException, RemoteAccessException {
		return null;
	}

	@Override
	public void sendTransMsg(List<CBRC_ControlRequest_Record> tranList,
			String telno) throws Exception {
		
	}


}
