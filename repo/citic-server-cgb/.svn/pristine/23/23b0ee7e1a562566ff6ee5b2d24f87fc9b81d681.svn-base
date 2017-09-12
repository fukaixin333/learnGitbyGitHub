package com.citic.server.jsga.task.taskBo;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.dict.DictCoder;
import com.citic.server.jsga.domain.Br40_cxqq;
import com.citic.server.jsga.domain.Br40_cxqq_back;
import com.citic.server.jsga.domain.Br40_cxqq_back_pz;
import com.citic.server.jsga.domain.Br40_cxqq_mx;
import com.citic.server.jsga.domain.JSGA_BasicInfo;
import com.citic.server.jsga.domain.JSGA_QueryPerson;
import com.citic.server.jsga.domain.response.JSGA_ControlResponse;
import com.citic.server.jsga.domain.response.JSGA_ControlResponse_Account;
import com.citic.server.jsga.domain.response.JSGA_QueryResponse;
import com.citic.server.jsga.domain.response.JSGA_QueryResponse_Main;
import com.citic.server.jsga.domain.response.JSGA_QueryScanResponse;
import com.citic.server.jsga.domain.response.JSGA_QueryScanResponse_Info;
import com.citic.server.jsga.mapper.MM40_cxqq_jsgaMapper;
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
	
	private MM40_cxqq_jsgaMapper br40_cxqqMapper;
	
	/** 数据字典码值转换组件 */
	private DictCoder dictCoder;
	
	public Cxqq_JSBo(ApplicationContext ac) {
		super(ac);
		br40_cxqqMapper = (MM40_cxqq_jsgaMapper) ac.getBean("MM40_cxqq_jsgaMapper");
		dictCoder = ac.getBean(DictCoder.class);
	}
	
	/**
	 * 删除请求任务：查询请求内容、查询请求信息及请求反馈信息
	 * 
	 * @param br40_cxqq 查询请求内容
	 * @throws Exception
	 */
	public void delQureyResponse(Br40_cxqq br40_cxqq) throws Exception {
		//1.删除査询请求内容表
		//br40_cxqqMapper.delBr40_cxqq(br40_cxqq);
		//2.删除请求单任务信息表
		//br40_cxqqMapper.delBr40_cxqq_mx(br40_cxqq);
		//3.删除请求反馈信息表
		//br40_cxqqMapper.delBr40_cxqq_back(br40_cxqq);
	}
	
	/**
	 * 处理文件
	 * 
	 * @param query_cgcx
	 * @param isemployee
	 * @throws Exception
	 */
	public void handleCxqqBw(JSGA_QueryResponse query_cgcx, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		JSGA_BasicInfo basicinfo = query_cgcx.getBasicInfo();
		
		String qqdbs = basicinfo.getQqdbs();//请求单标识
		String mbjgdm = basicinfo.getMbjgdm(); //目标机构代码
		String tasktype = mc21_task_fact.getTasktype();//监管类别 
		String orgkey = this.getOrgKey(mbjgdm, tasktype);//归属机构
		//1.插入请求基本信息
		//this.insertBr40_cxqq(basicinfo, queryperson, tasktype);
		Br40_cxqq br40_cxqq = new Br40_cxqq();
		br40_cxqq.setQqdbs(qqdbs);
		br40_cxqq.setTasktype(tasktype);
		//2.插入请求主体信息、反馈主体信息
		List<JSGA_QueryResponse_Main> querymain_cgList = query_cgcx.getQueryMainList();
		for (JSGA_QueryResponse_Main querymain_Cg : querymain_cgList) {
			String rwlsh = querymain_Cg.getRwlsh();//任务流水号
			//2.1 插入请求主体信息
			Br40_cxqq_mx qqmx = new Br40_cxqq_mx();
			BeanUtils.copyProperties(qqmx, querymain_Cg);
			qqmx.setQqdbs(qqdbs);//请求单标识
			qqmx.setTasktype(tasktype);//监管类别
			qqmx.setOrgkey(orgkey);//归属机构
			qqmx.setQrydt(DtUtils.getNowDate());//查询日期
			qqmx.setZtlb(basicinfo.getZtlb());
			qqmx.setXmltype(taskmag.getCode());
			br40_cxqq.setRwlsh(rwlsh);
			if (querymain_Cg.getCxzh() == null || querymain_Cg.getCxzh().equals("")) {
				qqmx.setCxlx("01");// 按照证件类型，证件号码
			} else {
				qqmx.setCxlx("02"); //按照账号查询
			}
			br40_cxqqMapper.delBr40_cxqq_mx(br40_cxqq);
			br40_cxqqMapper.insertBr40_cxqq_mx(qqmx);
			
			//2.2 插入反馈主体信息
			Br40_cxqq_back br40_cxqq_back = new Br40_cxqq_back();
			BeanUtils.copyProperties(br40_cxqq_back, basicinfo);//查询基本信息赋值到结果DTO中
			br40_cxqq_back.setQqdbs(qqdbs);//请求单标识
			br40_cxqq_back.setTasktype(tasktype);//监管类别
			br40_cxqq_back.setRwlsh(rwlsh);//任务流水号
			br40_cxqq_back.setCxjssj(DtUtils.getNowDate());//查询结束时间
			br40_cxqq_back.setZh(querymain_Cg.getCxzh());//账号
			br40_cxqq_back.setKh(querymain_Cg.getCxzh());//卡号
			br40_cxqq_back.setOrgkey(orgkey);//归属机构
			br40_cxqq_back.setQrydt(DtUtils.getNowDate());//查询日期
			br40_cxqq_back.setStatus("0");//状态
			br40_cxqq_back.setSeq("0");
			br40_cxqq_back.setMsgcheckresult("1");
			br40_cxqqMapper.delBr40_cxqq_back(br40_cxqq);
			br40_cxqqMapper.insertBr40_cxqq_back(br40_cxqq_back);
			
			//2.3 插入机构
			String cxzh = querymain_Cg.getCxzh();
			if (querymain_Cg.getZtmc() == null) {
				querymain_Cg.setZtmc("");
			}
			mc21_task_fact.setFacttablename(querymain_Cg.getZtmc() + "#" + mbjgdm);
			mc21_task_fact.setBdhm(qqdbs);

			if (cxzh != null && !cxzh.equals("")) {
				this.insertOrgMc21TaskFact1(mc21_task_fact, rwlsh, basicinfo.getZtlb(), querymain_Cg.getCxzh(), basicinfo.getQqcslx());
			} else {
				// 码值转换（将监管数据转换为核心系统可识别的数据）
				dictCoder.transcode(qqmx, tasktype);
				this.insertOrgMc21TaskFact2(mc21_task_fact, rwlsh, qqmx.getZzlx(), querymain_Cg.getZzhm());
			}

			//2.4 插入task2
			mc21_task_fact.setBdhm(qqdbs + "$" + rwlsh);
			this.insertMc21TaskFact2(mc21_task_fact, "JSGA_" + tasktype);
		}
		
		if (mc21_task_fact.getIsemployee() != null && mc21_task_fact.getIsemployee().equals("0")) {
			br40_cxqq.setStatus("1");
			br40_cxqqMapper.updateBr40_cxqq(br40_cxqq);
		}
	}
	
	/**
	 * 插入动态查询请求相关信息
	 * 
	 * @param bdhm
	 * @param bwPath
	 * @throws Exception
	 */
	public void insertBr40_cxqq_dt(JSGA_ControlResponse query, MC21_task_fact mc21_task_fact, MC20_task_msg taskmag) throws Exception {
		JSGA_BasicInfo basicinfo = query.getBasicInfo();
		
		String qqdbs = basicinfo.getQqdbs();//请求单标识
		String mbjgdm = basicinfo.getMbjgdm(); //目标机构代码
		String tasktype = mc21_task_fact.getTasktype();//监管类别 
		String orgkey = this.getOrgKey(mbjgdm, tasktype);//归属机构
		//1.插入请求基本信息
		//basicinfo.setOrgkey(orgkey);//归属机构
		//this.insertBr40_cxqq(basicinfo, queryperson, tasktype);
		
		//2. 插入请求主体信息、反馈主体信息
		List<JSGA_ControlResponse_Account> dtList = query.getControlAccountList();
		for (JSGA_ControlResponse_Account querymain_dt : dtList) {
			String rwlsh = querymain_dt.getRwlsh();
			Br40_cxqq br40_cxqq = new Br40_cxqq();
			br40_cxqq.setTasktype(tasktype);
			br40_cxqq.setQqdbs(qqdbs);
			br40_cxqq.setRwlsh(rwlsh);
			//2.1 插入请求主体信息
			Br40_cxqq_mx qqmx = new Br40_cxqq_mx();
			BeanUtils.copyProperties(qqmx, querymain_dt);
			qqmx.setQqdbs(qqdbs);//请求单标识
			qqmx.setTasktype(tasktype);//监管类别
			qqmx.setOrgkey(orgkey);//归属机构
			qqmx.setQrydt(DtUtils.getNowDate());//查询日期
			qqmx.setCxzh(querymain_dt.getZh());
			qqmx.setMxsdlx(querymain_dt.getZxsjqj());
			qqmx.setMxqssj(querymain_dt.getKssj());
			qqmx.setMxjzsj(querymain_dt.getJssj());
			qqmx.setZtlb(basicinfo.getZtlb());
			qqmx.setXmltype(taskmag.getCode());
			qqmx.setCxlx("02");
			br40_cxqqMapper.delBr40_cxqq_mx(br40_cxqq);
			br40_cxqqMapper.insertBr40_cxqq_mx(qqmx);
			//if (query.getBasicInfo().getQqcslx().equals("04")) { //解除动态查询可插入反馈表
			//2.2 插入反馈信息表
			Br40_cxqq_back br40_cxqq_back = new Br40_cxqq_back();
			BeanUtils.copyProperties(br40_cxqq_back, query.getBasicInfo());//查询基本信息赋值到结果DTO中
			br40_cxqq_back.setQqdbs(qqdbs);//请求单标识
			br40_cxqq_back.setTasktype(tasktype);//监管类别
			br40_cxqq_back.setRwlsh(rwlsh);//任务流水号
			br40_cxqq_back.setOrgkey(orgkey);//归属机构
			br40_cxqq_back.setQrydt(DtUtils.getNowDate());//查询日期
			br40_cxqq_back.setStatus("0");//状态
			br40_cxqq_back.setSeq("0");
			br40_cxqq_back.setCxfkjg("0");// 0-成功
			br40_cxqq_back.setFksjhm(query.getQueryPerson().getQqrsjh()); // 反馈手机号码
			br40_cxqq_back.setZxqssj(querymain_dt.getKssj()); // 执行起始时间
			br40_cxqq_back.setZxsjqj(querymain_dt.getZxsjqj()); // 执行时间区间
			br40_cxqq_back.setJssj(querymain_dt.getJssj()); // 结束时间
			br40_cxqq_back.setMsgcheckresult("1");
			br40_cxqq_back.setKh(qqmx.getZh());
			br40_cxqq_back.setZh(qqmx.getZh());
			br40_cxqqMapper.delBr40_cxqq_back(br40_cxqq);
			br40_cxqqMapper.insertBr40_cxqq_back(br40_cxqq_back);
			//}
			//插入机构任务
			mc21_task_fact.setFacttablename("" + "#" + mbjgdm);
			mc21_task_fact.setBdhm(qqdbs);
			this.insertOrgMc21TaskFact1(mc21_task_fact, rwlsh, basicinfo.getZtlb(), qqmx.getCxzh(), basicinfo.getQqcslx());
			
			//2.3 插入task2
			mc21_task_fact.setBdhm(qqdbs + "$" + rwlsh);
			this.insertMc21TaskFact2(mc21_task_fact, "JSGA");
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
	public void insertBr40_cxqq(JSGA_BasicInfo basicinfo, JSGA_QueryPerson queryperson, String tasktype) throws Exception {
		/** 查询请求内容 */
		Br40_cxqq br40_cxqq = new Br40_cxqq();
		BeanUtils.copyProperties(br40_cxqq, queryperson);//查询人信息赋值到结果DTO中
		BeanUtils.copyProperties(br40_cxqq, basicinfo);//基本查询信息赋值到结果DTO中
		br40_cxqq.setStatus("0");//状态
		br40_cxqq.setQrydt(DtUtils.getNowDate());
		br40_cxqq.setTasktype(tasktype);//监管类别
		//插入请求单信息表
		br40_cxqqMapper.insertBr40_cxqq(br40_cxqq);
	}
	
	/**
	 * 解析附件并插入
	 * 
	 * @param bdhm
	 * @param bwPath
	 * @throws Exception
	 */
	public void insertBr40_wh_attach(Br40_cxqq br40_cxqq) throws Exception {
		br40_cxqq.setFssj(DtUtils.getNowTime());
		br40_cxqqMapper.delBr40_wh_attach(br40_cxqq);
		br40_cxqqMapper.insertBr40_wh_attach(br40_cxqq);
		
	}
	
	/**
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
		String returnStr = "update  Br40_cxqq_mx set orgkey=@A@  where  qqdbs='"
							+ mc21_task_fact.getBdhm()
							+ "' and rwlsh='"
							+ rwlsh
							+ "' and tasktype='"
							+ mc21_task_fact.getTasktype()
							+ "';";
		returnStr = returnStr
					+ "update  br40_cxqq_back set orgkey=@A@,msgcheckresult='1' ,cxfkjg='',czsbyy = '',status='0' ,last_up_dt='"
					+ lastupdt
					+ "'  where  qqdbs='"
					+ mc21_task_fact.getBdhm()
					+ "' and rwlsh='"
					+ rwlsh
					+ "' and tasktype='"
					+ mc21_task_fact.getTasktype()
					+ "'&";
		returnStr = returnStr
					+ "update  br40_cxqq_back set msgcheckresult=@D@ ,cxfkjg='02' ,czsbyy = '查无客户信息',status='2'  ,last_up_dt='"
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
		this.insertMc21TaskFact2_Org(mc21_task_fact, "2", zjhm, zjlx, mc21_task_fact.getTasktype());
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj("");
		
	}
	
	public void insertOrgMc21TaskFact1(MC21_task_fact mc21_task_fact, String rwlsh, String credentialtype, String cardnumber, String qqcslx) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String lastupdt = DtUtils.getNowTime();
		String returnStr = "update  Br40_cxqq_mx set orgkey=@A@  where  qqdbs='"
							+ mc21_task_fact.getBdhm()
							+ "' and rwlsh='"
							+ rwlsh
							+ "' and tasktype='"
							+ mc21_task_fact.getTasktype()
							+ "';";
		returnStr = returnStr
					+ "update  br40_cxqq_back set orgkey=@A@,msgcheckresult='1',cxfkjg='',czsbyy = '',status='0' ,last_up_dt='"
					+ lastupdt
					+ "'  where  qqdbs='"
					+ mc21_task_fact.getBdhm()
					+ "' and rwlsh='"
					+ rwlsh
					+ "' and tasktype='"
					+ mc21_task_fact.getTasktype();
		if (qqcslx.equals("02") || qqcslx.equals("03")) { //是否动态查询，动态查询走task2
			returnStr = returnStr
						+ "'&"
						+ "update  br40_cxqq_back set msgcheckresult=@D@ ,cxfkjg='1' ,czsbyy = '查无客户信息',last_up_dt='"
						+ lastupdt
						+ "'    where  qqdbs='"
						+ mc21_task_fact.getBdhm()
						+ "' and rwlsh='"
						+ rwlsh
						+ "' and tasktype='"
						+ mc21_task_fact.getTasktype()
						+ "'";
		} else {
			String cxfkjg = "02";
			if (qqcslx.equals("04")) {
				cxfkjg = "1";
			}
			returnStr = returnStr
						+ "'&"
						+ "update  br40_cxqq_back set msgcheckresult=@D@ ,cxfkjg='"
						+ cxfkjg
						+ "' ,czsbyy = '查无客户信息',status='2' ,last_up_dt='"
						+ lastupdt
						+ "'    where  qqdbs='"
						+ mc21_task_fact.getBdhm()
						+ "' and rwlsh='"
						+ rwlsh
						+ "' and tasktype='"
						+ mc21_task_fact.getTasktype()
						+ "'";
		}
		mc21_task_fact.setTaskobj(returnStr);
		mc21_task_fact.setBdhm(bdhm + "$" + rwlsh);
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
	
public void insertOrgMc21TaskFact3(MC21_task_fact mc21_task_fact, String rwlsh, String type, String cardnumber, String qqcslx) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String lastupdt = DtUtils.getNowTime();
		String 	returnStr =  "update  br40_cxqq_back_pz set orgkey=@A@,msgcheckresult='1',last_up_dt='"
					+ lastupdt
					+ "'  where  qqdbs='"
					+ mc21_task_fact.getBdhm()
					+ "' and rwlsh='"
					+ rwlsh
					+ "' and tasktype='"
					+ mc21_task_fact.getTasktype();
		
			returnStr = returnStr
						+ "'&"
						+ "update  br40_cxqq_back_pz set msgcheckresult=@D@ ,cxfkjg='1' ,cxfkjgyy = '查无客户信息',last_up_dt='"
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
		this.insertMc21TaskFact2_Org(mc21_task_fact,type, cardnumber, type, mc21_task_fact.getTasktype());
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj("");
	}
	
}
