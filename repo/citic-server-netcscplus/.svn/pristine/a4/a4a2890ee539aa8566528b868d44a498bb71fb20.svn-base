package com.citic.server.net.mapper;

import java.util.List;

import com.citic.server.dx.domain.Bg_Q_Attach;
import com.citic.server.dx.domain.Br22_StopPay;
import com.citic.server.dx.domain.Br25_Freeze;
import com.citic.server.dx.domain.Br25_Freeze_back;
import com.citic.server.dx.domain.Br25_StopPay;
import com.citic.server.dx.domain.Br25_StopPay_back;

public interface BR25_kzqqMapper {
	//查询止付请求
	Br25_StopPay getBr25_stopByID(String transserialnumber);
	
	//查询止付请求响应
	Br25_StopPay_back getBr25_stopBackByID(Br25_StopPay_back br25_stoppay_back);
	
	//查询冻结请求
	Br25_Freeze getBr25_frozenByID(String transserialnumber);
	
	//查询冻结请求响应
	Br25_Freeze_back getBr25_frozenBackByID(Br25_Freeze_back br25_freeze_back);
	
	//删除止付控制请求
	void delBr25_kzqq_zf(String transserialnumber);
	
	//删除止付控制响应
	void delKzResBw_zf(String transserialnumber);
	
	//删除冻结控制请求
	void delBr25_kzqq_dj(String transserialnumber);
	
	//删除冻结控制响应
	void delKzResBw_dj(String transserialnumber);
	
	//删除冻结延期附件
	void delBr25_Attach(String transserialnumber);
	
	//插入止付控制请求
	void insertBr25_kzqq_zf(Br25_StopPay Cg_StopPay);
	
	//插入冻结控制请求
	void insertBr25_kzqq_dj(Br25_Freeze cg_Freeze);
	
	//修改冻结控制请求表状态
	void updateBr25_kzqq_dj_status(Br25_Freeze_back cs_Freeze);
	
	//修改冻结控制请求表状态
	void updateBr25_frozen_dj_status(Br25_Freeze_back cs_Freeze);
	
	//插入止付控制请求响应
	void insertBr25_stop_back(Br25_StopPay_back cs_stoppay);
	
	//修改止付控制请求响应表状态
	void updateBr25_kzqq_zf_status(Br25_StopPay_back cs_stoppay);
	
	//修改止付控制请求表状态
	void updateBr25_stop_zf_status(Br25_StopPay_back cs_stoppay);
	
	//插入冻结请求附件
	void insertBr25_frozen_attach(Bg_Q_Attach bg_Q_Attach);
	
	//插入冻结控制请求
	void insertBr25_frozen_back(Br25_Freeze_back cs_Freeze);
	
	//查询人工举报止付信息
	List<Br22_StopPay> getBr22_StopPayByID(String originalapplicationid);
	
}
