package com.citic.server.net.mapper;

import com.citic.server.dx.domain.Bg_Q_Attach;

public interface MM24_q_attachMapper {
	
	//删除BR24_附件表
	void delBg_q_attach(String bdhm);
	
	//插入BR24_附件表
	void insertBg_q_attach(Bg_Q_Attach br24_q_attach);
	
}
