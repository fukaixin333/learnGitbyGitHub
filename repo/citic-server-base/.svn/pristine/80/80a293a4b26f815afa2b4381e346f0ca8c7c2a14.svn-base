package com.citic.server.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 * @author hubq
 */
public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	/** 文件缓冲区的长度 */
	private static int buffersize = 1024;
	
	public FileUtils() {
	}
	
	/**
	 * 从文件路径中读取文件名称
	 * 
	 * @param oldFileUrl 文件名含路径
	 * @return 文件名
	 */
	public synchronized static String getFilenameFromPath(String oldFileUrl) {
		int lastIndex = oldFileUrl.lastIndexOf("\\");
		if (lastIndex < 1 || lastIndex >= oldFileUrl.length() - 1)
			lastIndex = oldFileUrl.lastIndexOf("/");
		if (lastIndex >= 0 && lastIndex <= oldFileUrl.length())
			return oldFileUrl.substring(lastIndex + 1).trim();
		else
			return oldFileUrl;
	}
	
	/**
	 * 根据绝对路径创建文件
	 * 
	 * @param oldFileUrl 文件名含路径
	 * @return 文件File
	 */
	public synchronized static File getFile(String filePathName) {
		String path = FileUtils.getPathFromPathName(filePathName);
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File file = new File(filePathName);
		return file;
	}
	
	/**
	 * 从文件路径中读取文件路径
	 * 
	 * @param oldFileUrl 文件名含路径
	 * @return 文件路径
	 */
	public synchronized static String getPathFromPathName(String oldFileUrl) {
		int lastIndex = oldFileUrl.lastIndexOf("\\");
		if (lastIndex < 1 || lastIndex >= oldFileUrl.length() - 1)
			lastIndex = oldFileUrl.lastIndexOf("/");
		if (lastIndex >= 0 && lastIndex <= oldFileUrl.length())
			return oldFileUrl.substring(0, lastIndex).trim();
		else
			return oldFileUrl;
	}
	
	/**
	 * 日期型动态路径 ROOT_YYYY-MM-DD = ROOT_2011-09-11
	 * 
	 * @param path
	 * @param dtStr
	 * @return
	 */
	public String replateDTpath(String path, String dtStr) {
		
		path = path.replace("YYYY-MM-DD", dtStr);
		path = path.replace("yyyy-mm-dd", dtStr);
		
		dtStr = dtStr.replace("-", "");
		
		path = path.replace("YYYYMMDD", dtStr);
		path = path.replace("yyyymmdd", dtStr);
		
		return path;
	}
	
	/** 2.获取路径下的文件 ****/
	private File[] getFile(File fileDir) {
		File[] files = null;
		if (fileDir.isDirectory()) {
			files = fileDir.listFiles();
		}
		return files;
		
	}
	
	/**
	 * 删除目录下符合某种
	 * 
	 * @param filepath
	 * @param filter
	 * @return
	 */
	public boolean delDir(File filepath, FilenameFilter filter) throws Exception {
		boolean isSucc = false;
		File[] files = filepath.listFiles();
		if (filter != null)
			files = filepath.listFiles(filter);
		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					delFile(files[i]);
				}
			}
			filepath.delete();
		} catch (Exception ex) {
			isSucc = false;
			ex.printStackTrace();
			logger.info("删除" + filepath.getAbsolutePath() + "或其下的文件失败!");
			throw ex;
		}
		return isSucc;
	}
	
	/**
	 * 删除单个文件或单个目录
	 * 
	 * @param filename
	 *        File对象
	 * @return 是否成功
	 */
	private boolean delFile(File filename) throws Exception {
		boolean isSucc = false;
		
		try {
			if (filename.isDirectory()) {
				delDir(filename, null);
			}
			if (filename.isFile() && filename.exists())
				filename.delete();
		} catch (Exception ex) {
			isSucc = false;
			ex.printStackTrace();
			logger.info("删除文件:" + filename.getAbsolutePath() + "失败!");
			throw ex;
		}
		return isSucc;
	}
	
	/**
	 * 创建数据目录
	 * 
	 * @param filename
	 */
	public boolean mkDir(String filenameStr) {
		boolean isSucc = false;
		
		try {
			
			File file = new File(filenameStr);
			if (!file.exists())
				isSucc = new File(filenameStr).mkdir();
			else
				isSucc = true;
		} catch (Exception ex) {
			isSucc = false;
			ex.printStackTrace();
		}
		return isSucc;
		
	}
	
	/**
	 * 根据路径创建目录，包括所有不存在的父目录
	 * 
	 * @param path 待创建目录的路径
	 */
	public static void mkDirs(String path) {
		File dir = new File(path);
		if (dir.exists() && dir.isDirectory()) {
			return;
		}
		dir.mkdirs();
	}
	
	/**
	 * 按文件名进行过滤
	 * 
	 * @author Administrator
	 */
	public class Filter implements FilenameFilter {
		String ext;
		
		private Filter(String ext) {
			this.ext = "." + ext;
		}
		
		public boolean accept(File dir, String name) {
			return name.endsWith(ext);
		}
		
	}
	
	/**
	 * 拷贝文件，并替换其中内容
	 * 
	 * @param sourceDir 原始文件目录，
	 * @param targetDir 目标文件目录，
	 * @param repStrHash 文件需要替换的字符串信息
	 * @return
	 */
	public boolean copyFolderAndReplaceCmd(String sourceDir, String targetDir, LinkedHashMap strHash) throws Exception {
		boolean isSucc = true;
		try {
			new File(targetDir).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(sourceDir);
			String[] file = a.list();
			File temp = null;
			if (file != null && file.length > 0) {
				for (int i = 0; i < file.length; i++) {
					if (sourceDir.endsWith(File.separator)) {
						temp = new File(sourceDir + file[i]);
					} else {
						temp = new File(sourceDir + File.separator + file[i]);
					}
					if (temp.isFile()) {
						//---------------------------------
						FileOutputStream output = new FileOutputStream(targetDir + "/" + (temp.getName()).toString());
						
						//String return_str = "";
						FileReader fr = null;
						LineNumberReader lr = null;
						
						try {
							fr = new FileReader(temp);
							lr = new LineNumberReader(fr, 512);
							while (true) {
								//读取源文件内容
								String str = lr.readLine();
								if (str == null)
									break;
								String lineStr = str + "\n";
								
								//替换文件内容
								
								Iterator iter = strHash.keySet().iterator();
								
								while (iter.hasNext()) {
									String oldString = (String) iter.next();
									String newString = (String) strHash.get(oldString);
									
									lineStr = lineStr.replace(oldString, newString);
									
								}
								
								//输出到目标文件
								output.write(lineStr.getBytes());
								
							}
							lr.close();
							lr = null;
							fr.close();
							fr = null;
						} catch (Exception e) {
							System.err.println("IO error");
						} finally {
							try {
								if (fr != null) {
									fr.close();
								}
								if (lr != null) {
									lr.close();
								}
								output.flush();
								output.close();
							} catch (Exception ex) {
							}
						}
						//---------------------------------
					}
					if (temp.isDirectory()) {// 如果是子文件夹
						this.copyFolderAndReplaceCmd(sourceDir + "/" + file[i], targetDir + "/" + file[i], strHash);
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("复制整个文件夹内容操作出错" + e.getMessage());
		}
		return isSucc;
	}
	
	public static boolean renameFile(String oldFile, String newFile) {
		try {
			File f = new File(newFile);
			File f2 = new File(oldFile);
			f2.renameTo(f);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 说明:单个文件的拷贝 文件属性在拷贝中丢失
	 * 
	 * @param from
	 *        原文件
	 * @param to
	 *        目标文件
	 * @return boolean true 成功 false 失败
	 */
	public static boolean copyFile(File src, File dest) {
		FileInputStream input = null;
		FileOutputStream output = null;
		
		try {
			input = new FileInputStream(src);
			output = new FileOutputStream(dest);
			
			int len;
			byte[] buffer = new byte[buffersize];
			while ((len = input.read(buffer, 0, buffersize)) > 0) {
				output.write(buffer, 0, len);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (output != null) {
				try {
					output.flush();
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除文件
	 * 
	 * @param filename
	 *        文件名(要包括完整的路径)
	 */
	public static void deleteDir(String filename) {
		boolean isSucc = false;
		try {
			File file = new File(filename);
			if (file.exists()) {
				deleteFile(file);
				isSucc = file.delete();
			}
		} catch (SecurityException e) {
			System.err.println("Delete File Fails:" + e.getMessage());
		}
	}
	
	/**
	 * 删除文件或目录
	 * 
	 * @param filePath 待删除文件或目录路径
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		deleteFile(file);
	}
	
	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
				file.delete();
			} else {
				file.delete();
			}
		}
	}
	
	/**
	 * 根据文件类型获取目标目录下的文件数组
	 * 
	 * @param dir
	 *        目标目录
	 * @param fileType
	 *        指定的文件类型。支持"file","directory","xml","txt","pdf","jpg","csv"格式，可根据需要扩展
	 *        ； file：返回文件，非目录；directory：返回目录；xml:返回xml格式文件；其他同理。
	 * @return
	 */
	public static File[] getFileListByDirectoryAndType(File dir, final String fileType) {
		// 获取目录
		File directory = dir;
		// 目录不存在或者目录是文件，返回0个长度的文件数组
		if (directory.isFile() || !directory.exists()) {
			return new File[0];
		}
		// 支持的文件格式
		String filterType[] = {"file", "directory", "xml", "txt", "pdf", "jpg", "csv"};
		HashSet<String> typeSet = Sets.newHashSet(filterType);
		// 文件格式不支持，返回0个长度的文件数组
		if (!typeSet.contains(fileType)) {
			return new File[0];
		}
		// 生成过滤规则
		FileFilter filter = null;
		if ("file".equals(fileType)) {// 文件
			filter = new FileFilter() {
				public boolean accept(File file) {
					return file.isFile();
				}
			};
		} else if ("directory".equals(fileType)) {// 目录
			filter = new FileFilter() {
				public boolean accept(File file) {
					return file.isDirectory();
				}
			};
		} else {// 指定格式的文件
			filter = new FileFilter() {
				public boolean accept(File file) {
					return file.getName().endsWith("." + fileType);
				}
			};
		}
		// 获取过滤后的文件数组
		File[] files = directory.listFiles(filter);
		
		return files;
	}
	
	/**
	 * 获取文件名数组
	 * 
	 * @param dir
	 *        目标目录
	 * @param fileType
	 *        指定的文件类型。支持"file","directory","xml","txt","pdf","jpg","csv","zip"格式，可根据需要扩展
	 *        ；
	 * @return
	 */
	public static String[] getFileNameListByDirectoryAndType(File dir, final String fileType) {
		// 获取目录
		File directory = dir;
		// 目录不存在或者目录是文件，返回0个长度的文件数组
		if (directory.isFile() || !directory.exists()) {
			return new String[0];
		}
		// 支持的文件格式
		String filterType[] = {"file", "directory", "xml", "txt", "pdf", "jpg", "csv", "zip"};
		HashSet<String> typeSet = Sets.newHashSet(filterType);
		// 文件格式不支持，返回0个长度的文件数组
		if (!typeSet.contains(fileType)) {
			return new String[0];
		}
		// 生成过滤规则
		FilenameFilter filter = null;
		if ("file".equals(fileType)) {// 文件
			filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.indexOf(".") != -1;
				}
			};
		} else if ("directory".equals(fileType)) {// 目录
			filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.indexOf(".") == -1;
				}
			};
		} else {// 指定格式的文件
			filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith("." + fileType);
				}
			};
		}
		// 获取过滤后的文件名数组
		String fieleName[] = directory.list(filter);
		
		return fieleName;
	}
	
	/**
	 * 移动文件
	 * 
	 * @param srcPath 待移动文件路径
	 * @param destPath 目标目录路径
	 */
	public static void moveFile(String srcPath, String destPath) {
		copyFile(srcPath, destPath);
		deleteFile(srcPath);
	}
	
	public static void copyFile(String srcFilePath, String destDirPath) {
		File srcFile = new File(srcFilePath);
		if (!srcFile.exists()) {
			return;
		}
		mkDirs(destDirPath);
		File destFile = new File(destDirPath + File.separator + srcFile.getName());
		copyFile(srcFile, destFile);
	}
	
	
	/**
	 * 说明：将内容写到文件中,如果文件已经存在，则接到后面写
	 * @param filepath: 文件全路径，例如：d:/test.txt
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	public static boolean contentAppendToFile(String filepath,String content) throws Exception{
		FileOutputStream fos = null;
        boolean flag=true;
        try{
            File objFile=new File(filepath);
            if(!objFile.exists()){
                objFile.createNewFile(); 
            }
            byte[] b = content.getBytes();
            fos = new FileOutputStream(filepath,true);
            fos.write(b);
        }catch(Exception e){
            e.printStackTrace();
            flag=false;
            throw e;
        }finally{
            try{
                fos.close();
            }catch(Exception e){}
        }
        return flag;
	}
    /**
     * 说明：将内容写到文件中,如果文件已经存在，则接到后面写
     * @param directory 路径,例如: D:/workpath/file\2016-05-26_fjdbc
     * @param filename 文件名,例如: T00_ORGAN.del
     * @param content 内容
     * @return boolean  写文件成功或失败
     * @throws Exception 
     */
    public static boolean contentAppendToFile(String directory,String filename,String content) throws Exception{ 
        return contentAppendToFile(directory+File.separator+filename, content);
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
}
