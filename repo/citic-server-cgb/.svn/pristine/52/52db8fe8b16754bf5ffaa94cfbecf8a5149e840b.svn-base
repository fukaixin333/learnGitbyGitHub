package com.citic.server.gf.task;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.dict.DictCoder;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.response.ControlResponse_Kzzh;
import com.citic.server.gf.mapper.ControlTaskMapper1A;
import com.citic.server.gf.service.IDataOperate01;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.DbFuncUtils;

/**
 * 高法控制类处理（广发银行版本）
 * 
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/05 15:25:00$
 */
public class TK_ESGF_CL02A extends NBaseTask implements Constants {
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(TK_ESGF_CL02A.class);
	
	private DbFuncUtils dbfunc = new DbFuncUtils();
	
	private DictCoder dictCoder; // 数据字典转换工具
	private IDataOperate01 dataOperate;
	
	private MC00_common_Mapper commonMapper;
	private ControlTaskMapper1A controlMapper;
	
	public TK_ESGF_CL02A(ApplicationContext ctx, MC21_task_fact taskFact) {
		super(ctx, taskFact);
		
		dictCoder = SpringContextHolder.getBean(DictCoder.class);
		controlMapper = SpringContextHolder.getBean(ControlTaskMapper1A.class);
		commonMapper = SpringContextHolder.getBean(MC00_common_Mapper.class);
		dataOperate = SpringContextHolder.getBean("remoteDataOperate1");
	}
	
	public boolean calTask() throws Exception {
		try {
			process0(this.getMC21_task_fact());
		} catch (Exception e) {
			LOGGER.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}
		
		return true;
	}
	
	private void clearControlData(String bdhm, String ccxh) throws Exception {
		controlMapper.clearAllDjxxInfo(bdhm, ccxh);
		controlMapper.clearAllQlxxInfo(bdhm, ccxh);
	}
	
	private void process0(MC21_task_fact taskFact) throws Exception {
		String bdhm = taskFact.getBdhm(); // 请求单号
		String ccxh = taskFact.getTaskobj(); // 请求单序号
		
		// 清理数据（有时会做无用功）
		clearControlData(bdhm, ccxh);
		
		// 获取控制请求信息（联表查询）
		ControlResponse_Kzzh taskDetail = controlMapper.queryControlTaskDetail(bdhm, ccxh);
		String kzcs = taskDetail.getKzcs(); // 控制措施
		
		// 数据字典转换
		dictCoder.transcode(taskDetail, TASK_TYPE_GF);
		
		boolean completed = true; // 是否处理完成（无需等待人工处理），即，是否进入Task3反馈阶段
		ControlRequest_Kzxx taskResult = null; // 控制类处理结果
		ControlRequest_Kzxx oriTaskResult = null; //取冻结编号
		// 01-冻结；02-继续冻结；04-解除冻结
		if ("06".equals(kzcs)) {
			String status = taskDetail.getStatus();
			try {
				if (StringUtils.isBlank(status) || "H".equals(status)) {
					oriTaskResult = controlMapper.queryOriginalTaskResult(taskDetail.getYdjdh(), taskDetail.getKhzh(), taskDetail.getGlzhhm());
					if (oriTaskResult == null || StringUtils.isBlank(oriTaskResult.getHxappid())) {
						LOGGER.warn("无原冻结请求信息或冻结编号");
						taskResult = new ControlRequest_Kzxx();
						taskResult.setStatus("F");
						taskResult.setJjyy("无原冻结请求信息或冻结编号");
					} else {
						taskDetail.setHxappid(oriTaskResult.getHxappid());
						taskResult = dataOperate.invokeDeductFunds(taskDetail);
					}
				} else {
					taskResult = dataOperate.invokeDeductFunds(taskDetail);
				}
			} catch (DataOperateException e) {
				LOGGER.error("Exception: {} - BDHM=[{}] CCXH=[{}]", e.getMessage(), bdhm, ccxh);
				taskResult = new ControlRequest_Kzxx();
				taskResult.setStatus("F");
				taskResult.setJjyy(e.getMessage());
			} catch (Exception e) {
				LOGGER.error("Exception: 执行扣划请求处理失败 - BDHM=[{}] CCXH=[{}]", bdhm, ccxh, e);
				taskResult = new ControlRequest_Kzxx();
				taskResult.setStatus("F");
				taskResult.setJjyy("系统处理异常");
			}
			
			status = taskResult.getStatus(); // 下一任务处理阶段标识
			if ("N".equals(status) || "F".equals(status)) {
				completed = false;
			} else if ("R".equals(status)) {
				taskResult.setKzzt("2"); // 2-未控
			} else {
				taskResult.setKzzt("1"); // 1-已控
			}
		} else if ("01".equals(kzcs) || "02".equals(kzcs) || "04".equals(kzcs)) {
			boolean isFreeze = "01".equals(kzcs);
			try {
				if (!isFreeze) {
					oriTaskResult = controlMapper.queryOriginalTaskResult(taskDetail.getYdjdh(), taskDetail.getKhzh(), taskDetail.getGlzhhm()); // 查询原冻结请求结果
					if (oriTaskResult == null || StringUtils.isBlank(oriTaskResult.getHxappid())) {
						throw new DataOperateException("无原冻结请求信息或冻结编号");
					}
					
					// 解除冻结（04）时，判断是否有未处理的同一原冻结编号的扣划任务
					if ("04".equals(kzcs)) {
						List<String> bdhmList = controlMapper.queryOriginalCount(taskDetail.getYdjdh(), "06");
						if (bdhmList != null && !bdhmList.isEmpty()) {
							controlMapper.updateUnfreezeStatus(taskDetail.getBdhm());
							return;
						}
					}
					
					taskDetail.setHxappid(oriTaskResult.getHxappid()); // 账户资产原冻结编号
					taskDetail.setLcappid(oriTaskResult.getLcappid()); // 金融理财原冻结编号
				}
				
				// 查询被执行人账户的在先冻结信息
				try {
					this.queryDjxxList(taskDetail);
				} catch (DataOperateException e) {
					LOGGER.warn("Exception: 在先冻结信息查询失败 - BDHM=[{}]", bdhm, e);
				}
				
				// 查询被执行人账户的共有权/优先权信息
				boolean isPriority = false;
				try {
					isPriority = this.queryQlxxList(taskDetail);
				} catch (DataOperateException e) {
					LOGGER.error("Exception: 共有权/优先权信息查询失败 - BDHM=[{}]", bdhm, e);
				}
				
				// 执行控制请求（非扣划）
				taskResult = dataOperate.invokeFreezeAccount(taskDetail);
				if (taskResult == null) {
					throw new DataOperateException("司法控制请求处理异常");
				}
				
				// 补全金额/份额类数据
				if (!isFreeze) {
					if (StringUtils.isBlank(taskResult.getSkje())) {
						taskResult.setSkje(oriTaskResult.getSkje()); // 实际控制金额
					}
					
					if (StringUtils.isBlank(taskResult.getCeskje())) {
						taskResult.setCeskje(oriTaskResult.getCeskje()); // 超额控制金额
					}
				} else if (isPriority) {
					// 如果存在优先权，则实控金额为零，超额控制金额为申请控制金额 
					taskResult.setSkje("0");
					taskResult.setCeskje(taskDetail.getJe()); // 
				} else if ("2".equals(taskDetail.getKznr())) { // 2-账户冻结
					taskResult.setSkje(taskResult.getKyye());
				}
				
				taskResult.setKzzt("1"); // 控制结果（1-已控；2-未控）
				taskResult.setStatus("O"); // O-处理成功
			} catch (DataOperateException e) {
				LOGGER.error("Exception: {} - BDHM=[{}] CCXH=[{}]", e.getMessage(), bdhm, ccxh);
				taskResult = new ControlRequest_Kzxx("2", e.getMessage());
				taskResult.setStatus("X"); // X-处理失败
			} catch (Exception e) {
				LOGGER.error("Exception: 司法控制请求处理失败 - BDHM=[{}] CCXH=[{}]", bdhm, ccxh, e);
				taskResult = new ControlRequest_Kzxx("2", "系统处理异常");
				taskResult.setStatus("X"); // X-处理失败
			}
		} else {
			taskResult = new ControlRequest_Kzxx("2", "暂不支持该控制措施=[" + kzcs + "]");
		}
		
		taskResult.setBdhm(bdhm);
		taskResult.setCcxh(ccxh);
		controlMapper.updateControlTaskInfo(taskResult);
		
		if (completed) {
			taskResult.setStatus_cd("5"); // 5-待生成报文
			controlMapper.updateControlTaskResult(taskResult);
			
			int isok = controlMapper.queryTodoTaskCount(bdhm);
			if (isok == 0) {
				controlMapper.updateControlRequestStatus(bdhm, "5");
				
				taskFact.setTaskkey("'T3_GF_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
				taskFact.setDatatime(Utility.currDateTime19());
				commonMapper.insertMc21_task_fact3(taskFact);
			}
			
			if ("06".equals(kzcs)) {
				// 将可能未执行的解冻类请求重置
				List<String> deductList = controlMapper.queryOriginalCount(taskDetail.getYdjdh(), "06");
				if (deductList == null || deductList.isEmpty()) {
					List<String> FreezeList = controlMapper.queryOriginalCount(taskDetail.getYdjdh(), "04");
					if (FreezeList != null && !FreezeList.isEmpty()) {
						controlMapper.updateMC21TaskFact2(FreezeList);
						controlMapper.deleteMc21Task2Status(FreezeList);
					}
				}
			}
		}
		
		// 0-待认定；1-待执行；2-回执文书待处理；5-待生成报文；6-已生成报文；7-回退监管；8-成功；9-失败回执
	}
	
	private void queryDjxxList(ControlResponse_Kzzh kzqq) throws RemoteAccessException, DataOperateException {
		List<QueryRequest_Djxx> djxxList = dataOperate.getDjxxList(kzqq);
		if (djxxList == null || djxxList.size() == 0) {
			return;
		}
		
		int lp = 0;
		for (QueryRequest_Djxx djxx : djxxList) {
			if (djxx.getHxappid() != null && djxx.getHxappid().equals(kzqq.getHxappid())) {
				continue; // 过滤掉本次冻结信息或原冻结信息
			}
			
			djxx.setBdhm(kzqq.getBdhm());
			djxx.setCcxh(kzqq.getCcxh());
			if (djxx.getCsxh() == null || djxx.getCsxh().equals("")) {
				djxx.setCsxh(String.valueOf(lp++));
			}
			djxx.setKhzh(kzqq.getKhzh());
			djxx.setGlzhhm(kzqq.getGlzhhm());
			djxx.setQrydt(Utility.currDate10());
			
			controlMapper.insertControlTaskDjxx(djxx);
		}
	}
	
	private boolean queryQlxxList(ControlResponse_Kzzh kzqq) throws RemoteAccessException, DataOperateException {
		List<QueryRequest_Qlxx> qlxxList = dataOperate.getQlxxList(kzqq);
		if (qlxxList == null || qlxxList.size() == 0) {
			return false;
		}
		
		int lp = 0;
		for (QueryRequest_Qlxx qlxx : qlxxList) {
			qlxx.setBdhm(kzqq.getBdhm());
			qlxx.setCcxh(kzqq.getCcxh());
			if (qlxx.getXh() == null || qlxx.getXh().length() == 0) {
				qlxx.setXh(String.valueOf(lp++));
			}
			qlxx.setKhzh(kzqq.getKhzh());
			qlxx.setGlzhhm(kzqq.getGlzhhm());
			qlxx.setQrydt(Utility.currDate10());
			controlMapper.insertControlTaskQlxx(qlxx);
		}
		return true;
	}
}
