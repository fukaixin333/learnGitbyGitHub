package com.citic.server.dx.service;

import java.util.ArrayList;
import java.util.List;

import com.citic.server.dx.domain.AccountsTransaction;
import com.citic.server.dx.domain.Br24_account_freeze;
import com.citic.server.dx.domain.Br24_account_info;
import com.citic.server.dx.domain.Br24_account_right;
import com.citic.server.dx.domain.Br24_card_info;
import com.citic.server.dx.domain.Br24_trans_info;
import com.citic.server.dx.domain.PartyQueryResult;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;

/**
 * 因为各银行核心系统提供的接口不一致，所以部分实现逻辑会有区别。<br />
 * 那么，针对Task2任务处理逻辑应该提供最精简的接口，存在差异的让项目工程中的具体实现类完成。<br />
 * AbstractDataOperate只是将默认的（80%以上银行的）实现逻辑封装在产品工程中，特殊银行（另外20%）在项目工程具体实现类中重写这些方法即可。<br />
 * 说的更明白一点，就是将本身Task2处理逻辑分成两部分：不可重写逻辑（固定情况）、可重写逻辑（特殊情况）。
 * 
 * @author Liu Xuanfei
 * @date 2016年5月20日 下午3:31:41
 */
public abstract class AbstractDataOperate2 implements DataOperate2 {
	
	/**
	 * 查询（子）账户的交易流水（非卡/折号）
	 * <p>
	 * <strong>:此方法非Task2调用:</strong>
	 * 
	 * @param accountNumber 账号
	 * @param inquiryPeriodStart 交易流水起始查询日期
	 * @param inquiryPeriodEnd 交易流程截止询日期
	 * @return 指定卡折的交易流水，按照时间倒序，最多1000条
	 * @throws DataOperateException, RemoteAccessException
	 * @see #getAccountsTransactionByCardNumber(String, String, String)
	 */
	public abstract List<Br24_trans_info> getAccountTransaction(String accountNumber, String inquiryPeriodStart, String inquiryPeriodEnd) throws DataOperateException,
		RemoteAccessException;
	
	/**
	 * 根据证件信息查询主账户（对私-卡/折号；对公-基本账户帐号）详细信息
	 * <p>
	 * <strong>使用场景：</strong>客户全账户查询
	 * 
	 * @param subjectType 查询主体类别（1-自然人主体；2-法人主体）
	 * @param accountCredentialType 证照类型
	 * @param acountCredentialNumber 证照号码
	 * @param accountSubjectName 查询主体名称
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public abstract List<Br24_card_info> getPrimaryAccountDetails(String subjectType, String accountCredentialType, String acountCredentialNumber, String accountSubjectName, String... args)
		throws DataOperateException, RemoteAccessException;
	
	/**
	 * 根据卡/折号/对公基本账户查询在先冻结（强制措施）信息
	 * <p>
	 * <strong>使用场景：</strong>客户全账户查询
	 * 
	 * @param cardNumber 主账号（对私-卡/折号；对公-基本账户号）
	 * @return
	 * @throws DataOperateException, RemoteAccessException
	 */
	public abstract List<Br24_account_freeze> getFreezeMeasures(String cardNumber) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 根据卡/折号/对公基本账户查询权利信息
	 * <p>
	 * <strong>使用场景：</strong>客户全账户查询
	 * 
	 * @param cardNumber 主账号（对私-卡/折号；对公-基本账户号）
	 * @return
	 * @throws DataOperateException, RemoteAccessException
	 */
	public abstract List<Br24_account_right> getRightsPriorities(String cardNumber) throws DataOperateException, RemoteAccessException;
	
	// ==========================================================================================
	//                     默认实现逻辑
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
				return null;
			}
			
			// 2、（循环）查询（子）账户的交易流水
			transInfoList = new ArrayList<Br24_trans_info>();
			for (Br24_account_info accountInfo : accountInfoList) {
				String accountNumber = accountInfo.getAccountNumber();
				List<Br24_trans_info> accountTransList = getAccountTransaction(accountNumber, inquiryPeriodStart, inquiryPeriodEnd);
				if (accountTransList != null && !accountTransList.isEmpty()) {
					transInfoList.addAll(accountTransList);
				}
			}
		} else if ("2".equals(subjectType)) { // 法人主体（对公）
			transInfoList = getAccountTransaction(cardNumber, inquiryPeriodStart, inquiryPeriodEnd);
		}
		
		return new AccountsTransaction(accountInfoList, transInfoList);
	}
	
	@Override
	public PartyQueryResult getPartyQuertResultList(String subjectType, String inquiryMode, String accountCredentialType, String acountCredentialNumber, String accountSubjectName, String... args)
		throws DataOperateException, RemoteAccessException {
		
		// 1、根据证件信息查询主账户（对私-卡/折号；对公-基本账户帐号）详细信息 
		List<Br24_card_info> cardInfoList = getPrimaryAccountDetails(subjectType, accountCredentialType, acountCredentialNumber, accountSubjectName, args);
		if (cardInfoList == null) {
			return null;
		}
		
		List<Br24_account_info> accountInfoList = null;
		List<Br24_account_freeze> accountFreezeList = null;
		List<Br24_account_right> accountRightsList = null;
		for (Br24_card_info cardInfo : cardInfoList) {
			String cardNumber = cardInfo.getCardNumber();
			
			if ("1".equals(subjectType)) { // 自然人主体（对私）
				// 2、（循环）根据主账户查询子账户详细信息
				List<Br24_account_info> infoList = getBr24_account_infoList(cardNumber, subjectType);
				if (infoList != null && !infoList.isEmpty()) {
					if (accountInfoList == null) {
						accountInfoList = new ArrayList<Br24_account_info>();
					}
					accountInfoList.addAll(infoList);
				}
			}
			
			if ("02".equals(inquiryMode)) {
				// 3、（循环）根据卡/折号/对公基本账户查询在先冻结（强制措施）信息
				List<Br24_account_freeze> freezeList = getFreezeMeasures(cardNumber);
				if (freezeList != null && !freezeList.isEmpty()) {
					if (accountFreezeList == null) {
						accountFreezeList = new ArrayList<Br24_account_freeze>();
					}
					accountFreezeList.addAll(freezeList);
				}
				
				// 4、（循环）根据卡/折号/对公基本账户查询权利信息
				List<Br24_account_right> rightsList = getRightsPriorities(cardNumber);
				if (rightsList != null && !rightsList.isEmpty()) {
					if (accountRightsList == null) {
						accountRightsList = new ArrayList<Br24_account_right>();
					}
					accountRightsList.addAll(rightsList);
				}
			}
		}
		
		return new PartyQueryResult(cardInfoList, accountInfoList, accountRightsList, accountFreezeList);
	}
}
