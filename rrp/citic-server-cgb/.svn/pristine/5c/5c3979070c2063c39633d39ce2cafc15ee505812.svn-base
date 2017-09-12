package com.citic.server.gdjg.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Br57_cxqq_back;
import com.citic.server.gdjg.domain.Br57_packet;
import com.citic.server.gdjg.domain.Gdjg_Request;
import com.citic.server.gdjg.domain.Gdjg_Response;
import com.citic.server.gdjg.domain.request.Gdjg_RequestCkdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestCkdjdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestCkjddj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDhdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDjjdhz;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDtcxdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestDtcxhz;
import com.citic.server.gdjg.domain.request.Gdjg_RequestGzdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestJjzfdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestJrcpdj;
import com.citic.server.gdjg.domain.request.Gdjg_RequestWddj;
import com.citic.server.gdjg.domain.request.Gdjg_Request_TransCx;
import com.citic.server.gdjg.mapper.Gdjg_outerPollingMapper;
import com.citic.server.gdjg.outer.OuterPollingTask21;
import com.citic.server.runtime.EncryptUtils;
import com.citic.server.runtime.GdjgKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FtpUtils;
import com.citic.server.utils.ZipUtils;

/**
 * 报文登记（上行）服务
 * 
 * @author liuxuanfei
 * @date 2017年6月2日 上午10:14:35
 */
@Service("requestMessageServiceGdjg")
public class RequestMessageServiceGdjg {
	private Logger logger = LoggerFactory.getLogger(RequestMessageServiceGdjg.class);
	
	@Autowired
	private Gdjg_outerPollingMapper gdjg_mapper;
	private FtpUtils ftpUtils;
	
	/**
	 * 银行网点登记接口
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendYhwddjMassage(Gdjg_RequestWddj request) throws RemoteAccessException {
		logger.info("银行网点登记");
		String content = request.getContent();
		return writeMessage(request, content, null);
	}
	
	/**
	 * 银行账号规则登记接口
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendYhzhgzdjMassage(Gdjg_RequestGzdj request) throws RemoteAccessException {
		logger.info("银行账号规则登记");
		String content = request.getContent();
		return writeMessage(request, content, null);
	}
	
	/**
	 * 城市代号对照登记接口
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendCsdhdzdjMassage(Gdjg_RequestDhdj request) throws RemoteAccessException {
		logger.info("城市代号对照登记");
		String content = request.getContent();
		return writeMessage(request, content, null);
	}
	
	/**
	 * 商业银行存款登记接口
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendCkdjMassage(Gdjg_RequestCkdj request) throws RemoteAccessException {
		logger.info("商业银行存款登记");
		if (request.getCases() != null && request.getCases().size() > 0) {
			String docno = request.getDocno();
			return writeMessage(request, docno, null);
		} else {//没有查询到记录时，反馈指定报文
			String feedback_req_xml = null;
			String feedback_res_xml = null;
			Gdjg_Response response = null;
			try {
				//1.生成无数据的报文
				feedback_req_xml = this.getFeedbackMessage(GdjgConstants.DATA_CONTENT_CKDJ, request.getDocno());
				//2.http加密压缩发送和解压解密获取反馈
				OuterPollingTask21 outer = new OuterPollingTask21();
				feedback_res_xml = outer.processResponseFile(feedback_req_xml);
				//3.使用JiBX绑定文件解析响应报文
				response = (Gdjg_Response) CommonUtils.unmarshallContext(Gdjg_Response.class, feedback_res_xml);
				logger.info("（无数据）存款登记结束==http发送：\n" + CommonUtils.localLineSeparator() + feedback_req_xml);
			} catch (JiBXException e) {
				logger.error("http响应信息:\n" + CommonUtils.localLineSeparator() + feedback_res_xml);
				throw new RemoteAccessException(e.getMessage(), e);
			} catch (Exception e) {
				logger.error("TASK3:http数据发送异常==http发送信息：\n" + feedback_req_xml);
				e.printStackTrace();
			}
			return response;
		}
		
	}
	
	/**
	 * 商业银行交易流水登记接口<br>
	 * 包含ftp发送流水zip（即流水登记接口）和http发送文件上传报文（即流水ftp文件登记接口）
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendJylsdjMassage(List<Gdjg_Request_TransCx> list, List<Br57_cxqq_back> backList) throws RemoteAccessException {
		logger.info("商业银行交易流水登记");
		return writeJylsMessage(list, backList);
	}
	
	/**
	 * 商业银行金融产品信息登记接口<br>
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendJrcpMassage(Gdjg_RequestJrcpdj request) throws RemoteAccessException {
		logger.info("商业银行金融产品信息登记");
		if (request.getCases() != null && request.getCases().size() > 0) {
			String docno = request.getDocno();
			return writeMessage(request, docno, null);
		} else {//没有查询到记录时，反馈指定报文
			String feedback_req_xml = null;
			String feedback_res_xml = null;
			Gdjg_Response response = null;
			try {
				//1.生成无数据的报文
				feedback_req_xml = this.getFeedbackMessage(GdjgConstants.DATA_CONTENT_YHJRCPDJ, request.getDocno());
				//2.http加密压缩发送和解压解密获取反馈
				OuterPollingTask21 outer = new OuterPollingTask21();
				feedback_res_xml = outer.processResponseFile(feedback_req_xml);
				//3.使用JiBX绑定文件解析响应报文
				response = (Gdjg_Response) CommonUtils.unmarshallContext(Gdjg_Response.class, feedback_res_xml);
				logger.info("（无数据）存款登记结束==http发送：\n" + CommonUtils.localLineSeparator() + feedback_req_xml);
			} catch (JiBXException e) {
				logger.error("http响应信息:\n" + CommonUtils.localLineSeparator() + feedback_res_xml);
				throw new RemoteAccessException(e.getMessage(), e);
			} catch (Exception e) {
				logger.error("TASK3:http数据发送异常==http发送信息：\n" + feedback_req_xml);
				e.printStackTrace();
			}
			return response;
		}
	}
	
	/**
	 * 商业银行存款冻结结果信息登记接口<br>
	 * 
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendFreezeControlMessage(Gdjg_RequestCkdjdj request) throws RemoteAccessException {
		logger.info("商业银行存款冻结结果信息登记");
		String docno = request.getDocno();
		return writeMessage(request, docno, null);
	}
	
	/**
	 * 商业银行存款解冻结果信息登记接口<br>
	 * 
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendUnfreezeControlMessage(Gdjg_RequestCkjddj request) throws RemoteAccessException {
		logger.info("商业银行存款解冻结果信息登记");
		String docno = request.getDocno();
		return writeMessage(request, docno, null);
	}
	
	/**
	 * 商业银行紧急止付结果信息登记接口<br>
	 * 
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendJjzfControlMessage(Gdjg_RequestJjzfdj request) throws RemoteAccessException {
		logger.info("商业银行紧急止付结果信息登记");
		String docno = request.getDocno();
		return writeMessage(request, docno, null);
	}
	
	/**
	 * 商业银行冻结解冻回执信息登记接口<br>
	 * 
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendDjjdhzMessage(Gdjg_RequestDjjdhz hzdj) throws RemoteAccessException {
		logger.info("商业银行冻结/解冻回执信息登记");
		String content = hzdj.getDocno();
		return writeMessage(hzdj, content, null);
		
	}
	
	/**
	 * 动态查询回执信息登记
	 * 
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendDtcxhzMassage(Gdjg_RequestDtcxhz request) throws RemoteAccessException {
		logger.info("商业银行动态查询回执信息登记");
		String docno = request.getDocno();
		return writeMessage(request, docno, null);
		
	}
	
	/**
	 * 动态查询动态数据登记
	 * 
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjg_Response sendDtcxdjMassage(Gdjg_RequestDtcxdj request) throws RemoteAccessException {
		logger.info("商业银行动态查询动态数据登记");
		String docno = request.getDocno();
		String helper = request.getHelper(); //此字段仅用于辅助动态查询数据写入本地存档
		return writeMessage(request, docno, helper);
	}
	
	/**
	 * 一般登记接口处理
	 * 
	 * @param request
	 * @param helper
	 * @return
	 * @throws RemoteAccessException
	 */
	private Gdjg_Response writeMessage(Gdjg_Request request, String docno, String helper) throws RemoteAccessException {
		//调用处未设置登录名和密码，此处添加
		request.setUsername(ServerEnvironment.getStringValue(GdjgKeys.LOGIN_USERNAME));
		request.setPassword(ServerEnvironment.getStringValue(GdjgKeys.LOGIN_PASSWORD));
		
		String xml_content = null;
		String responseXML = null;
		Gdjg_Response response = null;
		try {
			//A.使用JiBX绑定文件生成XML报文
			xml_content = CommonUtils.marshallContext(request);
			
			StringBuffer msg = new StringBuffer();
			msg.append("<?xml version=\"1.0\" encoding=\"GBK\"?>"); // Unicode
			msg.append(xml_content);
			xml_content = msg.toString();
			
			//本地保存
			String xml_content_copy = xml_content;
			String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
			String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, GdjgKeys.FILE_DIRECTORY);
			String path = rootpath + xmlpath + File.separator + docno;
			String filename = "";
			logger.info("写入本地：" + path);
			if (request.getContent().equals("YHCKDJ")) {
				filename = "Gdjg_RequestCkdj.xml";
			} else if (request.getContent().equals("YHJRCPDJ")) {
				filename = "Gdjg_RequestJrcpdj.xml";
			} else if (request.getContent().equals("YHCKDJDJ")) {
				filename = "Gdjg_RequestCkdjdj.xml";
			} else if (request.getContent().equals("YHCKJDDJ")) {
				filename = "Gdjg_RequestCkjddj.xml";
			} else if (request.getContent().equals("YHJJZFDJ")) {
				filename = "Gdjg_Requestjjzfdj.xml";
			} else if (request.getContent().equals("YHDJJDHZ")) {
				filename = "Gdjg_Requestdjjdhzdj.xml";
			} else if (request.getContent().equals("YHDTCXHZ")) {
				filename = "Gdjg_Requestdtcxhzdj.xml";
			} else if (request.getContent().equals("YHDTXXDJ")) {
				path = path + File.separator + StringUtils.substringBefore(helper, "_");
				filename = "Gdjg_Requestdtcxxxdj_" + StringUtils.substringAfter(helper, "_") +".xml";
			}
			CommonUtils.writeBinaryFile(xml_content_copy.getBytes("GBK"), path, filename);
			
			//B.http发送登记信息获取响应报文
			OuterPollingTask21 outer = new OuterPollingTask21();
			responseXML = outer.processResponseFile(xml_content);
			//C.使用JiBX绑定文件解析响应报文
			response = (Gdjg_Response) CommonUtils.unmarshallContext(Gdjg_Response.class, responseXML);
			logger.info("登记结束==http发送：\n" + CommonUtils.localLineSeparator());//+ xml_content
		} catch (JiBXException e) {
			logger.error("http响应信息：\n" + CommonUtils.localLineSeparator() + responseXML);
			throw new RemoteAccessException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("TASK3:http数据发送异常==http发送信息：\n" + xml_content);
			e.printStackTrace();
		}
		return response;
	}
	
	private String getFeedbackMessage(String content, String docno) {
		StringBuffer message = new StringBuffer();
		message.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		message.append("<RESPONSE>");
		message.append("<COMMAND TYPE=\"" + GdjgConstants.COMMAND_TYPE_LOGIN + "\">");
		message.append("<USERNAME>" + ServerEnvironment.getStringValue(GdjgKeys.LOGIN_USERNAME) + "</USERNAME>");
		message.append("<PASSWORD>" + ServerEnvironment.getStringValue(GdjgKeys.LOGIN_PASSWORD) + "</PASSWORD>");
		message.append("</COMMAND>");
		message.append("<DATA TYPE=\"ERROR\" CONTENT=\"" + content + "\">");
		message.append("<MESSAGE>").append("协作编号：" + docno + "，未查询到相关信息").append("</MESSAGE>");
		message.append("</DATA>");
		message.append("</RESPONSE>");
		
		return message.toString();
	}
	
	/**
	 * 交易流水文件上传接口<br>
	 * 1.调用交易流水登记接口（ftp上传文件）并生成交易流水文件上传xml报文<br>
	 * 2.调用交易流水ftp文件登记接口（http发送xml，告知已上传文件）
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	private Gdjg_Response writeJylsMessage(List<Gdjg_Request_TransCx> list, List<Br57_cxqq_back> backList) throws RemoteAccessException {
		String requestXML = null;
		String responseXML = null;
		Gdjg_Response response = null;
		try {
			//1.ftp发送交易流水zip，并获取交易流水ftp文件登记上送xml
			requestXML = this.getHttpRequestXml(list, backList);
			logger.info("交易流水信息如下：" + requestXML);
			
			if (!"".equals(requestXML)) {
				//2.http发送流水文件登记信息并获取响应报文
				OuterPollingTask21 outer = new OuterPollingTask21();
				responseXML = outer.processResponseFile(requestXML);
				//3.使用JiBX绑定文件解析响应报文
				response = (Gdjg_Response) CommonUtils.unmarshallContext(Gdjg_Response.class, responseXML);
				logger.info("http发送：\n" + CommonUtils.localLineSeparator() + requestXML + "\n" + "交易流水登记结束 ");
			} else {
				logger.error("TASK3:交易流水数据ftp过程发送异常==交易流水ftp登记信息：\n" + requestXML);
			}
		} catch (JiBXException e) {
			logger.error("http响应信息：\n" + CommonUtils.localLineSeparator() + responseXML);
			throw new RemoteAccessException(e.getMessage(), e);
		}
		
		catch (Exception e) {
			logger.error("TASK3:交易流水登记异常==http发送信息：\n" + requestXML);
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 交易流水登记接口处理（有数据）<br>
	 * 1.生成交易流水数据zip包<br>
	 * 2.ftp发送<br>
	 * 3.生成交易流水ftp文件登记xml
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getHttpRequestXml(List<Gdjg_Request_TransCx> list, List<Br57_cxqq_back> backList) throws Exception {
		String feedback_req_xml = "";
		try {
			int transtotalcount = list.size();//流水总数
			String docno = null;//协作号
			List<String> line_list = null;
			if (list != null && list.size() > 0) {
				docno = list.get(0).getDocno();
				String jyls = null;
				line_list = new ArrayList<String>(list.size() + 1);
				
				//获取第一行
				String user_info = getUserInfo(backList);
				line_list.add(user_info);
				
				//交易流水
				for (int i = 0; i < list.size(); i++) {
					Gdjg_Request_TransCx tran = list.get(i);
					jyls = getTransInfo(tran);
					line_list.add(jyls);
				}
				
				//测试用
				//				String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
				//				String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjgKeys.FILE_DIRECTORY) + File.separator + "temp";
				//				String path = rootpath + xmlpath;
				//				String filename = "Gdjg_RequestJyls.txt";
				//				StringBuffer newjyls = new StringBuffer();
				//				int i = 1;
				//				for (String s : line_list) {
				//					if(i<line_list.size()){
				//					newjyls.append(s).append("\r\n");}
				//					else{
				//						newjyls.append(s);	
				//					}
				//					i++;
				//				}
				
				//				if (newjyls != null)
				//					CommonUtils.writeBinaryFile(newjyls.toString().getBytes("GBK"), path, filename);
				
				list.clear();//清理list，释放资源
				
				//写文件以及ftp发送zip
				feedback_req_xml = this.writeTxtAndSendZip(docno, line_list, transtotalcount);
			}
		} catch (Exception e) {
			logger.error("交易流水转换为zip包过程中发生了异常：\n" + feedback_req_xml);
			e.printStackTrace();
		}
		return feedback_req_xml;
	}
	
	/**
	 * 获取交易流水的txt格式字符串
	 * 
	 * @return
	 */
	private String getTransInfo(Gdjg_Request_TransCx tran) {
		StringBuffer sb = new StringBuffer();
		sb.append(tran.getDocno() + "~|~");
		sb.append(tran.getCaseno() + "~|~");
		sb.append(tran.getUniqueid() + "~|~");
		sb.append(trimToNull(tran.getTransnum()) + "~|~");
		sb.append(notNull(tran.getAccname()) + "~|~");
		sb.append(trimToNull(tran.getAcctype()) + "~|~");
		sb.append(trimToNull(tran.getAccount()) + "~|~");
		sb.append(trimToNull(tran.getCardid()) + "~|~");
		sb.append(trimToNull(tran.getIdtype()) + "~|~");
		sb.append(trimToNull(tran.getId()) + "~|~");
		sb.append(trimToNull(tran.getExchangetype()) + "~|~");
		sb.append(trimToNull(tran.getTranstime()) + "~|~");
		sb.append(trimToNull(tran.getExpense()) + "~|~");
		sb.append(trimToNull(tran.getIncome()) + "~|~");
		sb.append(trimToNull(tran.getBanlance()) + "~|~");
		sb.append(trimToNull(tran.getCurrency()) + "~|~");
		sb.append(notNull(tran.getTranstype()) + "~|~");
		sb.append(trimToNull(tran.getTransaddr()) + "~|~");
		sb.append(trimToNull(tran.getTransaddrno()) + "~|~");
		sb.append(trimToNull(tran.getTranscountry()) + "~|~");
		sb.append(trimToNull(tran.getTransremark()) + "~|~");
		sb.append(trimToNull(tran.getTranstel()) + "~|~");
		sb.append(trimToNull(tran.getTranschannel()) + "~|~");
		sb.append(trimToNull(tran.getTransteller()) + "~|~");
		sb.append(trimToNull(tran.getTranstermip()) + "~|~");
		sb.append(trimToNull(tran.getMatchaccount()) + "~|~");
		sb.append(trimToNull(tran.getMatchaccname()) + "~|~");
		sb.append(trimToNull(tran.getMatchbankno()) + "~|~");
		sb.append(trimToNull(tran.getMatchbankname()) + "~|~");
		sb.append(trimToNull(tran.getMatchidtype()) + "~|~");
		sb.append(trimToNull(tran.getMatchid()) + "~|~");
		sb.append(trimToNull(tran.getLoanflag()) + "~|~");
		sb.append(trimToNull(tran.getTransoutlets()) + "~|~");
		sb.append(trimToNull(tran.getMatchcard()) + "~|~");
		sb.append(trimToNull(tran.getMatchbanlance()) + "~|~");
		sb.append(trimToNull(tran.getMatchaddr()) + "~|~");
		sb.append(trimToNull(tran.getMatchzipcode()) + "~|~");
		sb.append(trimToNull(tran.getMatchtel()) + "~|~");
		sb.append(trimToNull(tran.getLogno()) + "~|~");
		sb.append(trimToNull(tran.getCitationno()) + "~|~");
		sb.append(trimToNull(tran.getVouchertype()) + "~|~");
		sb.append(trimToNull(tran.getVoucher()) + "~|~");
		sb.append(trimToNull(tran.getCashflag()) + "~|~");
		sb.append(trimToNull(tran.getTerm()) + "~|~");
		sb.append(trimToNull("01") + "~|~");//tran.getTandf()
		sb.append(trimToNull(tran.getBusinessesname()) + "~|~");
		sb.append(trimToNull(tran.getBusinessesno()) + "~|~");
		sb.append(trimToNull(tran.getMacaddr()));
		return sb.toString();
	}
	
	/**
	 * 获取第一行的txt格式字符串
	 * 
	 * @return
	 */
	private String getUserInfo(List<Br57_cxqq_back> backList) {
		String queryResult = "成功";
		String reason = "查询成功";
		
		StringBuffer sb = new StringBuffer();
		sb.append(ServerEnvironment.getStringValue(GdjgKeys.LOGIN_USERNAME) + "~|~");//
		sb.append(ServerEnvironment.getStringValue(GdjgKeys.LOGIN_PASSWORD) + "~|~");
		sb.append(GdjgConstants.DATA_CONTENT_LSDJ + "~|~");
		sb.append(Utility.currDateTime14() + "~|~");
		sb.append(queryResult + "~|~");
		sb.append(reason + "~|~");
		sb.append(Utility.currDateTime14());
		//sb.append("~|~");
		return sb.toString();
	}
	
	/**
	 * 交易流水登记接口处理<br>
	 * 生成txt并压缩后sftp发送
	 * 
	 * @param docno 协作编号
	 * @param txt_name txt文件名
	 * @param zip_name_id zip包ID
	 * @param zip_name zip包名称
	 * @param line_ist 数据list
	 * @param transtotalcount 交易记录数
	 * @return
	 * @throws Exception
	 * @date 2016年12月28日 下午8:44:29
	 */
	private String writeTxtAndSendZip(String docno, List<String> line_list, int transtotalcount) throws Exception {
		//生成文件名
		String txt_name = docno + ".txt";//txt就一个 [协作编号_顺序号.xml]
		String zip_name_id = Utility.currDateTime14() + "_" + docno;//yyMMddHHmmss_协作编号
		String zip_name = zip_name_id + ".zip";//zip包名 [yyMMddHHmmss_协作编号.zip]
		
		//交易流水总数
		int count = line_list.size() - 1;
		String count_ls = count + "";
		
		//写入文件
		String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
		String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, GdjgKeys.FILE_DIRECTORY);
		String path = rootpath + xmlpath + File.separator + docno;//文件的绝对路径目录
		
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (String ss : line_list) {
			if (i < line_list.size()) {
				sb.append(ss).append("\r\n");
			} else {
				sb.append(ss);
			}
			i++;
		}
		
		String encStrings = sb.toString();
		final String secretKey1 = ServerEnvironment.getStringValue(GdjgKeys.HTTP_SECRET_KEY);
		byte[] keys1 = EncryptUtils.GetKeyBytes(secretKey1);
		byte[] encode1 = EncryptUtils.encryptMode(keys1, encStrings.getBytes("GBK"));//加密
		CommonUtils.writeBinaryFile(encode1, path, txt_name);
		
		//生成压缩包
		ZipUtils.zip(path + File.separator + zip_name, path + File.separator + txt_name);
		String localFilePath = path + File.separator + zip_name;
		logger.info("zip包已经生成 localFilePath" + localFilePath);
		
		//记录压缩包信息
		Br57_packet packet = new Br57_packet();
		packet.setPacketkey(zip_name_id);
		packet.setQrydt(DtUtils.getNowDate());
		packet.setDocno(docno);
		packet.setSenddate_dt(Utility.currDateTime19());
		packet.setFilename(zip_name);
		packet.setFilepath(xmlpath + File.separator + zip_name);
		packet.setStatus_cd("1");//0:未发送 1:已发送
		packet.setCreate_dt(Utility.currDateTime19());
		packet.setResultstatus("0");//0:待处理 1:成功 2:错误
		packet.setMsg_type_cd("N");//N：正常 R：重发
		
		//ftp发送zip包
		String target_path = ServerEnvironment.getStringValue(GdjgKeys.FTP_SEND_DIR);
		getftpUtils(target_path).uploadFile(new File(localFilePath));
		logger.info("已经ftp发送zip包");
		//压缩包记录入库
		gdjg_mapper.insertBr57_packet(packet);
		//判断是否重发报文
		String isretrans = this.isRetrans(docno);
		
		//拼接交易流水登记xml
		StringBuffer buf = new StringBuffer();
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		buf.append("<RESPONSE>");
		buf.append("<COMMAND TYPE=\"" + GdjgConstants.COMMAND_TYPE_LOGIN + "\">");
		buf.append("<USERNAME>").append(ServerEnvironment.getStringValue(GdjgKeys.LOGIN_USERNAME)).append("</USERNAME>");
		buf.append("<PASSWORD>").append(ServerEnvironment.getStringValue(GdjgKeys.LOGIN_PASSWORD)).append("</PASSWORD>");
		buf.append("</COMMAND>");
		buf.append("<DATA CONTENT=\"" + GdjgConstants.DATA_CONTENT_LSDJ_WJSC + "\">");
		buf.append("<ZIPFILEID>").append(zip_name_id).append("</ZIPFILEID>");//文件唯一标示,直接使用文件 名称(不包括后缀名)
		buf.append("<DOCNO>").append(docno).append("</DOCNO>");//标识出具体那一笔业务
		buf.append("<ZIPFILENAME>").append(zip_name).append("</ZIPFILENAME>");//ZIP文件名称
		buf.append("<TXTTOTALCOUNT>").append("1").append("</TXTTOTALCOUNT>");//TXT总文件数
		buf.append("<TRANSTOTALCOUNT>").append(count_ls).append("</TRANSTOTALCOUNT>");//交易流水总数
		buf.append("<ISRETRANS>").append(isretrans).append("</ISRETRANS>");//1 重传，默认 空。重传时，主要 以协助编号标 识是那一笔数 据，其中 ZIP 文件 ID、ZIP 文件名可和原 来一致，也可不一致。（建议使用新的时间戳生成）
		buf.append("</DATA>");
		buf.append("</RESPONSE>");
		
		return buf.toString();
	}
	
	/**
	 * 获取ftp连接
	 * 
	 * @return
	 */
	private FtpUtils getftpUtils(String target_path) {// 非线程安全
		if (ftpUtils == null) {
			ftpUtils = new FtpUtils();
			ftpUtils.setServer(ServerEnvironment.getStringValue(GdjgKeys.FTP_HOST));
			ftpUtils.setUser(ServerEnvironment.getStringValue(GdjgKeys.FTP_LOGIN_USERNAME));
			ftpUtils.setPassword(ServerEnvironment.getStringValue(GdjgKeys.FTP_LOGIN_PASSWORD));
			ftpUtils.setRemotepath(target_path);
		}
		return ftpUtils;
	}
	
	/**
	 * 是否重传 1：重传，默认 空
	 * 
	 * @return
	 */
	private String isRetrans(String docno) {
		String flag = "";
		int i = gdjg_mapper.selectReTransFlagByDocno(docno);
		if (i > 1) {//该协作编号存在zip包记录数大于1，表明重发过
			flag = "1";
		}
		return flag;
	}
	
	/**
	 * 将null转化为空
	 * 
	 * @return
	 */
	private String trimToNull(String s) {
		if (s == null) {
			s = "";
		}
		return s;
	}
	
	/**
	 * 转换非空字段
	 * 
	 * @return
	 */
	private String notNull(String s) {
		if (s == null || s == null) {
			s = "0";
		}
		return s;
	}
	
}
