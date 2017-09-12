package com.citic.server.dx.inner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.citic.server.basic.IPollingTask;
import com.citic.server.dx.domain.Br20_md_info;
import com.citic.server.runtime.CgbKeys;
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.utils.DtUtils;
import com.google.common.collect.Lists;
import com.ml.bdbm.client.FTSRequest;
import com.ml.bdbm.client.FTSResponse;
import com.ml.bdbm.client.FTSTaskClient;
import com.ml.bdbm.client.TransferFile;

/**
 * 黑/灰名单轮询（发送）任务
 * 1.获取名单发送配置信息
 * 2.判断是否到了发送时间
 * 3.获取带发送数据，生成发送记录和文件
 * 4.更新名单数据发送状态
 * 5.修改最后发送时间和下次发送时间
 * 
 * @author Liu Xuanfei
 * @date 2016年4月18日 上午11:23:08
 */
@Component("suspiciousInnerPollingTask")
public class SuspiciousInnerPollingTask extends AbstractSuspiciousPollingTask {
	public static final Logger logger = LoggerFactory.getLogger(SuspiciousInnerPollingTask.class);
	
	@Override
	public IPollingTask initPollingTask() throws Exception {
		return this;
	}
	
	@Override
	public void execute() {
		try {
			push_file();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件推送入口
	 * 1、处理全量文件
	 * 2、处理增量文件，只能起一个进程
	 * 2.0 更新待发送状态为中间状态
	 * 2.1 查询要处理的增量数据（条件为中间状态）
	 * 2.2 生成文件
	 * 2.2.1 更新中间状态为已发送
	 * 2.3 发送文件（抽象方法），待子类实现
	 * 2.4 写文件发送状态，更新文件发送时间
	 * 
	 * @throws Exception
	 */
	public void push_file() throws Exception {
		String incrementlocalpath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND);//本地增量名单待发送文件存放目录
		String mode = ServerEnvironment.getStringValue(Keys.HX_DATA_TYPE);//数据方式 ：定长/分割符
		String Separate = ServerEnvironment.getStringValue(Keys.HX_DATA_SEPARATE);//分割符号
		
		// 1.获取名单发送配置信息 --全量文件在前
		List<Br20_md_info> tasklist = Lists.newArrayList();
		tasklist = br20_md_infoMapper.getTaskInfo();
		// 2.判断是否需要生成文件
		if (tasklist != null && tasklist.size() > 0) {
			logger.info("=========［名单推送任务］开始执行=========");
			for (Br20_md_info br20_md_info : tasklist) {
				String filetype = br20_md_info.getFile_type();
				nextsendtime = br20_md_info.getNext_time();// 下次发送时间
				int amount = Integer.valueOf(br20_md_info.getFrequency());//频率
				int gran = Integer.valueOf(br20_md_info.getUnit());//频率单位
				if (getSendFlag(nextsendtime, amount, gran)) {// 判断是否发送
					br20_md_info.setNext_time(nextsendtime);//跟新下次发送时间
					if ("1".equals(filetype)) {// 增量文件
						br20_md_infoMapper.updateSend_flagByMiddle("9");//将待发送的数据状态改为中间状态
						ArrayList<Br20_md_info> incrementlist = br20_md_infoMapper.getIncrementSendData("9");// 获取br20_md_data_log中待发送的名单
						if (incrementlist != null && incrementlist.size() > 0) {
							List<String> list = dealDataList(incrementlist, mode, Separate);// 数据处理
							String filename = getTxt(incrementlocalpath, br20_md_info, list);// 生成数据文件和发送记录
							logger.info("===============文件已经生成================");
							if (fileToServer(filename)) {//发送数据文件
								if (fileEndToServer(filename)) {//确认文件
									updateSend_flag("9", nextsendtime);//更新名单数据发送状态为已发送
									updateSendTime(br20_md_info);//修改下次发送时间和更新最后发送时间
								} else {
									logger.info("===============确认文件发送失败=============");
								}
							} else {
								logger.info("==============数据文件发送失败==============");
							}
						}else{
							logger.info("=============没有待推送的名单数据=============");
						}
					}
				}else{
					logger.info("======未到名单推送时间====下次推送时间："+nextsendtime);
				}
			}
		}else{
			logger.info("===========没有名单推送任务==========");
		}
	}
	
	/**
	 * 生成txt文本文件并插入一条发送记录
	 * 
	 * @param localpath 本地存放路劲
	 * @param br20_md_info
	 * @param list 库中数据定长处理后的list
	 * @return
	 */
	@Override
	public String getTxt(String localpath, Br20_md_info br20_md_info, List<String> list) {
		File file = new File(localpath);
		if (!file.exists() || !file.isDirectory()) {// 保证目录存在
			file.mkdirs();
		}
		String filename = "";
		String head = StringUtils.rightPad("H", 1224, " ");//文件头
		String tail = StringUtils.rightPad("T", 1224, " ");//文件尾
		filename = "AFPS.CBOE.CICFQZ.S" + DtUtils.getNowDate("yyMMdd") + ".G" + DtUtils.getNowDate("HHmmss");
		// 向数据库插入一条发送记录
		Br20_md_info md_info = new Br20_md_info();
		// String file_code = codeService.getSequenceValus("seq_br20_md_accept");//取序列
		String file_code = UUID.randomUUID().toString().replaceAll("-", "");// 文件编号
		md_info.setFile_code(file_code);
		md_info.setSyscode(br20_md_info.getSyscode());
		md_info.setFile_name(filename);
		md_info.setFile_type(br20_md_info.getFile_type());
		md_info.setFlag("1");// 发送标志 0:失败 1:成功
		md_info.setSend_time(br20_md_info.getNext_time());
		br20_md_infoMapper.insertBr20_md_send(md_info);
		try {
			File outFile = new File(localpath + File.separator + filename);
			
			if (list != null && list.size() > 0) {
				list.add(0, head);//文件头部
				list.add(tail);//文件尾部
				String encoding = ServerEnvironment.getStringValue(Keys.MD_ENCODING);//获取编码方式
				FileUtils.writeLines(outFile, encoding, list);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(filename + "===================文件写入失败！=====================");
		}
		
		return filename;
	}
	
	/**
	 * 发送数据文件
	 * 
	 * @param srcName 数据文件名
	 * @return
	 */
	@Override
	public boolean fileToServer(String srcName) {
		boolean isSucc = false;
		try {
			String ip = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_IP); //源系统ip
			int port = Integer.parseInt(ServerEnvironment.getStringValue(CgbKeys.WF_SEND_PORT)); //目标端口号
			int timeout = Integer.parseInt(ServerEnvironment.getStringValue(CgbKeys.WF_SEND_TIMEOUT));//超时 单位为秒 实际可改为5-10分钟
			String destPath = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_ADDRESS); //目标地址目录
			String srcPath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND); //源系统地址目录
			
			FTSTaskClient client = new FTSTaskClient(ip, port);
			String taskId = "PEAWZGGP";// taskid从UFS获取
			TransferFile file = new TransferFile(srcName, srcPath, srcName, destPath);
			TransferFile[] fileList = new TransferFile[] {file};
			FTSRequest request = new FTSRequest(taskId, fileList);
			FTSResponse res = client.callTask(request, timeout);
			
			if (StringUtils.equals("0000", res.getErrorCode())) {
				isSucc = true;
				logger.info("====数据文件发送文服成功====代码："+res.getErrorCode()+"==消息："+res.getErrorMessage());
			}	
		} catch (Exception e) {
			logger.info("==========数据文件发送到文服失败=============");
			e.printStackTrace();
		}
		
		return isSucc;
		
	}
	
	/**
	 * 生成.end文件并发送
	 * 
	 * @param srcName 数据文件名
	 */
	@Override
	public boolean fileEndToServer(String srcName) {
		boolean isSucc = false;
		try {
			String ip = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_IP); //源系统ip
			int port = Integer.parseInt(ServerEnvironment.getStringValue(CgbKeys.WF_SEND_PORT)); //目标端口号
			int timeout = Integer.parseInt(ServerEnvironment.getStringValue(CgbKeys.WF_SEND_TIMEOUT));//超时 单位为秒 实际可改为5-10分钟
			String destPath = ServerEnvironment.getStringValue(CgbKeys.WF_SEND_ADDRESS); //目标地址目录
			String srcPath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND); //源系统地址目录		
			String filename = srcName + ".end";
			String oneline = "AGENTDIR=/cdadmin/UFSM/agent/CBOE/save\n";//代理路径
			String twoline = "SYSTEMCOUNTS=001\n";//序列号
			String threeline = "ROUT001=PEAWZGGP;/cdadmin/UFSM/agent/CBOE/save/;CIQFQZ0;";//tasked+路径＋io文件名
			
			String encoding = ServerEnvironment.getStringValue(Keys.MD_ENCODING);//获取编码方式
			FileUtils.writeStringToFile(new File(srcPath + File.separator + filename), oneline + twoline + threeline, encoding);
			
			FTSTaskClient client = new FTSTaskClient(ip, port);
			String taskId = "PEAWZGGP";// taskid从UFS获取
			TransferFile file = new TransferFile(filename, srcPath, filename, destPath);
			TransferFile[] fileList = new TransferFile[] {file};
			FTSRequest request = new FTSRequest(taskId, fileList);
			FTSResponse res = client.callTask(request, timeout);
			
			if (StringUtils.equals("0000", res.getErrorCode())) {
				isSucc = true;
				logger.info("====确认文件发送文服成功====代码："+res.getErrorCode()+"==消息："+res.getErrorMessage() );
			}
		} catch (Exception e) {
			logger.info("================确认文件发送到文服失败==================");
			e.printStackTrace();
		}
		
		return isSucc;
	}
	
}
