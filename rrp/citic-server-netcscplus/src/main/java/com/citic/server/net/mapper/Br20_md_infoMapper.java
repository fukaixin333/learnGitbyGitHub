package com.citic.server.net.mapper;

import java.util.ArrayList;
import java.util.List;

import com.citic.server.dx.domain.Br20_md_info;

public interface Br20_md_infoMapper {
	//白名单数据
	ArrayList<Br20_md_info> getWhiteList(String s);
	
	//库存的文件名
	ArrayList<String> getDealFileName(Br20_md_info br20_md_info);
	
	//修改名单数据状态
	void updateMd_flagByOperate_flag(Br20_md_info br20_md_info);
	
	//插入一条名单日志数据
	void insertBr20_md_data_log(Br20_md_info br20_md_info);
	
	//插入一条名单数据
	void insertBr20_md_data(Br20_md_info br20_md_info);
	
	//插入一条名单文件接收记录
	void insertBr20_md_accept(Br20_md_info br20_md_info);
	
	//修改名单文件接收状态
	void updateFlagByDo(String file_code);
	
	//根据文件名修改名单文件接收状态
	void updateFlagByFileName(String filename);
	
	//修改名单文件接收状态--失败
	void updateFlagByFail(String filename);
	
	//删除数据日志表
	void deleteBr20_md_data_log(String md_type);
	
	//删除数据表中非白名单数据
	void deleteBr20_md_dataByVo(String md_type);
	
	//配置信息
	ArrayList<Br20_md_info> getTaskInfo();
	
	//增量待发送的数据
	ArrayList<Br20_md_info> getIncrementSendData(String send_flag);
	
	//全量待发送的数据
	ArrayList<Br20_md_info> getAllSendData();
	
	//插入一条名单文件发送记录
	void insertBr20_md_send(Br20_md_info br20_md_info);
	
	//修改下次发送时间和最后发送时间
	void updateBr_md_taskByVo(Br20_md_info br20_md_info);
	
	//修改名单发送状态
	void updateSend_flagBykeys(Br20_md_info br20_md_info);
	
	//修改指定时间前的名单发送状态
	void updateSend_flagByTime(Br20_md_info br20_md_info);
	
	//修改名单未发送状态为中间状态
	void updateSend_flagByMiddle(String send_flag);
	
	//获取全量文件加载标识
	String getAllLoadFlag();
	
	//获取已处理的最近日期文件名，按文件类型和名单类型取
	String getMaxFileName(Br20_md_info br20_md_info);
	
	//获取已发送的最近日期文件名
	String getMaxSendFileName();
	
	//删除数据表中满足条件的值
	void deleteBr20_md_dataByKey(Br20_md_info br20_md_info);
	
	//批量删除Br20_md_data数据
	void deleteBr20_md_dataByBatch(List<Br20_md_info> list);
	
	//根据文件批次，筛除最新的有效的非白名单数据批量插入Br20_md_data
	void insertBr20_md_dataByNewAndBatch(Br20_md_info br20_md_info);
	
	//根据文件批次，批量删除Br20_md_data中非白名单数据
	void deleteBr20_md_dataByFlagAndBatch(String file_code);
}
