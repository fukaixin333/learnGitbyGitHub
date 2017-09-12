package com.citic.server.gf.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Br32_msg implements Serializable {
	
	private static final long serialVersionUID = 9114366095468279839L;
	
	private String msgkey = "";
	
	private String bdhm = "";
	
	private String msg_type_cd = "";
	
	private String packetkey = "";
	
	private String organkey_r = "";
	
	private String senddate = "";
	private String msg_filename = "";
	private String msg_filepath = "";
	
	private String status_cd = "";
	
	private String create_dt = "";
	
	private String qrydt="";
}
