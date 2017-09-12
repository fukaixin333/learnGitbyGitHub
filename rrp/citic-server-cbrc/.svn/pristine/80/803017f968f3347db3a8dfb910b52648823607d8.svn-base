package com.citic.server.cbrc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.cbrc.domain.Br41_kzqq;
import com.citic.server.cbrc.domain.Br42_msg;
import com.citic.server.cbrc.domain.Br42_packet;
import com.citic.server.cbrc.domain.CBRC_ReturnReceipt;
import com.citic.server.cbrc.domain.request.CBRC_FeedbackRequest_Record;
import com.citic.server.cbrc.domain.request.CBRC_FreezeRequest_Detail;
import com.citic.server.cbrc.domain.request.CBRC_FreezeRequest_Record;
import com.citic.server.cbrc.domain.request.CBRC_StopPaymentRequest_Detail;
import com.citic.server.cbrc.domain.request.CBRC_StopPaymentRequest_Recored;
import com.citic.server.cbrc.domain.response.CBRC_FreezeResponse_Account;
import com.citic.server.cbrc.domain.response.CBRC_StopPaymentResponse_Account;

/**
 * 国安高检公安 常规查询
 * 
 * @author
 */
public interface MM41_kzqq_cbrcMapper {
	
	/** 删除BR41_kzQQ控制请求单表 */
	void delBr41_kzqq(Br41_kzqq br41_kzqq);
	
	/** 插入BR41_kzQQ控制请求主表 */
	void insertBr41_kzqq(Br41_kzqq br41_kzqq);
	
	/** 删除控制请求主体信息 */
	void delBr41_kzqq_dj(Br41_kzqq br41_kzqq);
	
	void delBr41_kzqq_dj_back1(CBRC_FreezeResponse_Account cg_freeze);
	
	void delBr41_kzqq_dj_back(Br41_kzqq br41_kzqq);
	
	void delBr41_kzqq_zf(Br41_kzqq br41_kzqq);
	
	void delBr41_kzqq_zf_back1(CBRC_StopPaymentResponse_Account cg_stoppay);
	
	void delBr41_kzqq_zf_back(Br41_kzqq br41_kzqq);
	
	/** 插入控制单主体信息 */
	void insertBr41_kzqq_dj(CBRC_FreezeResponse_Account freeze);
	
	void insertBr41_kzqq_dj_back(CBRC_FreezeRequest_Record kzqq_dj);
	
	void insertBr41_kzqq_zf(CBRC_StopPaymentResponse_Account stoppay);
	
	void insertBr41_kzqq_zf_back(CBRC_StopPaymentRequest_Recored kzqq_zf);
	
	void insertBr41_kzqq_dj_back_mx(CBRC_FreezeRequest_Detail kzqq_dj_mx);
	
	void insertBr41_kzqq_zf_back_mx(CBRC_StopPaymentRequest_Detail kzqq_zf_mx);
	
	void delBr41_kzqq_dj_back_mx(CBRC_FreezeResponse_Account cg_freeze);
	
	void delBr41_kzqq_zf_back_mx(CBRC_StopPaymentResponse_Account cg_stoppay);
	
	List<CBRC_FreezeRequest_Detail> selectBr41_kzqq_dj_back_mxByList(CBRC_FreezeRequest_Record freeze);
	
	List<CBRC_StopPaymentRequest_Detail> selectBr41_kzqq_zf_back_mxByList(CBRC_StopPaymentRequest_Recored stoppay);
	
	/** 按条件 查询BR41_KZQQ控制请求内容主体表 */
	Br41_kzqq selectBr41_kzqqByVo(Br41_kzqq br41_kzqq);//请求单标识
	
	CBRC_StopPaymentResponse_Account selectBr41_kzqq_zfByVo(Br41_kzqq br41_kzqq);
	
	CBRC_FreezeResponse_Account selectBr41_kzqq_djByVo(Br41_kzqq br41_kzqq);
	
	List<CBRC_FreezeResponse_Account> selectBr41_kzqq_djList(Br41_kzqq br41_kzqq);
	
	/** 按条件 查询BR41_KZQQ_BACK控制请求反馈主体表 */
	CBRC_StopPaymentRequest_Recored selectBr41_kzqq_zf_backByVo(CBRC_StopPaymentResponse_Account stoppay);
	
	CBRC_FreezeRequest_Record selectBr41_kzqq_dj_backByVo(CBRC_FreezeResponse_Account freeze);
	
	List<CBRC_StopPaymentRequest_Recored> selectBr41_kzqq_zf_backByList(CBRC_StopPaymentResponse_Account stoppay);
	
	List<CBRC_FreezeRequest_Record> selectBr41_kzqq_dj_backByList(CBRC_FreezeResponse_Account freeze);
	
	List<CBRC_FeedbackRequest_Record> selectBr41_cxqq_hzwsList(Br41_kzqq br41_kzqq);
	
	void insertBr41_cxqq_hzws(CBRC_FeedbackRequest_Record hzws);
	
	Integer getBr41_kzqq_dj_backCount(Br41_kzqq br41_kzqq);
	
	Integer getBr41_kzqq_zf_backCount(Br41_kzqq br41_kzqq);
	
	void delBr42_msg(Br42_msg msg);
	
	void insertBr42_msg(Br42_msg msg);
	
	void delBr42_packet(Br42_packet packet);
	
	void insertBr42_packet(Br42_packet packet);
	
	//判断Br40_cxqq_back査询请求表中 状态是否只为6.已生成报文
	Integer isPacket_zfCount(Br41_kzqq kzqq);
	
	Integer isPacket_djCount(Br41_kzqq kzqq);
	
	List<Br42_msg> getBr42_msg(Br42_packet packet);
	
	void updateBr41_kzqq_dj_back(CBRC_FreezeRequest_Record kzqq_dj_back);
	
	void updateBr41_kzqq_zf_back(CBRC_StopPaymentRequest_Recored kzqq_zf_back);
	
	void updateBr41_kzqq(Br41_kzqq br41_kzqq);
	
	public void updateBr41_kzqqStatus(CBRC_ReturnReceipt returnReceipt);
	
	String getWjmc(Br41_kzqq br41_kzqq);
	
	void insertBr41_kzqq_hzws(CBRC_FeedbackRequest_Record wsinfo);
	
	CBRC_StopPaymentRequest_Recored selectBr41_kzqq_zf_backDisp(CBRC_StopPaymentResponse_Account stoppay);
	
	CBRC_FreezeRequest_Record selectBr41_kzqq_dj_backDisp(CBRC_FreezeResponse_Account freeze);
	
	Integer isTaskCount(Br41_kzqq br41_kzqq);
	
	public List<CBRC_FreezeRequest_Record> selectBr41_kzqq_dj_backInPacket(@Param("qqdbs") String qqdbs, @Param("tasktype") String tasktype);
}
