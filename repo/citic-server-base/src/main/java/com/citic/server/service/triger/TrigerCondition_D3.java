package com.citic.server.service.triger;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.service.UtilsService;

/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_D3 extends BaseTrigerCondition {

	public TrigerCondition_D3(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}

	/**
	 * D3：任务延时N天触发：任务在数据日期后第N天开始执行（N-param1=1、2、3），N=工作日；
	 * 参数说明：参数1：表示N数字
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
		while(n>0){
			
			if(!this.isWorkDay()){
				//节假日不计数
				continue;
			}else{
				//数据日期+1工作日；
				dt = dt.plusDays(1);
			}
			n--;
		}
		//比较数据日期和当前日期大小，判断是否可以计算
		//boolean b1 = dt.isAfterNow();  
		//boolean b2 = dt.isBeforeNow();  
		//boolean b3 = dt.isEqualNow(); 
		
		if(!dt.isAfterNow()){
			tfList.add(mC00_triger_fact);
		}
		
		return tfList;
	}
	
}