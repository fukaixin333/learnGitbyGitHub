package com.citic.server.gdjg.inner;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.SpringContextHolder;
import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.dict.DictCoder;
import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Br57_cxqq_back;
import com.citic.server.gdjg.domain.Br57_cxqq_mx;
import com.citic.server.gdjg.domain.request.Gdjg_RequestLsdj;
import com.citic.server.gdjg.domain.request.Gdjg_Request_TransCx;
import com.citic.server.gdjg.mapper.MM57_cxqqMapper;
import com.citic.server.gdjg.service.RemoteDataOperateGdjg;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.GdjgKeys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;

@Service("dynamicPollingTaskGdjg")
public class DynamicPollingTaskGdjg extends AbstractPollingTask {
	private final Logger logger = LoggerFactory.getLogger(DynamicPollingTaskGdjg.class);
	
	@Autowired
	private MM57_cxqqMapper mm57_cxqqMapper;
	
	private BaseBo baseBo;
	private RemoteDataOperateGdjg remoteDataOperate;
	
	/** 码表转换接口 */
	@Autowired
	private DictCoder dictCoder;
	
	@Override
	public void executeAction() {
		long li1 = System.currentTimeMillis();
		String currDataTime = Utility.currDateTime19();
		
		// 关闭截止时间到期的任务
		mm57_cxqqMapper.closeBr57_cxqq_acct_dynamic_main(currDataTime);
		
		// 获取所有待处理任务
		List<Br57_cxqq_mx> dynamicsTasks = mm57_cxqqMapper.queryBr57_cxqq_acct_dynamic_main(ServerEnvironment.TASK_TYPE_GDJCY, currDataTime);
		
		int fail = 0;
		for (Br57_cxqq_mx br57_cxqq_mx : dynamicsTasks) {
			try {
				processDynamicTask(br57_cxqq_mx);
			} catch (Exception e) {
				fail++;
				logger.error("任务处理异常：{}", e.getMessage(), e);
			}
		}
		
		long li2 = System.currentTimeMillis();
		logger.info("轮询动态查询任务完成，耗时[" + (li2 - li1) + "]ms，共计[" + dynamicsTasks.size() + "]个任务，失败[" + fail + "]个。");
	}
	
	private void processDynamicTask(Br57_cxqq_mx dynamics) throws Exception {
		String currData = Utility.currDate10();
		String currDataTime = Utility.currDateTime19();
		String lastPollingTime = dynamics.getLastpollingtime();
		String taskType = dynamics.getTasktype(); // 任务代号
		String uniqueId = dynamics.getUniqueid();
		String account = dynamics.getAccount();
		String rwlsh = dynamics.getRwlsh();
		
		logger.info("正在执行动态查询任务 - 唯一标识：[{}]", uniqueId);
		int seq = 0; // 计数序列
		if (dynamics.getPollinglock() != null && dynamics.getPollinglock().length() > 0) {
			seq = Integer.parseInt(dynamics.getPollinglock());
		}
		int nextSeq = seq + 1; // 下一个计数序列号
		
		dynamics.setStartdate(lastPollingTime);
		dynamics.setEnddate(currDataTime);
		List<Gdjg_Request_TransCx> transactionList = null;
		try {
			Gdjg_RequestLsdj transactionLs = getRemoteDataOperate().getAccountTransaction(dynamics); // 访问接口获取交易
			transactionList = transactionLs.getTrans();
		} catch (DataOperateException e) {
			logger.error("动态查询任务[{}] - 访问远程接口查询交易流水记录失败：{}", uniqueId);
			throw new DataOperateException(e.getMessage());
		}
		
		// 写账户交易流水表及相应表
		if (transactionList == null || transactionList.size() == 0) {
			logger.debug("动态查询任务[{}] - 无交易流水记录", uniqueId);
			nextSeq = seq; // 不更新计数序列号
		} else {
			// 查询已经反馈的交易流水记录（核心不支持精确到时分秒的环境）
			// 查询数据范围：最后一次轮询当天凌晨0点到最后一次轮询时间之间的记录。
			String startTime = Utility.toDate10(lastPollingTime) + " 00:00:00";
			List<String> transSerialList = mm57_cxqqMapper.selBr57_trans_infoList(uniqueId, account, taskType, startTime, lastPollingTime);
			
			int size = transactionList.size() - transSerialList.size(); // 初始化大小
			List<Gdjg_Request_TransCx> newTransList = new ArrayList<Gdjg_Request_TransCx>(size);
			for (Gdjg_Request_TransCx transaction : transactionList) {
				String transSerial = transaction.getTransnum(); //交易编号
				if (transSerial == null || transSerial.length() == 0) {
					logger.warn("动态查询任务[{}] - 交易流水记录未指定唯一序列号", uniqueId);
				} else if (!transSerialList.contains(transSerial)) {
					transaction.setTasktype(taskType);
					//transaction.setRwlsh(uniqueId + "_" + nextSeq); //标记一个账号查询了多少次
					transaction.setUniqueid(uniqueId + "_" + nextSeq);
					transaction.setCaseno(dynamics.getCaseno());
					transaction.setDocno(dynamics.getDocno());
					transaction.setQrydt(currData);
					//将字段进行转码【核心转监管】
					dictCoder.reverse(transaction, taskType); // 逆向转码
					newTransList.add(transaction);
				}
			}
			
			if (newTransList.size() == 0) {
				logger.debug("动态查询任务[{}] - 无新的交易流水记录", uniqueId);
				nextSeq = seq; // 不更新计数序列号
			} else {
				mm57_cxqqMapper.batchInsertBr57_cxqq_back_dyntrans(newTransList); // 批量插入数据库
				
				Br57_cxqq_back cxqq_back = new Br57_cxqq_back();
				cxqq_back.setCxfkjg("01"); // 01-表示成功，02-表示失败；
				cxqq_back.setCzsbyy("动态查询成功");
				cxqq_back.setUniqueid(uniqueId + "_" + nextSeq);
				insertBr57_cxqq_back(cxqq_back, dynamics, String.valueOf(nextSeq));
			}
		}
		
		// 更新本次处理时间
		mm57_cxqqMapper.updateBr57_cxqq_acct_dynamic_main(taskType, uniqueId, currDataTime, String.valueOf(nextSeq));
	}
	
	private void insertBr57_cxqq_back(Br57_cxqq_back br57_cxqq_back, Br57_cxqq_mx mx, String nextSeq) throws Exception {
		Br57_cxqq_back cxqq_back = new Br57_cxqq_back();
		cxqq_back.setDocno(mx.getDocno());
		cxqq_back.setCaseno(mx.getCaseno());
		cxqq_back.setUniqueid(br57_cxqq_back.getUniqueid());
		cxqq_back.setQrydt(mx.getQrydt());
		cxqq_back.setStatus("1");
		cxqq_back.setCxfkjg(br57_cxqq_back.getCxfkjg());
		cxqq_back.setCzsbyy(br57_cxqq_back.getCzsbyy());
		cxqq_back.setLast_up_dt(Utility.currDateTime19());
		
		mm57_cxqqMapper.insertBr57_cxqq_back(cxqq_back);
		
		// 写入task3
		this.insertTask3(mx, nextSeq);
	}
	
	public void insertTask3(Br57_cxqq_mx br57_cxqq_mx, String nextSeq) throws Exception {
		MC21_task_fact taskFact = new MC21_task_fact();
		
		taskFact.setBdhm(br57_cxqq_mx.getDocno());
		taskFact.setTaskobj(br57_cxqq_mx.getDocno() + "$" + br57_cxqq_mx.getUniqueid() + "_" + nextSeq);
		taskFact.setTgroupid(nextSeq); //标记查询次
		taskFact.setSubtaskid(GdjgConstants.DATA_CONTENT_YHDTCX);
		taskFact.setIsemployee("0");
		taskFact.setTasktype("21");
		getBaseBo().insertMc21TaskFact3(taskFact, "GDJG");
	}
	
	@Override
	protected String getTaskType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 因为<code>SpringContextHolder</code>使用XML注入方式，在本服务类注入之后，所以采用延迟加载BaseBo对象。
	 * 
	 * @return
	 */
	private BaseBo getBaseBo() {
		if (baseBo == null) {
			baseBo = new BaseBo();
		}
		return baseBo;
	}
	
	/**
	 * <code>RemoteDataOperate3</code>仅供广东省检察院查控系统<br />
	 * 由于并非所有项目工程都有实现，所以不能使用Spring注解注入，采用延迟加载对象。
	 * 
	 * @return
	 */
	private RemoteDataOperateGdjg getRemoteDataOperate() {
		if (remoteDataOperate == null) {
			remoteDataOperate = SpringContextHolder.getBean("remoteDataOperateGdjg");
		}
		return remoteDataOperate;
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(GdjgKeys.INNER_POLLING_TASK_DYNAMICS_PERIOD_GDJG, "每天|02:00");
	}
	
}
