package com.citic.server.mapper;

import java.util.ArrayList;

import com.citic.server.domain.MC02_rt_task_fact;


public interface MC02_rt_task_factMapper {
	
	int insertMc02_rt_task_fact(MC02_rt_task_fact mc02_rt_task_fact);

	int updateMc02_rt_task_fact(MC02_rt_task_fact mc02_rt_task_fact);
	
	int resetTaskFactStatus(String serverkey);
	int taskIsSet(MC02_rt_task_fact mc02_rt_task_fact);

	ArrayList<MC02_rt_task_fact> selectMc02_rt_task_factList();
	ArrayList<MC02_rt_task_fact> IsSetTaskListExecute(String key);

}

