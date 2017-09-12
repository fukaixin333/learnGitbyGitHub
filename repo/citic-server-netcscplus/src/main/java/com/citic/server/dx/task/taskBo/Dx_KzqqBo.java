package com.citic.server.dx.task.taskBo;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.TxConstants;
import com.citic.server.dx.domain.Bg_Q_Attach;
import com.citic.server.dx.domain.Br22_StopPay;
import com.citic.server.dx.domain.Br25_Freeze;
import com.citic.server.dx.domain.Br25_Freeze_back;
import com.citic.server.dx.domain.Br25_StopPay;
import com.citic.server.dx.domain.Br25_StopPay_back;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.domain.request.FreezeRequest100202;
import com.citic.server.dx.domain.request.FreezeRequest100204;
import com.citic.server.dx.domain.request.FreezeRequest100206;
import com.citic.server.dx.domain.request.StoppayRequest100102;
import com.citic.server.dx.domain.request.StoppayRequest100104;
import com.citic.server.dx.domain.request.StoppayRequest100106;
import com.citic.server.dx.domain.response.FreezeResponse100201;
import com.citic.server.dx.domain.response.FreezeResponse100203;
import com.citic.server.dx.domain.response.FreezeResponse100205;
import com.citic.server.dx.domain.response.StoppayResponse100101;
import com.citic.server.dx.domain.response.StoppayResponse100103;
import com.citic.server.dx.domain.response.StoppayResponse100105;
import com.citic.server.dx.service.CodeChange;
import com.citic.server.dx.service.DataOperate2;
import com.citic.server.net.mapper.BR25_kzqqMapper;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DbFuncUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;

public class Dx_KzqqBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Dx_KzqqBo.class);
	private BR25_kzqqMapper br25_kzqqMapper;
	
	/** 数据获取接口 */
	private DataOperate2 dataOperate;
	
	private CodeChange codeChange;
	
	public Dx_KzqqBo(ApplicationContext ac) {
		super(ac);
		br25_kzqqMapper = (BR25_kzqqMapper) ac.getBean("BR25_kzqqMapper");
		this.codeChange = (CodeChange) ac.getBean("codeChangeImpl");
	}
	
	/**
	 * 删除请求单号下的信息
	 * 
	 * @param bdhm
	 * @throws Exception
	 */
	public void delKzqqBw_zf(String transserialnumber) throws Exception {
		//删除请求表
		br25_kzqqMapper.delBr25_kzqq_zf(transserialnumber);
		br25_kzqqMapper.delKzResBw_zf(transserialnumber);
	}
	
	public void delKzqqBw_dj(String transserialnumber) throws Exception {
		//删除请求表
		br25_kzqqMapper.delBr25_kzqq_dj(transserialnumber);
		//删除响应表
		br25_kzqqMapper.delKzResBw_dj(transserialnumber);
	}
	
	public void analyKzqqBw(String root, MC20_task_msg taskmag, String flag, MC21_task_fact mc21_task_fact) throws Exception {
		
		if (taskmag != null && !taskmag.getMsgpath().equals("")) {
			String qq_path = root + taskmag.getMsgpath();
			
			//解析请求
			if (flag.equals("1")) { //止付
				this.analyKzqq_zf(qq_path, mc21_task_fact, taskmag);
			} else {//冻结
				this.analyKzqq_dj(qq_path, mc21_task_fact, taskmag);
			}
		}
		
	}
	
	/**
	 * 解析请求报文
	 * 
	 * @param bdhm
	 * @param bwPath
	 * @throws Exception
	 */
	public void analyKzqq_zf(String filename, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		Br25_StopPay_back cs_StopPay = new Br25_StopPay_back();
		
		Br25_StopPay cg_StopPay = new Br25_StopPay();
		String txcode = mc21_task_fact.getSubtaskid();
		if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT)) { //止付
			StoppayResponse100101 cg_stoppay_100101 = (StoppayResponse100101) CommonUtils.unmarshallUTF8Document(StoppayResponse100101.class, filename);
			if (cg_stoppay_100101.getOriginalApplicationID() == null) {
				cg_stoppay_100101.setOriginalApplicationID("");
			}
			BeanUtils.copyProperties(cg_StopPay, cg_stoppay_100101);
			String type = "1";
			String applicationtype = cg_StopPay.getApplicationType();
			if (applicationtype.equals("01") || applicationtype.equals("03") || applicationtype.equals("05")) {
				type = "3"; //账号
			}
			String accounttype = cg_StopPay.getAccountType();
			mc21_task_fact.setFacttablename(cg_StopPay.getAccountName() + "#" + taskmag.getPacketkey());
			this.insertOrgMc21TaskFact2(mc21_task_fact, cg_StopPay.getAccountNumber(), accounttype, type, "1");
		}
		if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_RELIEVE)) { //止付解除
			StoppayResponse100103 cg_stoppay_100103 = (StoppayResponse100103) CommonUtils.unmarshallUTF8Document(StoppayResponse100103.class, filename);
			BeanUtils.copyProperties(cg_StopPay, cg_stoppay_100103);
			cg_StopPay.setRemark(cg_stoppay_100103.getWithdrawalRemark());
			
		}
		if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_POSTPONE)) { //止付延期
			StoppayResponse100105 cg_stoppay_100105 = (StoppayResponse100105) CommonUtils.unmarshallUTF8Document(StoppayResponse100105.class, filename);
			BeanUtils.copyProperties(cg_StopPay, cg_stoppay_100105);
			cg_StopPay.setRemark(cg_stoppay_100105.getExtendRemark());
			cg_StopPay.setStartTime(cg_stoppay_100105.getPostponeStartTime());
		}
		
		cg_StopPay.setCreate_dt(DtUtils.getNowTime());
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee != null && isemployee.equals("0")) {
			cg_StopPay.setStatus_cd("1");
		} else {
			cg_StopPay.setStatus_cd("0");
		}
		cg_StopPay.setTransSerialNumber(cg_StopPay.getTransSerialNumber());
		cg_StopPay.setQrydt(DtUtils.getNowDate());
		String currtime = DtUtils.getNowTime();
		cg_StopPay.setLast_up_dt(currtime);
		cg_StopPay.setRecipient_time(currtime);
		cg_StopPay.setOrgkey(mc21_task_fact.getTaskobj());
		String result = "";
		if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_RELIEVE) || txcode.equals(TxConstants.TXCODE_STOPPAYMENT_POSTPONE)) {
			//止付解除//止付延期机构取原机构
			Br25_StopPay_back br25_stoppay_back = this.getHxAppid_zf(cg_StopPay);
			if (br25_stoppay_back != null && !br25_stoppay_back.getOrgkey().equals("")) {
				String orgkey = br25_stoppay_back.getOrgkey();
				cg_StopPay.setOrgkey(orgkey);
			} else {
				result = "0400";
				cg_StopPay.setStatus_cd("2");
				cs_StopPay.setStatus_cd("2");
			}
		}
		//插入控制请求账户表
		br25_kzqqMapper.insertBr25_kzqq_zf(cg_StopPay);
		
		//插入附件
		this.insertBr25_frozen_attach(cg_StopPay.getTransSerialNumber(), taskmag);
		
		String flag = "1";
		if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT) && result.equals("")) { //止付
			String applicationType = cg_StopPay.getApplicationType();
			if (applicationType.equals("04") && applicationType.equals("05")) { //人工举报后补止付的时候不发核心从人工录入中取
				String originalapplicationid = cg_StopPay.getOriginalApplicationID();
				List<Br22_StopPay> br22_stoppayList = br25_kzqqMapper.getBr22_StopPayByID(originalapplicationid);
				if (br22_stoppayList != null && br22_stoppayList.size() > 0) {
					Br22_StopPay br22_stoppay = br22_stoppayList.get(0);
					BeanUtils.copyProperties(cs_StopPay, br22_stoppay);
				}
				flag = "0";
			}
		}
		cs_StopPay.setResult(result);
		//插入响应
		cs_StopPay.setMsgcheckresult("1");
		this.insertKzXy_zf(cg_StopPay, cs_StopPay);
		if (flag.equals("1") && result.equals("")) {//人工举报后补止付的时候不发核心所有不差人task2
			if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_RELIEVE) || txcode.equals(TxConstants.TXCODE_STOPPAYMENT_POSTPONE)) {
				if (mc21_task_fact.getIsemployee().equals("0")) {
					this.insertMc21TaskFact2Fo(mc21_task_fact,"DX");
				}
			}else{
			//3.插入task2
			this.insertMc21TaskFact2(mc21_task_fact, "DX");
			}
		}
		if (!result.equals("")) {//找不到原始交易直接插入task3
			mc21_task_fact.setIsemployee("0");
			//3.插入task3
			this.insertMc21TaskFact3(mc21_task_fact, "DX");
		} else {
			if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_RELIEVE) || txcode.equals(TxConstants.TXCODE_STOPPAYMENT_POSTPONE)) {
				if (mc21_task_fact.getIsemployee().equals("1")) {
					//发送短信
					this.sendMsg(cg_StopPay.getOrgkey(), mc21_task_fact.getTaskname(), mc21_task_fact.getSubtaskid(), "3", mc21_task_fact.getTasktype(), "1");
				}
			}
		}
		
	}
	
	public void analyKzqq_dj(String filename, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		Br25_Freeze_back cs_Freeze = new Br25_Freeze_back();
		
		Br25_Freeze cg_Freeze = new Br25_Freeze();
		String txcode = mc21_task_fact.getSubtaskid();
		String applicationType = "";
		if (txcode.equals(TxConstants.TXCODE_FREEZE)) { //冻结
			FreezeResponse100201 cg_Freeze_100201 = (FreezeResponse100201) CommonUtils.unmarshallUTF8Document(FreezeResponse100201.class, filename);
			BeanUtils.copyProperties(cg_Freeze, cg_Freeze_100201);
			applicationType = cg_Freeze.getApplicationType();
			String accountType = cg_Freeze.getAccounttype();
			if (applicationType == null || applicationType.equals("")) {
				if (cg_Freeze.getAccountNumber().indexOf("_") > 0) {
					applicationType = "01";
				} else {
					applicationType = "00";
				}
			}
			cg_Freeze.setApplicationType(applicationType);
			String type = "3";
			if (applicationType.equals("00") || applicationType.equals("02")) {
				type = "1";
			}
			mc21_task_fact.setFacttablename(cg_Freeze.getAccountName() + "#" + taskmag.getPacketkey());
			this.insertOrgMc21TaskFact2(mc21_task_fact, cg_Freeze.getAccountNumber(), accountType, type, "0");
			
		}
		if (txcode.equals(TxConstants.TXCODE_FREEZE_RELIEVE)) { //冻结解除
			FreezeResponse100203 cg_Freeze_100203 = (FreezeResponse100203) CommonUtils.unmarshallUTF8Document(FreezeResponse100203.class, filename);
			BeanUtils.copyProperties(cg_Freeze, cg_Freeze_100203);
			cg_Freeze.setRemark(cg_Freeze_100203.getWithdrawalRemark());
			
		}
		if (txcode.equals(TxConstants.TXCODE_FREEZE_POSTPONE)) { //冻结延期
			FreezeResponse100205 cg_Freeze_100205 = (FreezeResponse100205) CommonUtils.unmarshallUTF8Document(FreezeResponse100205.class, filename);
			BeanUtils.copyProperties(cg_Freeze, cg_Freeze_100205);
			cg_Freeze.setRemark(cg_Freeze_100205.getExtendRemark());
			cg_Freeze.setFreezeStartTime(cg_Freeze_100205.getPostponeStartTime());
		}
		
		cg_Freeze.setCreate_dt(DtUtils.getNowTime());
		String isemployee = mc21_task_fact.getIsemployee();
		if (isemployee != null && isemployee.equals("0")) {
			cg_Freeze.setStatus_cd("1");
		} else {
			cg_Freeze.setStatus_cd("0");
		}
		cg_Freeze.setTransSerialNumber(cg_Freeze.getTransserialnumber());
		if (cg_Freeze.getOriginalapplicationid() == null) {
			cg_Freeze.setOriginalApplicationID("");
		}
		cg_Freeze.setQrydt(DtUtils.getNowDate());
		String currdate = DtUtils.getNowTime();
		cg_Freeze.setLast_up_dt(currdate);
		cg_Freeze.setRecipient_time(currdate);
		cg_Freeze.setOrgkey(mc21_task_fact.getTaskobj());
		String result = "";
		if (txcode.equals(TxConstants.TXCODE_FREEZE_RELIEVE) || txcode.equals(TxConstants.TXCODE_FREEZE_POSTPONE)) { //冻结解除 //冻结延期
			Br25_Freeze_back br25_freeze_back = this.getHxAppid_dj(cg_Freeze);
			if (br25_freeze_back != null && !br25_freeze_back.getOrgkey().equals("")) {
				String orgkey = br25_freeze_back.getOrgkey();
				cg_Freeze.setOrgkey(orgkey);
				
			} else {
				result = "0400";
				cg_Freeze.setStatus_cd("2");
				cs_Freeze.setStatus_cd("2");
			}
		}
		//插入控制请求账户表
		br25_kzqqMapper.insertBr25_kzqq_dj(cg_Freeze);
		//插入附件
		this.insertBr25_frozen_attach(cg_Freeze.getTransserialnumber(), taskmag);
		
		BeanUtils.copyProperties(cs_Freeze, cg_Freeze);
		// 3.插入冻结响应表
		cs_Freeze.setMsgcheckresult("1");
		this.insertKzXy_dj(cg_Freeze, cs_Freeze);
		
		if (result.equals("")) {
			if (txcode.equals(TxConstants.TXCODE_FREEZE_RELIEVE) || txcode.equals(TxConstants.TXCODE_FREEZE_POSTPONE)) { //冻结解除 //冻结延期
				if (mc21_task_fact.getIsemployee().equals("0")) {
					this.insertMc21TaskFact2Fo(mc21_task_fact,"DX");
				}
			}else{
			//3.插入task2
			this.insertMc21TaskFact2(mc21_task_fact, "DX");
			}
			if (txcode.equals(TxConstants.TXCODE_FREEZE_RELIEVE) || txcode.equals(TxConstants.TXCODE_FREEZE_POSTPONE)) { //冻结解除 //冻结延期 
				if (mc21_task_fact.getIsemployee().equals("1")) {
					//发送短信
					this.sendMsg(cg_Freeze.getOrgkey(), mc21_task_fact.getTaskname(), mc21_task_fact.getSubtaskid(), "3", mc21_task_fact.getTasktype(), "1");
				}
			}
		} else {
			//3.插入task3
			mc21_task_fact.setIsemployee("0");
			this.insertMc21TaskFact3(mc21_task_fact, "DX");
		}
		
	}
	
	public void insertKzXy_zf(Br25_StopPay cg_stoppay, Br25_StopPay_back cs_stoppay) throws Exception {
		
		String txcode = cg_stoppay.getTxCode();
		cs_stoppay.setTxCode(String.valueOf(Integer.parseInt(txcode) + 1)); //发送编码
		cs_stoppay.setTransSerialNumber(cg_stoppay.getTransSerialNumber());
		cs_stoppay.setFeedback_dt(DtUtils.getNowTime());
		cs_stoppay.setFeedbackOrgName(cg_stoppay.getBankName());
		cs_stoppay.setToorg(cg_stoppay.getMessageFrom());
		cs_stoppay.setApplicationID(cg_stoppay.getApplicationID());
		cs_stoppay.setAccountNumber(cg_stoppay.getAccountNumber());
		cs_stoppay.setAccountType(cg_stoppay.getAccountType());
		cs_stoppay.setCurrency(cg_stoppay.getCurrency());
		cs_stoppay.setQrydt(DtUtils.getNowDate());
		cs_stoppay.setLast_up_dt(DtUtils.getNowTime());
		String orgkey = cg_stoppay.getOrgkey();
		cs_stoppay.setOrgkey(orgkey);
		if (cs_stoppay.getStartTime() == null || cs_stoppay.getStartTime().equals("")) {
			cs_stoppay.setStartTime(cg_stoppay.getStartTime());
		}
		if (cs_stoppay.getEndTime() == null || cs_stoppay.getEndTime().equals("")) {
			cs_stoppay.setEndTime(cg_stoppay.getExpireTime());
		}
		
		if ("100103".equals(cg_stoppay.getTxCode())) {
			cs_stoppay.setStartTime(Utility.currDateTime19());// 止付解除生效时间
		}
		
		//获取经办人和电话取止付机构的联系人和电话
		MP02_rep_org_map rep_org = this.getMp02_organPerson("2", orgkey);
		cs_stoppay.setOperatorName(rep_org.getOperatorname());
		cs_stoppay.setOperatorPhoneNumber(rep_org.getOperatorphonenumber());
		//插入止付响应表
		
		br25_kzqqMapper.insertBr25_stop_back(cs_stoppay);
		
	}
	
	/*
	 * public void insertBr25_frozen_attach(String transserialnumber, Cg_Freeze cg_Freeze, String
	 * root) throws Exception {
	 * br25_kzqqMapper.delBr25_Attach(transserialnumber);
	 * DbFuncUtils dbfunc = new DbFuncUtils();
	 * List<Bg_Q_Attach> attachList = cg_Freeze.getAttachments();
	 * for (Bg_Q_Attach bg_q_attach : attachList) {
	 * byte[] attachnr = bg_q_attach.getContent();
	 * String path = root + "/dx/cg_Freeze/" + DtUtils.getNowDate();
	 * // 生成附件
	 * String filepath = CommonUtils.writeBinaryFile(attachnr, path, bg_q_attach.getFilename());
	 * bg_q_attach.setCreate_dt(DtUtils.getNowTime());
	 * bg_q_attach.setFilepath(filepath);
	 * bg_q_attach.setTransserialnumber(transserialnumber);
	 * bg_q_attach.setFileno(this.geSequenceNumber("SEQ_BR25_FROZEN_ATTACH"));
	 * br25_kzqqMapper.insertBr25_frozen_attach(bg_q_attach);
	 * }
	 * }
	 */
	
	public void insertBr25_frozen_attach(String transserialnumber, MC20_task_msg taskmag) throws Exception {
		
		br25_kzqqMapper.delBr25_Attach(transserialnumber);
		DbFuncUtils dbfunc = new DbFuncUtils();
		String attachName = taskmag.getAttachname();
		String attachPath = taskmag.getAttachpath();
		String[] attachNames = attachName.split(";");
		String[] attachPaths = attachPath.split(";");
		Bg_Q_Attach bg_q_attach = new Bg_Q_Attach();
		for (int i = 0; i < attachNames.length; i++) {
			bg_q_attach.setFilename(attachNames[i]);
			bg_q_attach.setCreate_dt(DtUtils.getNowTime());
			bg_q_attach.setFilepath(attachPaths[i]);
			bg_q_attach.setTransserialnumber(transserialnumber);
			bg_q_attach.setFileno(this.geSequenceNumber("SEQ_BR25_FROZEN_ATTACH"));
			br25_kzqqMapper.insertBr25_frozen_attach(bg_q_attach);
		}
	}
	
	public void insertKzXy_dj(Br25_Freeze cg_Freeze, Br25_Freeze_back cs_Freeze) throws Exception {
		
		String txcode = cg_Freeze.getTxcode();
		cs_Freeze.setTxCode(String.valueOf(Integer.parseInt(txcode) + 1)); //编码变了
		cs_Freeze.setTransSerialNumber(cg_Freeze.getTransserialnumber());
		cs_Freeze.setFeedback_dt(DtUtils.getNowTime());
		cs_Freeze.setFeedbackOrgName(cg_Freeze.getBankname());
		cs_Freeze.setTo(cg_Freeze.getMessagefrom());
		cs_Freeze.setApplicationID(cg_Freeze.getApplicationid());
		cs_Freeze.setAccountType(cg_Freeze.getAccountType());
		cs_Freeze.setQrydt(DtUtils.getNowDate());
		cs_Freeze.setLast_up_dt(DtUtils.getNowTime());
		cs_Freeze.setFreezeType(cg_Freeze.getFreezeType());
		cs_Freeze.setCurrency(cg_Freeze.getCurrency());
		cs_Freeze.setAccountNumber(cg_Freeze.getAccountNumber());
		if (cs_Freeze.getStartTime() == null || cs_Freeze.getStartTime().equals("")) {
			cs_Freeze.setStartTime(cg_Freeze.getFreezeStartTime());
		}
		if (cs_Freeze.getEndTime() == null || cs_Freeze.getEndTime().equals("")) {
			cs_Freeze.setEndTime(cg_Freeze.getExpireTime());
		}
		if (cs_Freeze.getAppliedBalance() == null || cs_Freeze.getAppliedBalance().equals("")) {
			cs_Freeze.setAppliedBalance(cg_Freeze.getBalance());
		}
		if ("100203".equals(cg_Freeze.getTxCode())) {
			cs_Freeze.setStartTime(Utility.currDateTime19());// 冻结解除生效时间
			cs_Freeze.setWithdrawalTime(Utility.currDateTime19());
		}
		if (cg_Freeze.getAccountType().equals("02")) {//对公卡号为空
			cs_Freeze.setCardNumber("");
		}
		String orgkey = cg_Freeze.getOrgkey();
		cs_Freeze.setOrgkey(orgkey);
		//获取经办人和电话取止付机构的联系人和电话
		MP02_rep_org_map rep_org = this.getMp02_organPerson("2", orgkey);
		cs_Freeze.setOperatorName(rep_org.getOperatorname());
		cs_Freeze.setOperatorPhoneNumber(rep_org.getOperatorphonenumber());
		
		// 码值转换
		codeChange.chgCode(cs_Freeze, "BR24_ACCOUNT_FREEZE", this.query_datasource);
		//插入冻结响应表
		br25_kzqqMapper.insertBr25_frozen_back(cs_Freeze);
		
	}
	
	public Br25_Freeze_back getHxAppid_dj(Br25_Freeze cg_Freeze) throws Exception {
		
		Br25_Freeze_back br25_freeze_back = new Br25_Freeze_back();
		br25_freeze_back.setApplicationID(cg_Freeze.getOriginalApplicationID());
		br25_freeze_back.setToorg(cg_Freeze.getMessageFrom());
		br25_freeze_back = br25_kzqqMapper.getBr25_frozenBackByID(br25_freeze_back);
		
		return br25_freeze_back;
		
	}
	
	public Br25_StopPay_back getHxAppid_zf(Br25_StopPay cg_StopPay) throws Exception {
		
		Br25_StopPay_back br25_stoppay_back = new Br25_StopPay_back();
		br25_stoppay_back.setApplicationID(cg_StopPay.getOriginalApplicationID());
		br25_stoppay_back.setToorg(cg_StopPay.getMessageFrom());
		br25_stoppay_back = br25_kzqqMapper.getBr25_stopBackByID(br25_stoppay_back);
		if (br25_stoppay_back == null) {
			br25_stoppay_back = new Br25_StopPay_back();
		}
		return br25_stoppay_back;
		
	}
	
	/*
	 * public String makeXml_zf(Cs_StopPay cs_stoppay) throws Exception {
	 * String returnStr = "";
	 * String txcode = cs_stoppay.getTxcode();
	 * if (txcode.equals("100102")) { //止付反馈
	 * returnStr = CommonUtils.marshallContext(cs_stoppay,"D2");
	 * }
	 * if (txcode.equals("100104")) { //止付解除反馈
	 * returnStr = CommonUtils.marshallContext(cs_stoppay,"D4");
	 * }
	 * if (txcode.equals("100106")) { //止付延期反馈
	 * returnStr = CommonUtils.marshallContext(cs_stoppay,"D6");
	 * }
	 * return returnStr;
	 * }
	 */
	
	/*
	 * public String makeXml_dj(Cs_Freeze cs_freeze) throws Exception {
	 * String returnStr = "";
	 * String txcode = cs_freeze.getTxcode();
	 * if (txcode.equals("100202")) { //冻结反馈
	 * returnStr = CommonUtils.marshallContext(cs_freeze,"D8");
	 * }
	 * if (txcode.equals("100204")) { //冻结解除反馈
	 * returnStr = CommonUtils.marshallContext(cs_freeze,"D10");
	 * }
	 * if (txcode.equals("100206")) { //冻结延期反馈
	 * returnStr = CommonUtils.marshallContext(cs_freeze,"D12");
	 * }
	 * return returnStr;
	 * }
	 */
	
	public Response sendMsgl_dj(Br25_Freeze_back cs_freeze) throws Exception {
		String transnum = cs_freeze.getTransSerialNumber();
		String orgkey = cs_freeze.getOrgkey();
		cs_freeze.setTransSerialNumber(this.getTransSerialNumber("2", orgkey));
		cs_freeze.setTo(cs_freeze.getToorg());
		cs_freeze.setMode(cs_freeze.getModeid());
		Response res = new Response();
		res.setCode(TxConstants.CODE_OK);
		String txcode = cs_freeze.getTxCode();
		if (txcode.equals(TxConstants.TXCODE_FREEZE_FEEDBACK)) { //冻结反馈
			FreezeRequest100202 freezerequest100202 = new FreezeRequest100202();
			BeanUtils.copyProperties(freezerequest100202, cs_freeze);
			freezerequest100202.setExpireTime(cs_freeze.getEndtime());
			//调用发送接口
			requestMessageService.sendFreezeRequest100202(freezerequest100202);
		}
		if (txcode.equals(TxConstants.TXCODE_FREEZE_RELIEVE_FEEDBACK)) { //冻结解除反馈
			FreezeRequest100204 freezerequest100204 = new FreezeRequest100204();
			BeanUtils.copyProperties(freezerequest100204, cs_freeze);
			freezerequest100204.setWithdrawalTime(cs_freeze.getStarttime());
			//调用发送接口
			requestMessageService.sendFreezeRequest100204(freezerequest100204);
		}
		if (txcode.equals(TxConstants.TXCODE_FREEZE_POSTPONE_FEEDBACK)) { //冻结延期反馈
			FreezeRequest100206 freezerequest100206 = new FreezeRequest100206();
			BeanUtils.copyProperties(freezerequest100206, cs_freeze);
			freezerequest100206.setExpireTime(cs_freeze.getEndtime());
			requestMessageService.sendFreezeRequest100206(freezerequest100206);
			
		}
		if (res.getTransSerialNumber().equals("")) {
			res.setTransSerialNumber(cs_freeze.getTransserialnumber());
		}
		res.setTransSerialNumber(transnum);
		return res;
		
	}
	
	public Response sendMsg_zf(Br25_StopPay_back cs_stoppay) throws Exception {
		String trannum = cs_stoppay.getTransSerialNumber();
		String orgkey = cs_stoppay.getOrgkey();
		cs_stoppay.setTransSerialNumber(this.getTransSerialNumber("2", orgkey));
		cs_stoppay.setTo(cs_stoppay.getToorg());
		cs_stoppay.setMode(cs_stoppay.getModeid());
		Response res = new Response();
		res.setCode(TxConstants.CODE_OK);
		String txcode = cs_stoppay.getTxCode();
		if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_FEEDBACK)) { //止付反馈
			StoppayRequest100102 stoppayrequest100102 = new StoppayRequest100102();
			BeanUtils.copyProperties(stoppayrequest100102, cs_stoppay);
			stoppayrequest100102.setExpireTime(cs_stoppay.getEndTime());
			//调用发送接口
			res = requestMessageService.sendStoppayRequest100102(stoppayrequest100102);
		}
		if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_RELIEVE_FEEDBACK)) { //止付解除反馈
			StoppayRequest100104 stoppayrequest100104 = new StoppayRequest100104();
			BeanUtils.copyProperties(stoppayrequest100104, cs_stoppay);
			stoppayrequest100104.setWithdrawalTime(cs_stoppay.getStartTime());
			res = requestMessageService.sendStoppayRequest100104(stoppayrequest100104);
		}
		if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_POSTPONE_FEEDBACK)) { //止付延期反馈
			StoppayRequest100106 stoppayrequest100106 = new StoppayRequest100106();
			BeanUtils.copyProperties(stoppayrequest100106, cs_stoppay);
			stoppayrequest100106.setExpireTime(cs_stoppay.getEndTime());
			//调用发送接口
			res = requestMessageService.sendStoppayRequest100106(stoppayrequest100106);
		}
		
		res.setTransSerialNumber(trannum);
		
		return res;
		
	}
	
	public void updateDj_status(Br25_Freeze_back cs_freeze, String code) throws Exception {
		
		String status_cd = "3";
		if (!code.equals(TxConstants.CODE_OK)) {
			status_cd = "4"; //失败
		}
		cs_freeze.setStatus_cd(status_cd);
		br25_kzqqMapper.updateBr25_kzqq_dj_status(cs_freeze);
		br25_kzqqMapper.updateBr25_frozen_dj_status(cs_freeze);
		
	}
	
	public void updateZf_status(Br25_StopPay_back cs_StopPay, String code) throws Exception {
		
		String status_cd = "3";
		if (!code.equals(TxConstants.CODE_OK)) {
			status_cd = "4"; //失败
		}
		cs_StopPay.setStatus_cd(status_cd);
		cs_StopPay.setFeedback_dt(DtUtils.getNowTime());
		br25_kzqqMapper.updateBr25_kzqq_zf_status(cs_StopPay);
		br25_kzqqMapper.updateBr25_stop_zf_status(cs_StopPay);
		
	}
	
	public Br25_StopPay_back sendHx_zf(Br25_StopPay cg_StopPay) throws Exception {
		Br25_StopPay_back cs_StopPay = new Br25_StopPay_back();
		try {
			HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
			String currency = StrUtils.null2String(cg_StopPay.getCurrency());
			HashMap dxbzMap = (HashMap) etlcodeMap.get("DXBZ");
			if (dxbzMap != null && !currency.equals("") && dxbzMap.get(currency) != null && !dxbzMap.get(currency).equals("")) {
				cg_StopPay.setCurrency((String) dxbzMap.get(currency));
			}
			//经办人证件类型
			String operatorIDType = StrUtils.null2String(cg_StopPay.getOperatorIDType());
			HashMap dxzjMap = (HashMap) etlcodeMap.get("DXZJ");
			if (dxzjMap != null && !operatorIDType.equals("") && dxzjMap.get(operatorIDType) != null && !dxzjMap.get(operatorIDType).equals("")) {
				cg_StopPay.setOperatorIDType((String) dxzjMap.get(operatorIDType));
			}
			//代办人证件类型 
			String investigatorIDType = StrUtils.null2String(cg_StopPay.getInvestigatorIDType());
			if (dxzjMap != null && !investigatorIDType.equals("") && dxzjMap.get(investigatorIDType) != null && !dxzjMap.get(investigatorIDType).equals("")) {
				cg_StopPay.setInvestigatorIDType((String) dxzjMap.get(investigatorIDType));
			}
			dataOperate = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
			String txcode = cg_StopPay.getTxCode();
			if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT)) { //止付反馈
				cs_StopPay = dataOperate.StopAccount(cg_StopPay);
			}
			if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_RELIEVE)) { //止付解除反馈
				cs_StopPay = dataOperate.StopAccount_JC(cg_StopPay);
			}
			if (txcode.equals(TxConstants.TXCODE_STOPPAYMENT_POSTPONE)) { //止付延期反馈
				cs_StopPay = dataOperate.StopAccount_YQ(cg_StopPay);
			}
			if (cs_StopPay.getHxappid() == null || cs_StopPay.getHxappid().equals("")) {
				cs_StopPay.setHxappid(cg_StopPay.getHxappid());
			}
			if (cg_StopPay.getAccountType().equals("02")) {//对公卡号为空
				cs_StopPay.setCardNumber("");
			}
		} catch (DataOperateException e) {
			// 3.3 错误统一采用异常处理
			logger.error("查询异常" + e.getMessage());
			cs_StopPay.setFailureCause(e.getMessage());
			cs_StopPay.setResult(e.getCode());
			cs_StopPay.setFeedbackRemark(e.getDescr());
		} catch (RemoteAccessException e) {
			e.printStackTrace();
			logger.error("Artery异常" + e.getMessage());
			//throw e;
			cs_StopPay.setResult("1199");
			cs_StopPay.setFeedbackRemark("处理异常");
		}
		cs_StopPay.setStatus_cd("1");
		return cs_StopPay;
		
	}
	
	public Br25_Freeze_back sendHx_dj(Br25_Freeze cg_Freeze) throws Exception {
		Br25_Freeze_back cs_Freeze = new Br25_Freeze_back();
		String freezetype = cg_Freeze.getFreezeType();
		try {
			String txcode = cg_Freeze.getTxCode();
			String appliedBalance = "";
			String frozedBalance = "";
			if (!txcode.equals(TxConstants.TXCODE_FREEZE)) { //非冻结查询冻结编号			 
				Br25_Freeze_back br25_freeze_back = this.getHxAppid_dj(cg_Freeze);
				if (br25_freeze_back != null) {
					String hxappid = br25_freeze_back.getHxappid();
					if (txcode.equals(TxConstants.TXCODE_FREEZE_RELIEVE)) { //冻结解除
						freezetype = br25_freeze_back.getFreezeType();
						cg_Freeze.setFreezeType(freezetype);
					}
					cg_Freeze.setHxappid(hxappid);
					appliedBalance = br25_freeze_back.getAppliedBalance(); //申请冻结限额
					frozedBalance = br25_freeze_back.getFrozedBalance();//执行冻结金额
				}
				//add查询原冻结信息取出类型传到核心
				Br25_Freeze br25_freeze_old = br25_kzqqMapper.getBr25_frozenByID(br25_freeze_back.getTransSerialNumber());
				if (br25_freeze_old != null) {
					cg_Freeze.setApplicationType(br25_freeze_old.getApplicationType());
					cg_Freeze.setBalance(br25_freeze_old.getBalance());
				}
				
			}
			
			String freezetype_s = cg_Freeze.getFreezeType();
			
			//冻结类型转成核心
			HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
			HashMap zjlxMap = (HashMap) etlcodeMap.get("DXDJFS");
			if (zjlxMap != null && zjlxMap.get(freezetype_s) != null && !zjlxMap.get(freezetype_s).equals("")) {
				freezetype_s = (String) zjlxMap.get(freezetype_s);
			}
			cg_Freeze.setFreezeType(freezetype_s);
			
			String currency = StrUtils.null2String(cg_Freeze.getCurrency());
			HashMap dxbzMap = (HashMap) etlcodeMap.get("DXBZ");
			if (dxbzMap != null && !currency.equals("") && dxbzMap.get(currency) != null && !dxbzMap.get(currency).equals("")) {
				cg_Freeze.setCurrency((String) dxbzMap.get(currency));
			}
			//经办人证件类型
			String operatorIDType = StrUtils.null2String(cg_Freeze.getOperatorIDType());
			HashMap dxzjMap = (HashMap) etlcodeMap.get("DXZJ");
			if (dxzjMap != null && !operatorIDType.equals("") && dxzjMap.get(operatorIDType) != null && !dxzjMap.get(operatorIDType).equals("")) {
				cg_Freeze.setOperatorIDType((String) dxzjMap.get(operatorIDType));
			}
			//代办人证件类型 
			String investigatorIDType = StrUtils.null2String(cg_Freeze.getInvestigatorIDType());
			if (dxzjMap != null && !investigatorIDType.equals("") && dxzjMap.get(investigatorIDType) != null && !dxzjMap.get(investigatorIDType).equals("")) {
				cg_Freeze.setInvestigatorIDType((String) dxzjMap.get(investigatorIDType));
			}
			
			dataOperate = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
			if (txcode.equals(TxConstants.TXCODE_FREEZE)) { //冻结		
				cs_Freeze = dataOperate.frozenAccount(cg_Freeze);
				if (freezetype.equals("02") && (cs_Freeze.getFrozedBalance() == null || cs_Freeze.getFrozedBalance().equals(""))) {
					cs_Freeze.setFrozedBalance(cs_Freeze.getAccountBalance()); // 全部冻结时将账户余额给执行冻结金额
				}
			}else if (txcode.equals(TxConstants.TXCODE_FREEZE_RELIEVE)) { //冻结解除
				cs_Freeze = dataOperate.frozenAccount_JC(cg_Freeze);
				// 如果申请解除金额为空值，将原申请冻结金额赋予之
				if (cs_Freeze != null && (cs_Freeze.getAppliedBalance() == null || cs_Freeze.getAppliedBalance().length() == 0)) {
					cs_Freeze.setAppliedBalance(appliedBalance);
				}
				// 如果未冻结金额为空值，将原执行冻结金额赋予之
				if (cs_Freeze != null && (cs_Freeze.getUnfreezeBalance() == null || cs_Freeze.getUnfreezeBalance().length() == 0)) {
					cs_Freeze.setUnfreezeBalance(frozedBalance);
					
				}
				// 如果账户余额为空值，将账户可用余额赋予之
				if (cs_Freeze != null && (cs_Freeze.getAccountBalance() == null || cs_Freeze.getAccountBalance().length() == 0)) {
					cs_Freeze.setAccountBalance(cs_Freeze.getAccountAvaiableBalance());
				}
			}else if (txcode.equals(TxConstants.TXCODE_FREEZE_POSTPONE)) { //冻结延期
				cs_Freeze = dataOperate.frozenAccount_YQ(cg_Freeze);
				//解除冻结时将原申请金额和执行金额付给
				if (cs_Freeze != null && (cs_Freeze.getAppliedBalance() == null || cs_Freeze.getAppliedBalance().equals(""))) {
					cs_Freeze.setAppliedBalance(appliedBalance);
				}
				if (freezetype.equals("02") && (cs_Freeze.getFrozedBalance() == null || cs_Freeze.getFrozedBalance().equals(""))) {
					cs_Freeze.setFrozedBalance(cs_Freeze.getAccountBalance()); // 全部冻结时将账户余额给执行冻结金额
				}
			}
			
			if (cs_Freeze.getHxappid() == null || cs_Freeze.getHxappid().equals("")) {
				cs_Freeze.setHxappid(cg_Freeze.getHxappid());
			}
		} catch (DataOperateException e) {
			// 3.3 错误统一采用异常处理
			logger.error("查询异常" + e.getMessage());
			cs_Freeze.setResult(e.getCode());
			cs_Freeze.setFeedbackRemark(e.getDescr());
		} catch (RemoteAccessException e) {
			e.printStackTrace();
			logger.error("Artery异常" + e.getMessage());
			//throw e;
			cs_Freeze.setResult("1199");
			cs_Freeze.setFeedbackRemark("处理异常");
		}
		cg_Freeze.setFreezeType(freezetype);
		cs_Freeze.setStatus_cd("1");
		return cs_Freeze;
		
	}
	
	/**
	 * type:1:账卡号 2：证件号码
	 * subjecttype: 1自然人 2法人
	 * 
	 * @param mc21_task_fact
	 * @param type
	 * @param cardnumber
	 */
	public void insertOrgMc21TaskFact2(MC21_task_fact mc21_task_fact, String cardnumber, String partyclass_cd, String type, String flag) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String taskobj = mc21_task_fact.getTaskobj();
		String returnStr = "update  br25_frozen set orgkey=@A@  where  transserialnumber='" + mc21_task_fact.getBdhm() + "';";
		returnStr = returnStr + "update  br25_frozen_back set orgkey=@A@,msgcheckresult='1'  where  transserialnumber='" + mc21_task_fact.getBdhm() + "'&";
		returnStr = returnStr + "update  br25_frozen_back set operatorname=@B@,operatorphonenumber=@C@ where  transserialnumber='" + mc21_task_fact.getBdhm() + "'&";
		returnStr = returnStr + "update  br25_frozen_back set msgcheckresult=@D@,result=@E@,status_cd='2' where  transserialnumber='" + mc21_task_fact.getBdhm() + "';";
		returnStr = returnStr + "update  br25_frozen set status_cd='2' where  transserialnumber='" + mc21_task_fact.getBdhm() + "'";
		if (flag.equals("1")) { //止付
			returnStr = "update  br25_stop set orgkey=@A@  where  transserialnumber='" + mc21_task_fact.getBdhm() + "';";
			returnStr = returnStr + "update  br25_stop_back set orgkey=@A@,msgcheckresult='1'   where  transserialnumber='" + mc21_task_fact.getBdhm() + "'&";
			returnStr = returnStr + "update  br25_stop_back set operatorname=@B@,operatorphonenumber=@C@ where  transserialnumber='" + mc21_task_fact.getBdhm() + "'&";
			returnStr = returnStr + "update  br25_stop_back set msgcheckresult=@D@,result=@E@,status_cd='2' where  transserialnumber='" + mc21_task_fact.getBdhm() + "';";
			returnStr = returnStr + "update  br25_stop set status_cd='2' where  transserialnumber='" + mc21_task_fact.getBdhm() + "'";
			
		}
		mc21_task_fact.setTaskobj(returnStr);
		this.insertMc21TaskFact2_Org(mc21_task_fact, type, cardnumber, partyclass_cd, "2");
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj(taskobj);
		
	}
	
}
