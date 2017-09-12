package com.citic.server.jsga.task.taskBo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.dict.DictCoder;
import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest_Detail;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Detail;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Recored;
import com.citic.server.jsga.domain.response.JSGA_FreezeResponse_Account;
import com.citic.server.jsga.domain.response.JSGA_StopPaymentResponse_Account;
import com.citic.server.jsga.mapper.MM41_kzqq_jsgaMapper;
import com.citic.server.jsga.service.IDataOperate12;
import com.citic.server.runtime.Constants12;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

public class Kzqq_CLBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Kzqq_CLBo.class);
	
	/** 数据获取接口 */
	private IDataOperate12 dataOperate;
	
	private MM41_kzqq_jsgaMapper br41_kzqqMapper;
	
	/** 数据字典码值转换组件 */
	private DictCoder dictCoder;
	
	public Kzqq_CLBo(ApplicationContext ac) {
		super(ac);
		this.br41_kzqqMapper = (MM41_kzqq_jsgaMapper) ac.getBean("MM41_kzqq_jsgaMapper");
		this.dictCoder = (DictCoder) SpringContextHolder.getBean(DictCoder.class);
	}
	
	public JSGA_StopPaymentRequest_Recored sendHx_zf(JSGA_StopPaymentResponse_Account cg_StopPay, MC21_task_fact mc21_task_fact, Br41_kzqq br41_kzqq) throws Exception {
		String taskType = mc21_task_fact.getTasktype(); // 任务代号
		
		JSGA_StopPaymentRequest_Recored cs_StopPay = new JSGA_StopPaymentRequest_Recored();
		//Br41_kzqq br41_kzqq = this.getBr41_kzqq(mc21_task_fact);
		String qqcslx = cg_StopPay.getQqcslx();
		
		JSGA_StopPaymentRequest_Recored _stoppay_back = br41_kzqqMapper.selectBr41_kzqq_zf_backDisp(cg_StopPay);
		String msgCheckResult = _stoppay_back.getMsgcheckresult();
		if (msgCheckResult != null && msgCheckResult.equals("1")) { // 判断是否是本行数据
			try {
				dataOperate = (IDataOperate12) this.ac.getBean(Constants12.REMOTE_DATA_OPERATE_NAME_JSGA);
				
				// 码值转换（将监管数据转换为核心系统可识别的数据）
				dictCoder.transcode(br41_kzqq, taskType);
				if (qqcslx.equals(Constants12.STOPPAYMENT)) {
					cs_StopPay = dataOperate.stoppayAccount(br41_kzqq, cg_StopPay);
				} else if (qqcslx.equals(Constants12.STOPPAYMENT_RELIEVE)) {
					cs_StopPay = dataOperate.stoppayAccountJC(br41_kzqq, cg_StopPay);
				}
				
				if (cs_StopPay.getHxappid() == null || cs_StopPay.getHxappid().equals("")) {
					cs_StopPay.setHxappid(cg_StopPay.getHxappid());
				}
				if (cs_StopPay.getZxjg() == null || cs_StopPay.getZxjg().equals("")) {
					cs_StopPay.setZxjg("0");
				}
			} catch (DataOperateException e) {
				cs_StopPay.setZxjg("1");
				cs_StopPay.setSbyy(e.getDescr());
				logger.warn("数据处理异常：{}", e.getMessage());
			} catch (RemoteAccessException e) {
				e.printStackTrace();
				cs_StopPay.setZxjg("1");
				cs_StopPay.setSbyy("应用程序异常");
				logger.error("应用程序异常：{}", e.getMessage(), e);
			}
			
		} else {
			cs_StopPay.setZxjg("1");
			cs_StopPay.setSbyy("查无此客户");
			// if (msgCheckResult != null && msgCheckResult.equals("3")) {
			// cs_StopPay.setSbyy("证件与名称不符");
			// }
		}
		cs_StopPay.setStatus("1");
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee.equals("2") && cs_StopPay.getZxjg().equals("1")) { //错误后走人工
			isemployee = "1";
		}
		if (isemployee.equals("0")) { //不走人工
			cs_StopPay.setStatus("2");//已处理
		}
		
		cs_StopPay.setMsgcheckresult(msgCheckResult);
		// 处理止付响应表
		insertBr41_kzqq_zf_back(cg_StopPay, cs_StopPay);
		if (isemployee.equals("0")) { //不走人工	
			insertMc21TaskFact3(mc21_task_fact, "JSGA_" + taskType);
		}
		return cs_StopPay;
		
	}
	
	public JSGA_FreezeRequest_Record sendHx_dj(JSGA_FreezeResponse_Account cg_Freeze, MC21_task_fact mc21_task_fact) throws Exception {
		String taskType = mc21_task_fact.getTasktype(); // 任务代号
		
		JSGA_FreezeRequest_Record cs_Freeze = new JSGA_FreezeRequest_Record();
		String freezetype = cg_Freeze.getDjfs();// 01-限额内冻结；02-只收不付
		Br41_kzqq br41_kzqq = this.getBr41_kzqq(mc21_task_fact);
		
		JSGA_FreezeRequest_Record freeze = br41_kzqqMapper.selectBr41_kzqq_dj_backDisp(cg_Freeze);
		
		String msgCheckResult = freeze.getMsgcheckresult();
		if (msgCheckResult != null && msgCheckResult.equals("1")) { // 判断是否是本行数据
			try {
				String qqcslx = cg_Freeze.getQqcslx();
				String appliedBalance = "";
				String frozedBalance = "";
				// 码值转换（将监管数据转换为核心系统可识别的数据）
				dictCoder.transcode(br41_kzqq, taskType);
				dictCoder.transcode(cg_Freeze, taskType);
				if (!qqcslx.equals(Constants12.FREEZE)) { // 非冻结查询冻结编号
					JSGA_FreezeRequest_Record _freeze_back = this.getHxAppid_dj(cg_Freeze);
					cs_Freeze.setQqdbs(_freeze_back.getQqdbs());
					cs_Freeze.setRwlsh(_freeze_back.getRwlsh());
					cs_Freeze.setTasktype(_freeze_back.getTasktype());
					cs_Freeze.setZh(_freeze_back.getZh());
					List<JSGA_FreezeRequest_Detail> freezeList = br41_kzqqMapper.selectBr41_kzqq_dj_back_mxByList(cs_Freeze);
					br41_kzqq.setFreezeList(freezeList);
					String hxappid = _freeze_back.getHxappid();
					cg_Freeze.setHxappid(hxappid);
					if (qqcslx.equals(Constants12.FREEZE_RELIEVE)) { // 冻结解除
						cg_Freeze.setDjfs(_freeze_back.getDjfs());
					}
					appliedBalance = _freeze_back.getSqdjxe(); // 申请冻结限额
					frozedBalance = _freeze_back.getSdje();// 执行冻结金额
				}
				
				dataOperate = (IDataOperate12) this.ac.getBean(Constants12.REMOTE_DATA_OPERATE_NAME_JSGA);
				
				if (qqcslx.equals(Constants12.FREEZE)) { // 冻结
					cs_Freeze = dataOperate.freezeAccount(br41_kzqq, cg_Freeze);
					if (cs_Freeze != null && "02".equals(freezetype)) {
						if (StringUtils.isBlank(cs_Freeze.getSdje())) {
							// 账户冻结，将账户余额作为实际冻结金额
							cs_Freeze.setSdje(cs_Freeze.getYe());
						}
						if (StringUtils.isBlank(cs_Freeze.getSqdjxe())) {
							// 账户冻结，将实际冻结金额作为申请冻结限额
							cs_Freeze.setSqdjxe(cs_Freeze.getSdje());
						}
					}
				} else if (qqcslx.equals(Constants12.FREEZE_RELIEVE)) { // 冻结解除
					cs_Freeze = dataOperate.freezeAccountJC(br41_kzqq, cg_Freeze);
					if (cs_Freeze != null) {
						if (StringUtils.isBlank(cs_Freeze.getSqdjxe())) {
							// 冻结解除，将原申请冻结金额作为本次申请解冻限额
							cs_Freeze.setSqdjxe(appliedBalance);
						}
						if (StringUtils.isBlank(cs_Freeze.getSdje())) {
							// 冻结解除，将原实际冻结金额作为本次实际解冻金额
							cs_Freeze.setSdje(frozedBalance);
						}
					}
				} else if (qqcslx.equals(Constants12.FREEZE_POSTPONE)) { // 冻结延期
					cs_Freeze = dataOperate.freezeAccountYQ(br41_kzqq, cg_Freeze);
					if (cs_Freeze != null) {
						if (StringUtils.isBlank(cs_Freeze.getSqdjxe())) {
							// 冻结解除，将原申请冻结金额作为本次申请冻结限额
							cs_Freeze.setSqdjxe(appliedBalance);
						}
						if (freezetype.equals("02") && StringUtils.isBlank(cs_Freeze.getSdje())) {
							// 账户冻结，将实际冻结金额作为申请冻结限额
							cs_Freeze.setSdje(cs_Freeze.getYe());
						}
					}
				}
				
				if (cs_Freeze.getHxappid() == null || cs_Freeze.getHxappid().equals("")) {
					cs_Freeze.setHxappid(cg_Freeze.getHxappid());
				}
				if (cs_Freeze.getZxjg() == null || cs_Freeze.getZxjg().equals("")) {
					cs_Freeze.setZxjg("0");
				}
			} catch (DataOperateException e) {
				cs_Freeze.setZxjg("1");
				cs_Freeze.setWndjyy(e.getDescr());
				logger.warn("数据处理异常：{}", e.getMessage());
			} catch (RemoteAccessException e) {
				cs_Freeze.setZxjg("1");
				cs_Freeze.setWndjyy("应用程序异常");
				logger.error("应用程序异常：{}", e.getMessage(), e);
			}
		} else {
			cs_Freeze.setZxjg("1");
			cs_Freeze.setWndjyy("查无此客户");
			
			if (msgCheckResult != null && msgCheckResult.equals("3")) {
				cs_Freeze.setWndjyy("证件与名称不符");
			}
		}
		cg_Freeze.setDjfs(freezetype);
		cs_Freeze.setStatus("1");
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee.equals("2") && cs_Freeze.getZxjg().equals("1")) { //错误后走人工
			isemployee = "1";
		}
		if (isemployee.equals("0")) { //不走人工
			cs_Freeze.setStatus("2");//已处理
		}
		cs_Freeze.setMsgcheckresult(msgCheckResult);
		// 处理冻结响应表
		insertBr41_kzqq_dj_back(cg_Freeze, cs_Freeze);
		if (isemployee.equals("0")) { //不走人工
			// 插入任务task3
			/*
			 * int istaskok = br41_kzqqMapper.isTaskCount(br41_kzqq);
			 * if (istaskok == 0) {
			 * int isok = br41_kzqqMapper.getBr41_kzqq_dj_backCount(br41_kzqq);
			 * if (isok == 0) {
			 * br41_kzqq.setStatus("2"); //修改已提交
			 * br41_kzqqMapper.updateBr41_kzqq(br41_kzqq);
			 * mc21_task_fact.setBdhm(br41_kzqq.getQqdbs());
			 * insertMc21TaskFact3(mc21_task_fact, "CBRC_" + mc21_task_fact.getTasktype());
			 * }
			 * }
			 */
			insertMc21TaskFact3(mc21_task_fact, "JSGA_" + mc21_task_fact.getTasktype());
		}
		return cs_Freeze;
		
	}
	
	public void insertBr41_kzqq_zf_back(JSGA_StopPaymentResponse_Account cg_stoppay, JSGA_StopPaymentRequest_Recored cs_stoppay) throws Exception {
		
		br41_kzqqMapper.delBr41_kzqq_zf_back1(cg_stoppay);
		br41_kzqqMapper.delBr41_kzqq_zf_back_mx(cg_stoppay);
		/** 任务流水号 */
		cs_stoppay.setRwlsh(cg_stoppay.getRwlsh());
		/** 请求单标识 */
		cs_stoppay.setQqdbs(cg_stoppay.getQqdbs());
		/** 监管类别 */
		cs_stoppay.setTasktype(cg_stoppay.getTasktype());
		/** 查询日期 */
		cs_stoppay.setQrydt(DtUtils.getNowDate());
		
		// 账号
		if (cs_stoppay.getZh() == null || cs_stoppay.getZh().length() == 0) {
			cs_stoppay.setZh(cg_stoppay.getZh());
		}
		// 卡号
		if (cs_stoppay.getKh() == null || cs_stoppay.getKh().length() == 0) {
			cs_stoppay.setKh(cs_stoppay.getZh()); //
		}
		// 执行起始时间
		if (cs_stoppay.getZxqssj() == null || cs_stoppay.getZxqssj().length() == 0) {
			cs_stoppay.setZxqssj(Utility.currDateTime19());
		}
		/** 归属机构 */
		String orgkey = cg_stoppay.getOrgankey();
		cs_stoppay.setOrgankey(orgkey);
		// 插入止付响应表
		br41_kzqqMapper.insertBr41_kzqq_zf_back(cs_stoppay);
		
		// 插入止付请求反馈明细
		List<JSGA_StopPaymentRequest_Detail> detailList = cs_stoppay.getStopPaymentDetailList();
		if (detailList != null) {
			for (JSGA_StopPaymentRequest_Detail detail : detailList) {
				detail.setRwlsh(cg_stoppay.getRwlsh());
				detail.setQqdbs(cg_stoppay.getQqdbs());
				detail.setTasktype(cg_stoppay.getTasktype());
				br41_kzqqMapper.insertBr41_kzqq_zf_back_mx(detail);
			}
		}
		
	}
	
	public void insertBr41_kzqq_dj_back(JSGA_FreezeResponse_Account cg_freeze, JSGA_FreezeRequest_Record cs_freeze) throws Exception {
		
		br41_kzqqMapper.delBr41_kzqq_dj_back1(cg_freeze);
		br41_kzqqMapper.delBr41_kzqq_dj_back_mx(cg_freeze);
		/** 任务流水号 */
		cs_freeze.setRwlsh(cg_freeze.getRwlsh());
		/** 请求单标识 */
		cs_freeze.setQqdbs(cg_freeze.getQqdbs());
		/** 监管类别 */
		cs_freeze.setTasktype(cg_freeze.getTasktype());
		/** 查询日期 */
		cs_freeze.setQrydt(DtUtils.getNowDate());
		/** 归属机构 */
		cs_freeze.setZh(cg_freeze.getZh());
		if (cs_freeze.getZxqssj() == null || cs_freeze.getZxqssj().equals("")) {
			cs_freeze.setZxqssj(Utility.currDateTime19());
		}
		cs_freeze.setDjjsrq(cg_freeze.getJssj());
		
		if (StringUtils.isBlank(cs_freeze.getSqdjxe())) {
			cs_freeze.setSqdjxe(cg_freeze.getJe());
		}
		String orgkey = cg_freeze.getOrgankey();
		cs_freeze.setOrgankey(orgkey);
		// 插入冻结响应表
		br41_kzqqMapper.insertBr41_kzqq_dj_back(cs_freeze);
		
		// 插入冻结响应明细表
		List<JSGA_FreezeRequest_Detail> detailList = cs_freeze.getFreezeDetailList();
		if (detailList != null) {
			for (JSGA_FreezeRequest_Detail detail : detailList) {
				detail.setRwlsh(cg_freeze.getRwlsh());
				detail.setQqdbs(cg_freeze.getQqdbs());
				detail.setTasktype(cg_freeze.getTasktype());
				br41_kzqqMapper.insertBr41_kzqq_dj_back_mx(detail);
			}
		}
	}
	
	public JSGA_StopPaymentRequest_Recored getHxAppid_zf(JSGA_StopPaymentResponse_Account cg_StopPay) throws Exception {
		
		JSGA_StopPaymentRequest_Recored stoppay_back = new JSGA_StopPaymentRequest_Recored();
		
		stoppay_back = br41_kzqqMapper.selectBr41_kzqq_zf_backByVo(cg_StopPay);
		if (stoppay_back == null) {
			stoppay_back = new JSGA_StopPaymentRequest_Recored();
		}
		return stoppay_back;
		
	}
	
	public JSGA_FreezeRequest_Record getHxAppid_dj(JSGA_FreezeResponse_Account cg_Freeze) throws Exception {
		
		JSGA_FreezeRequest_Record _freeze_back = new JSGA_FreezeRequest_Record();
		
		_freeze_back = br41_kzqqMapper.selectBr41_kzqq_dj_backByVo(cg_Freeze);
		if (_freeze_back == null) {
			_freeze_back = new JSGA_FreezeRequest_Record();
		}
		return _freeze_back;
		
	}
	
	private Br41_kzqq getBr41_kzqq(MC21_task_fact mc21_task_fact) {
		Br41_kzqq br41_kzqq = new Br41_kzqq();
		br41_kzqq.setQqdbs(StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$"));// 请求单标识
		br41_kzqq.setTasktype(mc21_task_fact.getTasktype());// 监管类别
		br41_kzqq = br41_kzqqMapper.selectBr41_kzqqByVo(br41_kzqq);
		br41_kzqq.setRwlsh(StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$"));
		return br41_kzqq;
	}
	
}
