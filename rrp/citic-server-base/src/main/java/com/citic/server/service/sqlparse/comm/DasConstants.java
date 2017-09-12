/**
 * 数据库常量定义类
 */
package com.citic.server.service.sqlparse.comm;

import java.sql.Types;

/**
 * @author gaosen
 *
 */
public final class DasConstants {
	/** 复杂SQL关键字 */
	public static final String[] COMPSQL_KEYS = { " union ", " distinct ", " group ", " unique ", " first ", " top ", " intersect ", " minus ",
			" except " };
	/** 表更新操作关键字L */
	public static final String[] UPDATE_SQL = new String[] { "insert", "update", "delete", "drop", "truncate", "create", "alert" };

	/** 临时表前缀 */
//	public static final String PRE_TMP_TABLE = "tmp";

	/** 每页最大记录数 */
	public static final int MAX_PAGE_SIZE = 10000;

	/** 单条sql默认执行时间 10分钟 */
	public static final int DEFAULT_MAX_EXEC_TIME = 600;

	/** sql默认最大结果集 */
	public static final int DEFAULT_MAX_FETCH_SIZE = 20000;

	/** SQL执行类型 */
	public static final int QUERY = 1;
	public static final int UPDATE = 2;
	public static final int OTHER = 0;

	/** 静态替换标志符 */
	public static final char STATIC_SING = '@';

	/** 动态替换标志符 */
	public static final char DYNC_SING = '#';

	/** 静态替换模式 */
	public static final String STATIC_PARRERN = "@[a-zA-Z0-9_:]+@";

	/** 动态替换模式 */
	public static final String DYNC_PARRERN = "#[a-zA-Z0-9_:]+#";

	/** 参数及数据类型用分割符 */
	public static final char SEPARATOR = ':';

	/** 多值字段分割苻号 */
	public static final char MUL_VAL_SEPARATOR = ',';

	public static final String DEFAULT_PARM_TYPE = "char";

	/** 替换后的空值 */
	public static final String STR_NULL = "^^^^^";

	/** 数据库序号名称 */
	public static final String DB_ROWNO_NAME = "rowno";

	/** 批量更新时，最多一次提交的记录数 */
	public static final int MAX_BATCH_UPDATE_REC = 2000;

	/** 记录数汇总列 */
	public static final String SUM_COUNT = "sum_count";

	/** 输入数据类型 文本 */
	public static final String DATA_TYPE_TEXT = "1";
	/** 数据库字符文本类型 */
	public static final int[] DB_TYPE_TEXT = new int[] { Types.CHAR, Types.VARCHAR };

	/** 输入数据类型 数值 */
	public static final String DATA_TYPE_NUMBER = "2";
	/** 数据库数值类型 */
	public static final int[] DB_TYPE_NUMBER = new int[] { Types.BIGINT, Types.INTEGER, Types.DECIMAL, Types.NUMERIC, Types.DOUBLE, Types.FLOAT };

	/** 输入数据类型 日期 */
	public static final String DATA_TYPE_DATE = "3";
	/** 数据库日期类型 */
	public static final int[] DB_TYPE_DATE = new int[] { Types.DATE, Types.TIMESTAMP, Types.TIME };
}
