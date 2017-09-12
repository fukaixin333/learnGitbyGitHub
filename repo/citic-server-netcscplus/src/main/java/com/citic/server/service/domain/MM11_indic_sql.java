package com.citic.server.service.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MM11_indic_sql implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;
	

	private String indickey   = "";
	private String indicseq   = "";
	private String sqlseq     = "";
	private String sqlstr     = "";

}
