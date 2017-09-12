/**========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年4月17日
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.dx.service;

import java.util.List;

import com.citic.server.dx.domain.Br24_bas_dto;

/**
 * @author gaojx
 *
 */
public interface CodeChange {
	/**
	 * 码表转换
	 * @param br24_bas_dto  要转换的DTO
	 * @param desTabName 目标表名称，全部大写
	 * @param query_datasource 1.银行业务系统 2.批后数据
	 */
	public Br24_bas_dto chgCode(Br24_bas_dto br24_bas_dto,String desTabName,String query_datasource);
	
	/**
	 * 码表转换
	 * @param dtoList 转换的DTOLIST
	 * @param desTabName 目标表名称，全部大写
	 * @param query_datasource 1.银行业务系统 2.批后数据
	 */
	public List<? extends Br24_bas_dto> chgCode(List<? extends Br24_bas_dto>  dtoList,String desTabName,String query_datasource);
	
}
