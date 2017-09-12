package com.citic.server.jsga.service;

import java.util.List;

import com.citic.server.jsga.domain.Br40_cxqq_mx;
import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.jsga.domain.request.JSGA_ControlRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Customer;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Transaction;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Recored;
import com.citic.server.jsga.domain.response.JSGA_FreezeResponse_Account;
import com.citic.server.jsga.domain.response.JSGA_StopPaymentResponse_Account;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;

/**
 * 数据加工接口
 * 
 * @author Liu Xuanfei
 * @date 2016年7月6日 上午10:31:28
 */
public interface IDataOperate12 {
	
	/**
	 * 获取账户详细信息（包括账户、强制措施、共有权/优先权、子账户信息等）
	 * 
	 * @param cxqq
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public JSGA_QueryRequest_Customer getAccountDetail(Br40_cxqq_mx cxqq) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 获取账户交易明细
	 * 
	 * @param _obj
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<JSGA_QueryRequest_Transaction> getAccountTransaction(Br40_cxqq_mx cxqq) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 获取账户详细信息以及交易明细
	 * 
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public JSGA_QueryRequest_Customer getAccountDetailAndTransaction(Br40_cxqq_mx cxqq) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户冻结
	 * 
	 * @param kzqq
	 * @param res
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public JSGA_FreezeRequest_Record freezeAccount(Br41_kzqq kzqq, JSGA_FreezeResponse_Account res) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户冻结延期
	 * 
	 * @param kzqq
	 * @param res
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public JSGA_FreezeRequest_Record freezeAccountYQ(Br41_kzqq kzqq, JSGA_FreezeResponse_Account res) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户解除冻结
	 * 
	 * @param kzqq
	 * @param res
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public JSGA_FreezeRequest_Record freezeAccountJC(Br41_kzqq kzqq, JSGA_FreezeResponse_Account res) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户紧急止付
	 * 
	 * @param kzqq
	 * @param res
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public JSGA_StopPaymentRequest_Recored stoppayAccount(Br41_kzqq kzqq, JSGA_StopPaymentResponse_Account res) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户解除止付
	 * 
	 * @param kzqq
	 * @param res
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public JSGA_StopPaymentRequest_Recored stoppayAccountJC(Br41_kzqq kzqq, JSGA_StopPaymentResponse_Account res) throws DataOperateException, RemoteAccessException;
	
	
	public void sendTransMsg(List<JSGA_ControlRequest_Record> tranList,String telno) throws Exception;
	
}