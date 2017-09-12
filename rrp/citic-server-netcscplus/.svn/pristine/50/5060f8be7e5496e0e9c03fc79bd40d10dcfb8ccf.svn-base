package com.citic.server.dx.task.taskBo;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.citic.server.utils.DbFuncUtils;
import com.citic.server.utils.DtUtils;

public class ValidateBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ValidateBo.class);
	
	private 	DbFuncUtils dbfunc = new DbFuncUtils();
	
	/**
	 * 插入当天的验证交易中间表
	 * 
	 */
	
	public ArrayList<String> insertBR21_validate_trans_mid(String data_dt,ArrayList<String> sqlList) throws Exception {
		  
		  String delsql=dbfunc.getDeleteSql("BR21_VALIDATE_TRANS_MID");
		  sqlList.add(delsql);

		String   sql=" INSERT INTO BR21_VALIDATE_TRANS_MID(TRANS_KEY,RULETYPE,PARTY_ID,PARTY_CLASS_CD,ACCOUNTNAME,ACCOUNTNUMBER,CARDNUMBER,SUBACCOUNTSERIAL,SUBACCOUNTNUMBER,TRANSACTIONTYPE,BORROWINGSIGNS,CURRENCY,TRANSAMT,ACCOUNTBALANCE,TX_DT,TRANSACTIONTIME,TRANSACTIONSERIAL,OPP_NAME,OPP_ACCTNUM,OPP_CARDNUM,OPP_CREDNUM,OPP_ACCTBALANCE,OPP_OPENBANK_ID,OPP_OPENBANK,TRANSREMARK,ORGANKEY,LOGNUMBER,SUMMONSNUMBER,VOUCHERTYPE,VOUCHERCODE,CASHMARK,TERMINALNUMBER,TRANSACTIONSTATUS,TRANSACTIONADDRESS,MERCHANTNAME,MERCHANTCODE,IPADRESS,MAC,TELLERCODE,REMARK,CHANNEL,TSTP_TYPE_F_CD)"
            +" SELECT DISTINCT  A.TRANS_KEY, CASE WHEN  B.FEATURECODE='3001' THEN  '3001' ELSE '1'  END ,A.PARTY_ID,A.PARTY_CLASS_CD,A.ACCOUNTNAME,A.ACCOUNTNUMBER,A.CARDNUMBER,A.SUBACCOUNTSERIAL,A.SUBACCOUNTNUMBER,A.TRANSACTIONTYPE,A.BORROWINGSIGNS,A.CURRENCY,A.TRANSAMT,A.ACCOUNTBALANCE,A.TX_DT,A.TRANSACTIONTIME,A.TRANSACTIONSERIAL,A.OPP_NAME,A.OPP_ACCTNUM,A.OPP_CARDNUM,A.OPP_CREDNUM,A.OPP_ACCTBALANCE,A.OPP_OPENBANK_ID,A.OPP_OPENBANK,A.TRANSREMARK,A.ORGANKEY,A.LOGNUMBER,A.SUMMONSNUMBER,A.VOUCHERTYPE,A.VOUCHERCODE,A.CASHMARK,A.TERMINALNUMBER,A.TRANSACTIONSTATUS,A.TRANSACTIONADDRESS,A.MERCHANTNAME,A.MERCHANTCODE,A.IPADRESS,A.MAC,A.TELLERCODE,A.REMARK,A.CHANNEL,A.TSTP_TYPE_F_CD "              
            +" FROM  BB21_TRANS A , BR22_CASE_TRANS B"
            +" WHERE A.TRANS_KEY=B.TRANS_KEY"
            + "   AND A.VALIDATE_ID='2'   and  a.CASHMARK='00' ";
		  //AND B.DATA_DT='"+data_dt+"'";

		  sqlList.add(sql);
		  
		 

		  return sqlList;
	}
	
	/**
	 * 插入验证交易中间表
	 * 
	 */
	
	public ArrayList<String> insertBT21_validate_mid(String data_dt,ArrayList<String> sqlList) throws Exception {
		  
		  String delsql=dbfunc.getDeleteSql("BT21_VALIDATE_TRANS_MID");
		  sqlList.add(delsql);
       //账户交易验证
		String   sql=" INSERT INTO BT21_VALIDATE_TRANS_MID(TRANS_KEY,VALTYPE,VALIDATE_ID)"
            +" SELECT TRANS_KEY,RULETYPE,'0'"              
            +" FROM  BR21_VALIDATE_TRANS_MID"
            +" WHERE RULETYPE='1'  AND "
            + "   ( CURRENCY IS NULL"   // 币种
          //  +"  OR TRANSACTIONTYPE IS NULL"            //交易类型
            +"  OR ACCOUNTNAME  IS NULL "  //账户名
            +"  OR ACCOUNTNUMBER  IS NULL "     //账号
            +"  OR  OPP_ACCTNUM  IS NULL "  //对方账号
         //   +"   OR OPP_NAME  IS NULL"          //对方名称
            +"   OR OPP_OPENBANK  IS NULL)";  //对方行名
		  sqlList.add(sql);
		   //3001交易验证	  
		  sql=" INSERT INTO BT21_VALIDATE_TRANS_MID(TRANS_KEY,VALTYPE,VALIDATE_ID)"
		            +" SELECT TRANS_KEY,RULETYPE,'0'"              
		            +" FROM  BR21_VALIDATE_TRANS_MID"
		            +" WHERE RULETYPE='3001'  AND "
		            +"  (  OPP_ACCTNUM  IS NULL "  //对方账号
		         //   +"   OR OPP_NAME  IS NULL"          //对方名称
		            +"   OR OPP_OPENBANK  IS NULL)";  //对方行名
				  sqlList.add(sql);
		  		  
				  //修交易表
				  sql=" UPDATE  BB21_TRANS  A SET VALIDATE_ID='0' "
				            +"WHERE EXISTS(SELECT 'X' FROM BT21_VALIDATE_TRANS_MID  B"              
				            +"    WHERE B.TRANS_KEY=A.TRANS_KEY )  "
				            + "   AND A.VALIDATE_ID='2'  ";
				  sqlList.add(sql);
				  sql=" UPDATE  BB21_TRANS  A SET VALIDATE_ID='1' "
				            +"WHERE  A.VALIDATE_ID='2'  ";
				  sqlList.add(sql);
		  return sqlList;
	}
	

	/**
	 * 修改验证表
	 * @param data_dt
	 * @param sqlList
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> dealBT21_PARTY_A_C_mid(ArrayList<String> sqlList) throws Exception {
		  
		  String delsql=dbfunc.getDeleteSql("BT21_VALIDATE_PAC_MID");
		  sqlList.add(delsql);
     //客户验证
		String   sql=" INSERT INTO BT21_VALIDATE_PAC_MID(OBJKEY,VALTYPE,VALIDATE_ID)"
          +" SELECT PARTY_ID,'1','0'"              
          +" FROM  BB21_PARTY A  "
          + "   WHERE A.VALIDATE_ID='2'  "
          + " AND  (CITP IS NULL"   // 证件类型
          +"  OR CTID IS NULL) "     ;  //证件号码
		  sqlList.add(sql);
		  
		  //账户验证
			   sql=" INSERT INTO BT21_VALIDATE_PAC_MID(OBJKEY,VALTYPE,VALIDATE_ID)"
	            +" SELECT ACCOUNTNUMBER,'2','0'"              
	            +" FROM  BB21_ACCT A  "
	            + "   WHERE A.VALIDATE_ID='2'  "
	            + " AND  (ACCOUNTTYPE IS NULL"   // 账户类型
	            +"  OR OPENDATE IS NULL"       //开户日期
			   +"  OR OPENPLACE IS NULL) "     ;  //开户地点
			  sqlList.add(sql);
			  
			  //卡验证
			   sql=" INSERT INTO BT21_VALIDATE_PAC_MID(OBJKEY,VALTYPE,VALIDATE_ID)"
	            +" SELECT CARDNUMBER,'3','0'"              
	            +" FROM  BB21_CARD A  "
	            + "   WHERE A.VALIDATE_ID='2'  "
			   +"     AND  OPENDATE IS NULL"     ;  //开户日期
			  sqlList.add(sql);
			  
			  //修改客户表
			  sql=" UPDATE  BB21_PARTY  A SET VALIDATE_ID='0' "
			            +"WHERE EXISTS(SELECT 'X' FROM BT21_VALIDATE_PAC_MID  B"              
			            +"    WHERE B.OBJKEY=A.PARTY_ID AND B.VALTYPE='1')  "
			            + "   AND A.VALIDATE_ID='2'  ";
			  sqlList.add(sql);
			  sql=" UPDATE  BB21_PARTY  A SET VALIDATE_ID='1' "
			            +"WHERE  A.VALIDATE_ID='2'  ";
			  sqlList.add(sql);
			  
			  
			  //修改账户表
			  sql=" UPDATE  BB21_ACCT  A SET VALIDATE_ID='0' "
			            +"WHERE EXISTS(SELECT 'X' FROM BT21_VALIDATE_PAC_MID  B"              
			            +"    WHERE B.OBJKEY=A.ACCOUNTNUMBER AND B.VALTYPE='2')  "
			            + "   AND A.VALIDATE_ID='2'  ";
			  sqlList.add(sql);
			  sql=" UPDATE  BB21_ACCT  A SET VALIDATE_ID='1' "
			            +"WHERE  A.VALIDATE_ID='2'  ";
			  sqlList.add(sql);
			  
			  //修改卡表
			  sql=" UPDATE  BB21_CARD  A SET VALIDATE_ID='0' "
			            +"WHERE EXISTS(SELECT 'X' FROM BT21_VALIDATE_PAC_MID  B"              
			            +"    WHERE B.OBJKEY=A.CARDNUMBER AND B.VALTYPE='3')  "
			            + "   AND A.VALIDATE_ID='2'  ";
			  sqlList.add(sql);
			  sql=" UPDATE  BB21_CARD  A SET VALIDATE_ID='1' "
			            +"WHERE  A.VALIDATE_ID='2'  ";
			  sqlList.add(sql);
			  
		

		  return sqlList;
	}
	public ArrayList<String> updateBR22_case_validate(String data_dt,ArrayList<String> sqlList) throws Exception {
		//案件验证中间表
		  String delsql=dbfunc.getDeleteSql("BT21_VALIDATE_CASE_MID");
		  sqlList.add(delsql);
       //插入需要同步的案例
		String   sql=" INSERT INTO BT21_VALIDATE_CASE_MID(CASEID,VALIDATE_ID1,VALIDATE_ID2,VALIDATE_ID3,VALIDATE_ID4)"
          +" SELECT CASEID,'1','1','1','1'"              
          +" FROM  BR22_CASE A  "
          + "   WHERE A.VALIDATE_ID='0'  ";
          if(!data_dt.equals("")){
          sql=sql+ " AND A.DATA_DT='"+data_dt+"'";
          }
		  sqlList.add(sql);
		  //客户
		  sql=" UPDATE BT21_VALIDATE_CASE_MID  T SET VALIDATE_ID1='0'"
		          +" WHERE EXISTS(SELECT 'X' FROM BB21_PARTY A,BR22_CASE_PARTY B "              
		          +"                 WHERE A.PARTY_ID=B.PARTY_ID  ";
	        if(!data_dt.equals("")){
	            sql=sql+ " AND B.DATA_DT='"+data_dt+"'";
	            }
	          sql=sql+" AND A.VALIDATE_ID='0'  AND B.CASEID=T.CASEID)  ";
				  sqlList.add(sql);
		  
		 //账户
				  sql=" UPDATE BT21_VALIDATE_CASE_MID  T SET VALIDATE_ID2='0'"
				          +" WHERE EXISTS(SELECT 'X' FROM BB21_ACCT A,BR22_CASE_PARTY B "              
				          +"                 WHERE A.ACCOUNTNUMBER=B.ACCT_NUM  ";
				   if(!data_dt.equals("")){
			            sql=sql+ " AND B.DATA_DT='"+data_dt+"'";
			            }
			          sql=sql+" AND A.VALIDATE_ID='0'  AND B.CASEID=T.CASEID)  ";
				
					//	  sqlList.add(sql);
						  
		//卡
				sql=" UPDATE BT21_VALIDATE_CASE_MID  T SET VALIDATE_ID3='0'"
						      +" WHERE EXISTS(SELECT 'X' FROM BB21_CARD A,BR22_CASE_PARTY B "              
						     +"                 WHERE A.CARDNUMBER=B.CARDNUMBER " ;
				   if(!data_dt.equals("")){
			            sql=sql+ " AND B.DATA_DT='"+data_dt+"'";
			            }
			          sql=sql+" AND A.VALIDATE_ID='0'  AND B.CASEID=T.CASEID)  ";
								  sqlList.add(sql);
		//交易
			    sql=" UPDATE BT21_VALIDATE_CASE_MID  T SET VALIDATE_ID4='0'"
						  +" WHERE EXISTS(SELECT 'X' FROM BB21_TRANS A,BR22_CASE_TRANS B "              
						+"                 WHERE A.TRANS_KEY=B.TRANS_KEY  " ;
				   if(!data_dt.equals("")){
			            sql=sql+ " AND B.DATA_DT='"+data_dt+"'";
			            }
			          sql=sql+" AND A.VALIDATE_ID='0'  AND B.CASEID=T.CASEID)  ";
				  sqlList.add(sql);
				  
		//修改案件标志
			 sql=" UPDATE BR22_CASE  T SET VALIDATE_ID='1'"
							  +" WHERE EXISTS(SELECT 'X' FROM BT21_VALIDATE_CASE_MID A "              
							+"                 WHERE A.CASEID=T.CASEID AND A.VALIDATE_ID1='1'  AND A.VALIDATE_ID2='1' AND A.VALIDATE_ID3='1' AND VALIDATE_ID4='1' )  ";
			sqlList.add(sql);
		  
		 return sqlList;
		
	}
	
	public ArrayList<String> updateBR22_case_validateOK(String data_dt,ArrayList<String> sqlList) throws Exception {
		
		//修改案件标志
			 String sql=" UPDATE BR22_CASE  T SET VALIDATE_ID='1'"
							  +" WHERE  DATA_DT='"+data_dt+"'";
			sqlList.add(sql);
		  
		 return sqlList;
		
	}
	
	
	
}
