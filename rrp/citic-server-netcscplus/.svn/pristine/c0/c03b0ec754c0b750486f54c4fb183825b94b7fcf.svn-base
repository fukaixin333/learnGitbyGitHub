/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月18日
 * Description:
 * =========================================================
 */
package com.citic.server.gf.service.local;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.SpringContextHolder;
import com.citic.server.gf.domain.QueryRequestObj;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.request.QueryRequest_Zhxx;
import com.citic.server.gf.domain.response.ControlResponse_Kzzh;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;
import com.citic.server.gf.service.AbstractDataOperate01;
import com.citic.server.net.mapper.MM30_xzcsMapper;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.BB13_organ_telno;
import com.citic.server.service.domain.BB13_sys_para;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;

/**
 * @author gaojx
 */
@Service("localDataOperate1")
public class LocalDataOperate1 extends AbstractDataOperate01 {
	
	@Autowired
	private MM30_xzcsMapper mm30_xzcsMapper;
	
	@Override
	public QueryRequestObj getQueryRequestObj(QueryResponse_Cxqq cxqq) throws DataOperateException, RemoteAccessException {
		try {
			CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
			HashMap transtypeHash = (HashMap<String, Object>) cacheService.getCache("ReHangOrganMap", HashMap.class);
			// 3. 执行查询，获取数据
			List<QueryResponse_Cxqq> party_id_List = mm30_xzcsMapper.getParty_Id(cxqq);
			String party_id = "";
			if (party_id_List != null && party_id_List.size() > 0) {
				party_id = party_id_List.get(0).getParty_id();
			}
			if (party_id.equals("")) {
				throw new RemoteAccessException("没有此客户");
			}
			String bdhm = cxqq.getBdhm();
			List<QueryRequest_Zhxx> zhxxList = mm30_xzcsMapper.getZhxxList(party_id);
			//取多法人机构
			String packetkey = cxqq.getPacketkey();
			String organkey_r = packetkey.substring(4, 21);
			HashMap repOrgHash = (HashMap<String, Object>) cacheService.getCache("Mp02_repOrgMapDetail", HashMap.class);
			HashMap orgMap = (HashMap) repOrgHash.get(organkey_r);
			if (zhxxList != null && zhxxList.size() > 0) {
				for (int i = 0; i < zhxxList.size(); i++) {
					QueryRequest_Zhxx zhxx = (QueryRequest_Zhxx) zhxxList.get(i);
					String orgkey = zhxx.getKhwddm();
					if (orgMap.containsKey(orgkey)) {
						zhxx.setBdhm(bdhm);
						zhxx.setCcxh(String.valueOf(i + 1));
						zhxx.setFksj(DtUtils.getNowTime());
						zhxx.setQrydt(DtUtils.getNowDate());
						//转换开户网点和名称
						String depositBankBranchCode = StrUtils.null2String(zhxx.getKhwddm());
						String depositBankBranch = StrUtils.null2String(zhxx.getKhwd());
						if (transtypeHash.get(depositBankBranchCode) != null) {
							BB13_sys_para syspara = (BB13_sys_para) transtypeHash.get(depositBankBranchCode);
							depositBankBranchCode = syspara.getVals();
							depositBankBranch = syspara.getCodename();
						}
						zhxx.setKhwd(depositBankBranch);// 开户网点
						zhxx.setKhwddm(depositBankBranchCode);// 开户网点代码
						// 插入账户基本信息
						mm30_xzcsMapper.insertBr30_xzcs_info(zhxx);
					}
				}
				
				// 插入金融资产信息
				mm30_xzcsMapper.insertBr30_xzcs_info_zc(bdhm);
				// 司法强制措施信息
				mm30_xzcsMapper.insertBr30_xzcs_info_cs(bdhm);
				// 资金往来（交易）**
				if (!"".equals(cxqq.getCkjssj()) && !"".equals(cxqq.getCkkssj())) { // CKKSSJ、CKJSSJ此字段默认情况为空,
																					// 则无需提供资金往来信息
					mm30_xzcsMapper.insertBr30_xzcs_info_wl(cxqq);
				}
				// 共有权/优先权信息
				mm30_xzcsMapper.insertBr30_xzcs_info_ql(bdhm);
				// 关联子类账户信息
				//mm30_xzcsMapper.insertBr30_xzcs_info_glxx(bdhm);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteAccessException(e.getMessage());
		}
		return null;
	}
	
	@Override
	public ControlRequest_Kzxx invokeFreezeAccount(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException {
		return null;
	}
	
	@Override
	public ControlRequest_Kzxx invokeDeductFunds(ControlResponse_Kzzh kzqq) throws DataOperateException, RemoteAccessException {
		return null;
	}
	
	@Override
	public void invokeSendMessage(List<BB13_organ_telno> telOrganList, String organKey, String kzcs) throws Exception {
	}
	
	@Override
	public List<QueryRequest_Djxx> getDjxxList(ControlResponse_Kzzh kzqq) throws RemoteAccessException, DataOperateException {
		return null;
	}
	
	@Override
	public List<QueryRequest_Qlxx> getQlxxList(ControlResponse_Kzzh kzqq) throws RemoteAccessException, DataOperateException {
		return null;
	}
}
