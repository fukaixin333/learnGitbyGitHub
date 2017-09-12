package com.citic.server.gdjg.outer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citic.server.basic.AbstractPollingTask;
import com.citic.server.basic.IPollingTask;
import com.citic.server.gdjg.GdjgConstants;
import com.citic.server.gdjg.domain.Br57_packet;
import com.citic.server.gdjg.domain.response.Gdjg_ResponseLsdj_wjjg_zipfileinfo;
import com.citic.server.gdjg.mapper.Gdjg_outerPollingMapper;
import com.citic.server.runtime.EncryptUtils;
import com.citic.server.runtime.GdjgKeys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.runtime.Utility;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC20_task_msg;
import com.citic.server.service.domain.MC21_task;

/**
 * 广东省检察院，广东省公安
 * 外联轮询
 * 
 * @author liuxuanfei
 * @date 2017年5月22日 上午10:18:41
 */

public abstract class AbstractOuterPollingTaskGdjg extends AbstractPollingTask {
	private final Logger logger = LoggerFactory.getLogger(AbstractOuterPollingTaskGdjg.class);
	//连接主机超时(单位毫秒)
	private final int connect_timeout = 30 * 1000;
	//从主机读取数据超时(单位毫秒)
	private final int read_timeout = 30 * 1000;
	
	@Autowired
	private Gdjg_outerPollingMapper gdjg_mapper;
	
	// 加密密钥（24字节密钥）
	final String secretKey = ServerEnvironment.getStringValue(GdjgKeys.HTTP_SECRET_KEY);
	
	/**
	 * 初始化参数
	 */
	@Override
	public IPollingTask initPollingTask() throws Exception {
		return super.initPollingTask();
		
	}
	
	/**
	 * 处理接收到的请求文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String processResponseFile(String requestXml) throws Exception {
		String xmlResponse = null;
		
		//1.生成查询格式的xml并使用3DES对报文进行加密 
		byte[] keys = EncryptUtils.GetKeyBytes(secretKey);
		byte[] xmldatas = requestXml.getBytes("GB2312");
		byte[] encode = EncryptUtils.encryptMode(keys, xmldatas);//加密
		HttpURLConnection conn = null;
		try {
			//2.根据接口访问方式,调用接口,发送消息并等待响应
			URL url = new URL(getRemoteURL());
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(connect_timeout);//连接主机超时
			conn.setReadTimeout(read_timeout); //从主机读取数据超时
			conn.setDoOutput(true);//设置是否向httpUrlConnection输出，默认情况下是false
			conn.setDoInput(true);//设置是否从httpUrlConnection读入，默认情况下是true
			conn.setUseCaches(false);
			//设定传送的内容类型是可序列化的java对象，避免可能的java.io.EOFException异常
			conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
			//设定请求的方式为POST
			conn.setRequestMethod("POST");
			conn.connect();
			logger.info("=============================================");
			logger.info("==== 广东省检察院查冻平台正尝试建立连接... ====");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		GZIPOutputStream gos = null;
		GZIPInputStream gis = null;
		ByteArrayOutputStream baos = null;
		try {
			gos = new GZIPOutputStream(conn.getOutputStream());
			gos.write(encode);
			gos.finish();
			
			int code = conn.getResponseCode();
			String msg = conn.getResponseMessage();
			logger.info("------响应消息：" + code + "," + msg + "-----");
			if (code == 200) {
				gis = new GZIPInputStream(conn.getInputStream());
				baos = new ByteArrayOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while ((len = gis.read(b)) != -1) {
					baos.write(b, 0, len);
				}
				byte[] datas = baos.toByteArray();
				byte[] xmlData = EncryptUtils.decryptMode(keys, datas);
				xmlResponse = new String(xmlData, "GBK");
			}
			
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		} finally {
			try {
				if (gos != null) {
					gos.close();
				}
				if (gis != null) {
					gis.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("==============================================");
		logger.info("接收报文信息为：[{}]", xmlResponse);
		
		return xmlResponse;
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
	protected void insertTask1(String taskType, String code, MC21_task task, String fileName, String subPath, String docno, String wspath) {
		String taskKey = taskType + "_" + docno;
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
		taskmsg.setAttachpath(wspath);
		gdjg_mapper.insertMC20_Task_Fact1(taskFact);
		gdjg_mapper.insertMC20_Task_Msg(taskmsg);
		
		//去重机制
		cacheReceivedTaskKey(taskKey);
	}
	
	/**
	 * 查询/申请 上传参数XML文件
	 * 
	 * @return
	 */
	protected abstract String requestXml(String content);
	
	/**
	 * 本地保存，解析XML文件，并插入task1
	 * 
	 * @return
	 */
	protected abstract void xml_unmarshall(String cx_xml, String type);
	
	/**
	 * 交易流水重发
	 * 
	 * @param zipfileinfo
	 */
	protected void reSendTrans(Gdjg_ResponseLsdj_wjjg_zipfileinfo zipfileinfo) {
		// 根据docno，更新br52_packet的状态
		Br57_packet br57_packet = new Br57_packet();
		br57_packet.setDocno(zipfileinfo.getDocno());
		br57_packet.setMsg_type_cd("R");// N：正常 R：重发
		br57_packet.setResultstatus(zipfileinfo.getResultstatus()); // 0: 待处理 1: 成功 2: 错误
		br57_packet.setResultinfo(zipfileinfo.getResultinfo());
		gdjg_mapper.updateBr57_packet(br57_packet);
		
		int i = gdjg_mapper.selectReTransFlagByDocno(zipfileinfo.getDocno());// 重传检测，已经重传过就不再重传［根据doc查询，存在2条记录就是已经重传过了］
		if (i < 2) {// 该协作编号存在zip包记录数小于2，表明未重发过
			// =======重置task3任务，重发改docno有关的交易数据＝＝＝＝====
			MC20_Task_Fact1 fact3 = new MC20_Task_Fact1();
			fact3.setBdhm(zipfileinfo.getDocno());
			fact3.setSubTaskID(GdjgConstants.DATA_CONTENT_LS);
			fact3.setTaskType("10");
			fact3.setServerID("");
			gdjg_mapper.deleteMC21_Task3_Status(fact3);// 根据docno删除fact1对应的stuas记录
			gdjg_mapper.updateMC21_Task_Fact3(fact3);// 根据docno[task1中docno唯一]，将fact2的serverid置为''
		}
	}
	
	/**
	 * 交易流水ftp文件登记结果查询报文
	 * 
	 * @param content
	 * @return
	 */
	public String requestMessageBySql() {
		return null;
		
	}
	
	/**
	 * 获取URL
	 * 
	 * @return
	 */
	protected abstract String getRemoteURL();
	
	/**
	 * 用于缓存的任务对象（参考ServerEnvironment中的常量）
	 */
	@Override
	protected abstract String getTaskType();
	
	/**
	 * 轮询时间间隔控制 默认间隔时间不为null，用默认，否则，间隔时间30分钟
	 */
	@Override
	protected String getExecutePeriodExpression() {
		return ServerEnvironment.getStringValue(GdjgKeys.OUTER_POLLING_PERIOD, "30");
	}
	
	/**
	 * 验证是否能获取相应法律文书
	 */
	protected abstract String DocValidate(String docno, String unzipPath);
}
