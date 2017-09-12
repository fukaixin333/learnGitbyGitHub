package com.citic.server.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class MP01_system_param implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178546082202025088L;

	private String paramtype      = "";
	private String paramkey    = "";
	private String paramvalue   = "";
	private String des = "";
}
