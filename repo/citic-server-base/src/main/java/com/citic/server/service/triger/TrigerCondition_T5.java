package com.citic.server.service.triger;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_task_status;
import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.mapper.MC00_task_statusMapper;

/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_T5 extends BaseTrigerCondition {

	public TrigerCondition_T5(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}

	/**
	 * T5：任务组完成触发：保证一组任务提前都完成 PARAM1=TASKGROUPID
	 * 参数说明：参数1：任务组编码
	 * @param datatime
	 * @param mC00_triger_sub
	 * @return
	 */
	public ArrayList runTrigerCondition() throws Exception{
		ArrayList tfList = new ArrayList();
		//
		if(this.getMC00_triger_sub().getParam1()==null || this.getMC00_triger_sub().getParam1().trim().equals("")){
			throw new Exception("参数1：任务组编码的参数没有设置！");
		}
		//一、取得计算任务编码
		String tgroupid = this.getMC00_triger_sub().getParam1();//参数1：代表任务编码
		
		//二、取得全部计算完成的任务（含子任务）：要分频度和数据源分别判断
		ArrayList freqList = this.getS2_result();//至少有日
		ArrayList dsList   = this.getS3_result();//可能为空
		
		Iterator fIter = freqList.iterator();
		while(fIter.hasNext()){
			String freq = (String)fIter.next();
			Iterator dIter = dsList.iterator();
			while(dIter.hasNext()){
				String dsid = (String)dIter.next();
				if(dsid.equals("1") || dsid.equals("0")){
					dsid = "";//不分数据源
				}
				//
				//1-取得完成的任务
				MC00_task_status mC00_task_status = new MC00_task_status();
				mC00_task_status.setDatatime(this.getDatatime());
				mC00_task_status.setTgroupid(tgroupid);
				mC00_task_status.setTaskid("");
				mC00_task_status.setFreq( freq );
				mC00_task_status.setDsid( dsid );
				//mC00_task_status.setCalstatus("1");计算错误的也要取出来
				
				MC00_task_statusMapper mC00_task_statusMapper = (MC00_task_statusMapper)this.getAc().getBean("MC00_task_statusMapper");
				ArrayList taskStatusList = mC00_task_statusMapper.getMC00_task_statusListForAllTask(mC00_task_status);
				
				//三、判断本频度，本数据源下得任务是否已经被执行过，或者有执行失败的
				boolean canTriger = true;
				if(taskStatusList.size()>0){
					Iterator iter = taskStatusList.iterator();
					while(iter.hasNext()){
						MC00_task_status ts = (MC00_task_status)iter.next();
						if(ts.getCalstatus()==null || !ts.getCalstatus().equals("1")){//子任务有未完成的
							canTriger = false;
							break;
						}
						
					}
				}else{
					canTriger = false;//子任务还都没有开始计算
				}
				if(canTriger){
					MC00_triger_fact mC00_triger_fact= new MC00_triger_fact();
					mC00_triger_fact.setTrigerid( this.getMC00_triger_sub().getTrigerid());
					mC00_triger_fact.setTrigercondid(this.getMC00_triger_sub().getTrigercondid());
					mC00_triger_fact.setDatatime(this.getDatatime());	
					mC00_triger_fact.setFreq(freq);
					mC00_triger_fact.setDsid(dsid);
					//添加触发结果
					tfList.add( mC00_triger_fact );
				}
				//
			}
		}
		
		return tfList;
	}
	
}