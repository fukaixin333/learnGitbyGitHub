package com.citic.server.service.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MC21_task_log implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	
	   private String taskid      = ""; 
	   private String trigerid    = ""; 
	   private String datatime    = ""; 
	   private String freq        = ""; 
	   private String dsid        = ""; 
	   private String organid     = ""; 
	   private String tasktype    = ""; 
	   private String caltime     = ""; 
	   private String calstatus   = ""; 

}
