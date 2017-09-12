package com.citic.server.mapper;

import java.util.ArrayList;

import com.citic.server.domain.MC00_task_fact;
import com.citic.server.domain.MC00_task_status;

public interface MC00_task_factMapper {
	
	ArrayList<String> getTrigeridListByDatatime(String datatime);

	ArrayList<MC00_task_fact> getMC00_task_factList();

	ArrayList<MC00_task_fact> getMC00_task_factListByDt(String datatime);
	
	
	void insertMC00_task_fact(MC00_task_fact mC00_task_fact);
	void insertMC00_task_log(MC00_task_status mc00_task_status);
	
	int updateMC00_task_fact(MC00_task_fact mC00_task_fact);

	int lockTask(MC00_task_fact mC00_task_fact);
	
	int unLockTask(String serverid);
	
	int unLockAllTask();
	
	int unLockCurrTask(MC00_task_fact mC00_task_fact);
	void insertMC00_task_fact_his(String datatime);
	void deleteMC00_task_factByDatatime(String datatime);
	void deleteMC00_task_fact_hisByDatatime(String datatime);
	int getMC00_task_factCount(String datatime);
	
	ArrayList<MC00_task_fact> getMC00_retask_factList();
	void deleteMC00_retask_fact(MC00_task_fact mC00_task_fact);
	
}

