package com.citic.server.service.task.taskBo;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.dict.DictCoder;
import com.citic.server.dx.domain.Response;
import com.citic.server.dx.service.DataOperate2;
import com.citic.server.dx.service.RequestMessageService;
import com.citic.server.net.mapper.MC00_common_Mapper;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.BB13_organ_telno;
import com.citic.server.service.domain.BR42_sequences;
import com.citic.server.service.domain.BaseDictBean;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.domain.MP02_rep_org_map;
import com.citic.server.service.time.SequenceService;
import com.citic.server.utils.DbFuncUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.StrUtils;

public class BaseBo {
	// private static final Logger logger = LoggerFactory.getLogger(BaseBo.class);
	
	/** 本地（批后）查询 -- 除快速查询 */
	public static final String LOCAL_QUERY = "2";
	/** 远程（核心）查询 */
	public static final String REMOTE_QUERY = "1";
	/** 远程（核心）查询 -- 除历史流水 */
	public static final String REMOTE_QUERY_T = "3";
	
	/** 最高人民法院 */
	public static final String LOCAL_DATA_OPERATE_NAME_GF = "localDataOperate1";
	public static final String REMOTE_DATA_OPERATE_NAME_GF = "remoteDataOperate1";
	
	/** 电信诈骗 */
	public static final String LOCAL_DATA_OPERATE_NAME_DX = "localDataOperate2";
	public static final String REMOTE_DATA_OPERATE_NAME_DX = "remoteDataOperate2";
	
	/** 四川省公安 */
	public static final String LOCAL_DATA_OPERATE_NAME_GA = "localDataOperate3";
	public static final String REMOTE_DATA_OPERATE_NAME_GA = "remoteDataOperate3";
	
	/** CBRC（国安、高检、公安、江西公安、深圳公安） */
	public static final String LOCAL_DATA_OPERATE_NAME_CBRC = "localDataOperateCBRC";
	public static final String REMOTE_DATA_OPERATE_NAME_CBRC = "remoteDataOperateCBRC";
	
	/** 广东纪检委 */
	public static final String REMOTE_DATA_OPERATE_NAME_GDJC = "remoteDataOperateGdjc";
	
	/** 武汉公安局 */
	public static final String REMOTE_DATA_OPERATE_NAME_WHPSB = "remoteDataOperateWhpsb";
	
	/** 上海公安局 */
	public static final String REMOTE_DATA_OPERATE_NAME_SHPSB = "remoteDataOperateShpsb";
	
	/** 成都民政局 */
	public static final String REMOTE_DATA_OPERATE_NAME_MZJ = "remoteDataOperateMzj";
	
	public MC00_common_Mapper common_Mapper;
	
	protected DbFuncUtils dbfunc = new DbFuncUtils();
	protected ApplicationContext ac;
	protected CacheService cacheService;
	
	/** 查询数据源,其实定义为是否有本地数据更合适 */
	protected String query_datasource;
	
	protected HashMap sysParaMap = new HashMap();
	
	// liuxuanfei 2016-05-24 先解决上线吧，本来应该在RequestMessageService统一设置序列，但又因为动态查询需要在插入数据时作为主键
	// 太多特殊情况需要考虑，一时间先放一放吧。
	private SequenceService applicationIdService;
	private SequenceService transSerialNumberService;
	
	protected RequestMessageService requestMessageService;
	
	// liuxuanfei 2016-10-21
	protected DictCoder dictCoder; // 码表转换工具
	
	// liuxuanfei 2016-04-27 14:04
	public BaseBo() {
		this(SpringContextHolder.getApplicationContext());
	}
	
	public BaseBo(ApplicationContext ac) {
		this.ac = ac;
		common_Mapper = (MC00_common_Mapper) ac.getBean("MC00_common_Mapper");
		cacheService = (CacheService) ac.getBean("cacheService");
		
		//查询数据来源：1.银行业务系统 2.批后数据
		sysParaMap = (HashMap) cacheService.getCache("sysParaDetail", HashMap.class);
		this.query_datasource = StringUtils.isEmpty((String) sysParaMap.get("query_datasource")) ? REMOTE_QUERY : (String) sysParaMap.get("query_datasource");
		
		applicationIdService = (SequenceService) ac.getBean("outerApplicationID");
		transSerialNumberService = (SequenceService) ac.getBean("outerTransSerialNumber");
		requestMessageService = (RequestMessageService) ac.getBean("requestMessageService");
		
		dictCoder = (DictCoder) ac.getBean(DictCoder.class); // 
	}
	
	/**
	 * @param reptype 2:电信诈骗 1高发
	 * @return
	 * @throws Exception
	 */
	public MP02_rep_org_map getMp02_organPerson(String reptype, String organkey) throws Exception {
		HashMap reportOrganMap = (HashMap) cacheService.getCache("RepOrgMapDetail", HashMap.class);
		MP02_rep_org_map mp02_rep_org_map = (MP02_rep_org_map) reportOrganMap.get(reptype + organkey);
		return mp02_rep_org_map;
	}
	
	/**
	 * 插入电信诈骗task2
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public void insertMc21TaskFact2(MC21_task_fact mc21_task_fact, String prefix) throws Exception {
		if (mc21_task_fact.getIsemployee() != null && mc21_task_fact.getIsemployee().equals("0")) { //判断是否人工处理(0：否  1:是)，否需要插入task3，是则不需要
			String organUpdateFlag = (String) sysParaMap.get("organUpdateFlag");
			if (organUpdateFlag != null && organUpdateFlag.equals("2")) {
				mc21_task_fact.setTaskkey("'T2_" + prefix + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT2"));
				mc21_task_fact.setDatatime(DtUtils.getNowTime());
				common_Mapper.insertMc21_task_fact2(mc21_task_fact);
			}
		}
	}
	
	public void insertMc21TaskFact2Fo(MC21_task_fact mc21_task_fact, String prefix) throws Exception {
		mc21_task_fact.setTaskkey("'T2_" + prefix + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT2"));
		mc21_task_fact.setDatatime(DtUtils.getNowTime());
		common_Mapper.insertMc21_task_fact2(mc21_task_fact);
	}
	
	/**
	 * 插入电信诈骗task3
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public void insertMc21TaskFact3(MC21_task_fact mc21_task_fact, String prefix) throws Exception {
		// 判断是否人工处理(0：否  1:是)，否需要插入task3，是则不需要
		if (mc21_task_fact.getIsemployee() != null && mc21_task_fact.getIsemployee().equals("0")) {
			mc21_task_fact.setTaskkey("'T3_" + prefix + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
			mc21_task_fact.setDatatime(DtUtils.getNowTime());
			common_Mapper.insertMc21_task_fact3(mc21_task_fact);
		}
	}
	
	/**
	 * 业务申请编号
	 * 
	 * @param txCode
	 * @throws Exception
	 */
	public String getApplicationID(String txCode) throws Exception {
		return txCode.substring(2) + applicationIdService.next();
	}
	
	/**
	 * 流水编号
	 * 
	 * @param 2:电信诈骗 1高发
	 * @throws Exception
	 */
	public String getTransSerialNumber(String reptype, String orgkey) throws Exception {
		HashMap reportOrganMap = (HashMap) cacheService.getCache("RepOrgMapDetail", HashMap.class);
		String organCode = StrUtils.null2String(((MP02_rep_org_map) reportOrganMap.get(reptype + orgkey)).getReport_organkey());
		String fromTgOrganCode = "000000000000";
		HashMap reportMap = (HashMap) cacheService.getCache("Mp02_repOrgDetail", HashMap.class);
		MP02_rep_org_map orgMap = (MP02_rep_org_map) reportMap.get(reptype + organCode);
		if (orgMap != null) {
			organCode = orgMap.getZhorgankey();
			fromTgOrganCode = orgMap.getHxcode();
		}
		return organCode + "_" + fromTgOrganCode + transSerialNumberService.next();
	}
	
	/**
	 * 插入响应表
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public void insertBr24_q_k_back_m_f(Response response) throws Exception {
		if (response != null) {
			response.setFeedback_dt(DtUtils.getNowTime());
			common_Mapper.delBr24_q_k_back_m_f(response.getTransSerialNumber());
			common_Mapper.insertBr24_q_k_back_m_f(response);
		}
	}
	
	public synchronized String getCxqqXMLFileName(String taskType, String packetKey, String bdhm) throws Exception {
		String organcode = packetKey.substring(4, 21); // 17位银行代码
		String packetSeq = packetKey.substring(21, 51); // 30位请求批次号
		
		String suffix = null;
		BR42_sequences br42_sequences = new BR42_sequences();
		br42_sequences.setOrgankey_r(organcode);
		br42_sequences.setPacket_seq(packetSeq);
		BR42_sequences br42_sequences1 = common_Mapper.getBr42_sequences(br42_sequences);
		if (br42_sequences1 == null || br42_sequences1.getOrgankey_r().length() == 0) {
			common_Mapper.insertBr42_sequences(br42_sequences);
			suffix = StrUtils.fillStr(12, "1");
		} else {
			Integer msgseq = br42_sequences1.getMsg_num();
			br42_sequences1.setMsg_num(new Integer(msgseq.intValue() + 1));
			common_Mapper.updateBr42_sequences(br42_sequences1);
			suffix = StrUtils.fillStr(12, String.valueOf(msgseq.intValue() + 1));
		}
		
		return "QR" + organcode + packetSeq + suffix;
	}
	
	/**
	 * @param taskType
	 * @param prefix QR、QRI、QNA
	 * @param packetKey
	 * @param bdhm
	 * @return
	 * @throws Exception
	 */
	public synchronized String getKzqqXmlFileName(String taskType, String prefix, String packetKey, String bdhm) throws Exception {
		String organcode = packetKey.substring(4, 21); // 17位银行代码
		String packetSeq = packetKey.substring(21, 51); // 30位请求批次号
		String suffix = StrUtils.fillStr(30, bdhm); // 后缀
		
		return prefix + organcode + packetSeq + suffix;
	}
	
	/**
	 * @param tasktype 3-公安、4-国安、 5-高检、6-四川省公安厅、7-江西公安、8-深圳公安
	 * @param type 分类代码
	 * @param packetSeq 包批次号
	 * @param bdhm 请求单号
	 * @return
	 * @throws Exception
	 */
	public synchronized String getMsgNameCBRC(String tasktype, String type, String qqdbs, String organkey) throws Exception {
		String msgname = "";
		// String organcode = organkey.substring(0, 8);
		BR42_sequences br42_sequences = new BR42_sequences();
		br42_sequences.setOrgankey_r(organkey);
		br42_sequences.setPacket_seq(qqdbs);
		BR42_sequences br42_sequences1 = common_Mapper.getBr42_sequences(br42_sequences);
		if (br42_sequences1 != null && !br42_sequences1.getOrgankey_r().equals("")) {
			Integer msgseq = br42_sequences1.getMsg_num();
			br42_sequences1.setMsg_num(new Integer(msgseq.intValue() + 1));
			common_Mapper.updateBr42_sequences(br42_sequences1);
			msgname = StrUtils.fillStr(12, msgseq.intValue() + 1 + "");
		} else {
			common_Mapper.insertBr42_sequences(br42_sequences);
			msgname = StrUtils.fillStr(12, "1");
		}
		msgname = type + organkey + qqdbs + msgname;
		return msgname;
	}
	
	public String geSequenceNumber(String seqename) throws Exception {
		String sql = dbfunc.getSeqStr(seqename);
		String id = common_Mapper.geSequenceNumber(sql);
		
		return id;
	}
	
	/**
	 * 插入电信诈骗task2
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public void insertMc21TaskFact2_Org(MC21_task_fact mc21_task_fact, String type, String cardnumber, String credentialtype, String taskType) throws Exception {
		
		String organUpdateFlag = (String) sysParaMap.get("organUpdateFlag");
		if (organUpdateFlag != null && (organUpdateFlag.equals("1") || organUpdateFlag.equals("0"))) {
			
			if (organUpdateFlag.equals("0")) { //若是本地从本地查询		
				if (mc21_task_fact.getFreq().equals("2")) {
					mc21_task_fact.setFreq("3");
				} else {
					mc21_task_fact.setFreq("0");
				}
			} else {
				if (type.equals("2")) { //证件类型号码查询
					//转成核心证件号码
					if (ServerEnvironment.TASK_TYPE_GF.equals(taskType) || ServerEnvironment.TASK_TYPE_DX.equals(taskType) || ServerEnvironment.TASK_TYPE_YJ.equals(taskType)) {
						HashMap etlcodeMap = (HashMap) cacheService.getCache("BB13_etl_code_mapDetail", HashMap.class);
						String code;
						if (ServerEnvironment.TASK_TYPE_GF.equals(taskType)) {
							code = "GFZJ";
						} else if (ServerEnvironment.TASK_TYPE_YJ.equals(taskType)) {
							code = "GAZJ";
						} else {
							code = "DXZJ";
						}
						HashMap zjlxMap = (HashMap) etlcodeMap.get(code);
						if (zjlxMap != null && zjlxMap.get(credentialtype) != null) { //证件类型转成核心需要的
							credentialtype = (String) zjlxMap.get(credentialtype);
						}
					} else {
						// 执行转码
						BaseDictBean bean = new BaseDictBean();
						bean.setTaskType(taskType); // 监管代号
						bean.setCertificateType(credentialtype); // 证件类型
						dictCoder.transcode(bean, taskType);
						// 转码后的值 
						credentialtype = bean.getCertificateType(); // 转码后的证件类型
					}
				}
			}
			
			String xm = mc21_task_fact.getFacttablename();
			//	if (credentialtype != null && !credentialtype.equals("")) {
			cardnumber = cardnumber + "#" + credentialtype + "#" + xm + "#" + mc21_task_fact.getBdhm();
			//	}
			String taskkey = mc21_task_fact.getTaskkey();
			mc21_task_fact.setBdhm(cardnumber);
			mc21_task_fact.setSubtaskid(type);
			mc21_task_fact.setTasktype(taskType);
			mc21_task_fact.setTgroupid(taskkey);
			mc21_task_fact.setTaskkey("'T2_" + taskType + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT2"));
			mc21_task_fact.setDatatime(DtUtils.getNowTime());
			String oldtaskid = mc21_task_fact.getTaskid();
			String taskid = "TK_ESGF_ORG";
			if (taskType.equals("2")) {
				taskid = "TK_ESDX_ORG";
			}
			mc21_task_fact.setTaskid(taskid);
			//查询是否需要人工处理
			HashMap map = (HashMap) cacheService.getCache("MC21TaskDetail", HashMap.class);
			if (map != null && map.get(oldtaskid) != null) {
				MC21_task task = (MC21_task) map.get(oldtaskid);
				mc21_task_fact.setIsdyna(task.getIsEmployee());
			}
			common_Mapper.insertMc21_task_fact2_org(mc21_task_fact);
			mc21_task_fact.setTaskkey(taskkey);
			mc21_task_fact.setTaskid(oldtaskid);
			
		} else {
			if (organUpdateFlag.equals("2")) {
				//				String orgkey = (String) sysParaMap.get("innerOrgKey");
				//				if (orgkey != null) {
				//					String taskObjs = mc21_task_fact.getTaskobj();
				//					this.updateOrgkeyStr(taskObjs, orgkey, reptype);
				//				}
			}
		}
	}
	
	/*
	 * arg0:机构 arg1：任务名称 arg2：txcode arg3:type(1卡号 2证件号码 3账号） arg4: 发文类型 arg5: （1认定 2执行）
	 */
	//发送短信
	public void sendMsg(String orgkey, String... arg) {
		if (orgkey != null && !orgkey.equals("")) {
			//机构查询手机号
			List<BB13_organ_telno> bb13_organ_telnoList = common_Mapper.getBb13_organ_tel(orgkey);
			DataOperate2 dataOperate2 = (DataOperate2) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_DX);
			//发送短信
			try {
				String[] strs = new String[arg.length + 1];
				for (int j = 0; j < arg.length; j++) {
					strs[j] = arg[j];
				}
				strs[arg.length] = orgkey;
				/*
				 * arg5:机构 arg0：任务名称 arg1：txcode arg2:type(1卡号 2证件号码 3账号） arg3: 发文类型 arg4: （1认定 2执行）
				 */
				dataOperate2.SendMsg(bb13_organ_telnoList, strs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
