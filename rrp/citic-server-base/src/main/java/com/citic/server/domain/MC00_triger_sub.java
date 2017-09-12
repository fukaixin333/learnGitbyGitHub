package com.citic.server.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MC00_triger_sub implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	

	  private String trigerid       = "";  
	  private String trigercondid   = "";  
	  private String param1         = "";  
	  private String param2         = "";  
	  private String param3         = ""; 
	  
	  //private String dstriger	  = "";//如果被触发任务有这个属性，就带过来

}
