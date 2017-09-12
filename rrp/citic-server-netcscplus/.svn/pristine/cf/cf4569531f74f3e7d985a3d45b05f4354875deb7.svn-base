package com.citic.server.service.tasksplit;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.service.domain.MM11_indic;
import com.citic.server.net.mapper.MM11_indicMapper;
 

/**
 * @author hubq
 * @version 1.0
 */

public class Split_indicator extends BaseSplit {

	public Split_indicator(ApplicationContext ac,String taskid,String tasksplit,String splitparams) {
		super(ac,taskid,tasksplit,splitparams);
	}

	/**
	 * 
	 */
	public ArrayList getSubtaskidList() throws Exception{
		ArrayList subtaskidList = new ArrayList();
		//splitparams  = indictype,callevel
		
		String splitparams = this.getSplitparams();
		String[] params = splitparams.split(",");
		String freq = params[0];
		String dsid = params[1];
		
		MM11_indic mm11_indic = new MM11_indic();
		mm11_indic.setFlag("1");
		mm11_indic.setFreq(params[0]);
		mm11_indic.setIndictype( params[2] );
		//mm11_indic.setCallevel( params[3] );
		
		MM11_indicMapper mm11_indicMapper = (MM11_indicMapper)this.getAc().getBean("MM11_indicMapper");
		ArrayList list = mm11_indicMapper.getMM11_indicList(mm11_indic);
		
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			MM11_indic _mm11_indic = (MM11_indic)iter.next();
			
			String indickey = _mm11_indic.getIndickey();
			String callevel = _mm11_indic.getCallevel();//对于指标需要分组去执行（层次）
			
			subtaskidList.add( indickey+"--"+callevel );
		}
		
		return subtaskidList;
	}
	
}