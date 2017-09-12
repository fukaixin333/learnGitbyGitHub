package com.citic.server.gf.task.taskBo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.dict.DictCoder;
import com.citic.server.gf.BatchDBTools;
import com.citic.server.gf.domain.Br32_msg;
import com.citic.server.gf.domain.QueryRequestObj;
import com.citic.server.gf.domain.Result;
import com.citic.server.gf.domain.Result_Jg;
import com.citic.server.gf.domain.request.QueryRequest;
import com.citic.server.gf.domain.request.QueryRequest_Djxx;
import com.citic.server.gf.domain.request.QueryRequest_Glxx;
import com.citic.server.gf.domain.request.QueryRequest_Jrxx;
import com.citic.server.gf.domain.request.QueryRequest_Qlxx;
import com.citic.server.gf.domain.request.QueryRequest_Wlxx;
import com.citic.server.gf.domain.request.QueryRequest_Zhxx;
import com.citic.server.gf.domain.response.QueryResponse_Cxqq;
import com.citic.server.gf.service.IDataOperate01;
import com.citic.server.gf.service.RequestMessageService01;
import com.citic.server.net.mapper.MM30_xzcsMapper;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.DataOperateException;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.BB13_sys_para;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.StrUtils;

public class CxqqBo extends BaseBo implements Constants {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CxqqBo.class);
	
	private IDataOperate01 dataOperate; // 数据获取接口
	
	private CacheService cacheService;
	private MM30_xzcsMapper mm30_xzcsMapper;
	
	private DictCoder dictCoder; // 数据字典转换工具
	
	public CxqqBo(ApplicationContext ac) {
		super(ac);
		mm30_xzcsMapper = (MM30_xzcsMapper) ac.getBean("MM30_xzcsMapper");
		cacheService = (CacheService) ac.getBean("cacheService");
		dictCoder = SpringContextHolder.getBean(DictCoder.class);
	}
	
	/**
	 * 删除请求单号下的信息
	 * 
	 * @param bdhm
	 * @throws Exception
	 */
	public void delCxqqBw(String bdhm) throws Exception {
		// 删除文书表
		//	mm30_xzcsMapper.delBr30_Ws(bdhm);
		// 删除工作证表
		
		// 删除请求表
		mm30_xzcsMapper.delBr30_xzcs(bdhm);
	}
	
	/**
	 * 解析请求报文
	 * 
	 * @param bdhm
	 * @param bwPath
	 * @throws Exception
	 */
	public void analyCxqqBw(MC20_task_msg taskMsg, MC21_task_fact mc21_task_fact) throws Exception {
		// 查询请求文件路径
		String filename = ServerEnvironment.getFileRootPath() + taskMsg.getMsgpath();
		
		// 解析请求
		QueryResponse_Cxqq cxqq = (QueryResponse_Cxqq) CommonUtils.unmarshallUTF8Document(QueryResponse_Cxqq.class, filename);
		// 插入查询请求表
		cxqq.setMsg_type_cd("NC");
		cxqq.setPacketkey(taskMsg.getPacketkey() + "_NC");
		cxqq.setStatus("0");
		cxqq.setQrydt(DtUtils.getNowDate());
		cxqq.setOrgkey(mc21_task_fact.getTaskobj());
		cxqq.setMsgcheckresult("1");
		cxqq.setRecipient_time(DtUtils.getNowTime());
		mm30_xzcsMapper.insertBr30_xzcs(cxqq);
		//删除文书
		mm30_xzcsMapper.delBr30_Ws(cxqq.getWsbh());
		// 插入文书表
		mm30_xzcsMapper.insertBr30_ws(cxqq.getWsbh());
		// 插入证件表
		mm30_xzcsMapper.delBr30_Gzz(cxqq.getBdhm());
		mm30_xzcsMapper.insertBr30_gzz(cxqq);
		
		String packetKey = taskMsg.getPacketkey();
		if (packetKey.startsWith("#")) {
			mc21_task_fact.setFacttablename(cxqq.getXm() + "#" + packetKey.substring(5, packetKey.length()));
		} else {
			mc21_task_fact.setFacttablename(cxqq.getXm() + "#" + packetKey.substring(4, 21));
		}
		
		this.insertOrgMc21TaskFact2(mc21_task_fact, cxqq.getZjlx(), cxqq.getDsrzjhm());
		
		// 插入Task2
		this.insertMc21TaskFact2(mc21_task_fact, "GF");
	}
	
	/**
	 * 处理查询任务单数据
	 * 
	 * @param mc21_task_fact
	 * @param dataSource
	 * @throws Exception
	 */
	public void handleQueryData(MC21_task_fact mc21_task_fact) throws Exception {
		String bdhm = mc21_task_fact.getBdhm();
		
		// 1、根据任务信息，获取查询请求信息
		QueryResponse_Cxqq cxqq = mm30_xzcsMapper.getBr30_xzcsVo(bdhm);
		String msgCheckResult = cxqq.getMsgcheckresult();
		if (msgCheckResult != null && (msgCheckResult.equals("") || msgCheckResult.equals("1"))) { //判断是否是本行数据	
			try {
				// 2、根据请求信息及标志，判断查询数据接口
				if (StringUtils.equals(this.query_datasource, LOCAL_QUERY)) {
					dataOperate = (IDataOperate01) this.ac.getBean(LOCAL_DATA_OPERATE_NAME_GF);
					try {
						dataOperate.getBr30_xzcs_infoList(cxqq);
					} catch (RemoteAccessException e) {
						logger.error("查询异常：{}", e.getMessage(), e);
						this.insertNullKzzh(bdhm, "查无开户信息"); // 插入空账户信息
						insertMc21TaskFact3(mc21_task_fact, "GF"); // 4、插入任务task3
						return;
					}
				} else {
					dataOperate = (IDataOperate01) this.ac.getBean(REMOTE_DATA_OPERATE_NAME_GF);
					dictCoder.transcode(cxqq, TASK_TYPE_GF); // 码值转换
					
					QueryRequestObj queryRequst = dataOperate.getBr30_xzcs_infoList(cxqq);
					List<QueryRequest_Zhxx> zhxxList = queryRequst.getZhxxList();
					// 人行开户网点显示现代化止付系统行号
					HashMap transtypeHash = (HashMap<String, Object>) cacheService.getCache("ReHangOrganMap", HashMap.class);
					
					for (QueryRequest_Zhxx zhxx : zhxxList) {
						zhxx.setBdhm(bdhm);
						zhxx.setFksj(DtUtils.getNowTime());
						zhxx.setQrydt(DtUtils.getNowDate());
						dictCoder.reverse(zhxx, TASK_TYPE_GF); // 码表转换（即使是批后提取业务数据，也需要进行码表转换）
						// 转换开户网点和名称
						String depositBankBranchCode = StrUtils.null2String(zhxx.getKhwddm());
						String depositBankBranch = StrUtils.null2String(zhxx.getKhwd());
						zhxx.setOrgkey(depositBankBranchCode);
						if (transtypeHash.get(depositBankBranchCode) != null) {
							BB13_sys_para syspara = (BB13_sys_para) transtypeHash.get(depositBankBranchCode);
							depositBankBranchCode = syspara.getVals();
							depositBankBranch = syspara.getCodename();
						}
						zhxx.setKhwd(depositBankBranch);// 开户网点
						zhxx.setKhwddm(depositBankBranchCode);// 开户网点代码
						// mm30_xzcsMapper.insertBr30_xzcs_info(zhxx);
					}
					
					// 插入账户基本信息
					BatchDBTools.batchInsertQueryRequest_Zhxx(zhxxList, 1000);
					
					this.insertWlxxList(queryRequst.getWlxxList(), bdhm); // 资金往来（交易）
					this.insertJrxxList(queryRequst.getJrxxList(), bdhm); // 插入金融资产信息
					this.insertDjxxList(queryRequst.getDjxxList(), bdhm); // 司法强制措施信息
					this.insertQlxxList(queryRequst.getQlxxList(), bdhm); // 共有权/优先权信息
					this.insertGlxxList(queryRequst.getGlxxList(), bdhm); // 关联子类账户信息
				}
			} catch (DataOperateException e) {
				logger.error("数据异常：{}", e.getMessage(), e);
				insertNullKzzh(bdhm, "查无开户信息"); // 插入空账户信息
			} catch (RemoteAccessException e) {
				logger.error("查询异常：[{}]{}", e.getCode(), e.getMessage(), e);
				insertNullKzzh(bdhm, "查询异常");
			} catch (Exception e) {
				throw e;
			}
		} else {
			this.insertNullKzzh(bdhm, "查无开户信息");
		}
		
		// 4、插入任务task3
		insertMc21TaskFact3(mc21_task_fact, "GF");
	}
	
	public void insertNullKzzh(String bdhm, String errorMsg) throws Exception {
		QueryRequest_Zhxx zhxx = new QueryRequest_Zhxx();
		zhxx.setBdhm(bdhm);
		zhxx.setFksj(DtUtils.getNowTime());
		zhxx.setQrydt(DtUtils.getNowDate());
		zhxx.setKhzh(errorMsg);
		zhxx.setCcxh("1");
		mm30_xzcsMapper.insertBr30_xzcs_info(zhxx);
	}
	
	// 插入金融资产信息	
	public void insertJrxxList(List<QueryRequest_Jrxx> jrxxList, String bdhm) throws Exception {
		if (jrxxList != null) {
			int xh = 1;
			for (QueryRequest_Jrxx jrxx : jrxxList) {
				jrxx.setBdhm(bdhm);
				if (jrxx.getZcxh() == null || jrxx.getZcxh().length() == 0) {
					jrxx.setZcxh(String.valueOf(xh++));
				}
				jrxx.setQrydt(DtUtils.getNowDate());
				dictCoder.reverse(jrxx, TASK_TYPE_GF);
				// mm30_xzcsMapper.insertBr30_xzcs_info_zc_hx(jrxx);
			}
			BatchDBTools.batchInsertQueryRequest_Jrxx(jrxxList, 1000);
		}
	}
	
	// 司法强制措施信息
	public void insertDjxxList(List<QueryRequest_Djxx> djxxList, String bdhm) throws Exception {
		if (djxxList != null) {
			int xh = 1;
			for (QueryRequest_Djxx djxx : djxxList) {
				djxx.setBdhm(bdhm);
				if (djxx.getCsxh() == null || djxx.getCsxh().length() == 0) {
					djxx.setCsxh(String.valueOf(xh++));
				}
				djxx.setQrydt(DtUtils.getNowDate());
				if (djxx.getDjwh() == null) {
					djxx.setDjwh("");
				}
				dictCoder.reverse(djxx, TASK_TYPE_GF);
				// mm30_xzcsMapper.insertBr30_xzcs_info_cs_hx(djxx);
			}
			BatchDBTools.batchInsertQueryRequest_Djxx(djxxList, 1000);
		}
	}
	
	// 共有权/优先权信息
	public void insertQlxxList(List<QueryRequest_Qlxx> qlxxList, String bdhm) throws Exception {
		if (qlxxList != null) {
			int xh = 1;
			for (QueryRequest_Qlxx qlxx : qlxxList) {
				qlxx.setBdhm(bdhm);
				if (qlxx.getXh() == null || qlxx.getXh().length() == 0) {
					qlxx.setXh(String.valueOf(xh++));
				}
				qlxx.setQrydt(DtUtils.getNowDate());
				dictCoder.reverse(qlxx, TASK_TYPE_GF);
				// mm30_xzcsMapper.insertBr30_xzcs_info_ql_hx(qlxx);
			}
			BatchDBTools.batchInsertQueryRequest_Qlxx(qlxxList, 1000);
		}
	}
	
	//资金往来信息
	public void insertWlxxList(List<QueryRequest_Wlxx> wlxxList, String bdhm) throws Exception {
		if (wlxxList != null) {
			int xh = 1;
			for (QueryRequest_Wlxx wlxx : wlxxList) {
				wlxx.setBdhm(bdhm);
				if (wlxx.getWlxh() == null || wlxx.getWlxh().length() == 0) {
					wlxx.setWlxh(String.valueOf(xh++));
				}
				wlxx.setQrydt(DtUtils.getNowDate());
				dictCoder.reverse(wlxx, TASK_TYPE_GF); // 码表转换（即使是批后提取业务数据，也需要进行码表转换）
				// mm30_xzcsMapper.insertBr30_xzcs_info_wl_hx(wlxx);
			}
			BatchDBTools.batchInsertQueryRequest_Wlxx(wlxxList, 1000);
		}
	}
	
	//关联子账号
	public void insertGlxxList(List<QueryRequest_Glxx> glxxList, String bdhm) throws Exception {
		if (glxxList != null) {
			int xh = 1;
			for (QueryRequest_Glxx glxx : glxxList) {
				glxx.setBdhm(bdhm);
				if (glxx.getGlxh() == null || glxx.getGlxh().length() == 0) {
					glxx.setGlxh(String.valueOf(xh++));
				}
				glxx.setQrydt(DtUtils.getNowDate());
				dictCoder.reverse(glxx, TASK_TYPE_GF);
				// mm30_xzcsMapper.insertBr30_xzcs_info_glxx_hx(glxx);
			}
			BatchDBTools.batchInsertQueryRequest_Glxx(glxxList, 1000);
		}
	}
	
	/**
	 * 反馈查询任务
	 * 
	 * @param mc21_task_fact
	 * @throws Exception
	 */
	public void feedBackQuery(MC21_task_fact mc21_task_fact) throws Exception {
		String bdhm = mc21_task_fact.getBdhm();
		
		QueryResponse_Cxqq cxqq = mm30_xzcsMapper.getBr30_xzcsVo(bdhm);
		String packetKey = cxqq.getPacketkey();
		
		boolean mkzip = true; // 是否需要打ZIP包（WebService模式单个BDHM直接反馈）
		if (packetKey.startsWith("#")) {
			mkzip = false;
		}
		
		// 1.获取查询任务数据
		QueryRequest request = queryRequestData(bdhm);
		// 2.生成查询反馈报文
		String relativePath = CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, Keys.FILE_DIRECTORY_GF);
		String absolutePath = ServerEnvironment.getFileRootPath() + relativePath;
		
		String xmlFileName = null;
		String bindingName = null;
		if (mkzip) {
			xmlFileName = this.getCxqqXMLFileName(TASK_TYPE_GF, packetKey, bdhm) + ".xml";
			bindingName = "binding_query_req";
		} else {
			xmlFileName = "QR" + packetKey.substring(5) + ".xml";
			bindingName = "binding_query_req1A";
		}
		String xmlFilePath = relativePath + File.separator + xmlFileName;
		String xmlContent = CommonUtils.marshallContext(request, bindingName, true);
		CommonUtils.writeBinaryFile(xmlContent.getBytes(StandardCharsets.UTF_8), absolutePath, xmlFileName);
		
		// 3.写表BR32_MSG
		this.insertBr32_msg(cxqq, xmlFilePath, mkzip ? "0" : "2");
		
		// 4.置主表状态
		QueryResponse_Cxqq upcxqq = new QueryResponse_Cxqq();
		upcxqq.setBdhm(bdhm);
		upcxqq.setStatus("6"); // 6.已生成报文
		mm30_xzcsMapper.updateBr30_xzcs(upcxqq);
		
		if (mkzip) {
			// 5.写task3生成数据包，需判断查询请求表(br30_xzcs)中同数据包编码(packetkey)下的状态只为（6.已生成报文  7:退回监管）才能写打包任务
			int i = mm30_xzcsMapper.isPacketCount(packetKey);
			if (i == 0) {
				mc21_task_fact.setTaskkey("'T3_" + "GF" + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
				mc21_task_fact.setDatatime(DtUtils.getNowTime());
				mc21_task_fact.setTaskid("TK_ESGF_FK03");
				mc21_task_fact.setBdhm(cxqq.getPacketkey());
				//删除task3
				int count = common_Mapper.selMc21_task_fact3_packet(packetKey);
				if (count == 0) {
					common_Mapper.insertMc21_task_fact3_packet(mc21_task_fact);
				}
			}
		} else {
			RequestMessageService01 service = SpringContextHolder.getBean("requestMessageService01");
			Result result = service.shfeedXzcxInfo(xmlContent);
			Result_Jg jg = null;
			if (result.getRealJgList() == null || result.getRealJgList().size() == 0) {
				if ("已经反馈, 不能再次反馈".equals(result.getRealError())) {
					jg = new Result_Jg(bdhm, "success", result.getRealError());
				} else {
					jg = new Result_Jg(bdhm, "fail", result.getRealError());
				}
			} else {
				jg = result.getRealJgList().get(0);
				if (jg.getBdhm() == null || jg.getBdhm().length() == 0) {
					jg.setBdhm(bdhm);
				}
			}
			
			jg.setQrydt(Utility.currDateTime19());
			mm30_xzcsMapper.deleteBr32_jg(bdhm);
			mm30_xzcsMapper.insertBr32_jg(jg);
			
			if ("fail".equalsIgnoreCase(jg.getResult())) {
				throw new DataOperateException(jg.getBdhm(), jg.getMsg());
			}
		}
	}
	
	public void insertBr32_msg(QueryResponse_Cxqq cxqqinfo, String path, String status) throws Exception {
		Br32_msg msg = new Br32_msg();
		String packetKey = cxqqinfo.getPacketkey();
		String organkey_r;
		if (packetKey.startsWith("#")) {
			organkey_r = packetKey.substring(5, 22);
		} else {
			organkey_r = packetKey.substring(4, 21);
		}
		String filename = FileUtils.getFilenameFromPath(path);
		msg.setMsgkey(this.geSequenceNumber("SEQ_BR32_MSG"));
		msg.setBdhm(cxqqinfo.getBdhm());
		msg.setMsg_type_cd(cxqqinfo.getMsg_type_cd());
		msg.setPacketkey(cxqqinfo.getPacketkey());
		msg.setOrgankey_r(organkey_r);
		msg.setSenddate(DtUtils.getNowDate());
		msg.setMsg_filename(filename);
		msg.setMsg_filepath(path);
		msg.setStatus_cd(status); //报文状态 0:待打包 1:已打包 2:不打包（WebService模式）
		msg.setCreate_dt(DtUtils.getNowTime());
		msg.setQrydt(DtUtils.getNowDate());
		mm30_xzcsMapper.delBr32_msg(msg);
		mm30_xzcsMapper.insertBr32_msg(msg);
	}
	
	/**
	 * 获取查询任务数据
	 * 
	 * @param mc21_task_fact
	 * @param dataSource
	 * @throws Exception
	 */
	public QueryRequest queryRequestData(String bdhm) throws Exception {
		QueryRequest request = new QueryRequest();
		// 账户基本信息(本项信息必须反馈，若无开户信息仍需反馈一条记彔即KHZH“查无开户信息')
		List<QueryRequest_Zhxx> zhxxList = mm30_xzcsMapper.getBr30_xzcs_infoList(bdhm);
		if (zhxxList == null || zhxxList.size() == 0) {
			zhxxList = new ArrayList<QueryRequest_Zhxx>(1);
			QueryRequest_Zhxx zhxxfk = new QueryRequest_Zhxx();
			zhxxfk.setCcxh("1");
			zhxxfk.setBdhm(bdhm);
			zhxxfk.setKhzh("查无开户信息");
			zhxxfk.setFksj(DtUtils.getNowTime());
			zhxxList.add(zhxxfk);
		} else {
			for (QueryRequest_Zhxx zhxx : zhxxList) {
				// 金融资产信息（判断无后则不反馈）
				List<QueryRequest_Jrxx> jrxxList = mm30_xzcsMapper.getBr30_xzcs_info_zcList(zhxx);
				// 司法强制措施信息（判断无后则不反馈 )
				List<QueryRequest_Djxx> djxxList = mm30_xzcsMapper.getBr30_xzcs_info_csList(zhxx);
				// 资金往来（交易）信息（判断无后则不反馈 )
				List<QueryRequest_Wlxx> wlxxList = mm30_xzcsMapper.getBr30_xzcs_info_wlList(zhxx);
				// 共有权/优先权信息（判断无后则不反馈）
				List<QueryRequest_Qlxx> qlxxList = mm30_xzcsMapper.getBr30_xzcs_info_qlList(zhxx);
				// 关联子类账户信息（判断无后则不反馈）
				List<QueryRequest_Glxx> glxxList = mm30_xzcsMapper.getBr30_xzcs_info_glxxList(zhxx);
				
				zhxx.setJrxxList(jrxxList);
				zhxx.setDjxxList(djxxList);
				zhxx.setWlxxList(wlxxList);
				zhxx.setQlxxList(qlxxList);
				zhxx.setGlxxList(glxxList);
			}
		}
		request.setZhxxList(zhxxList);
		
		return request;
	}
	
	/**
	 * type:1:卡号 2：证件号码 3子账号
	 * subjecttype: 1自然人 2法人
	 * 
	 * @param mc21_task_fact
	 * @param type
	 * @param cardnumber
	 */
	public void insertOrgMc21TaskFact2(MC21_task_fact mc21_task_fact, String zjlx, String zjhm) throws Exception {
		
		String subtaskid = mc21_task_fact.getSubtaskid();
		String bdhm = mc21_task_fact.getBdhm();
		String returnStr = "update  br30_xzcs set orgkey=@A@,msgcheckresult='1'  where  bdhm='" + mc21_task_fact.getBdhm() + "'&";
		returnStr = returnStr + "update  br30_xzcs set status='5',msgcheckresult=@D@  where  bdhm='" + mc21_task_fact.getBdhm() + "'";
		mc21_task_fact.setTaskobj(returnStr);
		this.insertMc21TaskFact2_Org(mc21_task_fact, "2", zjhm, zjlx, "1");
		mc21_task_fact.setSubtaskid(subtaskid);
		mc21_task_fact.setBdhm(bdhm);
		mc21_task_fact.setTaskobj("");
		
	}
	
	/**
	 * 删除查询信息
	 */
	public void delCxqqInfo(String bdhm) throws Exception {
		mm30_xzcsMapper.delBr30_xzcs_info_cs(bdhm);
		mm30_xzcsMapper.delBr30_xzcs_info_glxx(bdhm);
		mm30_xzcsMapper.delBr30_xzcs_info_ql(bdhm);
		mm30_xzcsMapper.delBr30_xzcs_info_wl(bdhm);
		mm30_xzcsMapper.delBr30_xzcs_info_zc(bdhm);
		mm30_xzcsMapper.delBr30_xzcs_info(bdhm);
	}
}
