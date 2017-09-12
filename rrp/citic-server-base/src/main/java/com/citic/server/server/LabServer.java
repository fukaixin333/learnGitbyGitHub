package com.citic.server.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.citic.server.ApplicationCFG;
import com.citic.server.SpringContextHolder;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.domain.MC01_model;
import com.citic.server.domain.MC01_task;
import com.citic.server.domain.MC01_task_status;
import com.citic.server.mapper.MC00_data_timeMapper;
import com.citic.server.mapper.MC01_modelMapper;
import com.citic.server.mapper.MC01_taskMapper;
import com.citic.server.mapper.MC01_task_statusMapper;
import com.citic.server.service.CacheService;
import com.citic.server.service.task.BaseTask;
import com.citic.server.service.task.TaskFactory;
import com.citic.server.service.tasksplit.BaseSplit;
import com.citic.server.service.tasksplit.SplitFactory;
import com.citic.server.utils.DtUtils;

@Service
public class LabServer {
	
	private static final Logger logger = LoggerFactory.getLogger(LabServer.class);
	
	@Autowired
    private ApplicationContext ac;
	
	@Autowired
	SpringContextHolder springContextHolder;
	
	@Autowired
	MC01_modelMapper mC01_modelMapper;
	
	@Autowired
	MC01_taskMapper mC01_taskMapper;
	
	@Autowired
	MC01_task_statusMapper mC01_task_statusMapper;
	
	@Autowired
	MC00_data_timeMapper mC00_data_timeMapper;
	
	@Autowired
	CacheService cacheService;

	
	public LabServer(){
		
	}
	
	/**
	 *  任务执行
	 *  1、任务设置：扫描模型主表状态为未计算的任务，按照计算的数据周期，拆分到计算任务表；同时按照任务表，拆分子任务到执行任务表；
	 *    1）对于定时计算，必须到指定时间后，才会设置任务
	 *  2、任务执行：按照模型主表任务设定的时间先后顺序，以模型维度为主开始执行
	 *    1）取满足数据时间要求的计算任务出来，顺序执行：执行任务表任务
	 *    2）计算任务取得与mc00_data_time关联，保证实验室计算在批处理之后进行，可以按天推进
	 *  3、设置模型计算最终状态
	 *  
	 */
	public void cal(String serverid)  throws Exception{
		
		/**
		 * 实验室用的数据都是未发布的，缓存中可能不会有
		 * 保证重新加载
		 */
		cacheService.clearAllCache();
		
		//设置任务
		this.splitTask();
		
		//计算任务
		this.calTask(serverid);
		
		//设置完成状态（MC01_model)
		this.finishModel();
		
	}
	
	private void splitTask() throws Exception{
		
		ArrayList modelList = mC01_modelMapper.getMC01_modelListForSplit();//未计算的 重新计算的
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		

		Iterator iter = modelList.iterator();
		while(iter.hasNext()){
			MC01_model mc01_model = (MC01_model)iter.next();
			
			if(mc01_model.getFlag().equals("2")){//新计算
				//
			}
			else if(mc01_model.getFlag().equals("5")){//重新计算
				//对于重新计算的，先删除，再添加任务
				this.clearTask(mc01_model.getMtaskkey());
			}else{
				continue;
			}
			
			//1-即时，2-定时
			String calmode = mc01_model.getCalmodule();
			/**
			 * 如果是即时计算：马上进行任务的拆分，后续马上开始计算
			 * 如果是定时计算：需要判断是否到定时时间，到了就开始计算
			 */
			boolean isSuccess = false;
			try{
				
				if(calmode.equals("1")){
					
					isSuccess = this.splitTask(mc01_model);
					
				}else{
					//系统确定开始计算的时间
					
					String ststr = mc01_model.getStarttime()+":00";
					
					DateTime starttime = new DateTime( ststr.substring(0,10)+"T"+ststr.substring(11) );
					
					//判断时间,到指定时间才去设置任务，避免频繁扫描
					DateTime nowtime = new DateTime();
					
					if(nowtime.compareTo(starttime)>=0 ){
						
						isSuccess = this.splitTask(mc01_model);
						
					}
				}
				
				
			}catch(Exception e){
				isSuccess = false;
				e.printStackTrace();
			}finally{
				mc01_model.setCalbegin_dt(DtUtils.getNowTime());
				
				if(isSuccess){
					mc01_model.setFlag("3");
				}else{
					mc01_model.setFlag("0");
				}
				
				mC01_modelMapper.modifyMC01_model(mc01_model);
			}
			
		}
		
	}
	
	private void finishModel(){
		
		/**
		 * 1、模型处于正在计算状态 flag=3
		 * 2、所有的任务都计算完成 则模型完成 flag = 1
		 *   有任务失败，则模型失败 flag!=1
		 */
		ArrayList modelList = mC01_modelMapper.getMC01_modelListForCal();//正在计算的
		
		/**
		 * 按照模型循环计算
		 */
		Iterator iter = modelList.iterator();
		while(iter.hasNext()){
			MC01_model mc01_model = (MC01_model)iter.next();
			String flag="1";
			ArrayList tList = mC01_task_statusMapper.getMC01_task_statusListForFinished(mc01_model.getMtaskkey());
			Iterator tIter = tList.iterator();
			while(tIter.hasNext()){
				MC01_task_status mc01_task_status = (MC01_task_status)tIter.next();
				if(mc01_task_status.getFlag().equals("1")){
					flag = "1";
				}
				else if(mc01_task_status.getFlag().equals("0")){
					flag = "0";
					break;
				}else{
					flag = "3";
					break;
				}
				
			}
			
			if(flag.equals("1") || flag.equals("0")){
				
				mc01_model.setCalend_dt(DtUtils.getNowTime());
				mc01_model.setFlag(flag);
				mC01_modelMapper.modifyMC01_model(mc01_model);
				
			}
			
		}
		
		
	}
	
	private void calTask(String serverid) throws Exception{
		
		//MC00_data_timeMapper mc00_data_timeMapper = (MC00_data_timeMapper)this.getAc().getBean("MC00_data_timeMapper");
		
		String _maxdatatime = mC00_data_timeMapper.getDatatimeForMaxDt();
		if(_maxdatatime==null || _maxdatatime.equals(""))
			_maxdatatime="1900-01-01";
		DateTime maxdatatime = new DateTime( _maxdatatime );
		
		ArrayList modelList = mC01_modelMapper.getMC01_modelListForCal();//正在计算的
		
		/**
		 * 按照模型循环计算
		 */
		Iterator iter = modelList.iterator();
		while(iter.hasNext()){
			MC01_model mc01_model = (MC01_model)iter.next();
			
			try{
				LinkedHashMap dtMap = this.getTaskListForCal(mc01_model);
				boolean isSucc = true;
				/**
				 * 按照模型下的任务，循环计算 ： 按照时间和计算顺序排序
				 * 相同计算顺序的子任务，可以并行
				 */

				Iterator dtIter = dtMap.keySet().iterator();
				while(dtIter.hasNext()){
					String datatime = (String)dtIter.next();
					
					//hubq : 2016-09-21 : 在任务计算时与mc00_data_time 表进行比对，保证所计算的时间，反洗钱批处理已经完成
					//修订后，可以计算未来数据了（任务数据没到，任务计算会停止下来等待）：：任务设置还是一次设置进来
					//注意：tools-config-amlplus.xml 中 lab_calmodel_maxdate_length 需要设置最大一次设置任务的区间
					
					DateTime curr_datatime = new DateTime( datatime );
					if(curr_datatime.compareTo(maxdatatime) > 0){
						//数据还没到，不计算
						continue;
					}
					
					LinkedHashMap tkMap = (LinkedHashMap)dtMap.get(datatime);
					Iterator tkIter = tkMap.keySet().iterator();
					while(tkIter.hasNext()){
						String taskid = (String)tkIter.next();;
						ArrayList taskList = (ArrayList)tkMap.get(taskid);
						//此处一组任务可以并行执行：都是一个任务项下的多个子任务
						
						Iterator tIter = taskList.iterator();
						while(tIter.hasNext()){
							MC01_task_status mc01_task_status = (MC01_task_status)tIter.next();
							
							mc01_task_status.setServerid(serverid);
							
							isSucc = this.calTask(mc01_task_status);
							String errMsg = "模型:"+mc01_model.getModelkey()+"(主任务编码="+mc01_model.getMtaskkey()+")-"
									+ mc01_model.getModelname()
									+" 下计算任务："+mc01_task_status.getTaskid()
									+"-"+mc01_task_status.getSubtaskid()
									+"-"+mc01_task_status.getTaskname()+"计算失败!";
							logger.error(errMsg);
							if(!isSucc){
								throw new Exception(errMsg);
							}
						}
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	private boolean calTask(MC01_task_status mc01_task_status) throws Exception{
		boolean isSuccess = true;
		
		
		TaskFactory taskFactory = new TaskFactory();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");
		
		MC00_task_fact mC00_task_fact = this.revertMC01ToMC00(mc01_task_status);
		
		try{
		
			mc01_task_status.setCalbegin_dt( fmt.print( new DateTime() ) );
			
			BaseTask baseTask = taskFactory.getInstance(ac,mC00_task_fact);
			
			baseTask.run();
			
			mc01_task_status.setFlag("1");
			
		}catch(Exception e){
			isSuccess = false;
			e.printStackTrace();
			mc01_task_status.setFlag("0");
			
		}finally{
			try{
				mc01_task_status.setCalend_dt( fmt.print( new DateTime() ) );
				mC01_task_statusMapper.modifyMC01_task_status(mc01_task_status);
			}catch(Exception e){
				isSuccess = false;
			}
		
		}
		return isSuccess;
	}
	
	/**
	 * 
	 * @param mc01_model
	 * @return
	 */
	private LinkedHashMap getTaskListForCal(MC01_model mc01_model){
		
		/**
		 * 系统有数据的才会被取出来计算
		 * 并且保证：一定在批处理结束（并且成功）后才会开始
		 */
		ArrayList tList = mC01_task_statusMapper.getMC01_task_statusListForCal(mc01_model.getMtaskkey());
		LinkedHashMap map = new LinkedHashMap();
		Iterator iter = tList.iterator();
		while(iter.hasNext()){
			MC01_task_status status =(MC01_task_status)iter.next();
			String datatime = status.getDatatime();
			
			LinkedHashMap taskMap = new LinkedHashMap();
			if(map.containsKey(datatime)){
				taskMap = (LinkedHashMap)map.get(datatime);
			}
			
			String taskid = status.getTaskid();
			ArrayList taskList = new ArrayList();
			if(taskMap.containsKey(taskid)){
				taskList = (ArrayList)taskMap.get(taskid);
			}
			
			taskList.add(status);
			
			taskMap.put(taskid, taskList);
			
			map.put(datatime, taskMap);
			
		}
		return map;
	}

	private boolean clearTask(String mtaskkey) throws Exception{
		
		mC01_task_statusMapper.deleteMC01_task_status(mtaskkey);
		
		return true;
	}
	
	private boolean splitTask(MC01_model mc01_model) throws Exception{
		boolean isSuccess = true;
		
		ArrayList taskList = (ArrayList)cacheService.getCache("mc01_task", ArrayList.class);
		
		String mindatatimeStr = (String)cacheService.getCache("mindatatime", String.class);
		
		//ArrayList taskList = mC01_taskMapper.getMC01_taskList();//改缓存
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		ApplicationCFG applicationCFG = (ApplicationCFG) ac.getBean("applicationCFG");
		int maxdateLength = new Integer( applicationCFG.getLab_calmodel_maxdate_length());
		
		/**
		 * 任务拆分：
		 * 1-首先考虑时间区间：按数据时间进行循环
		 * 2-考虑任务数量（mc01_task)
		 * 3-每个任务对应的子任务（tasksplit)
		 * 严格设置好执行顺序
		 */
		DateTime dataDt_begin = new DateTime( mc01_model.getDatabegin_dt() );
		DateTime dataDt_end = new DateTime( mc01_model.getDataend_dt() );
		DateTime data_dt = dataDt_begin;
		DateTime mindatatime = new DateTime( mindatatimeStr );
		
		
		if( dataDt_begin.isAfter( dataDt_end ) ){
			return false;
		}
		
		/**
		 * 按日设置任务
		 */
		while(data_dt.compareTo( dataDt_end ) <= 0){
			
			/**
			 * 系统最小数据时间之前的任务不设置，
			 * 没有数据，设置也计算不出结果
			 */
			if(data_dt.compareTo( mindatatime ) <0){
				
				data_dt = data_dt.plusDays(1);//下一天
				
				continue;
			}
			
			/**
			 * 按任务表设置任务
			 */
			
			String _modeltype = mc01_model.getModeltype();
			
			int i=1;
			Iterator iter = taskList.iterator();
			while(iter.hasNext()){
				MC01_task mc01_task = (MC01_task)iter.next();
				String taskkey = mc01_task.getTaskid();
				String tasksplit = mc01_task.getTasksplit();
				String splitparams = mc01_task.getSplitparams();
				
				//HUBQ 20151211 考虑实验室需要支持不同类型的模型计算：实时、准实时、联动等
				String modeltype = mc01_task.getModeltype();
				if(_modeltype!=null & !_modeltype.equals("")
				   & modeltype!=null & !modeltype.equals("")		
				   & !_modeltype.equals(modeltype)
						){
					continue;//任务不适用于本模型计算
				}
				
				if(!tasksplit.trim().equals("")){
					SplitFactory splitFactory = new SplitFactory();
					splitparams = "1,0,"+splitparams;//默认日力度，全部数据源
					BaseSplit baseSplit = splitFactory.getInstance(ac, mc01_model.getModelkey(),tasksplit,splitparams);
					
					ArrayList subtaskidList = baseSplit.getSubtaskidList();
					Iterator subIter = subtaskidList.iterator();
					while(subIter.hasNext()){
						String subtaskid = (String)subIter.next();
						
						MC01_task_status mc01_task_status = this.revertTaskToStatus(mc01_model, mc01_task,subtaskid, fmt.print(data_dt));
						
						mC01_task_statusMapper.insertMC01_task_status(mc01_task_status);
						
					}
					
				}else{
					String subtaskid = "0";//没有子任务
					
					MC01_task_status mc01_task_status = this.revertTaskToStatus(mc01_model, mc01_task,subtaskid, fmt.print(data_dt));
					
					mC01_task_statusMapper.insertMC01_task_status(mc01_task_status);
					
				}
				
			}
			
			/**
			 * 
			 */
			data_dt = data_dt.plusDays(1);//下一天
			
			i++;
			if(i > maxdateLength){
				logger.error("实验室模型计算时间设置过长！");
				break;
			}
			
		}
		
		
		return isSuccess;
		
	}
	
	private MC01_task_status revertTaskToStatus(MC01_model mc01_model, MC01_task mc01_task,String subtaskid, String data_dt) throws Exception{
		MC01_task_status mc01_task_status = new MC01_task_status();
		mc01_task_status.setMtaskkey( mc01_model.getMtaskkey() );
		mc01_task_status.setModeltype( mc01_model.getModeltype() );//可不用
		mc01_task_status.setDatatime( data_dt );
		mc01_task_status.setModelkey( mc01_model.getModelkey());
		mc01_task_status.setTaskid(mc01_task.getTaskid());
		mc01_task_status.setSubtaskid(subtaskid);
		mc01_task_status.setTaskname(mc01_task.getTaskname());
		mc01_task_status.setTaskseq( mc01_task.getTaskseq() );
		mc01_task_status.setFlag( "2" );
		mc01_task_status.setCalbegin_dt("");
		mc01_task_status.setCalend_dt("");
		mc01_task_status.setTaskcmd( mc01_task.getTaskcmd());
		
		return mc01_task_status;
	}
	
	private MC00_task_fact revertMC01ToMC00(MC01_task_status mc01_task_status) throws Exception{
		MC00_task_fact mc00_task_fact = new MC00_task_fact();
		
		mc00_task_fact.setTaskid( mc01_task_status.getTaskid() );
		mc00_task_fact.setDatatime( mc01_task_status.getDatatime() );
		mc00_task_fact.setSubtaskid( mc01_task_status.getSubtaskid() );
		mc00_task_fact.setTaskcmd( mc01_task_status.getTaskcmd() );
		
		mc00_task_fact.setTasksource("lab");
		
		return mc00_task_fact;
	}
	
	
}
