/**
 * Copyright (c) 2017, CITIC Application Service Provider Co., Ltd. All Rights Reserved.
 * -
 * $Author: liuxuanfei, $Date: 2017/07/10 12:02:18$
 */
package com.citic.server.gf.task;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.citic.server.SpringContextHolder;
import com.citic.server.crypto.MD5Coder;
import com.citic.server.gf.domain.Br32_msg;
import com.citic.server.gf.domain.MC20_WS;
import com.citic.server.gf.domain.Result;
import com.citic.server.gf.domain.Result_Jg;
import com.citic.server.gf.domain.request.ControlRequest;
import com.citic.server.gf.domain.request.ControlRequest_Djxx;
import com.citic.server.gf.domain.request.ControlRequest_Hzxx;
import com.citic.server.gf.domain.request.ControlRequest_Kzxx;
import com.citic.server.gf.domain.request.ControlRequest_Qlxx;
import com.citic.server.gf.domain.response.ControlResponse_Kzqq;
import com.citic.server.gf.mapper.ControlTaskMapper1A;
import com.citic.server.gf.service.RequestMessageService01;
import com.citic.server.runtime.ClasspathResourceLoader;
import com.citic.server.runtime.Constants;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.StandardCharsets;
import com.citic.server.runtime.Utility;
import com.citic.server.server.NBaseTask;
import com.citic.server.service.domain.MC21_task_fact;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DbFuncUtils;
import com.citic.server.utils.GraphicsUtilities;
import com.citic.server.utils.PDFUtils;

/**
 * @author liuxuanfei
 * @version $Revision: 1.0.0, $Date: 2017/07/10 12:02:18$
 */
public class TK_ESGF_FK02A extends NBaseTask implements Constants {
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(TK_ESGF_FK02A.class);
	
	private ControlTaskMapper1A controlMapper;
	private RequestMessageService01 messageService;
	
	protected DbFuncUtils dbfunc = new DbFuncUtils();
	
	public TK_ESGF_FK02A(ApplicationContext ac, MC21_task_fact mC21_task_fact) {
		super(ac, mC21_task_fact);
		controlMapper = SpringContextHolder.getBean(ControlTaskMapper1A.class);
		messageService = SpringContextHolder.getBean("requestMessageService01");
	}
	
	@Override
	public boolean calTask() throws Exception {
		try {
			process0(this.getMC21_task_fact());
		} catch (Exception e) {
			LOGGER.error("任务处理失败：{}", e.getMessage(), e);
			throw e;
		}
		
		return true;
	}
	
	private void process0(MC21_task_fact taskFact) throws Exception {
		String bdhm = taskFact.getBdhm();
		
		String relativeFilePath = CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, Keys.FILE_DIRECTORY_GF) + File.separator + bdhm;
		String absoluteFilePath = ServerEnvironment.getFileRootPath() + relativeFilePath;
		String absoluteFilePathURL = "file:" + absoluteFilePath + "/";
		
		ControlResponse_Kzqq kzqq = controlMapper.queryControlTaskInfo(bdhm);
		List<ControlRequest_Kzxx> kzxxList = controlMapper.queryTaskResultListByBdhm(bdhm);
		for (ControlRequest_Kzxx kzxx : kzxxList) {
			List<ControlRequest_Djxx> djxxList = controlMapper.queryTaskResultDjxxList(bdhm, kzxx.getCcxh());
			List<ControlRequest_Qlxx> qlxxList = controlMapper.queryTaskResultQlxxList(bdhm, kzxx.getCcxh());
			kzxx.setDjxxList(djxxList);
			kzxx.setQlxxList(qlxxList);
			kzxx.setCsksrq_cn(Utility.toDateTimeCN(kzxx.getCsksrq())); // 
			kzxx.setCsjsrq_cn(Utility.toDateTimeCN(kzxx.getCsjsrq())); // 
		}
		
		// 生成印章
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
		BufferedImage srcImage = ImageIO.read(resourceLoader.getResourceStream("images/gf/seal.png"));
		// BufferedImage watermarkImage = GraphicsUtilities.createWatermarkImage(srcImage, Utility.currDate8(), Color.RED);
		// watermarkImage = GraphicsUtilities.createWatermarkImage(watermarkImage, Utility.currDateTime19(), Color.BLACK, 1);
		BufferedImage watermarkImage = GraphicsUtilities.toCompatibleImage(srcImage);
		String watermarkImagePath = bdhm + Utility.currDateTime14() + ".png";
		GraphicsUtilities.writeImage(watermarkImage, "PNG", absoluteFilePath, watermarkImagePath);
		
		// 生成PDF回执文件
		String wjmc1 = null;
		String wjmc2 = null;
		List<MC20_WS> docInfoList = controlMapper.queryLegalDocumentInfo(bdhm, TASK_TYPE_GF); // 查询法律文书相关信息
		if (docInfoList == null || docInfoList.size() == 0) {
			wjmc1 = kzqq.getAh() + "执行裁定书";
		} else {
			for (MC20_WS docInfo : docInfoList) {
				wjmc1 = docInfo.getWjmc();
				if ("doc".equals(docInfo.getWjlx()) || docInfo.getWjmc().endsWith("裁定书")) {
					break;
				}
			}
		}
		wjmc2 = kzqq.getAh() + "协助执行通知书";
		kzqq.setWjmc1(wjmc1); // 裁定书
		kzqq.setWjmc2(wjmc2); // 通知书
		
		Map<String, Object> record = new HashMap<String, Object>();
		record.put("kzqq", kzqq);
		record.put("kzxxList", kzxxList);
		record.put("sealpath", watermarkImagePath);
		record.put("sysdatetime", Utility.currDateTime19());
		String pdfFileName = bdhm + Utility.currDateTime14() + ".pdf";
		PDFUtils.html2pdf("gf/hzws01.html", absoluteFilePath, pdfFileName, record, absoluteFilePathURL);
		
		// 生成XML反馈报文
		List<ControlRequest_Hzxx> hzxxList = new ArrayList<ControlRequest_Hzxx>(1);
		byte[] pdfData = CommonUtils.readBinaryFile(absoluteFilePath + File.separator + pdfFileName);
		String pdfFileNameCN = kzqq.getFymc() + "协助执行通知书（回执）";
		ControlRequest_Hzxx hzxx = new ControlRequest_Hzxx();
		hzxx.setBdhm(bdhm);
		hzxx.setWjmc(pdfFileNameCN);
		hzxx.setWjlx("pdf");
		hzxx.setWslb("回执"); // 文书类别
		hzxx.setWsnr(pdfData); // 文件内容
		hzxx.setMd5(MD5Coder.encodeHex(pdfData, true)); // 文书MD5值
		hzxx.setQrydt(Utility.currDate10());
		hzxx.setWjpath(relativeFilePath + File.separator + pdfFileName);
		hzxxList.add(hzxx);
		controlMapper.clearFallbackDocument(bdhm);
		controlMapper.insertFallbackDocument(hzxx);
		
		ControlRequest request = new ControlRequest();
		request.setKzxxList(kzxxList); // 结果信息
		request.setHzxxList(hzxxList); // 回执信息
		String xmlFileName = "QR" + ServerEnvironment.getStringValue(Keys.GF_BANK_CODE) + bdhm + ".xml";
		String relativeXmlFilePath = relativeFilePath + File.separator + xmlFileName;
		String requestXml = CommonUtils.marshallContext(request, "binding_control_req1A", true, "UTF-8");
		CommonUtils.writeTextFile(requestXml, absoluteFilePath, xmlFileName, StandardCharsets.UTF_8, false);
		controlMapper.updateControlRequestStatus(bdhm, "6"); // 6-已生成报文和文书
		
		// 反馈文件信息入库
		Br32_msg message = new Br32_msg();
		String packetKey = kzqq.getPacketkey();
		String organkey_r;
		if (packetKey.startsWith("#")) {
			organkey_r = packetKey.substring(5, 22); // !
		} else {
			organkey_r = packetKey.substring(4, 21);
		}
		message.setMsgkey(this.getNextSequence("SEQ_BR32_MSG"));
		message.setBdhm(bdhm);
		message.setMsg_type_cd(kzqq.getMsg_type_cd());
		message.setPacketkey(packetKey);
		message.setOrgankey_r(organkey_r);
		message.setSenddate(Utility.currDate10());
		message.setMsg_filename(xmlFileName);
		message.setMsg_filepath(relativeXmlFilePath);
		message.setStatus_cd("2"); // 报文状态 0:待打包 1:已打包 2:不打包（WebService模式）
		message.setCreate_dt(Utility.currDateTime19());
		message.setQrydt(Utility.currDate10());
		controlMapper.clearShfeedMessage(bdhm);
		controlMapper.insertShfeedMessage(message);
		
		// 向最高法反馈处理结果
		Result result = messageService.shfeedXzkzInfo(requestXml);
		Result_Jg jg = null;
		if (result.getRealJgList() == null || result.getRealJgList().size() == 0) {
			jg = new Result_Jg(bdhm, "fail", result.getRealError());
		} else {
			jg = result.getRealJgList().get(0);
			if (jg.getBdhm() == null || jg.getBdhm().length() == 0) {
				jg.setBdhm(bdhm);
			}
		}
		
		// 记录反馈响应结果
		jg.setQrydt(Utility.currDateTime19());
		controlMapper.clearShfeedResult(bdhm);
		controlMapper.insertShfeedResult(jg);
	}
	
	public String getNextSequence(String sname) throws Exception {
		return controlMapper.getNextSequence(dbfunc.getSeqStr(sname));
	}
}
