package com.citic.server.service.task;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.utils.DbFuncUtils;

/**
 * 初始化单日交易表
 * 
 * @author hubaiqing
 * @version 1.0
 */

public class TK_AML200 extends BaseTask {

	private static final Logger logger = LoggerFactory.getLogger(TK_AML200.class);
	private JdbcTemplate jdbcTemplate = null;
	String BH11_TRANS_D=" BH11_TRANS_D ";
	public TK_AML200(ApplicationContext ac, MC00_task_fact mC00_task_fact) {
		super(ac, mC00_task_fact);
		ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
		jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());
		if (this.getMC00_task_fact().getTasksource().equalsIgnoreCase("lab")) {
			BH11_TRANS_D = " BH11_TRANS_D" + "_L ";
		}
	}

	/**
	 * 
	 */
	public boolean calTask() throws Exception {
		boolean isSucc = true;
		ArrayList sqlList = new ArrayList();
		DbFuncUtils dbfunc = new DbFuncUtils();
		String taskid = this.getMC00_task_fact().getTaskid();
		String indickey = this.getMC00_task_fact().getSubtaskid();// =indickey
		String datatime = this.getMC00_task_fact().getDatatime();	
	
		String sql = "select count(*) from "+BH11_TRANS_D+"  where TSTM='" + datatime + "'";
		logger.debug(sql);
		int count = jdbcTemplate.queryForInt(sql);
		if (count <= 0) {
			// 初始化评级日流水表
			sql = dbfunc.getTruncateSql(BH11_TRANS_D);
			logger.debug(sql);
			sqlList.add(sql);
			sql = this.InsertBR16_trans_s_m(datatime);
			logger.debug(sql);
			sqlList.add(sql);
			isSucc = this.syncToDatabase(sqlList);
		}
		return isSucc;
	}

	public String InsertBR16_trans_s_m(String statisticdate) throws Exception {
		String sql = "INSERT INTO "+BH11_TRANS_D+"(TICD,CB_PK,TX_NO,VOUCHER_NO,CASH_IND,CASH_FLAG,DEBIT_CREDIT,TSDR,ORGANKEY,TSTM,DT_TIME,PARTY_ID,CUST_ID,"
				+ "PARTY_NAME,PARTY_CLASS_CD,ACCT_NUM,CTAC,CATP,CARD_NUM,CARD_TYPE,TX_CD,CB_TX_CD,TX_TYPE_CD,SUBJECTNO,"
				+ "CRTP,CURR_CD,CRAT,CNY_AMT,USD_AMT,AMT_VAL,DES,OVERAREA_IND,TSTP,CRSP,CHANNEL,TSCT,TRCD_COUNTRY,TRCD_AREA,"
				+ "TX_GO_COUNTRY,TX_GO_AREA,OPP_SYSID,OPP_ISPARTY,CFRC_COUNTRY,CFRC_AREA,CFCT,CFIC,CFIN,OPP_PARTY_ID,TCNM,"
				+ "TCAC,TCAT,TCIT,TCID,OPP_PARTY_CLASS_CD,OPP_PBC_PARTY_CLASS_CD,OPP_OFF_SHORE_IND,OPP_CTVC,BKNM,BITP,BKID,BKNT,ORG_TRANS_RELA,"
				+ "VALIDATE_IND,VALIDATE_IND2,TELLER,TCIT_EXP,BITP_EXP,RECEIVE_PAY_TYPE,RECEIVE_PAY_NUM,TSTP_TYPE_F,TSTP_TYPE_F_CD,PAYMENT_TRANS_NUM,"
				+ "IS_MERCH,MCC_MERCH,MERCH_NO,TRAN_IP,STATUS_CD,BATCH_IND,USER_ID,RE_IND,RE_IND2,RE_DT_E,RE_DT_S,CAL_IND,RULE_IND,TRANS_TYPE,"
				+ "TEMP1,TEMP2,LAST_UPD_USR,CARDNUMBER,TRANSACTIONSTATUS )"
				+ " SELECT  TICD,CB_PK,TX_NO,VOUCHER_NO,CASH_IND,CASH_FLAG,DEBIT_CREDIT,TSDR,ORGANKEY,TSTM,DT_TIME,PARTY_ID,CUST_ID,"
				+ "PARTY_NAME,PARTY_CLASS_CD,ACCT_NUM,CTAC,CATP,CARD_NUM,CARD_TYPE,TX_CD,CB_TX_CD,TX_TYPE_CD,SUBJECTNO,"
				+ "CRTP,CURR_CD,CRAT,CNY_AMT,USD_AMT,AMT_VAL,DES,OVERAREA_IND,TSTP,CRSP,CHANNEL,TSCT,TRCD_COUNTRY,TRCD_AREA,"
				+ "TX_GO_COUNTRY,TX_GO_AREA,OPP_SYSID,OPP_ISPARTY,CFRC_COUNTRY,CFRC_AREA,CFCT,CFIC,CFIN,OPP_PARTY_ID,TCNM,"
				+ "TCAC,TCAT,TCIT,TCID,OPP_PARTY_CLASS_CD,OPP_PBC_PARTY_CLASS_CD,OPP_OFF_SHORE_IND,OPP_CTVC,BKNM,BITP,BKID,BKNT,ORG_TRANS_RELA,"
				+ "VALIDATE_IND,VALIDATE_IND2,TELLER,TCIT_EXP,BITP_EXP,RECEIVE_PAY_TYPE,RECEIVE_PAY_NUM,TSTP_TYPE_F,TSTP_TYPE_F_CD,PAYMENT_TRANS_NUM,"
				+ "IS_MERCH,MCC_MERCH,MERCH_NO,TRAN_IP,STATUS_CD,BATCH_IND,USER_ID,RE_IND,RE_IND2,RE_DT_E,RE_DT_S,CAL_IND,RULE_IND,TRANS_TYPE,"
				+ "TEMP1,TEMP2,LAST_UPD_USR,CARDNUMBER,TRANSACTIONSTATUS   FROM  BB11_TRANS T WHERE T.TSTM='" + statisticdate + "'";
		return sql;
	}

}