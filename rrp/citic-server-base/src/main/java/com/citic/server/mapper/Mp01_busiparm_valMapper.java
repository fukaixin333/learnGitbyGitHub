package com.citic.server.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Select;

import com.citic.server.domain.Mp01_busiparm_val;

public interface Mp01_busiparm_valMapper {
	
	@Select("select * from Mp01_busiparm_val where busikey = #{busikey} order by parmvalkey,subitemkey" )
	ArrayList<Mp01_busiparm_val> getMp01_busiparm_valList(String busikey);

}

