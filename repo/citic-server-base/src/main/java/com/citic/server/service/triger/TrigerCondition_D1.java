package com.citic.server.service.triger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.citic.server.ApplicationCFG;
import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.service.UtilsService;

/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_D1 extends BaseTrigerCondition {

	public TrigerCondition_D1(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}

	/**
	 * 3：工作日触发：在节假日不触发计算，工作日执行；
	 * 参数说明：无
	 * @param datatime
	 * @param mC00_triger_sub
	 * @return
	 */
	public ArrayList runTrigerCondition() throws Exception{
		ArrayList tfList = new ArrayList();
		
		UtilsService utilsService= new UtilsService();
		
		MC00_triger_fact mC00_triger_fact= new MC00_triger_fact();
		mC00_triger_fact.setTrigerid( this.getMC00_triger_sub().getTrigerid());
		mC00_triger_fact.setTrigercondid(this.getMC00_triger_sub().getTrigercondid());
		mC00_triger_fact.setDatatime(this.getDatatime());		
		mC00_triger_fact.setFreq("0");
		mC00_triger_fact.setDsid("0");
		
		boolean isworkday = this.isWorkDay();
		
		if(isworkday){
			tfList.add(mC00_triger_fact);
		}
		
		return tfList;
	}
	
}