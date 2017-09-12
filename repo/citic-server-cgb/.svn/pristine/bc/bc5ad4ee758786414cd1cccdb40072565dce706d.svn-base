package com.citic.server.gdjg.outer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Br57_packet;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseCkcx;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseCkdjkz;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseCkjdkz;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseDtcx;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseJjzfkz;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseJrcpcx;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseLscx;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseLsdj_wjjg;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseLsdj_wjjg_zipfileinfo;
import com.citic.server.gdjg.mapper.Gdjg_outerPollingMapper;
import com.citic.server.runtime.GdjgKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.FtpUtils;
import com.citic.server.utils.StrUtils;
import com.citic.server.utils.Zip4jUtils;

/**
 * 广东省检察院
 * 外联轮询
 * 
 * @author liuxuanfei
 * @date 2017年5月22日 上午10:18:41
 */

@Service("outerPollingTask21")
public class OuterPollingTask21 extends AbstractOuterPollingTaskGdjg {
	private final Logger logger = LoggerFactory.getLogger(OuterPollingTask21.class);
	//查询类型
	private final String CKCX = GdjgConstants.DATA_CONTENT_CK;//存款查询
	private final String LSCX = GdjgConstants.DATA_CONTENT_LS;//交易流水查询
	private final String JRCPCX = GdjgConstants.DATA_CONTENT_YHJRCPCX;//金融产品查询
	private final String BXXCX = GdjgConstants.DATA_CONTENT_YHBXXCX;//保险箱查询
	private final String WJJGCX = GdjgConstants.DATA_CONTENT_LSDJ_WJSC_WJJG;//交易流水ftp登记结果查询
	//控制类型
	private final String CXDJ = GdjgConstants.DATA_CONTENT_YHCKDJCX;//存款冻结申请
	private final String CXJD = GdjgConstants.DATA_CONTENT_YHCKJDCX;//存款解冻申请
	private final String JJZF = GdjgConstants.DATA_CONTENT_YHJJZFQQ;//紧急止付申请
	private final String DTCX = GdjgConstants.DATA_CONTENT_YHDTCX;
	
	private final String DLZT = "YES";//登录状态
	private final String BWZT = "NORMAL";//报文状态
	
	@Autowired
	private Gdjg_outerPollingMapper gdjg_mapper;
	private FtpUtils ftpUtils;
	
	/**
	 * 查询/申请 上传参数XML文件
	 * 
	 * @param content
	 * @return
	 */
	@Override
	protected String requestXml(String content) {
		String username = ServerEnvironment.getStringValue(GdjgKeys.LOGIN_USERNAME);
		String password = ServerEnvironment.getStringValue(GdjgKeys.LOGIN_PASSWORD);
		StringBuilder message = new StringBuilder("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		message.append("<ROOT>");
		message.append("<COMMAND TYPE=\"LOGIN\">");
		message.append("<USERNAME>" + username + "</USERNAME>");
		message.append("<PASSWORD>" + password + "</PASSWORD>");
		message.append("</COMMAND>");
		message.append("<DATA CONTENT=\"" + content + "\">");
		message.append("</DATA>");
		message.append("</ROOT>");
		return message.toString();
	}
	
	/**
	 * 交易流水ftp文件登记结果查询报文
	 * 
	 * @param content
	 * @return
	 */
	public String requestMessageBySql() {
		//查询数据库，获取待省纪委处理的zip包list
		Br57_packet br57_packet = new Br57_packet();
		br57_packet.setStatus_cd("1");//已发送
		br57_packet.setResultstatus("0");//待处理
		//状态待处理的zip包
		List<Br57_packet> list = gdjg_mapper.selectBr57_packet(br57_packet);
		StringBuilder message = new StringBuilder();
		if (list != null && list.size() > 0) {
			message.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
			message.append("<ROOT>");
			message.append("<COMMAND TYPE=\"" + GdjgConstants.COMMAND_TYPE_LOGIN + "\">");
			message.append("<USERNAME>" + ServerEnvironment.getStringValue(GdjgKeys.LOGIN_USERNAME) + "</USERNAME>");
			message.append("<PASSWORD>" + ServerEnvironment.getStringValue(GdjgKeys.LOGIN_PASSWORD) + "</PASSWORD>");
			message.append("</COMMAND>");
			message.append("<DATA CONTENT=\"" + GdjgConstants.DATA_CONTENT_LSDJ_WJSC_WJJG + "\">");
			for (Br57_packet packet : list) {
				message.append("<ZIPFILEINFO>");//ZIP 文件信息
				message.append("<ZIPFILEID>").append(packet.getPacketkey()).append("</ZIPFILEID>");//ZIP 文件 ID
				message.append("<DOCNO>").append(packet.getDocno()).append("</DOCNO>");//协作编号
				message.append("</ZIPFILEINFO>");
			}
			message.append("</DATA>");
			message.append("</ROOT>");
		}
		
		return message.toString();
	}
	
	/**
	 * 任务处理逻辑
	 */
	@Override
	public void executeAction() {
		try {
			logger.info("商业银行存款查询开始");
			String ckcx_req_xml = this.requestXml(CKCX);//生成请求报文
			String ckcx_res_xml = processResponseFile(ckcx_req_xml);//http发送后获取查询报文
			if (ckcx_res_xml != null) {
				this.xml_unmarshall(ckcx_res_xml, CKCX);//解析查询报文，向task1插入任务
			}
			logger.info("商业银行存款查询结束");
			
			logger.info("商业银行交易流水查询开始");
			String jylscx_req_xml = this.requestXml(LSCX);
			String jylscx_res_xml = processResponseFile(jylscx_req_xml);
			if (jylscx_res_xml != null) {
				this.xml_unmarshall(jylscx_res_xml, LSCX);
			}
			logger.info("商业银行交易流水查询结束");
			
			logger.info("商业银行金融产品查询开始");
			String jrcpcx_req_xml = this.requestXml(JRCPCX);
			String jrcpcx_res_xml = processResponseFile(jrcpcx_req_xml);
			if (jrcpcx_res_xml != null) {
				this.xml_unmarshall(jrcpcx_res_xml, JRCPCX);
			}
			logger.info("商业银行金融产品查询结束");
			//			
			//			logger.info("商业银行保险箱信息查询开始");
			//			String bxx_req_xml = this.requestXml(BXXCX);
			//			String bxx_res_xml = processResponseFile(bxx_req_xml);
			//			if (bxx_res_xml != null) {
			//				this.xml_unmarshall(bxx_res_xml, BXXCX);
			//			}
			//			logger.info("商业银行保险箱信息查询结束");
			
			logger.info("商业银行存款冻结申请请求查询开始");
			String ckdjkz_req_xml = this.requestXml(CXDJ);
			String ckdjkz_res_xml = processResponseFile(ckdjkz_req_xml);
			if (ckdjkz_res_xml != null) {
				this.xml_unmarshall(ckdjkz_res_xml, CXDJ);
			}
			logger.info("商业银行存款冻结申请请求查询结束");
			logger.info("商业银行存款解冻申请请求查询开始");
			String ckjdkz_req_xml = this.requestXml(CXJD);
			String ckjdkz_res_xml = processResponseFile(ckjdkz_req_xml);
			if (ckjdkz_res_xml != null) {
				this.xml_unmarshall(ckjdkz_res_xml, CXJD);
			}
			logger.info("商业银行存款解冻申请请求查询结束");
			logger.info("商业银行紧急止付申请请求查询开始");
			String jjzfkz_req_xml = this.requestXml(JJZF);
			String jjzfkz_res_xml = processResponseFile(jjzfkz_req_xml);
			if (jjzfkz_res_xml != null) {
				this.xml_unmarshall(jjzfkz_res_xml, JJZF);
			}
			logger.info("商业银行紧急止付申请请求查询结束");
			logger.info("商业银行动态查询申请请求查询开始");
			String dtcxkz_req_xml = this.requestXml(DTCX);
			String dtcxkz_res_xml = processResponseFile(dtcxkz_req_xml);
			if (dtcxkz_res_xml != null) {
				this.xml_unmarshall(dtcxkz_res_xml, DTCX);
			}
			logger.info("商业银行动态查询申请请求查询结束");
			
			logger.info("商业银行交易流水ftp文件登记结果查询开始");
			String jylsdj_wjjg_req_xml = this.requestMessageBySql();
			if (!"".equals(jylsdj_wjjg_req_xml)) {//存在待处理的zip数据包
				String jylsdj_wjjg_res_xml = processResponseFile(jylsdj_wjjg_req_xml);
				if (jylsdj_wjjg_res_xml != null) {
					this.xml_unmarshall(jylsdj_wjjg_res_xml, WJJGCX);
				}
			} else {
				logger.info("没有待处理的交易流水zip数据包");
			}
			logger.info("商业银行交易流水ftp文件登记结果查询结束");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	protected void xml_unmarshall(String cx_xml, String type) {
		String tasktype = this.getTaskType();
		try {
			//1.商业银行存款查询
			if (CKCX.equals(type)) {
				Gdjg_ResponseCkcx ckcx_dto = (Gdjg_ResponseCkcx) CommonUtils.unmarshallContext(Gdjg_ResponseCkcx.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
				if (DLZT.equals(ckcx_dto.getCmdstatus())) {
					if (BWZT.equals(ckcx_dto.getDatatype()) && !StrUtils.isEmpty(ckcx_dto.getDocno())) {
						// a.生成xml附件
						String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
						String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjgKeys.FILE_DIRECTORY) + File.separator + ckcx_dto.getDocno();
						String path = rootpath + xmlpath;
						
						// 测试用
						logger.info("路径检测：" + path);
						
						String filename = type + "_" + ckcx_dto.getDocno() + ".xml";// 类型＋“_”+32位uuid组成文件名
						CommonUtils.writeBinaryFile(cx_xml.getBytes("GBK"), path, filename);
						
						// b.验证文书
						String docno = ckcx_dto.getDocno();
						String wspath = DocValidate(docno, path);
						if (wspath == null) {
							logger.info("存款查询法律文书获取失败");
							return;
						}
						
						// c.附件信息入库并向task1插入一条任务
						String code = GdjgConstants.DATA_CONTENT_CK;
						MC21_task task = getTaskClassDef(code);// 获取缓存中的任务信息
						logger.info("缓存为：" + task);
						this.insertTask1(tasktype, code, task, filename, xmlpath, ckcx_dto.getDocno(), wspath);// 库中存相对路径
					} else {
						String errMsg = ckcx_dto.getErrmessage();
						logger.warn("存款查询未获取到任务,失败原因：" + errMsg);
					}
				} else {
					logger.info("存款查询登录失败");
				}
				//2.商业银行交易流水查询
			} else if (LSCX.equals(type)) {
				Gdjg_ResponseLscx lscx_dto = (Gdjg_ResponseLscx) CommonUtils.unmarshallContext(Gdjg_ResponseLscx.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
				if (DLZT.equals(lscx_dto.getCmdstatus())) {
					if (BWZT.equals(lscx_dto.getDatatype()) && !StrUtils.isEmpty(lscx_dto.getDocno())) {
						//a.生成xml附件 
						String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
						String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjgKeys.FILE_DIRECTORY) + File.separator + lscx_dto.getDocno();
						String path = rootpath + xmlpath;
						
						//测试用
						logger.info("路径检测：" + path);
						String filename = type + "_" + lscx_dto.getDocno() + ".xml";//类型＋“_”+32位uuid组成文件名
						CommonUtils.writeBinaryFile(cx_xml.getBytes("GBK"), path, filename);
						
						// b.验证文书
						String docno = lscx_dto.getDocno();
						String wspath = DocValidate(docno, path);
						if (wspath == null) {
							logger.info("交易流水查询法律文书获取失败");
							return;
						}
						
						//c.附件信息入库并向task1插入一条任务 
						String code = GdjgConstants.DATA_CONTENT_LS;
						MC21_task task = getTaskClassDef(code);//获取缓存中的任务信息
						this.insertTask1(tasktype, code, task, filename, xmlpath, lscx_dto.getDocno(), wspath);//库中存相对路径
					} else {
						String errMsg = lscx_dto.getErrmessage();
						logger.warn("交易流水查询未获取到任务,失败原因：" + errMsg);
					}
				} else {
					logger.info("交易流水查询登录失败");
				}
				//3.金融产品查询
			} else if (JRCPCX.equals(type)) {
				Gdjg_ResponseJrcpcx jrcpcx_dto = (Gdjg_ResponseJrcpcx) CommonUtils.unmarshallContext(Gdjg_ResponseJrcpcx.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
				if (DLZT.equals(jrcpcx_dto.getCmdstatus())) {
					if (BWZT.equals(jrcpcx_dto.getDatatype()) && !StrUtils.isEmpty(jrcpcx_dto.getDocno())) {
						//a.生成xml附件 
						String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
						String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjgKeys.FILE_DIRECTORY) + File.separator + jrcpcx_dto.getDocno();
						String path = rootpath + xmlpath;
						
						//测试用
						logger.info("路径检测：" + path);
						
						String filename = type + "_" + jrcpcx_dto.getDocno() + ".xml";//类型＋“_”+32位uuid组成文件名
						CommonUtils.writeBinaryFile(cx_xml.getBytes("GBK"), path, filename);
						
						//b.附件信息入库并向task1插入一条任务 
						String code = GdjgConstants.DATA_CONTENT_YHJRCPCX;
						MC21_task task = getTaskClassDef(code);//获取缓存中的任务信息
						this.insertTask1(tasktype, code, task, filename, xmlpath, jrcpcx_dto.getDocno(), null);//库中存相对路径 ，金融产品无法律文书
					} else {
						String errMsg = jrcpcx_dto.getErrmessage();
						logger.warn("金融产品查询未获取到任务,失败原因：" + errMsg);
					}
				} else {
					logger.info("金融产品查询登录失败");
				}
				//4.保险箱查询
			}/*
			 * else if (BXXCX.equals(type)) {
			 * Gdjg_ResponseBxxcx bxxcx_dto = (Gdjg_ResponseBxxcx)
			 * CommonUtils.unmarshallContext(Gdjg_ResponseBxxcx.class, cx_xml);//JIBX解析
			 * //登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
			 * if (DLZT.equals(bxxcx_dto.getCmdstatus())) {
			 * if (BWZT.equals(bxxcx_dto.getDatatype()) && !StrUtils.isEmpty(bxxcx_dto.getDocno()))
			 * {
			 * //a.生成xml附件
			 * String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
			 * String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD,
			 * GdjgKeys.FILE_DIRECTORY) + File.separator + bxxcx_dto.getDocno();
			 * String path = rootpath + xmlpath;
			 * //测试用
			 * logger.info("路径检测：" + path);
			 * String filename = type + "_" + bxxcx_dto.getDocno() + ".xml";//类型＋“_”+32位uuid组成文件名
			 * CommonUtils.writeBinaryFile(cx_xml.getBytes("GBK"), path, filename);
			 * // b.验证文书
			 * String docno = bxxcx_dto.getDocno();
			 * String wspath = DocValidate(docno, path);
			 * if (wspath == null) {
			 * logger.info("保险箱查询法律文书获取失败");
			 * return;
			 * }
			 * //b.附件信息入库并向task1插入一条任务
			 * String code = GdjgConstants.DATA_CONTENT_YHBXXCX;
			 * MC21_task task = getTaskClassDef(code);//获取缓存中的任务信息
			 * this.insertTask1(tasktype, code, task, filename, xmlpath,
			 * bxxcx_dto.getDocno(),wspath);//库中存相对路径
			 * } else {
			 * String errMsg = bxxcx_dto.getErrmessage();
			 * logger.warn("保险箱查询未获取到任务,失败原因："+ errMsg);
			 * }
			 * } else {
			 * logger.info("保险箱查询登录失败");
			 * }
			 * //5.交易流水ftp文件登记结果查询
			 * }
			 */
			else if (WJJGCX.equals(type)) {
				Gdjg_ResponseLsdj_wjjg wjjg_dto = (Gdjg_ResponseLsdj_wjjg) CommonUtils.unmarshallContext(Gdjg_ResponseLsdj_wjjg.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且zip文件信息存在
				if (DLZT.equals(wjjg_dto.getCmdstatus())) {
					if (BWZT.equals(wjjg_dto.getDatatype()) && wjjg_dto.getZipfileinfo().size() > 0) {
						for (Gdjg_ResponseLsdj_wjjg_zipfileinfo zipfileinfo : wjjg_dto.getZipfileinfo()) {
							//0:待处理 1:成功 2:错误
							if ("1".equals(zipfileinfo.getResultstatus())) {
								//跟新zip包状态
								Br57_packet br57_packet = new Br57_packet();
								br57_packet.setDocno(zipfileinfo.getDocno());//协作编号 业务唯一标识
								br57_packet.setMsg_type_cd("N");//N：正常 R：重发
								br57_packet.setResultstatus(zipfileinfo.getResultstatus());
								br57_packet.setResultinfo(zipfileinfo.getResultinfo());
								gdjg_mapper.updateBr57_packet(br57_packet);
							} else if ("2".equals(zipfileinfo.getResultstatus())) {//结果状态
								// RESULTSTATUS=2，表示处理ftp数据时有错误，各银行接口需自动再重传一次,保证只会重传一次
								this.reSendTrans(zipfileinfo);
							} else {
								logger.info("交易流水ftp文件登记的zip文件正在处理中。。。。。。");
							}
						}
					} else {
						String errMsg = wjjg_dto.getErrmessage();
						logger.warn("交易流水ftp文件登记结果查询未获取到任务,失败原因：" + errMsg);
					}
				} else {
					logger.info("交易流水ftp文件登记结果查询登录失败");
				}//5.存款冻结
			} else if (CXDJ.equals(type)) {
				Gdjg_ResponseCkdjkz ckdjkz_dto = (Gdjg_ResponseCkdjkz) CommonUtils.unmarshallContext(Gdjg_ResponseCkdjkz.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
				if (DLZT.equals(ckdjkz_dto.getCmdstatus())) {
					if (BWZT.equals(ckdjkz_dto.getDatatype()) && !StrUtils.isEmpty(ckdjkz_dto.getDocno())) {
						//a.生成xml附件 
						String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
						String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjgKeys.FILE_DIRECTORY) + File.separator + ckdjkz_dto.getDocno();
						String path = rootpath + xmlpath;
						
						//测试用
						logger.info("路径检测：" + path);
						
						String filename = type + "_" + ckdjkz_dto.getDocno() + ".xml";//类型＋“_”+32位uuid组成文件名
						CommonUtils.writeBinaryFile(cx_xml.getBytes("GBK"), path, filename);
						
						// b.验证文书
						String docno = ckdjkz_dto.getDocno();
						String wspath = DocValidate(docno, path);
						if (wspath == null) {
							logger.info("存款冻结查询法律文书获取失败");
							return;
						}
						
						//c.附件信息入库并向task1插入一条任务 
						String code = GdjgConstants.DATA_CONTENT_YHCKDJCX;
						MC21_task task = getTaskClassDef(code);//获取缓存中的任务信息
						this.insertTask1(tasktype, code, task, filename, xmlpath, ckdjkz_dto.getDocno(), wspath);//库中存相对路径 
					} else {
						String errMsg = ckdjkz_dto.getErrmessage();
						logger.warn("存款冻结查询未获取到任务,失败原因：" + errMsg);
					}
				} else {
					logger.info("存款冻结查询登录失败");
				}
				//6.存款解冻
			} else if (CXJD.equals(type)) {
				Gdjg_ResponseCkjdkz ckjdkz_dto = (Gdjg_ResponseCkjdkz) CommonUtils.unmarshallContext(Gdjg_ResponseCkjdkz.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
				if (DLZT.equals(ckjdkz_dto.getCmdstatus())) {
					if (BWZT.equals(ckjdkz_dto.getDatatype()) && !StrUtils.isEmpty(ckjdkz_dto.getDocno())) {
						//a.生成xml附件 
						String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
						String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjgKeys.FILE_DIRECTORY) + File.separator + ckjdkz_dto.getDocno();
						String path = rootpath + xmlpath;
						
						//测试用
						logger.info("路径检测：" + path);
						
						String filename = type + "_" + ckjdkz_dto.getDocno() + ".xml";//类型＋“_”+32位uuid组成文件名
						CommonUtils.writeBinaryFile(cx_xml.getBytes("GBK"), path, filename);
						
						// b.验证文书
						String docno = ckjdkz_dto.getDocno();
						String wspath = DocValidate(docno, path);
						if (wspath == null) {
							logger.info("存款解冻查询法律文书获取失败");
							return;
						}
						
						//c.附件信息入库并向task1插入一条任务 
						String code = GdjgConstants.DATA_CONTENT_YHCKJDCX;
						MC21_task task = getTaskClassDef(code);//获取缓存中的任务信息
						this.insertTask1(tasktype, code, task, filename, xmlpath, ckjdkz_dto.getDocno(), wspath);//库中存相对路径 
					} else {
						String errMsg = ckjdkz_dto.getErrmessage();
						logger.warn("存款解冻查询未获取到任务,失败原因：" + errMsg);
					}
				} else {
					logger.info("存款解冻查询登录失败");
				}
				//7.紧急止付
			} else if (JJZF.equals(type)) {
				Gdjg_ResponseJjzfkz jjzfkz_dto = (Gdjg_ResponseJjzfkz) CommonUtils.unmarshallContext(Gdjg_ResponseJjzfkz.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
				if (DLZT.equals(jjzfkz_dto.getCmdstatus())) {
					if (BWZT.equals(jjzfkz_dto.getDatatype()) && !StrUtils.isEmpty(jjzfkz_dto.getDocno())) {
						//a.生成xml附件 
						String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
						String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjgKeys.FILE_DIRECTORY) + File.separator + jjzfkz_dto.getDocno();
						String path = rootpath + xmlpath;
						
						//测试用
						logger.info("路径检测：" + path);
						
						String filename = type + "_" + jjzfkz_dto.getDocno() + ".xml";//类型＋“_”+32位uuid组成文件名
						CommonUtils.writeBinaryFile(cx_xml.getBytes("GBK"), path, filename);
						
						//c.附件信息入库并向task1插入一条任务 
						String code = GdjgConstants.DATA_CONTENT_YHJJZFQQ;
						MC21_task task = getTaskClassDef(code);//获取缓存中的任务信息
						this.insertTask1(tasktype, code, task, filename, xmlpath, jjzfkz_dto.getDocno(), null);//库中存相对路径 
					} else {
						String errMsg = jjzfkz_dto.getErrmessage();
						logger.warn("存款紧急止付未获取到任务,失败原因：" + errMsg);
					}
				} else {
					logger.info("存款紧急止付查询登录失败");
				}
				//8.动态查询
			} else if (DTCX.equals(type)) {
				Gdjg_ResponseDtcx dtcx_dto = (Gdjg_ResponseDtcx) CommonUtils.unmarshallContext(Gdjg_ResponseDtcx.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
				if (DLZT.equals(dtcx_dto.getCmdstatus())) {
					if (BWZT.equals(dtcx_dto.getDatatype()) && !StrUtils.isEmpty(dtcx_dto.getDocno())) {
						//a.生成xml附件 
						String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
						String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjgKeys.FILE_DIRECTORY) + File.separator + dtcx_dto.getDocno();
						String path = rootpath + xmlpath;
						
						//测试用
						logger.info("路径检测：" + path);
						
						String filename = type + "_" + dtcx_dto.getDocno() + ".xml";//类型＋“_”+32位uuid组成文件名
						CommonUtils.writeBinaryFile(cx_xml.getBytes("GBK"), path, filename);
						
						// b.验证文书
						String docno = dtcx_dto.getDocno();
						String wspath = DocValidate(docno, path);
						if (wspath == null) {
							logger.info("动态查询法律文书获取失败");
							return;
						}
						
						//c.附件信息入库并向task1插入一条任务 
						String code = GdjgConstants.DATA_CONTENT_YHDTCX;
						MC21_task task = getTaskClassDef(code);//获取缓存中的任务信息
						this.insertTask1(tasktype, code, task, filename, xmlpath, dtcx_dto.getDocno(), wspath);//库中存相对路径 
					} else {
						String errMsg = dtcx_dto.getErrmessage();
						logger.warn("动态查询未获取到任务,失败原因：" + errMsg);
					}
				} else {
					logger.info("动态查询登录失败");
				}
				
			}
			
		} catch (JiBXException e) {
			logger.error("JIBX解析查询任务出错==任务类型:" + type + "==http响应的xml内容：" + "\r\n" + cx_xml, e);
		} catch (IOException e) {
			logger.error("生成本地xml发生异常==任务类型:" + type + "==http响应的xml内容：" + "\r\n" + cx_xml, e);
		}
		
	}
	
	@Override
	protected String getRemoteURL() {
		return ServerEnvironment.getStringValue(GdjgKeys.HTTP_SERVER_URL);
	}
	
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_GDJCY;
	}
	
	/**
	 * 验证是否能获取相应法律文书
	 * 若成功则返回文书存储地址，失败则返回null
	 */
	@Override
	protected String DocValidate(String docno, String unzipPath) {
		String wspath = null;
		
		//CommonUtils.createRelativePath(Keys.FILE_PATH_ATTACH, GdjgKeys.FILE_DIRECTORY);
		String remoteDownloadPath = ServerEnvironment.getStringValue(GdjgKeys.FTP_RECV_DIR);//ftp文件下载地址
		String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
		String zipFileSavePath = File.separator + "download" + File.separator + ServerEnvironment.getStringValue(GdjgKeys.FILE_DIRECTORY) + File.separator + Utility.currDate8();
		String localDownloadPath = rootpath + zipFileSavePath;//文件下载后保存地址
		
		//创建文件夹
		File dir = new File(localDownloadPath);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		
		logger.info("进行FTP联接：");
		if (ftpUtils == null) {
			ftpUtils = new FtpUtils();
			ftpUtils.setServer(ServerEnvironment.getStringValue(GdjgKeys.FTP_HOST));
			ftpUtils.setUser(ServerEnvironment.getStringValue(GdjgKeys.FTP_LOGIN_USERNAME));
			ftpUtils.setPassword(ServerEnvironment.getStringValue(GdjgKeys.FTP_LOGIN_PASSWORD));
			ftpUtils.setRemotepath(remoteDownloadPath);
			ftpUtils.setLocalpath(localDownloadPath);
		}
		
		logger.info("获取法律文书：");
		List<String> zipFileList = null;
		String newdocno = docno + ".zip";
		try {
			zipFileList = ftpUtils.getFileNameList(remoteDownloadPath);
			for (String zipFileName : zipFileList) {
				// 判断文书是否有对应
				if (!zipFileName.equals(newdocno)) {
					continue;
				} else {
					String zipFilePath = localDownloadPath + File.separator + zipFileName;
					if (new File(zipFilePath).exists()) {
						continue;
					}
					
					ftpUtils.getfile(zipFileName);
					
					logger.info("zipFileName为:" + zipFileName);
					logger.info("unzipPath为:" + unzipPath);
					File directory = new File(unzipPath);
					if (!directory.exists() || !directory.isDirectory()) {
						directory.mkdirs();
					}
					logger.info("解压法律文书：");
					
					Zip4jUtils.extractAllFiles(zipFilePath, unzipPath, null);
					//ZipUtils.unzip(zipFilePath, unzipPath);
					
					//将解压后的文书地址放入数据库
					File[] xmlFiles = directory.listFiles();
					for (File file : xmlFiles) {
						if (file.getName().contains(".pdf")) {
							wspath = unzipPath + File.separator + file.getName();
							logger.info("文书解压后地址为：" + wspath);
						}
					}
				}
			}
		} catch (Exception e1) {
			logger.warn("文书获取失败：" + e1.toString());
		}
		return wspath;
		
	}
}
