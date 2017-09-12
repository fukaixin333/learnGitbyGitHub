package com.citic.server.service.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MM12_entity_cfg implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	

	  private String entitykey     = "";
	  private String sourcecol     = "";
	  private String targetcol     = "";
	  
}
