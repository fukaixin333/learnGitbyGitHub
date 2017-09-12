package com.citic.server.cbrc.task.taskBo;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.cbrc.domain.Br41_kzqq;
import com.citic.server.cbrc.domain.CBRC_BasicInfo;
import com.citic.server.cbrc.domain.CBRC_QueryPerson;
import com.citic.server.cbrc.domain.request.CBRC_FreezeRequest_Record;
import com.citic.server.cbrc.domain.request.CBRC_StopPaymentRequest_Recored;
import com.citic.server.cbrc.domain.response.CBRC_FreezeResponse;
import com.citic.server.cbrc.domain.response.CBRC_FreezeResponse_Account;
import com.citic.server.cbrc.domain.response.CBRC_StopPaymentResponse;
import com.citic.server.cbrc.domain.response.CBRC_StopPaymentResponse_Account;
import com.citic.server.cbrc.mapper.MM41_kzqq_cbrcMapper;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;

/**
 * 控制请求解析
 * 
 * @author houxj
 */
public class Kzqq_JSBo extends BaseBo {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(Kzqq_JSBo.class);
	
	private MM41_kzqq_cbrcMapper br41_kzqqMapper;
	
	public Kzqq_JSBo(ApplicationContext ac) {
		super(ac);
		br41_kzqqMapper = (MM41_kzqq_cbrcMapper) ac.getBean("MM41_kzqq_cbrcMapper");
		
	}
	
	/**
	 * 删除请求任务：查询请求内容、查询请求信息及请求反馈信息
	 * 
	 * @param br40_cxqq 查询请求内容
	 * @throws Exception
	 */
	public void delFreezeResponse(Br41_kzqq br41_kzqq) throws Exception {
		//1.删除査询请求内容表
		//br41_kzqqMapper.delBr41_kzqq(br41_kzqq);
		//2.删除请求单任务信息表
		//br41_kzqqMapper.delBr41_kzqq_dj(br41_kzqq);
		//2.删除请求单反馈任务信息表
		//br41_kzqqMapper.delBr41_kzqq_dj_back( br41_kzqq);
	}
	
	public void delStopPayResponse(Br41_kzqq br41_kzqq) throws Exception {
		//1.删除査询请求内容表
		//br41_kzqqMapper.delBr41_kzqq(br41_kzqq);
		//2.删除请求单任务信息表
		//br41_kzqqMapper.delBr41_kzqq_zf(br41_kzqq);
		//br41_kzqqMapper.delBr41_kzqq_zf_back( br41_kzqq);
	}
	
	/**
	 * 插入冻结请求相关信息
	 * 
	 * @param bdhm
	 * @param bwPath
	 * @throws Exception
	 */
	public void insertBr41_kzqq_dj(CBRC_FreezeResponse query, MC21_task_fact mc21_task_fact) throws Exception {
		CBRC_BasicInfo basicinfo = query.getBasicInfo();
		//CBRC_QueryPerson queryperson = query.getQueryPerson();
		String qqdbs = basicinfo.getQqdbs();//请求单标识
		String mbjgdm = basicinfo.getMbjgdm(); //目标机构代码
		String tasktype = mc21_task_fact.getTasktype();//监管类别 
		String orgkey = this.getOrgKey(mbjgdm, tasktype);//归属机构
		Br41_kzqq br41_kzqq = new Br41_kzqq();
		br41_kzqq.setQqdbs(qqdbs);
		br41_kzqq.setTasktype(tasktype);
		//1.插入请求基本信息
		//basicinfo.setOrgkey(orgkey);//归属机构
		//this.insertBr41_kzqq(basicinfo,queryperson,tasktype);
		
		//2. 插入请求主体信息、反馈主体信息
		List<CBRC_FreezeResponse_Account> dtList = query.getFreezeAccountList();
		for (CBRC_FreezeResponse_Account freeze : dtList) {
			
			//2.1 插入请求主体信息
			freeze.setQqdbs(qqdbs);//请求单标识
			freeze.setTasktype(tasktype);//监管类别
			freeze.setOrgankey(orgkey);//归属机构
			freeze.setQrydt(DtUtils.getNowDate());//查询日期
			freeze.setZtlb(basicinfo.getZtlb());
			br41_kzqq.setRwlsh(freeze.getRwlsh());
			br41_kzqqMapper.delBr41_kzqq_dj(br41_kzqq);
			br41_kzqqMapper.insertBr41_kzqq_dj(freeze);
			
			//2.2 插入反馈信息表
			CBRC_FreezeRequest_Record kzqq_dj = new CBRC_FreezeRequest_Record();
			BeanUtils.copyProperties(kzqq_dj, query.getBasicInfo());//查询基本信息赋值到结果DTO中
			kzqq_dj.setQqdbs(qqdbs);//请求单标识
			kzqq_dj.setTasktype(tasktype);//监管类别
			kzqq_dj.setRwlsh(freeze.getRwlsh());//任务流水号
			kzqq_dj.setOrgankey(orgkey);//归属机构
			kzqq_dj.setQrydt(DtUtils.getNowDate());//查询日期
			kzqq_dj.setStatus("0");//状态
			kzqq_dj.setZh(freeze.getZh());
			kzqq_dj.setKh(freeze.getZh());
			kzqq_dj.setMsgcheckresult("1");
			kzqq_dj.setBeiz("");
			kzqq_dj.setSqdjxe(freeze.getJe());
			br41_kzqqMapper.delBr41_kzqq_dj_back(br41_kzqq);
			br41_kzqqMapper.insertBr41_kzqq_dj_back(kzqq_dj);
			
			//插入机构
			String cxzh = freeze.getZh();
			String zhxh = StrUtils.null2String(freeze.getZhxh());
			mc21_task_fact.setFacttablename(freeze.getDjzhhz() + "#" + mbjgdm);
			mc21_task_fact.setBdhm(qqdbs);

			if (cxzh != null && !cxzh.equals("") && (zhxh.equals("")||zhxh.equals("0"))) {
				this.insertOrgMc21TaskFact3(mc21_task_fact, freeze.getRwlsh(), basicinfo.getZtlb(), freeze.getZh());//卡号查询
			} else {
				if(zhxh.indexOf("_")<0){
					zhxh=	cxzh+"_"+zhxh;
				}
				this.insertOrgMc21TaskFact2(mc21_task_fact, freeze.getRwlsh(), "", zhxh); //子账户查询
			}

			//2.3.插入task2
			mc21_task_fact.setTaskobj(qqdbs);
			mc21_task_fact.setBdhm(qqdbs + "$" + freeze.getRwlsh());
			insertMc21TaskFact2(mc21_task_fact, "CBRC_" + tasktype);
		}
		
		if (mc21_task_fact.getIsemployee() != null && mc21_task_fact.getIsemployee().equals("0")) {
			br41_kzqq.setStatus("1");
			br41_kzqqMapper.updateBr41_kzqq(br41_kzqq);
		}
	}
	
	/**
	 * 插入冻结请求相关信息
	 * 
	 * @param bdhm
	 * @param bwPath
	 * @throws Exception
	 */
	public void insertBr41_kzqq_zf(CBRC_StopPaymentResponse query, MC21_task_fact mc21_task_fact) throws Exception {
		CBRC_BasicInfo basicinfo = query.getBasicInfo();
		//	CBRC_QueryPerson queryperson =query.getQueryPerson();
		String qqdbs = basicinfo.getQqdbs();//请求单标识
		String mbjgdm = basicinfo.getMbjgdm(); //目标机构代码
		String tasktype = mc21_task_fact.getTasktype();//监管类别 
		String orgkey = this.getOrgKey(mbjgdm, tasktype);//归属机构
		Br41_kzqq br41_kzqq = new Br41_kzqq();
		br41_kzqq.setQqdbs(qqdbs);
		br41_kzqq.setTasktype(tasktype);
		//1.插入请求基本信息
		//basicinfo.setOrgkey(orgkey);//归属机构
		//this.insertBr41_kzqq(basicinfo,queryperson,tasktype);
		
		//2. 插入请求主体信息、反馈主体信息
		List<CBRC_StopPaymentResponse_Account> stoppayList = query.getStopPaymentAccountList();
		for (CBRC_StopPaymentResponse_Account stoppay : stoppayList) {
			
			//2.1 插入请求主体信息
			stoppay.setQqdbs(qqdbs);//请求单标识
			stoppay.setTasktype(tasktype);//监管类别
			stoppay.setOrgankey(orgkey);//归属机构
			stoppay.setQrydt(DtUtils.getNowDate());//查询日期
			stoppay.setZtlb(basicinfo.getZtlb());
			br41_kzqq.setRwlsh(stoppay.getRwlsh());
			br41_kzqqMapper.delBr41_kzqq_zf(br41_kzqq);
			br41_kzqqMapper.insertBr41_kzqq_zf(stoppay);
			
			//2.2 插入反馈信息表
			CBRC_StopPaymentRequest_Recored kzqq_zf = new CBRC_StopPaymentRequest_Recored();
			BeanUtils.copyProperties(kzqq_zf, query.getBasicInfo());//查询基本信息赋值到结果DTO中
			kzqq_zf.setQqdbs(qqdbs);//请求单标识
			kzqq_zf.setTasktype(tasktype);//监管类别
			kzqq_zf.setRwlsh(stoppay.getRwlsh());//任务流水号
			kzqq_zf.setOrgankey(orgkey);//归属机构
			kzqq_zf.setQrydt(DtUtils.getNowDate());//查询日期
			kzqq_zf.setStatus("0");//状态
			kzqq_zf.setZh(stoppay.getZh());
			kzqq_zf.setKh(stoppay.getZh());
			kzqq_zf.setMsgcheckresult("1");
			br41_kzqqMapper.delBr41_kzqq_zf_back(br41_kzqq);
			br41_kzqqMapper.insertBr41_kzqq_zf_back(kzqq_zf);
			
			//插入机构
			
			mc21_task_fact.setFacttablename("" + "#" + mbjgdm);
			mc21_task_fact.setBdhm(qqdbs);
			
			this.insertOrgMc21TaskFact1(mc21_task_fact, stoppay.getRwlsh(), basicinfo.getZtlb(), stoppay.getZh());
			
			//2.3.插入task2
			mc21_task_fact.setTaskobj(qqdbs);
			mc21_task_fact.setBdhm(qqdbs + "$" + stoppay.getRwlsh());
			insertMc21TaskFact2(mc21_task_fact, "CBRC");
		}
		
		if (mc21_task_fact.getIsemployee() != null && mc21_task_fact.getIsemployee().equals("0")) {
			br41_kzqq.setStatus("1");
			br41_kzqqMapper.updateBr41_kzqq(br41_kzqq);
		}
	}
	
	/**
	 * 插入查询请求单主表
	 * 
	 * @param basicinfo 请求基本信息
	 * @param queryperson 请求人信息
	 * @param tasktype 监管类别
	 * @throws Exception
	 */
	public void insertBr41_kzqq(CBRC_BasicInfo basicinfo, CBRC_QueryPerson queryperson, String tasktype) throws Exception {
		/** 查询请求内容 */
		Br41_kzqq br41_kzqq = new Br41_kzqq();
		BeanUtils.copyProperties(br41_kzqq, queryperson);//查询人信息赋值到结果DTO中
		BeanUtils.copyProperties(br41_kzqq, basicinfo);//基本查询信息赋值到结果DTO中
		br41_kzqq.setStatus("0");//状态
		br41_kzqq.setQrydt(DtUtils.getNowDate());
		br41_kzqq.setTasktype(tasktype);//监管类别
		//插入请求单信息表
		br41_kzqqMapper.insertBr41_kzqq(br41_kzqq);
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
	 * 止付
	 */
	public void insertOrgMc21TaskFact1(MC21_task_fact mc21_task_fact, String rwlsh, String credentialtype, String cardnumber) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String lastupdt = DtUtils.getNowTime();
		String returnStr = "update  br41_kzqq_zf set organkey=@A@  where  qqdbs='"
							+ mc21_task_fact.getBdhm()
							+ "' and rwlsh='"
							+ rwlsh
							+ "' and tasktype='"
							+ mc21_task_fact.getTasktype()
							+ "';";
		returnStr = returnStr
					+ "update  br41_kzqq_zf_back set organkey=@A@,msgcheckresult='1' , zxjg='',status='0', sbyy='' ,last_up_dt='"
					+ lastupdt
					+ "'   where  qqdbs='"
					+ mc21_task_fact.getBdhm()
					+ "' and rwlsh='"
					+ rwlsh
					+ "' and tasktype='"
					+ mc21_task_fact.getTasktype()
					+ "'&";
		returnStr = returnStr
					+ "update  br41_kzqq_zf_back set msgcheckresult=@D@ ,zxjg='1'   ,status='2', sbyy='查无此客户',last_up_dt='"
					+ lastupdt
					+ "' where  qqdbs='"
					+ mc21_task_fact.getBdhm()
					+ "' and rwlsh='"
					+ rwlsh
					+ "' and tasktype='"
					+ mc21_task_fact.getTasktype()
					+ "'";
		mc21_task_fact.setTaskobj(returnStr);
		mc21_task_fact.setBdhm(bdhm + "$" + rwlsh);
		this.insertMc21TaskFact2_Org(mc21_task_fact, "1", cardnumber, credentialtype, mc21_task_fact.getTasktype());
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj("");
	}
	
	/**
	 * 冻结
	 * type:1:卡号 2：证件号码 3子账号
	 * subjecttype: 1自然人 2法人
	 * 
	 * @param mc21_task_fact
	 * @param type
	 * @param cardnumber
	 */
	public void insertOrgMc21TaskFact2(MC21_task_fact mc21_task_fact, String rwlsh, String zjlx, String zjhm) throws Exception {
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String lastupdt = DtUtils.getNowTime();
		String returnStr = "update  br41_kzqq_dj set organkey=@A@  where  qqdbs='"
							+ mc21_task_fact.getBdhm()
							+ "' and rwlsh='"
							+ rwlsh
							+ "' and tasktype='"
							+ mc21_task_fact.getTasktype()
							+ "';";
		returnStr = returnStr
					+ "update  br41_kzqq_dj_back set organkey=@A@,msgcheckresult='1'  ,last_up_dt='"
					+ lastupdt
					+ "'   where  qqdbs='"
					+ mc21_task_fact.getBdhm()
					+ "' and rwlsh='"
					+ rwlsh
					+ "' and tasktype='"
					+ mc21_task_fact.getTasktype()
					+ "'&";
		returnStr = returnStr
					+ "update  br41_kzqq_dj_back set msgcheckresult=@D@ ,zxjg='1' ,status='2', wndjyy='查无此客户',last_up_dt='"
					+ lastupdt
					+ "'  where  qqdbs='"
					+ mc21_task_fact.getBdhm()
					+ "' and rwlsh='"
					+ rwlsh
					+ "' and tasktype='"
					+ mc21_task_fact.getTasktype()
					+ "'";
		mc21_task_fact.setTaskobj(returnStr);
		mc21_task_fact.setBdhm(bdhm + "$" + rwlsh);
		this.insertMc21TaskFact2_Org(mc21_task_fact, "3", zjhm, zjlx, mc21_task_fact.getTasktype());
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj("");
		
	}
	
	public void insertOrgMc21TaskFact3(MC21_task_fact mc21_task_fact, String rwlsh, String credentialtype, String cardnumber) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String lastupdt = DtUtils.getNowTime();
		String returnStr = "update  br41_kzqq_dj set organkey=@A@  where  qqdbs='"
							+ mc21_task_fact.getBdhm()
							+ "' and rwlsh='"
							+ rwlsh
							+ "' and tasktype='"
							+ mc21_task_fact.getTasktype()
							+ "';";
		returnStr = returnStr
					+ "update  br41_kzqq_dj_back set organkey=@A@,msgcheckresult='1', zxjg='',status='0', wndjyy='' ,last_up_dt='"
					+ lastupdt
					+ "'    where  qqdbs='"
					+ mc21_task_fact.getBdhm()
					+ "' and rwlsh='"
					+ rwlsh
					+ "' and tasktype='"
					+ mc21_task_fact.getTasktype()
					+ "'&";
		returnStr = returnStr
					+ "update  br41_kzqq_dj_back set msgcheckresult=@D@ ,zxjg='1' , status='2', wndjyy='查无此客户',last_up_dt='"
					+ lastupdt
					+ "'    where  qqdbs='"
					+ mc21_task_fact.getBdhm()
					+ "' and rwlsh='"
					+ rwlsh
					+ "' and tasktype='"
					+ mc21_task_fact.getTasktype()
					+ "'";
		mc21_task_fact.setTaskobj(returnStr);
		mc21_task_fact.setBdhm(bdhm + "$" + rwlsh);
		this.insertMc21TaskFact2_Org(mc21_task_fact, "1", cardnumber, credentialtype, mc21_task_fact.getTasktype());
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj("");
	}
}
