package com.citic.server.cbrc.task.taskBo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.cbrc.CBRCConstants;
import com.citic.server.cbrc.CBRCKeys;
import com.citic.server.cbrc.domain.Br40_cxqq;
import com.citic.server.cbrc.domain.Br41_kzqq;
import com.citic.server.cbrc.domain.Br42_msg;
import com.citic.server.cbrc.domain.Br42_packet;
import com.citic.server.cbrc.domain.CBRC_ReturnReceipt;
import com.citic.server.cbrc.domain.request.CBRC_ExecResult;
import com.citic.server.cbrc.domain.request.CBRC_FeedbackRequest_Record;
import com.citic.server.cbrc.domain.request.CBRC_FreezeRequest;
import com.citic.server.cbrc.domain.request.CBRC_FreezeRequest_Detail;
import com.citic.server.cbrc.domain.request.CBRC_FreezeRequest_Record;
import com.citic.server.cbrc.domain.request.CBRC_StopPaymentRequest;
import com.citic.server.cbrc.domain.request.CBRC_StopPaymentRequest_Detail;
import com.citic.server.cbrc.domain.request.CBRC_StopPaymentRequest_Recored;
import com.citic.server.cbrc.domain.response.CBRC_FreezeResponse_Account;
import com.citic.server.cbrc.domain.response.CBRC_StopPaymentResponse_Account;
import com.citic.server.cbrc.mapper.MM40_cxqq_cbrcMapper;
import com.citic.server.cbrc.mapper.MM41_kzqq_cbrcMapper;
import com.citic.server.cbrc.service.RequestMessageServiceCBRC;
import com.citic.server.dict.DictCoder;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.service.task.taskBo.BaseBo;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FileUtils;
import com.citic.server.utils.PDFUtils;
import com.citic.server.utils.Zip4jUtils;

public class Kzqq_FKBo extends BaseBo {
	// private static final Logger logger = (Logger) LoggerFactory.getLogger(Kzqq_FKBo.class);
	
	private MM41_kzqq_cbrcMapper br41_kzqqMapper;
	
	/** 数据字典码值转换组件 */
	private DictCoder dictCoder;
	
	public Kzqq_FKBo(ApplicationContext ac) {
		super(ac);
		this.br41_kzqqMapper = (MM41_kzqq_cbrcMapper) ac.getBean("MM41_kzqq_cbrcMapper");
		this.dictCoder = (DictCoder) SpringContextHolder.getBean(DictCoder.class);
	}
	
	/**
	 * 反馈冻结控制任务单 -- task3
	 * 
	 * @param mc21_task_fact
	 * @param taskmag
	 * @throws Exception
	 */
	public void FeedBackControl_dj(MC21_task_fact mc21_task_fact) throws Exception {
		String taskType = mc21_task_fact.getTasktype();
		
		CBRC_FreezeResponse_Account cg_Freeze = new CBRC_FreezeResponse_Account();
		
		String qqdbs = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
		String rwlsh = StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$");
		Br41_kzqq _kzqq = new Br41_kzqq();
		_kzqq.setTasktype(taskType);
		_kzqq.setQqdbs(qqdbs);
		Br41_kzqq br41_kzqq = br41_kzqqMapper.selectBr41_kzqqByVo(_kzqq);
		
		cg_Freeze.setTasktype(taskType);
		cg_Freeze.setQqdbs(qqdbs);
		cg_Freeze.setRwlsh(rwlsh);
		//CBRC_FreezeRequest_Record  _freeze_back = br41_kzqqMapper.selectBr41_kzqq_dj_backByVo(cg_Freeze);
		
		List<CBRC_FreezeRequest_Record> _freeze_backList = br41_kzqqMapper.selectBr41_kzqq_dj_backByList(cg_Freeze);
		
		//2.feedbackinfo反馈信息: request01
		CBRC_FreezeRequest request01 = new CBRC_FreezeRequest();
		request01.setQqdbs(qqdbs);
		request01.setZtlb(br41_kzqq.getZtlb());
		request01.setSqjgdm(br41_kzqq.getSqjgdm());
		request01.setMbjgdm(br41_kzqq.getMbjgdm());
		request01.setHzsj(DtUtils.getNowTime());
		request01.setSfxd("01");
		//request01.setFreezeRecordList(_freeze_backList);
		
		if (_freeze_backList != null && _freeze_backList.size() > 0) {
			for (CBRC_FreezeRequest_Record _freeze : _freeze_backList) {
				List<CBRC_FreezeRequest_Record> freeze_backList = new ArrayList<CBRC_FreezeRequest_Record>();
				freeze_backList.add(_freeze);
				
				String xmltype_y = "SS17";
				if ("01".equals(_freeze.getZtlb())) {
					xmltype_y = "SS17";
				} else {
					xmltype_y = "SS18";
				}
				request01.setZtlb(_freeze.getZtlb());
				String xmltype = CBRCConstants.valueOf(xmltype_y);
				
				// 特殊字段：基本数据项 - 操作失败原因
				if ("0".equals(_freeze.getZxjg())) { // 执行结果 = 0（执行成功）
					request01.setCzsbyy("");
				} else {
					request01.setCzsbyy("失败");
				}
				
				// 处理冻结请求反馈明细
				dealCBRC_FreezeRequest_Detail(freeze_backList);
				request01.setFreezeRecordList(freeze_backList);
				
				// 2.1生成XML
				String path = generateXMLDocument(request01, br41_kzqq, xmltype, "cbrc_freeze_req", mc21_task_fact.getTasktype());
				
				// 2.2写表BR42_MSG
				br41_kzqq.setRwlsh(_freeze.getRwlsh());
				insertBr42_msg(br41_kzqq, path, br41_kzqq.getMbjgdm());
				
				// 4.置反馈表状态
				_freeze.setStatus("6"); // 2.已生成报文
				br41_kzqqMapper.updateBr41_kzqq_dj_back(_freeze);
				
			}
		}
		
		//5.写task3生成数据包，需判断查询请求表(Br40_cxqq_back)中同请求单编码(Qqdbs)下的状态只为（6.已生成报文 ）才能写打包任务
		int i = br41_kzqqMapper.isPacket_djCount(br41_kzqq);
		if (i == 0) {
			// 置主表状态
			br41_kzqq.setStatus("2"); // 已处理
			br41_kzqqMapper.updateBr41_kzqq(br41_kzqq);
			
			if (ServerEnvironment.TASK_TYPE_GONGAN.equals(taskType) || ServerEnvironment.TASK_TYPE_GAOJIAN.equals(taskType)) {
				dealReturnReceipt29(br41_kzqq, "dj");//请求执行结束回执信息内容
			} else if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(taskType)) {
				// 深圳公安局需要生成冻结回执文书（PDF）以及回执信息文件（XML）
				List<CBRC_FreezeRequest_Record> records = br41_kzqqMapper.selectBr41_kzqq_dj_backInPacket(qqdbs, taskType);
				
				String absolutePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_ATTACH, CBRCKeys.FILE_DIRECTORY_08);
				String pdfFileName = "RR29" + br41_kzqq.getMbjgdm().substring(0, 8) + qqdbs + ".pdf";
				String outputFilePath = absolutePath + File.separator + qqdbs + File.separator + pdfFileName;
				
				// 处理申请机构代码（数据字典）
				String sqjgdm = br41_kzqq.getSqjgdm();
				String mbjgdm = br41_kzqq.getMbjgdm();
				dictCoder.transcode(br41_kzqq, taskType);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("RECORDLIST", records);
				map.put("QQDBS", qqdbs);
				map.put("QQCSLX", br41_kzqq.getQqcslx()); // 
				map.put("SQJGDM", br41_kzqq.getSqjgdm()); // 
				map.put("MBJGDM", br41_kzqq.getMbjgdm()); // 
				map.put("DATETIME", Utility.currDateCN());
				PDFUtils.html2pdf("djhzws8.html", outputFilePath, map);
				
				// 还原码表转换的值（SQJGDM）
				br41_kzqq.setSqjgdm(sqjgdm);
				br41_kzqq.setMbjgdm(mbjgdm);
				
				insertBr42_msg(br41_kzqq, outputFilePath, br41_kzqq.getMbjgdm());
			}
			
			String prefix = "CBRC_" + taskType;
			mc21_task_fact.setTaskkey("'T3_" + prefix + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
			mc21_task_fact.setDatatime(DtUtils.getNowTime());
			mc21_task_fact.setTaskid("TK_ESYJ_FK06");
			if (!ServerEnvironment.TASK_TYPE_GONGAN.equals(mc21_task_fact.getTasktype())) {
				mc21_task_fact.setTaskid("TK_ESYJ_FK06_" + taskType);
			}
			if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(mc21_task_fact.getTasktype())) {
				mc21_task_fact.setBdhm(br41_kzqq.getQqdbs() + "$" + br41_kzqq.getMbjgdm().substring(0, 8));
			} else {
				mc21_task_fact.setBdhm(br41_kzqq.getQqdbs() + "$" + br41_kzqq.getMbjgdm());
			}
			mc21_task_fact.setTaskobj(br41_kzqq.getSqjgdm());
			// 高检所有xml打包发送
			if (ServerEnvironment.TASK_TYPE_GAOJIAN.equals(mc21_task_fact.getTasktype())) {
				kzPackageXmlFileList(mc21_task_fact);
			} else {
				common_Mapper.insertMc21_task_fact3_packet(mc21_task_fact);
			}
		}
	}
	
	// 高检控制请求反馈xml打包发送
	public void kzPackageXmlFileList(MC21_task_fact mc21_task_fact) throws Exception {
		String taskType = mc21_task_fact.getTasktype(); // 任务类型
		Br41_kzqq br41_kzqq = this.getBr41_kzqqFortask3(mc21_task_fact);
		String qqdbs = br41_kzqq.getQqdbs();
		String organkey = br41_kzqq.getMbjgdm();
		String sqjgdm = br41_kzqq.getSqjgdm();
		
		String rootPath = ServerEnvironment.getFileRootPath();
		String relativePath = CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, CBRCKeys.getFileDirectoryKey(taskType)) + File.separator + qqdbs;
		
		Br42_packet packet = new Br42_packet();
		packet.setTasktype(taskType);
		packet.setPacketkey(qqdbs);
		ArrayList<File> xmlFileList = new ArrayList<File>();
		List<Br42_msg> msgList = br41_kzqqMapper.getBr42_msg(packet);
		for (Br42_msg _msg : msgList) {
			xmlFileList.add(new File(_msg.getMsg_filepath()));
		}
		int n = 2;
		for (File xmlfile : xmlFileList) {
			String xmlZipFileSEQ = "00000000000" + n;
			xmlZipFileSEQ = xmlZipFileSEQ.substring(xmlZipFileSEQ.length() - 12, xmlZipFileSEQ.length());
			ArrayList<File> xmlFiles = new ArrayList<File>();
			xmlFiles.add(xmlfile);
			// CBRC命名规范：查控机构代码【6/8位】
			// +银行代码【17位】+请求单标识【22位】+序号【12位，000000000001-999999999999】.ZIP
			String realZipFileName = sqjgdm + organkey + qqdbs + xmlZipFileSEQ;
			String zipFileName = realZipFileName + ".zip";
			String absoluteZipFilePath = rootPath + relativePath + File.separator + zipFileName;
			// 先删除可能已经存在的同名压缩包文件
			FileUtils.deleteFile(absoluteZipFilePath);
			Zip4jUtils.addFilesWithDeflateComp(absoluteZipFilePath, xmlFiles); // 无密码压缩方式
			// 发送
			packet.setFilename(zipFileName);
			packet.setFilepath(relativePath + File.separator + zipFileName);
			RequestMessageServiceCBRC service = new RequestMessageServiceCBRC();
			CBRC_ReturnReceipt returnReceipt = service.sendZipPackage(packet);
			String bdhm = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
			if (returnReceipt == null) {
				returnReceipt = new CBRC_ReturnReceipt();
				returnReceipt.setQqdbs(bdhm); // 请求单标识
				returnReceipt.setHzdm(CBRCConstants.REC_CODE_99999); // 回执代码n
				returnReceipt.setJssj(Utility.currDateTime19()); // 接收时间
				returnReceipt.setHzsm("未知错误"); // 回执说明
				returnReceipt.setTasktype(mc21_task_fact.getTasktype()); // 任务类型
			} else {
				returnReceipt.setQqdbs(bdhm); // 请求单标识
				returnReceipt.setTasktype(mc21_task_fact.getTasktype()); // 任务类型
			}
			MM40_cxqq_cbrcMapper mm40_cxqq_cbrcMapper = (MM40_cxqq_cbrcMapper) SpringContextHolder.getBean("MM40_cxqq_cbrcMapper");
			mm40_cxqq_cbrcMapper.delBr40_receipt(returnReceipt);
			mm40_cxqq_cbrcMapper.insertBr40_receipt(returnReceipt);
			br41_kzqqMapper.updateBr41_kzqqStatus(returnReceipt); // 已打包
			n++;
		}
		for (File xmlFiles : xmlFileList) {
			if (xmlFiles.exists()) {
				xmlFiles.delete();
			}
		}
	}
	
	// 获取控制请求
	private Br41_kzqq getBr41_kzqqFortask3(MC21_task_fact mc21_task_fact) {
		Br41_kzqq br41_kzqq = new Br41_kzqq();
		br41_kzqq.setQqdbs(StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$"));// 请求单标识
		br41_kzqq.setTasktype(mc21_task_fact.getTasktype());// 监管类别
		br41_kzqq = br41_kzqqMapper.selectBr41_kzqqByVo(br41_kzqq);
		br41_kzqq.setRwlsh(StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$"));
		return br41_kzqq;
	}
	
	/**
	 * 反馈止付控制任务单 -- task3
	 * 
	 * @param mc21_task_fact
	 * @param taskmag
	 * @throws Exception
	 */
	public void FeedBackControl_zf(MC21_task_fact mc21_task_fact) throws Exception {
		String taskType = mc21_task_fact.getTasktype();
		
		CBRC_StopPaymentResponse_Account cg_stoppay = new CBRC_StopPaymentResponse_Account();
		String qqdbs = StringUtils.substringBefore(mc21_task_fact.getBdhm(), "$");
		String rwlsh = StringUtils.substringAfter(mc21_task_fact.getBdhm(), "$");
		Br41_kzqq _kzqq = new Br41_kzqq();
		_kzqq.setTasktype(taskType);
		_kzqq.setQqdbs(qqdbs);
		Br41_kzqq br41_kzqq = br41_kzqqMapper.selectBr41_kzqqByVo(_kzqq);
		
		cg_stoppay.setTasktype(taskType);
		cg_stoppay.setQqdbs(qqdbs);
		cg_stoppay.setRwlsh(rwlsh);
		List<CBRC_StopPaymentRequest_Recored> _stoppay_backList = br41_kzqqMapper.selectBr41_kzqq_zf_backByList(cg_stoppay);
		
		//1.feedbackinfo反馈信息: request01
		CBRC_StopPaymentRequest request01 = new CBRC_StopPaymentRequest();
		request01.setQqdbs(qqdbs);
		request01.setZtlb(br41_kzqq.getZtlb());
		request01.setSqjgdm(br41_kzqq.getSqjgdm());
		request01.setMbjgdm(br41_kzqq.getMbjgdm());
		request01.setHzsj(DtUtils.getNowTime());
		//request01.setStopPaymentRecoredList(_stoppay_backList);
		String path = "";
		
		if (_stoppay_backList != null && _stoppay_backList.size() > 0) {
			for (CBRC_StopPaymentRequest_Recored _stoppay : _stoppay_backList) {
				List<CBRC_StopPaymentRequest_Recored> stoppay_backList = new ArrayList<CBRC_StopPaymentRequest_Recored>();
				stoppay_backList.add(_stoppay);
				
				String xmltype_y = "SS21";
				if ("01".equals(_stoppay.getZtlb())) {
					xmltype_y = "SS21";
				} else {
					xmltype_y = "SS22";
				}
				request01.setZtlb(_stoppay.getZtlb());
				String xmltype = CBRCConstants.valueOf(xmltype_y);
				
				// 特殊字段：基本数据项 - 操作失败原因
				if ("0".equals(_stoppay.getZxjg())) { // 执行结果 = 0（执行成功）
					request01.setCzsbyy("");
				} else {
					request01.setCzsbyy("失败");
				}
				
				//处理止付请求反馈明细
				dealCBRC_StopPaymentRequest_Detail(stoppay_backList);
				request01.setStopPaymentRecoredList(stoppay_backList);
				
				// 2.1生成XML
				path = generateXMLDocument(request01, br41_kzqq, xmltype, "cbrc_stoppayment_req", mc21_task_fact.getTasktype());
				
				// 2.2写表BR42_MSG
				br41_kzqq.setRwlsh(_stoppay.getRwlsh());
				insertBr42_msg(br41_kzqq, path, br41_kzqq.getMbjgdm());
				
				// 4.置反馈表状态
				_stoppay.setStatus("6"); // 2.已生成报文
				br41_kzqqMapper.updateBr41_kzqq_zf_back(_stoppay);
			}
			
		}
		
		//5.写task3生成数据包，需判断查询请求表(Br40_cxqq_back)中同请求单编码(Qqdbs)下的状态只为（6.已生成报文 ）才能写打包任务
		int i = br41_kzqqMapper.isPacket_zfCount(br41_kzqq);
		if (i == 0) {
			
			// 置主表状态
			br41_kzqq.setStatus("2"); // 已处理
			br41_kzqqMapper.updateBr41_kzqq(br41_kzqq);
			
			if (ServerEnvironment.TASK_TYPE_GONGAN.equals(taskType)) {
				dealReturnReceipt29(br41_kzqq, "zf");//请求执行结束回执信息内容
			}
			
			String prefix = "CBRC_" + taskType;
			mc21_task_fact.setTaskkey("'T3_" + prefix + "_'||" + dbfunc.getSeq("SEQ_MC21_TASK_FACT3"));
			mc21_task_fact.setDatatime(DtUtils.getNowTime());
			mc21_task_fact.setTaskid("TK_ESYJ_FK06");
			if (!ServerEnvironment.TASK_TYPE_GONGAN.equals(mc21_task_fact.getTasktype())) {
				mc21_task_fact.setTaskid("TK_ESYJ_FK06_" + taskType);
			}
			if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(mc21_task_fact.getTasktype())) {
				mc21_task_fact.setBdhm(br41_kzqq.getQqdbs() + "$" + br41_kzqq.getMbjgdm().substring(0, 8));
			} else {
				mc21_task_fact.setBdhm(br41_kzqq.getQqdbs() + "$" + br41_kzqq.getMbjgdm());
			}
			mc21_task_fact.setTaskobj(br41_kzqq.getSqjgdm());
			common_Mapper.insertMc21_task_fact3_packet(mc21_task_fact);
		}
	}
	
	public void dealReturnReceipt29(Br41_kzqq br41_kzqq, String type) throws Exception {
		
		CBRC_ExecResult execResult = new CBRC_ExecResult();
		
		execResult.setQqdbs(br41_kzqq.getQqdbs());
		execResult.setQqcslx(br41_kzqq.getQqcslx());
		execResult.setMbjgdm(br41_kzqq.getMbjgdm());
		execResult.setSqjgdm(br41_kzqq.getSqjgdm());
		execResult.setHzsj(Utility.currDateTime14());
		if (type.equals("zf")) {
			CBRC_StopPaymentResponse_Account stappay = new CBRC_StopPaymentResponse_Account();
			stappay.setTasktype(br41_kzqq.getTasktype());
			stappay.setQqdbs(br41_kzqq.getQqdbs());
			List<CBRC_StopPaymentRequest_Recored> zfList = br41_kzqqMapper.selectBr41_kzqq_zf_backByList(stappay);
			execResult.setJsrws(zfList.size() + "");
			execResult.setFkrws(zfList.size() + "");
		} else {
			CBRC_FreezeResponse_Account freeze = new CBRC_FreezeResponse_Account();
			freeze.setTasktype(br41_kzqq.getTasktype());
			freeze.setQqdbs(br41_kzqq.getQqdbs());
			List<CBRC_FreezeRequest_Record> djList = br41_kzqqMapper.selectBr41_kzqq_dj_backByList(freeze);
			execResult.setJsrws(djList.size() + "");
			execResult.setFkrws(djList.size() + "");
		}
		execResult.setFkkhs("0");
		execResult.setFkzhs("0");
		execResult.setFkjymxs("0");
		
		String xmlPath = generateXMLDocument(execResult, br41_kzqq, "RR29", "cbrc_execresult_req", br41_kzqq.getTasktype());
		
		// 文件地址入库
		br41_kzqq.setRwlsh("RR29");
		insertBr42_msg(br41_kzqq, xmlPath, br41_kzqq.getMbjgdm());
	};
	
	public List<CBRC_FeedbackRequest_Record> getWsInfo(Br41_kzqq br41_kzqq) throws Exception {
		List<CBRC_FeedbackRequest_Record> liInfoList = br41_kzqqMapper.selectBr41_cxqq_hzwsList(br41_kzqq);
		return liInfoList;
	}
	
	/**
	 * 生成查询反馈报文XML
	 * 
	 * @param mc21_task_fact
	 * @param qs_zhxxlist
	 * @throws Exception
	 */
	public String generateXMLDocument(Object request, Br41_kzqq br41_kzqq, String xmltype, String bindingname, String taskType) throws Exception {
		String absolutePath = CommonUtils.createAbsolutePath(Keys.FILE_PATH_ATTACH, CBRCKeys.getFileDirectoryKey(taskType));
		absolutePath = absolutePath + File.separator + br41_kzqq.getQqdbs();
		String mbjgdm = br41_kzqq.getMbjgdm(); // 目标机构代码（银行代码）
		if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(taskType)) { // 深圳公安局
			mbjgdm = mbjgdm.substring(0, 8); // 截取前8位
		}
		String filename = this.getMsgNameCBRC(br41_kzqq.getTasktype(), xmltype, br41_kzqq.getQqdbs(), mbjgdm) + ".xml";
		CommonUtils.marshallUTF8Document(request, bindingname + taskType, absolutePath, filename);
		
		return absolutePath + File.separator + filename;
	}
	
	public void insertBr42_msg(Br41_kzqq br41_kzqq, String path, String organkey_r) throws Exception {
		Br42_msg msg = new Br42_msg();
		
		String filename = FileUtils.getFilenameFromPath(path);
		msg.setMsgkey(this.geSequenceNumber("SEQ_BR42_MSG"));
		msg.setBdhm(br41_kzqq.getRwlsh());
		msg.setMsg_type_cd(filename.substring(0, 4));
		msg.setPacketkey(br41_kzqq.getQqdbs());
		if (ServerEnvironment.TASK_TYPE_SHENZHEN.equals(br41_kzqq.getTasktype())) {
			msg.setOrgankey_r(organkey_r.substring(0, 8));
		} else {
			msg.setOrgankey_r(organkey_r);
		}
		msg.setSenddate(DtUtils.getNowDate());
		msg.setMsg_filename(filename);
		msg.setMsg_filepath(path);
		msg.setStatus_cd("0"); //报文状态 0:待打包 1:已打包
		msg.setCreate_dt(DtUtils.getNowTime());
		msg.setQrydt(DtUtils.getNowDate());
		msg.setTasktype(br41_kzqq.getTasktype());
		br41_kzqqMapper.delBr42_msg(msg);
		br41_kzqqMapper.insertBr42_msg(msg);
	}
	
	public void dealCBRC_StopPaymentRequest_Detail(List<CBRC_StopPaymentRequest_Recored> _stoppay_backList) {
		for (CBRC_StopPaymentRequest_Recored stoppay : _stoppay_backList) {
			List<CBRC_StopPaymentRequest_Detail> detailList = br41_kzqqMapper.selectBr41_kzqq_zf_back_mxByList(stoppay);
			stoppay.setStopPaymentDetailList(detailList);
		}
	}
	
	public void dealCBRC_FreezeRequest_Detail(List<CBRC_FreezeRequest_Record> _freeze_backList) {
		for (CBRC_FreezeRequest_Record freeze : _freeze_backList) {
			List<CBRC_FreezeRequest_Detail> detailList = br41_kzqqMapper.selectBr41_kzqq_dj_back_mxByList(freeze);
			freeze.setFreezeDetailList(detailList);
		}
	}
}
