/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/21 01:18:09$
 */
package com.citic.server.gf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import com.citic.server.SpringContextHolder;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Glxx;
import com.citic.server.gf.domain.request.QueryRequest_Jrxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.request.QueryRequest_Wlxx;
import com.citic.server.gf.domain.request.QueryRequest_Zhxx;
import com.citic.server.runtime.IllegalDataException;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/21 01:18:09$
 */
public final class BatchDBTools {
	
	@SuppressWarnings("unused")
	private static void batchInsertSQL(String dataSourceId, String tableName, String[] columns, String[][] values, int count) throws SQLException {
		DataSource dataSource = (DataSource) SpringContextHolder.getBean(dataSourceId);
		if (dataSource == null) {
			throw new IllegalDataException("The datasource is null");
		}
		
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(tableName + "(");
		// sql.append(StringUtils.join(values, ','));
		sql.append(") values(");
		sql.append(")");
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false); //
			
			ps = conn.prepareStatement(sql.toString());
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			close(ps, conn);
		}
	}
	
	public static void batchInsertQueryRequest_Zhxx(List<QueryRequest_Zhxx> zhxxList, int count) throws SQLException {
		if (zhxxList == null || zhxxList.size() == 0) {
			return;
		}
		
		DataSource dataSource = (DataSource) SpringContextHolder.getBean("busiDs");
		if (dataSource == null) {
			throw new IllegalDataException("The datasource is null");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into br30_xzcs_info");
		sql.append(" (bdhm,ccxh,khzh,cclb,zhzt,khwd,khwddm,khrq,xhrq,bz,ye,kyye,glzjzh,txdz,yzbm,lxdh,beiz,qrydt,orgkey,fksj)");
		sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false); //
			
			ps = conn.prepareStatement(sql.toString());
			
			int i = 0;
			for (QueryRequest_Zhxx zhxx : zhxxList) {
				ps.setString(1, zhxx.getBdhm());
				ps.setString(2, zhxx.getCcxh());
				ps.setString(3, zhxx.getKhzh());
				ps.setString(4, zhxx.getCclb());
				ps.setString(5, zhxx.getZhzt());
				ps.setString(6, zhxx.getKhwd());
				ps.setString(7, zhxx.getKhwddm());
				ps.setString(8, zhxx.getKhrq());
				ps.setString(9, zhxx.getXhrq());
				ps.setString(10, zhxx.getBz());
				ps.setString(11, zhxx.getYe());
				ps.setString(12, zhxx.getKyye());
				ps.setString(13, zhxx.getGlzjzh());
				ps.setString(14, zhxx.getTxdz());
				ps.setString(15, zhxx.getYzbm());
				ps.setString(16, zhxx.getLxdh());
				ps.setString(17, zhxx.getBeiz());
				ps.setString(18, zhxx.getQrydt());
				ps.setString(19, zhxx.getOrgkey());
				ps.setString(20, zhxx.getFksj());
				ps.addBatch(); // 
				
				if ((++i) % count == 0) {
					i = 0;
					ps.executeBatch();
					conn.commit();
				}
			}
			
			if (i > 0) {
				ps.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			close(ps, conn);
		}
	}
	
	public static void batchInsertQueryRequest_Wlxx(List<QueryRequest_Wlxx> wlxxList, int count) throws SQLException {
		if (wlxxList == null || wlxxList.size() == 0) {
			return;
		}
		
		DataSource dataSource = (DataSource) SpringContextHolder.getBean("busiDs");
		if (dataSource == null) {
			throw new IllegalDataException("The datasource is null");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into br30_xzcs_info_wl");
		sql.append(" (bdhm, ccxh, wlxh, jyzl, zjlx, bz, je, jysj, jylsh, zckzxm, zckzh, zckzhkhh, jydsyhhh, jydszjlx, jydszjhm, jydstxdz,");
		sql.append(" jydsyzbm, jydslxdh, beiz, khzh, organkey, qrydt)");
		sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false); //
			
			ps = conn.prepareStatement(sql.toString());
			
			int i = 0;
			for (QueryRequest_Wlxx wlxx : wlxxList) {
				ps.setString(1, wlxx.getBdhm());
				ps.setString(2, wlxx.getCcxh());
				ps.setString(3, wlxx.getWlxh());
				ps.setString(4, wlxx.getJyzl());
				ps.setString(5, wlxx.getZjlx());
				ps.setString(6, wlxx.getBz());
				ps.setString(7, wlxx.getJe());
				ps.setString(8, wlxx.getJysj());
				ps.setString(9, wlxx.getJylsh());
				ps.setString(10, wlxx.getZckzxm());
				ps.setString(11, wlxx.getZckzh());
				ps.setString(12, wlxx.getZckzhkhh());
				ps.setString(13, wlxx.getJydsyhhh());
				ps.setString(14, wlxx.getJydszjlx());
				ps.setString(15, wlxx.getJydszjhm());
				ps.setString(16, wlxx.getJydstxdz());
				ps.setString(17, wlxx.getJydsyzbm());
				ps.setString(18, wlxx.getJydslxdh());
				ps.setString(19, wlxx.getBeiz());
				ps.setString(20, wlxx.getKhzh());
				ps.setString(21, wlxx.getOrgankey());
				ps.setString(22, wlxx.getQrydt());
				ps.addBatch(); // 
				
				if ((++i) % count == 0) {
					i = 0;
					ps.executeBatch();
					conn.commit();
				}
			}
			
			if (i > 0) {
				ps.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			close(ps, conn);
		}
	}
	
	public static void batchInsertQueryRequest_Jrxx(List<QueryRequest_Jrxx> jrxxList, int count) throws SQLException {
		if (jrxxList == null || jrxxList.size() == 0) {
			return;
		}
		
		DataSource dataSource = (DataSource) SpringContextHolder.getBean("busiDs");
		if (dataSource == null) {
			throw new IllegalDataException("The datasource is null");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into br30_xzcs_info_zc");
		sql.append(" (bdhm, ccxh, zcxh, zcmc, zclx, cpxszl, dqh, jrcpbh,");
		sql.append(" lczh, zjhkzh, zcglr, zckfjy, zcjyxz, xzxcrq, zyqr, tgr, syr, clr, shr, tgzh,");
		sql.append(" jldw, bz, zcdwjg, se, kyse, zczje, beiz, khzh, qrydt, khwddm)");
		sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false); //
			
			ps = conn.prepareStatement(sql.toString());
			
			int i = 0;
			for (QueryRequest_Jrxx jrxx : jrxxList) {
				ps.setString(1, jrxx.getBdhm());
				ps.setString(2, jrxx.getCcxh());
				ps.setString(3, jrxx.getZcxh());
				ps.setString(4, jrxx.getZcmc());
				ps.setString(5, jrxx.getZclx());
				ps.setString(6, jrxx.getCpxszl());
				ps.setString(7, jrxx.getDqh());
				ps.setString(8, jrxx.getJrcpbh());
				ps.setString(9, jrxx.getLczh());
				ps.setString(10, jrxx.getZjhkzh());
				ps.setString(11, jrxx.getZcglr());
				ps.setString(12, jrxx.getZckfjy());
				ps.setString(13, jrxx.getZcjyxz());
				ps.setString(14, jrxx.getXzxcrq());
				ps.setString(15, jrxx.getZyqr());
				ps.setString(16, jrxx.getTgr());
				ps.setString(17, jrxx.getSyr());
				ps.setString(18, jrxx.getClr());
				ps.setString(19, jrxx.getShr());
				ps.setString(20, jrxx.getTgzh());
				ps.setString(21, jrxx.getJldw());
				ps.setString(22, jrxx.getBz());
				ps.setString(23, jrxx.getZcdwjg());
				ps.setString(24, jrxx.getSe());
				ps.setString(25, jrxx.getKyse());
				ps.setString(26, jrxx.getZczje());
				ps.setString(27, jrxx.getBeiz());
				ps.setString(28, jrxx.getKhzh());
				ps.setString(29, jrxx.getQrydt());
				ps.setString(30, jrxx.getKhwddm());
				ps.addBatch(); // 
				
				if ((++i) % count == 0) {
					i = 0;
					ps.executeBatch();
					conn.commit();
				}
			}
			
			if (i > 0) {
				ps.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			close(ps, conn);
		}
	}
	
	public static void batchInsertQueryRequest_Glxx(List<QueryRequest_Glxx> glxxList, int count) throws SQLException {
		if (glxxList == null || glxxList.size() == 0) {
			return;
		}
		
		DataSource dataSource = (DataSource) SpringContextHolder.getBean("busiDs");
		if (dataSource == null) {
			throw new IllegalDataException("The datasource is null");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into br30_xzcs_info_glxx");
		sql.append(" (bdhm, ccxh, glxh, zkhzh, glzhlb, glzhhm, bz, ye, kyye, beiz, qrydt)");
		sql.append(" values (?,?,?,?,?,?,?,?,?,?,?)");
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false); //
			
			ps = conn.prepareStatement(sql.toString());
			
			int i = 0;
			for (QueryRequest_Glxx glxx : glxxList) {
				ps.setString(1, glxx.getBdhm());
				ps.setString(2, glxx.getCcxh());
				ps.setString(3, glxx.getGlxh());
				ps.setString(4, glxx.getZkhzh());
				ps.setString(5, glxx.getGlzhlb());
				ps.setString(6, glxx.getGlzhhm());
				ps.setString(7, glxx.getBz());
				ps.setString(8, glxx.getYe());
				ps.setString(9, glxx.getKyye());
				ps.setString(10, glxx.getBeiz());
				ps.setString(11, glxx.getQrydt());
				ps.addBatch(); // 
				
				if ((++i) % count == 0) {
					i = 0;
					ps.executeBatch();
					conn.commit();
				}
			}
			
			if (i > 0) {
				ps.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			close(ps, conn);
		}
	}
	
	public static void batchInsertQueryRequest_Djxx(List<QueryRequest_Djxx> djxxList, int count) throws SQLException {
		if (djxxList == null || djxxList.size() == 0) {
			return;
		}
		
		DataSource dataSource = (DataSource) SpringContextHolder.getBean("busiDs");
		if (dataSource == null) {
			throw new IllegalDataException("The datasource is null");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into br30_xzcs_info_cs");
		sql.append(" (bdhm, ccxh, csxh,zcxh,jrcpbh, djjzrq, djjg, djwh, djje, beiz, qrydt)");
		sql.append(" values (?,?,?,?,?,?,?,?,?,?,?)");
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false); //
			
			ps = conn.prepareStatement(sql.toString());
			
			int i = 0;
			for (QueryRequest_Djxx djxx : djxxList) {
				ps.setString(1, djxx.getBdhm());
				ps.setString(2, djxx.getCcxh());
				ps.setString(3, djxx.getCsxh());
				ps.setString(4, djxx.getZcxh());
				ps.setString(5, djxx.getJrcpbh());
				ps.setString(6, djxx.getDjjzrq());
				ps.setString(7, djxx.getDjjg());
				ps.setString(8, djxx.getDjwh());
				ps.setString(9, djxx.getDjje());
				ps.setString(10, djxx.getBeiz());
				ps.setString(11, djxx.getQrydt());
				ps.addBatch(); // 
				
				if ((++i) % count == 0) {
					i = 0;
					ps.executeBatch();
					conn.commit();
				}
			}
			
			if (i > 0) {
				ps.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			close(ps, conn);
		}
	}
	
	public static void batchInsertQueryRequest_Qlxx(List<QueryRequest_Qlxx> qlxxList, int count) throws SQLException {
		if (qlxxList == null || qlxxList.size() == 0) {
			return;
		}
		
		DataSource dataSource = (DataSource) SpringContextHolder.getBean("busiDs");
		if (dataSource == null) {
			throw new IllegalDataException("The datasource is null");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into br30_xzcs_info_ql");
		sql.append(" (bdhm, ccxh, xh, zcxh, jrcpbh, qllx, qlr, qlje, qlrdz, qlrlxfs, djje, khzh, khwddm, qrydt)");
		sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false); //
			
			ps = conn.prepareStatement(sql.toString());
			
			int i = 0;
			for (QueryRequest_Qlxx qlxx : qlxxList) {
				ps.setString(1, qlxx.getBdhm());
				ps.setString(2, qlxx.getCcxh());
				ps.setString(3, qlxx.getXh());
				ps.setString(4, qlxx.getZcxh());
				ps.setString(5, qlxx.getJrcpbh());
				ps.setString(6, qlxx.getQllx());
				ps.setString(7, qlxx.getQlr());
				ps.setString(8, qlxx.getQlje());
				ps.setString(9, qlxx.getQlrdz());
				ps.setString(10, qlxx.getQlrlxfs());
				ps.setString(11, qlxx.getDjje());
				ps.setString(12, qlxx.getKhzh());
				ps.setString(13, qlxx.getKhwddm());
				ps.setString(14, qlxx.getQrydt());
				ps.addBatch(); // 
				
				if ((++i) % count == 0) {
					i = 0;
					ps.executeBatch();
					conn.commit();
				}
			}
			
			if (i > 0) {
				ps.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			rollback(conn);
			throw e;
		} finally {
			close(ps, conn);
		}
	}
	
	public static void rollback(Connection c) {
		if (c != null) {
			try {
				if (!c.isClosed()) {
					c.rollback();
				}
			} catch (Exception ex) {
			}
		}
	}
	
	public static void close(Statement s, Connection c) {
		if (s != null) {
			try {
				s.close();
			} catch (Exception e) {
			}
		}
		
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
			}
		}
	}
}
