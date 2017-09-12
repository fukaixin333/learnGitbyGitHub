package com.citic.server.service.task;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
import com.citic.server.domain.MC00_task_fact;

/**
 * 清理指标
 * 
 * @author
 * @version 1.0
 */

public class TK_CLE201 extends BaseTask {

	private static final Logger logger = LoggerFactory.getLogger(TK_CLE201.class);
	private JdbcTemplate jdbcTemplate = null;

	public TK_CLE201(ApplicationContext ac, MC00_task_fact mC00_task_fact) {
		super(ac, mC00_task_fact);
		ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());
	}

	/**
	 * 
	 */
	public boolean calTask() throws Exception {

		CallableStatement calstmt = null;
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		try {
			String dbtype = "oracle";
			DatabaseMetaData md = conn.getMetaData();
			String dbpName = md.getDatabaseProductName();
			if (dbpName.toLowerCase().indexOf("db2") > -1) {
				dbtype = "db2";
			}
			// 清理指标事实表
			String packagecal = "call P_CLEAN_MP01_TABLE(?) ";
			if (null != dbtype && dbtype.toLowerCase().equals("db2")) {
				packagecal = "call P_CLEAN_MP01_TABLE(?) ";
			}
			calstmt = conn.prepareCall(packagecal);
			calstmt.setString(1, this.getMC00_task_fact().getDatatime());// 数据时间
			calstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// 关闭
			if (calstmt != null) {
				try {
					calstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
}