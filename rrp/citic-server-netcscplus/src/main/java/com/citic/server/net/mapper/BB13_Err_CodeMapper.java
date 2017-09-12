package com.citic.server.net.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.inner.domain.AnswerCode;

public interface BB13_Err_CodeMapper {
	
	public List<AnswerCode> selectAllErrCode();
	
	public AnswerCode selectErrCodeBySCode(@Param("scode")  String scode, @Param("type")  String type);
}
