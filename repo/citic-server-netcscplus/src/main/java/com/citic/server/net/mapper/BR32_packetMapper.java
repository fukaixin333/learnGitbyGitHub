package com.citic.server.net.mapper;

import java.util.List;

import com.citic.server.gf.domain.Br32_msg;
import com.citic.server.gf.domain.Br32_packet;
import com.citic.server.gf.domain.request.RollbackRequest_Htxx;

public interface BR32_packetMapper {
	//查询数据包表
	Br32_packet getBr32_packet(String packetkey);
	
	//查询回退信息
	List<RollbackRequest_Htxx> getBr30_Htxx(String packetkey);
	
	List<RollbackRequest_Htxx> getBr31_Htxx(String packetkey);
	
	//查询报文表
	List<Br32_msg> getBr32_msg(String packetkey);
	
}
