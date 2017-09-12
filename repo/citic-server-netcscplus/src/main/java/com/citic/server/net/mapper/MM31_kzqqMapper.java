package com.citic.server.net.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.gf.domain.Br31_kzcl_info;
import com.citic.server.gf.domain.Br31_yxq_info;
import com.citic.server.gf.domain.Br32_msg;
import com.citic.server.gf.domain.MC20_GZZ;
import com.citic.server.gf.domain.request.ControlRequest_Djxx;
import com.citic.server.gf.domain.request.ControlRequest_Hzxx;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.ControlRequest_Qlxx;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.response.ControlResponse_Kzqq;
import com.citic.server.gf.domain.response.ControlResponse_Kzzh;

public interface MM31_kzqqMapper {
	
	//查询控制请求信息
	ControlResponse_Kzqq getBr31_kzqqVo(String bdhm);
	
	//查询控制请求账户
	ControlResponse_Kzzh getMM31_kzzh(@Param("bdhm") String bdhm, @Param("ccxh") String ccxh);
	
	Br31_kzcl_info getMM31_kzcl_info(@Param("bdhm") String bdhm, @Param("ccxh") String ccxh);
	
	//查询控制请求信息
	List<ControlRequest_Hzxx> getBr31_kzqq_hzxxVo(@Param("bdhm") String bdhm);
	
	//删除控制请求
	void delBr31_kzqq(String bdhm);
	
	//删除控制请求账户
	void delBr31_kzzh(String bdhm);
	
	//删除控制文书
	void delBr31_Ws(String bdhm);
	
	//删除回执文书
	void delBr31_Hzws(String bdhm);
	
	//删除控制工作证
	void delBr31_Gzz(String bdhm);
	
	//删除控制处理信息
	void delBr31_kzcl_info(String bdhm);
	
	//删除权利人
	void delBr31_yxq_info(Br31_kzcl_info br31_kzcl_info);
	
	//删除在先冻结信息
	void delBr31_zxdj_info(Br31_kzcl_info br31_kzcl_info);
	
	//修改控制账户处理
	void updateBr31_kzcl_info(ControlRequest_Kzxx kzqq_hxfk);
	
	//插入控制处理信息
	void insertBr31_kzcl_info(ControlResponse_Kzzh kzzh);
	
	//插入控制请求
	void insertBr31_kzqq(ControlResponse_Kzqq kzqq);
	
	//插入控制请求账户
	void insertBr31_kzzh(ControlResponse_Kzzh kzzh);
	
	//插入控制请求文书
	void insertBr31_ws(@Param("bdhm") String bdhm, @Param("tasktype") String tasktype);
	
	//插入控制请求工作证
	void insertBr31_kzqq_gzz(@Param("bdhm") String bdhm, @Param("tasktype") String tasktype);
	
	//删除br32_msg
	void delBr32_msg(String bdhm);
	
	//插入报文表
	void insertBr32_msg(Br32_msg msg);
	
	//控制处理信息
	ArrayList<ControlRequest_Kzxx> getBr31_kzcl_infoList(String bdhm);
	
	//优先权信息
	ArrayList<ControlRequest_Qlxx> getBr31_yxq_infoList(ControlRequest_Kzxx kzxx);
	
	//在先冻结信息
	ArrayList<ControlRequest_Djxx> getBr31_zxdj_infoList(ControlRequest_Kzxx kzxx);
	
	//司法控制请求
	void updateBr31_kzqq(ControlResponse_Kzqq kzqq);
	
	public void updateBr31_kzqqStatus(String bdhm, String status);
	
	//回执报文信息
	ArrayList<ControlRequest_Hzxx> getHzxxList(String bdhm);
	
	//优先权信息
	void insertBr31_yxq_info(QueryRequest_Qlxx cs_qlxx);
	
	//在先冻结信息
	void insertBr31_zxdj_info(QueryRequest_Djxx cs_djxx);
	
	//判断BR31_KZQQ司法査询请求表中 状态是否只为6.已生成报文 7:退回监管 
	Integer isKPacketCount(String packetkey);
	
	Integer getBr31_kzcl_infonoCount(String bdhm);
	
	//获取核心冻结编号
	ControlRequest_Kzxx getHxAppid(ControlResponse_Kzzh yh_kzqq);
	
	//生成pdf查询
	List<Br31_kzcl_info> selectBr31_kzcl_DetailByBdhm(String bdhm);
	
	List<Br31_yxq_info> selectBr31_yxq_DetailByBdhm(Br31_kzcl_info br31_kzcl_info);
	
	void insertBr31_kzqq_hzws(ControlRequest_Hzxx ControlRequest_Hzxx);
	
	//获取机构的内部账号
	ControlResponse_Kzzh getBr31_organ_inner_acct(String organkey);
	
	public List<MC20_GZZ> queryMc20_gzzByBdhm(@Param("bdhm") String bdhm);
}
