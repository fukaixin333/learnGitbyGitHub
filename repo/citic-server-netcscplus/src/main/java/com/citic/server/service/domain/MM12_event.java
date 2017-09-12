package com.citic.server.service.domain;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class MM12_event implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	

	  private String eventkey     = "";
	  private String event_des    = "";
	  private String event_type   = "";
	  private String interfactkey = "";
	  private String pbckey       = "";
	  private String stcrkey      = "";
	  private String curr_cd      = "";
	  private String freq         = "";
	  private String flag         = "";
	  private String deployflag   = "";
	  private String create_dt    = "";
	  private String creator      = "";
	  private String create_org   = "";
	  private String modifier     = "";
	  private String modify_dt    = "";
	  
	  
	  private String modelkey = "";

}
