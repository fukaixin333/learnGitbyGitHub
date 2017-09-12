package com.citic.server.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
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
import com.citic.server.domain.MC02_rt_task_fact;
import com.citic.server.domain.MC02_rt_task_para;
import com.citic.server.mapper.MC02_rt_task_factMapper;
import com.citic.server.mapper.MC02_rt_task_logMapper;
import com.citic.server.mapper.MC02_rt_task_paraMapper;
import com.citic.server.service.task.BaseTask;
import com.citic.server.service.task.RtBaseTask;
import com.citic.server.service.task.RtTaskFactory;
import com.citic.server.service.tasksplit.BaseSplit;
import com.citic.server.service.tasksplit.SplitFactory;
import com.citic.server.utils.DtUtils;

@Service
public class RtServer {

	private static final Logger logger = LoggerFactory.getLogger(RtServer.class);

	@Autowired
	private ApplicationContext ac;

	@Autowired
	SpringContextHolder springContextHolder;

	@Autowired
	MC02_rt_task_factMapper mC02_rt_task_factMapper;
	@Autowired
	MC02_rt_task_paraMapper mC02_rt_task_paraMapper;
	
	public RtServer() {

	}

	/**
	 * 任务执行 1、任务设置：扫描模型主表状态为未计算的任务，按照计算的数据周期，拆分到计算任务表；同时按照任务表，拆分子任务到执行任务表；
	 * 2、任务执行：按照模型主表任务设定的时间先后顺序，以模型维度为主开始执行 1）取满足数据时间要求的计算任务出来，顺序执行：执行任务表任务
	 * 
	 * 
	 */
	public void cal(String serverid) throws Exception {
		 
		// 设置任务
		this.splitTask();

		// 计算任务
		this.calTask(serverid);

	}

	public boolean calTask(String serverid) throws Exception {
		// 失败任务进行复位
		mC02_rt_task_factMapper.resetTaskFactStatus("");
		// 获取需要执行的任务
		ArrayList factlList = mC02_rt_task_factMapper.selectMc02_rt_task_factList();
		RtTaskFactory taskFactory = new RtTaskFactory();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		Iterator taskIter = factlList.iterator();
		while (taskIter.hasNext()) {
			MC02_rt_task_fact mC02_rt_task_fact = (MC02_rt_task_fact) taskIter.next();
			mC02_rt_task_fact=this.initTaskPara(mC02_rt_task_fact);
			try {
				mC02_rt_task_fact.setBegintime(fmt.print(new DateTime()));
				RtBaseTask baseTask = taskFactory.getInstance(ac, mC02_rt_task_fact);
				baseTask.run();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} 
//			finally {
//				mC02_rt_task_fact.setEndtime(fmt.print(new DateTime()));
//				mC02_rt_task_factMapper.updateMc02_rt_task_fact(mC02_rt_task_fact);
//			}
		}
		return true;
	}

	private boolean splitTask() throws Exception {
		// 1、获取需要自动设置的任务信息
		ArrayList list = mC02_rt_task_factMapper.IsSetTaskListExecute("");
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			MC02_rt_task_fact mC02_rt_task_fact = (MC02_rt_task_fact) iter.next();
			mC02_rt_task_fact.setStatisticdate(DtUtils.getNowDate());
			// 2、组织需要设置任务的时间
			String settime = mC02_rt_task_fact.getSettasktime();
			String[] settimeStr = null;
			if (settime.indexOf(",") > -1) {
				settimeStr = StringUtils.split(settime, ",");
			} else {
				settimeStr = new String[1];
				settimeStr[0] = settime;
			}
			// 3、遍历所有时间进行任务设置
			for (int i = 0; i < settimeStr.length; i++) {
				int time = Integer.valueOf(settimeStr[i]);
				// 4、判断是否已经到需要设置任务的时间
				int currtime = Integer.valueOf(DtUtils.getHour(DtUtils.toDate(DtUtils.getNowTime())));
				mC02_rt_task_fact.setObjdes(String.valueOf(time));
				if (currtime >= time) {
					// 5、检查任务是否设置过
					if (mC02_rt_task_factMapper.taskIsSet(mC02_rt_task_fact) <= 0) {
						// 6、未设置过进行任务设置
						mC02_rt_task_fact.setCalstatus("2");
						mC02_rt_task_fact.setTaskkey(mC02_rt_task_fact.getBusikey()+mC02_rt_task_fact.getObjdes()+DtUtils.getNowTime().replace("-", "").replace(":", "").replace(" ", ""));
						mC02_rt_task_fact.setCreat_dt(DtUtils.getNowTime());
						int count=mC02_rt_task_factMapper.insertMc02_rt_task_fact(mC02_rt_task_fact);
					}
				}
			}
		}

		return true;
	}

	public MC02_rt_task_fact initTaskPara(MC02_rt_task_fact mc02_rt_task_fact) {
		try {
			// 初始化任务参数T18_ONLINE_TASK_PARA
			HashMap paraHash = new HashMap();
			HashMap subparaHash = new HashMap();
			MC02_rt_task_para mc02_rt_task_para = new MC02_rt_task_para();
			mc02_rt_task_para.setTaskkey(mc02_rt_task_fact.getTaskkey());
			ArrayList paraList = mC02_rt_task_paraMapper.selectMc02_rt_task_paraByVo(mc02_rt_task_para);
			
			// 2.遍历需要执行的任务
			Iterator iter = paraList.iterator();
			while (iter.hasNext()) {
				mc02_rt_task_para = (MC02_rt_task_para) iter.next();
				if (mc02_rt_task_para.getSubnum() != null && !"".equals(mc02_rt_task_para.getSubnum())) {
					if (paraHash.containsKey(mc02_rt_task_para.getPara_code())) {
						subparaHash = (HashMap) paraHash.get(mc02_rt_task_para.getPara_code());
						subparaHash.put(mc02_rt_task_para.getSubnum(), mc02_rt_task_para.getPara_val());
					} else {
						subparaHash = new HashMap();
						subparaHash.put(mc02_rt_task_para.getSubnum(), mc02_rt_task_para.getPara_val());
						paraHash.put(mc02_rt_task_para.getPara_code(), subparaHash);
					}
				} else {
					paraHash.put(mc02_rt_task_para.getPara_code(), mc02_rt_task_para.getPara_val());

				}

				mc02_rt_task_fact.setParamap(paraHash);
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return mc02_rt_task_fact;
	}

}
