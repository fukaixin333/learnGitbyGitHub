package com.citic.server.service.triger;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.utils.DtUtils;

/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_D4 extends BaseTrigerCondition {

	public TrigerCondition_D4(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}
	
	/**
	 * D4：当日处理的时间段，PARAM1=开始事件；PARAM2=结束事件；在指定时间段内可以执行
	 * 参数说明：
	 * 参数1：允许开始计算开始时间 ： hh:mi:ss （24H）
	 * 参数2：允许开始计算结束时间 ： hh:mi:ss （24H）
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
		//
		if(this.getMC00_triger_sub().getParam1()==null || this.getMC00_triger_sub().getParam1().trim().equals("")){
			throw new Exception("参数1：允许开始计算的时间参数没有设置！");
		}
		if(this.getMC00_triger_sub().getParam2()==null || this.getMC00_triger_sub().getParam2().trim().equals("")){
			throw new Exception("参数2：允许开始计算的时间参数没有设置！");
		}
		if(this.getMC00_triger_sub().getParam1().length()!=8){
			throw new Exception("参数1：允许开始计算的时间参数格式有误！");
		}
		if(this.getMC00_triger_sub().getParam2().length()!=8){
			throw new Exception("参数2：允许开始计算的时间参数格式有误！");
		}
		
		String begin_hour = this.getMC00_triger_sub().getParam1().substring(0, 2);
		System.out.println("begin_hour:" + begin_hour);
		int begin_hour_i = Integer.parseInt(begin_hour);
		if(begin_hour_i < 0 || begin_hour_i > 23) {
			throw new Exception("参数1：允许开始计算的时间参数格式有误！");
		}
		
		String begin_min = this.getMC00_triger_sub().getParam1().substring(3, 5);
		System.out.println("begin_min:" + begin_min);
		int begin_min_i = Integer.parseInt(begin_min);
		if (begin_min_i < 0 || begin_min_i > 59) {
			throw new Exception("参数1：允许开始计算的时间参数格式有误！");
		}
		
		String begin_s = this.getMC00_triger_sub().getParam1().substring(6);
		System.out.println("begin_s:" + begin_s);
		int begin_s_i = Integer.parseInt(begin_s);
		if (begin_s_i < 0 || begin_s_i > 59) {
			throw new Exception("参数1：允许开始计算的时间参数格式有误！");
		}
		
		String end_hour = this.getMC00_triger_sub().getParam2().substring(0, 2);
		System.out.println("end_hour:" + end_hour);
		int end_hour_i = Integer.parseInt(end_hour);
		if(end_hour_i < 0 || begin_hour_i > 23) {
			throw new Exception("参数2：允许结束计算的时间参数格式有误！");
		}
		
		String end_min = this.getMC00_triger_sub().getParam2().substring(3, 5);
		System.out.println("end_min:" + end_min);
		int end_min_i = Integer.parseInt(end_min);
		if (end_min_i < 0 || end_min_i > 59) {
			throw new Exception("参数2：允许结束计算的时间参数格式有误！");
		}
		
		String end_s = this.getMC00_triger_sub().getParam2().substring(6);
		System.out.println("end_s:" + end_s);
		int end_s_i = Integer.parseInt(end_s);
		if (end_s_i < 0 || end_s_i > 59) {
			throw new Exception("参数2：允许结束计算的时间参数格式有误！");
		}
		//DateTime dt = new DateTime("1974-10-21T20:10:21");
		DateTime dt_begin = null;
		DateTime dt_end = null;
		
		try{
			//dt_begin = new DateTime(this.getDatatime()+"T"+this.getMC00_triger_sub().getParam1());
			//modified by jdm in 20160122 日期不能使用数据日期，要使用当前日起，否则new DateTime().isAfter( dt_end )永远是true
			dt_begin = new DateTime(DtUtils.getNowDate()+"T"+this.getMC00_triger_sub().getParam1());
		}catch(Exception e){
			throw new Exception("参数1：允许开始计算的时间参数格式有误！");
		}
		try{
			//dt_end = new DateTime(this.getDatatime()+"T"+this.getMC00_triger_sub().getParam2());
			//modified by jdm in 20160122 日期不能使用数据日期，要使用当前日起，否则new DateTime().isAfter( dt_end )永远是true
			dt_end = new DateTime(DtUtils.getNowDate()+"T"+this.getMC00_triger_sub().getParam2());
			
			//如果开始时间大于结束时间则结束时间对应的日期+1
			if (dt_begin.isAfter(dt_end)) {
				String theNextDay = DtUtils.add(DtUtils.getNowDate(), 1, 1);
				//System.out.println(theNextDay);
				dt_end = new DateTime(theNextDay+"T"+this.getMC00_triger_sub().getParam2());

			}
		}catch(Exception e){
			throw new Exception("参数2：允许结束计算的时间参数格式有误！");
		}
		
		if(new DateTime().isBefore(dt_begin) || new DateTime().isAfter( dt_end )){
			//mC00_triger_fact = null;
			System.out.println("任务可以执行的开始时间：" + dt_begin);
			System.out.println("任务可以执行的结束时间：" + dt_end);
			System.out.println("任务不在可执行时间范围内");
		}else{
			tfList.add( mC00_triger_fact );
		}
		return tfList;
	}
	
}