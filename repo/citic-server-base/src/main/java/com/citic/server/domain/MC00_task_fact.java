package com.citic.server.domain;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class MC00_task_fact implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	
	   private String taskid      = ""; 
	   private String subtaskid   = "";
	   private String datatime    = ""; 
	   private String serverid    = ""; 
	   private String dsid		  = "";
	   private String taskname    = ""; 
	   private String tgroupid    = ""; 
	   private String trigerid    = ""; 
	   private String freq        = ""; 
	   private String taskcmd     = ""; 
	   //重算标志
	   private String reflag     = ""; 
//	   /**
//	    * 如果指标要按照机构分别计算，那么本处
//	    */
//	   private String organkey    = "";
	   
	   private ArrayList tableList= new ArrayList();
	   
	   //private String global_temporary_prestr = "";
	   /** 任务来源 默认：product-生产；lab-模型实验室 */
	   private String tasksource = "product";
}
