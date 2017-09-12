package com.citic.server.net.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.citic.server.service.domain.MC21_task;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MC21_task_status;

public interface MC21_task_factMapper {
	
	/**
	 * 查询待处理的任务清单
	 * 
	 * @param tableName 任务表名
	 * @param taskType 任务类型
	 * @param taskModule 任务模块
	 * @return
	 * @author liuxuanfei
	 * @date 2017/07/20 13:58:19
	 */
	public List<MC21_task_fact> queryNextExecutoryTaskList(@Param("tablename") String tableName, @Param("tasktype") String taskType, @Param("serverid") String taskModule);
	
	ArrayList<MC21_task_fact> getMC21_task_factList(MC21_task_fact mC21_task_fact);
	
	ArrayList<MC21_task_fact> getMC22_task_factList(MC21_task_fact mC21_task_fact);
	
	public int lockTask(MC21_task_fact mC21_task_fact);
	
	int updateMC21_task_fact(MC21_task_fact mC21_task_fact);
	
	void insertMC21_task_status(MC21_task_status mc21_task_status);
	
	public int deleteMC21_task_status(MC21_task_status mc21_task_status);
	
	ArrayList<MC21_task> getMC21_taskList();
	
	void resetAllMC21_task_status(MC21_task_fact task_fact);
	
	int unLockAllTask(MC21_task_fact task_fact);
	
}
