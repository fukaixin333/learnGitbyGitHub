package com.citic.server.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citic.server.ApplicationCFG;
import com.citic.server.domain.MC00_data_time;
import com.citic.server.domain.MC00_datasource;
import com.citic.server.domain.MC00_task;
import com.citic.server.domain.MC00_task_fact;
import com.citic.server.domain.MC00_task_status;
import com.citic.server.domain.MC00_triger_fact;
import com.citic.server.domain.MC00_triger_sub;
import com.citic.server.mapper.MC00_data_timeMapper;
import com.citic.server.mapper.MC00_task_factMapper;
import com.citic.server.mapper.MC00_task_statusMapper;
import com.citic.server.mapper.MC00_triger_factMapper;
import com.citic.server.service.tasksplit.BaseSplit;
import com.citic.server.service.tasksplit.SplitFactory;

/**
 * @author hubq
 *
 */

@Service
public class TaskService {
	private static final Logger logger = LoggerFactory
			.getLogger(TaskService.class);

	@Autowired
	CacheService cacheService;

	@Autowired
	TrigerService trigerService;
	
	@Autowired
	UtilsService utilsService;
	
	@Autowired
	MC00_triger_factMapper mC00_triger_factMapper;

	@Autowired
	MC00_task_factMapper mC00_task_factMapper;

	@Autowired
	MC00_task_statusMapper mC00_task_statusMapper;

	@Autowired
	MC00_data_timeMapper mC00_data_timeMapper;

	@Autowired
	private ApplicationContext ac;

	public TaskService() {

	}

	
	public boolean setTasks(String datatime,ArrayList trigeredList) throws Exception {
		boolean isSucess = true;

		ArrayList taskfactList = new ArrayList();
		
		
		/** 系统任务列表（拆分前） */
		HashMap taskMap = (HashMap) cacheService.getCache("taskbytrigerid",HashMap.class);
		
		/** 系统所有触发条件 */
		HashMap trigersubMap = (HashMap) cacheService.getCache("triger_subbytrigerid", HashMap.class);
		
		//trigerid
		ArrayList haveSetTrigeridList = mC00_task_factMapper.getTrigeridListByDatatime(datatime);
		
		/**
		 * 
		 */
		Iterator iter = trigeredList.iterator();
		while (iter.hasNext()) {
			HashMap trigeredMap = (HashMap) iter.next();
			String trigerid = (String)trigeredMap.get("trigerid");
			String freqs = (String)trigeredMap.get("freqs");
			String dsids = (String)trigeredMap.get("dsids");

			if(dsids.equals("")){//与数据源无关
				dsids="0";
			}
			
			//判断任务是否已经被设置过，如果设置过，需要跳过不重复设置；
			boolean haveSet = false;
			Iterator setIter = haveSetTrigeridList.iterator();
			while(setIter.hasNext()){
				String setTrigerid = (String)setIter.next();
				if(trigerid.equalsIgnoreCase(setTrigerid)){
					haveSet = true;
					break;
				}
			}
			if(haveSet){
				continue;
			}
			//=============================================;
			
			MC00_task mC00_task = (MC00_task)taskMap.get(trigerid);
			
			String[] _freqs = freqs.split(",");
			for(int i=0;i<_freqs.length;i++){
				String freq = _freqs[i];
				
				String[] _dsids = dsids.split(",");
				for(int j=0;j<_dsids.length;j++){
					String dsid = _dsids[j];
					
					taskfactList = this.getTaskFactListForSet(taskfactList,mC00_task ,freq , dsid, datatime);
					
					
					
				}
			}
			
		}

		
		// 已经取得所有的任务设置，信息，写入数据库
		if (taskfactList.size() > 0) {

			this.syncToDatabase(datatime, taskfactList);

		}

		return isSucess;
	}
	
	private ArrayList getTaskFactListForSet(ArrayList taskfactList,MC00_task mC00_task,String freq,String dsid,String datatime) throws Exception{
		
		
		String taskid = mC00_task.getTaskid();
		String tasksplit = mC00_task.getTasksplit();//是否拆分
		String splitparams=mC00_task.getSplitparams();//拆分传入参数
		//传入参数中，增加数据源编码信息
		if(splitparams.equals("")){
			splitparams = freq+","+dsid;
		}else{
			splitparams = freq+","+dsid +","+ splitparams;
		}
		
		if (tasksplit != null && !tasksplit.equals("") && !tasksplit.equals("0")) {

			// 确定需要按照标识拆分子任务；
			SplitFactory splitFactory = new SplitFactory();
			BaseSplit baseSplit = splitFactory.getInstance(ac, taskid,tasksplit,splitparams);
			
			ArrayList subtaskidList = baseSplit.getSubtaskidList();
			
			if(subtaskidList.size()==0){//子任务还没有被初始化设置，默认通过,否则取不到子任务，计算永远停留在此
				logger.error("子任务还没有被初始化定义：datatime="+datatime+";taskid="+taskid+";freq="+freq+";dsid="+dsid);
				String subtaskid = "0";
				taskfactList.add(this.getMC00_task_fact(mC00_task, datatime,subtaskid , freq , dsid));
				
				/**
				 * 多频度下,subtaskid=0,会造成task_fact表主键冲突，需要剔除； 
				 */
				
				
				
			}else{
			
				//
				Iterator sIter = subtaskidList.iterator();
				while (sIter.hasNext()) {
					String subtaskid = (String) sIter.next();
					
					taskfactList.add(this.getMC00_task_fact(mC00_task,datatime, subtaskid , freq , dsid));
					
				}
			
			}

		} else {//任务不需要拆分
			String subtaskid = "0";
			taskfactList.add(this.getMC00_task_fact(mC00_task, datatime,subtaskid , freq , dsid));
		}
		return taskfactList;
	}
	
	/**
	 * 批量写入数据库
	 * 
	 * @param datatime
	 * @param taskfactList
	 * @throws Exception
	 */
	private void syncToDatabase(String datatime, ArrayList _taskfactList)
			throws Exception {
		final ArrayList taskfactList = new ArrayList();
		
		HashMap haveSetMap = new HashMap();
		Iterator iter = _taskfactList.iterator();
		while(iter.hasNext()){
			MC00_task_fact tf = (MC00_task_fact)iter.next();
			
			String key = tf.getDatatime()+"-" +tf.getTaskid()+"-"+tf.getSubtaskid()+"-"+tf.getFreq();
			
			if(!haveSetMap.containsKey(key)){
				taskfactList.add(tf);
				haveSetMap.put(key, "");
			}else{
				//主键冲突，有两种可能
				//1-多频度下任务没有被设置
				//2-业务数据指标编码有冲突
			}
			
		}
		
		/**
		 * 添加新任务
		 */
		String sql = "insert into mC00_task_fact(taskid,subtaskid,serverid,datatime,trigerid,dsid,taskname,tgroupid,freq,taskcmd) values(?,?,?,?,?,?,?,?,?,?)";

		ApplicationCFG applicationCFG = (ApplicationCFG) ac.getBean("applicationCFG");
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ac
				.getBean(applicationCFG.getJdbctemplate_manager());

		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				MC00_task_fact tf = (MC00_task_fact) taskfactList.get(i);
				ps.setString(1, tf.getTaskid());
				ps.setString(2, tf.getSubtaskid());
				ps.setString(3, tf.getServerid());
				ps.setString(4, tf.getDatatime());
				ps.setString(5, tf.getTrigerid());
				ps.setString(6, tf.getDsid());
				ps.setString(7, tf.getTaskname());
				ps.setString(8, tf.getTgroupid());
				ps.setString(9, tf.getFreq());
				ps.setString(10, tf.getTaskcmd());
			}

			public int getBatchSize() {
				return taskfactList.size();
			}
		};

		int[] resut = jdbcTemplate.batchUpdate(sql, setter);

	}

	private MC00_task_fact getMC00_task_fact(MC00_task task, String datatime,
			String _subtaskid,String freq, String dsid) {

		String subtaskid = "";
		String tgroupid = "";
		
		if(_subtaskid.indexOf("--")>-1){
			String[] s = _subtaskid.split("--");
			subtaskid = s[0];
			tgroupid = s[1];
		}else{
			subtaskid = _subtaskid;
			tgroupid = "1";
		}
		
		MC00_task_fact domain = new MC00_task_fact();
		domain.setTaskid(task.getTaskid());
		domain.setSubtaskid(subtaskid);
		domain.setServerid("");// 此处不可以设置，必须等LockTask时再确定
		domain.setDatatime(datatime);
		domain.setTrigerid(task.getTrigerid());
		domain.setDsid(dsid);
		domain.setTaskname(task.getTaskname());
		//domain.setTgroupid(task.getTgroupid());
		domain.setTgroupid(tgroupid);//目前针对指标层次特殊处理，含义：按照分组，在频度项下，分别执行
		domain.setFreq(freq);
		domain.setTaskcmd(task.getTaskcmd());
		
		return domain;
	}

	/**
	 * 系统启动后： 1、需要把本Server的锁定状态复位为未计算； 2、把锁定的SERVERID 释放
	 * 
	 * @param serverid
	 * @return
	 */
	public boolean resetMC00_task_status(String serverid) {

		boolean isSucc = false;

		/**
		 * mC00_task_fact:有serverid的更新为空 mC00_task_status:执行未成功的删除
		 */

		try {

			// 任务状态表重置，失败的可以重新计算
			// 把任务非成功状态清楚掉
			ApplicationCFG applicationCFG = (ApplicationCFG) ac
					.getBean("applicationCFG");
			if (serverid.equalsIgnoreCase(applicationCFG
					.getServer_name_mainserver())) {
				// 主服务重新启动，所有的状态都需要重置
				mC00_task_statusMapper.resetAllMC00_task_status();
				// 任务表解锁，允许其他服务重新绑定计算
				// 对非成功状态，又被某server绑定的任务，解绑
				mC00_task_factMapper.unLockAllTask();

			} else if (serverid.startsWith(applicationCFG
					.getServer_name_taskserver())) {
				mC00_task_statusMapper.resetMC00_task_status(serverid);
				// 任务表解锁，允许其他服务重新绑定计算
				// 对非成功状态，又被某server绑定的任务，解绑
				mC00_task_factMapper.unLockTask(serverid);

			}

			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return isSucc;

	}

	/**
	 * 
	 * @param serverid
	 * @param mC00_task_fact
	 * @return
	 */
	public boolean lockTask(String serverid, MC00_task_fact mC00_task_fact) {
		boolean isSucc = false;

		/**
		 * 如果锁定任务成功，返回值为1 如果锁定任务失败，或者报错，或者返回值为0
		 */
		try {
			int i = mC00_task_factMapper.lockTask(mC00_task_fact);
			if (i == 1) {
				isSucc = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isSucc;
	}

	public boolean unLockCurrTask(MC00_task_fact mC00_task_fact) {
		boolean isSucc = false;

		try {
			int i = mC00_task_factMapper.unLockCurrTask(mC00_task_fact);
			if (i == 1) {
				isSucc = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isSucc;
	}

	public ArrayList getTaskListForCal() {

		ArrayList taskfactList = mC00_task_factMapper.getMC00_task_factList();

		return taskfactList;
	}

	/**
	 * 当日任务是否全部执行完毕！
	 * 
	 * @param mC00_task_fact
	 * @return
	 */
	public boolean canFinishTask(String datatime,HashMap trigerFreqMap) {

		boolean canFinish = true;
		
		//第一步：比个数
		//特殊情况：当系统重启后，失败任务被删除，此处不判断会导致任务会继续完成下去。
		int status_c = mC00_task_statusMapper.getMC00_task_statusCount(datatime);
		int fact_c = mC00_task_factMapper.getMC00_task_factCount(datatime);
		//如果事实表与状态表的 个数不一致，那么必然任务不可以完成，后续不用判断了。
		if(status_c!=fact_c){
			return false;
		}
		//第二步：
		ArrayList taskstatusList = (ArrayList) mC00_task_statusMapper
				.getMC00_task_statusListGroupbytaskid(datatime);
		HashMap finishMap = new HashMap();
		//已经设置的任务是否都为完成状态
		Iterator iter = taskstatusList.iterator();
		while (iter.hasNext()) {
			MC00_task_status mC00_task_status = (MC00_task_status) iter.next();
			String taskid = mC00_task_status.getTaskid();
			String calstatus = mC00_task_status.getCalstatus();
			if (!calstatus.equals("1")) {// 有执行失败的任务
				canFinish = false;
			}
			finishMap.put(taskid, "");
		}
		
		if(!canFinish){
			return canFinish;
		}
		
		
		
		HashMap taskMap = (HashMap) cacheService.getCache("taskbytrigerid",
				HashMap.class);
		//判断完成的任务，和，原始的任务数量一致性
		Iterator tIter = taskMap.keySet().iterator();
		while (tIter.hasNext()) {
			String trigerid = (String) tIter.next();

			MC00_task mC00_task = (MC00_task)taskMap.get(trigerid);
			String taskid = mC00_task.getTaskid();
			
			if(trigerFreqMap.containsKey(trigerid)  && !( (Boolean)trigerFreqMap.get(trigerid) )){
				/*
				 * 循环判断每个任务的频度，如果任务频度不在当日频度范围内，说明当前任务永远不会被触发
				 * 例如：
				 * 1)如果当前数据时间只是日(1)，而任务只是月(4)，那么月是永远不会被触发
				 * 2)如果当前数据时间只是日月(1，4)，而任务只是月或者月季(4/4，5)，那么月是需要被触发 
				 * 因此略过，认为执行完毕即可。
				 */
				continue;
			}
			
			//存在任务没有被执行；
			if (!finishMap.containsKey(taskid)) {
				canFinish = false;
				break;
			}
		}
		return canFinish;
	}

	/**
	 * 保证事物完整性
	 * 
	 * @param datatime
	 * @return
	 */
	@Transactional("metaTransactionManager")
	public boolean finishTask(String datatime) {
		boolean isSuccess = false;

		// 先删除临时表，再设置MC00_datatime状态

		/**
		 * 删除前保留历史
		 */
		mC00_triger_factMapper.deleteMC00_triger_fact_hisByDatatime(datatime);
		mC00_triger_factMapper.insertMC00_triger_fact_his(datatime);
		mC00_triger_factMapper.deleteMC00_triger_factByDatatime(datatime);
		
		mC00_task_factMapper.deleteMC00_task_fact_hisByDatatime(datatime);
		mC00_task_factMapper.insertMC00_task_fact_his(datatime);
		mC00_task_factMapper.deleteMC00_task_factByDatatime(datatime);

		// 设置当日数据完成标志

		MC00_data_time mC00_data_time = new MC00_data_time();
		mC00_data_time.setDatatime(datatime);
		mC00_data_time.setFlag("1");

		int i = mC00_data_timeMapper.modifyMC00_data_time(mC00_data_time);
		if (i == 1) {
			isSuccess = true;
		}

		return isSuccess;
	}

	public HashMap getTaskStatusMapByTaskidAndDt(String _taskid,
			String _datatime) {
		MC00_task_status _mC00_task_status = new MC00_task_status();
		_mC00_task_status.setTaskid(_taskid);
		_mC00_task_status.setDatatime(_datatime);

		HashMap subtaskMap = new HashMap();
		ArrayList taskstatusList = mC00_task_statusMapper
				.getMC00_task_statusList(_mC00_task_status);
		Iterator iter = taskstatusList.iterator();
		while (iter.hasNext()) {
			MC00_task_status mC00_task_status = (MC00_task_status) iter.next();
			String subtaskid = mC00_task_status.getSubtaskid();
			String calstatus = mC00_task_status.getCalstatus();
			subtaskMap.put(subtaskid, calstatus);
		}

		return subtaskMap;

	}
	
	public static void main(String[] args){
		String a= "1,4";
		String b= "4";
		
		if(!a.contains(b)){
			System.out.println("==no");
		}else{
			System.out.println("==yes");
		}
		
		
		
	}
}
