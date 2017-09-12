package com.citic.server.gdjc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.gdjc.GdjcConstants;
import com.citic.server.gdjc.domain.Br52_packet;
import com.citic.server.gdjc.domain.Gdjc_Request;
import com.citic.server.gdjc.domain.Gdjc_Response;
import com.citic.server.gdjc.domain.request.Gdjc_RequestCkdj;
import com.citic.server.gdjc.domain.request.Gdjc_RequestDhdj;
import com.citic.server.gdjc.domain.request.Gdjc_RequestGzdj;
import com.citic.server.gdjc.domain.request.Gdjc_RequestLsdj_Trans;
import com.citic.server.gdjc.domain.request.Gdjc_RequestWddj;
import com.citic.server.gdjc.mapper.Gdjc_outerListenerMapper;
import com.citic.server.gdjc.outer.HttpHelp;
import com.citic.server.runtime.GdjcKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.RemoteAccessException;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.DES3;
import com.citic.server.utils.SftpUtils;
import com.citic.server.utils.ZipUtils;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 报文登记（上行）服务
 * 
 * @author yinxiong
 *
 */
@Service("requestMessageServiceGdjc")
public class RequestMessageServiceGdjc {
	private Logger logger = LoggerFactory.getLogger(RequestMessageServiceGdjc.class);
	
	@Autowired
	private HttpHelp http;
	@Autowired
	private Gdjc_outerListenerMapper gdjc_mapper;
	private SftpUtils sftpUtils;
	
	/**
	 * 银行网点登记接口
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjc_Response sendYhwddjMassage(Gdjc_RequestWddj request) throws RemoteAccessException {
		logger.info("银行网点登记");
		return writeMessage(request);
	}
	
	/**
	 * 银行账号规则登记接口
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjc_Response sendYhzhgzdjMassage(Gdjc_RequestGzdj request) throws RemoteAccessException {
		logger.info("银行账号规则登记");
		return writeMessage(request);
	}
	
	/**
	 * 城市代号对照登记接口
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjc_Response sendCsdhdzdjMassage(Gdjc_RequestDhdj request) throws RemoteAccessException {
		logger.info("城市代号对照登记");
		return writeMessage(request);
	}
	
	/**
	 * 商业银行存款登记接口
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjc_Response sendCkdjMassage(Gdjc_RequestCkdj request) throws RemoteAccessException {
		logger.info("商业银行存款登记");
		if(request.getCases()!=null&&request.getCases().size()>0){
			return writeMessage(request);
		}else{//没有查询到记录时，反馈指定报文
			String feedback_req_xml = null;
			String feedback_res_xml = null;
			Gdjc_Response response = null;
			try {
				//1.生成无数据的报文
				feedback_req_xml = this.getFeedbackMessage(GdjcConstants.DATA_CONTENT_CKDJ,request.getDocno());
				//2.http加密压缩发送和解压解密获取反馈
				feedback_res_xml = http.getxmlResponseByContent(feedback_req_xml);
				//3.使用JiBX绑定文件解析响应报文
				response = (Gdjc_Response) CommonUtils.unmarshallContext(Gdjc_Response.class, feedback_res_xml); 
				logger.info("（无数据）存款登记结束==http发送：\n" + CommonUtils.localLineSeparator() + feedback_req_xml);
			} catch (JiBXException e) {
				logger.error("http响应信息:\n"+ CommonUtils.localLineSeparator() +feedback_res_xml);
				throw new RemoteAccessException(e.getMessage(), e);
			}catch (Exception e) {
				logger.error("TASK3:http数据发送异常==http发送信息：\n"+feedback_req_xml);
				e.printStackTrace();
			}
			return response;
		}
		
	}
	
	/**
	 *商业银行交易流水登记接口<br>
	 *包含sftp发送流水zip（即流水登记接口）和http发送文件上传报文（即流水sftp文件登记接口）
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	public Gdjc_Response sendJylsdjMassage(List<Gdjc_RequestLsdj_Trans> list,String docno) throws RemoteAccessException {
		logger.info("商业银行交易流水登记");
		return writeJylsMessage(list,docno);
	}
	

	/**
	 * 一般登记接口处理
	 * 
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	private Gdjc_Response writeMessage(Gdjc_Request request) throws RemoteAccessException {
		//调用处未设置登录名和密码，此处添加
		request.setUsername(ServerEnvironment.getStringValue(GdjcKeys.LOGIN_USERNAME));
		request.setPassword(ServerEnvironment.getStringValue(GdjcKeys.LOGIN_PASSWORD));
		
		String xml_content = null;
		String responseXML = null;
		Gdjc_Response response = null;
		try {
			//A.使用JiBX绑定文件生成XML报文
			xml_content = CommonUtils.marshallContext(request); 
			//B.http发送登记信息获取响应报文
			responseXML = http.getxmlResponseByContent(xml_content);
			//C.使用JiBX绑定文件解析响应报文
			response = (Gdjc_Response) CommonUtils.unmarshallContext(Gdjc_Response.class, responseXML); 
			logger.info("登记结束==http发送：\n" + CommonUtils.localLineSeparator() + xml_content);
		} catch (JiBXException e) {
			logger.error("http响应信息：\n"+ CommonUtils.localLineSeparator() +responseXML);
			throw new RemoteAccessException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("TASK3:http数据发送异常==http发送信息：\n"+xml_content,e);
		}
		return response;
	}
	
	/**
	 * 交易流水文件上传接口<br>
	 * 1.调用交易流水登记接口（sftp上传文件）并生成交易流水文件上传xml报文<br>
	 * 2.调用交易流水sftp文件登记接口（http发送xml，告知已上传文件）
	 * @param request
	 * @return
	 * @throws RemoteAccessException
	 */
	private Gdjc_Response writeJylsMessage(List<Gdjc_RequestLsdj_Trans> list,String docno) throws RemoteAccessException {
		String requestXML = null;
		String responseXML = null;
		Gdjc_Response response = null;
		try {
			//1.sftp发送交易流水zip，并获取交易流水sftp文件登记上送xml
			 if(list!=null&&list.size()>0){
				 requestXML = this.getHttpRequestXml(list);
			 }else{
				 requestXML = this.getHttpRequestXmlByNull(docno);//无交易流水时
			 }
			 if(!"".equals(requestXML)){
				//2.http发送流水文件登记信息并获取响应报文
				responseXML = http.getxmlResponseByContent(requestXML);
				//3.使用JiBX绑定文件解析响应报文
				response = (Gdjc_Response) CommonUtils.unmarshallContext(Gdjc_Response.class, responseXML); 
				logger.info("http发送：\n" + CommonUtils.localLineSeparator() + requestXML+"\n"+"交易流水登记结束 ");
			 }else{
				logger.error("TASK3:交易流水数据sftp过程发送异常==交易流水sftp登记信息：\n"+requestXML); 
			 }
		} catch (JiBXException e) {
			logger.error("http响应信息：\n"+ CommonUtils.localLineSeparator() +responseXML);
			throw new RemoteAccessException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("TASK3:交易流水登记异常==http发送信息：\n"+requestXML); 
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 交易流水登记接口处理（有数据）<br>
	 * 1.生成交易流水数据zip包<br>
	 * 2.sftp发送<br>
	 * 3.生成交易流水sftp文件登记xml
	 * @return
	 * @throws Exception 
	 */
	private String getHttpRequestXml(List<Gdjc_RequestLsdj_Trans> list) throws Exception{
		//1.遍历list，将每条记录转化为json格式字符串
		//2.将字符串DES3加密，然后BASE64转换，再写入文件
		//3.压缩txt文件，返回绝对路径
		String feedback_req_xml = "";
		try {
			int transtotalcount = list.size();//流水总数
			String docno = null;//协作号
			List<String> line_ist = null;
			if(list!=null&&list.size()>0){
				docno = list.get(0).getDocno();
				String jyls = null;
				byte[] jyls_des3 = null;
				String jyls_des3_base64 = null;
				line_ist = new ArrayList<String>(list.size()+1);
				final byte[] keyBytes = ServerEnvironment.getStringValue(GdjcKeys.HTTP_SECRET_KEY).getBytes(); // 24 字节的密钥
				//获取第一行 用户信息json格式的加密
				String user_info = getUserInfoByJsonFormat();
				jyls_des3 = DES3.encryptMode(keyBytes, user_info.getBytes());// 使用3DES对报文进行加密;
				jyls_des3_base64 = Utility.encodeBase64(jyls_des3);//BSE64转换
				line_ist.add(jyls_des3_base64);
				//交易流水的json格式加密
				Gson gson = getGsonUtils();
				for(int i=0;i<list.size();i++){
					jyls = gson.toJson(list.get(i));
					jyls_des3 = DES3.encryptMode(keyBytes, jyls.getBytes());// 使用3DES对报文进行加密;
					jyls_des3_base64 = Utility.encodeBase64(jyls_des3);//BSE64转换
					line_ist.add(jyls_des3_base64);
				}
				list.clear();//清理list，释放资源
				//写文件以及sfp发送zip
				feedback_req_xml = this.writeTxtAndSendZip(docno,line_ist,transtotalcount);

			}
			
		} catch (Exception e) {
			logger.error("交易流水转换为zip包过程中发生了异常：\n"+feedback_req_xml);
			e.printStackTrace();
		}
		return feedback_req_xml;
	}
	/**
	 * 获取sftp连接
	 * 
	 * @return
	 */
	private SftpUtils getSftpUtils() {// 非线程安全
		if (sftpUtils == null) {
			sftpUtils = new SftpUtils();
			sftpUtils.setHost(ServerEnvironment.getStringValue(GdjcKeys.SFTP_HOST));
			sftpUtils.setUsername(ServerEnvironment.getStringValue(GdjcKeys.SFTP_LOGIN_USERNAME));
			sftpUtils.setPassword(ServerEnvironment.getStringValue(GdjcKeys.SFTP_LOGIN_PASSWORD));
		}
		return sftpUtils;
	}
	
	/**
	 * 定制排除策略的Gson
	 * 
	 * @return
	 */
	private Gson getGsonUtils(){
		 ExclusionStrategy myExclusionStrategy = new ExclusionStrategy() {
		      @Override
		      public boolean shouldSkipField(FieldAttributes fa) {
		        return fa.getName().equals("qrydt");
		      }
		      @Override
		      public boolean shouldSkipClass(Class<?> clazz) {
		        return false;
		      }
		  };
		 Gson gson  = new GsonBuilder().setExclusionStrategies(myExclusionStrategy).create();
		 
		 return gson;
	}
	
	/**
	 * 获取用户信息的json格式字符串
	 * 
	 * @return
	 */
	private String getUserInfoByJsonFormat(){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"USERNAME\":").append("\""+ServerEnvironment.getStringValue(GdjcKeys.LOGIN_USERNAME)+"\",");
		sb.append("\"PASSWORD\":").append("\""+ServerEnvironment.getStringValue(GdjcKeys.LOGIN_PASSWORD)+"\",");
		sb.append("\"CONTENT\":").append("\""+GdjcConstants.DATA_CONTENT_LSDJ+"\"");
		sb.append("}");
		
		return sb.toString();
	}
	
	/**
	 * 是否重传 1：重传，默认 空
	 * @return
	 */
	private String isRetrans(String docno){
         String flag = "";
         int i = gdjc_mapper.selectReTransFlagByDocno(docno);
         if(i>1){//该协作编号存在zip包记录数大于1，表明重发过
        	 flag = "1";
         }
         return flag;
	}
	
	private String getFeedbackMessage(String content,String docno){
		StringBuffer message = new StringBuffer();
		message.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		message.append("<RESULT>");
		message.append("<COMMAND TYPE=\"" + GdjcConstants.COMMAND_TYPE_LOGIN + "\">");
		message.append("<USERNAME>" + ServerEnvironment.getStringValue(GdjcKeys.LOGIN_USERNAME) + "</USERNAME>");
		message.append("<PASSWORD>" + ServerEnvironment.getStringValue(GdjcKeys.LOGIN_PASSWORD) + "</PASSWORD>");
		message.append("</COMMAND>");
		message.append("<DATA CONTENT=\"" +content+ "\">");
		message.append("<DOCNO>").append(docno).append("</DOCNO>");//标识出具体那一笔业务
		message.append("</DATA>");
		message.append("</RESULT>");
		
		return message.toString();
	}
	
	
	/**
	 * 交易流水登记接口处理（无数据）
	 * @param docno
	 * @return
	 * @throws Exception
	 * 
	 * @author yinxiong
	 * @date 2016年12月26日 下午7:36:50
	 */
	private String getHttpRequestXmlByNull(String docno) throws Exception{
		//1.生成只包含登录信息的字符串
		//2.将字符串DES3加密，然后BASE64转换，再写入文件
		String feedback_req_xml = "";
		List<String> line_ist = new ArrayList<String>(1);
		try {
				byte[] jyls_des3 = null;
				String jyls_des3_base64 = null;
				final byte[] keyBytes = ServerEnvironment.getStringValue(GdjcKeys.HTTP_SECRET_KEY).getBytes(); // 24 字节的密钥
				//获取第一行 用户信息json格式的加密
				String user_info = getUserInfoByJsonFormat();
				jyls_des3 = DES3.encryptMode(keyBytes, user_info.getBytes());// 使用3DES对报文进行加密;
				jyls_des3_base64 = Utility.encodeBase64(jyls_des3);//BSE64转换
				line_ist.add(jyls_des3_base64);
				//sftp登记数据并生成报文
			    feedback_req_xml = this.writeTxtAndSendZip(docno,line_ist,0);
			 
		} catch (Exception e) {
			logger.error("交易流水转换为zip包过程中发生了异常：\n"+feedback_req_xml);
			e.printStackTrace();
		}
		return feedback_req_xml;
	}
	
	/**
	 * 交易流水登记接口处理<br>
	 * 生成txt并压缩后sftp发送
	 * @param docno  协作编号
	 * @param txt_name txt文件名
	 * @param zip_name_id zip包ID
	 * @param zip_name   zip包名称
	 * @param line_ist   数据list
	 * @param transtotalcount  交易记录数
	 * @return
	 * @throws Exception
	 * 
	 * @author yinxiong
	 * @date 2016年12月28日 下午8:44:29
	 */
	private String writeTxtAndSendZip(String docno,List<String> line_ist,int transtotalcount) throws Exception{
		//生成文件名
		String txt_name = docno+"_0001.txt";//txt就一个 [协作编号_顺序号.xml]
		String zip_name_id = Utility.currDateTime14()+"_"+docno;//yyMMddHHmmss_协作编号
		String zip_name = zip_name_id+".zip";//zip包名 [yyMMddHHmmss_协作编号.zip]
		//写入文件
		String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
		String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_TEMP, GdjcKeys.FILE_DIRECTORY);
		String path = rootpath+xmlpath;//文件的绝对路径目录
		File file = new File(path+File.separator+txt_name);
		FileUtils.writeLines(file,"UTF-8",line_ist);
		//生成压缩包
		ZipUtils.zip(path+File.separator+zip_name,path+File.separator+txt_name);
		logger.info("zip包已经生成");
		//记录压缩包信息
		Br52_packet  packet = new Br52_packet();
		packet.setPacketkey(zip_name_id);
		packet.setDocno(docno);
		packet.setSenddate_dt(Utility.currDateTime19());
		packet.setFilename(zip_name);
		packet.setFilepath(xmlpath+File.separator+zip_name);
		packet.setStatus_cd("1");//0:未发送 1:已发送
		packet.setCreate_dt(Utility.currDateTime19());
		packet.setResultstatus("0");//0:待处理 1:成功 2:错误
		packet.setMsg_type_cd("N");//N：正常 R：重发
		//sftp发送zip包
		String target_path = ServerEnvironment.getStringValue(GdjcKeys.SFTP_SEND_DIR);
		getSftpUtils().put(path,zip_name, target_path, zip_name);
		logger.info("已经sftp发送zip包");
		//压缩包记录入库
		gdjc_mapper.insertBr52_packet(packet);
		//判断是否重发报文
		String isretrans = this.isRetrans(docno);
		//拼接交易流水登记xml
		StringBuffer buf = new StringBuffer();
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		buf.append("<RESPONSE>");
		buf.append("<COMMAND TYPE=\"").append(GdjcConstants.COMMAND_TYPE_LOGIN).append("\">");
		buf.append("<USERNAME>").append(ServerEnvironment.getStringValue(GdjcKeys.LOGIN_USERNAME)).append("</USERNAME>");
		buf.append("<PASSWORD>").append(ServerEnvironment.getStringValue(GdjcKeys.LOGIN_PASSWORD)).append("</PASSWORD>");
		buf.append("</COMMAND>");
		buf.append("<DATA CONTENT=\"").append(GdjcConstants.DATA_CONTENT_LSDJ_WJSC).append("\">");
		buf.append("<ZIPFILEID>").append(zip_name_id).append("</ZIPFILEID>");//文件唯一标示,直接使用文件 名称(不包括后缀名)
		buf.append("<DOCNO>").append(docno).append("</DOCNO>");//标识出具体那一笔业务
		buf.append("<ZIPFILENAME>").append(zip_name).append("</ZIPFILENAME>");//
		buf.append("<XMLTOTALCOUNT>").append("1").append("</XMLTOTALCOUNT>");//ZIP 文件中 XML 文件个数(txt时只有一个)
		buf.append("<TRANSTOTALCOUNT>").append(transtotalcount).append("</TRANSTOTALCOUNT>");//ZIP 文件中 包括的总交易流水 数,等于每个XML 交易流水数的总和
		buf.append("<XMLFILEINFO>");
		buf.append("<FILEINFO>");
		buf.append("<XMLFILENAME>").append(txt_name).append("</XMLFILENAME>");//各 XML 文件名称
		buf.append("<TRANSCOUNT>").append(transtotalcount).append("</TRANSCOUNT>");//对应每个 XML 中包含 的交易水数
		buf.append("</FILEINFO>");
		buf.append("</XMLFILEINFO>");
		buf.append("<ISRETRANS>").append(isretrans).append("</ISRETRANS>");//1 重传，默认 空。重传时，主要 以协助编号标 识是那一笔数 据，其中 ZIP 文件 ID、ZIP 文件名可和原 来一致，也可不一致。（建议使用新的时间戳生成）
		buf.append("</DATA>");
		buf.append("</RESPONSE>");
		
		return buf.toString();
	}
}
