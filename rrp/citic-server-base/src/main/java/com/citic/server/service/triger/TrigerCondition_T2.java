package com.citic.server.service.triger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;

import com.citic.server.domain.MC00_task;
import com.citic.server.domain.MC00_task_status;
import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.mapper.MC00_task_statusMapper;
import com.citic.server.service.CacheService;
import com.citic.server.service.UtilsService;

/**
 * @author hubq
 * @version 1.0
 */

public class TrigerCondition_T2 extends BaseTrigerCondition {
	
	public TrigerCondition_T2(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}

	/**
	 * T2：前一日（或其他频度），同一个任务完成触发
	 * 任务完成触发：保证某个任务提前执行完毕；
	 * 参数说明：无
	 * 通过同触发器中,频度和数据源的配置，分别判断：如果未配置，默认为日+全部数据源
	 * @param datatime
	 * @param mC00_triger_sub
	 * @return
	 */
	public ArrayList runTrigerCondition() throws Exception{
		ArrayList tfList = new ArrayList();
		UtilsService utilsService = new UtilsService();
		//一、取得全部的计算任务编码：本触发器，触发几个任务，就需要同时判断几个
		CacheService cacheService= (CacheService)this.getAc().getBean("cacheService");
		HashMap taskMap = (HashMap)cacheService.getCache("taskbytrigerid",HashMap.class);
		String trigerid = this.getMC00_triger_sub().getTrigerid();
		MC00_task mC00_task = (MC00_task)taskMap.get(trigerid);

		String taskid = mC00_task.getTaskid();
		//
		
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
				String predatatime = utilsService.getPreDatatime(this.getDatatime(), freq);
				//
				//1-取得完成的任务
				MC00_task_status mC00_task_status = new MC00_task_status();
				mC00_task_status.setDatatime( predatatime );
				mC00_task_status.setTaskid(taskid);
				mC00_task_status.setFreq( freq );
				mC00_task_status.setDsid( dsid );
				//mC00_task_status.setCalstatus("1");
				/**
				 * 计算错误的也要取出来 : 
				 * 正确的，错误的和没开始计算（无记录）
				 * 
				 * 由于一个taskid 可能对应多个subtaskid，那么就需要对每个subtaskid进行判断
				 * 对于进入task_fact表，没有进入task_status表的情况也要判断为false
				 * 
				 * 实现：本处采用左外连接 将连个表关联，信息都取出来，统一判断
				 */
				
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