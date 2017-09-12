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
import com.citic.server.runtime.Keys;
import com.citic.server.runtime.ServerEnvironment;
import com.citic.server.utils.DtUtils;
import com.citic.server.utils.FtpUtils;
import com.citic.server.utils.SftpUtils;
import com.google.common.collect.Lists;

/**
 * 黑/灰名单轮询（发送）任务
 * 1.获取名单发送配置信息
 * 2.判断是否到了发送时间
 * 3.获取带发送数据，生成发送记录和文件
 * 4.更新名单数据发送状态
 * 5.修改最后发送时间和下次发送时间
 * 
 * @author yinxiong
 * @date 2016年6月19日 上午11:23:08
 */
@Component("suspiciousInnerPollingTask")
public class SuspiciousInnerPollingTask extends AbstractSuspiciousPollingTask {
	public static final Logger logger = LoggerFactory.getLogger(SuspiciousInnerPollingTask.class);
	
	private FtpUtils ftp = new FtpUtils();
	private SftpUtils sftp = new SftpUtils();
	
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
		
		String sendtype = ServerEnvironment.getStringValue(Keys.HX_SEND_TYPE);// 文件发送方式 ftp：ftp发送 sftp：sftp发送 local：只生成本地文件，不发送
		String localpath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND);// 本地增量名单待发送文件存放目录
		String mode = ServerEnvironment.getStringValue(Keys.HX_DATA_TYPE);// 数据方式：定长/分割符
		String separate = ServerEnvironment.getStringValue(Keys.HX_DATA_SEPARATE);// 分割符号
		
		// 1.获取名单发送配置信息 --全量文件在前
		List<Br20_md_info> tasklist = Lists.newArrayList();
		tasklist = br20_md_infoMapper.getTaskInfo();
		// 2.判断是否需要生成文件
		if (tasklist != null && tasklist.size() > 0) {
			logger.info("========名单推送任务］开始执行===========");
			for (Br20_md_info br20_md_info : tasklist) {
				String filetype = br20_md_info.getFile_type();
				nextsendtime = br20_md_info.getNext_time();// 下次发送时间
				int amount = Integer.valueOf(br20_md_info.getFrequency());//频率
				int gran = Integer.valueOf(br20_md_info.getUnit());//频率单位
				if (getSendFlag(nextsendtime, amount, gran)) {// 判断是否发送
					br20_md_info.setNext_time(nextsendtime);//跟新下次发送时间
					if ("1".equals(filetype)) {// 增量文件
						br20_md_infoMapper.updateSend_flagByMiddle("9");// 将待发送的数据状态改为中间状态
						ArrayList<Br20_md_info> incrementlist = br20_md_infoMapper.getIncrementSendData("9");// 获取br20_md_data_log中待发送的名单
						if (incrementlist != null && incrementlist.size() > 0) {
							List<String> list = dealDataList(incrementlist, mode, separate);// 数据处理
							String filename = getTxt(localpath, br20_md_info, list);// 生成数据文件和发送记录
							logger.info("==========文件已经生成============");
							if (fileToServer(localpath, filename, ftp, sftp, sendtype)) {// 发送数据文件
								if (fileEndToServer(localpath, filename, ftp, sftp, sendtype)) {// 确认文件
									updateSend_flag("9", nextsendtime);// 更新名单数据发送状态为已发送
									updateSendTime(br20_md_info);// 修改下次发送时间和更新最后发送时间
								} else {
									logger.info("========确认文件发送失败=======");
								}
							} else {
								logger.info("=======数据文件发送失败==========");
							}
						} else {
							logger.info("=============没有待推送的名单数据=============");
						}
					}
				} else {
					logger.info("======未到名单推送时间====下次推送时间：" + nextsendtime);
				}
			}
		} else {
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
		filename = "AFPS.CBOE.CICFQZ.S" + DtUtils.getNowDate("yyMMdd") + ".G" + DtUtils.getNowDate("HHmmss");
		// 向数据库插入一条发送记录
		Br20_md_info md_info = new Br20_md_info();
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
				String encoding = ServerEnvironment.getStringValue(Keys.MD_ENCODING);//获取编码方式
				FileUtils.writeLines(outFile, encoding, list);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(filename + "===========文件写入失败！=========");
		}
		return filename;
	}
	
	/**
	 * 发送数据文件
	 * 
	 * @param localpath 数据文件本地生成路径
	 * @param srcName 数据文件名
	 * @param ftp
	 * @param sftp
	 * @param sendtype 数据发送方式 ftp／sftp／local
	 * @return
	 */
	public boolean fileToServer(String localpath, String srcName, FtpUtils ftp, SftpUtils sftp, String sendtype) {
		boolean isSucc = false;
		try {
			if (StringUtils.equals("ftp", sendtype)) {
				isSucc = ftpToServer(localpath, srcName, ftp);
			} else if (StringUtils.equals("sftp", sendtype)) {
				isSucc = sftpToServer(localpath, srcName, sftp);
			} else {//只生成本地文件
				isSucc = true;
			}
		} catch (Exception e) {
			logger.info("=======文件发送到服务器失败=========");
			e.printStackTrace();
		}
		return isSucc;
	}
	
	/**
	 * 生成.end文件并发送
	 * 
	 * @param localpath 数据文件本地生成路径
	 * @param srcName 数据文件名
	 * @param ftp
	 * @param sftp
	 * @param sendtype 数据发送方式
	 * @return
	 */
	public boolean fileEndToServer(String localpath, String srcName, FtpUtils ftp, SftpUtils sftp, String sendtype) {
		boolean isSucc = false;
		try {
			String srcPath = ServerEnvironment.getStringValue(Keys.FILE_PATH_INCREMENT_SEND); //源系统地址目录		
			String filename = srcName + ".end";
			String oneline = srcPath + "\n";//源系统文件路径
			String twoline = srcName + "\n";//数据文件名称
			String threeline = DtUtils.getNowTime();//时间［19位］
			
			String encoding = ServerEnvironment.getStringValue(Keys.MD_ENCODING);//获取编码方式
			FileUtils.writeStringToFile(new File(srcPath + File.separator + filename), oneline + twoline + threeline, encoding);
			if (StringUtils.equals("ftp", sendtype)) {
				isSucc = ftpToServer(localpath, filename, ftp);
			} else if (StringUtils.equals("sftp", sendtype)) {
				isSucc = sftpToServer(localpath, filename, sftp);
			} else {//只生成本地文件
				isSucc = true;
			}
		} catch (Exception e) {
			logger.info("=========确认文件发送到服务器失败===========");
			e.printStackTrace();
		}
		
		return isSucc;
	}
	
	@Override
	public boolean fileToServer(String srcName) {
		return false;
	}
	
	@Override
	public boolean fileEndToServer(String srcName) {
		return false;
	}
	
	@Override
	public IPollingTask initPollingTask() throws Exception {
		return this;
	}
}
