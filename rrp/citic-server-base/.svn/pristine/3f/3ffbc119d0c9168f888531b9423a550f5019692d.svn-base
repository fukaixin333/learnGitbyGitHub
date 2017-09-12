/**
 * 数据常用字符工具类
 */
package com.citic.server.service.sqlparse.comm;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author gaosen
 *
 */
public class SqlStrUtils {
	private static final Logger logger = LoggerFactory.getLogger(SqlStrUtils.class);

	/**
	 * 清理输入sql末尾的order by，考虑到只清理最后一个from之后的order by 清理时忽略order by 大小写
	 * 
	 * @param strSql
	 * @return
	 */
	public static String clrOrderBy(String strSql) {
		String instr = chgWhitespace(strSql);
		int orderIndex = StringUtils.lastIndexOfIgnoreCase(instr, " order ");
		int lastFromIndex = StringUtils.lastIndexOfIgnoreCase(instr, " from ");

		return orderIndex > lastFromIndex ? instr.substring(0, orderIndex) : instr;

	}

	/**
	 * 判断是否复杂SQL
	 * 
	 * @param strSql
	 * @return
	 */
	public static boolean isCompSql(String strSql) {
		boolean isComp = false;
		String instr = chgWhitespace(strSql);

		for (String sqlKey : DasConstants.COMPSQL_KEYS) {
			if (StringUtils.indexOfIgnoreCase(instr, " " + sqlKey + " ") > 1) {
				isComp = true;
				break;
			}
		}
		return isComp;
	}

	/**
	 * 是否是对临时表进行增，删，改，创表等操作
	 * 
	 * @param inSql
	 * @return true(表示该sql没问题)
	 */
//	public static boolean isUpdateTmpTable(String sql) {
//		Assert.notNull(sql, "sql不能为空");
//
//		String[] sqlArray = StringUtils.split(sql);
//		boolean isSucc = false;
//
//		for (int j = 0; j < sqlArray.length; j++) {
//			if (ArrayUtils.contains(DasConstants.UPDATE_SQL, sqlArray[j])) { // 如果检测到数据更新SQL，检查后续4个字符内，是否包含临时表前缀
//				for (int i = 0; i < 4; i++) {
//					if (i + j >= sqlArray.length) {
//						break;
//					}
//					if (StringUtils.startsWithIgnoreCase(sqlArray[i + j], DasConstants.PRE_TMP_TABLE)) {
//						isSucc = true;
//						break;
//					}
//				}
//			}
//		}
//		return isSucc;
//
//	}

	/**
	 * 将所有空白字符转换为空格
	 * 
	 * @param inSql
	 * @return
	 */
	public static String chgWhitespace(String inSql) {
		if (StringUtils.isEmpty((inSql))) {
			return inSql;
		}

		int sz = inSql.length();
		char[] chs = inSql.toCharArray();
		for (int i = 0; i < sz; i++) {
			if (Character.isWhitespace(inSql.charAt(i))) {
				chs[i] = ' ';
			}
		}
		return new String(chs);
	}

	/**
	 * 在指定的sql语句中插入条件语句，不支持复杂sql语句,请使用小写转换 自动补齐where 或 and 条件
	 * 
	 * @param sql
	 * @param whereSql
	 * @return
	 */
	public static String insertWhereSql(String sql, String whereSql) {
		int index = 0;
		int n = 0;

		if (StringUtils.isEmpty(sql) || StringUtils.isEmpty(whereSql) || StringUtils.startsWithIgnoreCase(whereSql.trim(), "order ")
				|| StringUtils.startsWithIgnoreCase(whereSql.trim(), "group ")) {
			return sql + " " + whereSql;
		}

		StringBuffer result = new StringBuffer(sql);
		StringBuffer tmpSql = new StringBuffer(whereSql);

		int tmpWhereIndex = tmpSql.indexOf("where ");
		int tmpAndIndex = tmpSql.indexOf("and ");

		// 清除where第一个where 或 and
		if (tmpWhereIndex > -1 && tmpWhereIndex < 6) {
			tmpSql.delete(tmpWhereIndex, 6);
		} else if (tmpAndIndex > -1 && tmpAndIndex < 4) {
			tmpSql.delete(tmpAndIndex, 4);
		}
		// 判断是否复杂SQL
		if (isCompSql(result.toString())) {
			index = result.lastIndexOf(" order ");
			tmpSql.insert(0, " where ");
			result.insert(0, " select * from ( ");
			result.append(" ) tmptab ").append(tmpSql);
		} else { // 简单sql
			index = result.lastIndexOf(" where ");

			if (index == -1) {
				tmpSql.insert(0, " where ");
				index = result.lastIndexOf(" group ");
				if (index == -1) {
					index = result.lastIndexOf(" order ");
				}
			} else {
				tmpSql.append(" and ");
				n = 7;
			}

			index = index > result.lastIndexOf(" from ") ? index : -1;
			if (index == -1) {
				result.append(tmpSql);
			} else
				result.insert(index + n, tmpSql);

		}

		return result.toString();
	}

	/**
	 * 清理SQL语句，将输入SQL进行规则化整理.
	 * 
	 * <pre>
	 * 1.清理SQL中的'--'注释
	 * 2.替换SQL中制表符、换行符等
	 * 3.除&quot;'&quot;及动态参数标志内容外，均转换为小写，''不替换
	 * </pre>
	 * 
	 * <h5>Example</h5>
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @param strSql
	 * @return
	 */
	public static String clearSql(String strSql) {
		if (StringUtils.isEmpty(strSql)) {
			return "";
		}

		boolean inQuotesFlag = false;
		boolean commFlag = false;
		String tmpSql = strSql + "  ";

		int len = tmpSql.length();
		char currChar;
		char nextChar = ' ';
		char preChar = ' ';
		boolean isFirstBlank = true;
		// boolean inDyncFlag = true;
		// boolean inStaticFlag = true;

		StringBuffer buf = new StringBuffer(tmpSql.length());
		for (int i = 0; i < len - 1; i++) {
			currChar = tmpSql.charAt(i);
			nextChar = tmpSql.charAt(i + 1);
			if (i > 1) {
				preChar = tmpSql.charAt(i - 1);
			}

			if (isFirstBlank) {// 去除前导空格
				if (currChar == ' ') {
					continue;
				} else {
					isFirstBlank = false;
					buf.append(' ');
				}
			}

			if (currChar == '-' && nextChar == '-') {// 是注释,不作处理
				commFlag = true;
				buf.append(' ');
				i++;
				continue;
			}

			if (commFlag) {// 是注释
				if (currChar == '\n') {// 行尾
					commFlag = false;// 注释结束
					buf.append(' ');// 加一空格
				} else {
					continue;
				}
			}

			if (currChar == '\t' || currChar == '\r' || currChar == '\f' || currChar == '\n') {// 替换制表符号即换行符
				buf.append(' ');
				continue;
			}

			if (currChar == '\'') {// 将所有不在''内的大写字母替换为小写字母
				if (nextChar == '\'') {// 是单引号，不作处理
					buf.append(currChar).append(nextChar);
					i++;
					continue;
				}
				inQuotesFlag = inQuotesFlag ? false : true;
			}
			if (inQuotesFlag) {
				buf.append(currChar);
				continue;
			}

			// 替换动态标志符号
			if (currChar == DasConstants.DYNC_SING && !inQuotesFlag) {
				if (isParmEnd(preChar)) {
					buf.append(' ').append(currChar);
				} else if (isParmEnd(nextChar)) {
					buf.append(currChar).append(' ');
				} else {
					buf.append(currChar);
				}
				// inDyncFlag = inDyncFlag ? false : true;
				continue;
			}

			// 替换静态标志符号
			// if (currChar == STATIC_SING && !inQuotesFlag) {
			// if (inStaticFlag) {
			// buf.append(' ').append(currChar);
			// } else {
			// buf.append(currChar).append(' ');
			// }
			// inStaticFlag = inStaticFlag ? false : true;
			// continue;
			// }

			if (currChar >= 'A' && currChar <= 'Z') {
				buf.append((char) (currChar + 32));
			} else {
				buf.append(currChar);
			}
		}
		if (nextChar == ')') {
			buf.append(' ').append(nextChar);
		} else {
			buf.append(nextChar);
		}

		return buf.toString();
	}

	/**
	 * 清除表别名，主要用于清除order by 语句中的表别名
	 * 
	 * @param strSql
	 * @return
	 */
	public static String removeTabAlaisPre(String strSql) {
		StringBuffer sb = new StringBuffer(strSql.length());

		String[] cols = StringUtils.split(strSql.replaceAll(" order[ ]+?by ", ""), ",");
		int len;
		for (int i = 0; i < cols.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			if ((len = StringUtils.indexOf(cols[i], " as ")) > 0) {
				sb.append(StringUtils.substring(cols[i], len + 4));
			} else {
				if ((len = StringUtils.indexOf(cols[i], ".")) > 0) {
					sb.append(StringUtils.substring(cols[i], len + 1));
				} else {
					sb.append(cols[i]);
				}
			}
		}

		return StringUtils.removeEnd(sb.toString(), ",");
	}

	/**
	 * 增加排序条件
	 * 
	 * @param inSql
	 * @param sort_cond
	 * @return
	 */
	public static String addOrderBy(String inSql, String[] sort_cond) {

		if (sort_cond == null || sort_cond.length < 1 || StringUtils.isEmpty(sort_cond[0])) {
			return inSql;
		}

		String tmpSql = clrOrderBy(inSql);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < sort_cond.length; i++) {
			if (i == 0) {
				sb.append(" order by ").append(sort_cond[i]);
			} else {
				sb.append(",").append(sort_cond[i]);
			}
		}

		return tmpSql + sb.toString();
	}

	/**
	 * 将输入SQL解析成计算总记录条数的SQL,并清理末尾的order by. <h5>Example</h5>
	 * 
	 * <pre>
	 *          strSql = &quot;select col1,col2 from tab1&quot;
	 *          return ： &quot;select count(1) from tab1&quot;
	 * </pre>
	 * 
	 * @param strSql
	 * @return
	 */
	public static String getCountSql(String inSql) {
		String strSql = clrOrderBy(clearSql(inSql));// 清理SQL注释及转换为小写,清理掉末尾的order
		// by子句

		if (isCompSql(inSql)) {
			return "select count(*) from (" + strSql + ") t_" + RandomStringUtils.randomAlphanumeric(10);
		} else {
			return "select count(*) " + strSql.substring(strSql.indexOf(" from "));
		}
	}

	/**
	 * 处理所有between 语句,将所有的betwen 替换为 >= and <=
	 * 
	 * @param inStr
	 * @return
	 */
	public static String replaceBetweenStr(String inStr) {
		StringBuffer sb = new StringBuffer(inStr.length());
		String[] tmpStr = StringUtils.split(inStr);
		int conFlag = 0;
		boolean isInBetween = false;
		int betweenFlag = 0;
		StringBuffer tmpSb = null;
		boolean isNotBetween = false;

		for (int i = 0; i < tmpStr.length; i++) {
			if (tmpStr[i].equals("where") || tmpStr[i].equals("or")) {
				conFlag = i;
				continue;
			}
			if (tmpStr[i].equals("and")) {
				if (isInBetween) { // 在between中s
					tmpSb = new StringBuffer();
					for (int j = conFlag + 1; j < betweenFlag; j++) {
						tmpSb.append(tmpStr[j]);
					}
					if (isNotBetween) {
						tmpStr[i] = "and " + tmpSb.toString() + ">";
					} else {
						tmpStr[i] = "and " + tmpSb.toString() + "<=";
					}
					isInBetween = false;
					isNotBetween = false;
				} else {
					conFlag = i;
				}
			} else if (tmpStr[i].equals("between")) {
				if (tmpStr[i - 1].equals("not")) {// not between
					tmpStr[i - 1] = "";
					isNotBetween = true;
				}
				if (isNotBetween) {
					tmpStr[i] = "<";
				} else {
					tmpStr[i] = ">=";
				}

				betweenFlag = i;
				isInBetween = true;
			}

		}

		for (int i = 0; i < tmpStr.length; i++) {
			if (!(tmpStr[i].equals(""))) {
				sb.append(" ").append(tmpStr[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 清理字符串中的空值，将空值替换为 1>0或者1<0 可用正则表达式进行优化
	 * 
	 * @param inStr
	 * @return
	 */
	public static String replaceNullStr(String inStr) {
		StringBuffer sb = new StringBuffer(inStr.length());
		String[] tmpStr = StringUtils.split(inStr);
		int conFlag = 0;
		String repCond = "";

		for (int i = 0; i < tmpStr.length; i++) {
			if (tmpStr[i].equals("where") || tmpStr[i].equals("and") || tmpStr[i].equals("or") || tmpStr[i].equals("having") || tmpStr[i].equals("(")) {
				conFlag = i;
				if (tmpStr[i].equals("or")) {
					repCond = "1<0";
				} else {
					repCond = "1>0";
				}
			}

			if (tmpStr[i].equals(DasConstants.STR_NULL)) {
				if (conFlag > 0) { // 出现在where 条件后 替换为 1>0 or 替换为1<0
					for (int j = conFlag + 1; j < i; j++) {
						tmpStr[j] = "";
					}
					tmpStr[i] = repCond;
				} else { // where 条件前，替换为空
					tmpStr[i] = "null";
				}
			}
		}

		for (int i = 0; i < tmpStr.length; i++) {
			if (!(tmpStr[i].equals(""))) {
				sb.append(" ").append(tmpStr[i]);
			}
		}
		return sb.toString();
	}

//	/**
//	 * 解析查询SQL中得动态参数
//	 * 
//	 * @param appSql
//	 * @param bean
//	 * @param isRemoveNulCond
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static OrigSql replaceDyncParm(String appSql, Object bean, boolean isRemoveNulCond) {
//		OrigSql origSql = new OrigSql();
//		ArrayList<Column> colList = Lists.newArrayList();
//		StringBuffer sb = new StringBuffer(appSql.length());
//		Map<String, Object> mapBean = null;
//		MetaObject metBean = null;
//		boolean isMap = false;
//
//		Pattern p = Pattern.compile(DasConstants.DYNC_PARRERN);
//		Matcher m = p.matcher(appSql);
//		String repStr = "";
//		Object parmObj = null;
//
//		if (bean instanceof Map) {
//			mapBean = (Map<String, Object>) bean;
//			isMap = true;
//		} else {
//			metBean = SystemMetaObject.forObject(bean);
//		}
//
//		while (m.find()) {
//			parmObj = null;
//			Column column = new Column();
//			String findStr = m.group();
//
//			// 去除前后引导符
//			String tmpStr = findStr.substring(1, findStr.length() - 1);
//			String[] keyStr = StringUtils.split(tmpStr.toLowerCase(), DasConstants.SEPARATOR);
//
//			try {
//				if (isMap) {
//					parmObj = mapBean.get(keyStr[0]);
//				} else {
//					parmObj = metBean.getValue(keyStr[0]);
//				}
//			} catch (Exception e) {
//				logger.warn("[" + keyStr[0] + "]属性信息不存在");
//			}
//
//			if (parmObj == null && isRemoveNulCond) {
//				repStr = DasConstants.STR_NULL;
//			} else {
//				repStr = "?";
//				column.setColumnName(keyStr[0]);
//
//				String colType = keyStr.length > 1 ? keyStr[1].toLowerCase() : DasConstants.DEFAULT_PARM_TYPE;
//				if (StringUtils.equals(colType, "char")) {
//					column.setDataType(Types.CHAR);
//				} else if (StringUtils.equals(colType, "date")) {
//					column.setDataType(Types.DATE);
//				} else if (StringUtils.equals(colType, "int")) {
//					column.setDataType(Types.INTEGER);
//				} else if (StringUtils.equals(colType, "number")) {
//					column.setDataType(Types.DECIMAL);
//				} else {
//					column.setDataType(Types.OTHER);
//				}
//
//				colList.add(column);
//			}
//
//			m.appendReplacement(sb, repStr);
//		}
//		m.appendTail(sb);
//
//		origSql.setSql(sb.toString());
//		origSql.setColList(colList);
//
//		return origSql;
//	}

	/**
	 * 判断参数符号是否结束
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isParmEnd(char c) {
		boolean isEndChar = false;
		switch (c) {
		case ')':
		case ',':
		case '+':
		case '-':
		case '*':
		case '/':
		case ';':
		case '=':
			isEndChar = true;
			break;

		default:
			break;
		}
		return isEndChar;
	}
}
