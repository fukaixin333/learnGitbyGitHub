/**
 * ========================================================
 * Copyright (c) 2014-2016 by CITIC All rights reserved.
 * Created Date : 2016年5月9日
 * Description:
 * =========================================================
 */
package com.citic.server.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SftpUtils {
	private final Logger logger = LoggerFactory.getLogger(SftpUtils.class);
	
	/** 主机名 */
	@Getter
	@Setter
	private String host = "127.0.0.1";
	/** 端口号 */
	@Getter
	@Setter
	private int port = 22;
	/** 用户名 */
	@Getter
	@Setter
	private String username = "";
	
	/** 密码 */
	private String password = "";
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		//如果FTP密码被base64编码过，那么，需要先解码
		//		if(password.lastIndexOf("==")>-1){
		//			password = Base64Utils.decodeBase64(password);
		//		}
		
		if (Base64Utils.isEncryption(password)) {
			password = Base64Utils.unmakeEncryption(password);
		}
		
		this.password = password;
	}
	
	@Getter
	@Setter
	String separatorStr = File.separatorChar + "";
	
	private Session session = null;
	
	private ChannelSftp sftp;
	
	/**
	 * 连接sftp服务器
	 * 
	 * @return
	 * @throws Exception
	 */
	private Session connect() throws Exception {
		JSch jsch = new JSch();
		// jsch.getSession(this.username, this.host, this.port);
		this.session = jsch.getSession(this.username, this.host, this.port);
		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("session is null");
		}
		
		logger.debug("Session created.");
		
		// 设置登陆主机的密码
		session.setPassword(password);// 设置密码
		// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		// 设置登陆超时时间
		session.connect(30000);
		logger.debug("Session connected.");
		
		return session;
	}
	
	private void disConnect() {
		
		try {
			this.session.disconnect();
			
		} catch (Exception e) {
			
		}
		
	}
	
	/**
	 * 文件上传
	 * 
	 * @param instream
	 * @param remoteDir
	 * @param remoteFileName
	 */
	public void put(InputStream instream, String remoteDir, String remoteFileName) {
		Channel channel = null;
		ChannelSftp sftp = null;
		OutputStream outstream = null;
		
		try {
			if (this.session == null || !this.session.isConnected()) {
				connect();
			}
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;
			
			sftp.cd(remoteDir);
			outstream = sftp.put(remoteFileName);
			
			byte b[] = new byte[1024];
			int n;
			while ((n = instream.read(b)) != -1) {
				outstream.write(b, 0, n);
			}
			
			outstream.flush();
			outstream.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
			disConnect();
		}
	}
	
	/**
	 * 上传文件,上传时以临时文件上传，上传成功后显示原文件名
	 * 
	 * @param localDir 本地路径
	 * @param localFileName 文件的原始名
	 * @param remoteDir
	 *        远程文件路径
	 * @param remoteFileName 临时文件名
	 */
	public void putWith(String localDir, String localFileName, String remoteDir, String remoteFileName) {
		Channel channel = null;
		ChannelSftp sftp = null;
		
		try {
			if (this.session == null || !this.session.isConnected()) {
				connect();
			}
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;
			
			String[] dirs = StringUtils.split(remoteDir, separatorStr);
			// 判断是否是以根目录开始
			if (StringUtils.startsWith(remoteDir, separatorStr)) {
				sftp.cd(separatorStr);
			}
			for (String dir : dirs) {
				try {
					sftp.cd(dir);
				} catch (Exception e) {
					sftp.mkdir(dir);
					sftp.cd(dir);
				}
			}
			// logger.debug("当前远程路径为：" + sftp.pwd());
			sftp.lcd(new File(localDir).getAbsolutePath());
			sftp.put(localFileName, remoteFileName, ChannelSftp.OVERWRITE);
			sftp.rename(remoteFileName, localFileName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
			disConnect();
		}
	}
	
	/**
	 * 上传文件,目前不支持中文文件名称
	 * 
	 * @param localDir
	 * @param localFileName
	 * @param remoteDir 远程文件路径，自动创建
	 * @param remoteFileName
	 */
	public void put(String localDir, String localFileName, String remoteDir, String remoteFileName) {
		Channel channel = null;
		ChannelSftp sftp = null;
		
		try {
			if (this.session == null || !this.session.isConnected()) {
				connect();
			}
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;
			
			String[] dirs = StringUtils.split(remoteDir, separatorStr);
			
			//判断是否是以根目录开始
			if (StringUtils.startsWith(remoteDir, separatorStr)) {
				sftp.cd(separatorStr);
			}
			
			for (String dir : dirs) {
				try {
					sftp.cd(dir);
				} catch (Exception e) {
					sftp.mkdir(dir);
					sftp.cd(dir);
				}
				
			}
			
			logger.debug("当前远程路径为：" + sftp.pwd());
			sftp.lcd(localDir);
			sftp.put(localFileName, remoteFileName, ChannelSftp.OVERWRITE);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
			disConnect();
		}
	}
	
	/**
	 * 从FTP服务器上下传多个文件,并根据文件大小检查文件是否完整
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void getfileMoreChk(List<String> filenamelist, String remotepath, String localpath) throws Exception {
		
		if (filenamelist != null && filenamelist.size() > 0) {
			try {
				for (int i = 0; i < filenamelist.size(); i++) {
					this.getfileChk(filenamelist.get(i), remotepath, localpath);
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	/**
	 * 从FTP服务器上下传一个文件, 并根据文件大小检查文件是否完整
	 * 
	 * @param ftpClient
	 * @param filename
	 * @throws Exception
	 */
	public void getfileChk(String filename, String remotepath, String localpath) throws Exception {
		boolean isNotOk = true;
		while (isNotOk) {
			long f1 = getFileSize(remotepath, filename, localpath, filename + ".1");
			Thread.sleep(20 * 1000);//休眠20秒
			long f2 = getFileSize(remotepath, filename, localpath, filename + ".2");
			if (f1 == f2) {
				getFileSize(remotepath, filename, localpath, filename);
				File f = new File(localpath + "/" + filename + ".1");
				if (f.exists()) {
					f.delete();
				}
				f = new File(localpath + "/" + filename + ".2");
				if (f.exists()) {
					f.delete();
				}
				isNotOk = false;
			} else {
				Thread.sleep(20 * 1000);//休眠20秒
			}
		}
		
	}
	
	public long getFileSize(String remotepath, String serverFileName, String localpath, String newFilename) {
		get(remotepath, serverFileName, localpath, newFilename);
		File f = new File(localpath + "/" + newFilename);
		return f.length();
	}
	
	/**
	 * 下载指定文件,目前不支持中文文件名称
	 * 
	 * @param serverDir
	 * @param serverFileName
	 * @param localDir 本地文件路径，必须存在
	 * @param localFileName
	 */
	public void get(String serverDir, String serverFileName, String localDir, String localFileName) {
		try {
			get(serverDir, serverFileName, localDir, localFileName, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public void get(String remotePath, String remoteFileName, String localPath, String localFileName, String suffix) throws Exception {
		Channel channel = null;
		ChannelSftp sftp = null;
		try {
			if (this.session == null || !this.session.isConnected()) {
				connect();
			}
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;
			// 
			sftp.cd(remotePath);
			// 判断是否是以根目录开始
			if (StringUtils.startsWith(remotePath, separatorStr)) {
				sftp.cd(separatorStr);
			}
			// 创建本地目录
			File localPathDirectory = new File(localPath);
			if (!localPathDirectory.exists() || !localPathDirectory.isDirectory()) {
				localPathDirectory.mkdirs();
			}
			// 打开本地目录
			sftp.lcd(localPathDirectory.getAbsolutePath());
			// 下载远程文件到本地目录
			sftp.get(remoteFileName, localFileName);
			
			// 更改远程文件名后缀
			if (suffix != null && suffix.length() > 0) {
				sftp.rename(remoteFileName, remoteFileName + suffix);
			}
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
			disConnect();
		}
	}
	
	/**
	 * 批量下载文件,下载后将服务器上的文件名加上.done后缀
	 * 
	 * @param serverDir
	 * @param serverFileName
	 * @param localDir
	 * @return
	 */
	public ArrayList<String> getFileMore(String serverDir, ArrayList<String> serverFileName, String localDir) {
		ArrayList<String> fileNameList = new ArrayList<String>();
		Channel channel = null;
		ChannelSftp sftp = null;
		
		try {
			if (this.session == null || !this.session.isConnected()) {
				connect();
			}
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;
			
			sftp.cd(serverDir);
			// 判断是否是以根目录开始
			if (StringUtils.startsWith(serverDir, separatorStr)) {
				sftp.cd(separatorStr);
			}
			sftp.lcd(localDir);
			if (serverFileName != null && serverFileName.size() > 0) {
				for (String fileName : serverFileName) {
					sftp.get(fileName, fileName);//下载后的文件名称和服务器上的一致
					sftp.rename(fileName, fileName + ".done");
					fileNameList.add(fileName);//记录下载并改名成功的文件名
				}
			} else {
				logger.info("=====要下载的文件列表为空=====");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
			disConnect();
		}
		return fileNameList;
	}
	
	/**
	 * 下载文件，并把服务器上的文件名修改成文件名+.done 后缀的文件
	 * 
	 * @param serverDir
	 * @param serverFileName
	 * @param localDir
	 * @param localFileName
	 */
	public void getWithFlag(String serverDir, String serverFileName, String localDir, String localFileName) {
		Channel channel = null;
		ChannelSftp sftp = null;
		
		try {
			if (this.session == null || !this.session.isConnected()) {
				connect();
			}
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;
			
			sftp.cd(serverDir);
			// 判断是否是以根目录开始
			if (StringUtils.startsWith(serverDir, separatorStr)) {
				sftp.cd(separatorStr);
			}
			sftp.lcd(localDir);
			sftp.get(serverFileName, localFileName);
			sftp.rename(serverFileName, serverFileName + ".done");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
			disConnect();
		}
	}
	
	/**
	 * 删除文件
	 * 
	 * @param directory
	 *        要删除文件所在目录
	 * @param deleteFile
	 *        要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile) {
		Channel channel = null;
		ChannelSftp sftp = null;
		try {
			if (this.session == null || !this.session.isConnected()) {
				connect();
			}
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;
			
			try {
				sftp.cd(directory);
			} catch (Exception e) {
				sftp.mkdir(directory);
				sftp.cd(directory);
			}
			
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
			disConnect();
		}
	}
	
	/**
	 * 获取指定目录下的文件
	 * 
	 * @param directory
	 * @return
	 * @throws SftpException
	 */
	public ArrayList<String> list(String directory) throws SftpException {
		Channel channel = null;
		sftp = null;
		Vector<?> vv = null;
		ArrayList<String> fileList = Lists.newArrayList();
		
		try {
			if (this.session == null || !this.session.isConnected()) {
				connect();
			}
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;
			// logger.info("directory:" + directory);
			vv = sftp.ls(directory);
			if (vv != null) {
				for (int ii = 0; ii < vv.size(); ii++) {
					LsEntry entry = (LsEntry) vv.elementAt(ii);
					SftpATTRS attrs = entry.getAttrs();
					//if (attrs.isReg()) { // Regular: 正规档案[-]
					if (!attrs.isDir()) {
						fileList.add(entry.getFilename());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
			disConnect();
		}
		
		return fileList;
	}
	
	/**
	 * 删除远程服务器中的文件
	 * 
	 * @param filepath 文件目录
	 * @param filename 文件名
	 * @throws Exception
	 * @author Liu Xuanfei
	 * @date 2016年10月11日 下午4:44:58
	 */
	public void removeFile(String filepath, String filename) throws Exception {
		Channel channel = null;
		ChannelSftp sftp = null;
		try {
			if (this.session == null || !this.session.isConnected()) {
				connect();
			}
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			sftp = (ChannelSftp) channel;
			sftp.cd(filepath);
			sftp.rm(filename);
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
			disConnect();
		}
	}
}
