package com.citic.server.jsga.inner;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.SpringContextHolder;
import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.dict.DictCoder;
import com.citic.server.jsga.domain.Br40_cxqq;
import com.citic.server.jsga.domain.Br40_cxqq_back;
import com.citic.server.jsga.domain.Br40_cxqq_mx;
import com.citic.server.jsga.domain.request.JSGA_QueryRequest_Transaction;
import com.citic.server.jsga.mapper.JSGA_OuterPollingTaskMapper;
import com.citic.server.jsga.mapper.MM40_cxqq_jsgaMapper;
import com.citic.server.jsga.service.IDataOperate12;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.JsgaKeys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

/**
 * * 动态查询任务
 * 
 * @author Liu Xuanfei
 * @date 2016年6月17日 下午7:38:14
 */
@Service("dynamicPollingTaskJSGA")
public class DynamicPollingTaskJSGA extends AbstractPollingTask {
	private final Logger logger = LoggerFactory.getLogger(DynamicPollingTaskJSGA.class);
	
	@Autowired
	private MM40_cxqq_jsgaMapper mm40_cxqqMapper;
	
	private BaseBo baseBo;
	private IDataOperate12 remoteDataOperate;
	
	/** 码表转换接口 */
	@Autowired
	private DictCoder dictCoder;
	
	@Autowired
	private JSGA_OuterPollingTaskMapper jsga_mapper;
	
	@Override
	public void executeAction() {
		long li1 = System.currentTimeMillis();
		String currDataTime = Utility.currDateTime19();
		
		// 关闭截止时间到期的任务
		jsga_mapper.closeBr40_cxqq_acct_dynamic_js(currDataTime);
		
		// 将动态查询到期的数据的状态改为3
		String currdate = Utility.currDate10();
		jsga_mapper.updateBr41_cxqq_back_dynamic(currdate);
		
		// 获取所有待处理任务
		List<Br40_cxqq> dynamicsTasks = jsga_mapper.queryBr40_cxqq_acct_dynamic_js(null, currDataTime);
		
		int fail = 0;
		for (Br40_cxqq dynamics : dynamicsTasks) {
			try {
				processDynamicTask(dynamics);
			} catch (Exception e) {
				fail++;
				logger.error("任务处理异常：{}", e.getMessage(), e);
			}
		}
		
		long li2 = System.currentTimeMillis();
		logger.info("轮询动态查询任务完成，耗时[" + (li2 - li1) + "]ms，共计[" + dynamicsTasks.size() + "]个任务，失败[" + fail + "]个。");
	}
	
	private void processDynamicTask(Br40_cxqq dynamics) throws Exception {
		String currData = Utility.currDate10();
		String currDataTime = Utility.currDateTime19();
		String lastPollingTime = dynamics.getLastpollingtime();
		String taskType = dynamics.getTasktype(); // 任务代号
		String qqdbs = dynamics.getQqdbs();
		String rwlsh = dynamics.getRwlsh();
		
		logger.info("正在执行动态查询任务 - [{}]", rwlsh);
		int seq = 0; // 计数序列
		if (dynamics.getPollinglock() != null && dynamics.getPollinglock().length() > 0) {
			seq = Integer.parseInt(dynamics.getPollinglock());
		}
		int nextSeq = seq + 1; // 下一个计数序列号
		
		Br40_cxqq_mx br40_cxqq_mx = mm40_cxqqMapper.selectBr40_cxqq_mxByVo(dynamics);
		br40_cxqq_mx.setMxqssj(lastPollingTime);
		br40_cxqq_mx.setMxjzsj(currDataTime);
		br40_cxqq_mx.setAcct_num(dynamics.getCxzh()); // 查询账号
		
		List<JSGA_QueryRequest_Transaction> transactionList = null;
		try {
			transactionList = getRemoteDataOperate().getAccountTransaction(br40_cxqq_mx); // 访问接口获取交易
		} catch (DataOperateException e) {
			logger.error("动态查询任务[{}] - 访问远程接口查询交易流水记录失败：{}", rwlsh, e.getMessage(), e);
			return;
		}
		
		// 写账户交易流水表及相应表
		if (transactionList == null || transactionList.size() == 0) {
			logger.debug("动态查询任务[{}] - 无交易流水记录", rwlsh);
			nextSeq = seq; // 不更新计数序列号
		} else {
			// 查询已经反馈的交易流水记录（核心不支持精确到时分秒的环境）
			// 查询数据范围：最后一次轮询当天凌晨0点到最后一次轮询时间之间的记录。
			String startTime = Utility.toDate10(lastPollingTime) + " 00:00:00";
			List<String> transSerialList = mm40_cxqqMapper.selBr40_trans_infoList(qqdbs, rwlsh, taskType, startTime, lastPollingTime);
			
			int size = transactionList.size() - transSerialList.size(); // 初始化大小
			if (size < 1) {
				logger.debug("动态查询任务[{}] - 无新的交易流水记录", rwlsh);
				nextSeq = seq; // 不更新计数序列号
			} else {
				List<JSGA_QueryRequest_Transaction> newTransList = new ArrayList<JSGA_QueryRequest_Transaction>(size);
				for (JSGA_QueryRequest_Transaction transaction : transactionList) {
					String transSerial = transaction.getTransseq();
					if (transSerial == null || transSerial.length() == 0) {
						logger.warn("动态查询任务[{}] - 交易流水记录未指定唯一序列号：[{}]", rwlsh, transaction.getJylsh());
					} else if (!transSerialList.contains(transSerial)) {
						transaction.setTasktype(taskType);
						transaction.setQqdbs(qqdbs);
						transaction.setRwlsh(rwlsh + "_" + nextSeq);
						transaction.setQrydt(currData);
						// 处理“交易对方卡号”、“交易对方账号”及“交易对方账卡号”
						if (transaction.getJydfzh() == null || transaction.getJydfzh().length() == 0) { // 交易对方账号
							transaction.setJydfzh(transaction.getJydfkh()); // 交易对方卡号
						} else if (transaction.getJydfkh() == null || transaction.getJydfkh().length() == 0) { // 交易对方卡号
							transaction.setJydfkh(transaction.getJydfzh()); // 交易对方账号
						}
						
						dictCoder.reverse(transaction, taskType); // 逆向转码
						newTransList.add(transaction);
					}
				}
				
				if (newTransList.size() == 0) {
					logger.debug("动态查询任务[{}] - 无新的交易流水记录", rwlsh);
					nextSeq = seq; // 不更新计数序列号
				} else {
					mm40_cxqqMapper.insertBr40_cxqq_back_trans(newTransList); // 批量插入数据库
					
					Br40_cxqq br40_cxqq = mm40_cxqqMapper.selectBr40_cxqqByVo(dynamics);
					Br40_cxqq_back cxqq_back = new Br40_cxqq_back();
					cxqq_back.setSeq(String.valueOf(nextSeq));
					cxqq_back.setCxfkjg("0"); // 0-表示成功，1-表示失败；
					insertBr40_cxqq_back(cxqq_back, br40_cxqq_mx, br40_cxqq);
				}
			}
		}
		// 更新本次处理时间
		jsga_mapper.updateBr40_cxqq_acct_dynamic_js(taskType, rwlsh, currDataTime, String.valueOf(nextSeq));
	}
	
	public void insertBr40_cxqq_back(Br40_cxqq_back cxqq_back, Br40_cxqq_mx br40_cxqq_mx, Br40_cxqq br40_cxqq) throws Exception {
		cxqq_back.setTasktype(br40_cxqq_mx.getTasktype());
		cxqq_back.setQqdbs(br40_cxqq_mx.getQqdbs());
		cxqq_back.setRwlsh(br40_cxqq_mx.getRwlsh());
		cxqq_back.setZh(br40_cxqq_mx.getCxzh());
		cxqq_back.setKh(br40_cxqq_mx.getCxzh());
		cxqq_back.setQrydt(DtUtils.getNowDate());//查询日期
		cxqq_back.setStatus("1");//核心已返回
		cxqq_back.setCxjssj(br40_cxqq_mx.getMxjzsj());
		cxqq_back.setSqjgdm(br40_cxqq.getSqjgdm());
		cxqq_back.setZtlb(br40_cxqq_mx.getZtlb());
		cxqq_back.setMbjgdm(br40_cxqq.getMbjgdm());
		cxqq_back.setOrgkey(br40_cxqq_mx.getOrgkey());
		cxqq_back.setMsgcheckresult("1");
		cxqq_back.setJssj(br40_cxqq_mx.getMxjzsj());
		cxqq_back.setZxqssj(br40_cxqq_mx.getMxqssj());
		// cxqq_back.setZxsjqj(br40_cxqq_mx.getMxsdlx());
		mm40_cxqqMapper.insertBr40_cxqq_back(cxqq_back);
		
		// 写入task3
		this.insertTask3(br40_cxqq_mx, cxqq_back.getSeq(), br40_cxqq);
	}
	
	public void insertTask3(Br40_cxqq_mx br40_cxqq_mx, String seq, Br40_cxqq _cxqq) throws Exception {
		MC21_task_fact taskFact = new MC21_task_fact();
		taskFact.setBdhm(br40_cxqq_mx.getQqdbs() + "$" + br40_cxqq_mx.getRwlsh());
		taskFact.setTgroupid(seq);
		taskFact.setSubtaskid(_cxqq.getQqcslx());
		taskFact.setIsemployee("0");
		taskFact.setTasktype(br40_cxqq_mx.getTasktype());
		getBaseBo().insertMc21TaskFact3(taskFact, "JSGA_" + _cxqq.getTasktype());
	}
	
	@Override
	protected String getTaskType() {
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
	 * <code>RemoteDataOperate3</code>仅供四川省公安厅资金查控系统<br />
	 * 由于并非所有项目工程都有实现，所以不能使用Spring注解注入，采用延迟加载对象。
	 * 
	 * @return
	 */
	private IDataOperate12 getRemoteDataOperate() {
		if (remoteDataOperate == null) {
			remoteDataOperate = SpringContextHolder.getBean("remoteDataOperate12");
		}
		return remoteDataOperate;
	}
	
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(JsgaKeys.INNER_POLLING_PERIOD_DYNAMICS, "每天|02:00");
	}
}
