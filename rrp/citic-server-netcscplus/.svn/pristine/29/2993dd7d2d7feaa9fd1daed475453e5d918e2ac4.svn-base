package com.citic.server.gf.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Glxx;
import com.citic.server.gf.domain.request.QueryRequest_Jrxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.request.QueryRequest_Wlxx;
import com.citic.server.gf.domain.request.QueryRequest_Zhxx;

/**
 * 查询/控制结果反馈对象
 * 
 * @author dingke
 * @date 2016年3月8日 下午4:20:36
 */
@Data
public class QueryRequestObj implements Serializable {
	private static final long serialVersionUID = -3548113934204110364L;
	
	// 账户信息
	List<QueryRequest_Zhxx> zhxxList;
	
	// 子账户信息
	List<QueryRequest_Glxx> glxxList;
	
	// 金融资产信息
	List<QueryRequest_Jrxx> jrxxList;

	// 司法强制措施信息
	List<QueryRequest_Djxx> djxxList;
	
	// 资金往来信息
	List<QueryRequest_Wlxx> wlxxList;
	
	// 共有权/优先权信息
	List<QueryRequest_Qlxx> qlxxList;
}
