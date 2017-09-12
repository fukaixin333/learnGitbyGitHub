package com.citic.server.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 考虑到双机热备，目前本工具支持多个服务器，但要求各服务器除IP地址不同外，其他信息必须完全相同
 * 配置server地址（IP地址），直接配置多个ip，使用“,”分割，系统将根据顺序，查找可以使用的服务器
 * 
 * @author hongcheng
 */

public class FtpUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);
	
	String server = ""; // FTP服务器的IP地址
	
	String user = ""; // 登录FTP服务器的用户名
	
	String password = ""; // 登录FTP服务器的用户名的口令
	
	String remotepath = ""; // FTP服务器上的路径
	
	String localpath = ""; // 本地路径
	
	// String filename   = ""; // 文件名
	
	/**
	 * 获取一个可使用的ftp客户端
	 * 
	 * @return
	 * @throws Exception
	 */
	private FTPClient getFtpClient() throws Exception {
		FTPClient ftpClient = null;
		String[] singleServers = StringUtils.split(server, ",");
		for (int i = 0; i < singleServers.length; i++) {
			try {
				ftpClient = getSingleFtpClient(singleServers[i]);
				if (ftpClient != null) {
					logger.info("login to ftp server '" + singleServers[i] + "'");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (ftpClient == null) {
			throw new IOException("Can't login to server '" + server + "'");
		}
		return ftpClient;
	}
	
	/**
	 * 登录一个指定地址的服务器
	 * 
	 * @param singleServer
	 * @return
	 * @throws Exception
	 */
	private FTPClient getSingleFtpClient(String singleServer) throws Exception {
		
		FTPClient ftpClient = new FTPClient();
		singleServer = singleServer.toLowerCase();
		int port = 21;
		if (singleServer.startsWith("ftp://")) {
			singleServer = singleServer.replace("ftp://", "");
		}
		if (singleServer.indexOf(":") > -1) {
			String[] s = singleServer.split(":");
			singleServer = s[0];
			if (s.length > 2) {
				port = new Integer(s[1]);
			}
		}
		ftpClient.setControlEncoding("GBK");
		ftpClient.connect(singleServer, port);
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			closeFtpClient(ftpClient);
			logger.warn("Can't connect to server '" + port + "'");
			throw new IOException("Can't connect to server '" + port + "'");
		}
		
		if (!ftpClient.login(this.user, this.password)) {
			closeFtpClient(ftpClient);
			logger.warn("Can't login to server '" + singleServer + "'");
			throw new IOException("Can't login to server '" + singleServer + "'");
		}
		
		// Use passive mode to pass firewalls.
		ftpClient.enterLocalPassiveMode();
		
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		ftpClient.setBufferSize(1024 * 1024);
		ftpClient.setDefaultTimeout(30 * 60 * 1000);
		
		// 设置远程目录
		if (StringUtils.isNotEmpty(remotepath)) {
			ftpClient.changeWorkingDirectory(remotepath);
		}
		
		return ftpClient;
	}
	
	// 取得服务器端文件列表 filename - filesize
	public HashMap getRemoteFileList() {
		HashMap map = new HashMap();
		
		FTPClient ftpClient = null;
		try {
			ftpClient = this.getFtpClient();
			ftpClient.enterLocalPassiveMode();
			FTPFile[] ftpFiles = ftpClient.listFiles();
			int size = (ftpFiles == null) ? 0 : ftpFiles.length;
			for (int i = 0; i < size; i++) {
				FTPFile ftpFile = ftpFiles[i];
				if (ftpFile.isDirectory()) {
					continue;
				}
				map.put(ftpFile.getName(), ftpFile.getSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeFtpClient(ftpClient);
		}
		
		return map;
		
	}
	
	public HashMap getRemoteFileSize(ArrayList _filenameList) throws Exception {
		
		HashMap filenameMap = new HashMap();
		
		// 创建FtpClient对象
		FTPClient ftpClient = this.getFtpClient();
		
		try {
			
			Iterator iter = _filenameList.iterator();
			while (iter.hasNext()) {
				String filename = (String) iter.next();
				long filesize = -1;
				
				filesize = this.getFileSize(ftpClient, filename);
				
				logger.debug(filename + "===" + filesize);
				
				filenameMap.put(filename, filesize);
				
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			// 退出FTP服务器
			this.closeFtpClient(ftpClient);
		}
		
		return filenameMap;
	}
	
	// 下载FTP服务器上某个目录下的所有文件到本地目录
	public void ftpdatafileList() throws Exception {
		List list1 = new ArrayList();
		
		// 创建FtpClient对象
		FTPClient ftpClient = this.getFtpClient();
		ftpClient.changeWorkingDirectory(remotepath);//转移到FTP服务器目录    
		FTPFile[] fs = ftpClient.listFiles();
		for (FTPFile ff : fs) {
			this.getfile(ftpClient, ff.getName());
		}
		
	}
	
	//  从FTP服务器上下传一个文件
	public void getfile(String filename) throws Exception {
		
		FTPClient ftpClient = this.getFtpClient();
		
		try {
			this.getfile(ftpClient, filename);
		} catch (Exception e) {
			throw e;
		} finally {
			this.closeFtpClient(ftpClient);
		}
		
	}
	
	/**
	 * 从FTP服务器上下传多个文件,并根据文件大小检查文件是否完整
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void getfileMoreChk(List<String> filenamelist) throws Exception {
		
		if (filenamelist != null && filenamelist.size() > 0) {
			FTPClient ftpClient = this.getFtpClient();
			try {
				for (int i = 0; i < filenamelist.size(); i++) {
					this.getfileChk(ftpClient, filenamelist.get(i));
				}
			} catch (Exception e) {
				throw e;
			} finally {
				this.closeFtpClient(ftpClient);
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
	public void getfileChk(FTPClient ftpClient, String filename) throws Exception {
		boolean isNotOk = true;
		long fileSizeBef = getFileSize(ftpClient, filename);
		while (isNotOk) {
			getfile(ftpClient, filename);
			long fileSizeAft = getFileSize(ftpClient, filename);
			if (fileSizeAft == fileSizeBef) {
				isNotOk = false;
			} else {
				Thread.sleep(20 * 1000);//休眠20秒
			}
		}
		
	}
	
	/**
	 * 从FTP服务器上下传多个文件
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void getfileMore(List<String> filenamelist) throws Exception {
		
		if (filenamelist != null && filenamelist.size() > 0) {
			FTPClient ftpClient = this.getFtpClient();
			try {
				for (int i = 0; i < filenamelist.size(); i++) {
					this.getfile(ftpClient, filenamelist.get(i));
				}
			} catch (Exception e) {
				throw e;
			} finally {
				this.closeFtpClient(ftpClient);
			}
		}
	}
	
	//  从FTP服务器上下传一个文件
	public void getfile(FTPClient ftpClient, String filename) throws Exception {
		
		OutputStream out = null;
		File file = new File(localpath);
		if (!file.exists() || !file.isDirectory()) {//保证目录存在
			file.mkdirs();
		}
		File file_out = new File(localpath + File.separator + filename);
		
		try {
			
			FTPFile[] fileInfoArray = ftpClient.listFiles(filename);
			if (fileInfoArray == null) {
				throw new FileNotFoundException("File " + filename + " was not found on FTP server.");
			}
			
			// Download file.
			out = new BufferedOutputStream(new FileOutputStream(file_out));
			if (!ftpClient.retrieveFile(filename, out)) {
				throw new IOException("Error loading file " + filename + " from FTP server. Check FTP permissions and path.");
			}
			
			out.flush();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ex) {
				}
			}
		}
		
	}
	
	public long getFileSize(FTPClient ftpClient, String filename) throws Exception {
		
		long fileSize = -1;
		FTPFile[] fileInfoArray = ftpClient.listFiles(filename);
		if (fileInfoArray == null) {
			throw new FileNotFoundException("File " + filename + " was not found on FTP server.");
		}
		
		// Check file size.
		FTPFile fileInfo = fileInfoArray[0];
		
		//logger.debug(fileInfo.getName()+":"+fileInfo.getSize());
		
		fileSize = fileInfo.getSize();
		
		return fileSize;
		
	}
	
	/**
	 * Lists the files in the given FTP directory.
	 * 获取指定目录下的文件名列表
	 * 
	 * @param filePath
	 *        absolute path on the server
	 * @return files relative names list
	 * @throws IOException
	 *         on I/O errors
	 */
	public List<String> getFileNameList(String filePath) throws Exception {
		FTPClient ftpClient = this.getFtpClient();
		try {
			List<String> fileList = new ArrayList<String>();
			
			FTPFile[] ftpFiles;
			if (filePath == null) {
				ftpFiles = ftpClient.listFiles();
			} else {
				ftpFiles = ftpClient.listFiles(filePath);
			}
			int size = (ftpFiles == null) ? 0 : ftpFiles.length;
			for (int i = 0; i < size; i++) {
				FTPFile ftpFile = ftpFiles[i];
				if (ftpFile.isFile()) {
					fileList.add(ftpFile.getName());
				}
			}
			return fileList;
		} catch (Exception e) {
			throw e;
		} finally {
			this.closeFtpClient(ftpClient);
		}
		
	}
	
	//下传标志文件（包含前缀、后缀）
	public void ftpflagfileList(String preStr, String endstr) throws Exception {
		// 创建FtpClient对象
		FTPClient ftpClient = this.getFtpClient();
		try {
			FTPFile[] ftpFiles = ftpClient.listFiles();
			
			for (FTPFile ff : ftpFiles) {
				String filename = ff.getName();
				if (filename.indexOf(preStr) < 0 || filename.indexOf(endstr) < 0) {
					continue;
				}
				getfile(ftpClient, filename);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			// 退出FTP服务器
			this.closeFtpClient(ftpClient);
		}
	}
	
	public void uploadDirectory(String directory) throws Exception {
		FTPClient ftpClient = this.getFtpClient();
		
		try {
			File file = new File(directory);
			if (file.isDirectory()) { //路径直接创建
				String dir = file.getName();
				ftpClient.makeDirectory(dir);
				logger.debug("创建目录：" + dir);
				ftpClient.cwd(dir);
				File[] files = file.listFiles();
				for (File subfile : files) {
					if (subfile.isDirectory()) {
						ftpClient.cwd("..");
						ftpClient.cwd(dir);
						uploadDirectory(subfile.getAbsolutePath());
					} else {
						ftpClient.cwd("..");
						ftpClient.cwd(dir);
						uploadFile(ftpClient, new File(directory + File.separator + subfile.getName()));
					}
				}
				ftpClient.cwd("..");
			} else {
				uploadFile(ftpClient, file);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			// 退出FTP服务器
			this.closeFtpClient(ftpClient);
			
		}
		
	}
	
	/***
	 * 上传Ftp文件
	 * 
	 * @param localFile 当地文件
	 * @param remotePath上传服务器路径
	 */
	private boolean uploadFile(FTPClient ftpClient, File localFile) {
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			inStream = new BufferedInputStream(new FileInputStream(localFile));
			logger.debug(localFile.getName() + "开始上传.....");
			success = ftpClient.storeFile(localFile.getName(), inStream);
			if (success == true) {
				logger.debug(localFile.getName() + "上传成功");
				return success;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(localFile + "未找到");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}
	
	/***
	 * 上传Ftp文件
	 * 
	 * @param localFile 当地文件
	 * @param remotePath上传服务器路径
	 * @throws Exception
	 */
	public boolean uploadFile(File localFile) throws Exception {
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			FTPClient ftpClient = this.getFtpClient();
			inStream = new BufferedInputStream(new FileInputStream(localFile));
			logger.debug(localFile.getName() + "开始上传.....");
			success = ftpClient.storeFile(localFile.getName(), inStream);
			if (success == true) {
				logger.debug(localFile.getName() + "上传成功");
				return success;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(localFile + "未找到");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}
	
	private void closeFtpClient(FTPClient ftpClient) {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException ex) {
			}
		}
	}
	
	/**
	 * 删除远程服务器中的文件
	 * 
	 * @param filepath 文件目录
	 * @param filename 文件名
	 * @throws Exception
	 * @author Liu Xuanfei
	 * @date 2016年10月11日 下午4:42:37
	 */
	public void removeFile(String filename) throws Exception {
		FTPClient ftpClient = this.getFtpClient();
		try {
			boolean exist = ftpClient.deleteFile(filename);
			if (exist) {
				// success
			} else {
				// failed
			}
		} finally {
			this.closeFtpClient(ftpClient);
		}
	}
	
	public static void main(String[] args) {
		FtpUtils ftp = new FtpUtils();
		ftp.setServer("172.16.179.135");
		ftp.setUser("aggtec");
		ftp.setPassword("aggtec");
		ftp.setRemotepath("/datafilepath");
		ftp.setLocalpath("/Users/hubaiqing/Desktop/testftp");
		//ftp.setFilename("");
		
		try {
			
			HashMap hash = ftp.getRemoteFileList();
			
			Iterator iter = hash.keySet().iterator();
			ArrayList list = new ArrayList();
			
			while (iter.hasNext()) {
				String filename = (String) iter.next();
				logger.debug(filename);
				list.add(filename);
				
				ftp.getfile(filename);
				
			}
			
			HashMap sizeHash = ftp.getRemoteFileSize(list);
			
			logger.debug("" + sizeHash);
			
			ftp.uploadDirectory("/Users/hubaiqing/Desktop/testftp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}
	
	/**
	 * @param server
	 *        the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}
	
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * @param user
	 *        the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password
	 *        the password to set
	 */
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
	
	/**
	 * @return the localpath
	 */
	public String getLocalpath() {
		return localpath;
	}
	
	/**
	 * @param localpath the localpath to set
	 */
	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}
	
	/**
	 * @return the remotepath
	 */
	public String getRemotepath() {
		return remotepath;
	}
	
	/**
	 * @param remotepath the remotepath to set
	 */
	public void setRemotepath(String remotepath) {
		this.remotepath = remotepath;
	}
	
}
