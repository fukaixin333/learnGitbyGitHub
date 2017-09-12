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

public class Split_event extends BaseSplit {

	public Split_event(ApplicationContext ac,String taskid,String tasksplit,String splitparams) {
		super(ac,taskid,tasksplit,splitparams);
	}
	
	/**
	 * 
	 */
	public ArrayList getSubtaskidList() throws Exception{
		ArrayList subtaskidList = new ArrayList();
		//splitparams  = eventtype,callevel
		
		String splitparams = this.getSplitparams();
		String[] params = splitparams.split(",");//默认都必须有的两个参数
		String freq = params[0];
		String dsid = params[1];
		
		MM12_event mm12_event = new MM12_event();
		mm12_event.setFreq(params[0]);
		//1-交易特征；2-主体特征;3-网络特征
		mm12_event.setEvent_type( params[2] );
		
		MM12_eventMapper mm12_eventMapper = (MM12_eventMapper)this.getAc().getBean("MM12_eventMapper");
		ArrayList list = mm12_eventMapper.getMM12_eventList(mm12_event);
		
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			MM12_event _mm12_event = (MM12_event)iter.next();
			subtaskidList.add( _mm12_event.getEventkey() );
		}
		
		return subtaskidList;
	}
	
}