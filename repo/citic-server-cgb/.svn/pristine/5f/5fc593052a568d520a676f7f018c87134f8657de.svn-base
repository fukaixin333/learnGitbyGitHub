package com.citic.server.service.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.junit.BaseJunit4Test;

public class BaseTaskTest extends BaseJunit4Test{
 
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void testSyncToDatabaseArrayList() throws Exception {
		System.out.println("开始执行测试模块");
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql ="delete from  br21_alert_party where exists(select 'x' from br21_alert_fact b where b.app_id=br21_alert_party.app_id and b.status_cd='1')  or data_dt='2017-03-02'";
			System.out.println(sql);
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
			conn.commit();
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		System.out.println("结束执行测试模块");
	}
	

}
