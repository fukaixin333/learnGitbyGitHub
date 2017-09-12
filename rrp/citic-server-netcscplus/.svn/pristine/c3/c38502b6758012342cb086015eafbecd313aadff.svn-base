package com.citic.server.service.task.taskBo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.service.domain.MM14_model;
import com.citic.server.utils.DbFuncUtils;
import com.citic.server.utils.AmlDtUtils;

public class AlertBo {
	private static final Logger logger = LoggerFactory.getLogger(AlertBo.class);
	private DbFuncUtils dbfunc;

	public AlertBo() throws Exception {
		dbfunc = new DbFuncUtils();
	}

	// 事实表名：区分实验室与生产数据
	private String BR12_EVENT_TRANS_MX = "BR12_EVENT_TRANS_MX";// 事件交易明细表
	private String BR12_EVENT_TRANS = "BR12_EVENT_TRANS";// 事件交易关系表
	private String BR12_EVENT_PARTY = "BR12_EVENT_PARTY";// 事件客户关系表
	private String BR12_EVENT_PARTY_MX = "BR12_EVENT_PARTY_MX";// 事件客户明细表
	private String BR12_EVENT_FACT = "BR12_EVENT_FACT";// 事件主表
	private String BR12_EVENT_ACCT = "BR12_EVENT_ACCT";// 事件账户信息
	private String BR12_EVENT_DETAIL = "BR12_EVENT_DETAIL";// 事件通用信息

	public void init(String lab) {
		if (lab.equalsIgnoreCase("lab")) {
			BR12_EVENT_TRANS_MX = "BR12_EVENT_TRANS_MX" + "_L";
			BR12_EVENT_TRANS = "BR12_EVENT_TRANS" + "_L";
			BR12_EVENT_PARTY = "BR12_EVENT_PARTY" + "_L";
			BR12_EVENT_PARTY_MX = "BR12_EVENT_PARTY_MX" + "_L";
			BR12_EVENT_FACT = "BR12_EVENT_FACT" + "_L";
			BR12_EVENT_ACCT = "BR12_EVENT_ACCT" + "_L";// 事件账户信息
			BR12_EVENT_DETAIL = "BR12_EVENT_DETAIL" + "_L";// 事件通用信息
		}
	}

	/**
	 * 往中间表插入当天的交易
	 * 
	 * @param conn
	 * @param statisticdate数据日期
	 * @throws Exception
	 */
	public ArrayList insertBT13_TRANS_MID(ArrayList sqlList, String data_dt, MM14_model mm14_model) throws Exception {
		StringBuffer sql = new StringBuffer();
		// 插入当天预警交易
		sqlList.add(dbfunc.getDeleteSql("BT13_TRANS_MID"));
		logger.debug(dbfunc.getDeleteSql("BT13_TRANS_MID"));
		sql = new StringBuffer();
		sql.append(" INSERT INTO BT13_TRANS_MID (TRANS_KEY,DATA_DT,EVENT_KEY,ALERTKEY) ");
		sql.append(" SELECT DISTINCT TRANS_KEY,DATA_DT,EVENTKEY,ALERTKEY FROM " + BR12_EVENT_TRANS + " T1 ");
		sql.append(" WHERE T1.DATA_DT='" + data_dt + "'");
		sql.append(" AND  T1.EVENTKEY IN (" + mm14_model.getEventStr() + ") ");

		// sql.append(" SELECT T.TRANS_KEY,T.DATA_DT,EVENTKEY,ALERTKEY" +
		// " FROM(SELECT DISTINCT TRANS_KEY,DATA_DT,EVENTKEY,ALERTKEY FROM BR12_EVENT_TRANS T1 ");
		// sql.append(" WHERE T1.DATA_DT='" + data_dt + "'");
		// sql.append(" AND  T1.EVENTKEY IN (" + mm14_model.getEventStr() +
		// ") ) T");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());

		// 清理中间表
		logger.info("清理中间表 ");
		sqlList.add(dbfunc.getDeleteSql("BT13_MODULE_TRANS"));
		// 写入数据
		String sqlstr = "insert into BT13_MODULE_TRANS(ticd,cb_pk,tx_no,voucher_no,organkey,tstm,dt_time,ctac,catp,party_id,party_class_cd,tx_cd,cb_tx_cd,tx_type_cd,debit_credit,tsdr,subjectno,crtp,curr_cd,"
				+ "receive_pay_type,receive_pay_num,crat,cny_amt,usd_amt,amt_val,cash_flag,des,overarea_ind,tstp,crsp,opp_sysid,opp_isparty,tstp_type_f,tstp_type_f_cd,"
				+ "payment_trans_num,cfrc_area,channel,cfct,cfic,cfin,opp_party_id,tcnm,tcac,tcat,tcit,tcit_exp,tcid,opp_party_class_cd,batch_ind,teller,cal_ind,rule_ind,"
				+ " trans_type,tsct,bknm,bitp,bitp_exp,bkid,bknt,org_trans_rela,validate_ind,validate_ind2,tx_go_country,tx_go_area,temp1,temp2,last_upd_usr,"
				+ "trcd_country,trcd_area,status_cd,cfrc_country,re_ind,party_name,cust_id,opp_pbc_party_class_cd,opp_off_shore_ind,"
				+ "cash_ind,card_num,card_type,user_id,re_ind2,re_dt_s,re_dt_e,acct_num) "
				+ "select distinct ticd,cb_pk,tx_no,voucher_no,organkey,tstm,dt_time,ctac,catp,"
				+ "party_id,party_class_cd,tx_cd,cb_tx_cd,tx_type_cd,debit_credit,tsdr,subjectno,crtp,curr_cd, receive_pay_type,receive_pay_num,"
				+ "crat,cny_amt,usd_amt,amt_val,cash_flag,des,overarea_ind,tstp,crsp,opp_sysid,opp_isparty,tstp_type_f,tstp_type_f_cd,"
				+ "payment_trans_num,cfrc_area,channel,cfct,cfic,cfin,opp_party_id,tcnm,tcac,tcat,tcit,tcit_exp,tcid,opp_party_class_cd,batch_ind,teller,cal_ind,rule_ind,"
				+ " trans_type,tsct,bknm,bitp,bitp_exp,bkid,bknt,org_trans_rela,validate_ind,validate_ind2,tx_go_country,tx_go_area,temp1,temp2,last_upd_usr,"
				+ "trcd_country,trcd_area,status_cd,cfrc_country,re_ind,party_name,cust_id,opp_pbc_party_class_cd,opp_off_shore_ind,"
				+ "cash_ind,card_num,card_type,user_id,re_ind2,re_dt_s,re_dt_e,acct_num FROM "
				+ BR12_EVENT_TRANS_MX
				+ "  T1 WHERE  EXISTS (select 1 from BT13_TRANS_MID T2  where T2.TRANS_KEY=T1.TICD)";

		sqlList.add(sqlstr);
		logger.debug(sqlstr);

		return sqlList;
	}

	/**
	 * 当天所有转账交易
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_trans_d_acct(ArrayList sqlList) throws Exception {
		sqlList.add(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT"));
		logger.debug(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT"));
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO BT13_TRANS_D_ACCT  ");
		sql.append(" (recordid,acct_num,party_id,party_class_cd,cash_trans_flag,opp_party_id,opp_acct_num) ");
		sql.append(" Select DISTINCT ");
		sql.append(" CASE WHEN party_id<opp_party_id ");
		sql.append(" THEN  party_id||','||opp_party_id ");
		sql.append(" ELSE  opp_party_id||','||party_id end,'',");
		sql.append(" party_id, ");
		sql.append(" party_class_cd,cash_flag,opp_party_id,''");
		sql.append(" from BT13_MODULE_TRANS ");
		sql.append(" where  cal_ind='1'   ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());

		return sqlList;
	}

	/**
	 * 发生多笔交易的账号
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_acct_count(ArrayList sqlList) throws Exception {
		StringBuffer sql = new StringBuffer();
		sqlList.add(dbfunc.getDeleteSql("BT13_acct_count"));
		logger.debug(dbfunc.getDeleteSql("BT13_acct_count"));
		sql.append(" INSERT into BT13_acct_count(acct_num) ");
		sql.append(" Select party_id From BT13_trans_d_acct where opp_party_id is not null  group by party_id having count(*)>1  ");
		sql.append(" Union ");
		sql.append(" Select opp_party_id From BT13_trans_d_acct where opp_party_id is not NULL ");
		sql.append(" group by opp_party_id having count(*)>1 ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 多笔交易的客户号直接关联
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_net_acct_muti(ArrayList sqlList) throws Exception {
		StringBuffer sql = new StringBuffer();
		sqlList.add(dbfunc.getDeleteSql("BT13_net_acct_muti"));
		logger.debug(dbfunc.getDeleteSql("BT13_net_acct_muti"));
		sql.append(" Insert into BT13_net_acct_muti(net_id,acct_num,opp_acct_num,muti_acct_num) ");
		sql.append(" Select  distinct 'M'||dense_rank() over(order by s.acct_num) net_id,t.party_id,t.opp_party_id,s.acct_num ");
		sql.append(" From BT13_trans_d_acct t, BT13_acct_count s ");
		sql.append(" where (t.party_id = s.acct_num or t.opp_party_id = s.acct_num) ");
		sql.append(" and t.opp_party_id is not NULL ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 多笔交易的账号直接关联的账号插入合并表BT13_net_acct
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_net_acct1(ArrayList sqlList) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" Insert into BT13_net_acct(net_id,acct_num,flag) ");
		sql.append(" Select t1.net_id,t1.acct_num,'1' From BT13_net_acct_muti t1 ");
		sql.append(" Union ");
		sql.append(" SELECT t2.net_id,t2.opp_acct_num,'1' From BT13_net_acct_muti t2 ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 合并网络
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public boolean mergeNet(Connection conn, String statisticdate, String batch_no, boolean is_log) throws Exception {
		int count = 0, rows = 0;
		boolean bool = true;
		try {
			while (bool) {
				// 清理中间表

				logger.debug(dbfunc.getDeleteSql("BT13_net_rela"));
				this.exeSql(conn, dbfunc.getDeleteSql("BT13_net_rela"));
				count = count + 1;

				// 插入中间表
				StringBuffer sql = new StringBuffer();
				sql.append(" Insert into BT13_net_rela(acct_seq,net_id,flag) ");
				sql.append(" Select  " + count + "||'P'||'" + batch_no + "'||dense_rank() over(order by t1.acct_num) acct_seq, t1.net_id,t1.flag ");
				sql.append(" From BT13_net_acct t1,BT13_net_acct t2 ");
				sql.append(" Where t1.net_id!=t2.net_id ");
				sql.append(" and t1.acct_num = t2.acct_num  ");
				logger.debug(sql.toString());
				rows = this.exeSql(conn, sql.toString());

				if (rows == 0) {
					bool = false;
				} else {
					// 删除重复记录
					String deletesql = dbfunc.deleteRepeatRecord("BT13_net_rela", "net_id");
					logger.debug(deletesql);
					this.exeSql(conn, deletesql);
					// 记录合并的网络
					if (is_log) {
						sql = new StringBuffer();
						sql.append(" UPDATE BR13_MFA_MODIFY_LOG g ");
						sql.append("    SET (n_net_id, flag) = ");
						sql.append("        (SELECT r.acct_seq, flag ");
						sql.append("           FROM BT13_net_rela r ");
						sql.append("          WHERE r.net_id = g.n_net_id) ");
						sql.append("  WHERE exists (SELECT 1 FROM BT13_net_rela r WHERE r.net_id = g.n_net_id)       ");
						logger.debug(sql.toString());
						this.exeSql(conn, sql.toString());

						sql = new StringBuffer();
						sql.append(" INSERT INTO BR13_MFA_MODIFY_LOG  (n_net_id, o_net_id, upd_dt, flag) ");
						sql.append("   SELECT r.acct_seq, r.net_id, " + statisticdate + ", r.flag ");
						sql.append("     FROM BT13_net_rela r ");
						sql.append("     LEFT OUTER JOIN BR13_MFA_MODIFY_LOG g  ON (r.acct_seq = g.n_net_id) ");
						sql.append("    WHERE g.n_net_id IS NULL   ");
						sql.append("      AND r.flag = 'A' "); // 观察的网络,已生成预警的网络
						logger.debug(sql.toString());
						this.exeSql(conn, sql.toString());

					}

					// 更新网络标识ID
					sql = new StringBuffer();
					sql.append(" Update BT13_net_acct t ");
					sql.append("    Set t.net_id = (Select r.acct_seq From BT13_net_rela r Where r.net_id = t.net_id) ");
					sql.append("  Where exists (Select 1 From BT13_net_rela r where r.net_id = t.net_id) ");
					logger.debug(sql.toString());
					this.exeSql(conn, sql.toString());
					// 删除合并后的重复数据
					deletesql = dbfunc.deleteRepeatRecord("BT13_net_acct", "net_id,acct_num");
					logger.debug(deletesql);
					this.exeSql(conn, sql.toString());
				}
			}
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public int exeSql(Connection conn, String sql) throws java.sql.SQLException {
		Statement stmt = null;
		int i = 0;
		if ("".equals(sql) || sql == null) {
			return i;
		}
		try {
			stmt = conn.createStatement();
			logger.debug("sql:::" + sql);
			i = stmt.executeUpdate(sql);
		} catch (java.sql.SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
		return i;
	}

	/**
	 * 剩余未进入网络的关系数据
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_trans_d_acct_2(ArrayList sqlList) throws Exception {
		sqlList.add(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT2"));
		logger.debug(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT2"));
		StringBuffer sql = new StringBuffer();
		sql.append(" Insert into BT13_TRANS_D_ACCT2 ");
		sql.append(" Select t.* ");
		sql.append(" From BT13_TRANS_D_ACCT t left outer join BT13_net_acct s on (t.party_id = s.acct_num ) ");
		sql.append(" Where s.acct_num is null   ");
		sql.append(" UNION ");
		sql.append(" Select t.* ");
		sql.append(" From BT13_TRANS_D_ACCT t left outer join BT13_net_acct s on ( t.opp_party_id = s.acct_num) ");
		sql.append(" Where s.acct_num is null  and t.opp_party_id is not null      ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 找单笔交易中存在关联的
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_net_acct_single(ArrayList sqlList) throws Exception {
		sqlList.add(dbfunc.getDeleteSql("BT13_net_acct_single"));
		logger.debug(dbfunc.getDeleteSql("BT13_net_acct_single"));
		StringBuffer sql = new StringBuffer();
		sql.append(" Insert into BT13_net_acct_single ");
		sql.append("   (net_id, acct_num1, opp_acct_num1, acct_num2, opp_acct_num2) ");
		sql.append("   Select 'S' || dense_rank() over(order by t1.party_id), ");
		sql.append("          t1.party_id,t1.opp_party_id,t2.party_id,t2.opp_party_id ");
		sql.append("     From BT13_TRANS_D_ACCT2 t1, BT13_TRANS_D_ACCT2 t2 ");
		sql.append("    Where t1.party_id = t2.opp_party_id       ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 找单笔交易中存在关联的
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_net_acct2(ArrayList sqlList) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" Insert into BT13_net_acct  (net_id, acct_num, flag) ");
		sql.append(" Select t.net_id, t.acct_num1, '2'    From BT13_net_acct_single t ");
		sql.append(" Union ");
		sql.append(" Select t.net_id, t.acct_num2, '2'    From BT13_net_acct_single t ");
		sql.append(" Union ");
		sql.append(" Select t.net_id, t.opp_acct_num1, '2'    From BT13_net_acct_single t ");
		sql.append("  where opp_acct_num1 is not null   ");
		sql.append(" Union ");
		sql.append(" Select t.net_id, t.opp_acct_num2, '2'    From BT13_net_acct_single t ");
		sql.append("  where opp_acct_num2 is not null  ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 生成剩余未进入网络的关系数据
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_trans_d_acct_3(ArrayList sqlList) throws Exception {

		sqlList.add(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT3"));
		logger.debug(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT3"));
		StringBuffer sql = new StringBuffer();
		sql.append(" Insert into BT13_TRANS_D_ACCT3 ");
		sql.append(" Select  t.* ");
		sql.append(" From BT13_TRANS_D_ACCT2 t left outer join BT13_net_acct s on (t.party_id = s.acct_num) ");
		sql.append(" Where s.acct_num is null  ");
		sql.append(" UNION ");
		sql.append(" Select  t.* ");
		sql.append(" From BT13_TRANS_D_ACCT2 t left outer join BT13_net_acct s on (t.opp_party_id = s.acct_num) ");
		sql.append(" Where s.acct_num is null  and t.opp_party_id is not null  ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 插入剩余未进入网络的关系数据到中间表
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public ArrayList insertBT13_net_acct_non(ArrayList sqlList) throws Exception {

		sqlList.add(dbfunc.getDeleteSql("BT13_net_acct_non"));
		logger.debug(dbfunc.getDeleteSql("BT13_net_acct_non"));
		StringBuffer sql = new StringBuffer();
		sql.append(" Insert into BT13_net_acct_non  (net_id, acct_num, opp_acct_num) ");
		sql.append("   Select 'N' || dense_rank() over(order by t.party_id), ");
		sql.append("          t.party_id,t.opp_party_id ");
		sql.append("     From BT13_trans_d_acct3 t ");
		// sql.append("    Where t.opp_party_id is not null  ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 插入合并表BT13_net_acct（批次三）
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_net_acct3(ArrayList sqlList) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" Insert into BT13_net_acct  (net_id, acct_num, flag) ");
		sql.append(" Select n.net_id, n.acct_num, '3'    From BT13_net_acct_non n ");
		sql.append(" Union ");
		sql.append(" Select n.net_id, n.opp_acct_num, '3' From BT13_net_acct_non n  where  n.opp_acct_num is not null ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 准备批次四的临时数据 插入合并表BT13_net_acct（批次四）
	 * 
	 * @param conn
	 * @throws Exception
	 */

	public ArrayList insertBT13_net_acct4(ArrayList sqlList) throws Exception {

		sqlList.add(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT41"));
		logger.debug(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT41"));
		sqlList.add(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT42"));
		logger.debug(dbfunc.getDeleteSql("BT13_TRANS_D_ACCT42"));
		// 找出进入的acct_num和opp_acct_num
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO BT13_TRANS_D_ACCT41 ");
		sql.append("   (recordid, acct_num, opp_acct_num, net_id, t_acct_num) ");
		sql.append("   SELECT  t.recordid,t.party_id,t.opp_party_id,s.net_id,s.acct_num ");
		sql.append("     FROM BT13_TRANS_D_ACCT t, BT13_net_acct s ");
		sql.append("    WHERE t.party_id = s.acct_num       ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());

		sql = new StringBuffer();
		sql.append(" INSERT INTO BT13_TRANS_D_ACCT42 ");
		sql.append("   (recordid, acct_num, opp_acct_num, net_id, t_acct_num) ");
		sql.append("   SELECT  t.recordid,t.party_id,t.opp_party_id,s.net_id,s.acct_num ");
		sql.append("     FROM BT13_TRANS_D_ACCT t, BT13_net_acct s ");
		sql.append("    WHERE t.opp_party_id= s.acct_num ");
		sql.append("      and t.opp_party_id is not null     ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());

		// 找到一笔交易的双方在两个网络中
		sqlList.add(dbfunc.getDeleteSql("BT13_net_acct_patch"));
		sql = new StringBuffer();
		sql.append(" INSERT INTO BT13_net_acct_patch   (net_id, acct_num, opp_acct_num) ");
		sql.append("   select   'F' || dense_rank() over(order by t1.acct_num),t1.acct_num,t2.opp_acct_num ");
		sql.append("     from BT13_trans_d_acct41 t1, BT13_trans_d_acct42 t2 ");
		sql.append("    where t1.recordid = t2.recordid ");
		sql.append("      and t1.net_id != t2.net_id      ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());

		// 插入BT13_net_acct
		sql = new StringBuffer();
		sql.append(" INSERT INTO BT13_net_acct ");
		sql.append("   (net_id, acct_num, flag) ");
		sql.append("   SELECT   p.net_id, p.acct_num, '4'     FROM BT13_net_acct_patch p ");
		sql.append("   UNION ");
		sql.append("   SELECT   p.net_id, p.opp_acct_num, '4' FROM BT13_net_acct_patch p ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());

		return sqlList;
	}

	/**
	 * 插入当天合并的网络并更新最小日期
	 * 
	 * @param conn
	 * @param statisticdate
	 * @throws Exception
	 */
	public ArrayList insertBT13_net_acct_day(ArrayList sqlList, String statisticdate) throws Exception {

		sqlList.add(dbfunc.getDeleteSql("BT13_net_acct_day"));
		logger.debug(dbfunc.getDeleteSql("BT13_net_acct_day"));
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO BT13_net_acct_day(net_id,acct_num,create_dt,flag) ");// 当天新生成的网络(当日新合成，但不一定是当日创建的)
		sql.append(" SELECT '" + statisticdate.replace("-", "") + "'||case when length(t.net_id)>9 then SUBSTR(t.net_id,9) else t.net_id end, t.acct_num,");
		sql.append(" '" + statisticdate + "',t.flag ");
		sql.append("   FROM BT13_net_acct t  ");

		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 将当天网络中的交易保存出来
	 * 
	 * @param conn
	 * @param statisticdate
	 * @throws Exception
	 */
	public ArrayList insertBR13_net_event_trans(ArrayList sqlList, String modelkey) throws Exception {

		// sqlList.add( dbfunc.getDeleteSql("BR13_NET_EVENT_TRANS"));
		StringBuffer sql = new StringBuffer();
		// sql.append(" INSERT INTO BR13_NET_EVENT_TRANS ");
		// sql.append(" (NET_ID,MODELKEY,ALERTKEY,EVENT_KEY,TRANS_KEY,PARTY_ID,ACCT_NUM, ");
		// sql.append(" 		TRANS_AMT,DATA_DT) ");
		// sql.append(" SELECT  DISTINCT  T.NET_ID||'"+modelkey+"','" + modelkey
		// +
		// "',T1.ALERTKEY,T1.EVENT_KEY,N.TICD,N.PARTY_ID,N.ACCT_NUM,N.CRAT,T1.DATA_DT ");
		// sql.append("     FROM BT13_NET_ACCT_DAY T, BT13_MODULE_TRANS N ,BT13_TRANS_MID T1");
		// sql.append("    WHERE T.acct_num = N.party_id and N.ticd=T1.TRANS_KEY");
		// sql.append("   UNION ");
		// sql.append(" SELECT  DISTINCT T.NET_ID||'"+modelkey+"','" + modelkey
		// +
		// "',T1.ALERTKEY,T1.EVENT_KEY,N.TICD,N.PARTY_ID,N.ACCT_NUM,N.CRAT,T1.DATA_DT ");
		// sql.append("     FROM BT13_NET_ACCT_DAY T, BT13_MODULE_TRANS N ,BT13_TRANS_MID T1");
		// sql.append("    WHERE T.acct_num = N.OPP_party_id and N.ticd=T1.TRANS_KEY");

		sql.append(" INSERT");
		sql.append(dbfunc.dealInsterTable("BR13_NET_EVENT_TRANS"));
		sql.append(" (NET_ID,MODELKEY,ALERTKEY,EVENT_KEY,TRANS_KEY,PARTY_ID,ACCT_NUM, ");
		sql.append(" 		TRANS_AMT,DATA_DT) ");
		sql.append(" SELECT  DISTINCT  T.NET_ID||'" + modelkey + "','" + modelkey
				+ "',T1.ALERTKEY,T1.EVENT_KEY,N.TICD,N.PARTY_ID,N.ACCT_NUM,N.CRAT,T1.DATA_DT ");
		sql.append("     FROM BT13_NET_ACCT_DAY T, BT13_MODULE_TRANS N ,BT13_TRANS_MID T1");
		sql.append("    WHERE (T.acct_num = N.party_id or T.acct_num = N.OPP_party_id ) and N.ticd=T1.TRANS_KEY");

		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	public ArrayList insertBR13_net_event(ArrayList sqlList, String modelkey, String datetime) throws Exception {
		// sqlList.add( dbfunc.getDeleteSql( "BR13_NET_EVENT"));

		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO BR13_NET_EVENT  (NET_ID, MODELKEY, EVENT_KEY,DATA_DT) ");
		sql.append("   SELECT   NET_ID, MODELKEY, EVENT_KEY,DATA_DT ");
		sql.append("    FROM BR13_NET_EVENT_TRANS  WHERE MODELKEY='" + modelkey + "'  AND DATA_DT='" + datetime + "'"
				+ " GROUP BY  NET_ID, MODELKEY, EVENT_KEY,DATA_DT");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	public ArrayList insertBR13_net_trans(ArrayList sqlList, String criminal, String data_dt, String modelkey) throws Exception {
		// sqlList.add( dbfunc.getDeleteSql( "BR13_NET_TRANS"));
		// 取交易金额最大的客户
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO BR13_NET_MAIN  (NET_ID, MODELKEY,DATA_DT,CRIMINAL_TYPE,FLAG) ");
		sql.append("   SELECT DISTINCT NET_ID,MODELKEY,DATA_DT ,'" + criminal + "','0'");
		sql.append("    FROM BR13_NET_EVENT_TRANS  WHERE   MODELKEY='" + modelkey + "' AND DATA_DT='" + data_dt + "' GROUP BY NET_ID, MODELKEY,DATA_DT ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		// 清理中间表
		sqlList.add("delete from BT13_NET_MAIN");

		sql = new StringBuffer();
		sql.append("insert into BT13_NET_MAIN(net_id,party_id,trans_amt,event_c) (select   a.net_id,a.party_id,sum(a.trans_amt),count(distinct a.event_key)  as event_c"
				+ " from BR13_NET_EVENT_TRANS a " + "WHERE  MODELKEY='" + modelkey + "' AND DATA_DT='" + data_dt + "'  group by a.net_id,party_id ) ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		// 先取事件最多的客户，如事件数相同再取交易金额最大的客户
		StringBuffer sql1 = new StringBuffer();
		sql1.append("INSERT INTO BT13_NET_MAIN1(net_id, party_id)");
		sql1.append(" select NET_ID ,party_id from");
		sql1.append(" (  select distinct b.NET_ID, party_id,dense_rank() over(partition by b.net_id order by b.party_id) as partynum   from  ( ");
		sql1.append(" select  t.net_id, party_id, trans_amt  from BT13_NET_MAIN t, (select  net_id,max(event_c) as event_c from BT13_NET_MAIN a group by a.net_id) t1  where t.net_id=t1.net_id and t.event_c = t1.event_c) b,     ");
		sql1.append(" (select net_id, max(trans_amt) as trans_amt1 "
				+ " from (select t.net_id, party_id, trans_amt from BT13_NET_MAIN t, (select net_id,max(event_c) as event_c from BT13_NET_MAIN  group by net_id) t1  where t.net_id=t1.net_id and t.event_c = t1.event_c) b  group by net_id) b1"
				+ " where b.trans_amt = b1.trans_amt1  and b.net_id = b1.net_id ");
		sql1.append(" )TT  where  tt.partynum='1' ");
		sqlList.add(sql1.toString());
		logger.debug(sql1.toString());

		sql = new StringBuffer();
		sql.append("update BR13_NET_MAIN t  set party_id = (select party_id from BT13_NET_MAIN1 t1 where t1.net_id=t.net_id)  " + "   WHERE  t.DATA_DT='"
				+ data_dt + "'  AND t.MODELKEY ='" + modelkey + "' AND t.party_id is null    ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		/**
		 * sql.append("update BR13_NET_MAIN t  set party_id = " +
		 * "  (select party_id  from " +
		 * "       ( select t1.net_id, t.party_id from " +
		 * "          (select net_id, max(trans_amt) as trans_amt1   from  BT13_NET_MAIN  group by net_id ) t1"
		 * +
		 * "           left join BT13_NET_MAIN t    on t.net_id = t1.net_id   and t.trans_amt = t1.trans_amt1) t3"
		 * + "    where t.net_id = t3.net_id)" +
		 * "   where t.DATA_DT='"+data_dt+"'  AND MODELKEY ='"
		 * +modelkey+"' AND   t.party_id is null ");
		 **/
		return sqlList;
	}

	/**
	 * 
	 * @param sqlList
	 * @param modulekey
	 *            模型KEY
	 * @param statisdt
	 *            当前数据时间
	 * @param befordt
	 *            数据周期
	 * @param curr_cd
	 *            是否区分本外币
	 * @return
	 * @throws Exception
	 */
	public ArrayList insertBR13_CASE_FACT(ArrayList sqlList, String modulekey, String statisdt, String befordt)
			throws Exception {
		// sqlList.add( dbfunc.getDeleteSql( "BR13_PARTY_EVENT_TRANS"));
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT  INTO BR21_CASE_FACT(APP_ID,MODELKEY,PARTY_ID,CREATE_DT,FLAG,DATA_DT,PARTY_CLASS_CD,OBJKEY,STCRKEY)  SELECT ");
		sql.append("    T1.OBJKEY||'" + modulekey + "'||'" + statisdt.replace("-", "") + "'||T2.STCRKEY");
		sql.append(",'" + modulekey + "',T1.PARTY_ID, '" + AmlDtUtils.getNowDate() + "','0','" + statisdt + "'");
		sql.append(" ,T1.PARTY_CLASS_CD ,T1.OBJKEY,T2.STCRKEY FROM " + BR12_EVENT_FACT + " T1,MM12_EVENT T2  WHERE T1.INTERFACTKEY='2'  AND T1.EVENTKEY=T2.EVENTKEY  AND T1.EVENTKEY IN  ");
		sql.append("    ( SELECT T.EVENTKEY  FROM MM14_LINK_EVENT T where T.MODELKEY='" + modulekey + "')");
		sql.append("  AND   T1.DATA_DT ='" + statisdt + "' GROUP BY  T1.PARTY_ID,T1.PARTY_CLASS_CD,T1.OBJKEY,T2.STCRKEY ");
	
		sqlList.add(sql.toString());
		logger.debug(sql.toString());

		sql = new StringBuffer();
		sql.append(" INSERT  INTO BR21_CASE_ALERT(APP_ID,MODELKEY,ALERTKEY,DATA_DT,EVENTKEY ,PARTY_CLASS_CD ,STCRKEY,OBJKEY)  SELECT ");
		 sql.append(" T1.OBJKEY||'" + modulekey + "'||'" + statisdt.replace("-", "") + "'||T2.STCRKEY"+ ",");	
		sql.append("  '" + modulekey + "' ,T1.ALERTKEY,'" + statisdt + "',T1.EVENTKEY " + " ,T.PARTY_CLASS_CD,T2.STCRKEY,T1.OBJKEY  "
				+ "FROM BR21_CASE_FACT T , MM12_EVENT T2," + BR12_EVENT_FACT +" T1 "
				+ "  WHERE T.PARTY_ID=T1.PARTY_ID AND T1.EVENTKEY=T2.EVENTKEY  AND T.DATA_DT='" + statisdt + "' AND T1.INTERFACTKEY='2'  AND  ");
		sql.append("  T.MODELKEY ='" + modulekey+"'" );
		sql.append("  AND T1.DATA_DT >='" + befordt + "' and T1.DATA_DT <='" + statisdt + "'  "
				+ " GROUP BY  T1.PARTY_ID,T1.ALERTKEY,T1.EVENTKEY,T.PARTY_CLASS_CD,T2.STCRKEY ,T1.OBJKEY ");
	
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}

	/**
	 * 
	 * @param sqlList
	 * @param modulekey
	 * @param statisdt
	 * @return
	 * @throws Exception
	 */
	public ArrayList insertBR13_CASE_PARTY(ArrayList sqlList, String modulekey, String statisdt) throws Exception {
		// sqlList.add( dbfunc.getDeleteSql( "BR13_PARTY_EVENT_TRANS"));S
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT  INTO BR21_CASE_PARTY(APP_ID,MODELKEY,ALERTKEY,PARTY_ID,EVENTKEY,DATA_DT,PARTY_CLASS_CD,STCRKEY,OBJKEY) ");
		sql.append("  SELECT DISTINCT  T1.APP_ID,'" + modulekey + "',T1.ALERTKEY,T2.PARTY_ID,T2.EVENTKEY,'" + statisdt + "' " + " ,T1.PARTY_CLASS_CD,T1.STCRKEY,T1.OBJKEY "
				+ " FROM BR21_CASE_ALERT T1,"+ BR12_EVENT_PARTY + " T2 ");
		sql.append("    WHERE T1.ALERTKEY=T2.ALERTKEY AND T1.DATA_DT='" + statisdt + "'  ");
		sql.append("                AND  T1.MODELKEY ='" + modulekey + "'  ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		
/*		sql= new StringBuffer();
		sql.append("update BR13_CASE_PARTY t set t.stcrkey=(select stcrkey from MM12_EVENT t0  where  t.EVENTKEY=t0.Eventkey) where t.DATA_DT='"+statisdt+"'"
				+ " and t.MODELKEY='"+modulekey+"'");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());*/
		
		return sqlList;
	}

	public ArrayList insertBR13_CASE_ACCT(ArrayList sqlList, String modulekey, String statisdt) throws Exception {
		// sqlList.add( dbfunc.getDeleteSql( "BR13_PARTY_EVENT_TRANS"));
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT  INTO BR21_CASE_ACCT(APP_ID,MODELKEY,ALERTKEY,ACCT_NUM,PARTY_ID,EVENTKEY,DATA_DT,PARTY_CLASS_CD,STCRKEY ,OBJKEY,CARDNUMBER ) ");
		sql.append("  SELECT DISTINCT T1.APP_ID,'" + modulekey + "',T1.ALERTKEY,T2.ACCT_NUM,T2.PARTY_ID,T2.EVENTKEY,'" + statisdt + "', T1.PARTY_CLASS_CD,T1.STCRKEY,T1.OBJKEY,T2.CARDNUMBER"
				+ "     FROM BR21_CASE_ALERT T1," + BR12_EVENT_ACCT + " T2 ");
		sql.append("    WHERE T1.ALERTKEY=T2.ALERTKEY AND T1.DATA_DT='" + statisdt + "'  ");
		sql.append("                AND  T1.MODELKEY ='" + modulekey + "'  ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		
		 sql = new StringBuffer();
		sql.append("INSERT  INTO BR21_CASE_EVENT(APP_ID,MODELKEY,ALERTKEY,ENTITYKEY,ENTITYPK,PARTY_ID,EVENTKEY,DATA_DT,PARTY_CLASS_CD,STCRKEY ,OBJKEY ) ");
		sql.append("  SELECT DISTINCT T1.APP_ID,'" + modulekey + "',T1.ALERTKEY,T2.ENTITYKEY,T2.ENTITYPK,T2.PARTY_ID,T2.EVENTKEY,'" + statisdt + "', T1.PARTY_CLASS_CD,T1.STCRKEY,T1.OBJKEY"
				+ "     FROM BR21_CASE_ALERT T1," + BR12_EVENT_DETAIL + " T2 ");
		sql.append("    WHERE T1.ALERTKEY=T2.ALERTKEY AND T1.DATA_DT='" + statisdt + "'  ");
		sql.append("                AND  T1.MODELKEY ='" + modulekey + "'  ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
	 
		return sqlList;
	}

 

	public ArrayList insertBR13_CASE_TRANS(ArrayList sqlList, String modulekey, String statisdt,String befordate) throws Exception {
		// sqlList.add( dbfunc.getDeleteSql( "BR13_PARTY_EVENT_TRANS"));
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT  INTO BR21_CASE_TRANS(APP_ID,MODELKEY,ACCT_NUM,PARTY_ID,TRANS_KEY,TRANS_AMT,EVENT_KEY,DATA_DT,PARTY_CLASS_CD,STCRKEY,OBJKEY,ALERTKEY,CARDNUMBER) ");
		sql.append("  SELECT  DISTINCT T1.APP_ID,'" + modulekey + "',T2.ACCT_NUM,T2.PARTY_ID,T2.TRANS_KEY,T2.TRANS_AMT,T2.EVENTKEY,'" + statisdt
				+ "' " + ",T1.PARTY_CLASS_CD,T1.STCRKEY,T1.OBJKEY,T1.ALERTKEY,T2.CARDNUMBER  FROM BR21_CASE_ALERT T1,(SELECT * FROM " + BR12_EVENT_TRANS + " WHERE DATA_DT>='" + befordate + "' and DATA_DT <='" + statisdt + "')  T2 ");
		sql.append("    WHERE T1.ALERTKEY=T2.ALERTKEY AND T1.DATA_DT='" + statisdt + "'  ");
		sql.append("                AND  T1.MODELKEY ='" + modulekey + "'  ");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		
/*		sql= new StringBuffer();
		sql.append("update BR13_CASE_TRANS t set t.stcrkey=(select stcrkey from MM12_EVENT t0  where  t.EVENT_KEY=t0.Eventkey) where t.DATA_DT='"+statisdt+"' "
				+ "and t.MODELKEY='"+modulekey+"'");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());*/
		
		return sqlList;
	}

/*	public ArrayList updateCRIMINAL_TYPE(ArrayList sqlList, String modulekey, String statisdt) throws Exception {
		// sqlList.add( dbfunc.getDeleteSql( "BR13_PARTY_EVENT_TRANS"));
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE BR13_CASE_FACT T  SET T.CRIMINAL_TYPE =");
		sql.append("  (SELECT CRIMINAL_TYPE  FROM  MM14_MODEL T1 WHERE T.MODELKEY=T1.MODELKEY) ");
		sql.append("  WHERE  T.DATA_DT='" + statisdt + "' AND  T.MODELKEY ='" + modulekey + "'");
		sqlList.add(sql.toString());
		logger.debug(sql.toString());
		return sqlList;
	}*/

	public ArrayList deleteTaskNet(ArrayList sqlList, String data_dt, String modelkey) throws Exception {
		String sql = "";

		String sqlwhere = "SELECT NET_ID FROM BR13_NET_MAIN WHERE FLAG IN ('2','3') ";
		// 清理网络_事件交易表 BR13_NET_EVENT_TRANS
		sql = "DELETE FROM  BR13_NET_EVENT_TRANS WHERE (NET_ID IN (" + sqlwhere + ")  OR  DATA_DT='" + data_dt + "') AND MODELKEY='" + modelkey + "'  ";
		sqlList.add(sql);
		logger.debug(sql.toString());

		// 清理网络_事件明细表 BR13_NET_EVENT
		sql = "DELETE FROM  BR13_NET_EVENT WHERE (NET_ID IN (" + sqlwhere + ")  OR  DATA_DT='" + data_dt + "') AND MODELKEY='" + modelkey + "'  ";
		sqlList.add(sql);
		logger.debug(sql.toString());

		// 清理网络_网络特征事实表BR13_NET_EVENT_TZ
		sql = "DELETE FROM  BR13_NET_EVENT_TZ WHERE (NET_ID IN (" + sqlwhere + ") OR  DATA_DT='" + data_dt + "') AND MODELKEY='" + modelkey + "'  ";
		sqlList.add(sql);
		logger.debug(sql.toString());

		// 网络_主表BR13_NET_MAIN
		sql = "DELETE FROM  BR13_NET_MAIN  WHERE ( FLAG IN ('2','3') OR DATA_DT='" + data_dt + "') AND MODELKEY='" + modelkey + "'";
		sqlList.add(sql);
		logger.debug(sql.toString());
		// 删除环节历史表
		sql = "DELETE FROM BH13_LINK_ALERT_HIS WHERE BUSINESSKEY IN (SELECT NET_ID FROM BR13_NET_MAIN WHERE  ( FLAG IN ('2','3') OR  DATA_DT='"+data_dt+"')   AND MODELKEY='" + modelkey + "'  ) ";
		sqlList.add(sql);
		logger.debug(sql.toString());
		sql = "DELETE FROM BH13_LINK_SCORE_HIS WHERE BUSINESSKEY IN (SELECT NET_ID FROM BR13_NET_MAIN WHERE  ( FLAG IN ('2','3') OR  DATA_DT='"+data_dt+"')   AND MODELKEY='" + modelkey + "'  )";
		sqlList.add(sql);
		logger.debug(sql.toString());
		return sqlList;
	}

	public ArrayList deleteTask(ArrayList sqlList, String data_dt, String modelkey) throws Exception {
		String sql = "";

		// 清理单主体合并_客户明细合并表BR13_CASE_PARTY
		sql = "DELETE FROM  BR21_CASE_PARTY WHERE   MODELKEY='" + modelkey + "'";
		sqlList.add(sql);
		logger.debug(sql.toString());

		// 清理单主体合并_账户明细表BR13_CASE_ACCT
		sql = "DELETE FROM  BR21_CASE_ACCT WHERE  MODELKEY='" + modelkey + "'";
		sqlList.add(sql);
		logger.debug(sql.toString());

		// 清理单主体_事件交易表BR13_CASE_TRANS
		sql = "DELETE FROM  BR21_CASE_TRANS WHERE  MODELKEY='" + modelkey + "'";
		sqlList.add(sql);
		logger.debug(sql.toString());

		// 清理单主体_事件明细表BR13_CASE_EVENT
		sql = "DELETE FROM  BR21_CASE_EVENT WHERE   MODELKEY='" + modelkey + "'";
		sqlList.add(sql);
		logger.debug(sql.toString());

		// 清理BR13_CASE_ALERT
		sql = "DELETE FROM  BR21_CASE_ALERT WHERE  MODELKEY='" + modelkey + "'";
		sqlList.add(sql);
		logger.debug(sql.toString());

		// 清理单主体合并_预警全并主表BR13_CASE_FACT
		sql = "DELETE FROM  BR21_CASE_FACT WHERE  MODELKEY='" + modelkey + "'";
		sqlList.add(sql);
		logger.debug(sql.toString());

		// 删除环节历史表
		sql = "DELETE FROM BH13_LINK_ALERT_HIS WHERE   MODELKEY='" + modelkey + "'    ";
		sqlList.add(sql);
		logger.debug(sql.toString());
		sql = "DELETE FROM BH13_LINK_SCORE_HIS WHERE   MODELKEY='" + modelkey + "'   ";
		sqlList.add(sql);
		logger.debug(sql.toString());
		return sqlList;
	}

}
