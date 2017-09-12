/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月17日
 * Description:
 * =========================================================
 */
package com.citic.server.dx.service;

import java.util.List;

import com.citic.server.dx.domain.AccountsTransaction;
import com.citic.server.dx.domain.Br24_account_holder;
import com.citic.server.dx.domain.Br24_account_info;
import com.citic.server.dx.domain.Br24_card_info;
import com.citic.server.dx.domain.Br25_Freeze;
import com.citic.server.dx.domain.Br25_Freeze_back;
import com.citic.server.dx.domain.Br25_StopPay;
import com.citic.server.dx.domain.Br25_StopPay_back;
import com.citic.server.dx.domain.OrganKeyQuery;
import com.citic.server.dx.domain.PartyQueryResult;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.service.domain.BB13_organ_telno;

/**
 * 数据查询接口
 * 
 * @author gaojx
 */
public interface DataOperate2 {
	/**
	 * 通过客户证件号查询全账户信息，包括：客户卡(主账户)信息、（子）账户信息，当<em>inquiryMode = 02</em>时还需包含强制措施、共有权/优先权等信息。<br />
	 * 通常，查询主体为“法人主体（对公）”时，不执行查询子账户操作（无子账户）。
	 * <p>
	 * <strong>使用场景：</strong>客户全账户查询
	 * 
	 * @param subjectType 查询主体类别（1-自然人主体；2-法人主体）
	 * @param inquiryMode 查询内容（01-账户基本信息；02-账户信息）
	 * @param accountCredentialType 查询证照类型代码(见附录1)
	 * @param acountCredentialNumber 查询证照号码
	 * @param accountSubjectName 查询主体名称（个人姓名或公司名称）
	 * @return 该客户的所有卡折信息（含已销户的卡）
	 * @throws DataOperateException, RemoteAccessException
	 */
	public PartyQueryResult getPartyQuertResultList(String subjectType, String inquiryMode, String accountCredentialType, String acountCredentialNumber, String accountSubjectName, String... args)
		throws DataOperateException, RemoteAccessException;
	
	/**
	 * 查询指定客户的卡／折信息(主账户信息) 使用：账户交易明细查询
	 * 
	 * @param cardNumber 主账户(对私-卡/折号；对公-基本账户帐号)
	 * @param subjectType 查询主体类别（1-自然人主体；2-法人主体）
	 * @return
	 * @throws DataOperateException, RemoteAccessException
	 */
	public Br24_card_info getBr24_card_info(String cardNumber, String subjectType) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 查询指定卡折户的账户信息（一般(子)账户）列表信息 使用：客户全账户查询、账户交易明细查询、账户动态查询
	 * 
	 * @param cardNumber 主账户(对私-卡/折号；对公-基本账户帐号)
	 * @param subjectType 查询主体类别（1-自然人主体；2-法人主体）
	 * @return
	 * @throws DataOperateException, RemoteAccessException
	 */
	public List<Br24_account_info> getBr24_account_infoList(String cardNumber, String subjectType) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 通过卡/折号查询所有账户的交易流水
	 * <p>
	 * <strong>默认实现分三步骤：</strong>
	 * <ul>
	 * <li>1、通过卡/折号调用{@link #getBr24_account_infoList(String, String)}查询账户明细列表；
	 * <li>2、循环调用{@link #getBr24_trans_infoList(String, String, String)}查询账户的交易流水明细；
	 * <li>3、返回上述步骤的综合结果。
	 * </ul>
	 * 
	 * @param cardNumber 卡/折号
	 * @param subjectType 查询主体类别（1-自然人主体；2-法人主体）
	 * @param inquiryPeriodStart 交易流水起始查询日期
	 * @param inquiryPeriodEnd 交易流程截止询日期
	 * @return 指定卡/折号的账户列表及交易流水，交易流水默认至多返回1000条。
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public AccountsTransaction getAccountsTransactionByCardNumber(String cardNumber, String subjectType, String inquiryPeriodStart, String inquiryPeriodEnd)
		throws DataOperateException, RemoteAccessException;
	
	/**
	 * 持卡主体查询，
	 * 
	 * @param subjectType
	 *        查询主体类别（1-自然人主体；2-法人主体）
	 * @param accountNumber
	 *        查询账卡号
	 * @return
	 * @throws DataOperateException, RemoteAccessException 查询异常
	 */
	public Br24_account_holder getBr24_account_holder(String subjectType, String accountNumber) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 冻结账户
	 * 
	 * @param freezeRequst 冻结
	 * @return
	 * @throws RemoteAccessException
	 */
	public Br25_Freeze_back frozenAccount(Br25_Freeze cg_Freeze) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 冻结账户解除
	 * 
	 * @param freezeRequst 冻结解除
	 * @return
	 * @throws RemoteAccessException
	 */
	public Br25_Freeze_back frozenAccount_JC(Br25_Freeze cg_Freeze) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 冻结账户延期
	 * 
	 * @param freezeRequst 冻结延期
	 * @return
	 * @throws RemoteAccessException
	 */
	public Br25_Freeze_back frozenAccount_YQ(Br25_Freeze cg_Freeze) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 止付 延期 解除账户
	 * 
	 * @param Cg_StopPay
	 * @return
	 * @throws RemoteAccessException
	 */
	public Br25_StopPay_back StopAccount(Br25_StopPay cg_StopPay) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 止付解除账户
	 * 
	 * @param Cg_StopPay
	 * @return
	 * @throws RemoteAccessException
	 */
	public Br25_StopPay_back StopAccount_JC(Br25_StopPay cg_StopPay) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 止付 延期
	 * 
	 * @param Cg_StopPay
	 * @return
	 * @throws RemoteAccessException
	 */
	public Br25_StopPay_back StopAccount_YQ(Br25_StopPay cg_StopPay) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 通过卡折号查询机构
	 * 
	 * @param cardnumber: subjectType：1对私 2对公
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public OrganKeyQuery getOrgkeyByCard(String subjectType, String cardNumber, String acctname) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 通过证件类型和号码查询机构
	 * 
	 * @param credentialNumber:
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public OrganKeyQuery getOrgkeyByCredentialNumber(String credentialtype, String credentialNumber, String acctname, String... args) throws DataOperateException,
		RemoteAccessException;
	
	/**
	 * 通过账号查询机构
	 * 
	 * @param accountNumber
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public OrganKeyQuery getOrgkeyByAcctNumber(String accountNumber, String acctname) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 短信
	 * 
	 * @param
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public void SendMsg(List<BB13_organ_telno> bb13_organ_telnoList, String... arg) throws Exception;
	
}
