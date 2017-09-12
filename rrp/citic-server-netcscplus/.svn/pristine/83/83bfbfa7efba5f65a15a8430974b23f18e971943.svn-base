package com.citic.server.service.tasksplit;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.service.domain.MM11_indic;
import com.citic.server.service.domain.MM14_model;
import com.citic.server.net.mapper.MM14_modelMapper;
 

/**
 * @author hubq
 * @version 1.0
 */

public class Split_model extends BaseSplit {

	public Split_model(ApplicationContext ac,String taskid,String tasksplit,String splitparams) {
		super(ac,taskid,tasksplit,splitparams);
	}
	
	/**
	 * 
	 */
	public ArrayList getSubtaskidList() throws Exception{
		ArrayList subtaskidList = new ArrayList();
		MM14_modelMapper mm14_modelMapper = (MM14_modelMapper)this.getAc().getBean("MM14_modelMapper");
		ArrayList list = mm14_modelMapper.getMM14_modelList( );
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			MM14_model mm14_model = (MM14_model)iter.next();
			subtaskidList.add( mm14_model.getModelkey() );
		}
		return subtaskidList;
	}
	
}