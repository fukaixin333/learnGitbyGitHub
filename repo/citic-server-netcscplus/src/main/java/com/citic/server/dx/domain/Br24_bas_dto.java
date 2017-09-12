/**========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月17日
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.dx.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author gaojx
 * 查询基本类DTO
 */
@Data
public class Br24_bas_dto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3404315358018312867L;
	/** 传输报文流水号（参见附录H） */
	private String transSerialNumber;
	/** 查询日期*/
	private String qrydt;
	
	public String getTransserialnumber() {
		return transSerialNumber;
	}


}
