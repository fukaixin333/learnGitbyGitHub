package com.citic.server.gdjg.task.taskBo;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjg.domain.Br57_kzqq;
import com.citic.server.gdjg.domain.Br57_kzqq_back;
import com.citic.server.gdjg.domain.Br57_kzqq_input;
import com.citic.server.gdjg.domain.Br57_kzqq_mx;
import com.citic.server.gdjg.domain.Br57_kzqq_mx_policeman;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseCkdjkz;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseCkjdkz;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseDtcx;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseJjzfkz;
import com.citic.server.gdjg.domain.response.Gdjg_Response_AccCx;
import com.citic.server.gdjg.domain.response.Gdjg_Response_BankCx;
import com.citic.server.gdjg.domain.response.Gdjg_Response_CaseCx;
import com.citic.server.gdjg.domain.response.Gdjg_Response_PolicemanCx;
import com.citic.server.gdjg.domain.response.Gdjg_Response_RespondentCx;
import com.citic.server.gdjg.mapper.MM57_kzqqMapper;
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
public class Kzqq_JSBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Kzqq_JSBo.class);
	
	private MM57_kzqqMapper br57_kzqqMapper;
	
	public Kzqq_JSBo(ApplicationContext ac) {
		super(ac);
		br57_kzqqMapper = (MM57_kzqqMapper) ac.getBean("MM57_kzqqMapper");
		
	}
	
	/**
	 * 删除请求任务：控制请求内容、控制请求信息及请求反馈信息
	 * 
	 * @param br50_kzqq 查询请求内容
	 * @throws Exception
	 */
	public void delQureyResponse(Br57_kzqq br57_kzqq) {
		//1.删除査询请求内容表
		br57_kzqqMapper.delBr57_kzqq(br57_kzqq);
		//2.删除办案人信息表
		br57_kzqqMapper.delBr57_kzqq_mx_policeman(br57_kzqq);
		//3.删除请求单任务信息表
		br57_kzqqMapper.delBr57_kzqq_mx(br57_kzqq);
		//4.删除请求反馈信息表
		br57_kzqqMapper.delBr57_kzqq_back(br57_kzqq);
		//5.删除查询输入项信息表
		br57_kzqqMapper.delBr57_kzqq_input(br57_kzqq);
	}
	
	/**
	 * 处理文件--存款冻结控制
	 * 
	 * @throws Exception
	 */
	public void handleCxqqBw_Ckdjkz(Gdjg_ResponseCkdjkz control_ckdj, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		String docno = control_ckdj.getDocno();//协作编号
		List<Gdjg_Response_CaseCx> casesList = control_ckdj.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构 此处mark
		for (Gdjg_Response_CaseCx cases : casesList) {
			//1.插入请求基本信息
			Br57_kzqq br57_kzqq = new Br57_kzqq();
			br57_kzqq.setDocno(docno);
			br57_kzqq.setCaseno(cases.getCaseno());
			br57_kzqq.setCasename(cases.getCasename());
			br57_kzqq.setExeunit(cases.getExeunit());
			br57_kzqq.setApplyorg(cases.getApplyorg());
			br57_kzqq.setTargetorg(cases.getTargetorg());
			br57_kzqq.setCasetype(cases.getCasetype());
			br57_kzqq.setRemark(cases.getRemark());
			br57_kzqq.setSendtime(cases.getSendtime());
			br57_kzqq.setDatasource("5");//5：冻结 6.解冻 7.紧急止付 8.动态查询
			br57_kzqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br57_kzqq.setOrgkey(orgkey);
			br57_kzqq.setQrydt(DtUtils.getNowDate());
			br57_kzqq.setLast_up_dt(DtUtils.getNowTime());
			br57_kzqqMapper.insertBr57_kzqq(br57_kzqq);
			
			//2.插入办案人信息
			List<Gdjg_Response_PolicemanCx> policemansList = cases.getPolicemans();
			for (Gdjg_Response_PolicemanCx policemans : policemansList) {
				Br57_kzqq_mx_policeman mxpolice = new Br57_kzqq_mx_policeman();
				BeanUtils.copyProperties(mxpolice, policemans);
				mxpolice.setDocno(docno);
				mxpolice.setCaseno(cases.getCaseno());
				mxpolice.setQrydt(DtUtils.getNowDate());
				br57_kzqqMapper.insertBr57_kzqq_mx_policeman(mxpolice);
			}
			
			//3.插入请求主体信息、反馈主体信息
			List<Gdjg_Response_RespondentCx> respondentsList = cases.getRespondents();
			for (Gdjg_Response_RespondentCx respondents : respondentsList) {
				//2.1 插入请求主体信息
				Br57_kzqq_mx qqmx = new Br57_kzqq_mx();
				BeanUtils.copyProperties(qqmx, respondents);
				qqmx.setDocno(docno);
				qqmx.setCaseno(cases.getCaseno());
				qqmx.setQrydt(DtUtils.getNowDate());
				br57_kzqqMapper.insertBr57_kzqq_mx(qqmx);
				
				
				
				//3.2 插入查询输入项信息
				List<Gdjg_Response_BankCx> bankList = respondents.getBanks();
				for (Gdjg_Response_BankCx bank : bankList) {
					List<Gdjg_Response_AccCx> accList = bank.getAccs();
					for (Gdjg_Response_AccCx account : accList) {
						Br57_kzqq_input br57_kzqq_input = new Br57_kzqq_input();
						BeanUtils.copyProperties(br57_kzqq_input, account);
						br57_kzqq_input.setDocno(docno);
						br57_kzqq_input.setCaseno(cases.getCaseno());
						br57_kzqq_input.setUniqueid(respondents.getUniqueid());
						br57_kzqq_input.setQrydt(DtUtils.getNowDate());
						br57_kzqq_input.setExeunit(cases.getExeunit());
						br57_kzqqMapper.insertBr57_kzqq_input(br57_kzqq_input);
						
						
						//3.3 插入反馈主体信息
						Br57_kzqq_back br57_kzqq_back = new Br57_kzqq_back();
						br57_kzqq_back.setDocno(docno);
						br57_kzqq_back.setCaseno(cases.getCaseno());
						br57_kzqq_back.setUniqueid(respondents.getUniqueid());
						br57_kzqq_back.setQrydt(DtUtils.getNowDate());
						br57_kzqq_back.setStatus("0");
						br57_kzqq_back.setLast_up_dt(DtUtils.getNowTime());
						
						br57_kzqq_back.setAccount(account.getAccount());
						br57_kzqq_back.setFroseq(account.getFroseq());
						br57_kzqq_back.setOldfroseq(account.getOldfroseq());
						br57_kzqqMapper.insertBr57_kzqq_back(br57_kzqq_back);
					}
				}
				
				//3.4 插入机构
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
					// 组织机构代码、工商营业执照、三合一
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
	 * 处理文件--存款解冻控制
	 * 
	 * @throws Exception
	 */
	public void handleCxqqBw_Ckjdkz(Gdjg_ResponseCkjdkz control_ckjd, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		String docno = control_ckjd.getDocno();//协作编号
		List<Gdjg_Response_CaseCx> casesList = control_ckjd.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构 此处mark
		
		for (Gdjg_Response_CaseCx cases : casesList) {
			//1.插入请求基本信息
			Br57_kzqq br57_kzqq = new Br57_kzqq();
			br57_kzqq.setDocno(docno);
			br57_kzqq.setCaseno(cases.getCaseno());
			br57_kzqq.setCasename(cases.getCasename());
			br57_kzqq.setExeunit(cases.getExeunit());
			br57_kzqq.setApplyorg(cases.getApplyorg());
			br57_kzqq.setTargetorg(cases.getTargetorg());
			br57_kzqq.setCasetype(cases.getCasetype());
			br57_kzqq.setRemark(cases.getRemark());
			br57_kzqq.setSendtime(cases.getSendtime());
			br57_kzqq.setDatasource("6");//5：冻结 6.解冻 7.紧急止付 8.动态查询
			br57_kzqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br57_kzqq.setOrgkey(orgkey);
			br57_kzqq.setQrydt(DtUtils.getNowDate());
			br57_kzqq.setLast_up_dt(DtUtils.getNowTime());
			br57_kzqqMapper.insertBr57_kzqq(br57_kzqq);
			
			//2.插入办案人信息
			List<Gdjg_Response_PolicemanCx> policemansList = cases.getPolicemans();
			for (Gdjg_Response_PolicemanCx policemans : policemansList) {
				Br57_kzqq_mx_policeman mxpolice = new Br57_kzqq_mx_policeman();
				BeanUtils.copyProperties(mxpolice, policemans);
				mxpolice.setDocno(docno);
				mxpolice.setCaseno(cases.getCaseno());
				mxpolice.setQrydt(DtUtils.getNowDate());
				br57_kzqqMapper.insertBr57_kzqq_mx_policeman(mxpolice);
			}
			
			//3.插入请求主体信息、反馈主体信息
			List<Gdjg_Response_RespondentCx> respondentsList = cases.getRespondents();
			for (Gdjg_Response_RespondentCx respondents : respondentsList) {
				//2.1 插入请求主体信息
				Br57_kzqq_mx qqmx = new Br57_kzqq_mx();
				BeanUtils.copyProperties(qqmx, respondents);
				qqmx.setDocno(docno);
				qqmx.setCaseno(cases.getCaseno());
				qqmx.setQrydt(DtUtils.getNowDate());
				br57_kzqqMapper.insertBr57_kzqq_mx(qqmx);
				
				
				
				//3.2插入查询输入项信息
				List<Gdjg_Response_BankCx> bankList = respondents.getBanks();
				for (Gdjg_Response_BankCx bank : bankList) {
					List<Gdjg_Response_AccCx> accList = bank.getAccs();
					for (Gdjg_Response_AccCx account : accList) {
						Br57_kzqq_input br57_kzqq_input = new Br57_kzqq_input();
						BeanUtils.copyProperties(br57_kzqq_input, account);
						br57_kzqq_input.setDocno(docno);
						br57_kzqq_input.setCaseno(cases.getCaseno());
						br57_kzqq_input.setUniqueid(respondents.getUniqueid());
						br57_kzqq_input.setQrydt(DtUtils.getNowDate());
						br57_kzqq_input.setExeunit(cases.getExeunit());
						//此处将froseq值放入Oldfroseq，避免查询fronumber时出现froseq不唯一
						br57_kzqq_input.setOldfroseq(account.getFroseq());
						br57_kzqq_input.setFroseq("");
						br57_kzqqMapper.insertBr57_kzqq_input(br57_kzqq_input);
						
						
						
						//3.3插入反馈主体信息
						Br57_kzqq_back br57_kzqq_back = new Br57_kzqq_back();
						br57_kzqq_back.setDocno(docno);
						br57_kzqq_back.setCaseno(cases.getCaseno());
						br57_kzqq_back.setUniqueid(respondents.getUniqueid());
						br57_kzqq_back.setQrydt(DtUtils.getNowDate());
						br57_kzqq_back.setStatus("0");
						br57_kzqq_back.setLast_up_dt(DtUtils.getNowTime());
						
						br57_kzqq_back.setAccount(account.getAccount());
						br57_kzqq_back.setOldfroseq(account.getFroseq());
						br57_kzqq_back.setThawseq(account.getThawseq());
						br57_kzqqMapper.insertBr57_kzqq_back(br57_kzqq_back);

					}
				}
				
				//3.4 插入机构
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
					// 组织机构代码、工商营业执照、三合一
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
	 * 处理文件--紧急支付控制
	 * 
	 * @throws Exception
	 */
	public void handleCxqqBw_Jjzfkz(Gdjg_ResponseJjzfkz control_jjzf, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		String docno = control_jjzf.getDocno();//协作编号
		List<Gdjg_Response_CaseCx> casesList = control_jjzf.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构 此处mark
		for (Gdjg_Response_CaseCx cases : casesList) {
			//1.插入请求基本信息
			Br57_kzqq br57_kzqq = new Br57_kzqq();
			br57_kzqq.setDocno(docno);
			br57_kzqq.setCaseno(cases.getCaseno());
			br57_kzqq.setCasename(cases.getCasename());
			br57_kzqq.setExeunit(cases.getExeunit());
			br57_kzqq.setApplyorg(cases.getApplyorg());
			br57_kzqq.setTargetorg(cases.getTargetorg());
			br57_kzqq.setCasetype(cases.getCasetype());
			br57_kzqq.setRemark(cases.getRemark());
			br57_kzqq.setSendtime(cases.getSendtime());
			br57_kzqq.setDatasource("7");//5：冻结 6.解冻 7.紧急止付 8.动态查询
			br57_kzqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br57_kzqq.setOrgkey(orgkey);
			br57_kzqq.setQrydt(DtUtils.getNowDate());
			br57_kzqq.setLast_up_dt(DtUtils.getNowTime());
			br57_kzqqMapper.insertBr57_kzqq(br57_kzqq);
			
			//2.插入办案人信息
			List<Gdjg_Response_PolicemanCx> policemansList = cases.getPolicemans();
			for (Gdjg_Response_PolicemanCx policemans : policemansList) {
				Br57_kzqq_mx_policeman mxpolice = new Br57_kzqq_mx_policeman();
				BeanUtils.copyProperties(mxpolice, policemans);
				mxpolice.setDocno(docno);
				mxpolice.setCaseno(cases.getCaseno());
				mxpolice.setQrydt(DtUtils.getNowDate());
				br57_kzqqMapper.insertBr57_kzqq_mx_policeman(mxpolice);
			}
			
			//3.插入请求主体信息、反馈主体信息
			//3.1 插入查询输入项信息
			List<Gdjg_Response_AccCx> accList = cases.getAccs();
			for (Gdjg_Response_AccCx account : accList) {
				//请求明细
				Br57_kzqq_mx br57_kzqq_mx = new Br57_kzqq_mx();
				br57_kzqq_mx.setUniqueid(account.getUniqueid());
				br57_kzqq_mx.setDocno(docno);
				br57_kzqq_mx.setCaseno(cases.getCaseno());
				br57_kzqq_mx.setQrydt(DtUtils.getNowDate());
				br57_kzqqMapper.insertBr57_kzqq_mx(br57_kzqq_mx);
				
				//请求反馈
				Br57_kzqq_back br57_kzqq_back = new Br57_kzqq_back();
				br57_kzqq_back.setDocno(docno);
				br57_kzqq_back.setCaseno(cases.getCaseno());
				br57_kzqq_back.setUniqueid(account.getUniqueid());
				br57_kzqq_back.setQrydt(DtUtils.getNowDate());
				br57_kzqq_back.setStatus("0");
				br57_kzqq_back.setLast_up_dt(DtUtils.getNowTime());
				br57_kzqq_back.setAccount(account.getAccount());
				br57_kzqq_back.setOldfroseq(account.getOldseq());
				br57_kzqqMapper.insertBr57_kzqq_back(br57_kzqq_back);
				
				//请求输入
                Br57_kzqq_input br57_kzqq_input = new Br57_kzqq_input();
				BeanUtils.copyProperties(br57_kzqq_input, account);
				br57_kzqq_input.setDocno(docno);
				br57_kzqq_input.setCaseno(cases.getCaseno());
				br57_kzqq_input.setQrydt(DtUtils.getNowDate());
				br57_kzqq_input.setExeunit(cases.getExeunit());
				br57_kzqq_input.setFroseq(account.getUniqueid());//将唯一标识作为紧急止付流水号
				br57_kzqqMapper.insertBr57_kzqq_input(br57_kzqq_input);
				
				//3.4 插入机构
				String cxzh = account.getAccount();
				mc21_task_fact.setFacttablename(account.getName() + "#" + "mbjgdm");
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
					this.insertOrgMc21TaskFact1(mc21_task_fact, cases.getCaseno(), account.getUniqueid(), account.getType(), account.getAccount());
				} else {
					// 组织机构代码、工商营业执照、三合一
					this.insertOrgMc21TaskFact2(mc21_task_fact, cases.getCaseno(), account.getUniqueid(), account.getIdtype(), account.getId());
				}
				
				//3.4 插入task2
				mc21_task_fact.setBdhm(account.getUniqueid());//task2的bdhm存uniqueid
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
	
}
