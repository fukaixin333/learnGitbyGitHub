package com.citic.server.gdjc.service;
import com.citic.server.gdjc.domain.Br50_cxqq_mx;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj_Bank;
import com.citic.server.gdjc.domain.request.Gdjc_RequestLsdj_Acc;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;

/**
 * @author Liu Xuanfei
 * @date 2016年8月17日 下午2:39:03
 */
public interface IDataOperateGdjc {
	
	/**
	 * 获取账户详细信息（包括卡、账户、子账户信息）
	 * 对于对私账户，返回主账户及子账户信息，对于对公账户，返回账户信息
	 * @param cxqq
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public Gdjc_RequestCkdj_Bank getAccountDetail(Br50_cxqq_mx br50_cxqq_mx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 获取账户交易明细
	 * 
	 * @param _obj
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public Gdjc_RequestLsdj_Acc getAccountTransaction(Br50_cxqq_mx br50_cxqq_mx) throws DataOperateException, RemoteAccessException;
	/**
	 * 三证查询，暂不使用，备用，宏程
	 * @param br50_cxqq_mx
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	
//	public Gdjc_RequestLsdj_Acc getAccountTransactionByID(Br50_cxqq_mx br50_cxqq_mx) throws DataOperateException, RemoteAccessException;
}
