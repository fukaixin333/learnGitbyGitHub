package com.citic.server.gf.task.taskBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.crypto.MD5Coder;
import com.citic.server.dict.DictCoder;
import com.citic.server.gf.domain.Br31_kzcl_info;
import com.citic.server.gf.domain.Br32_msg;
import com.citic.server.gf.domain.MC20_GZZ;
import com.citic.server.gf.domain.request.ControlRequest;
import com.citic.server.gf.domain.request.ControlRequest_Djxx;
import com.citic.server.gf.domain.request.ControlRequest_Hzxx;
import com.citic.server.gf.domain.request.ControlRequest_HzxxList;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.ControlRequest_Qlxx;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.response.ControlResponse_Kzqq;
import com.citic.server.gf.domain.response.ControlResponse_Kzzh;
import com.citic.server.gf.service.IDataOperate01;
import com.citic.server.net.mapper.MM31_kzqqMapper;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.BB13_organ_telno;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.StrUtils;

public class KzqqBo extends BaseBo implements Constants {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(KzqqBo.class);
	
	/** 数据获取接口 */
	private IDataOperate01 dataOperate;
	
	private DictCoder dictCoder; // 数据字典转换工具
	
	private MM31_kzqqMapper mm31_kzqqMapper;
	
	public KzqqBo(ApplicationContext ac) {
		super(ac);
		mm31_kzqqMapper = (MM31_kzqqMapper) ac.getBean("MM31_kzqqMapper");
		dictCoder = SpringContextHolder.getBean(DictCoder.class);
	}
	
	/**
	 * 删除请求单号下的信息
	 * 
	 * @param bdhm
	 * @throws Exception
	 */
	public void delKzqqBw(String bdhm) throws Exception {
		//删除请求表
		mm31_kzqqMapper.delBr31_kzqq(bdhm);
		//删除请求账户表
		mm31_kzqqMapper.delBr31_kzzh(bdhm);
		//删除控制处理信息
		mm31_kzqqMapper.delBr31_kzcl_info(bdhm);
		//删除文书表
		mm31_kzqqMapper.delBr31_Ws(bdhm);
		//删除工作证表
		mm31_kzqqMapper.delBr31_Gzz(bdhm);
	}
	
	public void analyKzqqBw(String root, MC20_task_msg taskmag, MC21_task_fact mc21_task_fact) throws Exception {
		String bdhm = taskmag.getBdhm();
		String qq_path = root + taskmag.getMsgpath();
		
		//解析请求
		this.analyKzqq(qq_path, taskmag, mc21_task_fact);
		
		//文书
		mm31_kzqqMapper.insertBr31_ws(bdhm, TASK_TYPE_GF);
		//证件
		mm31_kzqqMapper.insertBr31_kzqq_gzz(bdhm, TASK_TYPE_GF);
	}
	
	/**
	 * 解析请求报文 task1
	 * 
	 * @param bdhm
	 * @param bwPath
	 * @throws Exception
	 */
	public void analyKzqq(String filename, MC20_task_msg taskmag, MC21_task_fact mc21_task_fact) throws Exception {
		ControlResponse_Kzqq xz_Kzqq = (ControlResponse_Kzqq) CommonUtils.unmarshallUTF8Document(ControlResponse_Kzqq.class, filename);
		
		//写控制请求表
		xz_Kzqq.setPacketkey(taskmag.getPacketkey() + "_NK");
		xz_Kzqq.setMsg_type_cd("NK");
		xz_Kzqq.setStatus("0");
		if (mc21_task_fact.getIsemployee().equals("0")) {
			xz_Kzqq.setStatus("1");
		}
		xz_Kzqq.setQrydt(DtUtils.getNowDate());
		xz_Kzqq.setRecipient_time(DtUtils.getNowTime());
		xz_Kzqq.setOrgkey(mc21_task_fact.getTaskobj());
		
		boolean noGzz = (xz_Kzqq.getGzzbh() == null || xz_Kzqq.getGzzbh().length() == 0);
		boolean noGwz = (xz_Kzqq.getGwzbh() == null || xz_Kzqq.getGwzbh().length() == 0);
		if (noGzz || noGwz) {
			List<MC20_GZZ> gzzList = mm31_kzqqMapper.queryMc20_gzzByBdhm(xz_Kzqq.getBdhm());
			if (gzzList != null && gzzList.size() > 0) {
				MC20_GZZ gzz = gzzList.get(0);
				if (noGzz) {
					xz_Kzqq.setGzzbh(gzz.getGzzbm());
				}
				if (noGwz) {
					xz_Kzqq.setGwzbh(gzz.getGwzbm());
				}
			}
		}
		
		mm31_kzqqMapper.insertBr31_kzqq(xz_Kzqq);
		String packetkey = xz_Kzqq.getPacketkey();
		mc21_task_fact.setFacttablename(xz_Kzqq.getXm() + "#" + packetkey.substring(4, 21));
		String freq = mc21_task_fact.getFreq();
		mc21_task_fact.setFreq("2");
		this.insertOrgMc21TaskFact1(mc21_task_fact, xz_Kzqq.getZjlx(), xz_Kzqq.getDsrzjhm());
		mc21_task_fact.setFreq(freq);
		List<ControlResponse_Kzzh> kzzhlist = xz_Kzqq.getKzzhlist();
		for (ControlResponse_Kzzh xz_Kzzh : kzzhlist) {
			xz_Kzzh.setBdhm(xz_Kzqq.getBdhm());
			xz_Kzzh.setQrydt(DtUtils.getNowDate());
			xz_Kzzh.setOrgkey(mc21_task_fact.getTaskobj());
			//插入控制请求账户表
			
			mm31_kzqqMapper.insertBr31_kzzh(xz_Kzzh);
			//控制处理信息表
			xz_Kzzh.setStatus_cd("0");
			xz_Kzzh.setMsgcheckresult("1");
			mm31_kzqqMapper.insertBr31_kzcl_info(xz_Kzzh);
			
			//插入task2
			String khzh = StrUtils.null2String(xz_Kzzh.getKhzh()); //开户账号
			String glzhhm = StrUtils.null2String(xz_Kzzh.getGlzhhm());//开户账号子账号
			if (!glzhhm.equals("")) {
				khzh = khzh + "&" + glzhhm;
			}
			mc21_task_fact.setTaskobj(xz_Kzzh.getCcxh());
			
			this.insertOrgMc21TaskFact2(mc21_task_fact, khzh);
			
			HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
			String khflag = StrUtils.null2String((String) sysParaMap.get("khflag")); //默认扣划不走核心	
			if (khflag != null && khflag.equals("1")) { //扣划走核心
				this.insertMc21TaskFact2(mc21_task_fact, "GF");
			} else {
				String kzcs = xz_Kzzh.getKzcs();
				if (kzcs != null && !kzcs.equals("06")) {
					this.insertMc21TaskFact2(mc21_task_fact, "GF");
				}
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
	public void insertOrgMc21TaskFact2(MC21_task_fact mc21_task_fact, String acctnum) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String kzzh = mc21_task_fact.getTaskobj();
		String returnStr = "update  br31_kzzh set orgkey=@A@  where  bdhm='" + mc21_task_fact.getBdhm() + "' and ccxh='" + mc21_task_fact.getTaskobj() + "';";
		returnStr = returnStr + "update  br31_kzcl_info set orgkey=@A@,msgcheckresult='1'   where  bdhm='" + mc21_task_fact.getBdhm() + "' and ccxh='"
					+ mc21_task_fact.getTaskobj() + "' &";
		returnStr = returnStr + "update  br31_kzcl_info set  status_cd='5',msgcheckresult=@D@  where  bdhm='" + mc21_task_fact.getBdhm() + "' and ccxh='"
					+ mc21_task_fact.getTaskobj() + "'";
		mc21_task_fact.setBdhm(bdhm + "$" + mc21_task_fact.getTaskobj());
		mc21_task_fact.setTaskobj(returnStr);
		
		this.insertMc21TaskFact2_Org(mc21_task_fact, "3", acctnum, "", "1");
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj(kzzh);
		
	}
	
	public void insertOrgMc21TaskFact1(MC21_task_fact mc21_task_fact, String zjlx, String dsrzjhm) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String kzzh = mc21_task_fact.getTaskobj();
		String returnStr = "update  br31_kzqq set orgkey=@A@  where  bdhm='" + mc21_task_fact.getBdhm() + "'&";
		returnStr = returnStr + "update  br31_kzqq set status='5'  where  bdhm='" + mc21_task_fact.getBdhm() + "'";
		mc21_task_fact.setTaskobj(returnStr);
		this.insertMc21TaskFact2_Org(mc21_task_fact, "2", dsrzjhm, zjlx, "1");
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj(kzzh);
		
	}
	
	/**
	 * 处理高法控制任务单 -- task2
	 */
	public void handleControlData(MC21_task_fact mc21_task_fact) throws Exception {
		// 根据请求信息及标志，判断查询数据接口
		if (StringUtils.equals(this.query_datasource, LOCAL_QUERY)) {
			dataOperate = (IDataOperate01) this.ac.getBean(LOCAL_DATA_OPERATE_NAME_GF);
		} else {
			dataOperate = (IDataOperate01) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GF);
		}
		
		ControlRequest_Kzxx kzxx = null; // 控制类处理结果
		
		String bdhm = mc21_task_fact.getBdhm();
		String ccxh = mc21_task_fact.getTaskobj();
		String isEmployee = mc21_task_fact.getIsemployee(); // 人工处理标识（0-全自动；1-人工；2-半自动）
		
		// 获取任务处理信息（是否本行数据）
		Br31_kzcl_info kzclInfo = mm31_kzqqMapper.getMM31_kzcl_info(bdhm, ccxh);
		boolean checkResult = "".equals(kzclInfo.getMsgcheckresult()) || "1".equals(kzclInfo.getMsgcheckresult());
		
		String organKey = null; // 
		String kzcs = null; // 控制措施
		
		// 判断是否是本行数据	（默认值为1）
		if (checkResult) {
			// 获取控制请求信息（联表查询）
			ControlResponse_Kzzh kzqq = mm31_kzqqMapper.getMM31_kzzh(bdhm, ccxh);
			organKey = kzqq.getOrgkey();
			kzcs = kzqq.getKzcs(); // 控制措施
			
			String khzh = kzqq.getKhzh(); // 开户账号
			String glzhhm = kzqq.getGlzhhm(); // 开户账号子账号
			
			// 数据字典转换
			dictCoder.transcode(kzqq, TASK_TYPE_GF);
			
			// 查询被执行人账户的在先冻结信息
			List<QueryRequest_Djxx> djxxList = dataOperate.getDjxxList(kzqq);
			if (djxxList != null && djxxList.size() > 0) {
				int lp = 0;
				for (QueryRequest_Djxx djxx : djxxList) {
					lp++;
					djxx.setBdhm(bdhm);
					djxx.setCcxh(ccxh);
					if (djxx.getCsxh() == null || djxx.getCsxh().equals("")) {
						djxx.setCsxh(String.valueOf(lp));
					}
					djxx.setKhzh(khzh);
					djxx.setGlzhhm(glzhhm);
					djxx.setQrydt(Utility.currDate8());
					
					mm31_kzqqMapper.insertBr31_zxdj_info(djxx);
				}
				
			}
			
			// 查询被执行人账户的共有权/优先权信息
			List<QueryRequest_Qlxx> qlxxList = dataOperate.getQlxxList(kzqq);
			if (qlxxList != null && qlxxList.size() > 0) {
				int lp = 0;
				for (QueryRequest_Qlxx qlxx : qlxxList) {
					lp++;
					
					qlxx.setBdhm(bdhm);
					qlxx.setCcxh(ccxh);
					if (qlxx.getXh() == null || qlxx.getXh().length() == 0) {
						qlxx.setXh(String.valueOf(lp));
					}
					qlxx.setKhzh(khzh);
					qlxx.setGlzhhm(glzhhm);
					qlxx.setQrydt(Utility.currDate8());
					
					mm31_kzqqMapper.insertBr31_yxq_info(qlxx);
				}
			}
			
			if ("06".equals(kzcs)) { // 扣划请求
				String khflag = StrUtils.null2String((String) sysParaMap.get("khflag")); // 默认扣划不走核心	
				if (khflag != null && khflag.equals("1")) { // 扣划走核心
					// 取出机构对应的内部账号
					ControlResponse_Kzzh organacct = mm31_kzqqMapper.getBr31_organ_inner_acct(organKey);
					if (organacct != null) {
						kzqq.setInacct(organacct.getInacct());
						kzqq.setInacctname(organacct.getInacctname());
					}
				}
				
				kzxx = dataOperate.invokeFreezeAccount(kzqq);
				kzxx.setKzzt("");
				kzxx.setWnkzyy("");
				
				String manualHandlingDeduction = ServerEnvironment.getStringValue(Keys.GF_MANUAL_HANDLING_OF_DEDUCTION); // Manual handling of the deduction
				if (StringUtils.isNotBlank(manualHandlingDeduction)) {
					isEmployee = manualHandlingDeduction;
				}
			} else {
				try {
					ControlRequest_Kzxx lastKzxx = null;
					if ("02".equals(kzcs) || "04".equals(kzcs)) {
						// 控制措施为：02-继续冻结、04-解除冻结，需根据原冻结请求单号获取冻结时的核心冻结编号
						lastKzxx = mm31_kzqqMapper.getHxAppid(kzqq);
						kzqq.setSkje(lastKzxx.getSkje());
						kzqq.setSkse(lastKzxx.getSkse()); // 实际控制份额（理财产品）
						kzqq.setHxappid(lastKzxx.getHxappid()); // 原冻结编号
					}
					
					// 调用控制类接口
					kzxx = dataOperate.invokeFreezeAccount(kzqq);
					
					if (lastKzxx != null) {
						if (kzxx.getHxappid() == null || kzxx.getHxappid().equals("")) {
							kzxx.setHxappid(lastKzxx.getHxappid());
						}
						
						if (kzxx.getSkje() == null || kzxx.getSkje().equals("")) {
							kzxx.setSkje(lastKzxx.getSkje()); //实际控制金额
							kzxx.setSkse(lastKzxx.getSkse()); //实际控制份额（理财产品）
						}
						
						if ("02".equals(kzcs)) {
							if (kzxx.getCeskje() == null || kzxx.getCeskje().equals("")) {
								kzxx.setCeskje(lastKzxx.getCeskje()); // 超额控制金额
							}
						}
					}
					
					kzxx.setKzzt("1"); // 控制结果（1-已控；2-未控）
					kzxx.setWnkzyy("");
				} catch (DataOperateException e) {
					logger.error("处理控制请求数据异常：{}", e.getDescr(), e);
					kzxx = new ControlRequest_Kzxx("2", e.getDescr());
				} catch (RemoteAccessException e) {
					logger.error("处理控制请求系统异常：{}", e.getMessage(), e);
					kzxx = new ControlRequest_Kzxx("2", "控制类请求处理失败");
				}
			}
			
			// 控制相关信息落地，写控制处理信息表
			if ("1".equals(isEmployee)) {
				kzxx.setStatus_cd("2"); // 2-回执文书待处理
			} else {
				kzxx.setStatus_cd("5"); // 5-待生成报文
			}
		} else {
			kzxx = new ControlRequest_Kzxx("2", "查无该客户信息");
		}
		
		kzxx.setBdhm(bdhm);
		kzxx.setCcxh(ccxh);
		mm31_kzqqMapper.updateBr31_kzcl_info(kzxx);
		
		// 判断是否待人工处理
		if (!checkResult || "0".equals(isEmployee) || ("2".equals(isEmployee) && "1".equals(kzxx.getKzzt()))) { // [0-全自动]或[2-半自动，且处理成功]
			int isok = mm31_kzqqMapper.getBr31_kzcl_infonoCount(bdhm);
			if (isok == 0) {
				ControlResponse_Kzqq kzqq = new ControlResponse_Kzqq();
				kzqq.setBdhm(bdhm);
				kzqq.setStatus("2");
				String ishz = StrUtils.null2String((String) sysParaMap.get("GFHZFLAG")); //回执是否走人工 0否，1是
				if (ishz.equals("1")) {
				} else {
					kzqq.setStatus("5");
					insertMc21TaskFact3(mc21_task_fact, "GF");
				}
				mm31_kzqqMapper.updateBr31_kzqq(kzqq);
			}
		} else {
			// 发送短信通知人工处理
			List<BB13_organ_telno> bb13_organ_telnoList = this.common_Mapper.getBb13_organ_tel(organKey);
			dataOperate.SendMsg(bb13_organ_telnoList, organKey, kzcs);
		}
		
		// 0-待认定；1-待执行；2-回执文书待处理；5-待生成报文；6-已生成报文；7-回退监管；8-成功；9-失败回执
	}
	
	/**
	 * 反馈控制任务单 -- task3
	 * 
	 * @param mc21_task_fact
	 * @param taskmag
	 * @throws Exception
	 */
	public void FeedBackControl(MC21_task_fact mc21_task_fact) throws Exception {
		
		String bdhm = mc21_task_fact.getBdhm();
		ControlResponse_Kzqq kzqqinfo = mm31_kzqqMapper.getBr31_kzqqVo(bdhm);
		//1.获取查询任务数据,获取回执报文数据
		ControlRequest cs_kzxxList = new ControlRequest();
		cs_kzxxList = ControlData(mc21_task_fact);
		String packetkey = kzqqinfo.getPacketkey();
		//2.生成查询反馈报文
		String path = makeKzqqXML(cs_kzxxList, kzqqinfo.getOrgkey(), packetkey, kzqqinfo.getBdhm());
		//3.删除msg
		mm31_kzqqMapper.delBr32_msg(bdhm);
		//3.写表BR32_MSG
		String newpacketkey = packetkey + "_" + mc21_task_fact.getBdhm();
		kzqqinfo.setPacketkey(newpacketkey);
		insertBr32_msg(kzqqinfo, path);
		//回执文书是否后台自动生成
		this.makeHzws(bdhm, packetkey);
		//写回执信息
		List<ControlRequest_Hzxx> hzxxList = mm31_kzqqMapper.getBr31_kzqq_hzxxVo(mc21_task_fact.getBdhm());
		path = makeKzqq_hzxxXML(hzxxList, kzqqinfo.getOrgkey(), packetkey, kzqqinfo.getBdhm());
		insertBr32_msg(kzqqinfo, path);
		
		//4.置控制主表(Br31_kzqq)状态 为6.已生成XML
		ControlResponse_Kzqq kzqq = new ControlResponse_Kzqq();
		kzqq.setBdhm(mc21_task_fact.getBdhm());
		kzqq.setStatus("6");
		mm31_kzqqMapper.updateBr31_kzqq(kzqq);
		
		mc21_task_fact.setTaskkey("'T3_" + "GF" + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
		mc21_task_fact.setDatatime(DtUtils.getNowTime());
		mc21_task_fact.setTaskid("TK_ESGF_FK04");
		
		mc21_task_fact.setBdhm(newpacketkey);
		//mc21_task_fact.setTaskobj(packetkey);
		common_Mapper.insertMc21_task_fact3_packet(mc21_task_fact);
	}
	
	/**
	 * 获取控制任务数据
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public ControlRequest ControlData(MC21_task_fact mc21_task_fact) throws Exception {
		
		ControlRequest cs_kzxxList = new ControlRequest();
		
		List<ControlRequest_Kzxx> kzxxliist = new ArrayList<ControlRequest_Kzxx>();
		List<ControlRequest_Kzxx> returnKzxxList = new ArrayList<ControlRequest_Kzxx>();
		//控制处理信息
		kzxxliist = mm31_kzqqMapper.getBr31_kzcl_infoList(mc21_task_fact.getBdhm());
		if (kzxxliist != null && kzxxliist.size() > 0) {
			for (int i = 0; i < kzxxliist.size(); i++) {
				ControlRequest_Kzxx cs_kzxx = (ControlRequest_Kzxx) kzxxliist.get(i);
				//在先冻结信息（判断无后则不反馈）
				ArrayList<ControlRequest_Djxx> djxxList = mm31_kzqqMapper.getBr31_zxdj_infoList(cs_kzxx);
				//优先权信息（判断无后则不反馈）
				ArrayList<ControlRequest_Qlxx> qlxxList = mm31_kzqqMapper.getBr31_yxq_infoList(cs_kzxx);
				
				cs_kzxx.setDjxxList(djxxList);
				cs_kzxx.setQlxxList(qlxxList);
				returnKzxxList.add(cs_kzxx);
			}
		}
		cs_kzxxList.setKzxxList(returnKzxxList);
		return cs_kzxxList;
	}
	
	/**
	 * 生成控制反馈报文
	 * 
	 * @param mc21_task_fact
	 * @param qs_zhxxlist
	 * @throws Exception
	 */
	public String makeKzqqXML(ControlRequest cs_kzxxList, String orgkey, String packetKey, String bdhm) throws Exception {
		//获取系统参数
		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		
		String root = StrUtils.null2String((String) sysParaMap.get("2"));
		String path = "/attach/" + DtUtils.getNowDate() + "/gf";
		String filename = this.getKzqqXmlFileName(TASK_TYPE_GF, "QR", packetKey, bdhm) + ".xml";
		
		CommonUtils.marshallUTF8Document(cs_kzxxList, "binding_control_req", root + path, filename);
		
		return path + "/" + filename;
	}
	
	public String makeKzqq_hzxxXML(List<ControlRequest_Hzxx> hzxxList, String orgkey, String packetKey, String bdhm) throws Exception {
		//获取系统参数
		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		String root = StrUtils.null2String((String) sysParaMap.get("2"));
		List<ControlRequest_Hzxx> list = new ArrayList<ControlRequest_Hzxx>();
		for (ControlRequest_Hzxx hzxx : hzxxList) {
			String wjpash = hzxx.getWjpath();
			byte[] wsnr = CommonUtils.readBinaryFile(root + wjpash);
			hzxx.setWsnr(wsnr);
			String md5 = MD5Coder.encodeHex(wsnr, true); // 
			hzxx.setMd5(md5);
			hzxx.setWslb("回执");
			list.add(hzxx);
		}
		ControlRequest_HzxxList controlrequest = new ControlRequest_HzxxList();
		controlrequest.setHzxxList(list);
		
		String path = "/attach/" + DtUtils.getNowDate() + "/gf";
		String filename = this.getKzqqXmlFileName(TASK_TYPE_GF, "QRI", packetKey, bdhm) + ".xml";
		
		CommonUtils.marshallUTF8Document(controlrequest, root + path, filename);
		
		return path + "/" + filename;
	}
	
	public void insertBr32_msg(ControlResponse_Kzqq kzqqinfo, String path) throws Exception {
		Br32_msg msg = new Br32_msg();
		String orgkey = kzqqinfo.getOrgkey();
		MP02_rep_org_map rep_org = this.getMp02_organPerson("1", orgkey);
		String organkey = rep_org.getReport_organkey();
		String filename = FileUtils.getFilenameFromPath(path);
		msg.setMsgkey(this.geSequenceNumber("SEQ_BR32_MSG"));
		msg.setBdhm(kzqqinfo.getBdhm());
		msg.setMsg_type_cd(kzqqinfo.getMsg_type_cd());
		msg.setPacketkey(kzqqinfo.getPacketkey());
		msg.setOrgankey_r(organkey);
		msg.setSenddate(DtUtils.getNowDate());
		msg.setMsg_filename(filename);
		msg.setMsg_filepath(path);
		msg.setStatus_cd("0"); //报文状态 0:待打包 1:已打包
		msg.setCreate_dt(DtUtils.getNowTime());
		msg.setQrydt(DtUtils.getNowDate());
		//mm31_kzqqMapper.delBr32_msg(kzqqinfo.getPacketkey());
		mm31_kzqqMapper.insertBr32_msg(msg);
	}
	
	/**
	 * 删除控制信息
	 * 
	 * @param bdhm
	 * @throws Exception
	 */
	public void delKzqqInfo(String bdhm, String ccxh) throws Exception {
		Br31_kzcl_info kzzh = new Br31_kzcl_info();
		kzzh.setBdhm(bdhm);
		kzzh.setCcxh(ccxh);
		mm31_kzqqMapper.delBr31_yxq_info(kzzh);
		mm31_kzqqMapper.delBr31_zxdj_info(kzzh);
	}
	
	public void makeHzws(String bdhm, String packetkey) throws Exception {
		//获取系统参数
		HashMap sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		String gfhzflag = StrUtils.null2String((String) sysParaMap.get("GFHZFLAG"));
		if (gfhzflag != null && gfhzflag.equals("0")) { //是否需要人工处理回执
			HzwsBo hzwsBo = new HzwsBo(mm31_kzqqMapper);
			//删除文书表
			mm31_kzqqMapper.delBr31_Hzws(bdhm);
			hzwsBo.writeBr31_kzqq_hzws(bdhm, packetkey);
		}
	}
	
}
