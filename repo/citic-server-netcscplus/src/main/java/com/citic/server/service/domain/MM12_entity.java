package com.citic.server.service.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MM12_entity implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	

	  private String entitykey     = "";
	  private String entityame     = "";
	  private String fact_table     = "";
	  private String fact_pk     = "";
	  private String fact_s_table     = "";
	  private String fact_s_pk     = "";
	  private String iscopy     = "";
	  private String makenew_pk     = "";

}
