package com.citic.server.gdjc.task.taskBo;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.gdjc.domain.Br50_cxqq;
import com.citic.server.gdjc.domain.Br50_cxqq_back;
import com.citic.server.gdjc.domain.Br50_cxqq_mx;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseCkcx;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseCkcx_Case;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseCkcx_Respondent;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseLscx;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseLscx_Acc;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseLscx_Case;
import com.citic.server.gdjc.mapper.MM50_cxqqMapper;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;

/**
 * 常规查询
 * 
 * @author Hxj
 */
public class Cxqq_JSBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Cxqq_JSBo.class);
	
	private MM50_cxqqMapper br50_cxqqMapper;
	
	public Cxqq_JSBo(ApplicationContext ac) {
		super(ac);
		br50_cxqqMapper = (MM50_cxqqMapper) ac.getBean("MM50_cxqqMapper");
		
	}
	
	/**
	 * 删除请求任务：查询请求内容、查询请求信息及请求反馈信息
	 * 
	 * @param br50_cxqq 查询请求内容
	 * @throws Exception
	 */
	public void delQureyResponse(Br50_cxqq br50_cxqq) throws Exception {
		//1.删除査询请求内容表
		br50_cxqqMapper.delBr50_cxqq(br50_cxqq);
		//2.删除请求单任务信息表
		br50_cxqqMapper.delBr50_cxqq_mx(br50_cxqq);
		//3.删除请求反馈信息表
		br50_cxqqMapper.delBr50_cxqq_back(br50_cxqq);
	}
	
	/**
	 * 处理文件
	 * 
	 * @param query_cgcx
	 * @param isemployee
	 * @throws Exception
	 */
	public void handleCxqqBw_Ckcx(Gdjc_ResponseCkcx query_ckcx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		
		String docno = query_ckcx.getDocno();//协作编号
		List<Gdjc_ResponseCkcx_Case> casesList = query_ckcx.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构
		
		for (Gdjc_ResponseCkcx_Case cases : casesList) {
			//1.插入请求基本信息
			Br50_cxqq br50_cxqq = new Br50_cxqq();
			br50_cxqq.setDocno(docno);
			br50_cxqq.setCaseno(cases.getCaseno());
			br50_cxqq.setCasename(cases.getCasename());
			br50_cxqq.setExeunit(cases.getExeunit());
			br50_cxqq.setDatasource("1");//1：账户 2：交易	
			br50_cxqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br50_cxqq.setOrgkey(orgkey);
			br50_cxqq.setQrydt(DtUtils.getNowDate());
			br50_cxqq.setLast_up_dt(DtUtils.getNowTime());
			br50_cxqqMapper.insertBr50_cxqq(br50_cxqq);
			
			//2.插入请求主体信息、反馈主体信息
			List<Gdjc_ResponseCkcx_Respondent> respondentsList = cases.getRespondents();
			for (Gdjc_ResponseCkcx_Respondent respondents : respondentsList) {
				//2.1 插入请求主体信息
				Br50_cxqq_mx qqmx = new Br50_cxqq_mx();
				BeanUtils.copyProperties(qqmx, respondents);
				qqmx.setDocno(docno);
				qqmx.setCaseno(cases.getCaseno());
				qqmx.setQrydt(DtUtils.getNowDate());
				br50_cxqqMapper.insertBr50_cxqq_mx(qqmx);
				
				//2.2 插入反馈主体信息
				Br50_cxqq_back br50_cxqq_back = new Br50_cxqq_back();
				br50_cxqq_back.setDocno(docno);
				br50_cxqq_back.setCaseno(cases.getCaseno());
				br50_cxqq_back.setUniqueid(respondents.getUniqueid());
				br50_cxqq_back.setQrydt(DtUtils.getNowDate());
				br50_cxqq_back.setStatus("0");
				br50_cxqq_back.setLast_up_dt(DtUtils.getNowTime());
				br50_cxqqMapper.insertBr50_cxqq_back(br50_cxqq_back);
				
				//2.3 插入机构
				String cxzh = respondents.getAccount();
				mc21_task_fact.setFacttablename(respondents.getName() + "#" + "mbjgdm");
				mc21_task_fact.setBdhm(docno);
				/*
				 * 查询方式:
				 * 1：按名称、证件号查询 2：按账号或卡号查询
				 * 3：按单位名称查询 4：按组织机构代码查询 5：按工商营业执照编码查询 6：按三证合一编号查询
				 * 注：查询自然人时，只能按第 1、2 种方式查询；查询法人时，只能按第 2、3、4、5 、6种方式查询
				 */
				if (cxzh != null && !cxzh.equals("")) {
					this.insertOrgMc21TaskFact1(mc21_task_fact, cases.getCaseno(), respondents.getUniqueid(), respondents.getType(), respondents.getAccount());
				} else {
					this.insertOrgMc21TaskFact2(mc21_task_fact, cases.getCaseno(), respondents.getUniqueid(), respondents.getIdtype(), respondents.getId());
				}
				
				//2.4 插入task2
				mc21_task_fact.setBdhm(respondents.getUniqueid());//task2的bdhm存uniqueid
				mc21_task_fact.setTaskobj(docno + ";" + cases.getCaseno());//task2的taskobj存docno+caseno，以;分隔,在task中拆分使用
				this.insertMc21TaskFact2(mc21_task_fact, "GDJC");
			}
			
		}
		
	}
	
	/**
	 * 处理文件
	 * 
	 * @param query_cgcx
	 * @param isemployee
	 * @throws Exception
	 */
	public void handleCxqqBw_Lscx(Gdjc_ResponseLscx query_lscx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		
		String docno = query_lscx.getDocno();//协作编号
		List<Gdjc_ResponseLscx_Case> casesList = query_lscx.getCases();
		
		String orgkey = this.getOrgKey("306581000003", mc21_task_fact.getTasktype());//归属机构
		
		for (Gdjc_ResponseLscx_Case cases : casesList) {
			//1.插入请求基本信息
			Br50_cxqq br50_cxqq = new Br50_cxqq();
			br50_cxqq.setDocno(docno);
			br50_cxqq.setCaseno(cases.getCaseno());
			br50_cxqq.setCasename(cases.getCasename());
			br50_cxqq.setExeunit(cases.getExeunit());
			br50_cxqq.setDatasource("2");//1：账户 2：交易	
			br50_cxqq.setStatus("0");//0：待处理 1：待生成报文 3：反馈成功 4：反馈失败 
			br50_cxqq.setOrgkey(orgkey);
			br50_cxqq.setQrydt(DtUtils.getNowDate());
			br50_cxqq.setLast_up_dt(DtUtils.getNowTime());
			br50_cxqqMapper.insertBr50_cxqq(br50_cxqq);
			
			//2.插入请求主体信息、反馈主体信息
			List<Gdjc_ResponseLscx_Acc> respondentsList = cases.getAccs();
			for (Gdjc_ResponseLscx_Acc respondents : respondentsList) {
				//2.1 插入请求主体信息
				Br50_cxqq_mx qqmx = new Br50_cxqq_mx();
				BeanUtils.copyProperties(qqmx, respondents);
				qqmx.setDocno(docno);
				qqmx.setCaseno(cases.getCaseno());
				qqmx.setName(respondents.getAccname());
				qqmx.setQrydt(DtUtils.getNowDate());
				br50_cxqqMapper.insertBr50_cxqq_mx(qqmx);
				
				//2.2 插入反馈主体信息
				Br50_cxqq_back br50_cxqq_back = new Br50_cxqq_back();
				br50_cxqq_back.setDocno(docno);
				br50_cxqq_back.setCaseno(cases.getCaseno());
				br50_cxqq_back.setUniqueid(respondents.getUniqueid());
				br50_cxqq_back.setQrydt(DtUtils.getNowDate());
				br50_cxqq_back.setStatus("0");
				br50_cxqq_back.setLast_up_dt(DtUtils.getNowTime());
				br50_cxqqMapper.insertBr50_cxqq_back(br50_cxqq_back);
				
				//2.3 插入机构
				String cxzh = respondents.getAccount();
				mc21_task_fact.setFacttablename(respondents.getAccname() + "#" + "mbjgdm");
				mc21_task_fact.setBdhm(docno);
				
				if (cxzh != null && !cxzh.equals("")) {
					this.insertOrgMc21TaskFact1(mc21_task_fact, cases.getCaseno(), respondents.getUniqueid(), respondents.getType(), respondents.getAccount());
				} else {
					this.insertOrgMc21TaskFact2(mc21_task_fact, cases.getCaseno(), respondents.getUniqueid(), respondents.getIdtype(), respondents.getId());
				}
				
				//2.4 插入task2
				mc21_task_fact.setBdhm(respondents.getUniqueid());//task2的bdhm存uniqueid
				mc21_task_fact.setTaskobj(docno + ";" + cases.getCaseno());//task2的taskobj存docno+caseno，以;分隔,在task中拆分使用
				this.insertMc21TaskFact2(mc21_task_fact, "GDJC");
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
		returnStr = returnStr + "update  br50_cxqq_back set orgkey=@A@,msgcheckresult='1'   where  docno='" + mc21_task_fact.getBdhm() + "' and uniqueid='" + uniqueid
					+ "' and caseno='" + caseno + "'&";
		returnStr = returnStr + "update  br50_cxqq_back set msgcheckresult=@D@ ,Status='5' where  docno='" + mc21_task_fact.getBdhm() + "' and uniqueid='" + uniqueid
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
