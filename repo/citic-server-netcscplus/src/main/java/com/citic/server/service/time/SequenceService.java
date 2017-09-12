package com.citic.server.service.time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Liu Xuanfei
 * @date 2016年5月14日 上午11:34:27
 */
public class SequenceService {
	private final Logger logger = LoggerFactory.getLogger(SequenceService.class);
	
	private DataSource dataSource;
	private String sname;
	private int step = 20;
	private String expression = "{yyyyMMdd}{#22}";
	private boolean cutoff = false;
	
	private int maxRetries = 6; // 重试次数
	
	private Queue<Long> queue = new Queue<Long>();
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setSname(String sname) {
		this.sname = sname.toUpperCase();
	}
	
	public void setStep(int step) {
		this.step = step;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public void setCutoff(boolean cutoff) {
		this.cutoff = cutoff;
	}
	
	public String next() {
		synchronized (this) {
			if (queue.isEmpty()) {
				long[] generate = internalGenerate();
				if (generate != null) {
					for (long i = generate[0]; i <= generate[1]; i++) {
						queue.push(Long.valueOf(i));
					}
				} else {
					return null;
				}
			}
			
			// 解析表达式
			return decodeExpression(queue.pop().longValue()); //
		}
	}
	
	private String decodeExpression(long sid) {
		String nextVal = expression;
		int braceLevel = 0;
		int length = expression.length();
		int cursor = 0;
		char c;
		for (int i = 0; i < length; i++) {
			c = expression.charAt(i);
			if (c == '{') {
				if (braceLevel > 0) {
					fail(expression, i, "too many '{'");
				}
				braceLevel++;
				cursor = i + 1;
			} else if (c == '}') {
				braceLevel--;
				if (braceLevel < 0) {
					fail(expression, i, "missing '{'");
				}
				String token = expression.substring(cursor, i);
				if (token.startsWith("#")) {
					String nextSid = String.valueOf(sid);
					if (token.endsWith("#")) {
						if (token.length() < nextSid.length()) {
							fail(expression, i, "too short '#'");
						}
						nextVal = nextVal.replace("{" + token + "}", StringUtils.leftPad(nextSid, token.length(), "0"));
					} else {
						int num = Integer.parseInt(token.substring(1));
						nextVal = nextVal.replace("{" + token + "}", StringUtils.leftPad(nextSid, num, "0"));
					}
				} else {
					SimpleDateFormat format = new SimpleDateFormat(token);
					String datetime = format.format(new Date());
					nextVal = nextVal.replace("{" + token + "}", datetime);
				}
			}
		}
		
		if (braceLevel > 0) {
			fail(expression, length, "missing '}'");
		}
		
		return nextVal;
	}
	
	private void fail(String expression, int index, String description) {
		fail(expression, index, description, null);
	}
	
	private void fail(String expression, int index, String description, Throwable cause) {
		StringBuffer buffer = new StringBuffer('\n');
		buffer.append('\n');
		buffer.append(expression);
		buffer.append('\n');
		for (int i = 0; i < index; i++) {
			buffer.append(' ');
		}
		buffer.append('^');
		buffer.append(description);
		if (cause != null) {
			throw new IllegalArgumentException(buffer.toString(), cause);
		}
		throw new IllegalArgumentException(buffer.toString());
	}
	
	protected long[] internalGenerate() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false); // 
			for (int i = 0; i < maxRetries; i++) {
				long currSid = getCurrentSequenceValue(conn);
				if (currSid == -1L) {
					int row = executeUpdateImmediately(conn, getInsertSql());
					if (row == 1) { // 可能会插入失败。被其他服务抢先插入（集群模式下）。
						return new long[] {1L, step};
					}
				} else {
					long nextSid = currSid + step;
					int row = executeUpdateImmediately(conn, getUpdateSql(nextSid, currSid));
					if (row == 1) { // 可能会更新失败。被其他服务抢先更新（集群模式下）。
						return new long[] {currSid + 1, nextSid};
					}
				}
			}
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback(); //
				} catch (SQLException se) {
				}
			}
			logger.error("Database Exception: {}", e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}
	
	private long getCurrentSequenceValue(Connection conn) throws SQLException {
		long sid = -1L;
		PreparedStatement ps = conn.prepareStatement(getSelectSql());
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				sid = rs.getLong(1);
			}
			// TODO 根据时间以及cutoff类型，判断是否reset。改天有时间再弄吧。
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
				}
			}
		}
		return sid;
	}
	
	private int executeUpdateImmediately(Connection conn, String updateSql) throws SQLException {
		int row = 0;
		Statement statement = null;
		try {
			statement = conn.createStatement();
			row = statement.executeUpdate(updateSql);
			conn.commit(); // 
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
				}
			}
		}
		return row;
	}
	
	private String getSelectSql() {
		return "SELECT sid, datetime FROM mp01_pub_seq WHERE sname = '" + sname + "'";
	}
	
	private String getInsertSql() {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO mp01_pub_seq(sname, sid, datetime)");
		sql.append(" SELECT '" + sname + "', '" + step + "', '" + System.currentTimeMillis() + "'");
		sql.append(" FROM mp01_dual WHERE NOT EXISTS (SELECT '1' FROM mp01_pub_seq WHERE sname = '" + sname + "')");
		
		return sql.toString();
	}
	
	private String getUpdateSql(long newValue, long oldValue) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE mp01_pub_seq SET sid = '" + newValue + "', datetime = '" + System.currentTimeMillis() + "'");
		sql.append(" WHERE sname = '" + sname + "' AND sid = '" + oldValue + "'");
		
		return sql.toString();
	}
}
