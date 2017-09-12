package com.citic.server.shpsb.service;

import java.util.List;

import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.shpsb.domain.ShpsbSadx;
import com.citic.server.shpsb.domain.request.ShpsbRequestCzrzMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestDdzhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJyglhMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestJylsMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestKhzlMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhcyrMx;
import com.citic.server.shpsb.domain.request.ShpsbRequestZhxxMx;

/**
 * 数据加工接口
 * 
 * @author Liu Xuanfei
 * @date 2016年11月9日 下午5:45:01
 */
public interface IDataOperateShpsb {
	
	/**
	 * 账户信息查询接口
	 * 
	 * @param sadx 涉案对象
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<ShpsbRequestZhxxMx> getAccountInformation(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 账户持有人资料查询接口
	 * 
	 * @param sadx 涉案对象
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public ShpsbRequestZhcyrMx getAccountPossessor(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 开户资料查询接口
	 * 
	 * @param sadx 涉案对象
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public ShpsbRequestKhzlMx getAccountOpenInformation(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 交易明细查询接口
	 * 
	 * @param sadx 涉案对象
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<ShpsbRequestJylsMx> getAccountTransaction(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 操作日志查询接口
	 * 
	 * @param sadx 涉案对象
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<ShpsbRequestCzrzMx> getOperationLog(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 交易关联号查询接口
	 * 
	 * @param sadx 涉案对象
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<ShpsbRequestJyglhMx> getAffiliatedTransactionNo(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 对端账号查询
	 * 
	 * @param sadx 涉案对象
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<ShpsbRequestDdzhMx> getReciprocalAccount(ShpsbSadx sadx) throws DataOperateException, RemoteAccessException;
	
}
