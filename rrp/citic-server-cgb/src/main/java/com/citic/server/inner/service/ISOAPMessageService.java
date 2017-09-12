package com.citic.server.inner.service;

import java.util.List;

import com.citic.server.cgb.domain.request.ExternalTransferInput;
import com.citic.server.cgb.domain.request.FinancialDeferFreezeInput;
import com.citic.server.cgb.domain.request.FinancialFreezeInput;
import com.citic.server.cgb.domain.request.FinancialFrozenMeasuresInput;
import com.citic.server.cgb.domain.request.FinancialUnfreezeInput;
import com.citic.server.cgb.domain.request.OnlineTPQueryFKWarnInput;
import com.citic.server.cgb.domain.request.OnlineTPQueryFinancialInput;
import com.citic.server.cgb.domain.response.CargoRecordResult;
import com.citic.server.cgb.domain.response.FinancialDeferFreezeResult;
import com.citic.server.cgb.domain.response.FinancialFreezeResult;
import com.citic.server.cgb.domain.response.FinancialFrozenMeasure;
import com.citic.server.cgb.domain.response.FinancialUnfreezeResult;
import com.citic.server.cgb.domain.response.OnlineTPFKWarnDetail;
import com.citic.server.cgb.domain.response.OnlineTPFinancialDetail;
import com.citic.server.runtime.CgbKeys;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;

/**
 * HTTP+SOAP报文接口（直连）
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/24 16:34:12$
 */
public interface ISOAPMessageService extends CgbKeys {
	
	public FinancialFreezeResult invokeFreezeFinancial(FinancialFreezeInput in) throws DataOperateException, RemoteAccessException;
	
	public FinancialDeferFreezeResult invokeDeferFreezeFinancial(FinancialDeferFreezeInput in) throws DataOperateException, RemoteAccessException;
	
	public FinancialUnfreezeResult invokeUnfreezeFinancial(FinancialUnfreezeInput in) throws DataOperateException, RemoteAccessException;
	
	public List<FinancialFrozenMeasure> queryFinancialFrozenMeasures(FinancialFrozenMeasuresInput in) throws DataOperateException, RemoteAccessException;
	
	/**
	 * 金融理财信息查询（ZXZC02）（在线交易平台）
	 */
	public List<OnlineTPFinancialDetail> queryFinancialFromOnlineTP(String idType, String idNumber, String customerName) throws DataOperateException, RemoteAccessException;
	
	public List<OnlineTPFinancialDetail> queryFinancialFromOnlineTP(OnlineTPQueryFinancialInput in) throws DataOperateException, RemoteAccessException;
	
	public List<OnlineTPFKWarnDetail> queryFKWarnFromOnlineTP(OnlineTPQueryFKWarnInput in) throws DataOperateException, RemoteAccessException;
	
	/** 
	 * 统一支付系统（行外转账） 
	 * @throws RemoteAccessException 
	 * @throws DataOperateException 
	 */
	public CargoRecordResult invokeExternalTransfer(ExternalTransferInput in) throws DataOperateException, RemoteAccessException;
	
}
