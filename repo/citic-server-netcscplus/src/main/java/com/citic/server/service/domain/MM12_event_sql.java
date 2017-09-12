package com.citic.server.service.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MM12_event_sql implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	

	private String eventkey   = "";
	private String eventseq   = "";
	private String sqlseq     = "";
	private String sqlstr     = "";

}
