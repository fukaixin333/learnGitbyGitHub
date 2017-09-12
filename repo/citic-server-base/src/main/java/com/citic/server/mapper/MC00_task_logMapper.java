package com.citic.server.mapper;

import java.util.ArrayList;

import com.citic.server.domain.MC00_task_log;

public interface MC00_task_logMapper {
	
	ArrayList<MC00_task_log> getMC00_task_logList(MC00_task_log mC00_task_log);

	void insertMC00_task_log(MC00_task_log mC00_task_log);

	int modifyMC00_task_log(MC00_task_log mC00_task_log);

	void deleteMC00_task_log(String username);
	
	public MC00_task_log getMC00_task_logDisp(String username);
}

