package com.citic.server.service.task.taskBo;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citic.server.utils.DbFuncUtils;

public class AML503Bo {

	private static final Logger logger = LoggerFactory.getLogger(AML503Bo.class);

	private DbFuncUtils dbfunc;

	public ArrayList clear(ArrayList sqlList) {

		String sql_1 = "DELETE FROM BH13_EVENT_SCORE";
		String sql_2 = "DELETE FROM BH13_LINK_SCORE";
		String sql_3 = "DELETE FROM BH13_EVENT_ALERT";
		String sql_4 = "DELETE FROM BH13_LINK_ALERT";
		String sql_5 = "DELETE FROM BH13_MODEL_ALERT";
		sqlList.add(sql_1);
		sqlList.add(sql_2);
		sqlList.add(sql_3);
		sqlList.add(sql_4);
		sqlList.add(sql_5);

		return sqlList;
	}

	public ArrayList init(ArrayList sqlList) throws Exception {

		/**
		 * 场景分值计算时，需要售前构建一个数据库函数，将行进行合并为计算表达式，然后得到最终的计算结果 实现公式的解析计算
		 */
		String sql_1 = "" + "create or replace function getBH13_LINK_ALERT(businesskeystr String,modulekeystr String ,linkkeystr String) RETURN NUMBER is"
				+ "  ResultStr VARCHAR(256);" + "  Score NUMBER;" + "begin" + " FOR cur IN (SELECT "
				+ " CASE WHEN (SUBITEMKEY='&') THEN '*' WHEN (SUBITEMKEY='|') THEN '+' ELSE SUBITEMKEY END AS SUBITEMKEY" + " FROM BH13_EVENT_ALERT T2 "
				+ "    WHERE businesskeystr = T2.BUSINESSKEY AND modulekeystr=T2.MODELKEY AND linkkeystr=T2.LINKKEY ORDER BY SUBITEMSEQ) LOOP"
				+ "    ResultStr := ResultStr || cur.SUBITEMKEY;" + "  END LOOP;"
				+ "  EXECUTE IMMEDIATE 'SELECT (CASE WHEN ( ' || ResultStr || ')>=1 THEN 1 ELSE 0 END) FROM DUAL' INTO Score;" + "  RETURN Score;"
				+ "end getBH13_LINK_ALERT;";

		String sql_2 = "" + "create or replace function getBH13_MODEL_ALERT(businesskeystr String,"
				+ "                                               modulekeystr   String)"
				+ "  RETURN NUMBER is                                                   "
				+ "  ResultStr VARCHAR(256);                                            "
				+ "  Score     NUMBER;                                                  "
				+ "begin                                                                "
				+ "  FOR cur IN (SELECT CASE                                            "
				+ "                       WHEN (T1.SUBITEMKEY = '&') THEN               "
				+ "                        '*'                                          "
				+ "                       WHEN (T1.SUBITEMKEY = '|') THEN               "
				+ "                        '+'                                          "
				+ "                       ELSE                                          "
				+ "                        ''||T2.SCORE                                 "
				+ "                     END AS SCORESTR                                 "
				+ "                FROM MM14_MODEL_FORMULA T1                           " + "                JOIN BH13_LINK_ALERT T2                         "
				+ "                ON T1.MODELKEY = T2.MODELKEY                         "
				+ "                 AND businesskeystr = T2.BUSINESSKEY                 "
				+ "                 AND modulekeystr = T2.MODELKEY                      "
				+ "               ORDER BY T1.SUBITEMSEQ) LOOP                          "
				+ "    ResultStr := ResultStr || cur.SCORESTR;                          "
				+ "  END LOOP;                                                          "
				+ "  EXECUTE IMMEDIATE 'SELECT (CASE WHEN ( ' || ResultStr ||           "
				+ "                    ')>=1 THEN 1 ELSE 0 END) FROM DUAL'              "
				+ "    INTO Score;                                                      "
				+ "  RETURN Score;                                                      "
				+ "end getBH13_MODEL_ALERT;                                             ";

		/**
		 * 系统初始化时，根据数据库类型，按照初始化脚本创建。
		 */
		// sqlList.add(sql_1);
		// sqlList.add(sql_2);

		return sqlList;
	}

	/**
	 * 
	 * @param subtaskid
	 *            net - 基于网络 event - 基于单主体预警
	 * @return
	 * @throws Exception
	 */
	public ArrayList cal01_getEventScore_net(ArrayList sqlList) throws Exception {
		/**
		 * 来自网络事件
		 */
		String sql1 = "INSERT INTO BH13_EVENT_SCORE(BUSINESSKEY,MODELKEY,LINKKEY,EVENT_KEY,SCORE) "
				+ " SELECT T1.NET_ID,T1.MODELKEY,T2.LINKKEY,T1.EVENT_KEY,T2.EVENT_SCORE" + " FROM BR13_NET_MAIN T,BR13_NET_EVENT T1,MM14_LINK_EVENT T2 "
				+ " WHERE T.NET_ID=T1.NET_ID AND T1.MODELKEY=T2.MODELKEY AND T1.EVENT_KEY=T2.EVENTKEY AND T.FLAG='0'";
		/**
		 * 来自合并后网络特征 与SQL1可能有重复记录
		 */
		String sql2 = "INSERT INTO BH13_EVENT_SCORE(BUSINESSKEY,MODELKEY,LINKKEY,EVENT_KEY,SCORE) "
				+ " SELECT T1.NET_ID,T1.MODELKEY,T2.LINKKEY,T1.EVENT_KEY,T2.EVENT_SCORE" + " FROM BR13_NET_MAIN T,BR13_NET_EVENT_TZ T1,MM14_LINK_EVENT T2 "
				+ " WHERE T.NET_ID=T1.NET_ID AND T.FLAG='0' AND  T1.MODELKEY=T2.MODELKEY AND T1.EVENT_KEY=T2.EVENTKEY";

		sqlList.add(sql1);
		sqlList.add(sql2);

		logger.debug(sql1);
		logger.debug(sql2);

		return sqlList;
	}

	public ArrayList cal01_getEventScore_event(ArrayList sqlList) throws Exception {

		/**
		 * 来自单主体事件
		 */
		String sql3 = "INSERT INTO BH13_EVENT_SCORE(BUSINESSKEY,MODELKEY,LINKKEY,EVENT_KEY,SCORE) "
				+ " SELECT DISTINCT T1.APP_ID,T1.MODELKEY,T2.LINKKEY,T1.EVENTKEY,T2.EVENT_SCORE"
				+ " FROM BR21_CASE_FACT T,BR21_CASE_ALERT T1,MM14_LINK_EVENT T2 "
				+ " WHERE T.APP_ID=T1.APP_ID AND T.FLAG='0' AND T1.MODELKEY=T2.MODELKEY AND T1.EVENTKEY=T2.EVENTKEY  ";

		sqlList.add(sql3);

		logger.debug(sql3);
		return sqlList;
	}

	/**
	 * 积分法：计算环节得分情况
	 */
	public ArrayList cal02_getLinkScore(ArrayList sqlList) throws Exception {

		String sql1 = "INSERT INTO BH13_LINK_SCORE(BUSINESSKEY,MODELKEY,LINKKEY,SCORE,SCORE_FACT)"
				+ " SELECT DISTINCT T1.BUSINESSKEY,T1.MODELKEY,T1.LINKKEY,T2.SCORE,SUM(T1.SCORE)" + " FROM BH13_EVENT_SCORE T1 JOIN MM14_LINK T2"
				+ " ON (T1.MODELKEY=T2.MODELKEY AND T1.LINKKEY=T2.LINKKEY AND T2.LINK_LOGIC='1')" + " GROUP BY T1.BUSINESSKEY,T1.MODELKEY,T1.LINKKEY,T2.SCORE";

		sqlList.add(sql1);
		logger.debug(sql1);
		return sqlList;
	}

	/**
	 * 公式法：计算事件公式子项明细值
	 */
	public ArrayList cal02_getEventAlert(ArrayList sqlList) throws Exception {
		String sql1 = "INSERT INTO BH13_EVENT_ALERT(BUSINESSKEY,MODELKEY,LINKKEY, SUBITEMSEQ, SUBITEMTYPE,SUBITEMKEY)"
				+ " SELECT DISTINCT A.BUSINESSKEY, A.MODELKEY,  A.LINKKEY, A.SUBITEMSEQ, A.SUBITEMTYPE,"
				+ " CASE WHEN(A.SUBITEMTYPE IN('1','2','4')) THEN A.SUBITEMKEY WHEN(A.SUBITEMTYPE='3' AND B.BUSINESSKEY IS NOT NULL) THEN '1' ELSE '0' END"
				+ " FROM ( "
				+ "  SELECT DISTINCT T2.BUSINESSKEY,T2.MODELKEY,T2.LINKKEY ,T1.SUBITEMSEQ,T1.SUBITEMTYPE,T1.SUBITEMKEY FROM BH13_EVENT_SCORE  T2 ,MM14_LINK_FORMULA T1"
				+ "   WHERE T1.MODELKEY=T2.MODELKEY AND T1.LINKKEY=T2.LINKKEY " + " 	) A LEFT OUTER JOIN BH13_EVENT_SCORE B "
				+ " ON  A.MODELKEY=B.MODELKEY AND A.LINKKEY=B.LINKKEY AND A.BUSINESSKEY=B.BUSINESSKEY AND A.SUBITEMKEY=B.EVENT_KEY";
		sqlList.add(sql1);
		logger.debug(sql1);
		return sqlList;
	}

	public ArrayList cal03_getLinkAlert(ArrayList sqlList) throws Exception {
		/** 积分环节预警情况判定 */
		String sql1 = "INSERT INTO BH13_LINK_ALERT(BUSINESSKEY,MODELKEY,LINKKEY,SCORE)" + " SELECT BUSINESSKEY,MODELKEY,LINKKEY,"
				+ " CASE WHEN (SCORE_FACT>=SCORE) THEN '1' ELSE '0' END" + " FROM BH13_LINK_SCORE" + "";

		/** 场景环节预警情况判定 */
		String sql2 = "INSERT INTO BH13_LINK_ALERT(BUSINESSKEY,MODELKEY,LINKKEY,SCORE)"
				+ " SELECT DISTINCT BUSINESSKEY, MODELKEY, LINKKEY, getBH13_LINK_ALERT(BUSINESSKEY, MODELKEY, LINKKEY) SCORE" + " FROM BH13_EVENT_ALERT"
				+ " GROUP BY BUSINESSKEY, MODELKEY, LINKKEY" + "";

		sqlList.add(sql1);
		sqlList.add(sql2);
		logger.debug(sql1);
		logger.debug(sql2);
		return sqlList;
	}

	public ArrayList cal04_getModelAlert(ArrayList sqlList) throws Exception {

		String sql1 = "INSERT INTO BH13_MODEL_ALERT(BUSINESSKEY,MODELKEY)" + " SELECT DISTINCT BUSINESSKEY, MODELKEY" + " FROM BH13_LINK_ALERT"
				+ " WHERE SCORE=1 AND getBH13_MODEL_ALERT(BUSINESSKEY,MODELKEY) = 1";

		// String sql1 = "INSERT INTO BH13_MODEL_ALERT(BUSINESSKEY,MODELKEY)"
		// +
		// " SELECT T2.BUSINESSKEY,T2.MODELKEY FROM MM14_MODEL_FORMULA T1 LEFT JOIN BH13_LINK_ALERT T2"
		// +
		// " ON T1.MODELKEY=T2.MODELKEY AND getBH13_MODEL_ALERT(T2.BUSINESSKEY,T2.MODELKEY,T1.SUBITEMSEQ,T1.SUBITEMKEY)=1"
		// + "";
		//
		sqlList.add(sql1);
		logger.debug(sql1);
        //保存匹配成功的环节信息
		sql1="INSERT INTO BH13_LINK_ALERT_HIS(BUSINESSKEY,MODELKEY,LINKKEY,SCORE)"
				+ "SELECT T.BUSINESSKEY,T.MODELKEY,LINKKEY,SCORE FROM BH13_LINK_ALERT T ,BH13_MODEL_ALERT T1 "
				+ "WHERE T.Businesskey=T1.Businesskey AND T.Score='1' ";
		sqlList.add(sql1);
		logger.debug(sql1);
		
		sql1="INSERT INTO BH13_LINK_SCORE_HIS(BUSINESSKEY,MODELKEY,LINKKEY,SCORE,SCORE_FACT)"
				+ "SELECT T.BUSINESSKEY,T.MODELKEY,LINKKEY,SCORE,SCORE_FACT FROM BH13_LINK_SCORE T ,BH13_MODEL_ALERT T1 "
				+ "WHERE T.Businesskey=T1.Businesskey  ";
		sqlList.add(sql1);
		logger.debug(sql1);
		
		// 修改网络主表的网络处理状态
//		sql1 = "UPDATE BR13_NET_MAIN SET FLAG= '1'   WHERE  NET_ID IN (SELECT BUSINESSKEY FROM BH13_MODEL_ALERT )    ";
//		sqlList.add(sql1);
//		logger.debug(sql1);

		// 修改单主体合并预警的处理状态
		sql1 = "UPDATE BR21_CASE_FACT SET FLAG= '1' WHERE  APP_ID IN (SELECT BUSINESSKEY FROM BH13_MODEL_ALERT )  ";
		sqlList.add(sql1);
		logger.debug(sql1);
		// 匹配失败的直接修改为2
//		sql1 = "UPDATE BR13_NET_MAIN SET FLAG= '2' WHERE  FLAG='0' ";
//		sqlList.add(sql1);
//		logger.debug(sql1);
		// 匹配失败的直接修改为2
		sql1 = "UPDATE BR21_CASE_FACT SET FLAG= '2' WHERE  FLAG='0' ";
		sqlList.add(sql1);
		logger.debug(sql1);
		return sqlList;
	}

}
