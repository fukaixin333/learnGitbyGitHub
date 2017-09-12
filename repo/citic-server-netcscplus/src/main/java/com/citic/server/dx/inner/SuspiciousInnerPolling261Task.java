package com.citic.server.dx.inner;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.citic.server.ApplicationProperties;
import com.citic.server.SpringContextHolder;
import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;

/**
 261名单推送核心
 */
@Component("suspiciousInnerPolling261Task")
public class SuspiciousInnerPolling261Task extends AbstractPollingTask {
	public static final Logger logger = LoggerFactory.getLogger(SuspiciousInnerPolling261Task.class);
	private JdbcTemplate jdbcTemplate = null;
	private MC00_common_Mapper common_Mapper;
	
	@Override
	public void executeAction() {
	
              boolean flag=this.executeProc();  //生成名单
              if(flag){
            	  
              }
	}
	
	public boolean executeProc(){
		CallableStatement calstmt_bsReport = null;
		Connection conn =null;

		try {
			ApplicationProperties applicationProperties = (ApplicationProperties) SpringContextHolder.getBean(ApplicationProperties.class);
			 jdbcTemplate = (JdbcTemplate) SpringContextHolder.getBean(applicationProperties.getJdbcTemplate_business());
			 			 
			conn = jdbcTemplate.getDataSource().getConnection();
			
			/***********add by cuihy 20161219 start*****************/
			logger.info("------------261名单任务开始-----------" );
			String sqlMc00DataTime = "select datatime from mc00_data_time where  flag='1' "
					+ "and datatime = (select  to_char(to_date( max(file_date),'YYYY-MM-DD')+1,'YYYY-MM-DD') from BR55_CREATE_HXFILE where file_flag = '1')";
			common_Mapper = (MC00_common_Mapper) SpringContextHolder.getApplicationContext().getBean("MC00_common_Mapper");
			String datatime = common_Mapper.execSql(sqlMc00DataTime);
		    //业务数据加载完成后跑名单数据
			if(datatime != null&&!datatime.equals("") ){
				String packagecal = "call P100_BR55_MD_HX(?,?,?)";
				  
			    calstmt_bsReport = conn.prepareCall(packagecal);
				calstmt_bsReport.setString(1, datatime);
				calstmt_bsReport.setString(2, "");
				calstmt_bsReport.setString(3, "");
				calstmt_bsReport.executeUpdate();
				logger.debug("存储过程调用＝＝" + packagecal);

				 String sdt = "";
			     DtUtils dtu = new DtUtils();
			     //前一日23:00-22:59:59秒取当天日期，方便核心提取数据文件
			     if( dtu.getHour(new Date()).equals("23")){
			    	 sdt = dtu.getTimeByGran(dtu.toStrDate(new Date()),1,3,"yyyy-MM-dd").replace("-", ""); 
			     }else{
			    	 sdt = dtu.toStrDate(new Date()).replace("-", ""); 
			     }
			     //文件存储路径
			     String filepath = common_Mapper.execSql("select vals from bb13_sys_para where code = '261_filepath' and flag = '1'");
			     //最后生成的文件名
			     String hxfilename = "";
			     String okfile = "";
			     String filename = common_Mapper.execSql("select  file_name from BR55_CREATE_HXFILE  where file_date = ( select max(file_date)  from BR55_CREATE_HXFILE )");
			     if(filename.substring(filename.indexOf(".")-11,filename.indexOf(".")-3).equals(sdt)){
			    	 int seq = Integer.parseInt(filename.substring(filename.indexOf(".")-1,filename.indexOf(".")))+1;
			    	 hxfilename = filepath+"BLACK_LIST_AUX_" + sdt+  "_0"+ seq +".txt";
			    	 okfile = filepath + "BLACK_LIST_AUX_" + sdt+  "_0"+ seq +".ok";
			     }else{
			    	 hxfilename = filepath+"BLACK_LIST_AUX_" + sdt+  "_01.txt";
			    	 okfile = filepath + "BLACK_LIST_AUX_" + sdt +  "_01.ok";
			     }
				 Statement stmt = conn.createStatement();
				 StrUtils str = new StrUtils();
		         String sql = "select status_cd, md_type,md_source,md_kind,cardtype,cardno, md_type_xz,md_xz_qsrq,md_type_qx,md_type_qxlx from bR55_MD_HX order by md_code asc";
		            ResultSet rs = stmt.executeQuery(sql);//
		            FileOutputStream fos = new FileOutputStream(hxfilename,true);
		            
		            logger.debug("创建文件＝＝" + hxfilename);
		            
		            PrintStream p = new PrintStream(fos);
		            while (rs.next()) {
		            	StringBuffer hxdate = new StringBuffer();
		            	hxdate.append( str.null2String(rs.getString(1) ));
		            	hxdate.append("|");
		            	hxdate.append( str.null2String(rs.getString(2) ));
		            	hxdate.append("|");
		            	hxdate.append( str.null2String(rs.getString(3) ));
		            	hxdate.append("|");
		            	hxdate.append( str.null2String(rs.getString(4) ));
		            	hxdate.append("|");
		            	hxdate.append( str.null2String(rs.getString(5) ));
		            	hxdate.append("|");
		            	hxdate.append( str.null2String(rs.getString(6) ));
		            	hxdate.append("|");
		            	hxdate.append( str.null2String(rs.getString(7) ));
		            	hxdate.append("|");
		            	hxdate.append( str.null2String(rs.getString(8) ));
		            	hxdate.append("|");
		            	hxdate.append( str.null2String(rs.getString(9) ));
		            	hxdate.append("|");
		            	hxdate.append( str.null2String(rs.getString(10) ));
		            	hxdate.append("|");
		                p.println(hxdate);
		            }
		            p.close();
		            fos.flush();
		            File f = new File(okfile);
		            if (!f.exists()) {
		            f.createNewFile();
		            }
		            logger.debug("创建标志文件＝＝" + okfile);
		            
		            String insertsql = " insert into BR55_CREATE_HXFILE (file_name, file_flag, file_date)values ('"+ hxfilename +"', '1','"+ datatime +"')";
		            String hxflag = common_Mapper.execSql(insertsql);		
		            logger.debug("插入BR55_CREATE_HXFILE表＝＝" + insertsql);
		         
			}
          
			logger.info("------------261名单任务结束-----------" );
		 /**********add by cuihy 2016-12-16 end **************/

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// 关闭
			if (calstmt_bsReport != null) {
				try {
					calstmt_bsReport.close();
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

	@Override
	protected String getTaskType() {
		return null;
	}

	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(Keys.INNER_POLLING_TASK_SUSPICIOUS_PERIO_261, "120");
	}
}
