package com.citic.server.gdjg.task.taskBo;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjg.domain.Br57_cxqq;
import com.citic.server.gdjg.domain.Br57_cxqq_back;
import com.citic.server.gdjg.domain.Br57_cxqq_mx;
import com.citic.server.gdjg.domain.Br57_cxqq_mx_policeman;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseBxxcx;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseCkcx;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseDtcx;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseJrcpcx;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseLscx;
import com.citic.server.gdjg.domain.response.Gdjg_Response_AccCx;
import com.citic.server.gdjg.domain.response.Gdjg_Response_CaseCx;
import com.citic.server.gdjg.domain.response.Gdjg_Response_PolicemanCx;
import com.citic.server.gdjg.domain.response.Gdjg_Response_RespondentCx;
import com.citic.server.gdjg.mapper.MM57_cxqqMapper;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

/**
 * 广东省检察院
 * 
 * @author liuYing
 * @date 2017年5月25日 下午9:56:46
 */
public class Cxqq_JSBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_JSBo.class);
	
	private MM57_cxqqMapper br57_cxqqMapper;
	
	public Cxqq_JSBo(ApplicationContext ac) {
		super(ac);
		br57_cxqqMapper = (MM57_cxqqMapper) ac.getBean("MM57_cxqqMapper");
		
	}
	
	/**
	 * 删除请求任务：查询请求内容、查询请求信息及请求反馈信息
	 * 
	 * @param br50_cxqq 查询请求内容
	 * @throws Exception
	 */
	public void delQureyResponse(Br57_cxqq br57_cxqq) throws Exception {
		//1.删除査询请求内容表
		br57_cxqqMapper.delBr57_cxqq(br57_cxqq);
		//2.删除办案人信息表
		br57_cxqqMapper.delBr57_cxqq_mx_policeman(br57_cxqq);
		//3.删除请求单任务信息表
		br57_cxqqMapper.delBr57_cxqq_mx(br57_cxqq);
		//4.删除请求反馈信息表
		br57_cxqqMapper.delBr57_cxqq_back(br57_cxqq);
		
	}
	
	/**
	 * 处理文件--存款查询
	 */
	public void handleCxqqBw_Ckcx(Gdjg_ResponseCkcx query_ckcx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		
		String docno = query_ckcx.getDocno();//协作编号
		List<Gdjg_Response_CaseCx> casesList = query_ckcx.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构 此处mark
		
		for (Gdjg_Response_CaseCx cases : casesList) {
			//1.插入请求基本信息
			Br57_cxqq br57_cxqq = new Br57_cxqq();
			br57_cxqq.setDocno(docno);
			br57_cxqq.setCaseno(cases.getCaseno());
			br57_cxqq.setCasename(cases.getCasename());
			br57_cxqq.setExeunit(cases.getExeunit());
			br57_cxqq.setApplyorg(cases.getApplyorg());
			br57_cxqq.setTargetorg(cases.getTargetorg());
			br57_cxqq.setCasetype(cases.getCasetype());
			br57_cxqq.setRemark(cases.getRemark());
			br57_cxqq.setSendtime(cases.getSendtime());
			br57_cxqq.setDatasource("1");//1：账户 2：交易 3：金融产品 4：保险箱
			br57_cxqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br57_cxqq.setOrgkey(orgkey);
			br57_cxqq.setQrydt(DtUtils.getNowDate());
			br57_cxqq.setLast_up_dt(DtUtils.getNowTime());
			br57_cxqqMapper.insertBr57_cxqq(br57_cxqq);
			
			//2.插入办案人信息
			List<Gdjg_Response_PolicemanCx> policemansList = cases.getPolicemans();
			for (Gdjg_Response_PolicemanCx policemans : policemansList) {
				Br57_cxqq_mx_policeman mxpolice = new Br57_cxqq_mx_policeman();
				BeanUtils.copyProperties(mxpolice, policemans);
				mxpolice.setDocno(docno);
				mxpolice.setCaseno(cases.getCaseno());
				mxpolice.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx_policeman(mxpolice);
			}
			
			//3.插入请求主体信息、反馈主体信息
			List<Gdjg_Response_RespondentCx> respondentsList = cases.getRespondents();
			for (Gdjg_Response_RespondentCx respondents : respondentsList) {
				//2.1 插入请求主体信息
				Br57_cxqq_mx qqmx = new Br57_cxqq_mx();
				BeanUtils.copyProperties(qqmx, respondents);
				qqmx.setDocno(docno);
				qqmx.setCaseno(cases.getCaseno());
				qqmx.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx(qqmx);
				
				//3.2 插入反馈主体信息
				Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
				br57_cxqq_back.setDocno(docno);
				br57_cxqq_back.setCaseno(cases.getCaseno());
				br57_cxqq_back.setUniqueid(respondents.getUniqueid());
				br57_cxqq_back.setQrydt(DtUtils.getNowDate());
				br57_cxqq_back.setStatus("0");
				br57_cxqq_back.setLast_up_dt(DtUtils.getNowTime());
				br57_cxqqMapper.insertBr57_cxqq_back(br57_cxqq_back);
				
				//3.3 插入机构
				String cxzh = respondents.getAccount();
				mc21_task_fact.setFacttablename(respondents.getName() + "#" + "mbjgdm");
				mc21_task_fact.setBdhm(docno);
				/*
				 * 查询方式:
				 * 1：按名称、证件号查询
				 * 2：按账号或卡号查询
				 * 3：按单位名称查询
				 * 4：按组织机构代码查询
				 * 5：按工商营业执照编码查询
				 * 6：按证件号查询（如有些新疆的姓名存在录入困难或包含特殊符号，导致真实姓名与银行账户的户名匹配不上）
				 * 注：查询自然人时，只能按第1、2、6种方式查询；查询法人时，只能按第2、3、4、5种方式查询
				 */
				if (cxzh != null && !cxzh.equals("")) {
					this.insertOrgMc21TaskFact1(mc21_task_fact, cases.getCaseno(), respondents.getUniqueid(), respondents.getType(), respondents.getAccount());
				} else {
					//TODO 组织机构代码、工商营业执照、三合一
					this.insertOrgMc21TaskFact2(mc21_task_fact, cases.getCaseno(), respondents.getUniqueid(), respondents.getIdtype(), respondents.getId());
				}
				
				//3.4 插入task2
				mc21_task_fact.setBdhm(respondents.getUniqueid());//task2的bdhm存uniqueid
				mc21_task_fact.setTaskobj(docno + ";" + cases.getCaseno());//task2的taskobj存docno+caseno，以;分隔,在task中拆分使用
				this.insertMc21TaskFact2(mc21_task_fact, "GDJG");
			}
			
		}
		
	}
	
	/**
	 * 处理文件--交易流水查询
	 * 
	 * @throws Exception
	 */
	public void handleCxqqBw_Lscx(Gdjg_ResponseLscx query_lscx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		String docno = query_lscx.getDocno();//协作编号
		List<Gdjg_Response_CaseCx> casesList = query_lscx.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构
		
		for (Gdjg_Response_CaseCx cases : casesList) {
			//1.插入请求基本信息
			Br57_cxqq br57_cxqq = new Br57_cxqq();
			br57_cxqq.setDocno(docno);
			br57_cxqq.setCaseno(cases.getCaseno());
			br57_cxqq.setCasename(cases.getCasename());
			br57_cxqq.setExeunit(cases.getExeunit());
			br57_cxqq.setApplyorg(cases.getApplyorg());
			br57_cxqq.setTargetorg(cases.getTargetorg());
			br57_cxqq.setCasetype(cases.getCasetype());
			br57_cxqq.setRemark(cases.getRemark());
			br57_cxqq.setSendtime(cases.getSendtime());
			br57_cxqq.setDatasource("2");//1：账户 2：交易    3：金融产品   4：保险箱	
			br57_cxqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br57_cxqq.setOrgkey(orgkey);
			br57_cxqq.setQrydt(DtUtils.getNowDate());
			br57_cxqq.setLast_up_dt(DtUtils.getNowTime());
			br57_cxqqMapper.insertBr57_cxqq(br57_cxqq);
			
			//2.插入办案人信息
			List<Gdjg_Response_PolicemanCx> policemansList = cases.getPolicemans();
			for (Gdjg_Response_PolicemanCx policemans : policemansList) {
				Br57_cxqq_mx_policeman mxpolice = new Br57_cxqq_mx_policeman();
				BeanUtils.copyProperties(mxpolice, policemans);
				mxpolice.setDocno(docno);
				mxpolice.setCaseno(cases.getCaseno());
				mxpolice.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx_policeman(mxpolice);
			}
			
			//3.插入请求主体信息、反馈主体信息
			List<Gdjg_Response_AccCx> accsList = cases.getAccs();
			for (Gdjg_Response_AccCx accounts : accsList) {
				//3.1 插入请求主体信息
				Br57_cxqq_mx qqmx = new Br57_cxqq_mx();
				BeanUtils.copyProperties(qqmx, accounts);
				qqmx.setDocno(docno);
				qqmx.setCaseno(cases.getCaseno());
				qqmx.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx(qqmx);
				
				//3.2 插入反馈主体信息
				Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
				br57_cxqq_back.setDocno(docno);
				br57_cxqq_back.setCaseno(cases.getCaseno());
				br57_cxqq_back.setUniqueid(accounts.getUniqueid());
				br57_cxqq_back.setQrydt(DtUtils.getNowDate());
				br57_cxqq_back.setStatus("0");
				br57_cxqq_back.setLast_up_dt(DtUtils.getNowTime());
				br57_cxqqMapper.insertBr57_cxqq_back(br57_cxqq_back);
				
				//3.3 插入机构
				String cxzh = accounts.getAccount();
				mc21_task_fact.setFacttablename(accounts.getAccname() + "#" + "mbjgdm");
				mc21_task_fact.setBdhm(docno);
				
				if (cxzh != null && !cxzh.equals("")) {
					this.insertOrgMc21TaskFact1(mc21_task_fact, cases.getCaseno(), accounts.getUniqueid(), accounts.getType(), accounts.getAccount());
				} else {
					this.insertOrgMc21TaskFact2(mc21_task_fact, cases.getCaseno(), accounts.getUniqueid(), accounts.getIdtype(), accounts.getId());
				}
				
				//3.4 插入task2
				mc21_task_fact.setBdhm(accounts.getUniqueid());//task2的bdhm存uniqueid
				mc21_task_fact.setTaskobj(docno + ";" + cases.getCaseno());//task2的taskobj存docno+caseno，以;分隔,在task中拆分使用
				this.insertMc21TaskFact2(mc21_task_fact, "GDJG");
			}
			
		}
	}
	
	/**
	 * 处理文件--金融产品查询
	 */
	public void handleCxqqBw_Jrcpcx(Gdjg_ResponseJrcpcx query_jrcpcx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		String docno = query_jrcpcx.getDocno();//协作编号
		List<Gdjg_Response_CaseCx> casesList = query_jrcpcx.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构
		
		for (Gdjg_Response_CaseCx cases : casesList) {
			//1.插入请求基本信息
			Br57_cxqq br57_cxqq = new Br57_cxqq();
			br57_cxqq.setDocno(docno);
			br57_cxqq.setCaseno(cases.getCaseno());
			br57_cxqq.setCasetype(cases.getCasetype());
			br57_cxqq.setExeunit(cases.getExeunit());
			br57_cxqq.setApplyorg(cases.getApplyorg());
			br57_cxqq.setTargetorg(cases.getTargetorg());
			br57_cxqq.setRemark(cases.getRemark());
			br57_cxqq.setSendtime(cases.getSendtime());
			br57_cxqq.setDatasource("3");//1：账户 2：交易   3：金融产品 4：保险箱	
			br57_cxqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br57_cxqq.setOrgkey(orgkey);
			br57_cxqq.setQrydt(DtUtils.getNowDate());
			br57_cxqq.setLast_up_dt(DtUtils.getNowTime());
			br57_cxqqMapper.insertBr57_cxqq(br57_cxqq);
			
			//2.插入办案人信息
			List<Gdjg_Response_PolicemanCx> policemansList = cases.getPolicemans();
			for (Gdjg_Response_PolicemanCx policemans : policemansList) {
				Br57_cxqq_mx_policeman mxpolice = new Br57_cxqq_mx_policeman();
				BeanUtils.copyProperties(mxpolice, policemans);
				mxpolice.setDocno(docno);
				mxpolice.setCaseno(cases.getCaseno());
				mxpolice.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx_policeman(mxpolice);
			}
			
			//3.插入请求主体信息、反馈主体信息
			List<Gdjg_Response_AccCx> accsList = cases.getAccs();
			for (Gdjg_Response_AccCx accounts : accsList) {
				//3.1 插入请求主体信息
				Br57_cxqq_mx qqmx = new Br57_cxqq_mx();
				BeanUtils.copyProperties(qqmx, accounts);
				qqmx.setDocno(docno);
				qqmx.setCaseno(cases.getCaseno());
				qqmx.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx(qqmx);
				
				//3.2 插入反馈主体信息
				Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
				br57_cxqq_back.setDocno(docno);
				br57_cxqq_back.setCaseno(cases.getCaseno());
				br57_cxqq_back.setUniqueid(accounts.getUniqueid());
				br57_cxqq_back.setQrydt(DtUtils.getNowDate());
				br57_cxqq_back.setStatus("0");
				br57_cxqq_back.setLast_up_dt(DtUtils.getNowTime());
				br57_cxqqMapper.insertBr57_cxqq_back(br57_cxqq_back);
				
				//3.3 插入机构
				String cxzh = accounts.getAccount();
				mc21_task_fact.setFacttablename(accounts.getAccname() + "#" + "mbjgdm");
				mc21_task_fact.setBdhm(docno);
				
				if (cxzh != null && !cxzh.equals("")) {
					this.insertOrgMc21TaskFact1(mc21_task_fact, cases.getCaseno(), accounts.getUniqueid(), accounts.getType(), accounts.getAccount());
				} else {
					this.insertOrgMc21TaskFact2(mc21_task_fact, cases.getCaseno(), accounts.getUniqueid(), accounts.getIdtype(), accounts.getId());
				}
				
				//3.4 插入task2
				mc21_task_fact.setBdhm(accounts.getUniqueid());//task2的bdhm存uniqueid
				mc21_task_fact.setTaskobj(docno + ";" + cases.getCaseno());//task2的taskobj存docno+caseno，以;分隔,在task中拆分使用
				this.insertMc21TaskFact2(mc21_task_fact, "GDJG");
			}
			
		}
	}
	
	/**
	 * 处理文件--保险箱查询
	 */
	public void handleCxqqBw_Bxxcx(Gdjg_ResponseBxxcx query_bxxcx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		String docno = query_bxxcx.getDocno();//协作编号
		List<Gdjg_Response_CaseCx> casesList = query_bxxcx.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构
		
		for (Gdjg_Response_CaseCx cases : casesList) {
			//1.插入请求基本信息
			Br57_cxqq br57_cxqq = new Br57_cxqq();
			br57_cxqq.setDocno(docno);
			br57_cxqq.setCasetype(cases.getCasetype());
			br57_cxqq.setCaseno(cases.getCaseno());
			br57_cxqq.setExeunit(cases.getExeunit());
			br57_cxqq.setApplyorg(cases.getApplyorg());
			br57_cxqq.setTargetorg(cases.getTargetorg());
			br57_cxqq.setRemark(cases.getRemark());
			br57_cxqq.setSendtime(cases.getSendtime());
			br57_cxqq.setDatasource("4");//1：账户 2：交易    3：金融产品 4：保险箱
			br57_cxqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br57_cxqq.setOrgkey(orgkey);
			br57_cxqq.setQrydt(DtUtils.getNowDate());
			br57_cxqq.setLast_up_dt(DtUtils.getNowTime());
			br57_cxqqMapper.insertBr57_cxqq(br57_cxqq);
			
			//2.插入办案人信息
			List<Gdjg_Response_PolicemanCx> policemansList = cases.getPolicemans();
			for (Gdjg_Response_PolicemanCx policemans : policemansList) {
				Br57_cxqq_mx_policeman mxpolice = new Br57_cxqq_mx_policeman();
				BeanUtils.copyProperties(mxpolice, policemans);
				mxpolice.setDocno(docno);
				mxpolice.setCaseno(cases.getCaseno());
				mxpolice.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx_policeman(mxpolice);
			}
			
			//3.插入请求主体信息、反馈主体信息
			List<Gdjg_Response_AccCx> accsList = cases.getAccs();
			for (Gdjg_Response_AccCx accounts : accsList) {
				//3.1 插入请求主体信息
				Br57_cxqq_mx qqmx = new Br57_cxqq_mx();
				BeanUtils.copyProperties(qqmx, accounts);
				qqmx.setDocno(docno);
				qqmx.setCaseno(cases.getCaseno());
				qqmx.setName(accounts.getAccname());
				qqmx.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx(qqmx);
				
				//3.2 插入反馈主体信息
				Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
				br57_cxqq_back.setDocno(docno);
				br57_cxqq_back.setCaseno(cases.getCaseno());
				br57_cxqq_back.setUniqueid(accounts.getUniqueid());
				br57_cxqq_back.setQrydt(DtUtils.getNowDate());
				br57_cxqq_back.setStatus("0");
				br57_cxqq_back.setLast_up_dt(DtUtils.getNowTime());
				br57_cxqqMapper.insertBr57_cxqq_back(br57_cxqq_back);
				
				//3.3 插入机构
				String cxzh = accounts.getAccount();
				mc21_task_fact.setFacttablename(accounts.getAccname() + "#" + "mbjgdm");
				mc21_task_fact.setBdhm(docno);
				
				if (cxzh != null && !cxzh.equals("")) {
					this.insertOrgMc21TaskFact1(mc21_task_fact, cases.getCaseno(), accounts.getUniqueid(), accounts.getType(), accounts.getAccount());
				} else {
					this.insertOrgMc21TaskFact2(mc21_task_fact, cases.getCaseno(), accounts.getUniqueid(), accounts.getIdtype(), accounts.getId());
				}
				
				//3.4 插入task2
				mc21_task_fact.setBdhm(accounts.getUniqueid());//task2的bdhm存uniqueid
				mc21_task_fact.setTaskobj(docno + ";" + cases.getCaseno());//task2的taskobj存docno+caseno，以;分隔,在task中拆分使用
				this.insertMc21TaskFact2(mc21_task_fact, "GDJG");
			}
			
		}
	}
	
	/**
	 * type:1:卡号 2：证件号码 3子账号
	 * subjecttype: 1自然人 2法人
	 * 
	 * @param mc21_task_fact
	 * @param type
	 * @param cardnumber
	 */
	public void insertOrgMc21TaskFact2(MC21_task_fact mc21_task_fact, String caseno, String uniqueid, String zjlx, String zjhm) throws Exception {
		//获取系统参数
		HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
		HashMap zjlxMap = (HashMap) etlcodeMap.get("GDJC");
		if (zjlxMap.get(zjlx) != null) { //证件类型转成核心需要的
			zjlx = (String) zjlxMap.get(zjlx);
		}
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String returnStr = "update  br50_cxqq_mx set orgkey=@A@  where  docno='" + mc21_task_fact.getBdhm() + "' and uniqueid='" + uniqueid + "' and caseno='" + caseno + "';";
		returnStr = returnStr + "update  br50_cxqq_back set orgkey=@A@, msgcheckresult='1'   where  docno='" + mc21_task_fact.getBdhm() + "' and uniqueid='" + uniqueid
					+ "' and caseno='" + caseno + "'&";
		returnStr = returnStr + "update  br50_cxqq_back set msgcheckresult=@D@, Status='5' where  docno='" + mc21_task_fact.getBdhm() + "' and uniqueid='" + uniqueid
					+ "' and caseno='" + caseno + "'";
		mc21_task_fact.setTaskobj(returnStr);
		this.insertMc21TaskFact2_Org(mc21_task_fact, "2", zjhm, zjlx, mc21_task_fact.getTasktype());
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj("");
		
	}
	
	public void insertOrgMc21TaskFact1(MC21_task_fact mc21_task_fact, String caseno, String uniqueid, String credentialtype, String cardnumber) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String returnStr = "update  br50_cxqq_mx set orgkey=@A@  where  docno='" + mc21_task_fact.getBdhm() + "' and uniqueid='" + uniqueid + "' and caseno='" + caseno + "';";
		returnStr = returnStr + "update  br50_cxqq_back set orgkey=@A@,msgcheckresult='1'   where  docno='" + mc21_task_fact.getBdhm() + "' and uniqueid='" + uniqueid
					+ "' and caseno='" + caseno + "'&";
		returnStr = returnStr + "update  br50_cxqq_back set msgcheckresult=@D@ ,cxfkjg='02',Status='5' where  docno='" + mc21_task_fact.getBdhm() + "' and uniqueid='" + uniqueid
					+ "' and caseno='" + caseno + "'";
		mc21_task_fact.setTaskobj(returnStr);
		this.insertMc21TaskFact2_Org(mc21_task_fact, "1", cardnumber, credentialtype, mc21_task_fact.getTasktype());
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj("");
	}
	
	public String getOrgKey(String reportkey_r, String tasktype) {
		String organKey = "";
		HashMap<String, MP02_rep_org_map> repOrgMap = (HashMap<String, MP02_rep_org_map>) cacheService.getCache("Mp02_repOrgDetail", HashMap.class);
		MP02_rep_org_map repOrgObj = repOrgMap.get(tasktype + reportkey_r);
		if (repOrgObj != null) {
			organKey = repOrgObj.getHorgankey();
		}
		return organKey;
	}
	
	/**
	 * 处理文件--动态查询
	 * 
	 * @throws Exception
	 */
	public void handleCxqqBw_Dtcx(Gdjg_ResponseDtcx control_dtcx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		String docno = control_dtcx.getDocno();//协作编号
		List<Gdjg_Response_CaseCx> casesList = control_dtcx.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构 此处mark
		
		for (Gdjg_Response_CaseCx cases : casesList) {
			//1.插入请求基本信息
			Br57_cxqq br57_cxqq = new Br57_cxqq();
			br57_cxqq.setDocno(docno);
			br57_cxqq.setCaseno(cases.getCaseno());
			br57_cxqq.setExeunit(cases.getExeunit());
			br57_cxqq.setApplyorg(cases.getApplyorg());
			br57_cxqq.setTargetorg(cases.getTargetorg());
			br57_cxqq.setCasetype(cases.getCasetype());
			br57_cxqq.setRemark(cases.getRemark());
			br57_cxqq.setSendtime(cases.getSendtime());
			br57_cxqq.setDatasource("8");//5：冻结 6.解冻 7.紧急止付 8.动态查询
			br57_cxqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br57_cxqq.setOrgkey(orgkey);
			br57_cxqq.setQrydt(DtUtils.getNowDate());
			br57_cxqq.setLast_up_dt(DtUtils.getNowTime());
			br57_cxqqMapper.insertBr57_cxqq(br57_cxqq);
			
			//2.插入办案人信息
			List<Gdjg_Response_PolicemanCx> policemansList = cases.getPolicemans();
			for (Gdjg_Response_PolicemanCx policemans : policemansList) {
				Br57_cxqq_mx_policeman mxpolice = new Br57_cxqq_mx_policeman();
				BeanUtils.copyProperties(mxpolice, policemans);
				mxpolice.setDocno(docno);
				mxpolice.setCaseno(cases.getCaseno());
				mxpolice.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx_policeman(mxpolice);
				
			}
			
			//3.插入请求主体信息、反馈主体信息
			List<Gdjg_Response_AccCx> accList = cases.getAccs();
			for (Gdjg_Response_AccCx accounts : accList) {
				//2.1 插入请求主体信息
				Br57_cxqq_mx qqmx = new Br57_cxqq_mx();
				BeanUtils.copyProperties(qqmx, accounts);
				qqmx.setDocno(docno);
				qqmx.setCaseno(cases.getCaseno());
				qqmx.setQrydt(DtUtils.getNowDate());
				br57_cxqqMapper.insertBr57_cxqq_mx(qqmx);
				
				//3.2 插入反馈主体信息
				Br57_cxqq_back br57_cxqq_back = new Br57_cxqq_back();
				br57_cxqq_back.setDocno(docno);
				br57_cxqq_back.setCaseno(cases.getCaseno());
				br57_cxqq_back.setUniqueid(accounts.getUniqueid());
				br57_cxqq_back.setQrydt(DtUtils.getNowDate());
				br57_cxqq_back.setStatus("0");
				br57_cxqq_back.setLast_up_dt(DtUtils.getNowTime());
				br57_cxqqMapper.insertBr57_cxqq_back(br57_cxqq_back);
				
				//3.3 插入机构
				String cxzh = accounts.getAccount();
				mc21_task_fact.setFacttablename(accounts.getName() + "#" + "mbjgdm");
				mc21_task_fact.setBdhm(docno);
				/*
				 * 查询方式:
				 * 1：按名称、证件号查询
				 * 2：按账号或卡号查询
				 * 3：按单位名称查询
				 * 4：按组织机构代码查询
				 * 5：按工商营业执照编码查询
				 * 6：按证件号查询（如有些新疆的姓名存在录入困难或包含特殊符号，导致真实姓名与银行账户的户名匹配不上）
				 * 注：查询自然人时，只能按第1、2、6种方式查询；查询法人时，只能按第2、3、4、5种方式查询
				 */
				if (cxzh != null && !cxzh.equals("")) {
					this.insertOrgMc21TaskFact1(mc21_task_fact, cases.getCaseno(), accounts.getUniqueid(), accounts.getType(), accounts.getAccount());
				} else {
					//TODO 组织机构代码、工商营业执照、三合一
					this.insertOrgMc21TaskFact2(mc21_task_fact, cases.getCaseno(), accounts.getUniqueid(), accounts.getIdtype(), accounts.getId());
				}
				
				//3.4 插入task2
				mc21_task_fact.setBdhm(accounts.getUniqueid());//task2的bdhm存uniqueid
				mc21_task_fact.setTaskobj(docno + ";" + cases.getCaseno());//task2的taskobj存docno+caseno，以;分隔,在task中拆分使用
				this.insertMc21TaskFact2(mc21_task_fact, "GDJG");
			}
			
		}
		
	}
}
