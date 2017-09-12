package com.citic.server.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MC00_task_status implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	
	   private String  taskid      = "";
	   private String  subtaskid   = "";
	   private String  datatime    = "";
	   private String  serverid    = ""; 
	   private String  freq        = "";
	   private String  trigerid    = "";
	   private String  dsid		  = "";
	   private String  taskname    = "";
	   private String  tgroupid    = "";
	   private String  begintime   = "";
	   private String  endtime     = "";
	   private String  usetime     = "";
	   private String  calstatus   = "";
	   private String  taskcmd     = ""; 
	   private String errormsg     = ""; 

}
