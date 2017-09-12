/**========================================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created Date : 2015年5月15日 下午5:21:44
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.service.sqlparse;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citic.server.service.SqlParseService;
import com.citic.server.service.sqlparse.comm.DasConstants;
import com.citic.server.utils.DbFuncUtils;
import com.google.common.collect.Lists;

/**
 * @author gaojianxin
 *
 */
public abstract class DefaultSqlParseService implements SqlParseService {
	private final Logger logger = LoggerFactory.getLogger(DefaultSqlParseService.class);
	

	private DbFuncUtils dbFuncUtils  = new DbFuncUtils();
   
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.citic.server.service.SqlParseService#parseSql(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public String parseSql(final String inSql, Object parmObj) throws SQLException, ParseException {
		logger.debug("in sql:" + inSql);

		// 1.规则化SQL,并替换between
		String tmpSql = dbFuncUtils.toTruncateSql(inSql);

		// 2.替换静态参数
		tmpSql = replaceStaticParm(tmpSql, parmObj, false);

		// 3.去除空值
		logger.debug("out sql:" + tmpSql);

		return tmpSql;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.citic.server.service.SqlParseService#parseSqlList(java.util.List,
	 * java.util.Map)
	 */
	@Override
	public List<String> parseSqlList(List<String> inSqlList, Map<String, String> parmObj) throws SQLException, ParseException {
		List<String> outsqlList = Lists.newArrayList();
		for (String sql : inSqlList) {
			outsqlList.add(parseSql(sql, parmObj));
		}
		return outsqlList;
	}

	/**
	 * 转换为静态数据库日期函数
	 * 
	 * @param dataStr
	 * @return
	 * @throws ParseException
	 */
	protected abstract String toStaticDbDate(String dataStr) throws ParseException;

	/**
	 * 处理静态SQL
	 * 
	 * @param appSql
	 * @param bean
	 * @param isRemoveNulCond
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	private String replaceStaticParm(String appSql, Object bean, boolean isRemoveNulCond) throws SQLException, ParseException {
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile(DasConstants.STATIC_PARRERN);
		Matcher m = p.matcher(repMultiLike(appSql, bean));
		String appendStr = "";
		String chgType = "";
		MetaObject metBean = null;
		boolean isMap = false;
		Map<String, Object> mapBean = null;
		String tmpObj = null;

		if (bean instanceof Map) {
			mapBean = (Map<String, Object>) bean;
			isMap = true;
		} else {
			metBean = SystemMetaObject.forObject(bean);
		}

		while (m.find()) {
			tmpObj = null;

			String findStr = m.group();
			// 去除前后引导符
			String tmpStr = findStr.substring(1, findStr.length() - 1);
			String[] keyStr = StringUtils.split(tmpStr.toLowerCase(), DasConstants.SEPARATOR);
			// 取需要替换的数据类型
			chgType = keyStr.length > 1 ? keyStr[1].toLowerCase() : DasConstants.DEFAULT_PARM_TYPE;

			try {
				if (isMap) {
					tmpObj = (String) mapBean.get(keyStr[0]);
				} else {
					tmpObj = (String) metBean.getValue(keyStr[0]);
				}
			} catch (Exception e) {
				logger.warn("[" + keyStr[0] + "]属性信息不存在");
			}
			appendStr = getValFromStr(tmpObj, chgType, isRemoveNulCond);
			m.appendReplacement(sb, (appendStr == null ? "''" : appendStr));
		}
		m.appendTail(sb);

		return sb.toString();
	}

	/**
	 * 静态替换字符串值
	 * 
	 * @param inStr
	 * @param chgType
	 * @param isRepNull
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	private String getValFromStr(String inStr, String chgType, boolean isRepNull) throws SQLException, ParseException {
		StringBuffer sb = new StringBuffer(64);

		if (StringUtils.isBlank(inStr)) {
			return isRepNull ? DasConstants.STR_NULL : null;
		}

		// 去掉左右括号及单引号
		String tmpStr = inStr.replaceAll("[()']", "");

		if (chgType.equalsIgnoreCase("char")) {
			// 判断是否多值
			if (StringUtils.indexOf(tmpStr, ",") > 0) {
				String[] tmpvals = StringUtils.split(tmpStr, ",");
				if (tmpvals.length > 1000) {// 超过1000个，抛出异常
					throw new SQLException("参数值超过数据库的最大限制：\n" + tmpStr);
				}
				sb.append("(");
				for (int i = 0; i < tmpvals.length; i++) {
					if (i > 0) {
						sb.append(",");
					}
					sb.append("'").append(tmpvals[i]).append("'");
				}
				sb.append(")");
				//
			} else {
				sb.append("'").append(tmpStr).append("'");
			}
		} else if (chgType.equalsIgnoreCase("date")) {
			// 判断是否多值
			if (StringUtils.indexOf(tmpStr, ",") > 0) {
				String[] tmpvals = StringUtils.split(tmpStr, ",");
				if (tmpvals.length > 1000) {// 超过1000个，抛出异常
					throw new SQLException("参数值超过数据库的最大限制：\n" + tmpStr);
				}
				sb.append("(");
				for (int i = 0; i < tmpvals.length; i++) {
					if (i > 0) {
						sb.append(",");
					}
					sb.append(toStaticDbDate(tmpvals[i]));
				}
				sb.append(")");
			} else {
				sb.append(toStaticDbDate(inStr));
			}
		} else {
			if (StringUtils.indexOf(inStr, ",") > 0) {
				sb.append("(").append(tmpStr).append(")");
			} else {
				sb.append(tmpStr);
			}
		}

		return sb.toString();
	}

	/**
	 * 处理多值模糊输入
	 * 
	 * @param inStr
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String repMultiLike(String inStr, Object bean) {
		StringBuffer sb = new StringBuffer(inStr.length());
		String[] tmpStr = StringUtils.split(inStr);
		String tmpObj = "";
		StringBuffer colSb = null;
		StringBuffer mulLikeSb = null;
		int conFlag = 0;
		String isOr = "";
		MetaObject metBean = null;
		boolean isMap = false;

		if (bean == null) {
			return inStr;
		}

		if (bean instanceof Map) {
			isMap = true;
		} else {
			metBean = SystemMetaObject.forObject(bean);
		}

		for (int i = 0; i < tmpStr.length; i++) {
			if (tmpStr[i].equals("where") || tmpStr[i].equals("and") || tmpStr[i].equals("or")) {
				conFlag = i;
				continue;
			}
			if (conFlag < 1) {
				continue;
			}
			if (tmpStr[i].equals("like")) {
				if (tmpStr[i + 1].charAt(0) != DasConstants.STATIC_SING) {// 非静态替换，不做处理
					continue;
				}
				mulLikeSb = new StringBuffer();
				colSb = new StringBuffer();

				if (tmpStr[i - 1].equals("not")) {
					isOr = " and ";
				} else {
					isOr = " or ";
				}
				// 去除前后引导符
				String[] keyStr = StringUtils.split((tmpStr[i + 1].substring(1, tmpStr[i + 1].length() - 1)), DasConstants.SEPARATOR);

				if (isMap) {
					tmpObj = (String) ((Map) bean).get(keyStr[0]);
				} else {
					tmpObj = (String) metBean.getValue(keyStr[0]);
				}
				if (StringUtils.isEmpty(tmpObj)) {// 空值，暂不做处理，由后续统一处理
					continue;
				}

				// 去掉首尾小括号,以及用户输入的单引号，防止出现“**公司（北京）”这样的输入内容
				if (tmpObj.charAt(0) == '(') {
					tmpObj = StringUtils.removeEnd(StringUtils.removeStart(tmpObj, "("), ")");
				}
				tmpObj = StringUtils.remove(tmpObj, "'");

				for (int j = conFlag + 1; j < i; j++) {
					colSb.append(tmpStr[j]).append(" ");
					tmpStr[j] = "";
				}
				tmpStr[i] = "";
				String[] objStrs = StringUtils.split(tmpObj, DasConstants.MUL_VAL_SEPARATOR);
				for (int k = 0; k < objStrs.length; k++) {
					if (k > 0) {
						mulLikeSb.append(isOr);
					}
					mulLikeSb.append(colSb).append(" like '").append(objStrs[k]).append("'");
				}
				tmpStr[i + 1] = "(" + mulLikeSb + ")";
			}

		}

		for (int i = 0; i < tmpStr.length; i++) {
			if (!(tmpStr[i].equals(""))) {
				sb.append(" ").append(tmpStr[i]);
			}
		}
		return sb.toString();
	}

}
