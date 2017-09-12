package com.citic.server.gdjg.service;

import java.util.List;

import com.citic.server.gdjg.domain.Br57_cxqq_mx;
import com.citic.server.gdjg.domain.Br57_kzqq_input;
import com.citic.server.gdjg.domain.Br57_kzqq_mx;
import com.citic.server.gdjg.domain.Br57_kzqq_mx_policeman;
import com.citic.server.gdjg.domain.request.Gdjg_RequestBxxdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestCkdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestJrcpdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestLsdj;
import com.citic.server.gdjg.domain.request.Gdjg_Request_BankCx;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;

/**
 * 
 * @author liuxuanfei
 * @date 2017年5月24日 下午7:27:10
 */
public interface IDataOperateGdjg {
	/**
	 * 获取账户详细信息（包括账户、强制措施、共有权/优先权、子账户信息等）
	 * @param cxqq
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public Gdjg_Request_BankCx getAccountDetail(Br57_cxqq_mx br57_cxqq_mx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 获取交易明细
	 * 
	 * @param _obj
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public Gdjg_RequestLsdj getAccountTransaction(Br57_cxqq_mx br57_cxqq_mx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 获取保险箱信息
	 * 
	 * @param _obj
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public Gdjg_RequestBxxdj getSafeBoxInfo(Br57_cxqq_mx br57_cxqq_mx) throws DataOperateException, RemoteAccessException;
	/**
	 * 获取金融产品信息
	 * 
	 * @param _obj
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public Gdjg_Request_BankCx getJrcpInfo(Br57_cxqq_mx br57_cxqq_mx) throws DataOperateException, RemoteAccessException;
	
	 
	/**
	 * 获取冻结执行结果
	 * 
	 * @param _obj
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public  Gdjg_Request_BankCx getFreezeResult(Br57_kzqq_input br57_kzqq_input, Br57_kzqq_mx  br57_kzqq_mx, List<Br57_kzqq_mx_policeman>  mx_policeman) throws DataOperateException, RemoteAccessException;

	/**
	 * 获取解冻执行结果
	 * 
	 * @param _obj
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public  Gdjg_Request_BankCx getUnfreezeResult(Br57_kzqq_input br57_kzqq_input,Br57_kzqq_mx  br57_kzqq_mx, List<Br57_kzqq_mx_policeman>  mx_policeman) throws DataOperateException, RemoteAccessException;
	/**
	 * 获取紧急止付执行结果
	 * 
	 * @param _obj
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public  Gdjg_Request_BankCx getStopPaymentResult(Br57_kzqq_input br57_kzqq_input,Br57_kzqq_mx  br57_kzqq_mx, List<Br57_kzqq_mx_policeman>  mx_policeman) throws DataOperateException, RemoteAccessException;	
} 
