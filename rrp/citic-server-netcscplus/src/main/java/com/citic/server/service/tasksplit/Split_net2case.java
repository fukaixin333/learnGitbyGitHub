package com.citic.server.service.tasksplit;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.service.domain.MM12_event;
import com.citic.server.net.mapper.MM12_eventMapper;

/**
 * @author hubq
 * @version 1.0
 */

public class Split_net2case extends BaseSplit {

	public Split_net2case(ApplicationContext ac,String taskid,String tasksplit,String splitparams) {
		super(ac,taskid,tasksplit,splitparams);
	}

	/**
	 * 网络转案件过程中，503任务拆分
	 */
	public ArrayList getSubtaskidList() throws Exception{
		ArrayList subtaskidList = new ArrayList();
		
		subtaskidList.add("net&event");
		
		//subtaskidList.add("event");
		
		return subtaskidList;
	}
	
}