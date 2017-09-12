package com.citic.server.service.tasksplit;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.service.base.Base;

/**
 * 
 * @author hubq
 * @version 1.0
 */

public abstract class BaseSplit extends Base{
	
	private static final Logger logger = LoggerFactory.getLogger(BaseSplit.class);

	private String taskid = "";
	
	private String tasksplit = "";
	
	/** 可能需要传入1个或者多个参数，使用时，再拆分 */
	private String splitparams = "";
	
	private ApplicationContext ac;
	
    public BaseSplit(ApplicationContext _ac,String taskid,String tasksplit,String _splitparams) {
    	this.ac = _ac;
    	this.taskid = taskid;
    	this.tasksplit = tasksplit;
    	this.splitparams = _splitparams;
    }
    
    /**
     * @return
     * @throws Exception
     */
    public abstract ArrayList getSubtaskidList() throws Exception;
    
    public String getTaskid(){
    	return this.taskid;
    }

    public String getTasksplit(){
    	return this.tasksplit;
    }
    
    public ApplicationContext getAc(){
    	return this.ac;
    }

	public String getSplitparams() {
		return this.splitparams;
	}
    
}
