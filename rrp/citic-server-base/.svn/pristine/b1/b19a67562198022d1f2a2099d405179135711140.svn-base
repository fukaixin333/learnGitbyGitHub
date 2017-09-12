/**========================================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created Date : 2015年5月17日 下午11:19:09
 * Description: 
 * 
 *=========================================================
 */
package com.citic.server.service.sqlparse.oracle;

import java.text.ParseException;

import org.springframework.stereotype.Component;

import com.citic.server.service.sqlparse.DefaultSqlParseService;
import com.citic.server.utils.DtUtils;

/**
 * @author gaojianxin
 *
 */
@Component
public class OracleSqlParseService extends DefaultSqlParseService {

	/* (non-Javadoc)
	 * @see com.citic.server.service.sqlparse.DefaultSqlParseService#toStaticDbDate(java.lang.String)
	 */
	@Override
	protected String toStaticDbDate(String dataStr) throws ParseException {
		return "to_date('" + DtUtils.toStrDate(dataStr) + "','yyyy-mm-dd hh24:mi:ss')";
	}

	@Override
	public String getCalHolidaySql(String dateStr, String holidaytype, int amount) {
	    StringBuffer sb = new StringBuffer();
	    int rn = amount;
	    if(amount>0){
	        sb.append("select daykey from (  select daykey,row_number() over(order by daykey ) rn "); 
	        sb.append(" from mp01_holiday"); 
	        sb.append(" where daykey > '").append(dateStr).append("' ");
	    }else{
	        sb.append("select daykey from (  select daykey,row_number() over(order by daykey desc ) rn "); 
	        rn = amount * (-1);
	        sb.append(" from mp01_holiday"); 
	        sb.append(" where daykey < '").append(dateStr).append("' ");
	     }
       
        sb.append(" and holidaytype = '").append(holidaytype).append("' ");
        sb.append(" and isholiday = '0'");
        sb.append(") t where rn = ").append(rn);
        return sb.toString();
	}

}
