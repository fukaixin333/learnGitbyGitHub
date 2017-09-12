/**========================================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created Date : 2015年5月25日 上午9:10:07
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaojianxin
 *
 */

@Service
public class CalSqlService {
	private final Logger logger = LoggerFactory.getLogger(CalSqlService.class);

	@Autowired
	private SqlParseService sqlParseService;

	@Autowired
	private ParameterService parameterService;

	/**
	 * 实时接口服务
	 * 
	 * @param busikey
	 * @param transDto
	 * @param orgSq
	 * @return
	 * @throws Exception
	 */
	public String getExecSql(final String busikey, Object transDto, final String orgSq) throws Exception {
		String execSql = sqlParseService.parseSql(orgSq, transDto);
		return execSql;
	}

	/**
	 * 将原始SQL解析为可执行sql，自动查找，替换参数，不对参数值为空的情况，进行特殊处理
	 * 
	 * @param busikey
	 *            规则或事件等编码
	 * @param statisticdate
	 *            数据日期
	 * @param orgSql
	 *            原始SQL
	 * @param specParmObj
	 *            原始sql中的特殊参数，如机构等,可以为空
	 * @return
	 * @throws Exception
	 */
	public String getExecSql(final String busikey, final String statisticdate, final String orgSql, final Map<String, String> specParmObj) throws Exception {
		String execSql = "";
		Map<String, String> parmMap = parameterService.getDefaultVal(statisticdate);
		parmMap.putAll(parameterService.getFuncParmVal(statisticdate));
		parmMap.putAll(parameterService.getBusiParmVal(busikey));

		if (specParmObj != null && specParmObj.size() > 0) {
			parmMap.putAll(specParmObj);
		}

		execSql = sqlParseService.parseSql(orgSql, parmMap);

		return execSql;
	}

}
