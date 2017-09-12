package com.citic.server.service.triger;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;

/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_S1 extends BaseTrigerCondition {

	public TrigerCondition_S1(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}

	/**
	 * S1：数据源扫描触发：数据时间表 - 有待抽取状态日期（判断是否可以扫描）；
	 * 参数说明：无参数
	 * 无数据源属性，只与MC00_DATA_TIME有关，数据表内数据由主SERVER自动维护
	 * 对于本任务来说，既然带数据日期执行本任务，说明该数据日期可以进行数据源扫描
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
		
		tfList.add( mC00_triger_fact );
		
		return tfList;
	}
	
}