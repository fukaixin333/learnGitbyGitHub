package com.citic.server.net.mapper;

import java.util.List;

import com.citic.server.gf.domain.Br32_msg;
import com.citic.server.gf.domain.Result_Jg;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Glxx;
import com.citic.server.gf.domain.request.QueryRequest_Jrxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.request.QueryRequest_Wlxx;
import com.citic.server.gf.domain.request.QueryRequest_Zhxx;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;

public interface MM30_xzcsMapper {
	
	//删除査询请求
	void delBr30_xzcs(String bdhm);
	
	//删除査询文书
	void delBr30_Ws(String wsbm);
	
	//删除査询工作证
	void delBr30_Gzz(String bdhm);
	
	//删除BR30_XZCS_INFO 账户信息
	void delBr30_xzcs_info(String bdhm);
	
	//删除BR30_XZCS_INFO_WL 资金往来信息
	void delBr30_xzcs_info_wl(String bdhm);
	
	//删除BR30_XZCS_INFO_CS 司法强制措施信息
	void delBr30_xzcs_info_cs(String bdhm);
	
	//删除BR30_XZCS_INFO_ZC 金融资产信息
	void delBr30_xzcs_info_zc(String bdhm);
	
	//删除BR30_XZCS_INFO_QL 共有权/优先权信息
	void delBr30_xzcs_info_ql(String bdhm);
	
	//删除BR30_XZCS_INFO_GLXX 关联账户信息
	void delBr30_xzcs_info_glxx(String bdhm);
	
	//删除br32_msg
	void delBr32_msg(Br32_msg msg);
	
	public void deleteBr32_jg(String bdhm);
	
	public void insertBr32_jg(Result_Jg jg);
	
	//插入报文表
	void insertBr32_msg(Br32_msg msg);
	
	//插入査询请求
	void insertBr30_xzcs(QueryResponse_Cxqq cxqq);
	
	//插入査询请求文书
	void insertBr30_ws(String whbh);
	
	//插入査询请求工作证
	void insertBr30_gzz(QueryResponse_Cxqq cxqq);
	
	//查询査询请求信息
	QueryResponse_Cxqq getBr30_xzcsVo(String bdhm);
	
	//査询账户基本信息
	List<QueryRequest_Zhxx> getBr30_xzcs_infoList(String bdhm);
	
	//査询金融资产信息
	List<QueryRequest_Jrxx> getBr30_xzcs_info_zcList(QueryRequest_Zhxx zhxx);
	
	//司法强制措施信息
	List<QueryRequest_Djxx> getBr30_xzcs_info_csList(QueryRequest_Zhxx zhxx);
	
	//资金往来（交易）信息
	List<QueryRequest_Wlxx> getBr30_xzcs_info_wlList(QueryRequest_Zhxx zhxx);
	
	//资金往来（交易）信息本地取
	List<QueryRequest_Wlxx> getBr30_xzcs_info_wlList2(QueryRequest_Zhxx zhxx);
	
	//共有权/优先权信息
	List<QueryRequest_Qlxx> getBr30_xzcs_info_qlList(QueryRequest_Zhxx zhxx);
	
	//关联子类账户信息
	List<QueryRequest_Glxx> getBr30_xzcs_info_glxxList(QueryRequest_Zhxx zhxx);
	
	//获取客户号（根据请求单的证件类型和证件号码）
	List<QueryResponse_Cxqq> getParty_Id(QueryResponse_Cxqq cxqq);
	
	//String getParty_Id(QueryResponse_Cxqq cxqq);
	//获取主账号list（根据客户号获取）
	List<QueryRequest_Zhxx> getZhxxList(String party_id);
	
	//插入账户基本信息
	void insertBr30_xzcs_info(QueryRequest_Zhxx zhxx);
	
	//插入金融资产信息
	void insertBr30_xzcs_info_zc(String bdhm);
	
	//插入司法强制措施信息
	void insertBr30_xzcs_info_cs(String bdhm);
	
	//插入资金往来（交易）信息
	void insertBr30_xzcs_info_wl(QueryResponse_Cxqq cxqq);
	
	//共有权/优先权信息
	void insertBr30_xzcs_info_ql(String bdhm);
	
	//关联子类账户信息
	void insertBr30_xzcs_info_glxx(String bdhm);
	
	void updateBr30_xzcs(QueryResponse_Cxqq cxqq);
	
	//判断BR30_XZCS司法査询请求表中 状态是否只为6.已生成报文 7:退回监管 
	Integer isPacketCount(String packetkey);
	
	//插入金融资产信息
	void insertBr30_xzcs_info_zc_hx(QueryRequest_Jrxx queryrequest_jrxx);
	
	//插入司法强制措施信息
	void insertBr30_xzcs_info_cs_hx(QueryRequest_Djxx queryrequest_djxx);
	
	//插入资金往来（交易）信息
	void insertBr30_xzcs_info_wl_hx(QueryRequest_Wlxx queryrequest_wlxx);
	
	//共有权/优先权信息
	void insertBr30_xzcs_info_ql_hx(QueryRequest_Qlxx queryrequest_qlxx);
	
	//关联子类账户信息
	void insertBr30_xzcs_info_glxx_hx(QueryRequest_Glxx queryrequest_glxx);
	
	//查询交易信息
	List<QueryRequest_Wlxx> getTransWlxxInfoList(QueryResponse_Cxqq cxqq);
}
