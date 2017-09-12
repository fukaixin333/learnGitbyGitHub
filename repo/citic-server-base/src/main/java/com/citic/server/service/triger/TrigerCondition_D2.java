package com.citic.server.service.triger;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;

/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_D2 extends BaseTrigerCondition {

	public TrigerCondition_D2(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}

	/**
	 * D2：任务延时N天触发：任务在数据日期后第N天开始执行（N-param1=1、2、3），N=自然日；
	 * 参数说明：参数1：表示N数字
	 * @param datatime
	 * @param mC00_triger_sub
	 * @return
	 */
	public ArrayList runTrigerCondition() throws Exception{
		ArrayList tfList = new ArrayList();
		MC00_triger_fact mC00_triger_fact= new MC00_triger_fact();
		mC00_triger_fact.setTrigerid( this.getMC00_triger_sub().getTrigerid());
		mC00_triger_fact.setTrigercondid(this.getMC00_triger_sub().getTrigercondid());
		mC00_triger_fact.setDatatime(this.getDatatime());		
		mC00_triger_fact.setFreq("0");
		mC00_triger_fact.setDsid("0");
		
		int n = 0;
		
		if(this.getMC00_triger_sub().getParam1()==null || this.getMC00_triger_sub().getParam1().trim().equals("")){
			throw new Exception("参数1：延迟N天的参数值没有设置！");
		}
		try{
			n = new Integer(this.getMC00_triger_sub().getParam1());
		}catch(Exception e){
			throw new Exception("参数1：延迟N天的参数值必须为数字！"+e.getMessage());
		}
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = new DateTime(this.getDatatime());
		DateTime dt_now = new DateTime();
		
		Period p = new Period(dt,dt_now,PeriodType.days());//dt_now-dt

		int day = p.getDays();
		
		if(day>=n){
			//时间差 不足 设置参数的N天，可以计算
			tfList.add(mC00_triger_fact);
		}
		
		return tfList;
	}
	
}