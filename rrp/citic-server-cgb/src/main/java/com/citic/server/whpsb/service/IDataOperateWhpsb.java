package com.citic.server.whpsb.service;

import java.util.List;

import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.whpsb.domain.Br51_cxqq_mx;
import com.citic.server.whpsb.domain.Whpsb_RequestJymx_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestCkrzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestKhzl_Detail;
import com.citic.server.whpsb.domain.request.Whpsb_RequestZhxx_Detail;

/**
 * @author Liu Xuanfei
 * @date 2016年8月19日 上午11:34:49
 */
public interface IDataOperateWhpsb {
	/**
	 * 账户信息查询
	 * @param br51_cxqq_mx
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<Whpsb_RequestZhxx_Detail> getWhpsb_RequestZhxxList(Br51_cxqq_mx br51_cxqq_mx) throws DataOperateException, RemoteAccessException;
	
	
	/**
	 * 开卡资料查询
	 * @param br51_cxqq_mx
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public Whpsb_RequestCkrzl_Detail getWhpsb_RequestCkzlList(Br51_cxqq_mx br51_cxqq_mx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 开户资料查询
	 * @param br51_cxqq_mx
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public Whpsb_RequestKhzl_Detail getWhpsb_RequestKhzlList(Br51_cxqq_mx br51_cxqq_mx) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 交易明细查询
	 * @param br51_cxqq_mx
	 * @return
	 * @throws DataOperateException
	 * @throws RemoteAccessException
	 */
	public List<Whpsb_RequestJymx_Detail> getWhpsb_RequestJymxList(Br51_cxqq_mx br51_cxqq_mx) throws DataOperateException, RemoteAccessException;
	
}
