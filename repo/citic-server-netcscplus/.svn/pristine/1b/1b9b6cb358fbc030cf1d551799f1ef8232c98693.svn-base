package com.citic.server.service.task;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.net.mapper.MM11_indicMapper;
import com.citic.server.net.mapper.MM11_indic_logMapper;
import com.citic.server.net.mapper.MM11_indic_sqlMapper;
import com.citic.server.service.domain.MM11_indic;
import com.citic.server.service.domain.MM11_indic_log;
import com.citic.server.service.domain.MM11_indic_sql;
import com.citic.server.utils.claster.ClusterUtils;
import com.citic.server.utils.claster.Point3D;

/**
 * 指标计算：
 * 
 * @author hubaiqing
 * @version 1.0
 */

public class TK_AML201 extends BaseTask {

	private static final Logger logger = LoggerFactory.getLogger(TK_AML201.class);

	public TK_AML201(ApplicationContext ac, MC00_task_fact mC00_task_fact) {
		super(ac, mC00_task_fact);
	}

	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		String taskid = this.getMC00_task_fact().getTaskid();
		String indickey = this.getMC00_task_fact().getSubtaskid();// =indickey
		String datatime = this.getMC00_task_fact().getDatatime();

		if (indickey == null || indickey.equals("") || indickey.equals("0")) {
			// 本类别下没有需要计算的指标！
			return true;
		}

		MM11_indicMapper mm11_indicMapper = (MM11_indicMapper) this.getAc().getBean("MM11_indicMapper");

		MM11_indic mm11_indic = mm11_indicMapper.getMM11_indicDisp(indickey);

		MM11_indic_sqlMapper mm11_indic_sqlMapper = (MM11_indic_sqlMapper) this.getAc().getBean("MM11_indic_sqlMapper");
		ArrayList list = mm11_indic_sqlMapper.getMM11_indic_sqlList(indickey);

		ArrayList indicseqList = new ArrayList();
		HashMap sqlHash = new HashMap();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			MM11_indic_sql mm11_indic_sql = (MM11_indic_sql) iter.next();
			String indicseq = mm11_indic_sql.getIndicseq();
			String sqlseq = mm11_indic_sql.getSqlseq();
			String sqlstr = mm11_indic_sql.getSqlstr();

			StringBuffer sqlBuf = new StringBuffer();
			if (sqlHash.containsKey(indicseq)) {
				sqlBuf = (StringBuffer) sqlHash.get(indicseq);
			} else {
				indicseqList.add(indicseq);
			}

			sqlBuf.append(sqlstr);

			sqlHash.put(indicseq, sqlBuf);

		}
		if (indicseqList.size() > 0) {// 确实找到待执行的指标逻辑

			if (mm11_indic.getIsjl().equals("1")) {
				// 聚类算法
				String region_sf = "3";
				if (mm11_indic.getRegion_sf() != null && !mm11_indic.getRegion_sf().equals("")) {
					region_sf = mm11_indic.getRegion_sf();
				}
				isSucc = this.calCluster(indicseqList, sqlHash, indickey, datatime, region_sf, mm11_indic.getRegion_num());

			} else {
				// SQL算法
				isSucc = this.calSql(indicseqList, sqlHash, indickey, datatime);

			}

		}

		return isSucc;
	}

	/**
	 * 普通指标SQL算法
	 * 
	 * @param indickey
	 * @param datatime
	 * @return
	 * @throws Exception
	 */
	public boolean calSql(ArrayList indicseqList, HashMap sqlHash, String indickey, String datatime) throws Exception {
		boolean isSucc = true;

		ArrayList sqlList = new ArrayList();

		Iterator sqlIter = indicseqList.iterator();
		while (sqlIter.hasNext()) {
			String indicseq = (String) sqlIter.next();

			StringBuffer sqlStr = (StringBuffer) sqlHash.get(indicseq);

			String finallsqlStr = this.getCalSqlService().getExecSql(indickey, datatime, sqlStr.toString(), null);
			//实验室任务将日表替换为实验室是表BH11_TRANS_D_L
			if (this.getMC00_task_fact().getTasksource().equalsIgnoreCase("lab")) {
				finallsqlStr=finallsqlStr.replaceAll("(?i)BH11_TRANS_D", "BH11_TRANS_D_L");
			}
			logger.debug(finallsqlStr);

			sqlList.add(finallsqlStr.trim());

		}

		this.syncToDatabase(sqlList);

		/**
		 * ApplicationCFG applicationCFG = (ApplicationCFG)
		 * this.getAc().getBean( "applicationCFG");
		 * 
		 * JdbcTemplate jdbcTemplate = (JdbcTemplate) this.getAc().getBean(
		 * applicationCFG.getJdbctemplate_business());
		 * 
		 * int[] result = jdbcTemplate.batchUpdate(sqlStrs);
		 * 
		 * // 添加指标计算日志== // 需要计算 if
		 * (applicationCFG.getSave_cal_sqllog().equals("1")) {
		 * 
		 * this.writeSqllog(indickey, datatime, sqlStrs);
		 * 
		 * }
		 */

		return isSucc;
	}

	/**
	 * 聚类指标算法
	 * 
	 * @param indickey
	 * @param datatime
	 * @param KMeansType
	 * @param K
	 * @return
	 * @throws Exception
	 */
	public boolean calCluster(ArrayList indicseqList, HashMap sqlHash, String indickey, String datatime, String _KMeansType, String _K) throws Exception {
		boolean isSucc = true;
		/**
		 * 聚类算法：sqlHash只能有一条select语句，约定： 查询语句返回值：VAL1,VAL2,VAL3~~~~ 当前只有一维数据
		 */

		ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());

		Iterator sqlIter = indicseqList.iterator();
		while (sqlIter.hasNext()) {
			String indicseq = (String) sqlIter.next();

			StringBuffer sqlStr = (StringBuffer) sqlHash.get(indicseq);

			String finallsqlStr = this.getCalSqlService().getExecSql(indickey, datatime, sqlStr.toString(), null);

			if (finallsqlStr.trim().toLowerCase().startsWith("select")) {
				List vList = jdbcTemplate.queryForList(finallsqlStr);

				if (vList.size() > 0) {
					// this.calSql(indicseqList, sqlHash, indickey, datatime);
					this.calCluster(vList, _KMeansType, _K, indickey, datatime);
				} else {

					logger.error("聚类数据源为空，无法执行聚类算法！");
				}

			} else {
				jdbcTemplate.execute(finallsqlStr);
			}

		}

		return isSucc;
	}

	private boolean calCluster(List vList, String _KMeansType, String _K, String indickey, String datatime) throws Exception {
		boolean isSucc = false;
		int KMeansType = new Integer(_KMeansType);
		int K = new Integer(_K);

		Vector<Point3D> VP = new Vector();
		Iterator iter = vList.iterator();
		while (iter.hasNext()) {
			Map map = (Map) iter.next();

			double x = new Double(map.get(map.keySet().iterator().next()).toString());

			double y = 0;
			if (map.size() >= 2)
				y = new Double(map.get(map.keySet().iterator().next()).toString());

			double z = 0;
			if (map.size() >= 3)
				z = new Double(map.get(map.keySet().iterator().next()).toString());

			Point3D p3 = new Point3D(x, y, z);

			VP.add(p3);
		}
		ClusterUtils cUtils = new ClusterUtils(KMeansType, VP, K);

		ArrayList resultList = cUtils.startClusterCal();
		ArrayList sqlList = new ArrayList();
		Iterator rIter = resultList.iterator();
		int i = 1;
		while (rIter.hasNext()) {
			LinkedHashMap map = (LinkedHashMap) rIter.next();

			double xMax = this.doubleFormat(map.get("xMax"));
			double xMin = this.doubleFormat(map.get("xMin"));
			double xMpoint = this.doubleFormat(map.get("xMpoint"));

			if (xMax == 0.0 || xMin == 0.0 || xMpoint == 0.0) {
				continue;
			}

			String sql = "INSERT INTO BR11_HN_A_M_CLUSTER(INDICKEY,STATISDT,REGION_ID,REGION_UP,REGION_LOW,REGION_MID,REGION_CNT) " + " VALUES('" + indickey
					+ "','" + datatime + "'," + i + "," + xMax + "," + xMin + "," + xMpoint + "," + new Integer(map.get("xNum").toString()) + ")";

			sqlList.add(sql);

			i++;
		}

		isSucc = this.syncToDatabase(sqlList);
		return isSucc;
	}

	/**
	 * 将指定对象转换为double,保留两位小数，空值及非法数值转换为0.0
	 * 
	 * @param sVal
	 * @return
	 */
	private double doubleFormat(Object sVal) {
		double dVal = 0.0;

		if (sVal != null && NumberUtils.isNumber(sVal.toString())) {
			BigDecimal b = new BigDecimal(sVal.toString());
			dVal = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		return dVal;
	}

	// private double doubleFormat( String sVal ){
	//
	// double dVal = new Double(sVal);
	// if(dVal== Double.NaN){
	// dVal = 0.00;
	// }
	// if(dVal<0.000001){
	// dVal = 0.00;
	// }
	//
	// BigDecimal b = new BigDecimal( dVal );
	//
	// dVal = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	//
	// return dVal;
	// }

	private void writeSqllog(String indickey, String datatime, String[] sqlStrs) {
		try {
			ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
			ArrayList finallSqlList = new ArrayList();
			// 日志删除
			MM11_indic_logMapper mm11_indic_logMapper = (MM11_indic_logMapper) this.getAc().getBean("MM11_indic_logMapper");
			mm11_indic_logMapper.deleteMM11_indic_log(indickey);

			int splitlength = applicationProperties.getSaveCalBigSqlSplit();

			for (int i = 0; i < sqlStrs.length; i++) {
				String sqlstr = sqlStrs[i];

				ArrayList sqlList = new ArrayList();
				sqlList = this.sqlSplit(sqlList, sqlstr, splitlength);

				Iterator sqlIter = sqlList.iterator();
				int j = 1;
				while (sqlIter.hasNext()) {
					String sqlcontent = (String) sqlIter.next();

					MM11_indic_log log = new MM11_indic_log();
					log.setIndickey(indickey);
					log.setDatatime(datatime);
					log.setIndicseq("" + (i + 1));
					log.setSqlseq("" + j);
					log.setSqlstr(sqlcontent);

					finallSqlList.add(log);
					j++;
				}
			}

			this.syncToDatabase(datatime, finallSqlList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void syncToDatabase(String datatime, final ArrayList finallSqlList) throws Exception {

		String sql = "insert into mm11_indic_log(indickey,datatime,indicseq,sqlseq,sqlstr) values (?,?,?,?,?)";

		ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_manager());

		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				MM11_indic_log log = (MM11_indic_log) finallSqlList.get(i);
				ps.setString(1, log.getIndickey());
				ps.setString(2, log.getDatatime());
				ps.setString(3, log.getIndicseq());
				ps.setString(4, log.getSqlseq());
				ps.setString(5, log.getSqlstr());
			}

			public int getBatchSize() {
				return finallSqlList.size();
			}
		};

		int[] resut = jdbcTemplate.batchUpdate(sql, setter);
	}

	private ArrayList sqlSplit(ArrayList list, String sqlstr, int splitlength) {

		if (sqlstr.length() <= splitlength) {
			list.add(sqlstr);
		} else {

			list.add(sqlstr.toString().substring(0, splitlength));

			sqlstr = sqlstr.toString().substring(splitlength);

			list = this.sqlSplit(list, sqlstr, splitlength);

		}

		return list;
	}

}