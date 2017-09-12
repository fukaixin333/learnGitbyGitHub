package com.citic.server.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class BB13_code_filter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2087585177886917042L;
	
	private String code = "";
	private String value = "";
	private String flag = "";
}
