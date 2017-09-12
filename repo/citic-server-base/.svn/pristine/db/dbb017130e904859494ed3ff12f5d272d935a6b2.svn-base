package com.citic.server.mapper;

import java.util.ArrayList;

import com.citic.server.domain.MC00_task_status;

public interface MC00_task_statusMapper {
	
	ArrayList<MC00_task_status> getMC00_task_statusList(MC00_task_status mC00_task_status);
	ArrayList<MC00_task_status> getMC00_task_statusListForAllTask(MC00_task_status mC00_task_status);
	
	ArrayList<MC00_task_status> getMC00_task_statusListForNotSet(MC00_task_status mC00_task_status);
	ArrayList<MC00_task_status> getMC00_task_statusSameFreqListForNotSet(MC00_task_status mC00_task_status);
	ArrayList<MC00_task_status> getMC00_task_statusListSameGrouForNotSet(MC00_task_status mC00_task_status);
	
	ArrayList<MC00_task_status> getMC00_task_statusListGroupbytaskid(String datatime);
	
	void insertMC00_task_status(MC00_task_status mC00_task_status);

	int modifyMC00_task_status(MC00_task_status mC00_task_status);

	void deleteMC00_task_status(MC00_task_status mC00_task_status);

	//
	void resetMC00_task_status(String serverid);
	void resetAllMC00_task_status();
	
	int getMC00_task_statusCount(String datatime);
}


