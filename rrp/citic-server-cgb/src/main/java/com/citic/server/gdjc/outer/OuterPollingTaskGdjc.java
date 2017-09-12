package com.citic.server.gdjc.outer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.gdjc.GdjcConstants;
import com.citic.server.gdjc.domain.Br52_packet;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseCkcx;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseLscx;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseLsdj_wjjg;
import com.citic.server.gdjc.domain.response.Gdjc_ResponseLsdj_wjjg_zipfileinfo;
import com.citic.server.gdjc.mapper.Gdjc_outerListenerMapper;
import com.citic.server.runtime.GdjcKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.utils.CommonUtils;
import com.citic.server.utils.StrUtils;

/**
 * 广东省纪检监察
 * 
 * @author yinxiong
 * @date 2016年8月17日 上午10:20:02
 */
@Component("outerPollingTaskGdjc")
public class OuterPollingTaskGdjc extends AbstractPollingTask {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Gdjc_outerListenerMapper gdjc_mapper;
	@Autowired
	private HttpHelp http;
	private final String CKCX = "ckcx";//存款查询标识
	private final String LSCX = "lscx";//流水查询标识
	private final String WJJGCX = "wjjgcx";//sftp文件登记结果查询标识
	private final String DLZT = "YES";//登录状态
	private final String BWZT = "NORMAL";//报文状态
	/**
	 * 轮询执行入口
	 * 1. 把请求命令按规范封装成 xml 格式；
	 * 2. 对 xml 文件进行3DES加密；
	 * 3. 把 xml 格式数据压缩成 zip数据流；
	 * 4. 通过 http 协议建立与信息共享平台查询交换模块接口服务的连接，打开输入、输出通道；
	 * 5. 把压缩的 xml 格式请求数据写入连接的输出流；
	 * 6. 从连接的输入流读取响应数据；
	 * 7. 对响应数据进行 zip 解压缩；
	 * 8. 对响应的数据进行 3DES 解密处理；
	 * 9. 进行相应业务处理；
	 */
	@Override
	public void executeAction() {
		
		try {
				String ckcx_req_xml = this.requestMessage(GdjcConstants.DATA_CONTENT_CK);//生成请求报文
				logger.info("存款查询开始，CKCX请求报文："+ckcx_req_xml);
				String ckcx_res_xml = http.getxmlResponseByContent(ckcx_req_xml);//http发送后获取查询报文
				if (ckcx_res_xml != null) {
					logger.info("存款查询结束，CKCX响应报文："+ckcx_res_xml);
					this.xml_unmarshall(ckcx_res_xml, CKCX);//解析查询报文，向task1插入任务
				}
				String jylscx_req_xml = this.requestMessage(GdjcConstants.DATA_CONTENT_LS);
				logger.info("交易流水查询开始，JYLSCX请求报文："+jylscx_req_xml);
				String jylscx_res_xml = http.getxmlResponseByContent(jylscx_req_xml);
				if (jylscx_res_xml != null) {
					logger.info("流水查询查询结束，JYLSCX响应报文："+jylscx_res_xml);
					this.xml_unmarshall(jylscx_res_xml, LSCX);
				}
				String jylsdj_wjjg_req_xml = this.requestMessageBySql();
				logger.info("交易流水sftp文件登记结果查询开始，WJJG请求报文："+jylsdj_wjjg_req_xml);
				if(!"".equals(jylsdj_wjjg_req_xml)){//存在待处理的zip数据包
					String jylsdj_wjjg_res_xml = http.getxmlResponseByContent(jylsdj_wjjg_req_xml);
					if (jylsdj_wjjg_res_xml != null) {
						logger.info("交易流水sftp文件登记结果查询结束，WJJG响应报文："+jylsdj_wjjg_res_xml);
						this.xml_unmarshall(jylsdj_wjjg_res_xml, WJJGCX);
					}
				}else{
					logger.info("交易流水sftp文件登记结果查询结束,没有待处理的交易流水zip数据包");
				}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 查询任务解析
	 * 
	 * @param cx_xml
	 * @throws JiBXException
	 */
	private void xml_unmarshall(String cx_xml, String type) {
		try {
			//1.商业银行存款查询
			if (CKCX.equals(type)) {
				Gdjc_ResponseCkcx ckcx_dto = (Gdjc_ResponseCkcx) CommonUtils.unmarshallContext(Gdjc_ResponseCkcx.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
				if (DLZT.equals(ckcx_dto.getCmdstatus())) {
					if (BWZT.equals(ckcx_dto.getDatatype()) && !StrUtils.isEmpty(ckcx_dto.getDocno())) {
						// a.生成xml附件
						String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
						String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjcKeys.FILE_DIRECTORY);
						String path = rootpath + xmlpath;

						// 测试用
						logger.info("路径检测：" + path);

						String filename = type + "_" + ckcx_dto.getDocno() + ".xml";// 类型＋“_”+32位uuid组成文件名
						CommonUtils.writeBinaryFile(cx_xml.getBytes("UTF-8"), path, filename);
						// b.附件信息入库并向task1插入一条任务
						String code = GdjcConstants.DATA_CONTENT_CK;
						MC21_task task = getTaskClassDef(code);// 获取缓存中的任务信息
						this.insertTask1(code, task, filename, xmlpath, ckcx_dto.getDocno());// 库中存相对路径
					} else {
						logger.info("存款查询未获取到任务");
					}
				} else {
					logger.info("存款查询登录失败");
				}
				//2.商业银行交易流水查询
			} else if (LSCX.equals(type)) {
				Gdjc_ResponseLscx lscx_dto = (Gdjc_ResponseLscx) CommonUtils.unmarshallContext(Gdjc_ResponseLscx.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且协作编号存在，就向task1插入任务
				if (DLZT.equals(lscx_dto.getCmdstatus())) {
					if (BWZT.equals(lscx_dto.getDatatype()) && !StrUtils.isEmpty(lscx_dto.getDocno())) {
						//a.生成xml附件 
						String rootpath = ServerEnvironment.getFileRootPath();// KEY_文件根路径
						String xmlpath = CommonUtils.createRelativePath(Keys.FILE_PATH_CARD, GdjcKeys.FILE_DIRECTORY);
						String path = rootpath + xmlpath;
						
						//测试用
						logger.info("路径检测：" + path);
						
						String filename = type + "_"+lscx_dto.getDocno()+".xml" ;//类型＋“_”+32位uuid组成文件名
						CommonUtils.writeBinaryFile(cx_xml.getBytes("UTF-8"), path, filename);
						//b.附件信息入库并向task1插入一条任务 
						String code = GdjcConstants.DATA_CONTENT_LS;
						MC21_task task = getTaskClassDef(code);//获取缓存中的任务信息
						this.insertTask1(code, task, filename, xmlpath, lscx_dto.getDocno());//库中存相对路径
					} else {
						logger.info("交易流水查询未获取到任务");
					}
				} else {
					logger.info("交易流水查询登录失败");
				}
				//3.交易流水sftp文件登记结果查询
			}else if (WJJGCX.equals(type)){
				Gdjc_ResponseLsdj_wjjg wjjg_dto = (Gdjc_ResponseLsdj_wjjg) CommonUtils.unmarshallContext(Gdjc_ResponseLsdj_wjjg.class, cx_xml);//JIBX解析
				//登录成功（STATUS = “YES”）且没有异常（TYPE=“NORMAL”，并且zip文件信息存在
				if (DLZT.equals(wjjg_dto.getCmdstatus())) {
					if (BWZT.equals(wjjg_dto.getDatatype()) && wjjg_dto.getZipfileinfo()!=null&&wjjg_dto.getZipfileinfo().size()>0) {
						for (Gdjc_ResponseLsdj_wjjg_zipfileinfo zipfileinfo : wjjg_dto.getZipfileinfo()) {
							//0:待处理 1:成功 2:错误
							if("1".equals(zipfileinfo.getResultstatus())){
								//跟新zip包状态
								Br52_packet br52_packet = new Br52_packet();
								br52_packet.setDocno(zipfileinfo.getDocno());//协作编号 业务唯一标识
								br52_packet.setMsg_type_cd("N");//N：正常 R：重发
								br52_packet.setResultstatus(zipfileinfo.getResultstatus());
								br52_packet.setResultinfo(zipfileinfo.getResultinfo());
								gdjc_mapper.updateBr52_packet(br52_packet);
							}else if("2".equals(zipfileinfo.getResultstatus())){//结果状态 RESULTSTATUS=2，表示处理sftp数据时有错误，各银行接口需自动再重传一次,保证只会重传一次
								this.reSendTrans(zipfileinfo);
							}else{
								logger.info("交易流水sftp文件登记的zip文件正在处理中。。。。。。");
							}
						}
					} else {
						logger.info("交易流水sftp文件登记结果查询未获取到任务");
					}
				} else {
					logger.info("交易流水sftp文件登记结果查询登录失败");
				}
			}
		} catch (JiBXException e) {
			logger.error("JIBX解析查询任务出错==任务类型:" + type + "==http响应的xml内容："+ "\r\n" +cx_xml,e);
		} catch (IOException e) {
			logger.error("生成本地xml发生异常==任务类型:" + type + "==http响应的xml内容："+ "\r\n" +cx_xml,e);
		} catch (Exception e) {
			logger.error("xml映射发生异常==任务类型:" + type + "==http响应的xml内容："+ "\r\n" +cx_xml,e);
		} 
	}
	
	/**
	 * 交易流水重发
	 * 
	 * @param zipfileinfo
	 */
	private void reSendTrans(Gdjc_ResponseLsdj_wjjg_zipfileinfo zipfileinfo) {
		// 根据docno，更新br52_packet的状态
		Br52_packet br52_packet = new Br52_packet();
		br52_packet.setDocno(zipfileinfo.getDocno());
		br52_packet.setMsg_type_cd("R");// N：正常 R：重发
		br52_packet.setResultstatus(zipfileinfo.getResultstatus()); // 0: 待处理 1: 成功 2: 错误
		br52_packet.setResultinfo(zipfileinfo.getResultinfo());
		gdjc_mapper.updateBr52_packet(br52_packet);

		int i = gdjc_mapper.selectReTransFlagByDocno(zipfileinfo.getDocno());// 重传检测，已经重传过就不再重传［根据doc查询，存在2条记录就是已经重传过了］
		if (i < 2) {// 该协作编号存在zip包记录数小于2，表明未重发过
			// =======重置task3任务，重发改docno有关的交易数据＝＝＝＝====
			MC20_Task_Fact1 fact3 = new MC20_Task_Fact1();
			fact3.setBdhm(zipfileinfo.getDocno());
			fact3.setSubTaskID(GdjcConstants.DATA_CONTENT_LS);
			fact3.setTaskType("10");
			fact3.setServerID("");
			gdjc_mapper.deleteMc21_Task3_Status(fact3);// 根据docno删除fact1对应的stuas记录
			gdjc_mapper.updateMC21_Task_Fact3(fact3);// 根据docno[task1中docno唯一]，将fact2的serverid置为''
		}
	}
	
	/**
	 * task任务插入
	 * 
	 * @param code 代码
	 * @param task 任务信息
	 * @param fileName 文件名
	 * @param path 文件路径
	 * @param docno 协作编号
	 */
	private void insertTask1(String code, MC21_task task, String fileName, String subPath, String docno) {
		
		String taskKey = ServerEnvironment.TASK_TYPE_GDJC + "_" + docno;
		if (isMessageReceived(taskKey)) { // 任务去重
			return;
		}
		
		MC20_Task_Fact1 taskFact = new MC20_Task_Fact1();
		taskFact.setTaskKey(taskKey);
		taskFact.setSubTaskID(code);
		taskFact.setBdhm(docno);
		taskFact.setTaskID(task.getTaskID());
		taskFact.setTaskType(task.getTaskType());
		taskFact.setTaskName(task.getTaskName());
		taskFact.setTaskCMD(task.getTaskCMD());
		taskFact.setIsDYNA(task.getIsDYNA());
		taskFact.setDatatime(Utility.currDateTime19());
		taskFact.setFreq("1");
		taskFact.setTaskObj("100000");
		taskFact.setTGroupID("1");
		
		MC20_task_msg taskmsg = new MC20_task_msg();
		taskmsg.setTaskkey(taskKey);
		taskmsg.setCode(code);
		taskmsg.setBdhm(docno);
		taskmsg.setPacketkey(docno);//没有压缩包，存协作号
		taskmsg.setMsgpath(subPath + File.separator + fileName);
		taskmsg.setMsgname(fileName);
		taskmsg.setCreatedt(Utility.currDateTime19());
		gdjc_mapper.insertMC20_Task_Fact1(taskFact);
		gdjc_mapper.insertMC20_Task_Msg(taskmsg);
		
		//去重机制
		cacheReceivedTaskKey(taskKey);
	}
	
	/**
	 * 根据content获取不同的查询报文
	 * 
	 * @param content
	 * @return
	 */
	public String requestMessage(String content) {
		StringBuilder message = new StringBuilder();
		message.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		message.append("<ROOT>");
		message.append("<COMMAND TYPE=\"" + GdjcConstants.COMMAND_TYPE_LOGIN + "\">");
		message.append("<USERNAME>" + ServerEnvironment.getStringValue(GdjcKeys.LOGIN_USERNAME) + "</USERNAME>");
		message.append("<PASSWORD>" + ServerEnvironment.getStringValue(GdjcKeys.LOGIN_PASSWORD) + "</PASSWORD>");
		message.append("</COMMAND>");
		message.append("<DATA CONTENT=\"" + content + "\"></DATA>");
		message.append("</ROOT>");
		
		return message.toString();
	}
	
	/**
	 * 交易流水sftp文件登记结果查询报文
	 * 
	 * @param content
	 * @return
	 */
	public String requestMessageBySql() {
		//查询数据库，获取待省纪委处理的zip包list
		Br52_packet br52_packet = new Br52_packet();
		br52_packet.setStatus_cd("1");//已发送
		br52_packet.setResultstatus("0");//待处理
		//状态待处理的zip包
		List<Br52_packet> list = gdjc_mapper.selectBr52_packet(br52_packet);
		StringBuilder message = new StringBuilder();
		if(list!=null&&list.size()>0){
			message.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
			message.append("<ROOT>");
			message.append("<COMMAND TYPE=\"" + GdjcConstants.COMMAND_TYPE_LOGIN + "\">");
			message.append("<USERNAME>" + ServerEnvironment.getStringValue(GdjcKeys.LOGIN_USERNAME) + "</USERNAME>");
			message.append("<PASSWORD>" + ServerEnvironment.getStringValue(GdjcKeys.LOGIN_PASSWORD) + "</PASSWORD>");
			message.append("</COMMAND>");
			message.append("<DATA CONTENT=\"" + GdjcConstants.DATA_CONTENT_LSDJ_WJSC_WJJG + "\">");
			for (Br52_packet packet : list) {
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
	 * 获取缓存对象
	 */
	@Override
	protected String getTaskType() {
		return ServerEnvironment.TASK_TYPE_GDJC;
	}
	
	/**
	 * 轮询时间间隔控制 默认间隔时间不为null，用默认，否则，间隔时间30分钟
	 */
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(GdjcKeys.OUTER_POLLING_PERIOD, "30");
	}
	
}
