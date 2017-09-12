package com.citic.server.net.mapper;

import com.citic.server.service.domain.MM12_event_log;

public interface MM12_event_logMapper {
	
	void insertMM12_event_log(MM12_event_log mm12_event_log);
	
	void deleteMM12_event_log(String eventkey);

}

