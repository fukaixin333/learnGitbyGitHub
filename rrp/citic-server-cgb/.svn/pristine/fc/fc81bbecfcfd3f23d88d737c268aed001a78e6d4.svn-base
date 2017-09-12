package com.citic.server.inner.service;

import java.util.List;

import com.citic.server.inner.domain.AccountFrozenMeasure;
import com.citic.server.inner.domain.AccountTransaction;
import com.citic.server.inner.domain.ContractAccount;
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
import com.citic.server.inner.domain.response.V_AccountTransaction;
import com.citic.server.runtime.CgbKeys;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;

/**
 * 定长报文接口（Artery）
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/24 16:33:55$
 */
public interface IPrefixMessageService extends CgbKeys {
	
	/**
	 * 个人客户信息查询（025317）
	 * 
	 * @param in 查询条件
	 * @return 个人客户信息
	 * @throws DataOperateException 核心系统异常（错误码）
	 * @throws RemoteAccessException Artery平台运行时异常
	 */
	public IndividualCustomer queryIndividualCustomerInfo(Input025317 in) throws DataOperateException, RemoteAccessException;
	
	public IndividualCustomer queryIndividualCustomerInfo(String accountNumber) throws DataOperateException, RemoteAccessException;
	
	public IndividualCustomer queryIndividualCustomerInfo(String idType, String idNumber, String customerName) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 公司同业客户信息查询（025327）
	 * 
	 * @param in 查询条件
	 * @return 公司同业客户信息
	 * @throws DataOperateException 核心系统异常（错误码）
	 * @throws RemoteAccessException Artery平台运行时异常
	 */
	public CorporateCustomer queryCorporateCustomerInfo(Input025327 in) throws DataOperateException, RemoteAccessException;
	
	public CorporateCustomer queryCorporateCustomerInfo(String accountNumber) throws DataOperateException, RemoteAccessException;
	
	public CorporateCustomer queryCorporateCustomerInfo(String idType, String idNumber, String customerName) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 客户信息变更历史查询
	 * 
	 * @param in 查询条件
	 * @return 查询结果
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<CustomerUpdateInfo> queryCustomerInfoUpdateList(Input025890 in) throws DataOperateException, RemoteAccessException;
	
	public List<CustomerUpdateInfo> queryCustomerInfoUpdateList(String accountNumber, String beginDate, String endDate) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 合约账户查询（028100）
	 * 
	 * @param in 查询条件
	 * @return 查询结果
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<ContractAccount> queryContractAccountList(Input028100 in) throws DataOperateException, RemoteAccessException;
	
	public List<ContractAccount> queryContractAccountList(String idType, String idNumber, String customerName) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户校验及信息查询
	 * 
	 * @param  in 查询条件
	 * @return AccountVerifyInfo
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public AccountVerifyInfo queryAccountVerifyInfo(Input358040 in) throws DataOperateException, RemoteAccessException;
	
	public AccountVerifyInfo queryAccountVerifyInfo(String accountNumber, String currency, String cashExCode) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户信息查询（358080）
	 * 
	 * @param in 查询条件
	 * @return 查询结果
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public AccountDetail queryAccountDetail(Input358080 in) throws DataOperateException, RemoteAccessException;
	
	public AccountDetail queryAccountDetail(String accountNumber, String currency, String cashExCode) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 冻结在先查询（267570）
	 * 
	 * @param in 查询条件
	 * @return 查询结果
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<AccountFrozenMeasure> queryAccountFrozenMeasures(Input267570 in) throws DataOperateException, RemoteAccessException;
	
	public List<AccountFrozenMeasure> queryAccountFrozenMeasures(String accountNumber) throws DataOperateException, RemoteAccessException;
	
	/**
	 * @param frozenNumber（267530）
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public AccountFrozenInfo queryFrozenInfoByFrozenNumber(String frozenNumber) throws DataOperateException, RemoteAccessException;
	
	public AccountFrozenInfo queryFrozenInfoByFrozenNumber(Input267530 in) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户明细列表查询（267880）
	 * 
	 * @param in 查询条件
	 * @return 查询结果
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<SubAccountInfo> querySubAccountInfoList(Input267880 in) throws DataOperateException, RemoteAccessException;
	
	public List<SubAccountInfo> querySubAccountInfoList(String accountNumber) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 质押和授信类保证金查询（265561）
	 * （不支持非标准账号）
	 * 
	 * @param accountNumber
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public OwnershipInfo queryOwnershipInfo(Input265561 in) throws DataOperateException, RemoteAccessException;
	
	public OwnershipInfo queryOwnershipInfo(String accountNumber) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 交易历史查询（998070）
	 * 
	 * @param in 查询条件
	 * @return 查询结果
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public V_AccountTransaction queryAccountTransaction(Input998070 in) throws DataOperateException, RemoteAccessException;
	
	public List<AccountTransaction> queryAccountTransaction(String accountNumber, String startDate, String endDate) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户冻结
	 * 
	 * @param in 账户冻结请求信息
	 * @return 账户冻结结果信息
	 * @throws DataOperateException 核心系统异常（错误码）
	 * @throws RemoteAccessException Artery平台运行时异常
	 */
	public FreezeResult freezeAccount(Input267500 in) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户解冻
	 * 
	 * @param 账户冻结请求信息
	 * @return 账户冻结结果信息
	 * @throws DataOperateException 核心系统异常（错误码）
	 * @throws RemoteAccessException Artery平台运行时异常
	 */
	public UnfreezeResult unfreezeAccount(Input267510 in) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户冻结延期
	 * 
	 * @param 账户冻结延期请求信息
	 * @return 账户冻结延期结果信息
	 * @throws DataOperateException 核心系统异常（错误码）
	 * @throws RemoteAccessException Artery平台运行时异常
	 */
	public DeferFreezeResult deferFreezeAccount(Input267540 in) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 核心行内扣划
	 * 
	 * @param 账户扣划请求信息
	 * @return 扣划结果信息
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public DeductResult deductAccount(Input267580 in) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 二代行外扣划
	 * 
	 * @param 二代扣划请求信息
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public CargoRecord externalTransfer(InputPSB120 in) throws DataOperateException, RemoteAccessException;
	
	public CargoRecord queryCargoRecord(InputPSB411 in) throws DataOperateException, RemoteAccessException;
	
}
