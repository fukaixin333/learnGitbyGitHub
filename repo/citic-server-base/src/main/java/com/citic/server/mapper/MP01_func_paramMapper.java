package com.citic.server.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Select;

import com.citic.server.domain.MP01_func_param;

public interface MP01_func_paramMapper {
	
	@Select("select * from MP01_func_param")
	ArrayList<MP01_func_param> getMP01_func_paramList();
}

