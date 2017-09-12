package com.citic.server.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;






import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationCFG;
import com.citic.server.SpringContextHolder;
import com.citic.server.service.sqlparse.comm.SqlStrUtils;


public class DbFuncUtils {

	private JdbcTemplate jdbcTemplate = null;
	public String dbtype = "oracle";
	private ApplicationContext ac;

	@Autowired
	SpringContextHolder springContextHolder;

	public DbFuncUtils(){

		ac = springContextHolder.getApplicationContext();

		ApplicationCFG applicationCFG = (ApplicationCFG) ac.getBean("applicationCFG");

		jdbcTemplate = (JdbcTemplate) ac.getBean(applicationCFG.getJdbctemplate_business());

 Connection conn = null;
		
		try{
			
			conn = jdbcTemplate.getDataSource().getConnection();
			
			DatabaseMetaData md = conn.getMetaData();

			String dbpName = md.getDatabaseProductName();

			if (dbpName.toLowerCase().indexOf("db2") > -1) {
				dbtype = "db2";
			}
			if (dbpName.toLowerCase().indexOf("postgresql") > -1) {
				dbtype = "Ddata";
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
			conn.close();
			}catch(Exception ee){
				
			}
		}

	}

	public String getDeleteSql(String tablename) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			//sql = "truncate table " + tablename;
			sql = "delete from " + tablename;
		} else {
			sql = "delete from " + tablename;
		}

		return sql;
	}

	public String rename_table(String tablename,String to_tablename) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			//sql = "truncate table " + tablename;
			sql = "rename  " + tablename +" to "+to_tablename;
		} else {
			sql = "rename  table " + tablename +" to "+to_tablename;
		}

		return sql;
	}
	
	public String create_table(String tablename ,String str ) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			//sql = "truncate table " + tablename;
			sql = "create table   " + tablename +" as  " +str;
		}	else if ("Ddata".equals(dbtype)) {
			//sql = "truncate table " + tablename;
			sql = "create table   " + tablename +" as  " +str;
		}  else {
			sql = " create table  " + tablename +" AS (  "+str+" ) data initially deferred refresh deferred";
		}

		return sql;
	}
	
	/**
	 * 临时表不要使用，可能会导致计算中间结果丢失
	 * @param tablename
	 * @return
	 * @throws Exception
	 */
	public String getTruncateSql(String tablename) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "truncate table " + tablename;
		} else if ("Ddata".equals(dbtype)) {
			sql = "delete from  " + tablename;
		} else {
			//db2 V9.7之后的版本支持
			sql = "truncate table " + tablename + " immediate";
		}

		return sql;
	}

	public String getuse_hash(String a,String b) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "/*+ use_hash("+a+", "+b+") */";
		} else if ("Ddata".equals(dbtype)) {
			sql = "/*+ use_hash("+a+", "+b+") */";
		} else {
			sql = "";
		}

		return sql;
	}

	public String dealInsterTable(String tablename) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = " into "+tablename+"  nologging ";
		} else if ("Ddata".equals(dbtype)) {
			sql = " into "+tablename;
		}else {
			sql = " into "+ tablename;
		}

		return sql;
	}


	public String dealParaInsterTable(String tablename) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = " into /*+ append parallel("+tablename+",8)*/ "+tablename+"  nologging ";
		} else if ("Ddata".equals(dbtype)) {
			sql = " into "+tablename;
		}else {
			sql = " into "+ tablename;
		}
		return sql;
	}
	
	public String lastDiffDay(String datatime,String day) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "  TO_CHAR(LAST_DAY(TO_DATE("+datatime+",'YYYY-MM-DD')+"+day+"),'YYYY-MM-DD')  ";
		} else if ("Ddata".equals(dbtype)) {
			sql = "  TO_CHAR(LAST_DAY(TO_DATE("+datatime+",'YYYY-MM-DD')+"+day+"),'YYYY-MM-DD')  ";
		}else {
			sql = "  TO_CHAR(LAST_DAY(TO_DATE("+datatime+",'YYYY-MM-DD')+"+day+"  days),'YYYY-MM-DD')  ";
		}
		return sql;
	}




	public String getSeq(String seqename) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			//sql = "truncate table " + tablename;
			sql =   seqename+".Nextval";
		} else {
			sql =   " Nextval  for " + seqename;
		}

		return sql;
	}
	public String getSeqStr(String seqename) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			//sql = "truncate table " + tablename;
			sql =   "select "+seqename+".Nextval" +" from dual ";
		} else {
			sql =  "select "+ " Nextval  for " + seqename +" from sysibm.sysdummy1 ";
		}

		return sql;
	}
	
	public String getStcr(String ordername,String valname) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "listagg("+valname+",',') within GROUP (ORDER BY "+ordername+") ";
		} else {
			sql = " rtrim(replace(replace(xml2clob(xmlagg(xmlelement(name a, "+valname+"||','))),'<A>',''),'</A>',''))    ";
		}

		return sql;
	}

	
	public String getYearCurrDiff(String colname) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "  EXTRACT(year from  sysdate ) - EXTRACT(year from to_date_x("+colname+",'yyyy-mm-dd')) ";
		}else if ("Ddata".equals(dbtype)) {
			sql = "  trunc(months_between(to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'),TO_DATE_X("+colname+",'YYYY-MM-DD'))/12) ";
		}  else {
			sql = " year(current date)-year(date("+colname+")) ";
		}


		return sql;
	}
	

	public String getRowToCell(String ordername,String valname,String splitStr) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "listagg("+valname+",'"+splitStr+"') within GROUP (ORDER BY "+ordername+") ";
		} else if ("Ddata".equals(dbtype)) {
			sql = "string_agg("+valname+",'"+splitStr+"') ";
		}else {
			sql = " rtrim(replace(replace(xml2clob(xmlagg(xmlelement(name a, "+valname+"||'"+splitStr+"'))),'<A>',''),'</A>',''))    ";
		}

		return sql;
	}

	public String getLeft( String valname) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = valname ;
		}else if ("Ddata".equals(dbtype)) {
			sql = valname ;
		} else {
			sql = " left("+valname+",length("+valname+")-1)  ";
		}
		return sql;
	}

	public String getcurrent_date(String days) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "  to_char( sysdate + " + days+" ,'yyyy-mm-dd' )";
		} else {
			sql = "to_char( current date  + to_number(  "+days+")  days ,'yyyy-mm-dd' )" ;
		}

		return sql;
	}

	public String getcurrent_date(String dateStr,String days) throws Exception {
		return getcurrent_date(dateStr, days,false);
	}

	public String getcurrent_date(String dateStr, String days, boolean isCalCurrDay) throws Exception {
		String sql = "";
		int cnt = isCalCurrDay ? 1 : 0;
		if ("oracle".equals(dbtype)) {
			sql = "  to_char( to_date('" + dateStr + "','yyyy-mm-dd') + " + days + " -" + cnt + " ,'yyyy-mm-dd' )";
		}//huangjj
		else if ("Ddata".equals(dbtype)) {
			sql ="to_char( to_date('" + dateStr + "','yyyy-mm-dd')  + (to_number(  " +  days + ") - " + cnt + ")   ,'yyyy-mm-dd' )";
		}
		else {
			sql = "to_char( to_date('" + dateStr + "','yyyy-mm-dd')  + (to_number(  " +  days + ") - " + cnt + ")  days ,'yyyy-mm-dd' )  ";
		}

		return sql;
	}


	public String rownum() throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "ROWNUM";
		} else {
			sql = "ROW_NUMBER() over()";
		}
		return sql;
	}

	public String rownum(String orderStr) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "ROWNUM";
		} else {
			sql = "ROW_NUMBER() over( "+orderStr+" )";
		}
		return sql;
	}
	public String lengthWithoutSpace(String str) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "length(trim(" + str + "))";
		} else {
			sql = "length(ltrim(rtrim(" + str + ")))";
		}
		return sql;
	}
	public String trim(String str) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "trim("+str+")";
		} else {
			sql =  "ltrim(rtrim(" + str + "))";
		}
		return sql;
	}
	public String fsubstr(String str, int start, int index) {
		String restr = "";
		restr = "substr(" + str + "," + start + "," + index + ")";
		return restr;
	}
	public String trimLastChr(String str) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql =  "substr("+str+",1,length("+str+")-1)";
		} else {
			sql =   "substr(" + str + ",1,length(" + str + ")-1)";
		}
		return sql;
	}
	public String shortDateTo8Char(String str) throws Exception {
		String sql = "";
		if(str!=null&&!str.equals("")){
	
	     	if ("oracle".equals(dbtype)) {
		    	sql =  "replace("+str+",'-','')";
		   }else if ("Ddata".equals(dbtype)) {
			   sql =  "replace("+str+",'-','')";
		   }  else {
			sql =  "replace(char(" + str + "),'-','')";
		  }
		}
		return sql;
	}
	 public String getTstm(String str) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql =  " REPLACE(REPLACE(REPLACE("+str+",'-'),' '),':') ";
		}else	if ("Ddata".equals(dbtype)) {
			sql =  " REPLACE(REPLACE(REPLACE("+str+",'-',''),' ',''),':','') ";
		}  else {
			sql =  " REPLACE( char(REPLACE(char( REPLACE(char("+str+"), '-','')),' ','')),':','')   ";
		}
		return sql;
	}
	public String to_float(String str) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql =  str;
		} else {
			sql =  "cast("+str+" as float)";
		}
		return sql;
	}
	public String deleteRepeatRecord(String tablename, String primarykey) {
		StringBuffer sql = new StringBuffer();
		if ("oracle".equals(dbtype)) {
			sql.append("Delete from ");
			sql.append(tablename + " a ");
			sql.append(" Where a.rowid > (Select min(rowid) from ");
			sql.append(tablename + " b where ");
			if (primarykey.indexOf(",") > 0) {
				String[] primarykeys = primarykey.split(",");

				for (int i = 0; i < primarykeys.length; i++) {
					if (i == 0) {
						sql.append(" a." + primarykeys[i] + "=b." + primarykeys[i]);
					} else {
						sql.append(" and a." + primarykeys[i] + "=b." + primarykeys[i]);
					}

				}

			} else {
				sql.append("  a." + primarykey + "=b." + primarykey);
			}

			sql.append(" )");

		} else {
			sql.append(" delete from (select row_number() over (partition by ");
			sql.append(primarykey);
			sql.append(" order by ");
			sql.append(primarykey);
			sql.append(" ) as rn ,a.* from ");
			sql.append(tablename + " a )");
			sql.append(" where rn>1 ");
		}
		return sql.toString();
	}

	public String to_Date(String str) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = "to_date(" + str+",'yyyy-mm-dd')";
		} else {
			sql = "date(" + str+")";
		}

		return sql;
	}

	public String getsys_date( ) throws Exception {
		String sql = "";
		if ("oracle".equals(dbtype)) {
			sql = " sysdate ";
		} else {
			sql = "  current timestamp  ";
		}

		return sql;
	}

	/**
	 * 转换为正确的truncate Sql，同时支持脏读，即db2 后自动加IMMEDIATE
	 * @param tmpSql
	 * @return
	 */
	public String toTruncateSql(String tmpSql) {
		if ("db2".equals(dbtype)) {
			String clrSql = SqlStrUtils.clearSql(tmpSql);
			if (StringUtils.indexOf(clrSql, "truncate ") > -1) {
				if (StringUtils.indexOf(clrSql, " immediate") == -1) {
					tmpSql = tmpSql + " IMMEDIATE";
				}
			}

			if (StringUtils.indexOf(clrSql, "select ") > -1) {
				if (StringUtils.indexOf(clrSql, "with ur") == -1) {
					tmpSql = tmpSql + " WITH UR";
				}
			}
		}
		return tmpSql;
	}
}
