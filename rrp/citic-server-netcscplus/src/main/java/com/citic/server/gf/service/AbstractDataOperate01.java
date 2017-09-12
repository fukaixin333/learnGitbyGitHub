package com.citic.server.gf.service;

import java.util.List;

import com.citic.server.gf.domain.QueryRequestObj;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.service.domain.BB13_organ_telno;

/**
 * 最高人民法院远程访问接口
 * 
 * @author Liu Xuanfei
 * @date 2017年5月24日 下午8:27:33
 */
public abstract class AbstractDataOperate01 implements IDataOperate01 {
	
	@Override
	public QueryRequestObj getBr30_xzcs_infoList(QueryResponse_Cxqq cxqq) throws DataOperateException, RemoteAccessException {
		return getQueryRequestObj(cxqq);
	}
	
	
	@Override
	public void SendMsg(List<BB13_organ_telno> telOrganList, String organKey, String kzcs) throws Exception {
		invokeSendMessage(telOrganList, organKey, kzcs);
	}
}
