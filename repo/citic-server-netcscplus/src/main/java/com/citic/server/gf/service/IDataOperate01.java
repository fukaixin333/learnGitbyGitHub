/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月17日
 * Description:
 * =========================================================
 */
package com.citic.server.gf.service;

import java.util.List;

import com.citic.server.gf.domain.QueryRequestObj;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.response.ControlResponse_Kzzh;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.service.domain.BB13_organ_telno;

/**
 * 最高人民法院远程调用接口
 * 
 * @author Liu Xuanfei
 * @date 2017年5月24日 下午8:18:18
 */
public interface IDataOperate01 {
	
	/**
	 * 根据司法请求信息查询账户信息、金融资产信息、司法强制措施信息、共有权优先权信息、子账户信息，以及资金往来（交易）信息
	 * 
	 * @param cxqq
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public QueryRequestObj getQueryRequestObj(QueryResponse_Cxqq cxqq) throws DataOperateException, RemoteAccessException;
	
	/**
	 * @see #getQueryRequestObj(QueryResponse_Cxqq)
	 */
	@Deprecated
	public QueryRequestObj getBr30_xzcs_infoList(QueryResponse_Cxqq cxqq) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 查询被控制人账户的在先冻结信息（控制类请求）
	 * 
	 * @param accountNumber
	 * @param subAccount
	 * @return
	 * @throws RemoteAccessException
	 * @throws DataOperateException
	 *         -
	 * @author liuxuanfei
	 * @date 2017/07/04 20:25:55
	 */
	public List<QueryRequest_Djxx> getDjxxList(ControlResponse_Kzzh kzqq) throws RemoteAccessException, DataOperateException;
	
	/**
	 * 查询被控制人账户的共有权/优先权信息（控制类请求）
	 * 
	 * @param accountNumber
	 * @param subAccount
	 * @return
	 * @throws RemoteAccessException
	 * @throws DataOperateException
	 *         -
	 * @author liuxuanfei
	 * @date 2017/07/04 20:27:19
	 */
	public List<QueryRequest_Qlxx> getQlxxList(ControlResponse_Kzzh kzqq) throws RemoteAccessException, DataOperateException;
	
	/**
	 * 调用核心冻结类控制接口（不含扣划）
	 * 
	 * @param kzqq 冻结类控制请求信息
	 * @return 控制结果信息
	 * @throws DataOperateException 数据异常
	 * @throws RemoteAccessException 程序异常
	 *         -
	 * @author liuxuanfei
	 * @date 2017/07/04 20:22:43
	 */
	public ControlRequest_Kzxx invokeFreezeAccount(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 执行扣划
	 * 
	 * @param kzqq 扣划请求信息
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 *         -
	 * @author liuxuanfei
	 * @date 2017/07/04 20:45:23
	 */
	public ControlRequest_Kzxx invokeDeductFunds(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 调用短信发送接口
	 * 
	 * @param telOrganList
	 * @param organKey
	 * @param kzcs 00-查询；01-冻结；02-续冻；04-解冻；06-扣划
	 * @throws Exception
	 *         -
	 * @author liuxuanfei
	 * @date 2017/07/04 20:29:31
	 */
	public void invokeSendMessage(List<BB13_organ_telno> telOrganList, String organKey, String kzcs) throws Exception;
	
	/**
	 * @see #invokeSendMessage(List, String, String)
	 */
	@Deprecated
	public void SendMsg(List<BB13_organ_telno> bb13_organ_telnoList, String organkey, String kzcs) throws Exception;
	
}
