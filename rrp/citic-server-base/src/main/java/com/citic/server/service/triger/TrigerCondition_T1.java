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

public class TrigerCondition_T1 extends BaseTrigerCondition {

	
	public TrigerCondition_T1(ApplicationContext _ac,String _datatime, MC00_triger_sub _mC00_triger_sub,ArrayList _currTksList) {
		super(_ac,_datatime, _mC00_triger_sub,_currTksList);
	}

	/**
	 * T1：任务完成触发：保证某个任务提前执行完毕；PARAM1=TASKID
	 * 参数说明：参数1：任务编码
	 * 通过同触发器中,频度和数据源的配置，分别判断：如果未配置，默认为日+全部数据源
	 * @param datatime
	 * @param mC00_triger_sub
	 * @return
	 */
	public ArrayList runTrigerCondition() throws Exception{
		ArrayList tfList = new ArrayList();
		//
		if(this.getMC00_triger_sub().getParam1()==null || this.getMC00_triger_sub().getParam1().trim().equals("")){
			throw new Exception("参数1：任务编码的参数没有设置！");
		}
		//一、取得计算任务编码
		//被依赖的任务编码：例如，301.依赖201，本处为：TK_AML201
		String taskid = this.getMC00_triger_sub().getParam1();//参数1：代表任务编码
		
		//二、取得全部计算完成的任务（含子任务）：要分频度和数据源分别判断
		ArrayList freqList = this.getS2_result();//至少有日
		ArrayList dsList   = this.getS3_result();//可能为空
		
		//按频度循环判断
		Iterator fIter = freqList.iterator();
		while(fIter.hasNext()){
			String freq = (String)fIter.next();
			
			//按数据源循环判断
			Iterator dIter = dsList.iterator();
			while(dIter.hasNext()){
				String dsid = (String)dIter.next();
				if(dsid.equals("1") || dsid.equals("0")){
					dsid = "0";//不分数据源
				}
				
				
				//是否有任务执行过：如果没有，说明任务还没有被设置，是不能触发的
				boolean isHaveTasked = false;
				
				//有任务被执行，需要判断任务状态
				boolean isStatusFinished = true;
				
				//
				//1-取得完成的任务
				
				//作为初始化参数已经传入
				Iterator cIter = this.getCurrTksList().iterator();
				
				while(cIter.hasNext()){
					MC00_task_status dto = (MC00_task_status)cIter.next();
					if(   dto.getDatatime().equals( this.getDatatime() )
					   && dto.getTaskid().equals( taskid )	
					  // && dto.getFreq().equals(freq) //所有频度，所有层级都要计算完毕才可以
							){
						isHaveTasked = true;
						if(dto.getCalstatus()==null || !dto.getCalstatus().equals("1")){//任务执行状态表：有任务未执行完成
							isStatusFinished = false;
							break;
						}
						
						
					}
				}
				
				//三、判断本频度，本数据源下得任务是否已经被执行过，或者有执行失败的
				boolean canTriger = true;
				if( isHaveTasked ){
					canTriger = isStatusFinished;
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