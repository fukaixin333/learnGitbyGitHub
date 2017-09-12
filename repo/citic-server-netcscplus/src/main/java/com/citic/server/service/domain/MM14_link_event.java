package com.citic.server.service.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MM14_link_event implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	
	 /**模型编码*/
	  private String modelkey     = "";
	  /**环节编码*/
	  private String linkkey     = "";
	  /**事件编码*/
	  private String eventkey     = "";
	  /**分值*/
	  private String event_score      = "";
	  

}
