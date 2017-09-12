package com.citic.server.dx.task.taskBo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dx.domain.Bg_Q_Attach;
import com.citic.server.dx.domain.Br24_bas_info;
import com.citic.server.dx.domain.Br24_q_Main;
import com.citic.server.dx.domain.response.QueryResponse100301;
import com.citic.server.dx.domain.response.QueryResponse100303;
import com.citic.server.dx.domain.response.QueryResponse100305;
import com.citic.server.dx.domain.response.QueryResponse100307;
import com.citic.server.dx.domain.response.QueryResponse100309;
import com.citic.server.net.mapper.MM24_q_mainMapper;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

public class Q_mainBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Q_mainBo.class);
	private MM24_q_mainMapper mm24_q_mainMapper;
	
	public Q_mainBo(ApplicationContext ac) {
		super(ac);
		mm24_q_mainMapper = (MM24_q_mainMapper) ac.getBean("MM24_q_mainMapper");
	}
	
	public void delQureyResponse(String transserialnumber) throws Exception {
		mm24_q_mainMapper.delBr24_q_main(transserialnumber);
		
		mm24_q_mainMapper.delBg_q_attach(transserialnumber);
	}
	
	/**
	 * 插入BR24_查询请求单主表
	 * 
	 * @param bdhm
	 * @param bwPath
	 * @throws Exception
	 */
	public Br24_q_Main insertBr24_q_main100301(QueryResponse100301 query, String orgkey, String isemployee) throws Exception {
		Br24_q_Main q_main = new Br24_q_Main();
		q_main.setTxCode(query.getTxCode());
		q_main.setTransSerialNumber(query.getTransSerialNumber());
		q_main.setMessageFrom(query.getMessageFrom());
		
		q_main.setApplicationid(query.getApplicationID());
		q_main.setCasenumber(query.getCaseNumber());
		q_main.setCasetype(query.getCaseType());
		q_main.setEmergencylevel(query.getEmergencyLevel());
		q_main.setSubjecttype(query.getSubjectType());
		q_main.setBankid(query.getBankID());
		q_main.setBankname(query.getBankName());
		
		q_main.setAccountname(query.getAccountName());
		q_main.setAccountnumber(query.getCardNumber());
		q_main.setInquirymode(query.getInquiryMode());
		q_main.setInquiryperiodstart(query.getInquiryPeriodStart());
		q_main.setInquiryperiodend(query.getInquiryPeriodEnd());
		
		q_main.setReason(query.getReason());
		q_main.setRemark(query.getRemark());
		q_main.setApplicationtime(query.getApplicationTime());
		q_main.setApplicationorgid(query.getApplicationOrgID());
		q_main.setApplicationorgname(query.getApplicationOrgName());
		q_main.setOperatoridtype(query.getOperatorIDType());
		q_main.setOperatoridnumber(query.getOperatorIDNumber());
		q_main.setOperatorname(query.getOperatorName());
		q_main.setOperatorphonenumber(query.getOperatorPhoneNumber());
		q_main.setInvestigatoridtype(query.getInvestigatorIDType());
		q_main.setInvestigatoridnumber(query.getInvestigatorIDNumber());
		q_main.setInvestigatorname(query.getInvestigatorName());
		q_main.setQrydt(DtUtils.getNowDate());
		q_main.setOrgkey(orgkey);
		String currtime = DtUtils.getNowTime();
		q_main.setLast_up_dt(currtime);
		q_main.setRecipient_time(currtime);
		if (isemployee != null && isemployee.equals("0")) {
			q_main.setStatus("1");
		} else {
			q_main.setStatus("0");
		}
		mm24_q_mainMapper.insertBr24_q_main(q_main);
		return q_main;
	}
	
	public void insertBr24_q_main100303(QueryResponse100303 query, String orgkey, String isemployee) throws Exception {
		Br24_q_Main q_main = new Br24_q_Main();
		q_main.setTxCode(query.getTxCode());
		q_main.setMessageFrom(query.getMessageFrom());
		q_main.setTransSerialNumber(query.getTransSerialNumber());
		
		q_main.setApplicationid(query.getApplicationID());
		q_main.setCasenumber(query.getCaseNumber());
		q_main.setCasetype(query.getCaseType());
		q_main.setEmergencylevel(query.getEmergencyLevel());
		q_main.setSubjecttype(query.getSubjectType());
		q_main.setBankid(query.getBankID());
		q_main.setBankname(query.getBankName());
		/*
		 * <AccountName>查询账户名
		 * <CardNumber> 查询卡/折号
		 */
		q_main.setAccountname(query.getAccountName());
		q_main.setAccountnumber(query.getCardNumber());
		
		q_main.setReason(query.getReason());
		q_main.setRemark(query.getRemark());
		q_main.setApplicationtime(query.getApplicationTime());
		q_main.setApplicationorgid(query.getApplicationOrgID());
		q_main.setApplicationorgname(query.getApplicationOrgName());
		
		q_main.setOperatoridtype(query.getOperatorIDType());
		q_main.setOperatoridnumber(query.getOperatorIDNumber());
		q_main.setOperatorname(query.getOperatorName());
		q_main.setOperatorphonenumber(query.getOperatorPhoneNumber());
		q_main.setInvestigatoridtype(query.getInvestigatorIDType());
		q_main.setInvestigatoridnumber(query.getInvestigatorIDNumber());
		q_main.setInvestigatorname(query.getInvestigatorName());
		q_main.setQrydt(DtUtils.getNowDate());
		String currtime = DtUtils.getNowTime();
		q_main.setLast_up_dt(currtime);
		q_main.setRecipient_time(currtime);
		q_main.setOrgkey(orgkey);
		if (isemployee != null && isemployee.equals("0")) {
			q_main.setStatus("1");
		} else {
			q_main.setStatus("0");
		}
		mm24_q_mainMapper.insertBr24_q_main(q_main);
	}
	
	public void insertBr24_q_main100305(QueryResponse100305 query, String orgkey, String isemployee) throws Exception {
		Br24_q_Main q_main = new Br24_q_Main();
		
		q_main.setTxCode(query.getTxCode());
		q_main.setMessageFrom(query.getMessageFrom());
		q_main.setTransSerialNumber(query.getTransSerialNumber());
		
		q_main.setApplicationid(query.getApplicationID());
		q_main.setCasenumber(query.getCaseNumber());
		q_main.setCasetype(query.getCaseType());
		q_main.setEmergencylevel(query.getEmergencyLevel());
		q_main.setSubjecttype(query.getSubjectType());
		q_main.setBankid(query.getBankID());
		q_main.setBankname(query.getBankName());
		/*
		 * <AccountName>查询账户名
		 * <CardNumber> 查询卡/折号
		 */
		q_main.setAccountname(query.getAccountName());
		q_main.setAccountnumber(query.getCardNumber());
		/*
		 * <InterceptionOrderId> 执行命令 (1-执行)
		 * <InterceptionStartTime> 执行起始日期(yyyyMMddHHmmss)
		 * <InterceptionEndTime>执行截止日期(yyyyMMddHHmmss)
		 */
		q_main.setInterceptionorderid(query.getInterceptionOrderId());
		q_main.setInquiryperiodstart(query.getInterceptionStartTime());
		q_main.setInquiryperiodend(query.getInterceptionEndTime());
		
		q_main.setReason(query.getReason());
		q_main.setRemark(query.getRemark());
		q_main.setApplicationtime(query.getApplicationTime());
		q_main.setApplicationorgid(query.getApplicationOrgID());
		q_main.setApplicationorgname(query.getApplicationOrgName());
		
		q_main.setOperatoridtype(query.getOperatorIDType());
		q_main.setOperatoridnumber(query.getOperatorIDNumber());
		q_main.setOperatorname(query.getOperatorName());
		q_main.setOperatorphonenumber(query.getOperatorPhoneNumber());
		q_main.setInvestigatoridtype(query.getInvestigatorIDType());
		q_main.setInvestigatoridnumber(query.getInvestigatorIDNumber());
		q_main.setInvestigatorname(query.getInvestigatorName());
		q_main.setQrydt(DtUtils.getNowDate());
		String currtime = DtUtils.getNowTime();
		q_main.setLast_up_dt(currtime);
		q_main.setRecipient_time(currtime);
		q_main.setOrgkey(orgkey);
		if (isemployee != null && isemployee.equals("0")) {
			q_main.setStatus("1");
		} else {
			q_main.setStatus("0");
		}
		mm24_q_mainMapper.insertBr24_q_main(q_main);
	}
	
	public void insertBr24_q_main100307(QueryResponse100307 query, String orgkey, String isemployee) throws Exception {
		Br24_q_Main q_main = new Br24_q_Main();
		q_main.setTxCode(query.getTxCode());
		q_main.setMessageFrom(query.getMessageFrom());
		q_main.setTransSerialNumber(query.getTransSerialNumber());
		
		q_main.setApplicationid(query.getApplicationID());
		q_main.setCasenumber(query.getCaseNumber());
		q_main.setCasetype(query.getCaseType());
		q_main.setEmergencylevel(query.getEmergencyLevel());
		q_main.setSubjecttype(query.getSubjectType());
		q_main.setBankid(query.getBankID());
		q_main.setBankname(query.getBankName());
		
		/*
		 * <OriginalApplicationID>原动态查询申请编号
		 * <AccountName> 查询账户名
		 * <CardNumber> 查询卡/折号(与原账户动态查询请求卡/折号一致)
		 * <InterceptionEndTime> 动态查询解除生效时间(yyyyMMddHHmmss)
		 */
		q_main.setOriginalapplicationid(query.getOriginalApplicationID());
		q_main.setAccountname(query.getAccountName());
		q_main.setAccountnumber(query.getCardNumber());
		q_main.setInquiryperiodend(query.getInterceptionEndTime());
		
		q_main.setReason(query.getReason());
		q_main.setRemark(query.getRemark());
		q_main.setApplicationtime(query.getApplicationTime());
		q_main.setApplicationorgid(query.getApplicationOrgID());
		q_main.setApplicationorgname(query.getApplicationOrgName());
		
		q_main.setOperatoridtype(query.getOperatorIDType());
		q_main.setOperatoridnumber(query.getOperatorIDNumber());
		q_main.setOperatorname(query.getOperatorName());
		q_main.setOperatorphonenumber(query.getOperatorPhoneNumber());
		q_main.setInvestigatoridtype(query.getInvestigatorIDType());
		q_main.setInvestigatoridnumber(query.getInvestigatorIDNumber());
		q_main.setInvestigatorname(query.getInvestigatorName());
		q_main.setQrydt(DtUtils.getNowDate());
		String currtime = DtUtils.getNowTime();
		q_main.setLast_up_dt(currtime);
		q_main.setRecipient_time(currtime);
		q_main.setOrgkey(orgkey);
		if (isemployee != null && isemployee.equals("0")) {
			q_main.setStatus("1");
		} else {
			q_main.setStatus("0");
		}
		mm24_q_mainMapper.insertBr24_q_main(q_main);
	}
	
	public void insertBr24_q_main100309(QueryResponse100309 query, String orgkey, String isemployee) throws Exception {
		Br24_q_Main q_main = new Br24_q_Main();
		q_main.setTxCode(query.getTxCode());
		q_main.setMessageFrom(query.getMessageFrom());
		q_main.setTransSerialNumber(query.getTransSerialNumber());
		
		q_main.setApplicationid(query.getApplicationID());
		q_main.setCasenumber(query.getCaseNumber());
		q_main.setCasetype(query.getCaseType());
		q_main.setEmergencylevel(query.getEmergencyLevel());
		q_main.setSubjecttype(query.getSubjectType());
		q_main.setBankid(query.getBankID());
		q_main.setBankname(query.getBankName());
		
		/*
		 * <AccountCredentialType>查询证照类型代码(见附录1)
		 * <AccountCredentialNumber>查询证照号码
		 * <AccountSubjectName>查询主体名称（个人姓名或公司名称）
		 * <InquiryMode>查询内容(01-账户基本信息；02-账户信息(含强制措施、共有权/优先权信息)
		 */
		q_main.setAccountcredentialtype(query.getAccountCredentialType());
		q_main.setAccountcredentialnumber(query.getAccountCredentialNumber());
		q_main.setAccountsubjectname(query.getAccountSubjectName());
		q_main.setInquirymode(query.getInquiryMode());
		
		q_main.setReason(query.getReason());
		q_main.setRemark(query.getRemark());
		q_main.setApplicationtime(query.getApplicationTime());
		q_main.setApplicationorgid(query.getApplicationOrgID());
		q_main.setApplicationorgname(query.getApplicationOrgName());
		q_main.setOperatoridtype(query.getOperatorIDType());
		q_main.setOperatoridnumber(query.getOperatorIDNumber());
		q_main.setOperatorname(query.getOperatorName());
		q_main.setOperatorphonenumber(query.getOperatorPhoneNumber());
		q_main.setInvestigatoridtype(query.getInvestigatorIDType());
		q_main.setInvestigatoridnumber(query.getInvestigatorIDNumber());
		q_main.setInvestigatorname(query.getInvestigatorName());
		q_main.setQrydt(DtUtils.getNowDate());
		String currtime = DtUtils.getNowTime();
		q_main.setLast_up_dt(currtime);
		q_main.setRecipient_time(currtime);
		q_main.setOrgkey(orgkey);
		if (isemployee != null && isemployee.equals("0")) {
			q_main.setStatus("1");
		} else {
			q_main.setStatus("0");
		}
		mm24_q_mainMapper.insertBr24_q_main(q_main);
	}
	
	public void insertBr24_bas_info(Br24_bas_info br24_bas_info) throws Exception {
		
		//	String transserialnumberFK = this.geTransSerialNumber("2");
		
		MP02_rep_org_map rep_org = this.getMp02_organPerson("2", br24_bas_info.getOrgkey());
		String operatorName = rep_org.getOperatorname();
		String operatorPhoneNumber = rep_org.getOperatorphonenumber();
		String feedbackOrgName = rep_org.getOrganname();
		/** 默认值01 */
		br24_bas_info.setMode("01");
		/** 交易类型编码 */
		br24_bas_info.setTxCode(String.valueOf(Integer.parseInt(br24_bas_info.getTxcode()) + 1));
		/** 传输报文流水号（参见附录H） */
		//	br24_bas_info.setTransSerialNumber(transserialnumberFK);
		/** 经办人姓名 */
		br24_bas_info.setOperatorName(operatorName);
		/** 经办人电话 */
		br24_bas_info.setOperatorPhoneNumber(operatorPhoneNumber);
		/** 反馈机构名称 */
		br24_bas_info.setFeedbackOrgName(feedbackOrgName);
		/** 查询日期,默认取当前期 */
		br24_bas_info.setQrydt(DtUtils.getNowDate());
		/** 状态：0未处理 */
		br24_bas_info.setStatus("0");
		br24_bas_info.setMsgcheckresult("1");
		br24_bas_info.setLast_up_dt(DtUtils.getNowDate());
		mm24_q_mainMapper.delBr24_bas_info(br24_bas_info);
		mm24_q_mainMapper.insertBr24_bas_info(br24_bas_info);
		
	}
	
	/**
	 * 解析附件并插入
	 * 
	 * @param bdhm
	 * @param bwPath
	 * @throws Exception
	 */
	public void insertBr24_q_attach(String bdhm, MC20_task_msg taskmag) throws Exception {
		
		Bg_Q_Attach _q_attach = new Bg_Q_Attach();
		String[] attachpathStr = StringUtils.split(taskmag.getAttachpath(), ";");
		String[] attachnameStr = StringUtils.split(taskmag.getAttachname(), ";");
		
		for (int i = 0; i < attachpathStr.length; i++) {
			_q_attach.setFileno(this.geSequenceNumber("SEQ_BR24_Q_ATTACH"));
			_q_attach.setCreate_dt(DtUtils.getNowTime());
			_q_attach.setFilepath(attachpathStr[i]);
			_q_attach.setFilename(attachnameStr[i]);
			_q_attach.setTransserialnumber(bdhm);
			
			mm24_q_mainMapper.insertBg_q_attach(_q_attach);
		}
		
	}
	
	/**
	 * type:1:账卡号 2：证件号码
	 * subjecttype: 1自然人 2法人
	 * 
	 * @param mc21_task_fact
	 * @param type
	 * @param cardnumber
	 */
	public void insertOrgMc21TaskFact2(MC21_task_fact mc21_task_fact, String type, String credentialtype, String cardnumber) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String returnStr = "update  br24_q_main set orgkey=@A@  where  transserialnumber='" + mc21_task_fact.getBdhm() + "';";
		returnStr = returnStr + "update  br24_bas_info set orgkey=@A@  where  transserialnumber='" + mc21_task_fact.getBdhm() + "'&";
		returnStr = returnStr + "update  br24_bas_info set operatorname=@B@,operatorphonenumber=@C@ where  transserialnumber='" + mc21_task_fact.getBdhm() + "'&";
		returnStr = returnStr + "update  br24_bas_info set msgcheckresult=@D@,result=@E@,status='2'  where  transserialnumber='" + mc21_task_fact.getBdhm() + "';";
		returnStr = returnStr + "update  br24_q_main set status='2'  where  transserialnumber='" + mc21_task_fact.getBdhm() + "';";
		mc21_task_fact.setTaskobj(returnStr);
		this.insertMc21TaskFact2_Org(mc21_task_fact, type, cardnumber, credentialtype, "2");
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj("");
	}
	
	/*
	 * public String validateBr24_q_main( Br24_q_Main br24_q_main){
	 * String result="";
	 * String bankid=br24_q_main.getBankid();
	 * //检查bankid是否是该行的
	 * HashMap reportOrgMap = (HashMap) cacheService.getCache("Mp02_repOrgDetail", HashMap.class);
	 * if(reportOrgMap!=null&&!reportOrgMap.containsKey(bankid)){
	 * result="0799";
	 * return result;
	 * }
	 * //验证申请日期
	 * String applicationTime=br24_q_main.getApplicationtime();
	 * result=validateTime(applicationTime,"1");
	 * //验证交易日期
	 * String InquiryPeriodStart=StrUtils.null2String(br24_q_main.getInquiryperiodstart());
	 * String InquiryPeriodEnd=StrUtils.null2String(br24_q_main.getInterceptionenddate());
	 * String txcode=br24_q_main.getTxCode();
	 * if(txcode.equals("100301")){
	 * String InquiryMode=br24_q_main.getInquirymode();
	 * if(InquiryMode==null||InquiryMode.equals("")){
	 * result="0500";
	 * return result;
	 * }
	 * if(!InquiryMode.equals("01")){
	 * if(InquiryPeriodStart.equals("")||InquiryPeriodEnd.equals("")){
	 * result="0500";
	 * return result;
	 * }else{
	 * result=validateTime(InquiryPeriodStart,"");
	 * result=validateTime(InquiryPeriodEnd,"");
	 * }
	 * }
	 * }
	 * if(txcode.equals("100305")){
	 * result=validateTime(InquiryPeriodStart,"");
	 * result=validateTime(InquiryPeriodEnd,"");
	 * }
	 * if(txcode.equals("100307")){
	 * String OriginalApplicationID=br24_q_main.getOriginalapplicationid();
	 * if(OriginalApplicationID.equals("")){
	 * result="0500";
	 * }
	 * String InterceptionEndTime=br24_q_main.getInterceptionenddate();
	 * result=validateTime(InterceptionEndTime,"");
	 * }
	 * return result;
	 * }
	 * public String validateTime(String datetime,String type){
	 * String result="";
	 * try {
	 * datetime= Utility.toDateTime19(datetime);
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * result="0800";
	 * if(type.equals("1")){
	 * result="0802";
	 * }
	 * return result;
	 * }
	 * return result;
	 * }
	 */
	
}
