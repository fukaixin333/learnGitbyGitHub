package com.citic.server.net.mapper;

import com.citic.server.service.domain.MM11_indic_sql;

public interface MM11_indic_logMapper {
	
	void insertMM11_indic_log(MM11_indic_sql mm11_indic_sql);
	
	void deleteMM11_indic_log(String indickey);

}

