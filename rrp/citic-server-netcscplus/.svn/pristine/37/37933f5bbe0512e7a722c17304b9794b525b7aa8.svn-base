package com.citic.server.net.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.cbrc.domain.Br40_cxqq;
import com.citic.server.dx.domain.OrganKeyQuery;
import com.citic.server.dx.domain.Response;
import com.citic.server.service.domain.BB13_organ_telno;
import com.citic.server.service.domain.BB13_sys_para;
import com.citic.server.service.domain.BR42_sequences;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.domain.Mp02_organ;

public interface MC00_common_Mapper {
	
	void insertMc21_task_fact2_org(MC21_task_fact mc21_task_fact);
	
	void insertMc21_task_fact2(MC21_task_fact mc21_task_fact);
	
	void insertMc21_task_fact3(MC21_task_fact mc21_task_fact);
	
	void insertMc21_task_fact3_packet(MC21_task_fact mc21_task_fact);
	
	int selMc21_task_fact3_packet(String packetname);
	
	MC21_task_fact getMc21_task_fact1(String taskkey);
	
	//查询请求任务单的存放地址
	ArrayList<MC20_task_msg> getMC20_task_msgList(String bdhm);
	
	// 查询机构信息by机构号或机构名
	Mp02_organ getMp02_organByIdName(Mp02_organ organ);
	
	// 查询总行机构信息
	Mp02_organ getMp02_organByIdName1(Mp02_organ organ);
	
	// 系统码表信息
	ArrayList<BB13_sys_para> getsysPara();
	
	// 高法币种
	ArrayList<BB13_sys_para> getBB13_pbc_crtp();
	
	//查询机构
	ArrayList<Mp02_organ> getMp02_organ();
	
	//查询国家
	ArrayList<Mp02_organ> getMp02_country();
	
	//查询CATP
	ArrayList<Mp02_organ> getMp02_catp();
	
	//查询地区
	ArrayList<Mp02_organ> getMp02_area();
	
	//查询mp01_dict_std
	ArrayList<Mp02_organ> getMP01_dict_std();
	
	//查询mp01_dict_std
	ArrayList<Mp02_organ> getBB13_etl_code_map();
	
	//查询交易类型码表
	ArrayList<BB13_sys_para> getBB13_rb_tran_def();
	
	//查询人行机构
	ArrayList<BB13_sys_para> getBB13_union();
	
	//插入响应表
	void insertBr24_q_k_back_m_f(Response response);
	
	//删除响应表
	void delBr24_q_k_back_m_f(String transserialnumber);
	
	//查询上报机构编码
	ArrayList<MP02_rep_org_map> getMp02_reporgmap_map();
	
	//查询上报机构编码
	ArrayList<MP02_rep_org_map> getMp02_reporg();
	
	//查询序列
	BR42_sequences getBr42_sequences(BR42_sequences br42_sequences);
	
	//修改序列
	void updateBr42_sequences(BR42_sequences br42_sequences);
	
	//增加序列
	void insertBr42_sequences(BR42_sequences br42_sequences);
	
	//从批后获取卡表中取开卡机构
	List<OrganKeyQuery> getBb11_card_organ(String cardnumber);
	
	//从批后获取账户表中取机构
	List<OrganKeyQuery> getBb11_deposit_organ(String cardnumber);
	
	//从批后获取账户表中取机构
	List<OrganKeyQuery> getBb11_deposit_organ_bypartyid(String party_id);
	
	//从批后获取有证件号码从客户表中取机构
	List<OrganKeyQuery> getBb11_party_organ(String accountcredentialnumber);
	
	//查询机构下的发送手机号码
	List<BB13_organ_telno> getBb13_organ_tel(String organkey);
	
	String geSequenceNumber(String sqlStr);
	
	String execSql(String sqlStr);
	
	Integer isTaskCount(@Param("qqdbs") String qqdbs, @Param("tasktype") String tasktype);
	
	Br40_cxqq getBr40_cxqq(@Param("qqdbs") String qqdbs, @Param("tasktype") String tasktype);
	
}
