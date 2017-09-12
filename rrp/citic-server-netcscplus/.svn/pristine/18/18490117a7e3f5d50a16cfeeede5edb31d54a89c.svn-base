package com.citic.server.dx.task.taskBo;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DbFuncUtils;
import com.citic.server.utils.DtUtils;

public class Br13_alertBo {
	private static final Logger logger = (Logger) LoggerFactory
			.getLogger(Br13_alertBo.class);

	private DbFuncUtils dbfunc = new DbFuncUtils();

	/**
	 * 删除生成案例的数据
	 * 
	 */
	public ArrayList<String> delBR13_date(String data_dt,
			ArrayList<String> sqlList) throws Exception {

		// 删除预警客户表
		// String
		// sql="delete from  br21_alert_party a where exists(select 'x' from br21_alert_fact b where b.app_id=a.app_id and b.status_cd='1')  or data_dt='"+data_dt+"'";
		String sql = "delete from  br21_alert_party where app_id in (select app_id from br21_alert_fact where status_cd='1')  or data_dt='"
				+ data_dt + "'";
		sqlList.add(sql);

		// 删除预警客户表
		// sql="delete from  br21_alert_trans  a where exists(select 'x' from br21_alert_fact b where b.app_id=a.app_id and b.status_cd='1')  or data_dt='"+data_dt+"'";
		sql = "delete from  br21_alert_trans   where app_id in(select app_id from br21_alert_fact  where status_cd='1')  or data_dt='"
				+ data_dt + "'";
		sqlList.add(sql);

		// 删除预警合并主表
		sql = "delete from  br21_alert_fact   where status_cd='1'  or data_dt='"
				+ data_dt + "'";
		sqlList.add(sql);

		return sqlList;
	}

	/**
	 * 将匹配成功的数据放入合并表
	 * 
	 */
	public ArrayList<String> insertBR13_date(String data_dt,
			ArrayList<String> sqlList) throws Exception {

		// 放入合并主表
		String sql = " insert into br21_alert_fact(app_id,modelkey,party_id,objkey,curr_cd,data_dt,stcrkey,party_class_cd,status_cd)"
				+ " select  distinct app_id,modelkey,party_id,objkey,curr_cd,data_dt,stcrkey,party_class_cd,'0'"
				+ " from br21_case_fact  where flag='1' and  data_dt='"
				+ data_dt + "'";
		sqlList.add(sql);

		// 放入交易中的客户和账户和卡
		sql = " insert into br21_alert_party(app_id,modelkey,alertkey,party_id,objkey,cardnumber,acct_num,eventkey,data_dt,stcrkey,party_class_cd)"
				+ " select  distinct app_id,modelkey,alertkey,party_id,objkey,cardnumber,acct_num,event_key,data_dt,stcrkey,party_class_cd"
				+ " from br21_case_trans a  where data_dt='"
				+ data_dt
				+ "' "
				+ " and exists (select  'x' from br21_case_fact c  where  c.app_id=a.app_id and c.flag='1' )";
		sqlList.add(sql);

		// 放入账户表中的客户和账户和卡
		sql = " insert into br21_alert_party(app_id,modelkey,alertkey,party_id,objkey,cardnumber,acct_num,eventkey,data_dt,stcrkey,party_class_cd)"
				+ " select  distinct a.app_id,a.modelkey,a.alertkey,a.party_id,a.objkey,a.cardnumber,a.acct_num,a.eventkey,a.data_dt,a.stcrkey,a.party_class_cd"
				+ " from br21_case_acct a left join (select * from br21_alert_party  where data_dt='"
				+ data_dt
				+ "') b  on (b.app_id=a.app_id  and a.acct_num=b.acct_num) "
				+ " where a.data_dt='"
				+ data_dt
				+ "' and b.acct_num is null"
				+ " and exists (select  'x' from br21_case_fact c  where  c.app_id=a.app_id and c.flag='1' )";
		sqlList.add(sql);

		// 放入卡表中的客户和账户和卡
		sql = " insert into br21_alert_party(app_id,modelkey,alertkey,party_id,objkey,cardnumber,acct_num,eventkey,data_dt,stcrkey,party_class_cd)"
				+ " select  distinct a.app_id,a.modelkey,a.alertkey,a.party_id,a.objkey,a.entitypk,'',a.eventkey,a.data_dt,a.stcrkey,a.party_class_cd"
				+ " from br21_case_event a left join (select * from br21_alert_party  where data_dt='"
				+ data_dt
				+ "') b  on (b.app_id=a.app_id  and a.entitypk=b.cardnumber) "
				+ " where a.data_dt='"
				+ data_dt
				+ "'  and a.entitykey='505' and b.cardnumber is null"
				+ " and exists (select  'x' from br21_case_fact c  where  c.app_id=a.app_id and c.flag='1' )";
		sqlList.add(sql);

		// 放入客户表中的客户和账户和卡
		sql = " insert into br21_alert_party(app_id,modelkey,alertkey,party_id,objkey,cardnumber,acct_num,eventkey,data_dt,stcrkey,party_class_cd)"
				+ " select  distinct a.app_id,a.modelkey,a.alertkey,a.party_id,a.objkey,'','',a.eventkey,a.data_dt,a.stcrkey,a.party_class_cd"
				+ " from br21_case_party a left join (select * from br21_alert_party  where data_dt='"
				+ data_dt
				+ "') b  on  (b.app_id=a.app_id  and a.party_id=b.party_id) "
				+ " where a.data_dt='"
				+ data_dt
				+ "'  and b.party_id is null"
				+ " and exists (select  'x' from br21_case_fact c  where  c.app_id=a.app_id and c.flag='1' )";
		sqlList.add(sql);

		// 插入预警交易
		sql = " insert into br21_alert_trans(app_id,modelkey,alertkey,party_id,objkey,cardnumber,acct_num,trans_key,event_key,trans_amt,data_dt,stcrkey,party_class_cd)"
				+ " select  distinct app_id,modelkey,alertkey,party_id,objkey,cardnumber,acct_num,trans_key,event_key,trans_amt,data_dt,stcrkey,party_class_cd"
				+ " from br21_case_trans a  where data_dt='"
				+ data_dt
				+ "'"
				+ " and exists (select  'x' from br21_case_fact c  where  c.app_id=a.app_id and c.flag='1' )";
		sqlList.add(sql);

		return sqlList;
	}

	public ArrayList<String> updateBR13_date(String bsmStr, String data_dt,
			ArrayList<String> sqlList) throws Exception {

		String[] bsmStr1 = StringUtils.split(bsmStr, ",");
		String mDay = bsmStr1[0];// 多少天没有案例时生成
		String afterdate = DtUtils.add(data_dt, 1, Integer.valueOf(mDay));

		String hbDay = bsmStr1[2];// 合并天数
		String befordate = DtUtils.add(data_dt, 1, Integer.valueOf(hbDay));

		String transcount = bsmStr1[1];// 合并交易笔数
		// 删除中间表
		String delsql = dbfunc.getDeleteSql("br21_alert_fact_mid");
		sqlList.add(delsql);

		String insertsql = "insert into br21_alert_fact_mid(objkey,stcrkey) "
				+ "  select  objkey ,stcrkey  from br21_alert_trans  where  data_dt<='"
				+ data_dt
				+ "'  group by objkey,stcrkey having count(distinct trans_key)>"
				+ transcount;
		sqlList.add(insertsql);
		// 笔数达到
		String sql = "update br21_alert_fact a  set status_cd='1'  where  exists(select 'x' from   br21_alert_fact_mid b"
				+ "  where b.objkey=a.objkey and b.stcrkey=a.stcrkey  and a.status_cd='0'  and  data_dt<='"
				+ data_dt + "' ) ";
		sqlList.add(sql);

		// 删除中间表
		delsql = dbfunc.getDeleteSql("br21_alert_fact_mid");
		sqlList.add(delsql);

		insertsql = "insert into br21_alert_fact_mid(objkey,stcrkey) "
				+ " select a.objkey,a.stcrkey from("
				+ "  select  objkey ,stcrkey ,min(data_dt) as mindate,max(data_dt) as maxdate from br21_alert_party  where  data_dt<='"
				+ data_dt + "'  group by objkey,stcrkey ) a"
				+ " where a.mindate<='" + befordate + "'  or a.maxdate='"
				+ afterdate + "'";
		sqlList.add(insertsql);

		// 最小预警日期或最大预警日期达到
		sql = "update br21_alert_fact a  set status_cd='1'  where  exists(select 'x' from   br21_alert_fact_mid b"
				+ "  where b.objkey=a.objkey and b.stcrkey=a.stcrkey  and a.status_cd='0'  and  data_dt<='"
				+ data_dt + "' )";
		sqlList.add(sql);

		return sqlList;
	}

	/**
	 * 删除案例的数据
	 * 
	 */
	public ArrayList<String> delBR22_date(String data_dt,
			ArrayList<String> sqlList) throws Exception {

		// 删除案例客户表
		String sql = "delete from  br22_case_party  where data_dt='" + data_dt
				+ "'";
		sql = sql
				+ " and caseid in(select caseid from  br22_case  where datasource='1'  and  data_dt='"
				+ data_dt + "')";
		sqlList.add(sql);

		// 删除案例交易表
		sql = "delete from  br22_case_trans  where data_dt='" + data_dt + "'";
		sql = sql
				+ " and caseid in(select caseid from  br22_case  where datasource='1'  and  data_dt='"
				+ data_dt + "')";
		sqlList.add(sql);

		// 删除 案件关联账户表
		sql = "delete from  br22_case_acct_real  where data_dt='" + data_dt
				+ "'";
		sql = sql
				+ " and caseid in(select caseid from  br22_case  where datasource='1'  and  data_dt='"
				+ data_dt + "')";
		sqlList.add(sql);

		// 删除案件预警表
		sql = "delete from  br22_case_alert  where data_dt='" + data_dt + "'";
		sqlList.add(sql);

		// 删除案例表
		sql = "delete from  br22_case where data_dt='" + data_dt
				+ "'  and datasource='1' ";
		sqlList.add(sql);

		return sqlList;
	}

	/**
	 * 插入案例的数据
	 * 
	 */
	public ArrayList<String> insertBR22_date(String data_dt, String toorg,
			ArrayList<String> sqlList) throws Exception {

		String nowdate = data_dt.replaceAll("-", "");
		// 插入案件主表
		String sql = "INSERT INTO BR22_CASE(CASEID,DATASOURCE,OBJKEY,FEATURECODETYPE,FEATURECODE,PARTY_ID,MODEL_NO,PARTY_CLASS_CD,DATA_DT,STATUS_CD,MSG_TYPE_CD,VALIDATE_ID,TOORG)"
				+ " SELECT  DISTINCT  "
				+ nowdate
				+ "||OBJKEY||MODELKEY||STCRKEY,'1',OBJKEY, "
				+ " CASE WHEN STCRKEY IN('1001','1002') THEN '1'  WHEN STCRKEY='2001' THEN '2' ELSE '3' END,"
				+ " STCRKEY,PARTY_ID,MODELKEY,PARTY_CLASS_CD,'"
				+ data_dt
				+ "','0','N','0','"
				+ toorg
				+ "'"
				+ " FROM BR21_ALERT_FACT  WHERE STATUS_CD='1'  and  data_dt<='"
				+ data_dt + "'";
		sqlList.add(sql);

		// 插入案件预警表
		sql = " INSERT INTO BR22_CASE_ALERT(CASEID,ALERTKEY,DATA_DT)"
				+ " SELECT   DISTINCT B.CASEID,A.ALERTKEY,B.DATA_DT"
				+ " FROM BR21_ALERT_PARTY A,BR22_CASE B "
				+ " WHERE A.OBJKEY=B.OBJKEY AND A.STCRKEY=B.FEATURECODE  AND B.DATASOURCE='1'   and  a.data_dt<='"
				+ data_dt + "' AND B.DATA_DT='" + data_dt + "'";
		sqlList.add(sql);

		// 插入案件客户表
		sql = " INSERT INTO BR22_CASE_PARTY(PA_NO,CASEID,EVENTKEY,FEATURECODE,PARTY_ID,CARDNUMBER,ACCT_NUM,DATA_DT)"
				+ " SELECT  'S'||"
				+ dbfunc.getSeq("SEQ_BR22_CASE_PARTY")
				+ ",C.CASEID,C.EVENTKEY,C.STCRKEY,C.PARTY_ID ,C.CARDNUMBER,C.ACCT_NUM,C.DATA_DT"
				+ " FROM(SELECT   DISTINCT B.CASEID,A.EVENTKEY,A.STCRKEY,A.PARTY_ID ,A.CARDNUMBER,A.ACCT_NUM,B.DATA_DT"
				+ " FROM BR21_ALERT_PARTY A,BR22_CASE B "
				+ " WHERE A.OBJKEY=B.OBJKEY AND A.STCRKEY=B.FEATURECODE  AND B.DATASOURCE='1'   and  a.data_dt<='"
				+ data_dt + "' AND B.DATA_DT='" + data_dt + "') C";
		sqlList.add(sql);

		// 插入案件交易表
		sql = " INSERT INTO BR22_CASE_TRANS(CASEID,TRANS_KEY,EVENTKEY,FEATURECODE,PARTY_ID,PARTY_CLASS_CD,TRANS_AMT,CARDNUMBER,ACCT_NUM,DATA_DT)"
				+ " SELECT  DISTINCT B.CASEID,A.TRANS_KEY,A.EVENT_KEY,A.STCRKEY,A.PARTY_ID ,A.PARTY_CLASS_CD,A.TRANS_AMT,A.CARDNUMBER,A.ACCT_NUM,B.DATA_DT"
				+ " FROM BR21_ALERT_TRANS A,BR22_CASE B "
				+ " WHERE A.OBJKEY=B.OBJKEY AND A.STCRKEY=B.FEATURECODE   AND B.DATASOURCE='1'   and  a.data_dt<='"
				+ data_dt + "'  AND B.DATA_DT='" + data_dt + "'";
		sqlList.add(sql);

		// 案件关联账户
		sql = "INSERT INTO BR22_CASE_ACCT_REAL(CASEID,PARTY_ID,ACCT_NUM,ACCT_NUM_SUB,DATA_DT)"
				+ " SELECT  distinct B.CASEID,B.PARTY_ID,B.OBJKEY,A.CTAC,'"
				+ data_dt
				+ "'"
				+ " FROM BB11_ACCT A ,BR22_CASE  B"
				+ " WHERE A.PARTY_ID=B.PARTY_ID AND A.CTAC!=B.OBJKEY "
				+ " AND B.FEATURECODETYPE='2'  AND B.DATASOURCE = '1' AND B.DATA_DT = '"
				+ data_dt + "' ";
		// sqlList.add(sql);

		return sqlList;
	}

	/**
	 * 插入案例下客户交易卡的数据
	 * 
	 */
	public ArrayList<String> insertBR21_trans_data(String data_dt,
			ArrayList<String> sqlList) throws Exception {

		String delsql = dbfunc.getDeleteSql("BR22_TRANS_MID");
		sqlList.add(delsql);
		// 查询不在bb21_trans表的交易放入中间表
		String sql = " insert into br22_trans_mid(ticd)"
				+ " select   distinct a.trans_key  from br22_case_trans a left join bb21_trans b on b.trans_key=a.trans_key"
				+ "  where  A.DATA_DT='" + data_dt
				+ "' AND b.trans_key is null";
		sqlList.add(sql);
		// 1.插入bb21_trans
		sql = " INSERT INTO BB21_TRANS(TRANS_KEY,PARTY_ID,PARTY_CLASS_CD,ACCOUNTNAME,ACCOUNTNUMBER,CARDNUMBER,SUBACCOUNTSERIAL,SUBACCOUNTNUMBER,TRANSACTIONTYPE,BORROWINGSIGNS,CURRENCY,TRANSAMT,ACCOUNTBALANCE,TX_DT,TRANSACTIONTIME,TRANSACTIONSERIAL,OPP_NAME,OPP_ACCTNUM,OPP_CARDNUM,OPP_CREDNUM,OPP_ACCTBALANCE,OPP_OPENBANK_ID,OPP_OPENBANK,TRANSREMARK,ORGANKEY,LOGNUMBER,SUMMONSNUMBER,VOUCHERTYPE,VOUCHERCODE,CASHMARK,TERMINALNUMBER,TRANSACTIONSTATUS,TRANSACTIONADDRESS,MERCHANTNAME,MERCHANTCODE,IPADRESS,MAC,TELLERCODE,REMARK,CHANNEL,TSTP_TYPE_F_CD,VALIDATE_ID,STATE_CD)"
				+ " SELECT  A.TICD                            "
				+ "  ,PARTY_ID                       "
				+ "  ,PARTY_CLASS_CD                 "
				+ "  ,PARTY_NAME                     "
				+ "  ,ACCT_NUM                           "
				+ "  ,CARDNUMBER                     "
				+ "  ,CTAC_SEQ                       "
				+ "  ,CTAC                       "
				+ "  ,CASE WHEN CB_TX_CD IS NOT NULL THEN (SELECT    TARGETVAL FROM BR24_CODE_CHANGE D WHERE D.COLCODE='transactionType' AND D.SOURCEVAL=A.CB_TX_CD) ELSE ''  END AS      transactionType              "
				+ "  , CASE WHEN DEBIT_CREDIT ='D' THEN '0' ELSE '1' END      "
				+ "  ,CRTP                           "
				+ "  ,CRAT                           "
				+ "  ,AMT_VAL                        "
				+ "  ,TSTM                           "
				+ "  ,DT_TIME                        "
				+ "  ,TX_NO                          "
				+ "  ,TCNM                           "
				+ "  ,TCAC                           "
				+ "  ,OPPONENTCARDNUMBER             "
				+ "  ,TCID                           "
				+ "  ,OPPONENTACCOUNTBALANCE         "
				+ "  ,CFIC                           "
				+ "  ,CFIN                           "
				+ "  ,DES                            "
				+ "  ,ORGANKEY                       "
				+ "  ,LOGNUMBER                      "
				+ "  ,VOUCHER_NO                     "
				+ "  ,CARD_TYPE                      "
				+ "  ,CARD_NUM                       "
				+ ", CASE WHEN CASH_IND='00' THEN '01' ELSE '00' END                         "
				+ ",TERMINALNUMBER                   "
				+ ",TRANSACTIONSTATUS                "
				+ ",CASE WHEN TRCD_AREA  IS NULL AND TRCD_COUNTRY IS NOT NULL THEN (SELECT COUNTRYCSNAME   FROM  BB13_PBC_COUNTRY  C WHERE C.COUNTRYEKEY=A.TRCD_COUNTRY)                   "
				+ "           WHEN  TRCD_AREA IS NOT NULL  THEN (SELECT  PBC_AREANAME FROM BB13_PBC_AREA  C WHERE C.PBC_AREAKEY=A.TRCD_AREA)   ELSE NULL  END AS AREA"
				+ ",MERCHANTNAME                     "
				+ ",MERCH_NO                         "
				+ ",TRAN_IP                          "
				+ ",MAC                              "
				+ ",TELLER                           "
				+ ",TEMP1                            "
				+ ",CHANNEL                          "
				+ ",TSTP_TYPE_F_CD ,'2','1'                  "
				+ " FROM  BB11_TRANS A , BR22_TRANS_MID B"
				+ " WHERE A.TICD=B.TICD";

		sqlList.add(sql);

		return sqlList;
	}

	public ArrayList<String> insertBR21_party_data(String data_dt,
			ArrayList<String> sqlList) throws Exception {

		String delsql = dbfunc.getDeleteSql("BR22_TRANS_MID");
		sqlList.add(delsql);
		// 查询不在bb21_PARTY表的交易放入中间表
		String sql = " INSERT INTO BR22_TRANS_MID(TICD)"
				+ " SELECT   DISTINCT  A.PARTY_ID  FROM BR22_CASE_PARTY A LEFT JOIN BB21_PARTY B ON B.PARTY_ID=A.PARTY_ID"
				+ "  WHERE A.DATA_DT='" + data_dt + "' AND  B.PARTY_ID IS NULL";
		sqlList.add(sql);
		// 1.插入bb21_PARTY
		sql = " INSERT INTO BB21_PARTY(PARTY_ID,SUBJECTTYPE,SUBJECTNAME,CITP,CTID,TELNUMBER,RESIDENTADDR,RESIDENTTELNUM,WORKCOMPNAME,WORKADDRESS,WORKTELNUMBER,EMAILADDRESS,ORGANKEY,VALIDATE_ID)"
				+ "  SELECT  A.PARTY_ID           "
				// +" ,CASE WHEN PARTY_CLASS_CD='I' THEN '1' ELSE '2' END AS   SUBJECTTYPE  "
				+ " ,PARTY_CLASS_CD AS   SUBJECTTYPE  "
				+ " ,CTNM               "
				+ ",(SELECT    TARGETVAL FROM BR24_CODE_CHANGE D WHERE D.COLCODE='accountCredentialType' AND D.SOURCEVAL=A.CARD_TYPE_O) AS   CITP      " // 证件类型
				+ " ,CTID               "
				+ " ,CCTL_P             "
				+ " ,CTAR               "
				+ " ,CCTL_T             "
				+ " ,WORKCOMPANYNAME    "
				+ " ,WORKADDRESS        "
				+ " ,WORKTELNUMBER      "
				+ " ,CCEI_EMAIL      "
				+ " ,ORGANKEY  ,'2'         "
				+ " FROM  BB11_PARTY A , BR22_TRANS_MID B"
				+ " WHERE A.PARTY_ID=B.TICD";
		sqlList.add(sql);
		// 2.修改对公客户表的字段
		sql = " UPDATE  BB21_PARTY A SET (CRNM,CRIT,CRID,BUSILICENSENUM,STATETAXSERIAL,LOCALTAXSERIAL)"
				+ "=(  SELECT  CRNM           "
				+ ",(SELECT    TARGETVAL FROM BR24_CODE_CHANGE D WHERE D.COLCODE='accountCredentialType' AND D.SOURCEVAL=A.CRIT) AS   CRIT      " // 证件类型
				+ " ,CRID        "
				+ " ,BUSINESSLICENSENUMBER               "
				+ " ,STATETAXSERIAL             "
				+ " ,LOCALTAXSERIAL               "
				+ " FROM  BB11_PARTY_CORP  B"
				+ " WHERE A.PARTY_ID=B.PARTY_ID)"
				+ "     WHERE A.SUBJECTNAME='2' ";
		sqlList.add(sql);

		return sqlList;
	}

	public ArrayList<String> insertBR21_acct_data(String data_dt,
			ArrayList<String> sqlList) throws Exception {

		String delsql = dbfunc.getDeleteSql("BR22_TRANS_MID");
		sqlList.add(delsql);

		// 查询不在bb21_ACCT表的交易放入中间表
		String sql = " INSERT INTO BR22_TRANS_MID(TICD)"
				+ " SELECT   C.ACCT_NUM  FROM  "
				+ " ( SELECT   distinct A.ACCT_NUM  FROM BR22_CASE_PARTY A  WHERE  A.DATA_DT='"
				+ data_dt + "'  "
				// +"    UNION  SELECT    A.ACCT_NUM_SUB AS ACCT_NUM  FROM BR22_CASE_ACCT_REAL A  WHERE  A.DATA_DT='"+data_dt+"' "
				+ ")C  LEFT JOIN BB21_ACCT D ON D.ACCOUNTNUMBER=C.ACCT_NUM"
				+ "  WHERE D.ACCOUNTNUMBER IS NULL";
		sqlList.add(sql);
		sql = " INSERT INTO BB21_ACCT(ACCOUNTNUMBER,ACCOUNTSERIAL,ACCOUNTNAME,CARDNUMBER,PARTY_ID,ACCOUNTTYPE,ACCOUNTSTATUS,OPENDATE,CANCELDATE,ORGANKEY,CURRENCY,CASHREMIT,ACCOUNTBALANCE,AVAILABLEBALANCE,VALIDATE_ID )"
				+ "  SELECT ACCT_NUM,CTAC_SEQ,CTAC_NAME,CARD_NUM,PARTY_ID,"
				+ "  CASE WHEN TYPE_CD  IS NOT NULL THEN (SELECT    TARGETVAL FROM BR24_CODE_CHANGE D WHERE D.COLCODE='cclb' AND D.SOURCEVAL=A.TYPE_CD) ELSE ''  END AS  TYPE_CD "
				+ ",STATUS_CD,OATM,CATM,ORGANKEY,CURR_CD,CASH_CD,LAST_AMTVAL,AMTVAL,'2'   "
				+ " FROM  BB11_ID_DEPOSIT A , BR22_TRANS_MID B"
				+ " WHERE A.ACCT_NUM=B.TICD";
		sqlList.add(sql);

		// 1.插入对公bb21_ACCT
		sql = " INSERT INTO BB21_ACCT(ACCOUNTNUMBER,ACCOUNTSERIAL,ACCOUNTNAME,CARDNUMBER,PARTY_ID,ACCOUNTTYPE,ACCOUNTSTATUS,OPENDATE,CANCELDATE,ORGANKEY,CURRENCY,CASHREMIT,ACCOUNTBALANCE,AVAILABLEBALANCE,VALIDATE_ID )"
				+ "  SELECT ACCT_NUM,CTAC_SEQ,CTAC_NAME,CARD_NUM,PARTY_ID,TYPE_CD,STATUS_CD,OATM,CATM,ORGANKEY,CURR_CD,CASH_CD,LAST_AMTVAL,AMTVAL,'2'   "
				+ " FROM  BB11_DEPOSIT_C A , BR22_TRANS_MID B"
				+ " WHERE A.ACCT_NUM=B.TICD";
		sqlList.add(sql);

		return sqlList;
	}

	public ArrayList<String> insertBR21_CARD_data(String data_dt,
			ArrayList<String> sqlList) throws Exception {

		String delsql = dbfunc.getDeleteSql("BR22_TRANS_MID");
		sqlList.add(delsql);
		// 查询不在bb21_ACCT表的交易放入中间表
		String sql = " INSERT INTO BR22_TRANS_MID(TICD)"
				+ " SELECT   DISTINCT a.CARDNUMBER  FROM BR22_CASE_PARTY A LEFT JOIN BB21_CARD B ON B.CARDNUMBER=A.CARDNUMBER"
				+ "  WHERE A.DATA_DT='" + data_dt
				+ "' AND  B.CARDNUMBER IS NULL";
		sqlList.add(sql);
		// 1.插入BB21_CARD
		sql = " INSERT INTO BB21_CARD(CARDNUMBER,ACCOUNTNAME,CARD_TYPE,PARTY_ID,OPEN_ORGANKEY,OPENDATE,CANCELDATE,CANCEL_ORGANKEY,LASTTRANSDT,REMARK,OPER_NAME,OPER_CREDETYPE,OPER_CREDENUM,STATUS_CD,VALIDATE_ID )"
				+ "  SELECT  A.CARDNUMBER,ACCOUNTNAME,CARD_TYPE,PARTY_ID,OPEN_ORGANKEY,OPENDATE,CANCELDATE,CANCEL_ORGANKEY,LASTTRANSDT,REMARK,OPER_NAME"
				+ " ,(SELECT  CODE_RH FROM  BB13_ETL_CODE_MAP C  WHERE  C.CODE_SOU=A.OPER_CREDETYPE AND C.CODE_TYPE='DXZJ') AS   OPER_CREDETYPE, " // 证件类型
				+ "OPER_CREDENUM,STATUS_CD,'2'  "
				+ " FROM  BB11_CARD A , BR22_TRANS_MID B"
				+ " WHERE A.CARDNUMBER=B.TICD";
		sqlList.add(sql);

		return sqlList;
	}

	public ArrayList<String> UPDATEBR22_case(String data_dt,
			ArrayList<String> sqlList) throws Exception {

		// 1.修改客户相关的字段
		String sql = " UPDATE  BR22_CASE A SET (CASENAME,CTID,CITP,ORGKEY,PARTY_CLASS_CD)"
				+ "=(  SELECT  SUBJECTNAME,CTID,CITP,ORGANKEY , SUBJECTTYPE  "
				+ " FROM  BB21_PARTY  B"
				+ " WHERE A.PARTY_ID=B.PARTY_ID )"
				+ "   WHERE  A.DATA_DT='" + data_dt + "'  ";
		sqlList.add(sql);

		// 2.修改账户预警的案件的归属到账户机构
		sql = " UPDATE  BR22_CASE A SET (ORGKEY)"
				+ "=(  SELECT ORGANKEY           " + " FROM  BB21_ACCT  B"
				+ " WHERE A.OBJKEY=B.ACCOUNTNUMBER )"
				+ "   WHERE A.FEATURECODETYPE='2' AND  A.DATA_DT='" + data_dt
				+ "'  ";
		sqlList.add(sql);

		return sqlList;
	}

	public ArrayList<String> insertTaskFact3(String data_dt, String diffday,
			ArrayList<String> sqlList) throws Exception {

		String startdate = DtUtils.add(data_dt, 1,
				Integer.parseInt("-" + diffday));
		String sql = "     insert into MC22_TASK_FACT(taskkey,taskid,subtaskid,tasktype,datatime,freq,taskname,taskcmd) "
				+ "  select  'R'||"
				+ dbfunc.getSeq("SEQ_MC22_TAST_FACT")
				+ "||'_'||c.caseid,b.taskid,c.caseid,b.tasktype,'"
				+ data_dt
				+ "','1',b.taskname,b.taskcmd "
				+ "  from mc21_task_rela a, mc22_task b, br22_case c "
				+ "   where a.taskid = b.taskid  "
				+ "   and a.tx_code = c.featurecodetype and c.status_cd <6"
				+ "    and  c.DATA_DT <='" + startdate + "'  ";
		sqlList.add(sql);

		sql = "    update br22_case c set  c.status_cd ='6'"
				+ "  where c.status_cd <6" + "    and  c.DATA_DT <='"
				+ startdate + "'  ";
		sqlList.add(sql);

		return sqlList;
	}

}
