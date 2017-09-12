package com.citic.server.service.triger;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_task_status;
import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.mapper.MC00_task_statusMapper;
import com.citic.server.service.UtilsService;
/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_T3 extends BaseTrigerCondition {
	
	public TrigerCondition_T3(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}

	/**
	 * T3：前一日，全部任务完成触发：保证上一个数据日期 全部任务 已经完成；
	 * 参数说明：无
	 * @param datatime
	 * @param mC00_triger_sub
	 * @return
	 */
	public ArrayList runTrigerCondition() throws Exception{
		ArrayList tfList = new ArrayList();
		UtilsService utilsService = new UtilsService();
		
		//一、取得全部计算完成的任务（含子任务）：要分频度和数据源分别判断
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
				String predatatime = utilsService.getPreDatatime(this.getDatatime(), freq);
				//
				//1-取得完成的任务
				MC00_task_status mC00_task_status = new MC00_task_status();
				mC00_task_status.setDatatime(predatatime);//前一日
				mC00_task_status.setTaskid("");//全部任务
				mC00_task_status.setFreq( freq );
				mC00_task_status.setDsid( dsid );
				//mC00_task_status.setCalstatus("1");计算错误的也要取出来
				
				MC00_task_statusMapper mC00_task_statusMapper = (MC00_task_statusMapper)this.getAc().getBean("MC00_task_statusMapper");
				ArrayList taskStatusList = mC00_task_statusMapper.getMC00_task_statusListForAllTask(mC00_task_status);
				
				//二、判断本频度，本数据源下得任务是否已经被执行过，或者有执行失败的
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