package com.citic.server.jsga.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.jsga.domain.Br42_msg;
import com.citic.server.jsga.domain.Br42_packet;
import com.citic.server.jsga.domain.JSGA_ReturnReceipt;
import com.citic.server.jsga.domain.request.JSGA_FeedbackRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest_Detail;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Detail;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Recored;
import com.citic.server.jsga.domain.response.JSGA_FreezeResponse_Account;
import com.citic.server.jsga.domain.response.JSGA_StopPaymentResponse_Account;

/**
 * 国安高检公安 常规查询
 * 
 * @author
 */
public interface MM41_kzqq_jsgaMapper {
	
	/** 删除BR41_kzQQ控制请求单表 */
	void delBr41_kzqq(Br41_kzqq br41_kzqq);
	
	/** 插入BR41_kzQQ控制请求主表 */
	void insertBr41_kzqq(Br41_kzqq br41_kzqq);
	
	/** 删除控制请求主体信息 */
	void delBr41_kzqq_dj(Br41_kzqq br41_kzqq);
	
	void delBr41_kzqq_dj_back1(JSGA_FreezeResponse_Account cg_freeze);
	
	void delBr41_kzqq_dj_back(Br41_kzqq br41_kzqq);
	
	void delBr41_kzqq_zf(Br41_kzqq br41_kzqq);
	
	void delBr41_kzqq_zf_back1(JSGA_StopPaymentResponse_Account cg_stoppay);
	
	void delBr41_kzqq_zf_back(Br41_kzqq br41_kzqq);
	
	/** 插入控制单主体信息 */
	void insertBr41_kzqq_dj(JSGA_FreezeResponse_Account freeze);
	
	void insertBr41_kzqq_dj_back(JSGA_FreezeRequest_Record kzqq_dj);
	
	void insertBr41_kzqq_zf(JSGA_StopPaymentResponse_Account stoppay);
	
	void insertBr41_kzqq_zf_back(JSGA_StopPaymentRequest_Recored kzqq_zf);
	
	void insertBr41_kzqq_dj_back_mx(JSGA_FreezeRequest_Detail kzqq_dj_mx);
	
	void insertBr41_kzqq_zf_back_mx(JSGA_StopPaymentRequest_Detail kzqq_zf_mx);
	
	void delBr41_kzqq_dj_back_mx(JSGA_FreezeResponse_Account cg_freeze);
	
	void delBr41_kzqq_zf_back_mx(JSGA_StopPaymentResponse_Account cg_stoppay);
	
	List<JSGA_FreezeRequest_Detail> selectBr41_kzqq_dj_back_mxByList(JSGA_FreezeRequest_Record freeze);
	
	List<JSGA_StopPaymentRequest_Detail> selectBr41_kzqq_zf_back_mxByList(JSGA_StopPaymentRequest_Recored stoppay);
	
	/** 按条件 查询BR41_KZQQ控制请求内容主体表 */
	Br41_kzqq selectBr41_kzqqByVo(Br41_kzqq br41_kzqq);//请求单标识
	
	JSGA_StopPaymentResponse_Account selectBr41_kzqq_zfByVo(Br41_kzqq br41_kzqq);
	
	JSGA_FreezeResponse_Account selectBr41_kzqq_djByVo(Br41_kzqq br41_kzqq);
	
	List<JSGA_FreezeResponse_Account> selectBr41_kzqq_djList(Br41_kzqq br41_kzqq);
	
	/** 按条件 查询BR41_KZQQ_BACK控制请求反馈主体表 */
	JSGA_StopPaymentRequest_Recored selectBr41_kzqq_zf_backByVo(JSGA_StopPaymentResponse_Account stoppay);
	
	JSGA_FreezeRequest_Record selectBr41_kzqq_dj_backByVo(JSGA_FreezeResponse_Account freeze);
	
	List<JSGA_StopPaymentRequest_Recored> selectBr41_kzqq_zf_backByList(JSGA_StopPaymentResponse_Account stoppay);
	
	List<JSGA_FreezeRequest_Record> selectBr41_kzqq_dj_backByList(JSGA_FreezeResponse_Account freeze);
	
	List<JSGA_FeedbackRequest_Record> selectBr41_cxqq_hzwsList(Br41_kzqq br41_kzqq);
	
	void insertBr41_cxqq_hzws(JSGA_FeedbackRequest_Record hzws);
	
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
	
	void updateBr41_kzqq_dj_back(JSGA_FreezeRequest_Record kzqq_dj_back);
	
	void updateBr41_kzqq_zf_back(JSGA_StopPaymentRequest_Recored kzqq_zf_back);
	
	void updateBr41_kzqq(Br41_kzqq br41_kzqq);
	
	public void updateBr41_kzqqStatus(JSGA_ReturnReceipt returnReceipt);
	
	String getWjmc(Br41_kzqq br41_kzqq);
	
	void insertBr41_kzqq_hzws(JSGA_FeedbackRequest_Record wsinfo);
	
	JSGA_StopPaymentRequest_Recored selectBr41_kzqq_zf_backDisp(JSGA_StopPaymentResponse_Account stoppay);
	
	JSGA_FreezeRequest_Record selectBr41_kzqq_dj_backDisp(JSGA_FreezeResponse_Account freeze);
	
	Integer isTaskCount(Br41_kzqq br41_kzqq);
	
	public List<JSGA_FreezeRequest_Record> selectBr41_kzqq_dj_backInPacket(@Param("qqdbs") String qqdbs, @Param("tasktype") String tasktype);
}
