package com.citic.server.jsga.task.taskBo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.jsga.domain.Br41_kzqq;
import com.citic.server.jsga.domain.Br42_msg;
import com.citic.server.jsga.domain.JSGA_ReturnReceipt;
import com.citic.server.jsga.domain.request.JSGA_ExecResult;
import com.citic.server.jsga.domain.request.JSGA_FeedbackRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest_Detail;
import com.citic.server.jsga.domain.request.JSGA_FreezeRequest_Record;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Detail;
import com.citic.server.jsga.domain.request.JSGA_StopPaymentRequest_Recored;
import com.citic.server.jsga.domain.response.JSGA_FreezeResponse_Account;
import com.citic.server.jsga.domain.response.JSGA_StopPaymentResponse_Account;
import com.citic.server.jsga.mapper.MM40_cxqq_jsgaMapper;
import com.citic.server.jsga.mapper.MM41_kzqq_jsgaMapper;
import com.citic.server.jsga.service.RequestMessageService12;
import com.citic.server.runtime.Constants12;
import com.citic.server.runtime.JsgaKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FileUtils;

public class Kzqq_FKBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Kzqq_FKBo.class);
	
	private MM40_cxqq_jsgaMapper br40_cxqqMapper;
	
	private MM41_kzqq_jsgaMapper br41_kzqqMapper;
	
	public Kzqq_FKBo(ApplicationContext ac) {
		super(ac);
		this.br41_kzqqMapper = (MM41_kzqq_jsgaMapper) ac.getBean("MM41_kzqq_jsgaMapper");
		this.br40_cxqqMapper = (MM40_cxqq_jsgaMapper) ac.getBean("MM40_cxqq_jsgaMapper");
	}
	
	/**
	 * 反馈冻结控制任务单 -- task3
	 * 
	 * @param mc21_task_fact
	 * @param taskmag
	 * @throws Exception
	 */
	public void FeedBackControl_dj(MC21_task_fact mc21_task_fact) throws Exception {
		String taskType = mc21_task_fact.getTasktype();
		
		JSGA_FreezeResponse_Account cg_Freeze = new JSGA_FreezeResponse_Account();
		
		String qqdbs = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
		String rwlsh = StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$");
		Br41_kzqq _kzqq = new Br41_kzqq();
		_kzqq.setTasktype(taskType);
		_kzqq.setQqdbs(qqdbs);
		Br41_kzqq br41_kzqq = br41_kzqqMapper.selectBr41_kzqqByVo(_kzqq);
		
		cg_Freeze.setTasktype(taskType);
		cg_Freeze.setQqdbs(qqdbs);
		cg_Freeze.setRwlsh(rwlsh);
		
		List<JSGA_FreezeRequest_Record> _freeze_backList = br41_kzqqMapper.selectBr41_kzqq_dj_backByList(cg_Freeze);
		
		//2.feedbackinfo反馈信息: request01
		JSGA_FreezeRequest request01 = new JSGA_FreezeRequest();
		request01.setQqdbs(qqdbs);
		request01.setZtlb(br41_kzqq.getZtlb());
		request01.setSqjgdm(br41_kzqq.getSqjgdm());
		request01.setMbjgdm(br41_kzqq.getMbjgdm());
		request01.setHzsj(DtUtils.getNowTime());
		request01.setSfxd("01");
		//request01.setFreezeRecordList(_freeze_backList);
		
		if (_freeze_backList != null && _freeze_backList.size() > 0) {
			for (JSGA_FreezeRequest_Record _freeze : _freeze_backList) {
				List<JSGA_FreezeRequest_Record> freeze_backList = new ArrayList<JSGA_FreezeRequest_Record>();
				freeze_backList.add(_freeze);
				
				String xmltype_y = "SS17";
				if ("01".equals(_freeze.getZtlb())) {
					xmltype_y = "SS17";
				} else {
					xmltype_y = "SS18";
				}
				request01.setZtlb(_freeze.getZtlb());
				String xmltype = Constants12.valueOf(xmltype_y);
				
				// 特殊字段：基本数据项 - 操作失败原因
				if ("0".equals(_freeze.getZxjg())) { // 执行结果 = 0（执行成功）
					request01.setCzsbyy("");
				} else {
					request01.setCzsbyy("失败");
				}
				
				// 处理冻结请求反馈明细
				dealJSGA_FreezeRequest_Detail(freeze_backList);
				request01.setFreezeRecordList(freeze_backList);
				
				// 2.1生成XML
				String path = generateXMLDocument(request01, br41_kzqq, xmltype, "binding_jsga_freeze_req", mc21_task_fact.getTasktype());
				
				// 2.2写表BR42_MSG
				br41_kzqq.setRwlsh(_freeze.getRwlsh());
				insertBr42_msg(br41_kzqq, path, br41_kzqq.getMbjgdm());
				
				deal(path, qqdbs, rwlsh, taskType, br41_kzqq.getQqcslx());
				
				// 4.置反馈表状态
				_freeze.setStatus("6"); // 2.已生成报文
				br41_kzqqMapper.updateBr41_kzqq_dj_back(_freeze);
			}
		}
		// 置主表状态
		br41_kzqq.setStatus("2"); // 已处理
		br41_kzqqMapper.updateBr41_kzqq(br41_kzqq);
		
	}
	
	private void deal(String path, String qqdbs, String rwlsh, String taskType, String qqcslx) {
		logger.info("deal---xmlpath[{}],cxlx[{}],isAccount[{}],bdhm[{}],tasktype[{}]", path, qqdbs, rwlsh, taskType, qqcslx);
		RequestMessageService12 service = SpringContextHolder.getBean("requestMessageService12");
		String receipt = service.sendControlResult(path, qqcslx,false);
		logger.info("反馈code {}", receipt);
		JSGA_ReturnReceipt returnReceipt = new JSGA_ReturnReceipt();
		if (!"1000".equals(receipt.trim())) {
			returnReceipt.setQqdbs(qqdbs); // 请求单标识
			returnReceipt.setHzdm(receipt); // 回执代码
			returnReceipt.setJssj(Utility.currDateTime19()); // 接收时间
			returnReceipt.setHzsm("未知错误"); // 回执说明
			returnReceipt.setTasktype(taskType); // 任务类型
			returnReceipt.setRwlsh(rwlsh);
		} else {
			returnReceipt.setQqdbs(qqdbs); // 请求单标识
			returnReceipt.setTasktype(taskType); // 任务类型
			returnReceipt.setHzdm(receipt);
			returnReceipt.setJssj(Utility.currDateTime19());
			returnReceipt.setHzsm("成功反馈"); // 回执说明
			returnReceipt.setTasktype(taskType);
			returnReceipt.setRwlsh(rwlsh);
			
		}
		br40_cxqqMapper.delBr40_receipt(returnReceipt);
		br40_cxqqMapper.insertBr40_receipt(returnReceipt);
	}
	
	/**
	 * 反馈止付控制任务单 -- task3
	 * 
	 * @param mc21_task_fact
	 * @param taskmag
	 * @throws Exception
	 */
	public void FeedBackControl_zf(MC21_task_fact mc21_task_fact) throws Exception {
		String taskType = mc21_task_fact.getTasktype();
		
		JSGA_StopPaymentResponse_Account cg_stoppay = new JSGA_StopPaymentResponse_Account();
		String qqdbs = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
		String rwlsh = StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$");
		Br41_kzqq _kzqq = new Br41_kzqq();
		_kzqq.setTasktype(taskType);
		_kzqq.setQqdbs(qqdbs);
		Br41_kzqq br41_kzqq = br41_kzqqMapper.selectBr41_kzqqByVo(_kzqq);
		
		cg_stoppay.setTasktype(taskType);
		cg_stoppay.setQqdbs(qqdbs);
		cg_stoppay.setRwlsh(rwlsh);
		List<JSGA_StopPaymentRequest_Recored> _stoppay_backList = br41_kzqqMapper.selectBr41_kzqq_zf_backByList(cg_stoppay);
		
		//1.feedbackinfo反馈信息: request01
		JSGA_StopPaymentRequest request01 = new JSGA_StopPaymentRequest();
		request01.setQqdbs(qqdbs);
		request01.setZtlb(br41_kzqq.getZtlb());
		request01.setSqjgdm(br41_kzqq.getSqjgdm());
		request01.setMbjgdm(br41_kzqq.getMbjgdm());
		request01.setHzsj(DtUtils.getNowTime());
		//request01.setStopPaymentRecoredList(_stoppay_backList);
		String path = "";
		
		if (_stoppay_backList != null && _stoppay_backList.size() > 0) {
			for (JSGA_StopPaymentRequest_Recored _stoppay : _stoppay_backList) {
				List<JSGA_StopPaymentRequest_Recored> stoppay_backList = new ArrayList<JSGA_StopPaymentRequest_Recored>();
				stoppay_backList.add(_stoppay);
				
				String xmltype_y = "SS21";
				if ("01".equals(_stoppay.getZtlb())) {
					xmltype_y = "SS21";
				} else {
					xmltype_y = "SS22";
				}
				request01.setZtlb(_stoppay.getZtlb());
				String xmltype = Constants12.valueOf(xmltype_y);
				
				// 特殊字段：基本数据项 - 操作失败原因
				if ("0".equals(_stoppay.getZxjg())) { // 执行结果 = 0（执行成功）
					request01.setCzsbyy("");
				} else {
					request01.setCzsbyy("失败");
				}
				
				//处理止付请求反馈明细
				dealJSGA_StopPaymentRequest_Detail(stoppay_backList);
				request01.setStopPaymentRecoredList(stoppay_backList);
				
				// 2.1生成XML
				path = generateXMLDocument(request01, br41_kzqq, xmltype, "binding_jsga_stoppayment_req", mc21_task_fact.getTasktype());
				
				// 2.2写表BR42_MSG
				br41_kzqq.setRwlsh(_stoppay.getRwlsh());
				insertBr42_msg(br41_kzqq, path, br41_kzqq.getMbjgdm());
				
				
				deal(path, qqdbs, rwlsh, taskType, br41_kzqq.getQqcslx());
				// 4.置反馈表状态
				_stoppay.setStatus("6"); // 2.已生成报文
				br41_kzqqMapper.updateBr41_kzqq_zf_back(_stoppay);
			}
			
		}
		// 置主表状态
		br41_kzqq.setStatus("2"); // 已处理
		br41_kzqqMapper.updateBr41_kzqq(br41_kzqq);
		
	}
	
	public void dealReturnReceipt29(Br41_kzqq br41_kzqq, String type) throws Exception {
		
		JSGA_ExecResult execResult = new JSGA_ExecResult();
		
		execResult.setQqdbs(br41_kzqq.getQqdbs());
		execResult.setQqcslx(br41_kzqq.getQqcslx());
		execResult.setMbjgdm(br41_kzqq.getMbjgdm());
		execResult.setSqjgdm(br41_kzqq.getSqjgdm());
		execResult.setHzsj(Utility.currDateTime14());
		if (type.equals("zf")) {
			JSGA_StopPaymentResponse_Account stappay = new JSGA_StopPaymentResponse_Account();
			stappay.setTasktype(br41_kzqq.getTasktype());
			stappay.setQqdbs(br41_kzqq.getQqdbs());
			List<JSGA_StopPaymentRequest_Recored> zfList = br41_kzqqMapper.selectBr41_kzqq_zf_backByList(stappay);
			execResult.setJsrws(zfList.size() + "");
			execResult.setFkrws(zfList.size() + "");
		} else {
			JSGA_FreezeResponse_Account freeze = new JSGA_FreezeResponse_Account();
			freeze.setTasktype(br41_kzqq.getTasktype());
			freeze.setQqdbs(br41_kzqq.getQqdbs());
			List<JSGA_FreezeRequest_Record> djList = br41_kzqqMapper.selectBr41_kzqq_dj_backByList(freeze);
			execResult.setJsrws(djList.size() + "");
			execResult.setFkrws(djList.size() + "");
		}
		execResult.setFkkhs("0");
		execResult.setFkzhs("0");
		execResult.setFkjymxs("0");
		
		String xmlPath = generateXMLDocument(execResult, br41_kzqq, "RR29", "jsga_execresult_req", br41_kzqq.getTasktype());
		
		// 文件地址入库
		br41_kzqq.setRwlsh("RR29");
		insertBr42_msg(br41_kzqq, xmlPath, br41_kzqq.getMbjgdm());
	}
	
	public List<JSGA_FeedbackRequest_Record> getWsInfo(Br41_kzqq br41_kzqq) throws Exception {
		List<JSGA_FeedbackRequest_Record> liInfoList = br41_kzqqMapper.selectBr41_cxqq_hzwsList(br41_kzqq);
		return liInfoList;
	}
	
	/**
	 * 生成查询反馈报文XML
	 * 
	 * @param mc21_task_fact
	 * @param qs_zhxxlist
	 * @throws Exception
	 */
	public String generateXMLDocument(Object request, Br41_kzqq br41_kzqq, String xmltype, String bindingname, String taskType) throws Exception {
		String absolutePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_ATTACH, JsgaKeys.FILE_DIRECTORY_12);
		absolutePath = absolutePath + File.separator + br41_kzqq.getQqdbs();
		String mbjgdm = br41_kzqq.getMbjgdm(); // 目标机构代码（银行代码）
		String filename = this.getMsgNameCBRC(br41_kzqq.getTasktype(), xmltype, br41_kzqq.getQqdbs(), mbjgdm) + ".xml";
		CommonUtils.marshallUTF8Document(request, bindingname, absolutePath, filename);
		
		return absolutePath + File.separator + filename;
	}
	
	public void insertBr42_msg(Br41_kzqq br41_kzqq, String path, String organkey_r) throws Exception {
		Br42_msg msg = new Br42_msg();
		
		String filename = FileUtils.getFilenameFromPath(path);
		msg.setMsgkey(this.geSequenceNumber("SEQ_BR42_MSG"));
		msg.setBdhm(br41_kzqq.getRwlsh());
		msg.setMsg_type_cd(filename.substring(0, 4));
		msg.setPacketkey(br41_kzqq.getQqdbs());
		if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(br41_kzqq.getTasktype())) {
			msg.setOrgankey_r(organkey_r.substring(0, 8));
		} else {
			msg.setOrgankey_r(organkey_r);
		}
		msg.setSenddate(DtUtils.getNowDate());
		msg.setMsg_filename(filename);
		msg.setMsg_filepath(path);
		msg.setStatus_cd("0"); //报文状态 0:待打包 1:已打包
		msg.setCreate_dt(DtUtils.getNowTime());
		msg.setQrydt(DtUtils.getNowDate());
		msg.setTasktype(br41_kzqq.getTasktype());
		br41_kzqqMapper.delBr42_msg(msg);
		br41_kzqqMapper.insertBr42_msg(msg);
	}
	
	public void dealJSGA_StopPaymentRequest_Detail(List<JSGA_StopPaymentRequest_Recored> _stoppay_backList) {
		for (JSGA_StopPaymentRequest_Recored stoppay : _stoppay_backList) {
			List<JSGA_StopPaymentRequest_Detail> detailList = br41_kzqqMapper.selectBr41_kzqq_zf_back_mxByList(stoppay);
			stoppay.setStopPaymentDetailList(detailList);
		}
	}
	
	public void dealJSGA_FreezeRequest_Detail(List<JSGA_FreezeRequest_Record> _freeze_backList) {
		for (JSGA_FreezeRequest_Record freeze : _freeze_backList) {
			List<JSGA_FreezeRequest_Detail> detailList = br41_kzqqMapper.selectBr41_kzqq_dj_back_mxByList(freeze);
			freeze.setFreezeDetailList(detailList);
		}
	}
}
