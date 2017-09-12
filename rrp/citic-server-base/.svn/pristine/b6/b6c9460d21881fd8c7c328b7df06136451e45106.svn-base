/**========================================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created Date : 2015年5月15日 下午5:13:10
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * @author gaojianxin
 * SQL 解析服务
 */
public interface SqlParseService {
	
	/**
	 * 将原始SQL解析为可执行SQL，支持分号分割的SQL
	 * @param inSql
	 * @param parmObj
	 * @return
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public String parseSql(final String inSql, Object parmObj) throws SQLException, ParseException ;

	/**
	 * 将一组sql，解析为可执行sql
	 * @param inSqlList
	 * @param parmObj
	 * @return
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public List<String> parseSqlList(final List<String> inSqlList,Map<String, String> parmObj) throws SQLException, ParseException;

	/**
	 * 返回计算指定工作日期的SQL
	 * @param dateStr   基准日期
	 * @param holidaytype 1-对公 2-对私
	 * @param amount
	 * @return
	 */
	public String getCalHolidaySql(String dateStr,String holidaytype,int amount);
}
